package ru.maxitel.lk.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.Tariff;
import ru.maxitel.lk.Tariffs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TariffFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TariffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TariffFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "tariff";


    private Tariff tariff;

    private OnFragmentInteractionListener mListener;

    public TariffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TariffFragment.
     */

    public static TariffFragment newInstance(String tarif) {
        TariffFragment fragment = new TariffFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, tarif);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String tariff_name = getArguments().getString(ARG_PARAM);
            tariff = Tariffs.getTarif(tariff_name);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tariff, container, false);

        //Абоненсткая плата
        TextView payTextView = (TextView) view.findViewById(R.id.tariff_price);
        payTextView.setText(String.format(getString(R.string.rub_mes), tariff.getPrise()));

        //Скорость
        TextView speedTextView = (TextView) view.findViewById(R.id.speedTextView);
        speedTextView.setText(tariff.toString());

        //стоимость подключение тарифа
        TextView addTariffTextView = (TextView) view.findViewById(R.id.addTariffTextView);
        addTariffTextView.setText(String.format(getString(R.string.rub_add),tariff.getCostOfSwitching()));

        //кнопка подклюить
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfirmActivity.class);
                intent.setAction(ConfirmActivity.TARIFF_CHANGE);
                intent.putExtra(ConfirmActivity.TARIFF_CHANGE, tariff.getName());
                startActivity(intent);
            }
        });

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

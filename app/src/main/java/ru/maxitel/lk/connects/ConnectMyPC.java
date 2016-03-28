package ru.maxitel.lk.connects;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ru.maxitel.lk.User;



public class ConnectMyPC implements Closeable {

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ConnectMyPC(Socket socket) throws IOException
    {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public  void send(String message) throws IOException{
        synchronized (out)
        {
            out.println(message);
        }
    }
    public String receive() throws IOException{
        synchronized (in)
        {
            String name = in.readLine();
            switch (name){
                case "password":
                    return receivePassword();
                case "cookies":
                    return receiveCookies();
                case "widget":
                    return receiveWidget();
                case "tariffs":
                    return receiveTariffs();
                case "payment":
                    return receivePayment();
                case "message":
                    return receiveMessage();
                case "service":
                    return receiveService();
            }
            return null;
        }
    }

    private String receiveService() throws IOException{
        if (in.readLine().equals("true")) return "block";
        else {
            String response="";
            String s;
            while ((s=in.readLine())!=null){
                response+=s;
            }
            return response;
        }
    }

    @Override
    public void close() throws IOException
    {
        socket.close();
        out.close();
        in.close();
    }

    private String receivePassword() throws IOException {
        Map<String,String> cookies = new HashMap<>();
        cookies.put("user_id", in.readLine());
        cookies.put("user_login", in.readLine());

        User.setCookies(cookies);
        User.setVoluntarilyLocked(Boolean.parseBoolean(in.readLine()));

        String response="";
        String s;
        while ((s=in.readLine())!=null){
            response+=s;
        }
        return response;
    }
    private String receiveWidget() throws IOException {
        String response="";
        String s;
        while ((s=in.readLine())!=null){
            response+=s;
        }
        return response;
    }
    private String receiveCookies() throws IOException{
        User.setVoluntarilyLocked(Boolean.parseBoolean(in.readLine()));
        String response="";
        String s;
        while ((s=in.readLine())!=null){
            response+=s;
        }
        return response;
    }
    private String receiveTariffs() throws IOException{
        return receiveWidget();
    }
    private String receivePayment() throws IOException{
        return receiveWidget();
    }
    private String receiveMessage() throws IOException{
        return in.readLine();
    }
}

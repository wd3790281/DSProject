package au.edu.unimelb.dingw.Client;

import org.json.simple.JSONObject;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by dingwang on 15/9/11.
 */
public class Client {

    private PrintWriter out;
    private BufferedReader in;
    private Thread listenThread;
    private String host;
    private int port;

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() {
        SSLSocketFactory sslsocketfactory =
                new SSLKeyStoreLoader().load();
        SSLSocket sslsocket = null;

        try {
            sslsocket = (SSLSocket) sslsocketfactory.createSocket(this.host, this.port);
            sslsocket.startHandshake();
            System.out.println("Client Connected...");

            this.in = new BufferedReader(new InputStreamReader(sslsocket.getInputStream(), "UTF-8"));
            this.out = new PrintWriter(new OutputStreamWriter(sslsocket.getOutputStream(), "UTF-8"));

            Scanner cmdin = new Scanner(System.in);

            ResponseListener listener = new ResponseListener(sslsocket);
            this.listenThread = new Thread(listener);
            listenThread.start();

            while (true) {

                String command = cmdin.nextLine();

                if(ClientInfo.clientID == null && (!command.startsWith("#guest")) && (!command.startsWith
                        ("#userlogin"))){

                    System.out.println("Haven't choose identtiy");
                    prompt();
                    continue;

                }

                if (command.startsWith("#quit")) {

                    break;

                }
                JSONObject send = CommandListener.listenCommand(command);
                if (send == null) {
                    prompt();
                    continue;
                }else {
                    out.println(send.toString());
                    out.flush();
                }
            }

            cmdin.close();

        } catch (SocketException e){
            System.out.println("Server is closed");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JSONObject quit = new JSONObject();
            quit.put("type", "quit");
            this.out.println(quit.toString());
            this.out.flush();
            this.listenThread.interrupt();
            try {
                this.listenThread.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                in.close();
                out.close();
                sslsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("Disconnected from server");
        }
    }


    public static void main(String[] args) {

//        System.setProperty("javax.net.ssl.trustStore", "myKey");
//        System.setProperty("javax.net.ssl.trustStrorePassword", "123456");

        Arguements arguements = new Arguements();
        CmdLineParser parser = new CmdLineParser(arguements);

        try {
            parser.parseArgument(args);
            Client client = new Client(arguements.remoteServer,arguements.port);
            client.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void prompt(){
        System.out.print("[" + ClientInfo.currentRoomID + "] " + ClientInfo.clientID + "> ");
    }
}

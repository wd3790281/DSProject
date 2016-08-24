package au.edu.unimelb.dingw.Server;

import au.edu.unimelb.dingw.Server.Tools.Arguements;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by dingwang on 15/9/11.
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {
//        System.setProperty("javax.net.ssl.keyStore",);
//        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        Arguements arguements = new Arguements();
        CmdLineParser parser = new CmdLineParser(arguements);
        try {
            parser.parseArgument(args);
        } catch(CmdLineException e){
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

//        SSLServerSocketFactory sslserversocketfactory =
//                (SSLServerSocketFactory)
//                        SSLServerSocketFactory.getDefault();
        SSLServerSocketFactory sslserversocketfactory = new SSLKeyStoreLoader().load();
        SSLServerSocket sslServerSocket = null;
        try {
            sslServerSocket = (SSLServerSocket)
                    sslserversocketfactory.createServerSocket(arguements.port);

            System.out.println("ServerMain is listening...");

            while (true) {
                SSLSocket socket = (SSLSocket) sslServerSocket.accept();
                socket.startHandshake();

                System.out.println("Client Connected...");

                Thread client = new Thread(new ClientThread(socket));
                client.start();

            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sslServerSocket != null)
                sslServerSocket.close();
        }
    }
}

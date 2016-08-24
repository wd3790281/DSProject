package au.edu.unimelb.dingw.Client;

import au.edu.unimelb.dingw.Client.ResponseHelper.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dingwang on 15/9/15`.
 */


public class ResponseListener implements Runnable{

    private Socket socket;
    private DataInputStream in;
    public ResponseListener(Socket socket){
        this.socket = socket;
        try {
            this.in = new DataInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String response = null;
//        try {
//            response = this.in.readUTF();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try{
            while ((response= this.in.readLine()) != null){
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

                String type = (String) jsonObject.get("type");
                switch (type) {

                    case "newidentity":
                        new NewIdentityHelper().helpID(response);
                        Client.prompt();
                        break;
                    case "roomcontents":
                        new RoomContentsHelper().helpRoomContents(response);
                        Client.prompt();
                        break;
                    case "roomlist":
                        new RoomListHelper().helpList(response);
                        Client.prompt();
                        break;
                    case "roomchange":
                        new RoomChangeHelper().helpChange(response);
//                        Client.prompt();
                        break;
                    case "message":
                        new MessageHelper().helpMessage(response);
                        Client.prompt();
                        break;
                    case "error":
                        new ErrorHelper().helpError(response);
                        Client.prompt();
                        break;
                    case "systemmessage":
                        new SystemMessageHelper().help(response);
                        Client.prompt();
                        break;
                    default:

                        continue;
                }

            }
            System.out.print("Disconnected from server");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package au.edu.unimelb.dingw.Server;

import au.edu.unimelb.dingw.Server.Functions.*;
import au.edu.unimelb.dingw.Server.ModelHelper.AuthenticationHelper;
import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by dingwang on 15/9/14.
 */
public class MessageManager {

    public MessageManager(){

    }

    public void manageMessage(Guest newguest, String message) throws IOException {

        JSONParser parser = new JSONParser();
        JSONObject command = null;
        try {
            command = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String type = (String) command.get("type");
        switch (type){
            case "identitychange":
                ChangIdentity.handler(command, newguest);
                break;
            case "join":
                RoomChange.handler(command,newguest);
                break;
            case "who":
                RoomContent.handler(command,newguest);
                break;
            case "list":
                newguest.sendMessage(JSONcreater.roomList());
                break;
            case "createroom":
                CreateRoom.handler(command,newguest);
                break;
            case "kick":
                KickUser.handler(command,newguest);
                break;
            case "delete":
                DeleteRoom.handler(command,newguest);
                break;
            case "message":
                ChatRoom room;
                String contents = (String) command.get("content");
                room = newguest.getCurrentRoom();
                String  msg= JSONcreater.createMessage(newguest, contents);
                room.broadcast(msg);

                break;
            case "changepassword":
                String password = (String) command.get("password");
                Boolean changed = AuthenticationHelper.changePassword(newguest.getID(), password);
                if(changed)
                    newguest.sendMessage(JSONcreater.systemMessage("changed"));
                else
                    newguest.sendMessage(JSONcreater.systemMessage("User doesn't exist"));
                break;
            default:
                newguest.sendMessage("Wrong commend");
                break;

        }
    }
}

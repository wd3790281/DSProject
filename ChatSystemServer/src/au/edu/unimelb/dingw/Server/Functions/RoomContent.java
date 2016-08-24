package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import org.json.simple.JSONObject;

import java.io.IOException;

import static au.edu.unimelb.dingw.Server.Models.ChatRoom.findRoom;

/**
 * Created by dingwang on 15/9/14.
 */
public class RoomContent {
    public static void handler(JSONObject command, Guest newguest) throws IOException {
        String roomId;
        ChatRoom room;
        String response;
        roomId = (String) command.get("roomid");
        room = findRoom(roomId);
        if(room != null) {
            response = JSONcreater.roomContents(room, newguest);
        }else{
            response = JSONcreater.error("The request room is invalid or non existent");

        }
        newguest.sendMessage(response);

    }
}

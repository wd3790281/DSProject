package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import au.edu.unimelb.dingw.Server.ModelHelper.RoomHelper;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by dingwang on 15/9/14.
 */
public class CreateRoom {

    public static void handler(JSONObject command, Guest newguest) throws IOException {
        String roomId;
        ChatRoom room;
        String response;

        ChatRoom preveriousRoom = newguest.getCurrentRoom();
        roomId = (String) command.get("roomid");
        room = RoomHelper.createRoom(roomId, newguest);
        if(room != null){
            response = JSONcreater.roomList();
            String enterCreated = JSONcreater.createRoomChange(preveriousRoom, room, newguest);
            newguest.sendMessage(enterCreated);
        }else{
            response = JSONcreater.error("Room " + roomId + " is invalid or already in use");
        }
        newguest.sendMessage(response);
    }
}

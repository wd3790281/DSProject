package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import au.edu.unimelb.dingw.Server.ModelHelper.RoomHelper;
import org.json.simple.JSONObject;

import java.io.IOException;

import static au.edu.unimelb.dingw.Server.Models.ChatRoom.findRoom;

/**
 * Created by dingwang on 15/9/14.
 */
public class RoomChange {

    public static void handler(JSONObject command, Guest newguest) throws IOException {
        ChatRoom room;
        String response;
        Boolean success = false;
        String requiredRoom = (String) command.get("roomid");
        ChatRoom formerRoom = newguest.getCurrentRoom();
        room = findRoom(requiredRoom);

        if (room != null){
            success = RoomHelper.joinRoom(newguest, room, formerRoom);
        }else{
            response = JSONcreater.error("The request room is invalid or non existent or you can not join the room");
            newguest.sendMessage(response);
        }

        if(success) {
            if(room == ChatRoom.MainHall) {
                response = JSONcreater.createRoomChange(formerRoom, room, newguest);
                newguest.sendMessage(response);
                response = JSONcreater.roomList();
                ChatRoom.MainHall.broadcast(response);
                response = JSONcreater.roomContents(ChatRoom.MainHall,newguest);
                newguest.sendMessage(response);

            }else {
                response = JSONcreater.createRoomChange(formerRoom, room, newguest);
                room.broadcast(response);
            }
        }
        else {
            response = JSONcreater.createRoomChange(formerRoom, formerRoom,newguest);
            newguest.sendMessage(response);
        }
    }

}

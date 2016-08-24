package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.ModelHelper.AuthenticationHelper;
import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import au.edu.unimelb.dingw.Server.ModelHelper.RoomHelper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static au.edu.unimelb.dingw.Server.Models.ChatRoom.findRoom;

/**
 * Created by dingwang on 15/9/14.
 */
public class DeleteRoom {

    public static void handler(JSONObject command, Guest newguest) throws IOException {
        if(AuthenticationHelper.checkAutentication(newguest.getID())) {
            String roomId;
            ChatRoom room;
            Boolean success;
            roomId = (String) command.get("roomid");
            room = findRoom(roomId);
            success = RoomHelper.removeOutAllGuest(newguest, room);

            if (room != ChatRoom.MainHall && room.getOwner() == null && room.getGuest().size() == 0) {
                ChatRoom.allRooms.remove(room);
            }

            if (success) {
                String listMsg = JSONcreater.roomList();
                newguest.sendMessage(listMsg);
            }
        }else{
            newguest.sendMessage(JSONcreater.error("You do not have the right to delete room"));
        }
    }
}

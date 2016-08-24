package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.ModelHelper.AuthenticationHelper;
import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import au.edu.unimelb.dingw.Server.ModelHelper.RoomHelper;
import org.json.simple.JSONObject;

import java.io.IOException;

import static au.edu.unimelb.dingw.Server.ModelHelper.GuestHelper.findGuest;

/**
 * Created by dingwang on 15/9/14.
 */
public class KickUser {
    public static void handler(JSONObject command, Guest newguest) throws IOException {
        if(AuthenticationHelper.checkAutentication(newguest.getID())) {
            String roomId;
            ChatRoom room;
            String response;
            String kickedGuestID = (String) command.get("identity");
            Guest kickedGuest = findGuest(kickedGuestID);
            Long time = (Long) command.get("time");
            roomId = (String) command.get("roomid");
            room = ChatRoom.findRoom(roomId);
            if(room == null){
                return;
            }

            if (kickedGuest != null) {

                response = JSONcreater.createRoomChange(room, ChatRoom.MainHall, kickedGuest);
                room.broadcast(response);
                RoomHelper.kickUser(room, newguest, kickedGuest, time);

                String response2 = JSONcreater.roomList();
                kickedGuest.sendMessage(response2);
                String response3 = JSONcreater.roomContents(ChatRoom.MainHall, kickedGuest);
                kickedGuest.sendMessage(response3);

            }
        }else {
            newguest.sendMessage(JSONcreater.error("You do not have the right to kick users"));
        }
    }
}

package au.edu.unimelb.dingw.Server.ModelHelper;



import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dingwang on 15/9/30.
 */
public class RoomHelper {
    public static synchronized ChatRoom createRoom(String roomID, Guest guest){
        String pattern = "^[a-zA-Z]\\w{2,31}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(roomID);
        if(!m.matches()){
            return null;
        }

        ChatRoom room = ChatRoom.findRoom(roomID);

        ChatRoom newRoom = null;
        if(room == null){
            newRoom = new ChatRoom(roomID, guest);
            ChatRoom.allRooms.add(newRoom);
            joinRoom(guest, newRoom, guest.getCurrentRoom());
        }
        return newRoom;
    }

    public static synchronized Boolean removeOutAllGuest(Guest guest, ChatRoom room)  {
        Boolean success = false;
        if ( !ChatRoom.allRooms.contains(room) || (room.getOwner()!= guest) || (room == ChatRoom.MainHall) ){
            return success;
        }

        Set<Guest> copySet = new HashSet<>(room.getGuest());

        for (Guest u : copySet) {
            joinRoom(u, ChatRoom.MainHall, room);
            String response = JSONcreater.createRoomChange(room, ChatRoom.MainHall, u);
            u.sendMessage(response);
        }

        room.owner = null;
        success = true;

        return success;
    }

    public static synchronized boolean kickUser(ChatRoom room, Guest owner, Guest guest, Long time) {

        Boolean success = false;

        if(room.getOwner() == owner && room.getGuest().contains(guest)) {
            Date now = new Date();
            Pair<Date, Long> kickInfo = new Pair<>(now, time);
            room.kickedGuest.put(guest, kickInfo);
            joinRoom(guest, ChatRoom.MainHall, room);
            success = true;
        }
        return success;
    }



    public static synchronized void quitRoom (Guest guest, ChatRoom room){
        if(room == null || guest == null){
            return;
        }
        room.removeGuest(guest);
        if(room.getOwner() == guest && !AuthenticationHelper.checkAutentication(guest.getID())){
            RoomHelper.removeOutAllGuest(guest, room);
            ChatRoom.allRooms.remove(room);
        }
    }

    public static synchronized Boolean joinRoom(Guest guest, ChatRoom newRoom, ChatRoom formerRoom){
        Boolean success = false;

        if(formerRoom != newRoom && newRoom.joinable(guest) && newRoom != null && guest != null) {
            quitRoom(guest, formerRoom);
            newRoom.addGuest(guest);
            guest.setCurrentRoom(newRoom);
            success = true;
        }

        return success;
    }

}

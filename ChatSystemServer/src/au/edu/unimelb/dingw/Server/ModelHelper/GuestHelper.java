package au.edu.unimelb.dingw.Server.ModelHelper;

import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dingwang on 15/9/16.
 */
public class GuestHelper {

    public static Set<Guest> onlineGuestList = new HashSet<>();


    public static synchronized Guest createGuest(){

        int postfix = 1;
        for( ; ; postfix++) {
            String id = "guest" + postfix;
            Guest guest = null;
            for (Guest g : onlineGuestList) {
                if (g.getID().equals(id)) {
                    guest = g;
                    break;
                }
            }
            if (guest == null)
                break;
        }
        Guest newguest = new Guest("guest"+postfix);

        onlineGuestList.add(newguest);
        return newguest;
    }

    public static synchronized Boolean identityChange(Guest guest, String newIdentity){
        Boolean changed = false;
        String pattern = "^[a-zA-Z]\\w{2,15}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(newIdentity);
        if(!m.matches()){
            return changed;
        }
        Boolean idUsed = false;
        for(Guest g : onlineGuestList){
            if(g.getID().equals(newIdentity)){
                idUsed = true;
                break;
            }
        }
        String former = guest.getID();

        if(!former.equals(newIdentity) && (idUsed == false)){
            guest.changeID(newIdentity);
            changed = true;
        }
        return changed;
    }

    public static Guest findGuest(String guestID){
        Guest guest = null;
        for(Guest g : onlineGuestList){
            if(g.getID().equals(guestID)){
                guest = g;
                break;
            }
        }
        return guest;
    }

    public synchronized static void removeGuest (Guest guest){
        onlineGuestList.remove(guest);
    }

    public static void quit(Guest guest) {

        ChatRoom room = guest.getCurrentRoom();
        if(room != null) {
            String response = JSONcreater.createRoomChange(room, null, guest);
            room.broadcast(response);
            room.removeGuest(guest);

            if (guest.getClass() == Guest.class) {
                if (room != ChatRoom.MainHall && room.getOwner() == guest) {
                    RoomHelper.removeOutAllGuest(guest, room);
                }
            }
        }
        removeGuest(guest);

    }
}

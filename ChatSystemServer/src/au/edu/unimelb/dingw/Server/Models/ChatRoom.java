package au.edu.unimelb.dingw.Server.Models;


import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dingwang on 15/9/12.
 */
public class ChatRoom {
    public String roomID;
    public Guest owner;
    private Set<Guest> guests = new HashSet<>();


    public static ChatRoom MainHall = new ChatRoom("MainHall");

    public static Set<ChatRoom> allRooms = new HashSet<>();

    public HashMap<Guest, Pair> kickedGuest = new HashMap();

    public ChatRoom() {
    }

    public ChatRoom(String roomName){
        this.roomID = roomName;
//        allRooms.add(this);
    }
    public ChatRoom(String roonName,Guest owner){
        this.roomID = roonName;
        this.owner = owner;
    }


    public synchronized void addGuest (Guest guest){
        guests.add(guest);
    }

    public synchronized void removeGuest (Guest guestID){
        guests.remove(guestID);
    }

    public String getRoomID (){
        return  this.roomID;
    }

    public Guest getOwner(){
        return this.owner;
    }

    public Set<Guest> getGuest(){
        return guests;
    }

    public JSONObject getroomInfo (){
        JSONObject roomInfo = new JSONObject();
        roomInfo.put("roomid",this.roomID);
        roomInfo.put("count", guests.size());
        return roomInfo;
    }

    public Boolean joinable(Guest guest){
        if(kickedGuest.containsKey(guest)) {
            Pair<Date, Long> kickOutInfo = kickedGuest.get(guest);

            Date kickOutTime = kickOutInfo.getKey();
            Long timeInterval = kickOutInfo.getValue();
            Date now = new Date();
            if ((now.getTime() - kickOutTime.getTime()) >  timeInterval * 1000) {
                kickedGuest.remove(guest);
                return true;
            }else
                return false;
        }
        return true;
    }

    public static Set<ChatRoom> getAllRooms(){
        allRooms.add(MainHall);
//        Set<ChatRoom> rooms = new HashSet<>(allRooms);
        return allRooms;
    }

    public void broadcast(String message)  {


        for(Guest g: this.guests){

            g.sendMessage(message);
        }

    }

    public static ChatRoom findRoom(String roomId){
        ChatRoom room = null;
        for(ChatRoom r : ChatRoom.getAllRooms()){
            if(r.getRoomID().equals(roomId)){
                room = r;
                break;
            }
        }
        return room;
    }


}

package au.edu.unimelb.dingw.Server.Tools;

import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by dingwang on 15/9/14.
 */
public class JSONcreater {
    public static String createIndentityChange(String former, String newIdentity){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "newidentity");
        jsonObject.put("former", former);
        jsonObject.put("identity", newIdentity);
        return jsonObject.toString();
    }

    public static String createRoomChange(ChatRoom former, ChatRoom newRoom, Guest guest){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "roomchange");
        jsonObject.put("identity", guest.getID());
        if(former == null){
            jsonObject.put("former", "");
        }else {
            jsonObject.put("former", former.getRoomID());
        }
        if(newRoom ==null) {
            jsonObject.put("roomid", "" );
        }else{
            jsonObject.put("roomid", newRoom.getRoomID());
        }
        return jsonObject.toString();

    }

    public static String roomContents(ChatRoom room, Guest guest){
        JSONObject jsonObject = new JSONObject();
        Set<Guest> copyofList = room.getGuest();

        ArrayList<String> arrayList = new ArrayList<>();
        for(Guest g : copyofList){
            arrayList.add(g.getID());
        }

        jsonObject.put("type", "roomcontents");
        jsonObject.put("roomid", room.getRoomID());
        if(copyofList.size() != 0) {
            jsonObject.put("identities", arrayList);
        }else{
            jsonObject.put("identities", "");
        }
        if(room == ChatRoom.MainHall) {
            jsonObject.put("owner", "");
        }else {
            jsonObject.put("owner", room.getOwner().getID());
        }
        return jsonObject.toString();

    }

    public static String roomList(){
        JSONObject jsonObject = new JSONObject();
        JSONArray roomInfo = new JSONArray();
        for(ChatRoom r : ChatRoom.getAllRooms()){
            roomInfo.add(r.getroomInfo());
        }
        jsonObject.put("type", "roomlist");
        jsonObject.put("rooms", roomInfo);
        return jsonObject.toString();
    }

    public static String createMessage(Guest guest, String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "message");
        jsonObject.put("content", message);
        jsonObject.put("identity", guest.getID());
        return jsonObject.toString();
    }
    public static String error(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "error");
        jsonObject.put("message", message);
        return jsonObject.toString();
    }

    public static String systemMessage(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "systemmessage");
        jsonObject.put("message", message);
        return jsonObject.toString();
    }
}

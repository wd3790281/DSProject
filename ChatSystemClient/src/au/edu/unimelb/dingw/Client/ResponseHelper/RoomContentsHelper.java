package au.edu.unimelb.dingw.Client.ResponseHelper;

//import org.json.simple.JSONArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by dingwang on 15/9/15.
 */
public class RoomContentsHelper {

    public static void helpRoomContents(String response) throws ParseException {

        JSONObject jsonObject = (JSONObject) JSONValue.parse(response);

        String owner = (String) jsonObject.get("owner");
        String roomId = (String) jsonObject.get("roomid");
        System.out.println();
        System.out.print(roomId + " contains");
        JSONArray guestList = (JSONArray) jsonObject.get("identities");
        if(guestList.size() == 0){
            System.out.print("");
        }else {
            for (Object o : guestList) {
                String identity = (String) o;
                if (!owner .equals("") && identity.equals(owner)) {
                    identity = identity + "*";
                }
                System.out.print(" " + identity);
            }
        }
        System.out.print('\n');
    }
}

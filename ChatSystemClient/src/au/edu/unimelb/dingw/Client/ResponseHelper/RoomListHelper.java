package au.edu.unimelb.dingw.Client.ResponseHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by dingwang on 15/9/15.
 */
public class RoomListHelper {

    public static void helpList(String response) throws ParseException {
        JSONObject jsonObject = (JSONObject) JSONValue.parse(response);

        JSONArray rooms =(JSONArray) jsonObject.get("rooms");
        System.out.println();
        for (Object o : rooms) {
            JSONObject roomInfo = (JSONObject) o;
            String roomId = (String) roomInfo.get("roomid");
            Long count = (Long) roomInfo.get("count");
            System.out.println(roomId + ": " + count + " guests");
        }
    }
}

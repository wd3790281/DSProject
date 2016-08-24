package au.edu.unimelb.dingw.Client.ResponseHelper;

import au.edu.unimelb.dingw.Client.Client;
import au.edu.unimelb.dingw.Client.ClientInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by dingwang on 15/9/15.
 */
public class RoomChangeHelper {

    public static void helpChange(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        String identity = (String) jsonObject.get("identity");
        String former = (String) jsonObject.get("former");
        String roomid = (String) jsonObject.get("roomid");

        if(!ClientInfo.clientID.equals(identity) && ClientInfo.currentRoomID.equals(former)){
            System.out.println(identity + " leaves " + former);
            Client.prompt();
            return;
        }

        if(roomid == null || roomid.equals("")) {

            System.out.println(identity + " leaves " + former);
            Client.prompt();
            return;
        }

        if (roomid.equals(former)) {

            System.out.println();
            System.out.println("The request room is invalid or non existent.");
            Client.prompt();
            return;
        }

        if (former.equals("")) {
            System.out.println();
            System.out.println(identity + " moves to " + roomid);
        } else {
            System.out.println();
            System.out.println(identity + " moved from " + former + " to " + roomid);
        }

        if (ClientInfo.clientID.equals(identity)) {
            ClientInfo.currentRoomID = roomid;
        }
        Client.prompt();
    }
}

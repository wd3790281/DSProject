package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class WhoHelper {
    public static JSONObject helpWho (String command) {
        String[] split = command.split(" ");
        if (split.length != 2) {
            System.out.println("Invalid input");
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "who");
        jsonObject.put("roomid", split[1]);
        return jsonObject;
    }
}

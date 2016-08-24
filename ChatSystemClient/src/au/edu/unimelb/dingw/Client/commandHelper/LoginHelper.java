package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/10/16.
 */
public class LoginHelper {
    public static JSONObject helper(String string) {
        String[] split = string.split(" ");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "userlogin");
        jsonObject.put("Identity", split[1]);
        jsonObject.put("password", split[2]);
        return jsonObject;
    }
}

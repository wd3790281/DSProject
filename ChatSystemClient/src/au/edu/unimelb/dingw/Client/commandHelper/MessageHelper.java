package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class MessageHelper {
    public static JSONObject hlpeMessage(String command){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "message");
        jsonObject.put("content", command);
        return jsonObject;
    }
}

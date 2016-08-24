package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class ListRoomHelper {
    public static JSONObject helpList(String command){
        String[] split = command.split(" ");
        if(split.length != 1){
            System.out.println("Invalid input");
            return  null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "list");
        return jsonObject;
    }
}

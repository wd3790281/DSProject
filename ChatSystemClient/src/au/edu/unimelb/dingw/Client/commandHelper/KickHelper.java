package au.edu.unimelb.dingw.Client.commandHelper;

import au.edu.unimelb.dingw.Client.ClientInfo;
import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class KickHelper {
    public static JSONObject helpkick(String command){
        String[] split = command.split(" ");
        if(split.length != 4){
            System.out.println("Invalid input");
            return  null;
        }
        Integer time = new Integer(split[2]);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "kick");
        jsonObject.put("roomid", split[1]);
        jsonObject.put("time", time);
        jsonObject.put("identity", split[3]);
        return jsonObject;
    }
}

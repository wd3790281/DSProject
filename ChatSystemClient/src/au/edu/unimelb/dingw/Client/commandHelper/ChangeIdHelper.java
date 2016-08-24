package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class ChangeIdHelper {

    public static JSONObject helpChangeId(String string){
        String[] split = string.split(" ");
        JSONObject jsonObject = new JSONObject();
        if(split.length == 2){
            jsonObject.put("type", "identitychange");
            jsonObject.put("identity", split[1]);
        }else if(split.length ==4){
            jsonObject.put("type", "identitychange");
            jsonObject.put("identity", split[1]);
            jsonObject.put("upgrade", "upgrade");
            jsonObject.put("password", split[3]);
        }else {

            System.out.println("Invalid input");
            return null;
        }
        return  jsonObject;
    }
}

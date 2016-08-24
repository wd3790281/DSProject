package au.edu.unimelb.dingw.Client.commandHelper;

import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/10/15.
 */
public class ChangePasswordHelper {

    public static JSONObject helpChange(String string){
        String[] split = string.split(" ");
        if (split.length != 2) {
            System.out.println("Invalid input");
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","changepassword");
        jsonObject.put("password", split[1]);
        return jsonObject;
    }
}

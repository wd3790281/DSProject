package au.edu.unimelb.dingw.Client.ResponseHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by dingwang on 15/9/15.
 */
public class MessageHelper {
    public static void helpMessage(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        String identity = (String)jsonObject.get("identity");
        String message = (String)jsonObject.get("content");
        System.out.println();
        System.out.println(identity + ": " + message);

    }
}

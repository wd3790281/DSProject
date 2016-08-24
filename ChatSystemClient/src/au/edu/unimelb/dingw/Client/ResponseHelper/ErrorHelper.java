package au.edu.unimelb.dingw.Client.ResponseHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by dingwang on 15/9/15.
 */
public class ErrorHelper {
    public static void helpError(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        String message = (String)jsonObject.get("message");
//        System.out.println();
        System.out.println(message);

    }
}


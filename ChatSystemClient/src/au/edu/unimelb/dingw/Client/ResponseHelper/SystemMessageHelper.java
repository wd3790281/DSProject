package au.edu.unimelb.dingw.Client.ResponseHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by dingwang on 15/10/18.
 */
public class SystemMessageHelper {

    public static void help(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        String message = (String)jsonObject.get("message");
//        System.out.println();
        System.out.println(message);

    }
}

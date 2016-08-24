package au.edu.unimelb.dingw.Client.ResponseHelper;


import au.edu.unimelb.dingw.Client.ClientInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by dingwang on 15/9/15.
 */
public class NewIdentityHelper {

    public static void helpID(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        String former = (String) jsonObject.get("former");
        String newId = (String) jsonObject.get("identity");

        if(ClientInfo.clientID == null){
            ClientInfo.clientID = newId;
            return;
        }
        if (ClientInfo.clientID.equals(former)) {
            ClientInfo.clientID = newId;
        }
        if (former.equals(newId)) {
            System.out.println();
            System.out.println("Requested identity invalid or in use");
        }else {
            System.out.println();
            System.out.println(former + " is now " + newId);
        }

    }
}

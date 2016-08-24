package au.edu.unimelb.dingw.Server.Functions;

import au.edu.unimelb.dingw.Server.ModelHelper.AuthenticationHelper;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.ModelHelper.GuestHelper;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by dingwang on 15/9/30.
 */
public class ChangIdentity {

    public static void handler(JSONObject command, Guest newguest) throws IOException {
        String password = (String) command.get("password");

        String upgrade = (String) command.get("upgrade");
        String response;
        String newID = (String) command.get("identity");
        String former = newguest.getID();



        Boolean changed = GuestHelper.identityChange(newguest, newID);
        if(changed){
            response = JSONcreater.createIndentityChange(former, newID);
            newguest.getCurrentRoom().broadcast(response);

            if(upgrade != null && upgrade.equals("upgrade")){
                AuthenticationHelper.addAuthenticatedGuest(newID, password);

            }else {
                Boolean authenticatedGuest = AuthenticationHelper.checkAutentication(former);
                if (authenticatedGuest) {
                    AuthenticationHelper.updateAuthenticatedUserID(former, newID);
                }
            }
        }
        else{
            response = JSONcreater.createIndentityChange(former, former);
            newguest.sendMessage(response);
        }
    }
}

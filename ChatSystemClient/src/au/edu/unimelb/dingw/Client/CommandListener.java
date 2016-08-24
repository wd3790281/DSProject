package au.edu.unimelb.dingw.Client;

import au.edu.unimelb.dingw.Client.commandHelper.*;
import org.json.simple.JSONObject;

/**
 * Created by dingwang on 15/9/15.
 */
public class CommandListener {

    public static JSONObject listenCommand(String command){
        JSONObject jsonObject = new JSONObject();

        if(command.startsWith("#identitychange")){

            jsonObject = ChangeIdHelper.helpChangeId(command);

        }else if(command.startsWith("#join")){

            jsonObject = JoinHelper.helpJoin(command);

        }else if(command.startsWith("#who")){

            jsonObject = WhoHelper.helpWho(command);

        }else if(command.startsWith("#list")){

            jsonObject = ListRoomHelper.helpList(command);

        }else if(command.startsWith("#createroom")){

            jsonObject = CreateRoomHelper.helpCreate(command);

        } else if(command.startsWith("#delete")){

            jsonObject = DeleteHelper.helpDelete(command);

        }else if(command.startsWith("#kick")){

            jsonObject = KickHelper.helpkick(command);

        }else if(command.startsWith("#changepassword")){

            jsonObject = ChangePasswordHelper.helpChange(command);

        }else if (command.startsWith("#guest")){

            if(ClientInfo.clientID == null) {
                jsonObject.put("type", "guest");
            } else {
                System.out.println("You have got an idenity");
                jsonObject = null;
            }

        }else if (command.startsWith("#userlogin")) {

            if(ClientInfo.clientID == null) {
                jsonObject = LoginHelper.helper(command);
            } else {
                System.out.println("You have got an identiy");
                jsonObject = null;
            }

        }else{

            jsonObject = MessageHelper.hlpeMessage(command);

        }

        return jsonObject;
    }
}

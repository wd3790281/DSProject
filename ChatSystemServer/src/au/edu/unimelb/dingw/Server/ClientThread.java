package au.edu.unimelb.dingw.Server;

import au.edu.unimelb.dingw.Server.ModelHelper.AuthenticationHelper;
import au.edu.unimelb.dingw.Server.ModelHelper.GuestHelper;
import au.edu.unimelb.dingw.Server.ModelHelper.RoomHelper;
import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.JSONcreater;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.SSLSocket;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;


/**
 * Created by dingwang on 15/9/11.
 */
public class ClientThread implements Runnable {


    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Guest guest;

    public ClientThread(SSLSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

            try {

                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                sendToClient(JSONcreater.systemMessage("Please select your identity:"));
                String uponConnected;
                while ((uponConnected = in.readLine()) != null) {
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(uponConnected);
                    Boolean goOn = uponConnection(jsonObject);
                    if (goOn)
                        break;
                    else
                        continue;
                }

                String readIn;
                while ((readIn = in.readLine()) != null && this.socket.isConnected() &&(!this.socket.isClosed()) ) {

                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(readIn);
                    String type = (String) jsonObject.get("type");
                    if (type.equals("quit")) {

                        break;
                    }

                    MessageManager messageManager = new MessageManager();
                    messageManager.manageMessage(this
                            .guest, readIn);

                }
                GuestHelper.quit(this.guest);
                in.close();
                out.close();
                socket.close();

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(guest.getID() + " disconnected.");
            }

    }

    public Boolean uponConnection(JSONObject jsonObject) throws IOException {
        Boolean goOn = false;
        String type = (String) jsonObject.get("type");
        if(type.equals("guest")){
            this.guest = GuestHelper.createGuest();
            initialize(guest);
            goOn = true;
        }else if (type.equals("userlogin")){
            String ID = (String) jsonObject.get("Identity");
            String password = (String) jsonObject.get("password");
            Guest checkLoggedIn = GuestHelper.findGuest(ID);
            if(checkLoggedIn == null) {
                if (AuthenticationHelper.checkAutentication(ID, password)) {
                    this.guest = AuthenticationHelper.login(ID, this.out);
                    initialize(guest);
                    goOn = true;
                } else {
                    String error = JSONcreater.systemMessage("Wrong userID or password");
                    sendToClient(error);
                }
            }else{
                sendToClient(JSONcreater.error("User has logged in"));
            }
        }else{

            String error = JSONcreater.systemMessage("Wrong command");
            sendToClient(error);

        }
        return goOn;
    }


    public void initialize(Guest guest) throws IOException {

        RoomHelper.joinRoom(guest, ChatRoom.MainHall, null);

        guest.saveSocketOut(out);

        sendToClient(JSONcreater.createIndentityChange("", guest.getID()));
        sendToClient(JSONcreater.createRoomChange(null, ChatRoom.MainHall, guest));
        sendToClient(JSONcreater.roomContents(ChatRoom.MainHall, guest));
        sendToClient(JSONcreater.roomList());

    }

    private void sendToClient(String message) throws IOException {
        this.out.println(message);
        this.out.flush();
    }

}


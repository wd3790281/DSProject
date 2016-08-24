package au.edu.unimelb.dingw.Server.Models;


import au.edu.unimelb.dingw.Server.Models.ChatRoom;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by dingwang on 15/9/13.
 */
public class Guest {
    private String clientID;

    private ChatRoom currentRoom;

    private PrintWriter out;

    public Guest(String id){
        this.clientID =  id;
    }

    public Guest(String id, ChatRoom room, PrintWriter out){
        this.clientID = id;
        this.currentRoom = room;
        this.out = out;
    }

    public void changeID (String newID) {
        this.clientID = newID;
    }

    public String getID() {
        return this.clientID;
    }

    public void setCurrentRoom(ChatRoom newRoom){
        this.currentRoom = newRoom;
    }

    public ChatRoom getCurrentRoom(){
        return this.currentRoom;
    }

    public void saveSocketOut (PrintWriter out){
        this.out = out;
    }

    public void sendMessage(String message)  {
        this.out.println(message);
        this.out.flush();
    }


}

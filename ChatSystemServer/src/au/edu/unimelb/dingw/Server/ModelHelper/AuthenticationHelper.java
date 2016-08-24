package au.edu.unimelb.dingw.Server.ModelHelper;


import au.edu.unimelb.dingw.Server.Models.ChatRoom;
import au.edu.unimelb.dingw.Server.Models.Guest;
import au.edu.unimelb.dingw.Server.Tools.ObjectCrypter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.HashMap;

/**
 * Created by dingwang on 15/10/16.
 */
public class AuthenticationHelper {
    private static HashMap<String,byte[]> authenticatedGuest = new HashMap<>();
    private static ObjectCrypter crypter = new ObjectCrypter("regesiter".getBytes(),"IVVector".getBytes());

    public static synchronized void addAuthenticatedGuest (String ID, String password){
        try {

            byte[] encrypted = crypter.encrypt(password);
            authenticatedGuest.put(ID,encrypted);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (ShortBufferException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void updateAuthenticatedUserID(String formerID, String newID) {

        byte[] password = authenticatedGuest.get(formerID);
        authenticatedGuest.remove(formerID);
        authenticatedGuest.put(newID, password);

    }

    public static Boolean changePassword(String ID, String password){
        if(checkAutentication(ID)) {
            try {
                byte[] old = authenticatedGuest.get(ID);
                byte[] encryptedPassword = crypter.encrypt(password);
                authenticatedGuest.replace(ID, old, encryptedPassword);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (ShortBufferException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Boolean checkAutentication(String ID){

        if(authenticatedGuest.containsKey(ID))
            return true;
        else
            return false;
    }
    public static Boolean checkAutentication(String ID, String password){
        Boolean correct = false;

        if(authenticatedGuest.containsKey(ID)){
            byte[] savedPassword  = authenticatedGuest.get(ID);
            String decryptedPassword = null;
            try {
                decryptedPassword = (String) crypter.decrypt(savedPassword);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(password.equals(decryptedPassword))
                correct = true;
        }
        return correct;
    }

    public static synchronized Guest login(String ID, PrintWriter out){
        Guest user = new Guest(ID, ChatRoom.MainHall, out);
        GuestHelper.onlineGuestList.add(user);
        return user;
    }

}

package iesdomingoperezminik.es.proyectofinal.handlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SessionHandler {

    private static String sessionEmail = null;
    private static boolean logged = false;

    public static boolean isLogged() {
        return logged;
    }

    public static void setLogged(boolean logged) {
        SessionHandler.logged = logged;
    }

    public static String MD_5(String text) {
        StringBuilder sb = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());

            byte[] bytes = md.digest();
            sb = new StringBuilder();

            for (byte b : bytes)
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return sb != null ? sb.toString() : null;
    }

    public static String getSessionEmail() {
        return sessionEmail;
    }

    public static void setSessionEmail(String sessionEmail) {
        SessionHandler.sessionEmail = sessionEmail;
    }
}


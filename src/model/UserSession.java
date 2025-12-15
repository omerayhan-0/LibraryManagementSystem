package model;

public class UserSession {
    private static int userId;
    private static String username;
    private static String email;
    private static String tc;

    public static void setUser(int id, String uname, String mail, String tcKimlik) {
        userId = id;
        username = uname;
        email = mail;
        tc = tcKimlik;
    }

    public static int getUserId() { return userId; }
    public static String getUsername() { return username; }
    public static String getEmail() { return email; }
    public static String getTc() { return tc; }
    public static void clear() {
        username = null;
        email = null;
        tc = null;

    }
}

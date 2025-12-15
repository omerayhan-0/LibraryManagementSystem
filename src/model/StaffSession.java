package model;

public class StaffSession {

    private static int staffId;
    private static String fullName;
    private static String role;

    public static void setStaff(int id, String name, String userRole) {
        staffId = id;
        fullName = name;
        role = userRole;
    }

    public static int getStaffId() {
        return staffId;
    }

    public static String getFullName() {
        return fullName;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return staffId > 0;
    }

    public static void clear() {
        staffId = 0;
        fullName = null;
        role = null;
    }
}


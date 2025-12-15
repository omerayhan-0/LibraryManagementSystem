package model;

public class MemberSession {
    public static int memberId;

    public static void setMemberId(int id) {
        memberId = id;
    }

    public static int getMemberId() {
        return memberId;
    }

    public static void clear() {
        memberId = 0;
    }
}

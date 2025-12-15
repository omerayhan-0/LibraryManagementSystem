package model;

public class Member extends AbstractUser {

    private String tc;

    public Member(int id, String username, String email, String tc) {
        this.id = id;
        this.name = username;
        this.email = email;
        this.tc = tc;
    }

    public String getTc() {
        return tc;
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    @Override
    public void showDashboard() {
        System.out.println("Member dashboard açıldı");
        // JavaFX ekran yönlendirmesi burada yapılır
    }
}

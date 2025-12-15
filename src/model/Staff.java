package model;

public class Staff extends AbstractUser {

    private String role;

    public Staff(int id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public void showDashboard() {
        System.out.println("Staff dashboard açıldı");
    }
}

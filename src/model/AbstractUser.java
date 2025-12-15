package model;

public abstract class AbstractUser {
    protected int id;
    protected String name;
    protected String email;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    // ✅ Rol bilgisini her kullanıcı döndürsün
    public abstract String getRole();
    public abstract void showDashboard();
}

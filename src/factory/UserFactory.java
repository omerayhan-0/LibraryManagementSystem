package factory;

import model.AbstractUser;
import model.Member;
import model.Staff;

public class UserFactory {

    public static AbstractUser createUser(
            String userType,
            int id,
            String name,
            String email,
            String tc,
            String role
    ) {

        if (userType.equalsIgnoreCase("MEMBER")) {
            return new Member(id, name, email, tc);
        }

        if (userType.equalsIgnoreCase("STAFF")) {
            return new Staff(id, name, email, role);
        }

        throw new IllegalArgumentException("Geçersiz kullanıcı tipi");
    }
}

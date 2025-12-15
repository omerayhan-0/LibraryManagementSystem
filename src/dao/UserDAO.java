package dao;

import model.DatabaseConnection;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<Member> getAllMembers() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(new Member(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("tc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Member> searchMembers(String keyword) {
        List<Member> list = new ArrayList<>();
        String sql = """
                SELECT * FROM users
                WHERE username LIKE ?
                   OR email LIKE ?
                   OR tc LIKE ?
                """;

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Member(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("tc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.User;

public class UserService implements Service<User> {

    Connection connection;

    public UserService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(User user) {
        try {

            String userId = user.getUserId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String phone = user.getPhone();
            String email = user.getEmail();
            String password = user.getPassword();
            String userStatusId = user.getUserStatusId();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_insert_user(?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, userId);
                oCSF.setString(2, firstName);
                oCSF.setString(3, lastName);
                oCSF.setString(4, phone);
                oCSF.setString(5, email);
                oCSF.setString(6, password);
                oCSF.setString(7, userStatusId);
                
                oCSF.execute();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
            return false;
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Statement usersSt = connection.createStatement();
            usersSt.executeQuery("Delete from users where user_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    @Override
    public ArrayList<User> getAll() {

        ArrayList<User> users = new ArrayList<>();

        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from Users");

            while (usersRs.next()) {
                User user = new User(
                        usersRs.getString(1),
                        usersRs.getString(2),
                        usersRs.getString(3),
                        usersRs.getString(4),
                        usersRs.getString(5),
                        usersRs.getString(6),
                        usersRs.getString(7)
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return users;
    }

    @Override
    public User getById(String id) {
        User user = null;

        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from Users where user_id = " + id);

            usersRs.next();
            user = new User(
                    usersRs.getString(1),
                    usersRs.getString(2),
                    usersRs.getString(3),
                    usersRs.getString(4),
                    usersRs.getString(5),
                    usersRs.getString(6),
                    usersRs.getString(7)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return user;
    }

    public User getByEmail(String email) {
        User user = null;

        try {

            PreparedStatement pstmt = connection.prepareStatement("select * from users "
                    + "where email = ?");
            pstmt.setString(1, email);

            ResultSet usersRs = pstmt.executeQuery();

            usersRs.next();
            user = new User(
                    usersRs.getString(1),
                    usersRs.getString(2),
                    usersRs.getString(3),
                    usersRs.getString(4),
                    usersRs.getString(5),
                    usersRs.getString(6),
                    usersRs.getString(7)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return user;
    }

    @Override
    public void update(User user) {
        try {
            String userId = user.getUserId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String phone = user.getPhone();
            String email = user.getEmail();
            String password = user.getPassword();
            String userStatusId = user.getUserStatusId();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_update_user(?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, userId);
                oCSF.setString(2, firstName);
                oCSF.setString(3, lastName);
                oCSF.setString(4, phone);
                oCSF.setString(5, email);
                oCSF.setString(6, password);
                oCSF.setString(7, userStatusId);
                oCSF.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    public boolean userExist(String id) {
        boolean ue = false;
        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from Users where user_id = " + id);

            if (usersRs.next()) {
                return ue = true;
            } else {
                return ue = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");

        }
        return ue;
    }

}

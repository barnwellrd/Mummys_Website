package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.UserStatus;

public class UserStatusService implements Service<UserStatus> {

    Connection connection;

    public UserStatusService() {
        super();
    }

    public UserStatusService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(UserStatus userStatus) {
        try {
            String userStatusId = userStatus.getUserStatusId();
            String userStatusName = userStatus.getUserStatus();

            try (CallableStatement oCSF = connection.prepareCall("{?=call sp_insert_user_status(?,?)}")) {
                oCSF.setString(2, userStatusId);
                oCSF.setString(3, userStatusName);
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
            usersSt.executeQuery("Delete from user_statuses where user_status_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    @Override
    public UserStatus getById(String id) {
        UserStatus userStatus = null;

        try {
            Statement usersSt = connection.createStatement();
            ResultSet usersRs = usersSt.executeQuery("Select * from user_statuses where user_status_id = " + id);

            usersRs.next();
            userStatus = new UserStatus(
                    usersRs.getString(1),
                    usersRs.getString(2)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return userStatus;
    }

    @Override
    public ArrayList<UserStatus> getAll() {
        ArrayList<UserStatus> userStatuses = new ArrayList<>();

        try {
            Statement userStatusesSt = connection.createStatement();
            ResultSet userStatusesRs = userStatusesSt.executeQuery("Select * from USER_STATUSES");

            while (userStatusesRs.next()) {
                UserStatus userStatus = new UserStatus(
                        userStatusesRs.getString(1),
                        userStatusesRs.getString(2)
                );
                userStatuses.add(userStatus);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return userStatuses;
    }

    @Override
    public void update(UserStatus userStatus) {
        try {
            String userStatusId = userStatus.getUserStatusId();
            String userStatusName = userStatus.getUserStatus();

            try (CallableStatement oCSF = connection.prepareCall("{?=call sp_update_user_status(?,?)}")) {
                oCSF.setString(2, userStatusId);
                oCSF.setString(3, userStatusName);
                oCSF.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

}

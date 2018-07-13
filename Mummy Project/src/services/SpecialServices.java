package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domain.Special;
import java.sql.Statement;

public class SpecialServices implements Service<Special> {

    Connection con;

    public SpecialServices(Connection con) {
        super();
        this.con = con;
    }

    @Override
    public boolean add(Special spec) {
        CallableStatement oracleCallStmt;
        try {
            oracleCallStmt = con.prepareCall("{call sp_insert_special(?,?)}");
            oracleCallStmt.setString(1, spec.getItem_ID());
            oracleCallStmt.setInt(2, spec.getDiscoutPercentage());
            oracleCallStmt.executeUpdate();
            System.out.println("Add Successful");
            oracleCallStmt.close();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Item does not exist! Cannot create special discount!");
        }
        return false;
    }

    @Override
    public void deleteById(String id) {
        try {
            try (CallableStatement oCSF = con.prepareCall("{call sp_delete_special(?)}")) {
                oCSF.setString(1, id);
                oCSF.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    @Override
    public Special getById(String id) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM specials WHERE item_id = " + id);
            rs.next();

            Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentatge"));
            return spec;

        } catch (SQLException e) {
            //e.printStackTrace();
            System.err.println("Error executing query!");
        }

        return null;
    }

    @Override
    public ArrayList<Special> getAll() {
        ArrayList<Special> specials = new ArrayList<>();

        try {
            Statement locationsSt = con.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from specials");

            while (locationsRs.next()) {
                Special special = new Special(
                        locationsRs.getString(1),
                        locationsRs.getInt(2)
                );
                specials.add(special);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return specials;

    }

    @Override
    public void update(Special spec) {
        try {
            String specId = spec.getItem_ID();
            int discPercent = spec.getDiscoutPercentage();
            try (CallableStatement oCSF = con.prepareCall("{call sp_update_special(?,?)}")) {
                oCSF.setString(1, specId);
                oCSF.setInt(2, discPercent);
                
                oCSF.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");

        }

    }

    public boolean specialIdExists(String id) {
        boolean ls = false;
        try {
            Statement locationsSt = con.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Specials where item_id = " + id);
            if (locationsRs.next()) {
                return ls = false;
            } else {
                return ls = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return !ls;
    }

    public boolean itemIdExists(String id) {
        boolean ls = false;
        try {
            Statement locationsSt = con.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Items where item_id = " + id);
            if (locationsRs.next()) {
                return ls = false;
            } else {
                return ls = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return !ls;
    }

}

package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import domain.Location;
import java.sql.Types;

public class LocationService implements Service<Location> {

    Connection connection;

    public LocationService() {
        super();
    }

    public LocationService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(Location location) {
        try {
            //get location id
            //get order id
            CallableStatement getLocationId = connection.prepareCall(
                    "{?=call AssignLocationId}");
            getLocationId.registerOutParameter(1, Types.VARCHAR);
            getLocationId.execute();
            String locationId = getLocationId.getString(1);
            location.setLocationId(locationId);

            //String locationId = location.getLocationId();
            String userId = location.getUserId();
            String taxRate = location.getTaxRate();
            String street = location.getStreet();
            String city = location.getCity();
            String state = location.getState();
            String country = location.getCountry();
            String zip = location.getZip();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_insert_location(?,?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, locationId);
                oCSF.setString(2, userId);
                oCSF.setDouble(3, Double.parseDouble(taxRate));
                oCSF.setString(4, street);
                oCSF.setString(5, city);
                oCSF.setString(6, state);
                oCSF.setString(7, country);
                oCSF.setString(8, zip);
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
            connection.createStatement().executeQuery("DELETE FROM locations WHERE location_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    public boolean delById(String id) {
        try {
            try (CallableStatement oCSF = connection.prepareCall("{call sp_delete_location(?)}")) {
                oCSF.setString(1, id);
                oCSF.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
            return false;
        }
    }

    @Override
    public ArrayList<Location> getAll() {

        ArrayList<Location> locations = new ArrayList<>();

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations");

            while (locationsRs.next()) {
                Location location = new Location(
                        locationsRs.getString(1),
                        locationsRs.getString(2),
                        locationsRs.getString(3),
                        locationsRs.getString(4),
                        locationsRs.getString(5),
                        locationsRs.getString(6),
                        locationsRs.getString(7),
                        locationsRs.getString(8)
                );
                locations.add(location);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return locations;
    }

    @Override
    public Location getById(String id) {
        Location location = null;

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where location_id = " + id);

            locationsRs.next();
            location = new Location(
                    locationsRs.getString(1),
                    locationsRs.getString(2),
                    locationsRs.getString(3),
                    locationsRs.getString(4),
                    locationsRs.getString(5),
                    locationsRs.getString(6),
                    locationsRs.getString(7),
                    locationsRs.getString(8)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return location;
    }

    @Override
    public void update(Location location) {
        try {
            String locationId = location.getLocationId();
            String userId = location.getUserId();
            String taxRate = location.getTaxRate();
            String street = location.getStreet();
            String city = location.getCity();
            String state = location.getState();
            String country = location.getCountry();
            String zip = location.getZip();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_update_location(?,?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, locationId);
                oCSF.setString(2, userId);
                oCSF.setString(3, taxRate);
                oCSF.setString(4, street);
                oCSF.setString(5, city);
                oCSF.setString(6, state);
                oCSF.setString(7, country);
                oCSF.setString(8, zip);
                oCSF.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    public boolean updateLoc(Location location) {
        try {
            String locationId = location.getLocationId();
            String userId = location.getUserId();
            String taxRate = location.getTaxRate();
            String street = location.getStreet();
            String city = location.getCity();
            String state = location.getState();
            String country = location.getCountry();
            String zip = location.getZip();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_update_location(?,?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, locationId);
                oCSF.setString(2, userId);
                oCSF.setString(3, taxRate);
                oCSF.setString(4, street);
                oCSF.setString(5, city);
                oCSF.setString(6, state);
                oCSF.setString(7, country);
                oCSF.setString(8, zip);
                oCSF.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
            return false;
        }
    }

    public ArrayList<Location> getUserLocations(String userId) {

        ArrayList<Location> locations = new ArrayList<>();

        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where user_id = '" + userId + "'");

            while (locationsRs.next()) {
                Location location = new Location(
                        locationsRs.getString(1),
                        locationsRs.getString(2),
                        locationsRs.getString(3),
                        locationsRs.getString(4),
                        locationsRs.getString(5),
                        locationsRs.getString(6),
                        locationsRs.getString(7),
                        locationsRs.getString(8)
                );
                locations.add(location);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return locations;
    }

    public boolean locationIdExist(String id) {
        boolean ls = false;
        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where location_id = " + id);
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

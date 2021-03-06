package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Store;

public class StoreService implements Service<Store> {

    Connection connection;

    public StoreService() {
        super();
    }

    public StoreService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(Store store) {
        try {
            String storeId = store.getStoreId();
            String locationId = store.getLocationId();
            String storeName = store.getStoreName();
            String phoneNumber = store.getPhoneNumber();
            String managerId = store.getManagerId();
            int openTime = store.getOpenTime();
            int closeTime = store.getCloseTime();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_insert_store(?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, storeId);
                oCSF.setString(2, locationId);
                oCSF.setString(3, storeName);
                oCSF.setString(4, phoneNumber);
                oCSF.setString(5, managerId);
                oCSF.setInt(6, openTime);
                oCSF.setInt(7, closeTime);
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
            try (CallableStatement oCSF = connection.prepareCall("{call sp_delete_store(?)}")) {
                oCSF.setString(1, id);
                oCSF.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    @Override
    public ArrayList<Store> getAll() {

        ArrayList<Store> stores = new ArrayList<>();

        try {
            Statement storesSt = connection.createStatement();
            ResultSet storesRs = storesSt.executeQuery("Select * from Stores");

            while (storesRs.next()) {
                Store store = new Store(
                        storesRs.getString(1),
                        storesRs.getString(2),
                        storesRs.getString(3),
                        storesRs.getString(4),
                        storesRs.getString(5),
                        storesRs.getInt(6),
                        storesRs.getInt(7)
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return stores;
    }

    @Override
    public Store getById(String id) {
        Store store = null;

        try {
            Statement storesSt = connection.createStatement();
            ResultSet storesRs = storesSt.executeQuery("Select * from Stores where store_id = " + id);

            storesRs.next();
            store = new Store(
                    storesRs.getString(1),
                    storesRs.getString(2),
                    storesRs.getString(3),
                    storesRs.getString(4),
                    storesRs.getString(5),
                    storesRs.getInt(6),
                    storesRs.getInt(7)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return store;
    }

    @Override
    public void update(Store store) {
        try {
            String storeId = store.getStoreId();
            String locationId = store.getLocationId();
            String storeName = store.getStoreName();
            String phoneNumber = store.getPhoneNumber();
            String managerId = store.getManagerId();
            int openTime = store.getOpenTime();
            int closeTime = store.getCloseTime();

            try (CallableStatement oCSF = connection.prepareCall("{call sp_update_store(?,?,?,?,?,?,?)}")) {
                oCSF.setString(1, storeId);
                oCSF.setString(2, locationId);
                oCSF.setString(3, storeName);
                oCSF.setString(4, phoneNumber);
                oCSF.setString(5, managerId);
                oCSF.setInt(6, openTime);
                oCSF.setInt(7, closeTime);
                oCSF.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    public boolean storeIdExists(String id) {
        boolean ls = false;
        try {
            Statement locationsSt = connection.createStatement();
            ResultSet locationsRs = locationsSt.executeQuery("Select * from Stores where store_id = " + id);
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

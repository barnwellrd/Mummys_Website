/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domain.ItemType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class ItemTypeService implements Service<ItemType> {

    Connection connection;

    public ItemTypeService() {
        super();
    }

    public ItemTypeService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(ItemType itemType) {
        try {
            PreparedStatement preStmt = connection.prepareStatement("insert into item_types values(?,?)");
            preStmt.setString(1, itemType.getItemTypeId());
            preStmt.setString(2, itemType.getItemType());
            preStmt.executeUpdate(); //Data is not yet committed
            System.out.println("Inserted");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void update(ItemType itemType) {
        String statement = "UPDATE ITEM_TYPES SET ITEM_TYPE = ?"
                + "WHERE ITEM_TYPE_ID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);

            preparedStatement.setString(1, itemType.getItemType());
            preparedStatement.setString(2, itemType.getItemTypeId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<ItemType> getAll() {

        ArrayList<ItemType> itemTypesArr = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ITEM_TYPES");

            while (rs.next()) {
                ItemType itemTypes = new ItemType(rs.getString(1), rs.getString(2));
                itemTypesArr.add(itemTypes);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return itemTypesArr;
    }

    @Override
    public ItemType getById(String id) {
        ItemType itemType = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM ITEM_TYPES WHERE ITEM_TYPE_ID = " + id);

            resultSet.next();
            itemType = new ItemType(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return itemType;
    }

    @Override
    public void deleteById(String id) {
        try {
            connection.createStatement().executeQuery("DELETE FROM item_types WHERE item_type_id = " + id);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public boolean itemTypeExist(String id) {
        boolean it = false;
        try {
            Statement itemTypeSt = connection.createStatement();
            ResultSet itemTypeRs = itemTypeSt.executeQuery("Select * from ITEM_TYPES where ITEM_TYPE_ID = " + id);

            it = itemTypeRs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");

        }
        return it;
    }
}

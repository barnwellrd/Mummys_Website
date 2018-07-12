package services;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
	public boolean add(Special spec){
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
			// TODO Auto-generated catch block
                        //e.printStackTrace();
                        System.err.println("Item does not exist! Cannot create special discount!");
		}
		return false;
	}
	
	@Override
	public void deleteById(String id){
		try {
			CallableStatement oCSF = con.prepareCall("{call sp_delete_special(?)}");
			oCSF.setString(1, id);
                        oCSF.execute();
                        oCSF.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}
	}
	
	@Override
	public Special getById(String id){
		try {
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM specials WHERE item_id = " + id);
			rs.next();
			
			Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentatge"));
			return spec;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        System.err.println("Error executing query!");
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Special> getAll(){
//                ArrayList<Special> specArr = new ArrayList<Special>();
//                CallableStatement oracleCallStmt;
//		try {
//			oracleCallStmt = con.prepareCall("{call sp_getAll_special()}");
//			int i=0;
//                        while(specArr.get(i+1)!=null){
//                            Special spec = new Special(oracleCallStmt.getString("item_id"), oracleCallStmt.getInt("discount_percentage"));
//                            specArr.add(spec);
//			
//                            oracleCallStmt.setString(1, spec.getItem_ID());
//                            oracleCallStmt.setInt(2, spec.getDiscoutPercentage());
//                        }
//			oracleCallStmt.execute();
//			con.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//                        System.err.println("Error executing query!");
//		}
//                return specArr;
                ArrayList<Special> specials = new ArrayList<Special>();
		
		try{
			Statement locationsSt = con.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from specials");
			
			while(locationsRs.next()){
				Special special = new Special(
						locationsRs.getString(1),
						locationsRs.getInt(2)
						); 
				specials.add(special);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}
		return specials;

	}
	
	@Override
	public void update(Special spec){
            try{
			String specId = spec.getItem_ID();
			int discPercent = spec.getDiscoutPercentage();
                        CallableStatement oCSF = con.prepareCall("{call sp_update_special(?,?)}");
			oCSF.setString(1, specId);
                        oCSF.setInt(2, discPercent);
         
                        oCSF.executeUpdate();
                        oCSF.close();
                     
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
                     
		}
		
	}
        
        public boolean specialIdExists(String id){
            Special special = null;
            boolean ls = false;
            try{
                Statement locationsSt = con.createStatement();
                ResultSet locationsRs = locationsSt.executeQuery("Select * from Specials where item_id = " + id);
                if(locationsRs.next()){
                    return ls = false;
                }else {
                    return ls = true;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.err.println("Error executing query!");
            }
            if(!ls){
                return true;
            }else
                return false;
         }
        
        public boolean itemIdExists(String id){
            Special special = null;
            boolean ls = false;
            try{
                Statement locationsSt = con.createStatement();
                ResultSet locationsRs = locationsSt.executeQuery("Select * from Items where item_id = " + id);
                if(locationsRs.next()){
                    return ls = false;
                }else {
                    return ls = true;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.err.println("Error executing query!");
            }
            if(!ls){
                return true;
            }else
                return false;
         }

}

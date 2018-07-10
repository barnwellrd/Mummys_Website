package services;

import static cli.Tiger.con;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Location;
import domain.Menu;
import java.sql.PreparedStatement;

public class LocationService implements Service<Location>{
	
	Connection connection;
	
	public LocationService() {
		super();
	}
	public LocationService(Connection connection) {
		super();
		this.connection = connection;
	}
	public boolean add(Location location){
		try{
			String locationId = location.getLocationId();
			String street = location.getStreet();
			String city = location.getCity();
			String state = location.getState();
			String country = location.getCountry();
			String zip = location.getZip();
			
			CallableStatement oCSF = connection.prepareCall("{?=call sp_insert_location(?,?,?,?,?)}");
			oCSF.setString(2, locationId);
			oCSF.setString(3, street);
			oCSF.setString(4, city);
			oCSF.setString(5, state);
			oCSF.setString(6, country);
			oCSF.setString(7, zip);
			oCSF.execute();
			oCSF.close();
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
        }
                	public boolean addl(Location location){		
		try{			
			//con.setAutoCommit(false);
			PreparedStatement preStmt = con.prepareStatement("insert into locations values(?,?,?,?,?,?,?,?)");
			preStmt.setString(1, location.getLocationId());
                        preStmt.setString(2, location.getUser_id());
                        preStmt.setDouble(3, location.getTax_rate());
			preStmt.setString(4, location.getStreet());
			preStmt.setString(5, location.getCity());
			preStmt.setString(6, location.getState());
			preStmt.setString(7, location.getCountry());
			preStmt.setString(8, location.getZip());
			preStmt.executeUpdate(); //Data is not yet committed
			System.out.println("Inserted");
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
                        
	public void deleteById(String id){
		try{
			Statement locationsSt = connection.createStatement();
			locationsSt.executeQuery("Delete from locations where location_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<Location> getAll(){

		ArrayList<Location> locations = new ArrayList<Location>();
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations");
			
			while(locationsRs.next()){
				Location location = new Location(
						locationsRs.getString("location_id"),
						locationsRs.getString("street"),
						locationsRs.getString("city"),
						locationsRs.getString("country"),
						locationsRs.getString("state"),
						locationsRs.getString("zip")
						); 
				locations.add(location);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return locations;
	}
	public Location getById(String id){
		Location location = null;
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where location_id = " + id);
			
			locationsRs.next();
			location = new Location(
					locationsRs.getString(1),
					locationsRs.getString(2),
					locationsRs.getString(3),
					locationsRs.getString(4),
					locationsRs.getString(5),
					locationsRs.getString(6)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return location;
	}
        
        
        
        public void update(Location location){
		try{
			
			PreparedStatement preStmt = con.prepareStatement("UPDATE locations SET street=?, city=?, state=?, country=?, zip=? WHERE location_id=?");
			preStmt.setString(1, location.getStreet());
			preStmt.setString(2, location.getCity());
			preStmt.setString(3, location.getState());
			preStmt.setString(4, location.getCountry());
			preStmt.setString(5, location.getZip());
                        preStmt.setString(6, location.getLocationId());
                        preStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
        
        
        
        
	
	public ArrayList<Location> getUserLocations(String userId){

		ArrayList<Location> locations = new ArrayList<Location>();
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where user_id = '" + userId + "'");
			
			while(locationsRs.next()){
				Location location = new Location(
						locationsRs.getString(1),
						locationsRs.getString(2),
						locationsRs.getString(3),
						locationsRs.getString(4),
						locationsRs.getString(5),
						locationsRs.getString(6)
						); 
				locations.add(location);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return locations;
	}
	
}

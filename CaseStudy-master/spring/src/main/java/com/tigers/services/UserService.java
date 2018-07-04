package com.tigers.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.tigers.models.User;


// should use @Service tag
@Component
public class UserService implements Service<User>{
	
	//Connection connection;
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public UserService() {
		super();
		//this.connection = connection;
	}
	/*public boolean add(User user){
		try{
			
			String userId = user.getUserId();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String phone = user.getPhone();
			String email = user.getEmail();
			String password = user.getPassword();
			String userStatusId = user.getUserStatusId();
			
			
			System.out.println("About to print jdbctemplate");
			System.out.println(jdbcTemplate.getDataSource());
			System.out.println(jdbcTemplate);
			System.out.println("Should have printed jdbctemplate");
			jdbcTemplate.update("call sp_insert_user(?,?,?,?,?,?,?)", userId, firstName, lastName, phone, email, password, userStatusId);
			
			return true;
		}catch(Exception e){
			System.out.println("error");
			System.out.println(e.getMessage());
			return false;
		}	
	}*/
	
	/*
	public void deleteById(String id){
		try{
			Statement usersSt = connection.createStatement();
			usersSt.executeQuery("Delete from users where user_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	private JdbcTemplate jdbcTemplate;
	
	public UserService(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	/*
	 * Add a new user object
	 */
	@Override
	public void add(User user){
		String userId = user.getUserId();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String phone = user.getPhone();
		String email = user.getEmail();
		String password = user.getPassword();
		String userStatusId = user.getUserStatusId();
		
		String query = "{CALL sp_insert_user(?,?,?,?,?,?,?)}";
		
		jdbcTemplate.update(query, userId, firstName, lastName,
							phone, email, password, userStatusId);
	}
	
	
	/*
	 * Delete an existing user by user id
	 */
	@Override
	public void delete(String id){
		String query = "DELETE FROM Users WHERE Users.user_id = ?";
		jdbcTemplate.update(query, id);

	}
	
	
	/*
	 * Return List of all existing users
	 */
	@Override
	public List<User> list(){
		String query = "SELECT * FROM Users";
		List<User> users = jdbcTemplate.query(query, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setFirstName(rs.getString("first"));
				user.setLastName(rs.getString("last"));
				user.setPassword(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setUserStatusId(rs.getString("user_status_id"));
				
				return user;
			}
		});
		
		return users;
	}
	
	
	/*
	 * Return user object by user id
	 */
	@Override
	public User get(String id){
		String query = "SELECT * FROM Users WHERE Users.user_id = " + id;
		
		return jdbcTemplate.query(query, new ResultSetExtractor<User>() {
			
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					User user = new User();
					user.setUserId(rs.getString("user_id"));
					user.setFirstName(rs.getString("first"));
					user.setLastName(rs.getString("last"));
					user.setPhone(rs.getString("phone"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setUserStatusId(rs.getString("user_status_id"));
					
					return user;
				}
				return null;
			}
		});
	}
	
	
	/*
	 * Return user object by email
	 */
	public User getUserByEmail(String email){
		String query = "SELECT * FROM Users WHERE Users.email = " + "'" + email + "'";
		
		return jdbcTemplate.query(query, new ResultSetExtractor<User>() {
			
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					User user = new User();
					user.setUserId(rs.getString("user_id"));
					user.setFirstName(rs.getString("first"));
					user.setLastName(rs.getString("last"));
					user.setPhone(rs.getString("phone"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setUserStatusId(rs.getString("user_status_id"));
					
					return user;
				}
				return null;
			}
		});
	}
	
	
	/*
	 * Update an existing user with new user values
	 */
	public void update(User user) {
		String userId = user.getUserId();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String phone = user.getPhone();
		String email = user.getEmail();
		String password = user.getPassword();
		String userStatusId = user.getUserStatusId();
		
		String query = "{CALL sp_update_user(?,?,?,?,?,?,?)}";
		
		jdbcTemplate.update(query, userId, firstName, lastName,
							phone, email, password, userStatusId);

	}
	
	// does session need to be passed in?
	public String loginValidation(User user, HttpSession session) 
	{
		
		User tempUser = getUserByEmail(user.getEmail());
		
		if (tempUser != null) {
        	System.out.println("User exists");
        	if (tempUser.getPassword().equals(user.getPassword())) {
        		// successful login
        		System.out.println("passwords match");
        		session.setAttribute("currentUser", tempUser);
        		return "home";
        	}
        	// set currentuser in session and throw to home page
        	
        	else {
        		// invalid password
        		// below should be an alert or error page instead of a print statement
        		System.out.println("Invalid login/password combination. Please try logging in again.");
        		return "login";
        	}
    	
    }
	    else {
	    	System.out.println("User doesn't exist");
	    	// give error
	    	System.out.println("No account associated with that email. Please re-enter your information or register an account.");
	    	return "login";
	    }
	}
	
}

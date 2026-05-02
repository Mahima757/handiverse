package com.Utilities;

	import com.dao.UserDAO;
	import com.model.User;

	public class MainTest {

	    public static void main(String[] args) {

	        UserDAO dao = new UserDAO();

	        User user = new User(1, "Jeeya B.K.", "jeeya@test.com", "123", "ADMIN");

	        dao.addUser(user);

	        System.out.println("User Inserted!");
	    }
	}


package com.ingcollegeapt.week4twebapp.dao;

import com.ingcollegeapt.week4twebapp.dao.interfaces.UserDAOInterface;
import com.ingcollegeapt.week4twebapp.model.User;
import com.ingcollegeapt.week4twebapp.utilities.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserDAO implements UserDAOInterface {

    // TODO: Create database connection and tracking variable: In constructor
    private Connection conn;
    private boolean isConnectionError = false;

    public UserDAO() {
        try {
            conn = DBConfig.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            isConnectionError = true;
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public int insertUser(String name, String password, String email) {
//        this validation will be done in controller part
//    if (name.isBlank() || email.isBlank() || password.isBlank()) return 0;
        try {
            //Check name and email already present
            final String CHECK_IF_USER = "select name,email from users where LOWER(name)=LOWER(?) or LOWER(email)=LOWER(?);";
            PreparedStatement pStm_ = conn.prepareStatement(CHECK_IF_USER);
            pStm_.setString(1, name);
            pStm_.setString(2, email);
            ResultSet rs = pStm_.executeQuery();
            if (rs.next()) {
                return 2;   // 2 for user or email already present
            }
            final String INSERT_USER = "insert into users (name, password, email, role) values (?, ?, ?, ?);";
            PreparedStatement pStm = conn.prepareStatement(INSERT_USER);
            pStm.setString(1, name);
            pStm.setString(2, password);
            pStm.setString(3, email);
            pStm.setString(4, "USER");
            
            int result = pStm.executeUpdate();
            return result;  //0 or 1 
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return 3;  // if 3 fault in query
        }
    }

    @Override
    public User getUser(String userName) {
        try {
            final String SELECT_USER = "select * from users where name=?;";

            PreparedStatement pStm_ = conn.prepareStatement(SELECT_USER);
            pStm_.setString(1, userName);
            ResultSet rs = pStm_.executeQuery();
            if (rs.next()) {
                final User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreateAt(rs.getObject("created_at", LocalDateTime.class));
                user.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                return user;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
        return null;
    }

}

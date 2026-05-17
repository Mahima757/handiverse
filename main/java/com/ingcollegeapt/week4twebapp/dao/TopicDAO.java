package com.ingcollegeapt.week4twebapp.dao;

import com.ingcollegeapt.week4twebapp.dao.interfaces.TopicDAOInterface;
import com.ingcollegeapt.week4twebapp.model.Topic;
import com.ingcollegeapt.week4twebapp.utilities.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TopicDAO implements TopicDAOInterface {

    // TODO: Create database connection and tracking variable: In constructor
    private Connection conn;
    private boolean isConnectionError = false;

    public TopicDAO() {
        try {
            conn = DBConfig.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            isConnectionError = true;
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public ArrayList<Topic> fetchAllTopics(int userId) {
        try {
            //updated to view all topics but for respective user only
            final String SELECT_ALL_TOPICS = "select * from topics where user_id=?;";
            PreparedStatement pSt = conn.prepareStatement(SELECT_ALL_TOPICS);
            pSt.setInt(1, userId);
            ResultSet result = pSt.executeQuery();
            final ArrayList<Topic> topics = new ArrayList<Topic>();
            while (result.next()) {
                Topic tp = new Topic();
                tp.setId(result.getInt("id"));
                tp.setName(result.getString("name"));
                tp.setUserId(result.getInt("user_id"));
                tp.setCreatedAt(result.getObject("created_at", LocalDateTime.class));
                tp.setCreatedAt(result.getObject("updated_at", LocalDateTime.class));
                topics.add(tp);
            }

            return topics;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.getLogger(TopicDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }
    }

    // Enter learning entry (TODO): Insert topic into database 
    //Updated to include user_id in topic for specific user
    @Override
    public boolean insertTopic(String topic, int userId) {
        if (topic.isBlank()) return false;
        try {
            //Check topic already present
            final String CHECK_IF_TOPIC = "select name from topics where LOWER(name)=LOWER(?);";
            PreparedStatement pStm_ = conn.prepareStatement(CHECK_IF_TOPIC);
            pStm_.setString(1, topic);
            ResultSet rs = pStm_.executeQuery();
            if(rs.next()){
                return false;
            }
            final String INSERT_TOPIC = "insert into topics (name,user_id) values (?,?);";
            PreparedStatement pStm = conn.prepareStatement(INSERT_TOPIC);
            pStm.setString(1, topic);
            pStm.setInt(2, userId);
            int result = pStm.executeUpdate();
            return (result > 0);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public Topic getTopicDetails(int id) {

        try {
            final String SELECT_TOPIC_ID = "select * from topics where id = ?;";
            PreparedStatement pStm = conn.prepareStatement(SELECT_TOPIC_ID);
            pStm.setInt(1, id);
            ResultSet result = pStm.executeQuery();
            while (result.next()) {
                Topic tp = new Topic();
                tp.setId(id);
                tp.setName(result.getString("name"));
                tp.setCreatedAt(result.getObject("created_at", LocalDateTime.class));
                tp.setUpdatedAt(result.getObject("updated_at", LocalDateTime.class));
                return tp;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getLocalizedMessage());

                }
            }
        }
        return null;
    }

    @Override
    public boolean updateTopic(String newTopic, int id) {
        try {
            final String UPDATE_TOPIC = "update topics set name = ? where id=?;";
            PreparedStatement pStm = conn.prepareStatement(UPDATE_TOPIC);
            pStm.setString(1, newTopic);
            pStm.setInt(2, id);
            int result = pStm.executeUpdate();

            return (result > 0);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTopic(int id) {
        try {
            final String DELETE_TOPIC = "delete from topics where id=?;";
            PreparedStatement pStm = conn.prepareStatement(DELETE_TOPIC);
            pStm.setInt(1, id);
            int result = pStm.executeUpdate();
            return (result > 0);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return false;
        }    
    }

}

package com.ingcollegeapt.week4twebapp.dao.interfaces;

import com.ingcollegeapt.week4twebapp.model.Topic;
import java.util.ArrayList;


public interface TopicDAOInterface {
    //Enter learning entry (TODO): Fetch all topics
    ArrayList<Topic> fetchAllTopics(int userId);
    
    //Enter learning entry (TODO): Insert topic into database
    boolean insertTopic(String topic, int userId);

    //Get Topic details
    Topic getTopicDetails(int id);
    
    //Update the existing Topic
    boolean updateTopic(String newTopic, int id);
    
    //Delete the existing Topic
    boolean deleteTopic(int id);
}

package com.ingcollegeapt.week4twebapp.controller.servlets;

import com.ingcollegeapt.week4twebapp.dao.TopicDAO;
import com.ingcollegeapt.week4twebapp.model.Topic;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name = "TopicServlet", urlPatterns = {"/topic"})
public class TopicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //if no action parameter Null Pointer exception so handle it
        final String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "new": { // this adds local scope to variables inside it

                //Dispatcher is server side so no url change will be seen
                final RequestDispatcher rd = request.getRequestDispatcher("/pages/topicadd.jsp");
                rd.forward(request, response);
                break;
            }
            case "edit": { // this adds local scope to variables inside it
                
                //Dispatcher is server side so no url change will be seen
                final RequestDispatcher rd = request.getRequestDispatcher("/pages/topicadd.jsp");
                rd.forward(request, response);
                break;
            }
            default: {
                //Get topic DAO object for CRUD Topics table
                final TopicDAO tdao = new TopicDAO();

                //Read all available topics in table via Topic DAO
                //Updated for to include topics per user
                ArrayList<Topic> topics = tdao.fetchAllTopics(1);

                //Set data to be sent to jsp via attribute setter
                // "topics" is the attribute that stores topics list in request
                request.setAttribute("topics", topics);
                //Send this updated request object to jsp via dispatcher
                //Dispatcher is server side so no url change will be seen
                final RequestDispatcher rd = request.getRequestDispatcher("/pages/topiclist.jsp");
                rd.forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final TopicDAO tdao = new TopicDAO();
        //if no action parameter Null Pointer exception so handle it
        final String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "add": { // this adds local scope to variables inside it
                String topicName = request.getParameter("topic");
                //insert topic to specific user only
                boolean isAdded = tdao.insertTopic(topicName,1);
                if (isAdded) {
                    response.sendRedirect(request.getContextPath() + "/topic");
                } else {
                    request.setAttribute("error", "Duplicate or empty Topic!");
                    //Dispatcher is server side so no url change will be seen
                    final RequestDispatcher rd = request.getRequestDispatcher("/pages/topicadd.jsp");
                    rd.forward(request, response);
                }
                break;
            }
            case "delete": { // this adds local scope to variables inside it
                int topicId = Integer.valueOf(request.getParameter("topicid"));
                boolean isDeleted = tdao.deleteTopic(topicId);
                if (isDeleted) {
                    response.sendRedirect(request.getContextPath() + "/topic");
                } else {
                    //TODO: if not deleted implement the error
                    System.out.println("Unable to delete the selected topic!");
                }
                break;
            }
            default:

        }
    }

}

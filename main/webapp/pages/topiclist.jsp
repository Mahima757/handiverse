<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/topiclist.css" />
  </head>
  <body>
    <div class="page">
      <header class="header">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/topic" style="text-decoration: none">
              <img src="${pageContext.request.contextPath}/resources/book.png" alt="LL" />
          </a>
          <h3>Learning Log</h3>
        </div>
        <div class="usersession">
          <h3>Username</h3>
          <a href="#" class="logout">Logout</a>
        </div>
      </header>

      <nav class="navbar">
        <ul>
          <li></li>
          <li><p>Topic Lists</p></li>
          <li><a href="topic?action=new">+New Topic</a></li>
        </ul>
      </nav>

      <main class="content">
        <div class="search">
          <form action="">
            <label for="search">Topic: </label>
            <input type="text" name="search" placeholder="Search..." />
            <button type="submit">SEARCH</button>
          </form>
        </div>
        <div class="topicContainer">
          <ul>
              <c:forEach var="topic" items="${topics}">
                <li class="topicItem">
              <a class="topic" href="entry?action=detail&topicid=${topic.getId()}"
                >${topic.getName()}</a>
                
              <a class="edit" href="topic?name=${topic.getName()}&action=edit&topicid=${topic.getId()}">Edit</a>
              
              <!-- Use form especially if you want post request to be sent -->
              <form action="topic" method="post">
                <input type="hidden" name="action" value="delete" />
                <input type="hidden" name="topicid" value="${topic.getId()}" />
                <button class="submit" type="submit"
                  onclick="return confirm('Are you sure you want to delete?');">Delete</button>
              </form>
            </li>
            </c:forEach>
          </ul>
        </div>
      </main>

      <footer class="footer">
        <h3>© Learning Logs</h3>
      </footer>
    </div>
  </body>
</html>


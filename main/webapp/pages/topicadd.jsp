<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/topicadd.css" />
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
          <li><a href="${pageContext.request.contextPath}/topic">< BACK</a></li>
          <li><p>${param.action eq "edit"? "EDIT ":"ADD "} Topic</p></li>
          <li></li>
        </ul>
      </nav>

      <main class="content">
        <div class="topicContainer">
            <form action="topic" method="post">
            <div class="topicItem">
              <label for="">Topic: </label>
              <input type="hidden" name="action" value="add" />
              <input type="text" placeholder="Topic Name" name="topic" 
                     value="${param.action eq "edit"? param.name:""}" />
            </div>
            <div class="submit">
              <button type="submit">Save</button>
            </div>
          </form>
        </div>
      </main>
          <p style="color: red; text-align: center;">
              ${error==null?"":error}
          </p>

      <footer class="footer">
        <h3>© Learning Logs</h3>
      </footer>
    </div>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log | Add Entry</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addentry.css" />
  </head>
  <body>
    <div class="page">
      <!-- Header -->
      <header class="header">
        <div class="logo">
          <a href="topiclist.html">
            <img src="${pageContext.request.contextPath}/resources/book.png" alt="LL" />
          </a>
          <h3>Learning Log</h3>
        </div>
        <div class="usersession">
          <h3>Username</h3>
          <a href="#" class="logout">Logout</a>
        </div>
      </header>

      <!-- Navigation -->
      <nav class="navbar">
        <ul>
          <li><a href="${pageContext.request.contextPath}/pages/entrylist.jsp">&lt; Back (Entry)</a></li>
          <li><p>Edit/Add New Entry</p></li>
          <li></li>
        </ul>
      </nav>

      <!-- Main Content -->
      <main class="content">
        <h2 class="topic-title">Topic Name</h2>

        <div class="form-card">
          <form>
            <div class="form-row">
              <label for="title">Title:</label>
              <input type="text" id="title" placeholder="Title" />
            </div>

            <div class="form-row">
              <label for="description">Description:</label>
              <textarea
                id="description"
                rows="4"
                placeholder="Description"
              ></textarea>
            </div>

            <div class="form-row">
              <label for="link">Link:</label>
              <input type="url" id="link" placeholder="Link" />
            </div>

            <div class="form-row">
              <label for="image">Image:</label>
              <input type="file" id="image" />
            </div>

            <div class="form-actions">
              <button type="submit">Save</button>
            </div>
          </form>
        </div>
      </main>

      <!-- Footer -->
      <footer class="footer">
        <h3>© Learning Log</h3>
      </footer>
    </div>
  </body>
</html>
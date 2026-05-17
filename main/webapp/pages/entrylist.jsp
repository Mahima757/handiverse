<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Learning Log | Entries</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entrylist.css" />
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
          <li><a href="${pageContext.request.contextPath}/pages/topiclist.jsp">&lt; Back (Topic)</a></li>
          <li><p>Entries List</p></li>
          <li><a href="${pageContext.request.contextPath}/pages/entryadd.jsp }">+ Add Entry</a></li>
        </ul>
      </nav>

      <!-- Main Content -->
      <main class="content">
        <!-- Search -->
        <div class="search-bar">
          <form action="" method="get">
            <label for="search">Entry:</label>
            <input type="text" id="search" placeholder="Search..." />
            <button type="submit">Search</button>
          </form>
        </div>

        <h2 class="topic-title">Topic Name</h2>

        <!-- Entries List -->
        <div class="entry-grid">
          <div class="entry-card">
            <div class="entry-header">
              <div>
                <h3>Entry Title</h3>
                <p class="date">Date: 2026/12/12</p>
              </div>
              <div class="photo">Photo</div>
            </div>

            <p class="entry-text">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </p>

            <p class="link">
              Link: <a href="https://www.google.com">www.google.com</a>
            </p>

            <div class="entry-actions">
              <button>Edit</button>
              <button class="danger">Delete</button>
            </div>
          </div>

          <!-- Entry Card -->
          <div class="entry-card">
            <div class="entry-header">
              <div>
                <h3>Entry Title</h3>
                <p class="date">Date: 2026/12/12</p>
              </div>
              <div class="photo">Photo</div>
            </div>

            <p class="entry-text">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </p>

            <p class="link">
              Link: <a href="https://www.google.com">www.google.com</a>
            </p>

            <div class="entry-actions">
              <button>Edit</button>
              <button class="danger">Delete</button>
            </div>
          </div>
        </div>
      </main>

      <!-- Footer -->
      <footer class="footer">
        <h3>© Learning Log</h3>
      </footer>
    </div>
  </body>
</html>
    
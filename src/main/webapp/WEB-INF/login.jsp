<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - HandiVerse</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css ">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>


<nav class="navbar">

    <div class="logo">HandiVerse</div>

    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/home"
               class="${activePage eq 'home' ? 'active' : ''}">Home</a>
        </li>
        <li><a href="${pageContext.request.contextPath}/shop">Shop</a></li>
        <li><a href="#">Categories</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
    </ul>

    <div class="nav-auth">
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                <span class="welcome-user">${sessionScope.loggedUser.fullName}</span>
            </c:when>
            <c:otherwise>
                <a class="btn-login"    href="${pageContext.request.contextPath}/login">Login</a>
                <a class="btn-register" href="${pageContext.request.contextPath}/register">Register</a>
            </c:otherwise>
        </c:choose>
    </div>

</nav>

          

<!-- MAIN LOGIN -->
<main class="auth-page">
    <section class="auth-card">


        <!-- LEFT IMAGE SIDE -->
        <div class="auth-image">
            <div class="auth-image-text">
                <h1>Curated by Hand,<br>Delivered with Love.</h1>
                <p>Join our community of craft lovers and independent artisans.</p>
            </div>
        </div>
                
        <!-- RIGHT -->
         <!-- RIGHT FORM SIDE -->
        <div class="auth-form-area">
        

            <div class="auth-tabs">
                <a href="${pageContext.request.contextPath}/login" class="active">Login</a>
                <a href="${pageContext.request.contextPath}/register">Register</a>
            </div>
            
                

            <h2>Welcome Back</h2>
            <p class="auth-subtitle">Please enter your details to access your collection.</p>
            
  <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form class="auth-form" action="${pageContext.request.contextPath}/login" method="post">

                <label>Email Address</label>
                <input type="email" name="email" placeholder="e.g. user@handiverse.com" required>
                
              <div class="password-row">
                    <label>Password</label>
                    <a href="#">Forgot?</a>
                </div>
                  
             <input type="password" name="password" required>

                <button type="submit" class="auth-button">Login</button>
            </form>
            
            <div class="auth-bottom-text">
                Don’t have an account?
                <a href="${pageContext.request.contextPath}/register">Register</a>
            </div>

        </div>
            
  </section>
</main>

<!-- FOOTER -->
<div class="footer">
    <div class="footer-columns">
        <div>
            <h4>HandiVerse</h4>
            <p>Elevating everyday through artisanal excellence.</p>
        </div>

        <div>
            <h4>Shop</h4>
            <p>Pottery</p>
            <p>Crochet</p>
            <p>Paintings</p>
        </div>

        <div>
            <h4>Information</h4>
            <p>Shipping Info</p>
            <p>Privacy Policy</p>
            <p>Terms</p>
        </div>
    </div>


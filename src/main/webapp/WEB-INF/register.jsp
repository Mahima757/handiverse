<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - HandiVerse</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css ">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="logo">HandiVerse</div>

    <div class="nav-links">
        <a href="#">Home</a>
        <a href="#">Shop</a>
        <a href="#">Categories</a>
        <a href="#">About</a>
        <a href="#">Contact</a>
    </div>

    <div class="auth">
        <a href="${pageContext.request.contextPath}/login">Login</a>
		<a href="${pageContext.request.contextPath}/register">Register</a>
    </div>
</div>

<main class="auth-page">
    <section class="auth-card">

        <!-- LEFT IMAGE SIDE -->
        <div class="auth-image">
            <div class="auth-image-text">
                <h1>Curated by Hand,<br>Delivered with Love.</h1>
                <p>Join our community of craft lovers and independent artisans.</p>
            </div>
        </div>

        <!-- RIGHT FORM SIDE -->
        <div class="auth-form-area">

            <div class="auth-tabs">
                <a href="${pageContext.request.contextPath}/login">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="active">Register</a>
            </div>

            <h2>Create Account</h2>
            <p class="auth-subtitle">Register to start exploring handmade collections.</p>

            <c:if test="${not empty error}">
    <div class="error-message">
        ${error}
    </div>
</c:if>

<c:if test="${not empty success}">
    <div class="success-message">
        ${success}
    </div>
</c:if>

            <form class="auth-form" action="${pageContext.request.contextPath}/register" method="post">

                <label>Full Name</label>
                <input type="text" name="fullName" placeholder="Enter your full name" required>

                <label>Email Address</label>
                <input type="email" name="email" placeholder="e.g. user@handiverse.com" required>

                <label>Password</label>
                <input type="password" name="password" placeholder="Create a password" required>

                <button type="submit" class="auth-button">Register</button>
            </form>

            <div class="auth-bottom-text">
                Already have an account?
                <a href="${pageContext.request.contextPath}/login">Login</a>
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

    <div class="footer-bottom">
        © 2024 HandiVerse
    </div>
</div>

</body>
</html>
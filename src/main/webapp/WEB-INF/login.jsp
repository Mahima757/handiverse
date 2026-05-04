<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - HandiVerse</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css ">
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
        <a href="login.jsp">Login</a>
        <a href="#" class="register-btn">Register</a>
    </div>
</div>

<!-- MAIN LOGIN -->
<div class="container">
    <div class="card">

        <!-- LEFT -->
        <div class="left">
            <div class="overlay">
                <h1>Curated by Hand,<br>Delivered with Love.</h1>
                <p>Join our community of craft lovers and independent artisans.</p>
            </div>
        </div>

        <!-- RIGHT -->
        <div class="right">

            <div class="tabs">
                <span class="active">LOGIN</span>
                <span>REGISTER</span>
            </div>

            <h2>Welcome Back</h2>
            <p>Please enter your details to access your collection.</p>

            <form action="${pageContext.request.contextPath}/login" method="post">

                <label>Email Address</label>
                <input type="email" name="email" placeholder="e.g. user@handiverse.com" required>

                <label>Password <span class="forgot">Forgot?</span></label>
                <input type="password" name="password" required>

                <button class="login-btn">Login</button>

            </form>

            <div class="bottom-text">
                Don’t have an account? <a href="#">Register</a>
            </div>

        </div>

    </div>
</div>

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
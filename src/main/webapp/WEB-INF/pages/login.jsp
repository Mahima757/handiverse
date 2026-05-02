<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - HandiVerse</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', sans-serif;
        }

        body {
            background: #f5f1ee;
        }

        /* NAVBAR */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 60px;
            background: #f5f1ee;
        }

        .logo {
            font-size: 22px;
            font-weight: bold;
        }

        .nav-links {
            display: flex;
            gap: 30px;
        }

        .nav-links a {
            text-decoration: none;
            color: #444;
            font-size: 14px;
        }

        .auth {
            display: flex;
            gap: 15px;
        }

        .auth a {
            text-decoration: none;
            color: #444;
        }

        .register-btn {
            background: #6b4f4f;
            color: white;
            padding: 8px 16px;
            border-radius: 8px;
        }

        /* MAIN */
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 85vh;
        }

        .card {
            display: flex;
            width: 900px;
            border-radius: 20px;
            overflow: hidden;
            background: white;
        }

        /* LEFT IMAGE */
        .left {
            width: 50%;
            background: url('images/pottery.jpg') center/cover no-repeat;
            position: relative;
        }

        .overlay {
            position: absolute;
            bottom: 30px;
            left: 30px;
            color: white;
        }

        .overlay h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .overlay p {
            font-size: 14px;
        }

        /* RIGHT FORM */
        .right {
            width: 50%;
            padding: 40px;
        }

        .tabs {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .tabs span {
            font-size: 14px;
            cursor: pointer;
        }

        .tabs .active {
            border-bottom: 2px solid #333;
            padding-bottom: 5px;
        }

        .right h2 {
            margin-bottom: 10px;
        }

        .right p {
            font-size: 13px;
            margin-bottom: 20px;
            color: gray;
        }

        label {
            font-size: 12px;
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #e0cfcf;
        }

        .forgot {
            font-size: 12px;
            float: right;
        }

        .login-btn {
            width: 100%;
            margin-top: 20px;
            padding: 12px;
            border: none;
            border-radius: 10px;
            background: #e6b8b7;
            cursor: pointer;
        }

        .bottom-text {
            text-align: center;
            margin-top: 15px;
            font-size: 13px;
        }

        /* FOOTER */
        .footer {
            background: #111;
            color: #ccc;
            padding: 40px 60px;
            margin-top: 40px;
        }

        .footer-columns {
            display: flex;
            justify-content: space-between;
        }

        .footer h4 {
            margin-bottom: 10px;
        }

        .footer p {
            font-size: 13px;
            margin-bottom: 5px;
        }

        .footer-bottom {
            text-align: center;
            margin-top: 20px;
            font-size: 12px;
        }

        /* RESPONSIVE */
        @media(max-width: 768px) {
            .card {
                flex-direction: column;
                width: 90%;
            }

            .left, .right {
                width: 100%;
                height: 300px;
            }
        }

    </style>
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
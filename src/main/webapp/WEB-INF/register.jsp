<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register — HandiVerse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"><%-- FIX: removed trailing space in filename --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>

<!-- ================================================
     NAVBAR
================================================ -->
<nav class="navbar">
    <div class="logo">HandiVerse</div>
    <ul>
        <li><a href="${pageContext.request.contextPath}/home"
               class="${activePage eq 'home' ? 'active' : ''}">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/shop">Shop</a></li>
        <li><a href="#">Categories</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
    </ul>
    <div class="nav-auth">
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                <span class="welcome-user">${sessionScope.loggedUser.fullName}</span>
                <a class="btn-login"    href="${pageContext.request.contextPath}/profile">Profile</a>
                <a class="btn-register" href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a class="btn-login"    href="${pageContext.request.contextPath}/login">Login</a>
                <a class="btn-register" href="${pageContext.request.contextPath}/register">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>


<!-- ================================================
     MAIN AUTH SECTION
================================================ -->
<main class="auth-page">
    <section class="auth-card">

        <!-- LEFT — Image Side -->
        <div class="auth-image">
            <div class="auth-image-text">
                <h1>Curated by Hand,<br>Delivered with Love.</h1>
                <p>Join our community of craft lovers and independent artisans.</p>
            </div>
        </div>

        <!-- RIGHT — Form Side -->
        <div class="auth-form-area">

            <!-- Login / Register tabs -->
            <div class="auth-tabs">
                <a href="${pageContext.request.contextPath}/login">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="active">Register</a>
            </div>

            <h2>Create Account</h2>
            <p class="auth-subtitle">Register to start exploring handmade collections.</p>

            <%-- Global error / success banners --%>
            <c:if test="${not empty error}">
                <div class="alert alert--error">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert--success">${success}</div>
            </c:if>

            <%-- FORM — field names MUST match RegisterServlet getParameter() keys --%>
            <form class="auth-form"
                  action="${pageContext.request.contextPath}/register"
                  method="post">

                <%-- Full Name — servlet reads: getParameter("username") --%>
                <div class="form-group ${not empty erUser ? 'form-group--error' : ''}">
                    <label for="username">Full Name</label>
                    <input
                        type="text"
                        id="username"
                        name="username"<%-- FIX: was name="fullName", servlet expects "username" --%>
                        placeholder="Enter your full name"
                        value="${not empty prevName ? prevName : ''}"<%-- repopulate on error --%>
                        required>
                    <c:if test="${not empty erUser}">
                        <span class="field-error">${erUser}</span>
                    </c:if>
                </div>

                <%-- Email — servlet reads: getParameter("email") --%>
                <div class="form-group ${not empty erMail ? 'form-group--error' : ''}">
                    <label for="email">Email Address</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="e.g. user@handiverse.com"
                        value="${not empty prevEmail ? prevEmail : ''}"<%-- repopulate on error --%>
                        required>
                    <c:if test="${not empty erMail}">
                        <span class="field-error">${erMail}</span>
                    </c:if>
                </div>

                <%-- Password — servlet reads: getParameter("password") --%>
                <div class="form-group ${not empty erPass ? 'form-group--error' : ''}">
                    <label for="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Min 6 chars, 1 uppercase, 1 number, 1 symbol"
                        required>
                    <c:if test="${not empty erPass}">
                        <span class="field-error">${erPass}</span>
                    </c:if>
                </div>

                <%-- Confirm Password — servlet reads: getParameter("cpassword") --%>
                <%-- FIX: this field was completely missing from original form --%>
                <div class="form-group ${not empty erCon ? 'form-group--error' : ''}">
                    <label for="cpassword">Confirm Password</label>
                    <input
                        type="password"
                        id="cpassword"
                        name="cpassword"
                        placeholder="Re-enter your password"
                        required>
                    <c:if test="${not empty erCon}">
                        <span class="field-error">${erCon}</span>
                    </c:if>
                </div>

                <button type="submit" class="auth-button">Create Account</button>

            </form>

            <div class="auth-bottom-text">
                Already have an account?
                <a href="${pageContext.request.contextPath}/login">Login here</a>
            </div>

        </div>
    </section>
</main>


<!-- ================================================
     FOOTER
================================================ -->
<footer>
    <div class="footer-grid">

        <div class="footer-brand">
            <span class="footer-logo">HandiVerse</span>
            <p>Connecting the world's most talented artisans with those
               who seek the extraordinary in the everyday.</p>
        </div>

        <div class="footer-col">
            <h4>Shop</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/shop">Pottery</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Crochet</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Paintings</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Woodwork</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Information</h4>
            <ul>
                <li><a href="#">Shipping Info</a></li>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Terms of Service</a></li>
                <li><a href="#">FAQ</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Newsletter</h4>
            <p class="footer-newsletter-desc">Get early access to new drops.</p>
            <form class="newsletter-form"
                  action="${pageContext.request.contextPath}/newsletter"
                  method="post">
                <input type="email" name="email" placeholder="Your email address" required>
                <button type="submit">Subscribe</button>
            </form>
        </div>

    </div>

    <div class="footer-bottom">
        <span>&copy; 2024 HandiVerse. Artfully Crafted.</span>
        <span>Made with &#9825; in Pokhara, Nepal</span>
    </div>
</footer>

</body>
</html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Register</title>
        <link rel="icon" href="./resources/book.png" type="image/png">
        <link rel="stylesheet" href="./css/login_register.css" />
    </head>
    <body>
        <div class="container">
            <div class="title">
                <img src="./resources/book.png" alt="LL" style="width: 24px; height: 24px" />
                <h2>Learning Logs</h2>
            </div>
            <form action="register" method="post">
                <h2>Register</h2>
                <p style='margin:0px;color:red; display: ${not empty error ? "block" : "none"}'>${error}</p>
                <input type="text" placeholder="Username" name="username" value="${empty erUser ? param.username:''}" required />
                <input type="email" placeholder="Email" name="email" value="${empty erMail ? param.email:''}" required />
                <input
                    type="password"
                    placeholder="Password"
                    name="password"
                    required
                    />
                <input
                    type="password"
                    placeholder="Confirm Password"
                    name="cpassword"
                    required
                    />
                <button type="submit">Register</button>
            </form>
        </div>
    </body>
</html>


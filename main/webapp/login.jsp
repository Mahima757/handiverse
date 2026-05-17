<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Login</title>
        <link rel="icon" href="./resources/book.png" type="image/png" />
        <link rel="stylesheet" href="./css/login_register.css" />
    </head>
    <body>
        <div class="container">
            <div class="title">
                <img
                    src="./resources/book.png"
                    alt="LL"
                    style="width: 24px; height: 24px"
                    />
                <h2>Learning Logs</h2>
            </div>
            <form action="login" method="post">
                <h2>Login</h2>
                <p style='margin:0px;color:red; display: ${not empty error ? "block" : "none"}'>${error}</p>
                <input type="text" placeholder="Username" name="username" required />
                <input
                    type="password"
                    placeholder="Password"
                    name="password"
                    required
                    />
                <button type="submit">Log In</button>
            </form>
        </div>
    </body>
</html>


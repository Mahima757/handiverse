<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Us - Handiverse</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial,sans-serif;
}

body{
    background:#f8f2f2;
    color:#333;
}

/* Navbar */

.navbar{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:20px 60px;
    background:white;
    box-shadow:0 2px 10px rgba(0,0,0,0.05);
}

.logo{
    font-size:32px;
    font-weight:bold;
    color:#8a6a6a;
}

.nav-links{
    display:flex;
    align-items:center;
}

.nav-links a{
    text-decoration:none;
    margin-left:25px;
    color:#444;
    font-size:14px;
    font-weight:500;
}

.nav-links a:hover{
    color:#c98b8b;
}

/* Hero Section */

.hero{
    width:90%;
    margin:70px auto;
    display:flex;
    align-items:center;
    justify-content:space-between;
    gap:50px;
}

.hero-text{
    flex:1;
}

.hero-text h1{
    font-size:60px;
    margin-bottom:25px;
    line-height:1.2;
    color:#222;
}

.hero-text p{
    font-size:18px;
    line-height:1.8;
    color:#666;
}

.hero-image{
    flex:1;
}

.hero-image img{
    width:100%;
    border-radius:25px;
    object-fit:cover;
}

/* Ethos Section */

.ethos{
    width:90%;
    margin:100px auto;
    text-align:center;
}

.ethos h2{
    font-size:42px;
    margin-bottom:60px;
    color:#222;
}

.cards{
    display:flex;
    gap:30px;
}

.card{
    background:white;
    padding:40px;
    border-radius:20px;
    box-shadow:0 4px 12px rgba(0,0,0,0.08);
    transition:0.3s;
}

.card:hover{
    transform:translateY(-5px);
}

.card h3{
    margin-bottom:20px;
    font-size:28px;
    color:#8a6a6a;
}

.card p{
    line-height:1.8;
    color:#666;
}

/* Contact Section */

.contact{
    width:90%;
    margin:100px auto;
    display:flex;
    gap:50px;
}

.contact-left{
    flex:1;
}

.contact-left h2{
    font-size:45px;
    margin-bottom:30px;
    color:#222;
}

.contact-left p{
    line-height:1.8;
    margin-bottom:25px;
    color:#666;
    font-size:18px;
}

.contact-right{
    flex:1;
    background:#fff;
    padding:40px;
    border-radius:20px;
    box-shadow:0 4px 12px rgba(0,0,0,0.08);
}

.contact-right input,
.contact-right textarea{
    width:100%;
    padding:15px;
    margin-bottom:20px;
    border:1px solid #ddd;
    border-radius:10px;
    outline:none;
}

.contact-right button{
    width:100%;
    padding:15px;
    border:none;
    background:#8a6a6a;
    color:white;
    border-radius:10px;
    font-size:16px;
    cursor:pointer;
}

.contact-right button:hover{
    background:#6f5454;
}

/* Footer */

footer{
    background:#111;
    color:white;
    text-align:center;
    padding:30px;
    margin-top:80px;
}

</style>

</head>

<body>

<!-- Navbar -->

<div class="navbar">

    <div class="logo">Handiverse</div>

    <div class="nav-links">

        <a href="home">HOME</a>

        <a href="shop">SHOP</a>

        <a href="#">CATEGORIES</a>

        <a href="about">ABOUT</a>

        <a href="#">CONTACT</a>

    </div>

</div>

<!-- Hero Section -->

<div class="hero">

    <div class="hero-text">

        <h1>Crafting a slower, more meaningful world.</h1>

        <p>
            Handiverse is an online handicraft management system developed to support handmade artistry and promote beautiful handcrafted products such as crochet, paintings, and pottery.
        </p>

        <br>

        <p>
            We connect local artists and creators with customers who appreciate creativity, craftsmanship, and meaningful handmade products.
        </p>

    </div>

    <div class="hero-image">

        <img src="resources/potteryhome.jpeg" alt="Pottery Image">

    </div>

</div>

<!-- Ethos Section -->

<div class="ethos">

    <h2>The Handiverse Ethos</h2>

    <div class="cards">

        <div class="card">

            <h3>Authentic Artistry</h3>

            <p>
                Every handmade product reflects creativity, passion, and traditional craftsmanship created by skilled local artists.
            </p>

        </div>

        <div class="card">

            <h3>Sustainable Soul</h3>

            <p>
                We encourage sustainable handmade products that respect both culture and the environment.
            </p>

        </div>

        <div class="card">

            <h3>Fair Futures</h3>

            <p>
                Handiverse supports local artists by helping them showcase and sell their creations online.
            </p>

        </div>

    </div>

</div>

<!-- Contact Section -->

<div class="contact">

    <div class="contact-left">

        <h2>Get in Touch</h2>

        <p>Email: handiverse@gmail.com</p>

        <p>Phone: +977 9800000000</p>

        <p>Location: Nepal</p>

    </div>

    <div class="contact-right">

        <input type="text" placeholder="Your Name">

        <input type="email" placeholder="Your Email">

        <textarea rows="6" placeholder="Your Message"></textarea>

        <button>Send Message</button>

    </div>

</div>

<!-- Footer -->

<footer>

    © 2026 Handiverse | All Rights Reserved

</footer>

</body>
</html>
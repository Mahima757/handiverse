<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>HandiVerse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>

<!-- ================================================
     NAVBAR  (unchanged — your original code)
================================================ -->
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

        <%-- CART ICON WITH BADGE --%>
        <a href="${pageContext.request.contextPath}/cart" class="nav-cart" title="View Cart">
            <svg class="cart-icon" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path d="M6 2L3 6v14a2 2 0 002 2h14a2 2 0 002-2V6l-3-4z"/>
                <line x1="3" y1="6" x2="21" y2="6"/>
                <path d="M16 10a4 4 0 01-8 0"/>
            </svg>
            <c:if test="${not empty sessionScope.cartCount and sessionScope.cartCount > 0}">
                <span class="cart-badge">${sessionScope.cartCount}</span>
            </c:if>
        </a>

        <%-- LOGIN / REGISTER or PROFILE / LOGOUT --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                <span class="welcome-user">Welcome, ${sessionScope.loggedUser.fullName}</span>
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
     HERO
================================================ -->
<section class="hero">

    <%-- Background image as <img> tag so contextPath works correctly --%>
    <img
        class="hero-bg-img"
        src="${pageContext.request.contextPath}/resources/background.png" alt="Handmade pottery and crafts from Nepal">

    <%-- Dark overlay on top of image --%>
    <div class="hero-overlay"></div>

    <%-- Centered text content --%>
    <div class="hero-content">
        <p class="hero-eyebrow">Welcome to the Gallery</p>
        <h1>Handmade with Love</h1>
        <p class="hero-sub">
            Discover a curated collection of artisanal treasures, where every
            object tells a unique story of tradition, patience, and human touch.
        </p>
        <div class="hero-btns">
            <a class="btn-primary" href="${pageContext.request.contextPath}/shop">Shop Now</a>
            <a class="btn-outline" href="${pageContext.request.contextPath}/about">Explore Story</a>
        </div>
    </div>

</section>


<!-- ================================================
     CURATED COLLECTIONS
================================================ -->
<section class="collections">

    <div class="section-head">
        <div>
            <h2>Curated Collections</h2>
            <p class="section-sub">Explore masterpieces categorised by craft</p>
        </div>
        <a href="${pageContext.request.contextPath}/shop" class="view-all">View All Collections</a>
    </div>

    <div class="collection-grid">

        <c:choose>
            <c:when test="${not empty categories}">
                <c:forEach var="cat" items="${categories}" varStatus="st">
                    <c:if test="${st.index < 3}">
                        <a href="${pageContext.request.contextPath}/shop?category=${cat.categoryId}"
                           class="col-card ${st.index == 0 ? 'col-card--big' : 'col-card--small'}">
                            <img src="${pageContext.request.contextPath}/images/categories/${cat.categoryId}.jpg"
                                 alt="${cat.name}"
                                 onerror="this.src='${pageContext.request.contextPath}/images/placeholder-cat.jpg'">
                            <div class="col-label">
                                <h3>${cat.name}</h3>
                                <c:if test="${not empty cat.description}">
                                    <p>${cat.description}</p>
                                </c:if>
                            </div>
                        </a>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- Static fallback cards -->
                <a href="${pageContext.request.contextPath}/shop" class="col-card col-card--big">
                    <img src="${pageContext.request.contextPath}/resources/potteryhome.jpeg" alt="Pottery">
                    <div class="col-label">
                        <h3>Pottery</h3>
                        <p>Earth and fire transformed into timeless vessels.</p>
                    </div>
                </a>
                <a href="${pageContext.request.contextPath}/shop" class="col-card col-card--small">
                    <img src="${pageContext.request.contextPath}/resources/crochethome.png" alt="Crochet">
                    <div class="col-label"><h3>Crochet</h3></div>
                </a>
                <a href="${pageContext.request.contextPath}/shop" class="col-card col-card--small">
                    <img src="${pageContext.request.contextPath}/resources/woodworkhome.png" alt="Woodwork">
                    <div class="col-label"><h3>Woodwork</h3></div>
                </a>
            </c:otherwise>
        </c:choose>

    </div>
</section>


<!-- ================================================
     FEATURED PRODUCTS
================================================ -->
<section class="products">

    <div class="section-head">
        <div>
            <h2>Featured Pieces</h2>
            <p class="section-sub">Handpicked directly from our artisans</p>
        </div>
        <a href="${pageContext.request.contextPath}/shop" class="view-all">View All</a>
    </div>

    <div class="product-grid">

        <c:choose>
            <c:when test="${not empty featuredProducts}">
                <c:forEach var="p" items="${featuredProducts}">
                    <div class="product-card">

                        <div class="product-img-wrap">
                            <img src="${pageContext.request.contextPath}/images/products/${p.image}"
                                 alt="${p.productName}"
                                 onerror="this.src='${pageContext.request.contextPath}/images/placeholder.png'">
                            <span class="product-badge">New</span>
                        </div>

                        <div class="product-info">
                            <h3>${p.productName}</h3>
                            <p class="product-category">${p.categoryName}</p>
                            <div class="product-footer">
                                <span class="price">
                                    $<fmt:formatNumber value="${p.price}" type="number" minFractionDigits="2"/>
                                </span>
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="productId" value="${p.productId}">
                                    <input type="hidden" name="quantity"  value="1">
                                    <button type="submit" class="add-cart-btn" title="Add to Cart">&#43;</button>
                                </form>
                            </div>
                        </div>

                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p class="empty-msg">Our artisans are crafting something beautiful. Check back soon!</p>
            </c:otherwise>
        </c:choose>

    </div>
</section>


<!-- ================================================
     TESTIMONIALS — Voices of Appreciation
================================================ -->
<section class="testimonials">

    <div class="testimonials-left">
        <h2>Voices of<br>Appreciation</h2>
        <p>
            Our community is built on the connection between makers and
            those who appreciate the soul in the objects they own.
        </p>
        <div class="testi-nav">
            <a href="#testimonials" class="testi-arrow">&#8592;</a>
            <a href="#testimonials" class="testi-arrow">&#8594;</a>
        </div>
    </div>

    <div class="testimonials-right">

        <div class="testimonial-box">
            <span class="quote-mark">&#8220;</span>
            <p>
                The attention to detail in the ceramic set I purchased is
                breathtaking. You can truly feel the maker's hand in every
                curve. HandiVerse has changed how I curate my home.
            </p>
            <div class="testi-author">
                <img src="${pageContext.request.contextPath}/resources/avatar1.jpeg"
                     alt="Sarah Mitchell"
                     onerror="this.src='${pageContext.request.contextPath}/resources/avatar-default.png'">
                <div>
                    <strong>Sarah Mitchell</strong>
                    <span>Art Collector</span>
                </div>
            </div>
        </div>

        <div class="testimonial-box">
            <span class="quote-mark">&#8220;</span>
            <p>
                Finding authentic textiles that aren't mass-produced is a
                challenge. HandiVerse makes it effortless to support real
                artisans while elevating your living space.
            </p>
            <div class="testi-author">
                <img src="${pageContext.request.contextPath}/resources/avatar2.jpeg"
                     alt="Julian Chen"
                     onerror="this.src='${pageContext.request.contextPath}/images/avatar-default.png'">
                <div>
                    <strong>Julian Chen</strong>
                    <span>Interior Designer</span>
                </div>
            </div>
        </div>

    </div>

</section>


<!-- ================================================
     FOOTER
================================================ -->
<footer>

    <div class="footer-grid">

        <!-- Brand -->
        <div class="footer-brand">
            <span class="footer-logo">HandiVerse</span>
            <p>Connecting the world's most talented artisans with those
               who seek the extraordinary in the everyday.</p>
        </div>

        <!-- Explore -->
        <div class="footer-col">
            <h4>Explore</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/shop">Ceramics</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Paintings</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Woodwork</a></li>
            </ul>
        </div>

        <!-- Resources -->
        <div class="footer-col">
            <h4>Resources</h4>
            <ul>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Shipping Info</a></li>
                <li><a href="#">Terms of Service</a></li>
                <li><a href="#">FAQ</a></li>
            </ul>
        </div>

        <!-- Newsletter -->
        <div class="footer-col">
            <h4>Newsletter</h4>
            <p class="footer-newsletter-desc">
                Join our community for early access to new drops.
            </p>
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

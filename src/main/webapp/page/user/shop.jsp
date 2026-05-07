<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shop — HandiVerse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
</head>
<body>

<!-- =====================================================
     NAVBAR
====================================================== -->
<nav class="navbar">

    <div class="logo">HandiVerse</div>

    <ul>
        <li><a href="${pageContext.request.contextPath}/home"
               class="${activePage eq 'home' ? 'active' : ''}">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/shop"
               class="${activePage eq 'shop' ? 'active' : ''}">Shop</a></li>
        <li><a href="#">Categories</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
    </ul>

    <div class="nav-auth">

        <%-- Search --%>
        <form class="nav-search-form"
              action="${pageContext.request.contextPath}/shop"
              method="get">
            <input type="text" name="search"
                   placeholder="Search crafts..."
                   value="${not empty keyword ? keyword : ''}">
            <button type="submit">&#128269;</button>
        </form>

        <%-- Cart --%>
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

        <%-- Auth --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                <a class="btn-login"    href="${pageContext.request.contextPath}/profile">Profile</a>
                <a class="btn-register" href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a class="btn-register" href="${pageContext.request.contextPath}/register">Register</a>
            </c:otherwise>
        </c:choose>

    </div>
</nav>


<!-- =====================================================
     SHOP BODY — sidebar + main wrapped in filter form
====================================================== -->
<div class="shop-wrapper">

    <!-- ── LEFT SIDEBAR ──────────────────────────── -->
    <aside class="shop-sidebar">
        <form class="filter-form"
              action="${pageContext.request.contextPath}/shop"
              method="get">

            <%-- Carry keyword if present --%>
            <c:if test="${not empty keyword}">
                <input type="hidden" name="search" value="${keyword}">
            </c:if>

            <!-- COLLECTIONS (Category Checkboxes) -->
            <div class="sidebar-section">
                <h3 class="sidebar-heading">Collections</h3>
                <ul class="checkbox-list">
                    <c:forEach var="cat" items="${categories}">
                        <li class="checkbox-item">
                            <label class="checkbox-label">
                                <input
                                    type="checkbox"
                                    name="category"
                                    value="${cat.categoryId}"
                                    ${selectedCatSet.contains(cat.categoryId) ? 'checked' : ''}>
                                <span class="checkbox-custom"></span>
                                <span class="checkbox-text">${cat.name}</span>
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- PRICE RANGE -->
            <div class="sidebar-section">
                <h3 class="sidebar-heading">Price Range</h3>
                <div class="price-range-wrap">
                    <input
                        type="range"
                        name="maxPrice"
                        class="price-slider"
                        min="0"
                        max="1000"
                        step="10"
                        value="${not empty maxPrice ? maxPrice : 1000}">
                    <div class="price-labels">
                        <span>$0</span>
                        <span>$1,000+</span>
                    </div>
                </div>
            </div>

            <!-- SUSTAINABILITY TAGS -->
            <div class="sidebar-section">
                <h3 class="sidebar-heading">Sustainability Tags</h3>
                <div class="tag-list">
                    <label class="tag-pill">
                        <input type="checkbox" name="tag" value="eco-friendly">
                        <span>Eco-Friendly</span>
                    </label>
                    <label class="tag-pill">
                        <input type="checkbox" name="tag" value="hand-painted">
                        <span>Hand-Painted</span>
                    </label>
                    <label class="tag-pill">
                        <input type="checkbox" name="tag" value="recycled">
                        <span>Recycled</span>
                    </label>
                </div>
            </div>

            <button type="submit" class="apply-filters-btn">Apply Filters</button>

            <c:if test="${not empty selectedCatSet or not empty maxPrice}">
                <a href="${pageContext.request.contextPath}/shop"
                   class="clear-filters-link">&#10005; Clear All</a>
            </c:if>

        </form>
    </aside>


    <!-- ── MAIN CONTENT ──────────────────────────── -->
    <main class="shop-main">

        <!-- Top bar: title + count + sort -->
        <div class="shop-topbar">
            <div class="shop-topbar-left">
                <h2 class="shop-heading">Featured Crafts</h2>
                <p class="shop-count">
                    Showing ${products.size()} of ${totalCount} unique items
                </p>
            </div>
            <div class="shop-topbar-right">
                <form action="${pageContext.request.contextPath}/shop" method="get" class="sort-form">
                    <%-- Carry all current filters through sort change --%>
                    <c:if test="${not empty keyword}">
                        <input type="hidden" name="search" value="${keyword}">
                    </c:if>
                    <c:forEach var="catId" items="${selectedCatSet}">
                        <input type="hidden" name="category" value="${catId}">
                    </c:forEach>
                    <c:if test="${not empty maxPrice}">
                        <input type="hidden" name="maxPrice" value="${maxPrice}">
                    </c:if>
                    <select name="sort" class="sort-select" onchange="this.form.submit()">
                        <option value="newest"     ${sort eq 'newest'     ? 'selected' : ''}>Newest Arrivals</option>
                        <option value="price_asc"  ${sort eq 'price_asc'  ? 'selected' : ''}>Price: Low to High</option>
                        <option value="price_desc" ${sort eq 'price_desc' ? 'selected' : ''}>Price: High to Low</option>
                        <option value="name_asc"   ${sort eq 'name_asc'   ? 'selected' : ''}>Name: A to Z</option>
                    </select>
                    <noscript><button type="submit" class="sort-submit-btn">Sort</button></noscript>
                </form>
            </div>
        </div>


        <!-- Product Grid -->
        <c:choose>
            <c:when test="${not empty products}">

                <div class="product-grid">
                    <c:forEach var="p" items="${products}">
                        <div class="product-card">

                            <!-- Image -->
                            <a href="${pageContext.request.contextPath}/product?id=${p.productId}"
                               class="product-img-link">
                                <div class="product-img-wrap">
                                    <img
                                        src="${pageContext.request.contextPath}/images/products/${p.image}"
                                        alt="${p.productName}"
                                        onerror="this.src='${pageContext.request.contextPath}/images/placeholder.png'">
                                    <c:if test="${p.quantity == 0}">
                                        <div class="sold-out-overlay">Sold Out</div>
                                    </c:if>
                                </div>
                            </a>

                            <!-- Info -->
                            <div class="product-info">
                                <p class="product-cat-label">${p.categoryName}</p>
                                <h3 class="product-name">
                                    <a href="${pageContext.request.contextPath}/product?id=${p.productId}">
                                        ${p.productName}
                                    </a>
                                </h3>
                                <p class="product-price">
                                    $<fmt:formatNumber value="${p.price}" type="number" minFractionDigits="2"/>
                                </p>
                            </div>

                            <!-- Card Actions -->
                            <div class="product-actions">
                                <c:choose>
                                    <c:when test="${p.quantity > 0}">
                                        <form action="${pageContext.request.contextPath}/cart"
                                              method="post"
                                              class="add-cart-form">
                                            <input type="hidden" name="productId" value="${p.productId}">
                                            <input type="hidden" name="quantity"  value="1">
                                            <button type="submit" class="add-cart-btn">Add to Cart</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="add-cart-btn add-cart-btn--disabled" disabled>
                                            Sold Out
                                        </button>
                                    </c:otherwise>
                                </c:choose>

                                <a href="${pageContext.request.contextPath}/product?id=${p.productId}"
                                   class="view-btn" title="View Details">&#128065;</a>
                            </div>

                        </div>
                    </c:forEach>
                </div>


                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <div class="pagination">

                        <%-- Previous --%>
                        <c:choose>
                            <c:when test="${currentPage > 1}">
                                <a href="${pageContext.request.contextPath}/shop?page=${currentPage - 1}${not empty keyword ? '&search='.concat(keyword) : ''}&sort=${sort}"
                                   class="page-btn page-btn--nav">&#8249;</a>
                            </c:when>
                            <c:otherwise>
                                <span class="page-btn page-btn--nav page-btn--disabled">&#8249;</span>
                            </c:otherwise>
                        </c:choose>

                        <%-- Page numbers --%>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <span class="page-btn page-btn--active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/shop?page=${i}${not empty keyword ? '&search='.concat(keyword) : ''}&sort=${sort}"
                                       class="page-btn">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <%-- Next --%>
                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <a href="${pageContext.request.contextPath}/shop?page=${currentPage + 1}${not empty keyword ? '&search='.concat(keyword) : ''}&sort=${sort}"
                                   class="page-btn page-btn--nav">&#8250;</a>
                            </c:when>
                            <c:otherwise>
                                <span class="page-btn page-btn--nav page-btn--disabled">&#8250;</span>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </c:if>

            </c:when>

            <%-- Empty State --%>
            <c:otherwise>
                <div class="shop-empty">
                    <p class="empty-icon">&#127873;</p>
                    <h3>No products found</h3>
                    <p>Try adjusting your filters or search term.</p>
                    <a href="${pageContext.request.contextPath}/shop" class="btn-primary">
                        View All Products
                    </a>
                </div>
            </c:otherwise>
        </c:choose>

    </main>
</div>


<!-- =====================================================
     FOOTER
====================================================== -->
<footer>
    <div class="footer-grid">

        <div class="footer-brand">
            <span class="footer-logo">HandiVerse</span>
            <p>Supporting local artisans and bringing the beauty of
               handmade crafts to your doorstep. Every piece tells a story.</p>
            <div class="footer-social">
                <a href="#" title="Website">&#127760;</a>
                <a href="#" title="Email">&#9993;</a>
            </div>
        </div>

        <div class="footer-col">
            <h4>Shop</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/shop">All Collections</a></li>
                <li><a href="${pageContext.request.contextPath}/shop?sort=newest">New Arrivals</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Best Sellers</a></li>
                <li><a href="#">Gift Cards</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Information</h4>
            <ul>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Shipping Info</a></li>
                <li><a href="#">Terms of Service</a></li>
                <li><a href="#">FAQ</a></li>
            </ul>
        </div>

        <div class="footer-col">
            <h4>Newsletter</h4>
            <p class="footer-newsletter-desc">
                Join our mailing list for exclusive craft updates.
            </p>
            <form class="newsletter-form"
                  action="${pageContext.request.contextPath}/newsletter"
                  method="post">
                <input type="email" name="email" placeholder="Email address" required>
                <button type="submit">Subscribe</button>
            </form>
        </div>

    </div>

    <div class="footer-bottom">
        <span>&copy; 2024 HandiVerse. Artfully Crafted.</span>
    </div>
</footer>

</body>
</html>

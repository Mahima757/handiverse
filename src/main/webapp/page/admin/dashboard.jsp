<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Product, com.model.Order, java.util.List, java.text.NumberFormat, java.util.Locale" %>
<%
    // ── Data forwarded by AdminDashboardServlet ──────────────────────────────
    List<Product> topProducts   = (List<Product>) request.getAttribute("topProducts");
    List<Order>   recentOrders  = (List<Order>)   request.getAttribute("recentOrders");

    double totalRevenue   = request.getAttribute("totalRevenue")   != null ? (double) request.getAttribute("totalRevenue")   : 0;
    int    totalOrders    = request.getAttribute("totalOrders")    != null ? (int)    request.getAttribute("totalOrders")    : 0;
    int    newCustomers   = request.getAttribute("newCustomers")   != null ? (int)    request.getAttribute("newCustomers")   : 0;
    int    activeListings = request.getAttribute("activeListings") != null ? (int)    request.getAttribute("activeListings") : 0;

    double revenueChange  = request.getAttribute("revenueChange")  != null ? (double) request.getAttribute("revenueChange")  : 0;
    double ordersChange   = request.getAttribute("ordersChange")   != null ? (double) request.getAttribute("ordersChange")   : 0;
    double customersChange= request.getAttribute("customersChange")!= null ? (double) request.getAttribute("customersChange"): 0;

    // Week-by-week sales data for the chart (forwarded as int[4])
    int[] weeklySales = request.getAttribute("weeklySales") != null
                        ? (int[]) request.getAttribute("weeklySales")
                        : new int[]{0, 0, 0, 0};

    NumberFormat currencyFmt = NumberFormat.getCurrencyInstance(Locale.US);

    String activeNav = "dashboard";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artisan Craft – Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        /* ── CSS Variables ─────────────────────────────────────────────── */
        :root {
            --brand:       #8B2A2A;
            --brand-light: #B94040;
            --brand-bg:    #F9F4F1;
            --sidebar-bg:  #FFFFFF;
            --card-bg:     #FFFFFF;
            --text-main:   #1A1A1A;
            --text-muted:  #7A7A7A;
            --border:      #EAE4DF;
            --green:       #3D8C5E;
            --green-bg:    #EBF5EE;
            --amber:       #B06000;
            --amber-bg:    #FFF3E0;
            --red:         #C0392B;
            --red-bg:      #FDECEA;
            --shadow-sm:   0 1px 4px rgba(0,0,0,.06);
            --shadow-md:   0 4px 16px rgba(0,0,0,.08);
            --radius:      12px;
        }

        /* ── Reset ──────────────────────────────────────────────────────── */
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'DM Sans', sans-serif;
            background: var(--brand-bg);
            color: var(--text-main);
            display: flex;
            min-height: 100vh;
        }

        /* ── Sidebar ─────────────────────────────────────────────────────── */
        .sidebar {
            width: 220px;
            min-height: 100vh;
            background: var(--sidebar-bg);
            border-right: 1px solid var(--border);
            display: flex;
            flex-direction: column;
            position: fixed;
            top: 0; left: 0; bottom: 0;
            z-index: 100;
            padding: 28px 0 24px;
        }
        .sidebar-brand {
            padding: 0 24px 28px;
            border-bottom: 1px solid var(--border);
        }
        .sidebar-brand h1 {
            font-family: 'Playfair Display', serif;
            font-size: 1.25rem;
            color: var(--brand);
            line-height: 1.2;
        }
        .sidebar-brand span {
            font-size: .75rem;
            color: var(--text-muted);
            font-weight: 400;
            letter-spacing: .04em;
        }
        nav { margin-top: 20px; flex: 1; }
        nav a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 11px 24px;
            text-decoration: none;
            color: var(--text-muted);
            font-size: .9rem;
            font-weight: 500;
            border-left: 3px solid transparent;
            transition: color .2s, background .2s;
        }
        nav a:hover { color: var(--brand); background: var(--brand-bg); }
        nav a.active {
            color: var(--brand);
            background: var(--brand-bg);
            border-left-color: var(--brand);
            font-weight: 600;
        }
        nav a svg { width: 18px; height: 18px; flex-shrink: 0; }

        .sidebar-btn {
            margin: 24px;
        }
        .btn-add {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            width: 100%;
            padding: 12px;
            background: var(--brand);
            color: #fff;
            border: none;
            border-radius: var(--radius);
            font-family: 'DM Sans', sans-serif;
            font-size: .9rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: background .2s;
        }
        .btn-add:hover { background: var(--brand-light); }

        /* ── Main Content ────────────────────────────────────────────────── */
        .main {
            margin-left: 220px;
            flex: 1;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        /* ── Top Bar ─────────────────────────────────────────────────────── */
        .topbar {
            background: #fff;
            border-bottom: 1px solid var(--border);
            padding: 14px 32px;
            display: flex;
            align-items: center;
            gap: 16px;
            position: sticky;
            top: 0;
            z-index: 50;
        }
        .search-wrap {
            flex: 1;
            max-width: 420px;
            position: relative;
        }
        .search-wrap input {
            width: 100%;
            padding: 10px 16px 10px 40px;
            border: 1px solid var(--border);
            border-radius: 99px;
            font-family: 'DM Sans', sans-serif;
            font-size: .875rem;
            color: var(--text-main);
            background: var(--brand-bg);
            outline: none;
            transition: border-color .2s;
        }
        .search-wrap input:focus { border-color: var(--brand); }
        .search-wrap svg {
            position: absolute;
            left: 13px; top: 50%;
            transform: translateY(-50%);
            width: 16px; height: 16px;
            color: var(--text-muted);
        }
        .topbar-actions {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-left: auto;
        }
        .icon-btn {
            background: none;
            border: none;
            cursor: pointer;
            color: var(--text-muted);
            display: flex;
            align-items: center;
            transition: color .2s;
        }
        .icon-btn:hover { color: var(--brand); }
        .avatar {
            width: 36px; height: 36px;
            border-radius: 50%;
            background: var(--brand);
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: .85rem;
        }

        /* ── Page Body ───────────────────────────────────────────────────── */
        .content { padding: 32px; }
        .page-header { margin-bottom: 28px; }
        .page-header h2 {
            font-family: 'Playfair Display', serif;
            font-size: 1.75rem;
            color: var(--text-main);
        }
        .page-header p { color: var(--text-muted); font-size: .9rem; margin-top: 4px; }

        /* ── Stat Cards ──────────────────────────────────────────────────── */
        .stat-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 16px;
            margin-bottom: 28px;
        }
        .stat-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 22px 20px;
            box-shadow: var(--shadow-sm);
        }
        .stat-header {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            margin-bottom: 12px;
        }
        .stat-icon {
            width: 38px; height: 38px;
            border-radius: 10px;
            background: var(--brand-bg);
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--brand);
        }
        .stat-badge {
            font-size: .75rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 3px;
        }
        .stat-badge.up   { color: var(--green); }
        .stat-badge.down { color: var(--red); }
        .stat-badge.neutral { color: var(--text-muted); }
        .stat-label { font-size: .82rem; color: var(--text-muted); margin-bottom: 4px; }
        .stat-value {
            font-family: 'Playfair Display', serif;
            font-size: 1.6rem;
            color: var(--text-main);
        }

        /* ── Middle Row ──────────────────────────────────────────────────── */
        .mid-row {
            display: grid;
            grid-template-columns: 1fr 320px;
            gap: 20px;
            margin-bottom: 28px;
        }

        /* ── Sales Chart Card ────────────────────────────────────────────── */
        .chart-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 24px;
            box-shadow: var(--shadow-sm);
        }
        .card-title-row {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 4px;
        }
        .card-title {
            font-family: 'Playfair Display', serif;
            font-size: 1.1rem;
        }
        .card-subtitle { font-size: .8rem; color: var(--text-muted); }
        .period-badge {
            font-size: .78rem;
            font-weight: 600;
            padding: 6px 12px;
            border: 1px solid var(--border);
            border-radius: 99px;
            color: var(--text-muted);
            cursor: pointer;
        }

        /* SVG chart */
        .chart-wrap { margin-top: 20px; }
        .chart-wrap svg { width: 100%; height: 200px; overflow: visible; }

        /* ── Top Selling Card ────────────────────────────────────────────── */
        .top-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 24px;
            box-shadow: var(--shadow-sm);
        }
        .product-list { margin-top: 16px; display: flex; flex-direction: column; gap: 14px; }
        .product-row {
            display: flex;
            align-items: center;
            gap: 12px;
        }
        .product-img {
            width: 52px; height: 52px;
            border-radius: 8px;
            object-fit: cover;
            background: var(--brand-bg);
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.4rem;
        }
        .product-info { flex: 1; min-width: 0; }
        .product-name { font-size: .88rem; font-weight: 600; line-height: 1.3; }
        .product-sold { font-size: .78rem; color: var(--text-muted); margin-top: 2px; }
        .product-price { font-weight: 700; color: var(--brand); font-size: .95rem; }
        .view-all-link {
            display: block;
            margin-top: 18px;
            text-align: center;
            color: var(--brand);
            font-size: .85rem;
            font-weight: 600;
            text-decoration: none;
        }
        .view-all-link:hover { text-decoration: underline; }

        /* ── Orders Table ────────────────────────────────────────────────── */
        .orders-card {
            background: var(--card-bg);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow-sm);
            overflow: hidden;
        }
        .orders-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 22px 24px 18px;
            border-bottom: 1px solid var(--border);
        }
        .orders-header .card-title { font-size: 1.1rem; }
        .btn-export {
            padding: 9px 18px;
            border: 1px solid var(--border);
            border-radius: 8px;
            background: #fff;
            font-family: 'DM Sans', sans-serif;
            font-size: .83rem;
            font-weight: 600;
            cursor: pointer;
            color: var(--text-main);
            text-decoration: none;
            transition: background .2s, border-color .2s;
        }
        .btn-export:hover { background: var(--brand-bg); border-color: var(--brand); }

        table { width: 100%; border-collapse: collapse; }
        thead th {
            text-align: left;
            font-size: .77rem;
            font-weight: 700;
            letter-spacing: .06em;
            text-transform: uppercase;
            color: var(--text-muted);
            padding: 12px 24px;
            background: var(--brand-bg);
        }
        tbody tr {
            border-top: 1px solid var(--border);
            transition: background .15s;
        }
        tbody tr:hover { background: var(--brand-bg); }
        tbody td { padding: 16px 24px; font-size: .88rem; vertical-align: middle; }

        .order-id { color: var(--brand); font-weight: 600; text-decoration: none; }
        .order-id:hover { text-decoration: underline; }

        .badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 99px;
            font-size: .76rem;
            font-weight: 700;
        }
        .badge-shipped   { background: var(--green-bg);  color: var(--green); }
        .badge-processing{ background: var(--amber-bg);  color: var(--amber); }
        .badge-delivered { background: var(--green-bg);  color: var(--green); }
        .badge-cancelled { background: var(--red-bg);    color: var(--red);   }
        .badge-pending   { background: #EEF2FF;          color: #4F46E5;      }

        .menu-btn {
            background: none;
            border: none;
            cursor: pointer;
            color: var(--text-muted);
            font-size: 1.2rem;
            line-height: 1;
            padding: 4px 8px;
            border-radius: 6px;
            transition: background .2s;
        }
        .menu-btn:hover { background: var(--border); }

        .orders-footer {
            padding: 16px 24px;
            text-align: center;
            border-top: 1px solid var(--border);
        }
        .orders-footer a {
            color: var(--brand);
            text-decoration: none;
            font-size: .85rem;
            font-weight: 600;
        }
        .orders-footer a:hover { text-decoration: underline; }

        /* ── Responsive ──────────────────────────────────────────────────── */
        @media (max-width: 1200px) {
            .stat-grid { grid-template-columns: repeat(2, 1fr); }
            .mid-row   { grid-template-columns: 1fr; }
        }
        @media (max-width: 768px) {
            .sidebar { display: none; }
            .main    { margin-left: 0; }
            .stat-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>

<!-- ════════════════════════════════════════════════════════════
     SIDEBAR
════════════════════════════════════════════════════════════ -->
<aside class="sidebar">
    <div class="sidebar-brand">
        <h1>Artisan Craft</h1>
        <span>Management Suite</span>
    </div>

    <nav>
        <a href="adminDashboard" class="<%= "dashboard".equals(activeNav) ? "active" : "" %>">
            <!-- Dashboard icon -->
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="3" width="7" height="7" rx="1"/>
                <rect x="14" y="3" width="7" height="7" rx="1"/>
                <rect x="3" y="14" width="7" height="7" rx="1"/>
                <rect x="14" y="14" width="7" height="7" rx="1"/>
            </svg>
            Dashboard
        </a>
        <a href="adminOrders">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2"/>
                <rect x="9" y="3" width="6" height="4" rx="1"/>
                <path d="M9 12h6M9 16h4"/>
            </svg>
            Orders
        </a>
        <a href="adminProducts">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
            </svg>
            Products
        </a>
        <a href="adminCustomers">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
                <circle cx="9" cy="7" r="4"/>
                <path d="M23 21v-2a4 4 0 00-3-3.87M16 3.13a4 4 0 010 7.75"/>
            </svg>
            Customers
        </a>
        <a href="adminAnalytics">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/>
            </svg>
            Analytics
        </a>
    </nav>

    <div class="sidebar-btn">
        <a href="adminAddProduct" class="btn-add">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            Add Product
        </a>
    </div>
</aside>

<!-- ════════════════════════════════════════════════════════════
     MAIN
════════════════════════════════════════════════════════════ -->
<div class="main">

    <!-- Top Bar -->
    <header class="topbar">
        <div class="search-wrap">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="11" cy="11" r="8"/><path d="M21 21l-4.35-4.35"/>
            </svg>
            <input type="text" placeholder="Search orders, products…">
        </div>
        <div class="topbar-actions">
            <button class="icon-btn" title="Notifications">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M18 8A6 6 0 006 8c0 7-3 9-3 9h18s-3-2-3-9"/>
                    <path d="M13.73 21a2 2 0 01-3.46 0"/>
                </svg>
            </button>
            <button class="icon-btn" title="Settings">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="3"/>
                    <path d="M19.4 15a1.65 1.65 0 00.33 1.82l.06.06a2 2 0 010 2.83 2 2 0 01-2.83 0l-.06-.06a1.65 1.65 0 00-1.82-.33 1.65 1.65 0 00-1 1.51V21a2 2 0 01-4 0v-.09A1.65 1.65 0 009 19.4a1.65 1.65 0 00-1.82.33l-.06.06a2 2 0 01-2.83-2.83l.06-.06A1.65 1.65 0 004.68 15a1.65 1.65 0 00-1.51-1H3a2 2 0 010-4h.09A1.65 1.65 0 004.6 9a1.65 1.65 0 00-.33-1.82l-.06-.06a2 2 0 012.83-2.83l.06.06A1.65 1.65 0 009 4.68a1.65 1.65 0 001-1.51V3a2 2 0 014 0v.09a1.65 1.65 0 001 1.51 1.65 1.65 0 001.82-.33l.06-.06a2 2 0 012.83 2.83l-.06.06A1.65 1.65 0 0019.4 9a1.65 1.65 0 001.51 1H21a2 2 0 010 4h-.09a1.65 1.65 0 00-1.51 1z"/>
                </svg>
            </button>
            <div class="avatar">A</div>
        </div>
    </header>

    <!-- Page Content -->
    <div class="content">

        <!-- Page Header -->
        <div class="page-header">
            <h2>Overview</h2>
            <p>Detailed performance metrics for your craft boutique.</p>
        </div>

        <!-- ── Stat Cards ─────────────────────────────────────────────── -->
        <div class="stat-grid">

            <!-- Total Revenue -->
            <div class="stat-card">
                <div class="stat-header">
                    <div class="stat-icon">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="2" y="5" width="20" height="14" rx="2"/><path d="M2 10h20"/>
                        </svg>
                    </div>
                    <% if (revenueChange > 0) { %>
                    <span class="stat-badge up">+<%= String.format("%.1f", revenueChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="18 15 12 9 6 15"/></svg>
                    </span>
                    <% } else if (revenueChange < 0) { %>
                    <span class="stat-badge down"><%= String.format("%.1f", revenueChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="6 9 12 15 18 9"/></svg>
                    </span>
                    <% } else { %>
                    <span class="stat-badge neutral">Static</span>
                    <% } %>
                </div>
                <div class="stat-label">Total Revenue</div>
                <div class="stat-value"><%= currencyFmt.format(totalRevenue) %></div>
            </div>

            <!-- Total Orders -->
            <div class="stat-card">
                <div class="stat-header">
                    <div class="stat-icon">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 001.99 1.61h9.72a2 2 0 001.97-1.67L23 6H6"/>
                        </svg>
                    </div>
                    <% if (ordersChange > 0) { %>
                    <span class="stat-badge up">+<%= String.format("%.1f", ordersChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="18 15 12 9 6 15"/></svg>
                    </span>
                    <% } else if (ordersChange < 0) { %>
                    <span class="stat-badge down"><%= String.format("%.1f", ordersChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="6 9 12 15 18 9"/></svg>
                    </span>
                    <% } else { %>
                    <span class="stat-badge neutral">Static</span>
                    <% } %>
                </div>
                <div class="stat-label">Total Orders</div>
                <div class="stat-value"><%= totalOrders %></div>
            </div>

            <!-- New Customers -->
            <div class="stat-card">
                <div class="stat-header">
                    <div class="stat-icon">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M16 21v-2a4 4 0 00-4-4H6a4 4 0 00-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                            <line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/>
                        </svg>
                    </div>
                    <% if (customersChange > 0) { %>
                    <span class="stat-badge up">+<%= String.format("%.1f", customersChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="18 15 12 9 6 15"/></svg>
                    </span>
                    <% } else if (customersChange < 0) { %>
                    <span class="stat-badge down"><%= String.format("%.1f", customersChange) %>%
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><polyline points="6 9 12 15 18 9"/></svg>
                    </span>
                    <% } else { %>
                    <span class="stat-badge neutral">Static</span>
                    <% } %>
                </div>
                <div class="stat-label">New Customers</div>
                <div class="stat-value"><%= newCustomers %></div>
            </div>

            <!-- Active Listings -->
            <div class="stat-card">
                <div class="stat-header">
                    <div class="stat-icon">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M21 16V8a2 2 0 00-1-1.73l-7-4a2 2 0 00-2 0l-7 4A2 2 0 003 8v8a2 2 0 001 1.73l7 4a2 2 0 002 0l7-4A2 2 0 0021 16z"/>
                        </svg>
                    </div>
                    <span class="stat-badge neutral">Static</span>
                </div>
                <div class="stat-label">Active Listings</div>
                <div class="stat-value"><%= activeListings %></div>
            </div>
        </div>

        <!-- ── Middle Row: Chart + Top Selling ───────────────────────── -->
        <div class="mid-row">

            <!-- Sales Growth Chart -->
            <div class="chart-card">
                <div class="card-title-row">
                    <div>
                        <div class="card-title">Sales Growth</div>
                        <div class="card-subtitle">Last 30 days performance</div>
                    </div>
                    <span class="period-badge">Last 30 Days &#9660;</span>
                </div>
                <div class="chart-wrap">
                    <%
                        // Build a simple SVG line chart from weeklySales[4]
                        int chartW = 600, chartH = 180;
                        int maxVal = 1;
                        for (int v : weeklySales) if (v > maxVal) maxVal = v;
                        int pad = 30;
                        int innerW = chartW - pad * 2;
                        int innerH = chartH - pad * 2;

                        double[] px = new double[4];
                        double[] py = new double[4];
                        for (int i = 0; i < 4; i++) {
                            px[i] = pad + i * (innerW / 3.0);
                            py[i] = pad + innerH - (weeklySales[i] * 1.0 / maxVal) * innerH;
                        }

                        // Build smooth path (cubic bezier)
                        StringBuilder pathD = new StringBuilder();
                        pathD.append(String.format("M %.1f %.1f", px[0], py[0]));
                        for (int i = 1; i < 4; i++) {
                            double cpx1 = px[i-1] + (px[i] - px[i-1]) / 2.0;
                            double cpx2 = px[i]   - (px[i] - px[i-1]) / 2.0;
                            pathD.append(String.format(" C %.1f %.1f, %.1f %.1f, %.1f %.1f",
                                cpx1, py[i-1], cpx2, py[i], px[i], py[i]));
                        }

                        // Area fill path
                        StringBuilder areaD = new StringBuilder(pathD.toString());
                        areaD.append(String.format(" L %.1f %.1f L %.1f %.1f Z",
                            px[3], pad + innerH, px[0], pad + innerH));
                    %>
                    <svg viewBox="0 0 600 180" xmlns="http://www.w3.org/2000/svg">
                        <defs>
                            <linearGradient id="areaGrad" x1="0" y1="0" x2="0" y2="1">
                                <stop offset="0%"   stop-color="#8B2A2A" stop-opacity=".15"/>
                                <stop offset="100%" stop-color="#8B2A2A" stop-opacity="0"/>
                            </linearGradient>
                        </defs>
                        <!-- Grid lines -->
                        <% for (int row = 0; row <= 4; row++) { %>
                        <line x1="<%= pad %>" y1="<%= pad + row * (innerH/4) %>"
                              x2="<%= chartW - pad %>" y2="<%= pad + row * (innerH/4) %>"
                              stroke="#EAE4DF" stroke-width="1"/>
                        <% } %>
                        <!-- Area fill -->
                        <path d="<%= areaD %>" fill="url(#areaGrad)"/>
                        <!-- Line -->
                        <path d="<%= pathD %>" fill="none" stroke="#8B2A2A" stroke-width="2.5" stroke-linecap="round"/>
                        <!-- Dots -->
                        <% for (int i = 0; i < 4; i++) { %>
                        <circle cx="<%= String.format("%.1f", px[i]) %>" cy="<%= String.format("%.1f", py[i]) %>"
                                r="4" fill="#8B2A2A" stroke="#fff" stroke-width="2"/>
                        <% } %>
                        <!-- X labels -->
                        <% String[] labels = {"Week 1","Week 2","Week 3","Week 4"};
                           for (int i = 0; i < 4; i++) { %>
                        <text x="<%= String.format("%.1f", px[i]) %>" y="<%= chartH - 4 %>"
                              text-anchor="middle" font-size="11" fill="#7A7A7A" font-family="DM Sans, sans-serif">
                            <%= labels[i] %>
                        </text>
                        <% } %>
                    </svg>
                </div>
            </div>

            <!-- Top Selling -->
            <div class="top-card">
                <div class="card-title">Top Selling</div>
                <div class="product-list">
                    <%
                        if (topProducts != null && !topProducts.isEmpty()) {
                            for (Product p : topProducts) {
                    %>
                    <div class="product-row">
                        <div class="product-img">
                            <% if (p.getImage() != null && !p.getImage().isEmpty()) { %>
                                <img src="images/products/<%= p.getImage() %>"
                                     alt="<%= p.getProductName() %>"
                                     style="width:52px;height:52px;border-radius:8px;object-fit:cover;">
                            <% } else { %>
                                🛍️
                            <% } %>
                        </div>
                        <div class="product-info">
                            <div class="product-name"><%= p.getProductName() %></div>
                            <div class="product-sold"><%= p.getQuantity() %> Sold</div>
                        </div>
                        <div class="product-price"><%= currencyFmt.format(p.getPrice()) %></div>
                    </div>
                    <%
                            }
                        } else {
                    %>
                    <p style="color:var(--text-muted);font-size:.85rem;">No products found.</p>
                    <% } %>
                </div>
                <a href="adminProducts" class="view-all-link">View All Products</a>
            </div>
        </div>

        <!-- ── Recent Orders ──────────────────────────────────────────── -->
        <div class="orders-card">
            <div class="orders-header">
                <div class="card-title">Recent Orders</div>
                <a href="exportOrders" class="btn-export">Export Report</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (recentOrders != null && !recentOrders.isEmpty()) {
                            for (Order o : recentOrders) {
                                String statusLower = o.getStatus() != null ? o.getStatus().toLowerCase() : "";
                                String badgeClass = "badge-pending";
                                if      (statusLower.contains("ship"))     badgeClass = "badge-shipped";
                                else if (statusLower.contains("process"))  badgeClass = "badge-processing";
                                else if (statusLower.contains("deliver"))  badgeClass = "badge-delivered";
                                else if (statusLower.contains("cancel"))   badgeClass = "badge-cancelled";
                    %>
                    <tr>
                        <td>
                            <a href="adminOrderDetail?id=<%= o.getOrderId() %>" class="order-id">
                                #ORD-<%= o.getOrderId() %>
                            </a>
                        </td>
                        <td><%= o.getCustomerName() != null ? o.getCustomerName() : "N/A" %></td>
                        <td><%= o.getOrderDate() != null
                                ? new java.text.SimpleDateFormat("MMM dd, yyyy").format(o.getOrderDate())
                                : "—" %></td>
                        <td><strong><%= currencyFmt.format(o.getTotalAmount()) %></strong></td>
                        <td>
                            <span class="badge <%= badgeClass %>">
                                <%= o.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <button class="menu-btn" title="More options">&#8942;</button>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" style="text-align:center;color:var(--text-muted);padding:32px;">
                            No recent orders found.
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <div class="orders-footer">
                <a href="adminOrders">View All Recent Transactions</a>
            </div>
        </div>

    </div><!-- /content -->
</div><!-- /main -->

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<header id="header"><!--header-->
    <div class="header_top"><!--header_top-->
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="contactinfo">
                        <ul class="nav nav-pills">
                            <li><a href="#"><i class="fa fa-phone"></i> +7 7212 800 800</a></li>
                            <li><a href="#"><i class="fa fa-envelope"></i> info@distributor.com</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="social-icons pull-right">
                        <ul class="nav navbar-nav">
                            <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header_top-->

    <c:set var="clientSigned" value="${client}" scope="session"/>

    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="row">
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">
                            <c:if test="${client != null}">
                            <li><a href="/controller?page=account"><i class="fa fa-user"></i> Account</a></li>
                            <li><a href="/controller?page=checkout"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                            <li><a href="/controller?command=cart"><i class="fa fa-shopping-cart"></i> Cart</a></li>
                            </c:if>
                            <c:choose>
                                <c:when test="${clientSigned != null}">
                                    <li><a href="/controller?command=logout"><i class="fa fa-lock"></i> Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="/controller?page=login"><i class="fa fa-lock"></i> Login</a></li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->

    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul class="nav navbar-nav collapse navbar-collapse">
                            <li><a href="/controller?page=home" class="active">Home</a></li>
                            <li class="dropdown"><a href="#">Shop<i class="fa fa-angle-down"></i></a>
                                <ul role="menu" class="sub-menu">
                                    <li><a href="/controller?page=all-products">Products</a></li>
                                    <c:if test="${client != null}">
                                    <li><a href="/controller?command=orders">My orders</a></li>
                                    <li><a href="/controller?page=checkout">Checkout</a></li>
                                    <li><a href="/controller?command=cart">Cart</a></li>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${clientSigned != null}">
                                            <li><a href="/controller?command=logout">Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="/controller?page=login">Login</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
             </div>
        </div>
    </div><!--/header-bottom-->
</header><!--/header-->

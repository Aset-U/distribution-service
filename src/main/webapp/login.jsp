
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
    <t:head/>
<body>
<t:header/>

<section id="form"><!--form-->
    <div class="container">
        <div class="row">
            <div class="col-sm-4 col-sm-offset-1">
                <div class="login-form"><!--login form-->
                    <h2>Login to your account</h2>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="login" />
                        <input type="text" name="username" placeholder="Username" />
                        <input type="password" name="password" placeholder="Password" />
                        <p style="color:#ff0000">${errorLoginPassMessage}</p>
                        <button type="submit" class="btn btn-default">Login</button>
                    </form>
                </div><!--/login form-->
            </div>
            <div class="col-sm-1">
                <h2 class="or">OR</h2>
            </div>
            <div class="col-sm-4">
                <div class="signup-form"><!--sign up form-->
                    <h2>New User Signup!</h2>
                    <form method="post" action="/controller">
                        <input type="hidden" name="command" value="register" />
                        <input type="text" name="username" placeholder="Username"/>
                        <input type="password" name="password" placeholder="Password"/>
                        <input type="text" name="first_name" placeholder="First name"/>
                        <input type="text" name="last_name" placeholder="Last name"/>
                        <input type="text" name="phone_number" placeholder="Phone number"/>
                        <input type="email" name="email" placeholder="E-mail"/>
                        <button type="submit" class="btn btn-default">Signup</button>
                    </form>
                    <p style="color:#ff0000">${username_error}</p>
                </div><!--/sign up form-->
            </div>
        </div>
    </div>
</section><!--/form-->

        <script src="js/jquery.js"></script>
        <script src="js/price-range.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.prettyPhoto.js"></script>
        <script src="js/main.js"></script>
</body>
</html>

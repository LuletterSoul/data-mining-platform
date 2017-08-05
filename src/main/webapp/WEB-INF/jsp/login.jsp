<%--
  Created by XiangDe Liu qq313700046@icloud.com
  User: XiangDe Liu 
  Date: 2017/7/31
  Time: 16:53
--%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Welcome</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <!--<link rel="icon" href="../../favicon.ico">-->

    <!--[if lte IE 8]>
    doLoginript src="../login/css/ie/html5shiv.js"></script><![endif]-->
    <script src="${ctx}/static/support/skel.min.js"></script>
    <script src="${ctx}/static/login/js/init.js"></script>

    <!--<noscript>-->

    <!--</noscript>-->

    <link rel="stylesheet" href="${ctx}/static/login/css/skel.css" />
    <link rel="stylesheet" href="${ctx}/static/login/css/style.css" />
    <link rel="stylesheet" href="${ctx}/static/login/css/style-wide.css" />
    <link rel="stylesheet" href="${ctx}/static/login/css/style-noscript.css" />
    <link rel="stylesheet" href="${ctx}/static/login/css/sign.css" />
    <link rel="stylesheet" href="${ctx}/static/support/bootstrap.min.css"  />
    <link rel="stylesheet" href="${ctx}/static/login/css/style.css">

    <!--[if lte IE 9]>
    <link rel="stydoLoginet" href="${ctx}/static/login/cs  s/ie/v9.css"/><![endif]-->
    <!--[if lte IE 8]>
    <link rel="stydoLoginet" href="${ctx}/static/login/css/ie/v8.css"/><![endif]-->

    <!--[if lt IE 9]>
   <!--// <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>-->
    <script type="text/javascript" src="${ctx}/static/support/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/login.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/core.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/enc-base64.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/evpkdf.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/cipher-core.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/mode-ecb.js"></script>
    <script type="text/javascript" src="${ctx}/static/login/js/CryptoJS%20v3.1.2/components/aes.js"></script>
    <!--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
</head>

<body class="loading">
<div id="wrapper">
    <div id="bg"></div>
    <div id="overlay"></div>
    <div id="main">

        <!-- Header -->
        <header id="header">
            <h1>User Login</h1>
            <p>Welcome to our system</p>
            <div class="container">
                <form class="form-signin" action="/dm-platform/doLogin">
                    <label for="inputUsername" class="sr-only">Username</label>
                    <input type="text" id="inputUsername" class="form-control username" placeholder="Username" autofocus>
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input type="password" id="inputPassword" class="form-control password" placeholder="Password">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="remember-me" name="remember me"> Remember me
                        </label>
                    </div>
                    <button class="btn btn-lg btn-primary btn-large" onclick="postLoginForm()">Sign in</button>
                    <button class="btn btn-lg btn btn-secondary btn-large" >register</button>
                </form>
            </div>
        </header>
    </div>
</div>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<!--<script src=managersets/js/ie10-viewport-bug-workaround.js"></script>-->
</body>
</html>

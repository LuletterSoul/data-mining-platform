<%--
  Created by XiangDe Liu qq313700046@icloud.com
  User: XiangDe Liu 
  Date: 2017/7/1
  Time: 22:21
--%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<h1>Your Profile</h1>
<c:forEach items="${userList}" var="user">
</c:forEach>
</body>
</html>

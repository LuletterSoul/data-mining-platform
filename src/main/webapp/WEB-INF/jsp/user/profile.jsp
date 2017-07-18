<%--
  Created by XiangDe Liu qq313700046@icloud.com
  User: XiangDe Liu 
  Date: 2017/7/2
  Time: 12:11
--%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<c:out value="${userProfile.userId}"/>
<c:out value="${userProfile.name}"/>
<c:out value="${userProfile.gender}"/>
<c:out value="${userProfile.age}"/>
</body>
</html>

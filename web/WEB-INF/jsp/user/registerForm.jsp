<%--
  Created by XiangDe Liu qq313700046@icloud.com
  User: XiangDe Liu 
  Date: 2017/7/2
  Time: 11:57
--%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf"
           uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css"
      href="<c:url value="/WEB-INF/resources/style.css" />">
<html>
<head>
    <title>Register</title>
</head>
<body>
<sf:form commandName="userRegInfo" method="post">
    <sf:errors path="*" element="div" cssClass="errors"/>

    <sf:label path="userId" cssErrorClass="error">UserId</sf:label>
    <sf:input path="userId" cssErrorClass="error"/>

    <sf:label path="name" cssErrorClass="error">UserName</sf:label>
    <sf:input path="name" cssErrorClass="error"/>

    <sf:label path="gender" cssErrorClass="error">Gender</sf:label>
    <sf:input path="gender" cssErrorClass="error"/>

    <sf:label path="age" cssErrorClass="error">Age</sf:label>
    <sf:input path="age" cssErrorClass="error"/>
    <input type="submit" value="Register"/>
</sf:form>
</body>
</html>

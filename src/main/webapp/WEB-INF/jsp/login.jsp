<%--
  Created by IntelliJ IDEA.
  User: wkp
  Date: 2019/4/2
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/user/login.do" method="post">
    <input type="text" name="userName" required maxlength="24"/>
    <input type="password" name="password" required maxlength="24"/>
    <input type="submit" value="登录">
    <br/>

</form>
</body>
</html>

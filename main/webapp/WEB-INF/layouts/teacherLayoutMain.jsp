<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 13/08/2017
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/include/taglib.jsp" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
<head>
    <title><sitemesh:title/></title>
    <%@ include file="/WEB-INF/include/common.jsp" %>
    <sitemesh:head/>
</head>
<body>
<%@ include file="/WEB-INF/decorators/teacherMenuTop.jsp" %>
<sitemesh:body/>
<%@ include file="/WEB-INF/decorators/footer.jsp" %>
</body>
</html>

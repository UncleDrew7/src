<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 24/08/2017
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<%--template database --%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>


<html lang="en">

<head>
    <title>Admin | Grade OverView </title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
</head>

<body>
<%--TEST DATASOURCE --%>
<sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
                   url = "jdbc:mysql://localhost/ucms"
                   user = "root"  password = "lani"/>
<sql:query dataSource = "${snapshot}" var = "gradeItems">
    SELECT gi.grade_item_name
    FROM grade_items gi,courses crs,grade gr
    WHERE gi.course_id = crs.course_id AND gi.course_id = 2001 AND gi.grade_item_id = gr.grade_item_id
    GROUP BY gi.grade_item_id
    ORDER BY gi.grade_item_position;
</sql:query>
<sql:query dataSource = "${snapshot}" var = "gradeItemsGrades">
    SELECT usr.user_name,usr.user_id,gi.grade_item_name,grd.grade
    FROM grade grd,USER usr,grade_items gi
    WHERE grd.student_id = usr.user_id AND grd.course_id = gi.course_id AND grd.grade_item_id = gi.grade_item_id AND grd.course_id = 2001
    ORDER BY grd.student_id,gi.grade_item_position;
</sql:query>
<sql:query dataSource = "${snapshot}" var = "gradeItemsGradesStudents">
    SELECT usr.user_name, usr.user_id,gi.grade_item_name,grd.grade
    FROM grade grd,USER usr,grade_items gi
    WHERE grd.student_id = usr.user_id AND grd.course_id = gi.course_id AND grd.grade_item_id = gi.grade_item_id AND grd.course_id = 2001
    GROUP BY grd.student_id
</sql:query>
<sql:query dataSource = "${snapshot}" var = "gradeItemWeight">
    SELECT gi.grade_item_id ,gi.grade_item_name,gi.weight,gi.max_grade
    FROM grade_items gi
    WHERE gi.course_id = 2001;
</sql:query>
<sql:query dataSource = "${snapshot}" var = "courseTotal">
    SELECT grd.course_id, grd.student_id ,SUM(grdi.weight * grd.grade)DIV 1 total
    FROM grade grd, grade_items grdi
    WHERE grd.grade_item_id = grdi.grade_item_id
    GROUP BY grd.course_id AND grd.student_id
    ORDER BY GRD.student_id
</sql:query>

<sql:query dataSource = "${snapshot}" var = "weightTotal">
    SELECT  ROUND(SUM(gi.weight), 2)  weight_total
    FROM grade_items gi
    WHERE gi.course_id = 2001;
</sql:query>
<sql:query dataSource = "${snapshot}" var = "userCourseOverview">
    SELECT crs.course_name ,usr.user_name, SUM(grdi.weight * gr.grade)DIV 1 total
    FROM grade gr,courses crs,USER usr,grade_items grdi
    WHERE gr.course_id = crs.course_id AND usr.user_id = gr.student_id AND gr.grade_item_id = grdi.grade_item_id AND usr.user_id = 1001
    GROUP BY  gr.student_id
    ORDER BY usr.user_id
</sql:query>
<sql:query dataSource = "${snapshot}" var = "userGradeItemReport">
    SELECT usr.user_name,grdi.grade_item_name,grd.grade,grdi.min_grade,grdi.max_grade, ROUND((grd.grade/grdi.max_grade)*100 ,2)percentage
    FROM grade grd, grade_items grdi, courses crs,USER usr
    WHERE usr.user_id = 1001 AND crs.course_id = 2001 AND grdi.grade_item_id = grd.grade_item_id AND grd.student_id = usr.user_id AND grd.course_id = crs.course_id
    GROUP BY grdi.grade_item_id
    ORDER BY grdi.grade_item_position;
</sql:query>
<sql:query dataSource = "${snapshot}" var = "courseTotal">
    SELECT SUM(grdi.weight * gr.grade)DIV 1 total
    FROM grade gr,courses crs,USER usr,grade_items grdi
    WHERE gr.course_id = crs.course_id AND usr.user_id = gr.student_id AND gr.grade_item_id = grdi.grade_item_id AND usr.user_id = 1001 AND crs.course_id = 2001
    GROUP BY  gr.student_id
    ORDER BY usr.user_id
</sql:query>



<!--Content Begins  -->
<div class="content">

    <h2>Manage Courses</h2>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <div class="searchingBox">
            <div class="form-group pull-right">
                <div class="dropdown">
                    <button class="btn dropBtn  dropdown-toggle" type="button" data-toggle="dropdown">Manage Grades
                        <span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <li><a data-toggle="pill" href="#gradeItem" class="active">Grade Items </a></li>
                        <li><a data-toggle="pill" href="#gradeReport">Grade Report</a></li>
                        <li><a data-toggle="pill" href="#overviewReport">Overview Report </a></li>
                        <li><a data-toggle="pill" href="#userReport">User Report</a></li>
                    </ul>
                </div>
            </div>


        </div>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




                <div class="tab-content">
                    <div id="gradeItem" class="tab-pane fade">
                        <h3>Grade Items</h3>
                        <div class="divider"></div>
                        <%--!TABLE--%>
                        <div class="table-responsive">
                            <table id="gradeItemTable" class="table table-bordered table-hover">



                                <caption class="">
                                    <span>Showing grade items for Mathe 300932 </span>

                                </caption>


                                <thead>
                                <tr>
                                    <th>Grade Item Name</th>
                                    <th>Weight</th>
                                    <th>Max grade</th>
                                    <th>Action</th>
                                </tr>
                                </thead>

                                <tbody>


                                <c:forEach var="row" items='${gradeItemWeight.rows}' >
                                    <tr>
                                        <td><a href="${url}/admin/view/course"><c:out value="${row.grade_item_name}"/></a></td>
                                        <td><input type="text" class="form-control" id="weight" value="${row.weight}"></td>
                                        <td><c:out value="${row.max_grade}"/></td>
                                        <td>
                                            <div class="dropdown">
                                                <button class="btn btn-danger " type="button" data-toggle="dropdown">Action
                                                    <span class="caret"></span></button>
                                                <ul class="dropdown-menu">
                                                    <li><a href="#">Upload Grades</a></li>
                                                    <li><a href="#">Edit</a></li>
                                                    <li><a href="#">Delete</a></li>
                                                </ul>
                                            </div>

                                        </td>
                                    </tr>
                                </c:forEach>



                                </tbody>

                                <tfoot>
                                <tr>
                                    <th colspan="5" >Weight total : ${weightTotal.rowsByIndex[0][0]}</th>
                                </tr>
                                <tr>
                                <td colspan="5" class="text-center"><a class="courseButton saveBtn"  data-toggle="modal" data-target="#importCourseExcel" href="#">Add Grade Item</a>  <a class="courseButton saveBtn"  data-toggle="modal" data-target="#importCourseExcel" href="#">Save Changes </a></td>
                                </tr>
                                </tfoot>
                            </table>

                        </div>
                        <%--<--END OF TABLE-->--%>
                    </div>
                    <%---------------------------------------------------------------------------------------------------------------------%>
                    <div id="gradeReport" class="tab-pane fade">
                        <h3>Grade Report</h3>
                        <div class="divider"></div>
                        <%--!TABLE--%>
                        <div class="table-responsive">
                            <table id="gradeReprotTable" class="table table-bordered table-hover">



                                <caption class="">
                                    <span>Showing grade items for Mathe 300932 </span>

                                </caption>


                                <thead>
                                <tr>
                                    <th>Student Name</th>
                                    <th>Student Id</th>

                                    <c:forEach var = "row" items="${gradeItems.rows}" varStatus="index">
                                           <th><c:out value="${row.grade_item_name}"/></th>

                                    </c:forEach>

                                </tr>
                                </thead>

                                <tbody>

                                <c:set var="userId" value="00" />
                                <c:forEach var = "row" items="${gradeItemsGrades.rows}">
                                    <c:set var="id" value="0"/>

                                    <c:if test="${userId != row.user_id}">
                                        <c:set var="userId" value="${row.user_id}"/>
                                        <%--prevents from loop from repeating the same name --%>
                                        <tr>
                                            <td><c:out value="${row.user_name}"/></td>
                                            <td><c:out value="${row.user_id}"/></td>
                                            <c:set var="id" value="${row.user_id}"/>
                                            <c:forEach var = "row" items="${gradeItemsGrades.rows}">
                                                <c:if test="${id == row.user_id}">
                                                    <td><c:out value="${row.grade}"/></td>
                                                </c:if>
                                            </c:forEach>
                                        </tr>

                                    </c:if>

                                </c:forEach>


                                </tbody>

                                <tfoot>
                                <tr>
                                    <th colspan="5" >Weight total : 1</th>
                                </tr>
                                <tr>
                                    <td colspan="5" class="text-center"><a class="courseButton saveBtn"  data-toggle="modal" data-target="#importCourseExcel" href="#">Add Grade Item</a>  <a class="courseButton saveBtn"  data-toggle="modal" data-target="#importCourseExcel" href="#">Save Changes </a></td>
                                </tr>
                                </tfoot>
                            </table>
                    </div>

                </div>
                    <%----------------------------------------------------------------%>
                    <div id="overviewReport" class="tab-pane fade">
                        <h3>Overview Report</h3>
                        <div class="divider"></div>
                        <%--!TABLE--%>
                        <div class="table-responsive">
                            <table id="overviewReportTable" class="table table-bordered table-hover">



                                <caption class="">
                                    <span>Overview report for - Joanna Trollope </span>

                                </caption>


                                <thead>
                                <tr>

                                    <th>Course Name </th>
                                    <th>Grade</th>
                                </tr>
                                </thead>

                                <tbody>


                                <c:forEach var="row" items='${userCourseOverview.rows}' >
                                    <tr>
                                        <td><a href="${url}/admin/view/course"><c:out value="${row.course_name}"/></a></td>
                                        <td><c:out value="${row.total}"/></td>
                                    </tr>
                                </c:forEach>

                                </tbody>

                                <tfoot>

                                <tr>
                                    <td colspan="5" class="text-center"> <span>Overview report for - Joanna Trollope </span></td>
                                </tr>
                                </tfoot>
                            </table>

                        </div>
                        <%--<--END OF TABLE-->--%>
                    </div>
                    <%-----------------------------------------------------------------------------%>
                    <div id="userReport" class="tab-pane fade in active">
                        <h3>User Report</h3>
                        <%--!TABLE--%>
                        <div class="table-responsive">
                            <table id="userReportTable" class="table table-bordered table-hover">



                                <caption class="">
                                    <span>Overview report for - Joanna Trollope </span>

                                </caption>


                                <thead>
                                <tr>

                                    <th>Grade item</th>
                                    <th>Grade</th>
                                    <th>Range</th>
                                    <th>Percentage</th>
                                </tr>
                                </thead>

                                <tbody>


                                <c:forEach var="row" items='${userGradeItemReport.rows}' >
                                    <tr>
                                        <td><c:out value="${row.grade_item_name}"/></td>
                                        <td><c:out value="${row.grade}"/></td>
                                        <td><c:out value="${row.min_grade}"/> - <c:out value="${row.max_grade}"/></td>
                                        <td><c:out value="${row.percentage}"/>%</td>
                                    </tr>
                                </c:forEach>

                                </tbody>

                                <tfoot>
                                <tr>
                                    <th colspan="5" >Course total : ${courseTotal.rowsByIndex[0][0]}</th>
                                </tr>
                                <tr>
                                    <td colspan="5" class="text-center"> <span>Overview report for - Joanna Trollope </span></td>
                                </tr>
                                </tfoot>
                            </table>

                        </div>
                        <%--<--END OF TABLE-->--%>
                    </div>




        </div>




    </div>
    <%--/END OF MAIN CARD CONTAINER --%>


</div>
<!--Content Ends  -->
</div>

</body>

</html>

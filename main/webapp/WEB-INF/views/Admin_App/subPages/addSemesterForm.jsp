<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/09/2017
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/09/2017
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="addSemesterForm.addSemester"/> </title>
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
</head>

<body>
<%--TEST DATASOURCE --%>


<!--Content Begins  -->
<div class="content">
    <c:choose>
        <c:when test="${action eq 'update'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="addSemesterForm.manageSemester"/></a></li>
                    <li class="last active"><a ><spring:message code="addSemesterForm.editSemester"/></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="addSemesterForm.manageSemester"/></a></li>
                    <li class="last active"><a ><spring:message code="addSemesterForm.addSemester"/></a></li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">



            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <%--FORM--%>
                <%--<h3>Basic</h3>--%>

                <c:choose>
                    <c:when test="${action eq 'update'}">
                        <div class="formTitleBox">
                            <h1><spring:message code="addSemesterForm.editSemester"/></h1>
                        </div>

                        <form id="editSemesterForm" class="form-horizontal"  method="post">


                                <%--FORM HEADER--%>
                            <h3><spring:message code="addSemesterForm.information"/></h3>
                                <%--FORM BOX 1/--%>
                            <div class="formBox">

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="semesterCode"><spring:message code="addSemesterForm.semesterCode"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" value="${singleSemester.semesterCode}" autocomplete="off" placeholder='<spring:message code="placeHolder.asf1"/>' name="semesterCode" required>
                                    </div>
                                </div>



                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="startDate"><spring:message code="semesterForm.startDate"/></label>
                                    <div class="col-sm-9">
                                        <input autocomplete="off"  value="${singleSemester.startDate}" type="text" class="form-control datepicker"  placeholder='<spring:message code="placeHolder.enterSemesterStartdate"/>' name="startDate" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="endDate"><spring:message code="courseForm.endDate"/></label>
                                    <div class="col-sm-9">
                                        <input autocomplete="off" type="text"  value="${singleSemester.endDate}" class="form-control datepicker"  placeholder='<spring:message code="placeHolder.enterEndDate"/>' name="endDate" >
                                    </div>
                                </div>

                            </div>
                                <%--FORM BOX1--%>
                                <%--FORM DIVIDER--%>
                            <div class="formDivider"></div>
                                <%--FORM DIVIDER--%>


                            <div class="form-group">
                                <div class="submitBtnBox">
                                    <button  type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
                                </div>
                            </div>

                        </form>
                        <%--/FORM--%>
                    </c:when>

                    <c:otherwise>
                        <div class="formTitleBox">
                            <h1><spring:message code="addSemesterForm.createNewSemester"/></h1>
                        </div>

                        <form id="addSemesterForm" class="form-horizontal"  method="post">


                                <%--FORM HEADER--%>
                                    <h3><spring:message code="addSemesterForm.information"/></h3>
                                <%--FORM BOX 1/--%>
                            <div class="formBox">

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="semesterCode"><spring:message code="semesterCourseList.semesterCode"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="semesterCode" autocomplete="off" placeholder='<spring:message code="placeHolder.asf1"/>' name="semesterCode" required>
                                    </div>
                                </div>



                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="startDate"><spring:message code="semesterForm.startDate"/></label>
                                    <div class="col-sm-9">
                                        <input autocomplete="off" type="text" class="form-control datepicker" id="startDate" placeholder='<spring:message code="placeHolder.enterSemesterStartdate"/>' name="startDate" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="endDate"><spring:message code="courseForm.endDate"/></label>
                                    <div class="col-sm-9">
                                        <input autocomplete="off" type="text" class="form-control datepicker" id="endDate" placeholder='<spring:message code="placeHolder.enterEndDate"/>' name="endDate" >
                                    </div>
                                </div>

                            </div>
                                <%--FORM BOX1--%>
                                <%--FORM DIVIDER--%>
                            <div class="formDivider"></div>
                                <%--FORM DIVIDER--%>


                            <div class="form-group">
                                <div class="submitBtnBox">
                                    <button id="submit" type="submit" class="btn btn-default"><spring:message code="semesterForm.createSemester"/></button>
                                </div>
                            </div>

                        </form>
                        <%--/FORM--%>
                    </c:otherwise>
                </c:choose>


            </div>
            <%--BIG CARD CONTAINER BOX--%>

        </div>
        <!--BIG CONTAINER -->






        <!--Small cards-->
        <div class="col-sm-3 smallCardContainerParent">

            <div class="card bigCardContainer ">

                <c:choose>
                    <c:when test="${action eq 'update'}">
                        <h1><spring:message code="semesterForm.lastEditedSemester"/></h1>

                        <div class="table-responsive">
                            <table class="table table-condensed">
                                <c:forEach var = "lastUpdatedList" items="${lastUpdatedList}">
                                    <tr>
                                        <td ><a style="color: inherit;text-decoration: none" href="#" data-toggle="tooltip" title="Updated on ${lastUpdatedList.updatedAt}"><b><c:out value="${lastUpdatedList.semesterCode}"/></b></a></td>
                                        <td >(<i><c:out value="${lastUpdatedList.startDate}"/></i>)</td>

                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h1><spring:message code="semesterForm.lastAddedSemester"/></h1>

                        <div class="table-responsive">
                            <table class="table table-condensed">
                                <c:forEach var = "lastAddedList" items="${lastAddList}">
                                    <tr>
                                        <td><a style="color: inherit;text-decoration: none" href="#" data-toggle="tooltip" title="Updated on ${lastAddedList.updatedAt}"><b><c:out value="${lastAddedList.semesterCode}"/></b></a></td>
                                        <td ><i><c:out value="${lastAddedList.startDate}"/></i></td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>



            </div>

        </div>
        <!--Small cards-->







    </div>
    <!--MAIN CARD CONTAINER -->


</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {

        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        });

    });

    $('#addSemesterForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/semester/add',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                var r = confirm('<spring:message code="addSemesterForm.msg1"/>');
                if (r == true) {
                    location.reload(true);
                } else{
                    window.location.href = '${url}/admin/manage-courses/courses';
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $('#editSemesterForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/semester/update/${semesterId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert('<spring:message code="addSemesterForm.msg2"/>')
                location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });
</script>

</body>

</html>

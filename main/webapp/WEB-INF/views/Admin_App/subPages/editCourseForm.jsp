<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 28/09/2017
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 15/08/2017
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<%--template database --%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>


<html lang="en">

<head>
    <title><spring:message code="editCourseForm.editCourse"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>

    <link href="${url}/resources/main/summernote/summernote.css" rel="stylesheet">
    <script src="${url}/resources/main/summernote/summernote.js"></script>

</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>
    <c:choose>
        <c:when test="${nav eq 1}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
                <li class="last active"><a ><spring:message code="coursePage.editCourse" /></a></li>
            </ul>
        </div
        </c:when>
        <c:when test="${nav eq 2}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/view/course/${childCourseId}" class="icon-home"><spring:message code="editCourseForm.coursePage"/></a></li>
                    <li class="last active"><a ><spring:message code="coursePage.editCourse" /></a></li>
                </ul>
            </div
        </c:when>
            <c:otherwise>


        </c:otherwise>
    </c:choose>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">




            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="coursePage.editCourse" /></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="editCourseForm" class="form-horizontal" >


                    <%--FORM HEADER--%>
                    <h3><spring:message code="studentForm.basic" /></h3>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group imgGroup">
                            <label class="control-label col-sm-3" for="courseImg"></label>
                            <div class="col-sm-9">
                                <div class="imageBox" id="courseImg">

                                    <img src="${url}/resources/student_app_static/images/tempimg.jpg">
                                    <%--<input type='file' id="file" class="uploadButton" accept="image/*" />--%>

                                    <div id="uploadedImg" class="uploadedImg">
                                        <span class="unveil"></span>
                                    </div>


                                </div>
                                <%--<a href="#courseImg">Upload course Default Image </a>--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseId"><spring:message code="manageCourse.courseId" /> </label>
                            <div class="col-sm-9">
                                <input type="text" disabled value="${currentCourseDetails.childCourseId}" autocomplete="off" class="form-control" id="courseId"  name="courseId" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseName"><spring:message code="manageCourse.courseName" /> </label>
                            <div class="col-sm-9">
                                <input type="text"  value="${currentCourseDetails.courseName}" autocomplete="off" class="form-control" disabled id="courseName"name="courseName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseSName"><spring:message code="extra.courseShortName" /> </label>
                            <div class="col-sm-9">
                                <input type="text"  value="${currentCourseDetails.courseShortName}" autocomplete="off" class="form-control" disabled id="courseSName"  name="courseShortName" required>
                            </div>
                        </div>


                            <div style="<c:if test="${currentUserRole ne 'admin'}">display: none </c:if>" class="form-group">
                                <label class="control-label col-sm-3" for="teacher"><spring:message code="manageCourse.teacher" /></label>
                                <div class="col-sm-9">
                                    <select class="form-control" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="teacher" name="teacherId" required>
                                        <option value="${currentCourseDetails.teacherId}">${currentCourseDetails.teacherName}</option>
                                        <c:forEach var = "tList" items="${teachersList}">
                                            <c:if test="${currentCourseDetails.teacherName ne tList.userName}">
                                                <option value="${tList.userId}"><c:out value="${tList.userName}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                       <%-- <c:if test="${currentUserRole ne 'admin'}">
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="teacher"><spring:message code="manageCourse.teacher" /></label>
                                <div class="col-sm-9">
                                    <select  disabled class="form-control" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="teacher" name="teacherId" required>
                                        <option value="${currentCourseDetails.teacherId}">${currentCourseDetails.teacherName}</option>
                                    </select>
                                </div>
                            </div>
                        </c:if>--%>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="category"><spring:message code="semesterForm.semesterCode" /></label>
                            <div class="col-sm-9">
                                <select class="form-control" id="category" name="category" required>
                                    <option value="${currentCourseDetails.semseterId}">${currentCourseDetails.semesterCode}</option>
                                    <c:forEach var = "sList" items="${semesterList}">
                                        <%--<c:if test="${currentCourseDetails.teacherName ne sList.userName}">--%>
                                            <option value="${sList.semesterId}"><c:out value="${sList.semesterCode}"/></option>
                                        <%--</c:if>--%>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>


                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>
                    <h3><spring:message code="courseForm.courseDetails" /> </h3>
                    <%--FORM BOX 2/--%>
                    <div class="formBox">
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="startDate"><spring:message code="addClearanceExamForm.enrolmentStartDate"/></label>
                            <div class="col-sm-9">
                                <input type="text" value="${currentCourseDetails.startDate}"  autocomplete="off" class="form-control datepicker" id="startDate" name="startDate" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="endDate"><spring:message code="manageCourse2.enrolmentDeadline"/></label>
                            <div class="col-sm-9">
                                <input type="text" value="${currentCourseDetails.enrollmentDeadline}"  autocomplete="off" class="form-control datepicker" id="endDate" name="deadline" required>
                            </div>
                        </div>

                        <%--<div class="form-group">--%>
                            <%--<label class="control-label col-sm-3" for="endDate"><spring:message code="courseForm.endDate" /> </label>--%>
                            <%--<div class="col-sm-9">--%>
                                    <%--<input type="text"  value="${currentCourseDetails.endDate}" autocomplete="off" class="form-control datepicker" id="endDate" name="endDate" required>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="form-group">
                            <label class="control-label col-sm-3"><spring:message code="courseForm.description" /></label>
                            <div class="col-sm-9">
                                <textarea  readonly class="form-control"  autocomplete="off" rows="5"  name="courseDescription" required>${currentCourseDetails.courseDescriptionEn}</textarea>
                            </div>
                        </div>


                    </div>

                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class=" submitBtnBox">
                            <button <c:if test="${!hasPermission and currentUserRole ne 'admin' }">disabled</c:if>  style="color: inherit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges" /></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>


            </div>
            <%--BIG CARD CONTAINER BOX--%>




        </div>
        <!--BIG CONTAINER -->






        <!--Small cards-->
        <div class="col-sm-3 smallCardContainerParent">
            <%--PARENT CARD--%>
            <div class="card bigCardContainer ">

                <div class="formTitleBox settingsTitleBox">
                    <h1><spring:message code="studentForm.settings" /></h1>
                </div>

                <form id="courseSettingsForm">

                    <div class="form-group">
                        <label class="control-label " for="degreeType"><spring:message code="studentForm.degreeType" /></label>
                        <div>
                            <select class="form-control" id="degreeType" name="degreeType">
                                <option value="${currentCourseDetails.courseType}">${currentCourseDetails.courseType}</option>
                                <%--<option><spring:message code="extra.doubleDegree" /></option>--%>
                                <%--<option><spring:message code="extra.genralDegree" /></option>--%>
                                <%--<option><spring:message code="extra.all" /></option>--%>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label " for="contentLanguage"><spring:message code="courseForm.contentLanguage" /></label>
                        <div >
                            <select class="form-control" id="contentLanguage" name="contentLanguage">
                                <option value="${currentCourseDetails.contentLanguage}">${currentCourseDetails.contentLanguage}</option>
                                <c:if test="${currentCourseDetails.contentLanguage ne 'english'}">
                                    <option><spring:message code="editCourseForm.english"/></option>
                                </c:if>
                                <c:if test="${currentCourseDetails.contentLanguage ne 'chinese'}">
                                    <option><spring:message code="editCourseForm.chinese"/></option>
                                </c:if>

                            </select>
                        </div>
                    </div>

                    <%--<div class="form-group">--%>
                        <%--<label class="control-label " for="courseStatus"><spring:message code="courseForm.courseStatus" /></label>--%>
                        <%--<div >--%>
                            <%--<select class="form-control" id="courseStatus" name="courseStatus">--%>
                                <%--<option value="${currentCourseDetails.status}">${currentCourseDetails.status}</option>--%>
                                <%--<option>Active</option>--%>
                                <%--<option>Not Active</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  style="color: inherit" type="submit" class="btn btn-default"><spring:message code="studentForm.saveSettings" /></button>
                        </div>
                    </div>

                </form>




            </div>
            <%--PARENT CARD--%>

            <div class="card bigCardContainer ">

                <h1><spring:message code="courseForm.lastEditedCourses" /></h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "lastEdited" items="${lastEditedCourses}">
                            <tr>
                                <td ><c:out value="${lastEdited.courseId}"/></td>
                                <td ><c:out value="${lastEdited.courseName}"/></td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>

            </div>


        </div>
        <!--Small cards-->







    </div>
    <!--MAIN CARD CONTAINER -->




</div>
<!--Content Ends  -->
<script>
    $(document).ready(function() {
        $('#summernote').summernote({
            minHeight: 200,
            maxHeight:200,
            placeholder: '<spring:message code="editCourseForm.enterDescription"/>',
            value:'#####'
        });

        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        });
    });



    $('#editCourseForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-course/update-course-details/${childCourseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    alert('<spring:message code="addCourseForm.msg2" />');
                }else{
                    alert('<spring:message code="addCourseForm.msg4" />');
                }
                location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $('#courseSettingsForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-course/update-course-settings/${childCourseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert('<spring:message code="addCourseForm.msg2" />');
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

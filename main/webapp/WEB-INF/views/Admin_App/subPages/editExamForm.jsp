<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 13/10/2017
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 13/10/2017
  Time: 12:24
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




<html lang="en">

<head>
    <title>Admin| Edit Exam</title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>


</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>

    <c:choose>
        <c:when test="${param.nv eq 'ce'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/course-exam/${examData.parentCourseName}" class="icon-home"><spring:message code="courseExamPage.text1"/></a></li>
                    <li class="last active"><a ><spring:message code="courseExams.editExam"/></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-exams" class="icon-home"><spring:message code="manageExams.manageExams"/></a></li>
                    <li class="last active"><a ><spring:message code="courseExams.editExam"/></a></li>
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

                <div class="formTitleBox">
                    <h1>Edit exam</h1>
                </div>


                <form id="examForm" class="form-horizontal" >


                    <%--FORM HEADER--%>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examCode"><spring:message code="examForm.examCode"/></label>
                            <div class="col-sm-9">
                                <input disabled type="text"  value="${examData.id}" class="form-control" id="examCode" name="examCode" required >
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseName"><spring:message code="main.course"/></label>
                            <div class="col-sm-9">
                                <select  disabled onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="courseName" name="courseId" required>
                                    <option value="${examData.parentCourseId}">${examData.parentCourseShortName}</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="exName"><spring:message code="enrollmentList.ExamName"/></label>
                            <div class="col-sm-9">
                                <input type="text" value="${examData.examName}" class="form-control" id="exName" autocomplete="off" placeholder='<spring:message code="addClearanceExamForm.enterExamName"/>' name="exName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examDate"><spring:message code="manageExams2.dateOfExam"/></label>
                            <div class="col-sm-9">
                                <input value="${examData.dateOfExam}" autocomplete="off" type="text" class="form-control datepicker" id="examDate" name="examDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentStartDate"><spring:message code="examForm.enrolmentStartDate"/></label>
                            <div class="col-sm-9">
                                <input value="${examData.enrolmentStartDate}" autocomplete="off" type="text" class="form-control datepicker"  id="enrolmentStartDate" name="enrolmentStartDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentEndDate"><spring:message code="examForm.enrolmentEndDate"/></label>
                            <div class="col-sm-9">
                                <input value="${examData.enrolmentCloseDate}" autocomplete="off" type="text" class="form-control datepicker" id="enrolmentEndDate"  name="enrolmentEndDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="minGrade"><spring:message code="addExamForm.minGrade"/></label>
                            <div class="col-sm-9">
                                <input type="text"  value="${examData.minGrade}" id="minGrade" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterMinGrade"/>' name="minGrade" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="maxGrade"><spring:message code="addExamForm.maxGrade"/></label>
                            <div class="col-sm-9">
                                <input type="text" value="${examData.maxGrade}" id="maxGrade" class="form-control" name="maxGrade" autocomplete="off" placeholder='<spring:message code="gradeItems.enterMaxGrade"/>' required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="exWeight"><spring:message code="gradeItems.weight"/></label>
                            <div class="col-sm-9">
                                <input type="text" id="exWeight" value="${examData.weight}" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterWeight"/>' name="exWeight" required>
                            </div>
                        </div>



                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  style="color: inherit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
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


            <div class="card bigCardContainer ">

                <h1><spring:message code="editClearanceExamForm.lastEditedExams"/></h1>


                <div class="table-responsive">
                    <table width="100%" class="table table-condensed">
                        <c:forEach var = "eList" items="${lastEditedList}">
                            <tr >
                                <td ><c:out value="${eList.examName}"/></td>
                          <%--      <td ><c:out value="${eList.parentCourseShortName}"/></td>--%>
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

    });

    $( ".datepicker" ).datepicker({
        dateFormat: 'yy-mm-dd'
    });

    $('#examForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-exams/edit/${examId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    alert('<spring:message code="editExamForm.msg1" />');
                }else{
                    alert('<spring:message code="editExamForm.msg2" />');
                }

                $('#confirmModal').modal('toggle');
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });
</script>

</body>

</html>


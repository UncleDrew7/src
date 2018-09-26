<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 20/08/2017
  Time: 20:54
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
    <title><spring:message code="courseExamPage.text1"/></title>
    <link href="${url}/resources/student_app_static/css/my_course.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <link href="${url}/resources/app_admin_static/css/subPages/courseExamPage.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
</head>

<body>


<div>

    <!-- Tab panes -->
    <div class="content">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/view/course/${courseId}" class="icon-home"><c:out value="${courseNames.courseShortName}"/></a></li>
                <li class="last active"><a ><spring:message code="courseExamPage.text1"/></a></li>
            </ul>
        </div>
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="row mainContainer">

                <!--Small cards-->
                <div class="col-sm-3">
                    <%--<div class="smallCardContainer ">--%>
                        <%--<div>--%>
                            <%--<h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.filterCoures" /></h4>--%>
                        <%--</div>--%>
                        <%--<div>--%>
                            <%--<ul class="courseFilterList">--%>
                                <%--<li>--%>
                                    <%--<div class="courseCategoryItem">--%>
                                        <%--<a href="#courseCategory" class="courseOthersText">--%>
                                            <%--Exam Date--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<div class="courseCategoryItem">--%>
                                        <%--<a href="#courseCategory" class="courseOthersText">--%>
                                            <%--Exam Name--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<div class="courseCategoryItem">--%>
                                        <%--<a href="#courseCategory" class="courseOthersText">--%>
                                            <%--Enrolled Students--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<div class="courseCategoryItem">--%>
                                        <%--<a href="#courseCategory" class="courseOthersText">--%>
                                            <%--&lt;%&ndash;<spring:message code="studentPageCourseLables.allCourses" />&ndash;%&gt;--%>
                                            <%--Created By--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>



                </div>
                <!--Small cards-->
                <%-------------------------------%>

                <div class="col-sm-7 ">

                    <!--Coures list Begins-->
                    <ul class="courseList list-group">
                        <h3 style="margin-top: -5px; margin-bottom: 25px; font-weight: 300">${courseNames.courseName} : <spring:message code="coursePage.exams"/></h3>
                        <div  class="formDivider"></div>
                        <c:if test="${empty examList}"><div style="text-align: center"><i><spring:message code="courseExamPage.msg1"/> </i></div></c:if>
                        <c:forEach var = "eList" items="${examList}">
                            <c:if test="${eList.examType eq 'parentExam' or eList.examType eq 'childExam'}">
                                <!--List Item-->
                                <li class="list-group-item listCard">
                                    <!-- course box content-->
                                    <div class="courseCard">
                                        <div class="row">
                                            <!--intro names -->
                                            <div class="col-sm-4 ">
                                                <div>
                                                    <div class="courseTextStyle" >
                                                        <h4 style="margin-top: -5px"><c:out value="${eList.examName}"/></h4>
                                                    </div>
                                                    <div class="courseTextStyle" >
                                                        <span class="courseOthersText"><spring:message code="courseExamPage.examType"/>: <c:out value="${eList.examType}"/></span>
                                                    </div>
                                                    <div class="courseTextStyle" >
                                                        <span class="courseOthersText"><spring:message code="studentHome.examDate"/>: <c:out value="${eList.examDate}"/></span>
                                                    </div>
                                                    <div class="courseTextStyle" >
                                                        <span class="courseOthersText"><spring:message code="courseExamPage.deadline"/>:<c:out value="${eList.examEnrollmentDeadline}"/></span>
                                                    </div>




                                                </div>

                                            </div>

                                            <!--course name -->
                                            <div class="col-sm-5 courseCardBox3" >

                                                <div style="margin-top: -15px" class="courseNameTextBox">
                                                    <span> <a class="courseOthersText" href="${url}/admin/manage-exams/enrolmentList/${eList.parentExamId}/3?nv=ce"><spring:message code="courseExamPage.enrolledStudents"/></a>: <span style="margin-bottom: 5px" class="badge"><c:out value="${eList.totalEnrolledStudents}"/></span></span><br/>
                                                    <span style="float: right">
                                                        <c:if test="${eList.isExamActive eq true}"><span  class="label label-success"><spring:message code="courseExams.activeExam"/> </span></c:if>
                                                        <c:if test="${eList.isExamActive eq false}"><span  class="label label-danger"><spring:message code="courseExams.notActiveExam"/> </span></c:if>
                                                    </span>
                                                </div>
                                            </div>

                                            <div class="col-sm-3 courseCardBox3" >

                                                <!--course buttons-->
                                                <div class="enrollmentBox">

                                                        <%--<div class="unEnrollTextBox">--%>
                                                        <%--<a class="courseButton" href="subPages/course_details.jsp">Edit Exam</a>--%>
                                                        <%--</div>--%>

                                                        <%--<div class="unEnrollTextBox">--%>
                                                        <%--<a class="courseButton" href="subPages/course_details.jsp">Add Grades</a>--%>
                                                        <%--</div>--%>

                                                    <div class="btn-group-vertical" role="group" aria-label="...">
                                                        <button onclick="enroll(${eList.parentExamId})" <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  style="font-size: 13px; color:inherit; width: 150px" type="button" class="btn <c:if test="${hasPermission or currentUserRole eq 'admin'}">courseButton</c:if> btn-default"><spring:message code="courseExams.enroll"/></button>
                                                        <button onclick="addGrade(${eList.parentExamId},1)" <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>   style="font-size: 13px;color:inherit; width: 150px" type="button" class="btn <c:if test="${hasPermission or currentUserRole eq 'admin'}">courseButton</c:if> btn-default"><spring:message code="addGrades.addGrade"/></button>
                                                        <button onclick="createClearanceExam(${eList.parentExamId})" <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>   style="font-size: 13px;color:inherit; width: 150px" type="button" class="btn <c:if test="${hasPermission or currentUserRole eq 'admin'}">courseButton</c:if> btn-default"><spring:message code="manageExams2.addChildExam"/></button>
                                                            <%--<button onclick="editItem(${eList.parentExamId})" <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>   style="font-size: 13px;color:inherit; width: 150px" type="button" class="btn <c:if test="${hasPermission or currentUserRole eq 'admin'}">courseButton</c:if> btn-default">Edit Exam</button>--%>
                                                    </div>
                                                    <div>
                                                        <a style="margin-left:50px;" href="#unenroll" onclick="editItem(${eList.parentExamId})" class="unEnrollText"><spring:message code="eEnrolmentList.editExam"/></a>
                                                    </div>
                                                        <%--<c:if test="${hasPermission or currentUserRole eq 'admin'}">--%>
                                                        <%--<a style="margin-left:20px;" href="#unenroll" class="unEnrollText">Cancel Exam</a>--%>
                                                        <%--</c:if>--%>

                                                </div>
                                                <br/>


                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <!--List Item-->
                            </c:if>
                        </c:forEach>

                    </ul>
                    <br/><br/><br/>

                    <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                        <div id="add" class="sectionAddBox">
                            <span class="inIconAni glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <div><spring:message code="examForm.createNewExam" /></div>

                        </div>
                    </c:if>
                    <!--Coures list Ends-->
                </div>

                <div class="col-sm-2"></div>


            </div>


        </div>


    </div>

</div>



<%--<!-- Modal -->--%>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div class="formTitleBox">
                    <h1><spring:message code="examForm.createNewExam"/></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="addGradeItemForm" class="form-horizontal"  method="post">

                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="gradeItem"><spring:message code="gradeReport.gradeItem"/></label>
                            <div class="col-sm-9">
                                <input type="text" value="Exam" readonly class="form-control" id="gradeItem" autocomplete="off"  name="gradeItemType" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="giName"><spring:message code="enrollmentList.ExamName"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="giName" autocomplete="off" placeholder='<spring:message code="addClearanceExamForm.enterExamName"/>' name="giName" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="giDate"><spring:message code="examForm.dateOfExam"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="datepicker form-control"  id="giDate" autocomplete="off"  placeholder='<spring:message code="examForm.dateOfExamPlaceholder"/>' name="giDate" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="giStartDate"><spring:message code="examForm.enrolmentStartDate"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="datepicker form-control" id="giStartDate" autocomplete="off"  placeholder='<spring:message code="addClearanceExamForm.enterExamEnrolmentStartDate"/>' name="giStartDate" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="giEndDate"><spring:message code="examForm.enrolmentEndDate"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="datepicker form-control" id="giEndDate" autocomplete="off" placeholder='<spring:message code="addExamForm.enterExamEnrolmentDeadlineDate"/>' name="giEndDate" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="minGrade"><spring:message code="addExamForm.minGrade"/></label>
                            <div class="col-sm-9">
                                <input readonly type="text"  value="0" id="minGrade" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterMinGrade"/>' name="minGrade" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="maxGrade"><spring:message code="addExamForm.maxGrade"/></label>
                            <div class="col-sm-9">
                                <input readonly type="text" value="100" id="maxGrade" class="form-control" name="maxGrade" autocomplete="off" placeholder='<spring:message code="gradeItems.enterMaxGrade"/>' required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="giWeight"><spring:message code="gradeItems.weight"/></label>
                            <div class="col-sm-9">
                                <input readonly type="text" id="giWeight" value="0" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterWeight"/>' name="giWeight" required>
                            </div>
                        </div>

                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button type="submit" class="btn btn-default"><spring:message code="event.createEvent" /></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>
            </div>
        </div>
    </div>
</div>


<script>

    $( ".datepicker" ).datepicker({
        dateFormat: 'yy-mm-dd'
    });


    function enroll(itemId){
        window.location.href='${url}/admin/manage-exam/enroll/${courseId}/'+itemId+'/3?nv=ce';
    }

    function addGrade(itemId,type) {
        window.location.href='${url}/admin/grades/add-grades/${courseId}/'+itemId+'/'+type+'?nv=ce';
    }

    function editItem(itemId) {
        window.location.href='${url}/admin/manage-exams/edit/'+itemId+'?nv=ce';
    }

    function createClearanceExam(itemId) {
        window.location.href='${url}/admin/manage-exams/exam/add-clearance-exam/${courseId}/'+itemId+'?nv=ce';
    }

    $('#add').click(function(){
        $('#myModal').modal('toggle');
        <%--window.location.href='${url}/admin/course/content/add/${childCourseId}';--%>

    });

    $('#addGradeItemForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-gradeItems/add-grade-item/${courseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    alert('<spring:message code="addExamForm.msgs3" />');
                    location.reload(true);
                }else{
                    alert('<spring:message code="addExamForm.msg1" />');
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });
</script>



</body>

</html>
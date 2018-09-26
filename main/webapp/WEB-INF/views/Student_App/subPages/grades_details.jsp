<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="studentGrades_details.studentFullReport"/> </title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <div class="wrapper">
        <ul class="breadcrumbs">
            <li class=""><a href="${url}/student/grades" class="icon-home"><spring:message code="gradeReport.gradeReportT2" /></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a ><spring:message code="coursePage.gradeReport" /></a></li>
        </ul>
    </div>c

    <%--<h2 class="pageTitle">Grade Report</h2>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <%--<div class="searchingBox">--%>
            <%--&lt;%&ndash;<div class="form-group pull-right">&ndash;%&gt;--%>

            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>


        <%--</div>--%>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">


                <%----------------------------------------------------------------%>

                <div id="userReport" class="tab-pane fade in active">
                    <br/>
                    <h3><c:out value="${courseName.courseName}"/> <spring:message code="extra.gradeReportFor" /> - ${userName}</h3><br/>
                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="userReportTable" class="table table-bordered table-hover">



                            <thead>
                            <tr>
                                <th><spring:message code="extra.type" /></th>
                                <th><spring:message code="gradeReport.gradeItem" /></th>
                                <th><spring:message code="gradeReport.grade" /></th>
                                <th><spring:message code="gradeReport.range" /></th>
                                <th><spring:message code="gradeReport.percentage" /></th>
                                <th><spring:message code="gradeReport.letter" /></th>
                            </tr>
                            </thead>

                            <tbody>


                            <c:forEach var="gradeReport" items='${courseGradeFullReportList}' >
                                    <c:if test="${gradeReport.gradeItemType eq 'Exam'}">
                                        <tr>
                                            <td><span class="label label-default"><c:out value="${gradeReport.gradeItemType}"/></span></td>
                                            <td><c:out value="${gradeReport.gradeItemName}"/></td>
                                            <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.grade}"/></c:if></td>
                                            <td><c:out value="${gradeReport.minGrade}"/> - <c:out value="${gradeReport.maxGrade}"/></td>
                                            <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.percentage}"/>%</c:if></td>
                                            <td><c:out value="${gradeReport.letter}"/></td>
                                        </tr>
                                    </c:if>
                            </c:forEach>
                            <c:forEach var="gradeReport" items='${courseGradeFullReportList}' >
                                    <c:if test="${gradeReport.gradeItemType ne 'Exam'}">
                                        <tr>
                                            <td><span class="label label-default"><c:out value="${gradeReport.gradeItemType}"/></span></td>
                                            <td><c:out value="${gradeReport.gradeItemName}"/></td>
                                            <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.grade}"/></c:if></td>
                                            <td><c:out value="${gradeReport.minGrade}"/> - <c:out value="${gradeReport.maxGrade}"/></td>
                                            <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.percentage}"/>%</c:if></td>
                                            <td><c:out value="${gradeReport.letter}"/></td>
                                        </tr>
                                    </c:if>
                            </c:forEach>

                            </tbody>

                            <tfoot>
                            <tr>
                                <th colspan="6" ><spring:message code="gradeReport.total" /> : ${courseTotal}</th>
                            </tr>
                            <tr>
                                <td colspan="6" class="text-center"> <span><spring:message code="gradeReport.OverviewStmt" /> - ${userName} </span></td>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                    <%--<--END OF TABLE-->--%>

                    <h3 class="avilableExamsTitle"><spring:message code="studentGrades_details.text1"/></h3>
                    <%--<div class="divider"></div>--%>
                    <c:if test="${ empty recommendedAndAvailableExams}">
                        <div style="text-align: center"><i><spring:message code="studentGrades_details.text2"/></i></div>
                    </c:if>


                    <%--Available Exams--%>
                    <div class="avilableExamsContainer">

                        <!--Coures list Begins-->
                        <ul class="courseSearchList list-group">


                            <%--<c:if test="${ empty searchContentList}">--%>
                                <%--<div style="text-align: center"><i>No Data Found !!!</i></div>--%>
                            <%--</c:if>--%>

                                <%------------------------%>

                                <!--List Item-->
                                <c:forEach var = "examRecomendations" items="${recommendedAndAvailableExams}">
                                    <li class="list-group-item listCard">
                                        <!-- course box content-->
                                        <div class="courseCard">
                                            <div class="row">
                                                <!--intro names -->
                                                <div class="col-sm-8 ">
                                                    <div class="media">
                                                        <div class="media-left">
                                                            <h4><span class="label label-default">Exam</span></h4>
                                                        </div>
                                                        <div class="media-body">
                                                            <h5 class="media-heading"><c:out value="${examRecomendations.parentCourseId}"/></h5>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText">Exam Name:</span>
                                                                <a class="courseOthersText" href="${url}/admin/view/user-profile?userId=${searchList.examName}/>">
                                                                    <c:out value="${examRecomendations.examName}"/>
                                                                </a>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText">Exam Date:</span>
                                                                <c:out value="${examRecomendations.examDate}"/>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>

                                                <div class="col-sm-4 courseCardBox3" >
                                                    <div class="courseTextStyle" >
                                                        <div>
                                                            <span class="courseDateTextBox courseOthersText"><span class="label label-danger"><spring:message code="studentIndex.deadline"/></span>&nbsp;:&nbsp;<c:out value="${examRecomendations.examEnrollmentDeadline}"/></span>
                                                        </div>
                                                        <!--course buttons-->
                                                        <div class="courseBtnBox">
                                                            <a class="courseButton" href="#" onclick="examEnrollmentRequest(${examRecomendations.parentExamId},${examRecomendations.childCourseId},'${examRecomendations.examName}','${examRecomendations.parentCourseName}','${"no description .. "}','${"no start date "}','${examRecomendations.examDate}','${examRecomendations.examEnrollmentDeadline}')"><spring:message code="studentPageCourseLables.enroll" /></a>
                                                        </div>
                                                        <!--course buttons-->
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                                <!--List Item-->
                        </ul>
                        <br/><br/><br/><br/>
                        <!--Coures list Ends-->
                    </div>
                    <%--Available Exams--%>


                </div>



            </div>


        </div>




        </div>
        <%--/END OF MAIN CARD CONTAINER --%>


    </div>
    <!--Content Ends  -->


<%--exam enrollment modal--%>
<div class="modal fade" id="examEnrollModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <div class="modal-header">
                <h4  class="modal-title courseOthersText"><span id="examNameE"></span><button type="button" class="close" data-dismiss="modal">&times;</button></h4>
            </div>
            <h5 class="modelHead"><spring:message code="extra.examEnrolment"/> </h5>
            <div class="modal-body">
                <div class="courseTextStyle boxSpace" >
                    <span id="courseE" class="courseOthersText"></span><br/><br/>
                    <span class="courseOthersText" id="descripE">

                        </span>
                </div>
                <div class="courseTextStyle" >
                    <span class="label label-default"><spring:message code="studentHome.examDate"/></span> : <span class="courseOthersText" id="eDate"></span><div></div>
                    <span class="label label-danger"><spring:message code="manageCourse2.enrolmentDeadline"/></span> : <span class="courseOthersText" id="deadline"></span>
                </div>
            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-justified">
                    <a href="#"   id="makeExamRequest" onclick="makeExamEnrollmentRequest(this.value)" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModalExams" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                    <a href="#" id="reqE" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.cancel"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<%--CONFRIMATION MODAL EXAMS --%>

<script>
    $(document).ready(function() {
        $('#userReportTable').DataTable({
            paging: false,
            searching: false,
            ordering:  false,
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>

        });
    });

    function examEnrollmentRequest(examId,courseId,examName,courseName,courseDescr,examDate,enrolSrt,enrolEnd) {

        $('#examEnrollModal').modal('toggle');
        $('#reqE').val(courseId);
        $('#courseE').text(courseName);
        $('#descripE').text(courseDescr);
        $('#examNameE').text(examName);
        $('#eDate').text(enrolSrt);
        $('#deadline').text(enrolEnd);
        $('#makeExamRequest').val(examId);

    }

    function makeExamEnrollmentRequest(examId) {
        var r = confirm("Confirm Enrollment Request");
        if(r == true){
            $.ajax({
                url:'${url}/student/exam/request-enrollment/'+$('#reqE').val()+'/'+examId,
                type:'post',
                success:function(data){
                    switch(data) {
                        case "410":
                            alert('<spring:message code="studentSearchCourseAndExam.msg1"/>');
                            break;
                        case "420":
                            alert('<spring:message code="studentSearchCourseAndExam.msg2"/>');
                            break;
                        case "430":
                            alert('<spring:message code="studentSearchCourseAndExam.msg3"/>');
                            break;
                        case "440":
                            alert('<spring:message code="studentSearchCourseAndExam.msg4"/>');
                            break;
                        case "210":
                            alert('<spring:message code="studentSearchCourseAndExam.msg5"/>');
                            break;
                        case "200":
                        {
                            alert('<spring:message code="studentSearchCourseAndExam.msg6"/>');
                            document.location.reload(true);
                            break;
                        }
                        default:
                            alert('<spring:message code="studentSearchCourseAndExam.msg7"/>');
                    }

                    $('#examEnrollModal').modal('toggle');
//                    $('#confirmationModalExams').modal('toggle');
//                    $('#suc').text("Exam Request Made");
//                    $(".alert-success").css("display", "block");

                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //do nothing
        }

    }

    var translation ={
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }

</script>

</body>

</html>

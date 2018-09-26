<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 13/10/2017
  Time: 12:24
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
    <title><spring:message code="addExamForm.AddExam" /> </title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>


</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-exams" class="icon-home"> <spring:message code="manageExams.manageExams" /></a></li>
            <li class="last active"><a ><spring:message code="addClearanceExamForm.addExams" /></a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">



            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="examForm.createNewExam" /></h1>
                </div>


                <form id="examForm" class="form-horizontal" >


                    <%--FORM HEADER--%>
                    <%--FORM BOX 1/--%>
                        <h3><spring:message code="addCourseForm.basic" /> </h3>
                    <div class="formBox" >

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="majorId"><spring:message code="manageCourse2.major" /></label>
                            <div class="col-sm-9">

                                <input type="hidden"  autocomplete="off" class="form-control" id="majorId"  readonly name="majorId" required value="${examFormSelectCourseList.majorId}" >
                                <input type="text"  autocomplete="off" class="form-control" id="majorIdOut"  readonly name="majorIdOut" required value="${examFormSelectCourseList.majorName}">
                                 <%--<c:out value="${examFormSelectCourseList.majorName}"/>--%>

                                <%--<select autocomplete="off" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="majorSelect" name="majorId" required>
                                    <option value=""><spring:message code="manageGrades2.selectMajor" /></option>
                                    <c:forEach var = "major" items="${majorSelectList}">
                                        <option value="${major.majorId}"> <c:out value="${major.majorName}"/></option>
                                    </c:forEach>
                                </select>--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="semesterSelect"><spring:message code="manageUsers2.semester" /></label>
                            <div class="col-sm-9">
                                <select autocomplete="off" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="semesterSelect" name="semesterId" required>
                                    <option value=""><spring:message code="addCourseForm.selectSemester" /></option>
                                    <c:forEach var = "semesterList" items="${semesterSelectList}">
                                        <option value="${semesterList.semesterId}"><c:out value="${semesterList.semesterCode}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="parentCourseId"><spring:message code="main.course" /></label>
                            <div class="col-sm-9">
                                <input type="hidden"  autocomplete="off" class="form-control" id="parentCourseId"  readonly name="parentCourseId" required value="${examFormSelectCourseList.parentCourseId}" >
                                <input type="text"  autocomplete="off" class="form-control" id="parentCourseIdOut"  readonly name="parentCourseIdOut" required value="${examFormSelectCourseList.courseName}/${examFormSelectCourseList.courseShortName}">

                                <%--<select autocomplete="off" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="courseSelect" name="parentCourseId" required>
                                    <option value=""><spring:message code="addExamForm.selectCourse" /></option>
                                    <c:forEach var = "courselistSelect" items="${examFormSelectCourseList}">
                                        <option value="${courselistSelect.parentCourseId}"> (${courselistSelect.parentCourseId})<c:out value="${courselistSelect.courseName}"/></option>
                                    </c:forEach>
                                </select>--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="teacherSelect"><spring:message code="addExamForm.teacher" /></label>
                            <div class="col-sm-9">
                                <select autocomplete="off" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="teacherSelect" name="teacherId" required>
                                    <option value=""><spring:message code="addExamForm.selectCourse" /></option>
                                    <c:forEach var = "teacherList" items="${teacherSelectList}">
                                        <option value="${teacherList.userId}"> (${teacherList.userId})<c:out value="${teacherList.userName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                        <div class="formDivider"></div>
                        <h3><spring:message code="addExamForm.examDetails" /> </h3>

                        <div class="formBox">
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examName"><spring:message code="enrollmentList.ExamName" /></label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control" id="examName" placeholder='<spring:message code="addExamForm.enterExamName" />' name="examName" required>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examDate"><spring:message code="manageExams2.dateOfExam" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="examDate" placeholder='<spring:message code="addExamForm.enterExamDate" />' name="examDate" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentDeadLine"><spring:message code="examForm.enrolmentEndDate" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="enrolmentDeadLine" placeholder='<spring:message code="addExamForm.enterExamEnrolmentDeadlineDate" />' name="enrolmentDeadLine" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="minGrade"><spring:message code="addExamForm.minGrade" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="minGrade" value="0" name="minGrade">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="maxGrade"><spring:message code="addExamForm.maxGrade" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="maxGrade" value="100" name="maxGrade">
                            </div>
                        </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="weight"><spring:message code="addExamForm.weight" /></label>
                                <div class="col-sm-9">
                                    <input type="text"  autocomplete="off" class="form-control" id="weight" placeholder='<spring:message code="addExamForm.weight" />' name="weight" required>
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
                            <button type="submit" class="btn btn-default"><spring:message code="examForm.createExam" /></button>
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

                <h1><spring:message code="examForm.lastAddedExams" /></h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "aList" items="${lastAddedList}">
                            <tr>
                                <td ><c:out value="${aList.id}"/></td>
                                <td ><c:out value="${aList.examName}"/></td>
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
            url:'${url}/admin/manage-exams/add-exam',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    var r = confirm('<spring:message code="addExamForm.msg2" />');
                    if (r == true) {
                        location.reload(true);
                    } else{
                        window.location.href = '${url}/admin/manage-exams';
                    }
                }else{
                    alert('<spring:message code="addExamForm.msg1" />');
                }
                alert(data);
                location.reload(true);
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $("#majorSelect").change(function(){
        var courseSelect = $('#courseSelect');
        $('option', courseSelect).remove();
        var thisvalue = $(this).find("option:selected").val();
        if(thisvalue !== ''){
            $.ajax({
                url: "${url}/admin/get-parent-courses/"+thisvalue,
                type: "GET",
                cache: false,
                async: false,
                success: function (data) {
                    console.log(data);
                    courseSelect.append('<option >Choose Course:</option>');
                    for (var i = 0, len = data.length; i < len; i++) {
                        courseSelect.append('<option value='+data[i].parentCourseId+'>' +data[i].courseName+ '</option>');
                    }

                }, error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }
    });
<%--   $("#semesterSelect").change(function(){
       var courseSelect = $('#courseSelect');
       $('option', courseSelect).remove();
       var thisvalue = $(this).find("option:selected").val();
       var majorValue = $('#majorSelect').val();
//            alert(thisvalue);
      if(majorValue !== ''){
          $.ajax({
              url: "${url}/admin/get-child-courses/"+majorValue+"/"+thisvalue,
              type: "GET",
              cache: false,
              async: false,
              success: function (data) {
                  console.log(data);
                  courseSelect.append('<option >Choose Course:</option>');
                  for (var i = 0, len = data.length; i < len; i++) {
                      courseSelect.append('<option value='+data[i].childCourseId+'>' +data[i].courseName+ '</option>');
                  }

              }, error : function(data){
                  alert('<spring:message code="main.msgError" />');
              }
          });
      }
   });

   $("#majorSelect").change(function(){
       var courseSelect = $('#courseSelect');
       $('option', courseSelect).remove();
       var thisvalue = $(this).find("option:selected").val();
       var semesterValue = $('#semesterSelect').val();
//            alert(thisvalue);
       if(semesterValue !== ''){
           $.ajax({
               url: "${url}/admin/get-child-courses/"+thisvalue+"/"+semesterValue,
               type: "GET",
               cache: false,
               async: false,
               success: function (data) {
                   console.log(data);
                   courseSelect.append('<option >Choose Course:</option>');
                   for (var i = 0, len = data.length; i < len; i++) {
                       courseSelect.append('<option value='+data[i].childCourseId+'>' +data[i].courseName+ '</option>');
                   }

               }, error : function(data){
                   alert('<spring:message code="main.msgError" />');
               }
           });
       }
   });      --%>

</script>

</body>

</html>


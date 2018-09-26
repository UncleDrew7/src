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
    <title><spring:message code="addCourseForm.createCourse" /></title>
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
        <c:when test="${param.nv eq 'pc'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/parent-course/view/${parentCourseId}" class="icon-home"> <spring:message code="viewParentCourse.viewParentCourse" /></a></li>
                    <li class="last active"><a ><spring:message code="addCourseForm.addCourse" /></a></li>
                </ul>
            </div>
        </c:when>
        <c:when test="${param.nv eq 'mpc'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/majors" class="icon-home"> <spring:message code="manageCourse2.parenetCourse" /></a></li>
                    <li class="last active"><a ><spring:message code="addCourseForm.addCourse" /></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"> <spring:message code="addCourseForm.manageCourse" /></a></li>
                    <li class="last active"><a ><spring:message code="addCourseForm.addCourse" /></a></li>
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
                    <h1><spring:message code="addCourseForm.createChildCourseFor" /><span style="font-weight: 300"> <c:if test="${parentCourseId ne null}"> : ${parentDetails.courseName}</c:if></span></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="addCourseForm" class="form-horizontal" >


                    <%--FORM HEADER--%>
                    <h3><spring:message code="addCourseForm.basic" /></h3>
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
                            <label class="control-label col-sm-3" for="majorSelect"><spring:message code="manageCourse2.major" /></label>
                            <div class="col-sm-9">
                                <select  autocomplete="off" id="majorSelect" class="form-control span3" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="major" required>
                                   <%--<option>hell</option>--%>
                                      <c:if test="${parentCourseId ne null}">
                                          <option value="${parentDetails.majorId}">${parentDetails.majorName}</option>
                                          <c:forEach var = "major" items="${majorSelectList}">
                                              <c:if test="${parentDetails.majorId ne major.majorId}">
                                                  <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                                              </c:if>
                                          </c:forEach>
                                      </c:if>
                                       <c:if test="${parentCourseId eq null}">
                                           <option value=""><spring:message code="manageGrades2.selectMajor" /></option>
                                           <c:forEach var = "major" items="${majorSelectList}">
                                               <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                                           </c:forEach>
                                       </c:if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseSelect"><spring:message code="studentHome.course" /></label>
                            <div class="col-sm-9">
                                <select class="form-control span3" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="courseSelect" name="course" required>
                                    <c:if test="${parentCourseId ne null}">
                                        <option value="${parentCourseId}" >${parentDetails.courseName}</option>
                                    </c:if>
                                    <c:if test="${parentCourseId eq null}">
                                        <option value=""><spring:message code="addCourseForm.selectMajorThenChoosCourse" /></option>
                                    </c:if>

                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="semester"><spring:message code="manageCourse.semester" /></label>
                            <div class="col-sm-9">
                                <select autocomplete="off" class="form-control" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="semester" name="semester" required>
                                    <option value=""><spring:message code="addCourseForm.selectSemester" /></option>
                                    <c:forEach var = "semester" items="${semesterList}">
                                        <option value="${semester.semesterId}"><c:out value="${semester.semesterCode}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseShortName"><spring:message code="courseForm.courseShortName" /> </label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control" id="courseShortName" placeholder='<spring:message code="addCourseForm.plcHolder1" />'  readonly>

                                <%--<c:if test="${parentCourseId ne null}">
                                    <option value="${parentCourseId}" >${parentDetails.courseShortName}</option>
                                </c:if>
                                <c:if test="${parentCourseId eq null}">
                                    <option value=""><spring:message code="addCourseForm.selectMajorThenChoosCourse" /></option>
                                </c:if>--%>

                                   <%-- <select class="form-control span3" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="courseShortName" name="courseName" required>
                                        <c:if test="${parentCourseId ne null}">
                                            <option value="${parentCourseId}" >${parentDetails.courseShortNameName}</option>
                                        </c:if>
                                        <c:if test="${parentCourseId eq null}">
                                            <option value=""><spring:message code="addCourseForm.selectMajorThenChoosCourse" /></option>
                                        </c:if>--%>

                                    </select>


                            </div>
                        </div>


                        <div class="form-group">
                        <label class="control-label col-sm-3" for="teacher"><spring:message code="courseForm.teacher" /></label>
                        <div class="col-sm-9">
                            <select autocomplete="off" class="form-control" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();'id="teacher" name="teacherId" required>
                                <option value=""><spring:message code="addCourseForm.selectTeacher" /></option>
                                <c:forEach var = "row" items="${teachersList}">
                                    <option value="${row.userId}"><c:out value="${row.userName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="startDate"><spring:message code="addClearanceExamForm.enrolmentStartDate" /></label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control datepicker" id="startDate" placeholder='<spring:message code="addCourseForm.plcHolder2" />' name="startDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="deadline"><spring:message code="enrollmentList.deadline" /></label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control datepicker" id="deadline" placeholder='<spring:message code="addCourseForm.plcHolder3" />' name="deadline" required>
                            </div>
                        </div>

                    </div>

                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class=" submitBtnBox">
                            <button type="submit" class="btn btn-default"><spring:message code="addCourseForm.createCoures" /></button>
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
            <%--<div class="card bigCardContainer ">--%>

                <%--<div class="formTitleBox settingsTitleBox">--%>
                    <%--<h1>Settings</h1>--%>
                <%--</div>--%>



                <%--<div class="form-group">--%>
                    <%--<label class="control-label " for="degreeType">Degree Type</label>--%>
                    <%--<div>--%>
                        <%--<select class="form-control" id="degreeType" name="degreeType">--%>
                            <%--<option>Select Degree type for course </option>--%>
                            <%--<option>Double-Degree</option>--%>
                            <%--<option>General-Degree</option>--%>
                            <%--<option>All</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<label class="control-label " for="contentLanguage">Content Language</label>--%>
                    <%--<div >--%>
                        <%--<select class="form-control" id="contentLanguage" name="contentLanguage">--%>
                            <%--<option>English</option>--%>
                            <%--<option>Chinese</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<label class="control-label " for="courseStatus">Course Status</label>--%>
                    <%--<div >--%>
                        <%--<select class="form-control" id="courseStatus" name="courseStatus">--%>
                            <%--<option>Active</option>--%>
                            <%--<option>Not Active</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<div class="col-sm-offset-2 col-sm-10">--%>
                        <%--<button type="submit" class="btn btn-default">Save Settings</button>--%>
                    <%--</div>--%>
                <%--</div>--%>

            <%--</div>--%>
            <%--PARENT CARD--%>

                <div class="card bigCardContainer ">

                    <h1><spring:message code="addCourseForm.lastAddedCourses" /></h1>


                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <c:forEach var = "row" items="${lastAdded}">
                                <tr>
                                    <td ><c:out value="${row.parentCourseId}"/></td>
                                    <td ><c:out value="${row.courseName}"/></td>
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
    })

    $('#addCourseForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-courses/process-add-course',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    var r = confirm('<spring:message code="courseForm.msg1" />');
                    if (r == true) {
                        location.reload(true);
                    } else{
                        window.location.href = '${url}/admin/manage-courses/courses';
                    }
                }else{
                    alert('<spring:message code="addCourseForm.msg1" />');
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $("#majorSelect").change(function(){
        var courseSelect = $('#courseSelect');
      //  var shortName = $('#courseShortName');
        $('option', courseSelect).remove();
      //  $('option', courseShortName).remove();
        var thisvalue = $(this).find("option:selected").val();
//            alert(thisvalue);
        $.ajax({
            url: "${url}/admin/get-parent-courses/"+thisvalue,
            type: "GET",
            cache: false,
            async: false,
            success: function (data) {
                console.log(data);
                courseSelect.append('<option >Choose Course:</option>');
            //    shortName.append('<option >Choose CourseName:</option>');
                for (var i = 0, len = data.length; i < len; i++) {
                   courseSelect.append('<option value='+data[i].parentCourseId+'>' +data[i].courseName+ '</option>');
             //      shortName.append('<option value='+data[i].parentCourseId+'>' +data[i].courseShortName+ '</option>');
                }

            }, error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $("#courseSelect").change(function(){
        var shortName = $('#courseShortName');
       // $('option', courseShortName).remove();
        var thisvalue = $(this).find("option:selected").val();
//            alert(thisvalue);
        $.ajax({
            url: "${url}/admin/get-parent-coursesName/"+thisvalue,
            type: "GET",
            cache: false,
            async: false,
            success: function (data) {
                console.log(data);
                /*shortName.append('<option >Choose CourseName:</option>');
                for (var i = 0, len = data.length; i < len; i++) {
                    courseSelect.append('<option value='+data[i].parentCourseId+'>' +data[i].courseName+ '</option>');
                    shortName.append('<option value='+data[i].parentCourseId+'>' +data[i].courseShortName+ '</option>');
                }*/
                $('#courseShortName').val(data[0].courseShortName);

            }, error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });



</script>

</body>

</html>

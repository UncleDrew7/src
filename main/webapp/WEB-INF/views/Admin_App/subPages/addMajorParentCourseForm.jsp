<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/12/2017
  Time: 16:35
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
    <title><spring:message code="addMajorParentCourseForm.AddParentCourse" /></title>
    <%--<title><spring:message code="addMajorParentCourseForm.AddMajor" /></title>--%>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>


<!--Content Begins  -->
<div class="content">

    <c:if test="${type eq 1}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/manage-courses/majors" class="icon-home"> <spring:message code="manageCourse2.parenetCourse" /></a></li>
                <li class="last active"><a ><spring:message code="addMajorParentCourseForm.AddMajor" /></a></li>
            </ul>
        </div>
    </c:if>

    <c:if test="${type eq 2}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first">
                    <div class="mainLk">
                        <a href="${url}/admin/manage-courses/majors" class="icon-home"> <spring:message code="manageCourse2.parenetCourse" /></a>
                    </div>
                </li>
                <li class="last active"><a ><spring:message code="addMajorParentCourseForm.AddParentCourse" /></a></li>
            </ul>
        </div>
    </c:if>

    <!--MAIN CARD CONTAINER -->
    <%-----------------------------------------------------------------ADD MAJOR -------------------------------------------------------------------------%>
<c:if test="${type eq 1}">
    <div class="row mainContainer">

        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">
                <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="addMajorParentCourseForm.addNewMajor" /></h1>
                </div>



                <form id="majorForm" method="POST" class="form-horizontal" >
                        <%--FORM HEADER--%>
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
                            <label class="control-label col-sm-3" for="courseName"><spring:message code="addMajorParentCourseForm.majorName" /> </label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control" id="majorName" placeholder='<spring:message code="addMajorParentCourseForm.enterMajorName" />' name="majorName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="courseShortName"><spring:message code="addMajorParentCourseForm.majorShortName" /> </label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control" id="majorShortName" placeholder='<spring:message code="addMajorParentCourseForm.enterMajorShortName" />' name="majorShortName" required>
                            </div>
                        </div>


                    </div>

                    <div class="formDivider"></div>
                        <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class=" submitBtnBox">
                            <button type="submit" onclick="createNewMajor()" class="btn btn-default"><spring:message code="addMajorParentCourseForm.createMajor" /></button>
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

                <h1><spring:message code="addMajorParentCourseForm.lastAddedMajor" /></h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "classList" items="${lastAddedClasses}">
                            <tr>
                                <td ><c:out value="${classList.id}"/></td>
                                <td ><c:out value="${classList.className}"/></td>
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
</c:if>


    <%--------------------------------------------------------------ADD PARENT COURSE ---------------------------------------------------------------%>

    <c:if test="${type eq 2}">
        <div class="row mainContainer">

            <!-- BIG CONTAINER -->
            <div class="col-sm-9 bigCardContainerParent \">
                    <%--BIG CARD CONTAINER BOX --%>
                <div class="card bigCardContainer">
                    <div class="formTitleBox">
                        <h1><spring:message code="addMajorParentCourseForm.AddParentCourse" /></h1>

                    </div>

                    <form id="addParentCourseForm" class="form-horizontal" >


                            <%--FORM HEADER--%>
                        <%--<h3><spring:message code="addCourseForm.basic" /></h3>--%>
                        <h3><spring:message code="courseForm.courseDetails" /> </h3>
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
                                        <option value=""><spring:message code="manageGrades2.selectMajor" /></option>
                                        <c:forEach var = "major" items="${majorSelectList}">
                                            <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <%--<div class="form-group">
                                <label class="control-label col-sm-3" for="courseId"><spring:message code="manageCourse2.courseId" /> </label>
                                <div class="col-sm-9">
                                    <input type="text"  autocomplete="off" class="form-control" id="courseId" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseId" />' name="courseId" required>
                                </div>
                            </div>--%>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseName"><spring:message code="courseForm.courseName" /> </label>
                                <div class="col-sm-9">
                                    <input type="text"  autocomplete="off" class="form-control" id="courseName" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseName" />' name="courseName" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseShortName"><spring:message code="courseForm.courseShortNameEn" /> </label>
                                <div class="col-sm-9">
                                    <input type="text"  autocomplete="off" class="form-control" id="courseShortName" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseShortName" />' name="courseShortName" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseShortName"><spring:message code="courseForm.courseShortNameCn" /> </label>
                                <div class="col-sm-9">
                                    <input type="text"  autocomplete="off" class="form-control" id="courseShortNameCn" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseShortName" />' name="courseShortNameCn" required>
                                </div>
                            </div>


                        </div>
                            <%--FORM BOX1--%>
                            <%--FORM DIVIDER--%>
                        <%--<div class="formDivider"></div>--%>
                            <%--FORM DIVIDER--%>


                            <%--FORM HEADER--%>

                            <%--FORM BOX 2/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="cType"><spring:message code="enrollmentList.courseType" /></label>
                                <div class="col-sm-9">
                                    <select  autocomplete="off" id="cType" class="form-control span3"  name="courseType" required>
                                        <option value="必修课">必修课</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="credits"><spring:message code="manageCourse2.credits" /> </label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="credits" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseCredits" />' name="credits">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseDescription"><spring:message code="courseForm.description" /></label>
                                <div class="col-sm-9">
                                    <textarea  class="form-control"  autocomplete="off" rows="5" id="courseDescription" placeholder='<spring:message code="addMajorParentCourseForm.enterCourseDescription" />' name="courseDescription" required></textarea>
                                </div>
                            </div>

                        </div>
                            <%--FORM BOX1--%>
                            <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                            <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class=" submitBtnBox">
                                <button type="submit"  onclick="createNewParentCourse()" class="btn btn-default"><spring:message code="addCourseForm.createCoures" /></button>
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

                    <h1><spring:message code="addMajorParentCourseForm.lastAddedParentCourse" /></h1>


                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <c:forEach var = "lastAdded" items="${lastAddedParentCourse}">
                                <tr>
                                    <td ><c:out value="${lastAdded.parentCourseId}"/></td>
                                    <td ><c:out value="${lastAdded.courseName}"/></td>
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
    </c:if>

</div>
<!--Content Ends  -->

<script>

    var options = {
        valueNames: [ 'name', 'born' ]
    };

    var userList = new List('users', options);

    $(document).ready(function() {


    });




    
    function createNewMajor() {
        $('#majorForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();

            $.ajax({
                url:'${url}/admin/add-major',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        var r = confirm('<spring:message code="addMajorParentCourseForm.msg1" />');
                        if (r == true) {
                            location.reload(true);
                        } else{
                            window.location.href = '${url}/admin/manage-courses/majors';
                        }
                    }
                    else{
                        alert('<spring:message code="addMajorParentCourseForm.msg2" />');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        });
    }

    function createNewParentCourse() {
        $('#addParentCourseForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();

            $.ajax({
                url:'${url}/admin/manage-courses/add-parent-course',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        var r = confirm('<spring:message code="addMajorParentCourseForm.msg3" />');
                        if (r == true) {
                            location.reload(true);
                        } else{
                            window.location.href = '${url}/admin/manage-courses/majors';
                        }
                    }
                    else if(data === '501'){
                        alert('<spring:message code="addMajorParentCourseForm.msg5" />');
                    }
                    else if(data === '502'){
                        alert('<spring:message code="addMajorParentCourseForm.msg6" />');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        });

    }
</script>

</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/01/2018
  Time: 22:05
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
    <title><spring:message code="editMajorAndParentCourse.text1"/> </title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <c:if test="${object eq 'major'}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/manage-courses/majors" class="icon-home"> <spring:message code="manageCourse2.parenetCourse"/></a></li>
                <li class="last active"><a ><spring:message code="manageCourse2.editMajor"/></a></li>
            </ul>
        </div>
    </c:if>

    <c:if test="${object eq 'parent_course'}">
        <c:if test="${param.nv eq 'pc'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/parent-course/view/${itemId}" class="icon-home"> <spring:message code="viewParentCourse.viewParentCourse" /></a></li>
                    <li class="last active"><a ><spring:message code="editMajorAndParentCourse.editParentCourse"/></a></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${param.nv eq 'mpc'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/majors" class="icon-home"> <spring:message code="manageCourse2.parenetCourse" /></a></li>
                    <li class="last active"><a ><spring:message code="editMajorAndParentCourse.editParentCourse"/></a></li>
                </ul>
            </div>
        </c:if>

    </c:if>

    <!--MAIN CARD CONTAINER -->
    <%-----------------------------------------------------------------EDIT MAJOR -------------------------------------------------------------------------%>
    <c:if test="${object eq 'major'}">
        <div class="row mainContainer">

            <div class="col-sm-2">
                <br/>

                <div>
                    <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageCourse2.majorCodes"/></h4>
                    <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder='<spring:message code="manageUsers.search"/>'></div>
                </div>
                <div class="card  filterBoxCourse ">
                    <div>
                        <div style="" class="list-group">
                            <c:forEach var = "mslist" items="${majorSelectList}" varStatus="index">
                                <a href="${url}/admin/edit-parent-course-major/major?majorId=${mslist.majorId}"  class="list-group-item <c:if test='${param.filterId eq mslist.majorId}'>activeFilter</c:if>">${mslist.majorShortName}</a>
                            </c:forEach>
                        </div>

                    </div>
                </div>

            </div>
            <!-- BIG CONTAINER -->
            <div class="col-sm-7 bigCardContainerParent \">
                    <%--BIG CARD CONTAINER BOX --%>
                <div class="card bigCardContainer">

                    <div class="formTitleBox">
                        <h1><spring:message code="manageCourse2.editMajor"/></h1>
                    </div>

                    <c:if test="${empty majorData}">
                        <div style="text-align: center">
                            <i><spring:message code="editMajorAndParentCourse.pleaseSelectMajor"/></i>
                        </div>
                    </c:if>

                    <c:if test="${!empty majorData}">
                        <form id="majorForm" method="POST" class="form-horizontal" >
                                <%--FORM HEADER--%>
                                <%--FORM BOX 1/--%>
                            <div class="formBox2">

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
                                    <label class="control-label col-sm-3" for="courseName"><spring:message code="addMajorParentCourseForm.majorName"/> </label>
                                    <div class="col-sm-9">
                                        <input type="text"  autocomplete="off" class="form-control" id="majorName" value="${majorData.majorName}" name="majorName" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="courseShortName"><spring:message code="addMajorParentCourseForm.majorShortName"/> </label>
                                    <div class="col-sm-9">
                                        <input type="text"  autocomplete="off" class="form-control" id="majorShortName" value="${majorData.majorShortName}" name="majorShortName" required>
                                    </div>
                                </div>


                            </div>

                            <div class="formDivider"></div>
                                <%--FORM DIVIDER--%>

                            <div class="form-group">
                                <div class=" submitBtnBox">
                                    <button type="submit" onclick="editMajor()" class="btn btn-default"><spring:message code="extra.saveChanges"/> </button>
                                </div>
                            </div>

                        </form>
                        <div class="form-group">
                            <div class=" submitBtnBox">
                                <button  onclick="deleteMajor()" class="btn btn-danger"><spring:message code="editMajorAndParentCourse.btn1"/> </button>
                            </div>
                        </div>

                    </c:if>
                        <%--/FORM--%>

                </div>
                    <%--BIG CARD CONTAINER BOX--%>

            </div>
            <!--BIG CONTAINER -->

            <!--Small cards-->
            <div class="col-sm-3 smallCardContainerParent">

                <div class="card bigCardContainer ">

                    <h1><spring:message code="editMajorAndParentCourse.lastEditedMajor"/></h1>

                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <c:forEach var = "mjList" items="${lastEditedMajor}">
                                <tr>
                                    <td ><c:out value="${mjList.majorShortName}"/></td>
                                    <td ><c:out value="${mjList.majorName}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>

                </div>

            </div>
            <!--Small cards-->

        </div>
        <!--MAIN CARD CONTAINER -->
    </c:if>


    <%--------------------------------------------------------------ADD PARENT COURSE ---------------------------------------------------------------%>

    <c:if test="${object eq 'parent_course'}">
        <div class="row mainContainer">

            <!-- BIG CONTAINER -->
            <div class="col-sm-9 bigCardContainerParent \">
                    <%--BIG CARD CONTAINER BOX --%>
                <div class="card bigCardContainer">
                    <div class="formTitleBox">
                        <h1><spring:message code="editMajorAndParentCourse.editParentCourse"/> <span style="font-weight: 300"><c:if test="${object eq 'parent_course'}"> : ${parentCourseData.courseName} / ${parentCourseData.courseShortName}</c:if></span></h1>
                    </div>

                    <form id="editParentCourseForm" class="form-horizontal" >


                            <%--FORM HEADER--%>
                        <h3><spring:message code="addCourseForm.basic"/></h3>
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
                                <label class="control-label col-sm-3" for="majorSelect"><spring:message code="manageCourse2.major"/></label>
                                <div class="col-sm-9">
                                    <select  autocomplete="off" id="majorSelect" class="form-control span3" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="major" required>
                                        <option value="${parentCourseData.majorId}">${parentCourseData.majorName}</option>
                                        <c:forEach var = "major" items="${majorSelectList}">
                                            <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <%--<div class="form-group">--%>
                                <%--<label class="control-label col-sm-3" for="courseId">Course Id </label>--%>
                                <%--<div class="col-sm-9">--%>
                                    <%--<input type="text" value="${parentCourseData.parentCourseId}"   autocomplete="off" class="form-control" id="courseId" placeholder="Enter Course Id" name="courseId" required>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseName"><spring:message code="courseForm.courseName"/> </label>
                                <div class="col-sm-9">
                                    <input type="text"  value="${parentCourseData.courseName}" autocomplete="off" class="form-control" id="courseName" placeholder="Enter Course Name" name="courseName" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseShortName"><spring:message code="courseForm.courseShortName"/> </label>
                                <div class="col-sm-9">
                                    <input type="text"  value="${parentCourseData.courseShortName}" autocomplete="off" class="form-control" id="courseShortName"  name="courseShortName" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseShortNameCN"><spring:message code="courseForm.courseShortNameCn"/> </label>
                                <div class="col-sm-9">
                                    <input type="text"  value="${parentCourseData.courseShortNameCN}" autocomplete="off" class="form-control" id="courseShortNameCN"  name="courseShortNameCN" >
                                </div>
                            </div>


                        </div>
                            <%--FORM BOX1--%>
                            <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                            <%--FORM DIVIDER--%>


                            <%--FORM HEADER--%>
                        <h3><spring:message code="courseForm.courseDetails"/> </h3>
                            <%--FORM BOX 2/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="cType"><spring:message code="enrollmentList.courseType"/></label>
                                <div class="col-sm-9">
                                    <select  autocomplete="off" id="cType" class="form-control span3"  name="courseType" required>
                                        <option value="必修课">必修课</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="credits"><spring:message code="manageCourse2.credits"/> </label>
                                <div class="col-sm-9">
                                    <input type="text" value="${parentCourseData.credits}" class="form-control" id="credits"  name="credits">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="courseDescription"><spring:message code="courseForm.description"/></label>
                                <div class="col-sm-9">
                                    <textarea  class="form-control"  autocomplete="off" rows="5" id="courseDescription" placeholder="Enter Course Description" name="courseDescription" required>${parentCourseData.description}</textarea>
                                </div>
                            </div>

                        </div>
                            <%--FORM BOX1--%>
                            <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                            <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class=" submitBtnBox">
                                <button type="submit"  onclick="editParentCourse()" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
                            </div>
                        </div>

                    </form>
                        <%--/FORM--%>
                    <div class="form-group">
                        <div class=" submitBtnBox">
                            <button  onclick="deleteParentCourse()" class="btn btn-danger"><spring:message code="editMajorAndParentCourse.btn2"/> </button>
                        </div>
                    </div>


                </div>
                    <%--BIG CARD CONTAINER BOX--%>


            </div>
            <!--BIG CONTAINER -->


            <!--Small cards-->
            <div class="col-sm-3 smallCardContainerParent">


                <div class="card bigCardContainer ">

                    <h4 style="text-align: center"><spring:message code="editMajorAndParentCourse.lastEditedParentCourse"/></h4>


                    <div class="table-responsive">
                        <table class="table table-condensed">
                            <c:forEach var = "lastAdded" items="${lastEditedParentCourse}">
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




    function editMajor() {
        $('#majorForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();

            $.ajax({
                url:'${url}/admin/update-major/${majorId}',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        var r = confirm('<spring:message code="editMajorAndParentCourse.msg2"/>');
                        if (r == true) {
                            location.reload(true);
                        } else{
                            window.location.href = '${url}/admin/manage-users/courses';
                        }
                    }
                    else{
                        alert('<spring:message code="editMajorAndParentCourse.msg3"/>');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        });
    }

    function editParentCourse() {
        $('#editParentCourseForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();

            $.ajax({
                url:'${url}/admin/manage-courses/update-parent-course/${parentCourseData.parentCourseId}',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        alert('<spring:message code="editMajorAndParentCourse.msg7"/>');
                        <c:if test="${param.nv eq 'pc'}"> window.location.href = '${url}/admin/parent-course/view/${parentCourseData.parentCourseId}';</c:if>
                        <c:if test="${param.nv eq null}"> window.location.href = '${url}/admin/manage-courses/majors';</c:if>
                    }
                    else{
                        alert('<spring:message code="editMajorAndParentCourse.msg6"/>');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        });

    }

    function deleteMajor() {

        var r = confirm('<spring:message code="editMajorAndParentCourse.msg8"/>');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/delete-major/${majorId}',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        alert('<spring:message code="editMajorAndParentCourse.msg9"/>');
                        window.location.href = '${url}/admin/manage-courses/majors';
                    }
                    else{
                        alert('<spring:message code="editMajorAndParentCourse.msg12"/>');
                    }
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        } else{
            // do nothing
        }
    }


    function deleteParentCourse() {

        var r = confirm('<spring:message code="editMajorAndParentCourse.msg10"/>');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/delete-parent-course/${itemId}',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    if(data === '200'){
                        alert('<spring:message code="editMajorAndParentCourse.msg11"/>');
                        window.location.href = '${url}/admin/manage-courses/majors';
                    }
                    else if(data === '501'){
                        alert('<spring:message code="editMajorAndParentCourse.msg13"/>');
                    }
                    else{
                        alert('<spring:message code="editMajorAndParentCourse.msg12"/>');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        } else{
            // do nothing
        }
    }

</script>

</body>

</html>

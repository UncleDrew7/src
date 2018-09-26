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
    <title><spring:message code="addClassForm.title" /></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>


<!--Content Begins  -->
<div class="content">

    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-users/classes" class="icon-home"> <spring:message code="addClassForm.manageClasses" /></a></li>
           <c:if test="${action eq 'add'}">
               <li class="last active"><a ><spring:message code="addClassForm.addClass" /></a></li>
           </c:if>
            <c:if test="${action eq 'edit'}">
                <li class="last active"><a ><spring:message code="addClassForm.editClass" /></a></li>
            </c:if>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">
            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <c:if test="${action eq 'add'}">
                        <h1><spring:message code="addClassForm.createNewClass" /></h1>
                    </c:if>
                    <c:if test="${action eq 'edit'}">
                        <h1><spring:message code="addClassForm.editClass" /></h1>
                    </c:if>
                </div>

                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form  <c:if test="${action eq 'add'}">id="addClassForm"</c:if> <c:if test="${action eq 'edit'}">id="editClassForm"</c:if> class="form-horizontal"  method="post">

                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="major"><spring:message code="manageCourse2.major" /></label>
                            <div class="col-sm-9">
                                <select class="form-control" id="major" name="major">

                                    <c:if test="${action ne 'edit'}">
                                        <c:forEach var = "major" items="${majorSelectList}">
                                            <option value="${major.majorId}">${major.majorName}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${action eq 'edit'}">
                                        <option value="${classCurrentDetails.majorId}">${classCurrentDetails.majorName}</option>
                                        <c:forEach var = "major" items="${majorSelectList}">
                                            <c:if test="${classCurrentDetails.majorId ne major.majorId}">
                                                <option value="${major.majorId}">${major.majorName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="className"><spring:message code="manageUsers.className" /></label>
                            <div class="col-sm-9">
                                <input type="text" <c:if test="${action eq 'edit'}">value="${classCurrentDetails.className}"</c:if> class="form-control" id="className" autocomplete="off" placeholder='<spring:message code="addClassForm.enterClassName" />' name="className" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="intake"><spring:message code="addClassForm.intakePeriod" /></label>
                            <div class="col-sm-9">
                                <select onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="intake" name="intake">
                                    <%--<c:if test="${action eq 'edit'}"> <option value="${classCurrentDetails.intakePeriod}">${classCurrentDetails.intakePeriod}</option></c:if>--%>
                                    <c:forEach var = "intake" items="${intakeList}">
                                        <c:if test="${classCurrentDetails.intakePeriod ne intake}">
                                            <option value="${intake}">${intake}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <c:if test="${action eq 'edit'}">
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="status"><spring:message code="manageUsers.status" /></label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="status" name="status">
                                       <option>${classCurrentDetails.status}</option>
                                       <c:if test="${classCurrentDetails.status ne 'active'}">
                                           <option><spring:message code="courseExams.active" /></option>
                                       </c:if>
                                        <c:if test="${classCurrentDetails.status ne 'in-active'}">
                                            <option><spring:message code="addClassForm.inactive" /></option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                        </c:if>

                    </div>

                    <div class="formDivider"></div>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <c:if test="${action eq 'add'}">
                                <button id="submit" type="submit" class="btn btn-default"><spring:message code="addClassForm.createClass" /></button>
                            </c:if>
                            <c:if test="${action eq 'edit'}">
                                <button id="submit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges" /></button>
                            </c:if>
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

                <%--&lt;%&ndash;PARENT CARD&ndash;%&gt;--%>
                <%--<div class="card bigCardContainer ">--%>

                    <%--<div class="formTitleBox settingsTitleBox">--%>
                        <%--<h1>Class Settings</h1>--%>
                    <%--</div>--%>



                    <%--<div class="form-group">--%>
                        <%--<label class="control-label " for="degreeType">Class Status</label>--%>
                        <%--<div >--%>
                            <%--<select class="form-control" id="degreeType" name="degreeType">--%>
                                <%--<option>active</option>--%>
                                <%--<option>non-active</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<label class="control-label " for="primaryLanguage">Primary Language</label>--%>
                        <%--<div >--%>
                            <%--<select class="form-control" id="primaryLanguage" name="primaryLanguage">--%>
                                <%--<option>English</option>--%>
                                <%--<option>Chinese</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group">--%>
                        <%--<div class="col-sm-offset-2 col-sm-10">--%>
                            <%--<button type="submit" class="btn btn-default">Save Settings</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                <%--</div>--%>
                <%--&lt;%&ndash;PARENT CARD&ndash;%&gt;--%>


                <div class="card bigCardContainer ">

                <h1><spring:message code="addClassForm.lastAddedClass" /></h1>


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


</div>
<!--Content Ends  -->

<script>

    var options = {
        valueNames: [ 'name', 'born' ]
    };

    var userList = new List('users', options);

$(document).ready(function() {


    });

    $('#addClassForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/class/add',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                var r = confirm('<spring:message code="addClassForm.msg1" />');
                if (r == true) {
                    location.reload(true);
                } else{
                    window.location.href = '${url}/admin/manage-users/classes';
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $('#editClassForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/class/update/${classId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert('<spring:message code="addClassForm.msg6" />');
                window.location.href = '${url}/admin/manage-users/classes';
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });
</script>

</body>

</html>

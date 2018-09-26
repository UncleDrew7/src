<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 27/10/2017
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/09/2017
  Time: 19:45
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
    <title><spring:message code="editProfileForm.editUser"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">
    <c:choose>
    <c:when test="${param.ng eq 2}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="studentEditProfile.manageCourse"/></a></li>
                <li><a href="${url}/admin/view/course/${courseId}">${currentCourseDetails.courseShortName}</a></li>
                <li class="last active"><a ><spring:message code="editCourseForm.editCourse"/></a></li>
            </ul>
        </div
    </c:when>
    <c:otherwise>
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/student/profile" class="icon-home"><spring:message code="editProfileForm.myProfile"/></a></li>
            <li class="last active"><a ><spring:message code="studentEditProfile.editProfile"/></a></li>
        </ul>
    </div
    </c:otherwise>
    </c:choose>

            <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">
            <h4><br/></h4>


            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="studentEditProfile.editCurrentUser"/></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="editUserForm" class="form-horizontal"  method="post">


                    <%--FORM HEADER--%>
                    <h3><spring:message code="addCourseForm.basic"/></h3>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="userId"><spring:message code="studentForm.userId"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="userId" autocomplete="off" disabled placeholder="${userProfile.userId}" name="userId" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="role"><spring:message code="manageUsers2.role"/></label>
                            <div class="col-sm-9">
                                <select disabled class="form-control" id="role" name="role" >
                                    <option>${userProfile.role}</option>
                                    <option>Student</option>
                                    <option>Teacher</option>
                                    <option>Admin</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="userClass"><spring:message code="enrollmentList.class"/></label>
                            <div class="col-sm-9">
                                <input disabled type="text" value="${userProfile.className}" class="form-control" autocomplete="off" id="userClass"  name="userClass" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="intake"><spring:message code="manageUsers2.intake"/></label>
                            <div class="col-sm-9">
                                <input type="intake" class="form-control" disabled id="intake"  name="intake" value="${userProfile.intake}" required>
                            </div>
                        </div>

                        <c:if test="${userProfile.role eq 'Student'}">
                            <div class="form-group">
                                <label class="control-label col-sm-3 " for="degreeType"><spring:message code="courseForm.degreeType"/></label>
                                <div class="col-sm-9">
                                    <select disabled autocomplete="off" class="form-control" id="degreeType" name="degreeType">
                                        <option>${userProfile.degreeType}</option>
                                        <c:if test="${userProfile.degreeType ne 'double-degree'}"><option value="double-degree">double-degree</option></c:if>
                                        <c:if test="${userProfile.degreeType ne 'chinese-degree'}"><option value="chinese-degree">chinese-degree</option></c:if>
                                    </select>
                                </div>
                            </div>
                        </c:if>

                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>
                    <h3>User Infromation</h3>
                    <%--FORM BOX 2/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="firstName"><spring:message code="studentForm.firstName"/></label>
                            <div class="col-sm-9">
                                <input type="text" readonly class="form-control" autocomplete="off" id="firstName" placeholder="" value="${userProfile.firstName}" name="firstName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="lastName"><spring:message code="studentForm.lastName"/> </label>
                            <div class="col-sm-9">
                                <input type="text" readonly class="form-control" autocomplete="off" id="lastName" placeholder="" value="${userProfile.lastName}" name="lastName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="gender"><spring:message code="manageUsers.gender"/></label>
                            <div class="col-sm-9">
                                <select  disabled class="form-control" id="gender" name="gender" required>
                                    <option>${userProfile.gender}</option>
                                    <option>Male</option>
                                    <option>Female</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="dateOfBirth"><spring:message code="studentForm.dateOfBirth"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control datepicker" autocomplete="off" id="dateOfBirth" placeholder="0000-00-00" value="${userProfile.dateOfBirth}" name="dateOfBirth" required >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="email"><spring:message code="studentForm.email"/></label>
                            <div class="col-sm-9">
                                <input type="email" class="form-control" autocomplete="off" id="email" placeholder="" value="${userMainDetails.email}" name="email" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="city"><spring:message code="studentForm.city"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="city" placeholder="" value="${userProfile.city}" name="city" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="country"><spring:message code="manageUsers.country"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control"  id="country" placeholder="" value="${userProfile.country}" name="country" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="primaryLanguage"><spring:message code="studentForm.primaryLanguage"/></label>
                            <div class="col-sm-9">
                                <select  autocomplete="off"  class="form-control" id="primaryLanguage" name="primaryLanguage">
                                    <option>${userProfile.primaryLanguage}</option>
                                    <c:if test="${userProfile.primaryLanguage ne 'English'}"><option value="English">English</option></c:if>
                                    <c:if test="${userProfile.primaryLanguage ne 'Chinese'}"><option value="Chinese">Chinese</option></c:if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="desc"><spring:message code="studentEditProfile.selfDescription"/></label>
                            <div class="col-sm-9">
                                <textarea type="text" class="form-control"  id="desc" placeholder=""  name="description" >${userProfile.selfDescription}</textarea>
                            </div>
                        </div>

                    </div>

                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button id="submit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
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
       <%--     <div class="card bigCardContainer ">

                <div class="formTitleBox settingsTitleBox">
                    <h1><spring:message code="studentForm.settings"/></h1>
                </div>

         &lt;%&ndash;       <form id="editUserSettingsForm"  method="post">
                    <c:if test="${userProfile.role eq 'Student'}">
                        <div class="form-group">
                            <label class="control-label " for="degreeType"><spring:message code="courseForm.degreeType"/></label>
                            <div >
                                <select disabled autocomplete="off" class="form-control" id="degreeType" name="degreeType">
                                    <option>${userProfile.degreeType}</option>
                                    <c:if test="${userProfile.degreeType ne 'double-degree'}"><option value="double-degree">double-degree</option></c:if>
                                    <c:if test="${userProfile.degreeType ne 'chinese-degree'}"><option value="chinese-degree">chinese-degree</option></c:if>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label class="control-label " for="primaryLanguage"><spring:message code="studentForm.primaryLanguage"/></label>
                        <div >
                            <select  autocomplete="off"  class="form-control" id="primaryLanguage" name="primaryLanguage">
                                <option>${userProfile.primaryLanguage}</option>
                                <c:if test="${userProfile.primaryLanguage ne 'English'}"><option value="English">English</option></c:if>
                                <c:if test="${userProfile.primaryLanguage ne 'Chinese'}"><option value="Chinese">Chinese</option></c:if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default"><spring:message code="studentForm.saveSettings"/></button>
                        </div>
                    </div>
                </form>&ndash;%&gt;

            </div>--%>

                <h4><br/></h4>

            <div class="card bigCardContainer ">


                <div class="formTitleBox settingsTitleBox">
                    <h1><spring:message code="editProfileForm.changePass"/></h1>
                </div>

                <%--<form id="editUserPasswordForm" method="post">--%>
                <form id="changePassForm"  method="post" >
                    <div class="form-group">
                        <div class="toolTipBox">
                            <span id="errorText" class="tooltipItem"></span><br/>
                        </div>
                        <label class="control-label " for="curntPsw"><spring:message code="editProfileForm.currentPassword"/></label>
                        <div >
                            <input style="display: none" autocomplete="off" type="password" class="form-control">
                            <input autocomplete="off" type="password" class="form-control"  id="curntPsw" placeholder='<spring:message code="studentEditProfile.enterCurrentPassword"/>' name="currentPassword" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label " for="newPsw"><spring:message code="editProfileForm.newPassword"/></label>
                        <div>
                            <input autocomplete="off" type="password" class="form-control"  id="newPsw" placeholder='<spring:message code="studentEditProfile.enterNewpassword"/>'  name="newPassword" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button  onclick="changePass()" type="submit" class="btn btn-default"><spring:message code="editProfileForm.changePass"/></button>
                        </div>
                    </div>
                </form>
                <%--</form>--%>

            </div>
            <%--PARENT CARD--%>








    </div>
    <!--MAIN CARD CONTAINER -->


</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {
        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd',
            changeMonth:true,
            changeYear:true,
            yearRange: '-100y:c+nn'
        });

    });



    $('#editUserForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/student/profile/edit/${userId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert(data);
                location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $('#editUserSettingsForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/change-user-settings/${userId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert(data);
                location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    function changePass() {


        $('#changePassForm').submit(function(e){
            e.preventDefault();
            $.ajax({
                url:'${url}/student/profile/edit/change-password',
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    $('#errorText').text(data);
                    $(".toolTipBox").css("display", "inline-block");
                    $('#curntPsw').val('');
                    $('#newPsw').val('');
                    setTimeout(function(){
                        $(".toolTipBox").css("display", "none");
                    },2000)
                },
                error : function(error){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });

    }



</script>

</body>

</html>

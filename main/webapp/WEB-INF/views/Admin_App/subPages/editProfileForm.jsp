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
    <c:when test="${nav eq 1}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/view/user-profile" class="icon-home"><spring:message code="editProfileForm.myProfile"/></a></li>
                <li class="last active"><a ><spring:message code="editProfileForm.editUser"/></a></li>
            </ul>
        </div
    </c:when>
    <c:otherwise>
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-users/users" class="icon-home"><spring:message code="manageUsers.title1"/></a></li>
            <li class="last active"><a ><spring:message code="editProfileForm.editUser"/></a></li>
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
                    <h1><spring:message code="editProfileForm.editCurrentUser"/></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="editUserForm" class="form-horizontal"  method="post">


                    <%--FORM HEADER--%>
                    <h3><spring:message code="studentForm.userInformation"/></h3>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="userId"><spring:message code="studentForm.userId"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="userId" autocomplete="off" disabled placeholder="${userProfile.userId}" name="userId" required>
                            </div>
                        </div>

                        <div <c:if test="${nav eq 1}"> style="display: none"</c:if> class="form-group">
                            <label class="control-label col-sm-3" for="role"><spring:message code="manageUsers2.role"/></label>
                            <div class="col-sm-9">
                                <select class="form-control" id="role" name="role" >
                                    <option>${userProfile.role}</option>
                                    <option value="Student"><spring:message code="addUserForm.student"/></option>
                                    <option value="Teacher"><spring:message code="courseForm.teacher"/></option>
                                    <option value="Admin"><spring:message code="editProfileForm.admin"/></option>
                                </select>
                            </div>
                        </div>

                        <c:if test="${nav ne 1}">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="userClass"><spring:message code="enrollmentList.class"/></label>
                                <div class="col-sm-9">
                                    <input type="text" value="${userProfile.majorName}/${userProfile.className}" class="form-control" autocomplete="off" id="userClass" name="userClass" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="intake"><spring:message code="manageUsers2.intake"/></label>
                                <div class="col-sm-9">
                                    <input type="intake" class="form-control" disabled id="intake" name="intake" value="${userProfile.intake}" required>
                                </div>
                            </div>
                        </c:if>

                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>
                    <%--<h3><spring:message code="studentForm.userInformation"/></h3>--%>
                    <%--FORM BOX 2/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="firstName"><spring:message code="studentForm.firstName"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="firstName" placeholder="" value="${userProfile.firstName}" name="firstName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="lastName"><spring:message code="studentForm.lastName"/> </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="lastName" placeholder="" value="${userProfile.lastName}" name="lastName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="gender"><spring:message code="manageUsers.gender"/></label>
                            <div class="col-sm-9">
                                <select  class="form-control" id="gender" name="gender" required>
                                    <option>${userProfile.gender}</option>
                                    <option>Male</option>
                                    <option>Female</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="dateOfBirth"><spring:message code="studentForm.dateOfBirth"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="dateOfBirth" placeholder="" value="${userProfile.dateOfBirth}" name="dateOfBirth"  >
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
                                <input type="text" class="form-control" id="city" placeholder="" value="${userProfile.city}" name="city" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="country"><spring:message code="manageUsers.country"/></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control"  id="country" placeholder="" value="${userProfile.country}" name="country" >
                            </div>
                        </div>

                    </div>

                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button style="color: inherit" <c:if test="${currentUserRole ne 'admin'}">disabled </c:if> id="submit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>


            </div>
            <%--BIG CARD CONTAINER BOX--%>




        </div>
        <!--BIG CONTAINER -->






        <!--Small cards-->
        <br/>
        <div class="col-sm-3 smallCardContainerParent">
            <c:if test="${currentUserRole eq 'admin'}">
                <%--PARENT CARD--%>
                <div class="card bigCardContainer custm">

                    <div class="formTitleBox settingsTitleBox">
                        <h1><spring:message code="studentForm.settings"/></h1>
                    </div>

                    <form id="editUserSettingsForm"  method="post">
                        <c:if test="${userProfile.role eq 'Student'}">
                            <div class="form-group">
                                <label class="control-label " for="major"><spring:message code="manageCourse2.major"/></label>
                                <div >
                                    <select class="form-control" id="major"  name="majorId" >
                                        <option value="${userProfile.majorId}">${userProfile.majorName}</option>
                                        <c:forEach var = "sysMajor" items="${majorSelectList}" varStatus="index">
                                            <c:if test="${userProfile.majorName ne sysMajor.majorName }">
                                                <option value="${sysMajor.majorId}">${sysMajor.majorName}</option>
                                            </c:if>
                                        </c:forEach>


                                    </select>

                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label " for="degreeType"><spring:message code="courseForm.degreeType"/></label>
                                <div >
                                    <select autocomplete="off" class="form-control" id="degreeType" name="degreeType">
                                        <option>${userProfile.degreeType}</option>
                                        <option>Double-Degree</option>
                                        <option>General-Degree</option>
                                    </select>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label class="control-label " for="primaryLanguage"><spring:message code="studentForm.primaryLanguage"/></label>
                            <div >
                                <select  autocomplete="off"  class="form-control" id="primaryLanguage" name="primaryLanguage">
                                    <option>${userProfile.primaryLanguage}</option>
                                    <option>English</option>
                                    <option>Chinese</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default"><spring:message code="studentForm.saveSettings"/></button>
                            </div>
                        </div>
                    </form>

                </div>

                    <c:if test="${nav eq 1}">
                        <div class="card bigCardContainer ">

                            <div class="formTitleBox settingsTitleBox">
                                <h1><spring:message code="editProfileForm.changePassword"/></h1>
                            </div>

                                <%--<form id="editUserPasswordForm" method="post">--%>
                            <form id="changePassForm"  method="post" >
                                <div class="form-group">
                                    <div class="toolTipBox">
                                        <span id="errorText" class="tooltipItem"></span><br/>
                                    </div>
                                    <label class="control-label " for="curntPsw"><spring:message code="editProfileForm.currentPassword"/></label>
                                    <div >
                                        <input autocomplete="off" type="password" class="form-control"  id="curntPsw" placeholder='<spring:message code="editProfileForm.msg1"/>' name="currentPassword" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label " for="newPsw"><spring:message code="editProfileForm.newPassword"/></label>
                                    <div>
                                        <input autocomplete="off" type="password" class="form-control"  id="newPsw" placeholder='<spring:message code="editProfileForm.msg1"/>'  name="newPassword" required>
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
                    </c:if>



                <c:if test="${nav ne 1}">
                    <div class="card bigCardContainer ">

                        <div class="formTitleBox settingsTitleBox">

                            <h1><spring:message code="editProfileForm.resetPassword"/></h1>

                        </div>

                        <%--<form id="editUserPasswordForm" method="post">--%>
                            <div class="form-group">
                                <label class="control-label " for="degreeType"><spring:message code="editProfileForm.selectDefaultpassword"/></label>
                                <div >
                                    <select class="form-control" id="passwordSelect" name="passwordSelect">
                                        <option value="${userProfile.userId}"><spring:message code="studentForm.userId"/></option>
                                        <option value="${userProfile.firstName}"><spring:message code="studentForm.firstName"/></option>
                                        <option value="${userProfile.lastName}"><spring:message code="studentForm.lastName"/></option>
                                        <option value="${userProfile.lastName}${userProfile.userId}"><spring:message code="studentForm.lastName"/> + <spring:message code="studentForm.userId"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button  onclick="resetPassword()" class="btn btn-default"><spring:message code="editProfileForm.resetPassword"/></button>
                                </div>
                            </div>
                        <%--</form>--%>


                    </div>
                <%--PARENT CARD--%>
                </c:if>
            </c:if>


           <c:if test="${nav ne 1}">
               <div class="card bigCardContainer ">

                   <h1><spring:message code="editProfileForm.lastEditedUser"/></h1>


                   <div class="table-responsive">
                       <table class="table table-condensed">
                           <c:forEach var = "leList" items="${lastEditedUsers}">
                               <tr>
                                   <td ><c:out value="${leList.userId}"/></td>
                                   <td ><c:out value="${leList.userName}"/></td>
                               </tr>
                           </c:forEach>

                           </tbody>
                       </table>
                   </div>

               </div>
           </c:if>



        </div>
        <!--Small cards-->







    </div>
    <!--MAIN CARD CONTAINER -->


</div>
<!--Content Ends  -->

<script>


    $('#editUserForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/profile/edit/${userId}',
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

    function resetPassword() {

        var t = $('#passwordSelect').val();
//        alert(t)
        var r = confirm('<spring:message code="editProfileForm.msg3"/>');
        if (r == true) {

                $.ajax({
                    url:'${url}/admin/profile/edit/reset-password/${userId}',
                    type:'post',
                    data: {passwordSelect:t},
                    success:function(data){
                        alert(data);
                        location.reload(true);
                    },
                    error : function(){
                        alert('<spring:message code="main.msgError" />');
                    }
                });

        } else{
           // do nothing
        }

    }

    function changePassword() {

        var currentPassword = $('#curntPsw').val();
        var newPassword = $('#newPsw').val();


        if(newPassword != '' && currentPassword != ''){
            $.ajax({
                url:'${url}/admin/profile/edit/change-password',
                type:'post',
                success:function(data){
                    alert(data);
                },
                error : function(error){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }
        else{
            alert('<spring:message code="editProfileForm.msg4"/>');
        }
    }

    function changePass() {


        $('#changePassForm').submit(function(e){
            e.preventDefault();
            $.ajax({
                url:'${url}/admin/profile/edit/change-password',
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

//

</script>

</body>

</html>

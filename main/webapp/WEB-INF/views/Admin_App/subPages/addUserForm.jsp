<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 15/08/2017
  Time: 23:44
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
    <title><spring:message code="addUserForm.addUser"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery-ui2.css"></script>
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
</head>

<body>


<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-users/users" class="icon-home"> <spring:message code="manageUsers.title1"/></a></li>
            <li class="last active"><a ><spring:message code="addUserForm.addUser"/></a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">


            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="addUserForm.createUser"/></h1>
                </div>


                <%--FORM--%>
                    <%--<h3>Basic</h3>--%>
                    <form id="addUserForm" class="form-horizontal"  method="post">


                        <%--FORM HEADER--%>
                        <%--<h3><spring:message code="addCourseForm.basic"/></h3>--%>
                        <h3><spring:message code="studentForm.userInformation"/></h3>
                        <%--FORM BOX 1/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="userId"><spring:message code="studentForm.userId"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="userId" autocomplete="off" placeholder='<spring:message code="placeHolder.enterUserId"/>' name="userId" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="role"><spring:message code="manageUsers2.role"/></label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="role" name="role">
                                        <c:if test="${nav eq 1}"><option value="Student"><spring:message code="addUserForm.student"/></option></c:if>
                                        <c:if test="${nav eq 2}">
                                            <option value="Teacher"><spring:message code="addUserForm.teacher"/></option>
                                            <option value="Admin"><spring:message code="addUserForm.admin"/></option>
                                        </c:if>
                                    </select>
                                </div>
                            </div>

                           <c:if test="${nav eq 1}">
                               <%--<div class="form-group">--%>
                                   <%--<label class="control-label col-sm-3" for="userClass">Class</label>--%>
                                   <%--<div class="col-sm-9">--%>
                                       <%--<input type="text" class="form-control" autocomplete="off" id="userClass" placeholder="Enter User class" name="classId">--%>
                                   <%--</div>--%>
                               <%--</div>--%>
                               <%--<div class="form-group">--%>
                                   <%--<label class="control-label col-sm-3" for="majorSelect"><spring:message code="manageCourse2.major" /></label>--%>
                                   <%--<div class="col-sm-9">--%>
                                       <%--<select  autocomplete="off" id="majorSelect" class="form-control span3" onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' name="majorId" required>--%>
                                           <%--<option value=""><spring:message code="manageGrades2.selectMajor" /></option>--%>
                                           <%--<c:forEach var = "major" items="${majorSelectList}">--%>
                                               <%--<option value="${major.majorId}"><c:out value="${major.majorName}"/></option>--%>
                                           <%--</c:forEach>--%>
                                       <%--</select>--%>
                                   <%--</div>--%>
                               <%--</div>--%>

                               <div class="form-group">
                                   <label class="control-label col-sm-3" for="userClass"><spring:message code="enrollmentList.class"/></label>
                                   <div class="col-sm-9">
                                       <select onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' autocomplete="off" class="form-control" id="userClass" name="classId" >
                                           <option value=""><spring:message code="addUserForm.chooseStudentClass"/></option>
                                           <c:forEach var = "clList" items="${classList}">
                                               <option value="${clList.id}">${clList.majorShortName} | ${clList.className}</option>
                                           </c:forEach>
                                       </select>
                                   </div>
                               </div>

                               <%--<div class="form-group">--%>
                                   <%--<label class="control-label col-sm-3" for="intake"><spring:message code="manageUsers2.intake"/></label>--%>
                                   <%--<div class="col-sm-9">--%>
                                       <%--<select onfocus='this.size=10;' onblur='this.size=1;' onchange='this.size=1; this.blur();' class="form-control" id="intake" name="intake">--%>
                                           <%--<c:forEach var = "sIntake" items="${studentIntakeList}">--%>
                                               <%--<option value="${sIntake}">${sIntake}</option>--%>
                                           <%--</c:forEach>--%>
                                       <%--</select>--%>
                                   <%--</div>--%>
                               <%--</div>--%>

                               <div class="form-group">
                                   <label class="control-label col-sm-3" for="degreetype"><spring:message code="courseForm.degreeType"/></label>
                                   <div class="col-sm-9">
                                       <select class="form-control" id="degreeType" name="degreeType" required>
                                           <option value="double-degree"><spring:message code="addUserForm.doubleDegree"/></option>
                                           <option value="chinese-degree"><spring:message code="addUserForm.chineseDegree"/></option>
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
                            <%--<h3><spring:message code="studentForm.userInformation"/></h3>--%>
                            <%--FORM BOX 2/--%>
                            <div class="formBox">

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="firstName"><spring:message code="studentForm.firstName"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" autocomplete="off" id="firstName" placeholder='<spring:message code="placeHolder.enterFirstName"/>' name="firstName" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="lastName"><spring:message code="studentForm.lastName"/> </label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" autocomplete="off" id="lastName" placeholder='<spring:message code="placeHolder.enterLastName"/>' name="lastName" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="gender"><spring:message code="manageUsers.gender"/></label>
                                    <div class="col-sm-9">
                                        <select class="form-control" id="gender" name="gender" required>
                                            <%--<option><spring:message code="addUserForm.chooseGender"/></option>--%>
                                            <option><spring:message code="addUserForm.male"/></option>
                                            <option><spring:message code="addUserForm.female"/></option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="dateOfBirth"><spring:message code="studentForm.dateOfBirth"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control datepicker" autocomplete="off" id="dateOfBirth" placeholder="0000-00-00" name="dateOfBirth"  >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="email"><spring:message code="studentForm.email"/></label>
                                    <div class="col-sm-9">
                                        <input type="email" class="form-control" autocomplete="off" id="email" placeholder='<spring:message code="placeHolder.enterEmail"/>' name="email" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="city"><spring:message code="addUserForm.cityTown"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="city" placeholder='<spring:message code="placeHolder.enterCityOrTown"/>' name="city" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="country"><spring:message code="manageUsers.country"/></label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control"  id="country" placeholder='<spring:message code="placeHolder.enterCountry"/>' name="country" >
                                    </div>
                                </div>

                            </div>
                            <%--FORM BOX2--%>
                            <%--FORM DIVIDER--%>
                            <div class="formDivider"></div>
                            <%--FORM DIVIDER--%>


                            <%--FORM HEADER--%>
                            <h3><spring:message code="studentForm.defaultPassword"/></h3>
                            <%--FORM BOX 1/--%>
                            <div class="formBox">


                                    <div class="form-group">
                                        <label class="control-label col-sm-5" for="passwordSelect"><spring:message code="studentForm.selectDefaultPsw"/></label>
                                        <div class="col-sm-7">
                                            <select class="form-control" id="passwordSelect" name="passwordSelect">
                                                <option value="1"><spring:message code="studentForm.userId"/></option>
                                                <option value="2"><spring:message code="studentForm.firstName"/></option>
                                                <option value="3"><spring:message code="studentForm.lastName"/></option>
                                            </select>
                                        </div>
                                    </div>


                            </div>
                            <%--FORM BOX1--%>
                            <%--FORM DIVIDER--%>
                            <div class="formDivider"></div>
                            <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class="submitBtnBox">
                                <button id="submit" type="submit" class="btn btn-default"><spring:message code="addUserForm.createFUser"/></button>
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
                    <%--<div >--%>
                        <%--<select class="form-control" id="degreeType" name="degreeType">--%>
                            <%--<option>Double-Degree</option>--%>
                            <%--<option>General-Degree</option>--%>
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

                        <h1><spring:message code="studentForm.lastAddedUsers"/></h1>


                    <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "laList" items="${lastAdded}">
                            <tr>
                                <td ><c:out value="${laList.userId}"/></td>
                                <td ><c:out value="${laList.userName}"/></td>
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

        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd',
            changeMonth:true,
            changeYear:true,
            yearRange: '-100y:c+nn'
        });


    });

    $('#addUserForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/process-add-user',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                console.log(data);
                if(data === "200"){
                    var r = confirm('<spring:message code="addUserForm.msg1"/>');
                    if (r == true) {
                        location.reload(true);
                    } else{
                        window.location.href = '${url}/admin/manage-users/users';
                    }
                }
                if(data === "501"){
                    alert('<spring:message code="addUserForm.msg2"/>');
                }
                if(data === "502"){
                    alert('UserId Formate Error Only Integers Allowed ');
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

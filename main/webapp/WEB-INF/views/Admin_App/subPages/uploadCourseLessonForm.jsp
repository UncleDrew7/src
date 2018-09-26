<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/09/2017
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
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

<%--template database --%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>


<html lang="en">

<head>
    <title>Admin| Upload Course Lesson</title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
</head>

<body>
<%--TEST DATASOURCE --%>
<sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
                   url = "jdbc:mysql://192.168.10.30:3306/ucms"
                   user = "root"  password = "cdai"/>
<sql:query dataSource = "${snapshot}" var = "last5AddedUsers">
    SELECT usrp.first_name,usrp.last_name,usrp.user_id
    FROM ucms.user_profile usrp
    ORDER BY usrp.created_at DESC,usrp.user_id DESC
    LIMIT 5;
</sql:query>

<!--Content Begins  -->
<div class="content">

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">
            <h4><spring:message code="studentPageTitle.home" /></h4>


            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1>Create New User</h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="addUserForm" class="form-horizontal"  method="post">


                    <%--FORM HEADER--%>
                    <h3>Basic</h3>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="userId">User ID</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="userId" autocomplete="off" placeholder="Enter User Id" name="userId" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="role">Role</label>
                            <div class="col-sm-9">
                                <select class="form-control" id="role" name="role">
                                    <option>Student</option>
                                    <option>Teacher</option>
                                    <option>Admin</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="userClass">Class</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="userClass" placeholder="Enter User class" name="userClass" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="intake">Intake</label>
                            <div class="col-sm-9">
                                <input type="intake" class="form-control" id="intake" placeholder="Enter Intake" name="intake" value="Fall 2017" required>
                            </div>
                        </div>

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
                            <label class="control-label col-sm-3" for="firstName">First Name</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="firstName" placeholder="Enter First Name" name="firstName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="lastName">Last Name </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="lastName" placeholder="Enter Last Name" name="lastName" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="gender">Gender</label>
                            <div class="col-sm-9">
                                <select class="form-control" id="gender" name="gender" required>
                                    <option>Choose Gender</option>
                                    <option>Male</option>
                                    <option>Female</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="dateOfBirth">Date Of Birth</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" autocomplete="off" id="dateOfBirth" placeholder="0000-00-00" name="dateOfBirth" required >
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="email">Email</label>
                            <div class="col-sm-9">
                                <input type="email" class="form-control" autocomplete="off" id="email" placeholder="Enter Email" name="email" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="city">City/Town</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="city" placeholder="Enter City/Town" name="city" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="country">Country</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control"  id="country" placeholder="Enter Country" name="country" required>
                            </div>
                        </div>

                    </div>
                    <%--FORM BOX2--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>
                    <h3>Default Password</h3>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">


                        <div class="form-group">
                            <label class="control-label col-sm-3" ></label>
                            <div class="col-sm-9">
                                <label class="radio-inline ">
                                    <input type="radio"  name="radioSelect">Choose default Passwords
                                </label>
                                <label class="radio-inline ">
                                    <input type="radio" name="radioSelect">Create new default Password
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-5" for="passwordSelect">Select Default Password:</label>
                            <div class="col-sm-7">
                                <select class="form-control" id="passwordSelect" name="passwordSelect">
                                    <option>User ID</option>
                                    <option>First Name</option>
                                    <option>Last Name</option>
                                    <option>Last Name + User Id</option>
                                </select>
                            </div>
                        </div>

                        <fieldset disabled>
                            <div class="form-group">
                                <label class="control-label col-sm-5" for="defaultPassword">Create Default Password</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" autocomplete="off" id="defaultPassword" placeholder="Enter user default password" name="defaultPassword">
                                </div>
                            </div>
                        </fieldset>


                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button id="submit" type="submit" class="btn btn-default">Create User</button>
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
            <div class="card bigCardContainer ">

                <div class="formTitleBox settingsTitleBox">
                    <h1>Settings</h1>
                </div>



                <div class="form-group">
                    <label class="control-label " for="degreeType">Degree Type</label>
                    <div >
                        <select class="form-control" id="degreeType" name="degreeType">
                            <option>Double-Degree</option>
                            <option>General-Degree</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label " for="primaryLanguage">Primary Language</label>
                    <div >
                        <select class="form-control" id="primaryLanguage" name="primaryLanguage">
                            <option>English</option>
                            <option>Chinese</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Save Settings</button>
                    </div>
                </div>

            </div>
            <%--PARENT CARD--%>


            <div class="card bigCardContainer ">

                <h1>Last added users</h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "row" items="${last5AddedUsers.rows}">
                            <tr>
                                <td ><c:out value="${row.user_id}"/></td>
                                <td ><c:out value="${row.first_name}"/> <c:out value="${row.last_name}"/></td>
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
        $('#addUserForm').formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                email: {
                    // All the email address field have emailAddress class
                    selector: '.emailAddress',
                    validators: {
                        callback: {
                            message: 'You must enter at least one email address',
                            callback: function(value, validator, $field) {
                                var isEmpty = true,
                                    // Get the list of fields
                                    $fields = validator.getFieldElements('email');
                                for (var i = 0; i < $fields.length; i++) {
                                    if ($fields.eq(i).val() !== '') {
                                        isEmpty = false;
                                        break;
                                    }
                                }

                                if (!isEmpty) {
                                    // Update the status of callback validator for all fields
                                    validator.updateStatus('email', validator.STATUS_VALID, 'callback');
                                    return true;
                                }

                                return false;
                            }
                        },
                        emailAddress: {
                            message: 'The value is not a valid email address'
                        }
                    }
                }
            }
        });
    });

    $('#addUserForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/process-add-user',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
//                alert("worked");
                var r = confirm("Success! Do you want to add more users ?");
                if (r == true) {
                    location.reload(true);
                } else{
                    window.location.href = '${url}/admin/manage-users/users';
                }

                <%--if (confirm('Success! Do you want to add more users ?')) {--%>
                <%----%>
                <%--}--%>
                <%--else{--%>
                <%--window.location.href = '${url}/admin/manage-users/users';--%>
                <%--}--%>
            },
            error : function(){
                alert("error");
            }
        });
    });
</script>

</body>

</html>

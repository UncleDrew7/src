<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 28/08/2017
  Time: 06:32
  To change this template use File | Settings | File Templates.
--%>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title>Login</title>
    <link rel="stylesheet" href="${url}/resources/main/bootstrap/css/bootstrap.css" />
    <link href="${url}/resources/authentication/css/login.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery.js"></script>
    <script src="${url}/resources/main/bootstrap/js/bootstrap.js"></script>
</head>

<body>

<!--Content Begins  -->
<div class="content">

        <div class="wrapper">


            <div class="container">

                <h1>CDAI</h1>
                <h4>Chinesisch-Deutsches Institut fur Angewandte Ing/hieurwissenschaften</h4>

                <form id="loginForm" class="form">
                    <input name="userId" type="text" placeholder="User Id" required autocomplete="on">
                    <input style="display: none" type="password">
                    <input name="password" type="password" placeholder="Password" id="password" required autocomplete="off">
                    <label class="checkbox">
                        <input style="color: #fff" type="checkbox" value="remember-me" id="rememberMe" name="remember"> Remember me
                    </label>
                    <%--<button type="submit" value="Login" />--%>
                    <button id="submit" type="submit" style="" class="btn btn-default">Login</button>
                </form>
                <div style="display: none" id="msg" class=" valMsg alert alert-danger ">
                    <%--<strong >User name or Password is Incorrect! </strong>--%>
                </div>
            </div>
j
            <ul class="bg-bubbles">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>





</div>
<!--Content Ends  -->



<!-- Modal -->
<div class="modal fade" id="editModel" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-body">

                <div class="content">

                    <div class="wrapper">

                        <div class="container">
                            <div class="spinner spinner-1"><i class="loading">Loading...</i></div>

                        </div>

                    </div>
                </div>




            </div>
        </div>
    </div>
</div>


<script>


//    $(document).ready(function() {
//        $('#editModel').modal('toggle');
//    });

    $('#loginForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/loginProcess',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                // $('#editModel').modal('toggle');
                if(data === '200') {

                    window.location.href = '${url}/admin/dashboard';
                }
                if(data === '201'){
                    window.location.href = '${url}/student/home';
                }
                if(data === '400'){
                    // $('#editModel').modal('toggle');
                    $('#msg').empty();
                    $("#msg").css("display", "block");
                    $('#msg').append(
                        '<strong>'+
                            'User name or password is Incorrect'+
                            '</srong>');

                    $('#password').val("");
                    setTimeout(function(){
                        $("#msg").css("display", "none");
                    },2000)

                }else{
                    // $('#editModel').modal('toggle');
                    $('#msg').empty();
                    $('#msg').append(
                        '<strong>'+
                        'User does not exist'+
                        '</srong>');
                }
            },
            error : function(){
                // $('#editModel').modal('toggle');
                alert("Network connection failed!! ");
            }
        });
    });

    document.addEventListener("DOMContentLoaded", function(){
        var invalid = function(e){
            if(e.target.validity.valueMissing){
                e.target.setCustomValidity("Empty Input");
                return;
            }
        };



        var userId = document.getElementsByName("userId");
        userId[0].oninvalid = invalid;
        userId[0].oninput = function(e) {
            e.target.setCustomValidity("");
        };

        var password = document.getElementsByName("password");
        password[0].oninvalid = invalid;
        password[0].oninput = function(e) {
            e.target.setCustomValidity("");
        };


    });


</script>

</body>

</html>
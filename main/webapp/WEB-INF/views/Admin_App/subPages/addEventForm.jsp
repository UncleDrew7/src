    <%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 18/09/2017
  Time: 19:30
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
    <title><spring:message code="event3.title1" /></title>
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
    <script src="${url}/resources/main/ckeditor/ckeditor.js"></script>
    <link href="${url}/resources/main/summernote/summernote.css" rel="stylesheet">
    <script src="${url}/resources/main/summernote/summernote.js"></script>
</head>

<body>
<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <c:choose>
            <c:when test="${nav eq 1}">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
                    <li class="last active"><a href="#"><spring:message code="event.title1" /></a></li>
                </ul>
            </c:when>
            <c:when test="${nav eq 2}">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/events/list" class="icon-home"><spring:message code="event3.eventList" /></a></li>
                    <li class="last active"><a href="#"><spring:message code="event.title1" /></a></li>
                </ul>
            </c:when>
        </c:choose>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">



            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <h1><spring:message code="event.title2" /></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="addEventForm" class="form-horizontal"  method="post">

                    <h4><spring:message code="event.eventTitle" /></h4>
                    <div class="formBox">
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="text" <c:if test="${action eq 'edit'}"> value="${event.title}" </c:if> class="form-control" autocomplete="off" id="userClass" placeholder="<spring:message code="event.eventTitlePlaceHolder" />"  name="eventName" required>
                            </div>
                        </div>
                    </div>


                    <%--FORM HEADER--%>
                    <h4><spring:message code="event.eventType" /></h4>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">
                        <div class="form-group">
                            <div class="col-sm-12">
                                <select <c:if test="${action eq 'edit'}"> disabled </c:if> autocomplete="off" required class="form-control" id="role" name="eventType">
                                    <option value="all"><spring:message code="event3.systemNotification" /></option>
                                    <option value="user"><spring:message code="event3.personalNotification" /></option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <%--FORM HEADER--%>
                    <h4><spring:message code="event.date" /></h4>
                    <%--FORM BOX 1/--%>
                    <div class="formBox">
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input style="cursor: pointer" autocomplete="off" type="text" <c:if test="${action eq 'edit'}"> value="${event.eventDateTime}" </c:if> class="form-control datepicker" id="eventDate" placeholder="<spring:message code="event.eventDatePlaceHolder" />" name="eventDate" required>
                            </div>
                        </div>
                    </div>

                    <%--FORM HEADER--%>
                    <h4><spring:message code="event.description" /></h4>
                    <%--FORM BOX 2/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <%--<div class="col-sm-2"></div>--%>
                            <div class="col-sm-12">
                                <textarea  name="eventDescription" required placeholder="<spring:message code="event.eventDescriptionPlaceHolder" />" class="form-control" rows="5" id="comment"><c:if test="${action eq 'edit'}"> ${event.description} </c:if></textarea>
                                <%--<textarea name="eventDescription" required></textarea>--%>
                                <%--<textarea autocomplete="off" type="text" name="eventDescription" placeholder="Event description" required></textarea>--%>
                            </div>
                        </div>

                    </div>
                    <%--FORM BOX2--%>

                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">

                            <c:choose>
                                <c:when test="${action eq 'edit'}">
                                    <button id="submit" type="submit" class="btn btn-default"><spring:message code="coursePage.editEvent" /></button>
                                </c:when>
                                <c:otherwise>
                                    <button id="submit" type="submit" class="btn btn-default"><spring:message code="event.createEvent" /></button>
                                </c:otherwise>
                            </c:choose>
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
                    <h1><spring:message code="sharedPageLable.upcomingEvent" /></h1>
                </div>

                <ul class="eventList">
                    <c:forEach varStatus="index"  var="eventList" items="${upcomingEvents}">
                        <li>
                            <div class="notificationObject">
                                <div class="ntObjectCase">
                                    <div class="dateCase">
                                        <div class="dash-item__content">
                                            <div class="a">
                                                <ul class="quiz-results">
                                                    <li class="quiz-results__item quiz-results__item--average-score">
                                                        <span class="dayNumber"><c:out value="${eventList.eventDay}"/></span>
                                                        <div class="monthSm"><c:out value="${fn:substring(eventList.eventMonth,0,3)}"/></div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="textCase">
                                        <div style="text-align: left; font-weight: 400"><c:out value="${eventList.title}"/></div>
                                        <p style="text-align: left"><c:out value="${fn:substring(eventList.description,0,30)}" />...</p>
                                    </div>
                                    <div class="dateCase">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>

                <c:if test="${fn:length(upcomingEvents) gt 2 }">
                    <span class="viewMore"><spring:message code="commonLable.showMore" /></span>
                </c:if>


            </div>
            <%--PARENT CARD--%>


            <div class="card bigCardContainer ">

                <h1><spring:message code="event.LastAddedEvents" /></h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "events" items="${lastAddedEvents}">
                            <tr>
                                <td ><c:out value="${events.eventDay}"/>&nbsp;<c:out value="${fn:substring(events.eventMonth,0,3)}"/></td>
                                <td ><c:out value="${events.title}"/> </td>
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
//        CKEDITOR.replace( 'eventDescription' );
        $('#summernote').summernote({
            minHeight: 200,
            maxHeight:200,
            placeholder: '<spring:message code="event3.placeholder1" />'
        });
        ///date
        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        });



    });

    $('#addEventForm').submit(function(e){
        e.preventDefault();
        console.log($('#summernote').summernote('code'));
        <c:choose>
        <c:when test="${action eq 'edit'}">
                $.ajax({
                    url:'${url}/admin/manage-course/update-course-event/${eventId}',
                    type:'post',
                    data:$(this).serialize(),
                    success:function(data){
                        alert("Event Updated Successfully");
                        window.location.href = '${url}/admin/dashboard';

                    },
                    error : function(){
                        alert('<spring:message code="main.msgError" />');
                    }
                });
            </c:when>
            <c:otherwise>
                $.ajax({
                    url:'${url}/admin/event/add/',
                    type:'post',
                    data:$(this).serialize(),
                    success:function(data){
    //                alert("worked");
                        var r = confirm('<spring:message code="event3.msg1" />');
                        if (r == true) {
                            location.reload(true);
                        } else{
                            window.location.href = '${url}/admin/dashboard';
                        }

                    },
                    error : function(){
                        alert('<spring:message code="main.msgError" />');
                    }
                });
            </c:otherwise>

            </c:choose>

    });
</script>

</body>

</html>

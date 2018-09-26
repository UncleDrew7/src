<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 15/08/2017
  Time: 23:48
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<head>
    <title><spring:message code="coursePage.text1"/> </title>
    <link href="${url}/resources/app_admin_static/css/subPages/coursePage.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>

<body >
<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
            <li class="last active"><a > ${courseDetails.courseShortName}</a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="">
        <!--Small cards-->
        <div class="col-sm-3 smallParent">

            <%--$$$$$$$--%>
            <div class="notifcationContainer bigCardContainer">
                <div class="sectionContainerHeaderBox">
                    <h4><spring:message code="sharedPageLable.upcomingEvent" /> <a onClick=" addCourseEvent()"><c:if test="${hasPermission or currentUserRole eq 'admin'}">&nbsp;<span  class="inIcon glyphicon glyphicon-plus-sign" aria-hidden="true"></span></c:if></a></h4>
                </div>
                <c:if test="${empty courseEventList}">
                    <div style="text-align: center"><i><spring:message code="coursePage.noUpcomingEvents"/></i></div>
                </c:if>
                <ul class="eventList elBolck">
                    <c:forEach varStatus="index"  var="eventList" items="${courseEventList}">
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
                                    <div onclick="showNotification('${eventList.title}','${eventList.description}')"  class="textCase">
                                        <div class="eventText"><c:out value="${eventList.title}"/></div>
                                        <p  class="courseOthersText"><c:out value="${fn:substring(eventList.description,0,30)}" />...</p>
                                    </div>
                                    <div class="dateCase">
                                        <div class="dash-item__content">
                                            <div class="a">
                                                <ul class="quiz-results">
                                                    <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                                                        <li class="quiz-results__item quiz-results__item--average-score">
                                                            <a onclick="editEvent(${eventList.event_id})"><span onclick="" style="margin-left:25px " class="inIcon inIconAni glyphicon glyphicon-edit" aria-hidden="true"></span></a><br/>
                                                            <a onclick="deleteEvent(${eventList.event_id})"><span style="margin-left:25px " class="inIcon inIconAni glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>

                <c:if test="${fn:length(courseEventList) gt 2 }">
                    <span class="viewMore"><spring:message code="commonLable.showMore" /></span>
                </c:if>
            </div>

            <%--$$$$$$$--%>
            <div class="sectionContainer bigCardContainer">
                <div class="sectionContainerHeaderBox">
                    <h4><spring:message code="system.currentCourses" /></h4>
                </div>

                <div >
                    <c:if test="${empty lessonContentList}">
                        <div style="text-align: center"><i><spring:message code="coursePage.noLessonsUploaded"/></i></div>
                    </c:if>
                    <ul>
                        <c:forEach var = "lessonContentIndex" items="${lessonContentList}">
                            <li>
                                <a href="#${lessonContentIndex.lessonId}"><button class="button topicBox"><c:out value="${lessonContentIndex.lessonName}"/></button></a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

            </div>

        </div>
        <!--Small cards-->
        <div class="col-sm-9 courseContainer">
            <h3 class="title">${courseDetails.courseName}</h3>
            <%--COURSE DESCRIPTION CONTAINER --%>
                <%--<a class="courseButton"  data-toggle="modal" data-target="#importCourseExcel" href="#">Edit Course</a>--%>
            <%-------------------------------------------------------------%>
            <div class=" card  courseHeaderBox">


                <div class="courseSettingsBox">
                    <a href="${url}/admin/manage-course/edit/${courseDetails.childCourseId}/2"><spring:message code="coursePage.editCourse" /></a>
                </div>
               <div>
                   <span class="introText"><spring:message code="coursePage.aboutCourse" /></span>
                   <div id="aboutTextBox">
                       <p>
                           ${courseDetails.courseDescriptionEn}
                       </p>
                   </div>
                   <a id="more" class="hideText" href="#"><spring:message code="coursePage.readMore" /> </a>
                   <a id="less"  class="hideText" href="#"><spring:message code="extra.showLess" /> </a>
                   <br/><br/>
                   <span class="introText"><spring:message code="coursePage.teacher" /> : </span><span><a href="${url}/admin/view/${courseDetails.teacherId}">${courseDetails.teacherName}</a></span><br/>
                   <%--<span>Credits:4</span><br/>--%>
                   <%--<span>Course semester: Spring 2017</span><br/>--%>
                   <%--<span>Course Type: All</span><br/>--%>
                   <span class="introText"><spring:message code="coursePage.contentLanguage" /> : <spring:message code="coursePage.english"/></span><br/>

               </div>


                <%--<span class="introText dates"><spring:message code="coursePage.from" />: &nbsp;${courseDetails.startDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="coursePage.to" />:&nbsp; ${courseDetails.endDate}</span><br/>--%>

                <div style="padding-top: 5px" class="btn-group btn-group-justified" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button onClick="courseGradeReport(${childCourseId})" type="button" class="btn btn-default"><a ><spring:message code="coursePage.gradeReport" /> </a></button>
                    </div>
                   <%-- <div class="btn-group" role="group">
                        <button id="toExams"type="button" class="btn btn-default"><a href=""><spring:message code="coursePage.exams" /> </a></button>
                    </div>--%>
                    <div class="btn-group" role="group">
                        <button onClick="enrollments(${childCourseId})" type="button" class="btn btn-default"><a >
                            <spring:message code="coursePage.enrolledStudents" />
                            <c:if test="${enrolmentRequestCounts ne 0}">
                                <span style="background: lightgrey;" class=" appColor badge">${enrolmentRequestCounts}</span>
                            </c:if>
                        </a></button>
                    </div>
                </div>

            </div>
            <%-----------------------------------------------------%>
            <%--COURSE DESCRIPTION CONTAINER --%>

            <%--COURSE DETAILS CONTAINER --%>
            <div class=" bigCardContainer courseSections">
                <c:if test="${empty lessonContentList}">
                    <div style="text-align: center"><i><spring:message code="coursePage.noLessonsUploaded"/></i></div>
                </c:if>
                <ul>
                    <c:forEach var = "lessonContent" items="${lessonContentList}">
                        <li id="${lessonContent.lessonId}">
                            <div class="sectionContent">
                                <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                                    <div class="sectionSettingsBox">
                                        <a style="cursor: pointer" onClick="uploadContent(${lessonContent.lessonId})"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span><spring:message code="coursePage.addContent" /> </a>&nbsp;&nbsp;
                                        <a style="cursor: pointer" onClick="editContent(${lessonContent.lessonId})"><span class="glyphicon  glyphicon-pencil" aria-hidden="true"></span> <spring:message code="coursePage.edit" /></a>&nbsp;&nbsp;
                                        <a style="cursor: pointer" onClick="postdata(${lessonContent.lessonId})"><span class="glyphicon  glyphicon-trash" aria-hidden="true"></span> <spring:message code="coursePage.delete" /></a>
                                    </div>
                                </c:if>


                                <span  class="sectionTitle"><c:out value="${lessonContent.lessonName}"/></span><br/>
                                <div class="contentInfo">
                                    <p>
                                        <c:out value="${lessonContent.description}" escapeXml="false"/>
                                    </p>
                                    <ul>
                                        <c:forEach var = "lessonUploads" items="${lessonUploadsList}">
                                        <c:choose>
                                        <c:when test="${lessonContent.lessonId eq lessonUploads.lessonId}">
                                            <li>
                                                <a href="#" onclick="openModal('${lessonUploads.uploadName}','${lessonUploads.uploadUrl}')">
                                                <%--<a href="${url}/view/docs/${lessonUploads.uploadUrl}">--%>
                                                    <div class="icon orange">
                                                        <span class="doc-icon ptt">⧉</span>
                                                        <span class="doc-type"></span>
                                                    </div>
                                                    <div class="downloadText"><c:out value="${lessonUploads.uploadName}"/></div>
                                                </a>
                                            </li>
                                        </c:when>
                                        <%--<c:otherwise>--%>
                                        <%--dont show--%>
                                        <%--</c:otherwise>--%>
                                        </c:choose>


                                    </c:forEach>

                                        <%--<li>--%>
                                            <%--<div class="icon red">--%>
                                                <%--<span class="doc-icon pdf">☷</span>--%>
                                                <%--<span class="doc-type">PDF</span>--%>
                                            <%--</div>--%>
                                            <%--<div class="downloadText"> <span >Chapter 1</span></div>--%>

                                        <%--</li>--%>
                                    </ul>
                                </div>

                            </div>
                        </li>
                  </c:forEach>

                </ul>
                <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                    <div id="add" class="sectionAddBox">
                        <span class="inIconAni glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <div><spring:message code="coursePage.addTopic" /></div>

                    </div>
                </c:if>

            </div>
            <%--COURSE DETAILS CONTAINER--%>
        </div>
        <%--<!--Big cards-->--%>
    </div>
    <%--<!--MAIN CARD CONTAINER -->--%>

</div>
<%--<!--Content Ends  -->--%>

<%--<!-- Modal -->--%>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div class="formTitleBox">
                    <h1><spring:message code="coursePage.createCourseEvent" /></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="addCourseEventForm" class="form-horizontal"  method="post">

                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="event"><spring:message code="extra.eventName" /></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="event" autocomplete="off" placeholder='<spring:message code="placeHolders.enterUpcominEvent"/>' name="eventName" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="eventDate"><spring:message code="extra.eventDate" /></label>
                            <div class="col-sm-9">
                                <input type="text" class="datepicker form-control" autocomplete="off" id="eventDate" placeholder='<spring:message code="placeHolders.enterEventDate"/>' name="eventDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="eventText"><spring:message code="extra.eventDescription" /></label>
                            <div class="col-sm-9">
                                <textarea type="text" class="form-control" id="eventText" placeholder='<spring:message code="placeHolders.enterEventDescription"/>' name="eventDescription" required></textarea>
                            </div>
                        </div>

                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button  id="submit" type="submit" class="btn btn-default"><spring:message code="event.createEvent" /></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="editModel" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div class="formTitleBox">
                    <h1><spring:message code="coursePage.editEvent" /></h1>
                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form id="editCourseEventForm" class="form-horizontal"  method="post">

                    <%--FORM BOX 1/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="event"><spring:message code="extra.eventName" /></label>
                            <div class="col-sm-9">
                                <input type="text"  id="eName" class="form-control"  autocomplete="off"  name="eventName" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="eventDate"><spring:message code="extra.eventDate" /> </label>
                            <div class="col-sm-9">
                                <input type="text" id="eDate" class="form-control" autocomplete="off"  name="eventDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="eventText"><spring:message code="extra.eventDescription" /></label>
                            <div class="col-sm-9">
                                <textarea type="text" id="eDes" class="form-control"  name="eventDescription" required></textarea>
                            </div>
                        </div>

                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button id="editBtn" onclick="submitEdit(this.value)" type="submit" class="btn btn-default"><spring:message code="coursePage.editEvent" /></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>
            </div>
        </div>
    </div>
</div>

<%--modal end--%>


<%--Event Modal--%>
<div id="eventModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span> <span class="sr-only"><spring:message code="events.close"/></span></button>
                <h4 style="text-align: center;" id="modalTitle" class="modal-title"></h4>
            </div>
            <div style="text-align: center;" id="modalBody" class="modal-body"></div>
            <div class="modal-footer">
                <%--<button type="button" value="${row.user_id}" class="btn btn-default" id="deleteEvent">Delete</button>--%>
                <%--<a class="btn btn-primary" id="eventUrl" target="_blank">Edit</a>--%>
            </div>
        </div>
    </div>
</div>
<%--Event Modal--%>


<!-- Modal Iframe-->

<div class="modal fade" id="iframeModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button id="iframeCls" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <div id="fullsecreen"></div>
                <div id="iframeHeader"></div>

            </div>

            <div id="iframeBody" class="modal-body">

            </div>
        </div>
    </div>
</div>

<%--<!-- /#myModal iframe -->--%>

<script>
    // Script to open and close sidebar



    function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
        document.getElementById("myOverlay").style.display = "block";
    }

    function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
        document.getElementById("myOverlay").style.display = "none";
    }

    // Modal Image Gallery
    function onClick(element) {
        document.getElementById("img01").src = element.src;
        document.getElementById("modal01").style.display = "block";
        var captionText = document.getElementById("caption");
        captionText.innerHTML = element.alt;
    }

</script>


<script type="text/javascript">

    $('#addCourseEventForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-course/create-course-event/${childCourseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                //  alert("worked");
                var r = confirm('<spring:message code="coursePage.msg2"/>');
                if (r == true) {
                    $(this).closest('form').find("input[type=text], textarea").val("");
                } else{
                    location.reload(true);
                }
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });




    function submitEdit(eventId) {


        $('#editCourseEventForm').submit(function(e){
            e.preventDefault();
            $.ajax({
                url:'${url}/admin/manage-course/update-course-event/'+eventId,
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    //  alert("worked");
                    var r = confirm('<spring:message code="coursePage.msg2"/>');
                    if (r == true) {
                        // clear form
                    } else{
                        location.reload(true);
                    }
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });

    }

    $('#add').click(function(){
        window.location.href='${url}/admin/course/content/add/${childCourseId}';

    });

    function enrollments(childCourseId){
        window.location.href='${url}/admin/manage-courses/enrolment-list/${childCourseId}/3';
    }
    
    function courseGradeReport(childCourseId) {
        window.location.href='${url}/admin/course/grade/overview/${childCourseId}';
    }

    $(document).ready(function(){
        $('#less').hide();

        //HIDE SETTINGS ON PAGE LOAD
        $('#settingsList').hide();

        //SCROLL BAR
        //http://rocha.la/jQuery-slimScroll

//            $('.notifcationContainer').slimScroll({
//                position: 'right',
//                height: '300px',
//                alwaysVisible: false,
//                railVisible: false,
//            });





    });

    //HIDE AND SHOW SETTINGS LIST
    $("#settings").click(function(){
        $("#settingsList").toggle()
    });
    //HIDE AND SHOW SETTINGS LIST

    $('#more').click(function (e) {
        e.stopPropagation();
        $('#aboutTextBox').css({
            'height':'auto'
        });
        $('#more').hide();
        $("#less").show();

    });

    $('#less').click(function (e) {
        e.stopPropagation();
        $('#aboutTextBox').css({
            'height':'50px'
        });
        $('#less').hide();
        $("#more").show();

    });


    $(document).click(function () {
        $('#aboutTextBox').css({
            'height':'50px'
        });

        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        })

    });

    //exam view button
    $('#toExams').click(function(){
        window.location.href='${url}/admin/manage-courses/course-exam/${childCourseId}';
    })

    function postdata(data){
        var r = confirm('<spring:message code="coursePage.msg1"/>');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/course/content/delete',
                type:'post',
                data:{ lessonId: data},
                success:function(data){
                    alert('<spring:message code="coursePage.msg4"/>');
                    location.reload(true);
                },
                error : function(){
                    alert("error");
                }
            });
        } else{
          // Do nothing

        }
    }

    function editContent(lessonId){

        window.location.href = '${url}/admin/course/content/edit/${childCourseId}/'+lessonId;
    }

    function uploadContent(lessonId){
        window.location.href = '${url}/admin/course/content/edit/${childCourseId}/'+lessonId;
    }

    function addCourseEvent(){
        $('#myModal').modal('toggle');
    }

    function editEvent(eventId) {
//        alert(eventId);
        $.ajax({
            url:'${url}/admin/manage-course/event/'+eventId,
            type:'post',
            success:function(data){
                //alert(data);
                //console.log(data);
                $('#editBtn').val(data.event_id);
                $('#eName').val(data.title);
                $('#eDate').val(data.eventDateTime);
                $("textarea#eDes").val(data.description);
                $('#editModel').modal('toggle');
                // location.reload(true);
            },
            error : function(){
                alert("error");
            }
        });
        // $('#modalTitle').html(event.title);
//        $('#modalBody').html(event.description);
//        $('#fullCalModal').modal();
//        $('#fullCalModal').modal('toggle');
    }

    function deleteEvent(eventId) {
        var r = confirm('<spring:message code="coursePage.msg5"/>');
        if (r == true) {

            $.ajax({
                url:'${url}/admin/manage-course/delete-course-event/'+eventId,
                type:'post',
                success:function(data){
                    console.log(data);
                    location.reload(true);
                },
                error : function(){
                    alert("error");
                }
            });

        }else{
            //nothing done
        }

    }

    function openModal(title,url) {
        $('#iframeCls').val(url);
        $("#fullsecreen").html('<a href="#" onclick="openFullScreen()"><span class="inIconAni glyphicon glyphicon-fullscreen" aria-hidden="true"></span></a>')
        $("#iframeHeader").html('<h4 id="ifmt" class="modal-title">'+title+'</h4>')
        $("#iframeBody").html('<iframe frameborder="0" scrolling="yes" allowtransparency="true" src="${url}/view/docs/'+url+'"></iframe>');
        $('#iframeModal').modal('toggle');

    }

    function openFullScreen() {
        window.open('${url}/view/docs/'+$('#iframeCls').val(), '_blank');
        $('#iframeModal').modal('toggle');
    }

    function showNotification(title,notification) {
        $('#eventModal').modal('toggle');
        $('#modalTitle').text(title);
        $('#modalBody').text(notification);
    }

</script>

</body>


</html>



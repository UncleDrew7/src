<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 03:27
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
    <title><spring:message code="extra.notifications" /></title>
    <link href="${url}/resources/student_app_static/css/activity.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <link href="${url}/resources/main/summernote/summernote.css" rel="stylesheet">
    <script src="${url}/resources/main/summernote/summernote.js"></script>
    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>


<div>
    <!-- Tab panes -->
    <div class="content">

        <h3 class="pageTitle"><spring:message code="extra.notifications" /></h3>
        <a class="addActivity"  onClick=" addCourseEvent()"><spring:message code="activities.addNotification" /> <span class="inIcon glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>


        <div role="tabpanel" class="tab-pane active" id="home">
            <div id="notifications" class="row mainContainer ">
                <div class="col-sm-12 ">

                    <div class="bigCardContainer">

                        <div class="searchBoxSty">
                            <div class="input-group">
                                <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                    </ul>
                                </div><!-- /btn-group -->
                            </div><!-- /input-group -->
                        </div>

                        <!--TimeLine-->
                        <div class="timeline-container">
                            <div id="timeline">
                                <ul style="list-style-type: none" class="list">
                                <c:set var="count" value="0" scope="page" />
                                <c:forEach var = "newsFeed" items="${notificationList}">

                                    <c:choose>

                                        <c:when test="${count == 0}">


                                               <li>
                                                   <div class="timeline-item">
                                                       <div class="timeline-icon">
                                                           <i class="fa fa-envelope-open-o" aria-hidden="true"></i>
                                                       </div>
                                                       <div class="timeline-content">
                                                           <h2 class="titleName"><c:out value="${newsFeed.title}"/></h2>
                                                           <c:out value="${newsFeed.message}" escapeXml="false"/>

                                                           <c:forEach var = "uploads" items="${newsUploads}">
                                                               <c:choose>
                                                                   <c:when test="${newsFeed.id eq uploads.newsNotificationId}">
                                                                       <a href="${url}/download/new-notifications-items/${uploads.uploadUrl}">
                                                                           <div class="icon blue">
                                                                               <span class="doc-icon ptt">⧉</span>
                                                                               <span class="doc-type">file</span>
                                                                           </div>
                                                                           <div class="downloadText"><c:out value="${uploads.uploadName}"/></div>
                                                                       </a>
                                                                    </c:when>
                                                                </c:choose>
                                                            </c:forEach>


                                                           <span class="time-stamp"><a class="teacherName" href="subPages/teacher_profile.html"><c:out value="${newsFeed.createdByUserName}"/></a></span>
                                                           <br/>
                                                           <div style="float: right" class="btn-group">
                                                               <a style="cursor: pointer" onclick="editEvent(${newsFeed.id})"><span onclick="" style="margin-left:10px " class="inIcon glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                                               <a style="cursor: pointer"  onclick="deleteEvent(${newsFeed.id})"><span style="margin-left:10px " class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                                           </div>
                                                           <span class="time-stamp"><c:out value="${newsFeed.createdAt}"/></span>

                                                       </div>
                                                   </div>
                                               </li>

                                            <c:set var="count" value="${count + 1}" scope="page"/>

                                        </c:when>

                                        <c:otherwise>

                                           <li>
                                               <div class="timeline-item">
                                                   <div class="timeline-icon">
                                                       <i class="fa fa-envelope-open-o" aria-hidden="true"></i>
                                                   </div>
                                                   <div class="timeline-content right">
                                                       <h2 class="titleName"><c:out value="${newsFeed.title}"/></h2>
                                                       <c:out value="${newsFeed.message}" escapeXml="false"/>
                                                       <span class="time-stamp"><a class="teacherName" href="subPages/teacher_profile.html"><c:out value="${newsFeed.createdByUserName}"/></a></span>
                                                       <br/>
                                                       <div style="float: right" class="btn-group">
                                                           <a style="cursor: pointer" onclick="editEvent(${newsFeed.id})"><span onclick="" style="margin-left:10px " class="inIcon glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                                                           <a style="cursor: pointer"  onclick="deleteEvent(${newsFeed.id})"><span style="margin-left:10px " class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                                       </div>
                                                       <span class="time-stamp"><c:out value="${newsFeed.createdAt}"/></span>
                                                   </div>
                                               </div>
                                           </li>
                                            <c:set var="count" value="${count - 1}" scope="page"/>

                                        </c:otherwise>

                                    </c:choose>

                                </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <!--TimeLine-->
                    </div>
                </div>


            </div>

        </div>

    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-body">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <div class="formTitleBox">
                        <h1><spring:message code="publish2.h1" /></h1>
                    </div>


                    <%--FORM--%>
                    <%--<h3>Basic</h3>--%>
                    <form id="addNotificationForm" class="form-horizontal"  method="post">

                        <%--FORM BOX 1/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label style="margin-bottom: 8px;" class="control-label " for="title"><spring:message code="publish2.title" /></label>
                                <div class="col-sm-12">
                                    <input type="text" class="form-control" id="title" autocomplete="off" placeholder="Enter title " name="title" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label style="margin-bottom: 8px;" class="control-label" for="notification"><spring:message code="publish2.notifications" /> </label>
                                <div class="col-sm-12">
                                    <textarea type="text" class="form-control" id="notification" placeholder="Enter notification description" name="message" required></textarea>
                                </div>
                            </div>

                        </div>
                        <%--FORM BOX1--%>
                        <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                        <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class="submitBtnBox">
                                <button  id="submit" type="submit" class="btn btn-default"><spring:message code="publish2.create" /></button>
                            </div>
                        </div>

                    </form>
                    <%--/FORM--%>
                </div>
            </div>
        </div>
    </div>

    <%--edit --%>
    <!-- Modal -->
    <div class="modal fade" id="editModel" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-body">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <div class="formTitleBox">
                        <h2><spring:message code="publish2.editNotification" /></h2>
                    </div>


                    <%--FORM--%>
                    <%--<h3>Basic</h3>--%>
                    <form id="editNewsFeedForm" class="form-horizontal"  method="post">

                        <%--FORM BOX 1/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label style="margin-bottom: 8px;" class="control-label " for="title"><spring:message code="publish2.title" /></label>
                                <div class="col-sm-12">
                                    <input type="text" class="form-control"  id="editTitle" autocomplete="off" placeholder="Enter title " name="title" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label style="margin-bottom: 8px;" class="control-label" for="notification"><spring:message code="publish2.notifications" /> </label>
                                <div class="col-sm-12">
                                    <textarea type="text" class="form-control" id="editNotification" placeholder="Enter notification description" name="message" required></textarea>
                                </div>
                            </div>



                        </div>

                            <div class="form-group">
                                <div class="submitBtnBox">
                                    <button id="editBtn2" onclick="submitNewsEdit(this.value)"  type="submit" class="btn btn-default"><spring:message code="publish2.editEvent" /></button>
                                </div>
                            </div>
                        <%--FORM BOX1--%>
                        <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                        <%--FORM DIVIDER--%>



                    </form>
                    <%--/FORM--%>



                    <div class="bigCardContainer ">

                        <div class="formTitleBox settingsTitleBox">
                            <h2><spring:message code="publish2.uploadFile" /></h2>
                        </div>

                        <form id="uploadForm" method="POST" enctype="multipart/form-data">
                            <div class="form-group">
                                <div class="toolTipBox">
                                    <span id="errorText" class="tooltipItem"></span><br/>
                                </div>
                                <label class="control-label " for="files"><spring:message code="publish2.selectFile" /></label>
                                <div >
                                    <input autocomplete="off"type="file" name="file"  id="files" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label " for="fileName"><spring:message code="publish2.addFileName" /></label>
                                <div>
                                    <input autocomplete="off"type="text" name="name" class="form-control"  id="fileName" placeholder="Add File Name (Optional)">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="">
                                    <button id="uploadBtn" style="width: 100%" type="submit" onclick="submitUpload(this.value)"  class="btn btn-default"><spring:message code="publish2.upload" /></button>
                                </div>
                            </div>
                        </form>

                        <div class="formTitleBox settingsTitleBox">
                            <h2><spring:message code="publish2.uploadedFiles" /></h2>
                        </div>

                        <div id="uploadItem" class="list-group">

                        </div>

                        <%--<div class="table-responsive">--%>
                            <%--<table class="table table-condensed">--%>
                                    <%--<div style="text-align: center"><i>No Available Uploads!!</i></div>--%>
                                    <%--<tr>--%>
                                        <%--<td >&nbsp;<a id="uploadName" style="cursor: pointer" onclick="onUpdateClicked(${upload.lessonUploadId},'${upload.uploadName}','${upload.uploadUrl}')" style="color: rgb(42,114,173)" data-toggle="tooltip" title="${upload.uploadName}"></a></td>--%>
                                    <%--</tr>--%>
                            <%--</table>--%>
                        <%--</div>--%>

                    </div>





                </div>
            </div>
        </div>
    </div>


</div>

<script>

    var options = {
        valueNames: [ 'titleName']
    };
    var notifications  = new List('notifications', options);


    function addCourseEvent(){
        $('#myModal').modal('toggle');
    }


    $('#addNotificationForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/news-notification/add',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                  alert(data);
                var r = confirm('<spring:message code="publish2.msg1" />');
                if (r == true) {
                    // clear form
                } else{
                    location.reload(true);
                }
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });



    function editEvent(feedId) {
//        alert(feedId);
        $('#editModel').modal('toggle');
        $.ajax({
            url:'${url}/admin/single-notification/'+feedId,
            type:'post',
            success:function(data){
//                alert(data);
                console.log(data);
                $("#uploadBtn").val(data.id);
                $('#editTitle').val(data.title);
                $('#editBtn2').val(data.id);
                $('#editNotification').summernote("code", data.message)

                // location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });

        $.ajax({
            url:'${url}/uploads/news-notifications/'+feedId,
            type:'post',
            success:function(data){
                console.log(data);
                $('#uploadItem').empty()
                for (var i = 0, len = data.length; i < len; i++) {
                    console.log(data[i].uploadName);
                    $('#uploadItem').append(
                        "<a class='list-group-item '><img src='${url}/resources/main/icons/file_icon.png' alt='' width='30px' height='30px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>"
                        +data[i].uploadName+
                        "</span></a>");

                }
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    }
    function submitNewsEdit(feedId) {

        alert(feedId);

        $('#editNewsFeedForm').submit(function(e){
        e.preventDefault();
        $.ajax({
        url:'${url}/admin/news-notification/edit/'+feedId,
        type:'post',
        data:$(this).serialize(),
        success:function(data){
        alert(date);
        location.reload(true);
        },
        error : function(data){
            alert('<spring:message code="main.msgError" />');
        }
        });
        });

    }



    function deleteEvent(feedId) {
        var r = confirm('<spring:message code="publish2.msg2" />');
        if (r == true) {

            $.ajax({
                url:'${url}/admin/news-notification/delete/'+feedId,
                type:'post',
                success:function(data){
                    console.log(data);
                    alert(data);
                    location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //nothing done
        }


    }

    $(document).ready(function() {
//        CKEDITOR.replace( 'eventDescription' );
        $('#notification').summernote({
            minHeight: 200,
            maxHeight:200,
            placeholder: '<spring:message code="publish2.plhder" />'
        });

        $('#editNotification').summernote({
            minHeight: 200,
            maxHeight:200,
            placeholder: '<spring:message code="publish2.plhder" />'
        });
    });


    function submitUpload(feedId) {

        var data = new FormData($("#uploadForm")[0]);

        alert(feedId);
        $('#uploadForm').submit(function(e){
            e.preventDefault();
            $.ajax({
                url:'${url}/upload/news-notification-items/5',
                <%--url:'${url}/upload/news-notification-items/'+feedId,--%>
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                // alert("worked");
                    <%--var r = confirm(data);--%>
                    <%--if (r == true) {--%>
                        <%--location.reload(true);--%>
                    <%--} else{--%>
                        <%--window.location.href = '${url}/admin/manage-course';--%>
                    <%--}--%>
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

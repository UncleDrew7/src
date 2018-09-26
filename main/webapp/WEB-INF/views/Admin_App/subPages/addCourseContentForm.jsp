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
    <title><spring:message code="addCourseContentForm.addCourseContent" /></title>
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/coursePage.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
    <link href="${url}/resources/main/summernote/summernote.css" rel="stylesheet">
    <script src="${url}/resources/main/summernote/summernote.js"></script>
</head>

<body>
<%--TEST DATASOURCE --%>

<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/view/course/${courseId}" class="icon-home"><spring:message code="main.course" /></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a href="#">${courseNames.courseShortName}</a></li>
        </ul>
    </div>
    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">
            <%--<div id="uploadAlert" style="text-align: center" class="alert alert-success  fade in" role="alert">--%>
                <%--<a href="#" class="alert-link">File Uploaded Successfully !!!</a>--%>
            <%--</div>--%>
            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <div class="formTitleBox">
                    <c:if test="${action eq 'add'}"><h1><spring:message code="addCourseContentForm.addCourseContent" /></h1></c:if>
                    <c:if test="${action eq 'edit'}"><h1><spring:message code="addCourseContentForm.editCourseContent" /></h1></c:if>

                </div>


                <%--FORM--%>
                <%--<h3>Basic</h3>--%>
                <form <c:if test="${action eq 'edit'}">id="editContentForm"</c:if> <c:if test="${action eq 'add'}">id="addContentForm"</c:if> class="form-horizontal"  method="post">

                    <h4><spring:message code="addCourseContentForm.topicName" /></h4>
                    <div class="formBox">
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${action eq 'edit'}"></c:if>
                                <input type="text" <c:if test="${action eq 'edit'}">value="${lessonContent.lessonName}" </c:if> class="form-control" autocomplete="off" id="userClass" placeholder='<spring:message code="addCourseContentForm.enterTopicName" />' name="topic" required>
                            </div>
                        </div>
                    </div>

                    <%--FORM HEADER--%>
                    <h4><spring:message code="addCourseContentForm.topicDescription" /></h4>
                    <%--FORM BOX 2/--%>
                    <div class="formBox">

                        <div class="form-group">
                            <%--<div class="col-sm-2"></div>--%>
                            <div class="col-sm-12">
                                <textarea name="topicDescription" id="summernote" required><c:if test="${action eq 'edit'}"><c:out value="${lessonContent.description}" escapeXml="false"/> </c:if></textarea>
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
                            <button id="submit" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges" /></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>

            </div>
            <%--BIG CARD CONTAINER BOX--%>

        </div>
        <!--BIG CONTAINER -->

        <!--Small cards-->
        <div  style="min-width: 150px" class="col-sm-3 smallCardContainerParent2">
            <%--PARENT CARD--%>
           <c:if test="${action eq 'edit'}">
               <div class="card bigCardContainer " style="min-width: 150px">

                   <div class="formTitleBox settingsTitleBox">
                       <h1><spring:message code="publish2.uploadFile" /></h1>
                   </div>

                   <form method="POST" action="${url}/upload/docs/${courseId}/${lessonId}" enctype="multipart/form-data">
                       <div  class="form-group">
                           <div class="toolTipBox">
                               <span id="errorText" class="tooltipItem"></span><br/>
                           </div>
                           <label class="control-label " for="files"><spring:message code="publish2.selectFile" /></label>
                           <div style="max-width: 200px; overflow: hidden;">
                               <input  autocomplete="off"type="file" name="file"  id="files" required>
                           </div>
                       </div>
                       <div style="max-width: 200px" class="form-group">
                           <label class="control-label " for="fileName"><spring:message code="publish2.addFileName" /></label>
                           <div>
                               <input autocomplete="off"type="text" name="name" class="form-control"  id="fileName" placeholder='<spring:message code="addCourseContentForm.addFileNameOptional" />'>
                           </div>
                       </div>
                       <div class="form-group">
                           <div class="">
                               <button style="width: 50%" type="submit" class="btn btn-default"><spring:message code="publish2.upload" /></button>
                           </div>
                       </div>
                   </form>

               </div>

               <div class="card bigCardContainer ">

                   <h1><spring:message code="publish2.uploadedFiles" /></h1>

                   <div class="table-responsive">
                       <table class="table table-condensed">
                           <c:if test="${empty lessonUploadsList}">
                               <div style="text-align: center"><i><spring:message code="addCourseContentForm.noAvailableUploads" /></i></div>
                           </c:if>
                           <c:forEach var = "upload" items="${lessonUploadsList}">
                               <tr>
                                   <td ><img src="${url}/resources/main/icons/file_icon.png" alt="" width="30px" height="30px">&nbsp;<a style="cursor: pointer" onclick="onUpdateClicked(${upload.lessonUploadId},'${upload.uploadName}','${upload.uploadUrl}')" style="color: rgb(42,114,173)" data-toggle="tooltip" title="${upload.uploadName}"><c:out value="${upload.uploadUrl}"/></a></td>
                               </tr>
                           </c:forEach>

                           </tbody>
                       </table>
                   </div>

               </div>
           </c:if>
            <%--PARENT CARD--%>

            <c:if test="${action eq 'add'}">
                <div class="card bigCardContainer contentList">

                    <h1><spring:message code="addCourseContentForm.currentlyAdded" /></h1>

                    <div>
                        <c:if test="${empty currentlyAddedLessonList}">
                            <div style="text-align: center"><i><spring:message code="addCourseContentForm.noLessonsAdded" /></i></div>
                        </c:if>
                        <ul>
                            <c:forEach var = "caList" items="${currentlyAddedLessonList}">
                                <li>
                                    <a href="#${caList.lessonId}"><button class="button topicBox"><c:out value="${caList.lessonName}"/></button></a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    </div>

                </div>
            </c:if>




        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->


<%--upload edit modal --%>
<div class="modal fade" id="uploadModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <div class="modal-header">
                <h4  class="modal-title courseOthersText"><span><spring:message code="addCourseContentForm.editLessonUpload" /></span><button type="button" class="close" data-dismiss="modal">&times;</button></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="control-label " for="fileName"><spring:message code="addCourseContentForm.uploadFileName" /></label>
                    <div>
                        <input autocomplete="off"type="text" name="uploadName" class="form-control"  id="uploadName" required>
                        <input style="display: none" type="text" name="docName" id="docName" >
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-justified">
                    <a href="#"  id="saveUpload" onclick="saveUploadChanges()" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModalExams" class="btn btn-primary"><spring:message code="extra.saveChanges" /></a>
                    <a href="#" id="delUpload" onclick="deleteUpload()" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.delete" /></a>
                </div>
            </div>
        </div>
    </div>
</div>



</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {
        $('#summernote').summernote({
            minHeight: 200,
            maxHeight:200,
            placeholder: '<spring:message code="addCourseContentForm.enterTopicDescription" />'
        });
        ///date
        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        });

//        alert("hello").delay( 2000 );
//        $("#uploadAlert").css("display","none");
        setTimeout(function(){
            $("#uploadAlert").alert('close');
        },1500);

    });

    $('#addContentForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/course/content/add/${courseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
//                alert("worked");
                var r = confirm('<spring:message code="addCourseContentForm.sccMsg1" />');
                if (r == true) {
                    location.reload(true);
                } else{
                    window.location.href = '${url}/admin/view/course/${courseId}';
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    $('#editContentForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/course/content/update/${lessonId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                alert(data);
                window.location.href = '${url}/admin/view/course/${courseId}';
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    function onUpdateClicked(lessonUploadId,uploadName,docUrl) {
        $('#uploadName').val(uploadName);
        $('#docName').val(docUrl);
        $('#saveUpload').val(lessonUploadId);
        $('#delUpload').val(lessonUploadId);
        $('#uploadModal').modal('toggle');
    }

    function saveUploadChanges() {
//        alert($('#saveUpload').val() + ''+ $('#uploadName').val());
        var r = confirm('<spring:message code="addCourseContentForm.msg2" />');
        if(r == true){
            $.ajax({
                url:'${url}/admin/course/content/lessons/upload-update/'+$('#saveUpload').val(),
                data:{ uploadName:$('#uploadName').val()},
                type:'post',
                success:function(data){
                    alert(data);
                    $('#uploadModal').modal('toggle');
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                    $('#uploadModal').modal('toggle');
                }
            });

        }else{
            //do nothing
        }

    }

    function deleteUpload() {
//        alert($('#docName').val());
        var r = confirm('<spring:message code="addCourseContentForm.msg3" />');
        if(r == true){
            $.ajax({
                url:'${url}/admin/course/content/lessons/upload-delete/'+$('#delUpload').val(),
                data:{ docName:$('#docName').val()},
                type:'post',
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                    location.reload(true);
                }
            });

        }else{
            //do nothing
        }

    }
</script>

</body>

</html>

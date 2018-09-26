<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 15/10/2017
  Time: 00:34
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="gradeItems.gradeItems"/>  </title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/form.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
</head>

<body>


<!--Content Begins  -->
<div class="content">

    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/grades" class="icon-home"><spring:message code="manageGrades2.title"/></a></li>
            <li class="last active"><a > <spring:message code="gradeItems.gradeItems"/></a></li>
        </ul>
    </div>



    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <div class="searchingBox">

            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a href="${url}/admin/course/grade/overview/${courseId}" type="button" class="btn btn-default"><spring:message code="gradeItems.overview"/> </a>
                    <%--<button type="button" class="btn btn-default">Download Add Grade Item  Excel Template <span class="glyphicon glyphicon-download" aria-hidden="true"></span></button>--%>
                    <%--<button data-toggle="modal" data-target="#enrolmentModal" type="button" class="btn btn-default">Add Grade Items Using Excel <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>--%>
                </div>
            </div>

        </div>
        <%--/SEARCH CONTAINER--%>

        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">


            <h3><spring:message code="gradeItems.gradeItemsFor"/>  ${courseName.courseName} </h3>
            <div class="divider"></div>
            <%--!TABLE--%>
            <div class="table-responsive">
                <table id="gradeItemTable" class="table table-bordered table-hover">

                    <thead>
                    <tr>
                        <th><spring:message code="gradeItems.type"/></th>
                        <th><spring:message code="gradeItems.gradeItemname"/></th>
                        <th><spring:message code="gradeItems.weight"/></th>
                        <th><spring:message code="addExamForm.minGrade"/></th>
                        <th><spring:message code="addExamForm.maxGrade"/></th>
                        <th><spring:message code="main.action"/></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:if test="${empty gradeItemsList}"><th colspan="100%" style="text-align: center"><span ><spring:message code="gradeItems.nogradeItemsCreated"/></span> </th></c:if>

                    <c:forEach var="giList" items='${gradeItemsList}' >
                        <tr>
                            <td><span class="label label-default"><c:out value="${giList.examType}"/></span></td>
                            <td><a href="${url}/admin/grades/add-grades/${courseId}/${giList.parentExamId}<c:if test="${'exam'eq 'Exam'}">/1</c:if><c:if test="${'gradeItem' ne 'Exam'}">/2</c:if>">
                                <c:out value="${giList.examName}"/>
                                </a>
                            </td>
                            <td>${giList.weight}</td>
                            <td><c:out value="${giList.minGrade}"/></td>
                            <td><c:out value="${giList.maxGrade}"/></td>
                            <td>
                                <c:if test="${currentUserRole ne 'admin'}">
                                    <c:choose>
                                        <c:when test="${giList.examType ne 'parentExam' and giList.examType ne 'childExam'}">
                                            <a style="color: #fff8f8; width: 70px" <c:if test="${!hasPermission and currentUserRole ne 'admin'}"> disabled</c:if> href="${url}/admin/grades/add-grades/${courseId}/${giList.parentExamId}/2" class="btn btn-danger btn-xs" ><spring:message code="adminNavLable.grades"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a style="color: #fff8f8; width: 70px" <c:if test="${!hasPermission and currentUserRole ne 'admin'}"> disabled</c:if> href="${url}/admin/grades/add-grades/${courseId}/${giList.parentExamId}/1" class="btn btn-danger btn-xs" ><spring:message code="adminNavLable.grades"/></a>
                                        </c:otherwise>
                                    </c:choose>
                                   </c:if>

                                <c:if test="${currentUserRole eq 'admin'}">
                                    <div class="dropdown">
                                        <button   <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> class="btn btn-danger btn-xs " type="button" data-toggle="dropdown"><spring:message code="main.action"/>
                                            <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                                <li><a href="${url}/admin/grades/add-grades/${courseId}/${giList.parentExamId}<c:if test="${'exam' eq 'Exam'}">/1</c:if><c:if test="${'exam' ne 'Exam'}">/2</c:if>">
                                                    <spring:message code="adminNavLable.grades"/></a></li>
                                                <li><a style="cursor: pointer" onclick="editGradeItem(${giList.parentExamId})"><spring:message code="main.edit"/></a></li>
                                            <c:if test="${'gradeItems' ne 'Exam'}">
                                                <li><a  style="cursor: pointer" onclick="deleteGradeItem(${giList.parentExamId})"><spring:message code="main.delete"/></a></li>
                                            </c:if>
                                            <c:if test="${ giList.examType eq 'parentExam'}">
                                                <li><a  style="cursor: pointer" onclick="createClearanceExam(${giList.parentExamId})"><spring:message code="manageExams2.addChildExam"/></a></li>
                                            </c:if>

                                        </ul>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>


                    </tbody>

                    <tfoot>
                    <tr>
                        <th colspan="100%" ><spring:message code="gradeItems.weightTotal"/> ${totalWeight}</th>
                    </tr>
                    <tr>
                        <td colspan="100%" class="text-center"><button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> class=" btn courseButton saveBtn" onclick="addGradeItem()"><spring:message code="gradeItems.addGradeItem"/></button>
                            <%--<button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> class=" btn courseButton saveBtn"  data-toggle="modal" data-target="#importCourseExcel" href="#">Save Changes </button>--%>
                        </td>
                    </tr>
                    </tfoot>
                </table>

            </div>


        </div>
        <%--/END OF MAIN CARD CONTAINER --%>


    </div>
    <!--Content Ends  -->

    <%--<!-- Modal -->--%>
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-body">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <div class="formTitleBox">
                        <h1><spring:message code="addGrades.gradeItems"/></h1>
                    </div>


                    <%--FORM--%>
                    <%--<h3>Basic</h3>--%>
                    <form id="addGradeItemForm" class="form-horizontal"  method="post">

                        <%--FORM BOX 1/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="gradeItem"><spring:message code="gradeReport.gradeItem"/></label>
                                <div class="col-sm-9">
                                    <select  autocomplete="off" class="form-control"  name="gradeItemType" required>
                                        <option value=""><spring:message code="gradeItems.selectGradeItem"/></option>
                                            <%--<c:if test="${currentUserRole eq 'admin'}"><option>Exam</option></c:if>--%>
                                            <option>Test</option>
                                            <option>Assignment</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giName"><spring:message code="gradeItems.gradeItemname"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterGradeItemName"/>' name="giName" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giDate"><spring:message code="gradeItems.dateofGradeItem"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off"  placeholder='<spring:message code="gradeItems.enterDateOfGradeItem"/>' name="giDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giStartDate"><spring:message code="examForm.enrolmentStartDate"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off"  placeholder='<spring:message code="gradeItems.enterGradeItemStartDate"/>' name="giStartDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giEndDate"><spring:message code="examForm.enrolmentEndDate"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off" placeholder='<spring:message code="gradeItems.enterGradeItemEndDate"/>' name="giEndDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="minGrade"><spring:message code="addExamForm.minGrade"/></label>
                                <div class="col-sm-9">
                                    <input type="number"  value="0" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterMinGrade"/>' name="minGrade" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="maxGrade"><spring:message code="addExamForm.maxGrade"/></label>
                                <div class="col-sm-9">
                                    <input type="number" value="100" class="form-control" name="maxGrade" autocomplete="off" placeholder='<spring:message code="gradeItems.enterMaxGrade"/>' required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giWeight"><spring:message code="gradeItems.weight"/></label>
                                <div class="col-sm-9">
                                    <input step="0.01" type="number" value="0.0" class="form-control"  autocomplete="off" placeholder='<spring:message code="gradeItems.enterWeight"/>' name="giWeight" required>
                                </div>
                            </div>

                        </div>
                        <%--FORM BOX1--%>
                        <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                        <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class="submitBtnBox">
                                <button   type="submit" class="btn btn-default"><spring:message code="event.createEvent" /></button>
                            </div>
                        </div>

                    </form>
                    <%--/FORM--%>
                </div>
            </div>
        </div>
    </div>

    <%--<!-- Update Grade Item-->--%>
    <div class="modal fade" id="editModal" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-body">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <div class="formTitleBox">
                        <h1><spring:message code="gradeItems.editGradeItem"/></h1>
                    </div>


                    <%--FORM--%>
                    <%--<h3>Basic</h3>--%>
                    <form id="editGradeItemForm" class="form-horizontal"  method="post">

                        <%--FORM BOX 1/--%>
                        <div class="formBox">

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="gradeItem">Grade Item</label>
                                <div class="col-sm-9">
                                    <select disabled autocomplete="off" class="form-control" id="gradeItem" name="gradeItemType" required>
                                        <option value=""><spring:message code="gradeItems.selectGradeItem"/></option>
                                        <c:if test="${currentUserRole eq 'admin'}"><option>Exam</option></c:if>
                                        <option>Test</option>
                                        <option>Assignment</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giName"><spring:message code="gradeItems.gradeItemname"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="giName" autocomplete="off" placeholder='<spring:message code="gradeItems.enterGradeItemName"/>' name="giName" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giDate"><spring:message code="gradeItems.dateofGradeItem"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off" id="giDate" placeholder='<spring:message code="gradeItems.enterDateOfGradeItem"/>' name="giDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giStartDate"><spring:message code="examForm.enrolmentStartDate"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off" id="giStartDate" placeholder='<spring:message code="gradeItems.enterGradeItemStartDate"/>'  name="giStartDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giEndDate"><spring:message code="examForm.enrolmentEndDate"/></label>
                                <div class="col-sm-9">
                                    <input type="text" class="datepicker form-control" autocomplete="off" id="giEndDate" placeholder='<spring:message code="gradeItems.enterGradeItemEndDate"/>' name="giEndDate" >
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="minGrade"><spring:message code="addExamForm.minGrade"/></label>
                                <div class="col-sm-9">
                                    <input type="number"   class="form-control" id="minGrade" autocomplete="off" placeholder='<spring:message code="gradeItems.enterMinGrade"/>' name="minGrade" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="maxGrade"><spring:message code="addExamForm.maxGrade"/></label>
                                <div class="col-sm-9">
                                    <input type="number"  class="form-control" id="maxGrade" autocomplete="off" placeholder='<spring:message code="gradeItems.enterMaxGrade"/>' name="maxGrade" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-3" for="giWeight"><spring:message code="gradeItems.weight"/></label>
                                <div class="col-sm-9">
                                    <input step="0.01" type="number"  class="form-control" id="giWeight" autocomplete="off" placeholder='<spring:message code="gradeItems.enterWeight"/>' name="giWeight" required>
                                </div>
                            </div>

                        </div>
                        <%--FORM BOX1--%>
                        <%--FORM DIVIDER--%>
                        <div class="formDivider"></div>
                        <%--FORM DIVIDER--%>

                        <div class="form-group">
                            <div class="submitBtnBox">
                                <button  id="editBtn" onclick="submitEdit(this.value)" type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
                            </div>
                        </div>

                    </form>
                    <%--/FORM--%>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $( ".datepicker" ).datepicker({
        dateFormat: 'yy-mm-dd'
    });

    function addGradeItem(){
        $('#myModal').modal('toggle');
    }
    $('#addGradeItemForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-gradeItems/add-grade-item/${courseId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    alert('<spring:message code="addExamForm.msg4Gi" />');
                    location.reload(true);
                }else{
                    alert('<spring:message code="addExamForm.msg5Gi" />');
                }

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });

    function editGradeItem(gradeItemId){
        $.ajax({
            url:'${url}/admin/manage-gradeItems/get-grade-item/'+gradeItemId,
            type:'post',
            success:function(data){
                //alert(data);
                console.log(data);
                $('#editBtn').val(data.gradeItemId);
                $('#gradeItem').val(data.gradeItemType);
                $('#giName').val(data.gradeItemName);
                $('#giDate').val(data.dateOfExam);
                $('#giStartDate').val(data.enrolmentStartDate);
                $('#giEndDate').val(data.enrolmentCloseDate);
                $('#minGrade').val(data.minGrade);
                $('#maxGrade').val(data.maxGrade);
                $('#giWeight').val(data.weight);
                $('#editModal').modal('toggle');
                // location.reload(true);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
//        $('#editModal').modal('toggle');
    }

    function submitEdit(gradeItemId) {


        $('#editGradeItemForm').submit(function(e){
            e.preventDefault();
            $.ajax({
                url:'${url}/admin/manage-gradeItems/update-grade-item/'+gradeItemId,
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

    }

    function deleteGradeItem(gradeItemId) {
        var r = confirm('<spring:message code="gradeItems.msg1"/>');
        if (r == true) {

            $.ajax({
                url:'${url}/admin/manage-gradeItems/delete-grade-item/'+gradeItemId,
                type:'post',
                success:function(data){
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

    function createClearanceExam(itemId) {
        window.location.href='${url}/admin/manage-exams/exam/add-clearance-exam/${courseId}/'+itemId;
    }
</script>

</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 23/09/2017
  Time: 19:03
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
    <title><spring:message code="events.adimTitle"/></title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/reports.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>




<!--Content Begins  -->
<div style=" min-width: 400px" class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a href="#"><spring:message code="events.allEvents"/></a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="card mainCardContainer">

        <div id="fullCalModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span> <span class="sr-only"><spring:message code="events.close"/></span></button>
                        <h4 id="modalTitle" class="modal-title"></h4>
                    </div>
                    <div id="modalBody" class="modal-body"></div>
                    <div class="modal-footer">
                        <button  onclick="deleteEvent(this.value)" type="button" id="ddevent" class="btn btn-default"><spring:message code="main.delete"/></button>
                        <button onclick="editEvent(this.value)" class="btn btn-primary editEvent" id="eevent" ><spring:message code="main.edit"/></button>
                    </div>
                </div>
            </div>
        </div>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">
            <br/>

            <!--NAV TABS -->
            <ul class="nav nav-tabs">

                <li class="active"><a data-toggle="tab" href="#home"><spring:message code="events2.allEvents"/></a></li>

            </ul>
            <%--/NAV TABS--%>

            <!--TAB CONTENT-->
            <div  class="tab-content">

                <%--MANAGE USER TABLE --%>
                <div  id="home" class="tab-pane fade in active">

                    <h3><spring:message code="events2.events"/> </h3>

                    <div class="table-responsive">
                        <br/>
                        <table id="logTable" class="table table-bordered table-hover">


                            <thead>
                            <tr>

                                <th>#</th>
                                <th><spring:message code="events2.eventsTitle"/></th>
                                <th><spring:message code="events2.eventDate"/></th>
                                <th><spring:message code="events2.eventDescription"/></th>
                                <th><spring:message code="events2.eventType"/></th>
                                <th><spring:message code="events2.createdBy"/></th>
                                <th><spring:message code="events2.Action"/></th>

                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach var = "events" items="${eventsList}" varStatus="index">

                                <tr>
                                    <td><c:out value="${index.count}"/></td>
                                    <td><c:out value="${events.title}"/></td>
                                    <td><c:out value="${events.eventDateTime}"/></td>
                                    <td><c:out value="${fn:substring(events.description,0,50)}..." escapeXml="false"/></td>
                                    <td><c:out value="${events.eventType}"/></td>
                                    <td><c:out value="${events.createdByUserName}"/></td>
                                    <td>
                                        <div class="dropdown">
                                            <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="events2.Action"/>
                                                <span class="caret"></span></button>
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a style="cursor: pointer" onclick="getModal('${events.title}','${events.description}',${events.event_id})" class="view"><spring:message code="main.view"/>   &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>
                                                <li><a style="cursor: pointer" href="${url}/admin/events/edit/${events.event_id}/2"><spring:message code="main.edit"/>    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                <li><a style="cursor: pointer" onclick="deleteEvent(${events.event_id})"><spring:message code="main.delete"/>  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>

                            </c:forEach>



                            </tbody>

                            <%--<tfoot>--%>
                            <%--<tr>--%>
                            <%--<td colspan="5" class="text-center">Data retrieved from <a href="http://www.infoplease.com/ipa/A0855611.html" target="_blank">infoplease</a> and <a href="http://www.worldometers.info/world-population/population-by-country/" target="_blank">worldometers</a>.</td>--%>
                            <%--</tr>--%>
                            <%--</tfoot>--%>
                        </table>





                    </div><!--end of .table-responsive-->



                </div>


            </div>
            <%--MAIN TAB CONTAINER --%>

        </div>

        <%--/MAIN TAB CONTAINER--%>
    </div>

</div>
<%--/END OF MAIN CARD CONTAINER --%>


</div>
<!--Content Ends  -->
<script>
    $(document).ready(function() {
        $('#logTable').DataTable();

    } );

    function getModal( title,decr,id)
    {
        $('#modalTitle').html(title);
        $('#modalBody').html(decr);
        $('#fullCalModal').modal();
        $('#eevent').val(id);
        $('#ddevent').val(id);

    }

    function editEvent(eventId) {
        window.location.href = '${url}/admin/events/edit/'+eventId+'/2';
    }

    function deleteEvent(eventId) {
        var r = confirm('<spring:message code="eventsListAdmin.msg1"/>');
        if (r == true) {

            $.ajax({
                url:'${url}/admin/manage-course/delete-course-event/'+eventId,
                type:'post',
                success:function(data){
                    //console.log(data);
                    alert('<spring:message code="dashboard.msg3"/>');
                    location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="dashboard.msgError"/>');
                }
            });

        }else{
            //nothing done
        }

    }
</script>

</body>

</html>


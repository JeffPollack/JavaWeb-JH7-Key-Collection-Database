<%--

    Document   : keyRequestForm

    Created on : Nov 23, 2016, 6:48:24 AM

    Author     : jdpollac

--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://russet.wccnet.edu/jdpollack" user="jdpollack" password="sqBRY4skFhYd"/>
<sql:query dataSource="${ds}" var="bResult">
    SELECT DISTINCT building FROM KeyCollection;
</sql:query>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JH7_jdpollack</title>
        <script>
            function chg()
            {
                var val = document.getElementById('build').value;
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function ()
                {
                    if (xhttp.readyState === 4 && xhttp.status === 200)
                    {
                        document.getElementById('rooms').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("POST", "KeySelectServlet?valajax=" + val, true);
                xhttp.send();
            }
        </script>    
    </head>

    <body>
        <h1>Key Request Form</h1>
        <form action="KeyServlet">
            <!-- <select name="bld" onchange="submit()">  -->
            <table>
                <tr>
                    <td>
                        Select the Building : <select id="build" onchange="chg()">
                            <option>Select</option>

                            <c:forEach var="row" items="${bResult.rows}">
                                <c:if test="${row.building != selected}">
                                    <option value="<c:out value="${row.building}"/>">
                                        <c:out value="${row.building}"/>
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </br>
                <tr>
                    <td>
                        <div id="rooms">
                            Select the Room : <select>
                            </select>
                        </div>    
                    </td>
                </tr>
            </table>
            <br><input id="rSubmit" type="submit" name="action" value="request"/>
        </form>

        </br>

        <table border="3">
            <tr><th>Building</th><th>Room</th><th>Description</th><th>Key Number</th><th></th></tr>
                <c:forEach var="key" items="${KeyCollection}" varStatus="loopStatus">
                    <tr>
                    <form action="KeyServlet">
                        <td><input type="varchar" name="building" value="${key.building}"/></td>
                        <td><input type="varchar" name="room" value="${key.room}"/></td>
                        <td><input type="varchar" name="description" value="${key.description}"/></td>
                        <td><input type="varchar" name="keyNumber" value="${key.keyNumber}"/></td>
                        <td>
                            <input type="submit" name="action" value="remove"/>
                            <input type="submit" name="action" value="update"/>
                            <input type="hidden" name="index" value="${key.index}"/>
                        </td>
                    </form>    
                    </tr>
                </c:forEach>            
        </table>
    </br>
    <h3>Your Requests:</h3>
    <table border="3">
        <tr><th>Requesters Name</th><th>Key Number</th></tr>
        <c:forEach var="rData" items="${RequestData}" varStatus="loopStatus">
            <tr>
            <td><input type="varchar" name="name" value="${rData.name}"/></td>
            <td><input type="varchar" name="keyNumber" value="${rData.keyNumber}"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Simple_registration" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf" %>
			
		<tr>
			<td class="content">
			<%-- CONTENT --%>
					<form id="list_order_table" action="controller" method="post">
							
							<input type="hidden" name="command" value="simple"/>
			<c:choose>
			<c:when test="${fn:length(apartmentsList) == 0}">No such orders</c:when>
	
			<c:otherwise>
			<table id="list_simple">
				<thead>
					<tr>
						<td>№</td>
						<td>Title</td>
						<td>Seats</td>
						<td>Category</td>
						<td>Status</td>
						<td>Bill</td>
					</tr>
				</thead>


				<c:forEach var="bean" items="${apartmentsList}">
					
					<tr>
						<td>${bean.id}</td>
						<td>${bean.title}</td>
						<td>${bean.seats}</td>
						<td>${bean.category_name}</td>
						<td>${bean.statuses_name}</td>
						<td>${bean.bill}</td>
						
				<td><input type="radio"name="itemId" value="${bean.id}"> </td>
					</tr>

				</c:forEach>			
				
				
			</table>
			</c:otherwise>
			</c:choose>
					
				 	<input value="Make a reservation" type="submit" name="type" />
				<button type="button" name="back" onclick="history.back()">Back</button>	
			<%-- CONTENT --%>
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>
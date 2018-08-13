<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<c:set var="title" value="list_order_table" scope="page" />

<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">

				<form id="list_order_table" action="controller" method="post">

					<input type="hidden" name="command" value="advanced" />

					<table id="registration_table">
						<thead>
							<tr>
								<td>Number of seats</td>
								<td>Apartments classes</td>
								<td>Arrival</td>
								<td>Departure</td>
							</tr>
						</thead>

						<tr>
							<td><select name="seat" id="seat">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
							</select></td>

							<td><select name="category" id="category">
									<c:forEach var="category" items="${categoryList}">
										<option value="${category.category_name}">
											${category.category_name}</option>
									</c:forEach>
							</select></td>

							<td><input type="date" id="arrival" name="arrival" /></td>
							<td><input type="date" id="departure" name="departure" /></td>

						</tr>

					</table>

					<input value="Make a reservation" type="submit" name="type" />
					
					<button type="button" name="back" onclick="history.back()">Back</button>

					<br />
			</td>

		</tr>


		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>

</body>
</html>
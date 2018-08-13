<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="ADMIN_TOOLS" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<%-- CONTENT --%>
				<form id="list_order_table" action="controller" method="post">

					<input type="hidden" name="command" value="admin_order" />



					<c:choose>
						<c:when test="${fn:length(userOrderBeanList) == 0}">No such orders</c:when>

						<c:otherwise>
							<table id="list_order_table">
								<thead>
									<tr>
										<td>№</td>
										<td>Client</td>
										<td>Apartments</td>
										<td>Bill</td>
										<td>Status</td>
									</tr>
								</thead>

								<c:forEach var="bean" items="${userOrderBeanList}">
									<tr>
										<td>${bean.orderId}</td>
										<td>${bean.userFirstName}${bean.userLastName}</td>
										<td>${bean.apartments}</td>
										<td>${bean.bill}</td>
										<td>${bean.status_order}</td>
										<td><select name="status" id="status">
												<option value="${bean.orderId}">handling</option>
												<option value="${bean.orderId}">paid</option>
												<option value="${bean.orderId}">payment_expectation</option>
												<option value="${bean.orderId}">сanсeled</option>
										</select></td>
									</tr>
								</c:forEach>
							</table>
							<input value="Update DB" type="submit" name="type" />
							<input value="Sort by bill inc" type="submit" name="type" />
							<input value="Sort by bill dec" type="submit" name="type" />
							<input value="Sort by apartments" type="submit" name="type" />
							<input value="Sort by apartments dec" type="submit" name="type" />
						</c:otherwise>
					</c:choose>

					<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
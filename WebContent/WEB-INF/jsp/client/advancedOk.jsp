<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<c:set var="title" value="advanced" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">
				<form id="advancedOk_form" action="controller" method="post">


					<fieldset>
						<legend>Your application is accepted</legend>
						<button type="button" name="back" onclick="history.back()">Back</button>

					</fieldset>
					<br />
				</form>
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
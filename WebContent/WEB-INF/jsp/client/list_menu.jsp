<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<c:set var="title" value="select" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>

	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">
				<form id="select_form" action="controller" method="post">
					<input type="hidden" name="command" value="select" />

					<fieldset>
						<legend>Choose apartments</legend>
						<input name="type" type="submit" value="Simple">

					</fieldset>
					<fieldset>
						<legend>Advanced registration</legend>
						<input name="type" type="submit" value="Advanced">

					</fieldset>
					<br />
				</form>
			</td>
		</tr>
		
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>

	</table>
</body>
</html>
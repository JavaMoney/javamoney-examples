<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head><title>Trade Confirmation</title></head>
<body>
<h1>Trade Confirmation</h1>
<form method="post">

<table border="1">
	<tr>
		<td></td>
		<td><b>Symbol</b></td>
		<td><b>Shares</b></td>
	</tr>
	<tr>
		<td>
			<core:choose>
				<core:when test="${trade.buySell == true}">Buy</core:when>
				<core:otherwise>Sell</core:otherwise>
			</core:choose>
		</td>
		<td>
			<core:out value="${trade.symbol}"/>	
		</td>
		<td>
			<core:out value="${trade.shares}"/>	
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center">
			<input type="submit" class="formButton" alignment="center" name="_finish" value="Execute Order">
			<input type="submit" class="formButton" alignment="center" name="_cancel" value="Cancel Order">
		</td>
	</tr>
</table>

    
</form>
<br>
<a href="<core:url value="logon.htm"/>">Log out</a>
<br>
</body>
</html>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head><title>Successful Trade Acknowledgement</title></head>
<body>
<h1>Successful Trade Acknowledgement</h1>
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
				<core:when test="${trade.buySell == true}">Bought</core:when>
				<core:otherwise>Sold</core:otherwise>
			</core:choose>
		</td>
		<td>
			<core:out value="${trade.symbol}"/>	
		</td>
		<td>
			<core:out value="${trade.shares}"/>	
		</td>
	</tr>
</table>

<h2>Your order was filled</h2>
    
</form>
<br>
<a href="<core:url value="portfolio.htm"/>">View Portfolio</a><br/>
<a href="<core:url value="logon.htm"/>">Log out</a>
<br>
</body>
</html>
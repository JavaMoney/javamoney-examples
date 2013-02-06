<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
<head>
	<title>Trade</title>
	<script language=javascript src="<%=request.getContextPath ()%>/scripts/controls.js">
	</script>
</head>
<body class="loginbg" onload="initSkin()">
<h1>Trade</h1>
<form method="post">

<!-- first bind on the object itself to display global errors - if available -->
<spring:bind path="trade.*">
	<span class="logintexterror">
	    <core:forEach items="${status.errorMessages}" var="error">
	    Error: <core:out value="${error}"/><br/>
	    </core:forEach>
    </span>
    <br/>
</spring:bind>

<table border="1">
	<tr>
		<th></th>
		<th class="logintop">Symbol</th>
		<th class="logintop">Shares</th>
	</tr>
	<tr>
		<td>
			<spring:bind path="trade.buySell">
			<input type="radio" 
			       name="buySell" 
			       value="true" 
			       <core:if test="${status.value}">checked</core:if> >
				Buy
			</input>
			<input type="radio" 
			       name="buySell" 
			       value="false" 
			       <core:if test="${! status.value}">checked</core:if> >
				Sell
			</input>
			</spring:bind>			
		</td>
		<td>
			<spring:bind path="trade.symbol">
			<input type="text" name="symbol" value="<core:out value="${status.value}"/>"/>
			</spring:bind>
		</td>
		<td>
			<spring:bind path="trade.shares">
			<input type="text" name="shares" value="<core:out value="${status.value}"/>"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="submit" class="formButton" alignment="center" name="_target1" value="Execute Order"></td>
	</tr>
</table>

    
</form>
<br>
<a href="<core:url value="portfolio.htm"/>">View Portfolio</a><br/>
<a href="<core:url value="logon.htm"/>">Log out</a>
<br>
</body>
</html>
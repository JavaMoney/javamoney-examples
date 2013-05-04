<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%--@ page import="net.java.javamoney.examples.tradingapp.web.LogHelper"--%>
<html>
<head>
	<%-- meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="encoding" content="UTF-8" / --%>
	<title>Portfolio</title>
</head>
<body>
<h1>Portfolio</h1>
<span class="logintextbold">Cash:</span> 
<%-- fmt:formatNumber value="${model.cash}" type="currency" / --%>
<core:out value="${model.amountLocal}"/>
&nbsp;
<span class="logintextbold">Market:</span> 
<core:out value="${model.market}"/>
<br/>
<br/>
<table border="1">
	<tr>
		<th>Symbol</th>
		<th>Company</th>
		<th>Price</th>
		<th>Change</th>
		<th>% Change</th>
		<th>Shares</th>
		<th>Open</th>
		<th>Volume</th>
		<th>Current Value <span class="required">*</span></th>
		<th>Gain/Loss</th>
	</tr>
	<core:forEach items="${model.portfolioItems}" var="stock">
	<tr>
		<td><str:upperCase><core:out value="${stock.symbol}"/></str:upperCase></td>
		<td><core:out value="${stock.quote.company}"/></td>
		<td>
			<%-- fmt:formatNumber value="${stock.quote.value}" type="currency" / --%>
			<core:out value="${stock.quoteAmount}"/>
		</td>
		<td>
			<core:choose>
				<core:when test="${stock.quote.change >= 0}">
					<%-- fmt:formatNumber value="${stock.quote.change}" type="currency" / --%>
					<core:out value="${stock.quoteChangeAmount}"/>
				</core:when>
				<core:otherwise>
					<font color="red">
						<%-- fmt:formatNumber value="${stock.quote.change}" type="currency" / --%>
						<core:out value="${stock.quoteChangeAmount}"/>
					</font>
				</core:otherwise>
			</core:choose>	
		</td>
		<td>
			<core:choose>
				<core:when test="${stock.quote.pctChange >= 0}">
					<fmt:formatNumber value="${stock.quote.pctChange}" type="percent" />
				</core:when>
				<core:otherwise>
					<font color="red">
						<fmt:formatNumber value="${stock.quote.pctChange}" type="percent" />
					</font>
				</core:otherwise>
			</core:choose>	
		</td>
		<td><fmt:formatNumber value="${stock.shares}"/></td>
		<td>
			<%-- fmt:formatNumber value="${stock.quote.openPrice}" type="currency" / --%>
			<core:out value="${stock.quoteOpenAmount}"/>
		</td>
		<td><fmt:formatNumber value="${stock.quote.volume}"/></td>
		<td>
			<%-- fmt:formatNumber value="${stock.currentValue}" type="currency" / --%>
			<core:out value="${stock.currentAmountLocal}"/>
		</td>
		<td>
			<core:choose>
				<core:when test="${stock.gainLoss >= 0}">
					<%-- fmt:formatNumber value="${stock.gainLoss}" type="currency" /--%>
					<core:out value="${stock.gainLossAmount}"/>
				</core:when>
				<core:otherwise>
					<font color="red">
						<%-- fmt:formatNumber value="${stock.gainLoss}" type="currency" / --%>
						<core:out value="${stock.gainLossAmount}"/>
					</font>
				</core:otherwise>
			</core:choose>	
		</td>
	</tr>
	</core:forEach>
</table>
<br/>
<span class="required">*</span> in local Currency<br/>
<br>
<a href="<core:url value="trade.htm"/>">Make a trade</a><br/>
<a href="<core:url value="logon.htm"/>">Log out</a>
<br>
</body>
</html>
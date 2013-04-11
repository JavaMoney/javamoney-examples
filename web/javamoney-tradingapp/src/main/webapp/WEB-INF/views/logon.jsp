<%@ include file="/WEB-INF/views/include.jsp" %>
<%@page import="net.java.javamoney.examples.tradingapp.Constants"%>
<%--	
	Enumeration e =	application.getAttributeNames();
	while (e.hasMoreElements()) {
		//System.out.println("Attrib: " + e.nextElement());
		e.nextElement();
	}
	//System.out.println("Servlet: " + config.getServletName());
	System.out.println("Servlet Info: " + getServletInfo());
	//this.g
	e = config.getInitParameterNames();
	while (e.hasMoreElements()) {
		System.out.println("InitParam: " + e.nextElement());
	}
--%>

<html>
<head>
	<%-- META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8" / --%>
	<title><%= Constants.APP_TITLE %> Sign In</title>
	<script language=javascript src="<%=request.getContextPath ()%>/scripts/controls.js">
	</script>
	</head>
<body class="loginbg" onload="initSkin()">
<h1>Welcome to the <%= Constants.APP_TITLE %></h1>
<div class="SiteContainer SignIn">
	<h2 class="About">Sign In</h2>
	<br/>
	<div class=SignInForm id=Form>
		<fieldset>
			<form id=frmSignIn method="post">
				<%-- b><i>Please log in.</i></b --%>
				<br><br>
				<ul>
	  				<li>
						<label for="username">Username</label>
						<spring:bind path="credentials.username">
							<input class="textinput" type="text" 
						       name="username" 
						       value="<core:out value="${status.value}"/>"/>		
						</spring:bind>
						<spring:hasBindErrors name="credentials">
						<br>
						<span class="logintexterror">
						    <core:forEach items="${status.errorMessages}" var="error">
						    	<img align="middle" src="<%=request.getContextPath ()%>/images/message_error.gif" alt='Authentication Denied'>
						    	&nbsp;<core:out value="${error}"/><br/>
						    </core:forEach>
					    </span>
						<%-- core:out value="${status.errorMessage}"/ --%>
						</spring:hasBindErrors>
					</li>
					<li>
						<label for="password">Password</label>
						<spring:bind path="credentials.password">
						<input class="textinput" type="password" name="password" />
						</spring:bind>
			
						<spring:hasBindErrors name="credentials">
						<br>
						<span class="logintexterror">
							<core:forEach items="${status.errorMessages}" var="error">
						    	<img align="middle" src="<%=request.getContextPath ()%>/images/message_error.gif" alt='Authentication Denied'>
						    	&nbsp;<core:out value="${error}"/><br/>
						    </core:forEach>
						</span>
						</spring:hasBindErrors>
					</li>
					<li>
						<label for="market">Market</label>
						<spring:bind path="credentials.market">
						<select class="textinput" name="market">
							<option value="FRA">Frankfurt</option>
							<option value="LSE">London</option>
							<option value="NYSE">New York</option>
							<option value="TSE">Tokyo</option>
						</select>	
						</spring:bind>
					</li>
				</ul>
				<div class="Submit">
					<input type="submit" class="formButton" alignment="center" value="Proceed" title="Proceed" accesskey="p" onclick="form.submit();this.disabled=true;document.body.style.cursor = 'wait'; this.className='buttondisabled';">
					<!-- tr>
		       					<td class="loginbottom" height="10px" colspan="2">&nbsp;</td>
		     				</tr -->
   				</div>
			</form>
		</fieldset>
	</div>
</div>
</body>
</html>
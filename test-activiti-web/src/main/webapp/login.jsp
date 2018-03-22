<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<% 
		String path = request.getContextPath(); 
	%>
<head>
	<title>Activiti-登录系统</title>
</head>

<body style="margin-top: 3em;"> 
	<c:set var="ctx" value="<%=path%>"></c:set>
	<c:if test="${error}">
		<h2 id="error" class="alert alert-error">用户名或密码错误！！！</h2>
	</c:if>
	<c:if test="${timeout}">
		<h2 id="error" class="alert alert-error">未登录或超时！！！</h2>
	</c:if>
	<div style="width: 500px">
		<h2>任务表单示例</h2>
		<form action="<%=path%>/user/login" method="get">
			<table>
				<tr>
					<td width="80">用户名：</td>
					<td><input id="username" name="username" style="width: 100px" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input id="password" name="password" type="password" style="width: 100px" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<button type="submit" class="btn btn-primary">登录系统</button>
					</td>
				</tr>
			</table>
		</form>

	
	</div>
</body>
</html>
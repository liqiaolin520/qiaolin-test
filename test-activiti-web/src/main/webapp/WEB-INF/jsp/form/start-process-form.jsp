<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.activiti.engine.form.*, org.apache.commons.lang3.*"%>
<!DOCTYPE html>
<html>
<%
	String path = request.getContextPath();
%>
<head>
<title>启动流程</title>
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/bootstrap-responsive.min.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/jquery.datetimepicker.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.datetimepicker.full.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/common/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/common/bootstrap-datepicker.js"></script>

<body style="text-align: center">
	<form
		action="<%=path%>/processDefinition/startProcessInstance/${processDefinitionId}"
		class="form-horizontal" method="post">
		<h3>启动流程
		<c:if test="${formKey}">			 
			[${processDefinition.name}]，版本号：${processDefinition.version}
		</c:if>
		<c:if test="${!formKey}">
			[${startFormData.processDefinition.name}]，版本号：${startFormData.processDefinition.version}
		</c:if>
		</h3>
		
		<c:if test="${formKey}">
			${data}
		</c:if>
		
		<c:if test="${!formKey}">
			<c:forEach items="${data.formProperties}" var="fp">
				<div class="control-group">
					<%-- 文本或者数字类型 --%>
					<c:if test="${fp.type.name == 'string' || fp.type.name == 'long'}">
						<div class="controls">
							<span class="control-label" for="${fp.id}">${fp.name}:</span> <input
								type="text" id="${fp.id}" name="${fp.id}"
								data-type="${fp.type.name}" value="" />
						</div>
					</c:if>
	
					<%-- 日期 --%>
					<c:if test="${fp.type.name == 'date'}">
						<div class="controls">
							<span class="control-label" for="${fp.id}">${fp.name}:</span> <input
								type="text" id="${fp.id}" name="${fp.id}" class="datepicker"
								data-type="${fp.type.name}"
								data-date-format="${fn:toLowerCase(datePattern)}" />
						</div>
					</c:if>
	
					<%-- Javascript --%>
					<c:if test="${fp.type.name == 'javascript'}">
						<script type="text/javascript">${fp.value};</script>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
		<%-- 按钮区域 --%>
		<div class="control-group">
			<div class="controls">
				<a href="javascript:history.back();" class="btn"><i
					class="icon-backward"></i>返回列表</a>
				<button type="submit" class="btn">
					<i class="icon-play"></i>启动流程
				</button>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
	$.datetimepicker.setLocale('ch');
	$('.datepicker').datetimepicker({ 
		format:'Y-m-d H:i:00',
		step:30
	})
	</script>
</head>
</html>
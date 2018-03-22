<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<% 
	String path = request.getContextPath();
%>

<head>
	<title>已部署流程定义列表--chapter5</title>
	<link rel="stylesheet" href="<%=path %>/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="<%=path %>/css/bootstrap-responsive.min.css" type="text/css"> 
</head>
<body>
	<fieldset id="deployFieldset" style="width: 50%">
		<legend>部署流程资源</legend>
		<span class="alert alert-info"><b>支持文件格式：</b>zip、bar、bpmn、bpmn20.xml</span>
		<form action="<%=path%>/deployment/deploy" method="post" enctype="multipart/form-data" style="margin-top:1em;">
			<input type="file" name="file" />
			<input type="submit" value="Submit" class="btn" />
		</form>
		<hr class="soften" />
	</fieldset>
	<table width="100%" class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>流程定义ID</th>
				<th>部署ID</th>
				<th>流程定义名称</th>
				<th>流程定义KEY</th>
				<th>版本号</th>
				<th>XML资源名称</th>
				<th>图片资源名称</th>
				<th width="80">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="pd">
				<tr>
					<td>${pd.id }</td>
					<td>${pd.deploymentId }</td>
					<td>${pd.name }</td>
					<td>${pd.key }</td>
					<td>${pd.version }</td>
					<td><a target="_blank" href='<%=path %>/deployment/readResource?pdid=${pd.id }&name=${pd.resourceName }'>${pd.resourceName }</a></td>
					<td><a target="_blank" href='<%=path %>/deployment/readPng?pdid=${pd.id }&fileName=${pd.diagramResourceName}'>${pd.diagramResourceName }</a></td>
					<td>
						<form id="subform${pd.id}" style="display: none;" action="<%=path %>/deployment/deleteDeploy?deploymentId=${pd.deploymentId}&_method=DELETE" method="post">
						</form>
						<form id="subform1${pd.id}" style="display: none;" action="<%=path %>/processDefinition/getFrom/${pd.id}" method="post">
						</form>
							<a class="zhuce" href="#" name="submit" onclick="document.getElementById('subform${pd.id}').submit();return false" >删除</a>
							<a class="zhuce" href="#" name="submit" onclick="document.getElementById('subform1${pd.id}').submit();return false" >启动</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
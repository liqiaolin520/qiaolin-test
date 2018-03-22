package com.qiaolin.shiro.permission;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 角色权限解析器
 * @author qiaolin
 * @version 2017年4月28日
 * 
 */
public class MyRolePermissionResolver implements RolePermissionResolver{

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
	
		if("role1".equals(roleString)){
			return Arrays.asList(new WildcardPermission("menu:*"));
		}
		
		return null;
	}

}

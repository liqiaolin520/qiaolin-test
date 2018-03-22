package com.qiaolin.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 权限解析器
 * 	根据不同的需要选择不同的权限字符串解析器。
 * @author qiaolin
 * @version 2017年4月28日
 * 
 */
public class BitAndWildPermissionResolver implements PermissionResolver{

	@Override
	public Permission resolvePermission(String permissionString) {
		/**
		 *  如果是以+开头的权限字符串就用我们自己实现的解析器解析
		 *   否则用shiro自带的权限字符串解析器。
		 */
		if(permissionString.endsWith("+")){
			return new BitPermission(permissionString);
		}
	
		return new WildcardPermission(permissionString);
	}

}

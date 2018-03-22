package com.qiaolin.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.StringUtils;

/**
 * 自定义权限字符串解析器
 * @author qiaolin
 * @version 2017年4月28日
 * 
 */

public class BitPermission implements Permission{
	// 资源名称
	private String resourceIdentify;
	// 权限
	private int permissionBit;
	// 实例Id
	private String instanceId;
	
	
	public BitPermission() {}
	
	public BitPermission(String permission){
		String[] array = permission.split("\\+");
		
		if(array.length > 1){
			resourceIdentify = array[1];
		}
	
		if(resourceIdentify.isEmpty()){
			resourceIdentify = "*";
		}
		
		
		if(array.length > 2){
			permissionBit = Integer.valueOf(array[2]);
		}
		
		if(array.length > 3){
			instanceId = array[3];
		}
		
		if(instanceId.isEmpty()){
			instanceId = "*";
		}
		
	}

	@Override
	public boolean implies(Permission p) {
		
		/**
		 *  如果不是自己的权限字符串类型直接返回false
		 */
		if(!(p instanceof BitPermission)){
			return false;
		}
		
		BitPermission other = (BitPermission)p;
		
		/**
		 *  如果自己的resourceIdentity不为*,并且当前对象和传入的对象不相同返回false
		 */
		if(!("*".equals(this.resourceIdentify)||this.resourceIdentify.equals(other.resourceIdentify))){
			return false;
		}
		
		if(!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)){
			return false;
		}
		
		if(!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))){
			return false;
		}
		
		return true;
	}

}

package com.h3c.idcloud.infrastructure.common.constants;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CXF返回错误信息
 *
 * @author 张荣
 */
@XmlRootElement
public class MyError
{

	public String error;
	public String errorCode;
	public MyError(){

	}
	public MyError(String error,String errorCode){
		this.error=error;
		this.errorCode=errorCode;
	}

	//用户名与系统中已有用户名重复
	public static final MyError VERIFY_FAILED=new MyError("用户名或密码不正确！","0");

	//	public static final MyError UNKNOWN=new MyError("Other unknown factors","0");
	//用户名与系统中已有用户名重复
	public static final MyError USERNAME_AND_SYSTEMUSERNAME_REPETITION=new MyError("该用户账号已存在，请重新填写！","1");
	//	//用户不存在
	public static final MyError USER_NO_EXIST=new MyError("该用户不存在！","2");
//	//租户名与系统中已有用户名重复
//	public static final MyError TENANTNAME_AND_SYSTEMUSERNAME_REPETITION=new MyError("The tenant name and the system of the existing user name repetition","3");
//	//租户名与系统中已有租户名重复
//	public static final MyError TENANTNAME_AND_SYSTEMTENANTNAME_REPETITION=new MyError("The tenant name and the system of the existing tenants name repetition","4");
//	//用户名与注册的租户名重复
//	public static final MyError REGISTEREDUSER_AND_REGISTEREDTENANT_REPETITION=new MyError("The registered user name and registered tenants name repetition","5");
//	//Vlan资源分配失败
//	public static final MyError VLAN_RESOURCE_ALLOCATION_FAILURE=new MyError("Vlan resource allocation failure","6");
//	//不能添加此权限用户
//	public static final MyError CANNOT_ADD_USER=new MyError("Can't add the user permissions","7");
//	//不能对此用户进行操作
//	public static final MyError THIS_USER_CAN_OPERATE=new MyError("This user can not operate","8");
//	//不可更改用户名
//	public static final MyError DO_NOT_CHANGE_USERNAME=new MyError("Do not change the user name","9");
//	//该用户已审核过
//	 public static final MyError HAS_EXAMINE=new MyError("This user has exmaine","10");
//	 //添加用户角色不能为空
//	 public static final MyError THE_ROLE_CANT_BE_NULL=new MyError("this user can't be null","11");
//	 //旧密码与数据库原密码不一致
//	 public static final MyError THIS_OLD_NOT_SAME_DATABASE=new MyError("this oldpassword is not same with the database'password","12");
//	 //月结正在进行
//	 public static final MyError MONTHCLOSING_IS_RUNNING=new MyError("monthClosing is running","13");
//	 //验证码不匹配
//	 public static final MyError VERIFYCODE_IS_NOT_CORRECT=new MyError("this verifyCode is not correct","14");
//	 //租户域名与系统中已有的租户域名重复
//	 public static final MyError DOMAINNAME_AND_SYSTEMTENANTNAME_REPETITION=new MyError("this domainName have existed in database","15");
//	 //当前模板下的资费计划重复
//	 public static final MyError BILLTYPE_ALREADY_EXISTS=new MyError("the billtype is already exists","16");


}
/**
 * 
 */
package com.h3c.idcloud.core.pojo.dto.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.h3c.idcloud.core.pojo.dto.system.UserSession;
import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zharong
 *
 */
public class UserManager {

	private static Log logger = LogFactory.getLog(UserManager.class);
	
//	private static ThreadLocal<User> user = new ThreadLocal<User>();
//	
//	private static ThreadLocal<UserSession> userSession = new ThreadLocal<UserSession>();
//	
//	private static ThreadLocal<List<String>> myauthorities=new ThreadLocal<List<String>>(); 
	
//	public static User get(){
//		return (User)user.get();
//	}
//	
//	public static void removeUser(){
//		user.remove();
//	}
//	
//	public static void removeUserSession(){
//		userSession.remove();
//	}
	
	public static UserSession getUserSession(){
		HttpSession httpSession =  getCurrUserSession();
		if(null != httpSession) {
			return (UserSession)httpSession.getAttribute(WebConstants.CURRENT_PLATFORM_USER);
		}
		return null;
//		return (UserSession)userSession.get();
	}
	
//	public static void put(User currentUser){
//		user.set(currentUser);
//	}
	
//	public static void putUserSession(UserSession currentUser){
//		userSession.set(currentUser);
//	}
	
//	public static List<String> getAuthorities(){
//		return myauthorities.get();
//	}
//	
//	public static void putAuthorities(List<String> authorities){
//		myauthorities.set(authorities);
//	}
//	
//	public static void removeAuthorities(){
//		myauthorities.remove();
//	}
//	private static final UserManager userManager = new UserManager();
//
//	public static UserManager getInstance() {
//		return userManager;
//	}
	
	/**
	 * 获取含有当前用户的Session对象
	 * 
	 * @return Session
	 */
	private static HttpSession getCurrUserSession() {
		HttpServletRequest request = null;
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception ex) {
			return null;
		}
		return request.getSession();
	}

//    public static boolean hasAdminRole() {
//        UserSession userSession = getUserSession();
//        List<Role> roles = userSession.getUser().getRoles();
//        for(Role role : roles) {
//            if(role.getRoleSid().longValue() == WebConstants.RoleSid.ROLE_SUPER_ADMIN.longValue() ||
//                    role.getRoleSid().longValue() == WebConstants.RoleSid.ROLE_OP_ADMIN.longValue()||
//                    role.getRoleSid().longValue() == 11013L||//惠映销售部客服
//                    role.getRoleSid().longValue() == 11014L||//惠映市场部
//                    role.getRoleSid().longValue() == 11016L||//惠映销售部销售内勤
//            		role.getRoleSid().longValue() == 11017L)//惠映销售部管理员
//                return true;
//        }
//        return false;
//    }
    
//    public static boolean hasEnterRole() {
//        UserSession userSession = getUserSession();
//        List<Role> roles = userSession.getUser().getRoles();
//        for(Role role : roles) {
//            if(role.getRoleSid().longValue() == WebConstants.RoleSid.ROLE_ENTERPRISE_ADMIN.longValue()){
//            	return true;
//            }
//        }
//        return false;
//    }
}

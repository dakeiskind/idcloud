/**
 *
 */
package com.h3c.idcloud.infrastructure.common.util;


import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.h3c.idcloud.infrastructure.common.pojo.User;
import org.apache.http.protocol.HTTP;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 存取登录用户信息
 *
 * @author zharong
 */
public class AuthUtil {

    /**
     * 获取登录用户实体
     */
    public static User getAuthUser() {
        // Session取得
//        HttpSession session = getUserSession();
        // 获取当前Session中存在的用户
//        User user = null;
//        if (session != null) {
//            user = (User) session.getAttribute(WebConstants.CURRENT_USER);
//        }
        return null;
    }

    /** 临时兼容SSO User对象，需优化后去掉 */
//	public static UserSession getSsoAuthUser() {
//		// Session取得
//		HttpSession session = getUserSession();
//		// 获取当前Session中存在的用户
//		UserSession userSession = null;
//		if (session != null) {
//			userSession = (UserSession) session.getAttribute("SSO_TEMP_SESSION");
//		}
//
//		return userSession;
//	}

    /**
     * 获取含有当前用户的Session对象
     *
     * @return Session
     * @author zharong
     */
    private static HttpSession getUserSession() {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception ex) {
            return null;
        }
        return request.getSession();
    }

    /**
     * 获取当前用户关联的角色列表
     *
     * @return
     */
//	public static List<Role> getAuthUserRoles() {
//
//		// 获取当前用户
//		User user = (User) getUserSession().getAttribute(WebConstants.CURRENT_USER);
//
//		if (user == null || user.getModules() == null) {
//			return new ArrayList<Role>();
//		}
//
//		return user.getRoles();
//
//	}

    /**
     * 判断当前用户是否拥有对应主键的角色
     *
     * @param roleSid
     *            角色主键
     * @return true : 当前拥有此角色; false : 当前用户不拥有此角色
     */
//	public static boolean haveAuthUserRole(Long roleSid) {
//		if (roleSid == null) {
//			return false;
//		}
//		for (Role role : getAuthUserRoles()) {
//			if (roleSid.equals(role.getRoleSid())) {
//				return true;
//			}
//		}
//		return false;
//	}

    /**
     * 获取当前用户可访问的菜单和功能
     *
     * @return 功能菜单列表
     */
//	public static List<Module> getAuthUserModule() {
//
//		// 获取当前用户
//		User user = (User) getUserSession().getAttribute(WebConstants.CURRENT_USER);
//
//		if (user == null || user.getModules() == null) {
//			return new ArrayList<Module>();
//		}
//
//		return user.getModules();
//
//	}

    /**
     * 获取当前用户可访问的菜单
     *
     * @return 菜单列表
     */
//	public static List<Module> getAuthUserModuleTree() {
//
//		List<Module> modules = new ArrayList<Module>();
//		for (Module module : getAuthUserModule()) {
//			if (module.getModuleType() == 0) {
//				modules.add(module);
//			}
//		}
//		return modules;
//	}

    /**
     * 判断用户是否对应主键的功能权限
     *
     * @param moduleSid
     * @return
     */
//	public static boolean haveAuthUserModule(Long moduleSid) {
//		if (moduleSid == null) {
//			return false;
//		}
//		for (Module module : getAuthUserModule()) {
//			if (moduleSid.equals(module.getModuleSid())) {
//				return true;
//			}
//		}
//		return false;
//	}

    /**
     * 判断请求路径是否有权限
     *
     * @param path
     * @return true : 有权限; false : 没有权限
     */
//	public static boolean haveAuthUserModuleUrl(String path) {
//		if (path == null) {
//			return false;
//		}
//		// 遍历菜单并判断
//		for (Module module : getAuthUserModuleTree()) {
//			if (path.contains(module.getModuleUrl())) {
//				return true;
//			}
//		}
//		return false;
//	}

}

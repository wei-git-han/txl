//package com.css.config;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import com.css.Organ;
//import com.css.SSOActor;
//import com.css.SSOActorUtil;
//import com.css.SSOUser;
//
///**
// * 用户同步
// * @author fengsen
// *
// */
//public class SSOActorImp extends SSOActor {
//	public SSOActorImp(ServletContext servletContext) {
//		super(servletContext);
//	}
//
//	/**
//	 * 
//	 * 
//	 * session中用户标�?
//	 */
//	public static String USER = "user";
//
//	public boolean addUser(SSOUser user) {
//		try {
//			System.out.println("SSOUser远程数据添加成功");
//			String userId = user.getUserId();// 单点登录系统唯一标识主键
//			String fullname = user.getFullname();// 用户姓名
//			String account = user.getAccount();// 用户账号
//			String sex = user.getSex();
//			String organId = user.getOrganId();
//			Integer orderId = user.getOrderId();
//			String ca = user.getCa();
//			// 扩展属�?获取
//			Map<String, String> attrMap = user.getAttrMap();
//			// 单位名称
//			String EXT_DEPTNAME = attrMap.get("EXT_DEPTNAME");
//			// 部门名称
//			String EXT_DEPARTMENTNAME = attrMap.get("EXT_DEPARTMENTNAME");
//			StringBuilder sb = new StringBuilder();
//			sb.append("userId:").append(userId).append("fullname:").append(fullname).append("account:" + account)
//					.append("sex:").append(sex).append("organId:" + organId).append("orderId:" + orderId)
//					.append("ca:" + ca).append("单位名称:" + EXT_DEPTNAME).append("部门名称:" + EXT_DEPARTMENTNAME);
//			System.out.println("用户信息:" + sb.toString());
//			// EXT_DEPTNAME=单位名称
//			// EXT_DEPARTMENTNAME=部门名称
//			// EXT_DEPTTYPE=部别
//			// EXT_BIRTHDAY=生日
//			// EXT_NATION=民族
//			// EXT_TEL=办公电话
//			// EXT_NATIONALITY=国籍
//			// EXT_MARRIAGE=婚姻状况
//			// EXT_ADDRESS=家庭住址
//			// EXT_IDCARD=身份证号
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	public boolean removeUser(SSOUser user) throws Exception {
//		try {
//			System.out.println(user.getAccount() + "*******************");
//			System.out.println("SSOUser远程数据删除成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	public boolean updateUser(SSOUser user) throws Exception {
//		try {
//			System.out.println("SSOUser远程数修改成�?");
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 
//	 * 建议同步策略：account没有的新增，已存在的不变，返回改变的个数
//	 * 
//	 * 
//	 */
//	public int syncUser(List<SSOUser> userList) {
//		System.out.println(userList.size());
//		System.out.println("sc:" + servletContext);
//		return 1;
//	}
//
//	/**
//	 * 
//	 * 
//	 * 建议同步策略：organName没有的新增，已存在的不变，返回改变的个数
//	 * 
//	 * 
//	 */
//	public int syncOrgan(List<Organ> organList) {
//		System.out.println(organList.size());
//		System.out.println("sc:" + servletContext);
//		return 2;
//	}
//
//	public boolean addOrgan(Organ organ) throws Exception {
//		try {
//			System.out.println("Organ远程数添加成�?");
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	public boolean removeOrgan(Organ organ) throws Exception {
//		try {
//			System.out.println("Organ远程数删除成�?");
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	public boolean updateOrgan(Organ organ) throws Exception {
//		try {
//			System.out.println("Organ远程数更新成�?");
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean checkLogin(String loginName, ServletRequest servletRequest, ServletResponse servletResponse)
//			throws Exception {
//		return false;
//	}
//
//	@Override
//	public boolean loadUser(String loginName, ServletRequest servletRequest, ServletResponse servletResponse)
//			throws Exception {
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpSession session = request.getSession();
//		SSOUser user = (SSOUser) session.getAttribute(USER);
//		// 用户其他的属�?用户显示名称 获取应用的URL 应用URL
//		Map<String, Object> attributes = (Map) session.getAttribute("SSO_USER_INFO");
//		System.out.println(attributes.size());
//		if (attributes.size() > 0) {
//			// 用户姓名
//			System.out.println(attributes.get("FULLNAME"));
//			// 应用系统名称 逗号分隔
//			System.out.println(attributes.get("APPNAME"));
//			// 应用系统URL逗号分隔
//			System.out.println(attributes.get("INDEXURL"));
//			// 应用的标示�?号分�?
//			System.out.println(attributes.get("APPTYPE"));
//			// 应用系统其他扩展属�?
//		}
//		// 第一次登录系�?
//		if (user == null) {
//			// 这里应该通过自己的机制，查出自己系统中loginName对应的用户信息model
//			// WebApplicationContext wct =
//			// WebApplicationContextUtils.getWebApplicationContext(httpRequest
//			// .getSession().getServletContext());
//			// UserManager userManager = (UserManager)
//			// wct.getBean("userManager");
//			// user = userManager.findUserByLoginName(loginName);
//			user = new SSOUser();
//			user.setAccount(loginName);
//			user.setFullname("管理员");
//			user.setPassword("1");
//			// ---
//			// 保存用户信息到Session
//			session.setAttribute(USER, user);
//			return true ;
//		}
//		return false;
//	}
//}

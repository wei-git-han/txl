package com.css.apporgan.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.entity.BaseAppUser;
import com.css.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 以集合的形式处理组织机构
 *
 * @author gengds
 */
public class OrgUtil {

	// 获取部门树=============================================start======================================================
	/**
	 * 根据部门Id获取部门树结构(根节点ID为root)
	 * 
	 * @param organs
	 *            部门信息集合
	 * @return
	 */
	public static JSONObject getOrganTree(List<BaseAppOrgan> organs) {
		return getOrganTree(organs, "root", true, true);
	}

	/**
	 * 根据部门Id获取部门树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @return
	 */
	public static JSONObject getOrganTree(List<BaseAppOrgan> organs, String organId) {
		return getOrganTree(organs, organId, true, true);
	}

	/**
	 * 根据部门Id获取部门树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @param opened
	 *            是否展开一级子节点(true：展开)
	 * @return
	 */
	public static JSONObject getOrganTree(List<BaseAppOrgan> organs, String organId, boolean sublevel, boolean opened) {
		Map<String, BaseAppOrgan> orgMap = orgListToMapByOrganId(organs);
		if (StringUtils.isNotEmpty(organId)) {// 根节点不为空
			JSONObject organTree = setOrganTree(organs, orgMap, organId, sublevel);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		} else {
			// 根节点为空
			JSONObject organTree = setOrganTree(organs, orgMap, "root", sublevel);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		}
	}

	/**
	 * 设置部门树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organs
	 *            部门信息集合(Map)
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @return
	 */
	private static JSONObject setOrganTree(List<BaseAppOrgan> organs, Map<String, BaseAppOrgan> orgMap, String organId,
                                           boolean sublevel) {

		JSONObject organTree = new JSONObject();
		JSONArray jsons = new JSONArray();
		// 设置根节点部门
		BaseAppOrgan BaseAppOrgan = getBaseAppOrgan(orgMap, organId);
		organTree.put("id", BaseAppOrgan.getId());
		organTree.put("text", BaseAppOrgan.getName());
		organTree.put("type", "0");
		// 设置子节点部门
		List<BaseAppOrgan> subOrgs = getSubOrg(organs, organId);
		for (BaseAppOrgan subOrg : subOrgs) {
			JSONObject json = new JSONObject();
			json.put("id", subOrg.getId());
			json.put("text", subOrg.getName());
			json.put("type", "0");
			if (sublevel) {
				jsons.add(setOrganTree(organs, orgMap, subOrg.getId(), sublevel));
			} else {
				jsons.add(json);
			}
		}
		if (jsons.size() > 0) {
			organTree.put("children", jsons);
		}
		return organTree;
	}

	// 获取部门树=============================================end======================================================
	// 获取人员树=============================================start====================================================
	/**
	 * 获取部门人员树(根节点ID为root)
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param users
	 *            用户信息集合
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users) {
		return getUserTree(organs, users, "root", true, true);
	}

	/**
	 * 获取部门人员树
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param users
	 *            用户信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId) {
		return getUserTree(organs, users, organId, true, true);
	}
	/**
	 * 获取部门人员树
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param users
	 *            用户信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param hideOrgFlag
	 *            隐藏没有人员的部门          
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId, String hideOrgFlag) {
		return getUserTree(organs, users, organId, true, true,hideOrgFlag);
	}

	/**
	 * 获取部门人员树
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param users
	 *            用户信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @param opened
	 *            是否展开一级子节点(true：展开)
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId,
                                         boolean sublevel, boolean opened) {
		Map<String, BaseAppOrgan> orgMap = orgListToMapByOrganId(organs);
		Map<String, BaseAppUser> userMap = userListToMapByUserId(users);
		if (StringUtils.isNotEmpty(organId)) {// 根节点不为空
			JSONObject organTree = setUserTree(organs, orgMap, organId, users, userMap, sublevel);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		} else {
			// 根节点为空
			JSONObject organTree = setUserTree(organs, orgMap, "root", users, userMap, sublevel);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		}
	}
	/**
	 * 获取部门人员树
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param users
	 *            用户信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @param opened
	 *            是否展开一级子节点(true：展开)
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId,
                                         boolean sublevel, boolean opened, String hideOrgFlag) {
		Map<String, BaseAppOrgan> orgMap = orgListToMapByOrganId(organs);
		Map<String, BaseAppUser> userMap = userListToMapByUserId(users);
		if (StringUtils.isNotEmpty(organId)) {// 根节点不为空
			JSONObject organTree = setUserTree(organs, orgMap, organId, users, userMap, sublevel,hideOrgFlag);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		} else {
			// 根节点为空
			JSONObject organTree = setUserTree(organs, orgMap, "root", users, userMap, sublevel,hideOrgFlag);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		}
	}

	/**
	 * 获取人员树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param users
	 *            用户信息集合
	 * @param hideOrganIds
	 *            需隐藏的部门ID
	 * @param selectedOrganIds
	 *            需勾选的部门Id
	 * @param hideUserIds
	 *            需隐藏的人员ID
	 * @param selectedUserIds
	 *            需选中的人员ID
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId,
                                         String[] hideOrganIds, String[] selectedOrganIds, String[] hideUserIds, String[] selectedUserIds) {
		return getUserTree(organs, users, organId, true, true, hideOrganIds, selectedOrganIds, hideUserIds,
				selectedUserIds);
	}

	/**
	 * 获取人员树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param users
	 *            用户信息集合
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @param opened
	 *            是否展开一级子节点(true：展开)
	 * @param hideOrganIds
	 *            需隐藏的部门ID
	 * @param selectedOrganIds
	 *            需勾选的部门Id
	 * @param hideUserIds
	 *            需隐藏的人员ID
	 * @param selectedUserIds
	 *            需选中的人员ID
	 * * @param hideOrgFlag 
	 * 			隐藏没有人员的部门           
	 * @return
	 */
	public static JSONObject getUserTree(List<BaseAppOrgan> organs, List<BaseAppUser> users, String organId,
                                         boolean sublevel, boolean opened, String[] hideOrganIds, String[] selectedOrganIds, String[] hideUserIds,
                                         String[] selectedUserIds) {
		Map<String, BaseAppOrgan> orgMap = orgListToMapByOrganId(organs);
		Map<String, BaseAppUser> userMap = userListToMapByUserId(users);
		if (StringUtils.isNotEmpty(organId)) {// 根节点不为空
			JSONObject organTree = setUserTree(organs, orgMap, organId, users, userMap, sublevel, hideOrganIds,
					selectedOrganIds, hideUserIds, selectedUserIds);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		} else {
			// 根节点为空
			JSONObject organTree = setUserTree(organs, orgMap, "root", users, userMap, sublevel, hideOrganIds,
					selectedOrganIds, hideUserIds, selectedUserIds);
			JSONObject json = new JSONObject();
			json.put("opened", opened);
			organTree.put("state", json);
			return organTree;
		}
	}

	/**
	 * 设置人员树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param orgMap
	 *            部门信息集合(Map)
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param users
	 *            用户信息集合
	 * @param userMap
	 *            用户信息集合(Map)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @return
	 */
	private static JSONObject setUserTree(List<BaseAppOrgan> organs, Map<String, BaseAppOrgan> orgMap, String organId,
                                          List<BaseAppUser> users, Map<String, BaseAppUser> userMap, boolean sublevel) {
		JSONObject userTree = new JSONObject();
		JSONArray jsons = new JSONArray();
		// 设置根节点部门
		BaseAppOrgan BaseAppOrgan = getBaseAppOrgan(orgMap, organId);
		userTree.put("id", BaseAppOrgan.getId());
		userTree.put("text", BaseAppOrgan.getName());
		userTree.put("type", "0");
		// 设置人员信息
		List<BaseAppUser> sysUsers = getUsers(users, organId);
		for (BaseAppUser sysUser : sysUsers) {
			if (!StringUtils.contains("admin,sysadmin,secadmin,audadmin", sysUser.getAccount())) {
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", sysUser.getUserId());
				jsonUser.put("text", sysUser.getTruename());
				jsonUser.put("type", "1");
				jsonUser.put("deptid", sysUser.getOrganid());
				jsonUser.put("tel", sysUser.getMobile());
				jsons.add(jsonUser);
			}
		}
		// 设置子部门信息
		List<BaseAppOrgan> subOrgs = getSubOrg(organs, organId);
		for (BaseAppOrgan subOrg : subOrgs) {
			JSONObject json = new JSONObject();
			json.put("id", subOrg.getId());
			json.put("text", subOrg.getName());
			json.put("type", "0");
			if (sublevel) {
				jsons.add(setUserTree(organs, orgMap, subOrg.getId(), users, userMap, sublevel));
			} else {
				jsons.add(json);
			}
		}

		if (jsons.size() > 0) {
			userTree.put("children", jsons);
		}
		return userTree;
	}
	/**
	 * 
	 * @param organs 部门信息集合
	 * @param orgMap 部门信息集合(Map)
	 * @param organId 部门树根节点ID(默认为root)
	 * @param users 用户信息集合
	 * @param userMap  用户信息集合(Map)
	 * @param sublevel 包含全部子级(true:包含)
	 * @param hideOrgFlag 隐藏没有人员的部门
	 * @return
	 */
	private static JSONObject setUserTree(List<BaseAppOrgan> organs, Map<String, BaseAppOrgan> orgMap, String organId,
                                          List<BaseAppUser> users, Map<String, BaseAppUser> userMap, boolean sublevel, String hideOrgFlag) {
		boolean hideOrganflag = false;// 默认不隐藏
		JSONObject userTree = new JSONObject();
		JSONArray jsons = new JSONArray();
		// 设置根节点部门
		BaseAppOrgan BaseAppOrgan = getBaseAppOrgan(orgMap, organId);
		userTree.put("id", BaseAppOrgan.getId());
		userTree.put("text", BaseAppOrgan.getName());
		userTree.put("type", "0");
		// 设置人员信息
		List<BaseAppUser> sysUsers = getUsers(users, organId);
		for (BaseAppUser sysUser : sysUsers) {
			if (!StringUtils.contains("admin,sysadmin,secadmin,audadmin", sysUser.getAccount())) {
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("id", sysUser.getUserId());
				jsonUser.put("text", sysUser.getTruename());
				jsonUser.put("type", "1");
				jsonUser.put("deptid", sysUser.getOrganid());
				jsonUser.put("tel", sysUser.getMobile());
				jsons.add(jsonUser);
			}
		}
		// 设置子部门信息
		List<BaseAppOrgan> subOrgs = getSubOrg(organs, organId);
		List<String> hideOrganIds = getHideOrgIdsByParentOrgId(organs, users, organId);
		for (BaseAppOrgan subOrg : subOrgs) {
			JSONObject json = new JSONObject();
			// 判断部门是否隐藏
			hideOrganflag = false;
			if (null != hideOrganIds) {
				for (String hideOrganId : hideOrganIds) {
					if (StringUtils.equals(hideOrganId, subOrg.getId())) {
						hideOrganflag = true;
					}
				}
			}
			if (!hideOrganflag) {
				json.put("id", subOrg.getId());
				json.put("text", subOrg.getName());
				json.put("type", "0");
				if (sublevel) {
					jsons.add(setUserTree(organs, orgMap, subOrg.getId(), users, userMap, sublevel,hideOrgFlag));
				} else {
					jsons.add(json);
				}
			}
		}

		if (jsons.size() > 0) {
			userTree.put("children", jsons);
		}
		return userTree;
	}


	/**
	 * 设置人员树结构
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param orgMap
	 *            部门信息集合(Map)
	 * @param organId
	 *            部门树根节点ID(默认为root)
	 * @param users
	 *            用户信息集合
	 * @param userMap
	 *            用户信息集合(Map)
	 * @param sublevel
	 *            包含全部子级(true:包含)
	 * @param hideOrganIds
	 *            需隐藏的部门ID
	 * @param selectedOrganIds
	 *            需勾选的部门Id
	 * @param hideUserIds
	 *            需隐藏的人员ID
	 * @param selectedUserIds
	 *            需选中的人员ID
	 * @return
	 */
	private static JSONObject setUserTree(List<BaseAppOrgan> organs, Map<String, BaseAppOrgan> orgMap, String organId,
                                          List<BaseAppUser> users, Map<String, BaseAppUser> userMap, boolean sublevel, String[] hideOrganIds,
                                          String[] selectedOrganIds, String[] hideUserIds, String[] selectedUserIds) {
		// 设置判断参数
		boolean hideOrganflag = false;// 默认不隐藏
		boolean selectedOrganflag = false;// 默认不需勾选
		boolean hideUserflag = false;// 默认不隐藏
		boolean selectedUserflag = false;// 默认不需勾选

		JSONObject userTree = new JSONObject();
		JSONArray jsons = new JSONArray();
		// 设置根节点部门
		BaseAppOrgan BaseAppOrgan = getBaseAppOrgan(orgMap, organId);
		userTree.put("id", BaseAppOrgan.getId());
		userTree.put("text", BaseAppOrgan.getName());
		userTree.put("type", "0");
		// 设置人员信息
		List<BaseAppUser> sysUsers = getUsers(users, organId);
		for (BaseAppUser sysUser : sysUsers) {
			if (!StringUtils.contains("admin,sysadmin,secadmin,audadmin", sysUser.getAccount())) {
				JSONObject jsonUser = new JSONObject();
				// 判断用户是否隐藏
				hideUserflag = false;
				if (null != hideUserIds) {
					for (String hideUserId : hideUserIds) {
						if (StringUtils.equals(hideUserId, sysUser.getUserId())) {
							hideUserflag = true;
						}
					}
				}
				// 判断用户是否需勾选
				selectedUserflag = false;
				if (null != selectedUserIds) {
					for (String selectedUserI : selectedUserIds) {
						if (StringUtils.equals(selectedUserI, sysUser.getUserId())) {
							selectedUserflag = true;
						}
					}
				}
				if (!hideUserflag) {
					jsonUser.put("id", sysUser.getUserId());
					jsonUser.put("text", sysUser.getTruename());
					jsonUser.put("type", "1");
					jsonUser.put("deptid", sysUser.getOrganid());
					jsonUser.put("tel", sysUser.getMobile());
					jsons.add(jsonUser);
					if (selectedUserflag) {
						JSONObject json = new JSONObject();
						json.put("selected", true);
						jsonUser.put("state", json);
					}
				}

			}
		}
		// 设置子部门信息
		List<BaseAppOrgan> subOrgs = getSubOrg(organs, organId);
		for (BaseAppOrgan subOrg : subOrgs) {
			JSONObject jsonOrgan = new JSONObject();
			// 判断部门是否隐藏
			hideOrganflag = false;
			if (null != hideOrganIds) {
				for (String hideOrganId : hideOrganIds) {
					if (StringUtils.equals(hideOrganId, subOrg.getId())) {
						hideOrganflag = true;
					}
				}
			}
			// 判断部门是否需勾选
			selectedOrganflag = false;
			if (null != selectedOrganIds) {
				for (String selectedOrganId : selectedOrganIds) {
					if (StringUtils.equals(selectedOrganId, subOrg.getId())) {
						selectedOrganflag = true;
					}
				}
			}
			if (!hideOrganflag) {
				jsonOrgan.put("id", subOrg.getId());
				jsonOrgan.put("text", subOrg.getName());
				jsonOrgan.put("type", "0");
				if (selectedOrganflag) {
					JSONObject json = new JSONObject();
					json.put("selected", true);
					jsonOrgan.put("state", json);
				}
				if (sublevel) {
					jsons.add(setUserTree(organs, orgMap, subOrg.getId(), users, userMap, sublevel, hideOrganIds,
							selectedOrganIds, hideUserIds, selectedUserIds));
				} else {
					jsons.add(jsonOrgan);
				}
			}
		}

		if (jsons.size() > 0) {
			userTree.put("children", jsons);
		}
		return userTree;
	}
	
	/**
	 * 获取需要隐藏的部门ids
	 * @param organs  部门信息集合
	 * @param users		 用户信息集合
	 * @param organId 	部门树节点ID
	 * @return
	 */
	public static List<String> getHideOrgIdsByParentOrgId(List<BaseAppOrgan> organs,List<BaseAppUser> users,String organId) {
		List<String> subOrgIds = new ArrayList<String>();
		for (BaseAppOrgan organ : organs) {
			if (StringUtils.equals(organ.getParentId(), organId)) {
				subOrgIds.add(organ.getId());
			}
		}
		Map<String, Object> contain=new HashMap<String, Object>();
		List<String> hideOrganIds = new ArrayList<String>();
		if(subOrgIds!=null&&subOrgIds.size()>0) {
			for (BaseAppUser baseAppUser : users) {
				if(subOrgIds.contains(baseAppUser.getOrganid())) {
					contain.put(baseAppUser.getOrganid(), "true");
				}
			}
			if(contain!=null&&contain.size()>0) {
				for (String orgid : subOrgIds) {
					if(!contain.containsKey(orgid)) {
						hideOrganIds.add(orgid);
					}
				}
			}
		}
		return hideOrganIds;
	}

	// 获取人员树=============================================end======================================================
	// 获取部门和用户基本信息============================start=============================================================

	/**
	 * 根据用户Id获取用户信息
	 * 
	 * @param users
	 *            用户信息集合
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public static BaseAppUser getBaseAppUser(Map<String, BaseAppUser> users, String userId) {
		return users.get(userId);
	}

	/**
	 * 根据部门Id获取部门信息
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门ID
	 * @return
	 */
	public static BaseAppOrgan getBaseAppOrgan(Map<String, BaseAppOrgan> organs, String organId) {
		return organs.get(organId);
	}

	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @param organs
	 *            部门信息集合
	 * @param organId
	 *            部门ID
	 * @return
	 */
	public static List<BaseAppOrgan> getSubOrg(List<BaseAppOrgan> organs, String organId) {
		List<BaseAppOrgan> subOrgs = new ArrayList<BaseAppOrgan>();
		for (BaseAppOrgan organ : organs) {
			if (StringUtils.equals(organ.getParentId(), organId)) {
				subOrgs.add(organ);
			}
		}
		return subOrgs;
	}

	/**
	 * 根据部门Id获取部门用户信息
	 * 
	 * @param users
	 *            用户信息集合
	 * @param organId
	 *            部门ID
	 * @return
	 */
	public static List<BaseAppUser> getUsers(List<BaseAppUser> users, String organId) {
		List<BaseAppUser> tempUsers = new ArrayList<BaseAppUser>();
		for (BaseAppUser user : users) {
			if (StringUtils.equals(user.getOrganid(), organId)) {
				tempUsers.add(user);
			}
		}
		return tempUsers;
	}

	// 获取部门和用户基本信息============================end================================================================
	// list集合转换为Map集合=================================start=======================================================
	/**
	 * 将部门List转换为Map(以部门ID为key)
	 * 
	 * @param organs
	 * @return 部门信息集合
	 */
	public static Map<String, BaseAppOrgan> orgListToMapByOrganId(List<BaseAppOrgan> organs) {
		Map<String, BaseAppOrgan> orgMap = new HashMap<String, BaseAppOrgan>();
		for (BaseAppOrgan organ : organs) {
			orgMap.put(organ.getId(), organ);
		}
		return orgMap;
	}

	/**
	 * 将人员信息List转换为Map(以人员ID为key)
	 * 
	 * @param users
	 *            用户信息集合
	 * @return
	 */
	public static Map<String, BaseAppUser> userListToMapByUserId(List<BaseAppUser> users) {
		Map<String, BaseAppUser> userMap = new HashMap<String, BaseAppUser>();
		for (BaseAppUser user : users) {
			userMap.put(user.getUserId(), user);
		}
		return userMap;
	}

	// list集合转换为Map集合=================================end=========================================================
}

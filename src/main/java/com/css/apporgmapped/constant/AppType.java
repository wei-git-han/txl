package com.css.apporgmapped.constant;

import com.alibaba.fastjson.JSONObject;

/**
 * 应用类型枚举类 单应用类型(one)：一套系统中只有一个应用，如首长办公,首长办公秘书版,公文转办,通讯录... ...
 * 多应用类型(many)：一套系统中存在多个应用(可以理解为局级应用)，如公文处理，电子保密室,数字档案室... ...
 * 
 * @author gengds
 */
public enum AppType {

	// --------单应用类型-----------
	// 首长办公应用标识
	APP_SZBG("szbg", "one", ""),
	// 首长办公[秘书版]应用标识
	APP_SZBGMSB("szbgmsb", "one", ""),
	// 公文转办[综合秘书]应用标识
	APP_GWZB("gwzb", "one", "/app/zhms"),
	// 电子收发室应用标识
	APP_DZSFS("dzsfs", "one", ""),
	// 通讯录应用标识
	APP_TXL("txl", "one", ""),
	// 营房管理应用标识
	APP_YFGL("yfgl", "one", ""),
	// 备忘录应用标识
	APP_BWL("bwl", "one", ""),
	// 意见反馈应用标识
	APP_YJFK("yjfk", "one", "app/yjfk"),
	// 打印标识
	OUT_INFO("out","one", ""),
	//打印标识
	TRANSFER_INFO("transfer","one", ""),
	//打印标识
	PZL_INFO("pzl","one", ""),
	
	// --------多应用类型---------
	// 公文处理应用的标识
	APP_GWCL("gwcl", "many", "/app/gwcl"),
	// 电子保密室应用的标识
	APP_DZBMS("dzbms", "many", "/app/dzbms"),
	// 数字档案室应用的标识
	APP_SZDAS("szdas", "many", "/app/szdas"),
	// 值班管理应用的标识（部级、局级）
	APP_ZBGL("zbgl", "many", "/app/zbgl"),
	// 任务管理应用的标识
	APP_RWGL("rwgl", "many", "/app/rwgl"),
	// 请销假管理应用的标识
	APP_QXJGL("qxjgl", "many", "/app/qxjgl"),
	// 会议申请应用的标识
	APP_HYSQ("hysq", "many", "/app/hygl"),
	// 会议管理
	APP_HYGL("hygl", "many", "/app/hygl"),
	// 车辆申请应用的标识
	APP_CLSQ("clsq", "many", "/app/clsq"),
	// 车辆管理应用的标识
	APP_CLGL("clgl", "many", "/app/clgl"),
	// 出差申请
	APP_CCSQ("ccsq", "many", ""),
	// 交办事项
	APP_JBSX("jbsx", "many", "/app/jbsx"),
	// 重大安全事故报告
	APP_ZDAQSGBG("zdaqsgbg", "many", ""),
	// 在位情况
	APP_ZWQK("zwqk", "many", ""),
	// 一周工作安排[领导日程安排应用标识]
	APP_YZGZAP("yzgzap", "many", "/app/yzgzap");

	// app应用类型
	private String type;
	// 应用类型(one:单应用，many:多应用)
	private String appFlag;
	// 应用路径
	private String webUri;

	private AppType(String type, String appFlag, String webUri) {
		this.type = type;
		this.appFlag = appFlag;
		this.webUri = webUri;
	}

	/**
	 * 获取应用类型标识
	 * 
	 * @return one:单应用类型，many:多应用类型
	 */
	public String getAppFlag() {
		return appFlag;
	}

	/**
	 * 获取应用类型标识
	 * 
	 * @return 应用类型标识
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获取应用路径
	 * 
	 * @return 应用类型路径
	 */
	public String getWebUri() {
		return webUri;
	}

	/**
	 * 获取应用类型json字符串
	 */
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("appFlag", appFlag);
		json.put("webUri", webUri);
		return json.toJSONString();
	}

}

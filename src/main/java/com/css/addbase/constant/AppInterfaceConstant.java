package com.css.addbase.constant;

/**
 * 接口常量类；
 * 
 * TODO：常量是固定不变的数据，如果有变化的常量，请不要在此配置文件中进行定义；
 * 
 * 需要通过http协议访问请求的接口，前缀必须是：WEB_INTERFACE_
 * 
 * 各个应用之间所调用的接口定义都需要定义在该文件中，需要写清楚备注，什么功能使用的，需要怎么调用；
 * 
 * @author 中软信息系统工程有限公司
 * @email  
 * @date 2017年12月01日 下午1:23:52
 */
public class AppInterfaceConstant {
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 公文处理应用调用：公文流转送审批，到综合秘书保存发送记录的接口定义；
	 */
	public final static String WEB_INTERFACE_GWCL_TO_ZHMS_SAVE = "/secretary/api/saveSendSecretary";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 交办事项应用调用：交办事项上报给首长办公秘书版的接口定义；
	 */
	public final static String WEB_INTERFACE_JBSX_TO_SZBGMS = "/secretary/api/deliverBack";
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书设置：部级综合秘书设置同步至各局级综合秘书设置新增接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_ADD = "/app/gwcl/documentclerkset/saveJj";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书设置：部级综合秘书设置同步至各局级综合秘书设置修改接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_EDIT = "/app/gwcl/documentclerkset/updateJj";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书设置：部级综合秘书设置同步至各局级综合秘书设置删除接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_DEL = "/app/gwcl/documentclerkset/deleteJj";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书调用：综合秘书保存意见信息到公文处理的接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_SAVE_OPINION = "/doc/api/opinionSave";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书调用：综合秘书保存抄清信息到公文处理的接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_SAVE_CQ = "/doc/api/savecq";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书调用：综合秘书返回承办人到公文处理的接口定义；
	 */
	public final static String WEB_INTERFACE_ZHMS_TO_GWCL_BACK = "/doc/api/returnCbr";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 综合秘书调用：首长秘书返回综合秘书的接口定义；
	 */
	public final static String WEB_INTERFACE_SZMS_TO_ZHMS = "/doc/api/updateFlowAndOpinionInfo";
	
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
	 * 数字档案室应用调用：公文数据归档到数字档案室的接口定义；
	 */
	public final static String WEB_INTERFACE_GWCL_TO_SZDAS = "/reviewstamp/updateByDocId";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，卡片页形式的列表页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_CARD = "/api/myOffice/myOfficeCard";
	/**
	 * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，表格页形式的列表页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_TABLE = "/api/myOffice/myOfficeTable";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，编辑信息页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_EDIT = "/api/myOffice/info";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，意见回复数据的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_ANSWER = "/api/myOffice/answer";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，开始办理状态修改的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_KSBL = "/api/myOffice/startBL";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的办件，办结办件状态修改的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDBJ_FINISHED = "/api/myOffice/finishBj";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的阅件，阅件卡片页列表数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDYJ_CARD = "/api/myRead/myReadCard";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的阅件，阅件列表页列表数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDYJ_TABLE = "/api/myRead/myRead";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的阅件，阅件列表页合计数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDYJ_COUNT = "/api/myRead/myReadCount";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的阅件，编辑信息加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDYJ_EDIT = "/api/myRead/info";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的阅件，状态修改的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDYJ_CHANGE_STATUS = "/api/myRead/changeReadStatus";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，卡片页列表页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_CARD = "/api/myRead/myBorrow";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，列表页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_TABLE = "/api/myRead/myBorrowList";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，归还提醒列表页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_GHTX = "/api/myRead/remaindDaysList";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，借阅申请页面数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_JYSQ = "/api/myRead/borrowApply";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，借阅回复数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_ADVISE = "/api/myRead/advise";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：我的借阅，查看版式文件数据加载的接口定义
     */
    public final static String WEB_INTERFACE_DZBMS_TO_GWCL_WDJY_FORMAT = "/api/myRead/formaFileUrl";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 公文处理应用调用：公文数据归档，公文数据在归档时先到电子保密室，再到数字档案室的接口定义
     */
    public final static String WEB_INTERFACE_GWCL_TO_DZBMS_ARCHIVE = "/fileinfo/save";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 电子保密室应用调用：电子保密室到公文处理的消息提醒的接口定义
     */
    public final static String WEB_INTERFACE_GWCL_TO_DZBMS_MSG = "/msg/send";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 负一屏应用调用：负一屏调用部级值班管理信息接口定义
     */
    public final static String WEB_INTERFACE_ZBGL_TO_FYP_BJ = "/getBjInfo";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 负一屏应用调用：负一屏调用局级值班管理信息接口定义
     */
    public final static String WEB_INTERFACE_ZBGL_TO_FYP_JJ = "/getJjInfo";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 负一屏应用调用：负一屏调用通讯录信息接口定义
     */
    public final static String WEB_INTERFACE_TXL_TO_FYP = "/getTxlInfo";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 负一屏应用调用：部级负一屏数据，从首长办公获取首长活动安排，展示到负一屏中接口定义
     */
    public final static String WEB_INTERFACE_SZBG_HDAP_TO_FYP = "/secretary/api/weekactivity";
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 工作动态应用调用：工作动态向首长办公秘书版报送数据接口定义；（该应用暂时不用了，使用肖利剑提供的信息服务）
     */
    public final static String WEB_INTERFACE_GZDT_TO_SZBGMS = "/workdynamic/datatransmit";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 在位情况应用调用：首长在位情况数据同步到首长办公应用中的接口定义；
     */
    public final static String WEB_INTERFACE_ZWQK_TO_SZBG_SAVE = "/chairman/api/chairmanStatusSave";
    
    /**
     * @author mashuwen
	 * @date 2017年12月03日
     * 全文检索接口：数字档案室桌面检索接口定义
     */
    public final static String WEB_INTERFACE_SEARCH_SZDAS = "/api/search/szdas";
    
	
}

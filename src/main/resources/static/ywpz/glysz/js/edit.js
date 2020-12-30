var id = getUrlParam2("id");
var appNameVal = '<c:out value="${appName}"/>';
var saveUrl = {"url":"/app/txl/adminconfig/saveOrUpdate","dataType":"text"};  //保存
var editInfo = {"url":"/app/txl/adminconfig/info","dataType":"text"}; //编辑数据
//editInfo = {"url":"../data/info.json","dataType":"text"}; //编辑数据
//获取某人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
var getUserAdminTypeUrl = {"url":"/app/txl/adminconfig/getAuthor","dataType":"text"};
//getUserAdminTypeUrl = {"url":"../../main/data/getAuthor.json","dataType":"text"};
var userTree; //部门树
var pageModule = function(){
	var initrolefn = function(){
		$ajax({
			url:getUserAdminTypeUrl,
			async:false,
			success:function(data){
				if(data=="2"){
					userTree = {"url":"/txluser/tree","dataType":"text"}; //部门树
				}else{
					userTree = {"url":"/app/base/user/allTree","dataType":"text"}; //人员选择树
					//userTree = {"url":"../data/allTree.json","dataType":"text"}; //人员选择树
				}
			}
		})
	}
	
	var initdatafn = function(){
		if(id!="" && !!id){
			$ajax({
				url:editInfo,
				data:{id:id},
				success:function(data){
					setformdata(data);
					$("#roleType").val("局管理员");
				}
			})
		}
	}
	
	var initother = function(){
		$("#userName").createUserTree({
			url : userTree,
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userName").val(data.node.text);
				$("#userId").val(data.node.id);
			}
		});
		
		$("#quxiao,#fanhui").click(function(){
			window.location.href="/ywpz/glysz/html/index.html";
		})

		var sub_flag=false;
		$("#save").click(function(){
			var userName=$("#userName").val();
			var userId=$("#userId").val();
			if(userId == ''){
				newbootbox.alertInfo("请选择用户！");
				return;
			}
			if(sub_flag){
				return;
			}
			sub_flag=true;
			$ajax({
				url:saveUrl,
				data:{id:id,userName:userName,userId:userId,adminType:"2"},
				type: "GET",
				success:function(data){
					if(data.result == "success") {
						if(data.isShow == true){
                            newbootbox.alertInfo('保存成功！').done(function(){
                                window.location.href = "/ywpz/glysz/html/index.html";
                            });
						}else {
                            newbootbox.alertInfo('保存成功！').done(function(){
                            	top.location.href="/index.html" ;
                            });
						}
					}else if(data.result == "exist"){
						newbootbox.alertInfo("重复设置！");
					}else{
						newbootbox.alertInfo("保存失败！");
					}
					sub_flag=false;
				}
			});
			
		})
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initrolefn();
			initdatafn();
			initother();
		}
	};
	
}();

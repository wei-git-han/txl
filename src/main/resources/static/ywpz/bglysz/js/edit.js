var id = getUrlParam2("id");
var saveUrl = {"url":"/app/txl/adminconfig/saveOrUpdate","dataType":"text"};  //保存
var editInfo = {"url":"/app/txl/adminconfig/info","dataType":"text"}; //编辑数据
//editInfo = {"url":"../data/info.json","dataType":"text"}; //编辑数据
var userTree ={"url":"/txluser/tree","dataType":"text"}; //人员选择树
//userTree ={"url":"../../glysz/data/allTree.json","dataType":"text"}; //人员选择树
var shouZhangTree ={"url":"/app/txl/adminconfig/allShouZhang","dataType":"text"}; //人员选择树
//shouZhangTree ={"url":"../data/allShouZhang.json","dataType":"text"}; //人员选择树
var getUserAdminTypeUrl = {"url":"/app/txl/adminconfig/getAuthor","dataType":"text"};
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:editInfo,
			data:{id:id},
			success:function(data){
				setformdata(data);
				$("#roleType").val("部管理员");
			}
		})
	}

	var initother = function(){
/*		$("#leaderName").createUserTree({
			url : leaderTreeUrl,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data,treessname,treessid) {
				$("#leaderId").val(treessid);
				$("#leaderName").val(treessname);
			},
			deselectnode:function(e,data,treessname,treessid){
				$("#leaderId").val(treessid);
				$("#leaderName").val(treessname);
		   }
		});*/
		$("#userName").createUserTree({
			url : userTree,
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userName").val(data.node.text);
				$("#userId").val(data.node.id);
			}
		});
 
		//选择首长       seniorOfficial
		/*$("#seniorOfficial").createUserTree({
			url : shouZhangTree,
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#seniorOfficial").val(data.node.text);
				$("#seniorOfficialId").val(data.node.id);
			}
		});*/
		
		$("#quxiao,#fanhui").click(function(){
			window.location.href="/ywpz/bglysz/html/index.html";
		})
		var sub_flag=false;
		$("#save").click(function(){
			var userName=$("#userName").val();
			var userId=$("#userId").val();
			/*var seniorOfficial=$("#seniorOfficial").val();
			var seniorOfficialId=$("#seniorOfficialId").val();*/
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
				data:{id:id,userName:userName,userId:userId,adminType:"1"/*,seniorOfficial:seniorOfficial,seniorOfficialId:seniorOfficialId*/},
				type: "GET",
				success:function(data){
					if(data.result == "success") {
						if(data.isShow == true){
							if(data.adminTypetemp =="1"||data.adminTypetemp =="3"){
                                newbootbox.alertInfo('保存成功！').done(function(){
                                    window.location.href = "/ywpz/bglysz/html/index.html";
                                });
							}else if(data.adminTypetemp =="2"){
                                newbootbox.alertInfo('保存成功！').done(function(){
                                    $ajax({
                                        url: getUserAdminTypeUrl,
                                        type: "GET",
                                        success: function(data) {
                                            if(data=="3"){//即是部管理员又是局管理员,按照部管理员权限分配
                                                parent.$('#departAdmin').show();
                                                parent.$('#juAdmin').show();
                                                parent.$('#iframe100').attr("src","../../bglysz/html/index.html");
                                            }else if (data=="0"||data=="1"){//超级管理员或部管理员
                                                parent.$('#departAdmin').show();
                                                parent.$('#juAdmin').show();
                                                parent.$('#iframe100').attr("src","../../bglysz/html/index.html");
                                            }else{ //局管理员
                                                parent.$('#juAdmin').show();
                                                parent.$('#departAdmin').hide();
                                                parent.$('#iframe100').attr("src","../../glysz/html/index.html");
                                                parent.$("#departAdmin").removeClass("active");
                                                parent.$('#juAdmin').addClass("active");
                                            }
                                        }
                                    });
                                });
							}

						}else {
                            newbootbox.alertInfo('保存成功！').done(function(){
                                top.location.href="/index.html";
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
			initdatafn();
			initother();
		}
	}
}();

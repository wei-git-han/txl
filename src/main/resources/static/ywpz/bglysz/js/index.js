var grid = null;
var tableUrl = {"url":"/app/txl/adminconfig/list","dataType":"text"};
//tableUrl = {"url":"../data/list.json","dataType":"text"};
var delUrl = {"url":"/app/txl/adminconfig/delete","dataType":"text"};
var getUserAdminTypeUrl = {"url":"/app/txl/adminconfig/getAuthor","dataType":"text"};
var pageModule = function() {
	var initgrid = function() {
		grid = $("#gridcont").createGrid({
			columns: [
                {display: "单位名称",name:"orgName",width: "30%",align: "left",render: function(rowdata,n){
                        return rowdata.orgName;
                    }},
				  {display: "部门",name:"deptName",width: "30%",align: "left",render: function(rowdata,n){
					  return rowdata.deptName;   
				  }}, 
				  /*{display: "单位名称",name:"deptName",width: "40%",align: "left",render: function(rowdata,n){
					  return rowdata.deptName;   
				  }},*/
				  {display: "姓名",name: "userName",width: "20%",align: "center",render: function(rowdata,n){
					  return rowdata.userName;   
				  }}, 
				  
				  {display: "管理员类型",name: "adminType",width: "10%",align: "center",render: function(rowdata,n){
					  return "部管理员"
				  }}, 
				  
				  /*{display: "首长姓名",name: "seniorOfficial",width: "15%",align: "center",render: function(rowdata,n){
					  return rowdata.seniorOfficial;
				  }},*/
				  
				  {display: "操作",name: "do",width: "10%",align: "center",render: function(rowdata,n){
					  return '<i class="fa fa fa-pencil" style="cursor:pointer;background:#5498EB;padding:4px 5px;color:#fff;" onclick="editfn(\''+rowdata.id+'\')" title="编辑"></i>';
				  }}
				 ],
			width: "100%",
			height: "100%",
			checkbox: true,
			rownumberyon: true,
			paramobj:{type:'3',adminType:"1"},
			overflowx: false,
            pagesize: 15,
			url: tableUrl
			
		});
	}

	var initother = function() {
		$("#add").click(function() {
			window.location.href="edit.html";
		});
		
		$("#edit").click(function() {
			var datas = grid.getcheckrow();
			var ids=[];
			if(datas.length < 1 || datas.length > 1) {
				newbootbox.alertInfo("请选择一条数据进行编辑！");
			} else {
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				window.location.href="edit.html?id="+ids[0];
			}
			
		});

		$("#plsc").click(function() {
			var datas = grid.getcheckrow();
			var ids=[];
			if(datas.length < 1) {
				newbootbox.alertInfo("请选择要删除的数据！");
			} else {
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				newbootbox.confirm({
					 title: "提示",
				     message: "是否要进行删除操作？",
				     callback1:function(){
				    	 $ajax({
								url: delUrl,
								type: "GET",
								data: {"ids": ids.toString()},
								success: function(data) {
									if(data.result == "success") {
                                        if(data.isShow== false){
                                            newbootbox.alertInfo('删除成功！').done(function(){
                                                top.location.href="/index.html" ;
                                            });
                                        }else {
                                        	if(data.adminTypetemp=="2"){
                                                newbootbox.alertInfo('删除成功！').done(function(){
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
											}else {
                                                newbootbox.alertInfo('删除成功！').done(function(){
                                                    window.location.href = "/ywpz/bglysz/html/index.html";
                                                });
											}
                                        }
									}else{
										newbootbox.alertInfo("删除失败！");
									}
								}
							})
				     }
				});
			}
		});
		
	}

	return {
		//加载页面处理程序
		initControl: function() {
			initgrid();
			initother();
		}
	}

}();

//编辑
var editfn = function(id){
	window.location.href="edit.html?id="+id;
}
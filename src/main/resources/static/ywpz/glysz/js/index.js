var grid = null;
var tableUrl = {"url":"/app/txl/adminconfig/juList","dataType":"text"};
//tableUrl = {"url":"../data/juList.json","dataType":"text"};
var delUrl = {"url":"/app/txl/adminconfig/delete","dataType":"text"};
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
				  {display: "姓名",name: "userName",width: "20%",align: "center",render: function(rowdata,n){
					  return rowdata.userName;   
				  }}, 
				  {display: "管理员类型",name: "adminType",width: "10%",align: "center",render: function(rowdata,n){
					  return "局管理员";
				  }}, 
				  {display: "操作",name: "do",width: "10%",align: "center",render: function(rowdata,n){
					  var btns="";
					  if(rowdata.editFlag!="" && !!rowdata.editFlag){
						  btns= '<i class="fa fa-pencil btns"  onclick="editfn(\''+rowdata.id+'\')" title="编辑"></i>'+
						  		'<i class="fa fa-trash-o btns"  onclick="delfn(\''+rowdata.id+'\')" title="删除"></i>';
					  }
					  return btns;
				  }}
				 ],
			width: "100%",
			height: "100%",
			checkbox: true,
			rownumberyon: true,
			paramobj:{type:'3',adminType:"2"},
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
//删除
var delfn = function(id){
	newbootbox.confirm({
		 title: "提示",
	     message: "是否要进行删除操作？",
	     callback1:function(){
	    	 $ajax({
					url: delUrl,
					type: "GET",
					data: {"ids":id},
					success: function(data) {
						if(data.result == "success") {
							if(data.isShow== false){
                                newbootbox.alertInfo('删除成功！').done(function(){
                                    top.location.href="/index.html" ;
                                });
							}else {
                                newbootbox.alertInfo('删除成功！').done(function(){
                                    window.location.href = "/ywpz/glysz/html/index.html";
                                });
							}
						}else{
							newbootbox.alertInfo("删除失败！");
						}
					}
				})
	     }
	});
}
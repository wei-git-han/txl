var grid=null;
var pageModule = function(){
	var treeid = "";
	/*主要数据加载*/
	var initLxr = function(){
		/*联系人数据*/
		$ajax({
			url:lxr,
			success:function(data){
				$("#lxrContent").html("");
				$.each(data, function(i,obj) {
					var html2="";
					//var isSc ="activelove";
					$.each(obj.children, function(j,obj2) {
					/*	if(obj2.isSc == "true"){
							isSc = "haspic";
						}else if(obj2.isSc == "false"){
							isSc ="activelove";
						}else{
							isSc ="activelove";
						}*/
						html2+=  '<div class="center_model">'+
						 				'	<div  style="width:100%;height:100%;position:relative">'+	
										'	<div class="center_model_wrap">'+
										'		<div class="model_name">'+
										'			<div style="display:inline-block;">'+		
										'				<span   onclick="clickfn(\''+obj2.userid+'\')"><a style="font-size:24px;">'+obj2.fullname+'</a></span><i class="fa fa-star starsc" onclick="delscfn(\''+obj2.userid+'\')"></i>'+
										'			</div>'+
										'		</div>'+
										'		<div class="model_zhiwu">'+
										'			<span>'+obj2.dept+'</span>'+
										'		</div>'+
										'		<div class="model_tel">'+
										'			<img src="templates/admin/images/tel_03.png" class="imgs" />'+
										'			<span>'+obj2.telephone+'</span>'+
										'		</div>'+
										'		<div class="model_phone">'+
										'			<img src="templates/admin/images/phpne.png" class="imgs" />'+
										'			<span>'+obj2.mobile+'</span>'+
										'		</div>'+
										'	</div>'+
										'	<div class="model_adress">'+
										'		<img src="templates/admin/images/adress_20.png" class="imgs" /><span class="adresslemit">'+obj2.address+'</span>'+
										'	</div>'+
										'	</div>'+
										'</div>';
					})	
					$("#lxrContent").html(html2);
				})
			}
		});
		
		/*右侧部门电话数据*/
		$ajax({
			url:bmdh,
			success:function(data){
				$("#bmdh").html("");
				$.each(data, function(i,obj) {
					$("#bmdh").append(
						'<div class="model">'+
							'<div class="model_title"><a style="cursor:pointer"   onclick="clickbmdhfn(\''+obj.id+'\')">'+obj.orgName+'</a><i class="fa fa-trash-o ljt" style="display:none;cursor:pointer;float: right;padding-right:5px;padding-top:3px;"  onclick="delfn(\''+obj.id+'\')"    style="float:right;cursor:pointer;"></i></div>'+
							'<div class="model_title2"><img src="templates/admin/images/tel_03.png" class="telpic2" /><span>'+obj.orgTel+'</span></div>'+
							'<div class="model_title2" style="width:100%;margin-top:3px;"><img src="templates/admin/images/adress_20.png" style="float:left;margin-top: 3px;" class="telpic2" /><span  style="display: block;width: 155px; text-overflow: ellipsis;overflow: hidden; white-space: nowrap;" title='+obj.orgAddress+'>'+obj.orgAddress+'</span></div>'+
						'</div>'
					);
				
				})
			}
		})
	}

	
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                columns:[
                	 {display:"姓名",name:"fullname",width:"10%",align:"center",paixu:false,render:function(rowdata,n){
                        return  '<a onclick="clickfn(\''+rowdata.userid+'\')">'+rowdata.fullname+'</a>';
                     }},
                     {display:"手机号",name:"mobile",width:"17%",align:"center",paixu:false,render:function(rowdata){
                         return rowdata.mobile;                                        
                     }},
                     {display:"座机号",name:"telephone",width:"15%",align:"center",paixu:false,render:function(rowdata){
                          return rowdata.telephone;                                     
                     }},
                     {display:"职务",name:"post",width:"10%",align:"left",paixu:false,render:function(rowdata){
                           return rowdata.post;                                      
                     }},
                     {display:"房间号",name:"address",width:"20%",align:"left",paixu:false,render:function(rowdata){
                         return rowdata.address;                                        
                      }},
                     {display:"部门",name:"organName",width:"15%",align:"left",paixu:false,render:function(rowdata){
                        return rowdata.organName;                                         
                     }},
                     {display:"操作",name:"do",width:"13%",align:"center",paixu:false,render:function(rowdata){
                    	 if(rowdata.isSc == 0 ){
                    		 return '<a class="ysc" title="收藏" href="javascript:addscfn(\''+rowdata.userid+'\')"><i class="fa fa-star"></i></a>';
                    	 }else{
                    		 return '<a class="sc" title="取消收藏" href="javascript:delscfn(\''+rowdata.userid+'\')"><i class="fa fa-star"></i></a>';
                    	 }
                      }}
                 ],
                width:"100%",
                checkbox: false,
                rownumberyon:true,
                paramobj:{},
                overflowx:false,
                pageyno:false,
                loadafter:function(data){
                	if(true == data.manager){
                		$("#daoru").show();
                		$("#add_bmdh").show();
                		$(".ljt").show();
                	}else{
                		$("#daoru").hide();
                		$("#add_bmdh").hide();
                		$(".ljt").hide();
                	}
                },
                url: tablegrid
           });
	}
	
	/*全部联系人树加载*/
	var inittree = function(){
		$ajax({
			url:txltree,
			success:function(data){
				$("#tree_2").jstree({
				    "plugins": ["wholerow", "types"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				
				$("#tree_2").on("select_node.jstree", function(e,data) { 
				    var id = $("#" + data.selected).attr("id");
				    var searchValue = $("#searchValue").val();
				    $("#treeSecId").val(id);
					grid.setparams({"orgid":id,"searchValue":searchValue});
					grid.refresh();
				});
				
			}
		})

	}
	
	var initother = function(){
		//搜索划过更换图标
		$(".search_btn").hover(function(){
			$(this).attr("src","templates/admin/images/search-hover.png");
		},function(){
			$(this).attr("src","templates/admin/images/search-normal.png");
		});
		
		/*左按钮*/
		$("#leftbtn").click(function(){
			var left  =  parseInt($(".lxrContent").css("left"));
			if(left<=-190){
				left+=190;
				$(".lxrContent").css("left",left+"px");
			}else if(left>-190 || left<0){
				left+=Math.abs(left);
				$(".lxrContent").css("left",left+"px");
			}else{
				return;
			}
		})
			
		/*右按钮*/
		$("#rightbtn").click(function(){
			var left  =  parseInt($(".lxrContent").css("left"));
			var width1 = $(".lxrContent").width();
			var width2 = $(".lxrContent").parent().width();
			if(Math.abs(width1)>(width2+Math.abs(left))){
				var left1= width1-width2-Math.abs(left);
				if(left1>=190){
					left-=190;
				}else{
					left-=left1;
				}
				$(".lxrContent").css("left",left+"px");
			}else{
				return;
			}
		})
		
		
		//部门电话新增
		$("#add_bmdh").click(function(){
			window.location.href="txl/html/bmdh_add.html";
		})
		
		
		//导入
		$("#daoru").click(function(){
			var gettime = $("#starttime").val();
			$ajax({
				url:importdata,
				data:{gettime:gettime},
				success:function(data){
					$("#starttime").val(data.starttime);
					newbootbox.alertInfo("同步数据成功！").done(function(){
						initgrid();
					});
				}
			})
		});
		
		
		/*搜索*/
		$(".search_btn").click(function(){
			var id = $("#treeSecId").val();
			var searchValue = $("#searchValue").val();
			$("#secName").val(searchValue);
			grid.setparams({"orgid":id,"searchValue":searchValue});
			grid.refresh();
			
		});
		
		
		$(".searchValue").keypress(function(){
			$(".search_btn").click();
		})
	}
	


	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			inittree();
			initLxr();
			initgrid();
		},
		inittow:function(){
			initgrid();
			initLxr();
		}
	};
}();	

//点击人员姓名进行编辑
function clickfn(id){
	window.location.href="txl/html/txl_add.html?id="+id;
}
//点击部门名称进行编辑
function clickbmdhfn(id){
	window.location.href="txl/html/bmdh_edit.html?id="+id;
}

//加入收藏
var addscfn = function(id) {
	newbootbox.confirm({
     	title:"提示",
     	message: "是否确定收藏？？",
     	callback1:function(){
     		$ajax({
				url:addorupd,
				data:{id:id},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo("收藏成功！").done(function(){
							pageModule.inittow();
						});
					}else{
						newbootbox.alertInfo("收藏失败！");
					}
				}
			})
     	}
   });
}


function delscfn(id){
	$ajax({
		url:delSc,
		data:{id:id},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alertInfo("取消成功！").done(function(){
					pageModule.inittow();
				});
			}else{
				newbootbox.alertInfo("取消失败！");
			}
		}
	})
}

//删除
function delfn(id){
	 newbootbox.confirm({
     	title:"提示",
     	message: "是否要进行删除操作？",
     	callback1:function(){
     		$ajax({
				url:delone,
				data:{id:id},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo("删除成功！").done(function(){
							pageModule.inittow();
						});
					}else{
						newbootbox.alertInfo("删除失败！");
					}
				}
			})
     	}
	});
}


/*点击回车键搜索*/
function keySearch(){
	/*if(event.keyCode == 13){
		$(".search_btn").click();
	}*/
}

var show = function(obj){
	$("#"+obj).modal("show");
}
var hide = function(obj){
	$("#"+obj).modal("hide");
}

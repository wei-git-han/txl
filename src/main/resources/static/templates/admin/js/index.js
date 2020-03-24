var tablegrid2 = {"url":"txlUser/doublelistUser","dataType":"text"};//表格数据
var clientWidth = document.body.clientWidth; //浏览器宽度 alert(clientWidth) 1680
var scWidth = $(".lxrContent").parent().width();
var nolywidht = parseInt(scWidth/190);//盒子的个数
var thisWidth = scWidth/nolywidht-10; //每个盒子的宽度
var logininUserId;
var remarkUserId;
var remarkUserName;
$(window).resize(function(){
	window.location.href="index.html";
})
var currentPage=getUrlParam("currentPage")||1; //单表
var currentOrgid=getUrlParam("currentOrgid");
var cbox=true;
var show=true;
var grid=null;
var grid3=null;
var o = {};
o.pageSize = localStorage.getItem('pageSize1')||20;
o.pageSize2 = localStorage.getItem('pageSize2')||20;
var pageModule = function(){
	/*收藏*/
	var initLxr = function(){
		$ajax({
			url:lxr,
			success:function(data){
				$("#lxrContent").html("");
				$.each(data, function(i,obj) {
					var html2="";
					$.each(obj.children, function(j,obj2) {
						html2+= '<div class="center_model" style="width:228px;">'+
				 				'	<div  style="width:100%;height:100%;position:relative">'+	
								'	<div class="center_model_wrap">'+
								'		<div class="model_name">'+
								'			<div style="display:inline-block;">'+		
								'				<span onclick="clickfn(\''+obj2.userid+'\')"><a style="font-size:24px;">'+obj2.fullname+'</a></span><i class="fa fa-star starsc" onclick="delscfn(\''+obj2.userid+'\')"></i>'+
								'			</div>'+
								'		</div>'+
								'		<div class="model_zhiwu">'+
								'			<span title="'+obj2.dept+'">'+obj2.dept+'</span>'+
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
	}
	/*右侧部门电话数据*/
	var bmdhfn = function(){
		$ajax({
			url:bmdh,
			success:function(data){
				$("#bmdh").html("");
				$.each(data, function(i,obj) {
					$("#bmdh").append(
						'<div class="model">'+
						'	<div class="model_title"><a style="cursor:pointer" onclick="clickbmdhfn(\''+obj.id+'\')">'+obj.orgName+'</a><i class="fa fa-trash-o ljt" style="display:none;cursor:pointer;float: right;padding-right:5px;padding-top:3px;"  onclick="delfn(\''+obj.id+'\')"    style="float:right;cursor:pointer;"></i></div>'+
						'	<div class="model_title2"><img src="templates/admin/images/tel_03.png" class="telpic2" /><span>'+obj.orgTel+'</span></div>'+
						'	<div class="model_title2" style="width:100%;margin-top:3px;"><img src="templates/admin/images/adress_20.png" style="float:left;margin-top: 3px;" class="telpic2" /><span  style="display: block;width: 155px; text-overflow: ellipsis;overflow: hidden; white-space: nowrap;" title='+obj.orgAddress+'>'+obj.orgAddress+'</span></div>'+
						'</div>'
					);
				})
			}
		})
	}	
	//收藏夹的方法
	var initSc=function(){
		//点击收藏夹
		$(".left_sc").click(function(){
			$("#scDiv").show();
			$("#contentDiv").hide();
			$(this).addClass("activeSc");
			initLxr();//中间收藏列表
			initgrid3();
			$("#tree_2").jstree("deselect_all",false);
		});
		//点击组织机构
		$(".left_title").click(function(){
			$("#scDiv").hide();
			$("#contentDiv").show();
			$(this).siblings().removeClass("activeSc");
		});
	}
	//收藏夹的方法
	var initScLabel=function(){
		$("#lie").click(function(){   //列表  
			$(this).addClass("activeBtn").siblings().removeClass("activeBtn");
			$("#lieLbael").show();  
			$("#kaLbael").hide();
		   	initgrid3();
		});
		$("#ka").click(function(){   //卡片
			$(this).addClass("activeBtn").siblings().removeClass("activeBtn");
			$("#lieLbael").hide();
			$("#kaLbael").show();
			initLxr();//中间收藏列表
		});
	}
	var initmanager = function(){
		$ajax({
			url:authenurl,
			success:function(data){
//				if(true == true){
				if(true == data.manager){ 
		   		 	cbox = true;
		   		 	show = true;
		   		 	$("#daoru").show();
//        	   		$("#plszqx").show();
        	   		$("#add_bmdh").show();
        	   		$(".ljt").show();
			   	}else{
			   		cbox = false;
			   		show = false;
			   		$("#daoru").hide();
           	   		$("#add_bmdh").hide();
           	   		$(".ljt").hide();
			   	};
			   	initgrid();
				initgrid3();
			}
		});
	}
	//收藏卡表格
	var initgrid3 = function(){
		grid3 = $('#gridcont3').datagrid({
			url:lxr1.url,
			width: "100%",
			height: "100%",
			pagination:true,
			fitColumns: true,
			pageSize:o.pageSize2||20,
			queryParams:{},
			pageList: [20,40,60,80,100],
			striped:true,
			scrollbarSize:0,
			rownumbers:true,
			method:'GET',
			columns:[
				[
				{field:"fullname",title:"姓名",width:"15%",align:"center",halign:'center',sortable:false,formatter:function(value,rowdata,n){
					return rowdata.fullname;
					}
				},
				{field:"mobile",title:"手机号",width:"15%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					var str = "<div >"
						str+= rowdata.mobile+"</div>";
						if(rowdata.mobileTwo){
							str+="<div style='border-top: 1px dotted #ccc'>"+rowdata.mobileTwo+"</div>"
						}
						return str;
					}
				},
				{field:"telephone",title:"座机号",width:"15%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					var str = "<div >"
					str+= rowdata.telephone+"</div>";
					if(rowdata.telephoneTwo){
						str+="<div style='border-top: 1px dotted #ccc'>"+rowdata.telephoneTwo+"</div>"
					}
					return str;
				}},
				{field:"address",title:"房间号",width:"20%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					return rowdata.address;                                     
				}},
				{field:"dept",title:"部门",width:"25%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					return '<span title="'+rowdata.dept+'">'+rowdata.dept+'</span>';                                 
				}},
				{field:"caozuo",title:"收藏",width:"10%",align:"center",sortable:false,formatter:function(value,rowdata,n){
				 var caozuo = '<a class="sc" style="margin-right:10px;" title="取消收藏" href="javascript:delscfn(\''+rowdata.userid+'\')"><i class="fa fa-star"></i></a>';
				 return caozuo;
				}}
                ]
			],
			onLoadSuccess:function(){
				localStorage.setItem('pageSize2',$("#lieLbael .pagination-page-list").val())
			}
		});
	}
	//单表
	var initgrid = function(){
		grid = $('#gridcont').datagrid({
			url:tablegrid.url,
			width: "100%",
			height: "100%",
			pagination:true,
			fitColumns: true,
			pageSize:o.pageSize||20,
			queryParams:{"orgid":currentOrgid},
			pageList: [20,40,60,80,100],
			striped:true,
			scrollbarSize:0,
			rownumbers:true,
			pageNumber:(currentPage-0),
			method:'GET',
			columns:[
				[
//				{field:"ck",checkbox:"true"},
				{field:"fullname",title:"姓名",width:"10%",align:"center",halign:'center',sortable:false,formatter:function(value,rowdata,n){
					return '<a onclick="clickfn(\''+rowdata.userid+'\')">'+rowdata.fullname+'</a>';
					}
				},
				{field:"mobile",title:"手机号",width:"10%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					var str = "<div >"
					str+= rowdata.mobile+"</div>";
					if(rowdata.mobileTwo){
						str+="<div style='border-top: 1px dotted #ccc'>"+rowdata.mobileTwo+"</div>"
					}
					return str;
					}
				},
				{field:"telephone",title:"座机号",width:"10%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					var str = "<div >"
						str+= rowdata.telephone+"</div>";
					if(rowdata.telephoneTwo){
						str+="<div style='border-top: 1px dotted #ccc'>"+rowdata.telephoneTwo+"</div>"
					}
					return str;
				}},
				{field:"address",title:"房间号",width:"15%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					return rowdata.address;                                     
				}},
				{field:"dept",title:"部门",width:"20%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					return '<span title="'+rowdata.dept+'">'+rowdata.dept+'</span>';                                 
				}},
				{field:"sc",title:"操作",width:"5%",align:"center",sortable:false,formatter:function(value,rowdata,n){
				 var caozuo="";
             	 if(rowdata.isSc == 0 ){
             		 caozuo = '<a class="ysc" style="margin-right:10px;" title="收藏" href="javascript:addscfn(\''+rowdata.userid+'\')"><i class="fa fa-star"></i></a>';
             	 }else{
             		 caozuo = '<a class="sc" style="margin-right:10px;" title="取消收藏" href="javascript:delscfn(\''+rowdata.userid+'\')"><i class="fa fa-star"></i></a>';
             	 }
             	 if(show){
             		 if(rowdata.isShow=="1"||rowdata.isShow==""){
             			 caozuo += '<a class="sc" title="隐藏" href="javascript:addycfn(\''+rowdata.userid+'\')"><i class="fa fa-eye"></i></a>';
                   	 }else{
                   		caozuo += '<a class="ysc" title="取消隐藏" href="javascript:delycfn(\''+rowdata.userid+'\')"><i class="fa fa-eye"></i></a>';
                   	 }
             	 }
                	 return caozuo;  					
				}},
				{field:"remarks",title:"备注",width:"30%",align:"center",sortable:false,formatter:function(value,rowdata,n){
					if(rowdata.remarks == '' || rowdata.remarks == null){
            		  return '<div class="remarkContent" style="width: 100%; height: 18.5px; cursor:pointer;" title="点击进行编辑" data-toggle="modal" href="#editRemarks" onclick="editRemarks(\''+rowdata.userid+'\',\''+rowdata.fullname+'\',\''+rowdata.remarks+'\')">'+rowdata.remarks+'</div>';
            	  } else {
            		  return '<span class="remarkContent" style="cursor:pointer;" title="'+rowdata.remarks+'" data-toggle="modal" href="#editRemarks" onclick="editRemarks(\''+rowdata.userid+'\',\''+rowdata.fullname+'\',\''+rowdata.remarks+'\')">'+rowdata.remarks+'</span>'; 
            	  } 
				}}
                ]
			],
			onLoadSuccess:function(){
				currentPage = $(".pagination-num").val() - 0;
				currentOrgid=currentOrgid;
				localStorage.setItem('pageSize1',$("#danLbael .pagination-page-list").val())
			}
		});
	}
	var initMenuview = function(){
		$ajax({
			url:menuview,
			type:"GET",
			success:function(data){
				if(!data){return;}
				var lis='<div class="newpage13">设置功能权限,勾上表示隐藏。</div>';
				for(var i=0;i<data.rows.length;){
					lis+='<div class="newpage13"><div class="checkbox-list">';
					var obj=data.rows[i];
					lis+='<label class="checkbox-inline checkbox-inline1">';
					lis+='<input type="checkbox" id="" name="sqmenubox" value="'+obj.id+'"/>'+obj.menuName+'</label>';
					if(data.rows[i+1]){
						var obj=data.rows[i+1];
						lis+='<label class="checkbox-inline checkbox-inline1">';
						lis+='<input type="checkbox" id="" name="sqmenubox" value="'+obj.id+'"/>'+obj.menuName+'</label>';
					}
					if(data.rows[i+2]){
						var obj=data.rows[i+2];
						lis+='<label class="checkbox-inline checkbox-inline1">';
						lis+='<input type="checkbox" id="" name="sqmenubox" value="'+obj.id+'"/>'+obj.menuName+'</label>';
					}
					if(data.rows[i+3]){
						var obj=data.rows[i+3];
						lis+='<label class="checkbox-inline checkbox-inline1">';
						lis+='<input type="checkbox" id="" name="sqmenubox" value="'+obj.id+'"/>'+obj.menuName+'</label>';
					}
					lis+='</div></div>';
					i+=4;
				}
				lis+='<div class="newpage13"><button class="btn btn-primary btn-button1 pull-right" onclick=yesfn()>确定</button><button class="btn btn-primary btn-button1 pull-right" onclick=cancelqfn()>取消</button></div>';
				$('#menuview').html(lis);
			}
		});
		
	}
	/*全部联系人树加载*/
	var inittree = function(){
		/*
		 * 树的格式参照data文件里的txltree.json
		 * 如果当前节点下有子节点则children为true
		 * 点击+号给后台传当前节点的id
		 * 然后后台传给前端当前节点的其他子节点
		 * */
		$("#tree_2").jstree({
		    "plugins": ["wholerow", "types"],
		    "core": { 
		    	"themes" : {
			        "responsive": true
			    },
		    	"data": {
		    		"url":function(node){
		    				return "txlorgan/syncTree";
		    		},
		    		"data":function(node){
		    			return {"id":node.id};
		    		}
		    	}
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
						
		$("#tree_2").on("load_node.jstree", function(e,data) {
			$("#tree_2").jstree().open_node("root");
		});
		
		$("#tree_2").on("select_node.jstree", function(e,data) { 
			$("#scDiv").hide();
			$("#contentDiv").show();
			$(".left_sc").removeClass("activeSc");
		    var id = $("#" + data.selected).attr("id");
		    var searchValue = $("#searchValue").val();
		    currentOrgid = id;
		    $("#treeSecId").val(id);
			initgrid();
		});
	
		$("#tree_2").on("hover_node.jstree", function(e,data) {
			if(show){
				$(".jstree_caozuo").remove();
				var id = data.node.id;
				var isShow = data.node.original.isShow;
				if(id == "root"){
					return;
				};
				if(isShow == "0"){
					$("#"+id+"> a").append('<span class="jstree_caozuo" title="取消隐藏" style="margin-left:2px;color: #666;cursor: pointer;"><i class="fa fa-eye xsbtn"></i></span>');
				}else{
					$("#"+id+"> a").append('<span class="jstree_caozuo" title="隐藏" style="margin-left:2px;color: #4182D2;cursor: pointer;"><i class="fa fa-eye ycbtn"></i></span>');
				};
			};
			$(".ycbtn").click(function(){
				newbootbox.confirm({
			     	title:"提示",
			     	message: "是否确定隐藏？",
			     	callback1:function(){
			     		$ajax({
							url:hideorg,
							data:{id:id,isShow:'0'},
							success:function(data){
								if(data.result=="success"){
									newbootbox.alertInfo("隐藏成功！").done(function(){
										$("#tree_2").jstree().destroy();  //把树节点删除
										inittree();
										initgrid();
									});
								}else{
									newbootbox.alertInfo("隐藏失败！");
								}
							}
						})
			     	}
			   });
			});
			$(".xsbtn").click(function(){
				$ajax({
					url:hideorg,
					data:{id:id,isShow:'1'},
					success:function(data){
						if(data.result=="success"){
							newbootbox.alertInfo("取消隐藏成功！").done(function(){
								$("#tree_2").jstree().destroy();  //把树节点删除
								inittree();
								initgrid();
							});
						}else{
							newbootbox.alertInfo("取消隐藏失败！");
						}
					}
				})
			});
		});
		$("#tree_2").on("dehover_node.jstree", function(e,data) {
			$(".jstree_caozuo").remove();
		});
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
			if(left<=-(thisWidth+10)){
				left+=(thisWidth+10);
				$(".lxrContent").css("left",left+"px");
			}else if(left>-(thisWidth+10) || left<0){
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
				if(left1>=(thisWidth+10)){
					left-=(thisWidth+10);
				}else{
					left-=left1;
				}
				$(".lxrContent").css("left",left+"px");
				
			}else{
				return;
			}
		});
		//批量设置权限
		$("#plszqx").click(function(){
			var datas=grid.getcheckrow();
			var ids=[];
			if(datas.length>0){
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				$("#menuview").slideToggle(50);
			}else{
				newbootbox.alertInfo("请选择人员进行授权！");
			}
		});
		
		//部门电话新增
		$("#add_bmdh").click(function(){
			window.location.href="txl/html/bmdh_add.html";
		});
		
		//导入
		$("#daoru").click(function(){
			var gettime = $("#starttime").val();
			$ajax({
				url:importdata,
				data:{gettime:gettime},
				success:function(data){
					$("#starttime").val(data.starttime);
					newbootbox.alertInfo("同步数据成功！").done(function(){
						window.location.reload();
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
			$('#gridcont').datagrid('load',{"orgid":id,"searchValue":searchValue});
			$('#gridcont3').datagrid('load',{"searchValue":searchValue});
		});
		bindResize(document.getElementById("moveDiv"),document.getElementById("treeDiv"),document.getElementById("contentDiv"),document.getElementById("scDiv"));
		//拿到当前的用户
		$ajax({
			url: userInfourl,
			type: "GET",
			success: function(data) {
				logininUserId = data.CurrentUserId;
			}
		})
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			inittree();//左侧组织机构树
			initmanager();//列表
			bmdhfn();//右侧部门电话
			initMenuview();//设置功能权限
			initSc();  //收藏夹与组织机构切换的方法
			initScLabel(); //收藏中的列表和卡片控制
		},
		gridfresh:function(){
			initgrid();
		},
		initLxrfresh:function(){
			initLxr();
		},
		bmdhfnfresh:function(){
			bmdhfn();
		},
		gridfresh3:function(){
			initgrid3();
		}
	};
}();	

function yesfn(){
	var r = $('input[name="sqmenubox"]:checked');
	var rs = [];
	$.each(r, function(i) {
		rs.push(r[i].defaultValue);
	});
	var datas=grid.getcheckrow();
	var ids=[];
	if(datas.length>0){
		$(datas).each(function(i){
			ids[i]=this.userid;
		});
	}else{
		newbootbox.alertInfo("请选择人员进行授权！");
	}
	$ajax({
		url: yesurl,
		type: "GET",
		data: {"uids":ids.toString(),"menus": rs.toString()},
		success: function(data) {
			if(data.result == "success") {
				$("#menuview").slideUp(50);
				newbootbox.alertInfo('保存成功！').done(function(){
					grid.refresh();
				});
			}else{
				newbootbox.alertInfo("保存失败！");
			}
		}
	})
}
function cancelqfn(){
	$("#menuview").slideUp(50);
	//$.uniform.update($("input[type='checkbox']").empty());
}
//点击人员姓名进行编辑
function clickfn(id){
	window.location.href="txl/html/txl_add.html?id="+id+"&currentOrgid="+currentOrgid+"&currentPage="+currentPage;
}
//点击部门名称进行编辑
function clickbmdhfn(id){
	window.location.href="txl/html/bmdh_edit.html?id="+id;
}
//加入收藏
var addscfn = function(id) {
	newbootbox.confirm({
     	title:"提示",
     	message: "是否确定收藏？",
     	callback1:function(){
     		$ajax({
				url:addorupd,
				data:{id:id},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo("收藏成功！").done(function(){
							pageModule.initLxrfresh();
							pageModule.gridfresh();
							pageModule.gridfresh3();
							$(".search_btn").click();
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
					pageModule.initLxrfresh();
					pageModule.gridfresh3();
					$(".search_btn").click();
				});
			}else{
				newbootbox.alertInfo("取消失败！");
			}
		}
	})
}
//隐藏
var addycfn = function(id) {
	newbootbox.confirm({
     	title:"提示",
     	message: "是否确定隐藏？",
     	callback1:function(){
     		$ajax({
				url:isShowUrl,
				data:{id:id,isShow:'0'},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo("隐藏成功！").done(function(){
							pageModule.gridfresh();
						});
					}else{
						newbootbox.alertInfo("隐藏失败！");
					}
				}
			})
     	}
   });
}
function delycfn(id){
	$ajax({
		url:isShowUrl,
		data:{id:id,isShow:'1'},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alertInfo("取消隐藏成功！").done(function(){
					pageModule.gridfresh();
				});
			}else{
				newbootbox.alertInfo("取消隐藏失败！");
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
							pageModule.bmdhfnfresh();
						});
					}else{
						newbootbox.alertInfo("删除失败！");
					}
				}
			})
     	}
	});
}
var show = function(obj){
	$("#"+obj).modal("show");
}
var hide = function(obj){
	$("#"+obj).modal("hide");
}
function bindResize(el,el1,el2,el3){
	var els = el1.style;
	var els2 = el2.style;
	var els3 = el3.style;
	x=y=0;
	$(el).mousedown(function(e){
		x=e.clientX-el1.offsetWidth;
		if(el.setCapture){
			el.setCapture();
			el.onmousemove = function(ev){
				mouseMove(ev||event);
			};
			el.onmouseup = mouseUp;
		}else{
			$(document).bind("mousemove",mouseMove).bind("mouseup",mouseUp);
		}
		e.preventDefault();
	})
	function mouseMove(e){
		var w1= e.clientX-x;
		if(w1<=215){
			w1 = "215px";
		};
		if(w1>=250){
			w1 = "250px";
		};
		els.width = w1+"px";
		els2.paddingLeft = (w1)+"px";
		els3.paddingLeft = (w1)+"px";
	};
	function mouseUp(e){
		if(el.releaseCapture){
			el.releaseCapture();
			el.onmousemove = el.onmouseup = null ;
		}else{
			$(document).unbind("mousemove",mouseMove).unbind("mouseup",mouseUp);
		}
	}
}
function keyss(){
	$(".search_btn").click();
}
//编辑备注模态框
function editRemarks(id,name,remarks){
	remarkUserId = id;
	remarkUserName = name;
	$('#remarksText').val(remarks);
}
//保存备注
$('#saveRemarks').click(function(){
	$ajax({
		url:saveRemarks,
		data:{remarkedPersonId:remarkUserId,remarkContent:$('#remarksText').val(),remarkedPersonName:remarkUserName},
		success:function(data){
			$("#editRemarks").modal("hide");
			if(data.msg == "success"){
				newbootbox.alertInfo("编辑成功");
			} else {
				newbootbox.alertInfo("编辑出错");
			}
			pageModule.gridfresh();
		}
	})
})
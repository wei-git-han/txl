var clientWidth = document.body.clientWidth; //浏览器宽度 alert(clientWidth) 1680
var scWidth = $(".lxrContent").parent().width();
var nolywidht = parseInt(scWidth/190);//盒子的个数
var thisWidth = scWidth/nolywidht-10; //每个盒子的宽度

$(window).resize(function(){
	window.location.href="index.html";
})

var currentPage=getUrlParam("currentPage")||1;
var currentOrgid=getUrlParam("currentOrgid");
var cbox=true;
var show=true;
var grid=null;
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
						html2+= '<div class="center_model" style="width:'+thisWidth+'px;">'+
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
						'	<div class="model_title"><a style="cursor:pointer"   onclick="clickbmdhfn(\''+obj.id+'\')">'+obj.orgName+'</a><i class="fa fa-trash-o ljt" style="display:none;cursor:pointer;float: right;padding-right:5px;padding-top:3px;"  onclick="delfn(\''+obj.id+'\')"    style="float:right;cursor:pointer;"></i></div>'+
						'	<div class="model_title2"><img src="templates/admin/images/tel_03.png" class="telpic2" /><span>'+obj.orgTel+'</span></div>'+
						'	<div class="model_title2" style="width:100%;margin-top:3px;"><img src="templates/admin/images/adress_20.png" style="float:left;margin-top: 3px;" class="telpic2" /><span  style="display: block;width: 155px; text-overflow: ellipsis;overflow: hidden; white-space: nowrap;" title='+obj.orgAddress+'>'+obj.orgAddress+'</span></div>'+
						'</div>'
					);
				})
			}
		})
	}
	
	
	var initmanager = function(){
		$ajax({
			url:authenurl,
			success:function(data){
				if(true == data.manager){
		   		 	cbox = true;
		   		 	show = true;
		   		 	$("#daoru").show();
        	   		$("#plszqx").show();
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
			   	if(currentPage!=null && typeof(currentPage) !="undefined"){
			   		grid.loadtable2(currentPage);
			   	};
			}
		});
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
                 {display:"职务",name:"post",width:"17%",align:"center",paixu:false,render:function(rowdata){
                       return rowdata.post;                                      
                 }},
                 {display:"房间号",name:"address",width:"10%",align:"center",paixu:false,render:function(rowdata){
                     return rowdata.address;                                        
                  }},
                 {display:"部门",name:"dept",width:"21%",align:"left",paixu:false,render:function(rowdata){
                    return rowdata.dept;                                         
                 }},
                 {display:"操作",name:"caozuo",width:"10%",align:"center",paixu:false,render:function(rowdata){
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
                  }}
             ],
            width:"100%",
            checkbox:cbox,
            rownumberyon:true,
            paramobj:{"orgid":currentOrgid},
            overflowx:false,
            newpage:currentPage,
            pagesize: 12,
            loadafter:function(data){
            	currentPage = $("#gridcont_newpage").val();
            },
            url: tablegrid
       });
	}
	
	var initMenuview = function(){
		$ajax({
			url:menuview,
			type:"GET",
			success:function(data){
				if(!data){return;}
				var lis='<div class="newpage13">设置功能权限</div>';
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
		    		"url":"txlorgan/syncTree",
		    		"data":function(node){
		    			return {"id":node.id};
		    		}
		    	},
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
		    currentOrgid = id;
		    $("#treeSecId").val(id);
			grid.setparams({"orgid":id,"searchValue":searchValue});
			grid.loadtable();
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
					$("#"+id+"> a").append('<span class="jstree_caozuo" title="取消隐藏" style="margin-left:10px;color: #666;cursor: pointer;"><i class="fa fa-eye xsbtn"></i></span>');
				}else{
					$("#"+id+"> a").append('<span class="jstree_caozuo" title="隐藏" style="margin-left:10px;color: #4182D2;cursor: pointer;"><i class="fa fa-eye ycbtn"></i></span>');
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
										$(".search_btn").click();
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
								$(".search_btn").click();
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
			grid.setparams({"orgid":id,"searchValue":searchValue});
			grid.refresh();
		});
		
		$(".searchValue").keypress(function(){
			$(".search_btn").click();
		});
		bindResize(document.getElementById("moveDiv"),document.getElementById("treeDiv"),document.getElementById("contentDiv"));
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			inittree();//左侧组织机构树
			initLxr();//中间收藏列表
			initmanager();//列表
			bmdhfn();//右侧部门电话
			initMenuview();
		},
		gridfresh:function(){
			initgrid();
		},
		initLxrfresh:function(){
			initLxr();
		},
		bmdhfnfresh:function(){
			bmdhfn();
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
							//pageModule.gridfresh();
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
					//pageModule.gridfresh();
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
							$(".search_btn").click();
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
					//pageModule.gridfresh();
					$(".search_btn").click();
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
function bindResize(el,el1,el2){
	var els = el1.style;
	var els2 = el2.style;
	x=y=0;
	$(el).mousedown(function(e){
		x=e.clientX-el1.offsetWidth;
		//y=e.clientY-el.offsetHeight;
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
		//els.height = (e.clientY-y)+"px";
		els2.paddingLeft = (w1)+"px";
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
var pageModule = function(){
	var initrytree = function(){
		$ajax({
			url:rytree,
			success:function(data){
				$("#ry").jstree({
				     "plugins": ["wholerow", "types"],
				    "checkbox":{"keep_selected_style":false},
				    "core": {
				    "themes" : {
				        "responsive": false,
				        "icons":false
				    },    
				    "data": data,
				    }
				});
				
				$("#ry").on("select_node.jstree", function(e,data) { 	
					
					
				});
				
			}
		})
	}
	
	var initbmtree = function(){
		$ajax({
			url:bmtree,
			success:function(data){
				$("#bm").jstree({
				     "plugins": ["wholerow", "types"],
				    "checkbox":{"keep_selected_style":false},
				    "core": {
				    "themes" : {
				        "responsive": false,
				        "icons":false
				    },    
				    "data": data,
				    }
				});
				
				$("#bm").on("select_node.jstree", function(e,data) { 	
					
					
				});
				
			}
		})
	}
	
	
	
	//部门树
	var initOrgTree = function() {
		$("#Department").createSelecttree({
			url : bmtree,
			width : "170px",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#Department").val(data.node.text);
			}
		});
	}
	
	var initother = function(){
		//返回划过更换图标
		$(".imgsbig").hover(function(){
			$(this).attr("src","../images/back-hover.png");
		},function(){
			$(this).attr("src","../images/back-normal.png");
		});
		
		$(".add").hover(function(){
			$(this).attr("src","../images/add_h.png");
		},function(){
			$(this).attr("src","../images/add_n.png");
		});
		
		$(".daoru").hover(function(){
			$(this).attr("src","../images/daoru_h.png");
		},function(){
			$(this).attr("src","../images/daor_n.png");
		});
		
		$(".daochu").hover(function(){
			$(this).attr("src","../images/daochu_h.png");
		},function(){
			$(this).attr("src","../images/daochu_n.png");
		});
		
		
		$(".ry").click(function(){
			$(".ry").find(".imgs").removeAttr("src","../images/tab_hei.png");
			$(".ry").find(".imgs").attr("src","../images/tab_n.png");
			$(".bm").find(".imgs").removeAttr("src","../images/bm_n.png");
			$(".bm").find(".imgs").attr("src","../images/tab_heis.png");
		})
		
		$(".bm").click(function(){
			$(".bm").find(".imgs").removeAttr("src","../images/tab_heis.png");
			$(".bm").find(".imgs").attr("src","../images/bm_n.png");
			$(".ry").find(".imgs").removeAttr("src","../images/tab_n.png");
			$(".ry").find(".imgs").attr("src","../images/tab_hei.png");
		})
		
		$(".addphone").click(function(){
			$(".addpart").after('<div class="form-group">'+
				'<label class="col-xs-4 labels"></label>'+
				'<div class="col-xs-5">'+
				'	<input type="text" name="" id="" class="form-control" value="" />'+
				'</div>'+
			    '</div>');
		})

		
		$("#Department").click(function(){
			initOrgTree();
		})
		
		
		//表单验证
		$("#saveForm").validate({
	    	submitHandler: function() {
				var elementarry = ["username","sex","Department","positions","tel","phone","sproomzt"];
				var paramdata = getformdata(elementarry);
				$ajax({
					url:savetip,
					type: "GET",
					data:paramdata,
					success:function(data){
						if (data.result == "success") {
							 bootbox.dialog({
					            title: "提示",
					            message: "保存成功！",
					            buttons: {
					              success: {
					                label: "确定",
					                className: "btn-primary",
					                callback: function() {
					                }
					              }
					            }
					        }); 
						}else{
							newbootbox.alert("保存失败！")
						}
					}
				}) 
	    	}
		});
		
		
		//保存
		$("#save").click(function(){
			$("#saveForm").submit();
		})
		
	}

	
	return{
		//加载页面处理程序
		initControl:function(){
			initrytree();
			initbmtree();
			initother();
		}
	};
}();	
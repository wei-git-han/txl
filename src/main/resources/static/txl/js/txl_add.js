var id=getUrlParam("id");
var returndata = {"url":"/txluser/getUser","dataType":"text"};
var savetip ={"url":"/txluser/updateUser","dataType":"text"};
var pageModule = function(){
	
	var initdatafn = function(){
		$ajax({
			url:returndata,
			data:{id:id},
			success:function(data){
				setformdata(data.txlOrgtel);
				if(true == data.manager){
					$("#address").removeAttr("disabled");
					$("#save").show();
            	}else{
            		$("#address").attr("disabled",true);
            		$("#save").hide();
            	}
			}
		});
		
	}
	
	//部门树
	var initOrgTree = function() {
		$("#organName").createSelecttree({
			url : bmtree,
			width : "auto",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#organName").val(data.node.text);
				$("#organid").val(data.node.id);
			}
		});
	}
	
	var initother = function(){
		$("input[type=text]").attr("disabled","disabled");
		$("input[type=radio]").attr("disabled","disabled");
		$("#address").removeAttr("disabled");
		//返回划过更换图标
		$(".imgsbig").hover(function(){
			$(this).attr("src","../images/back-hover.png");
		},function(){
			$(this).attr("src","../images/back-normal.png");
		});
		
		$("#woman").click(function(){
			$(".headpic").attr("src","../../templates/admin/images/personn.png");
		})
		$("#man").click(function(){
			$(".headpic").attr("src","../../templates/admin/images/personman.png");
		})
		//返回上一层
		$("#return").click(function(){
			window.location.href="../../index.html";
		});
		$("#quxiao").click(function(){
			window.location.href="../../index.html";
		});
		
		//表单验证
		$("#saveForm").validate({
		    submitHandler: function() {
				var elementarry = ["userid","fullname","sex","organName","post","telephone","mobile","address"];
				var paramdata = getformdata(elementarry);
				$ajax({
					url:savetip,
					data:paramdata,
					type:'post',
					success:function(data){
						if (data.result == "success") {
							newbootbox.alertInfo("保存成功！").done(function(){
								window.location.href="../../index.html";
							});
						}else{
							newbootbox.alertInfo("保存失败！")
						}
					}
				})
		    }
		});
		
		
		$("#save").click(function(){
			$("#saveForm").submit();
		})
		
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initdatafn();
			initother();
			initOrgTree();
		}
	};
}();	
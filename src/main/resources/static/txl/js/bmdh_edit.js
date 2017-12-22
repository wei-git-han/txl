var id=getUrlParam("id");
var returndata = {"url":"/txlorgtel/info","dataType":"text"};
var savetip ={"url":"/txlorgtel/update","dataType":"text"};
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:returndata,
			data:{id:id},
			success:function(data){
				setformdata(data.txlOrgtel);
				if(true == data.manager){
					$("#orgAddress").removeAttr("disabled");
					$("#orgTel").removeAttr("disabled");
					$("#save").show();
            	}else{
            		$("#orgAddress").attr("disabled",true);
            		$("#orgTel").attr("disabled",true);
            		$("#save").hide();
            	}
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
				var elementarry = ["id","orgName","orgTel","orgAddress"];
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
		}
	};
}();	
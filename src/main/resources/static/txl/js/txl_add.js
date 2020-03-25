var id=getUrlParam("id");
var currentPage=getUrlParam("currentPage");
var currentOrgid=getUrlParam("currentOrgid");
var returndata = {"url":"/txluser/getUser","dataType":"text"};
var savetip ={"url":"/txluser/updateUser","dataType":"text"};
var pageModule = function(){
	
	var initdatafn = function(){
		$ajax({
			url:returndata,
			data:{id:id},
			success:function(data){
				$("#tt").html(data.txlOrgtel.fullname);
				setformdata(data.txlOrgtel);
				initTelShow(data.txlOrgtel.mobileTwo,data.txlOrgtel.telephoneTwo)
				if(data.ismyself == true){
					$("#post").removeAttr("disabled");
					$("#mobile").removeAttr("disabled");
					$("#telephone").removeAttr("disabled");
					$("#mobileTwo").removeAttr("disabled");
					$("#telephoneTwo").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#remarks").removeAttr("disabled");
					
				}else if(true == data.manager){
					$("#post").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#mobile").removeAttr("disabled");
					$("#telephone").removeAttr("disabled");
					$("#mobileTwo").removeAttr("disabled");
					$("#telephoneTwo").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#remarks").removeAttr("disabled");
					$("#save").show();
            	}else{
            		$("#address").attr("disabled",true);
//            		$("#mobile").removeAttr("disabled",true);
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
		if (currentOrgid == '' || currentOrgid == 'null' || currentOrgid == null) {
			currentOrgid = 'root';
		}
		$("#woman").click(function(){
			$(".headpic").attr("src","../../templates/admin/images/personn.png");
		})
		$("#man").click(function(){
			$(".headpic").attr("src","../../templates/admin/images/personman.png");
		})
		//返回上一层
		$("#return").click(function(){
			window.location.href="../../index.html?currentOrgid="+currentOrgid+"&currentPage="+currentPage;
		});
		$("#quxiao").click(function(){
			window.location.href="../../index.html?currentOrgid="+currentOrgid+"&currentPage="+currentPage;
		});
		
		//表单验证
		$("#saveForm").validate({
		    submitHandler: function() {
				var elementarry = ["userid","fullname","sex","organName","post","telephone","mobile","telephoneTwo","mobileTwo","address","remarks"];
				var paramdata = getformdata(elementarry);
				$ajax({
					url:savetip,
					data:paramdata,
					type:'post',
					success:function(data){
						if (data.result == "success") {
							newbootbox.alertInfo("保存成功！").done(function(){
								window.location.href="../../index.html?currentOrgid="+currentOrgid+"&currentPage="+currentPage;
								// window.location.href="../html/txl_add.html?id="+id+"&currentOrgid="+currentOrgid+"&currentPage="+currentPage;
							});
						}else{
							newbootbox.alertInfo("保存失败！")
						}
					}
				})
		    }
		});
		
		$('.add-mobile').click(function () {
			$('#otherMobile').show()
			$('#mobileTwo').show();
			$('.del-mobile').addClass('hover-show');
			$('.add-mobile').removeClass('hover-show');
		})
		$('.add-tel').click(function () {
			$('#otherTel').show()
			$('#telephoneTwo').show()
			$('.del-tel').addClass('hover-show');
			$('.add-tel').removeClass('hover-show');
		})
		$('.del-mobile').click(function () {
			$('#otherMobile').hide()
			$('#mobileTwo').hide();
			$('#mobileTwo').val('')
			$('.del-mobile').removeClass('hover-show');
			$('.add-mobile').addClass('hover-show');
		})
		$('.del-tel').click(function () {
			$('#otherTel').hide()
			$('#telephoneTwo').hide();
			$('#telephoneTwo').val('')
			$('.del-tel').removeClass('hover-show');
			$('.add-tel').addClass('hover-show');
		})
		$("#save").click(function(){
			$("#saveForm").submit();
		})
	}
	var initTelShow = function (mobile,tel){
		if(mobile){
			$('#otherMobile').show()
			$('#mobileTwo').show();
			$('.del-mobile').addClass('hover-show');
			$('.add-mobile').removeClass('hover-show');
		}else{
			$('#otherMobile').hide()
			$('#mobileTwo').hide();
			$('.del-mobile').removeClass('hover-show');
			$('.add-mobile').addClass('hover-show');
		}
		if(tel){
			$('#otherTel').show()
			$('#telephoneTwo').show()
			$('.del-tel').addClass('hover-show');
			$('.add-tel').removeClass('hover-show');
		}else{
			$('#otherTel').hide()
			$('#telephoneTwo').hide();
			$('.del-tel').removeClass('hover-show');
			$('.add-tel').addClass('hover-show');
		}
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			initdatafn();
			initOrgTree();
		}
	};
}();
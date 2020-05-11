var id=getUrlParam("id");
var currentPage=getUrlParam("currentPage");
var currentOrgid=getUrlParam("currentOrgid");
var returndata = {"url":"/txluser/getUser","dataType":"text"};
var savetip ={"url":"/txluser/updateUser","dataType":"text"};
var mobileNum = 1;//记录电话的当前个数
var teleNum = 1;
var mobverifyNum = 1;//标记每个电话输入框单独的id值，以便区分不同input框的校验
var telverifyNum = 1;
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
					$(".mobileLi").removeAttr("disabled");
					$(".telephoneLi").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#remarks").removeAttr("disabled");
					$("#dutyName").removeAttr("disabled");
					
				}else if(true == data.manager){
					$("#post").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#mobile").removeAttr("disabled");
					$("#telephone").removeAttr("disabled");
					$("#mobileTwo").removeAttr("disabled");
					$("#telephoneTwo").removeAttr("disabled");
					$(".mobileLi").removeAttr("disabled");
					$(".telephoneLi").removeAttr("disabled");
					$("#address").removeAttr("disabled");
					$("#dutyName").removeAttr("disabled");
					$("#remarks").removeAttr("disabled");
					$("#save").show();
            	}else{
            		$('.add-tel').removeClass('hover-show');
            		$('.add-mobile').removeClass('hover-show');
            		$('.phone-icon').removeClass('hover-show');
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
				var elementarry = ["userid","fullname","sex","organName","dutyName","post","telephone","mobile","telephoneTwo","mobileTwo","address","remarks"];
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
//			$('#otherMobile').show()
//			$('#mobileTwo').show();
//			$('.del-mobile').addClass('hover-show');
//			$('.add-mobile').removeClass('hover-show');
			var html=`<div class="form-group mobLi" style="">
				<label class="col-xs-4 labels"></label>
				<div class="col-xs-5">
					<input type="text" name="mobileTwo" id="mobverify`+mobverifyNum+`" class="form-control mobVal" value=""   tel="tel"  trim="trim"   />
					<i class="phone-icon hover-show" onclick="delmobile(this)"><img src="../images/del-tel.png" alt=""></i>
				</div>
			</div>`
			if(mobileNum<11){
				
				if(mobileNum==1){
					$("#addpart1").after(html)
				}else{
					$(".mobLi:last").after(html)
				}
				mobileNum+=1;
//				$('#del-mobile'+mobileNum).addClass('hover-show');
				if(mobileNum==10){
					$('.add-mobile').removeClass('hover-show');
				}else{
					
				}
			}else{
				
			}
			mobverifyNum+=1
		})
		$('.add-tel').click(function () {
//			$('#otherTel').show()
//			$('#telephoneTwo').show()
//			$('.del-tel').addClass('hover-show');
//			$('.add-tel').removeClass('hover-show');
			var html=`<div class="form-group telLi" style="">
								<label class="col-xs-4 labels"></label>
								<div class="col-xs-5">
									<input type="text" name="telephoneTwo" id="telverify`+telverifyNum+`" class="form-control telVal" value=""   tel="tel"  trim="trim" />
									<i class="phone-icon hover-show" onclick="deltel(this)"><img src="../images/del-tel.png" alt=""></i>
								</div>
							</div>`
			if(teleNum<11){
				
				if(teleNum==1){
					$("#addpart0").after(html)
				}else{
					$(".telLi:last").after(html)
				}
				teleNum+=1;
//				$('#del-tel'+teleNum).addClass('hover-show');
				if(teleNum==10){
					$('.add-tel').removeClass('hover-show');
				}else{
					
				}
				
			}else{
				
			}
			telverifyNum+=1
		})
		$('.del-mobile').click(function () {
//			$('#otherMobile').hide()
//			$('#mobileTwo').hide();
//			$('#mobileTwo').val('')
//			$('.del-mobile').removeClass('hover-show');
//			$('.add-mobile').addClass('hover-show');
		})
		$('.del-tel').click(function () {
//			$('#otherTel').hide()
//			$('#telephoneTwo').hide();
//			$('#telephoneTwo').val('')
//			$('.del-tel').removeClass('hover-show');
//			$('.add-tel').addClass('hover-show');
		})
		$("#save").click(function(){
			$("#telephoneTwo").val(dataHandle("telVal"));
			$("#mobileTwo").val(dataHandle("mobVal"));
			$("#saveForm").submit();
		})
	}
	var initTelShow = function (mobile,tel){
		var mobileArr = [];
		var mobileHtml = ``;
		var telArr = [];
		var telHtml = ``;
		if(mobile){
			mobileArr = mobile.split(",");
			$('.add-mobile').addClass('hover-show');
			mobileNum = mobileArr.length+1;
			if(mobileArr.length == 9){
				$('.add-mobile').removeClass('hover-show');
			}else{
				
			}	
			mobileArr.map(function(item,index){
				mobileHtml=`<div class="form-group mobLi" style="">
					<label class="col-xs-4 labels"></label>
					<div class="col-xs-5">
						<input type="text" name="mobileTwo" disabled id="mobverify`+index+`" class="mobileLi form-control mobVal" value="`+item+`"   tel="tel"  trim="trim"   />
						<i class="phone-icon hover-show" onclick="delmobile(this)"><img src="../images/del-tel.png" alt=""></i>
					</div>
				</div>`
				if(index == 0){
					$("#addpart1").after(mobileHtml)
				}else{
					$(".mobLi:last").after(mobileHtml)
				}
			})
			mobverifyNum = mobileArr.length+2
		}else{
			$('.add-mobile').addClass('hover-show');
		}
		
		if(tel){
			telArr = tel.split(",");
			$('.add-tel').addClass('hover-show');
			teleNum = telArr.length+1;
			if(telArr.length == 9){
				$('.add-tel').removeClass('hover-show');
			}else{
				
			}	
			telArr.map(function(item,index){
				telHtml=`<div class="form-group telLi" style="">
					<label class="col-xs-4 labels"></label>
					<div class="col-xs-5">
						<input type="text" name="telephoneTwo" disabled id="telverify`+index+`" class="telephoneLi form-control telVal" value="`+item+`"   tel="tel"  trim="trim" />
						<i class="phone-icon hover-show" onclick="deltel(this)"><img src="../images/del-tel.png" alt=""></i>
					</div>
				</div>`
				if(index == 0){
					$("#addpart0").after(telHtml)
				}else{
					$(".telLi:last").after(telHtml)
				}
			})
			telverifyNum = telArr.length+2;
		}else{
			$('.add-tel').addClass('hover-show');
		}
//		if(mobile){
//			$('#otherMobile').show()
//			$('#mobileTwo').show();
//			$('.del-mobile').addClass('hover-show');
//			$('.add-mobile').removeClass('hover-show');
//		}else{
//			$('#otherMobile').hide()
//			$('#mobileTwo').hide();
//			$('.del-mobile').removeClass('hover-show');
//			$('.add-mobile').addClass('hover-show');
//		}
//		if(tel){
//			$('#otherTel').show()
//			$('#telephoneTwo').show()
//			$('.del-tel').addClass('hover-show');
//			$('.add-tel').removeClass('hover-show');
//		}else{
//			$('#otherTel').hide()
//			$('#telephoneTwo').hide();
//			$('.del-tel').removeClass('hover-show');
//			$('.add-tel').addClass('hover-show');
//		}
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

function delmobile(item){
	mobileNum-=1;
	$(item).parent(".col-xs-5").parent(".form-group").remove();
	if(mobileNum < 10){
		$('.add-mobile').addClass('hover-show');
	}
}
function deltel(item){
	teleNum-=1;
	$(item).parent(".col-xs-5").parent(".form-group").remove();
	if(teleNum < 10){
		$('.add-tel').addClass('hover-show');
	}
}
function dataHandle(dom){
	var dataArr = []
	var domArr = Array.from($("."+dom))
	if(domArr.length>0){
		domArr.map(function(item,index){
			if($(item).val() != ""){
				dataArr.push($(item).val())
			}
			
		})
	}
	return dataArr.join(",")
}
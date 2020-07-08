var getUserAdminTypeUrl = {"url":"/app/txl/adminconfig/getAuthor","dataType":"text"};
//getUserAdminTypeUrl={"url":"../data/getAuthor.json","dataType":"text"};
var pageModule = function(){
	var initother = function(){
		$ajax({
			url: getUserAdminTypeUrl,
			type: "GET",
			success: function(data) {
				if(data=="3"){//即是部管理员又是局管理员,按照部管理员权限分配
					$('#departAdmin').show();
					$('#juAdmin').show();
					$('#iframe100').attr("src","../../bglysz/html/index.html");
				}else if (data=="0"||data=="1"){//超级管理员或部管理员
					$('#departAdmin').show();
					$('#juAdmin').show();
                    $('#iframe100').attr("src","../../bglysz/html/index.html");
				}else{ //局管理员
					$('#juAdmin').show();
					$('#departAdmin').hide();
                    $('#iframe100').attr("src","../../glysz/html/index.html");
                    $("#departAdmin").removeClass("active");
                    $('#juAdmin').addClass("active");
				}
			}
		});
		$(".newpage8").click(function(){
			$(".newpage8").removeClass("active");
			$(this).addClass("active");
		});
		$("#goback").click(function(){
		    window.location.href="../../../index.html";
		})
	}

	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	};
	
}();

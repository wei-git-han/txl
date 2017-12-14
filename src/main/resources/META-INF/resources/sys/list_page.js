	/* 名称:获取浏览器高度
	 * 描述:
	 * 		获取浏览器高度函数
	 * 参数:
	 * 		returnValue :　返回窗口高度值
	 */
	function getClientHeight(){
	    return document.body.clientHeight;
	}
	
	/* 名称:获取浏览器宽度
	 * 描述:
	 * 		获取浏览器宽度函数
	 * 参数:
	 * 		returnValue :　返回窗口宽度值
	 */
	function getClientWidth(){
	    return document.body.clientWidth;
	}
	
	//弹出窗口函数
	//说明：对应参数。
	//调用：openWin(url,width,height,title)
	function openWin(url,width,height,title){
		top.Dialog.open({URL:url,Width:width,Height:height,Title:title});
	}
	
	//弹出窗口函数
	//说明：对应参数。
	function openWinByFresh(url,width,height,title){
		var diag = new top.Dialog();
		diag.Title = title;
		diag.CancelEvent=function(){top.window.frmright.location=top.window.frmright.location.href;diag.close();};
		diag.URL = url;
		diag.Width=width;
		diag.Height=height;
		diag.show();
	}
	
	//弹出窗口函数并且可以给叉按钮刷新父页面事件
	//说明：对应参数。
	//调用：openWin(url,width,height,title)
	function openWinAndRefresh(url,width,height,title){
		var diag = new top.Dialog();
		diag.Title = title;
		try{
			diag.CancelEvent=function(){top.window.frmright.location=top.window.frmright.location.href;diag.close();};
			diag.URL = url;
			diag.Width=width;
			diag.Height=height;
			diag.show();
		}catch(o){
			
		}
	}

	/**
	 * 获取选中的checkbox的个数
	 * @param {Object} id
	 * @return {TypeName} 
	 */
	function getCheckedNum(elementName){
		var oArray = document.getElementsByName(elementName);
		var index=0;
		for(var i=0;i<oArray.length;i++){
			if(oArray[i].checked){
				index++;
			}
		}
		return index;
	}
	 /**
	  * 获取选择一个 checkbox的值
	  * 获取选中checkbox的值
	  * @param {Object} id
	  * @return {TypeName} 
	  */
	function getCheckedValue(elementName){
		var oArray = document.getElementsByName(elementName);
		var retValue="";
		for(var i=0;i<oArray.length;i++){
			if(oArray[i].checked){
				retValue=oArray[i].value;
				break;
			}
		}
		return retValue;
	}
	  
	  /**
	   *获取选择对象数组 
	   */
	  function getCheckedObjects(elementName){
		   var data = new Array();
		    var oArray = document.getElementsByName(elementName);
			for(var i=0;i<oArray.length;i++){
				if(oArray[i].checked){
					data.push(oArray[i]);
				}
			}
			return data;
	  }
	  
	  /**
	   * 获取选中的checkbox的值 逗号分隔
	   * @param {Object} elementName
	   * @return {TypeName} 
	   */
	  function getCheckedValues(elementName){
		  	var data = new Array();
		    var oArray = document.getElementsByName(elementName);
			for(var i=0;i<oArray.length;i++){
				if(oArray[i].checked){
					data.push(oArray[i].value);
				}
			}
			return data.join(",");
	  }
	  
	  //HuangJun关于行列锁定的JS
	  //获取浏览器高度和宽度以适应浏览器显示的table大小
		var winWidth = 0;//浏览器窗体内部宽度宽度初始化
		var winHeight = 0;//浏览器窗体内部高度宽度初始化
		//获取尺寸
		function findDimensions(){
			//获取窗口宽度
			if(window.innerWidth){
				winWidth = window.innerWidth;
			}
			else if((document.body)&&(document.body.clientWidth)){
				winWidth = document.body.clientWidth;
			}
			//获取窗口高度
			if(window.innerHeight){
				winHeight = window.innerHeight;
			}
			else if((document.body)&&(document.body.clientHeight)){
				winHeight = document.body.clientHeight;
			}
			//通过深入Document内部对body进行检测,获取窗口大小
			if(document.documentElement&&document.documentElement.clientWidth&&document.documentElement.clientHeight){
				winWidth = document.documentElement.clientWidth;
				winHeight = document.documentElement.clientHeight;
			}
		}
		
		var timer;
		window.onresize = function(){
			if(timer)
				clearTimeout(timer);
			if($("#scrollContent").position()!=null&&$("#scrollContent").position()!=''){
				timer = setTimeout(function(){
					var oldWidth = winWidth-$("#scrollContent").position().left;
					findDimensions();
					fixTable("MainTable",0,winWidth-15,winHeight-25,oldWidth);
				},1)
			}
		}
		
		//锁定表头行列
		function fixTable(TableID,FixColumnNumber,width,height,oldWidth){
			//创建四个table的框架
			if($("#"+TableID).length==1&&$("#scrollContent").length==1){
				//页面加载后table的宽度
				var firstWidth = $("#"+TableID).width();
				$("#scrollContent").addClass("border_gray");
				$("#scrollContent").css("overflow-x","hidden");
				$("#scrollContent").css("overflow-y","hidden");

				if($("#"+TableID+"_tableLayout").length!=0){
					$("#"+TableID+"_tableLayout").before($("#"+TableID));
					$("#"+TableID+"_tableLayout").empty();
					//如果本来在table里面设置的宽度大于本身的宽度，则一直保持这个宽度，保证有横向滚动条
					if(firstWidth>oldWidth){
						$("#"+TableID).css("width",firstWidth);
					}
					//如果本来在table里面设置的宽度小于于本身的宽度，则一直保持这个宽度，保证横向滚动条无法滚动
					else{
						$("#"+TableID).css("width",width);
					}
				}
				else{
					//如果一开始加载页面后，在table里面设置的宽度大于本身的宽度，保证有横向滚动条
					if(firstWidth>width-$("#scrollContent").position().left){
						$("#"+TableID).after("<div id='"+TableID+"_tableLayout' style='overflow:hidden;height:"+height+"px;width:"+firstWidth+"px;'></div>");
						$("#"+TableID).css("width",firstWidth);
					}
					//如果一开始加载页面后，在table里面设置的宽度小于本身的宽度，保证横向滚动条无法滚动
					else{
						$("#"+TableID).after("<div id='"+TableID+"_tableLayout' style='overflow:hidden;height:"+height+"px;width:"+width+"px;'></div>");
						$("#"+TableID).css("width",width);
					}
				}
				$('<div id="'+TableID+'_tableFix"></div>'
					+'<div id="'+TableID+'_tableHead"></div>'
					+'<div id="'+TableID+'_tableColumn"></div>'
					+'<div id="'+TableID+'_tableData"></div>').appendTo("#"+TableID+"_tableLayout");
				
				var tableFix = $("#"+TableID+"_tableFix");
				var tableHead = $("#"+TableID+"_tableHead");
				var tableColumn = $("#"+TableID+"_tableColumn");
				var tableData = $("#"+TableID+"_tableData");
				
				var oldTable = $("#"+TableID);
				var tableFixClone = oldTable.clone(false);
				tableFixClone.attr("id",TableID+"_tableFixClone");
				tableFix.append(tableFixClone);
				var tableHeadClone = oldTable.clone(false);
				tableHeadClone.attr("id",TableID+"_tableHeadClone");
				tableHead.append(tableHeadClone);
				var tableColumnClone = oldTable.clone(false);
				tableColumnClone.attr("id",TableID+"_tableColumnClone");
				tableColumn.append(tableColumnClone);
				
				tableData.append(oldTable);
				
				//需要15的表格样式以及一些方法
				setTableStyle();
				
				$("#"+TableID+"_tableLayout table").each(function(){
					$(this).css("margin","0");
				});
				
				//计算tableFix,tableHead的高度
				var HeadHeight = $("#"+TableID+"_tableHead thead").height();
				HeadHeight+=2;
				tableHead.css("height",HeadHeight);
				tableFix.css("height",HeadHeight);
				//计算tableFix,tableColumn的宽度
				var ColumnsWidth = 0;
				var ColumnsNumber = 0;
				if(document.getElementById(TableID).attributes["columns"]){
					$("#"+TableID+"_tableColumn tr:last td:lt("+document.getElementById(TableID).attributes["columns"].nodeValue+")").each(function(){
						ColumnsWidth+=$(this).outerWidth(true);
						ColumnsNumber++;
					});	
					//增加hover的效果
					for(var i=1;i<=document.getElementById(TableID).attributes["columns"].nodeValue;i++){
						var spanLength = $("#"+TableID+"_tableColumn tr:last td:nth-child("+i+")").find("span").length;
						if(spanLength==1){
							for(var j=0;j<$("#"+TableID+"_tableColumn tr").length;j++){
								var hoverTD = $("#"+TableID+"_tableColumn tr:nth-child("+j+") td:nth-child("+i+")");
								hoverTD.find("span").attr("title",hoverTD.children().first().html());
							}
						}
					}
				}
				else{
					$("#"+TableID+"_tableColumn tr:last td:lt("+FixColumnNumber+")").each(function(){
						ColumnsWidth+=$(this).outerWidth(true);
						ColumnsNumber++;
					});
				}
				ColumnsWidth+=2;
				
				tableColumn.css("width",ColumnsWidth);
				tableFix.css("width",ColumnsWidth);
				
				//需要15的弹出样式以及一些方法
				enableTooltips();
				
				//为tableHead和tableColumn添加联动的滚动条事件
				tableData.scroll(function(){
					tableHead.scrollLeft(tableData.scrollLeft());
					tableColumn.scrollTop(tableData.scrollTop());
				});
				//
				var topLeft = $("#scrollContent").position().left;
				tableHead.css("width",width-topLeft);
				tableData.css("width",width-topLeft+17);
				
				//取底边分页控件的top高度
				var topLong;
				if($("#pageDiv").length==1){
					topLong = $("#pageDiv").position().top;
				}
				else
					topLong = height;
				//去整个最外层(scrollContent)div的top高度
				var topShort = $("#scrollContent").position().top;

				//表格高度大于剩余部分，则开启滚动条
				if($("#"+TableID).height()>topLong-topShort)
					tableData.css({"overflow-y":"scroll"});
				else
					tableData.css({"overflow-y":"hidden"});
				//表格宽度大于剩余部分，则开启滚动条
				if($("#"+TableID).width()>width-topLeft+17-10){
					tableFix.css({"overflow":"hidden","postion":"relative","z-index":"50","background-color":"White"});
					tableHead.css({"overflow":"hidden","width":width-topLeft-10,"postion":"relative","z-index":"45","background-color":"White"});
					tableColumn.css({"overflow":"hidden","height":height-17,"postion":"relative","z-index":"40","background-color":"White"});
					tableData.css({"overflow-x":"scroll","width":width-topLeft+17-10,"height":height,"postion":"relative","z-index":"35"});
				}
				else{
					tableFix.css({"overflow":"hidden","postion":"relative","z-index":"50","background-color":"White"});
					tableHead.css({"overflow":"hidden","width":width-topLeft-10,"postion":"relative","z-index":"45","background-color":"White"});
					tableColumn.css({"overflow":"hidden","height":height-17,"postion":"relative","z-index":"40","background-color":"White"});
					tableData.css({"overflow-x":"hidden","width":width-topLeft+17-10,"height":height,"postion":"relative","z-index":"35"});
				}
				//为较小的table修正样式
				//if($("#"+TableID+"_tableHead").width()>$("#"+TableID+"_tableFix table").width()){
				//	$("#"+TableID+"_tableHead").css("width",$("#"+TableID+"_tableFix table").width());
				//	$("#"+TableID+"_tableData").css("width",$("#"+TableID+"_tableFix table").width()+17);
				//}
				//if($("#"+TableID+"_tableColumn").height()>$("#"+TableID+"_tableColumn table").height()){
				
				tableColumn.css("height",topLong-topShort-25);
				tableData.css("height",topLong-topShort-25+17);
					
					//$("#"+TableID+"_tableColumn").css("height",$("#"+TableID+"_tableColumn table").height());
					//$("#"+TableID+"_tableData").css("height",$("#"+TableID+"_tableColumn table").height()+17);
				//}
				//为整体添加样式，定位
				tableFix.offset($("#"+TableID+"_tableLayout").offset());
				tableHead.offset($("#"+TableID+"_tableLayout").offset());
				tableColumn.offset($("#"+TableID+"_tableLayout").offset());
				tableData.offset($("#"+TableID+"_tableLayout").offset());
			}
		}
		
		//由于从一个table拆分成四个table,所以要重新设置下页面排序的th中span排序用的class
		function sortTableAgain(sort,sortClassName){
			var MainTable_tableFixClone = $("#MainTable_tableFixClone");
			var MainTable_tableHeadClone = $("#MainTable_tableHeadClone");
			var MainTable_tableColumnClone = $("#MainTable_tableFixClone");
			var MainTable = $("#MainTable_tableFixClone");
			MainTable_tableFixClone.find("thead").find("tr").find("th").find("span").each(function(i){
				var sortSpan = $(MainTable_tableFixClone.find("thead").find("tr").find("th").find("span").get(i));
				if(sort==sortSpan.attr("id")){
					sortSpan.attr("class",sortClassName);
					$(MainTable_tableHeadClone.find("thead").find("tr").find("th").find("span").get(i)).attr("class",sortClassName);
					$(MainTable_tableColumnClone.find("thead").find("tr").find("th").find("span").get(i)).attr("class",sortClassName);
					$(MainTable.find("thead").find("tr").find("th").find("span").get(i)).attr("class",sortClassName);
					//终止循环
					return false;
				}
			});
		}
		
		$(function(){
			findDimensions();//调用函数,获取数值
			fixTable("MainTable",0,winWidth-17,winHeight-25);
		})

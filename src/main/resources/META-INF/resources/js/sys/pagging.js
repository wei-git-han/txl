//根据分页情况,提交表单
function goToPage(formid, start, limit){
	//若没有传入要提交的formid,则分页控件默认会放到id为"paggingForm"的form中
	if (!formid) {
		formid = "paggingForm";
	}
	var targetForm = document.getElementById(formid);
	if(targetForm.action.indexOf("?")!=-1) {
		targetForm.action += "&";
	} else {
		targetForm.action += "?";
	}
	targetForm.action += "start=" + start;
	targetForm.action += "&limit=" + limit;
	targetForm.action += "&pageSizeParam=" + limit;
	//alert(targetForm.action);
	targetForm.submit();
}

//根据当前页数跳转
function goToPageByIndex(formid, pageIndex, limit, totalPage){
	//若pageIndex不是正整数,则提示后直接返回
	if(!isInt(pageIndex)) {
		alert("请输入正确的页码!");
		return;
	}
	//根据传入的当前页数,计算开始的行数
	var start = (pageIndex - 1) * limit;
	//计算出最后一页的开始行数
	var lastStart = (totalPage - 1) * limit;
	//若当期开始行数已经大于最后一页的开始行数,则将其重置为最后一页的开始行数
	if (start > lastStart) {
		start = lastStart;
	}
	//传入计算出的开始的行数进行分页表单提交
	goToPage(formid,start,limit);
}

//判断是否为正整数
function isInt(validatedValue) {
	//判断正整数的正则表达式
	var regEx = /^[0-9]*[1-9][0-9]*$/;
	//判断是否满足指定的正则表达式
	return validatedValue.match(regEx);
}

	/**
	 * 表格可以排序 调用的脚步
	 * 根据分页情况,提交表单
	 * @param {Object} formid
	 * @param {Object} start
	 * @param {Object} limit
	 * @param {Object} urlParams 表格排序的参数
	 */
	function goToPage_(formid, start, limit,urlParams){
		//若没有传入要提交的formid,则分页控件默认会放到id为"paggingForm"的form中
		if (!formid) {
			formid = "paggingForm";
		}
		var targetForm = document.getElementById(formid);
		if(targetForm.action.indexOf("?")!=-1) {
			targetForm.action += "&";
		} else {
			targetForm.action += "?";
		}
		targetForm.action += "start=" + start;
		targetForm.action += "&limit=" + limit;
		targetForm.action += "&pageSizeParam=" + limit;
		targetForm.action+=urlParams;
		
		//alert(targetForm.action);
		targetForm.submit();
	}

	/**
	 * 根据当前页数跳转
	 * @param {Object} formid
	 * @param {Object} pageIndex
	 * @param {Object} limit
	 * @param {Object} totalPage
	 * @param {Object} urlParams
	 * @return {TypeName} 
	 */
	function goToPageByIndex_(formid, pageIndex, limit, totalPage,urlParams){
	
		//总 页数为零直接返回false
		if(totalPage==0){
			return false;
		}
		
		//若pageIndex不是正整数,则提示后直接返回
		if(!isInt(pageIndex)) {
			alert("请输入正确的页码!");
			return false;
		}
		//根据传入的当前页数,计算开始的行数
		var start = (pageIndex - 1) * limit;
		//计算出最后一页的开始行数
		var lastStart = (totalPage - 1) * limit;
		//若当期开始行数已经大于最后一页的开始行数,则将其重置为最后一页的开始行数
		if (start > lastStart) {
			start = lastStart;
		}
		
		//传入计算出的开始的行数进行分页表单提交
		goToPage_(formid,start, limit,urlParams);
	}
	
	
	function sortTableParam(obj,limit,formid,pageIndex,totalPages){
			//var limit ="${pagingInfo.pageSize}";
			//var formid="queryForm";
			//var pageIndex=document.getElementById('paggingText').value;
			//var totalPages="${pagingInfo.totalPages}";
			
			//var className =  $("#"+obj.id).attr("class");
			var className =  obj.className;
			if(className=='sort_off'){
				className="sort_down";
			}
			if(className.indexOf('sort_up')!=-1){
				className='sort_down';
			}else{
				className='sort_up';
			}
			
			
			var params ="&sort="+obj.id+"&sortClassName="+className;
			
			//alert(params);
			if($("#paggingUrlParam")){
				$("#paggingUrlParam").val(params);
			}
			
			goToPageByIndex_(formid, pageIndex, limit, totalPages,params);
		}
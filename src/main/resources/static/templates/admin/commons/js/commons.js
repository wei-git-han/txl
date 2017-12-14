var css={
		filter_html:function(html){
			/*1、本方法的功能：去除HTML标签、空格（&nbsp;）、换行（\n）*/
			var regex=/<[^>]+>/g;
			var txt=html.replace(regex,'');
			txt=txt.replace(/&nbsp;/g,'');
			txt=txt.replace(/\n/g,'');
			return txt;
		},
		limit_rich_text:function(){
			/*1、本方法的功能：限制富文本框的最大字符长度（去除掉：HTML标签、空格（&nbsp;）、换行（\n））*/
			var _obj=css;
			$('textarea.limit').each(function(){
				var $this=$(this);
				var id=$this.attr('id');
				var editor=CKEDITOR.instances[id];
				editor.on('change',function(event){
					var data_html=editor.getData();
					var txt=_obj.filter_html(data_html);
					var len=txt.length;
					$this.val(txt).blur();
				});
				
				var cancel=setInterval(function(){
					var $cke=$('#cke_'+id);
					if($cke.size()>0){
						clearInterval(cancel);
						$('#cke_'+id).after($this);
					}
				},300);
			});
		},
		get_checkbox_val:function(name,split){
			/*获取指定name的已被选择的复选框的所有值*/
			var vals='';
			split=split||',';
			$('input[name='+name+']:checked').each(function(){
				var $this=$(this);
				vals+=split+$this.val();
			});
			return vals.substring(split.length);
		},
		get_query_val:function(name){
			/*1、获取当前url中，指定参数的值*/
			var reg=new RegExp('(^|&)'+name+'=([^&]*)(&|$)');//构建一个含有目标参数的正则表达式对象
			var r=window.location.search.substr(1).match(reg);//匹配目标参数
			if(!!r){
				return unescape(r[2]);
			}else{
				return '';
			}
			
		},
		cut:function(selector){
			val=$(selector).val();
			val=val||'';
			$(selector).val(val.substring(0,10));
		}
};

jQuery(document).ready(function() {    
    Metronic.init(); // init metronic core componets
    Layout.init(); // init layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    //initcomonother();
    initinclude();
    
    var pagedate = new Date();
	var month = pagedate.getMonth()+1;
	if(month<10){
		month = "0"+month;
	}
	var day = pagedate.getDate();
	if(day<10){
		day = "0"+day;
	}
	
	var hour = pagedate.getHours();
	var minute = pagedate.getMinutes();
	
	var year = pagedate.getFullYear();
	$(".datee").val(year+"-"+month+"-"+day);
	$(".nowdate").val(year+"-"+month+"-"+day+" "+hour+":"+minute);
	
});    
    
function setformdata(data){
	for(key in data){
		if($("[name="+key+"]").attr("type")=="checkbox"){
			var checkbox = document.getElementsByName(key);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].value==data[key]){
					checkbox[j].checked=true;
					$(checkbox[j]).parent().addClass("checked");
				}
			};
		}else if($("[name="+key+"]").attr("type")=="radio"){
			var radio = document.getElementsByName(key);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].value==data[key]){
					radio[j].checked=true;
					$(radio[j]).parent().addClass("checked");
				}else{
					radio[j].checked=false;
					$(radio[j]).parent().removeClass("checked");
				}
			};
		}else{
			$("#"+key).val(data[key]);
			//alert(data[key])
		}
	}
	
}
    
/*	var elementarry = ["username","email","phone","password1","password2","ifduty","role"];
	返回表单值{username:"username",...}
	var paramdata = getformdata(elementarry);
*/

function getformdata(arry){
	var paramdata = {};
	$.each(arry,function(i){
		if($("[name="+arry[i]+"]").attr("type")=="checkbox"){
			var valuearry = [];
			var checkboxvalue = "";
			var checkbox = document.getElementsByName(arry[i]);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].checked==true){
					valuearry.push(checkbox[j].value);
				}
			};
			checkboxvalue = valuearry.join(",");
			paramdata[arry[i]] = checkboxvalue;
		}else if($("[name="+arry[i]+"]").attr("type")=="radio"){
			var radiovalue = "";
			var radio = document.getElementsByName(arry[i]);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].checked==true){
					radiovalue = radio[j].value;
				}
			};
			paramdata[arry[i]] = radiovalue;
		}else{
			paramdata[arry[i]] = $("#"+arry[i]).val();
		}
	})
	return paramdata;
}


/*
 传入一个表单name数组，清空表单数据（重置按钮使用）-------这个方法可以用在列表的重置按钮上，传入对应的表单元素的name值即可
 * */

function removeInputData(arry){
	$.each(arry,function(i){
		$("[name="+arry[i]+"]").val("");
	})
}

/*	var jsonarry =  [{
						text:"是否转维修",
						value:"01"
					},{
						text:"已转",
						value:"02"
					},{
						text:"未转",
						value:"03"
					}]
	用来对select进行初始化
	initselect("selectelement",jsonarry);
	*/
function initselect(id,arry){
	var html = "";
	$.each(arry,function(i){
		html+='<option value='+arry[i].value+'>'+arry[i].text+'</option>';
	});
	$("#"+id).append(html);
}
	
function $ajax(obj){
	var urlobj = obj.url;
	if(urlobj==null||typeof(urlobj)=="undefined"){
		return;
	}
	var url = urlobj.url;
	var async = obj.async;
	var dataType = urlobj.dataType;
	var data = obj.data;
	var success = obj.success;
	var error = obj.error;
	var type = obj.type;
	if(url==null||typeof(url)=="undefined"){
		return
	}
	if(async==null||typeof(async)=="undefined"){
		async = true;
	}
	if(dataType==null||typeof(dataType)=="undefined"){
		dataType = 'json';
	}
	if(data==null||typeof(data)=="undefined"){
		data = {};
	}
	if(success==null||typeof(success)=="undefined"){
		success = function(data){
			//alert();
		}
	}
	if(error==null||typeof(error)=="undefined"){
		error = function(msg) {
			//alert("系统故障!");
		}
	}
	if(type==null||typeof(type)=="undefined"){
		type = "GET";
	}else{
		if(dataType=='text'){
			type = "GET";
		}
	}
	$.ajax({
		url : url,
		dataType : dataType,
		type:type,
		async: async,
		data:data,
		success:function(data){
			if(dataType=="text"){
				data = eval("("+data+")");
			}
			return success(data);
		},
		error : error
	});
}
	
function getUrlParam(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r=window.location.search.substr(1).match(reg);
	if(r!=null)return unescape(r[2]);
	return null;
}

/*升级版，可以带中文自动解码*/
function getUrlParam2(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r=window.location.search.substr(1).match(reg);
	//if(r!=null)return unescape(r[2]);
	if(r!=null)return decodeURI(r[2]);
	return null;
}


	
function initinclude(){
	var include = $("[include]");
	if(include!=null&&typeof(include)!="undefined"){
		include.each(function(i){
			var obj = $(include[i]);
			var url = obj.attr("include");
			$ajax({
				url:{"url":url,"dataType":"html"},
				async:false,
				success:function(html){
					obj.append(html);
				}
			})
		})
	}
}

//用来日期格式化
Date.prototype.format =function(format) {
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

jQuery.fn.extend({
	createSelecttree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createSelecttree(obj);
		return gridobj;
	}
});

jQuery.fn.extend({
	createcheckboxtree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createcheckboxtree(obj);
		return gridobj;
	}
});


function createSelecttree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(width==null||typeof(width)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1" style="display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			$("."+obj.target+"tree1").slideToggle(50);
			return false;
		})
/*		$("body").click(function(){
			$("."+obj.target+"tree1").slideUp(50)
		})*/
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
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
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					//liuhq:当同一页面中有两个或多个相同树时赋值有重复问题，
					//$("#"+obj.target).val($("#"+id+">a").text());
					$("#"+obj.target).val($("#"+obj.target+"tree2").find("#"+id+">a").eq(0).text());
				    obj.selectnode(e,data);
				});
				
			}
		})
		
	}
	create();
}




function createcheckboxtree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(width==null||typeof(width)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1" style="display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			$("."+obj.target+"tree1").slideToggle(50);
			return false;
		})
/*		$("body").click(function(){
			$("."+obj.target+"tree1").slideUp(50)
		})*/
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types","checkbox"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
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
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_checked",true);
			
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						treessid.push(obj.id)
						treessname.push(obj.text)
					});
					//liuhq:当同一页面中有两个或多个相同树时赋值有重复问题，
					//$("#"+obj.target).val($("#"+id+">a").text());
					//$("#"+obj.target).val($("#"+obj.target+"tree2").find("#"+id+">a").eq(0).text());
					$("#bm").val(treessname);
					$("#bmId").val(treessid);
				    //obj.selectnode(e,data);
				});
				
			}
		})
		
	}
	create();
}




/**
 * 判断OS，如果是Window系统并且是IE浏览器，则需要初始化；如果是Linux系统并且是FireFox浏览器，则需要初始化；
 */
function getWebEquipmentOS(){
	// 判断OS
	var sUserAgent = navigator.userAgent;
	var isWin = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
	var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
	var isUnix = (navigator.platform == 'X11') && !isWin;
	// 判断浏览器
	var explorere = navigator.userAgent.toLowerCase();
	var isFireFox = (explorere.indexOf("firefox") > 0);
	var isIE = (explorere.indexOf("msie") > 0);
	if ((isLinux || isUnix) && isFireFox) {
		return 'firefox';
	} else if (isWin && isIE) {
		return 'ie';
	} else {
		return 'no_init';
	}
	
}






var path = "/gwgl/";
var newbootbox = {
	/*封装的新的alert弹出框*/
	alert:function(text){
        bootbox.dialog(
	        {
	            message: text,
	            title: "提示",
	            buttons: {
		            success: {
		                label: "确定",
		                className: "btn-primary",
		                callback: function() {
		                }
		            }
	            }
	        }
        );
	},
	confirm:function(obj){  //有两个回调函数，确认删除？
		bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
	        buttons: {
	          success: {
	            label: "确定",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	alertdialog:function(obj){//有一个回调函数
		bootbox.dialog({
			title: obj.title,
		    message: obj.message,
            buttons: {
              success: {
                label: "确定",
                className: "btn-primary",
                callback: function() {
                	obj.callback1();	
                }
              }
            }
        });
	},
	sure:function(hrefstr,modalId){
		$("#"+modalId).modal("hide");
		//alert(this.bootboxfg1)
		if(hrefstr != ""){
			/*var topWindow = window.top;
			topWindow.location.href=hrefstr;*/
			window.parent.location.href=hrefstr;
		}
		
	},
	
	quxiao:function(modalId){
		//alert(modalId)
		$("#"+modalId).modal("hide");
	},
	
	newclose:function(modalId){
		//alert(modalId)
		$("#"+modalId).modal("hide");
	}
}



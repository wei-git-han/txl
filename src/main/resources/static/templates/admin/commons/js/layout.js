jQuery(document).ready(function() {
	var nyopen = true;
	$(".menu_button").click(function(){
		if(nyopen==true){
			$(".level1 li").removeClass("open");
			$(".layout_left").css("width","35px");
			$(".layout_center").css("padding-left","35px");
			nyopen = false;
		}else{
			$(".layout_left").css("width","220px");
			$(".layout_center").css("padding-left","220px");
			nyopen = true;
		}
	})
	$(".menu_list li a").click(function(){
		if(typeof($(this).parent().find("ul").attr("class"))=="undefined"){
			$(this).parent().parent().find("li").removeClass("open");
			$(this).parent().addClass("open");
			return;
		}
		if(($(this).parent().parent().attr("class").indexOf("level1"))!=-1){
			if(nyopen == false){
				$(".layout_left").css("width","220px");
				$(".layout_center").css("padding-left","220px");
				nyopen = true;
			}
			openactive(this,"level1");
		}else if(($(this).parent().parent().attr("class").indexOf("level2"))!=-1){
			openactive(this,"level2");
		}else if(($(this).parent().parent().attr("class").indexOf("level3"))!=-1){
			openactive(this,"level3");
		}else if(($(this).parent().parent().attr("class").indexOf("level4"))!=-1){
			openactive(this,"level4");
		}
	})
});

function openactive (obj,id){
	if($(obj).parent().attr("class")=="open"){
		$(obj).parent().removeClass("open");
	}else{
		$("."+id+" > li").removeClass("open");
		$(obj).parent().addClass("open");
	}
}
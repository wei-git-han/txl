$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/permission/list',
        datatype: "json",
        colModel: [			
			{ label: '权限ID', name: 'pid', width: 50, key: true },
			{ label: '权限名称', name: 'pname', width: 80 }, 			
			{ label: '具体操作', name: 'phandle', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysPermission: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysPermission = {};
		},
		update: function (event) {
			var pid = getSelectedRow();
//			alert(pId);
			if(pid == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(pid)
//            alert(vm.sysPermission.pid)
		},
		saveOrUpdate: function (event) {
//		    alert(vm.sysPermission.pid);
			var url = vm.sysPermission.pid == null ? "../sys/permission/save" : "../sys/permission/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysPermission),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var pids = getSelectedRows();
			if(pids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/permission/delete",
				    data: JSON.stringify(pids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(pid){
			$.get("../sys/permission/info/"+pid, function(r){
                vm.sysPermission = r.sysPermission;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});
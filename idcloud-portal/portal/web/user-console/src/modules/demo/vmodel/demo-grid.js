define(["app-modules/demo/services/demo-grid",
		"lib/jquery/pintuer",
		'app-utils/jqx/window',
		'app-utils/variableService',
		'app-utils/messageBoxService',
		"app-utils/$extendService"], function  (diskService,pintuer,win,variable,messageBox) {

	var consoleDisk = avalon.define({
		$id:'consoleDisk',
		title:'云硬盘',
		add:function(){
			$("#diskLsit").addClass("hidden");
			$("#diskLsit").removeClass("show");
			$("#diskAdd").removeClass("hidden");
			$("#diskAdd").addClass("fadein-right");
		},
		remove:function(row){
			$("#diskGrid").ptGrid("removeRow",row);
			$('#diskGrid').ptGrid("refreshfilterrow");
		},
		search:function(){
			if($("#searchSel").val()==1){
				searchObj.diskType = $("#searchText").val();
				searchObj.diskStatus = "";
			}else if($("#searchSel").val()==2){
				searchObj.diskType = "";
				searchObj.diskStatus = $("#searchText").val();
			}
			$("#diskGrid").ptGrid("applyfilters");
			$('#diskGrid').ptGrid("refreshfilterrow");
		},
		detail:function(row){;

			//var rowDate = $("#diskGrid").ptGrid("getrowdata",row);
			//alert(JSON.stringify(rowDate));
		},
		addSnapshot:function(row){
			win.initWindow({
				title: "+ 创建快照",
				url: variable.app_modules+"/console/host/views/host-snapshot-add.html",
				btn: ['确定', '取消'],
				area: ['450px', '340px'],
				confirm:function(){

				},
				callback:function(){
					var val = {
						name:"jpg",
						comName:"newsdddsd",
						industry:"2"
					}
					avalon.vmodels.demoWindow.data = val;
				}
			});
		},
		clk1:function(){
			alert("操作1");
		},
		clk2:function(){
			alert("操作2");
		},
		refreshclick:function(){
			$('#diskGrid').ptGrid("refreshfilterrow");
			messageBox.msgNotification({
				type:"info",
				message:"刷新完成!"
			});
		},
		removeClk:function(){
			$("#diskGrid").ptGrid("removeRow");
			$("#diskGrid").ptGrid("applyfilters");
			$('#diskGrid').ptGrid("refreshfilterrow");
		},
		gzyzjclk:function(){
			alert("挂载云主机");
		},
		updateDatas:function(){
			var data = [{"diskId":"1111","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"不支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
				{"diskId":"2222","diskName":"d-fds121","diskType":"普通1云盘60Gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","diskStatus":"未使用","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"},
				{"diskId":"3333","diskName":"d-fds121","diskType":"普通云盘40G","diskStatus":"使用中","payType":"按量付费","isUninstall":"支持","isDiskArea":"北京可用区A","diskParam":"系统盘"}];
			$("#diskGrid").ptGrid("setdata",data);
			$('#diskGrid').ptGrid("refreshfilterrow");
		}
	});

	var searchObj = {
		diskType:""
		,diskStatus:""
	};

	//输入框初始
	var initInput = function(){
		$('#beginDate').datepicker({});
		$('#endDate').datepicker({});
	};

	//初始grid
	var initGrid = function(){
		$("#diskGrid").ptGrid({
			selectionmode:"checkbox",
			sortable: true,
			controller: consoleDisk,
			data:{
				localData: diskService.getGridDate(),
				params: searchObj
			},
			columns: [
				{ text: '磁盘ID/磁盘名称', datafield: 'diskId'},
				{ text: '磁盘种类', datafield: 'diskType'},
				{ text: '磁盘状态', datafield: 'diskStatus'},
				{ text: '付费类型', datafield: 'payType'},
				{ text: '可卸载',datafield:'isUninstall'},
				{ text: '可用区',datafield:'isDiskArea'},
				{ text: '磁盘属性',datafield:'diskParam'},
				{ text: '操作', datafield: '',sortable: false,width:190,align:"center"
					, cellsrenderer:function (row,rowdata) {
					var cellsHtml = "";
					cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">详情</a>';
					cellsHtml += '<span class="text-explode">|</span>';
					cellsHtml += '<a href="javascript:void(0);" ms-click="addSnapshot('+row+')">创建快照</a>';
					cellsHtml += '<span class="text-explode">|</span>';
					cellsHtml += '<a href="javascript:void(0);" ms-click="remove('+row+')">删除</a>';
					cellsHtml += '<span class="text-explode">|</span>';
					cellsHtml += '<div class="button-group">';
					cellsHtml += '<button type="button" class="button dropdown-toggle">';
					cellsHtml += '更多<span class="downward"></span>';
					cellsHtml += '</button>';
					cellsHtml += '<ul class="drop-menu pull-right">';
					cellsHtml += '<li><a href="javascript:void(0);" class="disabled">编辑</a> </li>';
					cellsHtml += '<li><a href="javascript:void(0);">删除</a> </li>';
					cellsHtml += '</ul>';
					cellsHtml += '</div>';
					return cellsHtml;
				}
				}
			],
			toolbars:[
				{id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
				{id: "ghsjBtn",name:"更换数据",type:"button",click:"updateDatas()"},
				{id: "removeBtn",name:"删除",type:"button",disabled:true,click:"removeClk()"},
				{id: "gzyzjbtn",name:"挂载云主机",type:"button",disabled:true,click:"gzyzjclk()"},

				{id: "moreBtn",name:"更多操作",type:"morebutton",icon:"icon-th-large",data:[{name:"重启",icon:"icon-th-large",click:"clk1()"},{name:"绑定标签",icon:"icon-th-large",click:"clk2()"},{name:"加载硬盘",icon:"icon-th-large"},{name:"加载SSH密钥",icon:"icon-th-large"},{name:"加载防火墙规则",icon:"icon-th-large"}]},

				{id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},

				{id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},

				{id: "searchSel",type:"select",align:"right",data:[{name:"磁盘种类",value:"1"},{name:"磁盘状态",value:"2"}]}

			],
			rowselect:function(){
				var selectDatas = $("#diskGrid").ptGrid("getselectedrow");
				if(selectDatas.length>0){
					$("#removeBtn").removeAttr("disabled");
					$("#gzyzjbtn").removeAttr("disabled");
				}else{
					$("#removeBtn").attr("disabled","disabled");
					$("#gzyzjbtn").attr("disabled","disabled");
				}
			}
		});
	}

	return avalon.controller(function  ($ctrl) {
		$ctrl.$onEnter = function(param, rs, rj){
			//进入视图
			avalon.vmodels.serviceContainer.navSelectedFlag = 'console.console-disk-mgt';
		};

		$ctrl.$onRendered = function () {
			// 视图渲染后，意思是avalon.scan完成
			// require(['lib/jquery/pintuer']);
			initGrid();
			initInput();
			pintuer.init();
		};

		$ctrl.$onBeforeUnload = function () {
			// 视图销毁前
		};

		$ctrl.$vmodels = [consoleDisk];

	});
});
define(["app-modules/user-center/accountMgt/services/user-lpk",
		"lib/jquery/pintuer",
		'app-utils/jqx/window',
		'app-utils/messageBoxService',
		'app-utils/codeService',
		"app-utils/$extendService",
		'datepicker'], function  (lpkService,pintuer,window,messageBox,codeS) {
	var userLpk = avalon.define({
		$id:'userLpk',
		title:'礼品卡',
		searchObjLpk:function(){
			searchObjLpk.validStart =$("#startFile").val();
			searchObjLpk.validTo = $("#endFile").val();
			searchObjLpk.status = $("#lpkStatus").val();
			console.log(JSON.stringify(searchObjLpk));
			initGrid();
			$('#lpkGrid').ptGrid("refreshfilterrow");
			messageBox.msgNotification({
				type:"success",
				message:"刷新成功"
			});
		},
		refreshLpkClick:function(){
			initGrid();
			$('#lpkGrid').ptGrid("refreshfilterrow");
			messageBox.msgNotification({
				type:"info",
				message:"刷新成功"
			});
		}
	});

	var searchObjLpk = {
		validStart:"",
		validTo:"",
		status:""
	};

	//初始化充值明细搜索下拉框
	var  initSearchLPK =function(){
		codeS.setOption('lpkStatus','GIFT_CARD_STAUS');
	};

	//初始grid
	var initGrid = function(){
		$("#lpkGrid").ptGrid({
			sortable: true,
			controller: userLpk,
			data:{
				url: "/rest/giftCards/displayGiftCards",
				type: 'GET',
				params: searchObjLpk
			},
			columns: [
				{ text: '名称', datafield: 'cardName'},
				{ text: '卡号', datafield: 'cardNo'},
				{ text: '卡密', datafield: 'cardPassword'},
				{ text: '面额(元)', datafield: 'faceValue'},
				{text:'有效期开始',datafield:'validStart'},
				{text:'有效期截止',datafield:'validTo'},
				{text:'状态',datafield:'statusName'}
				//{text:'充值时间',datafield:'chargeTime'},
			],
			toolbars:[
				{id: "refreshLpkBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshLpkClick()"}
			]
		});
	}

	return avalon.controller(function  ($ctrl) {
		$ctrl.$onEnter = function(param, rs, rj){
			//进入视图
			avalon.vmodels.userContainer.secondNavFlag = "expense"
			avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-gift';
		};

		$ctrl.$onRendered = function () {
			// 视图渲染后，意思是avalon.scan完成
			// require(['lib/jquery/pintuer']);
			pintuer.init();
			initGrid();
			initSearchLPK();
			$('#beginDate').datepicker({autoclose: true});
			$('#endDate').datepicker({autoclose: true});
		};

		$ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		};

		$ctrl.$vmodels = [userLpk];
		
	});
});

var ticketAutoHandlerModel = function () {
	var me = this;
	var ticketMgt = new ticketMgtModel();
	// 处理工单的自动化部分的初始化
	this.initAutoHandler = function(rowData){
		$("#processType").val(rowData.processType);
		Core.AjaxRequest({
			type : 'get',
			url : ws_url + "/rest/serviceInstances/" + rowData.processObjectId,
			async:false,
			callback : function (data) {
				internetVlanName = data.internetVlanName;
				intranetVlanName = data.intranetVlanName;
				computeResSetName = data.computeResSetName;
				$("#ticket-autoHandler-mgtObjSid").val(data.mgtObjSid);
				//开通失败工单
				if (rowData.processType == "02") {
					me.initTicketResCompute();
					var changeLogMsg = {};
					$("#openX86VmResSelect").show();
					$("#changeResSelect").hide();
					//得到审核的时候选择的一些值
					Core.AjaxRequest({
						type : 'get',
						url : ws_url + "/rest/serviceInstances/getLastChangeLog/" + rowData.processObjectId,
						async:false,
						callback : function (data) {
							changeLogMsg = data;
							$("#ticket-autoHandler-resType").val(data.variables.resType);
							$("#ticket-autoHandler-ve").val(data.variables.ve);
						}
					});
					Core.AjaxRequest({
						type : 'get',
						url : ws_url + "/rest/order/getInstSpec/" + rowData.processObjectId,
						async: false,
						callback : function(data2) {
							me.getCustomCode('ticket-autoHandler-partitionType', '/system/PARTITION_TYPE' , 'codeDisplay', 'codeValue', false, 'GET', null);
							var needWanValne = me.getSpecByName(data2, "NEED_WAN");
							var netDatas = null;
							if("1" === needWanValne){
								me.getCustomCode('ticket-autoHandler-vLanIDO', '/biz/getSubmitUserResources', 'resSetType', 'resSetSid', false, 'POST', {'resType': 1, 'orderId': data.orderId});
								ticketMgt.initIpAddressDropDownList("ticket-autoHandler-wanIps", $("#ticket-autoHandler-vLanIDO").val());
								netDatas = changeLogMsg.params.nets;
								for(var n=0;n<netDatas.length;n++){
									if("01"==netDatas[n].resNetworkType){
										$("#ticket-autoHandler-wanIps").val(netDatas[n].resNetworkId);
									}
								}
								$("#ticket-autoHandler-wanTr").show();
							}

							var needLanValne = me.getSpecByName(data2, "NEED_LAN");
							if("1" === needLanValne){
								me.initDropDownList('ticket-autoHandler-vLanIDI', 'networkName', 'resNetworkSid');
								ticketMgt.initIpAddressDropDownList("ticket-autoHandler-lanIps", $("#ticket-autoHandler-vLanIDI").val());
								netDatas = changeLogMsg.params.nets;
								for(var n=0;n<netDatas.length;n++){
									if("02"==netDatas[n].resNetworkType){
										$("#ticket-autoHandler-lanIps").val(netDatas[n].resNetworkId);
									}
								}
								$("#ticket-autoHandler-lanTr").show();
							}
							//网络变化===》ip下拉框的变化
							$('#ticket-autoHandler-vLanIDO').change(function () {
								$("#ticket-autoHandler-wanIps").jqxDropDownList('clear');
								ticketMgt.initIpAddressDropDownList("ticket-autoHandler-wanIps", $(this).val());
							});

							$('#ticket-autoHandler-vLanIDI').change(function () {
								$("#ticket-autoHandler-lanIps").jqxDropDownList('clear');
								ticketMgt.initIpAddressDropDownList("ticket-autoHandler-lanIps", $(this).val());
							});
						}
					});

					$("#ticket-autoHandler-rescomuteLabel").jqxDropDownButton({
						width: 220,
						height: 22,
						dropDownWidth : 220,
						dropDownHeight : 180,
						theme:currentTheme
					});
					$('#ticket-autoHandler-rescomuteTree').jqxTree({height: '100%', width: '100%', theme : currentTheme});
					$('#ticket-autoHandler-rescomuteTree').on('select', function (event) {
						var args = event.args;
						var item = $('#ticket-autoHandler-rescomuteTree').jqxTree('getItem', args.element);
						var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;"><img width="16" height="16" style="float: left; margin-right: 4px;" class="itemicon" src="'+ item.icon+'">' + item.label + '</div>';
						$("#ticket-autoHandler-rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
						$('#ticket-autoHandler-rescomuteLabel').jqxDropDownButton('close');
						var value = item.value;
						$('#ticket-autoHandler-rescomuteid').val(value.split(",")[0]);
						$('#ticket-autoHandler-rescomuteType').val(value.split(",")[1]);
						if(value.split(",")[1] == 'RES-HOST') {
							me.getCustomCode('ticket-autoHandler-vLanIDI', '/approve/lans', 'networkName', 'resNetworkSid', false, 'POST', {'hostSid': value.split(",")[0]});
						} else {
							if(value.split(",")[0] == '自动分配' || value.split(",")[1] == 'VE' || value.split(",")[1] == 'VC') {
								me.setAutoAllocate('ticket-autoHandler-vLanIDI');
							}
						}
					});

					$("#ticket-autoHandler-virtualSwitch").jqxDropDownList({
						width: 220,
						height: 25,
						dropDownHeight : 180,
						theme:"metro"
					});
					$("#ticket-autoHandler-rootHbaCard").jqxDropDownList({
						width: 220,
						height: 25,
						dropDownHeight : 180,
						theme:"metro"
					});
					$("#ticket-autoHandler-networkHbaCard").jqxDropDownList({
						width: 220,
						height: 25,
						dropDownHeight : 180,
						theme:"metro"
					});

					$('#ticket-autoHandler-partitionType').on('change', function (event) {
						var vEnv = $("#ticket-autoHandler-ve").val();
						if(vEnv === 'HMC,IVM') {
							if($(this).val() == '1') {
								$('.ticket-autoHandler-resSwitch').show();
								$('.ticket-autoHandler-resHba').hide();
							} else if($(this).val() == '0') {
								$('.ticket-autoHandler-resSwitch').hide();
								$('.ticket-autoHandler-resHba').show();
							}
							me.initTicketResCompute();

						}
					});
					me.switchTicketResSelectView();
					//初始化值
					var item = null;
					if((changeLogMsg.params.allocateResHostIds!=null&&changeLogMsg.params.allocateResHostIds.length>1)
						||(changeLogMsg.params.allocateResVcIds!=null&&changeLogMsg.params.allocateResVcIds.length>1)){
						//自动分配
						$('#ticket-autoHandler-rescomuteTree').jqxTree('selectItem', null);
					}else{
						if(changeLogMsg.params.allocateResHostIds!=null&&changeLogMsg.params.allocateResHostIds.length==1){
							item = me.getTreeSelectedItem($('#ticket-autoHandler-rescomuteTree').jqxTree('getItems'),
								changeLogMsg.params.allocateResHostIds[0]);
							$('#ticket-autoHandler-rescomuteTree').jqxTree('selectItem', item);
						}else if(changeLogMsg.params.allocateResVcIds!=null&&changeLogMsg.params.allocateResVcIds.length==1){
							item = me.getTreeSelectedItem($('#ticket-autoHandler-rescomuteTree').jqxTree('getItems'),
								changeLogMsg.params.allocateResVcIds[0]);
							$('#ticket-autoHandler-rescomuteTree').jqxTree('selectItem', item);
						}
					}
					if(changeLogMsg.params.nets.length>0){
						for(var netIndex=0;netIndex<changeLogMsg.params.nets.length;netIndex++){
							if(changeLogMsg.params.nets[netIndex].resNetworkType=="01"){
								$("#ticket-autoHandler-vLanIDI").val(changeLogMsg.params.nets[netIndex].resNetworkId);
								$("#ticket-autoHandler-lanIps").val(changeLogMsg.params.nets[netIndex].ipAddress);
							}
						}
					}
				}
				//云主机自动回收失败工单
				else if (rowData.processType == "07") {
					if(rowData.autoHandlerFlag === 1) {
						$("#openX86VmResSelect").hide();
						$("#changeResSelect").show();
					}
				}
				//云主机自动变更失败工单
				else if (rowData.processType == "08") {
					if(rowData.autoHandlerFlag === 1) {
						$("#openX86VmResSelect").hide();
						$("#changeResSelect").show();
					}
				}else{
					$("#openX86VmResSelect").hide();
					$("#changeResSelect").hide();
				}
			},
			failure:function(data){
			}
		});
	};

	this.getTreeSelectedItem = function(array,value){
		for(var i=0;i<array.length;i++){
			var strs = array[i].value.split(",");
			if(strs[0]==value){
				return array[i];
			}
		}
	};

	this.initDropDownList = function (id, fieldText, valueText) {
		var dropdownCmp = $("#"+id+"");
		dropdownCmp.jqxDropDownList({
			placeHolder: "请选择...",
			displayMember: fieldText,
			valueMember: valueText,
			width: 220,
			height: 22,
			autoDropDownHeight: false,
			dropDownWidth: 220,
			dropDownHeight: 180,
			disabled: false,
			theme: currentTheme
		});
	};

	// 自定义数据字典方法
	this.getCustomCode1 =function(editor,url,fieldText,valueText,nullItem,methodType,params,selected){
		Core.AjaxRequest({
			url :ws_url +"/rest"+ url,
			type:methodType = null ? "get":methodType,
			params:params = null ? "":params,
			async:false,
			callback : function (data) {
				var source ={
					datatype: "json",
					datafields: [
						{ name: fieldText },
						{ name: valueText }
					],
					localdata:data
				};

				var settings ={
					message: "没有可以选择的资源，请检查后再试"
				};

				if (data.length < 1) {
//					Core.alert(settings);
					obj.isEmpty = true;
					return;
				}
				else {
					obj.isEmpty = false;
				}

				var i = 0;
				$.each(data, function (i, row) {
					if (row.resSetType == selected) {
						obj.selectedIndex = i;
						return false;
					}
				});


				var dataAdapter = new $.jqx.dataAdapter(source);
				editor.jqxDropDownList({
					placeHolder: "请选择...",
					selectedIndex: 0,
					source: dataAdapter,
					displayMember: fieldText,
					valueMember: valueText,
					width: 250,
					//height: 200,
					autoDropDownHeight: true,

					//dropDownWidth :200,
					//dropDownHeight :200,
					disabled: false,
					theme:"metro"
				});
				// 判断是否显示空白行
				if(typeof(nullItem) == "boolean"&& nullItem ){
					$("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				}
			}
		});
	};

	this.getNetsSpecIpValue = function (nets, vlanSid) {
		var ipAddress = '';
		for(var i = 0; i < nets.length; i++) {
			if(nets[i].resNetworkId === vlanSid) {
				ipAddress = nets[i].ipAddress;
				break;
			}
		}
		return ipAddress;
	};

	this.getSpecByName = function (specs, specName) {
		for(var i = 0; i < specs.length; i++) {
			if(specs[i].name == specName) {
				return specs[i].value;
			}
		}
	};

	/** 根据虚拟机化环境，决定资源选择终审页面显示*/
	this.switchTicketResSelectView = function() {
		var resType = $("#ticket-autoHandler-resType").val();
		var vEnv = $("#ticket-autoHandler-ve").val();
		var mgtObjSid = $("#ticket-autoHandler-mgtObjSid").val();
		var partitionType = $("#ticket-autoHandler-partitionType").val();
		//虚拟机
		if(resType == '1') {
			if(vEnv === 'VMware') {
				$('.ticket-autoHandler-resPartition').hide();
				$('#ticket-autoHandler-rescomuteTr').show();
				$('.ticket-autoHandler-resSwitch').hide();
				$('.ticket-autoHandler-resHba').hide();
				$('#ticket-autoHandler-lanTr').show();
				$('#ticket-autoHandler-wanTr').hide();
				me.initTicketResCompute();
			} else if(vEnv === 'HMC,IVM') {
				$('.ticket-autoHandler-resPartition').show();
				$('#ticket-autoHandler-rescomuteTr').show();
				$('.ticket-autoHandler-resSwitch').hide();
				$('.ticket-autoHandler-resHba').hide();
				$('#ticket-autoHandler-lanTr').show();
				$('#ticket-autoHandler-wanTr').hide();
				$('#ticket-autoHandler-partitionType').val('1');
				$('#ticket-autoHandler-partitionType').trigger('change');
			}else{
				$('.ticket-autoHandler-resPartition').hide();
				$('#ticket-autoHandler-rescomuteTr').hide();
				$('.ticket-autoHandler-resSwitch').hide();
				$('.ticket-autoHandler-resHba').hide();
				$('#ticket-autoHandler-lanTr').show();
				$('#ticket-autoHandler-wanTr').hide();
			}
		} else {
			$('.ticket-autoHandler-resPartition').hide();
			$('#ticket-autoHandler-rescomuteTr').show();
			$('.ticket-autoHandler-resSwitch').hide();
			$('.ticket-autoHandler-resHba').hide();
			$('#ticket-autoHandler-lanTr').show();
			$('#ticket-autoHandler-wanTr').hide();
			me.initTicketResCompute();
		}
	};

	this.initTicketResCompute = function () {
		me.initTicketResComputeTree();
		me.setTicketResCompute('');
	};

	this.getTicketResPoolType = function () {
		var ve = $("#ticket-autoHandler-ve").val();
		var partitionType = $("#ticket-autoHandler-partitionType").val();
		var resType = $("#ticket-autoHandler-resType").val();
		var resPoolType = "";
		if(ve == 'HMC,IVM') {
			if(partitionType == '1') {
				resPoolType = "PCVP";
			} else if(partitionType == '0'){
				resPoolType = "PCP";
			}
		} else if(ve == 'VMware') {
			if(resType == '1') {
				resPoolType = "PCVX";
			} else if(resType == '2') {
				resPoolType = "PCX";
			}
		}
		return resPoolType;
	};

	this.initTicketResComputeTree = function () {
		var mgtObjSid = $("#ticket-autoHandler-mgtObjSid").val();
		var resPoolType = me.getTicketResPoolType();
		if($.trim(resPoolType) === "" || $.trim(mgtObjSid) === "") {
			return;
		}
		Core.AjaxRequest({
			type : 'post',
			url : ws_url + "/rest/mgtObj/findMgtObjComTree",
			async: false,
			params: {
				resPoolType: resPoolType,
				mgtObjSid: mgtObjSid
			},
			callback: function (treeData) {
				var treeFillData = [];
				if(treeData.length > 0) {
					treeFillData = [{
						'resTopologySid': '-1',
						'parentTopologySid': '',
						'resTopologyValue': '自动分配,AUTO',
						'resTopologyName': '自动分配',
						'topologyIcon': '../../images/icon/resource-pool-pni-2.png'
					}];
					for(var i = 0; i < treeData.length; i++) {
						treeData[i]['resTopologyValue'] = treeData[i]['resTopologySid'] + "," + treeData[i]['resTopologyType'];
						treeFillData.push(treeData[i]);
					}
				} else {
					$("#ticket-autoHandler-rescomuteTree").jqxTree('clear');
					var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">请选择...</div>';
					$("#ticket-autoHandler-rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
					$("#ticket-autoHandler-rescomuteid").val('');
					$("#ticket-autoHandler-virtualSwitch").jqxDropDownList('clear');
					$("#ticket-autoHandler-cpuResPool").jqxDropDownList('clear');
					$("#ticket-autoHandler-rootHbaCard").jqxDropDownList('clear');
					$("#ticket-autoHandler-networkHbaCard").jqxDropDownList('clear');
					$("#ticket-autoHandler-vLanIDI").jqxDropDownList('clear');
					$("#ticket-autoHandler-lanIps").jqxDropDownList('clear');
				}
				var source =
				{
					datatype: "json",
					datafields: [
						{ name: 'resTopologySid' },
						{ name: 'parentTopologySid' },
						{ name: 'resTopologyValue' },
						{ name: 'resTopologyName' },
						{ name: 'topologyIcon' }
					],
					id: 'resTopologySid',
					localdata: treeFillData
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				dataAdapter.dataBind();
				var records = dataAdapter.getRecordsHierarchy('resTopologySid', 'parentTopologySid', 'items', [{ name: 'resTopologyName', map: 'label'},{ name: 'resTopologyValue', map: 'value'},{ name: 'topologyIcon', map: 'icon'}]);
				$('#ticket-autoHandler-rescomuteTree').jqxTree({source: records, height: '100%', width: '100%', theme : currentTheme});
				$('#ticket-autoHandler-rescomuteTree').jqxTree('expandAll');
				$('#ticket-autoHandler-rescomuteTree').jqxTree('selectItem', null);
			}
		});
	};

	this.setAutoAllocate = function (id) {
		var dropDownElem = $("#"+id+"");
		dropDownElem.jqxDropDownList('clear');
		dropDownElem.jqxDropDownList('insertAt', { label:"自动分配" ,value: "自动分配"}, 0);
		dropDownElem.jqxDropDownList('selectIndex', 0 );
	};

	this.setTicketResCompute = function (rescomuteid, rescomuteType) {
		var rescomuteLabel = '';
		if(!rescomuteid || rescomuteid === "") {
			rescomuteLabel = '自动分配';
			$("#ticket-autoHandler-rescomuteid").val('');
		} else {
			var treeItems = $("#ticket-autoHandler-rescomuteTree").jqxTree('getItems');
			for(var i = 0; i < treeItems.length; i++) {
				if((rescomuteid + "," + rescomuteType) == treeItems[i].value) {
					rescomuteLabel = treeItems[i].label;
					break;
				}
			}
		}
		var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' + rescomuteLabel + '</div>';
		$("#ticket-autoHandler-rescomuteLabel").jqxDropDownButton('setContent', dropDownContent);
	};

	// 自定义数据字典方法
	this.getCustomCode =function(id,url,fieldText,valueText,nullItem,methodType,params){
		Core.AjaxRequest({
			url :ws_url +"/rest"+ url,
			type:methodType = null ? "get":methodType,
			params:params = null ? "":params,
			async:false,
			callback : function (data) {
				var dropdownCmp = $("#"+id+"");
				dropdownCmp.jqxDropDownList({
					placeHolder: "请选择...",
					displayMember: fieldText,
					valueMember: valueText,
					width: 220,
					height: 22,
					autoDropDownHeight: false,
					dropDownWidth: 220,
					dropDownHeight: 180,
					disabled: false,
					theme: currentTheme
				});

				if(!data ||data.length === 0) {
					me.checkInstanceResSelect(id);
					if(id == 'ticket-autoHandler-vLanIDI') {
						$('#ticket-autoHandler-lanIps').jqxDropDownList('clear');
					}
				}
				var includeAutoAllc = [];
				if((id == 'ticket-autoHandler-virtualSwitch' || id == 'ticket-autoHandler-cpuResPool'
					|| id == 'ticket-autoHandler-rootHbaCard' || id == 'ticket-autoHandler-networkHbaCard'
					|| id == 'ticket-autoHandler-vLanIDI') && data.length > 0) {
					var auto = {};
					auto[fieldText] = '自动分配';
					auto[valueText] = '自动分配';
					includeAutoAllc.push(auto);
				}
				for(var i = 0; i < data.length; i++) {
					includeAutoAllc.push(data[i]);
				}
				var source ={
					datatype: "json",
					datafields: [
						{ name: fieldText },
						{ name: valueText }
					],
					localdata:includeAutoAllc
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				dropdownCmp.jqxDropDownList({source: dataAdapter});
			}
		});
	};

};

/**************************************************** 资源检查********************************************/
function resCheckSubmit() {
	var autoTicket = new ticketAutoHandlerModel();
	var params = {};
	//如果是开通流程
	if($("#processType").val() == '02') {
		var resSelectData = [];
		var resData = {};
		resData.rescomuteid = $("#ticket-autoHandler-rescomuteid").val() == '自动分配' ? '' : $("#ticket-autoHandler-rescomuteid").val();
		resData.rescomuteType = $("#ticket-autoHandler-rescomuteType").val();
		resData.vLanIDO = $("#ticket-autoHandler-vLanIDO").val() == '自动分配' ? '' : $("#ticket-autoHandler-vLanIDO").val() ;
		resData.vLanIDI = $("#ticket-autoHandler-vLanIDI").val() == '自动分配' ? '' : $("#ticket-autoHandler-vLanIDI").val();
		resData.wanIp = $("#ticket-autoHandler-wanIps").val() == '自动分配' ? '' :$("#ticket-autoHandler-wanIps").val();
		resData.lanIp = $("#ticket-autoHandler-lanIps").val() == '自动分配' ? '' :$("#ticket-autoHandler-lanIps").val();
		resData.virtualSwitch = $("#ticket-autoHandler-partitionType").val() == '1' ? $("#ticket-autoHandler-virtualSwitch").val() :'';
		resData.rootHbaCard = $("#ticket-autoHandler-partitionType").val() == '0' ? $("#ticket-autoHandler-rootHbaCard").val():'';
		resData.networkHbaCard = $("#ticket-autoHandler-partitionType").val() == '0' ? $("#ticket-autoHandler-networkHbaCard").val():'';
		resData.resType = $("#ticket-autoHandler-resType").val();
		resData.ve = $("#ticket-autoHandler-ve").val();
		resData.partitionType = $('#ticket-autoHandler-ve').val() == 'HMC,IVM' ? $('#ticket-autoHandler-partitionType').val() : '';
		resData.resPoolType = autoTicket.getTicketResPoolType();
		resData.instanceSid =  $("#processObjectId").val();
		resSelectData.push(resData);
		params = {
			processObjectId: $("#processObjectId").val(),
			resSet: resSelectData,
			ticketType: $("#processType").val()
		};
	}
	Core.AjaxRequest({
		url : ws_url + "/rest/approve/platform/vmResCheck",
		aysnc:false,
		params :params,
		callback : function (data) {
		},
		failure:function(data){
		}
	});
}

function excecuteSubmit() {
	var ticketMgt = new ticketMgtModel();
	var autoTicket = new ticketAutoHandlerModel();
	var params = {};
	var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
	var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
	if (rowindex < 0) {
		return;
	}
	if($("#processType").val() == '02') {
		var resSelectData = [];
		var resData = {};
		resData.rescomuteid = $("#ticket-autoHandler-rescomuteid").val() == '自动分配' ? '' : $("#ticket-autoHandler-rescomuteid").val();
		resData.rescomuteType = $("#ticket-autoHandler-rescomuteType").val();
		resData.vLanIDO = $("#ticket-autoHandler-vLanIDO").val() == '自动分配' ? '' : $("#ticket-autoHandler-vLanIDO").val() ;
		resData.vLanIDI = $("#ticket-autoHandler-vLanIDI").val() == '自动分配' ? '' : $("#ticket-autoHandler-vLanIDI").val();
		resData.wanIp = $("#ticket-autoHandler-wanIps").val() == '自动分配' ? '' :$("#ticket-autoHandler-wanIps").val();
		resData.lanIp = $("#ticket-autoHandler-lanIps").val() == '自动分配' ? '' :$("#ticket-autoHandler-lanIps").val();
		resData.virtualSwitch = $("#ticket-autoHandler-partitionType").val() == '1' ? $("#ticket-autoHandler-virtualSwitch").val() :'';
		resData.rootHbaCard = $("#ticket-autoHandler-partitionType").val() == '0' ? $("#ticket-autoHandler-rootHbaCard").val():'';
		resData.networkHbaCard = $("#ticket-autoHandler-partitionType").val() == '0' ? $("#ticket-autoHandler-networkHbaCard").val():'';
		resData.resType = $("#ticket-autoHandler-resType").val();
		resData.ve = $("#ticket-autoHandler-ve").val();
		resData.partitionType = $('#ticket-autoHandler-ve').val() == 'HMC,IVM' ? $('#ticket-autoHandler-partitionType').val() : '';
		resData.resPoolType = autoTicket.getTicketResPoolType();
		resData.instanceSid = $("#processObjectId").val();
		resSelectData.push(resData);
		params = {
			processObjectId: $("#processObjectId").val(),
			resSet: resSelectData,
			ticketType: $("#processType").val(),
			ticketSid: data.ticketSid
		};
	} else if($("#processType").val() == '07') {
		params = {
			ticketSid: data.ticketSid,
			processObjectId: $("#processObjectId").val(),
//			detailSid: $("#detailSid").val(),
//			orderId: $("#orderId").val(),
//			processInstanceId: "0",
//			approvorAction: $("#approvorAction").val(),
			ticketType: $("#processType").val()
		};
	} else if($("#processType").val() == '08') {
		params = {
			ticketSid: data.ticketSid,
			processObjectId: $("#processObjectId").val(),
//			detailSid: $("#detailSid").val(),
//			orderId: $("#orderId").val(),
//			processInstanceId: "0",
//			approvorAction: $("#approvorAction").val(),
			ticketType: $("#processType").val()
		};
	}
	Core.AjaxRequest({
		url : ws_url + "/rest/tickets/platform/executeTicket",
		params: params,
		callback : function (data) {
			$('#jqxgridTicket').jqxGrid('clearselection');
			$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			ticketMgt.searchTicketMgt();
			$("#popupProcessWindow").jqxWindow('close');


			//更新菜单
			/** 获取当前用户的角色和权限数据，生成导航条 */
			if(currentUser != null || currentUser != 'undefined'){
				Core.AjaxRequest({
					url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
					type : "GET",
					async: false,
					callback : function (data) {

						window.parent.createNavList(data);
						window.parent.moudulesData = data;

						window.parent.$("#menuContent .liShow").removeClass("liShow");
						window.parent.$("#menuContent").find("b:contains('工单管理')").parents("li").addClass("liShow");
					}
				});
			}
		},
		failure:function(data){

		}
	});
}
  
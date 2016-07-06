// 调整IP
var vmConfigIpModel = function() {

	// 验证初始化
	this.initValidator = function() {
		$('#vmConfigIpForm').jqxValidator({
			rules : [ {
				input : '.ipAddress',
				message : '不能为空',
				action : 'keyup, blur',
				rule : function(input, commit) {
					var flag = false;
					$(".ipAddress").each(function() {
						var ip = $(this).val();
						if (ip != null && ip != "") {
							flag = true;
						}
					});
					return flag;
				}
			}, {
				input : '.ipAddress',
				message : '请输入正确的IP地址',
				action : 'keyup, blur',
				rule : function(input, commit) {
					var flag = false;
					$(".ipAddress").each(function() {
						var ip = $(this).val();
						if (ip != null && ip != "") {
							flag = true;
						}
					});
					return flag;
				}
			}, ]
		});
	};

	// 初始化window
	this.initPopWindow = function() {
		// 监控信息
		$("#vmConfigIpWindow").jqxWindow({
			width : 450,
			height : 185,
			theme : currentTheme,
			resizable : false,
			isModal : true,
			autoOpen : false,
			modalOpacity : 0.3,
			cancelButton: $("#vmConfigIpCancel"), 
			initContent : function() {
				$("#vmConfigIpSave").jqxButton({
					width : '50',
					theme : currentTheme,
					height : '25px'
				});
				$("#vmConfigIpCancel").jqxButton({
					width : '50',
					theme : currentTheme,
					height : '25px'
				});
			}
		});
	};
	this.appendTr = function(ip) {
		if (ip != "" && ip != null) {
			var trHtml = "<tr>"
					+ "<td align='right'><font style='color:red'></font>IP地址：</td>"
					+ "<td>"
					+ "<input type='text' class='ipAddress' value='"
					+ ip
					+ "'/>"
					+ "<a id='jqxTuidingBtn' onclick='deleteTr($(this))' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-1'></i></a>"
					+ "</td>" + "</tr>";
			$("#ipTable").append(trHtml);
		} else {
			var trHtml = "<tr>"
					+ "<td align='right'><font style='color:red'></font>IP地址：</td>"
					+ "<td>"
					+ "<input type='text' class='ipAddress'/>"
					+ "<a id='jqxTuidingBtn' onclick='appendTr($(this))' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-1'></i></a>"
					+ "</td>" + "</tr>";
			$("#ipTable").append(trHtml);
		}
	};
};
var resVmSid;

// 添加tr
function appendTr(e) {
	var input = e.prev();
		var trHtml = "<tr>"
				+ "<td align='right'><font style='color:red'></font>IP地址：</td>"
				+ "<td>"
				+ "<input type='text' class='ipAddress'/>"
				+ "<a id='jqxTuidingBtn' onclick='appendTr($(this))' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-plus-1'></i</a>"
				+ "</td>" + "</tr>";
		$("#ipTable").append(trHtml);
		e.remove();
		var deletebutn = "<a id='jqxTuidingBtn' onclick='deleteTr($(this))' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-2'></i></a>";
		input.after(deletebutn);

}

// 删除tr
function deleteTr(e) {
	var trEle = e.parent().parent();
	trEle.remove();
}
// 提交编辑主机信息
function vmConfigIpSubmit() {
	var isOk = $('#vmConfigIpForm').jqxValidator('validate');
	if (isOk) {
		var ipCheck=false;
		$(".ipAddress").each(function() {
			if($(this).val()!=""&&$(this).val()!=null){
				var ipReg = /^(?:(?:[1-9]?[0-9]|1[0-9]{2}|2(?:[0-4][0-9]|5[0-5]))\.){3}(?:[1-9]?[0-9]|1[0-9]{2}|2(?:[0-4][0-9]|5[0-5]))$/;
				if (ipReg.test($(this).val())) {
					ipCheck=true;
				}else{
					Core.alert({
						title : "错误",
						type : "error",
						message : "请输入正确的IP地址",
						callback : function() {
							$(this).css("border","1px solied red");
						}
					});
					ipCheck=false;
					return;
				}
			}
		});
		
		if(ipCheck){
			var ipString = "";
			$(".ipAddress").each(function() {
				ipString += $(this).val() + ",";
			});
			ipString = ipString.substring(0, ipString.length - 1);

			Core.AjaxRequest({
				url : ws_url + "/rest/vms/configIp",
				params : {
					resVmSid : resVmSid,
					vmIp : ipString
				},
				async : false,
				showMsg : true,
				callback : function(data) {
					$("#vmConfigIpWindow").jqxWindow('close');
					var vmmanagedmodel = new virtualBizVMDatagridModel();
					vmmanagedmodel.searchVMInfo();
				}
			});
		}
	}

}
// 弹出修改IP信息
function popConfigIpWindow(row) {
	var data = $('#bizVmdatagrid').jqxGrid('getrowdata', row);
	resVmSid = data.resBizVmSid;
	var ipString = data.vmIp;
	var configip = new vmConfigIpModel();
	// 删除所有tr
	$("#ipTable tr").remove();
	if (ipString != "" && ipString != null) {
		var ipSet = ipString.split(",");
		// 初始化监控信息
		for ( var i = 0; i < ipSet.length; i++) {
			configip.appendTr(ipSet[i]);
		}
		configip.appendTr(null);
	} else {
		configip.appendTr(null);
	}
	var windowW = $(window).width();
	var windowH = $(window).height();
	$("#vmConfigIpWindow").jqxWindow({
		position : {
			x : (windowW - 450) / 2,
			y : (windowH - 185) / 2
		}
	});
	$("#vmConfigIpWindow").jqxWindow('open');
	
}
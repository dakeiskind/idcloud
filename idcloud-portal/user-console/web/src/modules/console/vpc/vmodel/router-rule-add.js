define([
        'app-utils/codeService',
        'app-utils/httpService',
        'avalon',
        ], function(code,http) {
	var addPort = avalon.define({
		$id:'addPortsWindow',
		addRuleData:null,
        zoneSid: "",
        pnData: [],
        pnValue: "",
        subnetData: [],
        subnetValue: "",
        initData:function(zoneSid){
            // 1.查询当前分区下的私有网络
            addPort.zoneSid = zoneSid;
            var json = {
                zoneSid: zoneSid
            };
            addPort.pnData = code.getCustomCode("/networks/vpc?"+ $.param(json),"GET",null);
            if(addPort.pnData.length > 0){
                addPort.pnValue = addPort.pnData[0].resVpcSid;
                // 2.联动查询私有网络下的子网
                var subnetJson = {
                    zoneSid: zoneSid,
                    parentTopologySid: addPort.pnValue
                };
                addPort.subnetData = code.getCustomCode("/networks/subnet?"+ $.param(subnetJson),"GET",null);
                if(addPort.subnetData.length > 0){
                    addPort.subnetValue = addPort.subnetData[0].resNetworkSid;
                }
            }
        },
        pnValueChanged: function(){
            var subnetJson = {
                zoneSid: addPort.zoneSid,
                parentTopologySid: addPort.pnValue
            };
            addPort.subnetData = code.getCustomCode("/networks/subnet?"+ $.param(subnetJson),"GET",null);
            if(addPort.subnetData.length > 0){
                addPort.subnetValue = addPort.subnetData[0].resNetworkSid;
            }
        },
        initValidator:function(){
            // 验证格式
            $("#addPortsForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    ruleName: {required: true,maxlength: 128,minlength: 2},
                    subNetIp: {required: true},
                    routerName:{required: true},
                    routerId:{required: true},
                },
                messages: {
                    ruleName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    },
                    subNetIp: {
                        required:"不能为空",
                       
                    },
                    routerName: {
                        required:"不能为空",
                       
                    },
                    routerId: {
                        required:"不能为空",
                       
                    },


                }
            });
        }
		
	});

    return addPort;

});
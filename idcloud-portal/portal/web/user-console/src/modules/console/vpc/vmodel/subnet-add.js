define(['app-utils/codeService','avalon'], function(code) {
	var subnetAddWindow = avalon.define({
		$id:'subnetAddWindow',
		addSubnetData: {
            vpcSid: null,
            subNetName: null,
            subNetDescription: null,
            subNetCidr: ""
        },
        vpcData:[], // 私有网络
        vpcValue: "", // 私有网络数据
        pnSegment: null,
        initData:function(){
            var json = {
                zoneSid: avalon.vmodels.subnetMgt.zoneValue
            };
            subnetAddWindow.vpcData = code.getCustomCode("/networks/vpc?"+$.param(json),"get",null);
            if(subnetAddWindow.vpcData.length > 0){
                subnetAddWindow.vpcValue = subnetAddWindow.vpcData[0].cidr;
            }
        },
        switchPrivateSegment:function(value){
            if(value == "192.168.0.0/16"){
                subnetAddWindow.pnSegment = "192.168.0.0/16";
                var str = '<span style="font-size: 12px">192.168.</span>'+
                    '<input class="hidden" type="hidden" value="192.168.">'+
                    '<input class="cidr" type="text" name="cidr1" maxLength="3" value="1" style="width:35px;padding-left: 4px;height: 20px;font-size: 12px;" class="input text-small radius-none">.'+
                    '0&nbsp;/&nbsp;24'+
                    '<p class="description" style="width: 220px">默认网段掩码为24,例如:192.168.1.0/24</p>'
                $("#segment").html(str);
            }else if(value == "10.10.0.0/16"){

                subnetAddWindow.pnSegment = "10.10.0.0/16";
                var str = '<span style="font-size: 12px">10.0.</span>'+
                    '<input class="hidden"  type="hidden" value="10.0.">'+
                    '<input class="cidr" type="text" name="cidr2" maxLength="3" value="1" style="width:35px;padding-left: 4px;height: 20px;font-size: 12px;" class="input text-small radius-none">.'+
                    '0&nbsp;/&nbsp;24'+
                    '<p class="description" style="width: 220px">默认网段掩码为24,例如:10.0.1.0/24</p>'
                $("#segment").html(str); 
            }else if(value == ''){
                subnetAddWindow.pnSegment = null;
                $("#segment").html('');
            }
        },
        initValidator:function(){
            $("#addSubnetForm").validate({
            // 验证格式
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    subnetName: {required: true,maxlength: 128,minlength: 2},
                    cidr1:{digits:true,max:255,min:1},
                    cidr2:{digits:true,max:255,min:1},
                    segmentNetwork:{required: true}
                },
                messages: {
                    subnetName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    },
                    cidr1:{
                        digits:"请输入整数",
                        max:"最大值为255",
                        min:"最小值为1",
                    },
                    cidr2:{
                        digits:"请输入整数",
                        max:"最大值为255",
                        min:"最小值为1",
                    },
                    segmentNetwork:{
                        required:"请选择一条网段"
                    }
                    
                }
            });
        }
		
	});

    subnetAddWindow.$watch("vpcValue", function(a, b) {
        // 计费
        subnetAddWindow.switchPrivateSegment(a);

    });

    return subnetAddWindow;

});
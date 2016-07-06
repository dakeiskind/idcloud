define(['layer','app-utils/httpService','avalon'], function(layer,http) {
	var pnAddWindow = avalon.define({
		$id:'pnAddWindow',
		addPnData:{},
        addSubnetData:{},
        closeWindow:function(){
           layer.closeAll();
        },
        thePreStep:function(){
            // 跳转到新增子网内容
            $("#subNetForm").fadeOut('fast',function(){
                $("#addPnForm").fadeIn('fast');
            });
        },
        theNextStep:function(){
            if($("#addPnForm").valid()){
                // 跳转到新增子网内容
                $("#addPnForm").fadeOut('fast',function(){
                    $("#subNetForm").fadeIn('fast');
                });
            }
        },
        switchSubnetSegment:function(obj){
            // TODO 解析ip地址
            if($(obj).val() == "192.168.0.0/16"){
                var str = '<span style="font-size: 12px">192.168.</span>'+
                    '<input class="hidden" type="hidden" value="192.168.">'+
                    '<input class="cidr" type="text" name="cidr1" maxLength="3" value="1" style="width:35px;padding-left: 4px;height: 20px;font-size: 12px;" class="input text-small radius-none">.'+
                    '0&nbsp;/&nbsp;24'+
                    '<p class="description" style="width: 220px">默认网段掩码为24,例如:192.168.1.0/24</p>'
                $("#subnetSegment").html(str);
            }else if($(obj).val() == "10.10.0.0/16"){
                var str = '<span style="font-size: 12px">10.0.</span>'+
                    '<input class="hidden"  type="hidden" value="10.0.">'+
                    '<input class="cidr" type="text" name="cidr2" maxLength="3" value="1" style="width:35px;padding-left: 4px;height: 20px;font-size: 12px;" class="input text-small radius-none">.'+
                    '0&nbsp;/&nbsp;24'+
                    '<p class="description" style="width: 220px">默认网段掩码为24,例如:10.10.1.0/24</p>'
                $("#subnetSegment").html(str);
            }
        },
        submitPrivateNetworkInfo:function(){
            if($("#subNetForm").valid()){
                pnAddWindow.addSubnetData.subNetCidr = $("#subnetSegment .hidden").val()+$("#subnetSegment .cidr").val()+".0/24";
                pnAddWindow.addPnData.subNet = pnAddWindow.addSubnetData;

                http.AjaxRequest({
                    url :"/rest/networks/vpc",
                    type: "POST",
                    params: pnAddWindow.addPnData,
                    async: true,
                    callback : function (data) {
                        // 关闭新增window
                        layer.closeAll();

                        // 判断当前路由地址
                        var urlPath = window.location.href.split("#!")[1];
                        // 获取数据，判断数据来源
                        if("/console/vpc/topology" == urlPath){
                            // 刷新拓扑图
                            avalon.vmodels.networkTopology.initTopologyPicture();
                        }else{
                            // 刷新列表
                            avalon.vmodels.pnMgt.refreshclick();
                        }
                    }
                });
            }
        },
        initValidator:function(){
            // 验证格式
            $("#addPnForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    vpcName: {required: true,maxlength: 128,minlength: 2},
                    vpcCidr:{required: true}
                },
                messages: {
                    vpcName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    },
                    vpcCidr:{
                        required:"请选择一条网段"
                    }
                    
                }
            });
            $("#subNetForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    subNetName: {required: true,maxlength: 128,minlength: 2},
                    cidr1:{digits:true,max:255,min:1},
                    cidr2:{digits:true,max:255,min:1},
                },
                messages: {
                    subNetName: {
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
                }
            });

        }
		
	});

    return pnAddWindow;

});
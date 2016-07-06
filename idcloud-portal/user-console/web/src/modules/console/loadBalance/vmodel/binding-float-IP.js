define(['app-utils/jqx/window',
        'app-utils/messageBoxService',
        'app-utils/variableService',
        'validator',
        'avalon',
        'app-utils/validatorService',], function(window,messageBox,variable,validate) {
    var bindingFloatIP = avalon.define({
        $id:'bindingFloatIP',
        data:{

        },
        ipAddress:[
            '192.168.1.1.1'
        ],
        resourcePool:"public",
        distributionFloatIP:function(){
            window.initWindow({
                title: "管理浮动IP的关联",
                url: variable.app_modules+"/console/loadBalance/views/distribution-float-IP.html",
                btn: ['分配IP','取消'],
                area: ['450px', '200px'],
                confirm:function(){
                    if($("#distributionFloatIPForm").valid()){
                        var resourcePool = avalon.vmodels.distributionFloatIP.data.resourcePool;
                        messageBox.msgNotification({
                            type:"success",
                            message: resourcePool+ "添加成功!"
                        });
                        $("#IPAddress").empty();
                        $("#IPAddress").append('<option selected="selected" value="">请选择一个IP地址</option>');
                        $("#IPAddress").append('<option value='+avalon.vmodels.bindingFloatIP.ipAddress[0]+'>'+avalon.vmodels.bindingFloatIP.ipAddress[0]+'</option>');
                        return true;
                    }
                    return false;
                },
                callback:function(){
                }
            });
        },
        initValidator:function(){
            $("#bindingFloatIPForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    IPAddress:{required: true},
                    waitConnectionPort:{required: true}
                },
                messages: {
                    IPAddress:{
                        required:"必填"
                    },
                    waitConnectionPort: {
                        required:"必填"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });
    return bindingFloatIP;
});
define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var addMonitor = avalon.define({
        $id:'addMonitor',
        data:{},

        monitorTypeSwitch:function(obj){
            if($(obj).val() == "HTTP" || $(obj).val() == "HTTPS"){
                $(".HTTPTr").removeClass("hidden");
            }else if($(obj).val() == "PING" || $(obj).val() == "TCP"){
                $(".HTTPTr").addClass("hidden");
            }
        },

        initValidator:function(){
            //var overtime;
            $("#addMonitorForm").validate({
                onkeyup: function(element) {
                    //var overtime = $("#overtime").val();
                    $(element).valid();
                },
                rules: {
                    monitorType:{required: true},
                    delay:{required: true},
                    overtime:{required: true},
                    maxTryTime:{required:true,min:1,max:10}

                },
                messages: {
                    monitorType:{
                        required:"请选择一种监控类型"
                    },
                    delay: {
                        required:"必填"
                    },
                    overtime: {
                        required:"必填"
                    },
                    maxTryTime: {
                        required:"必填",
                        min:"请输入1~10之间的整数",
                        max:"请输入1~10之间的整数"
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });
    return addMonitor;
});
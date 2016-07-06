define(['avalon'], function() {
	var subnetEditWindow = avalon.define({
		$id:'subnetEditWindow',
        editSubnetData:{
            resNetworkSid: null,
            networkName: null,
            description: null
        },
		initValidator:function(){
            // 验证格式
            $("#editSubnetForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    subnetName: {required: true,maxlength: 128,minlength: 2},
                    description:{maxlength: 256,minlength: 2}
                },
                messages: {
                    subnetName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    },
                    description:{
                        maxlength: '字段不能超过256个字符',
                        minlength: '字段最少为2个字符'
                    }
                    
                }
            });
        }
	});

    return subnetEditWindow;

});
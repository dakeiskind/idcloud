define(['avalon'], function() {
	var pnEditWindow = avalon.define({
		$id:'pnEditWindow',
        editPnData:{
            resVpcSid:null,
            vpcName: null,
            description: null,
            cidr: null
        },
        initValidator:function(){
            // 验证格式
            $("#editPnForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    pnName: {required: true,maxlength: 128,minlength: 2},
                    segmentNetwork:{required: true}
                },
                messages: {
                    pnName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    },
                    segmentNetwork:{
                        required:"请选择一条网段"
                    }
                    
                }
            });
        }
		
	});

    return pnEditWindow;

});
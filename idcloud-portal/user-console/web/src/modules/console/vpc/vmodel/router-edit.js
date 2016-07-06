define(['avalon'], function() {
	var routerEditWindow = avalon.define({
		$id:'routerEditWindow',
		editData:null,
        initValidator:function(){
            // 验证格式
            $("#editRouterForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    routerName: {required: true,maxlength: 128,minlength: 2},
                    segmentNetwork:{required: true}
                },
                messages: {
                    routerName: {
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

    return routerEditWindow;

});
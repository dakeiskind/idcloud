define(['avalon'], function() {
	var forwordRule = avalon.define({
		$id:'portsEditWindow',
		editPortData:null,
        initValidator:function(){
            // 验证格式
            $("#editPortsForm").validate({
                onkeyup: function(element) {
                    $(element).valid(); 
                },
                rules: {
                    ruleName: {required: true,maxlength: 128,minlength: 2}
                },
                messages: {
                    ruleName: {
                        required:"不能为空",
                        maxlength: '字段不能超过128个字符',
                        minlength: '字段最少为2个字符'
                    }
                    
                }
            });
        }
		
	});

    return forwordRule;

});
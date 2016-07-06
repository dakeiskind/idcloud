define(['validator'],function() {
	jQuery.validator.addMethod("isZipCode", function (value, element) {
		var tel = /^[0-9]{6}$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的邮政编码");

	jQuery.validator.addMethod("idCardNumber", function (value, element) {
		var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的身份证号码");

	jQuery.validator.addMethod("isDouble",function(value,element){
		var exp = /^[A-Za-z\u4e00-\u9fa5]+$/;
		return this.optional(element) || (exp.test(value));
	},"只能输入中英文");

	jQuery.validator.addMethod("isCash",function(value,element){
		var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		return this.optional(element) || (exp.test(value));
	},"输入的金额为8位，小数点后2位");

	jQuery.validator.addMethod("isMobile",function(value,element){
		var exp = /^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/;
		return this.optional(element) || (exp.test(value));
	},"输入手机号码有误");

	var Validator = function(){
		// 只能输入英文或数字,并且不包括  \ / : * ? " < > | ” 特殊字符
		this.inputEnglishOrNumber = function(value){
			var reg = new RegExp("^[a-zA-Z0-9_]{1,1024}$");
			if(reg.test(value)){
				return true;
			}else{
				return false;
			}
		};


		//保留8位正整数，小数点后面两位
		this.inputFloat = function(value){
			var reg = new RegExp("/\d{1,8}\.{0,1}\d{0,2}/");
			if (reg.test(value)){
				return true;
			}else{
				return false;
			}
		};
	};
	
	return new Validator();
});
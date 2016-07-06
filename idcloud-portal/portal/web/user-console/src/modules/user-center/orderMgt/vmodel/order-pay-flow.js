define(['app-utils/variableService','avalon',"app-utils/$extendService"], function(variable) {
	
	var payFlow = avalon.define({
		$id: 'orderPayFlow',
		confirmPage:variable.app_modules + "/user-center/orderMgt/views/order-pay-one.html",
		payPage:"",
		payResultPage:"",
		toPayPage:function(){
			payFlow.payPage = variable.app_modules + "/user-center/orderMgt/views/order-pay-two.html";
			$("#secondDIV").removeClass("hidden");
			$("#secondDIV").removeClass("show");
			$("#firstDIV").addClass("hidden");
			$("#firstDIV").removeClass("show");
		},
		toPayResultPage:function(){

		}
	});
	return payFlow;
});
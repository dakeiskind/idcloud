define(['app-utils/httpService'],function(http) {

	var orderBsService = function() {
		var me = this;

		// 获取价格
		this.getPrice = function(data){
			$("#cbsPrice").html("<span style='font-size: 14px;color:gray'>正在计算价格，请稍等...</span>");
			var list = me.createData(data);
			http.AjaxRequest({
				url : "/rest/billings/getPrice",
				type : "POST",
				params: list,
				async : true,
				showMsg:false,
                showWaiting:true,
				callback : function(data) {
					if(data){
						setTimeout(function(){
							$("#cbsPrice").html("￥"+parseFloat(data).toFixed(2));
						},300);
					}else{
						setTimeout(function(){
							$("#cbsPrice").html("<span style='font-size: 20px'>没有定价</span>");
						},300);
					}

				}
			});
		};
		this.createData = function(orderData){
            //console.log(JSON.stringify(orderData))
			var list = [];
			for(var i=0;i<orderData.length;i++){
				var data = orderData[i];
				var orderObj = new Object();
				orderObj.serviceCode = data.orderType;
				orderObj.data = {
					"billingType": data.buyTimeType,
					"duration": data.buyTime,
					"orderType": "01",
					"quantity": data.orderCount
				};
				orderObj.specifications={
					"region": data.regionSid,
					"zone": data.zoneSid,
					"dataDisk": [
						{ "dataDiskCategory":data.diskType,
							"dataDiskSize":data.diskSize}
					]
				};
				orderObj.specificationsDesc =  "xxx";
				list.push(orderObj);
			}
			return list;
		};
		$.fn.extend({
		//输入框加方法
		addCount: function(){
			var inputObj = $(this).prev();
			var g = /^[0-9]*[1-9][0-9]*$/;
			var flag = g.test(inputObj.val().trim());
			if(!flag&&inputObj.val().trim()!='0'){
				inputObj.val(0);
			}else{
				var countValue = parseInt(inputObj.val().trim());
				if(99 == countValue){
					return;
				}
				inputObj.val(countValue+1);
				avalon.vmodels.orderBsPage.calculationPrice();
			}

		},
		//输入框减方法
		decCount: function(){
			var inputObj = $(this).next();
			var g = /^[1-9]*[1-9][0-9]*$/;
			var flag = g.test(inputObj.val().trim());
			if(!flag){
				inputObj.val(1);
			}else{
				var countValue = parseInt(inputObj.val().trim());
				if(countValue==1){
					inputObj.val(1);
					avalon.vmodels.orderBsPage.calculationPrice();
				}else{
					inputObj.val(countValue-1);
					avalon.vmodels.orderBsPage.calculationPrice();
				}
			}
		},
		initShopping: function(){
			var shoppingdiv = '<div class="border  shopping">'+
							  '<div class="bg-sub text-white shopping-title text-small"><span class="text-small">你已选购的清单(<span class="shopping-title-count text-small">'+0+'</span>台)</span></div>'+
							  '<div class="shopping-content-list text-small"></div>';
			shoppingdiv += "</div>";
			$(this).append(shoppingdiv);
		},
		//删除购物车
		removeShopping:function(divname){
			var orderData = avalon.vmodels.orderBsPage.orderHistoryListData;
			if(orderData.length > 0){
				for(var i=0;i<orderData.length;i++){
					if(divname == orderData[i].id){
						orderData.splice(i,1);
						break;
					}
				}
			}
			avalon.vmodels.orderBsPage.orderHistoryListData = orderData;
			var id = $(this).attr("id");
			var sumcount = parseInt($(".shopping-title-count").text());
			var perCount = $("#"+divname+"div").find(".perCount").val();
			$(".shopping-title-count").text(sumcount-parseInt(perCount));
			$("#"+divname+"div").remove();
			if(0 == (sumcount-1)){
				$("#submitButton").attr("disabled","disabled");
			}
		},
		showShopping:function(jsonStr){
			if(jsonStr==null){
				return;
			} else {
				try{
					var shoppingdiv = '';
					var kObj = jsonStr;
					kid=kObj.id;
					var jsonArray= kObj.data;
					var delfunction = kObj.delFunction;
//						var kObjType = kObj.type;
					var kObjTitle = kObj.title;
					var kObjCount = kObj.count;
					var count= kObj.perCount;
					shoppingdiv +='<div id="'+kid+'div" class="shopping-list text-small" >';
					shoppingdiv += '<input class="perCount" type="hidden" value='+kObj.perCount+'>'
					shoppingdiv +='<div class="shopping-list-title text-small"  id="'+kid+'" >'+
								  '<span class="text-small"><strong>'+kObjTitle+'</strong></span>'+
								  '<span class="shopping-list-right text-small">'+
								  '<span class="text-small" style="margin-top:-8px;padding-top:-10px;">'+kObjCount+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
								  '<span id="shopping-remove" onclick="'+delfunction+'"><i class="icon-times-circle" style="color:#D6D6D6"></i></span>'+
								  '</span>'+
								  '</div>'+
								  '<div class="text-small">';

					for(var i=0,len=jsonArray.length;i<len;i++){
						var jsonObj = jsonArray[i];
						var type = jsonObj.type;
						var name = jsonObj.name;
						var value= jsonObj.value;
						var style= jsonObj.style;
						var nameid= "";
						if(jsonObj.id!= ""&&jsonObj.id!=undefined){
							nameid = jsonObj.id;
						}
						shoppingdiv+='<div class="shopping-list-content-list"><span style="" class="label-left text-small" align="left" valign="middle"><label>'+name+':</label></span>';
						shoppingdiv+='<span class="text-small" style="'+style+'" id="'+nameid+'">'+value+'</span>';
						shoppingdiv+='</div>';
					}

					shoppingdiv+='</div></div>';
					var sumcount = parseInt($(".shopping-title-count").text());
					sumcount += parseInt(count);
					$(".shopping-title-count").text(sumcount);
					$(".shopping-content-list").append(shoppingdiv);

					//确认订单可用
					$("#submitButton").removeAttr("disabled");
				} catch(e){

					return;
				}
			}
		}
	});
	};

	return new orderBsService();
});
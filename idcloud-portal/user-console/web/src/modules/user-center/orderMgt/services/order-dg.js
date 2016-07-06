define(['app-utils/httpService'],function(http) {

    var orderDgService = function() {
		var me = this;

		// 获取价格
		this.getPrice = function(data){
			$("#productPrice").html("<span style='font-size: 14px;color:gray'>正在计算价格，请稍等...</span>");
			var list = me.createData(data);
			http.AjaxRequest({
				url : "/rest/billings/getPrice",
				type : "POST",
				params: list,
				showMsg:false,
			    showWaiting:true,
				callback : function(data) {
					if(data){
						setTimeout(function(){
							$("#productPrice").html("￥"+parseFloat(data).toFixed(2));
						},100);
					}else{
						setTimeout(function(){
							$("#productPrice").html("<span style='font-size: 20px'>没有定价</span>");
						},100);
					}

				}
			});
		};
		this.createData = function(orderData){
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
				if(orderData[0].orderType =="cs"){
					// 获取数据盘
					var dataDisk = [];
					for(var j=0;j<data.dataDisk.length;j++){
						var obj = new Object();
						obj.dataDiskCategory =  "cloud_ssd";
						obj.dataDiskSize = data.dataDisk[j];
						obj.dataDiskSnapshot = "";
						obj.dataDiskDevice = "";
						obj.dataDiskDeletewithinstance = "true";
						obj.dataDiskInstanceId = null
						dataDisk.push(obj);
					}
					orderObj.specifications = {
						"region": data.regionSid,
						"zone": data.zoneSid,
						"password": "",
						"hostname": "test",
						"instance": [
							{
								"instanceCategory": data.hostType,
								"cpu": data.cpu,
								"memory": data.memory
							}
						],
						"systemDisk": [
							{
								"systemDiskCategory": "cloud_efficiency",
								"systemDiskSize": data.systemDisk,
								"systemDiskDevice": "/dev/xvda"
							}
						],
						"dataDisk": dataDisk,
						"networkType": "vpc", // 网络类型
						"networks": data.subNetwork, // 子网
						"bandwidth": "0", // 带宽
						"os": {
							"imageType": data.isoType,
							"imageId": data.os
						},
						"keyPair": data.keyPair,
						"securityGroup": "ccd98605-77ba-11e5-b6e5-005056a52fbf"

					};
				}else if(orderData[0].orderType =="eip"){
					orderObj.specifications={
						"region": data.regionSid,
						"zone": data.zoneSid,
						"instance": [
							{"bandwidth":data.bandwidth}
						]
					};
				}
				orderObj.specificationsDesc =  "xxx";
				list.push(orderObj);
			}
			return list;
		};
		// 本地数据
    	this.getData = function(name){
			// 获取价格
    		var obj ;
    		if(name=="netType"){
    			obj =[{name:"私有网络",value:"私有"},{name:"经典网络",value:"公有"}];
    		}

    		return obj;
    	};
		// 初始化购物车
		this.initShoppingCart = function(){
			var shoppingDiv = '<div class="border  shopping">'+
					'<div class="bg-sub text-white shopping-title text-small"><span class="text-small">你已选购的清单(<span class="shopping-title-count text-small">'+0+'</span>台)</span></div>'+
					'<div class="shopping-content-list text-small"></div>';
			shoppingDiv += "</div>";
			$('#shoppingServer').append(shoppingDiv);
		};

		// 添加购物车信息
		this.createShoppingCartInfo = function(jsonStr){
			var shoppingdiv = '';
			var kObj = jsonStr;
			var jsonArray= kObj.data;
			var kObjTitle = kObj.title;
			var kObjCount = kObj.count;
			var count= kObj.perCount;
			var sid = kObj.id;
			shoppingdiv +='<div id="'+kObj.id+'div" class="shopping-list text-small" >';
			shoppingdiv += '<input class="perCount" type="hidden" value='+kObj.perCount+'>'
			shoppingdiv +='<div class="shopping-list-title text-small"  id="'+kObj.id+'" >'+
					'<span class="text-small"><strong>'+kObjTitle+'</strong></span>'+
					'<span class="shopping-list-right text-small">'+
					'<span class="text-small" style="margin-top:-8px;padding-top:-10px;">'+kObjCount+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
					'<span class="shopping-remove" sid="'+kObj.id+'" onclick="removeShoppingCartInfo(this)"><i class="icon-times-circle" style="color:#D6D6D6"></i></span>'+
					'</span>'+
					'</div>'+
					'<div class="text-small">';

			for(var i=0,len=jsonArray.length;i<len;i++){
				var jsonObj = jsonArray[i];
				var type = jsonObj.type;
				var name = jsonObj.name;
				var value= jsonObj.value;
				var style= jsonObj.style;
				var nameid= ""
				if(jsonObj.id!= ""&&jsonObj.id!=undefined){;
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
		};

    	//输入框加方法
        this.addDgCount = function(){
        	var countValue = parseInt($("#orderCount").val());
        	if(countValue>=99){
        		return;
        	}
        	$("#orderCount").val(countValue+1);
        };

        //输入框减方法
        this.decDgCount = function(){
        	var countValue = parseInt($("#orderCount").val());
        	if(countValue==0){
        		$("#orderCount").val(0);
        	}else{
        		$("#orderCount").val(countValue-1);
        	}
        };

        //添加数据盘
        this.addDataDisk = function(){
			var length = $(".rangeDiv").length;
        	if(parseInt(length) >= 4){
        		return;
        	}
			var random = new Date().getTime();
        	var divStr ='<div id="divRange'+random+'" class=" x12" style="margin-left:0px;padding-left:0px;"><div class="x7 text-left" style="padding-left:0px;">'+
        					'<div class="controls mt-10" style="margin-left:0px;">'+
        						'<input type="text" id="range'+random+'" class="rangeDiv" value="23" name="range"/>'+
        					'</div> '+
        				'</div> '+
        				'<div class="x5 text-small" style="margin-top:25px;padding-left:0px;margin-left:0px;" >'+
        					'<input type="text" style="width:35px;height: 22px;margin-right:0px;padding-left:5px;margin-top:1px;" class="rangevalue border border-sub text-small" maxlength="3" value="10" max="500" id="rangevalue'+random+'" onchange="changeDataSliderValue(this,\''+random+'\')"/>GB '+
        					'<span  style="padding-left:0px;margin-left:0px;margin-top:-3px;" class="text-small button-group"><button type="button" class="button dropdown-toggle radius-none button-little" style="border:0px;">自动分配挂载点<span class="downward"></span>'+
        					'</button><ul class="drop-menu pull-right"><li><a href="javascript:void(0);" class="text-small">挂载点1</a> </li><li><a href="javascript:void(0);" class="text-small">挂载点2</a> </li></ul></span>&nbsp;'+
        					'<span onclick="delSlider(\'divRange'+random+'\')"><a style="cursor: pointer" class="text-blue text-small">删除</a></span>'+
        				'</div></div>'
        	$('#divRangeData').append(divStr);
        	addSlider(random);
			length = $(".rangeDiv").length;
        	$("#dataDiskCount").text(4-parseInt(length));
        	if(4-parseInt(length) == 0){
				$("#addDiskBtn").hide();
			}else{
				$("#addDiskBtn").show();
			}
        };
    	
        //加入购物车
        this.addShopCart = function(){
        	var obj = getCloudObj();
        	var listStr='<li id="cartlist'+obj.Sid+'">'+
        		'<div class="media media-x">'+
        			'<div class="buycloudtitle">'+
        				'<div class="x4"><input type="checkbox">&nbsp;&nbsp;云主机</div>'+
        				'<div class="x2 x6-move"><span style="width: 50px;text-align: right" align="right" onclick="delShoppingCart(\''+obj.Sid+'\')"><a href="#">删除</a></span></div>'+
        			'</div>'+
        			'<div class="buyshowcontent"><label>配置:'+obj.cpu+'/'+obj.memory+'/'+obj.operatingSystem+'</label></div>'+
        			'<div class="buyshowcontent"><label>系统盘:'+obj.systemDisk+'</label></div>'+
        			'<div class="buyshowcontent"><label>数据盘:'+obj.dataDisk+'</label></div>'+
        			'<div class="buyshowcontent"><label>商务合同号:'+obj.businessContractNo+'</label></div>'+
        			'<div class="buyshowcontent"><label>项目立项号:'+obj.projectApprovalNo+'</label></div>'+
        			'<div class="buyshowcontent"><label>订购数量:'+obj.orderCount+'</label></div>'+
        		'</div>'+
        	"</li>";
        	$('#shoppingcartlist').append(listStr);
        };

    };

    //添加slider层
    function addSlider(num){
    	$("#range"+num).ionRangeSlider({
    		grid: true,
		    min: 0,
		    max: 500,
		    from: 10,
		    step: 10,
		    grid_num: 5,
			hide_min_max:true,
		    prettify_enabled: false,
		    onChange: function (data) {
		    	$("#rangevalue"+num).val(data.from);
		    },
			onFinish:function(){
				// 计算价格
				avalon.vmodels.orderDg.calculationPrice();
			}

		    
    	});
    }
    
    return new orderDgService();
});
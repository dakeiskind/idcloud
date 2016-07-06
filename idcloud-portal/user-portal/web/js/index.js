if($("#auxiliary").length>0){
	$("#auxiliary").show();
}
// 获取服务目录
Core.AjaxRequest({
	 url: ws_url + "/rest/services/serviceTree",
	 type: "post",
	 //async: false,
	 //showWaiting:false,
	 callback: function (data) {
		 console.log(data)
		 //setServiceCatalogContent(data);
		 setServiceContentTest(data);
	 }
 });
// 设置服务的内容
function setServiceContent(data){
	var index = 0;
	var div1 = "<div class='text_content '>";
	var div = "<div class='text_content' style='margin-top: 370px'>";
	var str = "";
	for(var i=0;i<data.length;i++) {
		if(data[i].serviceDefineList == ""){
		}else{
			// 判断是否是第一项
			if(index == 0){
				str+=div1;
				// 判断ul的高度
				str += "<h2 class='tab_label l_show'>"+data[i].catalogName+"</h2>";
				var height;
				if(data[i].serviceDefineList.length%3 == 0){
					height = (data[i].serviceDefineList.length/3)*332;
				}else{
					height = (Math.floor(data[i].serviceDefineList.length/3)+1)*332;
				}
				for(var j=0;j<data[i].serviceDefineList.length;j++){
					var param = 'productSid='+data[i].serviceDefineList[j].serviceSid+'&productParentSid='+data[i].serviceDefineList[j].parentCatalogSid;
					str+="<div class='product_box_s' >";
					str+="<div class='product_box_s1'><img src= "+data[i].serviceDefineList[j].bImagePath+" /></div>"
					str+="<div class='product_box_s2'>";
					str+="<h3 class='service_name '>"+data[i].serviceDefineList[j].serviceName+"</h3>";
					str+="<p class='service_details'><span style='font-size:12px;'  title="+data[i].serviceDefineList[j].description+">"+interceptionString(data[i].serviceDefineList[j].description,55)+"</span></p>";
					if(data[i].serviceDefineList[j].canOrder=="1"){
						str+="<div class='service_botton'><a href='product.html?"+param+"' id='detial' class='product_more_detial'>查看详情</a></div></div></div>";
					}
					index++;
				};
			} else{
				str+=div;
				// 判断ul的高度
				str += "<h2 class='tab_label l_show'>"+data[i].catalogName+"</h2>";
				var height;
				if(data[i].serviceDefineList.length%3 == 0){
					height = (data[i].serviceDefineList.length/3)*332;
				}else{
					height = (Math.floor(data[i].serviceDefineList.length/3)+1)*332;
				}
				for(var j=0;j<data[i].serviceDefineList.length;j++){
					var param = 'productSid='+data[i].serviceDefineList[j].serviceSid+'&productParentSid='+data[i].serviceDefineList[j].parentCatalogSid;
					str+="<div class='product_box_s' >";
					str+="<div class='product_box_s1'><img src="+data[i].serviceDefineList[j].bImagePath+" /></div>"
					str+="<div class='product_box_s2'>";
					str+="<h3 class='service_name '>"+data[i].serviceDefineList[j].serviceName+"</h3>";
					str+="<p class='service_details'><span style='font-size:12px;'  title="+data[i].serviceDefineList[j].description+">"+interceptionString(data[i].serviceDefineList[j].description,55)+"</span></p>";
					if(data[i].serviceDefineList[j].canOrder=="1"){
						str+="<div class='service_botton'><a href='product.html?"+param+"' id='detial' class='product_more_detial'>查看详情</a></div></div></div></div>";
					}
				};
			}
			str+="</div>";
		}
	}
	$("#serviceContent").append(str);
}

function setServiceContentTest(data){
	var str = "";
	for(var i=0;i<data.length;i++) {
		if(data[i].serviceDefineList == ""){
		}else {
			str += "<div class='text_content' style='width:100%;height:100%;float:left;'>"
			str += "<h2 class='tab_label'>" + data[i].catalogName + "</h2>";
			for (var j = 0; j < data[i].serviceDefineList.length; j++) {
				var param = 'productSid='+data[i].serviceDefineList[j].serviceSid+'&productParentSid='+data[i].serviceDefineList[j].parentCatalogSid;
				str += "<div class='product_box_s' >";
				str += "<div class='product_box_s1'><img src=" + data[i].serviceDefineList[j].bImagePath + " /></div>"
				str+="<div class='product_box_s2'>";
				str+="<h3 class='service_name '>"+data[i].serviceDefineList[j].serviceName+"</h3>";
				str+="<p class='service_details'><span style='font-size:12px;'  title="+data[i].serviceDefineList[j].description+">"+interceptionString(data[i].serviceDefineList[j].description,55)+"</span></p>";
				if(data[i].serviceDefineList[j].canOrder=="1"){
					str+="<div class='service_botton'><a href='product.html?"+param+"' id='detial' class='product_more_detial'>查看详情</a></div>";
				}
				str += "</div></div>";
			}
			str += "</div>";
		}
	}
	$("#serviceContent").append(str);
}

// 切换服务目录
function switchServiceCatalog(index,obj){
	$(".l_show").removeClass("l_show");
	$(obj).addClass("l_show");
	$(".text_content").hide();
	$(".text_content").eq(index).show();
}
//unComment(['slide-2','slide-3','slide-4']);
//
//// 跳转到产品页面
//function gotoProduct(productSid,productParentSid){
//    if(typeof(productSid) == 'number' && typeof(productParentSid) == 'number'){
//        var str = encodeURIComponent('productSid='+productSid+'&productParentSid='+productParentSid+'');
//        window.location = "${ctx}/pages/product/products.jsp?"+str;
//    }
//}

//$(window).load(function () {
//    addJS(ctx + "/script/slider.js");
//});

function interceptionString(str, length) {
	if (typeof (str) == "string" || typeof (length) == "number") {
		var retstr = "";
		if (str.length > length) {
			retstr = str.substring(0, length) + "...";
		} else {
			retstr = str;
		}
		return retstr;
	} else {
		return "";
	}
}

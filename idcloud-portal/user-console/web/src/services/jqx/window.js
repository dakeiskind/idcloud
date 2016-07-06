
define(["layer"], function (layer) {
	  
  	layer.config({
    	path: '../lib/layer/' 
  	});
	var service = function(){
		var me = this;

		this.initWindow = function(settings){
			// 默认属性
		  	var defaults={
				type:1, // 默认为加载页面方式
				title:"标题", // 默认标题
				url:"", // 弹出框内容url
				skin:"", // 默认皮肤
				area:['600px','300px'], // 默认自适应
				shift:2,
				shade: [0.3, '#000'], // 默认是0.3透明度的黑色背景（'#000'）,如不想要遮罩层，设置shade = 0
				moveType: 1, // 拖动方式
				closeBtn : 1,
				maxmin: false,
				btn:'',
				shadeClose:false, // 默认点击遮罩层不关闭
				confirm:null,
				callback: function(layero, index){
			         // 弹出层加载成功回调
			    }
		  	};

		  	// 合并参数对象
		  	var obj = $.extend(defaults,settings);

		  	if(obj.url == null || obj.url == 'undefined' ||obj.url == ''){
		  		return false;
		  	}

		  	// 按钮最多有2个
		  	if(obj.btn.length >2 ){
		  		var btn = new Array();
		  		btn[0] = obj.btn[0];
		  		btn[1] = obj.btn[1];
		  		obj.btn = btn;
		  	}
		  	$.get(obj.url,function(data,status){

			  		layer.open({
			            type: 1,
			            title: obj.title,
			            skin: obj.skin,
			            area: obj.area,
			            shift: obj.shift,
			            moveType: obj.moveType,
			            shadeClose: obj.shadeClose, 
			            content: data,
			            closeBtn: obj.closeBtn,
						maxmin: obj.maxmin,
						shade: obj.shade,
						btn:obj.btn,
						yes:function(index, layero){
							var isok = true;
							if(obj.confirm == null || obj.confirm == 'undefined'){
							}else{
								isok = obj.confirm();
							}
							if(isok){
								layer.close(index);
							}
						},
						cancel :function(){

						},
			            success: obj.callback
			        });
			        
		  	});
		}
	};

	return new service();
});
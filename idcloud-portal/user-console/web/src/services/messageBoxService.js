define(["layer","toastr"], function(layer,toastr) {
	 layer.config({
            path: 'lib/layer/'
     });
	 var messageBox = function(){
		   	var me = this;
		 	
	        // alert弹出框
	        this.alert = function(settings){
                var title = settings.title === undefined ? "提示" : settings.title;
                var message = settings.message === undefined ? "" : settings.message;
                var width = settings.width === undefined ? "350px" : settings.width;

                layer.alert("<i class='icon-question-circle' style='color:red;font-size:16px;'></i>&nbsp;&nbsp;"+message,
                    {btn: ['确认'], title: title,area:width},
                    function(index){
                        if(settings.alert == 'undefined'){
                        }else{
                            settings.callback();
                        }
                        layer.close(index);
                    }
                );
	        };
	        
	        // confirm 弹出框
	        this.confirm = function(settings){
	        	var title = settings.title === undefined ? "请选择" : settings.title;
    	    	var message = settings.message === undefined ? "" : settings.message;
    		    var width = settings.width === undefined ? "350px" : settings.width;
    		    var height = settings.height === undefined ? "150px" : settings.height;

        		layer.confirm("<i class='icon-question-circle' style='color:#0099d7;font-size:16px;'></i>&nbsp;&nbsp;"+message, {
                    btn: ['确认','取消'],
                    title: title ,
                    area:width
                }, function(index){
                    if(settings.confirm == 'undefined'){
          					}else{
          						settings.callback();
          					}
                    layer.close(index);
                }, function(){
                    
                });
	        };
	        
	        
	        // 通知
	        this.msgNotification = function(settings){
	        	
    	    	var message = settings.message === undefined ? "" : settings.message;
    	    	var position = settings.position === undefined ? "toast-bottom-right" : settings.position;
    	    	var type = settings.type === undefined ? "info" : settings.type;
                toastr.clear();// 清除当前列表
                toastr.options = {
                  "closeButton": true,
                  "debug": false,
                  "progressBar": true,
                  "positionClass": "toast-bottom-right",
                  "onclick": null,
                  "showDuration": "400",
                  "hideDuration": "1000",
                  "timeOut": "1000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
                }
                toastr.options.positionClass = position;
                if(type == 'info'){                  
                    toastr.info(message);
                }else if(type == 'success'){
                    toastr.success(message);
                }else if(type == 'warning'){
                    toastr.warning(message);
                }else if(type == 'error'){
                    toastr.error(message);
                }


	        }
	 };
	 
	 return new messageBox();

});
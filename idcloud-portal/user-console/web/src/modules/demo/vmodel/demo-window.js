define(['app-utils/jqx/window','app-utils/messageBoxService','app-utils/variableService'], function(window,messageBox,variable) {
	
	var testWindow = avalon.define({
		$id:'testWindow',
		editData:null,
		index:0,
		window:function(){
			window.initWindow({
                title: "+ 新增", 
                url: variable.app_modules+"/demo/views/demo-window-demo1.html",
                btn: ['保存','取消'],
                area: ['450px', '340px'],
                confirm:function(){
                    var t = avalon.vmodels.demoWindow.data;
                    alert(JSON.stringify(t));
                    return true;
                },
                callback:function(){
                	var data = [
                		{name:"jpg1",comName:"news1",industry:"2"},
                		{name:"jpg2",comName:"news2",industry:"1"},
                		{name:"jpg3",comName:"news3",industry:"3"},
                		{name:"jpg4",comName:"news4",industry:"2"}
                	];
                	testWindow.editData = data[testWindow.index];
                	testWindow.index ++;
                }
             });
		},
		confirm:function(){
			messageBox.confirm({
                message:"您确定要删除该用户吗？",
                callback:function(){
                    alert("确定删除！");
                }
            });
		},
		info:function(){
			messageBox.msgNotification({
                type:"info",
                message:"操作成功!"
            });
		},
		success:function(){
			messageBox.msgNotification({
                type:"success",
                message:"操作成功!"
            });

		},
		warning:function(){
			messageBox.msgNotification({
                type:"warning",
                message:"操作成功!"
            });
		},
		error:function(){
			messageBox.msgNotification({
                type:"error",
                message:"操作成功!"
            });
		}
	});

	return avalon.controller(function ($ctrl) {
		  	
		    $ctrl.$onEnter = function (param, rs, rj) {
		    	// 进入视图
		    	avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-window';
		    };
	    
		    $ctrl.$onRendered = function () {
		    	

		    };
	 
		    $ctrl.$onBeforeUnload = function () {
		    	 // 视图销毁前
		    };
	    	$ctrl.$vmodels = [testWindow];
   		});
	
});
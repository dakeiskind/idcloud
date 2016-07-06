var resAddRzModel = function(){	
	
	var me = this;
	
	// 判断新增的区域、数据中心是否重复
	this.getTopologyName = function(name,type){
		var Todata = null;
		Core.AjaxRequest({
 			url : ws_url + "/rest/topologys/checkTopologyRepeat/",
 			type:'POST',
 			async:false,
 			params:{
 				resTopologyName : name,
 				resTopologyType : type
 			},
 			callback : function (data) {
 				Todata = data;
 			}
 		 });
		return Todata;
	};
	
    // 验证
    this.initValidator = function(){
    	// 新增区域验证
		$('#addRzForm').jqxValidator({
	        rules: [  
	                  { input: '#add-rz-resTopologyName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
	                  { input: '#add-rz-resTopologyName', message: '资源分区名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
	                  { input: '#add-rz-resTopologyName', message: '资源分区名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	                  	  	var list = me.getTopologyName(input.val(),"RZ");
	                  	  	if(list.length > 0){
	                  	  		return false;
	                  	  	}else{
	                  	  		return true;
	                  	  	}
	                      }
		              },
	               ]
		});
		
		// 新增区域验证成功
		$('#addRzForm').on('validationSuccess', function (event) {
			var rz = Core.parseJSON($("#addRzForm").serializeJson());
			if(rz.resEnvId == ""){
				Core.alert({
					type:"error",
					message:"关联资源环境字段不能为空！"
				});
				return;
			}
			var data = rz.resEnvId.split(",");
			rz.resEnvId = data[0];
			if(data[1] != "Openstack"){
				rz.regionName = null;
			}
			Core.AjaxRequest({
				url : ws_url + "/rest/topologys/create/Rz",
				params :rz,
				callback : function (data) {
					$("#addRzWindow").jqxWindow('close');
					// 刷新基本信息
					if($("#poolRzDatagrid").length > 0){
						var rz = new poolRzDatagridModel();
						rz.searchPoolRzInfo();
					}else{
						setPoolTreeValue();
					}
				},
				failure:function(data){
					$("#addRzWindow").jqxWindow('close');
				}
			});
	     });
		
    };
    
 // 初始化弹出window
	this.initPopWindow = function(){
		// 新增区域window
		  $("#addRzWindow").jqxWindow({
		        width: 360,
		        height:240,
		        theme:currentTheme,
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#addRzCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件

		        	$("#add-rz-resTopologyName").jqxInput({placeHolder: "", height: 22, width: 180,maxLength:32, minLength: 1,theme:currentTheme});
					$("#add-rz-openstack-regionName").jqxInput({placeHolder: "", height: 22, width: 180,maxLength:32, minLength: 1,theme:currentTheme});
					$("#add-rz-description").jqxInput({placeHolder: "", height: 30, width: 200, minLength: 1,theme:currentTheme});

			    	$("#addRzSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#addRzCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
		        }
		    });
	 };
	
};

//弹出新增区域window
function popAddRzWindow(){

	var code = new codeModel({width:180,dropDownWidth:180,autoDropDownHeight:true});
	code.getCustomCode("add-rz-relation-ve","/topologys","resTopologyName","topologyValue",null,"POST",{
		findChildBySid:resTopologySid,
		resTopologyType:"VE"
	});

	if($("#add-rz-relation-ve").val() != ""){
		var data = $("#add-rz-relation-ve").val().split(",");
		$("#veType").html(data[1]);
		if("Openstack" == data[1]){
			$("#openstackZoomName").show();
		}else{
			$("#openstackZoomName").hide();
		}
	}

	// 当下拉框值发生变化时
	$("#add-rz-relation-ve").on('select', function (event){
		var args = event.args;
		if (args) {
			var item = args.item;
			var data = item.value.split(",");
			$("#veType").html("&nbsp;&nbsp;"+data[1]);
			$("#add-rz-env-type").val(data[1]);
			if("Openstack" == data[1]){
				$("#openstackZoomName").show();
			}else{
				$("#openstackZoomName").hide();
			}
		}
	});


	$("#add-rz-resTopologyName").val(null);
	$("#add-rz-description").val(null);
	
	var windowW = $(window).width(); 
  	var windowH = $(window).height(); 
  	// 设置新增类型
	$("#add-rz-resTopologyType").val("RZ");
	$("#add-rz-sortNo").val("5");
	$("#add-rz-parentTopologySid").val(resTopologySid);

  	$("#addRzWindow").jqxWindow({ position: { x: (windowW-380)/2, y: (windowH-240)/2 } });
  	$("#addRzWindow").jqxWindow('open');
}

//提交区域信息
function addRzSubmit(){
	$('#addRzForm').jqxValidator('validate');
}


// 删除数据中心或者集群
function removeRz(){
	Core.confirm({
		title:"提示",
		message: "确定要删除该<font style='color:red'>资源分区及相关资源池</font>吗？",
		confirmCallback:function(){
			Core.AjaxRequest({
 				url : ws_url + "/rest/topologys/delete/rz/"+resTopologySid+"",
 				type:"get",
 				callback : function (data) {
 					// 刷新左边的Tree
 					if($("#poolRzDatagrid").length > 0){
 						var rz = new poolRzDatagridModel();
 						rz.searchPoolRzInfo();
 					}else{
 						setPoolTreeValue();
 					}
 			    },
 			    failure:function(data){
 			    	
 			    }
 			});
		}
   });

}
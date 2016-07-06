var resBizNanotubeVmModel = function (vm) {
		var me = this;
	    this.items = ko.observableArray();
	    this.searchObj = {
	    		vmNameLike:"",
	    		status:"",
//	    		bizSid:resBizSid,
//	    		bizLevel:resBizLevel,
//	    		bizName:resBizType,
//	    		parentBizSid:resParentBizSid
		};
	    
	    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
	    this.initNanotubeVmInput = function(){
	    	// 初始化查询组件
	        $("#search-biz-nanotube-vm-name").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-nanotube-vm-ip").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
	        $("#search-biz-nanotube-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme}); 
	        
			var codesearch = new codeModel({width:100,autoDropDownHeight:true});
			codesearch.getCommonCode("search-biz-nanotube-vm-status","RES_VM_STATUS",true);
	    };
	    
	    // 查询出可纳管的虚拟机
	    this.searchNanotubeVmInfo = function(){
	    	
	    	 Core.AjaxRequest({
	 			url : ws_url + "/rest/resbiz/vm/findNanotubeVm",
	 			type:'post',
	 			params:me.searchObj,
	 			async:false,
	 			callback : function (data) {
	 				me.items(data);
	 				var sourcedatagrid ={
			              datatype: "json",
			              localdata: me.items
	 			    };
	 			    var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
	 			    $("#nanotubeableVmDatagrid").jqxGrid({source: dataAdapter});
	 			}
	 		 });
	    };
	    
	    // 初始化用户datagrid的数据源
	    this.initNanotubeVMDatagrid = function(){
	          $("#nanotubeableVmDatagrid").jqxGrid({
	              width: "99.8%",
				  theme:currentTheme,
	              columnsresize: true,
	              pageable: true, 
	              pagesize: 10, 
	              autoheight: true,
	              autowidth: false,
	              sortable: true,
	              selectionmode:"checkbox",
	              localization: gridLocalizationObj,
	              columns: [
	  	                  { text: '虚拟机名称', datafield: 'vmName'},
		                  { text: '操作系统', datafield: 'osVersion',width:150},
		                  { text: 'CPU(核)', datafield: 'cpuCores',width:60},
		                  { text: '内存(MB)', datafield: 'memorySize',width:80},
		                  { text: 'IP地址', datafield: 'vmIp',width:200},
		                  { text: '状态', datafield: 'statusName',width:60}
			                 
	              ],
	              showtoolbar: true,
	              rendertoolbar: function (toolbar) {
	                  var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
	                  var nanotubeBtn = $("<div><a id='jqxNanotubeBtn' onclick='nanotubeBizVm()'>&nbsp;&nbsp;<i class='icons-blue icon-download'></i>纳管&nbsp;&nbsp;</a></div>");
	                  toolbar.append(container);
	                  container.append(nanotubeBtn);  
	              }
	          });
	          
	          $("#nanotubeableVmDatagrid").on('rowselect', function (event) {
	    		  var args = event.args; 
	    		  var index = args.rowindex;
	    		  var data = $('#nanotubeableVmDatagrid').jqxGrid('getrowdata', index);
	    		  
    			  $("#jqxNanotubeBtn").jqxButton({ disabled: false });
		   			 
	          });
	          
	    	  $("#jqxNanotubeBtn").jqxButton({width:'60px',theme:currentTheme,height: '18px',disabled: true }); 
	    };	    
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
			$("#nanotubeVmWindow").jqxWindow({
	                width: 800, 
	                height:450,
	                resizable: true,  
	                isModal: true, 
	                autoOpen: false, 
	                theme: currentTheme,
	                cancelButton: $("#nanotubeVmCancel"), 
	                modalOpacity: 0.3,
	                initContent:function(){	                	
	                	$("#nanotubeVmCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
	                }
	         });
			
			$("#nanotubeVmChooseBizWindow").jqxWindow({
                width: 300, 
                height:146,
                resizable: true,  
                isModal: true, 
                autoOpen: false, 
                theme: currentTheme,
                cancelButton: $("#chooseBizCancel"), 
                modalOpacity: 0.3,
                initContent:function(){
                	
                	$("#chooseBizSave").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                	$("#chooseBizCancel").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
                }
			});
			
	    };    
  };
  
	/** 弹出虚拟机纳管window */
  function popNanotubeVmWindow(){
	    // 刷新下可纳管的虚拟机列表
	    var nanotube = new resBizNanotubeVmModel();
	    nanotube.searchNanotubeVmInfo();
	    // 清除之前预留的值
	    var windowW = $(window).width(); 
    	var windowH = $(window).height(); 
    	$("#nanotubeVmWindow").jqxWindow({ position: { x: (windowW-800)/2, y: (windowH-450)/2 } });
    	$("#nanotubeVmWindow").jqxWindow('open');
  };
  
  // 条件查询可纳管虚拟机
  function searchBizNanotubeVm(){
	  	var nano = new resBizNanotubeVmModel();
	  	nano.searchObj.vmNameLike = $("#search-biz-nanotube-vm-name").val();
	  	nano.searchObj.vmIp = $("#search-biz-nanotube-vm-ip").val();
	  	nano.searchObj.status = $("#search-biz-nanotube-vm-status").val()=="全部"?"":$("#search-biz-nanotube-vm-status").val();
		nano.searchNanotubeVmInfo();
  }
  
  // 弹出纳管虚拟机的业务结构
  function nanotubeBizVm(){
	    var rowindex = $('#nanotubeableVmDatagrid').jqxGrid('getselectedrowindexes');
	    var resBizVmSids = "";
		if(rowindex.length > 0){
			for(var i=0;i<rowindex.length;i++){
	   			var data = $('#nanotubeableVmDatagrid').jqxGrid('getrowdata', rowindex[i]);
	   			if(i == rowindex.length-1){
	   				// 为了查询出集群下面的存储，加上引号
	   				resBizVmSids += data.resBizVmSid;
				}else{
					resBizVmSids += data.resBizVmSid + ",";
				}
	   		}
			// 选中纳管的虚拟机的sid
			$("#biz_nanotube_resBizVmSids").val(resBizVmSids);
			// 清除下来列表框
			var codeadd = new codeModel({width:150,autoDropDownHeight:false,dropDownHeight:200,dropDownWidth:150});
//        	codeadd.getCustomCode("biz_nanotube_parentBizSid","/bizType/findSbizType","name","bizSid", false, 'POST', {'parentBizSid': 0});
        	codeadd.getCustomCode("biz_nanotube_bizSid","/mgtObj/find","name","mgtObjSid", false, 'POST', {condition:{'level': '1'}});
        	
        	$("#biz_nanotube_userSid").jqxDropDownList({
				width: 150,
				height: 25,
				displayMember: "account", 
				valueMember: "userSid",
				autoDropDownHeight :true,
				theme:"metro"
			});
        	//初始化用户下拉框
        	$("#biz_nanotube_bizSid").on('change', function (event) {
        		$("#biz_nanotube_userSid").jqxDropDownList('clear'); 
        		if($("#biz_nanotube_bizSid").val()!=null&&$("#biz_nanotube_bizSid").val()!=""){
        			Core.AjaxRequest({
        				url : ws_url + "/rest/user/findAllUsersByMgtObj/"+$("#biz_nanotube_bizSid").val(),
        				type:'get',
        				async : false,
        				callback : function(data) {
        					$("#biz_nanotube_userSid").jqxDropDownList({
        						width: 150,
        						height: 25,
        						source: data,
        						selectedIndex: 0,
        						displayMember: "account", 
        						valueMember: "userSid",
        						autoDropDownHeight :true,
        						theme:"metro"
        					});
        				}
        			});
        		}
            });
        	$("#biz_nanotube_bizSid").trigger('change');
        	var windowW = $(window).width(); 
		  	var windowH = $(window).height(); 
		  	$("#nanotubeVmChooseBizWindow").jqxWindow({ position: { x: (windowW-300)/2, y: (windowH-136)/2 } });
		  	$("#nanotubeVmChooseBizWindow").jqxWindow('open');
		}else{
			$("#jqxNanotubeBtn").jqxButton({ disabled: true });
		}
  }
 
  // 保存纳管虚拟机信息
  function submitNanotubeVmInfo(){
	  if($("#biz_nanotube_bizSid").val()==null||$("#biz_nanotube_bizSid").val()==""){
		  Core.alert({
			  title : "提示",
			  message : "请选择项目！"
		  });
		  return;
	  }else if($("#biz_nanotube_userSid").val()==null||$("#biz_nanotube_userSid").val()==""){
		  Core.alert({
			  title : "提示",
			  message : "请选择所属用户！"
		  });
		  return;
	  }else{
		  var nano = Core.parseJSON($("#nanotubeVmChooseBizForm").serializeJson());
		  Core.AjaxRequest({
				url : ws_url + "/rest/resbiz/vm/relateResBizVm",
				type:'post',
				async:false,
				params:nano,
				callback : function (data) {
					// 关闭window
					$("#nanotubeVmChooseBizWindow").jqxWindow('close');
					$("#nanotubeVmWindow").jqxWindow('close');
					// 取消选中
					$('#nanotubeableVmDatagrid').jqxGrid('clearselection');
					// 刷新列表，带着查询条件刷新
					searchBizNanotubeVm();
					searchBizVm();
				}
		    });
	  }
  }
	
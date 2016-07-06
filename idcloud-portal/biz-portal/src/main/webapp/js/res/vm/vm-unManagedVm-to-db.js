
//存放Grid传过来的虚机信息
var infoData;

var yearData=[{"id":"1","value":"1"},{"id":"2","value":"2"},{"id":"3","value":"3"},{"id":"99","value":"永久"}];
var monthData=[{"id":"1","value":"1"},{"id":"2","value":"2"},{"id":"3","value":"3"},{"id":"4","value":"4"},{"id":"5","value":"5"}
,{"id":"6","value":"6"},{"id":"7","value":"7"},{"id":"8","value":"8"},{"id":"9","value":"9"}];
// 编辑主机 
var umMgtVmToDb = function () {
	var me=this;
	this.items = ko.observableArray();
	
	// 设置修改值
	this.setUmMgtVmToDbForm = function(data){
		infoData=data;
    	$("#view-vm-name").html(data.name);
    	$("#view-vm-cpu").html(data.numCpu);
    	$("#view-vm-memory").html(data.memorySizeMB);
    	$("#view-vm-system").html(data.guestType);
    	$("#view-wm-ip").html(data.ipAddress); 
    	$("#view-vm-statusName").html(data.statusName);
    	$("#view-belong-vm").html(data.hostName);
    	
    	me.items(data.diskInfos);
    	var sourcedatagrid ={
                  datatype: "json",
                  localdata: me.items
        };
        var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);	
        $("#vmVDisksgrid").jqxGrid({source: dataAdapter});
    };
    
  //查询条件租户、用户、IP联动查询
    $('#managed-tenant').on('select', function (event){
    			var params=null;
    		    var args = event.args;
    		    if (args) {
    		    var item = args.item;
    		    var value = item.value;
    		    if(item.value!="全部"){
    		    	params={tenantSid:value};
    		    	 var usersearch = new codeModel({autoDropDownHeight : false,dropDownHeight : 185,disabled:false});
	    		    usersearch.getCustomCode("managed-user","/user/findAll","realName","account",null,"post",params);
	    		    
	    		    //获取tenant
	    		    Core.AjaxRequest({
	    	 			url : ws_url + "/rest/tenants/"+item.value,
	    	 			type:'GET',
	    	 			async:false,
	    	 			callback : function (data) {
	    	 				//根据tenant的vlanId查询未使用IP
	    	 				var ipParams={vlanId:data.vlanId,usageStatus:"02"};
	    	 				 var ipsearch = new codeModel({autoDropDownHeight : false,dropDownHeight : 185,disabled:false});
	    	 				ipsearch.getCustomCode("managed-ip","/ips","ipAddress","resSid",null,"post",ipParams);
	    	 			}
	    	 		 });
    		    }else{

					var usersearch = new codeModel({autoDropDownHeight : false,dropDownWidth : 135,dropDownHeight : 185,disabled:true});
					usersearch.getCustomCode("managed-user","/user/findAll","realName","account",true,"post",null);
					
					 var ipsearch = new codeModel({autoDropDownHeight : false,dropDownHeight : 185,disabled:true});
 	 				ipsearch.getCustomCode("managed-ip","/ips","ipAddress","resSid",true,"post",null);
    		    }
    		   
    		}                        
		});
    
    //根据购买类型，联动购买时长
    $('#managed-billingType').on('select', function (event){
    		    var args = event.args;
    		    if (args) {
    		    var item = args.item;
    		    if(item.value=="Year"){
    		    	initBuyTime(yearData);
    		    }else{
    		    	initBuyTime(monthData);
    		    }
    		}                        
		});
    
    // 初始化验证规则   
    this.initValidator = function(){
			$('#unMgtVmToDbForm').jqxValidator({
		        rules: [  
		                  { input: '#managed-tenant', message: ' 不能为空!', action: 'keyup, blur', rule: function (input, commit) {
                	  			if(input.val()=="全部" ){
                	  				return false;
                	  			}else{
                	  				return true;
                	  			}
                	  		}
		                  },
		               ]
			});
		    	
//			// 纳管虚机
			$('#unMgtVmToDbForm').on('validationSuccess', function (event) {
				
				 infoData.tenantSid=$("#managed-tenant").val();
				 infoData.ownerId=$("#managed-user").val();
				 infoData.resIpSid=$("#managed-ip").val();
				 infoData.platformVmSystem=$("#managed-vm-system").val();
				 infoData.billingType=$("#managed-billingType").val();
				 infoData.buyTimes=$("#managed-buy-time").val();
				 var vmwareInfo = infoData;
				 Core.AjaxRequest({
						url : ws_url + "/rest/resourceInstance/platform/managedVmToDb",
						params :vmwareInfo,
						callback : function (data) {
							var vmmanagedvm = new vmManagedModel();
							vmmanagedvm.searchVMInfo();
							var rowIDs = new Array();
							rowIDs.push(infoData.uid);
							$("#unMgtVmToDbWindow").jqxWindow('close');
							$('#vmUnMgtdatagrid').jqxGrid('deleterow', rowIDs);
					    },
					    failure:function(data){
					    	$("#unMgtVmToDbWindow").jqxWindow('close');
					    }
					});
		     });
    };
	 // 初始化弹出window
	this.initPopWindow = function(){
		  $("#unMgtVmToDbWindow").jqxWindow({
		        width: 800, 
		        height:420,
		        theme:currentTheme,  
		        resizable: false,  
		        isModal: true, 
		        autoOpen: false, 
		        cancelButton: $("#unMgtVmToDbCancel"), 
		        modalOpacity: 0.3,
		        initContent:function(){
		        	// 初始化新增用户页面组件
			    	$("#unMgtVmToDbSave").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	$("#unMgtVmToDbCancel").jqxButton({ width: '50',theme:currentTheme,height: '25px'});
			    	var tenantsearch = new codeModel({autoDropDownHeight : false,dropDownWidth : 155,dropDownHeight : 185});
					tenantsearch.getCustomCode("managed-tenant", "/tenants", "tenantName","tenantSid", true, "post", null);
					
					var usersearch = new codeModel({autoDropDownHeight : false,dropDownWidth : 135,dropDownHeight : 185,disabled:true});
					usersearch.getCustomCode("managed-user","/user/findAll","realName","account",true,"post",null);
					
					 var ipsearch = new codeModel({autoDropDownHeight : false,dropDownHeight : 185,disabled:true});
 	 				ipsearch.getCustomCode("managed-ip","/ips","ipAddress","resSid",true,"post",null);
			        $("#search-vm-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
			      
					var codesearch = new codeModel({autoDropDownHeight:true});
					codesearch.getCommonCode("managed-billingType","BILLING_TYPE_YM",false);
					var codesearch1 = new codeModel({autoDropDownHeight:false,dropDownWidth:320,dropDownHeight : 90});
					codesearch1.getCommonCode("managed-vm-system","OS_TYPE",false);
			    	
					initBuyTime(yearData);
		        }
		    });
	};
	
    // 初始化用户datagrid的数据源
    this.initVMDatagrid = function(){
          $("#vmVDisksgrid").jqxGrid({
              width: "100%",
              height:"90px",
			  theme:currentTheme,
              columnsresize: true,
              pageable: false, 
              autoheight: true,
              autowidth: false,
              sortable: true,
              selectionmode:"singlerow",
              localization: gridLocalizationObj,
              columns: [
                  { text: '磁盘名称', datafield: 'vDiskName'},
                  { text: '磁盘类型', datafield: 'type'},
                  { text: '磁盘大小', datafield: 'size'},
                  { text: '所属存储', datafield: 'belongDataStore'}
              ]
          });
    };
};


// 未纳入管理虚机入库
function unMgtVmToDbSave(){
	$('#unMgtVmToDbForm').jqxValidator('validate');
}

//初始化购买时长

function initBuyTime(data){
	var source ={
        datatype: "json",
        datafields: [
            { name: "id" },
            { name: "value" }
        ],
        localdata:data
    };
	var cpuDataAdapter = new $.jqx.dataAdapter(source);
	$("#managed-buy-time").jqxDropDownList({
	     source: cpuDataAdapter,
	     displayMember: "value", 
	     valueMember: "id",
	     theme:"metro",
	     selectedIndex: 0,
	     width:120,
	     autoDropDownHeight:false,
	     dropDownHeight:90
	});
}
  
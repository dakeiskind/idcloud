var softtypeTrs = 0;
//var temp;
var virtualImageAddModel = function () {
		var me = this;
		// 验证初始化
	    this.initValidator = function(){
	    	$('#findImageForm').jqxValidator({
                rules: [
                          { input: '#add-image-imageName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-image-imageName', message: '模板名称不能超过256个字符', action: 'blur', rule: 'length=1,256' },
                          { input: '#add-image-osAdmin', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-image-osAdmin', message: '管理账号不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#add-image-osPasswd', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-image-osPasswd', message: '管理密码不能超过16个字符', action: 'blur', rule: 'length=1,16' },
                          { input: '#add-image-imageSize', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                          { input: '#add-image-imageSize', message: '只能输入数字', action: 'keyup, blur', rule: function (input, commit) {
              	  			if(/[^\d]/g.test(input.val())){
                	  				return false;
                	  			}else{
                	  				return true;
                	  			}
                	  		}
                          },
                       ]
	    	});
	    };
	    
	    this.initInput = function(){
	    	$("#add-image-imageType1").jqxRadioButton({ width: 100, height: 22,checked: true,theme:currentTheme});
	        $("#addImageSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			$("#addImageCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			//$("#deleteSoftwareSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			//$("#addSoftwareSpec").jqxButton({ width: '50',height:"25",theme:currentTheme});
   			$("#add-image-imageName").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 256,theme:currentTheme});
   			$("#add-image-osAdmin").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 256,theme:currentTheme});
   			$("#add-image-osPasswd").jqxPasswordInput({placeHolder: "", height: 22, width: 200, minLength: 256,theme:currentTheme});
   			$("#add-image-imageSize").jqxInput({placeHolder: "", height: 22, width: 200, minLength: 256,theme:currentTheme});
        	var codesearch = new codeModel({width:200,autoDropDownHeight:false,dropDownWidth:200});
   			codesearch.getCommonCode("add-image-osType","OS_TYPE",false);
   			var imageOsType =  $("#add-image-osType").val();
   			codesearch.getCustomCode("add-image-osVersion","/system/getCodesByOsVersionParams","codeDisplay","codeValue",false,"POST",{parentCodeValue:imageOsType});
	    };
		
		//初始下拉列表框的联动问题
	    this.initComboxLinkage = function(){
	    	var codeadd = new codeModel({width:200,autoDropDownHeight:false,dropDownWidth:200});
	    	$('#add-image-osType').on('change', function (event){     
				    var args = event.args;
				    if (args) {
				    var item = args.item;
				    var value = item.value;
				    codeadd.getCustomCode("add-image-osVersion","/system/getCodesByOsVersionParams","codeDisplay","codeValue",false,"POST",{parentCodeValue:value});
				    me.getOsVersionValue();
				    } 
			});
	    };  
	    this.getOsVersionValue = function (){
	    	var items = $("#add-image-osVersion").jqxDropDownList('getItems');
	    	if(items.length > 0){
	    		$("#add-image-osVersion").jqxDropDownList('selectItem', items[0] );
	    	}else{
	    		$("#add-image-osVersion").jqxDropDownList({placeHolder: ""});
	    	} 
	    };

// 	    this.findSoftWare = function (oper) {
// 	    	var roleType = "";
// 	    	Core.AjaxRequest({
//  				url : ws_url + "/rest/system/findImageSoftWare/",
//  				type:"post",
//  				async:false,
//  				params :{},
//  				callback : function (data) {
//  					$("#"+oper+"-imageSoftWare-database").html("");
//  					$("#"+oper+"-imageSoftWare").html("");
//  					for(var i = 0; i < data.length; i++) {
//  						if("db"==data[i].softWareValue){
//  							var checkBoxElem = $("<div id='"+oper+"-software-database-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
//  							$("#"+oper+"-imageSoftWare-database").append(checkBoxElem);
// // 							checkBoxElem.jqxCheckBox({ width: 120, height: 15,theme:currentTheme});
//  							checkBoxElem.jqxRadioButton({ width: 120, height: 15,theme:currentTheme});
//  						}
//  						if("mw"==data[i].softWareValue){
//  							var checkBoxElem = $("<div id='"+oper+"-software-" + i + "' class='"+oper+"-softwatr' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
//  							$("#"+oper+"-imageSoftWare").append(checkBoxElem);
//  							checkBoxElem.jqxRadioButton({ width: 140, height: 15,theme:currentTheme});
//  						}
//  					}
//  			    },
//  			    failure:function(data){
//  			    }
//  			});
// 	    };
			this.findSoftWare = function (oper) {
				    	var roleType = "";
				    	Core.AjaxRequest({
			 				url : ws_url + "/rest/system/findImageSoftWare/",
			 				type:"post",
			 				async:false,
			 				params :{},
			 				callback : function (data) {
			 					$("#"+oper+"-imageSoftWare-database").html("");
			 					$("#"+oper+"-imageSoftWare").html("");
			 					for(var i = 0; i < data.length; i++) {
			 						if("db"==data[i].softWareValue){
			 							if ("oracle"==data[i].parentCodeValue) {
			 								var checkBoxElemoracle = $("<div id='"+oper+"-software-database-oracle-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare-database").append(checkBoxElemoracle);
			 								checkBoxElemoracle.jqxRadioButton({ width: 120, height: 15,groupName :"oracle",theme:currentTheme});
			 							}
			 							if ("db2"==data[i].parentCodeValue) {
			 								var checkBoxElemdb2 = $("<div id='"+oper+"-software-database-db2-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare-database").append(checkBoxElemdb2);
			 								checkBoxElemdb2.jqxRadioButton({ width: 120, height: 15,groupName :"db2",theme:currentTheme});
			 							}
			 							if ("sybase"==data[i].parentCodeValue) {
			 								var checkBoxElemsybase = $("<div id='"+oper+"-software-database-sybase-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare-database").append(checkBoxElemsybase);
			 								checkBoxElemsybase.jqxRadioButton({ width: 120, height: 15,groupName :"sybase",theme:currentTheme});
			 							}
			 							if ("sqlserver"==data[i].parentCodeValue) {
			 								var checkBoxElemsqlserver = $("<div id='"+oper+"-software-database-sqlserver-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare-database").append(checkBoxElemsqlserver);
			 								checkBoxElemsqlserver.jqxRadioButton({ width: 120, height: 15,groupName :"sqlserver",theme:currentTheme});
			 							}
			 						}
			 						if("mw"==data[i].softWareValue){
			 							if ("was"==data[i].parentCodeValue) {
			 								var checkBoxElemwas = $("<div id='"+oper+"-software-was-" + i + "' class='"+oper+"-softwatr' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare").append(checkBoxElemwas);
			 								checkBoxElemwas.jqxRadioButton({ width: 120, height: 15,groupName :"was",theme:currentTheme});
			 							}
			 							if ("ctg"==data[i].parentCodeValue) {
			 								var checkBoxElemctg = $("<div id='"+oper+"-software-ctg-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare").append(checkBoxElemctg);
			 								checkBoxElemctg.jqxRadioButton({ width: 120, height: 15,groupName :"ctg",theme:currentTheme});
			 							}
			 							if ("weblogic"==data[i].parentCodeValue) {
			 								var checkBoxElemweblogic = $("<div id='"+oper+"-software-weblogic-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 								$("#"+oper+"-imageSoftWare").append(checkBoxElemweblogic);
			 								checkBoxElemweblogic.jqxRadioButton({ width: 120, height: 15,groupName :"weblogic",theme:currentTheme});
			 							}
			 							
			 							// var checkBoxElem = $("<div id='"+oper+"-software-" + i + "' class='"+oper+"-softwatr' data='"+ data[i].codeValue +"' style='float: left;margin-top:10px;'>"+data[i].codeDisplay+"</div>");
			 							// $("#"+oper+"-imageSoftWare").append(checkBoxElem);
			 							// checkBoxElem.jqxRadioButton({ width: 140, height: 15,theme:currentTheme});
			 						}
			 					}
			 			    },
			 			    failure:function(data){
			 			    }
			 			});
				    };
	    this.findVe = function (oper) {
	    	var roleType = "";
	    	var resTopologyType = "VE"
	    	Core.AjaxRequest({
 				url : ws_url + "/rest/topology/findResourceTopologyByVe/"+resTopologyType,
 				type:"get",
 				async:false,
 				callback : function (data) {
 					$("#"+oper+"-image-resve").html("");
 					for(var i = 0; i < data.length; i++) {
						var checkBoxElem = $("<div id='"+oper+"-image-resve-" + i + "' class='"+oper+"-softwatr-database' data='"+ data[i].resTopologySid +"' style='float: left;margin-top:10px;'>"+data[i].resTopologyName+"</div>");
						$("#"+oper+"-image-resve").append(checkBoxElem);
						checkBoxElem.jqxCheckBox({ width: 180, height: 15,theme:currentTheme});
 					}
 					
 			    },
 			    failure:function(data){
 			    }
 			});
	    };
	    
	    // 初始化弹出window
	    this.initPopWindow = function(){
	    	$("#findImageWindow").jqxWindow({
                width: 700, 
                height:480,
                resizable: false,  
                isModal: true, 
                autoOpen: false, 
                cancelButton: $("#addImageCancel"), 
                theme: currentTheme,
                modalOpacity: 0.3,
                initContent:function(){
                	 // 初始化中间件和数据库选项卡
                	me.findSoftWare('add');
                	var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
                	if(rowindex >= 0){
                		var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
        				if(""!=data.installedSoftware && data.installedSoftware!=null){
        					var softwatrs = data.installedSoftware.split(",");
            				for(var j=0;j<softwatrs.length;j++){
            					Core.AjaxRequest({
            						url : ws_url + "/rest/system/findImageSoftWareTypeByCodeValue/"+softwatrs[j],
					 				type:"get",
					 				async:false,
					 				callback : function (softtypedata) {
					 					if ("db"==softtypedata[0].parentCodeValue) {
				 						var checkbox = $("#add-imageSoftWare-database").find('[id^=add-software-database-][data='+'"'+softwatrs[j]+'"'+']');
			           		    		if(checkbox && checkbox.length > 0) {
			           		    			checkbox.jqxRadioButton('check');
			           		    			checkbox.val(true);
			           		    		}
				 					}
					 					if ("mw"==softtypedata[0].parentCodeValue) {
					 						var checkbox = $("#add-imageSoftWare").find('[id^=add-software-][data='+'"'+softwatrs[j]+'"'+']');
				           		    		if(checkbox && checkbox.length > 0) {
				           		    			checkbox.jqxRadioButton('check');
				           		    			checkbox.val(true);
				           		    		}
					 					}
					 				}
            					});
            				}
        				}
                	}
                	 // 初始化可选资源环境
                	me.findVe('add');
                	var rowindex = $('#virtualImageDatagrid').jqxGrid('getselectedrowindex');
                	if(rowindex >= 0){
                		var data = $('#virtualImageDatagrid').jqxGrid('getrowdata', rowindex);
           		    	var roles = data.resVeSid;
           		    	var resVeSid = roles.split(",");
           		    	for(var i = 0; i < resVeSid.length; i++) {
           		    		var checkbox = $("#add-image-resve").find('[id^=add-image-resve][data='+ resVeSid[i]+']');
           		    		if(i==resVeSid.length-1){
           		    			checkbox.jqxCheckBox({disabled:true });
           		    		}
           		    		if(checkbox && checkbox.length > 0) {
           		    			checkbox.jqxCheckBox('check');
           		    			checkbox.val(true);
           		    		}
           		    	}
                	}
//                	 // 初始化可选资源环境
//                	me.findVe('add');
                }
             });
	    };
	    this.setImageBasicInfo = function(data){
	  	  $("#add-image-imageName").val(data);
	    };
	    this.setImageUUIDInfo = function(data){
	    	$("#uuid").val(data);
	    };
	    this.setImageAdminInfo = function(data){
	    	$("#add-image-osAdmin").val(data);
	    };
	    this.setImagePasswdInfo = function(data){
	    	$("#add-image-osPasswd").val(data);
	    };
	    this.setImageOsTypeInfo = function(data){
	    	$("#add-image-osType").val(data);
	    };
	    this.setImageOsVersionInfo = function(data){
	    	$("#add-image-osVersion").val(data);
	    };
	    this.setImageResVeSidInfo = function(data){
	    	$("#add-image-resVeSid").val(data);
	    };
	    this.setImageSizeInfo = function(data){
	    	$("#add-image-imageSize").val(data);
	    };
	   
	   
  };

  function confirmVirtualInfo(){
	     var isOk = $('#findImageForm').jqxValidator('validate');
		 var virtual =  JSON.parse($("#findImageForm").serializeJson());
		 var osTypesid = $("#add-image-osType").jqxDropDownList('getItems');
		 var osVersionsid = $("#add-image-osVersion").jqxDropDownList('getItems');
		 var roles = $("#add-imageSoftWare").find('[id^=add-software]');
		 var roledb = $("#add-imageSoftWare-database").find('[id^=add-software-database]');
		 var veSid= $("#add-image-resve").find('[id^=add-image-resve]');
		 var installedSoftware = [];
		 var resVeSids = [];
		 
		 roledb.each(function () {
			 if($(this).find("input[name^=add-software-database]").val() == "true") {
				 installedSoftware.push($(this).attr("data"));
			 }
		 });
		 roles.each(function () {
			 if($(this).find("input[name^=add-software]").val() == "true") {
				 installedSoftware.push($(this).attr("data"));
			 }
		 });
		 veSid.each(function () {
			 if($(this).find("input[name^=add-image-resve]").val() == "true") {
				 resVeSids.push($(this).attr("data"));
			 }
		 });
		 virtual['installedSoftware'] = installedSoftware.join(",");
		 virtual['resVeSids'] = resVeSids.join(",");
		 
		// console.log(JSON.stringify(virtual));
//		 var str = "";
//		 for(var js2 in virtual){
//			 if(js2.indexOf("softwareVersion")!=-1){
//				 str += virtual[js2] + ",";
//			 }
//		 }
//		 if (str.length > 0) {
//		        str = str.substr(0, str.length - 1);
//		 }
//		 virtual['installedSoftware'] = str;
//		 for(var js3 in virtual){
//			 if(js3.indexOf("softwareVersion")!=-1 ||js3.indexOf("softwareType")!=-1){
//				 delete virtual[js3];  
//			 }
//		 }
		  if(isOk){
			  if(virtual.osType =='全部'){
				  Core.confirm({
					  title:"提示",
					  message:"请选择操作系统类型"
				  });
				  return false;
			  }else
			  if(virtual.osVersion =='全部'){
				  Core.confirm({
					  title:"提示",
					  message:"请选择操作系统版本"
				  });
				  return false;
			  }
			  Core.AjaxRequest({
					url : ws_url + "/rest/templates/update",
					params :virtual,
					callback : function (data) {
//						 var image = new virtualImageDatagridModel();
//						 image.searchVirtualImageInfo();
						searchImage();
						 $("#findImageWindow").jqxWindow('close');
						
				    }
			  });
		  }
  }
  
  // 关闭新增资源环境窗口
  function closeAddVirtualWindow(){
	  $('#findImageWindow').jqxWindow('close');
  } 
  
//  function createSoftWarekTr(){
//	  var html="";
//	  html="<tr>"
//		+
//		"<td align='right'>部署软件类型:</td>"+
//		"<td align='left'>"+ "<div name='softwaretype' data-name='softwareType"+softtypeTrs+"' id='add-software-type"+softtypeTrs+"'></div>"+"</td>"
//		+
//		"<td align='right'>部署软件版本:</td>"+
//		"<td align='left'>"+"<div data-name='softwareVersion"+softtypeTrs+"' id='add-software-osVersion"+softtypeTrs+"'></div>"
//		+
//		"<td align='right'><input id='deleteSofts"+softtypeTrs+"'"+"type='button' onclick='deleteSoft(this)' value='删除'/></td>"+
//		"</tr>";
//	  $("#softwareSpecTrs").append(html);
//	  $("#deleteSofts"+softtypeTrs).jqxButton({ width : '80', height : "25", theme : currentTheme });
//	  var codesearch1 = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
//	  var codeadd1 = new codeModel({width:150,autoDropDownHeight:false,dropDownWidth:150});
//	  codesearch1.getCommonCode("add-software-type"+softtypeTrs,"SOFTWARE_TYPE",false);
//	 
//	  var softwareType =  $("#add-software-type"+softtypeTrs).val();
//	  codesearch1.getCustomCode("add-software-osVersion"+softtypeTrs,"/system/getCodeByParams","codeDisplay","codeValue",false,"POST",{parentCodeValue:softwareType});
//	  var softwareVersion =  $("#add-software-osVersion"+softtypeTrs).val();
//	  var temp = softtypeTrs;
//	  $("#add-software-type"+temp).on('change', function (event){  
//		    var id = event.owner.id
//		    var idnum = id.substr(id.length-1,1);
//		    var args = event.args;
//		    if (args) {
//		    var item = args.item;
//		    var value = item.value;
//		    codeadd1.getCustomCode("add-software-osVersion"+idnum,"/system/getCodeByParams","codeDisplay","codeValue",true,"POST",{parentCodeValue:value});
//		    var softwareType =  $("#add-software-osVersion"+idnum).val();
//		    var items = $("#add-software-osVersion"+idnum).jqxDropDownList('getItems');
//	    	if(items.length > 0){
//	    		$("#add-software-osVersion"+idnum).jqxDropDownList('selectItem', items[0] );
//	    	}else{
//	    		$("#add-software-osVersion"+idnum).jqxDropDownList({placeHolder: ""});
//	    	} 
//		    } 
//	  });
//	    softtypeTrs++;
//	}
//  	function deleteSoft(obj){
//  		var tr = $(obj).parents("tr")[0];
//  		$(tr).remove();
//  	}
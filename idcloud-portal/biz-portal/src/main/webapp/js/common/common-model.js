
/**
 * 获取数据字典的方法
 */
var codeModel = function(options){
	var defaultVal = {
        	selectedIndex: 0, 
        	displayMember: "codeDisplay", 
        	valueMember: "codeValue", 
        	width: 120, 
        	height: 22,
        	autoDropDownHeight : true,
        	dropDownWidth : 120,
        	dropDownHeight : 120,
        	itemHeight: 22,
        	disabled:false,
        	isEmpty:false,
        	isCheckboxes:false,
        	label:"全部"
    };
    var obj = $.extend(defaultVal, options);
    
    this.isEmpty = function (){
    	return obj.isEmpty;
    };

    // 获取数据字典表方法
    this.getCommonCode =function(id,codeName,nullItem){
    	Core.AjaxRequest({
			url :ws_url + "/rest/system/"+codeName, 
			type:"get",
			async:false,
			callback : function (data) {
				var source =
		         {
		             datatype: "json",
		             datafields: [
		                 { name: 'codeValue' },
		                 { name: 'codeDisplay' }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 
				 $("#"+id+"").jqxDropDownList({
					   placeHolder:"全部",
	                   selectedIndex: obj.selectedIndex, 
	                   source: dataAdapter,
	                   displayMember: obj.displayMember, 
	                   valueMember: obj.valueMember,
	                   width: obj.width,
	                   height: obj.height,
	                   itemHeight:obj.itemHeight,
	                   autoDropDownHeight : obj.autoDropDownHeight,
	                   dropDownHeight : obj.dropDownHeight,
	                   disabled:obj.disabled,
	                   theme:"metro"
	             });
				 // 判断是否显示空白行
				 if(typeof(nullItem) == "boolean"&& nullItem ){
					 $("#"+id+"").jqxDropDownList('insertAt', { label:obj.label ,value:""},0);
				 }
	        } 
	     });
    };
    // 自定义数据字典方法
    this.getCustomCode =function(id,url,fieldText,valueText,nullItem,methodType,params){
    	Core.AjaxRequest({
			url :ws_url +"/rest"+ url, 
			type:methodType = null ? "get":methodType,
			params:params = null ? "":params,
			async:false,
			callback : function (data) {
				var source ={
		             datatype: "json",
		             datafields: [
		                 { name: fieldText },
		                 { name: valueText }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 $("#"+id+"").jqxDropDownList({
					   placeHolder: "请选择...",
					   checkboxes: obj.isCheckboxes,
	                   selectedIndex: obj.selectedIndex, 
	                   source: dataAdapter,
	                   displayMember: fieldText, 
	                   valueMember: valueText,
	                   width: obj.width,
	                   height: obj.height,
	                   itemHeight:obj.itemHeight,
	                   autoDropDownHeight :obj.autoDropDownHeight,
	                   dropDownWidth :obj.dropDownWidth,
	                   dropDownHeight :obj.dropDownHeight,
	                   disabled:obj.disabled,
	                   theme:"metro"
	             });
				// 判断是否显示空白行
				 if(typeof(nullItem) == "boolean"&& nullItem ){
					 $("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				 }
	        } 
	     });
    };

	//根据data数据生成下拉框
	this.initSelectByData =function(id,data,fieldText,valueText,nullItem,nullItemLabel,nullItemValue){
		var source =
		{
			datatype: "json",
			datafields: [
				{ name: fieldText },
				{ name: valueText }
			],
			localdata:data
		};
		var dataAdapter = new $.jqx.dataAdapter(source);
		$("#"+id+"").jqxDropDownList({
			placeHolder:obj.placeHolder,
			selectedIndex: obj.selectedIndex,
			source: dataAdapter,
			displayMember: fieldText,
			valueMember: valueText,
			width: obj.width,
			height: obj.height,
			autoDropDownHeight :obj.autoDropDownHeight,
			dropDownWidth :obj.dropDownWidth,
			dropDownHeight :obj.dropDownHeight,
			disabled:obj.disabled,
//               filterable:me.obj.filterable,
			checkboxes: obj.checkboxes,
			theme:currentTheme
		});
		// 判断是否显示空白行
		//if(typeof(nullItem) == "boolean"&& nullItem ){
		//	nullItemLabel = nullItemLabel === undefined ? $.i18n.prop('input.all') : nullItemLabel;
		//	nullItemValue = nullItemValue === undefined ? "" : nullItemValue;
		//	$("#"+id+"").jqxDropDownList('insertAt', { label: nullItemLabel, value:nullItemValue},0);
		//}

		if(data.length==0){
			$("#"+id+"").jqxDropDownList('insertAt', { label:" " ,value:"0"},0);
		}
		$("#"+id+"").jqxDropDownList('selectIndex', 0);
	};

    // 自定义数据字典方法
    this.getCustomCode1 =function(id,url,fieldText,valueText,nullItem,methodType,params,selected){
    	Core.AjaxRequest({
			url :ws_url +"/rest"+ url, 
			type:methodType = null ? "get":methodType,
			params:params = null ? "":params,
			async:false,
			callback : function (data) {
				var source ={
		             datatype: "json",
		             datafields: [
		                 { name: fieldText },
		                 { name: valueText }
		             ],
		             localdata:data
		         };
				
				var settings ={
						message: "没有可以选择的资源，请检查后再试",
				};
				
				if (data.length < 1) {
//					Core.alert(settings);
					obj.isEmpty = true;	
					return;
				}
				else {
					obj.isEmpty = false;
				}
				
				var i = 0;
				$.each(data, function (i, row) {
					if (row.resSetType == selected) {
						obj.selectedIndex = i;
						return false;
					}	
				});
				
				
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 $("#"+id+"").jqxDropDownList({
					   placeHolder: "请选择...",
	                   selectedIndex: obj.selectedIndex, 
	                   source: dataAdapter,
	                   displayMember: fieldText, 
	                   valueMember: valueText,
	                   width: obj.width,
	                   height: obj.height,
	                   itemHeight:obj.itemHeight,
	                   autoDropDownHeight :obj.autoDropDownHeight,
	                   dropDownWidth :obj.dropDownWidth,
	                   dropDownHeight :obj.dropDownHeight,
	                   disabled:obj.disabled,
	                   theme:"metro"
	             });
				// 判断是否显示空白行
				 if(typeof(nullItem) == "boolean"&& nullItem ){
					 $("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				 }
	        } 
	     });
    };
    
    // 条件查询数据字典表
    this.getCommonCodeByConditions =function(id,nullItem,params){
    	Core.AjaxRequest({
			url :ws_url + "/rest/system/getCodeByParams", 
			type:"post",
			params:params,
			async:false,
			callback : function (data) {
				var source =
		         {
		             datatype: "json",
		             datafields: [
		                 { name: 'codeValue' },
		                 { name: 'codeDisplay' }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 
				 $("#"+id+"").jqxDropDownList({
					   placeHolder:"全部",
	                   selectedIndex: obj.selectedIndex, 
	                   source: dataAdapter,
	                   displayMember: obj.displayMember, 
	                   valueMember: obj.valueMember,
	                   width: obj.width,
	                   height: obj.height,
	                   itemHeight:obj.itemHeight,
	                   autoDropDownHeight : obj.autoDropDownHeight,
	                   dropDownHeight :obj.dropDownHeight,
	                   theme:"metro"
	             });
				 // 判断是否显示空白行
				 if(typeof(nullItem) == "boolean"&& nullItem ){
					 $("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				 }
	        } 
	     });
    };
    
    
    // 获取数据字典表Attribute1的值
    this.getAttribute1CommonCode =function(id,codeName,nullItem){
    	Core.AjaxRequest({
			url :ws_url + "/rest/system/"+codeName, 
			type:"get",
			callback : function (data) {
				var source =
		         {
		             datatype: "json",
		             datafields: [
		                 { name: 'attribute1' },
		                 { name: 'codeDisplay' }
		             ],
		             localdata:data
		         };
				 var dataAdapter = new $.jqx.dataAdapter(source);
				 
				 $("#"+id+"").jqxDropDownList({
					   placeHolder:"全部",
	                   selectedIndex: obj.selectedIndex, 
	                   source: dataAdapter,
	                   displayMember: obj.displayMember, 
	                   valueMember: "attribute1",
	                   width: obj.width,
	                   height: obj.height,
	                   itemHeight:obj.itemHeight,
	                   autoDropDownHeight : obj.autoDropDownHeight,
	                   theme:"metro"
	             });
				 // 判断是否显示空白行
				 if(typeof(nullItem) == "boolean"&& nullItem ){
					 $("#"+id+"").jqxDropDownList('insertAt', { label:"全部" ,value:""},0);
				 }
	        } 
	     });
    };
    
};
//筛选json数据
function FilterJsonData(data,name,value,type){
	if(value=="" || value==null){
		return data;
	}
	var type = type === undefined ? true : type;
	var arr=[];
	for(var i=0;i<data.length;i++){
		var x = data[i];
		var AAA = (x[name]+"").toLowerCase();
		var BBB = value.toLowerCase();
		if(type){
			if(AAA==BBB || BBB.indexOf(","+AAA+",")>-1){
				arr.push(x);
			}
		}else{
			if(!(AAA==BBB || BBB.indexOf(","+AAA+",")>-1)){
				arr.push(x);
			}
		}
	}
	if(arr.length==0){
		arr = {};
	}
	return arr;
}

//数据字典数据
if(codeData==undefined){
	var codeData = [];
}

//获取数据字典数据
function getCodeData(codeName){
	if(codeData.length==0){
		codeData = {};
	}
	var codeNameAry = codeName.split(",");
	for(var i=0;i<codeNameAry.length;i++){
		codeData[codeNameAry[i]]=[];
	}
	Core.AjaxRequest({
		url :ws_url + "/rest/system/"+codeName,
		async:false,
		type :"GET",
		callback : function (data) {
			for(var i=0;i<data.length;i++){
				codeData[jQuery.trim(data[i].codeCategory)].push(data[i]);
			}
		}
	});
}

/**
 * 根据codeCategory取相应代码及名称的基对象
 * 样例1
 * 根据categoryCode "OS" 从ws服务里读出编码数据
 * var osCodeAndName=$.extend({codeCategory:'OS'},vrCodeAndName);
 * 样例2
 * 第一对象为目标对象，第二个对象为基对象，第三个对象为扩展的对象
 * var basicStatusCodeAndName=$.extend({},vrCodeAndName,
 {datas:[{codeValue:0,codeDisplay:'创建中'},{codeValue:1,codeDisplay:'已创建'},{codeValue:2,codeDisplay:'创建失败'},{codeValue:3,codeDisplay:'已释放'}]});
 **/
var vrCodeAndName={
	getNameByCode:function(code){
		if (code !== null && code !== '') {
			if (this.getDatas() != null) {
				for (var i = 0; i < this.datas.length; i++) {

					if (code == this.datas[i].codeValue) {
						return this.datas[i].codeDisplay;
					}
				}
			}
		}
		return '';
	},
	dataadapter:null,
	getDataAdapter:function(){
		var sup=this;
		if(this.dataadapter==null){
			var source = {
				datatype : "json",
				datafields : [ {
					name : 'codeValue'
				}, {
					name : 'codeDisplay'
				} ],
				url : ws_url + "/rest/system/"+this.codeCategory
			};
			this.dataadapter = new $.jqx.dataAdapter(source,{async :false,
				loadComplete:function(){
					sup.datas = sup.dataadapter.records;
				}});
			this.dataadapter.dataBind();
		}
		return this.dataadapter;
	},
	datas:null,
	getDatas:function(){
		if(this.datas==null)
			this.getDataAdapter();
		return this.datas;
	}
};

var dateRender=function (rowIndex, columnfield, value, defaulthtml, columnproperties) {
	if(value!=null&&value!=''){
		var html='<div style="overflow: hidden;  text-overflow: ellipsis; padding-bottom: 2px; text-align: center; margin-right: 2px; margin-left: 4px; margin-top: 4px;">'+value.substring(0,10)+'</div>';
		return html;
	}else{
		return '';
	}
};
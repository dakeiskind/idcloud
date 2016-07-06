<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/pages/common/taglibs.jsp"%>
		<%@ include file="/pages/common/resources.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${ctx}/js/common/common-model.js"></script>
		<style type="text/css">
			
		</style>   
</head>
<body>
   <div style="width:100%;height:30px;padding:5px 0px 5px 0px;">
           <table border="0" style="margin-left:10px" width="900px" height="100%" cellspacing="0" cellpadding="2">
            	<tr>
            		<td align="left" width="60px" nowrap="nowrap">时间类型：</td>
           			<td width="85px"><div id="search-st-time-type" ></div></td>
           			<td align="center" width="40px" nowrap="nowrap">年份：</td>
           			<td id="st-dateTime" width="auto">
           			      
           			</td>
           			<td align="center" width="70px"><a onclick="searchClassifyStCount()" id="search-st-classify-button"><i class='icons-blue icon-search-4'></i>查询&nbsp;</a></td>
           			
           			<td align="right">
           			    <a style="margin-right:-5px;"id="search-st-report-word-button" onclick="downloadFile(this,'word')"><i class='icons-blue icon-download-6'></i>Word下载</a>
						<a style="margin-right:-5px;"id="search-st-report-pdf-button" onclick="downloadFile(this,'pdf')"><i class='icons-blue icon-download-6'></i>PDF下载</a>
						<a id="search-st-report-excel-button" onclick="downloadFile(this,'excel')"><i class='icons-blue icon-download-6'></i>Excel下载</a>
					</td>
            	</tr>
            </table>
   </div>
   <div style="width:900px;height:93%;margin-left:10px;">
		<iframe id="stClassifyCountIframe" src="" width="100%" height="100%" frameborder="no" border="1" style="border:1px solid #E5E5E5" marginwidth="0" marginheight="0"></iframe>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		//初始化组件
		initInput();
		initDropdownComponent();
		// 初始化inframe
		// 默认按年
		var startTime = $("#search-st-year-start-time").val();
		var endTime = $("#search-st-year-end-time").val();
		var objSearch = {
			timeType : 'year',
			startYearTime : startTime,
			startEndTime : endTime
		};
		
		var json = JSON.stringify(objSearch);
		
		$("#stClassifyCountIframe").attr("src",ws_url + "/rest/reports/st/jasper/storageClassifyCount/"+json).load(function() { 
			if($("#stClassifyCountIframe").contents().find("#_blank").length > 0){
				$("#search-st-report-pdf-button").jqxButton({disabled:true});
				$("#search-st-report-excel-button").jqxButton({disabled:true});
				$("#search-st-report-word-button").jqxButton({disabled:true});
			}else{
				$("#search-st-report-pdf-button").jqxButton({disabled:false});
				$("#search-st-report-excel-button").jqxButton({disabled:false});
				$("#search-st-report-word-button").jqxButton({disabled:false});
			}
		});
	});
	
	//初始化组件
	function initInput(){
		$("#search-st-classify-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
		$("#search-st-report-pdf-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-st-report-excel-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
		$("#search-st-report-word-button").jqxButton({ width: '80px',height:'25px',theme:currentTheme});
	}

    // 下载文件
	function downloadFile(obj,type){
		var isok = $(obj).jqxButton("disabled");
		if(!isok){
			
			var timeType = $("#search-st-time-type").val();
			if("year" == timeType){
				var startTime = $("#search-st-year-start-time").val();
				var endTime = $("#search-st-year-end-time").val();
				var objSearch = {
					downloadType : type,
					timeType : timeType,
					startYearTime : startTime,
					startEndTime : endTime
				};
				var json = JSON.stringify(objSearch);
				window.location = ws_url + "/rest/reports/st/export/jasper/storageClassifyCount/"+json; 
				
			}else if("quarter" == timeType){
				var yearTime = $("#search-st-quarter-year-time").val();
				var objSearch = {
					downloadType : type,	
					timeType : timeType,
					quarterYearTime : yearTime
				};
				
				var json = JSON.stringify(objSearch);
				window.location = ws_url + "/rest/reports/st/export/jasper/storageClassifyCount/"+json; 
				
			}else if("month" == timeType){
				var yearTime = $("#search-st-month-year-time").val();
				var startMonthTime = $("#search-st-month-start-time").val();
				var endMonthTime = $("#search-st-month-end-time").val();
				var objSearch = {
					downloadType : type,
					timeType : timeType,
					monthYearTime : yearTime,
					monthStartTime : startMonthTime,
					monthEndTime : endMonthTime
				};
				
				var json = JSON.stringify(objSearch);
				window.location = ws_url + "/rest/reports/st/export/jasper/storageClassifyCount/"+json; 
				
			}
		}
		
	}
    
    // 查询
    function searchClassifyStCount(){
    	var type = $("#search-st-time-type").val();
		if("year" == type){
			var startTime = $("#search-st-year-start-time").val();
			var endTime = $("#search-st-year-end-time").val();
			var objSearch = {
				timeType : type,
				startYearTime : startTime,
				startEndTime : endTime
			};
			
			var json = JSON.stringify(objSearch);
			
			$("#stClassifyCountIframe").attr("src",ws_url + "/rest/reports/st/jasper/storageClassifyCount/"+json).load(function() { 
				if($("#stClassifyCountIframe").contents().find("#_blank").length > 0){
					$("#search-st-report-pdf-button").jqxButton({disabled:true});
					$("#search-st-report-excel-button").jqxButton({disabled:true});
					$("#search-st-report-word-button").jqxButton({disabled:true});
				}else{
					$("#search-st-report-pdf-button").jqxButton({disabled:false});
					$("#search-st-report-excel-button").jqxButton({disabled:false});
					$("#search-st-report-word-button").jqxButton({disabled:false});
				}
			});
			
		}else if("quarter" == type){
			var yearTime = $("#search-st-quarter-year-time").val();
			var objSearch = {
				timeType : type,
				quarterYearTime : yearTime
			};
			
			var json = JSON.stringify(objSearch);
			
			$("#stClassifyCountIframe").attr("src",ws_url + "/rest/reports/st/jasper/storageClassifyCount/"+json).load(function() { 
				if($("#stClassifyCountIframe").contents().find("#_blank").length > 0){
					$("#search-st-report-pdf-button").jqxButton({disabled:true});
					$("#search-st-report-excel-button").jqxButton({disabled:true});
					$("#search-st-report-word-button").jqxButton({disabled:true});
				}else{
					$("#search-st-report-pdf-button").jqxButton({disabled:false});
					$("#search-st-report-excel-button").jqxButton({disabled:false});
					$("#search-st-report-word-button").jqxButton({disabled:false});
				}
			});
			
		}else if("month" == type){
			var yearTime = $("#search-st-month-year-time").val();
			var startMonthTime = $("#search-st-month-start-time").val();
			var endMonthTime = $("#search-st-month-end-time").val();
			var objSearch = {
				timeType : type,
				monthYearTime : yearTime,
				monthStartTime : startMonthTime,
				monthEndTime : endMonthTime
			};
			
			var json = JSON.stringify(objSearch);
			
			$("#stClassifyCountIframe").attr("src",ws_url + "/rest/reports/st/jasper/storageClassifyCount/"+json).load(function() { 
				if($("#stClassifyCountIframe").contents().find("#_blank").length > 0){
					$("#search-st-report-pdf-button").jqxButton({disabled:true});
					$("#search-st-report-excel-button").jqxButton({disabled:true});
					$("#search-st-report-word-button").jqxButton({disabled:true});
				}else{
					$("#search-st-report-pdf-button").jqxButton({disabled:false});
					$("#search-st-report-excel-button").jqxButton({disabled:false});
					$("#search-st-report-word-button").jqxButton({disabled:false});
				}
			});
			
		}
		
    }
    
    // 初始化下拉框组件
    function initDropdownComponent(){
    	// 时间类型数据源
    	var timeType = [
    	    {name:"按年",value:"year"},
    	    {name:"按季",value:"quarter"},
    	    {name:"按月",value:"month"}
    	];
    	
    	var timeTypeSource ={
            datatype: "json",
            datafields: [
                { name: 'name' },
                { name: 'value' }
            ],
            localData: timeType
        };
        var dataTimeTypeAdapter = new $.jqx.dataAdapter(timeTypeSource);
        $("#search-st-time-type").jqxDropDownList({
            selectedIndex: 0, 
            source: dataTimeTypeAdapter, 
            displayMember: "name", 
            valueMember: "value", 
            dropDownHeight: 95,
            theme:currentTheme,
            width: 70, 
            height: 25
        });
        
        initSpecificTimeComponent("year");
        
        $("#search-st-time-type").on('select', function (event) {
            if (event.args) {
                var item = event.args.item;
                if (item) {
                	initSpecificTimeComponent(item.value);
                }
            }
        });
    }
    
    // 具体时间下拉框初始化
    function initSpecificTimeComponent(type){
    	
 		var quarterStr = '<div id="search-st-quarter-year-time" style="float:left"></div>&nbsp;&nbsp;';
 		
 	    var monthStr = '<div id="search-st-month-year-time" style="float:left"></div>&nbsp;&nbsp;'+
 	    			   '<div style="height:25px;line-height:25px;float:left">&nbsp; — &nbsp;</div>'+
				       '<div id="search-st-month-start-time" style="float:left"></div> <div style="height:25px;line-height:25px;float:left">&nbsp;至&nbsp;</div> <div id="search-st-month-end-time" style="float:left"></div>';
	    
	    var yearStr = '<div id="search-st-year-start-time" style="float:left"></div> <div style="height:25px;line-height:25px;float:left">&nbsp;至&nbsp;</div> <div id="search-st-year-end-time" style="float:left"></div>';		       

    	// 按月时间型数据源
    	var monthTimeDate = [
    	    {name:"1月",value:"1"},
    	    {name:"2月",value:"2"},
    	    {name:"3月",value:"3"},
    	    {name:"4月",value:"4"},
    	    {name:"5月",value:"5"},
    	    {name:"6月",value:"6"},
    	    {name:"7月",value:"7"},
    	    {name:"8月",value:"8"},
    	    {name:"9月",value:"9"},
    	    {name:"10月",value:"10"},
    	    {name:"11月",value:"11"},
    	    {name:"12月",value:"12"},
    	];
    	// 按年时间型数据源
    	var yearTimeDate = [];
    	var date = new Date();
   	  	var year = date.getFullYear();
   	 	var month = date.getMonth()+1;
   	  	for(var i=year;i>year-5;i--){
   		  var obj = new Object();
   		  obj.name = i+"年";
   		  obj.value = i;
   		  yearTimeDate.push(obj);
   	  	}
   	  	
   	    if("year" == type){
   	    	
   	    	$("#st-dateTime").html(yearStr);
   	    	var source ={
   	             datatype: "json",
   	             datafields: [
   	                 { name: 'name' },
   	                 { name: 'value' }
   	             ],
   	             localData: yearTimeDate
   	         };
	   	     var dataAdapter = new $.jqx.dataAdapter(source);
	         $("#search-st-year-start-time").jqxDropDownList({
	             selectedIndex: 4, 
	             source: dataAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 70, 
	             height: 25
	         });
	         $("#search-st-year-end-time").jqxDropDownList({
	             selectedIndex: 0, 
	             source: dataAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 70, 
	             height: 25
	         });
   	    	
		}else if("quarter" == type){
			$("#st-dateTime").html(quarterStr);
			
			// 初始化年
			var yearSource ={
   	             datatype: "json",
   	             datafields: [
   	                 { name: 'name' },
   	                 { name: 'value' }
   	             ],
   	             localData: yearTimeDate
   	         };
			 var dataYearAdapter = new $.jqx.dataAdapter(yearSource);
	         $("#search-st-quarter-year-time").jqxDropDownList({
	             selectedIndex: 4, 
	             source: dataYearAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 70, 
	             height: 25
	         });
	         $("#search-st-quarter-year-time").val(year);
			
		}else if("month" == type){
			$("#st-dateTime").html(monthStr);
			
			// 初始化年
			var yearSource ={
   	             datatype: "json",
   	             datafields: [
   	                 { name: 'name' },
   	                 { name: 'value' }
   	             ],
   	             localData: yearTimeDate
   	         };
			 var dataYearAdapter = new $.jqx.dataAdapter(yearSource);
	         $("#search-st-month-year-time").jqxDropDownList({
	             selectedIndex: 4, 
	             source: dataYearAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 70, 
	             height: 25
	         });
	         $("#search-st-month-year-time").val(year);
			
			// 初始化月份
   	    	var source ={
   	             datatype: "json",
   	             datafields: [
   	                 { name: 'name' },
   	                 { name: 'value' }
   	             ],
   	             localData: monthTimeDate
   	         };
	   	     var dataAdapter = new $.jqx.dataAdapter(source);
	         $("#search-st-month-start-time").jqxDropDownList({
	             selectedIndex: 0, 
	             source: dataAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 60, 
	             height: 25
	         });
	         $("#search-st-month-end-time").jqxDropDownList({
	             source: dataAdapter, 
	             displayMember: "name", 
	             valueMember: "value", 
	             dropDownHeight: 95,
	             theme:currentTheme,
	             width: 80, 
	             height: 25
	         });
	         $("#search-st-month-end-time").val(month);
		}
    }
</script>

</html>
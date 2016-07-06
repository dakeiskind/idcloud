
define(["../httpService","jqwidgets/jqx-all"], function (http) {
	  
	  var service = function(){
		  
		  // 初始化grid组件
		  this.initgrid = function(settings){
			  // 默认属性
			  var defaults={
					width: "100%",
					theme:"bootstrap",
					data: null,
					columnsresize: true,
					pageable: true, 
					pagesize: 10, 
					rowsheight: 30,
					groupsheaderheight:35,
					autoheight: true,
					selectionmode:"none",
					virtualmode: false, // 服务器分页
					showtoolbar: false,
					columns:[],
					rowdetails:false,
					rendertoolbar: function (toolbar) {
						toolbar.append(null);
					}
			  };
			  
			  // 合并参数对象
			  var obj = $.extend(defaults,settings);
			  
			  // 详细行模板
			  var nestedGrids = new Array();
			  var rowDetailsTempale = function (index, parentElement, gridElement, record) {
		            var id = record[obj.rowsDetailConfig.sid].toString();
		            var grid = $($(parentElement).children()[0]);
		            nestedGrids[index] = grid;
		            var filtergroup = new $.jqx.filter();
		            var filtervalue = id;
		            var filtercondition = 'equal';
		            var filter = filtergroup.createfilter('stringfilter', filtervalue, filtercondition);
		            var detailsbyid = [];
		            var detailsItems = null;
		            // 获取datagrid详细行的数据
		            if(obj.rowsDetailConfig.subData.localData != null &&obj.rowsDetailConfig.subData.localData != 'undefined'){
		            	detailsItems = obj.rowsDetailConfig.subData.localData
					 }else{
						  http.AjaxRequest({
							  	url : obj.rowsDetailConfig.subData.url, 
								type: obj.rowsDetailConfig.subData.type,
								params:obj.rowsDetailConfig.subData.params,
								async:obj.rowsDetailConfig.subData.async,
								callback : function (data) {
									detailsItems = data;
								}
						  });
					}
		            
		            for (var m = 0; m < detailsItems.length; m++) {
		                var result = filter.evaluate(detailsItems[m][obj.rowsDetailConfig.parentSid]);
		                if (result)
		                	detailsbyid.push(detailsItems[m]);
		            }
		            var detailssource = { 
		            	datatype: "json",
		                localdata: detailsbyid 
		            };
		            var nestedGridAdapter = new $.jqx.dataAdapter(detailssource);
		            if (grid != null) {
		            	grid.jqxGrid({
		        			source: nestedGridAdapter,
		        			width:"95%",
		        			theme: obj.theme,
		        			autowidth: false,
		        			autoheight: true,
		        			columnsresize: true,
		        			rowsheight: 25,
		                    enablehover: false,
		                    selectionmode: obj.rowsDetailConfig.selectionmode,
		        			columns: obj.rowsDetailConfig.columns
		        		});
		            }
			  };
			  
			  var dataAdapter = null;
			  // 判断是否是服务器分页，并获取grid数据
			  if(obj.virtualmode){
				  var data = http.getPagingDataAdapter({
						url: obj.data.url,
						params: obj.data.params
					});
				  dataAdapter = data;
			  }else{
				  if(obj.data.localData != null && obj.data.localData != 'undefined'){
					  var source ={
							datatype: "json",
							localdata: obj.data.localData
					  };
					  dataAdapter = new $.jqx.dataAdapter(source);
				  }else{
					  http.AjaxRequest({
						  	url : obj.data.url, 
							type: obj.data.type,
							params:obj.data.params,
							async:obj.data.async,
							callback : function (data) {
								var source ={
									datatype: "json",
									localdata: data
								};
								dataAdapter = new $.jqx.dataAdapter(source);
							}
					  });
				  }
			  }

			  // 初始化 grid组件
			  $(obj.id).jqxGrid({
					width: obj.width,
					theme: obj.theme,
					source: dataAdapter,
					columnsresize: obj.columnsresize,
					pageable: obj.pageable, 
					pagesize: obj.pagesize, 
					groupsheaderheight: obj.groupsheaderheight,
					rowsheight: obj.rowsheight,
					autoheight: obj.autoheight,
					selectionmode:obj.selectionmode,
					columns: obj.columns,
					virtualmode: obj.virtualmode,
					rendergridrows: function(){
						 return dataAdapter.records;
					},
					showtoolbar: obj.showtoolbar,
					rowdetails: obj.rowdetails,
					initrowdetails: rowDetailsTempale,
			        rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>",rowdetailsheight:170,rowdetailshidden: true },
					rendertoolbar: function (toolbar) { 
		                 
						var container = $("<div style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
						toolbar.append(container);
						if(obj.showtoolbar){
							// 初始化按钮标签
							for(var i=0;i<obj.toolbars.length;i++){
								var str = "<a id="+obj.toolbars[i].id+">"+obj.toolbars[i].name+"</a>";
								container.append(str);
			                }
							// 初始化按钮及绑定事件
		                    for(var i=0;i<obj.toolbars.length;i++){
		                    	$("#"+obj.toolbars[i].id).jqxButton({ width: '80',theme:"bootstrap",height: '18px', disabled: obj.toolbars[i].disabled});
			                	$("#"+obj.toolbars[i].id).on("click",obj.toolbars[i].method);
		                    }
						}
		                
					}
			 });
			 // 绑定控制按钮状态事件 
			 $(obj.id).on('rowselect', obj.selectgrid);
		  };
		  
		  // 刷新grid组件
		  this.refreshGrid = function(){
			  
		  };
	  };

	  return new service();
});
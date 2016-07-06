define(["./httpService",
		"lib/jquery/pintuer",
		"datepicker",
		"lib/jquery/jquery.tablesorter.min"
		], function(http,pintuer) {
	/**
	  * 将form表单转换成json字符串
	  */
	 (function($){  
	     $.fn.serializeJson=function(){  
	         var str = '{';
	         var id = this.attr("id");
	         
	         var length = $("#"+id+" [name]").length;
	         var array = $("#"+id+" [name]");
	         
	     	for(var i=0;i<length;i++){
	     		var name = array.eq(i).attr("name");
	     		var id = array.eq(i).attr("id");
	     		if(i == (length-1)){
	     			str+='"'+ name +'"' + ':' + '"'+ $("#"+id+"").val() +'"' + "}";
	     		}else{
	         		str+='"'+ name +'"' + ":" + '"'+ $("#"+id+"").val() +'"' + ",";
	     		}
	     	}
	         return str;  
	     };  
	 })(jQuery);


	/**
	 * 手风琴插件
	 */
	(function($){$.fn.extend({
		    accordion: function(options) {
				var defaults = {
					accordion: 'true',
					isAutoOpen: false,
					speed: 300,
					closedSign: '<span class="icon-caret-right"></span>',
					openedSign: '<span class="icon-caret-down"></span>'
				};

				// Extend our default options with those provided.
				var opts = $.extend(defaults, options);
				//Assign current element to variable, in this case is UL element
		 		var $this = $(this);
		 		
		 		//add a mark [+] to a multilevel menu
		 		$this.find("li").each(function() {
		 			if($(this).find("ul").size() != 0){
		 				//add the multilevel sign next to the link
		 				$(this).find("a:first").prepend("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>"+ opts.closedSign +"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
		 				
		 				//avoid jumping to the top of the page when the href is an #
		 				if($(this).find("a:first").attr('href') == "#"){
		 		  			$(this).find("a:first").click(function(){return false;});
		 		  		}
		 			}
		 		});

		 		// 只展开class为active的<li>层
		 		$this.find("li.active").each(function() {
		 			$(this).parents("ul").slideDown(opts.speed);
		 			$(this).parents("ul").parent("li").find("span:first").html(opts.openedSign);
		 		});

		 		// 是否自动展开
		 		if(opts.isAutoOpen){
		 			$this.find("li").each(function() {
		 			$(this).parents("ul").slideDown(opts.speed);
		 			$(this).parents("ul").parent("li").find("span:first").html(opts.openedSign);
		 		});
		 		}

		  		$this.find("li a").click(function() {
		  			if($(this).parent().find("ul").size() != 0){
		  				if(opts.accordion){
		  					// 当为open的时候，不显示
		  					if(!$(this).parent().find("ul").is(':visible')){
		  						parents = $(this).parent().parents("ul");
		  						visible = $this.find("ul:visible");
		  						visible.each(function(visibleIndex){
		  							var close = true;
		  							parents.each(function(parentIndex){
		  								if(parents[parentIndex] == visible[visibleIndex]){
		  									close = false;
		  									return false;
		  								}
		  							});
		  							if(close){
		  								if($(this).parent().find("ul") != visible[visibleIndex]){
		  									$(visible[visibleIndex]).slideUp(opts.speed, function(){
		  										$(this).parent("li").find("span:first").html(opts.closedSign);
		  									});
		  									
		  								}
		  							}
		  						});
		  					}
		  				}
		  				if($(this).parent().find("ul:first").is(":visible")){
		  					$(this).parent().find("ul:first").slideUp(opts.speed, function(){
		  						$(this).parent("li").find("span:first").delay(opts.speed).html(opts.closedSign);
		  					});
		  					
		  					
		  				}else{
		  					$(this).parent().find("ul:first").slideDown(opts.speed, function(){
		  						$(this).parent("li").find("span:first").delay(opts.speed).html(opts.openedSign);
		  					});
		  				}
		  			}
		  		});
		    }
		});
	})(jQuery);

	/**
	 * 列表
	 */
	(function($){$.fn.extend({
		ptObject : [],
		ptGrid: function(settings,param,param2) {
			if(this.ptObject[this[0].id]==undefined){
				this.ptObject[this[0].id] = new Object();
			}
			var obj = this.ptObject[this[0].id];
			if(typeof(settings)=="object"){
				this.ptInitGrid(settings);
			}else{
				/*获取列表数据的方法开始*/
				if(settings=="getselectedrow"){//获取选中列
					var selectRows =  $(this).find("table").children(".tbody").find("tr."+obj.selecttrclass);
					var selectObj = Array();
					for(var i=0;i<selectRows.length;i++){
						var selectRow = selectRows[i];
						selectObj.push(jQuery.parseJSON($(selectRow).find("td.localData").html()));
					}
					return selectObj;
				}else if(settings=="setdata"){
					this.ptObject[this[0].id].data.localData = param;
				}else if(settings=="getrowdata"){//获取传入行下标对应行数据
					var rows =  $(this).find("table").children(".tbody").find("tr");
					var selRow = rows[param];
					return jQuery.parseJSON($(selRow).find("td.localData").html());
				}else if(settings=="applyfilters"){
					this.ptObject[this[0].id].thispage = 1;
					return;
				}else if(settings=="refreshfilterrow"){//搜索
					var date = obj.data.localData;
					var params = obj.data.params;
					var newdate = new Array();
					if(params==null){
						newdate = date;
					}else{
						for(var i=0;i<date.length;i++){
							var rowdate = date[i];
							rowdate.rrooww = i;
							var ispush = 1;
							for(var key in params){
								if(rowdate[key]!=undefined && rowdate[key].indexOf(params[key])==-1 && params[key]!=""){
									ispush = 0;
									break;
								}
							}
							if(ispush==1){
								newdate.push(rowdate);
							}
						}
					}
					this.ptObject[this[0].id].data.localDataFilter = newdate;
					this.ptInitTable();
				}else if(settings=="addRow"){//添加数据,param=object
					this.ptObject[this[0].id].data.localData.push(param);
				}else if(settings=="removeRow"){//删除数据,param=行数
					if(param!=null){
						if(this.ptObject[this[0].id].data.localDataFilter!=null){
							param = this.ptObject[this[0].id].data.localDataFilter[param].rrooww;
						}
						this.ptObject[this[0].id].data.localData.splice(param,1);
					}else{
						var rows =  $(this).find("table").children(".tbody").find("tr");
						var j=0;
						for(var i=0;i<rows.length;i++){
							var row = rows[i];
							if($(row).hasClass(obj.selecttrclass)){
								var rrooww = j;
								if(this.ptObject[this[0].id].data.localDataFilter!=null){
									rrooww = this.ptObject[this[0].id].data.localDataFilter[i].rrooww+j;
									console.log(rrooww);
								}
								this.ptObject[this[0].id].data.localData.splice(rrooww,1);
								j--;
							}
							if(this.ptObject[this[0].id].data.localDataFilter!=null){
							}else{
								j++;
							}
						}
					}
				}else if(settings=="updateRow"){//修改数据,param=行数,param2=修改之后的object
					if(this.ptObject[this[0].id].data.localDataFilter!=null){
						param = this.ptObject[this[0].id].data.localDataFilter[param].rrooww;
					}
					this.ptObject[this[0].id].data.localData.splice(param,1,param2);
				}
			}
		},
		ptInitGrid : function(settings) {
			if($(this).children(".ptGrid").length==0){
				$(this).append($("<div class=\"ptGrid xs12\"></div>"));
			}
			if($(this).find(".ptToolbars").length==0){
				$(this).children(".ptGrid").append($("<div class=\"ptToolbars xs12 margin-small-bottom\" style=\"display:none;\"></div>"));
			}
			if($(this).find(".ptTable").length==0){
				$(this).children(".ptGrid").append($("<div class=\"ptTable xs12\"></div>"));
			}
			var me = this;
			//var $me = $(this).children(".ptGrid");
			// 合并参数对象
			var obj = {
					width: "100%",	//列表宽度(可选)
					theme:"ice",	//列表皮肤(可选)
					data: null,		//1.静态数据{localData: json数据} 2.ajax获取数据{url:接口url,type:post/get,params:传入参数,async:false/true}
					pageable: true,		//是否分页(可选)
					pagesize: 10,		//每页显示数(可选)
					sortable: false,	//是否列排序-全局(可选)
					selectionmode:"none", //列表类型,目前支持none,singlerow,checkbox(可选)
					columns:[],		//列信息
					toolbars:null,
					rowselect:null,		//选中行触发事件(可选)
					controller:null,	//grid所在控制器，用于激活动态html事件
					/********************以下非提交参数**********************/
					thispage : 1,
					rowcount : 0,
					pagecount : 1,
					selecttrclass : "gridactive" //选中列表样式;
			}
			obj = $.extend(obj,settings);
			//以下是列表初始
			if(obj.data!=null && obj.data.localData != null && obj.data.localData != 'undefined'){
			}else{
				http.AjaxRequest({
					url : obj.data.url,
					type: obj.data.type,
					params:obj.data.params,
					async:false,
					contentType: "application/json",
					callback : function (data) {
						obj.data.localData = data;
					}
				});
			}
			obj.rowcount = obj.data.localData.length;
			$.extend(me.ptObject[this[0].id],obj);

			this.ptInitToolbars();
			this.ptInitTable();
		},
		ptInitToolbars : function(){
			var obj = this.ptObject[this[0].id];
			if(obj.toolbars!=null && obj.toolbars.length>0 && $(this).children(".ptGrid").children(".ptToolbars").html().length==0){
				$(this).children(".ptGrid").children(".ptToolbars").show();
				var datepickerary = new Array();
				var toolbarsHtml = "";
				toolbarsHtml += "<div class=\"xs12\">";
				for(var i=0;i<obj.toolbars.length;i++) {
					var toolbarsObj = obj.toolbars[i];
					toolbarsObj.name = toolbarsObj.name == undefined ? "" : toolbarsObj.name;
					toolbarsObj.placeholder = toolbarsObj.placeholder == undefined ? "" : toolbarsObj.placeholder;
					toolbarsObj.width = toolbarsObj.width == undefined ? "150" : toolbarsObj.width;
					toolbarsObj.click = toolbarsObj.click == undefined ? "" : toolbarsObj.click;
					toolbarsObj.icon = toolbarsObj.icon == undefined ? "" : toolbarsObj.icon;
					var attrStr = "";
					if (toolbarsObj.disabled) {
						attrStr = ' disabled="disabled"';
					}
					if (toolbarsObj.type == "button") {
						var classStr = "button button-small bg-blue radius-none";
						if (toolbarsObj.class != null) {
							classStr = toolbarsObj.class;
						}
						if (toolbarsObj.align == "right") {
							classStr += " float-right";
						}else{
							classStr += " float-left";
						}
						toolbarsHtml += '<button class="' + classStr + '" id="' + toolbarsObj.id + '" ' + attrStr + ' ms-click="' + toolbarsObj.click + '">';
						toolbarsHtml += '<span class="' + toolbarsObj.icon + '"><span> ';
						toolbarsHtml += toolbarsObj.name;
						toolbarsHtml += '</button>';
					} else if (toolbarsObj.type == "select") {
						var classStr = "select text-small radius-none";
						var divClassStr = "";
						if (toolbarsObj.align == "right") {
							divClassStr += " float-right";
						}
						toolbarsHtml += '<div class="' + divClassStr + '">';
						if (toolbarsObj.name != "") {
							toolbarsHtml += '<div class="search_div_02">' + toolbarsObj.name + '</div>';
						}
						toolbarsHtml += '	<div class="search_div_01">';
						toolbarsHtml += '	<select class="' + classStr + '" id="' + toolbarsObj.id + '">';
						for (var j = 0; j < toolbarsObj.data.length; j++) {
							var opobj = toolbarsObj.data[j];
							toolbarsHtml += '<option value="' + opobj.value + '">' + opobj.name + '</option>';
						}
						toolbarsHtml += '	</select>';
						toolbarsHtml += '	</div>';
						toolbarsHtml += '</div>';
					} else if (toolbarsObj.type == "text") {
						var classStr = "input input-small radius-none";
						var divClassStr = "";
						if (toolbarsObj.align == "right") {
							divClassStr += " float-right";
						}
						toolbarsHtml += '<div class="' + divClassStr + '">';
						if (toolbarsObj.name != "") {
							toolbarsHtml += '<div class="search_div_02">' + toolbarsObj.name + '</div>';
						}
						toolbarsHtml += '	<div class="search_div_01">';
						toolbarsHtml += '	<input type="text" class="' + classStr + '" id="' + toolbarsObj.id + '" placeholder="' + toolbarsObj.placeholder + '" width="'+toolbarsObj.width+'" />';
						toolbarsHtml += '	</div>';
						toolbarsHtml += '</div>';
					}else if(toolbarsObj.type=="date"){
						var classStr = "input radius-none";
						var divClassStr = "";
						if (toolbarsObj.align == "right") {
							divClassStr += " float-right";
						}
						toolbarsHtml += '<div class="' + divClassStr + '">';
						if (toolbarsObj.name != "") {
							toolbarsHtml += '<div class="search_div_02">' + toolbarsObj.name + '</div>';
						}
						toolbarsHtml += '	<div class="search_div_01">';
						toolbarsHtml += '	<input type="text" class="' + classStr + '" id="' + toolbarsObj.id + '" placeholder="' + toolbarsObj.placeholder + '" width="'+toolbarsObj.width+'" />';
						toolbarsHtml += '	</div>';
						toolbarsHtml += '</div>';
						datepickerary.push(toolbarsObj.id);
					}else if(toolbarsObj.type=="morebutton"){
						var classStr = "button-group";
						if (toolbarsObj.align == "right") {
							classStr += " float-right";
						}else{
							classStr += " float-left";
						}
						toolbarsHtml += '<div class="'+classStr+'" >';
						toolbarsHtml += '<button type="button bg-blue" class="button button-small radius-none dropdown-toggle">';
						toolbarsHtml += '<span class="' + toolbarsObj.icon + '"><span> ';
						toolbarsHtml += toolbarsObj.name;
						toolbarsHtml += ' <span class="downward"></span>';
						toolbarsHtml += '</button>';
						toolbarsHtml += '<ul class="drop-menu pull-right">';
						for(var j=0;j<toolbarsObj.data.length;j++){
							var moreBtnObj = toolbarsObj.data[j];
							var liAtt = "";
							if(moreBtnObj.click!=""){
								liAtt += ' ms-click="'+moreBtnObj.click+'"';
							}
							toolbarsHtml += '<li '+liAtt+'><a href="javascript:void(0);">';
							toolbarsHtml += '<span class="' + moreBtnObj.icon + '"><span> ';
							toolbarsHtml += moreBtnObj.name+'</a> </li>';
						}
						toolbarsHtml += '</ul>';
						toolbarsHtml += '</div>';
					}
				}
				toolbarsHtml += "</div>";
				$(this).find(".ptToolbars").append($(toolbarsHtml));
				for(var i=0;i<datepickerary.length;i++){
					$('#'+datepickerary[i]).datepicker({});
				}
			}
		},
		ptInitTable : function() {
			$(this).find(".ptTable").children("table").remove();
			var tableheaders = new Array();
			var obj = this.ptObject[this[0].id];
			var me = this;
			var $me = $(this).children(".ptGrid").children(".ptTable");
			var localData = obj.data.localData;
			var colspan = obj.columns.length;
			if(obj.data.localDataFilter!=null){
				localData = obj.data.localDataFilter;
			}

			var tableHtml = "";
			var tablestyle="";
			if(obj.width!="100%"){
				if(obj.width.indexOf("%")>-1){
					tablestyle+="width:"+obj.width+";";
				}else{
					tablestyle+="width:"+obj.width+"px;";
				}
			}
			tableHtml += '<table class="table table-bordered" style="'+tablestyle+'">';
			tableHtml += '<thead>';
			tableHtml += '<tr>';
			if(obj.selectionmode=="checkbox"){
				tableHtml += '<th style="width:38px;text-align:center;"><input type="checkbox" class="ptcheckbox"></th>';
				tableheaders.push({sorter: false});
				colspan++;
			}
			for(var i=0;i<obj.columns.length;i++){
				var titleobj = obj.columns[i];
				var thattr = "";
				var sorter = obj.sortable;
				if(titleobj.sortable!=null){
					sorter = titleobj.sortable;
				}
				tableheaders.push({sorter: sorter});

				if(titleobj.align!=undefined){
					thattr += ' class="'+titleobj.align+'"';
				}
				if(titleobj.width!=undefined){
					thattr += ' style="width:'+titleobj.width+'px"';
				}
				tableHtml += '<th '+thattr+'>'+titleobj.text+'</th>';
			}
			tableHtml += '</tr>';
			tableHtml += '</thead>';
			tableHtml += '<tbody class="tbody">';
			if(localData.length>0){
				for(var i=0;i<localData.length;i++) {
					var rowdata = localData[i];
					if(obj.pageable==true && (i>=obj.pagesize*obj.thispage || i<obj.pagesize*(obj.thispage-1))){
						tableHtml += '<tr class="ptshow" style="display:none;">';
					}else{
						tableHtml += '<tr class="ptshow">';
					}
					if(obj.selectionmode=="checkbox"){
						tableHtml += '<td style="width:38px;text-align:center;"><input type="checkbox" class="ptcheckbox"></td>';
					}
					for (var j = 0; j < obj.columns.length; j++) {
						colobj = obj.columns[j];
						var tdstyle = "";
						if(colobj.width!=null){
							tdstyle += "width:"+colobj.width+"px;";
						}
						if(colobj.cellsrenderer!=null){
							tableHtml += '<td style="overflow:inherit;'+tdstyle+'" align="center"><div class="cellsrenderer">' + colobj.cellsrenderer(i,rowdata) + '</div></td>';
						}else{
							var str = rowdata[colobj.datafield];
							if(typeof str == "number"){
								str = parseFloat(str).toFixed(2);
							}
							str = (str == null || str == undefined)?"":str;
							tableHtml += '<td style="'+tdstyle+'" class="field" title="'+rowdata[colobj.datafield]+'">' + str + '</td>';
						}
					}
					tableHtml += '<td class="localData" style="display:none;">'+JSON.stringify(rowdata)+'</td>';
					tableHtml += '</tr>';
				}
			}else{
				tableHtml += '<tr>';
				tableHtml += '<td colspan="'+colspan+'" style="height:100px;line-height:100px;" align="center">暂没有数据</td>';
				tableHtml += '</tr>';
			}
			tableHtml += '</tbody>';
			if(localData.length>0){
				tableHtml += '<tfoot>';
				tableHtml += '<tr>';
				tableHtml += '<td colspan="'+colspan+'" class="pageTd" style="background:#fff;">';

				tableHtml += '</td>';
				tableHtml += '<tr>';
				tableHtml += '</tfoot>';
			}
			tableHtml += '</table>';
			$me.append($(tableHtml));
			//排序
			$me.find("table").tablesorter({
				theme: obj.theme,
				headers: tableheaders
			});
			$me.find("table").bind("sortEnd",function(e, table) {
				me.ptRefreshContainer(false);
			});
			//
			if(obj.selectionmode=="singlerow"){//单选
				$me.children("table").children(".tbody").children("tr").on("click",function(){
					if($(this).hasClass(obj.selecttrclass)){
						$me.children("table").children(".tbody").children("tr").removeClass(obj.selecttrclass);
					}else{
						$me.children("table").children(".tbody").children("tr").removeClass(obj.selecttrclass);
						$(this).addClass(obj.selecttrclass);
					}
					if(obj.rowselect!=null){
						obj.rowselect();
					}
				});
			}else if(obj.selectionmode=="checkbox"){//多选
				$me.children("table").children("thead").find(".ptcheckbox").on("change",function(){
					if($(this).prop("checked")){
						$me.children("table").children(".tbody").find("tr").addClass(obj.selecttrclass);
						$me.children("table").children(".tbody").find(".ptcheckbox").prop("checked",true);
					}else{
						$me.children("table").children(".tbody").find("tr").removeClass(obj.selecttrclass);
						$me.children("table").children(".tbody").find(".ptcheckbox").prop("checked",false);
					}
					if(obj.rowselect!=null){
						obj.rowselect();
					}
				});

				var isclickcheckbox = 0;
				$me.children("table").children(".tbody").find(".ptcheckbox").on("change",function(){
					isclickcheckbox = 1;
					if($(this).prop("checked")){
						$(this).parent("td").parent("tr").addClass(obj.selecttrclass);
					}else{
						$(this).parent("td").parent("tr").removeClass(obj.selecttrclass);
					}
					if(obj.rowcount==$me.children("table").children(".tbody").find("tr."+obj.selecttrclass).length){
						$me.children("table").children("thead").find(".ptcheckbox").prop("checked",true);
					}else{
						$me.children("table").children("thead").find(".ptcheckbox").prop("checked",false);
					}
					if(obj.rowselect!=null){
						obj.rowselect();
					}
				});

				$me.children("table").children(".tbody").children("tr").find("td.field").on("click",function(){
					if(isclickcheckbox==0){
						if($(this).parent().hasClass(obj.selecttrclass)){
							$(this).parent().removeClass(obj.selecttrclass);
							$(this).parent().find(".ptcheckbox").prop("checked",false);
						}else{
							$(this).parent().addClass(obj.selecttrclass);
							$(this).parent().find(".ptcheckbox").prop("checked",true);
						}
						if(obj.rowcount==$me.children("table").children(".tbody").find("tr."+obj.selecttrclass).length){
							$me.children("table").children("thead").find(".ptcheckbox").prop("checked",true);
						}else{
							$me.children("table").children("thead").find(".ptcheckbox").prop("checked",false);
						}
						if(obj.rowselect!=null){
							obj.rowselect();
						}
					}else{
						isclickcheckbox = 0;
					}
				});
			}
			this.ptInitPage();
			pintuer.init();
			avalon.scan(0,obj.controller);
		},
		ptInitPage : function(){
			var obj = this.ptObject[this[0].id];
			var me = this;
			var $me = $(this).children(".ptGrid");
			obj.thispage = parseInt(obj.thispage);
			var localData = obj.data.localData;
			if(obj.data.localDataFilter!=null){
				localData = obj.data.localDataFilter;
			}
			var pageCount = parseInt(localData.length/obj.pagesize);
			if(pageCount<localData.length/obj.pagesize){
				pageCount++;
			}

			var pageHtml = "";
			if(obj.pageable==true && localData.length>0){
				pageHtml += '<div class="page" style="float:right;">';
			}else{
				pageHtml += '<div class="page"  style="float:right;display:none;">';
			}
			pageHtml += '<div class="pagination-info">' +
			'<span class="ng-binding">共有 '+localData.length+' 条</span>，<span class="ng-binding">每页显示 '+obj.pagesize+' 条</span>' +
			'</div>';
			pageHtml += '<div class="pright">';
			pageHtml += '<ul class="pagination pagination-group pagination-small radius-none">';
			pageHtml += '<li class="prev radius-none"><a href="javascript:void(0);"><i class="icon-angle-double-left"></i></a> </li>';
			for(var i=1;i<=pageCount;i++){
				if(pageCount<=8){
					if(obj.thispage==i){
						pageHtml += '<li class="'+obj.selecttrclass+' pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
					}else{
						pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
					}
				}else{
					if(obj.thispage<5){
						if(obj.thispage==i){
							pageHtml += '<li class="'+obj.selecttrclass+' pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
						}else if(i<=5){
							pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
						}else{
							i = pageCount;
							pageHtml += '<li class="disabled"><a href="javascript:void(0);">...</a> </li>';
							pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
						}
					}else if(obj.thispage>=pageCount-4){
						if(i<=pageCount-4){
							pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
							pageHtml += '<li class="disabled"><a href="javascript:void(0);">...</a> </li>';
							i = pageCount-4
						}
						if(obj.thispage==i){
							pageHtml += '<li class="'+obj.selecttrclass+' pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
						}else{
							pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+i+'</a> </li>';
						}
					}else{
						pageHtml += '<li class="pageNum"><a href="javascript:void(0);">1</a> </li>';
						pageHtml += '<li class="disabled"><a href="javascript:void(0);">...</a> </li>';
						pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+(obj.thispage-1)+'</a> </li>';
						pageHtml += '<li class="'+obj.selecttrclass+' pageNum"><a href="javascript:void(0);">'+obj.thispage+'</a> </li>';
						pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+(obj.thispage+1)+'</a> </li>';
						pageHtml += '<li class="disabled"><a href="javascript:void(0);">...</a> </li>';
						pageHtml += '<li class="pageNum"><a href="javascript:void(0);">'+pageCount+'</a> </li>';
						break;
					}
				}
			}
			pageHtml += '<li class="next"><a href="javascript:void(0);"><i class="icon-angle-double-right"></i></a> </li>';
			pageHtml += '</ul>';
			pageHtml += '</div>';
			pageHtml += '</div>';
			$me.find(".pageTd").children(".page").remove();
			$me.find(".pageTd").append($(pageHtml));
			$(".pageNum").on("click",function(){
				$(".pageNum").removeClass(obj.selecttrclass);
				$me.addClass(obj.selecttrclass);
				obj.thispage = $(this).text();
				me.ptRefreshContainer();
			});
			$(".prev").on("click",function(){
				if(obj.thispage>1){
					obj.thispage--;
					$(".pageNum").removeClass(obj.selecttrclass);
					$($(".pageNum")[obj.thispage-1]).addClass(obj.selecttrclass);
					me.ptRefreshContainer();
				}
			});
			$(".next").on("click",function(){
				if(obj.thispage<pageCount){
					obj.thispage++;
					$(".pageNum").removeClass(obj.selecttrclass);
					$($(".pageNum")[obj.thispage-1]).addClass(obj.selecttrclass);
					me.ptRefreshContainer();
				}
			});
		},
		ptRefreshContainer : function(isUpdate){
			var obj = this.ptObject[this[0].id];
			var $me = $(this).children(".ptGrid");

			var rows = $me.find("table").children(".tbody").children("tr");
			rows.hide();
			for(var i=obj.pagesize*obj.thispage-1;i>=obj.pagesize*(obj.thispage-1);i--){
				$(rows[i]).show();
			}
			this.ptInitPage();
			if(isUpdate==undefined && isUpdate){
				$me.children("table").trigger("update");
			}
		}
	});
	})(jQuery);
});

// 弹出validator信息插件
(function($) {
	$.fn.extend({
		validatorPop:function(){
			var messgae = $(this).attr("title");
			var width = 0;
			if(messgae.length < 7){
				width = messgae.length * 16.5;
			}else if(messgae.length == 7){
				width = messgae.length * 15;
			}else if(messgae.length > 7){
				width = messgae.length * 14;
			}
			var str = "<div class='error-pop' style='min-width:50px;max-width:300px;width:"+width+"px'>" +
					messgae+
					"<div class='arrow'><div class='box1'></div></div>"+
					"</div>";
			$(this).append(str);
		}
	})

})(jQuery);
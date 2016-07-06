var ticketBindModel = function(ticketMgt) {
	
	/**************************************************** 工单管理********************************************/
	
    /** 查询用户 */
    this.searchTicket = function(){
    	ticketMgt.searchObj.ticketNoLike = $("#search-ticket-id").val();
    	ticketMgt.searchObj.createdStartDt = $("#search-ticket-createDt-start").val();
    	ticketMgt.searchObj.createdEndDt = $("#search-ticket-createDt-end").val();
    	ticketMgt.searchObj.processType = $("#search-ticket-type").val()=="全部"?"":$("#search-ticket-type").val();
    	ticketMgt.searchObj.status = $("#search-ticket-status").val()=="全部"?"":$("#search-ticket-status").val(); 
    	ticketMgt.searchTicketMgt();
    };
	
    //添加日常维护工单
    this.addNormalTicket = function(){
    	$("#popAddTicketWindow").jqxWindow('open');
    };
	
	// 分配
	this.allocateItem = function() {
		var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
		var ok = $("#allocateBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
			
			$("#allocate-ticketSid").val(data.ticketSid);
			
			// 将常用的字段可以使用这个方法设置数据
			ticketMgt.searchTicketMgtAllocate();

			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#popupAllocateWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 350) / 2
				}
			});
			$("#popupAllocateWindow").jqxWindow('open');
		}
	};
	
	// 处理
	this.processItem = function() {
		var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
		var ok = $("#processBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
			
			$("#view-ticketSid").val(data.ticketSid);
//			$("#instanceNetGrid").jqxGrid('clear');

			$("#vmOpen-name").val("");
			
			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#popupProcessWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 350) / 2
				}
			});
			$("#popupProcessWindow").jqxWindow('open');
			$('#handlerTab').jqxTabs('select', 0);
			
			// 将常用的字段可以使用这个方法设置数据
			ticketMgt.searchTicketMgtProcess(data);
		}
	};
	
	// 详情
	this.viewItem = function() {
		var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
		var ok = $("#viewBtn").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
			
			$("#process-ticketSid").val(data.ticketSid);
			
			// 将常用的字段可以使用这个方法设置数据
			ticketMgt.searchTicketMgtView(data);

			var windowW = $(window).width();
			var windowH = $(window).height();
			$("#popupViewWindow").jqxWindow({
				position : {
					x : (windowW - 800) / 2,
					y : (windowH - 550) / 2
				}
			});
			$("#popupViewWindow").jqxWindow('open');
		}
	};
	
	/** 提交分配用户的信息 */
    this.allocateUserSubmit = function(){
    	// 判断是否通过了验证
    	ticketMgt.allocateTicketMgtUser();
    };
    
    // 答复工单
	this.processTicketSubmit = function() {
		// 判断是否通过了验证
    	$('#processForm').jqxValidator('validate');
  
	};
	
	// 关闭工单
	this.closeTicketSubmit = function() {
		var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#processTicket").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
			
			var param = {};
			param.ticketSid = data.ticketSid;
			param.action = "04";
			
			Core.AjaxRequest({
 				url : ws_url + "/rest/tickets/platform/operationTicket?ticketSid=" + data.ticketSid + "&action="+"04" + "&operateContent=" + "",
 					params :param,
					callback : function (data) {
						$('#jqxgridTicket').jqxGrid('clearselection');
						$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			   			$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			   			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						ticketMgt.searchTicketMgt();
						$("#popupProcessWindow").jqxWindow('close');
						

						//更新菜单
						/** 获取当前用户的角色和权限数据，生成导航条 */
					    if(currentUser != null || currentUser != 'undefined'){
					    	Core.AjaxRequest({ 
					    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
					    		type : "GET",
					    		async: false,
					    		callback : function (data) {
					    			
					    			window.parent.createNavList(data);
					    			window.parent.moudulesData = data;
					    			
					    			window.parent.$("#menuContent .liShow").removeClass("liShow");
					    			window.parent.$("#menuContent").find("b:contains('工单管理')").parents("li").addClass("liShow");
					    		} 
					    	});
					    }
				    },
				    failure:function(data){
				    	$("#popupProcessWindow").jqxWindow('close');
				    }
				});

		}
	};

	
	// 移除工单
	this.removeTicketSubmit = function() {
		var rowindex = $('#jqxgridTicket').jqxGrid('getselectedrowindex');
		// 判断审核按钮是否可用
		var ok = $("#processTicket").jqxButton("disabled");
		if (rowindex >= 0 && !ok) {
			var data = $('#jqxgridTicket').jqxGrid('getrowdata', rowindex);
			var param = {};
			param.ticketSid = data.ticketSid;
			param.action = "05";
			
			Core.AjaxRequest({
 				url : ws_url + "/rest/tickets/platform/operationTicket",
					params :param,
					callback : function (data) {
						$('#jqxgridTicket').jqxGrid('clearselection');
						$("#allocateBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			   			$("#processBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
			   			$("#viewBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px',  disabled: true});
						ticketMgt.searchTicketMgt();
						$("#popupProcessWindow").jqxWindow('close');
						

						//更新菜单
						/** 获取当前用户的角色和权限数据，生成导航条 */
					    if(currentUser != null || currentUser != 'undefined'){
					    	Core.AjaxRequest({ 
					    		url : ws_url + "/rest/user/platform/findMoudules/" + currentUser.userSid,
					    		type : "GET",
					    		async: false,
					    		callback : function (data) {
					    			
					    			window.parent.createNavList(data);
					    			window.parent.moudulesData = data;
					    			
					    			window.parent.$("#menuContent .liShow").removeClass("liShow");
					    			window.parent.$("#menuContent").find("b:contains('工单管理')").parents("li").addClass("liShow");
					    		} 
					    	});
					    }
				    },
				    failure:function(data){
				    	$("#popupProcessWindow").jqxWindow('close');
				    }
				});

		}
	};

};

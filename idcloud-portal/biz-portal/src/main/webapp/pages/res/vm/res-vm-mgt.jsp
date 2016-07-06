<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style type="text/css">
#vmTabs {
	width: 99.8%;
	height: 99%;
}

#vmTabs ul {
	margin: 0px;
	padding: 0px;
	width: 99%;
	margin-left: 0.5%;
	margin-top: 5px;
	height: 30px;
	background: #F4F4F4;
	list-style: none;
	border: 1px solid #E5E5E5;
}

#vmTabs .vm {
	margin: 1px;
	height: 26px;
	line-height: 30px;
	padding: 1px 8px 1px 8px;
	cursor: pointer;
	float: left;
}

#vmTabs .vm:hover {
	background: #DEDEDE;
}

#vmTabs .show {
	background: #fff;
	border: 1px solid #e5e5e5;
	border-bottom: 0px;
	height: 27px;
}

#vmTabs .content {
	width: 99%;
	margin-left: 0.5%;
	height: 92%;
	display: none;
	border: 1px solid #e5e5e5;
	border-top: 0px;
}

#vmTabs .contentShow {
	display: block;
}
</style>

<div id='vmTabs'>
	<ul>
		<li class="vm show" style="margin-left: 30px;">已纳入管理</li>
		<li class="vm">未纳入管理</li>
	</ul>
	<div class="content contentShow">
		<%@ include file="res-vm-managed-index.jsp"%>
	</div>
	<div class="content">
        <%@ include file="res-vm-unmanaged-index.jsp"%> 
	</div>
</div>


<script type="text/javascript">
	function initVMTabs() {
		var vm = new vmManagedModel();
	 	vm.initInput();
	 	vm.initVMDatagrid();
	 	vm.searchVMInfo();
		$("#vmTabs .vm").each(function(index) {
			$(this).click(function() {
				$("#vmTabs .show").removeClass("show");
				$(this).addClass("show");

				$("#vmTabs .contentShow").removeClass("contentShow");
				$("#vmTabs .content").eq(index).addClass("contentShow");
			});
		});
	}
</script>


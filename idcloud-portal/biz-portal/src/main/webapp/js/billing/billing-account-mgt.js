var initUserAccountPageJs = function () {
    var me = this;

    // 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.init = function(){
        //初始化搜索条件
        me.initSearchCondition();
        //初始化datagrid
        me.initUserAccountDatagrid();
        //初始按钮组件
        me.initOperationBtn();
        //初始化弹框
        me.initPopWindow();
        //初始化验证规则
        me.initValidator();
        //初始编辑框组件
        me.initEditWin();
    };

    //初始化搜索
    this.initSearchCondition = function() {
        //搜索条件
        me.searchObj = {
            'qm.accountNameLike':"",
            'qm.accountType':"",
            'qm.accountLevelSid':"",
            'qm.userAccountLike':"",
            'qm.status':""
        };

        //初始化搜索组件
        $("#search-account-name").jqxInput({width: 100, height: 22, minLength: 1,theme:currentTheme});
        $("#search-user-account-name").jqxInput({width: 100, height: 22, minLength: 1,theme:currentTheme});
        var codesearch = new codeModel({width:100,dropDownWidth:100,autoDropDownHeight:true});
        codesearch.getCommonCode("search-status","ACCOUNT_STATUS", true);
        codesearch.getCommonCode("search-account-type","ACCOUNT_TYPE", true);
        codesearch.getCustomCode("search-account-level-sid","/level/findAll","levelName","levelSid",true);
        $("#searchBtn").jqxButton({ width: 60, height: 22, theme : currentTheme});

        //重置搜索的input值
        $("#search-account-name").val('');
        $("#search-user-account-name").val('');
        $("#search-account-type").val('');
        $("#search-account-level-sid").val('');
        $("#search-status").val('');
    };

    // 初始化新增账户页面组件
    this.initAddWin = function() {
        $("#add-account-name").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#add-balance").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#add-gift-balance").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#add-default-space").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#add-order-space").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme,disabled:true});
        $("#saveAddAccountBtn").jqxButton({ width: 60, theme : currentTheme});
        $("#cancelAddAccountBtn").jqxButton({ width: 60, theme : currentTheme});
        //查询数据字典获取组件数据
        var codeadd = new codeModel({width:150,dropDownWidth:150,autoDropDownHeight:true});
        codeadd.getCommonCode("add-status","ACCOUNT_STATUS");
        codeadd.getCommonCode("add-account-type","ACCOUNT_TYPE");
        codeadd.getCustomCode("add-account-level-sid","/level/findAll","levelName","levelSid",false);
    };

    //初始化编辑账户页面组件
    this.initEditWin = function() {
        $("#edit-account-name").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#edit-balance").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#edit-gift-balance").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#edit-default-space").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#edit-usable-credit").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
        $("#edit-order-space").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme,disabled:true});
        $("#saveEditAccountBtn").jqxButton({ width: 60, theme : currentTheme});
        $("#cancelEditAccountBtn").jqxButton({ width: 60, theme : currentTheme});
        $("#edit-usable-credit").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});

        //查询数据字典获取组件数据
        var codeadd = new codeModel({width:150,dropDownWidth:150,autoDropDownHeight:true});
        codeadd.getCommonCode("edit-status","ACCOUNT_STATUS");
        codeadd.getCustomCode("edit-account-level-sid","/level/findAll","levelName","levelSid",false);
    };

    this.refreshFirstPage=function(){
        this.searchObj['qm.accountNameLike'] = '';
        this.searchObj['qm.accountType']= '';
        this.searchObj['qm.accountLevelSid'] = '';
        this.searchObj['qm.status']= '';
        $("#jqxgridAccount").jqxGrid('applyfilters');
        $('#jqxgridAccount').jqxGrid('refreshfilterrow');
    };
    this.refresh=function(){
        $("#jqxgridAccount").jqxGrid('applyfilters');
        $('#jqxgridAccount').jqxGrid('refreshfilterrow');
        $('#jqxgridAccount').jqxGrid('clearselection');
        $("#jqxgridAccount").jqxGrid('refresh');
    };
    //初始化账户列表
    this.initUserAccountDatagrid = function(){
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "jqxgridAccount",
            url: ws_url + "/rest/billingAccount/findByPage",
            params: me.searchObj
        });
        $("#jqxgridAccount").jqxGrid({
            width: "99.8%",
            theme:currentTheme,
            source: dataAdapter,
            virtualmode: true,
            rendergridrows: function(){
                return dataAdapter.records;
            },
            columnsresize: true,
            pageable: true,
            pagesize: 10,
            autoheight: true,
            autowidth: false,
            sortable: true,
            selectionmode:"singlerow",
            localization: gridLocalizationObj,
            columns: [
                { text: "账户", datafield: 'accountName' ,width:"15%" },
                { text: "账户类型", datafield: 'accountTypeName' ,width:"15%"},
                { text: "账户级别", datafield: 'accountLevelName',width:"11%"},
                { text: "余额(RMB)", datafield: 'balance' ,width:"10%"},
                { text: "赠送余额(RMB)", datafield: 'giftBalance' ,width:"8%"},
                { text: "赠送空间(G)", datafield: 'defaultSpace' ,width:"8%"},
                { text: "购买空间", datafield: 'orderSpace' ,width:"8%"},
                { text: "可用积分", datafield: 'usableCredit' ,width:"8%"},
                { text: "状态", datafield: 'statusName' ,width:"8%"},
                { text: "更新人", datafield: 'updatedBy' ,width:"9%"}
            ],
            showtoolbar : true,
            rendertoolbar : function(toolbar) {
                //设置toolbar操作按钮
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                // module.user.account.list.add
                var accountAddBtn = $("<div><a id='accountAddBtn' data-bind='click: addAccount'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加&nbsp;&nbsp;</a></div>");
                var accountEditBtn = $("<div><a id='accountEditBtn' data-bind='click: editAccount'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var accountDeleteBtn = $("<div><a id='accountDeleteBtn' data-bind='click: delAccount'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-4'></i>删除&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
  			    container.append(accountAddBtn);
                container.append(accountEditBtn);
                container.append(accountDeleteBtn);
            }
        });
        // 添加事件，控制按钮是否可用
        $("#jqxgridAccount").on('rowselect', function (event) {
            $("#accountEditBtn").jqxButton({disabled: false});
            $("#accountDeleteBtn").jqxButton({disabled: false});
        });
    };

    // 初始化弹出window
    this.initPopWindow = function(){
        //初始化新增账户弹窗
        $("#winAddAccount").jqxWindow({
            showCollapseButton : false,
            width : 600,
            height : 215,
            isModal : true,
            autoOpen : false,
            theme : currentTheme,
            cancelButton : $("#cancelAddAccountBtn"),
            initContent : me.initAddWin
        });

        // 初始化修改账户弹窗
        $("#winEditAccount").jqxWindow({
            showCollapseButton : false,
            width : 600,
            height : 215,
            isModal : true,
            autoOpen : false,
            theme : currentTheme,
            cancelButton : $("#cancelEditAccountBtn")
        });
    };

    // 初始化验证规则
    this.initValidator = function(){
        //新增账户验证
        $("#winFormAddAccount").jqxValidator({
            animationDuration: 1
            ,rules: [
                { input: '#add-account-name', message: "不能为空", action: 'keyup, blur', rule: 'required' }
                ,{ input: '#add-account-name', message: "不能超过16个字符", action: 'keyup, blur', rule: 'length=1,16' }
                ,{ input: '#add-balance', message: "不能为空", action: 'keyup, blur', rule: 'required' }
                ,{ input: '#add-balance', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
                ,{ input: '#add-balance', message: "不合法,整数部分最多8位;小数部分最多2位", action: 'keyup, blur', rule: function(input, commit) {return isNumWithDecimal(input, 8, 2, commit);} }
                ,{ input: '#add-gift-balance', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
                ,{ input: '#add-gift-balance', message: "不合法,整数部分最多8位;小数部分最多2位", action: 'keyup, blur', rule: function(input, commit) {return isNumWithDecimal(input, 8, 2, commit);} }
            ]
        });

        //编辑账户验证
        $("#winFormEditAccount").jqxValidator({
            animationDuration: 1
            ,rules: [
                { input: '#edit-account-name', message: "不能为空", action: 'keyup, blur', rule: 'required' }
                ,{ input: '#edit-account-name', message: "不能超过16个字符", action: 'keyup, blur', rule: 'length=1,16' }
                ,{ input: '#edit-account-name', message: "必须输入英文或数字", action: 'keyup, blur', rule: function (input, commit) {return IsEnOrNum(input, commit);}}
                ,{ input: '#edit-balance', message: "不能为空", action: 'keyup, blur', rule: 'required' }
                ,{ input: '#edit-balance', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
                ,{ input: '#edit-balance', message: "不合法,整数部分最多8位;小数部分最多2位", action: 'keyup, blur', rule: function(input, commit) {return isNumWithDecimal(input, 8, 2, commit);} }
                ,{ input: '#edit-gift-balance', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
                ,{ input: '#edit-gift-balance', message: "不合法,整数部分最多8位;小数部分最多2位", action: 'keyup, blur', rule: function(input, commit) {return isNumWithDecimal(input, 8, 2, commit);} }
                ,{ input: '#edit-default-space', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
                ,{ input: '#edit-usable-credit', message: "必须输入数字", action: 'keyup, blur', rule: function (input, commit) {return IsNum(input, commit);}}
            ]
        });
    };

    //初始化操作按钮，编辑 删除按钮默认为disabled
    this.initOperationBtn = function(){
	  	$("#accountAddBtn").jqxButton({width: 50, disabled: false, theme:currentTheme });
        $("#accountEditBtn").jqxButton({ width: 50, disabled: true, theme:currentTheme});
        $("#accountDeleteBtn").jqxButton({ width: 50, disabled: true, theme:currentTheme});
    };
};

var accountBindModel = function(accountObj){
    var me=this;
    //弹出新增账户
    this.addAccount = function () {
        $("#add-account-name").val("");
        $("#add-balance").val("");
        $("#add-gift-balance").val("");
        $('#winAddAccount').jqxWindow('open');
    };
    //提交新增账户的信息
    this.addAccountSubmit = function(){
        //判断是否验证通过
        if($('#winFormAddAccount').jqxValidator('validate')){
            var addAccount = {};
            addAccount.accountName = $('#add-account-name').val();
            addAccount.accountType = $('#add-account-type').val();
            addAccount.accountLevelSid = $('#add-account-level-sid').val();
            addAccount.balance = $('#add-balance').val();
            addAccount.giftBalance = $('#add-gift-balance').val();
            addAccount.defaultSpace = $('#add-default-space').val();
            addAccount.orderSpace = $('#add-order-space').val();
            addAccount.status = $('#add-status').val();

            Core.AjaxRequest({
                url : ws_url + "/rest/billingAccount/addAccount",
                params: addAccount,
                type: 'POST',
                showMsg : true,
                callback : function (data) {
                    $("#winAddAccount").jqxWindow('close');
                    accountObj.refreshFirstPage();

                }
            });
        }
    };

    //弹出编辑用户账户
    this.editAccount = function () {
        var rowindex = $('#jqxgridAccount').jqxGrid('getselectedrowindex');
        if(rowindex >= 0){
            var data = $('#jqxgridAccount').jqxGrid('getrowdata', rowindex);
            $("#edit-account-sid").val(data.accountSid);
            $("#edit-account-name").val(data.accountName);
            $("#edit-account-type").html(data.accountTypeName);
            $("#edit-account-level-sid").val(data.accountLevelSid);
            $("#edit-balance").val((data.balance == null || data.balance === '')?0:data.balance);
            $("#edit-gift-balance").val((data.giftBalance == null || data.giftBalance === '')?0:data.giftBalance);
            $('#edit-default-space').val(data.defaultSpace);
            $('#edit-usable-credit').val((data.usableCredit == null || data.usableCredit === '')?0:data.usableCredit);
            $('#edit-order-space').val(data.orderSpace);
            $("#edit-status").val(data.status);
            $('#winEditAccount').jqxWindow('open');
        }
    };

    //提交编辑用户账户
    this.editAccountSubmit = function() {
        //判断是否验证通过
        if($('#winFormEditAccount').jqxValidator('validate')){
            var editAccount = {};
            editAccount.accountSid = $("#edit-account-sid").val();
            editAccount.accountName = $("#edit-account-name").val();
            editAccount.accountLevelSid = $("#edit-account-level-sid").val();
            editAccount.balance = $("#edit-balance").val();
            editAccount.giftBalance = $("#edit-gift-balance").val();
            editAccount.defaultSpace = $('#edit-default-space').val();
            editAccount.usableCredit = $('#edit-usable-credit').val();
            editAccount.orderSpace = $('#edit-order-space').val();
            editAccount.status = $("#edit-status").val();
            Core.AjaxRequest({
                url : ws_url + "/rest/billingAccount/updateAccount",
                params : editAccount,
                showMsg : true,
                callback : function (data) {
                    me.searchAccount();
                    $("#winEditAccount").jqxWindow('close');
                }
            });
        }
    };

    //删除账户
    this.delAccount = function(){
        var rowindex = $('#jqxgridAccount').jqxGrid('getselectedrowindex');
        if(rowindex >= 0){
            var data = $('#jqxgridAccount').jqxGrid('getrowdata', rowindex);
            Core.confirm({
                message: "确定要删除该账户吗？",
                confirmCallback:function(){
                    Core.AjaxRequest({
                        type: 'POST',
                        showMsg : true,
                        url : ws_url + "/rest/billingAccount/deleteAccount",
                        params : {accountSid : data.accountSid},
                        callback : function (data) {
                            me.searchAccount();
                            accountObj.initOperationBtn();
                        }
                    });
                }
            });
        }
    };

    //按条件搜索
    this.searchAccount = function() {
        accountObj.searchObj['qm.accountNameLike'] = $("#search-account-name").val();
        accountObj.searchObj['qm.userAccountLike'] = $("#search-user-account-name").val();
        accountObj.searchObj['qm.accountType']= $("#search-account-type").val() == "全部" ? '' : $("#search-account-type").val();
        accountObj.searchObj['qm.accountLevelSid'] = $("#search-account-level-sid").val() == "全部" ? '' : $("#search-account-level-sid").val();
        accountObj.searchObj['qm.status']= $("#search-status").val() == "全部" ? '' : $("#search-status").val();
        accountObj.refresh();
    };
};

$(function(){
    //初始化js对象
    var userAccountObj = new initUserAccountPageJs();
    //初始化页面主键
    userAccountObj.init();
    //初始化主键事件
    var accountBindModelObj = new accountBindModel(userAccountObj);
    ko.applyBindings(accountBindModelObj);
});
var depPrizeModel = function(){
    var  me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        "qm.titleLike":null,
        "qm.validStartDtStrLike":null,
        "qm.validToDtStrLike":null
    };
    //查询列表数据
    this.searchDataInfo = function(){
        $("#depositPrizedatagrid").jqxGrid('applyfilters');
        $('#depositPrizedatagrid').jqxGrid('refreshfilterrow');
        $('#depositPrizedatagrid').jqxGrid('clearselection');
    };
    //刷新
    this.refreshDataInfo = function(){
        $("#accountLevelGrid").jqxGrid('updatebounddata');
        $("#accountLevelGrid").jqxGrid('clearselection');
        $("#accountLevelGrid").jqxGrid('refresh');
    };
    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
        $("#search-title").jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $("#search-validStartDtStr").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
        $("#search-validToDtStr").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
        $("#search-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    //初始化验证规则
    this.initValidator = function(){
        // 新增表单验证
        $("#addDpForm").jqxValidator({
            rules: [
                {input: '#add-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input: '#add-minDeposit1', message: '不能为空', action: 'keyup, blur', rule: 'required' }
            ]
        });
        //编辑表单验证
        $("#editDpForm").jqxValidator({
            rules: [
                {input: '#edit-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input: '#edit-minDeposit1', message: '不能为空', action: 'keyup, blur', rule: 'required' },
            ]
        });
        //新增验证成功
        $('#addDpForm').on('validationSuccess', function (event) {
            var addDeprize = JSON.parse($("#addDpForm").serializeJson());
            Core.AjaxRequest({
                url : ws_url + "/rest/depositPrize/addDepositPrize",
                params :addDeprize,
                callback : function (data) {
                    me.refreshDataInfo();
                    $("#addDpWindow").jqxWindow('close');
                },
                failure:function(data){

                }
            });
        });
        //编辑验证成功
        $('#editDpForm').on('validationSuccess', function (event) {
            var editDeprize = JSON.parse($("#editDpForm").serializeJson());
            Core.AjaxRequest({
                url : ws_url + "/rest/depositPrize/updateDepositPrize",
                params :editDeprize,
                callback : function (data) {
                    me.refreshDataInfo();
                    $("#editDpWindow").jqxWindow('close');
                },
                failure:function(data){

                }
            });
        });

    };
    // 初始化充值datagrid的数据源
    this.initDeprizeDatagrid = function(){
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "depositPrizedatagrid" ,
            url: ws_url + "/rest/depositPrize/findAll",
            params: me.searchObj
        });
            $("#depositPrizedatagrid").jqxGrid({
                width: "100%",
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
                selectionmode:"checkbox",
                localization: gridLocalizationObj,
                columns: [
                    { text: '充值奖励名称', datafield: 'title',cellsalign: 'center', align: 'center',width:100},
                    { text: '有效期开始', datafield: 'validStartDt'},
                    { text: '有效期截止', datafield: 'validToDt'},
                    { text: '充值下限1', datafield: 'minDeposit1'},
                    { text: '送现金1', datafield: 'cashGiven1'},
                    { text: '充值下限2', datafield: 'minDeposit2'},
                    { text: '送现金2', datafield: 'cashGiven2',cellsalign: 'center', align: 'center',width:60},
                    { text: '充值下限3', datafield: 'minDeposit3'},
                    { text: '送现金3', datafield: 'cashGiven3'},
                    { text: '描述', datafield: 'des',cellsalign: 'center', align: 'center',width:60}
                ],
                showtoolbar: true,
                // 设置toolbar操作按钮
                rendertoolbar: function(toolbar){
                    var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                    var addBtn = $("<div><a id='jqxAddDeprizeBtn' onclick ='popAddDeprizeItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                    var editBtn = $("<div><a id='jqxEditDeprizeBtn' onclick ='popEditDeprizeItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                    var deleteBtn = $("<div><a id='jqxDeleteDeprizeBtn' onclick='removeDeprizeItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                    toolbar.append(container);
                    container.append(addBtn);
                    container.append(editBtn);
                    container.append(deleteBtn);
                }
            });
        $("#jqxDeleteDeprizeBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
        $("#jqxAddDeprizeBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
        $("#jqxEditDeprizeBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
    };
    // 初始化操作按钮
    this.initOperationBtn = function(){
        $("#jqxDeleteDeprizeBtn").jqxButton({disabled: true});
        $("#jqxEditDeprizeBtn").jqxButton({disabled: true});
    };
    // 初始化弹出window
    this.initPopWindow = function(){
        //初始化弹出新增Window
        $("#addDpWindow").jqxWindow({
            width: 700,
            height:260,
            resizable: false,
            isModal: true,
            autoOpen: false,
            cancelButton: $("#add-cancle-dp"),
            theme: currentTheme,
            modalOpacity: 0.3,
            initContent:function(){
                //初始化新增页面组件
                $("#add-title").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-validStartDt").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
                $("#add-validToDt").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
                $("#add-minDeposit1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-cashGiven1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-minDeposit2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-cashGiven2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-minDeposit3").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-cashGiven3").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-desc").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#add-save-dp").jqxButton({ width: '50',height:"25",theme:currentTheme});
                $("#add-cancle-dp").jqxButton({ width: '50',height:"25",theme:currentTheme});
            }
        });
        //初始化编辑弹出Window
        $("#editDpWindow").jqxWindow({
            width: 700,
            height:260,
            resizable: false,
            isModal: true,
            autoOpen: false,
            cancelButton: $("#edit-cancleDpBtn"),
            theme: currentTheme,
            modalOpacity: 0.3,
            initContent: function () {
                // 初始化编辑用户页面组件
                $("#edit-title").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-validStartDt").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
                $("#edit-validToDt").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
                $("#edit-minDeposit1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-cashGiven1").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-minDeposit2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-cashGiven2").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-minDeposit3").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-cashGiven3").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-desc").jqxInput({placeHolder: "", height: 22, width: 170, minLength: 1,theme:currentTheme});
                $("#edit-saveDpBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
                $("#edit-cancleDpBtn").jqxButton({ width: '50',height:"25",theme:currentTheme});
            }
        });

    };

};

//点击toolbar新增按钮弹出Window
function popAddDeprizeItemWindow(){
    $("#add-title").val("");
    $("#add-validStartDt").val("");
    $("#add-validToDt").val("");
    $("#add-minDeposit1").val("");
    $("#add-cashGiven1").val("");
    $("#add-minDeposit2").val("");
    $("#add-cashGiven2").val("");
    $("#add-minDeposit3").val("");
    $("#add-cashGiven3").val("");
    $("#add-desc").val("");
    var windowW = $(window).width();
    var windowH = $(window).height();
    $("#addDpWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
    $("#addDpWindow").jqxWindow('open');
};

//提交新增数据
function addDpInfoSubmit(){
    // 判断是否通过了验证
    $('#addDpForm').jqxValidator('validate');
};

//弹出编辑用户window
function popEditDeprizeItemWindow(row){
    var row = $('#depositPrizedatagrid').jqxGrid('selectedrowindexes');
    if (row >=0 ){
        var data = $('#depositPrizedatagrid').jqxGrid('getrowdata', row);
        $("#edit-sid").val(data.depositPrizeSid);
        $("#edit-title").val(data.title);
        $("#edit-validStartDt").val(data.validStartDtStr);
        $("#edit-validToDt").val(data.validToDtStr);
        $("#edit-minDeposit1").val(data.minDeposit1);
        $("#edit-cashGiven1").val(data.cashGiven1);
        $("#edit-minDeposit2").val(data.minDeposit2);
        $("#edit-cashGiven2").val(data.cashGiven2);
        $("#edit-minDeposit3").val(data.minDeposit3);
        $("#edit-cashGiven3").val(data.cashGiven3);
        $("#edit-desc").val(data.des);

        var windowW = $(window).width();
        var windowH = $(window).height();
        $("#editDpWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
        $("#editDpWindow").jqxWindow('open');
    }
};

//提交编辑信息
function editDpInfoSubmit(){
    // 判断是否通过了验证
    $('#editDpForm').jqxValidator('validate');
};

//删除信息
function removeDeprizeItem(){
    var depPrize = new depPrizeModel();
   var rowIndex = $("#depositPrizedatagrid").jqxGrid('getselectedrowindex');
    if (rowIndex >0){
        var data = $('#depositPrizedatagrid').jqxGrid('getrowdata', rowIndex);
        //console.log(JSON.stringify(data));
        Core.confirm({
            title:'提示',
            message:'确定要删除吗？',
            confirmCallback:function(){
                Core.AjaxRequest({
                    url:ws_url + "/rest/depositPrize/removeDepositPrize/"+data.depositPrizeSid,
                    showMsg : true,
                    callback : function (data) {
                        me.refreshDataInfo();
                },
                    failure:function(data){}
                });
            }
        });
    }
};

// 查询
function searchDepositPrizeInfo(){
    var depPrize = new depPrizeModel();
    depPrize.searchObj["qm.titleLike"] = $("#search-title").val();
    depPrize.searchObj["qm.validStartDtStrLike"] = $("#search-validStartDtStr").val() ==""?"":($("#search-validStartDtStr").val()+" 00:00:00");
    depPrize.searchObj["qm.validToDtStrLike"] = $("#search-validToDtStr").val() ==""?"":($("#search-validToDtStr").val()+" 23:59:59");
    depPrize.searchDataInfo();
};
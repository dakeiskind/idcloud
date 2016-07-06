var initPageJs = function () {
    var me = this;
    //搜索条件
    this.searchObj = {
        "qm.msgTitle":"",
        "qm.msgType":""
    };

    // 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.initInput = function() {
        //初始搜索组件
        $("#search-msg-title").jqxInput({width: 100, height: 22, minLength: 1, theme: currentTheme});
        var codes = new codeModel({width:100,autoDropDownHeight:true});
        codes.getCommonCode("search-msg-type","MSG_TYPE",true);
        $("#searchBtn").jqxButton({theme: currentTheme});

        //初始化列表
        me.initDatagrid();
        // 初始化弹出window
        me.initPopWindow();
        // 初始化验证规则
        me.initValidator();
    };

    //初始化列表
    this.initDatagrid = function(){
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "jqxgridSiteMsg",
            url: ws_url + "/rest/sitemsg/find",
            params: me.searchObj
        });
        $("#jqxgridSiteMsg").jqxGrid({
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
            localization: gridLocalizationObj
            ,columns: [
                 { text: "消息标题", datafield: "msgTitle"}
                ,{ text: "消息类型", datafield: "msgTypeName"}
                ,{ text: "消息内容", datafield: "msgContent" }
                ,{ text: "创建时间", datafield: "createdDt" }
                ,{ text: "创建人", datafield: "createdBy" }
            ]
            ,showtoolbar: true
            ,rendertoolbar: function (toolbar) {
                //设置toolbar操作按钮
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                var msgAddBtn = $("<div><a id='msgAddBtn' data-bind='click: msgAdd'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>添加消息&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
                container.append(msgAddBtn);
                //初始按钮组件
                $("#msgAddBtn").jqxButton({disabled: false, theme:currentTheme });
            }
        });
    };
    //查询列表数据
    this.searchDataInfo = function(){
        $("#jqxgridSiteMsg").jqxGrid('applyfilters');
        $('#jqxgridSiteMsg').jqxGrid('refreshfilterrow');
        $('#jqxgridSiteMsg').jqxGrid('clearselection');
    };
    //刷新
    this.refreshDataInfo = function(){
        $("#jqxgridSiteMsg").jqxGrid('updatebounddata');
        $("#jqxgridSiteMsg").jqxGrid('clearselection');
        $("#jqxgridSiteMsg").jqxGrid('refresh');
    };
    // 初始化弹出window
    this.initPopWindow = function(){
        $("#winAddSiteMsg").jqxWindow({
             width: 520
            ,height: 260
            ,showCollapseButton: false
            ,isModal: true
            ,autoOpen: false
            ,resizable: false
            ,theme : currentTheme
            ,cancelButton: $("#cancelAddSiteMsgBtn")
            ,initContent:function(){
                $("#add-msg-title").jqxInput({width: 150, height: 22, minLength: 1,theme:currentTheme});
                var codeadd = new codeModel({width:150,autoDropDownHeight:true});
                codeadd.getCommonCode("add-msg-type","MSG_TYPE");
                $("#add-msg-content").jqxInput({width: 426, height: 120, minLength: 1,theme:currentTheme});
                $("#saveAddSiteMsgBtn").jqxButton({theme : currentTheme});
                $("#cancelAddSiteMsgBtn").jqxButton({theme : currentTheme});
            }
        });
    };
    // 初始化验证规则
    this.initValidator = function(){
        $("#winFormAddSiteMsg").jqxValidator({
            animationDuration: 1
            ,rules: [
                 { input: '#add-msg-title', message: "不能为空", action: 'keyup, blur', rule: 'required' }
                ,{ input: '#add-msg-title', message: "不能超过64个字符", action: 'keyup, blur', rule: 'length=0,64' }
                ,{ input: '#add-msg-content', message: "不能为空", action: 'keyup, blur', rule: 'required' }
            ]
        });
    };
};

var siteMsgBindModel = function(obj){
    var nodeSids = "";
    //添加消息
    this.msgAdd = function(){
        $("#add-msg-title").val("");
        $("#add-msg-content").val("");
        $('#winAddSiteMsg').jqxWindow('open');
    };
    //添加消息提交
    this.addSiteMsgSubmit = function(){
        //判断是否验证通过
        if($('#winFormAddSiteMsg').jqxValidator('validate')){
            var addobj = new Object();
            addobj.msgTitle = $("#add-msg-title").val();
            addobj.msgContent = $("#add-msg-content").val();
            addobj.msgType = $("#add-msg-type").val();
            Core.AjaxRequest({
                url : ws_url + "/rest/sitemsg/insert"
                ,params : addobj
                ,callback : function (data) {
                    if(data){
                        Core.alert({
                            title:"提示",
                            message:"发送成功！"
                        });
                        obj.refreshDataInfo();
                        $("#winAddSiteMsg").jqxWindow('close');
                    }
                }
                ,failure:function(data){
                    Core.alert({
                        title:"提示",
                        type:"error",
                        message:"发送失败！"
                    });
                }
            });
        }
    };
    //搜索
    this.searchSiteMsg = function(){
        obj.searchObj["qm.msgTitle"]=$("#search-msg-title").val();
        obj.searchObj["qm.msgType"]=$("#search-msg-type").val()=="全部"?"":$("#search-msg-type").val();
        obj.searchDataInfo();
    };
};

$(function(){
    //初始化js对象
    var siteMsgObj = new initPageJs();
    //初始化页面主键
    siteMsgObj.initInput();
    //初始化主键事件
    var siteMsgBindModelObj = new siteMsgBindModel(siteMsgObj);
    ko.applyBindings(siteMsgBindModelObj);
});
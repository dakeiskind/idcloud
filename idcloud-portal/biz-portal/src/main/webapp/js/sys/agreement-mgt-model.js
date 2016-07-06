var agreementModel = function(){
    var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        "qm.accountNameLike":null,
        "qm.agreementTitleLike":null,
        "qm.dateSignedFromLike":null,
        "qm.dateSignedToLike":null
    };
    //    协议数据
    this.searchAgreementInfo = function(){
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "jqxgridAgreement",
            url: ws_url + "/rest/agreement/find",
            params: me.searchObj
        });
        $("#jqxgridAgreement").jqxGrid({
            source: dataAdapter,
            rendergridrows: function(){
                return dataAdapter.records;
            }
        });
    };

    this.searchAgreementInfos = function(){
        $("#jqxgridAgreement").jqxGrid("gotopage",0);
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "jqxgridAgreement",
            url: ws_url + "/rest/agreement/find",
            params: me.searchObj
        });
        $("#jqxgridAgreement").jqxGrid({
            source: dataAdapter,
            rendergridrows: function(){
                return dataAdapter.records;
            }
        });
    };

    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
        //初始化查询组件
        $("#search-agreement-custom").jqxInput({width: 100, height: 22, minLength: 1, theme: currentTheme});
        $("#search-agreement-title").jqxInput({width: 100, height: 22, minLength: 1, theme: currentTheme});
        $("#search-agreement-startDate-start").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro", formatString: 'yyyy-MM-dd'});
        $("#search-agreement-startDate-end").jqxDateTimeInput({width: '100px', culture: 'zh-CN', formatString: 'd', height: 22,showFooter:true,clearString:'清除',todayString:'今天',allowNullDate: true,value: null,theme:"metro", formatString: 'yyyy-MM-dd'});;
        $("#search-agreement-btn").jqxButton({theme: currentTheme});
    };
    // 初始化验证规则
    this.initValidator = function(){
        // 新增协议表单验证
        $('#addAgreementForm').jqxValidator({
            rules:[
                {input: '#add-agreement-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input: '#add-agreement-price', message: '不能为空', action: 'keyup, blur', rule: 'required' },
            ]
        });
        //编辑表单验证成功
        $('#editAgreementForm').jqxValidator({
            rules: [
                {input: '#edit-agreement-title', message: '不能为空', action: 'keyup, blur', rule: 'required' },
                {input: '#edit-agreement-price', message: '不能为空', action: 'keyup, blur', rule: 'required' },
            ]
        });
        //新增协议表单验证成功
        $('#addAgreementForm').on('validationSuccess', function (event) {
            var addCode = JSON.parse($("#addCodeForm").serializeJson());
            Core.AjaxRequest({
                url : ws_url + "/rest/agreement/insertAgreement",
                params :addCode,
                callback : function (data) {
                    me.searchCodeInfo();
                    $("#addCodeWindow").jqxWindow('close');

                },
                failure:function(data){

                }
            });
        });
        //修改协议验证成功
        $('#editAgreementForm').on('validationSuccess', function (event) {
            var code = Core.parseJSON($("#editCodeForm").serializeJson());
            Core.AjaxRequest({
                url : ws_url + "/rest/agreement/updateAgreement",
                params :code,
                callback : function (data) {
                    me.searchCodeInfo();
                    $("#editCodeWindow").jqxWindow('close');
                },
                failure:function(data){

                }
            });
        });
    };
    //初始化协议atagrid的数据源
    this.initAgreementDatagrid = function(){
        var dataAdapter = Core.getPagingDataAdapter({
            gridId: "jqxgridAgreement",
            url: ws_url + "/rest/agreement/find",
            params: me.searchObj
        });
        $("#jqxgridAgreement").jqxGrid({
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
                { text: '客户', datafield: 'accountName',cellsalign: 'center', align: 'center',width:300},
                { text: '协议标题', datafield: 'agreementTitle',align: 'center',cellsalign: 'center',width:100},
                { text: '协议主要内容', datafield: 'brief',align: 'center',cellsalign: 'center',width:300},
                { text: '签订日', datafield: 'dateSigned',align: 'center',cellsalign: 'center',width:300},
                { text: '签订价格', datafield: 'price',cellsalign: 'center', align: 'center',width:70},
                { text: '付款情况描述', datafield: 'payment',cellsalign: 'center', align: 'center',width:220},
                { text: '协议文件', datafield: 'agreementFile',cellsalign: 'center', align: 'center',width:210}
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                var addBtn = $("<div><a id='jqxAddAgreementBtn' onclick ='popAddAgreementItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='jqxEditAgreementBtn' onclick ='popEditAgreementItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>编辑&nbsp;&nbsp;</a></div>");
                var deleteBtn = $("<div><a id='jqxDeleteAgreementBtn' onclick='removeAgreementItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                var refreshBtn = $("<div><a id='jqxRefreshAgreementBtn' onclick='refreshAgreementItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-loop-alt'></i>刷新&nbsp;&nbsp;</a></div>");
                toolbar.append(container);
                container.append(refreshBtn);
                container.append(addBtn);
                container.append(editBtn);
                container.append(deleteBtn);
            }
        });
        //初始化toolbar上的操作按钮
        $("#jqxDeleteAgreementBtn").jqxButton({ width: '60',theme:currentTheme,height: '18px'});
        $("#jqxAddAgreementBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
        $("#jqxEditAgreementBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
        $("#jqxRefreshAgreementBtn").jqxButton({ width: '60',height: '18px', theme:currentTheme});
    };

    //初始化toolbar上按钮是否可点击
    this.initOperationBtn = function(){
        $("#jqxDeleteAgreementBtn").jqxButton({disabled: true});
    };
    // 初始化弹出window
    this.initPopWindow = function() {
        //初始化新增window
        $("#addAgreementWindow").jqxWindow({
            width: 700,
            height: 260,
            resizable: false,
            isModal: true,
            autoOpen: false,
            cancelButton: $("#add-cancle-agreement-btn"),
            theme: currentTheme,
            modalOpacity: 0.3,
            initContent: function () {
                var codeadd = new codeModel({width: 150, autoDropDownHeight: true});
                codeadd.getCommonCode("add-agreement-custom", "ACCOUNT_NAME");
                $("#add-agreement-title").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#add-agreement-date-signed").jqxDateTimeInput({
                    width: '100px',
                    culture: 'zh-CN',
                    formatString: 'd',
                    height: 22,
                    showFooter: true,
                    clearString: '清除',
                    todayString: '今天',
                    allowNullDate: true,
                    value: null,
                    theme: "metro",
                    formatString: 'yyyy-MM-dd'
                });
                $("#add-agreement-price").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#add-agreement-desc").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                //$("#add-agreement-file").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#add-agreement-content").jqxInput({width: 426, height: 120, minLength: 1, theme: currentTheme});
                $("#add-save-agreement-btn").jqxButton({theme: currentTheme});
                $("#add-cancle-agreement-btn").jqxButton({theme: currentTheme});
            }
        });
        $("#editAgreementWindow").jqxWindow({
            width: 700,
            height: 260,
            resizable: false,
            isModal: true,
            autoOpen: false,
            cancelButton: $("#edit-cancle-agreement-btn"),
            theme: currentTheme,
            modalOpacity: 0.3,
            initContent: function () {
                var codeadd = new codeModel({width: 150, autoDropDownHeight: true});
                codeadd.getCommonCode("edit-agreement-custom", "ACCOUNT_NAME");
                $("#edit-agreement-title").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#edit-agreement-date-signed").jqxDateTimeInput({
                    width: '100px',
                    culture: 'zh-CN',
                    formatString: 'd',
                    height: 22,
                    showFooter: true,
                    clearString: '清除',
                    todayString: '今天',
                    allowNullDate: true,
                    value: null,
                    theme: "metro",
                    formatString: 'yyyy-MM-dd'
                });
                $("#edit-agreement-price").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#edit-agreement-desc").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#edit-agreement-file").jqxInput({width: 150, height: 22, minLength: 1, theme: currentTheme});
                $("#edit-agreement-content").jqxInput({width: 426, height: 120, minLength: 1, theme: currentTheme});
                $("#edit-save-agreement-btn").jqxButton({theme: currentTheme});
                $("#edit-cancle-agreement-btn").jqxButton({theme: currentTheme});
            }
        });
    };
};

// 弹出新增用户window
function popAddAgreementItemWindow (){
    var codeadd = new codeModel({width:170,autoDropDownHeight:true});
    codeadd.getCommonCode("add-agreement-custom", "ACCOUNT_NAME",true);
    $("#add-agreement-title").val("");
    $("#add-agreement-date-signed").val("");
    $("#add-agreement-price").val("");
    $("#add-agreement-desc").val("");
    //$("#add-agreement-file").val("");
    $("#add-agreement-content").val("");
    var windowW = $(window).width();
    var windowH = $(window).height();
    $("#addAgreementWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
    $("#addAgreementWindow").jqxWindow('open');
};

//提交新增用户数据
function addAgreementSubmit (){
    // 判断是否通过了验证
    $('#addAgreementForm').jqxValidator('validate');
};

//弹出编辑协议
function popEditAgreementItemWindow(row){
    var row = $('#jqxgridAgreement').jqxGrid('selectedrowindexes');
    if(row >= 0){
        var data = $('#jqxgridAgreement').jqxGrid('getrowdata', row);
        var codeadd = new codeModel({width:170,autoDropDownHeight:true});
        codeadd.getCommonCode("add-agreement-custom", "ACCOUNT_NAME",true);
        $("#edit-agreement-title").val("");
        $("#edit-agreement-date-signed").val("");
        $("#edit-agreement-price").val("");
        $("#edit-agreement-desc").val("");
        $("#edit-agreement-file").val("");
        $("#edit-agreement-content").val("");
        var windowW = $(window).width();
        var windowH = $(window).height();
        $("#editAgreementWindow").jqxWindow({ position: { x: (windowW-700)/2, y: (windowH-260)/2 } });
        $("#editAgreementWindow").jqxWindow('open');
    }
};

/** 提交编辑协议信息 */
function editAgreementSubmit (){
    // 判断是否通过了验证
    $('#editAgreementForm').jqxValidator('validate');
};

/** 删除用户 */
function removeCodeItem (){

    var rowindex = $('#jqxgridAgreement').jqxGrid('getselectedrowindexes');
    var codeSids = "";
    if(rowindex.length > 0){
        for(var i=0;i<rowindex.length;i++){
            var data = $('#codeOsVersionDatagrid').jqxGrid('getrowdata', rowindex[i]);
            if(i == rowindex.length-1){
                if(data != null && data != "" && data != 'undefined' ){
                    codeSids += data.codeSid;
                }

            }else{
                if(data != null && data != "" && data != 'undefined' ){
                    codeSids += data.codeSid + ",";
                }
            }
        }
        Core.confirm({
            title:"提示",
            message:"确定要删除选中的操作系统吗？",
            confirmCallback:function(){
                Core.AjaxRequest({
                    url : ws_url + "/rest/system/deleteSystemCode?codeSids="+codeSids,
                    type:"get",
                    callback : function (data) {
                        // 清除选择项
                        $('#codeOsVersionDatagrid').jqxGrid('clearselection');
                        // 刷新datagrid
                        var code = new syscodeModel();
                        code.searchCodeInfo();
                    }
                });
            }
        });
    }
};

/**刷新*/
function refreshAgreementItem(){
    var agreement = new agreementModel();
    agreement.searchAgreementInfo();
}

/** 查询大客户协议 */
function searchAgreement(){
    var agreement = new agreementModel();
    agreement.searchObj["qm.accountNameLike"] = $("#search-agreement-custom").val();
    agreement.searchObj["qm.agreementTitleLike"] = $("#search-agreement-title").val();
    agreement.searchObj["qm.dateSignedFromLike"] = $("#search-agreement-startDate-start").val()==""?"":( $("#search-agreement-startDate-start").val()+" 00:00:00");
    agreement.searchObj["qm.dateSignedToLike"] = $("#search-agreement-startDate-end").val()==""?"":( $("#search-agreement-startDate-end").val()+" 23:59:59");
    agreement.searchAgreementInfos();
};

//文件上传
var fileIndex = 0;
function addUploadInput(obj){
    var uploadDiv = '<tr><td colspan="2">'+
        '<form id="upload'+fileIndex+'" name="upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">'+
        '<div class="box" style="margin-top:6px;">'+
        '<input type="file" class="uploadFile" name="fileAttach'+fileIndex+'" onchange="getFile(this)" />'+
        '<input type="text" name="copyFile'+fileIndex+'" class="textbox" id="fileC"/>'+
        '<a href="javascript:void(0);" class="link">浏览</a>&nbsp;&nbsp;'+
        '<a href="javascript:void(0);" style="margin-left: 5px;" class="link" onclick="removeUploadFile(this)">移除</a>'+
        '</div>' +
        '</form>'+
        '</td></tr>';
    $($($(obj).parents("table")[0]).find("tbody[name='uploadInputDiv']")).append(uploadDiv);
    console.log("addUploadInput");
    fileIndex++;
}

function submitUploadFile(obj){
    var fileFlag = true;
    var files = $($(obj).parents("table")[0]).find("[class='uploadFile']");
    console.log(files);
    for(var i=0;i<files.length;i++){
        var filename = $(files[i]).val();
        console.log(filename);
        filename = filename.substring(filename.lastIndexOf("\\")+1,filename.length);
        console.log(filename);
        if(filename ==""|| filename==null){
            fileFlag = false;
        }
    }
    if(fileFlag){
        var uploadForms = $("form[id^='upload']");
        var uploadFlag = true;
        for(var i=0;i<uploadForms.length;i++){
            if("1" != $($(uploadForms[i]).find("input[class='uploadFile']")).attr("flag")){
                Core.AjaxFormSubmit({
                    formId : $(uploadForms[i]).attr("id"),
                    url : ws_url+"/rest/mgtObj/uploadMgtObjFile",
                    async : false,
                    callback : function(data) {
                        if(null==data){
                            uploadFlag = false;
                        }else{
                            $($(uploadForms[i]).find("input[class='uploadFile']")[0]).attr("uploadFileSid",data[0]);
                        }
                    }
                });
            }
        }
        if(uploadFlag){
            $("a[class='link']").remove();
            $("input[class='uploadFile']").attr("disabled","disabled");
            $("input[class='uploadFile']").attr("flag","1");
            $("input[name^='copyFile']").attr("disabled","disabled");
        }
    }else{
        Core.alert({
            title:"提示",
            message:"请选择要上传的文件！"
        });
        return;
    }
}

function removeUploadFile(obj){
    $($(obj).parents("tr")[0]).remove();
    console.log("removeUploadFile");
}

function removeUploadedFile(obj){
    var sid = $(obj).attr("attachId");
    var sids = $("#projectFiles").val();
    var fileId = sids + sid + ",";
    $("#edit-mgtobj-fileSids").val(fileId);
    $($(obj).parents("tr")[0]).remove();
}

function getFile(obj){
    var file_name = $(obj).val();
    console.log(file_name);
    file_name = file_name.substring(file_name.lastIndexOf("\\")+1,file_name.length);
    console.log(file_name);
    $($(obj).next()).val(file_name);
    //$("#fileC").val(file_name);
    console.log("getFile");
}
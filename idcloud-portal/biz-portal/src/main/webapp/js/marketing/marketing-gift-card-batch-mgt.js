var myDesk;


var batchStatusCodeAndName=$.extend({},vrCodeAndName,{codeCategory:'BATCH_STATUS'});
var rightsActivation=true;
var rightsGenerate=true;
var rightsInvalid=true;
function MyDesk() {
    //getModuleRights();
    //for(var i=0;i<moduleRights.length;i++){
    //    if(moduleRights[i].operationId=="rightsActivation" && moduleRights[i].isVisible=="false"){
    //        rightsActivation=false; continue;}
    //    if(moduleRights[i].operationId=="rightsGenerate" && moduleRights[i].isVisible=="false"){
    //        rightsGenerate=false;continue;}
    //    if(moduleRights[i].operationId=="rightsInvalid" && moduleRights[i].isVisible=="false"){
    //        rightsInvalid=false;continue;}
    //}
    var me = this;
    this.searchOrderObj={};
    this.fetchSearchData=function(){
        this.searchOrderObj['qm.batchNo']=$('#batchNoSch').val();
        this.searchOrderObj['qm.cardName']=$('#cardNameSch').val();
        this.searchOrderObj['qm.status']=$('#statusSch').val()=='all'?null:$('#statusSch').val();

    };
    this.initialize=function(){

        this.initSearchCondition();
        this.initInput();
        this.initContentList();

        this.initOperation();
        this.initValidation();
        this.initOperationBtnStatus();
    };
    this.initSearchCondition=function(){
        $('#batchNoSch').jqxInput({placeHolder: "", height: 20, width: 100, minLength: 1,theme:currentTheme});
        $('#cardNameSch').jqxInput({placeHolder: "", height: 20, width: 100, minLength: 1,theme:currentTheme});
        //$('#statusSch').jqxDropDownList({
        //    selectedIndex:0,
        //
        //    source :batchStatusCodeAndName.getDatas(),
        //    displayMember : "codeDisplay",
        //    valueMember : "codeValue",
        //    width : 100,
        //    height : 20,
        //    dropDownHeight:100
        //});
        //$("#statusSch").jqxDropDownList('insertAt', { label:"全部" ,value:'all'},0);
        var codesearch = new codeModel({width:100,autoDropDownHeight:true});
        codesearch.getCommonCode("statusSch","BATCH_STATUS",true);
        $("#searchBtn").jqxButton({ width: 50, height: 20, theme : currentTheme});
        $('#searchBtn').click(function(){
            me.fetchSearchData();
            me.search();
            me.initOperationBtnStatus();
        });
    };
    this.getRowData=function(id){
        if(this.dataAdapter.records==null){
            return null;
        }
        for(var i=0;i<this.dataAdapter.records.length;i++){
            var dt=this.dataAdapter.records[i];
            if(dt.batchSid==id){
                return dt;
            }
        }
        return null;
    };
    this.dataAdapter;
    this.initContentList=function(){
        me.fetchSearchData();
        //this.dataAdapter = Core.NewDataAdapter({gridId:"mygrid",url:ws_url+"/rest/marketing/giftCardBatchs",params:me.searchOrderObj,type:'GET',
        //    setting:{formatData: function (data) {
        //        if(data['sortdatafield']==='statusName'){
        //            data['sortdatafield']='status';
        //        }
        //        return data;
        //    }}});
        this.dataAdapter = Core.getPagingDataAdapter({
            gridId: "mygrid",
            url: ws_url + "/rest/giftCardBatchs/getGiftCardBatchs",
            type:'GET',
            params: me.searchObj
        });
        $("#mygrid").jqxGrid({
            width: "100%"
            ,theme:currentTheme
            ,source:me.dataAdapter
            ,pageable: true
            ,pagesize: 10
            //,pagesizeoptions: pagesizeoptions
            ,autoHeight: true
            ,sortable: true
            ,localization: gridLocalizationObj
            ,selectionmode:"singlerow"
            ,virtualmode: true
            ,enabletooltips: true,enablebrowserselection: true
            ,altrows: true,
            rendergridrows: function(){
                //for (var i = 0; i < dataAdapter.records.length; i++) {
                //    dataAdapter.records[i].status == '1'
                //        ? dataAdapter.records[i].statusName =
                //        '已激活'
                //        : dataAdapter.records[i].statusName =
                //        '未激活';
                //}
                //return dataAdapter.records;
                var rows=me.dataAdapter.records;
                for(var i=0;i<rows.length;i++){
                    //var row=rows[i];
                    //if(row!=null){
                    //    row['statusName']=batchStatusCodeAndName.getNameByCode(row['status']);
                    //}
                    rows[i].status == '1'
                            ? rows[i].statusName =
                            '已激活'
                            : rows[i].statusName =
                            '未激活';
                }
                return rows;
            },
            clipboard:true,
            columnsresize:true,
            columns: [
                { text: "批次号", datafield: 'batchNo',width:170},
                { text: "礼品卡名称", datafield: 'cardName'},
                { text: "金额", datafield: 'faceValue',width:100,align:'center',cellsalign:'right',cellsformat:'c2'},
                { text: "有效期开始", datafield: 'validStart',width:100,align:'center',cellsalign:'center',cellsrenderer:dateRender},
                { text: "有效期截止",datafield:'validTo',width:100,align:'center',cellsalign:'center',cellsrenderer:dateRender},
                { text: "发行量", datafield: 'quantity',width:100,align:'center',cellsalign:'center',cellsformat:'n'},
                { text: "状态",datafield:'statusName',width:100,align:'center',cellsalign:'center'},
                { text: "未激活量",datafield:'inactiveNumber',width:60,align:'center',cellsalign:'center'},
                {text: "操作",datafield:'batchSid',width:80,sortable:false,align:'center',cellsrenderer:function (rowIndex, columnfield, value, defaulthtml, columnproperties, rowdata) {
                    //var html='<div style="overflow: hidden;  text-overflow: ellipsis; padding-bottom: 2px; text-align: center; margin-right: 2px; margin-left: 4px; margin-top: 4px;">';
                    var html = '<div style="text-align: center;">';
                    //末生成

                    if(rowdata.status==0){
                        if(rightsActivation==true)
                            html += '<i class="icon-ok-squared icons-18-disabled" title="'+"批量激活"+'"></i>';
                        if(rightsGenerate==true)
                            html += '<a class="icon-doc-add icons-18-green2" title="'+"生成礼品卡"+'" href="javascript:myDesk.genGiftCard(\''+rowdata.batchSid+'\');void(0);"></a>';
                        if(rightsInvalid==true)
                            html += '<a class="icon-cancel-squared icons-18-red" title="'+"作废"+'" href="javascript:myDesk.invalidateGiftCard(\''+rowdata.batchSid+'\');void(0);"></a>';
                    }else{
                        //已生成
                        if(rightsActivation==true){
                            if(rowdata.status==1 && rowdata.inactiveNumber>0){
                                html += '<a class="icon-ok-squared icons-18-green2" title="'+"批量激活"+'" href="javascript:myDesk.activeGiftCard(\''+rowdata.batchSid+'\');void(0);"></a>';
                            }else {
                                html += '<i class="icon-ok-squared icons-18-disabled" title="'+"批量激活"+'"></i>';
                            }
                        }
                        if(rightsGenerate==true)
                            html += '<i class="icon-doc-add icons-18-disabled" title="'+"生成礼品卡"+'"></i>';
                        if(rightsInvalid==true)
                            html += '<i class="icon-cancel-squared icons-18-disabled" title="'+"作废"+'"></i>';
                    }
                    html+='</div>';
                    return html;
                }}
            ],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                //var container = $("<div class='jqxGridToolbarDiv'></div>");
                //
                //
                //var addBtn = $("<div class='mod'><div class='button_01' id='addBtn'><i class='icons-blue icon-plus-3'></i>添加</div></div>");
                //var editBtn = $("<div class='mod'><div class='button_01' id='editBtn'><i class='icons-blue icon-edit'></i>编辑</div></div>");
                var container = $("<div  id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                var addBtn = $("<div><a id='addBtn'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                var editBtn = $("<div><a id='editBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit' ></i>编辑&nbsp;&nbsp;</a></div>");
                container.append(addBtn);
                container.append(editBtn);

                toolbar.append(container);
            }

        });
    };
    // 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.initInput = function(){
        this.initAddWindow();
        this.initModWindow();

    };
    this.initAddWindow=function(){
        //---新增---
        $('#addvalidStart').jqxDateTimeInput({width: 156, height: 20,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});
        $('#addvalidTo').jqxDateTimeInput({width: 156, height: 20,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});

        $("#addcardName").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});
        $("#addfaceValue").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});
        $("#addquantity").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});
        $("#saveAdd").jqxButton({theme:currentTheme});
        $("#saveAdd").click(function (){
            //判断是否验证通过
            if(!$('#addWindow').jqxValidator('validate')){
                return;
            }
            me.add();

        });
        $("#returnAdd").jqxButton({theme:currentTheme});
        $("#returnAdd").click(function (){
            $('#addWindow').jqxValidator('hide');
            $("#addWindow").jqxWindow('close');
        });
        $("#addWindow").on('close',function(){
            $('#addWindow').jqxValidator('hide');
        });
    };
    this.initModWindow=function(){


        //---修改---
        $('#modBatchNo').jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme,disabled:true});
        $('#modvalidStart').jqxDateTimeInput({width: 156, height: 20,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});
        $('#modvalidTo').jqxDateTimeInput({width: 156, height: 20,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});

        $("#modcardName").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});
        $("#modfaceValue").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});
        $("#modquantity").jqxInput({placeHolder: "", height: 20, width: 150, minLength: 1,theme:currentTheme});

        $("#saveModifyBtn").jqxButton({theme:currentTheme});
        $("#saveModifyBtn").on('click', function (){
            //判断是否验证通过
            if(!$('#modifyWindow').jqxValidator('validate')){
                return;
            }
            me.modify();

        });
        $("#returnModifyBtn").jqxButton({theme:currentTheme});
        $("#returnModifyBtn").on('click', function (){
            $('#modifyWindow').jqxValidator('hide');
            $("#modifyWindow").jqxWindow('close');
        });
        $("#modifyWindow").on('close',function(){
            $('#modifyWindow').jqxValidator('hide');
        });
    };

    this.initValidation=function(){
        $('#addWindow').jqxValidator({ rules: [
            { input: '#addvalidStart', message: "有效开始时期不能为空\!", action: 'keyup', rule:function(){
                var v=$('#addvalidStart').val();
                if(v==null||v==''){
                    return false;
                }else{
                    return true;
                }

            } },

            { input: '#addvalidTo', message: "有效结束日期不能为空\!", action: 'keyup', rule: function(){
                var v=$('#addvalidTo').val();
                if(v==null||v==''){
                    return false;
                }else{
                    return true;
                }

            } },
            { input: '#addcardName',message:"礼品卡名称不能为空\!",action:'keyup',rule:'required'},
            { input: '#addfaceValue',message:"面额不能为空且必须是数字\!",action:'keyup',
                rule: function(){
                    var v=$('#addfaceValue').val();
                    if(v===null||v===''){
                        return false;
                    }else{
                        if(isNaN(v))
                            return false;
                        else{
                            var vn=parseFloat(v);
                            if(vn>0){
                                return true;
                            }else{
                                return false;
                            }
                        }
                    }

                }},
            { input: '#addquantity',message:"发行量不能为空且必须是数字\!",action:'keyup', rule: function(){
                var v=$('#addquantity').val();
                if(v===null||v===''){
                    return false;
                }else{
                    if(isNaN(v))
                        return false;
                    else{
                        var vn=parseFloat(v);
                        if(vn>0){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }

            }}
        ]});
        $('#modifyWindow').jqxValidator({ rules: [
            { input: '#modvalidStart', message: "有效期开始", action: 'keyup', rule:function(){
                var v=$('#modvalidStart').val();
                if(v==null||v==''){
                    return false;
                }else{
                    return true;
                }

            } },

            { input: '#modvalidTo', message:"有效期截止", action: 'keyup', rule: function(){
                var v=$('#modvalidTo').val();
                if(v==null||v==''){
                    return false;
                }else{
                    return true;
                }

            } },
            { input: '#modcardName',message:"礼品卡名称不能为空\!",action:'keyup',rule:'required'},
            { input: '#modfaceValue',message:"面额不能为空且必须是数字\!",action:'keyup', rule: function(){
                var v=$('#modfaceValue').val();
                if(v===null||v===''){
                    return false;
                }else{
                    if(isNaN(v))
                        return false;
                    else{
                        var vn=parseFloat(v);
                        if(vn>0){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }

            }},
            { input: '#modquantity',message:"发行量不能为空且必须是数字\!",action:'keyup', rule: function(){
                var v=$('#modquantity').val();
                if(v===null||v===''){
                    return false;
                }else{
                    if(isNaN(v))
                        return false;
                    else{
                        var vn=parseFloat(v);
                        if(vn>0){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }

            }}]});

    };
    this.clearAddInfo=function(){
        $("#addcardName").val("");
        $("#addfaceValue").val("");
        $("#addquantity").val("");
        $("#addvalidStart").val("");
        $("#addvalidTo").val("");
    };
    this.initOperation=function(){
        $("#addBtn").on('click', function () {
            $('#addWindow').jqxWindow({
                showCollapseButton: true, height: 270, width: 330,isModal: true,theme:currentTheme
            });
            me.clearAddInfo();
            $('#addWindow').jqxWindow('open');
        });

        // 控制按钮是否可用
        $("#mygrid").on('rowselect', function (event) {

            var args = event.args;
            var index = args.rowindex;
            var data = $('#mygrid').jqxGrid('getrowdata', index);

            $("#editBtn").jqxButton({disabled: false});

        });
        $('#editBtn').click(function(){

            var rowindex = $('#mygrid').jqxGrid('getselectedrowindex');
            if(rowindex==-1){
                Core.alert({message:"请先选择一条数据"});
                return;
            }


            var data = $('#mygrid').jqxGrid('getrowdata', rowindex);
            if(data.status!=0){
                Core.alert({message:"只有未生成的批次才能修改\!"});
                return;
            }
            $('#modifyWindow').jqxWindow({
                showCollapseButton: true, height: 310, width: 330,isModal: true,theme:currentTheme
            });

            $("#modBatchSid").val(data.batchSid);
            $("#modBatchNo").val(data.batchNo);
            $("#modvalidStart").val((data.validStart==null||data.validStart=='')?'':(data.validStart.toDate().format('yyyy-MM-dd')));
            $("#modvalidTo").val((data.validTo==null||data.validTo=='')?'':(data.validTo.toDate().format('yyyy-MM-dd')));
            $("#modcardName").val(data.cardName);
            $("#modfaceValue").val(data.faceValue);
            $("#modquantity").val(data.quantity);


            var dcs=data.distributeChannel==null||data.distributeChannel==''?[]:data.distributeChannel.split(',');
            for(var i=0;i<dcs.length;i++){
                $("#moddistributeChannel").jqxDropDownList('checkItem',dcs[i]);
            }
            $("#modremarks").val(data.remarks);


            $('#modifyWindow').jqxWindow('open');
        });

    };
    this.initOperationBtnStatus=function(){

        $("#addBtn").jqxButton({disabled: false,theme:currentTheme });
        $("#editBtn").jqxButton({disabled: true,theme:currentTheme });
        //hideBtns();
    };
    this.add=function(){

        $("#saveAdd").jqxButton({disabled:true});
        var url=ws_url+"/rest/giftCardBatchs/addGiftCardBatch";
        var requestData = {};
        requestData.cardName=$("#addcardName").val();
        requestData.faceValue=$("#addfaceValue").val();
        requestData.quantity=$("#addquantity").val();
        requestData.validStartStr=new Date($("#addvalidStart").val()).format('yyyy-MM-dd');
        requestData.validToStr=new Date($("#addvalidTo").val()).format('yyyy-MM-dd');

        Core.AjaxRequest({
            url : url
            ,type:'POST'
            ,params : requestData
            ,showMsg : true
            ,async: false
            ,callback : function (data) {
                me.fetchSearchData();
                me.refresh();
                me.initOperationBtnStatus();
                $('#addWindow').jqxValidator('hide');
                $("#addWindow").jqxWindow('close');
                Core.alert({message:"添加成功！"});
            }
            ,failure:function(data){}
        });
        $("#saveAdd").jqxButton({disabled:false});
    };
    this.genGiftCard=function(batchSid){
        var url=ws_url+"/rest/giftCardBatchs/"+batchSid+"/genGiftCard";
        var requestData = {};
        Core.confirm({title:"请确认",message:"要生成礼品卡吗?",confirmCallback:function(){
            Core.AjaxRequest({
                url : url
                ,type:'POST'
                ,params : requestData
                ,showMsg : true
                ,async: false
                ,callback : function (data) {
                    me.fetchSearchData();
                    me.refresh();
                    me.initOperationBtnStatus();
                    Core.alert({message:"成功生成礼品卡！"});
                }
                ,failure:function(data){}
            });
        }});
    };
    this.gotoManageCard=function(batchNo){
        window.location.href=ctx+"/pages/personal/billing-gift-card-mgt.jsp?batchNo="+batchNo;
    };
    this.activeGiftCard=function(batchSid){
        var url=ws_url+"/rest/marketing/giftCardBatchs/"+batchSid+"/activateGiftCard";
        var requestData = {};
        Core.confirm({title:"请确认",message:"要激活该批次下的礼品卡吗?",confirmCallback:function(){
            Core.AjaxRequest({
                url : url
                ,params : requestData
                ,showMsg : true
                ,async: false
                ,type:'PUT'
                ,callback : function (data) {
                    me.fetchSearchData();
                    me.refresh();
                }
                ,failure:function(data){}
            });

        }});

    };
    this.invalidateGiftCard=function(batchSid){
        var url=ws_url+"/rest/marketing/giftCardBatchs/"+batchSid+"/invalid";
        var requestData = {};
        Core.confirm({title:"请确认",message:"要作废该批次下的礼品卡吗?",confirmCallback:function(){
            Core.AjaxRequest({
                url : url
                ,params : requestData
                ,showMsg : true
                ,async: false
                ,type:'DELETE'
                ,callback : function (data) {
                    me.fetchSearchData();
                    me.refresh();
                }
                ,failure:function(data){}
            });

        }});


    };
    this.search = function(){
        $("#mygrid").jqxGrid('applyfilters');
        $('#mygrid').jqxGrid('refreshfilterrow');
        $('#mygrid').jqxGrid('clearselection');
    };
    this.refresh=function(){
        $('#mygrid').jqxGrid('clearselection');
        $('#mygrid').jqxGrid('clear');
        $("#mygrid").jqxGrid('updatebounddata', 'cells');
    };
    this.modify=function(){
        $("#saveModifyBtn").jqxButton({disabled:true});
        var url=ws_url+"/rest/marketing/giftCardBatchs";
        var requestData = {};
        requestData.batchSid=$("#modBatchSid").val();
        requestData.validStart=new Date($("#modvalidStart").val()).format('yyyy-MM-dd');
        requestData.validTo=new Date($("#modvalidTo").val()).format('yyyy-MM-dd');
        requestData.cardName=$("#modcardName").val();
        requestData.faceValue=$("#modfaceValue").val();
        requestData.quantity=$("#modquantity").val();

        Core.AjaxRequest({
            url : url
            ,params : requestData
            ,showMsg : true
            ,type : 'PUT'
            ,async: false
            ,callback : function (data) {
                me.refresh();

                $('#modifyWindow').jqxValidator('hide');
                $("#modifyWindow").jqxWindow('close');
            }
            ,failure:function(data){}
        });
        $("#saveModifyBtn").jqxButton({disabled:false});
    };

    this.addTab=function(batchNo){
        var title="礼品卡管理";
        var htmlContent='<iframe width="100%" height="100%" frameborder="0" seamless="seamless" onLoad="iFrameHeight(this);" id="'+batchNo+'" src="'+ctx+'/pages/personal/billing-gift-card-mgt.jsp?batchNo='+batchNo+'" scrolling="auto"></iframe>';
        if($('#mytabs').jqxTabs('length')==1)
            $('#mytabs').jqxTabs('addLast',title,htmlContent);
        else{

            $('#mytabs').jqxTabs('setContentAt',1,htmlContent);
        }
        $('#mytabs').jqxTabs('select',1);
    };

}
$(function(){
    //---初始化jqxTabsProduct---
//  		$('#mytabs').jqxTabs({width : '100%', height : '100%', position : 'top', showCloseButtons: true, theme : currentTheme});
//		$('#mytabs').jqxTabs('hideCloseButtonAt', 0);
    myDesk  = new MyDesk();

    // 初始化页面组件
    myDesk.initialize();
});

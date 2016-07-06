var myDesk;


var cardStatusCodeAndName=$.extend({},vrCodeAndName,{codeCategory:'GIFT_CARD_STATUS'
});

function MyDesk() {
    //getModuleRights();
    var me = this;
    this.searchOrderObj={};
    this.fetchSearchData=function(){
        this.searchOrderObj['qm.batchNo']=$('#batchNoSch').val();
        this.searchOrderObj['qm.cardNo']=$('#cardNoSch').val();
        this.searchOrderObj['qm.cardName']=$('#griftCardName').val();
        this.searchOrderObj['qm.chargeAccount']=$('#chargedAccount').val();
        this.searchOrderObj['qm.status']=$('#statusSch').val()=='all'?null:$('#statusSch').val();
        this.searchOrderObj['qm.userClient']=$('#userClient').val();
        this.searchOrderObj['qm.validStartDt']=$("#validStartDtSearch").val() == ""?"":new Date(Date.parse($("#validStartDtSearch").val().replace(/-/g,"/"))).format('yyyy-MM-dd hh:mm:ss');
        this.searchOrderObj['qm.validToDt']=$("#validToDtSearch").val() == ""?"":new Date(Date.parse($("#validToDtSearch").val().replace(/-/g,"/"))).format('yyyy-MM-dd hh:mm:ss');


    };
    this.initialize=function(){
        $('#batchNoSch').val(batchNo);
        this.initSearchCondition();
        this.initContentList();
        this.initInput();
        this.initOperation();
        this.initOperationBtnStatus();
    };
    this.initSearchCondition=function(){
        $('#batchNoSch').jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});

        $('#cardNoSch').jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $('#griftCardName').jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $('#chargedAccount').jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        $('#userClient').jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1,theme:currentTheme});
        //Core.jqwidgets({id:"validStartDtSearch",name:"jqxDateTimeInput"});
        //Core.jqwidgets({id:"validToDtSearch",name:"jqxDateTimeInput"});
        $('#validStartDtSearch').jqxDateTimeInput({width: 156, height: 22,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});
        $('#validToDtSearch').jqxDateTimeInput({width: 156, height: 22,showFooter: true,theme:currentTheme,allowNullDate: true, culture: 'zh-CN', formatString: 'd',value:null});
        //$('#statusSch').jqxDropDownList({
        //    selectedIndex:0,
        //    source :cardStatusCodeAndName.getDatas(),
        //    displayMember : "codeDisplay",
        //    valueMember : "codeValue",
        //    width : 106,
        //    height : 22,
        //    autoDropDownHeight : true
        //});
        //$("#statusSch").jqxDropDownList('insertAt', { label:'全部' ,value:'all'},0);
        var codesearch = new codeModel({width:100,autoDropDownHeight:true});
        codesearch.getCommonCode("statusSch","GIFT_CARD_STATUS",true);
        $("#searchBtn").jqxButton({ theme : currentTheme});
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
            if(dt.cardSid==id){
                return dt;
            }
        }
        return null;
    };
    this.dataAdapter;
    this.initContentList=function(){
        me.fetchSearchData();

        //this.dataAdapter = Core.NewDataAdapter({gridId:"mygrid",url:ws_url+"/rest/marketing/giftCards/findGiftCards",params:me.searchOrderObj,type:'GET'
            //,
            //setting:{formatData: function (data) {
            //    if(data['sortdatafield']==='statusName'){
            //        data['sortdatafield']='status';
            //    }
            //    return data;
            //}}
        //});
        this.dataAdapter = Core.getPagingDataAdapter({
            gridId: "mygrid",
            url: ws_url + "/rest/giftCards/getAvailableBatchs",
            params:me.searchOrderObj,
            type:'get'
        });
        var cellhtml = "<div style=\"overflow: hidden; text-overflow: ellipsis; padding-bottom: 2px; text-align: left; margin-right: 2px; margin-left: 4px; margin-top: 4px;\">";

        $("#mygrid").jqxGrid({
            width: "100%"
            ,theme:currentTheme
            ,source:me.dataAdapter
            ,enabletooltips: true,enablebrowserselection: true
            ,pageable: true
            ,pagesize: 10
            //,pagesizeoptions: pagesizeoptions
            //,height: gridHeight
            ,autoHeight: true
            ,sortable: true
            ,localization: gridLocalizationObj
            //,selectionmode:"checkbox"
            ,selectionmode:"singlerow"
            ,virtualmode: true
            ,altrows: true
            ,rendergridrows:function(){
                var rows=me.dataAdapter.records;
                //for(var i=0;i<rows.length;i++){
                //    var row=rows[i];
                //    if(row!=null){
                //        row['statusName']=cardStatusCodeAndName.getNameByCode(row['status']);
                //    }
                //}
                return rows; },
            clipboard:true,
            columnsresize:true,
            columns: [
                { text: '生成批次号', datafield: 'batchNo',width:170},
                { text: '礼品卡名称', datafield: 'cardName'},
                { text: '礼品卡卡号', datafield: 'cardNo',width:180},
                { text: '礼品卡卡密', datafield: 'cardPassword',width:80},
                { text: '面额', datafield: 'faceValue',width:80,align:'center',cellsalign:'right',cellsformat:'c2'},
                { text: '有效期开始', datafield: 'validStart',width:100,align:'center',cellsalign:'center',cellsrenderer:dateRender},
                { text: '有效期截止',datafield:'validTo',width:100,align:'center',cellsalign:'center',cellsrenderer:dateRender},

                { text: '状态',datafield:'statusName',width:50,align:'center',cellsalign:'center'},
                {text: '操作',datafield:'batchSid',enabletooltip:false,width:60,sortable:false,align:'center',cellsrenderer:function (rowIndex, columnfield, value, defaulthtml, columnproperties, rowdata) {

                    var html = '<div style="text-align: center;">';
                    //末激活
                    if(rowdata.status==0){
                        html += '<a class="icon-ok-squared icons-18-green2" title="激活" href="javascript:myDesk.activeGiftCard(\''+rowdata.cardSid+'\');void(0);"></a>';
                        html += '<a class="icon-cancel-squared icons-18-red" title="作废" href="javascript:myDesk.invalidateGiftCard(\''+rowdata.cardSid+'\');void(0);"></a>';
                    }else{
                        html += '<i class="icon-ok-squared icons-18-disabled" title="激活"></i>';
                        if(rowdata.status==1){
                            html += '<a class="icon-cancel-squared icons-18-red" title="作废" href="javascript:myDesk.invalidateGiftCard(\''+rowdata.cardSid+'\');void(0);"></a>';
                        }else {
                            html += '<i class="icon-cancel-squared icons-18-disabled" title="作废"></i>';
                        }
                    }

                    return html+='</div>';

                }},
                { text: "激活时间", datafield: 'activatedTime',width:100,type: 'date', format: "yyyy-MM-dd HH:mm:ss"},
                { text: "充值帐号", datafield: 'chargeAccountSid',width:80,
                    cellsrenderer:function(row, columnfield, value, defaulthtml,columnproperties, rowdata){
                        if(rowdata.chargeAccount)
                            return cellhtml + rowdata.chargeAccount.accountName+"</div>";
                    }},
                { text: "充值人", datafield: 'chargeOperUserSid',width:80,
                    cellsrenderer:function(row, columnfield, value, defaulthtml,columnproperties, rowdata){
                        if(rowdata.chargeUser)
                            return cellhtml + rowdata.chargeUser.account+"</div>";
                    }},
                { text: "充值时间", datafield: 'chargeTime',width:100,type: 'date', format: "yyyy-MM-dd HH:mm:ss"}
                ,{ text: "客服经理", datafield: 'distributeUserSid',width:80,
                    cellsrenderer:function(row, columnfield, value, defaulthtml,columnproperties, rowdata){
                        if(rowdata.distributeUser)
                            return cellhtml + rowdata.distributeUser.realName+"</div>";
                    }}],
            showtoolbar: true,
            // 设置toolbar操作按钮
            rendertoolbar: function (toolbar) {
                var container = $("<div id='btnUserGroup'></div>");

                var container = $("<div  id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                var exportAllBtn = $("<div><a id='exportAllBtn'>&nbsp;&nbsp;<i class='icons-blue icon-export-1'></i>导出所有&nbsp;&nbsp;</a></div>");
                var activeBtn = $("<div><a id='activeBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit' ></i>激活&nbsp;&nbsp;</a></div>");
                var exportSelectedBtn = $("<div><a id='exportSelectedBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-export-1'></i>导出所选&nbsp;&nbsp;</a></div>");
                container.append(exportAllBtn);
                container.append(activeBtn);
                container.append(exportSelectedBtn);
                toolbar.append(container);
                //hideBtns();
            }

        });
    };
    $("#mygrid").on({'rowunselect':function(event){
        var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');
        if(rowindexes==null||rowindexes.length==0){
            me.initOperationBtnStatus();
        }
    },'rowclick':function (event) {
        var args = event.args;
        var boundIndex = args.rowindex;
        $("#mygrid").jqxGrid('clearselection');
        $("#mygrid").jqxGrid('selectrow', boundIndex);
    } });
    // 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.initInput = function(){
    };

    this.initOperation=function(){

        // 控制按钮是否可用
        $("#mygrid").on('rowselect', function (event) {

            var args = event.args;
            var index = args.rowindex;
            var data = $('#mygrid').jqxGrid('getrowdata', index);

            $("#activeBtn").jqxButton({disabled: false});
            $("#exportSelectedBtn").jqxButton({disabled: false});

        });
        $('#exportAllBtn').click(function(){
            me.exportQueried();
        });
        $('#activeBtn').click(function(){
            var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');
            if(rowindexes==null||rowindexes.length==0){

                return;
            }
            var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');

            for(var i=0;i<rowindexes.length;i++){
                var data = $('#mygrid').jqxGrid('getrowdata', rowindexes[i]);
                if(data!=null&&data.cardSid!=null&&data.status!=0){
                    Core.alert({message:"只有选中未激活的礼品卡才能被激活！"});
                    return ;
                }
            }
            me.activeGiftCard();
        });
        $('#exportSelectedBtn').click(function(){
            var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');
            if(rowindexes==null||rowindexes.length==0){

                return;
            }
            me.exportSelected();
        });

    };



    this.initOperationBtnStatus=function(){

        $("#exportAllBtn").jqxButton({disabled: false,theme:currentTheme });
        $("#activeBtn").jqxButton({disabled: true,theme:currentTheme });
        $("#exportSelectedBtn").jqxButton({disabled: true,theme:currentTheme });
    };
    this.exportQueried=function(){
        var url=ws_url+"/rest/marketing/giftCards/exportQueried";
        var requestData = {};

        window.open(url+'?'+$.param(me.searchOrderObj),'_blank');

    };
    this.exportSelected=function(){
        var url=ws_url+"/rest/marketing/giftCards/exportSpecified";
        var requestData = {};

        var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');
        var cardSids=[];
        for(var i=0;i<rowindexes.length;i++){
            var data = $('#mygrid').jqxGrid('getrowdata', rowindexes[i]);
            if(data!=null&&data.cardSid!=null)
                cardSids.push(data.cardSid);
        }
        requestData.giftCardSids=cardSids.join(',');
        window.open(url+'?'+$.param(requestData),'_blank');
    };


    this.activeGiftCard=function(cardSid){
        var url=ws_url+"/rest/marketing/giftCards/activate";
        var requestData = {};
        if(cardSid!=undefined)
            requestData.giftCardSids=cardSid;
        else{
            var rowindexes = $('#mygrid').jqxGrid('getselectedrowindexes');
            var cardSids=[];
            for(var i=0;i<rowindexes.length;i++){
                var data = $('#mygrid').jqxGrid('getrowdata', rowindexes[i]);
                if(data!=null&&data.cardSid!=null){
                    if(data.status!=0){
                        Core.alert({message:"只能激活未激活的礼品卡"});
                        return ;
                    }
                    cardSids.push(data.cardSid);
                }
            }
            requestData.giftCardSids=cardSids.join(',');
        }
        url+='?'+'giftCardSids='+requestData.giftCardSids;
        Core.confirm({title:"请确认",message:"要激活礼品卡吗?",confirmCallback:function(){
            Core.AjaxRequest({
                url : url
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
    this.invalidateGiftCard=function(cardSid){
        var url=ws_url+"/rest/marketing/marketing/giftCards/"+cardSid+"/invalid";
        var requestData = {};

        Core.confirm({title:"请确认",message:"要作废礼品卡吗?",confirmCallback:function(){
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
    this.search = function(){
        var startDt = $("#validStartDtSearch").val() == ""?"":new Date(Date.parse($("#validStartDtSearch").val().replace(/-/g,"/"))).format('yyyy-MM-dd hh:mm:ss');
        var endDt = $("#validToDtSearch").val() == ""?"":new Date(Date.parse($("#validToDtSearch").val().replace(/-/g,"/"))).format('yyyy-MM-dd hh:mm:ss');
        if(startDt && endDt && startDt > endDt){
            Core.alert({
                message: "有效开始时间不能大于结束时间！",
                callback:function(){}
            });
            return;
        }

        $("#mygrid").jqxGrid('applyfilters');
        $('#mygrid').jqxGrid('refreshfilterrow');
        $('#mygrid').jqxGrid('clearselection');
    };
    this.refresh=function(){

        $('#mygrid').jqxGrid('clearselection');
        $('#mygrid').jqxGrid('clear');
        $("#mygrid").jqxGrid('updatebounddata', 'cells');
    };
}
$(function(){

    //('#mytabs').jqxTabs({width : '100%', height : '100%', position : 'top', showCloseButtons: true, theme : currentTheme});
    //$('#mytabs').jqxTabs('hideCloseButtonAt', 0);

    myDesk  = new MyDesk();

    // 初始化页面组件
    myDesk.initialize();
});
  
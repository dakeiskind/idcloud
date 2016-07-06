var msgInboxModel = function(){
    var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        msgType:"",
        readFlag:"",
        sendDate:""
    };

    //消息数据
    this.searchMsgInboxInfo = function(){
        Core.AjaxRequest({
            url:ws_url + "",
            type:'post',
            params:me.searchObj,
            async:false,
            callback:function(data){
                me.items(data);
                var sourcedatagrid ={
                    datatype: "json",
                    localdata: me.items
                };
                var dataAdapter = new $.jqx.dataAdapter(sourcedatagrid);
                $("#msgInboxDategrid").jqxGrid({source: dataAdapter});
            }
        });
    };

    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function(){
        //初始化查询组件
        var msgInbox = new codeModel({width:150,autoDropDownHeight:true});
        msgInbox.getCommonCode("search-inbox-type","MSG_TYPE",true);
        msgInbox.getCommonCode("search-inbox-read-flag","READ_FLAG",true);
        $("#search-inbox-start-time").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd', height: 22, allowNullDate: true,value:null,theme:currentTheme});
        $("#search-inbox-end-time").jqxDateTimeInput({width: '120px', culture: 'zh-CN', formatString: 'd',height: 22, allowNullDate: true,value:null,theme:currentTheme});
        $("#search-inbox-button").jqxButton({ width: '50px',height:'25px',theme:currentTheme});
    };
    this.setEditUserForm = function(data){

    };

};

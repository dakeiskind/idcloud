define(['app-utils/variableService','app-utils/jqx/window','app-utils/httpService','app-utils/messageBoxService'],function(variable,win,http,messageBox) {
    var diskService = function() {
        var me = this;

        //刷新/查询数据
        this.referenceData = function(){

        };

        //挂载云硬盘
        this.attachDisk = function(row){
            win.initWindow({
               title: "挂载云主机",
               url: variable.app_modules
                    + "/console/host/views/attach-host.html",
               btn: ['确定', '取消'],
               area: ['400px',
                      '150px'],
               callback: function () {},
               confirm: function () {
                   var obj = new Object();
                   obj.actionName = "attach";
                   var vmSid = $("#vmSid").val();
                   var cbs = me.getSelectedCbsArr(row,vmSid);
                   obj.actionCbs = cbs;
                   me.httpOperation(obj,"挂载硬盘成功！");
                   return true;
               }
           });
        };

        //卸载云硬盘
        this.detachDisk = function(row){
            var obj = new Object();
            obj.actionName = "detach";
            var cbs = me.getSelectedCbsArr(row,null);
            obj.actionCbs = cbs;
            me.httpOperation(obj,"卸载硬盘成功！");
            return true;
        };

        //删除并释放云硬盘
        this.releaseDisk = function(row){
            var obj = new Object();
            obj.actionName = "release";
            var cbs = me.getSelectedCbsArr(row,null);
            obj.actionCbs = cbs;
            me.httpOperation(obj,"删除释放硬盘成功！");
            return true;
        };

        //获取选中的cbs obj 信息
        this.getSelectedCbsArr = function(row,vmSid){
            var cbs = new Array();
            if (row >= 0) {
                var selectObj = $("#diskGrid").ptGrid("getrowdata", row);
                if(vmSid != null)
                    selectObj.vmSid = vmSid;
                cbs.push(selectObj);
            }else {
                var selectObj = $("#diskGrid").ptGrid("getselectedrow");
                for (var i=0;i<selectObj.length;i++) {
                    if(vmSid != null)
                        selectObj[i].vmSid = vmSid;
                    cbs.push(selectObj[i]);
                }
            }
            return cbs;
        };

        this.httpOperation = function(obj,message){
            http.AjaxRequest({
                url:"/rest/cbs/operation",
                type:"POST",
                params:obj,
                showWaiting:true,
                callback:function(data){
                    if (data){
                        messageBox.msgNotification({
                            type:"success",
                            message:message
                        });
                    }
                }
            });
        };

        this.add = function(){
            alert("add");
        };
        this.edit = function(data){
            alert(data);
        };
        this.remove = function(data){
            alert(data);
        };
    };
    return new diskService();
});
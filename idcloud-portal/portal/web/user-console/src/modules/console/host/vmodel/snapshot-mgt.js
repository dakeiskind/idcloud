define(["app-modules/console/host/services/snapshot-mgt","lib/jquery/pintuer",'app-utils/jqx/window','app-utils/messageBoxService',"app-utils/$extendService"], function  (snapshotService,pintuer,window,messageBox) {
    var snapshotMgt = avalon.define({
        $id:'snapshotMgt',
        title:'快照',
        zone:"01",

        rollBack:function(row){
            messageBox.confirm({
                message:"您确定要回滚该磁盘吗？",
                callback:function(){
                    messageBox.msgNotification({
                        type:"success",
                        message:"回滚成功"
                    });
                }
            });
        },
        remove:function(row){
            messageBox.confirm({
                message:"您确定要删除选中的快照吗？",
                callback:function(){
                    $("#diskSnapshotGrid").ptGrid("removeRow");
                    $('#diskSnapshotGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除成功"
                    });
                }
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.snapshotName = $("#searchText").val();
                searchObj.diskID = "";
            }else if($("#searchSel").val()==2){
                searchObj.snapshotName = "";
                searchObj.diskID = $("#searchText").val();
            }
            $('#diskSnapshotGrid').ptGrid("refreshfilterrow");
        },
        refreshclick : function(){
            $('#diskSnapshotGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        refreshclick_1 : function(){
            $('#hostSnapshotGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        addBackUp: function(){
            messageBox.msgNotification({
                type:"success",
                message:"创建自定义快照"
            });
        },
        zoneSwitch:function(obj){
            if(snapshotMgt.zone != $(obj).val()){
                snapshotMgt.zone = $(obj).val();
                //initGrid();
            }
        }
    });

    var searchObj = {
        snapshotName:""
        ,diskID:""
    };

    //初始grid
    var initHostSnapshotGrid = function(){

        $("#hostSnapshotGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: snapshotMgt,
            data:{
                //url: "/rest/cs/snapshot/getSnapshotList/"+snapshotMgt.zone,
                //type: 'POST',
                localData: snapshotService.getGridDate(),
                params: searchObj
            },
            columns: [
                {text:'名称',datafield:"snapshotName"},
                {text:"状态",datafield:"status"},
                {text:"创建时间",datafield:"createdDt"},
                { text: '操作', datafield: '',sortable: false,width:140,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="edit('+row+')">编辑</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="recovery('+row+')">恢复</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="delete('+row+')">删除</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn_1",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick_1()"},
                {id: "refreshBtn_1",name:"添加快照",type:"button",icon:"icon-refresh",click:"refreshclick_1()"},

                {id: "searchBtn_1",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search_1()"},
                {id: "searchText_1",type:"text",align:"right",width:150,placeholder:"请输入主机名"},
                {id: "searchSel_1",type:"select",align:"right",data:[{name:"全部",value:""},{name:"快照ID",value:"1"},{name:"磁盘ID",value:"2"}]}
            ]
        });
    };
    var initDiskSnapshotGrid = function(){

        $("#diskSnapshotGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: snapshotMgt,
            data:{
                //url: "/rest/cs/snapshot/getSnapshotList/"+snapshotMgt.zone,
                //type: 'POST',
                localData: snapshotService.getGridDate(),
                params: searchObj
            },
            columns: [
                {text:'快照ID/名称',datafield:"snapshotName"},
                {text:"磁盘ID",datafield:"diskID"},
                {text:"磁盘容量",datafield:"diskCapacity"},
                {text:"磁盘属性",datafield:"diskAttr"},
                {text:"创建时间",datafield:"creatTime"},
                {text:"状态",datafield:"statusTxt"},
                {text:"进度",datafield:"process"},
                { text: '操作', datafield: '',sortable: false,width:200,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="rollBack('+row+')">回滚磁盘</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="addBackUp('+row+')">创建自定义镜像</a>';
                    return cellsHtml;
                }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"remove()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入主机名"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"快照ID",value:"1"},{name:"磁盘ID",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#diskSnapshotGrid").ptGrid("getselectedrow");
                if(selectDatas.length>0){
                    $("#removeBtn").removeAttr("disabled");
                }else{
                    $("#removeBtn").attr("disabled","disabled");
                }
            }
        });
    };

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.cs-snapshot';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            initHostSnapshotGrid();
            initDiskSnapshotGrid();
            pintuer.init();
            //avalon.scan(0,consoleDisk);
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [snapshotMgt];

    });
});
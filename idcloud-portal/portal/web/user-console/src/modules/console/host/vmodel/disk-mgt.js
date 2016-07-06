define(['app-utils/httpService','app-utils/codeService',"app-modules/console/host/services/disk-mgt",
        "lib/jquery/pintuer",
        'app-utils/jqx/window',
        'app-utils/variableService',
        'app-utils/messageBoxService',
        'echarts',
        'app-utils/validatorService',
        "app-utils/$extendService",
        ], function  (http,code,diskService,pintuer,win,variable,messageBox,echart) {
    var consoleDisk = avalon.define({
        $id:'consoleDisk',
        title:'云硬盘',
        diskNameTitle:'',
        hostData:null,
        selectrow:"",
        addSnapshotData:null,
        orderblockStorage: "",
        regionData: code.getCustomCode("/topologys", "POST",
                                       {resTopologyType: "R"}),
        regionValue: "", // 当前区域下拉框数据
        zoneValue: "", // 当前区域分区下拉框数据
        zoneData: [], // 区域分区数据
        dataDetail:{
            basic:{
                diskId:"",diskType:"",diskStatus:"",diskName:"",diskCapacity:"",diskAttr:"",
                diskRegion:"",diskAvailable:"",diskCreate:"",diskCreateDate:"",diskSnapshot:"",
                diskSnapshotStrategy:"",diskPayType:"",diskDes:""
            },
            mount:{
                example:"",point:"",can:"",isRelease:"",isSnpashotRelease:""
            }
        },
        add:function(){
            consoleDisk.orderblockStorage = variable.app_modules + "/user-center/orderMgt/views/order-blockStorage.html"

            $("#diskDetail").addClass("hidden");
            $("#diskDetail").removeClass("show");
            $("#diskList").addClass("hidden");
            $("#diskList").removeClass("show");
            $("#diskAdd").addClass("show");
            $("#diskAdd").removeClass("hidden");
            $("#diskAdd").addClass("fadein-left");

//        	avalon.router.go("user.order-blockStorage");

//            avalon.vmodels.userContainer.navSelectedFlag = "user/order-blockStorage";
//            avalon.router.navigate("user/order-blockStorage");

        },
        release:function(row){
            messageBox.confirm({
                message:"您确定要卸载硬盘吗？",
                callback:function(){
                    var obj = new Object();
                    obj.actionName = "detach";
                    var cbs = getSelectedCbsArr(row,null);
                    obj.actionCbs = cbs;
                    httpOperation(obj,"卸载硬盘成功！");
                    return true;
                }
            });
        },
        mount:function(row){
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
                   var cbs = getSelectedCbsArr(row,vmSid);
                   obj.actionCbs = cbs;
                   httpOperation(obj,"挂载硬盘成功！");
                   return true;
               }
           });
        },
        backList:function(){
            $("#diskList").addClass("show");
            $("#diskList").removeClass("hidden");
            $("#diskList").addClass("fadein-left");
            $("#diskAdd").addClass("hidden");
            $("#diskAdd").removeClass("show");
            $("#diskDetail").addClass("hidden");
            $("#diskDetail").removeClass("show");
        },
        remove:function(row){
            messageBox.confirm({
                message:"您确定要删除并释放硬盘吗？",
                callback:function(){
                    var obj = new Object();
                    obj.actionName = "release";
                    var cbs = getSelectedCbsArr(row,null);
                    obj.actionCbs = cbs;
                    httpOperation(obj,"删除释放硬盘成功！");
                    return true;
                }
            });
        },
        refresh:function(){
            initGrid();
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        search:function(){
            if($("#searchSel").val()==1){
                searchObj.diskType = $("#searchText").val();
                searchObj.diskStatus = "";
            }else if($("#searchSel").val()==2){
                searchObj.diskType = "";
                searchObj.diskStatus = $("#searchText").val();
            }else{
                searchObj.diskType = $("#searchText").val();
                searchObj.diskStatus = $("#searchText").val();
            }
            $("#diskGrid").ptGrid("applyfilters");
            $('#diskGrid').ptGrid("refreshfilterrow");
        },
        detail:function(row){
            var rowDate = $("#diskGrid").ptGrid("getrowdata",row);
            avalon.vmodels.consoleDisk.selectrow = row;
            avalon.vmodels.consoleDisk.diskNameTitle = rowDate.diskId;
            var dataDetail = {basic:{
                    diskId:rowDate.instanceId,diskType:rowDate.diskType,diskStatus:rowDate.vdStatusName,diskName:rowDate.instanceName,
                    diskCapacity:rowDate.diskSize,diskAttr:rowDate.diskAttr,diskRegion:rowDate.zoneName,diskAvailable:rowDate.diskAvailable,
                    diskCreate:rowDate.dredgeTime,diskCreateDate:rowDate.dredgeTime,diskSnapshot:rowDate.diskSnapshot,
                    diskSnapshotStrategy:rowDate.diskSnapshotStrategy,diskPayType:rowDate.billingTypeName,diskDes:rowDate.diskDes
                },
                mount:{
                    example:rowDate.vmName,point:rowDate.point,can:rowDate.can,isRelease:rowDate.isRelease,
                    isSnpashotRelease:rowDate.isSnpashotRelease
                }
            };
            avalon.vmodels.consoleDisk.dataDetail = dataDetail;
            $("#diskList").removeClass("show");
            $("#diskList").addClass("hidden");
            $("#diskDetail").removeClass("hidden");
            $("#diskDetail").addClass("fadein-right");
            consoleDisk.initCharts();
            consoleDisk.initChartsnet();
        },
        addSnapshot:function(row){
            if(isNaN(row)){
                row = avalon.vmodels.consoleDisk.selectrow;
            }
            win.initWindow({
                title: "创建快照",
                url: variable.app_modules+"/console/host/views/host-snapshot-add.html",
                btn: ['确定', '取消'],
                area: ['450px', '260px'],
                confirm:function(){
                    //var valiobj = $("#snapshotForm").validate();
                    //valiobj.element(document.getElementById("snapshotName"));
                    if($("#snapshotForm").valid()){

                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowDate = $("#diskGrid").ptGrid("getrowdata",row);
                    avalon.vmodels.consoleDisk.addSnapshotData = {
                        diskName:rowDate.instanceName,
                        hostName:rowDate.vmName,
                        diskType:rowDate.diskType,
                        snapshotName:""
                    };
                }
            });
        },
        initCharts: function () {
            var myChart = echart.init(document.getElementById('mainCharts'));
            var option = {
                tooltip: {
                    show: true
                },
                legend: {
                    data: ['CPU使用率']
                },
                grid: {
                    x: 30,
                    y: 20,
                    x2: 10,
                    y2: 30
                },

                xAxis: [
                    {
                        type: 'category',
                        data: ["21:08", "21:10", "21:12", "21:14", "21:16", "21:18"]
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: "CPU使用率",
                        "type": "line",
                        "data": [0, 0, 0, 0, 0, 0,0,0]
                    }
                ]
            };
            myChart.setOption(option);
            window.setTimeout(myChart.resize,1000);
            return myChart;
        },
        initChartsnet: function () {
            var myChart2 = echart.init(document.getElementById('mainChartstwo'));
            var option = {
                tooltip: {
                    show: true
                },
                legend: {
                    data: ['出网kbps','入网kbps']
                },
                grid: {
                    x: 30,
                    y: 20,
                    x2: 10,
                    y2: 30
                },

                xAxis: [
                    {
                        type: 'category',
                        data: ["21:08", "21:10", "21:12", "21:14", "21:16", "21:18"]
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: "出网kbps",
                        "type": "line",
                        "data": [0, 0, 0, 0, 0, 0,0,0]
                    },
                    {
                        name: "入网kbps",
                        "type": "line",
                        "data": [0, 0, 0, 0, 0, 0,0,0]
                    }
                ]
            };
            myChart2.setOption(option);
            window.setTimeout(myChart2.resize,1000);
            return myChart2;
        }
    });
    consoleDisk.$watch("regionValue", function (a, b) {
        consoleDisk.zoneData =
            code.getCustomCode("/topologys", "POST", {parentTopologySid: a, resTopologyType: "RZ"});
        var temp = consoleDisk.zoneData;
        consoleDisk.zoneValue = temp[0].resTopologySid;
    });
    // 初始化分区1212
    // 初始化分区111
    var zoom = consoleDisk.regionData;
    consoleDisk.regionValue = zoom[0].resTopologySid;
    var searchObj = {
        diskType:""
       ,diskStatus:""
    };
//获取选中的cbs obj 信息
    var getSelectedCbsArr = function(row,vmSid){
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
    var httpOperation = function(obj,message){
        http.AjaxRequest({
             url:"/rest/cbs/operation",
             type:"POST",
             params:obj,
             showWaiting:true,
             callback:function(data){
                 if (data){
                     initGrid();
                     initButton();
                     messageBox.msgNotification({
                        type:"success",
                        message:message
                    });
                 }
             }
         });
    };
    var initButton = function(){
        $("#releaseBtn").attr("disabled","disabled");
        $("#mountBtn").attr("disabled","disabled");
    };
    Array.prototype.isH=function (a){
        if(this.length===0){return false};
        for(var i=0;i<this.length;i++){
            if(this[i].vmSid==a){return true}
        }
    };
    Array.prototype.isFH=function (a){
        if(this.length===0){return false};
        for(var i=0;i<this.length;i++){
            if(this[i].vmSid!==a){return true}
        }
    };
    //初始grid;
    var initGrid = function(){
        $("#diskGrid").ptGrid({
            selectionmode:"checkbox",
            sortable: true,
            controller: consoleDisk,
            data:{
                url: "/rest/cbs/findCbs",
                type: 'POST',
                params: searchObj
            },
            columns: [
                { text: '硬盘ID/硬盘名称', datafield: 'instanceIdName'},
                { text: '硬盘类型', datafield: 'diskType'},
                { text: '容量(单位：G)', datafield: 'diskSize'},
                { text: '硬盘状态', datafield: 'vdStatusName'},
                { text: '付费类型', datafield: 'billingTypeName'},
                { text: '挂载云主机',datafield:'vmName'},
                { text: '可用区',datafield:'zoneName'},
                { text: '创建时间',datafield:'dredgeTime'},
                { text: '操作', datafield: '',sortable: false,width:150,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                        var cellsHtml = "";
                        cellsHtml += '<a href="javascript:void(0);" ms-click="detail('+row+')">详情</a>';
                        cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<div class="button-group">';
                    cellsHtml += '<ul class="nav nav-inline nav-menu" >';
                    cellsHtml +=
                        '<li style="line-height: 20px;padding:0px 0px"><a'
                        + ' ms-mouseenter=""'
                        + ' style="line-height: 20px;padding:0px 0px" href="javascript:;">更多<span class="downward"></span></a>';
                    cellsHtml +=
                        '<ul style="font-size: xx-small;right:0;left:auto" class="drop-menu">';
                    cellsHtml +=
                        '<li><a style="font-size: xx-small" onclick=""  class="addshop icon-play hidden"  href="javascript:void(0);" >&nbsp;创建快照</a>'
                        + ' </li>';
                    cellsHtml +=
                        '<li><a style="font-size: xx-small"  class="remove icon-trash-o" href="javascript:void(0);" ms-click="remove(' + row + ')" >&nbsp;删除</a>'
                        + ' </li>';
                    cellsHtml += '</ul>';
                    cellsHtml += '</li>';
                    cellsHtml += '</ul>';
                    cellsHtml += '</div>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refresh()"},
                {id: "releaseBtn",name:"释放",type:"button",icon:"icon-expand",disabled:true,click:"release()"},
                {id: "mountBtn",name:"挂载云主机",type:"button",icon:"icon-cloud-upload",disabled:true,click:"mount()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"硬盘种类",value:"diskType"},{name:"硬盘状态",value:"diskStatus"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#diskGrid").ptGrid("getselectedrow");
                var str = selectDatas.isH(undefined)?true:false;
                var str1 = selectDatas.isFH(undefined)?true:false;
                console.log(str)
                console.log(str1)
                if(selectDatas.length>0&&!str&&str1){
                    $("#releaseBtn").removeAttr("disabled");
                    $("#mountBtn").attr("disabled","disabled");
                }else if(selectDatas.length>0&&str&&!str1){
                    $("#releaseBtn").attr("disabled","disabled");
                    $("#mountBtn").removeAttr("disabled");
                }else{
                    initButton();
                    }
            }
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.cs-cbs';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
//            require(['src/modules/user-center/orderMgt/vmodel/order-dg']);
            initGrid();
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [consoleDisk];

    });
});

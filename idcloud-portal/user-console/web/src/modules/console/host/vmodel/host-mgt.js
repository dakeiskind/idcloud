/**
 * Created by Administrator on 2016/1/31.
 */
define(['app-utils/jqx/window','app-utils/codeService', 'app-utils/httpService', 'app-utils/messageBoxService',
        "app-modules/console/host/services/host-mgt",
        "lib/jquery/pintuer",
        'echarts',
        'app-utils/variableService',
        'echarts/chart/line',
        "app-utils/$extendService",
        "base64"], function (win,code, http, messageBox, hostService, pintuer, ec, variable) {
    var hostMgt = avalon.define({
        $id: 'hostMgt',
        title: '云主机',
        hostName: '',
        orderdg: "",
        hostEipData:null,
        regionData: code.getCustomCode("/topologys", "POST",
                                       {resTopologyType: "R"}),
        regionValue: "", // 当前区域下拉框数据
        zoneValue: "", // 当前区域分区下拉框数据
        zoneData: [], // 区域分区数据
        dataDetail: {
            basic: {
                resSid: "",
                monitor: "",
                zoneName: "",
                addressIP: "",
                state: "",
                networkType: "",
                configuration: "",
                payWay: ""
            }
        },
        directToOrderPage: function () {
            // 判断是否需要跳转到订购页面
            var params = window.location.href;
            var from = $.base64.atob(params.split("=")[1], true);
            if (from == "orderPage") {
                hostMgt.orderdg =
                    variable.app_modules
                    + "/user-center/orderMgt/views/order-dg.html";
                $("#hostDetail").addClass("hidden");
                $("#hostDetail").removeClass("show");
                $("#hostmgt").addClass("hidden");
                $("#hostmgt").removeClass("show");
                $("#hostAdd").addClass("show");
                $("#hostAdd").removeClass("hidden");
                $("#hostAdd").addClass("fadein-left");
            }
        },
        start: function (row) {
            if($(this).hasClass("disabled")){
                return;
            }
            messageBox.confirm({
               message: "您确定要启动选中的主机吗？",
               callback: function () {
                   var params=[];
                   if (row >= 0) {
                       var selectObj = $(
                           "#hostGrid")
                           .ptGrid("getrowdata",
                                   row);
                       var obj = new Object();
                       obj.serviceInstanceSid =
                           selectObj.instanceSid;
                       obj.instanceName=selectObj.instanceName;
                       obj.action = "start";
                       obj.rebootType = "";
                       obj.zone = selectObj.zoneSid;
                       params.push(obj);
                   } else {
                       var selectObj = $(
                           "#hostGrid").ptGrid(
                           "getselectedrow");
                       for (var i=0;i<selectObj.length;i++) {
                           console.log(i);
                           var obj = new Object();
                           obj.serviceInstanceSid =
                               selectObj[i].instanceSid;
                           obj.instanceName=selectObj[i].instanceName;
                           obj.action = "start";
                           obj.rebootType = "";
                           obj.zone = selectObj[i].zoneSid;
                           params.push(obj);
                       }
                   }
                   http.AjaxRequest({
                        type: 'POST',
                        url: "/rest/cs/serviceInstanceOperation",
                        params: params,
                        callback: function () {
                            initGrid();
                            initButton();
                            messageBox.msgNotification(
                                {
                                    type: "success",
                                    message: "操作成功!"
                                });
                        }
                    });
               }
           });
        },
        stop: function (row) {
            if($(this).hasClass("disabled")){
                return;
            }
            messageBox.confirm({
               message: "您确定要停止选中的主机吗？",
               callback: function () {
                   var params=[];
                   if (row >= 0) {
                       var selectObj = $(
                           "#hostGrid")
                           .ptGrid("getrowdata",
                                   row);
                       var obj = new Object();
                       obj.serviceInstanceSid =
                           selectObj.instanceSid;
                       obj.instanceName=selectObj.instanceName;
                       obj.action = "stop";
                       obj.rebootType = "";
                       obj.zone = selectObj.zoneSid;
                       params.push(obj);
                   } else {
                       var selectObj = $(
                           "#hostGrid").ptGrid(
                           "getselectedrow");
                       for (var i=0;i<selectObj.length;i++ ) {
                           var obj = new Object();
                           obj.serviceInstanceSid =
                               selectObj[i].instanceSid;
                           obj.instanceName=selectObj[i].instanceName;
                           obj.action = "stop";
                           obj.rebootType = "";
                           obj.zone = selectObj[i].zoneSid;
                           params.push(obj);
                           console.log(params)
                       }
                   }

                   http.AjaxRequest({
                        type: 'POST',
                        url: "/rest/cs/serviceInstanceOperation",
                        params: params,
                        callback: function () {
                            initGrid();
                            initButton();
                            messageBox.msgNotification(
                                {
                                    type: "success",
                                    message: "操作成功!"
                                });
                        }
                    });
               }
           });
        },
        add: function () {
            hostMgt.orderdg =
                variable.app_modules
                + "/user-center/orderMgt/views/order-dg.html";

            $("#hostDetail").addClass("hidden");
            $("#hostDetail").removeClass("show");
            $("#hostmgt").addClass("hidden");
            $("#hostmgt").removeClass("show");
            $("#hostAdd").addClass("show");
            $("#hostAdd").removeClass("hidden");
            $("#hostAdd").addClass("fadein-left");
        },
        deleteCs:function(row){
            messageBox.confirm({
               message: "您确定要删除选中云主机吗？",
               callback: function () {
                   var params=[];
                   if (row >= 0) {
                       var selectObj = $("#hostGrid").ptGrid("getrowdata", row);
                       var obj=new Object();
                       obj.instanceSid=selectObj.instanceSid;
                       params.push(obj);
                   }else {
                       var selectObj = $("#hostGrid").ptGrid("getselectedrow");
                       for (var i=0;i<selectObj.length;i++ ) {
                           var obj = new Object();
                           obj.instanceSid=selectObj[i].instanceSid;
                           params.push(obj);
                       }
                   }
                   http.AjaxRequest({
                        type: 'POST',
                        url: "/rest/cs/release",
                        params:params,
                        callback: function () {
                            initGrid();
                            initButton();
                            messageBox.msgNotification(
                                {
                                    type: "success",
                                    message: "操作成功!"
                                });
                        }
                    });
               }
           });
        },
        restart: function (row) {
            if($(this).hasClass("disabled")){
                return;
            }
            messageBox.confirm({
               message: "您确定要重启选中的主机吗？",
               callback: function () {
                   var params=[];
                   if (row >= 0) {
                       var selectObj = $(
                           "#hostGrid")
                           .ptGrid("getrowdata",
                                   row);
                       var obj = new Object();
                       obj.serviceInstanceSid =
                           selectObj.instanceSid;
                       obj.instanceName=selectObj.instanceName;
                       obj.action = "reboot";
                       obj.rebootType = "SOFT";
                       obj.zone = selectObj.zoneSid;
                       params.push(obj);
                   } else {
                       var selectObj = $(
                           "#hostGrid").ptGrid(
                           "getselectedrow");
                       for (var i=0;i<selectObj.length;i++ ) {
                           var obj = new Object();
                           obj.serviceInstanceSid =
                               selectObj[i].instanceSid;
                           obj.instanceName=selectObj[i].instanceName;
                           obj.action = "reboot";
                           obj.rebootType = "SOFT";
                           obj.zone =
                               selectObj[i].zoneSid;
                           params.push(obj);
                       }
                   }
                   http.AjaxRequest({
                        type: 'POST',
                        url: "/rest/cs/serviceInstanceOperation",
                        params: params,
                        callback: function () {
                            initGrid();
                            initButton();
                            messageBox.msgNotification(
                                {
                                    type: "success",
                                    message: "操作成功!"
                                });
                        }
                    });
               }
           });
        },
        mouselow:function(row){
            var selectObj = $("#hostGrid").ptGrid("getrowdata", row);
            var datas = selectObj;
            if ( datas.resInsVmStatusName === "正常") {
                $(".startcz").addClass("disabled");
                $(".stopcz").removeClass("disabled");
                $(".restartcz").removeClass("disabled");
                $(".vnc").removeClass("disabled");
                if(datas.eipIp===undefined){
                    $(".eip").removeClass("hidden");
                    $(".eip").addClass("show");
                    $(".jeip").removeClass("show");
                    $(".jeip").addClass("hidden");
                }else if (datas.eipIp!==undefined){
                    $(".eip").removeClass("show");
                    $(".eip").addClass("hidden");
                    $(".jeip").removeClass("hidden");
                    $(".jeip").addClass("show");
                }
            } else if (datas.resInsVmStatusName === "已关机") {
                $(".startcz").removeClass("disabled");
                $(".stopcz").addClass("disabled");
                $(".restartcz").addClass("disabled");
                $(".vnc").removeClass("disabled");
                if(datas.eipIp===undefined){
                    $(".eip").removeClass("hidden");
                    $(".eip").addClass("show");
                    $(".jeip").removeClass("show");
                    $(".jeip").addClass("hidden");
                }else if (datas.eipIp!==undefined){
                    $(".eip").removeClass("show");
                    $(".eip").addClass("hidden");
                    $(".jeip").removeClass("hidden");
                    $(".jeip").addClass("show");
                }
            } else {
                $(".startcz").addClass("disabled");
                $(".stopcz").addClass("disabled");
                $(".restartcz").addClass("disabled");
                $(".vnc").addClass("disabled");
                $(".eip").addClass("hidden");
                $(".jeip").addClass("hidden");
            }
        },
        //绑定弹性公网IP
        bindEip:function(row){
            var rowDate = $("#hostGrid").ptGrid("getrowdata",row);
                //if (isNaN(row)) {
                //    row = avalon.vmodels.consoleElasticIPMgt.selectrow;
                //}
                win.initWindow({
                   title: "绑定弹性公网IP",
                   url: variable.app_modules
                        + "/console/host/views/host-eip.html",
                   btn: ['确定', '取消'],
                   area: ['400px',
                          '220px'],
                   callback: function () {
                       hostMgt.hostEipData = {
                           instanceName: rowDate.instanceName,
                           instanceSid:rowDate.instanceSid
                       };
                   },
                   confirm: function () {
                       if ($("#hostEipForm").valid()) {
                           var obj = new Object();
                           obj.zone = rowDate.zoneSid;
                           obj.floatingIpSid=$("#eipIp").val();
                           obj.subNetSid=rowDate.resNetworkSid;
                           obj.vmIp=$("#vmIp").val();
                           obj.resVmSid=rowDate.resSid;
                           http.AjaxRequest({
                                type: 'POST',
                                url: "/rest/eip/attachFloatingIp",
                                params: obj,
                                callback: function (data) {
                                    //if(data==true){
                                    initGrid();
                                    //messageBox.msgNotification(
                                    //    {
                                    //        type: "success",
                                    //        message: "绑定成功!"
                                    //    });
                                    //}else {
                                    //    messageBox.msgNotification(
                                    //        {
                                    //            type: "error",
                                    //            message: "绑定失败!"
                                    //        });
                                    //}
                                }
                            });
                           return true;
                       }
                       return false;
                   },
               });
        },
        unbundEip:function(row){
            var rowDate = $("#hostGrid").ptGrid("getrowdata", row);
                var obj = new Object();
                obj.zone = rowDate.zoneSid;
                obj.floatingIpSid = rowDate.floatingIpSid;
                obj.resVmSid = rowDate.resSid;
                messageBox.confirm({
                title:"解绑弹性公网IP",
                message: "您确定要解绑吗？",
               callback: function () {
                   http.AjaxRequest({
                    type: 'POST',
                    url: "/rest/eip/dettachFloatingIp",
                    params: obj,
                    callback: function (data) {
                        //if (data == true) {
                        initGrid();
                        //    messageBox.msgNotification(
                        //        {
                        //            type: "success",
                        //            message: "解绑成功!"
                        //        });
                        //} else {
                        //    messageBox.msgNotification(
                        //        {
                        //            type: "error",
                        //            message: "解绑失败!"
                        //        });
                        //}
                    }
                });
               }
           });
        },
        //Web Console控制台
        webconsole: function (row) {
            if($(this).hasClass("disabled")){
                return;
            }
            var selectObj = $("#hostGrid").ptGrid("getrowdata", row);
            window.open(
                "src/modules/console/host/views/vnc-console.html?csSid="
                + selectObj.resSid+"&instanceName="+selectObj.instanceName,selectObj.instanceName);

        },
        remove: function (row) {
            $("#hostGrid").ptGrid("removeRow", row);
            $('#hostGrid').ptGrid("refreshfilterrow");
        },
        removeClk: function () {
            $("#hostGrid").ptGrid("removeRow");
            $('#hostGrid').ptGrid("refreshfilterrow");
        },
        search: function () {
            if ($("#searchSel").val() == 1) {
                params.instanceName = $("#searchText").val();
                params.vmIp = "";
                params.resInsVmStatusName = "";
            } else if ($("#searchSel").val() == 2) {
                params.instanceName = "";
                params.vmIp = $("#searchText").val();
                params.resInsVmStatusName = "";
            } else if ($("#searchSel").val() == 3) {
                params.instanceName = "";
                params.vmIp = "";
                params.resInsVmStatusName = $("#searchText").val();
            } else {
                params.instanceName = "";
                params.vmIp = "";
                params.resInsVmStatusName = "";
            }
            //$("#hostGrid").ptGrid("applyfilters");
            $('#hostGrid').ptGrid("refreshfilterrow");
        },
        refreshclick: function () {
            //$('#hostGrid').ptGrid("refreshfilterrow");
            initGrid();
            messageBox.msgNotification({
               type: "success",
               message: "刷新成功!"
           });
        },
        backList: function () {
            $("#hostDetail").addClass("hidden");
            $("#hostDetail").removeClass("show");
            $("#hostmgt").removeClass("hidden");
            $("#hostmgt").addClass("fadein-left");
            $("#hostAdd").addClass("hidden");
            $("#hostAdd").removeClass("show");
        },
        detail: function (row) {
            var rowDate = $("#hostGrid").ptGrid("getrowdata", row);
            avalon.vmodels.hostMgt.selectrow = row;
            //avalon.vmodels.hostMgt.hostName = rowDate.hostName;
            var data=JSON.parse(rowDate.specification)
            for (var i=0;i<data.instance.length;i++)
            var dataDetail = {
                basic: {
                    hostName: rowDate.instanceId,
                    zoneName:rowDate.zoneName,
                    instanceName:rowDate.instanceName,
                    cpu:data.instance[0].cpu,
                    memory:data.instance[0].memory,
                    vmIp:rowDate.vmIp,
                    billingTypeName:rowDate.billingTypeName,
                    dredgeDate:rowDate.dredgeDate,
                    billingEndTime:rowDate.billingEndTime,
                    osType:rowDate.specValue.substring(20),
                    orderId:rowDate.orderId,
                    eipIp:rowDate.eipIp
                }
            };
            avalon.vmodels.hostMgt.dataDetail = dataDetail;
            $("#hostmgt").addClass("hidden");
            $("#hostmgt").removeClass("show");
            $("#hostDetail").removeClass("hidden");
            $("#hostDetail").addClass("fadein-right");
        },
        initCharts: function () {
            var myChart = ec.init(
                document.getElementById('mainCharts'));
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
                        data: ["21:08", "21:10", "21:12", "21:14",
                               "21:16", "21:18"]
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
                        "data": [0, 0, 0, 0, 0, 0, 0, 0]
                    }
                ]
            };
            myChart.setOption(option);
        },
        initChartsnet: function () {
            var myChart = ec.init(
                document.getElementById('mainChartstwo'));
            var option = {
                tooltip: {
                    show: true
                },
                legend: {
                    data: ['出网kbps', '入网kbps']
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
                        data: ["21:08", "21:10", "21:12", "21:14",
                               "21:16", "21:18"]
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
                        "data": [0, 0, 0, 0, 0, 0, 0, 0]
                    },
                    {
                        name: "入网kbps",
                        "type": "line",
                        "data": [0, 0, 0, 0, 0, 0, 0, 0]
                    }
                ]
            };
            myChart.setOption(option);
        }

    });
    hostMgt.$watch("regionValue", function (a, b) {
        hostMgt.zoneData =
            code.getCustomCode("/topologys", "POST", {parentTopologySid: a, resTopologyType: "RZ"});
        var temp = hostMgt.zoneData;
        hostMgt.zoneValue = temp[0].resTopologySid;
    });
    // 初始化分区
    var zoom = hostMgt.regionData;
    alert(zoom);
    hostMgt.regionValue = zoom[0].resTopologySid;
    //得到表单数据对象
    //function getCloudObj(type){
    //    var displayData = new Object(); // 展示数据
    //    var transferData = new Object(); // 传输数据
    //    displayData.status = true;
    //    // 地域分布
    //    transferData.regionSid = $("#areaBig").val();
    //    transferData.regionName = $("#areaBig option:selected").text();
    //    transferData.zoneSid = $("#areaSmall").val();
    //    transferData.zoneName = $("#areaSmall option:selected").text();
    //    // 验证地域和分区
    //    if($("#areaBig").val() == null){
    //        notice.push('区域');
    //        displayData.status = false;
    //        $("#areaBig").css("border","1px solid red");
    //    }else{
    //        $("#areaBig").css("border","1px solid #DADADA");
    //    }
    //    if($("#areaSmall").val() == null){
    //        notice.push('区域分区');
    //        displayData.status = false;
    //        $("#areaSmall").css("border","1px solid red");
    //    }else{
    //        $("#areaSmall").css("border","1px solid #DADADA");
    //    }
    //    var data = new Array();
    //    var region = new Object();
    //    region.name="地域";
    //    region.value=$("#areaBig option:selected").text()+"("+$("#areaSmall
    // option:selected").text()+")"; data.push(region); return displayData; }
    var params = {
        instanceName: "",
        vmIp: "",
        resInsVmStatusName: ""
    };
    var initButton=function(){
        $("#start").attr("disabled", "disabled");
        $("#stop").attr("disabled", "disabled");
        $("#restart").attr("disabled", "disabled");
        $("#remove").attr("disabled", "disabled");
    };
    Array.prototype.isHas=function (a){
        if(this.length===0){return false};
        for(var i=0;i<this.length;i++){
            if(this[i].resInsVmStatusName===a){return true}
        }
    };
    //初始grid
    var initGrid = function () {
        $("#hostGrid").ptGrid({
          selectionmode: "checkbox",
          sortable: true,
          controller: hostMgt,
          data: {
              //localData: hostService.getGridDate(),
              url : "/rest/cs",
              type : "POST",
              params: params
          },
          columns: [
              {text: '云主机ID/名称', datafield: "sidAndName"},
              //{text: "监控", datafield: "monitor"},
              {text: "所在可用区", datafield: "zoneName"},
              {text: "IP地址", datafield: "vmIp"},
              {text: "状态", datafield: "resInsVmStatusName"},
              {text: "配置", datafield: "specValue"},
              {text: "付费方式", datafield: "billingTypeName"},
              {text: "创建时间", datafield: "createdDt"},
              {
                  text: '操作',
                  datafield: '',
                  sortable: false,
                  width: 100,
                  align: "center"
                  ,
                  cellsrenderer: function (row, rowdata) {
                      var cellsHtml = "";
                      cellsHtml +=
                          '<a href="javascript:void(0);" ms-click="detail('
                          + row + ')">详情</a>';
                      cellsHtml += '<span class="text-explode">|</span>';
                      cellsHtml += '<div class="button-group">';
                      cellsHtml += '<ul class="nav nav-inline nav-menu" >';
                      cellsHtml +=
                          '<li style="line-height: 20px;padding:0px 0px"><a'
                          + ' ms-mouseenter="mouselow(' + row + ')"'
                          + ' style="line-height: 20px;padding:0px 0px" href="javascript:;">更多<span class="downward"></span></a>';
                      cellsHtml +=
                          '<ul style="font-size: xx-small;right:0;left:auto" class="drop-menu">';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="startcz" class="startcz icon-play"  href="javascript:void(0);" ms-click="start(' + row + ')">&nbsp;启动</a>'
                          + ' </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="stopcz" class="stopcz icon-stop" href="javascript:void(0);" ms-click="stop(' + row + ')" >&nbsp;关机</a>'
                          + ' </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="restartcz" class="restartcz icon-undo" href="javascript:void(0);" ms-click="restart(' + row + ')" >&nbsp;重启</a> </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="vnc"  class="vnc icon-windows" href="javascript:void(0);" ms-click="webconsole('
                          + row + ')" >&nbsp;远程控制台</a> </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="eip" class="eip icon-sitemap"'
                          + ' href="javascript:void(0);" ms-click="bindEip(' + row + ')" >&nbsp;绑定弹性公网IP</a> </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="jeip" class="jeip icon-sitemap"'
                          + ' href="javascript:void(0);" ms-click="unbundEip(' + row + ')" >&nbsp;解绑弹性公网IP</a> </li>';
                      cellsHtml +=
                          '<li><a style="font-size: xx-small" id="del" class="del icon-trash-o"'
                          + ' href="javascript:void(0);" ms-click="deleteCs(' + row + ')" >&nbsp;删除</a> </li>';
                      cellsHtml += '</ul>';
                      cellsHtml += '</li>';
                      cellsHtml += '</ul>';
                      cellsHtml += '</div>';
                      return cellsHtml;
                  }
              }
          ],
          toolbars: [
              {
                  id: "refreshBtn",
                  name: "刷新",
                  type: "button",
                  icon: "icon-refresh",
                  click: "refreshclick()"
              },
              {
                  id: "start",
                  name: "启动",
                  type: "button",
                  disabled: true,
                  icon: "icon-play",
                  click: "start()"
              },
              {
                  id: "stop",
                  name: "关机",
                  type: "button",
                  disabled: true,
                  icon: "icon-stop",
                  click: "stop()"
              },
              {
                  id: "restart",
                  name: "重启",
                  type: "button",
                  disabled: true,
                  icon: "icon-undo",
                  click: "restart()"
              },
              {
                  id: "remove",
                  name: "刪除",
                  type: "button",
                  disabled: true,
                  icon: "icon-trash-o",
                  click: "deleteCs()"
              },
              {
                  id: "searchBtn",
                  type: "button",
                  align: "right",
                  class: "button radius-none button-small icon-search",
                  click: "search()"
              },
              {
                  id: "searchText",
                  type: "text",
                  align: "right",
                  width: 150,
                  placeholder: "请输入主机名"
              },
              {
                  id: "searchSel",
                  type: "select",
                  align: "right",
                  data: [{name: "全部", value: ""},
                      {name: "实例ID", value: "1"},
                      {name: "IP地址", value: "2"}, {
                          name: "状态",
                          value: "3"
                      }]
              }
          ],
          rowselect: function () {
              var selectDatas = $("#hostGrid").ptGrid("getselectedrow");
              var datas = selectDatas;
              var str=datas.isHas("已关机")?true:false;
              var str1=datas.isHas("正常")?true:false;
              var str2=datas.isHas("删除中")?true:false;
              var str3=datas.isHas("开机中")?true:false;
              var str4=datas.isHas("关机中")?true:false;
              var str5=datas.isHas("重启中")?true:false;
              var str6=datas.isHas("创建中")?true:false;
              if(!str&&str1&&!str2&&!str3&&!str4&&!str5&&!str6){
                  $("#start").attr("disabled", "disabled");
                  $("#stop").removeAttr("disabled");
                  $("#restart").removeAttr("disabled");
              }else if(str&&!str1&&!str2&&!str3&&!str4&&!str5&&!str6){
                  $("#start").removeAttr("disabled");
                  $("#stop").attr("disabled", "disabled");
                  $("#restart").attr("disabled", "disabled");
              }else {
                  initButton();
              }
              if(JSON.stringify(selectDatas)!=="[]"){
                  $("#remove").removeAttr("disabled");
              }else {
                  initButton();
              }
          }
      });
    };
    return avalon.controller(function ($ctrl) {
        $ctrl.$onEnter = function (param, rs, rj) {
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs";
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.cs-host';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            // require(['lib/jquery/pintuer']);
            initGrid();
            pintuer.init();
            hostMgt.initCharts();
            hostMgt.initChartsnet();
            hostMgt.directToOrderPage();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [hostMgt];

    });
});

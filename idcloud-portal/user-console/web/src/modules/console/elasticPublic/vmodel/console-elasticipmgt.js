/**
 * Created by Tdz on 2016/1/28.
 */
define(['app-utils/httpService','app-utils/codeService',"app-modules/console/elasticPublic/services/console-elasticipmgt", 'lib/jquery/pintuer',
        'app-utils/jqx/window', 'app-utils/messageBoxService', 'app-utils/variableService',
        'app-utils/$extendService', 'validator'],
       function (http,code,elasticipServices, pintuer, win, messageBox, variable) {

           var consoleElasticIPMgt = avalon.define({
               $id: 'consoleElasticIPMgt',
               title: "弹性公网IP管理",
               addelasticipData: null,
               orderdg: "",
               regionData:code.getCustomCode("/topologys","POST",{resTopologyType:"R"}),
               regionValue:"", // 当前区域下拉框数据
               zoneValue:"", // 当前区域分区下拉框数据
               zoneData:[], // 区域分区数据
               value1: 1,
               dataDetail: {
                   basic: {
                       instanceID: "",
                       addressIP: "",
                       monitor: "",
                       bandwidth: "",
                       state: "",
                       bindingInstance: "",
                       stateInstance: "",
                       time: ""
                   },
               },
               directToOrderPage: function () {
                   // 判断是否需要跳转到订购页面
                   var params = window.location.href;
                   var from = $.base64.atob(
                       params.split("=")[1], true);
                   //console.log(from);
                   if (from == "orderPage") {
                       consoleElasticIPMgt.orderdg =
                           variable.app_modules
                           + "/console/elasticPublic/views/console-elasticip.html";
                       $("#hostDetail").addClass("hidden");
                       $("#hostDetail").removeClass("show");
                       $("#hostmgt").addClass("hidden");
                       $("#hostmgt").removeClass("show");
                       $("#hostAdd").addClass("show");
                       $("#hostAdd").removeClass("hidden");
                       $("#hostAdd")
                           .addClass("fadein-left");
                   }
               },
               add: function () {
                   consoleElasticIPMgt.orderdg =
                       variable.app_modules
                       + "/console/elasticPublic/views/console-elasticip.html";
                   $("#elasticipmgt").addClass("hidden");
                   $("#elasticipmgt").removeClass("show");
                   $("#elasticipAdd").addClass("show");
                   $("#elasticipAdd").removeClass("hidden");
                   $("#elasticipAdd").addClass("fadein-left");
               },
               edit: function (row) {
                   //var rowDate =
                   // $("#testGrid").ptGrid("getrowdata",
                   // row);
                   alert("绑定成功");
               },
               unbundling: function (row) {
                       var rowDate = $("#eipGrid").ptGrid("getrowdata", row);
                       if (rowDate.resVmInsName !== undefined) {
                           var obj = new Object();
                           obj.zone = rowDate.zoneSid;
                           obj.floatingIpSid = rowDate.floatingIpSid;
                           obj.resVmSid = rowDate.resInstanceHostSid;
                           messageBox.confirm({
                              title:"解绑弹性公网IP",
                              message: "您确定要解绑吗？",
                              callback: function () {
                                  http.AjaxRequest({
                                   type: 'POST',
                                   url: "/rest/eip/dettachFloatingIp",
                                   params: obj,
                                   callback: function (data) {
                                       console.log(data)
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
                       } else {
                           messageBox.msgNotification(
                               {
                                   type: "warning",
                                   message: "该弹性公网IP未被绑定!"
                               });
                       }
               },
               selectunbund:function(){
                   messageBox.confirm({
                      message: "您确定要解绑吗？",
                      callback: function () {
                          var rowDate = $("#eipGrid").ptGrid("getselectedrow");
                          for (var i=0;i<rowDate.length;i++ ) {
                              var obj = new Object();
                              obj.zone = rowDate[i].zoneSid;
                              obj.floatingIpSid = rowDate[i].floatingIpSid;
                              obj.resVmSid = rowDate[i].resInstanceHostSid;
                          }
                          http.AjaxRequest({
                               type: 'POST',
                               url: "/rest/eip/dettachFloatingIp",
                               params: obj,
                               callback: function (data) {
                                   console.log(data)
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
               deleteEip:function(row){
                   messageBox.confirm({
                      title:"删除弹性公网IP",
                      message: "您确定要删除该弹性公网IP吗？",
                      callback: function () {
                          var data = $("#eipGrid").ptGrid("getrowdata", row);
                          console.log(data)
                          http.AjaxRequest({
                               type: 'GET',
                               url: "/rest/eip/release/"+data.instanceSid,
                               callback: function () {
                                   initGrid();
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
               search: function () {
                   if ($("#searchSel").val() == 1) {
                       searchObj.addressIP =
                           $("#searchText").val();
                       searchObj.state = "";
                   } else if ($("#searchSel").val() == 2) {
                       searchObj.addressIP = "";
                       searchObj.state =
                           $("#searchText").val();
                   } else {
                       searchObj.addressIP =
                           $("#searchText").val();
                       searchObj.state =
                           $("#searchText").val();
                   }
                   $('#eipGrid')
                       .ptGrid("refreshfilterrow");
               },
               backList: function () {
                   $("#elasticipAdd").addClass("hidden");
                   $("#elasticipAdd").removeClass("show");
                   $("#elasticipmgt").removeClass("hidden");
                   $("#elasticipmgt")
                       .addClass("fadein-left");
               },
               refreshclick: function () {
                   initGrid();
                   messageBox.msgNotification({
                  type: "success",
                  message: "刷新成功!"
              });
               },
               addelasticip: function (row) {
                   //console.log(rowDate)
                   var rowDate = $("#eipGrid").ptGrid("getrowdata",row);
                   if(rowDate.ipStatusName=="创建成功"&&(rowDate.resVmInsName==null||rowDate.resVmInsName=="")){
                       if (isNaN(row)) {
                           row = avalon.vmodels.consoleElasticIPMgt.selectrow;
                       }
                   win.initWindow({
                      title: "绑定弹性公网IP",
                      url: variable.app_modules
                           + "/console/elasticPublic/views/elasticip-add.html",
                      btn: ['确定', '取消'],
                      area: ['400px',
                             '250px'],
                      callback: function () {
                          consoleElasticIPMgt.addelasticipData = {
                              vmIp: rowDate.vmIp
                              //state: rowDate.state
                          };
                      },
                      confirm: function () {
                          //var rowDate = $("#eipGrid").ptGrid("getrowdata",row);
                          if ($("#elasticipForm").valid()) {
                          var obj = new Object();
                              obj.zone = rowDate.zoneSid;
                              obj.floatingIpSid=rowDate.floatingIpSid;
                              obj.subNetSid=$("#resNetworkSid").val();
                              obj.vmIp=$("#vmIp").val();
                              obj.resVmSid=$("#resVmSid").val();
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
                   }else if(rowDate.resVmInsName!=null&&rowDate.resVmInsName!=""&&rowDate.resVmInsName!==undefined){
                       console.log(rowDate.resVmInsName!==undefined)
                       messageBox.msgNotification(
                           {
                               type: "warning",
                               message: "该弹性公网IP已经被绑定!"
                           });
                   }else if (rowDate.ipStatusName=="创建中"||rowDate.ipStatusName=="异常"){
                       messageBox.msgNotification(
                           {
                               type: "error",
                               message: "该弹性公网IP未被创建成功!"
                           });
                   }
               }
           });
           consoleElasticIPMgt.$watch("regionValue", function(a, b) {
               consoleElasticIPMgt.zoneData = code.getCustomCode("/topologys","POST",{parentTopologySid:a,resTopologyType:"RZ"});
               var temp = consoleElasticIPMgt.zoneData;
               consoleElasticIPMgt.zoneValue = temp[0].resTopologySid;
           });
           // 初始化分区
           var zoom = consoleElasticIPMgt.regionData;
           consoleElasticIPMgt.regionValue = zoom[0].resTopologySid;
           var searchObj = {
               addressIP: ""
               , state: ""
           };
           //初始grid
           var initGrid = function () {
               $("#eipGrid").ptGrid({
                 selectionmode: "checkbox",
                 sortable: true,
                 controller: consoleElasticIPMgt,
                 data: {
                     //localData: [],
                     url: "/rest/eip/findFloatingIps",
                     type: 'POST',
                     params: searchObj
                 },
                 columns: [
                     {text: '实例ID', datafield: 'instanceId', sortable: true},
                     {text: 'IP地址', datafield: 'vmIp', sortable: true},
                     {text: '带宽(mbps)', datafield: 'bandWidth'},
                     {text: '状态', datafield: 'ipStatusName'},
                     {text: '绑定实例', datafield: 'resVmInsName'},
                     {text: '创建时间', datafield: 'createdDt'},
                     {
                         text: '操作',
                         datafield: '',
                         sortable: false,
                         width: 90,
                         align: "center"
                         ,
                         cellsrenderer: function (row, rowdata) {
                             var cellsHtml = "";
                             cellsHtml +=
                                 '<a href="javascript:void(0);" ms-click="addelasticip('
                                 + row + ')">绑定</a>';
                             cellsHtml +=
                                 '<span class="text-explode">|</span>';
                             cellsHtml +=
                                 '<a  href="javascript:void(0);" ms-click="unbundling('
                                 + row + ')">解绑</a>';
                             cellsHtml +=
                                 '<span class="text-explode">|</span>';
                             cellsHtml +=
                                 '<a href="javascript:void(0);" ms-click="deleteEip('
                                 + row + ')">删除</a>';
                             //cellsHtml +=
                             //    '<span class="text-explode">|</span>';
                             //cellsHtml += '<div class="button-group">';
                             //cellsHtml +=
                             //    '<button type="button" class="button dropdown-toggle">';
                             //cellsHtml +=
                             //    '更多<span class="downward"></span>';
                             //cellsHtml += '</button>';
                             //cellsHtml +=
                             //    '<ul class="drop-menu pull-right">';
                             //cellsHtml +=
                             //    '<li><a href="javascript:void(0);">修改带宽</a> </li>';
                             //cellsHtml +=
                             //    '<li><a href="javascript:void(0);" >释放</a> </li>';
                             //cellsHtml +=
                             //    '<li><a href="javascript:void(0);" >账单</a> </li>';
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
                         id: "removeBtn",
                         name: "解绑",
                         type: "button",
                         disabled: true,
                         icon: "icon-unlock",
                         click: "selectunbund()"
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
                         placeholder: "请输入搜索内容"
                     },
                     {
                         id: "searchSel",
                         type: "select",
                         align: "right",
                         data: [{name: "IP地址", value: "1"},
                             {name: "状态", value: "2"}]
                     }
                 ],
                 rowselect: function () {
                     var selectDatas = $("#eipGrid").ptGrid("getselectedrow");
                     console.log(selectDatas)
                     if(selectDatas.length==0){
                         $("#removeBtn").attr("disabled", "disabled");
                     }
                     for(var i=0;i<selectDatas.length;i++){
                         if (selectDatas.length > 0&&selectDatas[i].resVmInsName!=undefined) {
                             $("#removeBtn").removeAttr("disabled");
                         } else {
                             $("#removeBtn").attr("disabled", "disabled");
                         }
                     }
                 }
             });
           }
           return avalon.controller(function ($ctrl) {

               $ctrl.$onEnter = function (param, rs, rj) {
                   // 进入视图
                   avalon.vmodels.serviceContainer.secondNavFlag = "eip";
                   avalon.vmodels.serviceContainer.navSelectedFlag = 'console.eip-home';
               };

               $ctrl.$onRendered = function () {
                   // 视图渲染后，意思是avalon.scan完成
                   pintuer.init();
                   initGrid();
                   //validator();
               };

               $ctrl.$onBeforeUnload = function () {
                   // 视图销毁前
               };
               $ctrl.$vmodels = [consoleElasticIPMgt];
           });

       });
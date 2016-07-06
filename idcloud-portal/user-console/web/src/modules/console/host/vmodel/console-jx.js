define(['lib/jquery/pintuer','app-modules/console/host/services/console-jx','app-utils/jqx/window','app-utils/messageBoxService','app-utils/variableService','app-utils/validatorService','validator','app-utils/$extendService'], function  (pintuer,jxService,window,messageBox,variable,validate) {
    var consoleJx = avalon.define({
        $id:'consoleJx',
        title:"镜像",
        selectRow:"",
        editData:null,
        orderdg: variable.app_modules + "/user-center/orderMgt/views/order-dg.html",
        search:function(){
            if($("#searchSel").val() == 0){
                searchObj.mirrName=$("#searchText").val();
                searchObj.platform="";
                searchObj.byte="";
            }else if( $("#searchSel").val() == 1){
                searchObj.platform=$("#searchText").val();
                searchObj.mirrName="";
                searchObj.byte="";
            }else if ( $("#searchSel").val() ==2){
                searchObj.byte=$("#searchText").val();
                searchObj.mirrName="";
                searchObj.platform="";
            }else{
                searchObj.byte="";
                searchObj.mirrName="";
                searchObj.platform="";
            }
            $("#mirrorGrid").ptGrid("applyfilters");
            $('#mirrorGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"查询成功!"
            });
        },
        refreshclick:function(){
            $("#mirrorGrid").ptGrid("applyfilters");
            $('#mirrorGrid').ptGrid("refreshfilterrow");
            messageBox.msgNotification({
                type:"success",
                message:"刷新成功！"
            });
        },
        editDesc:function(row){
            window.initWindow({
                title: " 编辑自定义镜像描述",
                url: variable.app_modules+"/console/host/views/console-jx-edit.html",
                btn: ['保存', '取消'],
                area: ['500px', '380px'],
                confirm:function(){
                    if($("#editJxForm").valid()){
                        searchObj.description = $("#desc").val();
                        messageBox.msgNotification({
                            type:"success",
                            message:"编辑镜像描述成功!"
                        });
                        return true;
                    }
                    return false;
                },
                callback:function(){
                    var rowDate = $("#mirrorGrid").ptGrid("getrowdata",row);
                    avalon.vmodels.consoleJx.editData = {
                        mirrName:rowDate.mirrName,
                        mirrType:rowDate.mirrType,
                        description:rowDate.description
                    };


                }
            });
        },
        remove:function(row){
            messageBox.confirm({
                message:"您确定要删除选中的镜像吗？",
                callback:function(){
                    $("#mirrorGrid").ptGrid("removeRow");
                    $('#mirrorGrid').ptGrid("refreshfilterrow");
                    messageBox.msgNotification({
                        type:"success",
                        message:"删除镜像成功!"
                    });
                }
            });
        },
        backList: function () {
            $("#mirrList").removeClass("hidden");
            $("#mirrList").addClass("fadein-left");
            $("#hostAdd").addClass("hidden");
            $("#hostAdd").removeClass("show");
        },
        addCloudHost:function(row){
            messageBox.confirm({
                message:"您确定要创建云主机吗？",
                callback:function(){
                    $("#mirrList").addClass("hidden");
                    $("#mirrList").removeClass("show");
                    $("#hostAdd").addClass("show");
                    $("#hostAdd").removeClass("hidden");
                    $("#hostAdd").addClass("fadein-left");
                }
            });
        }
    });

    var searchObj = {
        mirrName:"",
        platform:"",
        byte:"",
        description:""
    };

    //初始化grid
    var initGrid = function(){
        $("#mirrorGrid").ptGrid({
            selectionmode:"checkbox",
           sortable:true,
            controller:consoleJx,
            data:{
                localData:jxService.getData(),
                params:searchObj
            },
            columns: [
                {text:'镜像ID/名称', datafield:"mirrName"},
                {text:"镜像类型",datafield:"mirrType"},
                {text:"镜像描述",datafield:"description"},
                {text:"平台", datafield:"platform"},
                {text:"系统位数", datafield:"byte"},
                {text:"创建时间", datafield:"creatTime"},
                {text:"状态", datafield:"status"},
                {text:"进度", datafield:"process"},
                {text: '操作', datafield: '',sortable: false,width:140,align:"center"
                    , cellsrenderer:function (row,rowdata) {
                    var cellsHtml = "";
                    cellsHtml += '<a href="javascript:void(0);" ms-click="editDesc('+row+')">编辑描述</a>';
                    cellsHtml += '<span class="text-explode">|</span>';
                    cellsHtml += '<a href="javascript:void(0);" ms-click="addCloudHost('+row+')">创建云主机</a>';
                    return cellsHtml;
                    }
                }
            ],
            toolbars:[
                {id: "refreshBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshclick()"},
                {id: "removeBtn",name:"删除",type:"button",icon:"icon-trash-o",disabled:true,click:"remove()"},
                {id: "searchBtn",type:"button",align:"right",class:"button radius-none button-small icon-search",click:"search()"},
                {id: "searchText",type:"text",align:"right",width:150,placeholder:"请输入搜索内容"},
                {id: "searchSel",type:"select",align:"right",data:[{name:"全部",value:""},{name:"镜像名称",value:"0"},{name:"平台",value:"1"},{name:"系统位数",value:"2"}]}
            ],
            rowselect:function(){
                var selectDatas = $("#mirrorGrid").ptGrid("getselectedrow");
                if(selectDatas.length >0){
                    $("#removeBtn").attr("disabled",false);
                }else{
                    $("#removeBtn").attr("disabled",true);
                }
            }
        });
    }





    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.serviceContainer.secondNavFlag = "cs"
            avalon.vmodels.userContainer.navSelectedFlag = 'console.cs-image';
        };

        $ctrl.$onRendered = function () {
            pintuer.init();
            initGrid();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [consoleJx];

    });
});


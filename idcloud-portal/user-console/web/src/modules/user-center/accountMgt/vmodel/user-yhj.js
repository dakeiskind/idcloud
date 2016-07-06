define(["lib/jquery/pintuer",
        "app-modules/user-center/accountMgt/services/user-yhj",
        'app-utils/jqx/window','app-utils/messageBoxService',
        'app-utils/codeService',
        "app-utils/$extendService"], function (pintuer,yhjService,window,messageBox,codeS){
   var userYhj = avalon.define({
      $id:"userYhj",
       title:"优惠券",
       search:function(){
           searchObjYhj.starTime = $("#beginDate").val();
           searchObjYhj.endTime = $("#endDate").val();
           searchObjYhj.status = $("#yhjStatus").val();
           $("#yhjGrid").ptGrid("applyfilters");
           $('#yhjGrid').ptGrid("refreshfilterrow");
           messageBox.msgNotification({
               type:"success",
               message:"查询成功"
           });
       },
       refreshYhjClick:function(){
           $("#yhjGrid").ptGrid("applyfilters");
           $('#yhjGrid').ptGrid("refreshfilterrow");
           messageBox.msgNotification({
               type:"info",
               message:"刷新成功"
           });
       }
   });

    var searchObjYhj = {
        starTime:"",
        endTime:"",
        status:""
    };

    //初始化充值明细搜索下拉框
    var  initSearchYHQ =function(){
        codeS.setOption('yhjStatus','COUPON_STAUS');
    };

    var initInput = function(){
        $('#beginDate').datepicker({autoclose: true});
        $('#endDate').datepicker({autoclose: true});
    };

    var initGrid = function(){
        $("#yhjGrid").ptGrid({
            sortable: true,
            controller: userYhj,
            data:{
                url: "/rest/coupons/displayPersonalCoupons",
                type: 'GET',
                params: searchObjYhj
            },
            columns: [
                { text: '优惠券代码', datafield: 'couponCode'},
                { text: '有效期开始', datafield: 'validStartDt'},
                { text: '有效期截止', datafield: 'validToDt'},
                {text:'折扣率',datafield:'discountRate'},
                {text:'用户级别',datafield:'userLevel'},
                { text: '用户群体', datafield: 'userGroup'},
                {text:'状态',datafield:'statusName'}
                //{text:'充值时间',datafield:'czTime'}
            ],
            toolbars:[
                {id: "refreshYhjBtn",name:"刷新",type:"button",icon:"icon-refresh",click:"refreshYhjClick()"}
            ]
        });
    }

    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "expense"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.expense-coupon';
        };

        $ctrl.$onRendered = function () {
            pintuer.init();
            initGrid();
            initInput();
            initSearchYHQ();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [userYhj];

    });
});

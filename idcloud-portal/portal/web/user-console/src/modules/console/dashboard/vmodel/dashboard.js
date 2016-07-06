define(['app-modules/console/dashboard/services/dashboard',"lib/jquery/pintuer"],
       function (dashboardService,pintuer) {
       var data = dashboardService.getData();
    var dashbaord = avalon.define({
        $id: 'console-dashbaord',
        data:data,
        logData:null,
        gotoHost:function(){
            avalon.router.navigate("console/host");
        },
        gotoStorage:function(){
            avalon.router.navigate("console/cbs");
        },
        gotoFloatingIp:function(){
            avalon.router.navigate("console/eip-mgt");
        },
        gotoVirNetwork:function(){
            avalon.router.navigate("console/network");
        },
        initSysTLogRecord:function(){
          var logData = dashbaord.logData;
          for(var i=0;i<logData.length;i++){
              if(i >= 5){
                  $("#vertical-timeline").append(
                      '<div class="vertical-timeline-block" >'+
                      '<div class="vertical-timeline-icon bg-blue">'+
                      '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                      '</div>'+
                      '<div class="vertical-timeline-content">'+
                      '<a href="javascrip:;" style="color: #0066ff">点击查看更多</a>'+
                      '</div>'+
                      '</div>'
                  )
                  break;
              }
              if(logData[i].actResult == '1'){
                  $("#vertical-timeline").append(
                      '<div class="vertical-timeline-block" >'+
                      '<div class="vertical-timeline-icon bg-blue">'+
                      '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                      '</div>'+
                      '<div class="vertical-timeline-content">'+
                      '<font>'+logData[i].actName+'</font>&nbsp;&nbsp;&nbsp;&nbsp;' +
                      '<font>成功</font>'+
                      '<p>'+logData[i].actDetail+'</p>'+
                      '<span>'+
                      '<small>'+logData[i].actEndDate+'</small>'+
                      '</span>'+
                      '</div>'+
                      '</div>'
                  );
              }else{
                  $("#vertical-timeline").append(
                      '<div class="vertical-timeline-block" >'+
                      '<div class="vertical-timeline-icon bg-blue">'+
                      '<i class="icon-hand-o-up" style="color: #fff"></i>'+
                      '</div>'+
                      '<div class="vertical-timeline-content">'+
                      '<font>'+logData[i].actName+'</font>&nbsp;&nbsp;&nbsp;&nbsp;' +
                      '<font>失败</font>'+
                      '<p></p>'+
                      '<span>'+
                      '<small>'+logData[i].actEndDate+'</small>'+
                      '</span>'+
                      '</div>'+
                      '</div>'
                  );
              }
          }
      }
    });
   var initData = function(){
       var logData = dashboardService.getLogData();
       dashbaord.logData = logData;
   }
    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.serviceContainer.navSelectedFlag = null;
            avalon.vmodels.serviceContainer.secondNavFlag = "console.home";
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
            initData();
            dashbaord.initSysTLogRecord();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [dashbaord];
    });

});
/**
 * Created by Administrator on 2016/2/19.
 */
// 接收url传来的产品sid
var productSid,productParentSid,canOrder,serviceScope;
// 接收全部服务的详细数据
var servicedata = new  Object();
// 获取当前服务sid
var serviceSid;
var roleSid = 0;
// 得到左边导航栏的html
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
function getListLeftTree(data,roleSid){
    var str="";
    var index = 0;
    for(var i = 0; i<data.length;i++){
        if(data[i].serviceDefineList.length === 0){
            continue;
        }else{
            if(index===0){
                str +="<ul id="+data[i].catalogSid+">"+data[i].catalogName;
            }else{
                str +="<ul id="+data[i].catalogSid+">"+data[i].catalogName;
            }

            for(var j=0;j<data[i].serviceDefineList.length;j++){
                if(index === 0){
                    initServiceContent = data[i].serviceDefineList[j];
                    str +="<li id='servicelist"+data[i].serviceDefineList[j].serviceSid
                          +"' style='cursor:pointer' class='servicelist nav_selected' "
                          + "onclick='showServiceInfo("+data[i].serviceDefineList[j].parentCatalogSid+""
                          + ",this,"+data[i].serviceDefineList[j].serviceSid+")'>&nbsp;&nbsp;&nbsp;"
                          + "<span style='position:absolute;color: grey'>"
                          +data[i].serviceDefineList[j].serviceName+"</span></li>";
                    index++;
                }else{
                    str +="<li id='servicelist"+data[i].serviceDefineList[j].serviceSid+"' "
                          + "style='cursor:pointer' class='servicelist'  "
                          + "onclick='showServiceInfo("+data[i].serviceDefineList[j].parentCatalogSid
                          +",this,"+data[i].serviceDefineList[j].serviceSid+")'>&nbsp;&nbsp;&nbsp;"
                          + "<span style='position:absolute;color: grey'>"
                          +data[i].serviceDefineList[j].serviceName+"</span></li>";
                    index++;
                }
            }
            str +="</ul>";
        }
    }
    return str;
}

$(function(){
//
//    // 获得当前用户，验证是否登录
//    Core.AjaxRequest({
//        url : ws_url + "/rest/user/current",
//        type : "GET",
//        async : false,
//        // sunyu add for #115
//        showTimeout: false,
//        // end
//        callback : function (data) {
//            var user = data.user;
//            if(typeof user != "undefined" && null != user) {
//                roleSid = user.roles[0].roleSid;
//            }
//        }
//    });

    // 初始化设置产品服务的左边导航
    //Core.AjaxRequest({
    //    url : "http://localhost:8081/rest/services/serviceTree",
    //    callback : function (data) {
    //        servicedata = data;
    //        $("#list_left_nav").html(getListLeftTree(data,roleSid));
    //        productParentSid =getQueryString("productParentSid");
    //        productSid = getQueryString("productSid");
    //        if (null == productSid || productSid == "" || productSid == "undefined" || null == productParentSid || productParentSid == "" || productParentSid == "undefined") {
    //            // 如果是没有通过链接进入产品画面，默认显示第一条信息
    //            setValue(initServiceContent);
    //        } else {
    //            $(".selectedService").removeClass("selectedService");
    //            $("#servicelist"+productSid+"").addClass("selectedService");
    //            $(".h2").removeClass("h2");
    //            $("#"+productParentSid+"").addClass("h2");
    //            setValue(getCurrentServiceInfo(Number(productSid)));
    //        }
    //    }
    //});
Core.AjaxRequest({
url: ws_url + "/rest/services/serviceTree",
type: "post",
callback: function (data) {
 servicedata = data;
 $("#list_left_nav").html(getListLeftTree(servicedata,roleSid));
 productParentSid =getQueryString("productParentSid");
 productSid = getQueryString("productSid");
 if (null == productSid || productSid == "" || productSid == "undefined" || null == productParentSid || productParentSid == "" || productParentSid == "undefined") {
     // 如果是没有通过链接进入产品画面，默认显示第一条信息
     setValue(initServiceContent);
 } else {
     $(".nav_selected").removeClass("nav_selected");
     $("#servicelist"+productSid+"").addClass("nav_selected");
     setValue(getCurrentServiceInfo(Number(productSid)));
 }
}
});
    //data=[{"catalogSid":100,"catalogParentName":null,"catalogName":"服务目录","description":"根目录","catelogGroup":null,"parentCatalogSid":null,"imagePath":null,"bgImagePath":"","tanentId":null,"status":"0","statusName":"可用","createdBy":null,"createdDt":null,"updatedBy":null,"updatedDt":null,"version":1,"value":null,"serviceDefineList":[]},{"catalogSid":101,"catalogParentName":"服务目录","catalogName":"基础设施云","description":"基础设施云","catelogGroup":"","parentCatalogSid":100,"imagePath":"/images/nav/infrastructure_cloud.png","bgImagePath":"/images/index/computeServices.png","tanentId":"","status":"1","statusName":"不可用","createdBy":"admin","createdDt":"2015-04-10 00:00:00","updatedBy":"admin","updatedDt":"2015-04-10 00:00:00","version":1,"value":null,"serviceDefineList":[{"serviceSid":100,"serviceName":"云主机服务","description":"云主机服务是基于虚拟化和自动化技术，可以即需即供，灵活定制扩展的服务器资源服务。","serviceType":"01","serviceTypeName":"原子服务","serviceStatus":"03","serviceStatusName":"已发布","pricingPlanSid":null,"serInstanceSid":null,"contractId":null,"ownerId":null,"ownerName":null,"parentCatalogSid":101,"parentCatalogName":"基础设施云","tanentId":null,"sImagePath":null,"sImagePath1":"/images/nav/service/virtual_computer_gray_64.png","sImagePath2":"/images/nav/service/virtual_computer_white_64.png","bImagePath":"/images/index/img/11.png","detailDescription":"云主机服务是基于虚拟化和自动化技术，可以即需即供，灵活定制扩展的服务器资源服务。云主机改变传统的服务器硬件购买方式为按需服务的方式，给用户带来的收益是：\r\n用户自服务，即需即供，按需服务；\r\n大幅节约IT成本，尤其是CAPEX;\r\n提高系统可用性，去除单点故障；\r\n资源弹性伸缩和扩展，随业务需求灵活变化；\r\n自动化运维，易管理；","productDescription":"<li style=\"line-height:23px\"><p>润泽企业云可基于多种虚拟化技术，为用户提供适应各种环境的X86或Unix云主机。</p></li>\r\n  <li style=\"line-height:23px\"><p>润泽企业云自带各种主流操作系统的云主机模块，用户在“服务申请”中可以选择所需的云主机模块，快速开通。用户可在“管理中心”里，通过图形化界面自行定制和创建所需的云主机模板，快速部署自定制系统。</p></li>\r\n  <li style=\"line-height:23px\"><p>用户在“服务申请”中开通云主机后，可获取对应的访问方式，并可在“管理中心”内，通过图形化界面对云主机进行整个生命周期的管理，包括：启动、关机、重启、备份、恢复、扩充硬盘、重置和修改服务器硬件性能等。</p></li>\r\n  <li style=\"line-height:23px\"><p>在“云监控服务”中，将对用户申请的服务器进行健康检查和监控，用户可通过图形化方式获取服务器实时监控信息。</p></li>\r\n  <li style=\"line-height:23px\"><p>企业云主机资源池管理员可以通过云服务管理平台内的运营管理模块，对企业云主机资源池进行统一的容量、性能、配置的管理和调度。</p></li>\r\n  <li style=\"line-height:23px\"><p>针对集团用户，润泽企业云支持多级虚拟数据中心的管理，即每个子企业只能看到本单位所分配的虚拟数据中心资源，而集团层面可以看到整个虚拟数据中心资源。</p></li>","productFeatures":"<div class=\"detaile_info\"> \r\n   <img src=\"../../images/index/no1.png\" /> \r\n   <p style=\"padding-top:9px\">支持对多种异构虚拟化技术和物理服务器的统一管理和调度。 </p> \r\n  </div> \r\n  <div class=\"detaile_info\"> \r\n   <img src=\"../../images/index/no2.png\" /> \r\n   <p style=\"padding-top:9px\">支持基于多种调度策略（均分、填满、高可用、节能优先等），在资源池内智能调度和部署云主机。</p> \r\n  </div> \r\n  <div class=\"detaile_info\"> \r\n   <img src=\"../../images/index/no3.png\" /> \r\n   <p style=\"padding-top:9px\">支持云主机的自动化运维管理和图形化操作。</p> \r\n  </div> \r\n  <div class=\"detaile_info\"> \r\n   <img src=\"../../images/index/no4.png\" /> \r\n   <p style=\"padding-top:9px\">支持在云主机上批量、自动部署软件（数据库、中间件等）。</p> \r\n  </div>  ","productCase":"<div class='case_box_s' ><div class='case_box_s1'><div style=\"line-height:23px\"><p>某运营商省公司，其业务支撑域在传统管理下存在以下挑战：\r\n\t</div></div><ul class='text-left' style=\"padding-left:0px;list-style:none;line-height:23px\">\r\n\t<p >1、基础设施资源利用率参差不齐，资源总体的利用率低，急待进行资源整合。</p>\r\n\t<p>2、缺乏快速响应能力：对于快速的资源上线、变更压力，传统的软硬绑定模式已经不能满足要求。</p>\r\n\t<p>3、业务资源管理能力薄弱：对于IT系统没有一个良好的统一管理平台，IT系统运维质量参差不齐。</p>\r\n\t</ul></div>\r\n<div class='case_box_s' ><div class='case_box_s1'><div style=\"line-height:23px\"><p><li style=\"line-height:23px\">润泽解决方案：\r\n </div></div> <ul style=\"padding-left:10px;list-style:none;line-height:23px\">\r\n\t<p>1、对资源的合理分配与调度、资源到业务到管理视图展现、IT运维流程的规范化和标准化。</p>\r\n\t<p>2、IT管理能力的提升：通过对资源的全生命周期管理，使得资源得到了良好管理和快速的响应。</p>\r\n\t<p>3、实现了可观的经济效益（硬件成本的大幅度降低）。</p>\r\n\t</ul>\r\n\t</li>\r\n</p></div>","canOrder":"1","repeatOrder":"1","helpPath":null,"softwarePackagePath":null,"expiringDate":null,"operationHandler":null,"createdBy":"admin","createdDt":"2015-09-16 00:00:00","updatedBy":"admin","updatedDt":"2014-05-27 00:00:00","version":1,"serviceConfig":null,"serviceSpec":null,"serviceScope":"00","relationService":null,"servicePerf":null,"serviceOperation":null,"sortRank":1},{"serviceSid":105,"serviceName":"块存储服务","description":"块存储服务是专为云主机设计的数据持久存储服务，用户可在块存储上驻留自己的文件系统，或者直接作为卷设备使用。","serviceType":"02","serviceTypeName":"复合服务","serviceStatus":"03","serviceStatusName":"已发布","pricingPlanSid":null,"serInstanceSid":null,"contractId":null,"ownerId":null,"ownerName":null,"parentCatalogSid":101,"parentCatalogName":"基础设施云","tanentId":null,"sImagePath":null,"sImagePath1":"/images/nav/service/storage_block_gray_64.png","sImagePath2":"/images/nav/service/storage_block_white_64.png","bImagePath":"/images/index/img/15.png","detailDescription":"块存储是专为云主机设计的数据持久存储服务，用户可在块存储上驻留自己的文件系统，或者直接作为卷设备使用。","productDescription":"<li style=\"line-height:23px\"><p>对存储的操作，状态搜集等。并且云管理平台搜集到的信息，将通过友好的界面，展现在系统管理的相应界面上，以方便用户后续的维护。</p></li>","productFeatures":"<div class=\"detaile_info\">\r\n   <img src=\"../../images/index/no1.png\" />\r\n   <p>用户可利用块存储服务创建容量 1 GB 到 1 TB 的卷，然后通过“管理中心”将这些存储卷作为硬盘进行安装。同一云主机实例可安装多个卷。 </p>\r\n  </div>\r\n  <div class=\"detaile_info\">\r\n   <img src=\"../../images/index/no2.png\" />\r\n   <p>润泽企业云可以对块存储上文件进行时间点快照，并保存在更加廉价的分布式对象存储中。用户可以用相同快照对任意多的卷进行实例化。可以跨地区复制这些快照，从而能够更轻松地实现数据中心迁移和灾难恢复。 </p>\r\n  </div>","productCase":"<li style=\"line-height:23px\"><p>需要频繁的读取和写入的应用程序和数据库，例如，需要快速访问的文件系统，数据库或原始块级存储的应用程序。</p></li>","canOrder":"1","repeatOrder":"1","helpPath":null,"softwarePackagePath":null,"expiringDate":null,"operationHandler":null,"createdBy":"admin","createdDt":"2015-09-16 00:00:00","updatedBy":"admin","updatedDt":"2014-08-14 00:00:00","version":1,"serviceConfig":null,"serviceSpec":null,"serviceScope":"00","relationService":null,"servicePerf":null,"serviceOperation":null,"sortRank":2},{"serviceSid":106,"serviceName":"弹性IP服务","description":"提供基于公网或专线的私有网络接入入口，您可以通过弹性IP将润泽企业云与其他的计算资源进行互联。","serviceType":"01","serviceTypeName":"原子服务","serviceStatus":"03","serviceStatusName":"已发布","pricingPlanSid":null,"serInstanceSid":null,"contractId":null,"ownerId":null,"ownerName":null,"parentCatalogSid":101,"parentCatalogName":"基础设施云","tanentId":null,"sImagePath":null,"sImagePath1":"/images/nav/service/relationship_DB_gray_64.png","sImagePath2":"/images/nav/service/relationship_DB_white_64.png","bImagePath":"/images/index/img/13.png","detailDescription":"提供基于公网或专线的私有网络接入入口，您可以通过弹性IP将润泽企业云与其他的计算资源进行互联。","productDescription":null,"productFeatures":null,"productCase":null,"canOrder":"1","repeatOrder":"1","helpPath":null,"softwarePackagePath":null,"expiringDate":null,"operationHandler":null,"createdBy":"admin","createdDt":"2015-09-16 00:00:00","updatedBy":"admin","updatedDt":"2014-05-28 00:00:00","version":1,"serviceConfig":null,"serviceSpec":null,"serviceScope":"00","relationService":null,"servicePerf":null,"serviceOperation":null,"sortRank":3}]},{"catalogSid":104,"catalogParentName":"服务目录","catalogName":"平台云服务","description":"平台服务云","catelogGroup":"","parentCatalogSid":100,"imagePath":"/images/nav/platform_cloud.png","bgImagePath":"/images/index/platformServices.png","tanentId":"","status":"1","statusName":"不可用","createdBy":"admin","createdDt":"2015-04-10 00:00:00","updatedBy":"admin","updatedDt":"2015-04-10 00:00:00","version":1,"value":null,"serviceDefineList":[]},{"catalogSid":105,"catalogParentName":"服务目录","catalogName":"行业应用云","description":"行业应用云","catelogGroup":"","parentCatalogSid":100,"imagePath":"/images/nav/industry_cloud.png","bgImagePath":"/images/index/storageServices.png","tanentId":"","status":"1","statusName":"不可用","createdBy":"admin","createdDt":"2015-04-10 00:00:00","updatedBy":"admin","updatedDt":"2015-04-10 00:00:00","version":1,"value":null,"serviceDefineList":[]},{"catalogSid":106,"catalogParentName":"服务目录","catalogName":"应用市场","description":"应用市场","catelogGroup":"","parentCatalogSid":100,"imagePath":"/images/nav/application_market.png","bgImagePath":"/images/index/saasService.png","tanentId":"","status":"1","statusName":"不可用","createdBy":"admin","createdDt":"2015-04-10 00:00:00","updatedBy":"admin","updatedDt":"2015-04-10 00:00:00","version":1,"value":null,"serviceDefineList":[]}]
    //servicedata = data;
    //        $("#list_left_nav").html(getListLeftTree(data,roleSid));
    //        productParentSid =getQueryString("productParentSid");
    //        productSid = getQueryString("productSid");
    //        if (null == productSid || productSid == "" || productSid == "undefined" || null == productParentSid || productParentSid == "" || productParentSid == "undefined") {
    //            // 如果是没有通过链接进入产品画面，默认显示第一条信息
    //            setValue(initServiceContent);
    //        } else {
    //            $(".nav_selected").removeClass("nav_selected");
    //            $("#servicelist"+productSid+"").addClass("nav_selected");
    //            //$(".h2").removeClass("h2");
    //            //$("#"+productParentSid+"").addClass("h2");
    //            setValue(getCurrentServiceInfo(Number(productSid)));
    //        }
});

// 显示服务的基本信息
function showServiceInfo(parentSid,obj,serviceSid){

    //$(".h2").removeClass("h2");
    //$("#"+parentSid+"").addClass("h2");
    //alert("qq")
    $(".nav_selected").removeClass("nav_selected");
    $(obj).addClass("nav_selected");

    // 产品介绍 默认选中 第一个
    $("#content_title .service_info").removeClass("active");
    $("#content_title .service_info").eq(0).addClass("active");
    $(".selected").removeClass("selected");
    $(".content_body").eq(0).addClass("selected");

    //对象存储只能订购一个
    //var flag = checkIsExistenceService(serviceSid);
    //if((serviceSid=="104"||serviceSid=="107")&&flag){
    //    $("#buttonConfig").css("display","none");
    //}else{
    //    $("#buttonConfig").css("display","block");
    //}
    setValue(getCurrentServiceInfo(serviceSid));
}

//得到当前选中的服务的具体内容
function getCurrentServiceInfo(serviceSid){
    var serviceDetailInfo =new Object();
    for(var i=0;i<servicedata.length;i++){
        if(servicedata[i].serviceDefineList === ""){
            continue;
        }else{
            for(var j=0;j<servicedata[i].serviceDefineList.length;j++){
                if(servicedata[i].serviceDefineList[j].serviceSid == serviceSid){
                    serviceDetailInfo = servicedata[i].serviceDefineList[j];
                    break;
                }
            }
        }
    }
    return serviceDetailInfo;
}

// 选项卡动画
$("#content_title .service_info").each(function(index){
    $(this).click(function(){
        $("#content_title .service_info").removeClass("active");
        $(this).addClass("active");
        $(".selected").removeClass("selected");
        $(".content_body").eq(index).addClass("selected");
    });
});

// 设置服务具体内容
function setValue(serviceDetailInfo){
    serviceSid = serviceDetailInfo.serviceSid;

    if(roleSid == 103 && serviceDetailInfo.serviceScope == 1){
        $("#buttonConfig").css("cursor","auto");
        $("#buttonConfig").css("background-position","0px -240px");
        $("#buttonConfig").hover(function(){
            $("#buttonConfig").css("background-position","0px -240px");
        },function(){
            $("#buttonConfig").css("background-position","0px -240px");
        });
    }else if(roleSid == 104 && serviceDetailInfo.serviceScope == 2){
        $("#buttonConfig").css("cursor","auto");
        $("#buttonConfig").css("background-position","0px -240px");
        $("#buttonConfig").hover(function(){
            $("#buttonConfig").css("background-position","0px -240px");
        },function(){
            $("#buttonConfig").css("background-position","0px -240px");
        });

    }else{
        if(serviceDetailInfo.canOrder == "1"){
            $("#buttonConfig").css("cursor","pointer");
            $("#buttonConfig").css("background-position","0px 0px");
            $("#buttonConfig").hover(function(){
                $("#buttonConfig").css("background-position","0px -120px");
            },function(){
                $("#buttonConfig").css("background-position","0px 0px");
            });
        }else{
            $("#buttonConfig").css("cursor","auto");
            $("#buttonConfig").css("background-position","0px -240px");
            $("#buttonConfig").hover(function(){
                $("#buttonConfig").css("background-position","0px -240px");
            },function(){
                $("#buttonConfig").css("background-position","0px -240px");
            });
        }
    }
    //$("#serviceIcon").attr("src",ctx+serviceDetailInfo.sImagePath1);
    $("#serviceIcon").attr("src","images/se_product_icon01.png");
    $("#serviceName").html(serviceDetailInfo.serviceName);
    $("#outline").html("&nbsp;&nbsp;&nbsp;"+serviceDetailInfo.detailDescription+"");
    //产品优势赋值
    $("#productAd").html();
    $("#productAd").append(serviceDetailInfo.productAdvantage);
    // 产品说明赋值
    $("#desc").html("");
    $("#desc").append(serviceDetailInfo.productDescription);
    // 产品特点赋值
    $("#feature").html("");
    $("#feature").append(serviceDetailInfo.productFeatures);

    // 应用场景和案例赋值

    $("#sceneAndCase").html("");
    $("#sceneAndCase").append(serviceDetailInfo.productCase);
}

// 申请服务 产品导购
function applicationService(){

    if($("#buttonConfig").css("background-position") != "0px -240px"){
// 			var sid = encodeURIComponent("serviceSid="+serviceSid);
        window.location.href = ctx+"/pages/order/serviceConfig.jsp?serviceSid="+serviceSid;
    }
}

// 判断该用户或租户管理员所属的租户是否申请了对象存储
//function checkIsExistenceService(serviceSid){
//    var isok = false;
//    if(typeof currentUser != "undefined" && null != currentUser){
//        Core.AjaxRequest({
//            url : ws_url + "/rest/serviceInstances/judgement/exist/service/"+serviceSid,
//            type : "GET",
//            async : false,
//            callback : function (data) {
//                if(data.result == "true"){
//                    isok = true;
//                }else{
//                    isok = false;
//                }
//            }
//        });
//    }
//    return isok;
//}
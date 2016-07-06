define(["lib/jquery/pintuer"
    ], function  (pintuer){

    var webFileUploader = avalon.define({
        $id:'webFileUploader'
    });



    return avalon.controller(function  ($ctrl) {
        $ctrl.$onEnter = function(param, rs, rj){
            //进入视图
            avalon.vmodels.userContainer.secondNavFlag = "demo"
            avalon.vmodels.userContainer.navSelectedFlag = 'user.demo-fileUploader';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
             require(["lib/webuploader/webuploader-demo"],function(webuploaderDemo){
                 webuploaderDemo.init();
             });


        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };

        $ctrl.$vmodels = [webFileUploader];

    });
});

/**
 * Created by Tdz on 2016/1/27.
 */
define(['lib/jquery/pintuer', 'app-utils/validatorService', 'validator'], function (pintuer, validate) {

    var hostDetails = avalon.define({
        $id: 'hostDetails',
        title: "云主机详情"
    });

    return avalon.controller(function ($ctrl) {

        $ctrl.$onEnter = function (param, rs, rj) {
            // 进入视图
            avalon.vmodels.serviceContainer.navSelectedFlag = 'console.hostDetails';
        };

        $ctrl.$onRendered = function () {
            // 视图渲染后，意思是avalon.scan完成
            pintuer.init();
        };

        $ctrl.$onBeforeUnload = function () {
            // 视图销毁前
        };
        $ctrl.$vmodels = [hostDetails];
    });

});
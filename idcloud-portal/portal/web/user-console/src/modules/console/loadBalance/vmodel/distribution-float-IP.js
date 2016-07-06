define(['avalon','app-utils/validatorService','validator'], function(validate) {
    var distributionFloatIP = avalon.define({
        $id:'distributionFloatIP',
        data:{
            resourcePool:""
        }
    });
    return distributionFloatIP;
});
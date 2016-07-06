define(['avalon'], function() {
    var strategyAddWindow = avalon.define({
        $id:'strategyAddWindow',
        addPnData:null,
        nextClick_first:function(){
            $("#basicInfo").addClass("hidden");
            $("#basicInfo").removeClass("show");
            $("#first").addClass("complete");
            $("#first").removeClass("active");
            $("#sec").addClass("active");
            $("#alarmRule").removeClass("hidden");
            $("#alarmRule").addClass("show");
        },
        goBackClick_first:function(){
            $("#basicInfo").addClass("show");
            $("#basicInfo").removeClass("hidden");
            $("#first").addClass("active");
            $("#first").removeClass("complete");
            $("#sec").removeClass("active");
            $("#alarmRule").removeClass("show");
            $("#alarmRule").addClass("hidden");
        },
        nextClick_sec:function(){
            $("#alarmRule").removeClass("show");
            $("#alarmRule").addClass("hidden");
            $("#sec").addClass("complete");
            $("#strategyInfo").addClass("show");
            $("#strategyInfo").removeClass("hidden");
        },
        goBackClick_sec:function(){
            $("#alarmRule").removeClass("hidden");
            $("#alarmRule").addClass("show");
            $("#sec").removeClass("complete");
            $("#sec").addClass("active");
            $("#strategyInfo").addClass("hidden");
            $("#strategyInfo").removeClass("show");
        },
        createstrategy:function(){
            if (confirm("您确定要关闭本页吗？")){
                window.opener=null;
                window.open('','_self');
                window.close();
            } else{}
        }
    });

    return strategyAddWindow;

});

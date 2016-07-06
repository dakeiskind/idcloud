define(['avalon',
        'app-utils/validatorService'
    ], function() {
    var addSnapshotWindow = avalon.define({
        $id:'addSnapshotWindow',
        data:null,
        validater:function(){
            $("#snapshotForm").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    snapshotName: {required: true, minlength:2, maxlength:128}
                },
                messages: {
                    snapshotName: {
                        required:"不能为空",
                        minlength:"快照名称为2-128个字符",
                        maxlength:"快照名称为2-128个字符"
                    }
                }
            });
        }
    });

    return addSnapshotWindow;
});
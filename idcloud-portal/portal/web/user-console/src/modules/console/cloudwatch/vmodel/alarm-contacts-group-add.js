define(['avalon',
    'app-utils/validatorService'], function() {
    var createAlarmContacts = avalon.define({
        $id:'createAlarmContactsGroup',
        data:null,
        initValidator:function(){
            $("#createAlarmContactsGroupFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    groupName: {required: true}
                },
                messages: {
                    groupName: {
                        required:"不能为空"
                    }
                }
            });
        }
    });

    return createAlarmContacts;
});
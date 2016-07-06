define(['app-utils/httpService','app-utils/codeService'
        ,'app-utils/variableService'
        ,'avalon'
        ,'app-utils/validatorService'
        ,'validator','jquery-fileupload'], function(http,codeService,variable,validate,fileUpload) {
    var addGd = avalon.define({
        $id:'addGd',
        data:{
            issueType:"",
            cloudHost:"",
            feedbackMail:null,
            feedbackPhone:null,
            issueTitle:null,
            issueDesc:null
        },
        param:{
            serviceSid : 100,
            userSid:variable.currentUser.userSid,
            account:variable.currentUser.account
        },
        init:function(){
            codeService.setOption('questionType','ISSUE_TYPE');
            var questionType = $("#questionType").find("option");
            for(var i = 0;i<questionType.length;i++){
                if(questionType.eq(i).val() == 'ISSUE_ADMIN_HINT'){
                    questionType.eq(i).attr("disabled",true);
                    break;
                }
            }
            addGd.data.issueType = $("#questionType").val();
            addGd.data.cloudHost = "";
            //addGd.data.feedbackMail = null;
            //addGd.data.feedbackPhone = null;
            addGd.data.issueTitle = null;
            addGd.data.issueDesc = null;
            addGd.initValidator();

            $("#tempUploadFile").fileupload({
                url:http.ws_url+'/rest/issue/upload',
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
                maxFileSize: 5000000, // 5 MB
                maxNumberOfFiles: 1,
                disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
                previewMaxWidth: 150,
                previewMaxHeight: 150,
                previewCrop: true,
                //formData:{path : "path"},
                beforeSend: function(XMLHttpRequest) {
                    XMLHttpRequest.setRequestHeader("authorization","Bearer "+$.cookie('IDC_TOKEN'));
                },
                done:function(e,result){
                    messageBox.msgNotification({
                        type:"success",
                        message:"文件上传成功！"
                    })
                    $("#filePathUrl").val(result.result.data);
                }
            }).on('fileuploadadd', function (e, data) {

            }).on('fileuploadfail', function (e, data) {

            })
        },
        initValidator:function(){
            $("#gdFrom").validate({
                onkeyup: function(element) {
                    $(element).valid();
                },
                rules: {
                    problemType:{required: true},
                    feedbackMail:{required: true,email: true},
                    feedbackPhone:{required: true,number: true,maxlength:11,minlength:11},
                    title:{required: true,rangelength:[1,20]},
                    problemDes:{required: true,rangelength:[1,200]}
                },
                messages: {
                    problemType:{
                        required:"请选择问题类型"
                    },
                    feedbackMail: {
                        required:"不能为空",
                        email: '请输入正确的邮箱地址'
                    },
                    feedbackPhone: {
                        required:"不能为空",
                        number: '请输入正确手机号码',
                        maxlength: '请输入正确手机号码',
                        minlength: '请输入正确手机号码'
                    },
                    title: {
                        required:"不能为空",
                        rangelength: '标题过长'
                    },
                    problemDes: {
                        required:"不能为空",
                        rangelength: '描述过长'
                    }
                },
                submitHandler:function(form){
                    alert("submitted");
                }
            });
        }
    });
    return addGd;
});

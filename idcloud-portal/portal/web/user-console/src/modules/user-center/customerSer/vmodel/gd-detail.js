define(['app-utils/messageBoxService'
        ,'app-utils/httpService'
        ,'app-utils/variableService'
        ,'avalon'], function(messageBox,http,variable){
    var gdDetail = avalon.define({
        $id:"gdDetail",
        data:{

        },
        userName:"userName",
        //追加问题
        add_problem:function(){
            var problem_content = $("#problem_content").val()
            if(problem_content == null || problem_content == ""){
                messageBox.msgNotification({
                    type:"warning",
                    message:"请输入内容!"
                });
                return;
            }
            if(problem_content.length >= 80){
                messageBox.msgNotification({
                    type:"warning",
                    message:"输入内容过长(限定80字符内)!!"
                });
                return;
            }
            var issueReply = {};
            issueReply.issueSid = gdDetail.data.issueSid;
            issueReply.content = problem_content;
            http.AjaxRequest({
                url : "/rest/issueReply/reply",
                type: "POST",
                params:issueReply,
                async:false,
                callback : function (data) {
                    if(data){
                        $("#add_problems").append(
                            '<div class="xs12" style="margin-top: 20px;">'+
                            '<div class="xs11 text-right">'+
                            '<span id="username" style="font-size: 12px;">'+variable.currentUser.realName+'</span>&nbsp;&nbsp;&nbsp;' +
                            '<span style="margin-right: 10px;font-size: 12px;color: #808080">'+getNowFormatDate()+'</span>'+
                            '<div class="popo">'+
                            '<div class="right">'+
                            '<div style="margin-right: 10px;" class="right radius text-blue" style="margin-top: 0; width: auto;">'+problem_content+'</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '<div class="text-left"><img class="radius-circle" width="50px" height="50px"  src="/user-console/static/images/user.jpg"></div>'+
                            '</div>'
                        );
                        $("textarea").val("");
                    }
                }
            });
        },
        init:function(){
            gdDetail.selectIssueReplyRecord();
            if(gdDetail.data.issueStatus == 03 || gdDetail.data.issueStatus == 04){
                $("#problem_content").attr('disabled',true);
                $("#add_problem").attr('disabled',true);
            }
        },
        //现在之前记录
        selectIssueReplyRecord:function(){
            var issueSid = gdDetail.data.issueSid;
            http.AjaxRequest({
                url : "/rest/issueReply/findReply/"+issueSid,
                type: "GET",
                async:false,
                callback : function (data) {
                    for(var i = data.length-1;i>=0;i--){
                        if(data[i].replyType == 02){
                            $("#add_problems").append(
                                '<div class="xs12" style="margin-top: 20px;">'+
                                '<div class="xs11 text-right">'+
                                '<span id="username" style="font-size: 12px;">'+data[i].createdBy+'</span>&nbsp;&nbsp;&nbsp;' +
                                '<span style="margin-right: 10px;font-size: 12px;color: #808080">'+data[i].createdDt+'</span>'+
                                '<div class="popo">'+
                                '<div class="right">'+
                                '<div style="margin-right: 10px;" class="right radius text-blue" style="margin-top: 0; width: auto;">'+data[i].content+'</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>'+
                                '<div class="text-left"><img class="radius-circle" width="50px" height="50px"  src="/user-console/static/images/user.jpg"></div>'+
                                '</div>'
                            );
                        }else{
                            $("#add_problems").append(
                                '<div class="xs12" style="margin-top: 20px;">'+
                                '<div class="xs1 text-right"><img class="radius-circle" width="50px" height="50px"  src="/user-console/static/images/admin.png"></div>'+
                                '<div class="xs11 text-left">'+
                                '<span style="margin-left: 10px;font-size: 12px;color: #808080">'+data[i].createdDt+'</span>&nbsp;&nbsp;&nbsp;'+
                                '<span id="username" style="font-size: 12px;">'+data[i].createdBy+'</span>' +
                                '<div class="popo">'+
                                '<div class="left">'+
                                '<div style="margin-left: 10px;" class="left radius text-blue" style="margin-top: 0; width: auto;">'+data[i].content+'</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>'
                            );
                        }
                    }
                }
            });
        }
    });

    //当前时间(YYYY-MM-dd HH:mm:ss)
    var getNowFormatDate = function() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    };
    return gdDetail;
});
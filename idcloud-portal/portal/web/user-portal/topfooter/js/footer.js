 //获取服务目录
Core.AjaxRequest({
     url: ws_url + "/rest/services/serviceTree",
     type: "post",
     callback: function (data) {
         createBottomServiceLis(data);
     }
 });
function createBottomServiceLis(data) {
    var str = "<ul>服务产品";
    for (var i = 0; i < data.length; i++) {
        if (data[i].serviceDefineList == "" && data[i].catalogSid == 100) {
        } else {
            if (data[i].serviceDefineList == "") {
                str +=
                    "<li><a style='cursor:pointer; style='text-decoration:none;' onclick='viewServiceDetailInfo()'>"
                    + data[i].catalogName + "</a></li>";
            } else {
                str +=
                    "<li><a style='cursor:pointer; style='text-decoration:none;' onclick='viewServiceDetailInfo("
                    + data[i].serviceDefineList[0].serviceSid + ","
                    + data[i].serviceDefineList[0].parentCatalogSid + ")'>" + data[i].catalogName
                    + "</a></li>";
            }
        }
    }
    $("#bottomUlDiv").append(str + "</ul>");
}

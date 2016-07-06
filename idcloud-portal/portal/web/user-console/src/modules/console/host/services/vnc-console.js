/**
 * Created by tdz on 2016/4/1.
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
var resSid=getQueryString("csSid");
var instanceName=getQueryString("instanceName");
    console.log(resSid)
$.ajax({
   url: ws_url+"/rest/cs/vnc/" + resSid,    //请求的url地址
   dataType: "json",   //返回格式为json
   contentType:"application/json; charset=UTF-8",
   async: false, //请求是否异步，默认为异步，这也是ajax重要特性
   type: "GET",   //请求方式
   beforeSend: function(XMLHttpRequest) {
       XMLHttpRequest.setRequestHeader("authorization","Bearer "+($.cookie('IDC_TOKEN') == 'undefined'?"":$.cookie('IDC_TOKEN')));
   },
   success: function(data) {
       if (null != data) {
           loadvnc(data);
           setTitle();
       }  //请求成功时处理
   },
   error: function() {
       //请求出错处理
   }
});

function loadvnc(data){
    $(function(){
    $("#vncconsole").append(
        '<iframe src='+data.data+' scrolling="no" frameborder="0" width="100%"'
        + ' height="100%"></iframe>'
    );
    });
}

function setTitle(){
    $(document).attr("title","金贸云-"+instanceName+"-远程控制台");
}
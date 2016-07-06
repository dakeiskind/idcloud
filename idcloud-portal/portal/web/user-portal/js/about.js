/**
 * Created by tdz on 2016/3/30.
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
var hp=getQueryString("hp");
$(function(){
    if(hp==3){
        $("#hptt1").removeClass("nav_selected");
        $("#hp1").hide();
        $("#hp2").hide();
        $("#hptt3").addClass("nav_selected");
        $("#hp3").show();
    }else if(hp==2){
        $("#hptt1").removeClass("nav_selected");
        $("#hp1").hide();
        $("#hp3").hide();
        $("#hptt2").addClass("nav_selected");
        $("#hp2").show();
    }
});
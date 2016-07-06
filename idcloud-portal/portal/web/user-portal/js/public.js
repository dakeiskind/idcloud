function checkHelp(n){
	for(var i=1;i<100;i++){
		if($("#hptt"+i).length>0){
			$("#hptt"+i).removeClass("nav_selected");
			$("#hp"+i).hide();
		}else{
			break;
		}
	}
	$("#hptt"+n).addClass("nav_selected");
	$("#hp"+n).show();
}
function omover() {
    var divlist = document.getElementById("lishistory");
    divlist.style.display = "block";

}
function omout() {
    var divlist = document.getElementById("lishistory");
    divlist.style.display = "none";
}
$(function(){
	var publiccontent = "";
	publiccontent+='<div class="quick_btn01">';
	publiccontent+='<div><a href="javascript:void(0);" class="icon_03" id="BizQQWPA1"></a></div>';
	publiccontent+='<div><a href="#" class="icon_04"></a></div>';
	publiccontent+='</div>';
	$("body").append(publiccontent);
});
$(function() {
	$("#header").load('topfooter/header.html');
	$("#footer").load('topfooter/footer.html');
});

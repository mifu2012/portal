//显示进度条
function showLoading(iframeId, rootDir) {
	var loadingDiv=document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="";
	if(loadingDiv==null) {
		loadingDiv=document.createElement("div");
		loadingDiv.id="loading_wait_Div";
		loadingDiv.style.position="absolute";
		loadingDiv.style.left="30.3%";
		loadingDiv.style.top="34.5%";
		loadingDiv.innerHTML="<img src='"+rootDir+"/common/images/loading.gif'>";
		document.body.appendChild(loadingDiv);
	}
	var iframe = document.getElementById(iframeId);
	if (iframe.attachEvent) {
		iframe.attachEvent("onload", function() {
			loadingDiv.style.display = "none";
		});
	} else {
		iframe.onload = function() {
		loadingDiv.style.display = "none";
		};
	}
}

//显示帮助按钮
function showHelpDiv(obj){ 
	document.getElementById('reportHelpDiv').style.zIndex=999; 
	document.getElementById('reportHelpDiv').style.position="absolute"; 
	if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
		document.getElementById('reportHelpDiv').style.marginLeft="-60px"; 
		document.getElementById('reportHelpDiv').style.marginTop="30px"; 
		document.getElementById('notifyicon_content').style.position="absolute"; 
		document.getElementById('notifyicon_content').style.marginTop="18px"; 
	} else { 
		document.getElementById('reportHelpDiv').style.marginLeft=(document.getElementById("divImg").offsetLeft-30)+"px";
	} 
	document.getElementById('reportHelpDiv').style.display=''; 
}

//隐藏帮助按钮
function hideHelpDiv(){
	document.getElementById('reportHelpDiv').style.display='none';
}
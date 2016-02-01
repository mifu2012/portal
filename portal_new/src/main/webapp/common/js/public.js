// JavaScript Document
/**
 * 校验必填项
 */
function checkIsMust(obj,name,content) {
	if ($('#' + obj.id).val() == "") {
		$('#'+name).html("<font color='#FFA500'>"+content+"</font>");
		obj.style.borderColor = "#FFA500";
		return false;
	}else{
		$('#'+name).html("");
		obj.style.borderColor = "";
		return true;
	}
}

/**
 * 校验数字
 */
function checkIsNumber(name){
	if($('#' + name).val() == ""){
		alert('此项不能为空!');
		return false;
	}else{
		if(isNaN($('#' + name).val())){
			alert('非数字,请重新输入!');
			return false;
		}else{
			if($('#'+name).val()<0){
				alert('数值必须大于0!');
				return false;
			}else{
				return true;
			}
		}
	}
} 
/**
 * 校验数组划分规则范围
 */
function checkInNumber(name1, name2) {
	if (checkIsNumber(name2)) {
		if ($('#' + name2).val() > 100) {
			alert("第二个数值必须小于或者等于100");
			return false;
		} else {
			if (parseInt($('#' + name2).val()) < parseInt($('#' + name1).val())) {
				alert('第二个数值必须大于第一个数值!');
				return false;
			}
			return true;
		}
	}
	return false;
}
/**
 * 验证商品价格(一般为整数或者double类型)
 * 
 */
function checkIsDouble(price){
	var reg=/^-?\d+(\.\d*)?$/; // 验证价格正则表达式
	if(reg.test($('#'+price).val())){
	    return true;
	}else{
		alert("请填写正确的商品价格!");
		return false;
	}
}

/**
 * 正则验证小数点问题
 * 以下两个方法
 * */
function check(obj,length,area,divId)
{
if(obj.value == ""){
	 document.getElementById(divId).innerHTML="<font color='#FFA500'>此项为必填项!</font>";
	 obj.style.borderColor="#FFA500";
	return false;
}
if((obj.value*1) > area){
	 document.getElementById(divId).innerHTML="<font color='#FFA500'>数值必须小于"+area+"</font>";
	 obj.style.borderColor="#FFA500";
	return false;
}
var reg =  /^[+|-]?[0-9]+\d*[\.\d]?\d{0,13}$/; //最大可允许十三位小数
if(!reg.exec(obj.value))
{
obj.className='fontred';
window.setTimeout(function(){obj.focus();},0); //这一句重要，能兼容firefox和IE
document.getElementById(divId).innerHTML="<font color='#FFA500'>输入值非法，请重新输入!</font>";
obj.style.borderColor="#FFA500";
return false;
} else {
obj.className='';
obj.value = toFixCount(obj.value, length);
toFixCount(obj.value);
obj.style.borderColor="";
document.getElementById(divId).innerHTML="";
return true;
}
}
/**
 * 转换数值
 */
function toFixCount(val, length)
{
index = val.indexOf('.');
if (val.substr(index+1).length > length)
{
return parseFloat(val).toFixed(length);
}
return val;
}
/**
 * 弹出窗体(无滚动条)
 */
function openWindow(url,title,height,width){
var top = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
var left = (window.screen.availWidth-10-width)/2; //获得窗口的水平位置;
window.open(url,title,"height="+height+",width="+width+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,top="+top+",left="+left);
}
/**
 * 弹出窗体(滚动条)
 */
function KaiWindow(url,title,height,width){
var top = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
var left = (window.screen.availWidth-10-width)/2; //获得窗口的水平位置;
window.open(url,title,"height="+height+",width="+width+",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,top="+top+",left="+left);
}
/**
 * 关闭子窗体,让父窗体刷新数据
 */
function closeWindow(){
	window.close();
	window.opener.location.reload();
}
/**
 * 输入密码至少要6位
 */
 function checkPassWord(obj){
	 if(obj.value=="")
		{
			document.getElementById("newPassworddiv").innerHTML="<font color='#FFA500'>密码不能为空！</font>";
			obj.style.borderColor="#FFA500";
			return false;
		}
	 var p=/^[0-9A-Za-z]{6,}$/;
		if(p.test(obj.value))
		{
			document.getElementById("newPassworddiv").innerHTML="<font color='#FFA500'>有效密码！</font>";
			obj.style.borderColor="";
			return true;
		}else
		{
			document.getElementById("newPassworddiv").innerHTML="<font color='#FFA500'>密码最少为6位！</font>";
			obj.style.borderColor="#FFA500";
			return false;
		}
	 
 }
 /**
  * 确认密码
  */
 function checkrepass(obj)
 {
 	if(obj.value=="")
 	{
 		document.getElementById("passWorddiv").innerHTML="<font color='#FFA500'>确认密码不能为空！</font>";
 		obj.style.borderColor="#FFA500";
 		return false;
 	}
  if(obj.value==document.getElementById('newPassword').value)
  {
 		obj.style.borderColor="";
 		return true;
 }else
 {
 document.getElementById("passWorddiv").innerHTML="<font color='#FFA500'>两次密码不一致！</font>";
 		obj.style.borderColor="#FFA500";
 		return false;
 }
 }
 /**
  * 是否是整数
  */
 function isInteger(obj,divId,content){
	 if(obj.value == ""){
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>"+content+"</font>";
	 		obj.style.borderColor="#FFA500";
	 		return false;
	 }
	 var p=/^([+-]?)(\d+)$/;
	 if(p.test(obj.value)){
	 	  obj.style.borderColor="";
	 	 document.getElementById(divId).innerHTML="";
		  return true;
	 }else{
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>非法输入,请重新填写!</font>";
	 		obj.style.borderColor="#FFA500";
		  return false;
	 }
 }
 /**
  * 是否为正整数
  * @return
  */
 function isPlusInteger(obj,divId,content){
	 if(obj.value == ""){
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>"+content+"</font>";
	 		obj.style.borderColor="#FFA500";
	 		return false;
	 }else{
		 return true;
	 }
	 
 }
 /**
  * 校验是否为浮点数
  * @return
  */
 function isFloat(obj,divId,content){
	 if(obj.value == ""){
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>"+content+"</font>";
	 		obj.style.borderColor="#FFA500";
	 		return false;
	 }
	 var p=/^([+-]?)\d*\.\d+$/;
	 if(p.test(obj.value)){
	 	  obj.style.borderColor="";
	 	 document.getElementById(divId).innerHTML="";
		  return true;
	 }else{
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>非法输入,请重新填写!</font>";
	 		obj.style.borderColor="#FFA500";
		  return false;
	 }
 }
 /**
  * 校验是否为正浮点数
  * @return
  */
 function isPlusFloat(obj,divId,content){
	 if(obj.value == ""){
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>"+content+"</font>";
	 		obj.style.borderColor="#FFA500";
	 		return false;
	 }
	 var p=/^([+]?)\d*\.\d+$/;
	 if(p.test(obj.value)){
	 	  obj.style.borderColor="";
	 	 document.getElementById(divId).innerHTML="";
		  return true;
	 }else{
		 document.getElementById(divId).innerHTML="<font color='#FFA500'>非法输入,请重新填写!</font>";
	 		obj.style.borderColor="#FFA500";
		  return false;
	 }
 }
 /**
  * 处理选择框
  */
 function selectChange(){
	 var v_check="";
	$('.cheese').each(function(i,v){
	if(v.checked==true){v_check=v.value;}
	});
	if(v_check == '1'){
	    $('#regex').attr('disabled',true);
	    $('#begin').attr('disabled',false);
	    $('#begin1').attr('disabled',false);
	}else{
	    $('#regex').attr('disabled',false);
	    $('#begin').attr('disabled',true);
	    $('#begin1').attr('disabled',true);
	}
	}

 /**
  * AJAX校验唯一性
  */
 function validateUnique(obj,name,content,url,params,newValue){
	 if(checkIsMust(obj,name,content)){
		 if(newValue!="" && newValue==obj.value){
			 document.getElementById('uniquesIgn').setAttribute('value','1');
		 }else{
			 sendRequest(url, params, "POST", function() {
					if (xhr.readyState == 4) {
						if (xhr.status == 200) {
							if(xhr.responseText == '1'){
								obj.style.borderColor="";
								document.getElementById('uniquesIgn').setAttribute('value','1');
							}else{
								document.getElementById(name).innerHTML='<font color="#FFA500">此项数据已存在,请重新填写!</font>';
								obj.style.borderColor="#FFA500";
								document.getElementById('uniquesIgn').setAttribute('value','0');
							}
						} 
					}
				});
		 }
	
	 }
 }
 /**
  *校验只能是数字，字母，下划线组成
  **/
 function checkShoppingId(obj,divname,content,url,params,newValue){
	 if(obj.value == ""){
		 document.getElementById(divname).innerHTML='<font color="#FFA500">'+content+'</font>';
		 obj.style.borderColor="#FFA500";
		 document.getElementById('uniquesIgn').setAttribute('value','0');
		 return false;
	 }
	 var reg = /^\w+$/;
	 if(reg.test(obj.value)){
		return  validateUnique(obj,divname,content,url,params,newValue);
	 }else{
		 document.getElementById(divname).innerHTML='<font color="#FFA500">该项只能有字母,数字和下划线组成!</font>';
		 obj.style.borderColor="#FFA500";
		 document.getElementById('uniquesIgn').setAttribute('value','0');
		 return false;
	 }

 }
 function showdiv(divid){
		document.getElementById(divid).style.display="";
	}
 
document.writeln('<div id=meizzWeekLayer style="position: absolute; width: 120px; height: 200px; z-index: 9998; display:none;border:0px solid  red;">');
document.writeln('<span id=tmpSelectYearLayerOfWeek style="z-index:9999; position:absolute; top:2px; left:18px; display: none">');
document.writeln('</span>');
document.writeln('<table border=1 cellspacing=1 cellpadding=0 width=120px height=200px bgcolor=#cccccc onselectstart="return false"> ');
document.writeln('<tr><td width=120px height=17px bgcolor=#FFFFFF> ');
document.writeln('<table border=0 cellspacing=1 cellpadding=0 width=100% height=15px> ');
document.writeln('<tr align=center><td Author=meizz align=left> ');
document.writeln(' <input Author=meizz type=button value="" title="前一年" onclick="meizzPrevYOfWeek()"  onfocus="this.blur()"  ');
document.writeln('style="background:url(../common/js/My97DatePicker/skin/default/img.gif) no-repeat;cursor:pointer; BACKGROUND-COLOR:#ffffff; BORDER-BOTTOM:#808080 0px outset; BORDER-LEFT:#808080 0px  ');
document.writeln('outset; BORDER-RIGHT:#808080 0px outset; BORDER-TOP:#808080 0px outset; FONT-SIZE:12px; height:15px;width:32px;');
document.writeln(' color: #ccffff; font-weight: bold"></td> ');
document.writeln(' <td width=70px align=center style="font-size:12px;cursor:default" Author=meizz> ');
document.writeln(' <span Author=meizz id=meizzYearHeadOfWeek onmouseover="style.backgroundColor=\'#ccffff\'"  ');
document.writeln(' onmouseout="style.backgroundColor=\'white\' " title="点击这里选择年份"  ');
document.writeln(' onclick="tmpSelectYearInnerHTMLOfWeek(this)" style="cursor: pointer;width:100%;"></span>年</td> ');
document.writeln(' <td Author=meizz align=right><input name="button" type=button style="background:url(../common/js/My97DatePicker/skin/default/img.gif) no-repeat -32px 0px;cursor:pointer; BACKGROUND-COLOR:#ffffff;  ');
document.writeln('BORDER-BOTTOM:#808080 0px outset; BORDER-LEFT:#808080 0px outset; BORDER-RIGHT:#808080 0px outset;  ');
document.writeln(' BORDER-TOP:#808080 0px outset; font-size:12px; height:15px; color:#ccffff; font-weight:bold;width:32px;" title="后一年"  ');
document.writeln(' onfocus="this.blur()" onclick="meizzNextYOfWeek()" value=""  author=meizz /></td> ');
document.writeln(' </tr></table></td></tr> ');
document.writeln('<tr><td width=122px height=60px> ');
document.writeln('<table border=0 cellspacing=1 cellpadding=0 width=100% height=60px bgcolor=#FFFFFF> ');
var n=0; for (var j=0;j<9;j++){ document.writeln (' <tr align=center>'); for (var i=0;i<6;i++){
document.writeln('<td width=10 height=10 id=meizzWeek'+n+' style="font-size:12px" Author=meizz onclick=meizzMonthClickOfWeek(this) onmouseover="this.style.background=\'#ccffff\';" onmouseout="this.style.background=\'white\';"></td>');n++;}
document.writeln('</tr>');}
document.writeln(' </table></td></tr> ');
document.writeln(' <tr><td> ');
document.writeln(' <table border=0 cellspacing=1 cellpadding=0 width=100% bgcolor=#FFFFFF> ');
document.writeln(' <tr><td Author=meizz align=left><input Author=meizz type=button value="" title="前一年"  ');
document.writeln('onclick="meizzPrevYOfWeek()"  onfocus="this.blur()" style="background:url(../common/js/My97DatePicker/skin/default/img.gif) no-repeat;	cursor: pointer;BACKGROUND-COLOR: ');
document.writeln('#ffffff;BORDER-BOTTOM: #808080 0px outset; BORDER-LEFT: #808080 0px outset; BORDER-RIGHT: #808080 0px ');
document.writeln('outset; BORDER-TOP: #808080 0px outset; FONT-SIZE: 12px; height: 15px;width:32px;color: #ccffff; font-weight: ');
document.writeln('bold"></td> ');
document.writeln('<td  Author=meizz align=center><input Author=meizz type=button value="关闭" onclick="document.getElementById(\'meizzWeekLayer\').style.display=\'none\';" ');
document.writeln('onfocus="this.blur()" title="显示当前年月" style="cursor: pointer;BACKGROUND-COLOR: ');
document.writeln('#ffffff;BORDER-BOTTOM: #808080 0px outset; BORDER-LEFT: #808080 0px outset; BORDER-RIGHT: #808080 ');
document.writeln('0px outset; BORDER-TOP: #808080 0px outset;font-size: 12px; height: 15px;color: #000000;"></td> ');
document.writeln('<td   Author=meizz align=right><input name="button" type=button style="background:url(../common/js/My97DatePicker/skin/default/img.gif) no-repeat -32px 0px;cursor: pointer;BACKGROUND-COLOR: ');
document.writeln('#ffffff;BORDER-BOTTOM: #808080 0px outset; BORDER-LEFT: #808080 0px outset; BORDER-RIGHT: #808080 0px ');
document.writeln('outset; BORDER-TOP: #808080 0px outset;font-size: 12px; height: 15px;width:32px;color: #ccffff; font-weight: ');
document.writeln('bold" title="后一年"  onfocus="this.blur()" onclick="meizzNextYOfWeek()" value=""  author=meizz /></td> ');
document.writeln(' </tr> ');
document.writeln(' </table> ');
document.writeln(' </td></tr> ');
document.writeln('</table></div> ');
var outObject;

var Sys = {}; 
var ua = navigator.userAgent.toLowerCase(); 
var s; 
(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : 
(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : 
(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : 
(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0; 

//以下进行测试 
//if (Sys.ie) document.write('IE: ' + Sys.ie); 
//if (Sys.firefox) document.write('Firefox: ' + Sys.firefox); 
//if (Sys.chrome) document.write('Chrome: ' + Sys.chrome); 
//if (Sys.opera) document.write('Opera: ' + Sys.opera); 
//if (Sys.safari) document.write('Safari: ' + Sys.safari); 

function getEventOfWeek(event){
   var e = event || window.event;  
   if(e!=null) return e;
   if (!e) {  
        var c = this.getEvent.caller;
        while (c) {  
            e = c.arguments[0];  
            if (e && (Event == e.constructor || MouseEvent == e.constructor)) {  
                break;  
           }  
            c = c.caller;  
       }  
   }  
   return e;  
} 

var th;
function setWeek(tt,obj) //主调函数
{
  if (arguments.length >  3){alert("对不起！传入本控件的参数太多！");return;}
  if (arguments.length == 0){alert("对不起！您没有传回本控件任何参数！");return;}
  var dads  = document.getElementById("meizzWeekLayer").style;
  th = tt;
  var ttop  = tt.offsetTop;     //TT控件的定位点高
  var thei  = tt.clientHeight;  //TT控件本身的高
  var tleft = tt.offsetLeft;    //TT控件的定位点宽
  var ttyp  = tt.type;          //TT控件的类型
  while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
  dads.top  = (ttyp=="image")? ttop+thei+'px' : ttop+thei+3+'px';
  dads.left = tleft+'px';
  outObject = (arguments.length == 1) ? th : obj;
  dads.display = '';

  var event= getEventOfWeek();
  event.returnValue=false;
  try{
	   event.preventDefault();
  }catch(ex){}
  //默认控件当前的年份
  //#########################
  try{
	  var crtObj=event.srcElement==null?event.target:event.srcElement;
	  var inputDate=crtObj==null?"":crtObj.value;
	  if(inputDate!=null&&inputDate.length>0)
	   {
		   var year=inputDate.split("-")[0];
		   var month=inputDate.split("-")[1];
		   //alert("CRT_YEAR:"+document.getElementById("meizzYearHeadOfWeek").innerText);
		   //document.getElementById("tmpSelectYearOfWeek").value=year;
		   document.getElementById("meizzYearHeadOfWeek").innerHTML=year;
		   yearChuOfWeek=year;
	   }
  }catch(ex){alert(ex.message);}
 //默认控件当前的年份 ####################  
}

var yearChuOfWeek=new Date().getFullYear(); //定义年的变量的初始值
var xieMonthChuOfWeek=new Array(53);               //定义写日期的数组
//function document.onclick()  //任意点击时关闭该控件
//{ 
//  with(window.event.srcElement)
//  { if (tagName != "INPUT" && getAttribute("Author")==null)
//    document.all.meizzWeekLayer.style.display="none";
//  }
//}

function meizzWriteHeadOfWeek(yy)  //往 head 中写入当前的年与月
{ 
  if(Sys.firefox){
	  document.getElementById("meizzYearHeadOfWeek").textContent  = yy;
  }else{
	  document.getElementById("meizzYearHeadOfWeek").innerText  = yy;
  };
}

function tmpSelectYearInnerHTMLOfWeek(selYear) //年份的下拉框
{
  var strYear;
  if(Sys.firefox){
	  strYear= selYear.textContent;
  }else{
	  strYear=selYear.innerText;
  };
  if (strYear.match(/\D/)!=null){alert("年份输入参数不是数字！");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("年份值不在 1000 到 9999 之间！");return;}
  var n = m - 10;
  if (n < 1000) n = 1000;
  if (n + 26 > 9999) n = 9974;
  var s = "<select Author=meizz id=tmpSelectYearOfWeek style='font-size: 12px'";
     s += "onblur='document.getElementById(\"tmpSelectYearLayerOfWeek\").style.display=\"none\";meizzSetMonthOfWeek(this.value);'";
     s += "onchange='document.getElementById(\"tmpSelectYearLayerOfWeek\").style.display=\"none\";meizzSetMonthOfWeek(this.value);'";
     s += "yearChuOfWeek = this.value; meizzSetMonthOfWeek(yearChuOfWeek)'>\r\n";
  var selectInnerHTML = s;
  for (var i = n; i < n + 26; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='" + i + "' selected>" + i + "年" + "</option>\r\n";}
    else {selectInnerHTML += "<option value='" + i + "'>" + i + "年" + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  document.getElementById("tmpSelectYearLayerOfWeek").style.display="";
  document.getElementById("tmpSelectYearLayerOfWeek").innerHTML = selectInnerHTML;
  document.getElementById("tmpSelectYearOfWeek").focus();
}



function closeLayerOfWeek()               //这个层的关闭
  {
    document.getElementById("meizzWeekLayer").style.display="none";
  }

//function document.onkeydown()
//  {
//    if (window.event.keyCode==27)document.all.meizzWeekLayer.style.display="none";
//  }


function meizzPrevYOfWeek()  //往前翻 Year
  {
    if(yearChuOfWeek > 999 && yearChuOfWeek <10000){yearChuOfWeek--;}
    else{alert("年份超出范围（1000-9999）！");}
	//alert(yearChuOfWeek);
    meizzSetMonthOfWeek(yearChuOfWeek);
  }
function meizzNextYOfWeek()  //往后翻 Year
  {
    if(yearChuOfWeek > 999 && yearChuOfWeek <10000){yearChuOfWeek++;}
    else{alert("年份超出范围（1000-9999）！");}
    meizzSetMonthOfWeek(yearChuOfWeek);
  }
function meizzToMonthOfWeek()  //ToMonth Button
{
    yearChuOfWeek = new Date().getFullYear();
   meizzSetMonthOfWeek(yearChuOfWeek);
}

function meizzSetMonthOfWeek(yy)   //主要的写程序**********
{
  //alert(yy);
  meizzWriteHeadOfWeek(yy);
  //一年多少周
  var weekCount=weekCountOfYear(yy);
  for (var i = 0; i < 54; i++){xieMonthChuOfWeek[i]=i+1;}  //将显示框的内容全部清空
  //当前第几周
  var crtWeekNo=weekNo();
  for (var i = 0; i < xieMonthChuOfWeek.length; i++)
  { 
    var da = eval("document.getElementById('meizzWeek"+i+"')");     //书写新的一个月的日期星期排列
    if(da!=null) da.style.display="";
    if (xieMonthChuOfWeek[i]!="")
      { da.innerHTML = "<b>" + xieMonthChuOfWeek[i] + "</b>";
	    if(crtWeekNo==xieMonthChuOfWeek[i])
		  {
              da.style.backgroundColor ="#ccffff";
		  }else
		  {
              da.style.backgroundColor ="#ffffff";
		  }
        da.style.cursor="pointer";
      }
     else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default";}
  }
  //隐藏
  for(var i=weekCount;i<=xieMonthChuOfWeek.length; i++)
  {
     var da = eval("document.getElementById('meizzWeek"+i+"')");
	 if(da!=null) da.style.display="none";
  }
}
//当前第几周
function weekNo() {
  var totalDays = 0;    
  now = new Date();
  years=now.getYear();
  if (years < 1000)
  years+=1900;
  var days = new Array(12); // Array to hold the total days in a month
  days[0] = 31;
  days[2] = 31;
  days[3] = 30;
  days[4] = 31;
  days[5] = 30;
  days[6] = 31;
  days[7] = 31;
  days[8] = 30;
  days[9] = 31;
  days[10] = 30;
  days[11] = 31;
 
  //  Check to see if this is a leap year
 
   if (Math.round(now.getYear()/4) == now.getYear()/4) {
     days[1] = 29;
  }else{
     days[1] = 28;
  }
 
//  If this is January no need for any fancy calculation otherwise figure out the
//  total number of days to date and then determine what week
 
  if (now.getMonth() == 0) {  
     totalDays = totalDays + now.getDate();
  }else{
     var curMonth = now.getMonth();
     for (var count = 1; count <= curMonth; count++) {
         totalDays = totalDays + days[count - 1];
     }
     totalDays = totalDays + now.getDate();
   }
   var week = Math.round(totalDays/7);
   return week;
}

//取一年多少周
function weekCountOfYear(year)
{
  var d=new Date(year,0,1);
  var yt=( ( year%4==0 && year%100!=0) || year%400==0)? 366:365; 
  //下面的算法是，将新年第一天作为第一周(500W要求)
  if (d.getDay() == 0) {
   return Math.ceil((yt+6)/7.0);
  } else {
   return Math.ceil((yt+d.getDay()-1)/7.0);
  }
  //下面是原始周数
  // return Math.ceil((yt-d.getDay())/7.0);
}

function meizzMonthClickOfWeek(no)  //点击显示框选取日期，主输入函数*************
{
  var n;
  if(Sys.firefox){
	  n= no.textContent;
  }else{
	  n=no.innerText;
  };
  if(n<10)n='0'+n;
  var yy = yearChuOfWeek;
  
  if (outObject)
  {
    if (!n) {outObject.value=""; return;}
   
    outObject.value= yy + "-" + n  ; //注：在这里你可以输出改成你想要的格式
    closeLayerOfWeek();
    th.onchange();
  }
  else {closeLayerOfWeek(); alert("您所要输出的控件对象并不存在！");}
}
meizzSetMonthOfWeek(yearChuOfWeek);
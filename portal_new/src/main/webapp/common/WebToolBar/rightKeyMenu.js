	/*******以下内容可以修改***************/ 
	var mname=new Array( 
	"&nbsp;新增一子项(<u>S</u>)..", 
	"&nbsp;修部该子项(<u>F</u>)..", 
	"<hr>",
	"&nbsp;删除该子项(<u>Y</u>).." 
	); 
	//mname是菜单对应的名称，数组的个数必须与下面murl对应 

	var murl=new Array( 
	"createOption();", 
	"modifyOption();", 
	"",
	"deleteOption();" 
	); 
	//murl是菜单对应的操作，可以是任意javascript代码但是要注意不要在里面输入\"，只能用' 
	//如果要实现跳转可以这样window.location='url';  
	var ph=18,mwidth=50;//每条选项的高度,菜单的总宽度 
	var bgc="#D4D0C8",txc="black";//菜单没有选中的背景色和文字色 
	var cbgc="#000080",ctxc="#ffffff";//菜单选中的选项背景色和文字色 

	/****************以下代码请不要修改******************/ 
	var mover="this.style.background='"+cbgc+"';this.style.color='"+ctxc+"';" 
	var mout="this.style.background='"+bgc+"';this.style.color='"+txc+"';" 

	document.oncontextmenu=function() 
	{  
	 return false; 
	}
	
    document.onclick=function()
    {
       mlay.style.display="none"; 
    }

	function showoff() 
	{ 
	  //mlay.style.display="none"; 
	    //alert('ddd');
	  	mlay.style.display=""; 
	    mlay.style.pixelTop=event.clientY; 
	    mlay.style.pixelLeft=event.clientX; 
	} 
    //onload="fresh();"
	function fresh() 
	{ 
		 mlay.style.background=bgc; 
		 mlay.style.color=txc; 
		 mlay.style.width=mwidth; 
		 mlay.style.height=mname.length*ph; 
		 var menuContent="<div  style='position: absolute; background-color: #D4D0C8; border: 2px outset #FFFFFF;width: 0; height: 0;padding:1;'>";
		menuContent+="<table width=110px height="+mname.length*ph+"px cellpadding=0  cellspacing=0 border=0 border='1'>"; 
		 var i=0; 
		 for(i=0;i<mname.length;i++) 
		 { 
		  menuContent+="\n<tr align=center height="+ph+" onclick=\""+murl[i]+"mlay.style.display='none'; \"  onMouseover=\""+mover+"\" onMouseout=\""+mout+"\">\n<td style='font-size:9pt;cursor:hand;' align='left'>\n"+mname[i]+"\n</td>\n</tr>"; 
		 } 
		 menuContent+="\n</table></div>"; 
		 mlay.innerHTML=menuContent; 
	} 
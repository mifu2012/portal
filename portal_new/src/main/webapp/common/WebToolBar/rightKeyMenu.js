	/*******�������ݿ����޸�***************/ 
	var mname=new Array( 
	"&nbsp;����һ����(<u>S</u>)..", 
	"&nbsp;�޲�������(<u>F</u>)..", 
	"<hr>",
	"&nbsp;ɾ��������(<u>Y</u>).." 
	); 
	//mname�ǲ˵���Ӧ�����ƣ�����ĸ�������������murl��Ӧ 

	var murl=new Array( 
	"createOption();", 
	"modifyOption();", 
	"",
	"deleteOption();" 
	); 
	//murl�ǲ˵���Ӧ�Ĳ���������������javascript���뵫��Ҫע�ⲻҪ����������\"��ֻ����' 
	//���Ҫʵ����ת��������window.location='url';  
	var ph=18,mwidth=50;//ÿ��ѡ��ĸ߶�,�˵����ܿ�� 
	var bgc="#D4D0C8",txc="black";//�˵�û��ѡ�еı���ɫ������ɫ 
	var cbgc="#000080",ctxc="#ffffff";//�˵�ѡ�е�ѡ���ɫ������ɫ 

	/****************���´����벻Ҫ�޸�******************/ 
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
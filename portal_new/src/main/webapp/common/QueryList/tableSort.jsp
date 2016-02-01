<%@ page contentType="application/x-javascript;charset=GBK" %>
var table_sort_count=0;
/**//**************************************************************************
排序的主方法，有三个形参，STableTd,iCol,sDataType分别为需要排序的表格ID，
需要排序的表格列号，所在列的数据类型（支持int,float,date,string四种数据类型)
**************************************************************************/
function sortTable(sDataType)
{
	var oTable=null;
	if(event.srcElement.parentElement.parentElement.tagName.toLowerCase()=="tr")
	{
	  oTable=event.srcElement.parentElement.parentElement.parentElement;
	}else
	{
	  oTable=event.srcElement.parentElement.parentElement.parentElement;
	}
	if(oTable==null||oTable.tagName.toLowerCase()!="table") return;
	//生成提示
	var loadingDiv = document.getElementById('loading_wait_div');
	if(loadingDiv==null)
	{
		loadingDiv=document.createElement("<div id='loading_wait_div' name='loading_wait_div' style='border:0px solid orange;position: absolute; left: 40%; top: 40%;display:;' ></div>");
		loadingDiv.innerHTML="<img src='<%=request.getContextPath()%>/common/images/indicator.gif' title='正在处理中...'>";
		document.body.appendChild(loadingDiv);
	}
	//重新生成图片
	//表格
	var oTbody=oTable.children.item(0);
	//第一行
	var oTR=oTbody.children.item(0);
	//列
	for(var j=0;j<oTR.children.length;j++)
	{
	   //默认都不排序
	   if(oTR.children.item(j).onclick==null) continue;
	   oTR.children.item(j).innerHTML=oTR.children.item(j).innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_off.png'>";
	}
	if(table_sort_count%2==0)  //升序
	{
		event.srcElement.innerHTML=event.srcElement.innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_up.png'>";
	}
	else if(table_sort_count%2==1) //降序
	{
		event.srcElement.innerHTML=event.srcElement.innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_down.png'>";
	}
	//当前排序列索引
	var iCol=event.srcElement.cellIndex;
    var oTbody=oTable.tBodies[0]; //获取表格的tbody
    var colDataRows=oTbody.rows; //获取tbody里的所有行的引用

    var aTRs=new Array(); //定义aTRs数组用于存放tbody里的行
    for(var i=0;i<colDataRows.length;i++)  //依次把所有行放如aTRs数组
    {
        aTRs.push(colDataRows[i]);
    }
    /**//***********************************************************************
    sortCol属性是额外给table添加的属性，用于作顺反两种顺序排序时的判断，区分
    首次排序和后面的有序反转
    ************************************************************************/
	if(oTable.getAttribute("sortCol")==null)
	{
       oTable.setAttribute("sortCol","-1");
	}
    if(oTable.sortCol==iCol)  //非首次排序
    {
        aTRs.reverse();
    }
    else    //首次排序
    {
        if(table_sort_count%2==0)  //升序
        {
            aTRs.sort(generateCompareTRs(iCol,sDataType));
        }
        else if(table_sort_count%2==1) //降序
        {
            aTRs.sort(generateCompareTRs1(iCol,sDataType));
        }
    }
    var oFragment=document.createDocumentFragment();    //创建文档碎片
    for(var i=0;i<aTRs.length;i++)   //把排序过的aTRs数组成员依次添加到文档碎片
    {
        oFragment.appendChild(aTRs[i]);
    }
    oTbody.appendChild(oFragment);  //把文档碎片添加到tbody,完成排序后的显示更新 
    oTable.sortCol=iCol;    //把当前列号赋值给sortCol,以此来区分首次排序和非首次排序,//sortCol的默认值为-1
	table_sort_count++;
	//提示消失
	if(loadingDiv!=null) loadingDiv.style.display="none";
};

//比较函数，用于两项之间的排序
//升序
function generateCompareTRs(iCol,sDataType)
{
    return   function compareTRs(oTR1,oTR2)
    {
        var vValue1=convert(oTR1.cells[iCol].firstChild.innerText==null?oTR1.cells[iCol].firstChild.nodeValue:oTR1.cells[iCol].firstChild.innerText,sDataType);//nodeValue to innerText
        var vValue2=convert(oTR2.cells[iCol].firstChild.innerText==null?oTR2.cells[iCol].firstChild.nodeValue:oTR2.cells[iCol].firstChild.innerText,sDataType);
        if(vValue1<vValue2)
        {
            return -1;
        }
        else if(vValue1>vValue2)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    };
};

//降序
function generateCompareTRs1(iCol,sDataType)
{
    return   function compareTRs(oTR1,oTR2)
    {
        var vValue1=convert(oTR1.cells[iCol].firstChild.innerText==null?oTR1.cells[iCol].firstChild.nodeValue:oTR1.cells[iCol].firstChild.innerText,sDataType);//nodeValue to innerText
        var vValue2=convert(oTR2.cells[iCol].firstChild.innerText==null?oTR2.cells[iCol].firstChild.nodeValue:oTR2.cells[iCol].firstChild.innerText,sDataType);
        if(vValue1>vValue2)
        {
            return -1;
        }
        else if(vValue1<vValue2)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    };
};

//数据类型转换函数
function convert(sValue,sDataType)
{
    switch(sDataType)
    {
        case "int":return parseInt(sValue);
        case "float": return parseFloat(sValue);
        case "date":return new Date(sValue.replace(eval("/-/g"),"/"));;
        default:return sValue.toString();
    }
};

window.onload=function()
{
	var allTable=document.getElementsByTagName("table");
	if(allTable==null||allTable.length==0) return;
	for(var i=0;i<allTable.length;i++)
	{
		if(allTable[i].getAttribute("isSort")==null||allTable[i].getAttribute("isSort")!="1") continue;
		//表格
		var oTbody=allTable[i].children.item(0);
		//第一行
		var oTR=oTbody.children.item(0);
		//列
		for(var j=0;j<oTR.children.length;j++)
		{
		   //提示可排序
		   if(oTR.children.item(j).onclick==null) continue;
		   oTR.children.item(j).style.cursor="hand";
		   oTR.children.item(j).title="点击标题进行排序";
           oTR.children.item(j).innerHTML=oTR.children.item(j).innerHTML.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_off.png'>";
		}
	}
}

//去前后空格
String.prototype.trim = function()
{
  return this.replace(/(^\s+)|\s+$/g,"");
}
<%@ page contentType="application/x-javascript;charset=GBK" %>
var table_sort_count=0;
/**//**************************************************************************
��������������������βΣ�STableTd,iCol,sDataType�ֱ�Ϊ��Ҫ����ı��ID��
��Ҫ����ı���кţ������е��������ͣ�֧��int,float,date,string������������)
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
	//������ʾ
	var loadingDiv = document.getElementById('loading_wait_div');
	if(loadingDiv==null)
	{
		loadingDiv=document.createElement("<div id='loading_wait_div' name='loading_wait_div' style='border:0px solid orange;position: absolute; left: 40%; top: 40%;display:;' ></div>");
		loadingDiv.innerHTML="<img src='<%=request.getContextPath()%>/common/images/indicator.gif' title='���ڴ�����...'>";
		document.body.appendChild(loadingDiv);
	}
	//��������ͼƬ
	//���
	var oTbody=oTable.children.item(0);
	//��һ��
	var oTR=oTbody.children.item(0);
	//��
	for(var j=0;j<oTR.children.length;j++)
	{
	   //Ĭ�϶�������
	   if(oTR.children.item(j).onclick==null) continue;
	   oTR.children.item(j).innerHTML=oTR.children.item(j).innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_off.png'>";
	}
	if(table_sort_count%2==0)  //����
	{
		event.srcElement.innerHTML=event.srcElement.innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_up.png'>";
	}
	else if(table_sort_count%2==1) //����
	{
		event.srcElement.innerHTML=event.srcElement.innerText.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_down.png'>";
	}
	//��ǰ����������
	var iCol=event.srcElement.cellIndex;
    var oTbody=oTable.tBodies[0]; //��ȡ����tbody
    var colDataRows=oTbody.rows; //��ȡtbody��������е�����

    var aTRs=new Array(); //����aTRs�������ڴ��tbody�����
    for(var i=0;i<colDataRows.length;i++)  //���ΰ������з���aTRs����
    {
        aTRs.push(colDataRows[i]);
    }
    /**//***********************************************************************
    sortCol�����Ƕ����table��ӵ����ԣ�������˳������˳������ʱ���жϣ�����
    �״�����ͺ��������ת
    ************************************************************************/
	if(oTable.getAttribute("sortCol")==null)
	{
       oTable.setAttribute("sortCol","-1");
	}
    if(oTable.sortCol==iCol)  //���״�����
    {
        aTRs.reverse();
    }
    else    //�״�����
    {
        if(table_sort_count%2==0)  //����
        {
            aTRs.sort(generateCompareTRs(iCol,sDataType));
        }
        else if(table_sort_count%2==1) //����
        {
            aTRs.sort(generateCompareTRs1(iCol,sDataType));
        }
    }
    var oFragment=document.createDocumentFragment();    //�����ĵ���Ƭ
    for(var i=0;i<aTRs.length;i++)   //���������aTRs�����Ա������ӵ��ĵ���Ƭ
    {
        oFragment.appendChild(aTRs[i]);
    }
    oTbody.appendChild(oFragment);  //���ĵ���Ƭ��ӵ�tbody,�����������ʾ���� 
    oTable.sortCol=iCol;    //�ѵ�ǰ�кŸ�ֵ��sortCol,�Դ��������״�����ͷ��״�����,//sortCol��Ĭ��ֵΪ-1
	table_sort_count++;
	//��ʾ��ʧ
	if(loadingDiv!=null) loadingDiv.style.display="none";
};

//�ȽϺ�������������֮�������
//����
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

//����
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

//��������ת������
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
		//���
		var oTbody=allTable[i].children.item(0);
		//��һ��
		var oTR=oTbody.children.item(0);
		//��
		for(var j=0;j<oTR.children.length;j++)
		{
		   //��ʾ������
		   if(oTR.children.item(j).onclick==null) continue;
		   oTR.children.item(j).style.cursor="hand";
		   oTR.children.item(j).title="��������������";
           oTR.children.item(j).innerHTML=oTR.children.item(j).innerHTML.trim()+"&nbsp;<img src='<%=request.getContextPath()%>/common/images/arrow_off.png'>";
		}
	}
}

//ȥǰ��ո�
String.prototype.trim = function()
{
  return this.replace(/(^\s+)|\s+$/g,"");
}
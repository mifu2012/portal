var g_Activecheck = 0;
function  Window_Onload()
{
  //ob_arr��ͨ�ò�ѯ�б��checkbox���飬ֵΪnull��ʾ���б�û�м�¼��ob_arr.lengthΪnull��ʾ���б�ֻ��һ����¼
  ob_arr = document.all.item("query_checkbox");
  if ( ob_arr == null )
    return;
  if ( ob_arr.length == null )
  {
    ob_arr.checked = true;
    //����ѡ��ʱtr��td��css
    ob_arr.parentElement.parentElement.className = "query_list_data_trSelected";
    ob_arr.parentElement.className = "query_list_data_tdSelected";
  }
  else
  {
    for (i=0;i<ob_arr.length;i++)
      //����checkbox����td��cssΪquery_list_meta_td
      ob_arr.item(i).parentElement.className = "query_list_meta_td";
      //����Ĭ���б�ĵ�һ����¼Ϊѡ��
    ob_arr.item(0).checked = true;
    ob_arr.item(0).parentElement.parentElement.className = "query_list_data_trSelected";
    ob_arr.item(0).parentElement.className = "query_list_data_tdSelected";
  }
}
//OnMouseOver�ı��ɫ
function onMouseOver(item)
{
  ob_arr = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
  if ( ob_arr == null || ob_arr.length == null || ob_arr.length<=item.index-1 )
    return;
  var crtObj=ob_arr.item[item.index];
  if(crtObj!=null)
  {
	  if(crtObj.checked==false) item.className = "query_list_data_trOver";
  }
  /*
  if(document.all.item("query_checkbox",item.index)!=null)
  {
    if (document.all.item("query_checkbox",item.index).checked  != true) item.className = "query_list_data_trOver";
  }
  */
}
//OnMouseOut�ı��ɫ
function onMouseOut(item)
{
  ob_arr = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
  if ( ob_arr == null || ob_arr.length == null || ob_arr.length<=item.index-1 )
    return;
  item.className = ( (item.index-1)%2 == 0 ) ? "query_list_data_tr1" : "query_list_data_tr2";
  item.style.backgroundColor = item.currentStyle.getAttribute("backgroundColor",2);
  ob_arr =  document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
  var crtObj=ob_arr.item[item.index];
  if(crtObj!=null)
  {
	  if(crtObj.checked==false) item.className = "query_list_data_trSelected";
  }
  /*
  if(document.all.item("query_checkbox",item.index)!=null)
	{
     if (document.all.item("query_checkbox",item.index).checked  == true) item.className = "query_list_data_trSelected";
	}
	*/
}
//OnClick�ı��ɫ����Check
function clickcheck(trIndex)
{
   	var obCheckBox = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
	var currentClickObj = null;//window.event.srcElement;
	try
	{
		var event=window.event;
		currentClickObj=event.srcElement;
	}catch(e)
	{
		var event=arguments[0];
		currentClickObj=event.target;
	}
	if(currentClickObj.tagName.toLowerCase()=="input") //��������빹����
		return;
	if(currentClickObj.tagName.toLowerCase()=="select")//��������빹����
		return;
	if ( obCheckBox == null)
		return;
	if(currentClickObj.parentElement!=null)
	{
		trIndex=currentClickObj.parentElement.index;
	}
	if(obCheckBox.length == null){
        //���ֻ��һ����¼
        obCheckBox.checked = true;
        var obTableRow = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
    	for(i=0;i<obCheckBox.length;i++){
      		parent_td = obCheckBox.item(i).parentElement;
      		parent_tr = parent_td.parentElement;
        	if (parent_tr.index==trIndex){
         		parent_tr.className = "query_list_data_trSelected";
                break;
            }
        }
	}else{
    	for(i=0;i<obCheckBox.length;i++){
        	obCheckBox.item(i).checked = false;
      		parent_td = obCheckBox.item(i).parentElement;
      		parent_tr = parent_td.parentElement;
      		parent_tr.className = ( i%2 == 0 ) ? "query_list_data_tr1" : "query_list_data_tr2";
         	if (obCheckBox.item(i).index==trIndex){
	       		parent_tr.className = "query_list_data_trSelected";
       			obCheckBox.item(i).checked = true;
        	}
        }
	}
}

function DoubleClick(){

}
//ȥǰ��ո�
String.prototype.trim = function()
{
  return this.replace(/(^\s+)|\s+$/g,"");
}

/*
  ����
*/
function orderFieldClick(fieldName){
	//����ʱ����ѡ�и�ѡ��
	var obCheckBox = document.getElementsByName("query_checkbox");
	if(obCheckBox.length==null)
	{
		obCheckBox.checked=false;
	}else
	{
		for(var i=0;i<obCheckBox.length;i++)
		{
			obCheckBox[i].checked=false;
		}
	}
	//�����ֶ�
	var oldFieldName=document.pulbicqueryForm.orderField.value;//("orderField").value;
	if(fieldName!=null)
	{
		//תΪСд
		oldFieldName=oldFieldName.toLowerCase();
		var orderFieldArray=oldFieldName.split(" ");
		//alert(orderFieldArray.length);
		if(orderFieldArray.length==1)
		{
			//Ĭ��������
			fieldName=fieldName+" asc";
		}else if(orderFieldArray.length==2&&orderFieldArray[1].trim()=="asc")
		{
			//����
			fieldName=fieldName+" desc";
		}else 
		{
			//����
			fieldName=fieldName+" asc";
		}
	}
	   //ȡ��ǰ����URI
    document.pulbicqueryForm.action=window.location.href;
    document.pulbicqueryForm.orderField.value = fieldName;
    document.pulbicqueryForm.submit();
}
//��ɶ�hiddenCheckbox��ȫѡ��ȫ��ѡ����
function listAllorNone(){
  var obj = event.srcElement;//document.all.item("allcheck");
  if(obj==null) obj=document.pulbicqueryForm.allcheck;//document.all.item("allcheck");
  if(obj.checked){
    listSelectAll();
    obj.title="ȫ��ѡ";
  }
  if(!obj.checked){
    listSelectNone();
    obj.title="ȫѡ";
  }
}

//ȫѡ
function listSelectAll(){
  var hiddenCheckboxObj = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
  if(hiddenCheckboxObj==null)
    return;
  if(hiddenCheckboxObj.length==null){
    hiddenCheckboxObj.checked = true;
    hiddenCheckboxObj.parentElement.parentElement.className = "query_list_data_trSelected";
    listSelectNum=1;
  }else{
    for(i=0;i<hiddenCheckboxObj.length;i++){
      hiddenCheckboxObj.item(i).checked = true;
      hiddenCheckboxObj.item(i).parentElement.parentElement.className = "query_list_data_trSelected";
    }
    listSelectNum=hiddenCheckboxObj.length;
  }
}

//ȫ��ѡ
function listSelectNone(){
  var hiddenCheckboxObj = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
  if(hiddenCheckboxObj==null)
    return;
  if(hiddenCheckboxObj.length==null){
    hiddenCheckboxObj.checked = false;
    hiddenCheckboxObj.parentElement.parentElement.className = "query_list_data_tr1";
  }else{
    for(i=0;i<hiddenCheckboxObj.length;i++){
      hiddenCheckboxObj.item(i).checked = false;
      hiddenCheckboxObj.item(i).parentElement.parentElement.className = (i%2==1)?"query_list_data_tr1":"query_list_data_tr2";
    }
  }
  listSelectNum=0;
}
//ȡ��ѡ��¼
function getSingleSelected(fieldName){
    var sRecordID,sFeatures,sHref,stepInco,finiFlag,dealIndex;
    var ob_arr = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
    if (ob_arr == null){
      	return null;
    }else{
      if (ob_arr.length == null){
        if (ob_arr.checked == true){
            if (fieldName == null){
          		sRecordID = ob_arr.value;
            }else{
         		sRecordID = eval("ob_arr."+fieldName);
            }
	       return  sRecordID;
        }
        else{
          return null;
        }
      }else{
        for (i=0;i<ob_arr.length;i++){
          if (ob_arr.item(i).checked == true){
			  if(sRecordID!=null&&sRecordID.length>0) ob_arr.item(i).checked=false;
            if (fieldName == null){
            	sRecordID = ob_arr.item(i).value;
            }else{
         		sRecordID = eval("ob_arr.item(i)."+fieldName);
            }
            //break;
          }
        }
      }
    }
    if(sRecordID == null || sRecordID == ""){
      return null;
    }
    return  sRecordID;
  }

//ȡ��ѡ��¼����,�ֿ�
function getMultiSelected(fieldName,splitString){
    var sRecordID="",sFeatures,sHref,stepInco,finiFlag,dealIndex;
    var ob_arr = document.getElementsByName("query_checkbox");//document.all.item("query_checkbox");
    if (ob_arr == null){
      	return null;
    }else{
      if (ob_arr.length == null){
        if (ob_arr.checked == true){
            if (fieldName == null){
           		sRecordID = ob_arr.value;
           	}else{
             	sRecordID = eval("ob_arr."+fieldName);
            }
	       return  sRecordID;
        }
        else{
          return null;
        }
      }else{
        for (i=0;i<ob_arr.length;i++){
          if (ob_arr.item(i).checked == true){
            if (sRecordID != ""){
            	if (splitString == null){
             		sRecordID+=",";
          		}else{
             		sRecordID+=splitString;
          		}
            }
            if (fieldName == null){
            	sRecordID += ob_arr.item(i).value;
            }else{
             	sRecordID += eval("ob_arr.item(i)."+fieldName);
            }
          }
        }
      }
    }
    if(sRecordID == null || sRecordID == ""){
      return null;
    }
    return  sRecordID;
  }
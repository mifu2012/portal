//ȡ��ѡ��¼
function getSingleSelected(fieldName){
    var sRecordID,sFeatures,sHref,stepInco,finiFlag,dealIndex;
    var ob_arr =document.getElementsByName("query_checkbox"); 
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
            if (fieldName == null){
            	sRecordID = ob_arr.item(i).value;
            }else{
         		sRecordID = eval("ob_arr.item(i)."+fieldName);
            }
            break;
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
    var ob_arr = document.getElementsByName("query_checkbox");
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



//ȡȫ����¼����,�ֿ�
function getAllRecord(fieldName,splitString){
    var sRecordID="",sFeatures,sHref,stepInco,finiFlag,dealIndex;
    var ob_arr = document.getElementsByName("query_checkbox");//'pulbicqueryForm' 
    if (ob_arr == null){
      	return null;
    }else{
      if (ob_arr.length == null){
            if (fieldName == null){
           		sRecordID = ob_arr.value;
           	}else{
             	sRecordID = eval("ob_arr."+fieldName);
            }
	       return  sRecordID;
      }else{
        for (i=0;i<ob_arr.length;i++){
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
    if(sRecordID == null || sRecordID == ""){
      return null;
    }
    return  sRecordID;
  }

/*
  ��ת��ĳҳ
*/
function gotoPage(pageNo){
	//��ҳʱ����ѡ�и�ѡ��
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
	//���ҳ��
	var maxPage=eval("pulbicqueryForm.publicquery_gotopage.max_value");//document.pulbicqueryForm.publicquery_gotopage.max_value;
	//��Сҳ��
    var minPage=eval("pulbicqueryForm.publicquery_gotopage.min_value");//document.getElementsByName('publicquery_gotopage').min_value;
	if (minPage==0)
	{
		alert("��ǰ��¼��Ϊ��");
		return;
	}
	if (pageNo==null||pageNo<=1)
	{
		pageNo=parseInt(minPage);
	}
	if (pageNo>parseInt(maxPage))
	{
		alert('�������ҳ������ת��βҳ');
		pageNo=maxPage;
	}
   //ȡ��ǰ����URI
   document.pulbicqueryForm.action=window.location.href;
   //document.all.item("pageNo").value=pageNo;
   document.pulbicqueryForm.pageNo.value=pageNo;
   document.pulbicqueryForm.submit();
}

/*
  ��ת��ĳҳ
*/
function gotoInputPage(){
	var pageNo=eval("pulbicqueryForm.publicquery_gotopage.value");//document.all.item('publicquery_gotopage').value;
	gotoPage(pageNo);
}

/*
  ��ת��ĳҳ
*/
function gotoInputPageKeyPress(){
	if (event.keyCode!=13){
           return;
    }
	var pageNo=eval("pulbicqueryForm.publicquery_gotopage.value");//document.all.item('publicquery_gotopage').value;
	gotoPage(pageNo);
}

//��������п�
function SyDG_moveOnTd(td)
{
	if(event.offsetX>td.offsetWidth-10)
		td.style.cursor='w-resize';
	else
		td.style.cursor='default';
	if(td.mouseDown!=null && td.mouseDown==true)
	{
		if(td.oldWidth+(event.x-td.oldX)>0)
			td.width=td.oldWidth+(event.x-td.oldX);
		td.style.width=td.width;
		td.style.cursor='w-resize';
		
		table=td;
		while(table.tagName!='TABLE') table=table.parentElement;
		table.width=td.tableWidth+(td.offsetWidth-td.oldWidth);
		table.style.width=table.width;
	}
}
//http://scm.91sr.com/index.jsp ������
function SyDG_downOnTd(td)
{
	if(event.offsetX>td.offsetWidth-10)
	{
		td.mouseDown=true;
		td.oldX=event.x;
		td.oldWidth=td.offsetWidth;
		table=td;while(table.tagName!='TABLE')table=table.parentElement;
		td.tableWidth=table.offsetWidth;
	}
}
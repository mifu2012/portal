<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="gbk"%>
<%@ taglib prefix="dt" uri="displayTag"%>
<%
	String rootPath = request.getContextPath();
	String sCommonPath = request.getContextPath() + "/common/";
	String sImagePath = request.getContextPath() + "/common/images/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
��������<script type="text/javascript" src="<%=rootPath%>/common/Common.jsp"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=sCommonPath%>css/Public1.css">
		<link rel="stylesheet" type="text/css"
			href="<%=sCommonPath%>QueryList/Query.css">
		<script language="javascript"
			src="<%=sCommonPath%>QueryList/publicquery.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=sCommonPath%>css/publicCSS.jsp">
		<script language="javascript"
			src="<%=sCommonPath%>QueryList/QueryList.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=sCommonPath%>WebToolBar/WebToolBar.css">
		<script language="javascript"
			src="<%=sCommonPath%>WebToolBar/WebToolBar.js"></script>
		<SCRIPT LANGUAGE="JavaScript">
<!--
  window.onload = initWindow;
  function initWindow(){
    initMenu();
  }
��//�����˵�
  function initMenu(){
    //����ButtonBar
	  SetToolBarHandle(true);
      SetToolBarHeight(22);
	  AddToolBarItemEx("button_delete","<%=sCommonPath%>/WebToolBar/img/new_field.gif","","","����","65","0","addIt()");
	  AddToolBarItemEx("button_delete","<%=sCommonPath%>/WebToolBar/img/edit_record.gif","","","�޸�","65","0","editOne()");
	  AddToolBarItemEx("button_delete","<%=sCommonPath%>/WebToolBar/img/del1.gif","","","ɾ��","65","0","delMulti()");
	  AddToolBarItemEx("button_delete","<%=sCommonPath%>/WebToolBar/img/fresh_record.gif","","","ˢ��","65","0","refreshFile()");
	  AddToolBarItemEx("button_delete","<%=sCommonPath%>/WebToolBar/img/help.png","","","����","65","0","alert('����')");
      window.document.getElementById("ToolBar").innerHTML=GenToolBar("","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
   }
   function addIt()
   {
	   alert("����");
   }
   function editOne()
   {
	   alert("�༭:"+getSingleSelected());
   }
   function delMulti()
   {
	   alert("ɾ��:"+getMultiSelected());
   }
   //ˢ������
   function refreshFile()
   {
     document.location.reload();
   }
//-->
</SCRIPT>
	</head>
	<body class="PanelFlat" style="overflow: auto;" leftMargin="0"
		topMargin="0" style="background-color:#E0E7F8">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="background-color: #91A9E5;">
					<div id="ToolBar" name="ToolBar" class='Out_border_class'></div>
				</td>
			</tr>
			<tr>
				<td>
					<dt:table tableHeight="353px">
						<dt:row onDblClick="editOne()">
						   <!---#rowStatus.index��ʾ��ǰ���-->
						   <!--#rootPath��ʾ��·��-->
						   <!--&quot;Ϊ���ţ�ת���-->
							<dt:column property="kpiCode,#rowStatus.index"
								pattern="<input type=&quot;checkbox&quot; id=&quot;query_checkbox_{1}&quot; name=&quot;query_checkbox&quot; value=&quot;{0}&quot; index=&quot;{1}&quot;>"
								title="<input type='checkbox' name='allcheck' onclick='javasrcipt:listAllorNone()'>"
								align="center" width="15px" />
							<dt:column property="#rowStatus.index+1" title="��" align="center"
								width="25px" />
							<dt:column property="kpiCode" title="ָ�����" align="center"
								canOrder="false" width="15%" />
							<!--���Ӿ���������<a>�е���������ת���[&quot;]��ʡ��-->
							<dt:column property="kpiName,#rootPath,kpiCode" title="ָ������" align="left" pattern="<a href=&quot;http://www.baidu.com?kpiCode={2}&rootPath={1}&quot;>{0}</a>"/>
							<dt:column property="dispName" title="��ʾ����" align="left"
								width="18%" canOrder="false" />
							<!--format��ֻ֧�����ںͻ��ҵĸ�ʽ��#.00-->
							<dt:column property="gmtCreate" title="��������" align="center"
								width="10%" canOrder="false" format="yyyy-MM-dd HH:mm"/>
							<dt:column property="kpiTypeDesc" title="ָ������" align="center"
									width="10%" canOrder="false"/>
						</dt:row>
					</dt:table>
				</td>
			</tr>
		</table>
	</body>
</html>
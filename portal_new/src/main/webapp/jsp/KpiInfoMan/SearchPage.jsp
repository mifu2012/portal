<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="gbk"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%
	String rootPath = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>���ݷ�ҳDEMO</title>
		<script type="text/javascript" src="<%=rootPath%>/common/Common.jsp"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=rootPath%>/common/css/Public1.css">
		<link rel="stylesheet" type="text/css"
			href="<%=rootPath%>/common/css/Popup.jsp">
		<script language="javascript">
    //��ѯ
	function Search() {
		document.forms[0].action="<%=rootPath%>/kpiInfoMan/listKpiInfo";
		document.manager.submit();
        Clear();
	}
	//���
	function Clear()
	{
		document.all.item("fileInfo.fileName").value="";
		document.all.item("fileInfo.createDate").value="";
	}
</script>
	</head>
	<body scroll="no">
		<form action="<%=rootPath%>/kpiInfoMan/listKpiInfo"
			target="ContentiFrm" method="post" name="manager" id="manager"
			theme="simple">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="background-color: #E0E7F8" class="Out_border">
				<tr>
					<td class="searchUtil">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="padding-top: 0px;">
									<fieldset>
										<legend>
											��ѯ����
										</legend>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="right">
													ָ�����:
												</td>
												<td style="padding-bottom: 8px;">
												    <input type="text" name="kpiCode" style="background-color:#E0E7F8;width:120px;font-size:13px;" class="selected_block_input">
												</td>
												<td align="right">
													ָ������:
												</td>
												<td style="padding-bottom: 8px;">
												    <input type="text" name="kpiName" style="background-color:#E0E7F8;width:120px;font-size:13px;" class="selected_block_input">
												</td>
												<td align="right">
													��ʾ����:
												</td>
												<td style="padding-bottom: 8px;">
												    <input type="text" name="dispName" style="background-color:#E0E7F8;width:120px;font-size:13px;" class="selected_block_input">
												</td>
												<td align="right">
													ָ������:
												</td>
												<td style="padding-bottom: 8px;">
													<select name="kpiType" style="width: 100px;font-size:12px;margin:-2px;background-color:#E0E7F8;">
													   <option value=""></option>
													   <option value="<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>">��ָ��</option>
													   <option value="<%=DwpasCKpiInfo.KPI_TYPE_OF_WEEK%>">��ָ��</option>
													   <option value="<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>">��ָ��</option>
													</select>
												</td>
												<td style="padding-bottom: 8px;">
													<input type='button' name='auditButton' value='��  ѯ'
														class='button1' onclick="Search();">
													&nbsp;
													<input type='button' name='auditButton' value='��  ��'
														class='button1' onclick="Clear();">
												</td>
											</tr>
										</table>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td valign="top" width="100%">
									<iFrame id="ContentiFrm" name="ContentiFrm"
										style="background-color: #E0E7F8"
										src="<%=rootPath%>/kpiInfoMan/listKpiInfo"
										scrolling="auto" frameborder="0" border="0" framespacing="0"
										noresize width="100%" height="450px" leftMargin="0"
										topMargin="0"></iFrame>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

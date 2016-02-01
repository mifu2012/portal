<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>报表表头设计</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!-- 用于IE8之前的版本，处理JSON数据 -->
<script type="text/javascript" src="<%=basePath%>/js/json2.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>/js/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/zTree/js/jquery.ztree.exedit-3.2.js"></script>
<!--uuid-->
<script type="text/javascript" src="<%=basePath%>/js/zTree/js/uuid.js"></script>
<!--jquery_ui_costom-->
<link rel="stylesheet" href="<%=basePath%>/js/jui_custom/jquery.ui.tabs.css">
<link rel="stylesheet" href="<%=basePath%>/js/jui_custom/jquery.ui.core.css">
<link rel="stylesheet" href="<%=basePath%>/js/jui_custom/jquery.ui.theme.css">

<script src="<%=basePath%>/js/jui_custom/jquery.ui.widget.js"></script>
<script src="<%=basePath%>/js/jui_custom/jquery.ui.tabs.js"></script>
<!--jquery_ui_costom-->
<style type='text/css'>
	td{
		font-size:13px;
	}
	input,select,textarea{
		font-size:100%;
	}
	/* 按钮 */
	a.myBtn,a.myBtn em{background-image:url(../images/lhgdg_bg.png);background-repeat:no-repeat;vertical-align: middle;}
	a.myBtn,a.myBtn em{display:inline-block;height:21px;cursor: pointer;}
	a.myBtn{text-decoration:none;background-position:-32px -41px;padding-left:15px;color:#000;cursor:default;margin-right:5px;}
	a.myBtn:hover{background-position:-32px -83px;}
	a.myBtn em{font-style:normal;background-position:right -62px;padding-right:15px;line-height:21px;line-height:24px\0;font-size:12px;}
	a.myBtn:hover em{background-position:right -104px;}
</style>
<script type="text/javascript">
<!--
	$(function() {
		$( "#tabs" ).tabs();
	});
//-->
</script>
<SCRIPT type="text/javascript">
<!--
	var setting = {
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false,//不能多选
			nameIsHTML : true
		},
		edit : {
			enable : true,
			//删除
			showRemoveBtn : setRemoveBtn,
			removeTitle : "删除节点",
			//编辑名称按钮不显示
			showRenameBtn : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			//删除
			beforeRemove : beforeRemove,
			//拖拽
			beforeDrag : zTreeBeforeDrag,
			beforeDrop : zTreeBeforeDrop,
			onDrop : zTreeonDrop,
			//选中
			beforeClick: zTreeBeforeClick,
			onClick : zTreeOnClick
		}
	};

	var zNodes = [ {
		id : 1,
		pId : 0,
		name : "<b>报表表头设置</b>",
		open : true,
		icon : "<%=basePath%>/js/zTree/img/8.png"
	}
     <c:forEach items="${reportDefine.reportCellList}" var="reportCell" varStatus="vs">
		  , {
			id : "${reportCell.id}",
			pId : "${reportCell.pId}",
			name : "${reportCell.content}",
			open : true,
			/*下面是自定义的*/
			isDynamicColumn : 	<c:if test="${reportCell.dynamicColumnField == null}">0</c:if><c:if test="${reportCell.dynamicColumnField != null}">1</c:if>,
			headerField : "${reportCell.dynamicColumnField}",
		    dataType : "${reportCell.dataType}",
			dataField : "${reportCell.dataField}",
			align : "${reportCell.align}",
			rowSum : "${reportCell.rowSum}",
			groupSum : "${reportCell.groupSum}",
			pageSum : "${reportCell.pageSum}",
			formatter : "${reportCell.formatter}",
			datefmt : "${reportCell.datefmt}",
			groupSummaryType : "${reportCell.groupSummaryType}",
			pageSummaryType : "${reportCell.pageSummaryType}",
			width : "${reportCell.width}",
			scale : "${reportCell.scale}",
			relRptId : "${reportCell.relRptId}",
			link : "${reportCell.link}",
			relRptName : "${reportCell.relRptName}",
			commas : "${reportCell.commas}",
			currency : "${reportCell.currency}",
			warningValue : "${reportCell.warningValue}"
		}
	 </c:forEach> ];

	//新增节点
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_"
				+ treeNode.id
				+ "' title='新增节点' style='display:;' onfocus='this.blur();'></span>";
		sObj.append(addStr);
		var btn = $("#addBtn_" + treeNode.id);
		if (btn)
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(treeNode, {
					id : new UUID().id,
					pId : treeNode.id,
					name : "new node" + (newCount++),
					width : "100"
				});
				//添加完节点后，选中此节点
				zTree.selectNode(treeNode.children[treeNode.children.length-1]);
				zTreeOnClick(getEvent(), "treeDemo", treeNode.children[treeNode.children.length-1]);
				//初始化绑定字段和字段类型的值
				document.getElementById("dataField").value = '';
				document.getElementById("dynamicColumn").value = '0';
				document.getElementById("headerField").value = '';
				var formatterOpt = document.getElementById("formatter");
				formatterOpt.length = 0;//清空数据
				formatterOpt.options.add(new Option("", ""));
				formatterOpt.style.height = "20px";
				formatterOpt.value = "";
				return false;
			});
		//如果超过了三级，则不能新增
		if (treeNode.level >= 3) {
			document.getElementById("addBtn_" + treeNode.id).style.display = "none";
		}
	};
	
	//删除节点
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.id).unbind().remove();
	};

	//有子结点时不能删除,跟节点不能删除
	function setRemoveBtn(treeId, treeNode) {
		return (treeNode.id != 1 && !treeNode.isParent);
	};

	//删除节点时进行确认删除提示
	function beforeRemove(treeId, treeNode) {
		if (treeNode.pId == null || treeNode.pId <= 0)
			return false;
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		if (zTree.transformToArray(treeNode).length > 1) {
			alert('不能删除父节点');
			return false;
		}
		return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	};
	
	//根节点不能拖拽
	function zTreeBeforeDrag(treeId, treeNodes) {
		return (treeNodes[0].id != 1);
	};

	//拖拽,不能拖到根目录，不能出现level为4的目录
	function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
		nodeDepth = 0;
		getNodeDepth(treeNodes[0]);
		return !(targetNode == null 									//目标节点不能为空
				|| (moveType != "inner" && targetNode.level == 0) 		//不能与根节点并列
				|| (moveType == "inner" && (targetNode.level+nodeDepth-treeNodes[0].level+1) >3)//不能超过3级
				|| (moveType != "inner" && (targetNode.level+nodeDepth-treeNodes[0].level) >3));//不能超过3级
	};
	
	//拖拽后点击该节点
	function zTreeonDrop(event, treeId, treeNodes, targetNode, moveType) {
		zTreeOnClick(getEvent(), "treeDemo", treeNodes[0]);
	};

	//顶级节点不能点击
	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		return (treeNode.id != 1);
	};

	var nodeFieldType;//节点字段类型
	//点击节点
	function zTreeOnClick(event, treeId, treeNode) {
		$("#headerConfig").css("display", "");
		// 动态列
		nodeDepth = 0;
		getNodeDepth(treeNode);// 获取节点最大深度
		if (treeNode.level > 1 || nodeDepth >2) {
			// 若节点在第二层以及以后层，或者该节点拥有第三层节点，则不能配置为动态列
			$("#dynamicColumn").val(0);
			$("#dynamicColumn").attr("disabled","disabled");
			$("#headerField").val("");
			isDynamicColumn(0);
			if (treeNode.headerField != "" && treeNode.headerField != null) {
				setNodeValue("headerField", "");
				setNodeValue("name", "请输入");
			}
		} else {
			$("#dynamicColumn").removeAttr("disabled","disabled");
			if (treeNode.isDynamicColumn == null || treeNode.isDynamicColumn == "") {
				treeNode.isDynamicColumn = 0;
			}
			$("#dynamicColumn").val(treeNode.isDynamicColumn);
			isDynamicColumn(treeNode.isDynamicColumn);
			if (treeNode.isDynamicColumn == 1) {
				$("#headerField").val(treeNode.headerField);
			}
		}
		//四个移动按钮是否可用的判断
		if (treeNode.isFirstNode) {
			document.getElementById("movePreBtn").disabled = true;
			document.getElementById("moveTopBtn").disabled = true;
		} else {
			document.getElementById("movePreBtn").disabled = false;
			document.getElementById("moveTopBtn").disabled = false;
		}
		if (treeNode.isLastNode) {
			document.getElementById("moveNextBtn").disabled = true;
			document.getElementById("moveBottomBtn").disabled = true;
		} else {
			document.getElementById("moveNextBtn").disabled = false;
			document.getElementById("moveBottomBtn").disabled = false;
		}
		//赋值
		document.getElementById("headerName").value=treeNode.name;
		//document.getElementById("headerName").select();
		//绑定字段，如果是父节点则不能选择绑定字段
		if(treeNode.isParent){
			document.getElementById("dataField").value = '';
			document.getElementById("dataField").disabled = true;
		}else{
			document.getElementById("dataField").disabled = false;
			if (treeNode.dataField == null) {
				document.getElementById("dataField").value = '';
			} else {
				document.getElementById("dataField").value = treeNode.dataField;
			}
		}
		//对齐方式,默认是居中
		document.getElementById("textAlign").value = treeNode.align==null||treeNode.align.length==0?"center":treeNode.align;
		//formatter　字段类型
		document.getElementById("formatter").value = treeNode.formatter;
		//宽度，如果是父节点则宽度为所有子节点宽度之和
		var width = 0;
		if (treeNode.children && treeNode.children.length > 0) {
			for ( var i = 0; i < treeNode.children.length; i++) {
				if (treeNode.children[i].width != null) {
					width += parseInt(treeNode.children[i].width); 
				}
			}
			treeNode.width = width;
			document.getElementById("textWidth").value = width;
			document.getElementById("textWidth").disabled = true;
		} else {
			document.getElementById("textWidth").disabled = false;
			document.getElementById("textWidth").value = treeNode.width==null||treeNode.width.length==0?100:treeNode.width;
		}
		//如果是字符型或日期类型，只能进行统计个数
		var formatter = treeNode.formatter;
		var formatterOpt = document.getElementById("formatter");//字段类型选项重新设置
		formatterOpt.length = 0;//清空数据
		var groupSummaryTypeOpt = document.getElementById("groupSummaryType");//分组汇总选项重新设置
		groupSummaryTypeOpt.length = 0;//清空数据
		groupSummaryTypeOpt.options.add(new Option("", ""));
		groupSummaryTypeOpt.options.add(new Option("汇总", "count"));
		var pageSummaryTypeOpt = document.getElementById("pageSummaryType");//单页汇总选项重新设置
		pageSummaryTypeOpt.length = 0;//清空数据
		pageSummaryTypeOpt.options.add(new Option("", ""));
		pageSummaryTypeOpt.options.add(new Option("汇总", "count"));
		$("#formatBtn").css("display", "none");
		// 如果是父节点，字段类型、分组汇总、单页汇总、报表链接不能选择
		if (treeNode.isParent) {
			formatterOpt.options.add(new Option("", ""));
			formatterOpt.style.height = "20px";
			formatterOpt.value = "";
			groupSummaryTypeOpt.value = "";
			pageSummaryTypeOpt.value = "";
			document.getElementById("formatter").disabled = true;
			document.getElementById("groupSummaryType").disabled = true;
			document.getElementById("pageSummaryType").disabled = true;
			document.getElementById("link").disabled = true; //报表链接disabled
			$("#linkBtn").css("display", "none"); //报表链接按钮隐藏
			$("#link").val("");	//报表链接内容为空
		} else {
			document.getElementById("link").setAttribute("relRptId", treeNode.relRptId);
			document.getElementById("link").setAttribute("setting", treeNode.link);
			document.getElementById("link").disabled = false; //报表链接取消disabled
			$("#linkBtn").css("display", ""); //报表链接按钮显示
			$("#link").val(treeNode.relRptName); //对报表链接内容赋值
			document.getElementById("formatter").disabled = false;
			document.getElementById("pageSummaryType").disabled = false;
			//获取当前节点的字段类型
			$("select[id='dataField'] option").each(function(){
				if ($(this).val() == treeNode.dataField) {
					nodeFieldType = parseInt($(this).attr("dataFieldType"));
				}
			});
			if (nodeFieldType >= 91) {
				formatterOpt.options.add(new Option("日期型", "date"));
			   	formatterOpt.value = "date";
			} else if (nodeFieldType > 1 && nodeFieldType <= 10) {
				$("#formatBtn").css("display", "");
				//分组统计
				groupSummaryTypeOpt.options.add(new Option("求和", "sum"));
				groupSummaryTypeOpt.options.add(new Option("平均值", "avg"));
				groupSummaryTypeOpt.options.add(new Option("最小值", "min"));
				groupSummaryTypeOpt.options.add(new Option("最大值", "max"));
				//单页汇总
				pageSummaryTypeOpt.options.add(new Option("求和", "sum"));
				pageSummaryTypeOpt.options.add(new Option("平均值", "avg"));
				pageSummaryTypeOpt.options.add(new Option("最小值", "min"));
				pageSummaryTypeOpt.options.add(new Option("最大值", "max"));
				if (nodeFieldType == 4 || nodeFieldType == 5) {
					//整数型
					formatterOpt.options.add(new Option("整数型","integer"));
				} else {
					//小数型
					formatterOpt.options.add(new Option("小数型","number"));
				}
			} else if(nodeFieldType == null || nodeFieldType == 0) {
				//未绑定字段的节点
				formatterOpt.options.add(new Option("", ""));
				formatterOpt.value = "";
			} else {
				//字符型
				formatterOpt.options.add(new Option("字符型","string"));
			   	formatterOpt.value="string";
			}
			//分组统计
			if(document.getElementById("groupingOpt").value == 0){
				//未设置分组统计
				document.getElementById("groupSummaryType").value = "";
				$("#groupSummaryType").attr("disabled", "true");
			}else{
			   document.getElementById("groupSummaryType").value = treeNode.groupSummaryType;
			   document.getElementById("groupSummaryType").disabled = false;
			}
			//单页统计
			if(treeNode.pageSummaryType != null && treeNode.pageSummaryType !=''){
				document.getElementById("pageSummaryType").value = treeNode.pageSummaryType;
			}else{
				document.getElementById("pageSummaryType").value = "";
			}
		}
		nodeFieldType = 0;// 清空节点字段类型
	};

	// 获取节点最大深度
	var nodeDepth = 0;
	function getNodeDepth(zTreeNode) {
		if (zTreeNode.level > nodeDepth)
			nodeDepth = zTreeNode.level;
		if (zTreeNode.children && zTreeNode.children.length > 0) {
			for ( var i = 0; i < zTreeNode.children.length; i++) {
				getNodeDepth(zTreeNode.children[i]);
			}
		}
	}
	
    // 修改节点值
	function setNodeValue(propertyName,propertyValue)
	{
	   var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	   if(zTree.getSelectedNodes()==null||zTree.getSelectedNodes().length==0) return;
       var crtNode = zTree.getSelectedNodes()[0];
       eval("crtNode."+propertyName+"='"+propertyValue+"'");
	   zTree.updateNode(crtNode);
	};

	var zTree;
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
	});
//-->
</SCRIPT>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 5px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
</HEAD>
<script type="text/javascript">
<!--
    //设置分组
	function setGrouping(flag)
	{
		if(flag==0){
			$("#lockColumnCountOpt").removeAttr("disabled");
			$("#mergeColumnCountOpt").removeAttr("disabled");
			$("#showRowNumOpt").removeAttr("disabled");
			$("input:radio").attr("checked",false);//清空所选择的字段
			$("input:radio").attr("disabled","true");
			return;
		}else{
			$("#lockColumnCountOpt").attr({value:0, disabled:true});	//锁定列禁用
			$("#mergeColumnCountOpt").attr({value:0, disabled:true});	//合并列禁用
			$("#showRowNumOpt").attr({value:0, disabled:true});			//显示行号禁用
        	$("input:radio").removeAttr("disabled");
			return;
		}
	}
	
	//设置字段类型
	function setFormatter(crtObj)
	{
		var dataFieldType=crtObj.options[crtObj.selectedIndex].getAttribute("dataFieldType");
	   	var formatterOpt=document.getElementById("formatter");//字段类型
	   	formatterOpt.length=0;//清空数据
	   	var groupSummaryTypeOpt = document.getElementById("groupSummaryType");//分组汇总类型
	   	groupSummaryTypeOpt.length = 0;//清空数据
	   	groupSummaryTypeOpt.options.add(new Option("", ""));
	   	groupSummaryTypeOpt.options.add(new Option("汇总", "count"));
	   	var pageSummaryTypeOpt = document.getElementById("pageSummaryType");//单页汇总类型
	   	pageSummaryTypeOpt.length = 0;//清空数据
	   	pageSummaryTypeOpt.options.add(new Option("", ""));
	   	pageSummaryTypeOpt.options.add(new Option("汇总", "count"));
	   	$("#formatBtn").css("display", "none");
		//分组统计按钮
	   	if(document.getElementById("groupingOpt").value!=0){
			   document.getElementById("groupSummaryType").disabled=false;
		}
	   	if(dataFieldType!=null&&dataFieldType.length>0) {
		   //根据类型判断
           var fieldType=parseInt(dataFieldType);
		   if(fieldType>=91)
			{
			   	//日期型
               	formatterOpt.options.add(new Option("日期型","date"));
             	//默认是日期
			   	formatterOpt.value="date";
			} else if (fieldType > 1 && fieldType <= 10)
			{
				$("#formatBtn").css("display", "");
				if (fieldType == 4 || fieldType == 5) {
					//整数型
					formatterOpt.options.add(new Option("整数型","integer"));
					formatterOpt.value="integer";//默认为整数型
				} else {
					//小数型
					formatterOpt.options.add(new Option("小数型","number"));
			   		formatterOpt.value="number";//默认是小数型
				}
			   	//分组统计
				groupSummaryTypeOpt.options.add(new Option("求和", "sum"));
				groupSummaryTypeOpt.options.add(new Option("平均值", "avg"));
				groupSummaryTypeOpt.options.add(new Option("最小值", "min"));
				groupSummaryTypeOpt.options.add(new Option("最大值", "max"));
				//单页汇总
				pageSummaryTypeOpt.options.add(new Option("求和", "sum"));
				pageSummaryTypeOpt.options.add(new Option("平均值", "avg"));
				pageSummaryTypeOpt.options.add(new Option("最小值", "min"));
				pageSummaryTypeOpt.options.add(new Option("最大值", "max"));
			} else {
				//字符型
				formatterOpt.options.add(new Option("字符型", "string"));
				formatterOpt.value="string";
			}
		} else {
			//未选择绑定字段
			formatterOpt.options.add(new Option("", ""));
			formatterOpt.value = "";
		}
	   	document.getElementById("groupSummaryType").value = "";
		document.getElementById("pageSummaryType").value = "";
	   	//修改节点值
	  	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	  	if(zTree.getSelectedNodes()==null||zTree.getSelectedNodes().length==0) return;
       	var crtNode = zTree.getSelectedNodes()[0];
       	eval("crtNode.formatter='"+formatterOpt.value+"'");
	   	zTree.updateNode(crtNode);
	}
	
	//已选的排序字段勾上
	window.onload = function(){
		<c:if test="${reportDefine.groupFieldList != null}">
			$("input[name='chk_groupField']").removeAttr("disabled");
	   		<c:forEach items="${reportDefine.groupFieldList}" var="groupField">
		   		var chk=document.getElementById("chk_groupField_${groupField.fieldName}");
           		if(chk != null) chk.checked = true;//默认选中
	   		</c:forEach>
		</c:if>
 		<c:if test="${reportDefine.groupFieldList == null}">
    		document.getElementById("lockColumnCountOpt").disabled = false;
    		document.getElementById("mergeColumnCountOpt").disabled = false;
    		document.getElementById("showRowNumOpt").disabled = false;
 		</c:if>
	};
	
	/**
	        是否为动态列
	  value :　1表示为动态列，0表示不是动态列
	  option : 'change'表示为改变动态列属性，否则为初始化时运行
	*/
	function isDynamicColumn(value, option) {
		if (value == 1) {
			$("#headerColumnTr").css("display", "");
			$("#headerNameTr").css("display", "none");
		} else {
			if (option == 'change') {
				$("#headerField").val("");
				setNodeValue("headerField", "");
				$("#headerName").val($("#dataField").val());
				setNodeValue("name", $("#dataField").val());
			}
			$("#headerColumnTr").css("display", "none");
			$("#headerNameTr").css("display", "");
		}
	}
	
	/**
	       保存URL
	  url : 链接地址
	  relRptId : 关联报表ID
	  relRptName : 关联报表名称
	*/
	function saveLinkSetting(url, relRptId, relRptName) {
		setNodeValue("link", url);
		setNodeValue("relRptId", relRptId);
		setNodeValue("relRptName", relRptName);
		document.getElementById("link").setAttribute("relRptId", relRptId);
		document.getElementById("link").setAttribute("setting", url);
		$("#link").val(relRptName);
	}
	
	// 数据格式设置
	function formatConfig() {
		var format = $("#formatter").val();
		var node = zTree.getSelectedNodes()[0];
		var scale = node.scale == null ? -1 : node.scale;
		var commas = node.commas ==  null ? 0 : node.commas;
		var currency = node.currency ==  null ? 0 : node.currency;
		var url = "<%=basePath%>/jsp/newReport/formatConfig.jsp";
		var warningValue = node.warningValue == null ? "" : node.warningValue;
		var dg = new $.dialog({
			title : '数据格式设置',
			id : 'formatConfig',
			width : 300,
			height : 190,
			top: 80, 
			iconTitle : false,
			cover : true,
			maxBtn : true,
			xButton : true,
			resize : true,
			page : url,
			args : {
					format : format,
					scale : scale,
					commas : commas,
					currency : currency,
					warningValue : warningValue
				}
		});
		dg.ShowDialog();
	}
	
	/**
		保存数据格式设置
		commas 千分符标识（逗号）
		currency 货币符标识
		scale 小数位数
		warningValue 预警值
	*/
	function saveFormatConfig(commas, currency, scale, warningValue) {
		setNodeValue("warningValue", warningValue);
		setNodeValue("commas", commas);
		setNodeValue("currency", currency);
		setNodeValue("scale", scale);
	}
//-->
</script>
<BODY style='margin-left: 5px;' scroll=no>
<div style='margin:0,0,0,0;padding-left:5px;padding-right:5px;padding-top:0px;padding-bottom:0px;'>
	<div id="tabs">
		<ul>
			<li><a id="tab1" href="#tabs-1" style='font-size:13px;'>报表设置</a></li>
			<li><a id="tab2" href="#tabs-2" style='font-size:13px;'>表头设置</a></li>
		</ul>
		<!--报表设置-->
		<div id="tabs-1" style='padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom: 5px;'>
		   <!--tab内容-->
			<table border='0' width='100%'>
				<tr>
					<td valign='top' width='45%'>
						<fieldset style='border: 1px solid #000000; *margin-top: -15px;'>
						　　<legend>报表设置</legend>
							<table width='100%' border='0' height='270px' style='margin-left: 0px; margin-top: -18px; margin-top: 0px\0; *margin-top: 0px;'>
							   <tr>
							   		<td align='right' width="33%">报表名称：</td>
								    <td align='left'>
								    <input type='hidden' name='reportId' value='${reportDefine.reportId}' id='reportId'/>
									<input type='text' id='reportName' name='reportName' style='width:124px;' value='${reportDefine.reportName}'>
								  </td>
							  </tr>
							  <tr>
								  <td align='right'>是否分组：</td>
								  <td><select style='width:130px;' id='groupingOpt' name='groupingOpt' onchange='setGrouping(this.value);'>
										 <option value='0' <c:if test="${reportDefine.grouping==0}">selected</c:if>>否</option>
										 <option value='1' <c:if test="${reportDefine.grouping==1}">selected</c:if>>是</option>
									  </select>
								  </td>
							   </tr>
							   <tr>
								  <td align='right'>每页大小：</td>
								  <td align='left'><select name='pageSizeOpt' id='pageSizeOpt' style='width: 130px;vertical-align:middle;'>
								         <option value='0' <c:if test="${reportDefine.pageSize==0}">selected</c:if>>不分页</option>
										 <option value='10' <c:if test="${reportDefine.pageSize==10}">selected</c:if>>10</option>
										 <option value='20' <c:if test="${reportDefine.pageSize==20}">selected</c:if>>20</option>
										 <option value='30' <c:if test="${reportDefine.pageSize==30}">selected</c:if>>30</option>
										 <option value='50' <c:if test="${reportDefine.pageSize == null || reportDefine.pageSize==50}">selected</c:if>>50</option>
										 <option value='100' <c:if test="${reportDefine.pageSize==100}">selected</c:if>>100</option>
										 <option value='200' <c:if test="${reportDefine.pageSize==200}">selected</c:if>>200</option>
										 <option value='300' <c:if test="${reportDefine.pageSize==300}">selected</c:if>>300</option>
										 <option value='500' <c:if test="${reportDefine.pageSize==500}">selected</c:if>>500</option>
									  </select>
								  </td>
							  </tr>
							   <tr>
								  <td align='right'>显示行号：</td>
								  <td><select style='width:130px;' name='showRowNumOpt' disabled id='showRowNumOpt'>
										 <option value='0' <c:if test="${reportDefine.showRowNum==0}">selected</c:if>>否</option>
										 <option value='1' <c:if test="${reportDefine.showRowNum==1}">selected</c:if>>是</option>
									  </select>
								  </td>
							  </tr>
							  <tr>
								  <td align='right'>锁定列数：</td>
								  <td><select style='width:130px;' id='lockColumnCountOpt' disabled name='lockColumnCount'>
								         <option value='0' <c:if test="${reportDefine.lockColumnCount==0}">selected</c:if>>不锁定</option>
										 <option value='1' <c:if test="${reportDefine.lockColumnCount==1}">selected</c:if>>前一列</option>
										 <option value='2' <c:if test="${reportDefine.lockColumnCount==2}">selected</c:if>>前两列</option>
										 <option value='3' <c:if test="${reportDefine.lockColumnCount==3}">selected</c:if>>前三列</option>
										 <option value='4' <c:if test="${reportDefine.lockColumnCount==4}">selected</c:if>>前四列</option>
									  </select>
								  </td>
							  </tr>
							  <tr>
								  <td align='right'>合并列数：</td>
								  <td><select style='width:130px;' id='mergeColumnCountOpt' disabled name='mergeColumnCount'>
								         <option value='0' <c:if test="${reportDefine.mergeColumnCount==0}">selected</c:if>>不合并</option>
										 <option value='1' <c:if test="${reportDefine.mergeColumnCount==1}">selected</c:if>>前一列</option>
										 <option value='2' <c:if test="${reportDefine.mergeColumnCount==2}">selected</c:if>>前两列</option>
										 <option value='3' <c:if test="${reportDefine.mergeColumnCount==3}">selected</c:if>>前三列</option>
										 <option value='4' <c:if test="${reportDefine.mergeColumnCount==4}">selected</c:if>>前四列</option>
									  </select>
								  </td>
							  </tr>
							  <tr>
								  <td align='right'>隔行换色：</td>
								  <td><select style='width:130px;' name='altRowsOpt' id='altRowsOpt'>
										 <option value='0' <c:if test="${reportDefine.altRows==0}">selected</c:if>>否</option>
										 <option value='1' <c:if test="${reportDefine.altRows==1}">selected</c:if>>是</option>
									  </select>
								  </td>
							   </tr>
							</table>
					    </fieldset>
			       </td>
			       <td valign='top'>
						<fieldset style='border: 1px solid #000000;margin-left: 0px; height: 285px;'>
							<legend>分组字段</legend>
							<div style='margin-left: -10px; margin-left:0px\0; *margin-left:0px; border: 0px solid;overflow:auto;height:270px;'>
								 <c:forEach items="${reportFieldList}" var="reportField" varStatus="vs">
								    <c:if test="${vs.index!=0}"><br/></c:if>
								    &nbsp;<input type='radio' name='chk_groupField' id='chk_groupField_${reportField.columnLabel}' disabled value='${reportField.columnLabel}'>&nbsp;<label for='chk_groupField_${reportField.columnLabel}' style='cursor:pointer;'>${reportField.columnLabel}</label>
								 </c:forEach>
							</div>
						</fieldset>
					</td>
			    </tr>
			</table>
		   <!--tab内容-->
		</div>
		<!--表头设置-->
		<div id="tabs-2" style='padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom: 5px;'>
		<!--tab内容-->
			<table border='0' width='100%'>
				<tr>
					<td width='45%' valign='top'>
						<fieldset style='border: 1px solid #000000; margin-left: 0px; height: 285px;'>
							<legend>表头列表</legend>
							<div style="height: 270px">
								<div align="left"
									style='border: 0px solid; overflow:auto; height:225px;'>
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<hr/>
								<input type="button" value="前移" onclick='moveNode(-1);' id="movePreBtn" disabled style="margin-left: 25px; margin-left: 30px\0; *margin-left: 30px; width: 45px;">
								<input type="button" value="后移" onclick='moveNode(1);' id="moveNextBtn" disabled style="width: 45px; ">
								<input type="button" value="置顶" onclick='moveNode(-2);' id="moveTopBtn" disabled style="width: 45px; ">
								<input type="button" value="置底" onclick='moveNode(2);' id="moveBottomBtn" disabled style="width: 45px; ">
							</div>
						</fieldset>
					</td>
					<td valign='top'>
						<fieldset style='border: 1px solid #000000; height: 285px;'>
							<legend>表头设置</legend>
							<table width='100%' border='0' height='270px' style='margin-left: 0px; display: none' id="headerConfig">
								<tr>
									<td align='right' width="27%">动态列：</td>
									<td>
									  	<select name='dynamicColumn' style='width:150px;' id='dynamicColumn' onchange='isDynamicColumn(this.value,"change");setNodeValue("isDynamicColumn",this.value);' onkeyup="this.blur();this.focus();">
									  　 		<option value="0">否</option>
											<option value="1">是</option>
									  	</select>
									</td>
								</tr>
								<tr style="display: none;" id="headerColumnTr">
									<td align='right'>表头字段：</td>
									<td>
									  <select name='headerField' style='width:150px;' id='headerField' onchange='setNodeValue("headerField",this.value);setNodeValue("name","\#{"+this.value+"}");' onkeyup="this.blur();this.focus();">
									  　 <option value="" style='color:red;'>== 请选择 ==</option>
									  　 <c:forEach items="${reportFieldList}" var="reportField" varStatus="vs">
											<option value='${reportField.columnLabel}' id='opt_header_field_${reportField.columnLabel}'>${reportField.columnLabel}</option>
										 </c:forEach>
									  </select>
									</td>
								</tr>
								<tr>
									<td align='right'>绑定字段：</td>
									<td>
									  <select name='dataField' style='width:150px;' id='dataField' onchange='setNodeValue("dataField",this.value);setFormatter(this);setNodeValue("scale",this.options[this.selectedIndex].getAttribute("scale"));' onkeyup="this.blur();this.focus();">
									  　 <option value="" style='color:red;'>== 请选择 ==</option>
									  　 <c:forEach items="${reportFieldList}" var="reportField" varStatus="vs">
											<option value='${reportField.columnLabel}' id='opt_data_field_${reportField.columnLabel}'>${reportField.columnLabel}</option>
											<script language='javascript'>
											   document.getElementById("opt_data_field_${reportField.columnLabel}").setAttribute("dataFieldType","${reportField.columnType}");
											   document.getElementById("opt_data_field_${reportField.columnLabel}").setAttribute("scale","${reportField.columnScale}");
											</script>
										 </c:forEach>
									  </select>
									</td>
								</tr>
								<tr>
									<td align='right'>字段类型：</td>
									<td>
									   <select name='formatter' style='width:150px;' id='formatter' onchange='setNodeValue("formatter",this.value);' onkeyup="this.blur();this.focus();">
									   	  <option value=''></option>
										  <option value='number'>小数型</option>
										  <option value='integer'>整数型</option>
										  <option value='date'>日期型</option>
										  <option value='string'>字符型</option>
									   </select>
									</td>
									<td><a id="formatBtn" class="myBtn" style="display: none;"
											href="javascript:formatConfig()">
											<em>格式</em></a></td>
								</tr>
								<tr id="headerNameTr">
									<td align='right'>表头名称：</td>
									<td>
									<input type='text' id='headerName' name='headerName' maxlength="10" style='width:144px;' onchange='setNodeValue("name",this.value);' onblur='if(this.value==null||this.value.length==0) this.value="默认名称";setNodeValue("name",this.value);' onfocus='this.select();'></td>
								</tr>
								<tr>
									<td align='right'>显示宽度：</td>
									<td>
									<input type='text' id='textWidth' name='textWidth' maxlength="4" style='width:144px;ime-mode:disabled;' onchange='setNodeValue("width",this.value);' onblur='if(this.value==null||this.value.length==0) this.value=100;setNodeValue("width",this.value);' onfocus='this.select();' onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
								</tr>
								<tr>
									<td align='right'>文字对齐：</td>
									<td>
									  <select name='textAlign' style='width:150px;' id='textAlign' onchange='setNodeValue("align",this.value);' onkeyup="this.blur();this.focus();">
										 <option value='left'>居左对齐</option>
										 <option value='center' selected>居中对齐</option>
										 <option value='right'>居右对齐</option>
									  </select>
									</td>
								</tr>
								<tr>
									<td align='right'>分组汇总：</td>
									<td>
									   <select name='groupSummaryType' style='width:150px;' id='groupSummaryType' onchange='setNodeValue("groupSummaryType",this.value);' onkeyup="this.blur();this.focus();" disabled>
									      <option value=''></option>
										  <option value='count'>汇总</option>
										  <option value='sum'>求和</option>
										  <option value='avg'>平均值</option>
										  <option value='min'>最小值</option>
										  <option value='max'>最大值</option>
									   </select>
									</td>
								</tr>
								<tr>
									<td align='right'>单页汇总：</td>
									<td>
									   <select name='pageSummaryType' style='width:150px;' id='pageSummaryType' onchange='setNodeValue("pageSummaryType",this.value);' onkeyup="this.blur();this.focus();">
									      <option value=''></option>
										  <option value='count'>汇总</option>
										  <option value='sum'>求和</option>
										  <option value='avg'>平均值</option>
										  <option value='min'>最小值</option>
										  <option value='max'>最大值</option>
									   </select>
									</td>
								</tr>
								<tr>
									<td align='right'>钻取报表：</td>
									<td>
									   <input type='text' id='link' name='link' readonly value="" style='width:144px;' onfocus='this.select();'>
									</td>
									<td><a id="linkBtn" class="myBtn"
											href="javascript:parent.window.linkSetting(document.getElementById('link').getAttribute('relRptId'), document.getElementById('link').getAttribute('setting'))">
											<em>选择</em></a></td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		<!--tab内容-->
		</div>
	</div>
</div>
	<textarea name="testResult" rows="3" cols="4" style='width: 99%; height: 40px;display:none;' id='testResult' readonly></textarea>
</BODY>
</HTML>
<script type="text/javascript">
<!--
	var dg;
	$(document).ready(function(){
		dg = frameElement.lhgDG;
		if(dg!=null)
		{
			dg.addBtn('ok','确认',function(){
                testZTree();
			});
		}
	});
	var cellArray = new Array();
	//放置需要换行的表头，暂时储存
	var tempArray = new Array();
	//最大深度
	var maxDepth = 0;
	//节点跨列数
	var cellColSpan = 0;
	function testZTree() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//刷新数据，要不取不到新增的数据
		zTree.refresh();
		//清空数据
		cellArray = new Array();
		//默认选择第一个子结点
		zTree.selectNode(zTree.getNodes()[0]);
		//查询最大的深度
		getTreeMaxDepth(zTree.getNodes()[0]);
		if (maxDepth <= 0) {
			if(!confirm("确定不配置表头吗？")) {
				return;
			}
		}
		//按级别查询
		var colNum = [0, 0, 0];
		var dynamicColNum = 0;
		for (var i = 1; i <= maxDepth; i++) {
			var nodes = zTree.getNodesByParam("level", i, null);
			// 换行后将暂存的表头放入cellArray
			if (tempArray.length != 0) {
				for(var j = 0; j< tempArray.length; j++) {
					cellArray[cellArray.length] = tempArray[j];
				}
				tempArray.length = 0;//清空暂存的表头
			}
			if (nodes != null && nodes.length > 0) {
				var tempNode = null;
				var tempNode1 = null;
				var width = 0;
				for ( var j = 0; j < nodes.length; j++) {
					//子节点
					tempNode = nodes[j];
					tempNode1 = nodes[j];
					//节点最大深度
					nodeDepth = 0;
					getNodeDepth(tempNode);
					//报表表单单元格
					var cell = new reportCell();
					cell.colSpan = 0;
					cell.id = tempNode.id;
					cell.pId = tempNode.pId;
					//设置跨行数
					if (tempNode.level == 1) {
						cell.rowSpan = maxDepth - nodeDepth + 1;
					} else if (tempNode.level == 2 && maxDepth == 3 && !tempNode.isParent) {
						nodeDepth = 0;
						getNodeDepth(tempNode.getParentNode());
						if (nodeDepth == 3) {
							cell.rowSpan = 2;
						} else {
							cell.rowSpan = 1;
						}
					} else {
						cell.rowSpan = 1;// 不是第一层则跨行1
					}
					//重新获取节点深度
					nodeDepth = 0;
					getNodeDepth(tempNode);
					//判断是否有子节点，来设置跨列数,列类型
					if (tempNode.children == null || tempNode.children == "") {
						cell.colSpan = 1;		//跨列数1
						cell.dataType = 1;		//数据列
					} else {
						//有子节点，则跨列数为所有子节点跨列数之和
						cellColSpan = 0;
						getCellColSpan(tempNode);
						cell.colSpan = cellColSpan;
						if (nodeDepth == 3 && tempNode.pId == 1) {
							cell.dataType = 3;		//三级表头
						} else {
							cell.dataType = 2;		//二级表头
						}
					}
					
					// 动态列
					if (tempNode.isDynamicColumn == 1) {
						dynamicColNum++;
						if (tempNode.headerField == "" || tempNode.headerField == null) {
							alert("请设置表头字段！");
							zTree.selectNode(tempNode);
							zTreeOnClick(getEvent(), "treeDemo", tempNode);
							return;
						}
						cell.dynamicColumnField = tempNode.headerField;
					}
					var tempDynamicNode = tempNode;
					for (var m = 0; m < i-1; m++) {
						tempDynamicNode = tempDynamicNode.getParentNode();
						if (tempDynamicNode.isDynamicColumn == 1) {
							if (cell.dynamicHeaderFieldList == null) {
								cell.dynamicHeaderFieldList = tempDynamicNode.headerField;
							} else {
								cell.dynamicHeaderFieldList += ","+tempDynamicNode.headerField;
							}
						}
					}
					
					// 链接报表（报表下钻）
					if (tempNode.relRptId != null && tempNode.relRptId != "" && !tempNode.isParent) {
						cell.relRptId = tempNode.relRptId;
						cell.link = tempNode.link;
						cell.relRptName = tempNode.relRptName;
					}
					cell.content = tempNode.name;
					if (!tempNode.isParent){
						cell.dataField = tempNode.dataField;//绑定的字段
					}
					//文字对齐
					cell.align = tempNode.align==null||tempNode.align.length==0?"center":tempNode.align;
					//小计
					cell.rowSum =  tempNode.rowSum==null||tempNode.rowSum.length==0?0:tempNode.rowSum;
					//分组统计
					cell.groupSum =  tempNode.groupSum==null||tempNode.groupSum.length==0?0:tempNode.groupSum;
					//合计
					cell.pageSum =   tempNode.pageSum==null||tempNode.pageSum.length==0?0:tempNode.pageSum;
					//第一个子节点列
					var firstChildCol="";
					if (tempNode.children && tempNode.children.length > 0) {
						tempNode1 = tempNode.children[0];
						while (tempNode1.children && tempNode1.children.length > 0) {
							tempNode1 = tempNode1.children[0];
						}
			           firstChildCol = tempNode1.getParentNode().children[0].dataField;
			           //alert(firstChildCol);
		            }
					cell.firstChildCol = firstChildCol;
					//判断是否绑定了字段
					if((tempNode.children==null || tempNode.children=="") && (tempNode.dataField==null||tempNode.dataField.length==0)) {
						alert('该列未绑定字段：'+tempNode.name);
						zTree.selectNode(tempNode);
						zTreeOnClick(getEvent(), "treeDemo", tempNode);
						return;
					}
					//字段类型
					cell.formatter = tempNode.formatter;
					cell.commas = tempNode.commas || 0;
					cell.currency = tempNode.currency || 0;
					cell.scale = tempNode.scale;//精度
					cell.warningValue = tempNode.warningValue;
					//datefmt
					cell.datefmt = tempNode.datefmt;
					//如果设置了可分组统计，则选择
					if(document.getElementById("groupingOpt").value==1) {
					   cell.groupSummaryType = tempNode.groupSummaryType;
					} else {
                       cell.groupSummaryType="";
					}
					//本页汇总
					cell.pageSummaryType = tempNode.pageSummaryType;
					//宽度,如果是父节点，则为所有子节点之和
					width = 0;
					if (tempNode.children && tempNode.children.length > 0) {
						for ( var m = 0; m < tempNode.children.length; m++) {
							width += parseInt(tempNode.children[0].width); 
						}
						cell.width = width;
					} else {
						cell.width = tempNode.width<=0?100:tempNode.width;
					}
					// 处理需要换行的列
					if (tempNode.level == 2 && maxDepth ==3 && tempNode.children == null && cell.rowSpan == 1) {
						cell.rowNum = i;//行
						cell.colNum = colNum[i]++;//列
						tempArray[tempArray.length] = cell;//暂时储存，到下一行时添加
					} else {
						cell.rowNum = i-1;//行
						cell.colNum = colNum[i-1]++;//列
						//添加
						cellArray[cellArray.length] = cell;
					}
				}
			}
		}
		//绑定字段不能相同
		var nodes = zTree.transformToArray(zTree.getNodes());
		if (nodes != null && nodes.length > 0) {
			for ( var i = 1; i < nodes.length-1; i++) {
				for ( var j = i+1; j < nodes.length; j++) {
					if( nodes[i].isParent || nodes[j].isParent){
						continue;
					}
					if( nodes[i].dataField == nodes[j].dataField){
						alert('"'+nodes[i].name+'"'+'绑定的字段和'+'"'+nodes[j].name+'"'+'绑定的字段相同，请修改。');
						zTree.selectNode(nodes[i]);
						zTreeOnClick(getEvent(), "treeDemo", nodes[i]);
						return;
					}
				}
			}
			// 绑定动态列字段不能相同
			for ( var i = 1; i < nodes.length-1; i++) {
				for ( var j = i+1; j < nodes.length; j++) {
					if(!nodes[i].isDynamicColumn || !nodes[j].isDynamicColumn){
						continue;
					}
					if(nodes[i].headerField == nodes[j].headerField){
						alert('"'+nodes[i].name+'"'+'绑定的表头字段和'+'"'+nodes[j].name+'"'+'绑定的表头字段相同，请修改。');
						zTree.selectNode(nodes[i]);
						zTreeOnClick(getEvent(), "treeDemo", nodes[i]);
						return;
					}
				}
			}
		}
		//报表信息
		var reportName=document.getElementById('reportName').value;
		if(reportName==null||reportName.length==0) {
			alert('报表名称不能为空');
			return;
		}
		var report=new reportDefine();
		//报表信息
        report.reportId=document.getElementById('reportId').value;
		report.reportName=document.getElementById('reportName').value;
		report.reportDesc="";
		report.width=0;
		report.height=0;
		report.autowidth=0;
		report.dynamicColNum = dynamicColNum;
		//分页大小
		report.pageSize=document.getElementById('pageSizeOpt').value;
		//显示行号
		report.showRowNum=document.getElementById('showRowNumOpt').value;
		//锁定列数，判断锁定的列数是否包含部分子表头
		report.lockColumnCount=document.getElementById('lockColumnCountOpt').value;
		var lock = parseInt(report.lockColumnCount);
		if(lock > 0){
			var nodes = zTree.getNodesByParam("level", 1, null);
			for(var i = 0; i < lock; i++){
				if(nodes[i] == null) {
					alert("总共列数小于锁定列数，请重新设置锁定列数。");
					return;
				} else {
					if(nodes[i].isParent){
						//有子节点，则跨列数为所有子节点跨列数之和
						cellColSpan = 0;
						getCellColSpan(nodes[i]);
						if((cellColSpan+i) > lock){
							alert("锁定列内包含部分的多级表头，请重新设置锁定列数。");
							return;
						}
						lock -= cellColSpan - 1;
					}
				}
			}
		}
		//合并列数
		report.mergeColumnCount=document.getElementById('mergeColumnCountOpt').value;
		//分组字段
		var groupFieldArray = new Array();
        var allCheck=document.getElementsByName("chk_groupField");
		for(var i=0; i < allCheck.length; i++)
		{
			var groupField = new Object();
			groupField.fieldName = "";
			groupField.isDataColumn = 0;
		   	if (allCheck[i].checked) {
			   	groupField.fieldName = allCheck[i].value;
			   	//判断是否数据列
			   	for ( var j = 0; j < cellArray.length; j++) {
					if (cellArray[j].dataField == groupField.fieldName) {
						groupField.isDataColumn = 1;
					}
				}
			   	groupFieldArray[groupFieldArray.length] = groupField;
			}
		}
		if (groupFieldArray.length > 0) {
			report.groupFieldList = groupFieldArray;
		}
		//设置了分组，但没有选择分组字段。
		if(document.getElementById('groupingOpt').value == 1 && groupFieldArray.length == 0){
			alert("请选择分组字段或取消分组。");
			$("#tab1").click();
			return;
		}
		//本页合计
		report.summary=0;
		var nodes = zTree.transformToArray(zTree.getNodes());
		if (nodes != null && nodes.length > 0) {
			for(var i = 1; i < nodes.length; i++) {
				if(!nodes[i].isParent && nodes[i].pageSummaryType != '' && nodes[i].pageSummaryType != null) {
					report.summary=1;
					break;
				}
			}
		}
		//隔行换色
		report.altRows=document.getElementById('altRowsOpt').value;
		//报表列
		report.reportCellList=cellArray;
		document.getElementById("testResult").value = JSON.stringify(report);
		window.parent.document.getElementById('reportDefine').value=document.getElementById("testResult").value;
		//刷新父级报表界面,预览报表界面及报表
		//保存名称
		window.parent.document.getElementById('reportName').value=reportName;
		//保存
		window.parent.saveReportDesign();
        //关闭窗口
        dg.cancel();
	}

    //同时兼容ie和ff的写法
    function getEvent() 
    { 
        if(document.all)  return window.event;   
        func=getEvent.caller;       
        while (func!=null) { 
            var arg0=func.arguments[0];
            if (arg0) {
              if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)) { 
              	return arg0;
              }
            }
            func=func.caller;
        }
        return null;
    }

    //报表定义
	function reportDefine(reportId,reportName,reportDesc,width,height,autowidth,pageSize,
			showRowNum,lockColumnCount,mergeColumnCount,groupFieldList,summary,altRows,
			reportCellList,dynamicColNum) {
	   /*报表信息*/
       this.reportId=reportId;
	   this.reportName=reportName;
       this.reportDesc=reportDesc;
	   this.width=width;//宽度
	   this.height=height;//高度
	   this.autowidth=autowidth;//自动宽度
	   this.pageSize=pageSize;//分页页大小
	   this.showRowNum=showRowNum;//显示行号
	   this.lockColumnCount=lockColumnCount;//锁定列
	   this.mergeColumnCount=mergeColumnCount;//和并列
	   this.groupFieldList=groupFieldList;//分组列
	   this.summary=summary;//本页合计
	   this.altRows=altRows;//隔行换色
       this.reportCellList=reportCellList;//报表列定义
       this.dynamicColNum=dynamicColNum;//动态列数量
	}

	/**
	  id  ID
	  pId 父级ID
	  rowNum：TD行号
	  colNum：TD列号
	  rowSpan：TD合并行数
	  colSpan：TD合并列数
	  content：TD内容 如果是绑定字段，则是${list.字段名}
	  dataType：TD数据类型 0:普通文本 1：绑定字段
	  dataShowType：TD显示方式 0:列表显示，1:汇总统计 2：分组统计
	  dataField：TD关联字段 SQL中字段
	  dynamicColumnField： 动态列名
	  dynamicHeaderFieldList：动态字段表头字段
	  relRptId： 关联报表ID
	  link： 链接URL
	  relRptName：关联报表名称
	  commas：千分符标识
	  currency：货币符标识
	  warningValue：预警值
	 */
	function reportCell(id, pId, rowNum, colNum, rowSpan, colSpan, content,
			dataType, dataShowType, dataField,align,rowSum,groupSum,pageSum,firstChildCol,
			formatter,datefmt,groupSummaryType,pageSummaryType,width,scale,dynamicColumnField,
			dynamicHeaderFieldList,relRptId,link,relRptName,commas,currency,warningValue) {
		this.id = id;
		this.pId = pId;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.content = content;
		this.dataType = dataType;
		this.dataShowType = dataShowType;
		this.dataField = dataField;
		this.align = align;
		//小计
		this.rowSum = rowSum;
		//分组统计
		this.groupSum = groupSum;
		//合计
		this.pageSum = pageSum;
		//第一个子节点列
		this.firstChildCol = firstChildCol;
		//formatter
		this.formatter = formatter;
		//datefmt
		this.datefmt = datefmt;
		//groupSummaryType
		this.groupSummaryType = groupSummaryType;
		//pageSummaryType
		this.pageSummaryType = pageSummaryType;
		//width
		this.width = width;
		//精度
		this.scale = scale;
		this.dynamicColumnField = dynamicColumnField;
		this.dynamicHeaderFieldList = dynamicHeaderFieldList;
		this.relRptId = relRptId;
		this.link = link;
		this.relRptName = relRptName;
		this.commas = commas;
		this.currency = currency;
		this.warningValue = warningValue;
	}
	//获取最大深度
	function getTreeMaxDepth(zTreeNode) {
		if (zTreeNode.level > maxDepth)
			maxDepth = zTreeNode.level;
		if (zTreeNode.children && zTreeNode.children.length > 0) {
			for ( var i = 0; i < zTreeNode.children.length; i++) {
				getTreeMaxDepth(zTreeNode.children[i]);
			}
		}
	}
	//获取节点跨列数
	function getCellColSpan(zTreeNode) {
		cellColSpan += zTreeNode.children.length;
		for ( var i = 0; i < zTreeNode.children.length; i++) {
			if (zTreeNode.children[i].children && zTreeNode.children[i].children.length >0) {
				cellColSpan--;
				getCellColSpan(zTreeNode.children[i]);
			}
		}
	}
	//移动节点
	function moveNode(flag) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var crtNode = zTree.getSelectedNodes()[0];
		if (flag < 0) {
			//前移或置顶
			if (crtNode.getPreNode() == null || crtNode.isFirstNode)
				return;//或用方法isFirstNode;
			if (flag == -1) {
				//前移
				zTree.moveNode(crtNode.getPreNode(), crtNode, "prev");//将第二个节点 移动成为 第一个节点的前一个节点
			} else if (flag == -2) {
				//置顶
				//父结点
				var parentNode = crtNode.getParentNode();
				zTree.moveNode(parentNode.children[0], crtNode, "prev");//将第二个节点 移动成为 第一个节点的前一个节点
			}
		} else {
			//后移或置底
			if (crtNode.getNextNode() == null || crtNode.isLastNode)
				return;
			if (flag == 1) {
				//后移
				zTree.moveNode(crtNode.getNextNode(), crtNode, "next");//将第二个节点 移动成为 第一个节点的后一个节点
			} else if (flag == 2) {
				//置底
				//父结点
				var parentNode = crtNode.getParentNode();
				zTree.moveNode(parentNode.children[parentNode.children.length - 1], crtNode, "next");//将第二个节点 移动成为 第一个节点的后一个节点
			}
		}
		zTreeOnClick(getEvent(), "treeDemo", crtNode);
	}
//-->
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/common";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表表头设计</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<%=basePath%>/js/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<!-- 用于IE8之前的版本，处理JSON数据 -->
<script type="text/javascript" src="<%=basePath%>/js/json2.js"></script>

<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.exedit-3.2.js"></script>
<!--uuid-->
<script type="text/javascript" src="<%=basePath%>/js/zTree/js/uuid.js"></script>
</head>
<body>
<script type="text/javascript">
	var reportColumnList = window.parent.document.getElementById("datalist").contentWindow.document.getElementById("reportColumnList");
	var result = eval(reportColumnList.value);
	var isChecked = 0;
	for ( var i = 0; i < result.length; i++) {
		if (result[i].isShow == true) {
			isChecked = 1;
		}
	}
	
	var setting = {
			view : {
				selectedMulti : false,//不能多选
				nameIsHTML : true
			},
			check : {
				enable : true,
				chkStyle : "checkbox"
			},
			edit : {
				//展开
				dblClickExpand : dblClickExpand
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				// 选中
				onClick : zTreeOnClick
			}
		};
	var zNodes = [{
		id : 1,
		pId : 0,
		name : '<b>列配置</b>',
		open : true,
		checked : false,
		icon : '<%=basePath%>/js/zTree/img/8.png'}];
	// 是否选中根节点
	if (isChecked == 1) {
		zNodes[0].checked = true;
	}
	for(var i=0; i<result.length; i++){
		if(result[i].frozen == 1){		//锁定列，不能隐藏
			zNodes[i+1] = {
					id : result[i].id,
					pId : result[i].pId,
					name : result[i].name,
					checked : result[i].isShow,
					dataField : result[i].dataField,
					chkDisabled :true,
					frozen : 1,
					isChanged : 0,
					open : true};
		}else{
			zNodes[i+1] = {
					id : result[i].id,
					pId : result[i].pId,
					name : result[i].name,
					checked : result[i].isShow,
					dataField : result[i].dataField,
					isChanged : result[i].isChanged,
					open : true};
		}
	}	 
	
	//双击扩展，不扩展根节点
	function dblClickExpand(treeId, treeNode) {
		return treeNode.level > 0;
	}
	
	//切换所勾选状况
	function zTreeOnClick(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, '', true);
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

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
</script>
<div style='margin-left: -10px; margin-left: 0px\0; *margin-left:0px; margin-top: -5px; *margin-top: 0px; border: 0px solid; overflow:auto; height:auto; width:auto;'>
	<ul id="treeDemo" class="ztree"></ul>
</div>
<textarea name="testResult" rows="3" cols="4"
		style='width: 99%; height: 200px;display: none;' id='testResult' readonly></textarea>
</body>
<script type="text/javascript">
	var dg;
	$(document).ready(function(){
		dg = frameElement.lhgDG;
		if(dg!=null)
		{
			dg.addBtn('ok','确认',function(){
                save();
			});
		}
	});
	//最大深度
	var maxDepth = 0;
	var columnArray = new Array();
	function save(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//默认选择第一个子结点
		zTree.selectNode(zTree.getNodes()[0]);
		//查询最大的深度
		getTreeMaxDepth(zTree.getNodes()[0]);
		//按级别查询
		for ( var i = 1; i <= maxDepth; i++) {
			var nodes = zTree.getNodesByParam("level", i, null);
			if (nodes != null && nodes.length > 0) {
				var tempNode = null;
				for ( var j = 0; j < nodes.length; j++) {
					tempNode = nodes[j];
					//报表列属性
					var column = new reportColumn();
					column.id = tempNode.id;
					column.pId = tempNode.pId;
					column.isShow = tempNode.checked;
					column.name = tempNode.name;
					column.dataField = tempNode.dataField;
					column.frozen = tempNode.frozen;
					if (tempNode.checkedOld == tempNode.checked) {
						column.isChanged = 0;
					} else {
						column.isChanged = 1;
					}
					columnArray[columnArray.length] = column;
				}
			}
		}
		reportColumnList.value = JSON.stringify(columnArray);
		//显示隐藏列
		window.parent.document.getElementById("datalist").contentWindow.hideShowColumns();
		//关闭窗口
        dg.cancel();
	}
	//栏目配置定义
  	/**
	  id  ID
	  pId 父级ID
	  isShow 是否显示
	  name 显示名
	  dataField 绑定字段
	  frozen 是否锁定
	  isChanged 是否改变
	 */
	 function reportColumn(id, pId, isShow, name, dataField, frozen, isChanged) {
			this.id = id;
			this.pId = pId;
			this.isShow = isShow;
			this.name = name;
			this.dataField = dataField;
			this.frozen = frozen;
			this.isChanged = isChanged;
	  	}
</script>
</html>
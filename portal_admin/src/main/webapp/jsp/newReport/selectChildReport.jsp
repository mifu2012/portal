<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择子报表</title>
<%-- <link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/> --%>
<link type="text/css" rel="stylesheet" href="<%=basePath%>js/zTree/css/zTreeStyle/zTreeStyle.css"/>

</head>
<body>
	<div>
		<input type="text" id="reportName" onfocus="this.select()"/>
		<ul id="tree" class="ztree"></ul>
	</div>
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/zTree/js/jquery.ztree.all-3.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/zTree/js/jquery.ztree.exhide-3.4.min.js"></script>
	
	<script type="text/javascript">
		var dg;
		var key;
		$(document).ready(function(){
			// 添加确定按钮
			dg = frameElement.lhgDG;
			dg.addBtn('ok','确定',function(){
				confirmClick();
			});
			// ztree设置
			var setting = {
			    showLine: true,
			    data: {
					simpleData: {
						enable:true
					}
				},
				callback : {
					// 选中
					onClick : zTreeOnClick,
					// 双击选择
					onDblClick: zTreeOnDblClick
				},
				view: {
					//fontCss: getFontCss
				} 
			};
			// ztree节点数据
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			// 初始化ztree
			var t = $("#tree");
			t = $.fn.zTree.init(t, setting, zTreeNodes);
			
			// 绑定查询事件
			key = $("#reportName");
			key.bind("propertychange", searchNode)
				.bind("input", searchNode);
		});
		
		// 单击选中ztree选项
		function zTreeOnClick(event, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			// 如果为父节点扩展、收缩
			if (treeNode.isParent) {
				treeObj.expandNode(treeNode,"","","",true);
			}
		}
		
		// 双击选中ztree选项
		function zTreeOnDblClick(event, treeId, treeNode) {
			if (!treeNode.isParent) {
				confirmClick();
			}
		}
		
		// 点击确定
		function confirmClick() {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes = treeObj.getSelectedNodes();
			if (nodes == null) {
				alert("请选择一个报表！");
				return;
			}
			window.parent.getReportParameters(nodes[0].id, nodes[0].name);
			dg.cancel();
		}
		
		// 筛选包含查询字的节点
		var hasFlag = 0;
		function filter(node, value) {
			hasFlag = 0;
			if (node.isParent) {
				existFilterChild(node, value);
			} else {
				existFilterFather(node, value);
				//return !(node.name.indexOf(value) > -1);
			}
			return (hasFlag == 0);
		}
		
		// 递归往上查询是否存在包含关键字的节点
		function existFilterFather(node, value) {
			if (node.name.indexOf(value) > -1) {
				hasFlag = 1;
				return;
			}
			if (node.getParentNode() != null) {
				existFilterFather(node.getParentNode(), value);
			}
			if (hasFlag == 1) return;
		}
		
		// 递归往下查询是否存在包含关键字的节点
		function existFilterChild(node, value) {
			if (node.name.indexOf(value) > -1) {
				hasFlag = 1;
				return;
			}
			var childNodes = node.children;
			for ( var i = 0; i < childNodes.length; i++) {
				if (childNodes[i].name.indexOf(value) > -1) {
					hasFlag = 1;
					return;
				}
				if (childNodes[i].isParent) {
					existFilterChild(childNodes[i], value);
				}
				if (hasFlag == 1) return;
			}
		}
		
		// 查询节点
		function searchNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			var value = $.trim(key.get(0).value);
			// 显示隐藏的节点
			zTree.showNodes(zTree.getNodesByParam("isHidden", true));
			// 筛选节点
			zTree.hideNodes(zTree.getNodesByFilter(filter, null, null, value));
		}
	</script>
</body>
</html>
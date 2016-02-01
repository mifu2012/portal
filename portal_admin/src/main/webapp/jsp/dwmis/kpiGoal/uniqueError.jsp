<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>错误</title>
<script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<link type="text/css" rel="stylesheet" href="../css/dwmis.css"/>
  <script type="text/javascript" charset="utf-8">
	var dg;
   function fun(flag){
	  /* var temp=flag;
  	alert(temp);
       if(temp=="misDmnsn"){
          alert("维度名称不能重复！");
      }else if(temp=="kpiInfo"){
	   alert("指标名称不能重复！");
      }else if(temp=="misDptmnt"){
	   alert("部门名称不能重复！");
      }else{
	   alert(temp+"暂不支持该绩效类型");
      } 
  	history.back();
	  */
	  alert(flag);
	  dg = frameElement.lhgDG;
	  dg.cancel();
  } 
  //-->
  </script>
<body onload="fun('${flag}')"><!-- onload="fun('$!flag')" -->
${flag}
</body>
</html>

	
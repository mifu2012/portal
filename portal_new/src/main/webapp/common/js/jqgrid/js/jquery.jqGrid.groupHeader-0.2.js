/**
 * jqGrid complexGroupHeaders
 * name cj
 * email 85309651@qq.com
 * blog http://cjblog.iteye.com/
 * version 0.2
 * date 2012-06-03
**/
(function($){
	$.jgrid.extend({
		setComplexGroupHeaders : function(o) {
			var $t = this[0];
			if(!$t.grid) { return; }
			var $ghs;
			if(o.groupHeaders){
				$ghs = o.groupHeaders;
				$(this).setGroupHeaders(o);
			}else{
				$ghs = $t.p.groupHeader;
			}
			var $cghs = o.complexGroupHeaders;
			if(!$cghs){return;}
			var colModel = $t.p.colModel;
			var s_ths = $("#gbox_"+$t.id+" .ui-jqgrid-htable .jqg-second-row-header > th");
			for(var i=0;i<$ghs.groupHeaders.length;i++){
				var ghItem = $ghs.groupHeaders[i];
				var count_secondCol = 0;
				for(var j=0;j<colModel.length;j++){
					if(colModel[j].name==ghItem.startColumnName){
						break;
					}
					count_secondCol++;
				}
				for(var j=0;j<i;j++){
					count_secondCol = count_secondCol - ($ghs.groupHeaders[j].numberOfColumns-1);
				}
				$(s_ths[count_secondCol]).attr("startColName","s_" + ghItem.startColumnName)
				.attr("numberOfColumns",ghItem.numberOfColumns);
			}
			var newTr = $('<tr role="rowheader" class="ui-jqgrid-labels jqg-four-row-header"></tr>');
			var moveThs = {};
			var setThStatus = function(columnName,th,status){
				var thItem = moveThs[columnName];
				if(!thItem){
					moveThs[columnName] = th;
					thItem = th;
				}
				if(thItem.attr("status")!=0 && thItem.attr("status")!=3){
					thItem.attr("status",status);
				}
			};
			
			var startIndex,endIndex;
			for(var i=0;i<$cghs.length;i++){
				for(var j=0;j<colModel.length;j++){
					var colItem = colModel[j];
					if(colItem.name == $cghs[i].startColumnName){
						startIndex = j;
						endIndex = startIndex + $cghs[i].numberOfColumns;
						var numberOfColumns = $cghs[i].numberOfColumns;
						//计算跨列，删除隐藏列
						for ( var n = j; n < j + numberOfColumns; n++) {
							if (colModel[n].hidden == true) {
								$cghs[i].numberOfColumns--;
							}
						}
						break;
					}
				}
				if ($cghs[i].numberOfColumns == 0) {
					continue;
				}
				for(var j=0;j<colModel.length;j++){
					var colItem = colModel[j];
					var thItem = $("thead #" + $t.id + "_" + colItem.name);
					if(j<startIndex||j>=endIndex){
						var a = thItem.attr("rowspan");
						if(thItem.attr("rowspan")==2){
							thItem.attr("sortable",colItem.sortable);
							thItem.attr("colmodeName",colItem.name);
							setThStatus(colItem.name,thItem,1);
						}else if(!thItem.attr("rowspan") || thItem.attr("rowspan") == 1){
							var th = $("#gbox_"+$t.id+" .ui-jqgrid-htable .jqg-second-row-header th[startcolname='s_"+colItem.name+"']");
							if(th.length!=0){
								setThStatus("s_" + colItem.name,th,2);
							}
						}
					}else if(j==startIndex){
						var newTh = $('<th role="columnheader" class="ui-state-default ui-th-column-header ui-th-ltr" style="height: 22px; border-top-width: 0px; border-top-style: none; border-top-color: initial; "></th>');
						newTh.attr("colspan",$cghs[i].numberOfColumns).html($cghs[i].titleText);
						setThStatus(colItem.name,thItem,0);
						setThStatus("n_"+colItem.name,newTh,3);
						var th = $("#gbox_"+$t.id+" .ui-jqgrid-htable .jqg-second-row-header th[startcolname='s_"+colItem.name+"']");
						if(th.length!=0){
							var clonTh = th.clone(true);
							setThStatus("s_" + colItem.name,clonTh,0);
						}
					}else{
						setThStatus(colItem.name,thItem,0);
						var th = $("#gbox_"+$t.id+" .ui-jqgrid-htable .jqg-second-row-header th[startcolname='s_"+colItem.name+"']");
						if(th.length!=0){
							var clonTh = th.clone(true);
							setThStatus("s_" + colItem.name,clonTh,0);
						}
					}
				}
			}
			for (prop in moveThs) {
				var item = moveThs[prop];
				var status = item.attr("status");
				if(status==1){
					var cloneItem = item.clone(true);
					cloneItem.attr("rowspan",3);
					var htableHeight = $("#gbox_"+$t.id+" .ui-jqgrid-htable").css("height");
					var height = parseInt(htableHeight.substring(0,htableHeight.indexOf('p')), 10) + 22 +'px';
					cloneItem.css("height", height);
					if(cloneItem.attr("sortable")=='true'){
						cloneItem.unbind("click");
						cloneItem.click(function(){
							$("#gbox_"+$t.id+" .ui-jqgrid-htable .s-ico").css("display","none");
							var name = $(this).attr("colmodeName");
							$t.p.sortname = name;
							$(this).find("span.s-ico").css("display","inline");
							var ascClass = $(this).find("span.ui-icon-asc").attr("class");
							var reg = /.*ui-state-disabled.*/ig;
							var result = reg.test(ascClass);
							if(result){
								$t.p.sortorder = 'asc';
								$(this).find("span.ui-icon-asc").removeClass("ui-state-disabled");
								$(this).find("span.ui-icon-desc").addClass("ui-state-disabled");
							}else{
								$t.p.sortorder = 'desc';
								$(this).find("span.ui-icon-desc").removeClass("ui-state-disabled");
								$(this).find("span.ui-icon-asc").addClass("ui-state-disabled");
							}
							$($t).trigger("reloadGrid");
						});
					}
					newTr.append(cloneItem);
					item.remove();
				}else if(status==2){
					newTr.append(item.clone(true).attr("rowspan",2));
					item.remove();
				}else if(status==3){
					newTr.append(item);
				}
			}
			var second_tr = $("#gbox_"+$t.id+" .ui-jqgrid-htable .jqg-second-row-header");
			newTr.insertBefore(second_tr);
		}
	});
})(jQuery);
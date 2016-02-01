$E.domReady(function(){
	// tree menu
	A($$(".treemenu dt a")).each(function(el){
		E.connect(el,"click",function(ev){
			selfel=el.parent().next();
			if(selfel.hasClass("fn-hide")){
				selfel.removeClass("fn-hide");
				el.parent().removeClass("act");
			}else{
				selfel.addClass("fn-hide");
				el.parent().addClass("act");
			}
			ev.stopEvent();
		});
	});
	
	// select控件
	A($$(".J-select dt a")).each(function(el){
		E.connect(el,"click",function(ev){
			selfel=el.parent().next();
			if(selfel.hasClass("fn-hide")){
				selfel.removeClass("fn-hide");
			}else{
				selfel.addClass("fn-hide");
				el[0].blur();
			}
			ev.stopEvent();
		});
	});
	A($$(".J-select dd a")).each(function(el){
		E.connect(el,"click",function(ev){
			html=el.getHtml();
			val=el.attr("data");
			selfel=el.parent().prev().first("span");
			selfel.setHtml(html);
			selfel=el.parent().prev().first("input");
			selfel.attr("value",val);
			el.parent().addClass("fn-hide");
			ev.stopEvent();
		});
	});
	function hideselect(){
		A($$(".niceselect")).each(function(el){
			selfel=el.first("dd");
			if(selfel!=null){
				divel=selfel.first("div");
				if(divel==null){
					selfel.addClass("fn-hide");
				}else{
					divel.setStyle("display","none");
				}
			}
		});
	}
	document.onclick = function(event){
		ev=window.event.srcElement;
		if(ev==null){
			hideselect();
		}
		tagname=ev.tagName;
		if(tagname=="HTML"){
			hideselect();
		}else{
			if(tagname!="DL"){
				while(tagname!="DT" && tagname!="DL" && tagname!="HTML"){
					ev=ev.parentNode;
					tagname=ev.tagName;
				}
				if(tagname=="DL"){
					if(ev.className.indexOf("niceselect")<0){
						hideselect();
					}
				}else{
					hideselect();
				}
			}else{
				if(ev.className.indexOf("niceselect")<0){
					hideselect();
				}
			}
		}
	};
	
	formatnicetable();
	
	A($$(".treemenu dd a")).each(function(el){
		if(el.hasClass("current")){
			ddel=el.parent("dd");
			dtel=ddel.prev("dt");
			dtel.removeClass("act");
			ddel.removeClass("fn-hide");
		}
	});
});


// nicetable
function formatnicetable(){
	A($$(".nicetable tbody tr")).each(function(el){
		E.connect(el,"mouseover",function(ev){
			el.addClass("bg");
		});
		E.connect(el,"mouseout",function(ev){
			el.removeClass("bg");
		});
	});
}

// formatdate
function formatdate(xdate,sign){
	if(sign){
		xyear=xdate.substring(0,4);
		xmonth=Number(xdate.substring(4,6));
		xday=Number(xdate.substring(6,8));
		return(xyear+"."+xmonth+"."+xday);
	}else{
		ndate=xdate.split(".");
		xyear=ndate[0];
		xmonth=Number(ndate[1]);
		if(xmonth<10)xmonth="0"+xmonth;
		xday=Number(ndate[2]);
		if(xday<10)xday="0"+xday;
		return(String(xyear)+String(xmonth)+String(xday));
	}
}
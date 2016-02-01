/**
 * 地洞仪通用js.
 * @author 阿拉神丁 (djqq1987[AT]gmail.com)
 * @version 0.1, 2011/11/16
 */
E.domReady(function () {
    Loader.use(['aralex.calendar', 'aralex.xbox','arale.http'], function () {

		//通用选择产品弹出框  x2
        x2 = new aralex.xbox.DomXbox({
            width: 800,
            height: 500,
            beforeHide: function () {
                //alert("hidding...")
            },
            beforeShow: function () {
                //	alert("show...");
                currentBox = this;
            },
            afterShow: function () {
            },
            value: function (e) {
                return $('J-selPro');
            }
        });
        $A($$(".J-sdrop")).each(function (el) {
            el.click(function (e) {
                if (!el.hasClass("changepro")) {
                    if ($("productId") && $("productId").node.value) {
                        location.href = el.attr("data-url") + "?productId=" + $("productId").node.value;
                        return;
                    }
                }
                if ($("J-toUrl") && $("J-toUrl").node.value) {
                    $("J-toUrl").node.value = el.attr("data-url");
                }
                x2.show();
            });
        });
        
        //首页查看明细弹出框
        if($('J-morebox')){
	        x3 = new aralex.xbox.DomXbox({
	            width: 690,
	            beforeHide: function () {
	                //alert("hidding...")
	            },
	            beforeShow: function () {
	                //	alert("show...");
	                currentBox = this;
	            },
	            afterShow: function () {
	            },
	            value: function (e) {
	                return $('J-morebox');
	            }
	        });
	        
	        $A($$(".J-morebtn")).each(function (el) {
	            el.click(function (e) {
	                x3.show();
	            });
	        });
        }

		//趋势图通用弹出
        $A($$(".J-showtread")).each(function(el){
        	el.click(function(){
        		var d = el.attr("data-date");
        		var u = el.attr("data-url");
        		var prod = el.attr("data-prod");
        		if(prod){
        			prod="&productId="+prod;
        		}else{
        			prod="";
        		}
                var frameboxtrend = new aralex.xbox.IframeXbox({
                	el: "fireIFRAME",
                	closeLink: '<a href="#" style="margin-right:10px;">关闭</a>', //可以被覆盖
                	value: u+"?date="+d+prod,
                    width: "800"
                });
                frameboxtrend.show();
        	});
        });
		
		
        
        //大事件通用弹出
        $A($$(".J-show-event")).each(function(el){
        	el.click(function(){
        		var id = el.attr("data-id");
                var frameboxs = new aralex.xbox.IframeXbox({
                	el: "fireIFRAME",
                	closeLink: '<a href="#" style="margin-right:10px;">关闭</a>', //可以被覆盖
                	value: "/eventDetail.htm?eventId=" + id,
                    width: "960"
                });
                frameboxs.show();
        	});
        });
        //大事件通用弹出amcharts使用
		window.bigEvent=function(eveid){
			var id = eveid;
            var framebox = new aralex.xbox.IframeXbox({
               	el: "fireIFRAME",
                value: "/eventDetail.htm?eventId=" + id,
                width: "960"
            });
            framebox.show();
		};
        
		//页面浮层空白点击隐藏通用
        E.on($$("body"), "onclick", function (e) {

            if ($(e.target).hasClass("datesel-btn") || $(e.target).parent(".aralex-calcontainer")) {

            } else {
                cal && cal.hide();
            }
            if ($(e.target).hasClass("monthsel-btn") || $(e.target).parent(".sel-month")) {

            } else {
                $("J-month") && $("J-month").addClass("hidden");
            }
            if ($(e.target).hasClass("J-tip") || $(e.target).parent(".J-tip") || $(e.target).hasClass("box-askicon")) {

            } else {
                A($$(".J-tip")).each(function(el){
                	el.addClass('hidden');
                });
            }
            return;
        });
        
        //导航头健康度下拉
        //E.on($$(".drop"),"onmouseover",function(){
        //	$("J-dropitem").removeClass("hidden");
        //});
        //E.on($$(".drop"),"onmouseout",function(){
        //	$("J-dropitem").addClass("hidden");
        //});
        
        //时间初使化
        if (!$("J-time")) {
            var timediv = document.createElement("div");
            timediv.id = "J-time";
            document.body.appendChild(timediv);
        }
		
		//格式化时间2011-05-06 to 20110506
        function formatdate(xdate) {
            var xyear = xdate.substring(0, 4) + "";
            var xmonth = xdate.substring(5, 7) + "";
            var xday = xdate.substring(8, 10) + "";
            return (xyear + "" + xmonth + "" + xday);
        }
        //反格式化时间20110506  to 2011-05-06
        function reformatdate(id, xdate) {
            if ($(id) && $(id).node.value) {
                xdate = $(id) && $(id).node.value;
            }
            var xyear = xdate.substring(0, 4) + "";
            var xmonth = xdate.substring(4, 6) + "";
            var xday = xdate.substring(6, 8) + "";
            return (xyear + "-" + xmonth + "-" + xday);
        }
		//Arale 日期组件初使化
        var cal = new aralex.calendar.Calendar({
            srcId: 'J-time',
            week_start: '7',
            dropfilter: true,
            afterDoSelectEvent: function (date) {
                var productId = "";
                var kpiid = "";
                var statickpiid = "";
                if ($("productId") && $("productId").node.value) {
                    productId = "&productId=" + $("productId").node.value;
                }
                if ($("kpiid") && $("kpiid").node.value) {
                    kpiid = "&kpiid=" + $("kpiid").node.value;
                }
                if ($("statickpiid") && $("statickpiid").node.value) {
                    statickpiid = "&statickpiid=" + $("statickpiid").node.value;
                }
                cal.hide();
                location.href = durl() + "?date=" + formatdate(date) + productId + kpiid + statickpiid;
            },
            mindate: reformatdate("J-minDate", "20110101"),
            maxdate: reformatdate("J-maxDate", "20200101"),
            pagedate:reformatdate("J-nowDate", "20200101"),
            date_delimiter: "-"
        });
        cal.render();
        //class is datesel-btn click to show cal;
        $E.connect($$(".datesel-btn"), "onclick", function (e) {
            var timeNode = $(e.target);
            var pos = timeNode.getOffsets();
            cal.select(reformatdate(null, timeNode.attr("data-val")));
            cal.setPosition(pos.left - 80, pos.top + 22);
            cal.show();
        })

		//获取当前url psthname
        function durl() {
            return window.location.pathname;
        }
		
		
		//月份初使
		var Dmonth = {
		    monthDom: $("J-month"),//月份选择控件
		    clickDom:null,//点击控件
		    beginMonth: "201001",
		    endMonth: "202202",
		    //next two prototype only used in productHealthMainFrame
		    isHealth: false,
		    changetd:"",
		    //init this
		    init: function () {
		        var that = this;
		        that.beginMonth = $("J-minMonth") && $("J-minMonth").node.value || "201001";
		        that.endMonth = $("J-maxMonth") && $("J-maxMonth").node.value || "202202";
		        that.show();
		        that.nextYear();
		        that.preYear();
		        that.selMonth();
		        that.toNowYear();
		        that.initMonthSel();
		    },
		    //show this
		    show: function () {
		    	var that =this;
		        $E.connect($$(".monthsel-btn"), "onclick", function (e) {
		            var monNode = $(e.target);
		            that.clickDom = monNode;
		            if(monNode.hasClass("J-isHealth")){
		            	that.isHealth = true;
		            	that.changetd = monNode.attr("data-val");
		            }
		            var pos = monNode.getOffsets();
		            $("J-month").setPosition({ left: pos.left, top: pos.top + 24 });
		            $("J-month").removeClass('hidden');
		        });
		    },
		    //hide this
		    hide: function () {
		        var that = this;
		        that.monthDom.addClass("hidden");
		        return false;
		    },
		    //nextYear click
		    nextYear: function () {
		        var that = this;
		        E.on("J-nextYear", "onclick", function () {
		            var cyear = parseInt($("J-currentYear").getHtml());
		            $("J-currentYear").setHtml(cyear + 1);
		            that.initMonthSel();
		        });
		    },
		    //prevYear click
		    preYear: function () {
		        var that = this;
		        E.on("J-preYear", "onclick", function () {
		            var cyear = parseInt($("J-currentYear").getHtml());
		            $("J-currentYear").setHtml(cyear - 1);
		            that.initMonthSel();
		        });
		    },
		    //todo:after select a month
		    selMonth: function () {
		        var that = this;
		        E.on("J-monthCon", "onclick", function (e) {
		            var tar = $(e.target);
		            if (tar.hasClass("nosel")) {
		                return;
		            }
		            if (tar.hasClass("acti")) {
		                that.hide();
		                var mo = tar.attr("data-val");
		                var codeid = "";
		                if ($("productId") && $("productId").node.value) {
		                    codeid = "&productId=" + $("productId").node.value;
		                }
		                if(!that.isHealth){
			                var kpiid = "";
			                var statickpiid = "";
			                if ($("kpiid") && $("kpiid").node.value) {
			                    kpiid = "&kpiid=" + $("kpiid").node.value;
			                }
			                if ($("statickpiid") && $("statickpiid").node.value) {
			                    statickpiid = "&statickpiid=" + $("statickpiid").node.value;
			                }
			                location.href = durl() + "?date=" + $("J-currentYear").getHtml() + mo + codeid + kpiid + statickpiid;
			                return;
		                }else{
		                	that.clickDom.node.value = $("J-currentYear").getHtml()+"年"+mo+"月";
		                	that.clickDom.attr("data-date",$("J-currentYear").getHtml()+""+mo);
		                	window.changeHealthDate&&window.changeHealthDate();
		                	that._changeHealthData($("productId").node.value,$("J-currentYear").getHtml(),mo,that.changetd);
		                	return;
		                }
		            }
		        });
		    },
		    //only used in productHealthMainFrame.vm 
		    //param prodId is ProductId
		    //mo is selected month
		    //td is after selected which to change
		    _changeHealthData:function(prodId,year,mo,td){
		    	Ajax.get("/prodana/productHealthMainFrame.htm?getTabData=true&productId="+prodId+"&month="+year+mo, {
                    success: function (data) {
                        if (data) {
                            $(td+"-exchange")&&$(td+"-exchange").setHtml(data.exchange?data.exchange:"-");
                            $(td+"-biznum")&&$(td+"-biznum").setHtml(data.biznum?data.biznum:"-");
                            $(td+"-depuser")&&$(td+"-depuser").setHtml(data.depuser?data.depuser:"-");
                            $(td+"-lost")&&$(td+"-lost").setHtml(data.lost?data.lost:"-");
                            $(td+"-help")&&$(td+"-help").setHtml(data.help?data.help:"-");
                            $(td+"-cross")&&$(td+"-cross").setHtml(data.cross?data.cross:"-");
                        } else {
                            alert("数据获取失败");
                        }
                    },
                    failure: function () {
                        alert("数据获取失败");
                    }
                })
		    },
		    toNowYear:function(){
		    	var that = this;
		    	E.on("J-currentYear","onclick",function(){
		    		var d = new Date();
		    		var y = d.getFullYear()+"";
		    		$("J-currentYear").setHtml(y);
		    		that.initMonthSel();
		    	});
		    },
		    //format month active.
		    initMonthSel: function () {
		        var that = this;
		        var cyear = $("J-currentYear").getHtml();
		        A($$(".acti")).each(function (el) {
		            var mon = el.attr("data-val");
		            if (cyear + mon < that.beginMonth || cyear + mon > that.endMonth) {
		                el.addClass("nosel");
		            } else {
		                el.removeClass("nosel");
		            }
		        });
		    }
		}
        Dmonth.init();
        
        //通用提示组件。
        A($$(".box-askicon")).each(function(el){
        	el.mouseover(function(){
        		A($$(".J-tip")).each(function(el){
                	el.addClass('hidden');
                });
        		el.query(".J-tip")[0].removeClass("hidden");
        	});
        	el.mouseout(function(){
        		A($$(".J-tip")).each(function(el){
                	el.addClass('hidden');
                });
        	});
        });

    });

});
//amcharts bigEvent click
window.bigEvent=null;
function amClickedOnEvent(chart_id, date, des, id,url){
	bigEvent&&bigEvent(id);
}

//add date to thread amcharts on left-top    
function amRolledOver(chart_id, date, period, data_object){
	$("chartdate-"+chart_id)&&$("chartdate-"+chart_id).setHtml(treadChartFormatDate(date));
}
//change date from 20111122 to 2011年11月22日 
//if(xdate.length==6) it return 2011年11月
function treadChartFormatDate(xdate) {
	
	var xyear = xdate.substring(0, 4) + "年";
    var xmonth = xdate.substring(4, 6) + "月";
    var xday = "";
    if(xdate.length>6){
    	 xday = xdate.substring(6, 8) + "日";
   	}
    return (xyear + xmonth + xday);
}





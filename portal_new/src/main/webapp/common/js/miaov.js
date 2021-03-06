(function () {
    var radius = 120;
    var dtr = Math.PI / 180;
    var d = 300;

    var mcList = [];
    var active = true;
    var stop = false;
    var lasta = 1;
    var lastb = 1;
    var distr = true;
    var tspeed = -8;
    var size = 250;

    var mouseX = 5;
    var mouseY = 5;

    var howElliptical = 1;

    var aA = null;
    var oDiv = null;
    
    var sa,ca,sb,cb,sc,cc;

    window.onload = function () {
        var i = 0;
        var oTag = null;

        oDiv = document.getElementById('Div1');

        aA = oDiv.getElementsByTagName('a');
		
		for (i = 0; i < aA.length; i++) {
			var m = $(aA[i]).getHtml();
			var q = "";
			for(var j = 0;j<m.length;j+=5){
				q+=m.substring(j,j+5>m.length?m.length:j+5)+"<br/>";
			}
			$(aA[i]).setHtml(q);
		}
		
        for (i = 0; i < aA.length; i++) {
            oTag = {};

            oTag.offsetWidth = aA[i].offsetWidth;
            oTag.offsetHeight = aA[i].offsetHeight;

            mcList.push(oTag);
        }

        sineCosine(0, 0, 0);

        positionAll();

        oDiv.onmouseover = function () {
            active = true;
        };

        oDiv.onmouseout = function () {
            active = false;
            stop = false;
        };

        oDiv.onmousemove = function (ev) {
            var oEvent = window.event || ev;

            mouseX = oEvent.clientX - (oDiv.offsetLeft + oDiv.offsetWidth / 2);
            mouseY = oEvent.clientY - (oDiv.offsetTop + oDiv.offsetHeight / 2);

            mouseX /= 5;
            mouseY /= 5;
        };
        E.on("Div1", "onmousemove", function (e) {
        	if($(e.target).hasClass("J-astop")){
            	stop = true;
            }else{
            	stop = false;
            }
        });
        setInterval(update, 70);
        
    };

    function update() {
        var a;
        var b;
		a = -1/3;
        b = 1/3
        if (stop) {
	        return;
	    }
        var c = 0;
        sineCosine(a, b, c);
        for (var j = 0; j < mcList.length; j++) {
            var rx1 = mcList[j].cx;
            var ry1 = mcList[j].cy * ca + mcList[j].cz * (-sa);
            var rz1 = mcList[j].cy * sa + mcList[j].cz * ca;

            var rx2 = rx1 * cb + rz1 * sb;
            var ry2 = ry1;
            var rz2 = rx1 * (-sb) + rz1 * cb;

            var rx3 = rx2 * cc + ry2 * (-sc);
            var ry3 = rx2 * sc + ry2 * cc;
            var rz3 = rz2;

            mcList[j].cx = rx3;
            mcList[j].cy = ry3;
            mcList[j].cz = rz3;

            per = d / (d + rz3);

            mcList[j].x = (howElliptical * rx3 * per) - (howElliptical * 2);
            mcList[j].y = ry3 * per;
            mcList[j].scale = per;
            mcList[j].alpha = per;

            mcList[j].alpha = (mcList[j].alpha - 0.6) * (10 / 6);
        }

        doPosition();
        depthSort();
        
    }

    function depthSort() {
        var i = 0;
        var aTmp = [];

        for (i = 0; i < aA.length; i++) {
            aTmp.push(aA[i]);
        }

        aTmp.sort
	(
		function (vItem1, vItem2) {
		    if (vItem1.cz > vItem2.cz) {
		        return -1;
		    }
		    else if (vItem1.cz < vItem2.cz) {
		        return 1;
		    }
		    else {
		        return 0;
		    }
		}
	);

        for (i = 0; i < aTmp.length; i++) {
            aTmp[i].style.zIndex = i;
        }
    }

    function positionAll() {
        var phi = 0;
        var theta = 0;
        var max = mcList.length;
        var i = 0;

        var aTmp = [];
        var oFragment = document.createDocumentFragment();

        //�������
        for (i = 0; i < aA.length; i++) {
            aTmp.push(aA[i]);
        }

        aTmp.sort
	(
		function () {
		    return Math.random() < 0.5 ? 1 : -1;
		}
	);

        for (i = 0; i < aTmp.length; i++) {
            oFragment.appendChild(aTmp[i]);
        }

        oDiv.appendChild(oFragment);

        for (var i = 1; i < max + 1; i++) {
            if (distr) {
                phi = Math.acos(-1 + (2 * i - 1) / max);
                theta = Math.sqrt(max * Math.PI) * phi;
            }
            else {
                phi = Math.random() * (Math.PI);
                theta = Math.random() * (2 * Math.PI);
            }
            //����任
            mcList[i - 1].cx = radius * Math.cos(theta) * Math.sin(phi);
            mcList[i - 1].cy = radius * Math.sin(theta) * Math.sin(phi);
            mcList[i - 1].cz = radius * Math.cos(phi);

            aA[i - 1].style.left = mcList[i - 1].cx + oDiv.offsetWidth / 2 - mcList[i - 1].offsetWidth / 2 + 'px';
            aA[i - 1].style.top = mcList[i - 1].cy + oDiv.offsetHeight / 2 - mcList[i - 1].offsetHeight / 2 + 'px';
        }
    }

    function doPosition() {
        for (var i = 0; i < mcList.length; i++) {
        	var lef = mcList[i].cx + 140 - mcList[i].offsetWidth / 2;
        	var top = mcList[i].cy + 113 - mcList[i].offsetHeight / 2;
        	if(lef<-30){
        		lef = -30;
        	}
        	if(lef>180){
        		lef = 180;
        	}
        	if(top<-20){
        		top = -20;
        	}
        	if(top>185){
        		top = 185;
        	}
            aA[i].style.left = lef + 'px';
            aA[i].style.top = top + 'px';

            var fs = Math.ceil(12 * mcList[i].scale / 2);
            aA[i].style.fontSize = fs + 6 + 'px';

            aA[i].style.filter = "alpha(opacity=" + 100 * mcList[i].alpha + ")";
            aA[i].style.opacity = mcList[i].alpha;
        }
    }

    function sineCosine(a, b, c) {
        sa = Math.sin(a * dtr);
        ca = Math.cos(a * dtr);
        sb = Math.sin(b * dtr);
        cb = Math.cos(b * dtr);
        sc = Math.sin(c * dtr);
        cc = Math.cos(c * dtr);
    }
})();
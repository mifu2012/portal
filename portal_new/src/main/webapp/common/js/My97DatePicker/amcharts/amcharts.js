var inheriting = {},
AmCharts = {
    Class: function(a) {
        var b = function() {
            if (arguments[0] !== inheriting) this.events = {},
            this.construct.apply(this, arguments)
        };
        a.inherits ? (b.prototype = new a.inherits(inheriting), b.base = a.inherits.prototype, delete a.inherits) : (b.prototype.createEvents = function() {
            for (var a = 0,
            b = arguments.length; a < b; a++) this.events[arguments[a]] = []
        },
        b.prototype.listenTo = function(a, b, c) {
            a.events[b].push({
                handler: c,
                scope: this
            })
        },
        b.prototype.addListener = function(a, b, c) {
            this.events[a].push({
                handler: b,
                scope: c
            })
        },
        b.prototype.removeListener = function(a, b, c) {
            a = a.events[b];
            for (b = a.length - 1; 0 <= b; b--) a[b].handler === c && a.splice(b, 1)
        },
        b.prototype.fire = function(a, b) {
            for (var c = this.events[a], g = 0, h = c.length; g < h; g++) {
                var i = c[g];
                i.handler.call(i.scope, b)
            }
        });
        for (var c in a) b.prototype[c] = a[c];
        return b
    },
    charts: []
};
AmCharts.addChart = function(a) {
    AmCharts.charts.push(a)
};
AmCharts.removeChart = function(a) {
    for (var b = AmCharts.charts,
    c = b.length - 1; 0 <= c; c--) b[c] == a && b.splice(c, 1)
};
if (document.addEventListener) AmCharts.isNN = !0,
AmCharts.isIE = !1,
AmCharts.ddd = 0.5;
if (document.attachEvent) AmCharts.isNN = !1,
AmCharts.isIE = !0,
AmCharts.ddd = 0;
AmCharts.IEversion = 0;
if ( - 1 != navigator.appVersion.indexOf("MSIE") && document.documentMode) AmCharts.IEversion = document.documentMode;
if (9 <= AmCharts.IEversion) AmCharts.ddd = 0.5;
AmCharts.handleResize = function() {
    for (var a = AmCharts.charts,
    b = 0; b < a.length; b++) {
        var c = a[b];
        c && c.div && c.handleResize()
    }
};
AmCharts.handleMouseUp = function(a) {
    for (var b = AmCharts.charts,
    c = 0; c < b.length; c++) {
        var d = b[c];
        d && d.handleReleaseOutside(a)
    }
};
AmCharts.handleMouseMove = function(a) {
    for (var b = AmCharts.charts,
    c = 0; c < b.length; c++) {
        var d = b[c];
        d && d.handleMouseMove(a)
    }
};
AmCharts.resetMouseOver = function() {
    for (var a = AmCharts.charts,
    b = 0; b < a.length; b++) {
        var c = a[b];
        if (c) c.mouseIsOver = !1
    }
};
AmCharts.onReadyArray = [];
AmCharts.ready = function(a) {
    AmCharts.onReadyArray.push(a)
};
AmCharts.handleLoad = function() {
    for (var a = AmCharts.onReadyArray,
    b = 0; b < a.length; b++)(0, a[b])()
};
AmCharts.isNN && (document.addEventListener("mousemove", AmCharts.handleMouseMove, !0), window.addEventListener("resize", AmCharts.handleResize, !0), document.addEventListener("mouseup", AmCharts.handleMouseUp, !0), window.addEventListener("load", AmCharts.handleLoad, !0));
AmCharts.isIE && (document.attachEvent("onmousemove", AmCharts.handleMouseMove), window.attachEvent("onresize", AmCharts.handleResize), document.attachEvent("onmouseup", AmCharts.handleMouseUp), window.attachEvent("onload", AmCharts.handleLoad));
AmCharts.AmChart = AmCharts.Class({
    construct: function() {
        this.version = "2.5.0";
        AmCharts.addChart(this);
        this.createEvents("dataUpdated");
        this.height = this.width = "100%";
        this.dataChanged = !0;
        this.chartCreated = !1;
        this.previousWidth = this.previousHeight = 0;
        this.backgroundColor = "#FFFFFF";
        this.borderAlpha = this.backgroundAlpha = 0;
        this.color = this.borderColor = "#000000";
        this.fontFamily = "Verdana";
        this.fontSize = 11;
        this.numberFormatter = {
            precision: -1,
            decimalSeparator: ".",
            thousandsSeparator: ","
        };
        this.percentFormatter = {
            precision: 2,
            decimalSeparator: ".",
            thousandsSeparator: ","
        };
        this.labels = [];
        this.allLabels = [];
        this.titles = [];
        this.chartDiv = document.createElement("div");
        this.chartDiv.style.overflow = "hidden";
        this.legendDiv = document.createElement("div");
        this.legendDiv.style.overflow = "hidden";
        this.balloon = new AmCharts.AmBalloon;
        this.balloon.chart = this;
        this.titleHeight = 0;
        this.prefixesOfBigNumbers = [{
            number: 1E3,
            prefix: "k"
        },
        {
            number: 1E6,
            prefix: "M"
        },
        {
            number: 1E9,
            prefix: "G"
        },
        {
            number: 1E12,
            prefix: "T"
        },
        {
            number: 1E15,
            prefix: "P"
        },
        {
            number: 1E18,
            prefix: "E"
        },
        {
            number: 1.0E21,
            prefix: "Z"
        },
        {
            number: 1.0E24,
            prefix: "Y"
        }];
        this.prefixesOfSmallNumbers = [{
            number: 1.0E-24,
            prefix: "y"
        },
        {
            number: 1.0E-21,
            prefix: "z"
        },
        {
            number: 1.0E-18,
            prefix: "a"
        },
        {
            number: 1.0E-15,
            prefix: "f"
        },
        {
            number: 1.0E-12,
            prefix: "p"
        },
        {
            number: 1.0E-9,
            prefix: "n"
        },
        {
            number: 1.0E-6,
            prefix: "\u03bc"
        },
        {
            number: 0.001,
            prefix: "m"
        }];
        try {
            document.createEvent("TouchEvent"),
            this.touchEventsEnabled = !0
        } catch(a) {
            this.touchEventsEnabled = !1
        }
        this.panEventsEnabled = !1
    },
    drawChart: function() {
        this.destroy();
        var a = this.container.set();
        this.set = a;
        var b = this.container,
        c = this.backgroundColor,
        d = this.backgroundAlpha,
        e = this.realWidth,
        f = this.realHeight;
        if (void 0 != c && 0 < d) this.background = c = AmCharts.rect(b, e - 1, f, c, d, 1, this.borderColor, this.borderAlpha),
        a.push(c);
        if (a = this.backgroundImage) this.path && (a = this.path + a),
        this.bgImg = b = b.image(a, 0, 0, e, f),
        this.set.push(b);
        this.drawTitles()
    },
    drawTitles: function() {
        var a = this.titles;
        if (0 < a.length) for (var b = 20,
        c = 0; c < a.length; c++) {
            var d = a[c],
            e = d.color;
            if (void 0 == e) e = this.color;
            var f = d.size,
            g = d.alpha;
            isNaN(g) && (g = 1);
            var b = b + f / 2,
            h = this.marginLeft,
            e = AmCharts.text(this.container, h + (this.divRealWidth - this.marginRight - h) / 2, b, d.text, {
                fill: e,
                "fill-opacity": g,
                "font-size": f,
                "font-family": this.fontFamily
            }),
            b = !0;
            if (void 0 != d.bold) b = d.bold;
            b && e.attr({
                "font-weight": "bold"
            });
            d = e.getBBox();
            b = d.y + d.height + 5;
            this.set.push(e)
        }
    },
    write: function(a) {
        var b = this.balloon;
        if (b && !b.chart) b.chart = this;
        if (!this.listenersAdded) this.addListeners(),
        this.listenersAdded = !0;
        this.div = a = "object" != typeof a ? document.getElementById(a) : a;
        a.style.overflow = "hidden";
        var b = this.chartDiv,
        c = this.legendDiv,
        d = this.legend;
        this.measure();
        if (d) switch (d.position) {
        case "bottom":
            a.appendChild(b);
            a.appendChild(c);
            break;
        case "top":
            a.appendChild(c);
            a.appendChild(b);
            break;
        case "absolute":
            c.style.position = "absolute";
            b.style.position = "absolute";
            if (void 0 != d.left) c.style.left = d.left;
            if (void 0 != d.right) c.style.right = d.right;
            if (void 0 != this.legend.top) c.style.top = d.top;
            if (void 0 != this.legend.bottom) c.style.bottom = d.bottom;
            a.appendChild(b);
            a.appendChild(c);
            break;
        case "right":
            c.style.position = "relative";
            b.style.position = "absolute";
            a.appendChild(b);
            a.appendChild(c);
            break;
        case "left":
            c.style.position = "relative",
            b.style.position = "absolute",
            a.appendChild(b),
            a.appendChild(c)
        } else a.appendChild(b);
        this.divIsFixed = AmCharts.findIfFixed(b);
        this.container = Raphael(this.chartDiv, this.realWidth, this.realHeight);
        this.initChart()
    },
    initChart: function() {
        this.previousHeight = this.realHeight;
        this.previousWidth = this.realWidth;
        var a = this.container;
        a && a.clear();
        this.redrawLabels()
    },
    renderfix: function() {
        this.container && this.container.renderfix()
    },
    measure: function() {
        var a = this.div,
        b = this.chartDiv,
        c = a.offsetWidth,
        d = a.offsetHeight,
        e = this.container;
        if (a.clientHeight) c = a.clientWidth,
        d = a.clientHeight;
        var a = AmCharts.toCoordinate(this.width, c),
        f = AmCharts.toCoordinate(this.height, d);
        if (a != this.previousWidth || f != this.previousHeight) b.style.width = a + "px",
        b.style.height = f + "px",
        e && e.setSize(a, f),
        this.balloon.setBounds(2, 2, a - 2, f);
        this.realWidth = a;
        this.realHeight = f;
        this.divRealWidth = c;
        this.divRealHeight = d
    },
    destroy: function() {
        AmCharts.removeSet(this.set);
        this.clearTimeOuts()
    },
    clearTimeOuts: function() {
        var a = this.timeOuts;
        if (a) for (var b = 0; b < a.length; b++) clearTimeout(a[b]);
        this.timeOuts = []
    },
    clear: function() {
        AmCharts.callMethod("clear", [this.chartScrollbar, this.scrollbarVertical, this.scrollbarHorizontal, this.chartCursor]);
        this.chartCursor = this.scrollbarHorizontal = this.scrollbarVertical = this.chartScrollbar = null;
        this.clearTimeOuts();
        this.container && this.container.clear();
        AmCharts.removeChart(this)
    },
    setMouseCursor: function(a) {
        document.body.style.cursor = a
    },
    bringLabelsToFront: function() {
        for (var a = this.labels,
        b = a.length - 1; 0 <= b; b--) a[b].toFront()
    },
    redrawLabels: function() {
        this.labels = [];
        for (var a = this.allLabels,
        b = 0; b < a.length; b++) this.drawLabel(a[b])
    },
    drawLabel: function(a) {
        var b = a.x,
        c = a.y,
        d = a.text,
        e = a.align,
        f = a.size,
        g = a.color,
        h = a.rotation,
        i = a.alpha,
        k = a.bold;
        if (this.container) {
            a = AmCharts.toCoordinate(b, this.realWidth);
            c = AmCharts.toCoordinate(c, this.realHeight);
            a || (a = 0);
            c || (c = 0);
            if (void 0 == g) g = this.color;
            if (isNaN(f)) f = this.fontSize;
            e || (e = "start");
            "left" == e && (e = "start");
            "right" == e && (e = "end");
            "center" == e && (e = "middle", h ? c = this.realHeight - c + c / 2 : a = this.realWidth / 2 - a);
            void 0 == i && (i = 1);
            void 0 == h && (h = 0);
            a = AmCharts.text(this.container, a, c + f / 2, d, {
                fill: g,
                "fill-opacity": i,
                "text-anchor": e,
                "font-family": this.fontFamily,
                "font-size": f
            });
            k && a.attr({
                "font-weight": "bold"
            });
            0 != h && a.transform("...R" + h);
            a.toFront();
            this.labels.push(a)
        }
    },
    addLabel: function(a, b, c, d, e, f, g, h, i) {
        a = {
            x: a,
            y: b,
            text: c,
            align: d,
            size: e,
            color: f,
            alpha: h,
            rotation: g,
            bold: i
        };
        this.container && this.drawLabel(a);
        this.allLabels.push(a)
    },
    clearLabels: function() {
        for (var a = this.labels,
        b = a.length - 1; 0 <= b; b--) a[b].remove();
        this.labels = [];
        this.allLabels = []
    },
    updateHeight: function() {
        var a = this.divRealHeight,
        b = this.legend;
        if (b) {
            var c = Number(this.legendDiv.style.height.replace("px", "")),
            b = b.position;
            if ("top" == b || "bottom" == b) a -= c,
            0 > a && (a = 0),
            this.chartDiv.style.height = a + "px"
        }
        return a
    },
    updateWidth: function() {
        var a = this.divRealWidth,
        b = this.divRealHeight,
        c = this.legend;
        if (c) {
            var d = Number(this.legendDiv.style.width.replace("px", "")),
            e = Number(this.legendDiv.style.height.replace("px", "")),
            c = c.position;
            if ("right" == c || "left" == c) a -= d,
            0 > a && (a = 0),
            this.chartDiv.style.width = a + "px",
            "left" == c ? this.chartDiv.style.left = AmCharts.findPosX(this.div) + d + "px": this.legendDiv.style.left = a + "px",
            this.legendDiv.style.top = (b - e) / 2 + "px"
        }
        return a
    },
    getTitleHeight: function() {
        var a = 0,
        b = this.titles;
        if (0 < b.length) for (var c = 0; c < b.length; c++) a += b[c].size + 12;
        return a
    },
    addTitle: function(a, b, c, d, e) {
        isNaN(b) && (b = this.fontSize + 2);
        a = {
            text: a,
            size: b,
            color: c,
            alpha: d,
            bold: e
        };
        this.titles.push(a);
        return a
    },
    addListeners: function() {
        var a = this;
        a.touchEventsEnabled && a.panEventsEnabled ? (a.chartDiv.addEventListener("touchstart",
        function(b) {
            a.handleTouchMove.call(a, b)
        },
        !0), a.chartDiv.addEventListener("touchmove",
        function(b) {
            a.handleTouchMove.call(a, b)
        },
        !0), a.chartDiv.addEventListener("touchstart",
        function(b) {
            a.handleTouchStart.call(a, b)
        }), a.chartDiv.addEventListener("touchend",
        function(b) {
            a.handleTouchEnd.call(a, b)
        })) : (AmCharts.isNN && (a.chartDiv.addEventListener("mousedown",
        function(b) {
            a.handleMouseDown.call(a, b)
        },
        !0), a.chartDiv.addEventListener("mouseover",
        function(b) {
            a.handleMouseOver.call(a, b)
        },
        !0), a.chartDiv.addEventListener("mouseout",
        function(b) {
            a.handleMouseOut.call(a, b)
        },
        !0)), AmCharts.isIE && (a.chartDiv.attachEvent("onmousedown",
        function(b) {
            a.handleMouseDown.call(a, b)
        }), a.chartDiv.attachEvent("onmouseover",
        function(b) {
            a.handleMouseOver.call(a, b)
        }), a.chartDiv.attachEvent("onmouseout",
        function(b) {
            a.handleMouseOut.call(a, b)
        })))
    },
    dispatchDataUpdatedEvent: function() {
        if (this.dispatchDataUpdated) this.dispatchDataUpdated = !1,
        this.fire("dataUpdated", {
            type: "dataUpdated"
        })
    },
    drb: function() {
       
    },
    invalidateSize: function() {
        this.measure();
        if ((this.realWidth != this.previousWidth || this.realHeight != this.previousHeight) && this.chartCreated) this.legend && this.legend.invalidateSize(),
        this.initChart()
    },
    validateData: function(a) {
        if (this.chartCreated) this.dataChanged = !0,
        this.initChart(a)
    },
    validateNow: function() {
        this.initChart()
    },
    showItem: function(a) {
        a.hidden = !1;
        this.initChart()
    },
    hideItem: function(a) {
        a.hidden = !0;
        this.initChart()
    },
    hideBalloon: function() {
        var a = this;
        a.hoverInt = setTimeout(function() {
            a.hideBalloonReal.call(a)
        },
        80)
    },
    hideBalloonReal: function() {
        this.balloon && this.balloon.hide()
    },
    showBalloon: function(a, b, c, d, e) {
        this.handleMouseMove();
        this.balloon.enabled && (this.balloon.followCursor(!1), this.balloon.changeColor(b), c || this.balloon.setPosition(d, e), this.balloon.followCursor(c), a && this.balloon.showBalloon(a))
    },
    handleTouchMove: function(a) {
        this.hideBalloon();
        var b = this.chartDiv;
        if (a.touches) a = a.touches.item(0),
        this.mouseX = a.pageX - AmCharts.findPosX(b),
        this.mouseY = a.pageY - AmCharts.findPosY(b)
    },
    handleMouseOver: function() {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !0
    },
    handleMouseOut: function() {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !1
    },
    handleMouseMove: function(a) {
        if (this.mouseIsOver) {
            var b = this.chartDiv;
            if (!a) a = window.event;
            var c;
            if (a) {
                if (document.attachEvent && !window.opera) {
                    if (!a.target) a.target = a.srcElement;
                    c = a.x;
                    a = a.y
                } else this.divIsFixed ? (c = a.clientX - AmCharts.findPosX(b), a = a.clientY - AmCharts.findPosY(b)) : (c = a.pageX - AmCharts.findPosX(b), a = a.pageY - AmCharts.findPosY(b));
                this.mouseX = c;
                this.mouseY = a
            }
        }
    },
    handleTouchStart: function(a) {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !0;
        this.handleMouseDown(a)
    },
    handleTouchEnd: function(a) {
        AmCharts.resetMouseOver();
        this.handleReleaseOutside(a)
    },
    handleReleaseOutside: function() {},
    handleMouseDown: function(a) {
        AmCharts.resetMouseOver();
        this.mouseIsOver = !0;
        a && a.preventDefault && a.preventDefault()
    },
    addLegend: function(a) {
        this.legend = a;
        a.chart = this;
        a.div = this.legendDiv;
        var b = this.handleLegendEvent;
        this.listenTo(a, "showItem", b);
        this.listenTo(a, "hideItem", b);
        this.listenTo(a, "clickMarker", b);
        this.listenTo(a, "rollOverItem", b);
        this.listenTo(a, "rollOutItem", b);
        this.listenTo(a, "rollOverMarker", b);
        this.listenTo(a, "rollOutMarker", b);
        this.listenTo(a, "clickLabel", b)
    },
    removeLegend: function() {
        this.legend = void 0
    },
    handleResize: function() { (AmCharts.isPercents(this.width) || AmCharts.isPercents(this.height)) && this.invalidateSize();
        this.renderfix()
    }
});
AmCharts.Slice = AmCharts.Class({
    construct: function() {}
});
AmCharts.SerialDataItem = AmCharts.Class({
    construct: function() {}
});
AmCharts.GraphDataItem = AmCharts.Class({
    construct: function() {}
});
AmCharts.Guide = AmCharts.Class({
    construct: function() {}
});
AmCharts.toBoolean = function(a, b) {
    if (void 0 == a) return b;
    switch (("" + a).toLowerCase()) {
    case "true":
    case "yes":
    case "1":
        return ! 0;
    case "false":
    case "no":
    case "0":
    case null:
        return ! 1;
    default:
        return Boolean(a)
    }
};
AmCharts.formatMilliseconds = function(a, b) {
    if ( - 1 != a.indexOf("fff")) {
        var c = b.getMilliseconds(),
        d = "" + c;
        10 > c && (d = "00" + c);
        10 <= c && 100 > c && (d = "0" + c);
        a = a.replace(/fff/g, d)
    }
    return a
};
AmCharts.callMethod = function(a, b) {
    for (var c = 0; c < b.length; c++) {
        var d = b[c];
        if (d) {
            if (d[a]) d[a]();
            var e = d.length;
            if (0 < e) for (var f = 0; f < e; f++) {
                var g = d[f];
                if (g && g[a]) g[a]()
            }
        }
    }
};
AmCharts.toNumber = function(a) {
    return "number" == typeof a ? a: Number(("" + a).replace(/[^0-9\-.]+/g, ""))
};
AmCharts.toColor = function(a) {
    if ("" != a && void 0 != a) if ( - 1 != a.indexOf(",")) for (var a = a.split(","), b = 0; b < a.length; b++) {
        var c = a[b].substring(a[b].length - 6, a[b].length);
        a[b] = "#" + c
    } else a = a.substring(a.length - 6, a.length),
    a = "#" + a;
    return a
};
AmCharts.toSvgColor = function(a, b) {
    if ("object" == typeof a) {
        void 0 == b && (b = 90);
        for (var c = b,
        d = 0; d < a.length; d++) c += "-" + a[d];
        return c
    }
    return a
};
AmCharts.toCoordinate = function(a, b, c) {
    var d;
    void 0 != a && (a = a.toString(), c && c < b && (b = c), d = Number(a), -1 != a.indexOf("!") && (d = b - Number(a.substr(1))), -1 != a.indexOf("%") && (d = b * Number(a.substr(0, a.length - 1)) / 100));
    return d
};
AmCharts.fitToBounds = function(a, b, c) {
    a < b && (a = b);
    a > c && (a = c);
    return a
};
AmCharts.isDefined = function(a) {
    return void 0 == a ? !1 : !0
};
AmCharts.stripNumbers = function(a) {
    return a.replace(/[0-9]+/g, "")
};
AmCharts.extractPeriod = function(a) {
    var b = AmCharts.stripNumbers(a),
    c = 1;
    b != a && (c = Number(a.slice(0, a.indexOf(b))));
    return {
        period: b,
        count: c
    }
};
AmCharts.resetDateToMin = function(a, b, c, d) {
    void 0 == d && (d = 1);
    var e, f, g, h, i, k, j;
    switch (b) {
    case "YYYY":
        e = Math.floor(a.getFullYear() / c) * c;
        f = 0;
        g = 1;
        j = k = i = h = 0;
        break;
    case "MM":
        e = a.getFullYear();
        f = Math.floor(a.getMonth() / c) * c;
        g = 1;
        j = k = i = h = 0;
        break;
    case "WW":
        e = a.getFullYear();
        f = a.getMonth();
        b = a.getDay();
        0 == b && 0 < d && (b = 7);
        g = a.getDate() - b + d;
        j = k = i = h = 0;
        break;
    case "DD":
        e = a.getFullYear();
        f = a.getMonth();
        g = Math.floor(a.getDate() / c) * c;
        j = k = i = h = 0;
        break;
    case "hh":
        e = a.getFullYear();
        f = a.getMonth();
        g = a.getDate();
        h = Math.floor(a.getHours() / c) * c;
        j = k = i = 0;
        break;
    case "mm":
        e = a.getFullYear();
        f = a.getMonth();
        g = a.getDate();
        h = a.getHours();
        i = Math.floor(a.getMinutes() / c) * c;
        j = k = 0;
        break;
    case "ss":
        e = a.getFullYear();
        f = a.getMonth();
        g = a.getDate();
        h = a.getHours();
        i = a.getMinutes();
        k = Math.floor(a.getSeconds() / c) * c;
        j = 0;
        break;
    case "fff":
        e = a.getFullYear(),
        f = a.getMonth(),
        g = a.getDate(),
        h = a.getHours(),
        i = a.getMinutes(),
        k = a.getSeconds(),
        j = Math.floor(a.getMilliseconds() / c) * c
    }
    return a = new Date(e, f, g, h, i, k, j)
};
AmCharts.getPeriodDuration = function(a, b) {
    void 0 == b && (b = 1);
    var c;
    switch (a) {
    case "YYYY":
        c = 316224E5;
        break;
    case "MM":
        c = 26784E5;
        break;
    case "WW":
        c = 6048E5;
        break;
    case "DD":
        c = 864E5;
        break;
    case "hh":
        c = 36E5;
        break;
    case "mm":
        c = 6E4;
        break;
    case "ss":
        c = 1E3;
        break;
    case "fff":
        c = 1
    }
    return c * b
};
AmCharts.roundTo = function(a, b) {
    if (0 > b) return a;
    var c = Math.pow(10, b);
    return Math.round(a * c) / c
};
AmCharts.intervals = {
    s: {
        nextInterval: "ss",
        contains: 1E3
    },
    ss: {
        nextInterval: "mm",
        contains: 60,
        count: 0
    },
    mm: {
        nextInterval: "hh",
        contains: 60,
        count: 1
    },
    hh: {
        nextInterval: "DD",
        contains: 24,
        count: 2
    },
    DD: {
        nextInterval: "",
        contains: Infinity,
        count: 3
    }
};
AmCharts.getMaxInterval = function(a, b) {
    var c = AmCharts.intervals;
    return a >= c[b].contains ? (a = Math.round(a / c[b].contains), b = c[b].nextInterval, AmCharts.getMaxInterval(a, b)) : "ss" == b ? c[b].nextInterval: b
};
AmCharts.formatDuration = function(a, b, c, d, e, f) {
    var g = AmCharts.intervals,
    h = f.decimalSeparator;
    if (a >= g[b].contains) {
        var i = a - Math.floor(a / g[b].contains) * g[b].contains;
        "ss" == b && (i = AmCharts.formatNumber(i, f), 1 == i.split(h)[0].length && (i = "0" + i));
        if (("mm" == b || "hh" == b) && 10 > i) i = "0" + i;
        c = i + "" + d[b] + "" + c;
        a = Math.floor(a / g[b].contains);
        b = g[b].nextInterval;
        return AmCharts.formatDuration(a, b, c, d, e, f)
    }
    "ss" == b && (a = AmCharts.formatNumber(a, f), 1 == a.split(h)[0].length && (a = "0" + a));
    if (("mm" == b || "hh" == b) && 10 > a) a = "0" + a;
    c = a + "" + d[b] + "" + c;
    if (g[e].count > g[b].count) for (a = g[b].count; a < g[e].count; a++) b = g[b].nextInterval,
    "ss" == b || "mm" == b || "hh" == b ? c = "00" + d[b] + "" + c: "DD" == b && (c = "0" + d[b] + "" + c);
    ":" == c.charAt(c.length - 1) && (c = c.substring(0, c.length - 1));
    return c
};
AmCharts.formatNumber = function(a, b, c, d, e) {
    a = AmCharts.roundTo(a, b.precision);
    if (isNaN(c)) c = b.precision;
    var f = b.decimalSeparator,
    b = b.thousandsSeparator,
    g = 0 > a ? "-": "",
    a = Math.abs(a),
    h = a.toString();
    if ( - 1 == h.indexOf("e")) {
        for (var h = h.split("."), i = "", k = h[0].toString(), j = k.length; 0 <= j; j -= 3) i = j != k.length ? 0 != j ? k.substring(j - 3, j) + b + i: k.substring(j - 3, j) + i: k.substring(j - 3, j);
        void 0 != h[1] && (i = i + f + h[1]);
        void 0 != c && 0 < c && "0" != i && (i = AmCharts.addZeroes(i, f, c))
    } else i = h;
    i = g + i;
    "" == g && !0 == d && 0 != a && (i = "+" + i); ! 0 == e && (i += "%");
    return i
};
AmCharts.addZeroes = function(a, b, c) {
    a = a.split(b);
    void 0 == a[1] && 0 < c && (a[1] = "0");
    return a[1].length < c ? (a[1] += "0", AmCharts.addZeroes(a[0] + b + a[1], b, c)) : void 0 != a[1] ? a[0] + b + a[1] : a[0]
};
AmCharts.scientificToNormal = function(a) {
    var b, a = a.toString().split("e");
    if ("-" == a[1].substr(0, 1)) {
        b = "0.";
        for (var c = 0; c < Math.abs(Number(a[1])) - 1; c++) b += "0";
        b += a[0].split(".").join("")
    } else {
        var d = 0;
        b = a[0].split(".");
        if (b[1]) d = b[1].length;
        b = a[0].split(".").join("");
        for (c = 0; c < Math.abs(Number(a[1])) - d; c++) b += "0"
    }
    return b
};
AmCharts.toScientific = function(a, b) {
    if (0 == a) return "0";
    var c = Math.floor(Math.log(Math.abs(a)) * Math.LOG10E);
    Math.pow(10, c);
    mantissa = mantissa.toString().split(".").join(b);
    return mantissa.toString() + "e" + c
};
AmCharts.generateGradient = function(a, b, c) {
    if (c) for (var d = c.length - 1; 0 <= d; d--) b += "-" + AmCharts.adjustLuminosity(a, c[d] / 100);
    else if ("object" == typeof a) if (1 < a.length) for (d = 0; d < a.length; d++) b += "-" + a[d];
    else b = a[0];
    else b = a;
    return b
};
AmCharts.randomColor = function() {
    function a() {
        return Math.floor(256 * Math.random()).toString(16)
    }
    return "#" + a() + a() + a()
};
AmCharts.hitTest = function(a, b, c) {
    var d = !1,
    e = a.x - 5,
    f = a.x + a.width + 5,
    g = a.y - 5,
    h = a.y + a.height + 5,
    i = AmCharts.isInRectangle;
    d || (d = i(e, g, b));
    d || (d = i(e, h, b));
    d || (d = i(f, g, b));
    d || (d = i(f, h, b)); ! d && !0 != c && (d = AmCharts.hitTest(b, a, !0));
    return d
};
AmCharts.isInRectangle = function(a, b, c) {
    return a >= c.x - 5 && a <= c.x + c.width + 5 && b >= c.y - 5 && b <= c.y + c.height + 5 ? !0 : !1
};
AmCharts.isPercents = function(a) {
    if ( - 1 != ("" + a).indexOf("%")) return ! 0
};
AmCharts.dayNames = "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday".split(",");
AmCharts.shortDayNames = "Sun,Mon,Tue,Wed,Thu,Fri,Sat".split(",");
AmCharts.monthNames = "January,February,March,April,May,June,July,August,September,October,November,December".split(",");
AmCharts.shortMonthNames = "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec".split(",");
AmCharts.formatDate = function(a, b) {
    var c = a.getFullYear(),
    d = ("" + c).substr( - 2, 2),
    e = a.getMonth(),
    f = e + 1;
    9 > e && (f = "0" + f);
    var g = a.getDate(),
    h = g;
    10 > g && (h = "0" + g);
    var i = a.getDay(),
    k = "0" + i,
    j = a.getHours(),
    m = j;
    24 == m && (m = 0);
    var n = m;
    10 > n && (n = "0" + n);
    b = b.replace(/JJ/g, n);
    b = b.replace(/J/g, m);
    m = j;
    0 == m && (m = 24);
    n = m;
    10 > n && (n = "0" + n);
    b = b.replace(/HH/g, n);
    b = b.replace(/H/g, m);
    m = j;
    11 < m && (m -= 12);
    n = m;
    10 > n && (n = "0" + n);
    b = b.replace(/KK/g, n);
    b = b.replace(/K/g, m);
    m = j;
    12 < m && (m -= 12);
    n = m;
    10 > n && (n = "0" + n);
    b = b.replace(/LL/g, n);
    b = b.replace(/L/g, m);
    n = m = a.getMinutes();
    10 > n && (n = "0" + n);
    b = b.replace(/NN/g, n);
    b = b.replace(/N/g, m);
    n = m = a.getSeconds();
    10 > n && (n = "0" + n);
    b = b.replace(/SS/g, n);
    b = b.replace(/S/g, m);
    n = m = a.getMilliseconds();
    10 > n && (n = "00" + n);
    100 > n && (n = "0" + n);
    var l = m;
    10 > l && (l = "00" + l);
    b = b.replace(/QQQ/g, n);
    b = b.replace(/QQ/g, l);
    b = b.replace(/Q/g, m);
    b = 12 > j ? b.replace(/A/g, "am") : b.replace(/A/g, "pm");
    b = b.replace(/YYYY/g, "@IIII@");
    b = b.replace(/YY/g, "@II@");
    b = b.replace(/MMMM/g, "@XXXX@");
    b = b.replace(/MMM/g, "@XXX@");
    b = b.replace(/MM/g, "@XX@");
    b = b.replace(/M/g, "@X@");
    b = b.replace(/DD/g, "@RR@");
    b = b.replace(/D/g, "@R@");
    b = b.replace(/EEEE/g, "@PPPP@");
    b = b.replace(/EEE/g, "@PPP@");
    b = b.replace(/EE/g, "@PP@");
    b = b.replace(/E/g, "@P@");
    b = b.replace(/@IIII@/g, c);
    b = b.replace(/@II@/g, d);
    b = b.replace(/@XXXX@/g, AmCharts.monthNames[e]);
    b = b.replace(/@XXX@/g, AmCharts.shortMonthNames[e]);
    b = b.replace(/@XX@/g, f);
    b = b.replace(/@X@/g, e + 1);
    b = b.replace(/@RR@/g, h);
    b = b.replace(/@R@/g, g);
    b = b.replace(/@PPPP@/g, AmCharts.dayNames[i]);
    b = b.replace(/@PPP@/g, AmCharts.shortDayNames[i]);
    b = b.replace(/@PP@/g, k);
    return b = b.replace(/@P@/g, i)
};
AmCharts.findPosX = function(a) {
    for (var b = a.offsetLeft; a = a.offsetParent;) b += a.offsetLeft,
    a != document.body && a != document.documentElement && (b -= a.scrollLeft);
    return b
};
AmCharts.findPosY = function(a) {
    for (var b = a.offsetTop; a = a.offsetParent;) b += a.offsetTop,
    a != document.body && a != document.documentElement && (b -= a.scrollTop);
    return b
};
AmCharts.findIfFixed = function(a) {
    for (; a = a.offsetParent;) if ("fixed" == a.style.position) return ! 0;
    return ! 1
};
AmCharts.formatValue = function(a, b, c, d, e, f, g, h) {
    if (b) {
        void 0 == e && (e = "");
        for (var i = 0; i < c.length; i++) {
            var k = c[i],
            j = b[k];
            void 0 != j && (j = f ? AmCharts.addPrefix(j, h, g, d) : AmCharts.formatNumber(j, d), a = a.replace(RegExp("\\[\\[" + e + "" + k + "\\]\\]", "g"), j))
        }
    }
    return a
};
AmCharts.formatDataContextValue = function(a, b) {
    if (a) for (var c = a.match(/\[\[.*?\]\]/g), d = 0; d < c.length; d++) {
        var e = c[d],
        e = e.substr(2, e.length - 4);
        void 0 != b[e] && (a = a.replace(RegExp("\\[\\[" + e + "\\]\\]", "g"), b[e]))
    }
    return a
};
AmCharts.massReplace = function(a, b) {
    for (var c in b) {
        var d = b[c];
        void 0 == d && (d = "");
        a = a.replace(c, d)
    }
    return a
};
AmCharts.cleanFromEmpty = function(a) {
    return a.replace(/\[\[[^\]]*\]\]/g, "")
};
AmCharts.addPrefix = function(a, b, c, d) {
    var e = AmCharts.formatNumber(a, d),
    f = "",
    g;
    if (0 == a) return "0";
    0 > a && (f = "-");
    a = Math.abs(a);
    if (1 < a) for (g = b.length - 1; - 1 < g; g--) {
        if (a >= b[g].number) {
            a /= b[g].number;
            d = Number(d.precision);
            1 > d && (d = 1);
            a = AmCharts.roundTo(a, d);
            e = f + "" + a + "" + b[g].prefix;
            break
        }
    } else for (g = 0; g < c.length; g++) if (a <= c[g].number) {
        a /= c[g].number;
        d = Math.abs(Math.round(Math.log(a) * Math.LOG10E));
        a = AmCharts.roundTo(a, d);
        e = f + "" + a + "" + c[g].prefix;
        break
    }
    return e
};
AmCharts.removeObject = function(a, b) {
    a && a.node && (a.remove(), b && b.exclude(a))
};
AmCharts.removeSet = function(a) {
    if (a) for (var b = 0; b < a.length; b++) {
        var c = a[b];
        0 < c.length && AmCharts.removeSet(c);
        var d = c.clip,
        e = c.node;
        if (e) {
            if (e.clipRect) d = e.clipRect;
            e.parentNode && c.remove()
        }
        d && (d.parentNode && d.parentNode.removeChild(d), delete d)
    }
};
AmCharts.copyProperties = function(a, b) {
    for (var c in a)"events" != c && void 0 != a[c] && "function" != typeof a[c] && (b[c] = a[c])
};
AmCharts.recommended = function() {
    var a = "js";
    document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") || swfobject && swfobject.hasFlashPlayerVersion("8") && (a = "flash");
    return a
};
AmCharts.Bezier = AmCharts.Class({
    construct: function(a, b, c, d, e, f, g, h, i, k) {
        "object" == typeof g && (g = g[0]);
        "object" == typeof h && (h = h[0]);
        var j = "";
        1 == i && (j = ".");
        1 < i && (j = "-");
        d = {
            stroke: d,
            fill: g,
            "fill-opacity": h,
            "stroke-dasharray": j,
            opacity: e,
            "stroke-width": f
        };
        e = b.length;
        this.lineArray = ["M", b[0], c[0]];
        f = [];
        for (g = 0; g < e; g++) f.push({
            x: b[g],
            y: c[g]
        });
        1 < f.length && this.drawBeziers(this.interpolate(f));
        this.lineArray = this.lineArray.concat(k);
        this.path = a.path(this.lineArray).attr(d)
    },
    interpolate: function(a) {
        var b = [];
        b.push({
            x: a[0].x,
            y: a[0].y
        });
        var c = a[1].x - a[0].x,
        d = a[1].y - a[0].y;
        b.push({
            x: a[0].x + c / 6,
            y: a[0].y + d / 6
        });
        for (var e = 1; e < a.length - 1; e++) {
            var f = a[e - 1],
            g = a[e],
            d = a[e + 1],
            c = d.x - g.x,
            d = d.y - f.y,
            f = g.x - f.x;
            f > c && (f = c);
            b.push({
                x: g.x - f / 3,
                y: g.y - d / 6
            });
            b.push({
                x: g.x,
                y: g.y
            });
            b.push({
                x: g.x + f / 3,
                y: g.y + d / 6
            })
        }
        d = a[a.length - 1].y - a[a.length - 2].y;
        c = a[a.length - 1].x - a[a.length - 2].x;
        b.push({
            x: a[a.length - 1].x - c / 3,
            y: a[a.length - 1].y - d / 6
        });
        b.push({
            x: a[a.length - 1].x,
            y: a[a.length - 1].y
        });
        return b
    },
    drawBeziers: function(a) {
        for (var b = 0; b < (a.length - 1) / 3; b++) this.drawBezierMidpoint(a[3 * b], a[3 * b + 1], a[3 * b + 2], a[3 * b + 3])
    },
    drawBezierMidpoint: function(a, b, c, d) {
        var e = this.getPointOnSegment(a, b, 0.75),
        f = this.getPointOnSegment(d, c, 0.75),
        g = (d.x - a.x) / 16,
        h = (d.y - a.y) / 16,
        a = this.getPointOnSegment(a, b, 0.375),
        b = this.getPointOnSegment(e, f, 0.375);
        b.x -= g;
        b.y -= h;
        var i = this.getPointOnSegment(f, e, 0.375);
        i.x += g;
        i.y += h;
        c = this.getPointOnSegment(d, c, 0.375);
        g = this.getMiddle(a, b);
        e = this.getMiddle(e, f);
        f = this.getMiddle(i, c);
        h = this.lineArray;
        h.push("Q", a.x, a.y, g.x, g.y);
        h.push("Q", b.x, b.y, e.x, e.y);
        h.push("Q", i.x, i.y, f.x, f.y);
        h.push("Q", c.x, c.y, d.x, d.y)
    },
    getMiddle: function(a, b) {
        return {
            x: (a.x + b.x) / 2,
            y: (a.y + b.y) / 2
        }
    },
    getPointOnSegment: function(a, b, c) {
        return {
            x: a.x + (b.x - a.x) * c,
            y: a.y + (b.y - a.y) * c
        }
    }
});
AmCharts.Cuboid = AmCharts.Class({
    construct: function(a, b, c, d, e, f, g, h, i, k, j, m) {
        this.set = a.set();
        this.container = a;
        this.h = c;
        this.w = b;
        this.dx = d;
        this.dy = e;
        this.colors = f;
        this.alpha = g;
        this.bwidth = h;
        this.bcolor = i;
        this.balpha = k;
        if ("object" != typeof f) this.colors = [f];
        0 > b && 0 == j && (j = 180);
        0 > c && 270 == j && (j = 90);
        this.gradientRotation = j;
        if (0 == d && 0 == e) this.cornerRadius = m;
        this.draw()
    },
    draw: function() {
        var a = this.set;
        a.remove();
        var b = this.container,
        c = Math.abs(this.w),
        d = Math.abs(this.h),
        e = this.dx,
        f = this.dy,
        g = this.colors,
        h = this.alpha,
        i = this.bwidth,
        k = this.bcolor,
        j = this.balpha,
        m = this.gradientRotation,
        n = this.cornerRadius;
        if (0 < e || 0 < f) {
            var l = g[g.length - 1];
            0 < d && (l = g[0]);
            l = AmCharts.adjustLuminosity(l, -0.2);
            l = AmCharts.polygon(b, [0, e, c + e, c, 0], [0, f, f, 0, 0], [l], h, 0, 0, 0, m);
            a.push(l);
            l = AmCharts.line(b, [0, e, c + e], [0, f, f], k, j, i);
            a.push(l);
            l = AmCharts.adjustLuminosity(g[0], -0.2);
            if (0 < d && 0 < c) {
                var o = AmCharts.rect(b, c, d, l, h, 0, 0, 0, 0, m);
                a.push(o);
                o.translate(e, -d + f);
                o = AmCharts.line(b, [e, e], [f, -d + f], k, j, i);
                a.push(o);
                o = AmCharts.polygon(b, [0, 0, e, e, 0], [0, -d, -d + f, f, 0], l, h, 0, 0, 0, m);
                a.push(o);
                l = AmCharts.polygon(b, [0, 0, e, e, 0], [0, -d, -d + f, f, 0], l, h, 0, 0, 0, m);
                l.translate(c, 0);
                a.push(l);
                l = AmCharts.line(b, [0, e, e, 0], [ - d, -d + f, f, 0], k, j, i);
                a.push(l);
                l.translate(c, 0)
            }
            l = g[0];
            0 < d && (l = g[g.length - 1]);
            l = AmCharts.adjustLuminosity(l, 0.2);
            l = AmCharts.polygon(b, [0, e, c + e, c, 0], [0, f, f, 0, 0], [l], h, 0, 0, 0, m);
            l.translate(0, -d);
            a.push(l);
            e = AmCharts.line(b, [0, e, c + e], [0, f, f], k, j, i);
            e.translate(0, -d);
            a.push(e)
        }
        1 > d && (d = 1, j = h = 0);
        b = AmCharts.rect(b, c, d, g, h, i, k, j, n, m);
        b.attr({
            y: -d
        });
        a.push(b);
        this.front = b
    },
    y: function(a) {
        var b = this.set;
        0 != this.dx && 0 != this.dy ? 0 < this.h ? b.translate(0, a + this.h) : b.translate(0, a) : 0 > this.h ? this.set.attr({
            y: a + this.h
        }) : this.set.attr({
            y: a
        })
    },
    x: function(a) {
        var b = this.set;
        0 != this.dx && 0 != this.dy ? 0 > this.w ? b.translate(a + this.w, 0) : b.translate(a, 0) : 0 > this.w ? b.attr({
            x: a + this.w
        }) : b.attr({
            x: a
        })
    },
    width: function(a) {
        this.w = a;
        this.draw()
    },
    height: function(a) {
        this.h = a;
        this.draw()
    },
    getX: function() {
        return this.front.getBBox().x
    },
    getY: function() {
        return this.front.getBBox().y
    }
});
AmCharts.AmLegend = AmCharts.Class({
    construct: function() {
        this.createEvents("rollOverMarker", "rollOverItem", "rollOutMarker", "rollOutItem", "showItem", "hideItem", "clickMarker", "rollOverItem", "rollOutItem", "clickLabel");
        this.position = "bottom";
        this.borderColor = this.color = "#000000";
        this.borderAlpha = 0;
        this.markerLabelGap = 5;
        this.verticalGap = 10;
        this.align = "left";
        this.horizontalGap = 0;
        this.spacing = 10;
        this.markerDisabledColor = "#AAB3B3";
        this.markerType = "square";
        this.markerSize = 16;
        this.markerBorderAlpha = 0;
        this.markerBorderThickness = 1;
        this.marginBottom = this.marginTop = 0;
        this.marginLeft = this.marginRight = 20;
        this.autoMargins = !0;
        this.valueWidth = 50;
        this.switchable = !0;
        this.switchType = "x";
        this.switchColor = "#FFFFFF";
        this.rollOverColor = "#CC0000";
        this.reversedOrder = !1;
        this.labelText = "[[title]]";
        this.valueText = "[[value]]";
        this.useMarkerColorForLabels = !1;
        this.rollOverGraphAlpha = 1;
        this.textClickEnabled = !1;
        this.equalWidths = !0;
        this.dateFormat = "DD-MM-YYYY"
    },
    setData: function(a) {
        this.data = a;
        this.invalidateSize()
    },
    invalidateSize: function() {
        this.destroy();
        this.entries = [];
        this.valueLabels = [];
        var a = this.data;
        a && 0 < a.length && this.drawLegend();
        this.container && this.container.renderfix()
    },
    drawLegend: function() {
        var a = this.chart,
        b = this.position,
        c = this.width,
        d = a.realWidth,
        e = a.realHeight,
        f = this.div,
        g = this.data;
        if ("right" == b || "left" == b) this.maxColumns = 1,
        this.marginLeft = this.marginRight = 10;
        else if (this.autoMargins) this.marginRight = a.marginRight,
        this.marginLeft = a.marginLeft,
        "bottom" == b ? (this.marginBottom = a.autoMarginOffset, this.marginTop = 0) : (this.marginTop = a.autoMarginOffset, this.marginBottom = 0);
        this.divWidth = b = void 0 != c ? AmCharts.toCoordinate(c, d) : a.realWidth;
        f.style.width = b + "px";
        this.container ? this.container.setSize(b, e) : this.container = Raphael(f, b, e);
        this.lx = 0;
        this.ly = 8;
        e = this.markerSize;
        if (0 < e) this.lx += e + this.markerLabelGap,
        this.ly = e / 2;
        this.titleWidth = 0;
        if (e = this.title) f = a.fontSize,
        this.titleWidth = AmCharts.text(this.container, 0, this.marginTop + f / 2 + this.ly, e, {
            fill: this.color,
            "text-anchor": "start",
            "font-weight": "bold",
            "font-family": a.fontFamily,
            "font-size": f
        }).getBBox().width + 15;
        for (a = this.index = this.maxLabelWidth = 0; a < g.length; a++) this.createEntry(g[a]);
        for (a = this.index = 0; a < g.length; a++) this.createValue(g[a]);
        this.arrangeEntries();
        this.updateValues()
    },
    arrangeEntries: function() {
        var a = this.position,
        b = this.marginLeft + this.titleWidth,
        c = this.marginRight,
        d = this.marginTop,
        e = this.marginBottom,
        f = this.horizontalGap,
        g = this.div,
        h = this.divWidth,
        i = this.maxColumns,
        k = this.verticalGap,
        j = this.spacing,
        m = h - c - b,
        n = 0,
        l = 0,
        o = this.container.set();
        this.set = o;
        for (var r = this.entries,
        p = 0; p < r.length; p++) {
            var q = r[p].getBBox(),
            u = q.width;
            u > n && (n = u);
            q = q.height;
            q > l && (l = q)
        }
        for (var x = u = 0,
        A = f,
        p = 0; p < r.length; p++) {
            var I = r[p];
            this.reversedOrder && (I = r[r.length - p - 1]);
            var q = I.getBBox(),
            s;
            this.equalWidths ? s = f + x * (n + j + this.markerLabelGap) : (s = A, A = A + q.width + f + j);
            s + q.width > m && 0 < p && (u++, x = 0, s = f, A = s + q.width + f + j);
            I.translate(s + "," + (k + (l + k) * u));
            x++; ! isNaN(i) && x >= i && (x = 0, u++);
            o.push(I)
        }
        q = o.getBBox();
        i = q.height + 2 * k - 1;
        "left" == a || "right" == a ? (f = q.width + 2 * f, g.style.width = f + b + c + "px") : f = h - b - c - 1;
        c = AmCharts.rect(this.container, f, i, this.backgroundColor, this.backgroundAlpha, 1, this.borderColor, this.borderAlpha);
        c.toBack();
        o.push(c);
        o.translate(b + "," + d);
        if ("top" == a || "bottom" == a) o.pop(),
        "center" == this.align && o.translate((f - q.width) / 2 + ",0"),
        "right" == this.align && o.translate(f - q.width + ",0");
        a = i + d + e + 1;
        0 > a && (a = 0);
        g.style.height = a + "px"
    },
    createEntry: function(a) {
        if (!1 !== a.visibleInLegend) {
            var b = this,
            c = b.chart,
            d = a.markerType;
            if (!d) d = b.markerType;
            var e = a.color,
            f = a.alpha;
            a.legendKeyColor && (e = a.legendKeyColor());
            a.legendKeyAlpha && (f = a.legendKeyAlpha());
            if (!0 == a.hidden) e = b.markerDisabledColor;
            if (d = b.createMarker(d, e, f)) if (0 < d.length) for (f = 0; f < d.length; f++) d[f].dItem = a;
            else d.dItem = a;
            if (f = b.switchType) var g = "x" == f ? b.createX() : b.createV();
            g.dItem = a; ! 0 != a.hidden ? "x" == f ? g.hide() : g.show() : "x" != f && g.hide();
            b.switchable || g.hide();
            f = b.container.set([d, g]);
            c.touchEventsEnabled ? (f.touchend(function() {
                b.clickMarker(this.dItem)
            }), f.touchstart(function() {
                b.rollOverMarker(this.dItem)
            })) : f.hover(function() {
                b.rollOverMarker(this.dItem)
            },
            function() {
                b.rollOutMarker(this.dItem)
            }).click(function() {
                b.clickMarker(this.dItem)
            });
            c = b.color;
            if (a.showBalloon && b.textClickEnabled && void 0 != b.selectedColor) c = b.selectedColor;
            b.useMarkerColorForLabels && (c = e);
            if (!0 == a.hidden) c = b.markerDisabledColor;
            e = b.chart.fontSize;
            if (!isNaN(b.fontSize)) e = b.fontSize;
            if (f = AmCharts.massReplace(b.labelText, {
                "[[title]]": a.title
            })) {
                var h = AmCharts.text(b.container, b.lx, b.ly, f, {
                    fill: c,
                    "text-anchor": "start",
                    "font-family": b.chart.fontFamily,
                    "font-size": e
                }),
                e = h.getBBox().width;
                if (b.maxLabelWidth < e) b.maxLabelWidth = e
            }
            e = b.container.set();
            d && e.push(d);
            g && e.push(g);
            h && e.push(h);
            b.entries[b.index] = e;
            a.legendEntry = b.entries[b.index];
            a.legendLabel = h;
            a.legendSwitch = g;
            b.index++
        }
    },
    rollOverMarker: function(a) {
        this.switchable && this.dispatch("rollOverMarker", a);
        this.dispatch("rollOverItem", a)
    },
    rollOutMarker: function(a) {
        this.switchable && this.dispatch("rollOutMarker", a);
        this.dispatch("rollOutItem", a)
    },
    clickMarker: function(a) {
        this.switchable ? !0 == a.hidden ? this.dispatch("showItem", a) : this.dispatch("hideItem", a) : this.dispatch("clickMarker", a)
    },
    rollOverLabel: function(a) {
        a.hidden || (this.textClickEnabled && a.legendLabel && a.legendLabel.attr({
            fill: this.rollOverColor
        }), this.dispatch("rollOverItem", a))
    },
    rollOutLabel: function(a) {
        if (!a.hidden) {
            if (this.textClickEnabled && a.legendLabel) {
                var b = this.color;
                if (void 0 != this.selectedColor && a.showBalloon) b = this.selectedColor;
                if (this.useMarkerColorForLabels && (b = a.lineColor, void 0 == b)) b = a.color;
                a.legendLabel.attr({
                    fill: b
                })
            }
            this.dispatch("rollOutItem", a)
        }
    },
    clickLabel: function(a) {
        this.textClickEnabled ? a.hidden || this.dispatch("clickLabel", a) : this.switchable && (!0 == a.hidden ? this.dispatch("showItem", a) : this.dispatch("hideItem", a))
    },
    dispatch: function(a, b) {
        this.fire(a, {
            type: a,
            dataItem: b
        })
    },
    createValue: function(a) {
        var b = this;
        if (!1 !== a.visibleInLegend) {
            var c = b.maxLabelWidth;
            if (!b.equalWidths) b.valueAlign = "left";
            if ("left" == b.valueAlign) c = a.legendEntry.getBBox().width;
            var d = c;
            if (b.valueText) {
                var e = b.color;
                if (b.useMarkerColorForLabels) e = a.color;
                if (!0 == a.hidden) e = b.markerDisabledColor;
                var f = b.chart.fontSize;
                if (isNaN(b.fontSize)) f = b.fontSize;
                var g = b.valueText,
                c = c + b.lx + b.markerLabelGap + b.valueWidth,
                h = "end";
                "left" == b.valueAlign && (c -= b.valueWidth, h = "start");
                e = AmCharts.text(b.container, c, b.ly, g, {
                    fill: e,
                    "text-anchor": h,
                    "font-family": b.chart.fontFamily,
                    "font-size": f
                });
                b.entries[b.index].push(e);
                d += b.valueWidth + b.markerLabelGap;
                e.dItem = a;
                b.valueLabels.push(e)
            }
            b.index++;
            d = b.container.rect(b.markerSize + b.markerLabelGap, 0, d, b.markerSize).attr({
                stroke: "none",
                fill: "#FFCCFF",
                "fill-opacity": 0
            });
            d.dItem = a;
            b.entries[b.index - 1].push(d);
            d.mouseover(function() {
                b.rollOverLabel(this.dItem)
            }).mouseout(function() {
                b.rollOutLabel(this.dItem)
            }).click(function() {
                b.clickLabel(this.dItem)
            })
        }
    },
    createV: function() {
        var a = this.markerSize;
        return this.container.path(["M", a / 5, a / 3, "L", a / 2, a - a / 5, "L", a - a / 5, a / 5, "L", a / 2, a / 1.7, "Z"]).attr({
            fill: this.switchColor,
            stroke: this.switchColor
        })
    },
    createX: function() {
        var a = this.markerSize - 3;
        return this.container.path(["M", 3, 3, "L", a, a, "M", a, 3, "L", 3, a]).attr({
            stroke: this.switchColor,
            "stroke-width": 3
        })
    },
    createMarker: function(a, b, c) {
        var d = this.markerSize,
        e = this.container,
        f, g = this.markerBorderColor;
        g || (g = b);
        c = {
            fill: b,
            stroke: g,
            opacity: c,
            "stroke-opacity": this.markerBorderAlpha,
            "stroke-width": this.markerBorderThickness
        };
        switch (a) {
        case "square":
            f = e.rect(0, 0, d, d).attr(c);
            break;
        case "circle":
            f = e.circle(d / 2, d / 2, d / 2).attr(c);
            break;
        case "line":
            f = e.path(["M", 0, d / 2, "L", d, d / 2]).attr({
                stroke: b,
                "stroke-width": this.markerBorderThickness
            });
            break;
        case "dashedLine":
            f = e.path(["M", 0, d / 2, "L", d / 2 - 2, d / 2, "M", d / 2 + 2, d / 2, "L", d, d / 2]).attr({
                stroke: b,
                "stroke-width": this.markerBorderThickness
            });
            break;
        case "triangleUp":
            f = e.path(["M", 0, d, "L", d / 2, 0, "L", d, d, "L", 0, d, "Z"]).attr(c);
            break;
        case "triangleDown":
            f = e.path(["M", 0, 0, "L", d / 2, d, "L", d, 0, "L", 0, 0, "Z"]).attr(c);
            break;
        case "bubble":
            c.fill = NaN,
            c.gradient = "r" + b + "-" + AmCharts.adjustLuminosity(b, -0.4),
            f = e.circle(d / 2, d / 2, d / 2).attr(c)
        }
        return f
    },
    validateNow: function() {
        this.invalidateSize()
    },
    updateValues: function() {
        for (var a = this.valueLabels,
        b = this.chart,
        c = 0; c < a.length; c++) {
            var d = a[c],
            e = d.dItem;
            if (void 0 != e.type) {
                var f = e.currentDataItem;
                if (f) {
                    var g = this.valueText;
                    if (e.legendValueText) g = e.legendValueText;
                    e = g;
                    e = b.formatString(e, f);
                    d.attr({
                        text: e
                    })
                } else d.attr({
                    text: " "
                })
            } else f = b.formatString(this.valueText, e),
            d.attr({
                text: f
            })
        }
    },
    destroy: function() {
        var a = this.container;
        a && a.clear()
    }
});
AmCharts.AmBalloon = AmCharts.Class({
    construct: function() {
        this.enabled = !0;
        this.fillColor = "#CC0000";
        this.fillAlpha = 1;
        this.borderThickness = 2;
        this.borderColor = "#FFFFFF";
        this.borderAlpha = 1;
        this.cornerRadius = 6;
        this.maximumWidth = 220;
        this.horizontalPadding = 8;
        this.verticalPadding = 5;
        this.pointerWidth = 10;
        this.pointerOrientation = "vertical";
        this.color = "#FFFFFF";
        this.textShadowColor = "#000000";
        this.adjustBorderColor = !1;
        this.showBullet = !0;
        this.show = this.follow = !1;
        this.bulletSize = 3
    },
    draw: function() {
        var a = this.pointToX,
        b = this.pointToY;
        if (!isNaN(a)) {
            var c = this.chart,
            d = c.container;
            AmCharts.removeSet(this.set);
            var e = d.set();
            this.set = e;
            if (this.show) {
                var f = this.l,
                g = this.t,
                h = this.r,
                i = this.b,
                k = this.textShadowColor;
                this.color == k && (k = null);
                var j = this.balloonColor,
                m = this.fillColor,
                n = this.borderColor;
                void 0 != j && (this.adjustBorderColor ? n = j: m = j);
                var l = this.horizontalPadding,
                o = this.verticalPadding,
                j = this.pointerWidth,
                r = this.pointerOrientation,
                p = this.cornerRadius,
                q = c.fontFamily,
                u = this.fontSize;
                if (void 0 == u) u = c.fontSize;
                c = AmCharts.text(d, 0, 0, this.text, {
                    fill: this.color,
                    "font-family": q,
                    "font-size": u
                });
                e.push(c);
                if (void 0 != k) {
                    var x = AmCharts.text(d, 1, 1, this.text, {
                        fill: k,
                        opacity: 0.4,
                        "font-family": q,
                        "font-size": u
                    });
                    e.push(x)
                }
                k = c.getBBox();
                e = k.height + 2 * o;
                o = k.width + 2 * l;
                window.opera && (e += 6);
                c.translate(o / 2 + "," + e / 2);
                x && x.translate(o / 2 + "," + e / 2);
                "horizontal" != r ? (l = a - o / 2, k = b < g + e + 10 && "down" != r ? b + j: b - e - j) : (2 * j > e && (j = e / 2), k = b - e / 2, l = a < f + (h - f) / 2 ? a + j: a - o - j);
                k + e >= i && (k = i - e);
                k < g && (k = g);
                l < f && (l = f);
                l + o > h && (l = h - o);
                if (0 < p) {
                    if (n = AmCharts.rect(d, o, e, [m], [this.fillAlpha], this.borderThickness, n, this.borderAlpha, this.cornerRadius), this.showBullet) {
                        var A = AmCharts.circle(d, this.bulletSize, m, this.fillAlpha);
                        A.translate(a + "," + b)
                    }
                } else i = [],
                p = [],
                "horizontal" != r ? (f = a - l, f > o - j && (f = o - j), f < j && (f = j), i = [0, f - j, a - l, f + j, o, o, 0, 0], p = b < g + e + 10 && "down" != r ? [0, 0, b - k + 3, 0, 0, e, e, 0] : [e, e, b - k - 3, e, e, 0, 0, e]) : (g = b - k, g > e - j && (g = e - j), g < j && (g = j), p = [0, g - j, b - k, g + j, e, e, 0, 0], i = a < f + (h - f) / 2 ? [0, 0, a - l, 0, 0, o, o, 0] : [o, o, a - l, o, o, 0, 0, o]),
                n = AmCharts.polygon(d, i, p, m, this.fillAlpha, this.borderThickness, n, this.borderAlpha);
                this.set.push(n);
                n.toFront();
                x && x.toFront();
                c.toFront();
                this.set.translate(l + "," + k);
                k = n.getBBox();
                this.bottom = k.y + k.height;
                this.yPos = k.y;
                A && this.set.push(A)
            }
        }
    },
    followMouse: function() {
        if (this.follow && this.show) {
            var a = this.chart.mouseX,
            b = this.chart.mouseY;
            this.pointToX = a;
            this.pointToY = b;
            if (a != this.previousX || b != this.previousY) if (this.previousX = a, this.previousY = b, 0 == this.cornerRadius) this.draw();
            else {
                var c = this.set;
                if (c) {
                    var d = c.getBBox(),
                    a = a - d.width / 2,
                    e = b - d.height - 10;
                    if (a < this.l) a = this.l;
                    a > this.r - d.width && (a = this.r - d.width);
                    e < this.t && (e = b + 10);
                    c.translate(a - d.x + "," + (e - d.y))
                }
            }
        }
    },
    changeColor: function(a) {
        this.balloonColor = a
    },
    setBounds: function(a, b, c, d) {
        this.l = a;
        this.t = b;
        this.r = c;
        this.b = d
    },
    showBalloon: function(a) {
        this.text = a;
        this.show = !0;
        this.draw()
    },
    hide: function() {
        this.follow = this.show = !1;
        this.destroy()
    },
    setPosition: function(a, b, c) {
        this.pointToX = a;
        this.pointToY = b;
        c && (a != this.previousX || b != this.previousY) && this.draw();
        this.previousX = a;
        this.previousY = b
    },
    followCursor: function(a) {
        var b = this;
        if (b.follow = a) b.pShowBullet = b.showBullet,
        b.showBullet = !1;
        else if (void 0 != b.pShowBullet) b.showBullet = b.pShowBullet;
        clearInterval(b.interval);
        var c = b.chart.mouseX,
        d = b.chart.mouseY;
        if (!isNaN(c) && a) b.pointToX = c,
        b.pointToY = d,
        b.interval = setInterval(function() {
            b.followMouse.call(b)
        },
        20)
    },
    destroy: function() {
        clearInterval(this.interval);
        AmCharts.removeSet(this.set)
    }
});
AmCharts.AmCoordinateChart = AmCharts.Class({
    inherits: AmCharts.AmChart,
    construct: function() {
        AmCharts.AmCoordinateChart.base.construct.call(this);
        this.createEvents("rollOverGraphItem", "rollOutGraphItem", "clickGraphItem", "doubleClickGraphItem");
        this.plotAreaFillColors = "#FFFFFF";
        this.plotAreaFillAlphas = 0;
        this.plotAreaBorderColor = "#000000";
        this.startDuration = this.startAlpha = this.plotAreaBorderAlpha = 0;
        this.startEffect = "elastic";
        this.sequencedAnimation = !0;
        this.colors = "#FF6600,#FCD202,#B0DE09,#0D8ECF,#2A0CD0,#CD0D74,#CC0000,#00CC00,#0000CC,#DDDDDD,#999999,#333333,#990000".split(",");
        this.balloonDateFormat = "MMM DD, YYYY";
        this.valueAxes = [];
        this.graphs = []
    },
    initChart: function() {
        AmCharts.AmCoordinateChart.base.initChart.call(this);
        this.createValueAxes();
        var a = this.legend;
        a && a.setData(this.graphs)
    },
    createValueAxes: function() {
        0 == this.valueAxes.length && this.addValueAxis(new AmCharts.ValueAxis)
    },
    parseData: function() {
        this.processValueAxes();
        this.processGraphs()
    },
    parseSerialData: function() {
        AmCharts.AmSerialChart.base.parseData.call(this);
        var a = this.graphs,
        b = this.seriesIdField;
        if (!b) b = this.categoryField;
        this.chartData = [];
        var c = this.dataProvider;
        if (c) {
            var d = !1;
            if (this.categoryAxis) d = this.categoryAxis.parseDates;
            if (d) var e = AmCharts.extractPeriod(this.categoryAxis.minPeriod),
            f = e.period,
            e = e.count;
            var g = {};
            this.lookupTable = g;
            for (var h = 0; h < c.length; h++) {
                var i = {},
                k = c[h],
                j = k[this.categoryField];
                i.category = j;
                g[k[b]] = i;
                if (d) j = new Date(j),
                j = AmCharts.resetDateToMin(j, f, e),
                i.category = j,
                i.time = j.getTime();
                var m = this.valueAxes;
                i.axes = {};
                i.x = {};
                for (var n = 0; n < m.length; n++) {
                    var l = m[n].id;
                    i.axes[l] = {};
                    i.axes[l].graphs = {};
                    for (var o = 0; o < a.length; o++) {
                        var j = a[o],
                        r = j.id,
                        p = j.periodValue;
                        if (j.valueAxis.id == l) {
                            i.axes[l].graphs[r] = {};
                            var q = {};
                            q.index = h;
                            q.values = this.processValues(k, j, p);
                            this.processFields(j, q, k);
                            q.category = i.category;
                            q.serialDataItem = i;
                            q.graph = j;
                            i.axes[l].graphs[r] = q
                        }
                    }
                }
                this.chartData[h] = i
            }
        }
        for (b = 0; b < a.length; b++) j = a[b],
        j.dataProvider && this.parseGraphData(j)
    },
    processValues: function(a, b, c) {
        var d = {},
        e = Number(a[b.valueField + c]);
        if (!isNaN(e)) d.value = e;
        e = Number(a[b.openField + c]);
        if (!isNaN(e)) d.open = e;
        e = Number(a[b.closeField + c]);
        if (!isNaN(e)) d.close = e;
        e = Number(a[b.lowField + c]);
        if (!isNaN(e)) d.low = e;
        e = Number(a[b.highField + c]);
        if (!isNaN(e)) d.high = e;
        return d
    },
    parseGraphData: function(a) {
        var b = a.dataProvider,
        c = a.seriesIdField;
        if (!c) c = this.seriesIdField;
        if (!c) c = this.categoryField;
        for (var d = 0; d < b.length; d++) {
            var e = b[d],
            f = this.lookupTable["" + e[c]],
            g = a.valueAxis.id;
            if (f) g = f.axes[g].graphs[a.id],
            g.serialDataItem = f,
            g.values = this.processValues(e, a, a.periodValue),
            this.processFields(a, g, e)
        }
    },
    addValueAxis: function(a) {
        a.chart = this;
        this.valueAxes.push(a);
        this.validateData()
    },
    removeValueAxesAndGraphs: function() {
        for (var a = this.valueAxes,
        b = a.length - 1; - 1 < b; b--) this.removeValueAxis(a[b])
    },
    removeValueAxis: function(a) {
        var b = this.graphs,
        c;
        for (c = b.length - 1; 0 <= c; c--) {
            var d = b[c];
            d && d.valueAxis == a && this.removeGraph(d)
        }
        b = this.valueAxes;
        for (c = b.length - 1; 0 <= c; c--) b[c] == a && b.splice(c, 1);
        this.validateData()
    },
    addGraph: function(a) {
        this.graphs.push(a);
        this.chooseGraphColor(a, this.graphs.length - 1);
        this.validateData()
    },
    removeGraph: function(a) {
        for (var b = this.graphs,
        c = b.length - 1; 0 <= c; c--) b[c] == a && (b.splice(c, 1), a.destroy());
        this.validateData()
    },
    processValueAxes: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.chart = this;
            if (!c.id) c.id = "valueAxis" + b;
            if (!0 === this.usePrefixes || !1 === this.usePrefixes) c.usePrefixes = this.usePrefixes
        }
    },
    processGraphs: function() {
        for (var a = this.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.chart = this;
            if (!c.valueAxis) c.valueAxis = this.valueAxes[0];
            if (!c.id) c.id = "graph" + b
        }
    },
    formatString: function(a, b) {
        var c = b.graph,
        d = c.valueAxis;
        d.duration && b.values.value && (d = AmCharts.formatDuration(b.values.value, d.duration, "", d.durationUnits, d.maxInterval, d.numberFormatter), a = a.split("[[value]]").join(d));
        a = AmCharts.massReplace(a, {
            "[[title]]": c.title,
            "[[description]]": b.description,
            "<br>": "\n"
        });
        return a = AmCharts.cleanFromEmpty(a)
    },
    getBalloonColor: function(a, b) {
        var c = a.lineColor,
        d = a.balloonColor,
        e = a.fillColors;
        "object" == typeof e ? c = e[0] : void 0 != e && (c = e);
        if (b.isNegative) {
            var e = a.negativeLineColor,
            f = a.negativeFillColors;
            "object" == typeof f ? e = f[0] : void 0 != f && (e = f);
            void 0 != e && (c = e)
        }
        if (void 0 != b.color) c = b.color;
        void 0 == d && (d = c);
        return d
    },
    getGraphById: function(a) {
        for (var b, c = this.graphs,
        d = 0; d < c.length; d++) {
            var e = c[d];
            e.id == a && (b = e)
        }
        return b
    },
    processFields: function(a, b, c) {
        if (a.itemColors) {
            var d = a.itemColors,
            e = b.index;
            b.color = e < d.length ? d[e] : AmCharts.randomColor()
        }
        d = "color,alpha,fillColors,description,bullet,customBullet,bulletSize,bulletConfig,url".split(",");
        for (e = 0; e < d.length; e++) {
            var f = d[e],
            g = a[f + "Field"];
            g && (g = c[g], AmCharts.isDefined(g) && (b[f] = g))
        }
        b.dataContext = c
    },
    chooseGraphColor: function(a, b) {
        if (void 0 == a.lineColor) {
            var c;
            c = this.colors.length > b ? this.colors[b] : AmCharts.randomColor();
            a.lineColor = c
        }
    },
    handleLegendEvent: function(a) {
        var b = a.type;
        if (a = a.dataItem) {
            var c = a.hidden,
            d = a.showBalloon;
            switch (b) {
            case "clickMarker":
                d ? this.hideGraphsBalloon(a) : this.showGraphsBalloon(a);
                break;
            case "clickLabel":
                d ? this.hideGraphsBalloon(a) : this.showGraphsBalloon(a);
                break;
            case "rollOverItem":
                c || this.highlightGraph(a);
                break;
            case "rollOutItem":
                c || this.unhighlightGraph();
                break;
            case "hideItem":
                this.hideGraph(a);
                break;
            case "showItem":
                this.showGraph(a)
            }
        }
    },
    highlightGraph: function(a) {
        var b = this.graphs,
        c, d = 0.2;
        if (this.legend) d = this.legend.rollOverGraphAlpha;
        for (c = 0; c < b.length; c++) {
            var e = b[c];
            e != a && e.changeOpacity(d)
        }
    },
    unhighlightGraph: function() {
        for (var a = this.graphs,
        b = 0; b < a.length; b++) a[b].changeOpacity(1)
    },
    showGraph: function(a) {
        a.hidden = !1;
        this.initChart()
    },
    hideGraph: function(a) {
        a.hidden = !0;
        this.initChart()
    },
    hideGraphsBalloon: function(a) {
        a.showBalloon = !1;
        this.updateLegend()
    },
    showGraphsBalloon: function(a) {
        a.showBalloon = !0;
        this.updateLegend()
    },
    updateLegend: function() {
        this.legend && this.legend.invalidateSize()
    },
    animateAgain: function() {
        var a = this.graphs;
        if (a) for (var b = 0; b < a.length; b++) a[b].animationPlayed = !1
    }
});
AmCharts.AmRectangularChart = AmCharts.Class({
    inherits: AmCharts.AmCoordinateChart,
    construct: function() {
        AmCharts.AmRectangularChart.base.construct.call(this);
        this.createEvents("zoomed");
        this.marginRight = this.marginBottom = this.marginTop = this.marginLeft = 20;
        this.verticalPosition = this.horizontalPosition = this.depth3D = this.angle = 0;
        this.heightMultiplyer = this.widthMultiplyer = 1;
        this.zoomOutText = "Show all";
        this.zoomOutButton = {
            backgroundColor: "#b2e1ff",
            backgroundAlpha: 1
        };
        this.trendLines = [];
        this.autoMargins = !0;
        this.marginsUpdated = !1;
        this.autoMarginOffset = 10
    },
    initChart: function() {
        AmCharts.AmRectangularChart.base.initChart.call(this);
        this.updateDxy(); ! this.marginsUpdated && this.autoMargins && this.resetMargins();
        this.updateMargins();
        this.updatePlotArea();
        this.updateScrollbars();
        this.updateTrendLines();
        this.updateChartCursor();
        this.updateValueAxes();
        this.scrollbarOnly || this.updateGraphs()
    },
    drawChart: function() {
        AmCharts.AmRectangularChart.base.drawChart.call(this);
        this.drawPlotArea();
        var a = this.chartData;
        if (a && 0 < a.length)(a = this.chartCursor) && a.draw(),
        a = this.zoomOutText,
        "" != a && a && this.drawZoomOutButton()
    },
    resetMargins: function() {
        var a = {};
        if ("serial" == this.chartType) {
            for (var b = this.valueAxes,
            c = 0; c < b.length; c++) {
                var d = b[c];
                d.ignoreAxisWidth || (d.setOrientation(this.rotate), d.fixAxisPosition(), a[d.position] = !0)
            }
            if ((c = this.categoryAxis) && !c.ignoreAxisWidth) c.setOrientation(!this.rotate),
            c.fixAxisPosition(),
            c.fixAxisPosition(),
            a[c.position] = !0
        } else {
            d = this.xAxes;
            b = this.yAxes;
            for (c = 0; c < d.length; c++) {
                var e = d[c];
                e.ignoreAxisWidth || (e.setOrientation(!0), e.fixAxisPosition(), a[e.position] = !0)
            }
            for (c = 0; c < b.length; c++) d = b[c],
            d.ignoreAxisWidth || (d.setOrientation(!1), d.fixAxisPosition(), a[d.position] = !0)
        }
        if (a.left) this.marginLeft = 0;
        if (a.right) this.marginRight = 0;
        if (a.top) this.marginTop = 0;
        if (a.bottom) this.marginBottom = 0;
        this.fixMargins = a
    },
    measureMargins: function() {
        var a = this.valueAxes,
        b, c = this.autoMarginOffset,
        d = this.fixMargins,
        e = this.realWidth,
        f = this.realHeight,
        g = c,
        h = c,
        i = e - c;
        b = f - c;
        for (var k = 0; k < a.length; k++) b = this.getAxisBounds(a[k], g, i, h, b),
        g = b.l,
        i = b.r,
        h = b.t,
        b = b.b;
        if (a = this.categoryAxis) b = this.getAxisBounds(a, g, i, h, b),
        g = b.l,
        i = b.r,
        h = b.t,
        b = b.b;
        if (d.left && g < c) this.marginLeft = Math.round( - g + c);
        if (d.right && i > e - c) this.marginRight = Math.round(i - e + c);
        if (d.top && h < c) this.marginTop = Math.round(this.marginTop - h + c + this.titleHeight);
        if (d.bottom && b > f - c) this.marginBottom = Math.round(b - f + c);
        this.animateAgain();
        this.initChart()
    },
    getAxisBounds: function(a, b, c, d, e) {
        if (!a.ignoreAxisWidth) {
            var f = a.set;
            if (f) switch (f = f.getBBox(), a.position) {
            case "top":
                a = f.y;
                d > a && (d = a);
                break;
            case "bottom":
                a = f.y + f.height;
                e < a && (e = a);
                break;
            case "right":
                a = f.x + f.width;
                c < a && (c = a);
                break;
            case "left":
                a = f.x,
                b > a && (b = a)
            }
        }
        return {
            l: b,
            t: d,
            r: c,
            b: e
        }
    },
    drawZoomOutButton: function() {
        var a = this,
        b = a.container.set(),
        c = a.color,
        d = a.fontSize,
        e = a.zoomOutButton;
        if (e) {
            if (e.fontSize) d = e.fontSize;
            if (e.color) c = e.color
        }
        c = AmCharts.text(a.container, 29, 8, a.zoomOutText, {
            fill: c,
            "font-family": a.fontFamily,
            "font-size": d,
            "text-anchor": "start"
        });
        d = c.getBBox();
        c.translate("0," + d.height / 2);
        e = AmCharts.rect(a.container, d.width + 40, d.height + 15, [e.backgroundColor], [e.backgroundAlpha]);
        b.push(e);
        if (void 0 != a.pathToImages) {
            var f = a.container.image(a.pathToImages + "lens.png", 7, 7, 16, 16);
            f.translate("0," + (d.height / 2 - 6));
            f.toFront();
            b.push(f)
        }
        c.toFront();
        e.hide();
        a.zoomOutButtonBG = e;
        b.push(c);
        a.set.push(b);
        e = b.getBBox();
        b.translate(a.marginLeftReal + a.plotAreaWidth - e.width + "," + a.marginTopReal);
        b.hide();
        a.touchEventsEnabled && b.touchstart(function() {
            a.rollOverZB()
        }).touchend(function() {
            a.clickZB()
        });
        b.mouseover(function() {
            a.rollOverZB()
        }).mouseout(function() {
            a.rollOutZB()
        }).click(function() {
            a.clickZB()
        });
        for (e = 0; e < b.length; e++) b[e].attr({
            cursor: "pointer"
        });
        a.zoomOutButtonSet = b
    },
    rollOverZB: function() {
        this.zoomOutButtonBG.show()
    },
    rollOutZB: function() {
        this.zoomOutButtonBG.hide()
    },
    clickZB: function() {
        this.zoomOut()
    },
    zoomOut: function() {
        this.updateScrollbar = !0;
        this.zoom()
    },
    drawPlotArea: function() {
        var a = this.dx,
        b = this.dy,
        c = this.marginLeftReal,
        d = this.marginTopReal,
        e = this.plotAreaWidth,
        f = this.plotAreaHeight,
        g = AmCharts.toSvgColor(this.plotAreaFillColors),
        h = this.plotAreaFillAlphas;
        "object" == typeof h && (h = h[0]);
        g = AmCharts.rect(this.container, e, f, this.plotAreaFillColors, h, 1, this.plotAreaBorderColor, this.plotAreaBorderAlpha);
        g.translate(c + "," + d);
        this.set.push(g);
        if (0 != a && 0 != b) g.translate(a + "," + b),
        g = this.plotAreaFillColors,
        "object" == typeof g && (g = g[0]),
        g = AmCharts.adjustLuminosity(g, -0.15),
        h = {
            fill: g,
            "fill-opacity": h,
            stroke: this.plotAreaBorderColor,
            "stroke-opacity": this.plotAreaBorderAlpha
        },
        e = this.container.path(["M", 0, 0, "L", a, b, "L", e + a, b, "L", e, 0, "L", 0, 0, "Z"]).attr(h),
        e.translate(c + "," + (d + f)),
        this.set.push(e),
        a = this.container.path(["M", 0, 0, "L", 0, f, "L", a, f + b, "L", a, b, "L", 0, 0, "Z"]).attr(h),
        a.translate(c + "," + d),
        this.set.push(a)
    },
    updatePlotArea: function() {
        this.realWidth = this.updateWidth() - 1;
        this.realHeight = this.updateHeight() - 1;
        var a = this.realWidth - this.marginLeftReal - this.marginRightReal - this.dx,
        b = this.realHeight - this.marginTopReal - this.marginBottomReal;
        1 > a && (a = 1);
        1 > b && (b = 1);
        this.plotAreaWidth = Math.round(a);
        this.plotAreaHeight = Math.round(b)
    },
    updateDxy: function() {
        this.dx = this.depth3D * Math.cos(this.angle * Math.PI / 180);
        this.dy = -this.depth3D * Math.sin(this.angle * Math.PI / 180)
    },
    updateMargins: function() {
        var a = this.getTitleHeight();
        this.titleHeight = a;
        this.marginTopReal = this.marginTop - this.dy + a;
        this.marginBottomReal = this.marginBottom;
        this.marginLeftReal = this.marginLeft;
        this.marginRightReal = this.marginRight
    },
    updateValueAxes: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.axisRenderer = AmCharts.RectangularAxisRenderer;
            c.guideFillRenderer = AmCharts.RectangularAxisGuideFillRenderer;
            c.axisItemRenderer = AmCharts.RectangularAxisItemRenderer;
            c.dx = this.dx;
            c.dy = this.dy;
            c.visibleAxisWidth = this.plotAreaWidth;
            c.visibleAxisHeight = this.plotAreaHeight;
            c.visibleAxisX = this.marginLeftReal;
            c.visibleAxisY = this.marginTopReal;
            this.updateObjectSize(c)
        }
    },
    updateObjectSize: function(a) {
        a.width = this.plotAreaWidth * this.widthMultiplyer;
        a.height = this.plotAreaHeight * this.heightMultiplyer;
        a.x = this.marginLeftReal + this.horizontalPosition;
        a.y = this.marginTopReal + this.verticalPosition
    },
    updateGraphs: function() {
        for (var a = this.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.x = this.marginLeftReal + this.horizontalPosition;
            c.y = this.marginTopReal + this.verticalPosition;
            c.width = this.plotAreaWidth * this.widthMultiplyer;
            c.height = this.plotAreaHeight * this.heightMultiplyer;
            c.index = b;
            c.dx = this.dx;
            c.dy = this.dy;
            c.rotate = this.rotate;
            c.chartType = this.chartType
        }
    },
    updateChartCursor: function() {
        var a = this.chartCursor;
        if (a) a.x = this.marginLeftReal,
        a.y = this.marginTopReal,
        a.width = this.plotAreaWidth,
        a.height = this.plotAreaHeight,
        a.chart = this
    },
    updateScrollbars: function() {},
    addChartCursor: function(a) {
        AmCharts.callMethod("destroy", [this.chartCursor]);
        a && (this.listenTo(a, "changed", this.handleCursorChange), this.listenTo(a, "zoomed", this.handleCursorZoom));
        this.chartCursor = a
    },
    removeChartCursor: function() {
        AmCharts.callMethod("destroy", [this.chartCursor]);
        this.chartCursor = null
    },
    addTrendLine: function(a) {
        this.trendLines.push(a)
    },
    removeTrendLine: function(a) {
        for (var b = this.trendLines,
        c = b.length - 1; 0 <= c; c--) b[c] == a && b.splice(c, 1)
    },
    adjustMargins: function(a, b) {
        var c = a.scrollbarHeight;
        "top" == a.position ? b ? this.marginLeftReal += c: this.marginTopReal += c: b ? this.marginRightReal += c: this.marginBottomReal += c
    },
    getScrollbarPosition: function(a, b, c) {
        a.position = b ? "bottom" == c || "left" == c ? "bottom": "top": "top" == c || "right" == c ? "bottom": "top"
    },
    updateChartScrollbar: function(a, b) {
        if (a) {
            a.rotate = b;
            var c = this.marginTopReal,
            d = this.marginLeftReal,
            e = a.scrollbarHeight,
            f = this.dx,
            g = this.dy;
            "top" == a.position ? b ? (a.y = c, a.x = d - e) : (a.y = c - e + g, a.x = d + f) : b ? (a.y = c + g, a.x = d + this.plotAreaWidth + f) : (a.y = c + this.plotAreaHeight + 1, a.x = this.marginLeftReal)
        }
    },
    showZoomOutButton: function() {
        var a = this.zoomOutButtonSet;
        a && (a.show(), this.zoomOutButtonBG.hide())
    },
    hideZoomOutButton: function() {
        var a = this.zoomOutButtonSet;
        a && (a.hide(), this.zoomOutButtonBG.hide())
    },
    handleReleaseOutside: function(a) {
        AmCharts.AmRectangularChart.base.handleReleaseOutside.call(this, a); (a = this.chartCursor) && a.handleReleaseOutside()
    },
    handleMouseDown: function(a) {
        AmCharts.AmRectangularChart.base.handleMouseDown.call(this, a);
        var b = this.chartCursor;
        b && b.handleMouseDown(a)
    },
    handleCursorChange: function() {}
});
AmCharts.TrendLine = AmCharts.Class({
    construct: function() {
        this.createEvents("click");
        this.isProtected = !1;
        this.dashLength = 0;
        this.lineColor = "#00CC00";
        this.lineThickness = this.lineAlpha = 1
    },
    draw: function() {
        var a = this;
        a.destroy();
        var b = a.chart,
        c = b.container;
        a.set = c.set();
        var d, e, f, g, h = a.categoryAxis,
        i = a.initialDate,
        k = a.initialCategory,
        j = a.finalDate,
        m = a.finalCategory,
        n = a.valueAxis,
        l = a.valueAxisX,
        o = a.initialXValue,
        r = a.finalXValue,
        p = a.initialValue,
        q = a.finalValue;
        h && (i && (d = h.dateToCoordinate(i)), k && (d = h.categoryToCoordinate(k)), j && (e = h.dateToCoordinate(j)), m && (e = h.categoryToCoordinate(m)));
        l && !n.recalculateToPercents && (isNaN(o) || (d = l.getCoordinate(o)), isNaN(r) || (e = l.getCoordinate(r)));
        n && !n.recalculateToPercents && (isNaN(p) || (f = n.getCoordinate(p)), isNaN(q) || (g = n.getCoordinate(q)));
        if (!isNaN(d) && !isNaN(e) && !isNaN(f) && !isNaN(f)) b.rotate ? (b = AmCharts.line(c, [f, g], [d, e], a.lineColor, a.lineAlpha, a.lineThickness, a.dashLength), b.translate(0, a.y)) : (b = AmCharts.line(c, [d, e], [f, g], a.lineColor, a.lineAlpha, a.lineThickness, a.dashLength), b.translate(a.x, 0)),
        a.line = b,
        a.set.push(b),
        hoverLine = AmCharts.line(c, [d, e], [f, g], a.lineColor, 0.01, 5),
        a.set.push(hoverLine),
        hoverLine.mouseup(function() {
            a.handleLineClick()
        }).mouseover(function() {
            a.handleLineOver()
        }).mouseout(function() {
            a.handleLineOut()
        })
    },
    handleLineClick: function() {
        var a = {
            type: "click",
            trendLine: this
        };
        this.fire(a.type, a)
    },
    handleLineOver: function() {
        var a = this.rollOverColor;
        void 0 != a && this.line.attr({
            stroke: a
        })
    },
    handleLineOut: function() {
        this.line.attr({
            stroke: this.lineColor
        })
    },
    destroy: function() {
        AmCharts.removeSet(this.set)
    }
});
AmCharts.AmSerialChart = AmCharts.Class({
    inherits: AmCharts.AmRectangularChart,
    construct: function() {
        AmCharts.AmSerialChart.base.construct.call(this);
        this.createEvents("changed");
        this.columnSpacing = 5;
        this.columnWidth = 0.8;
        this.updateScrollbar = !0;
        this.categoryAxis = new AmCharts.CategoryAxis;
        this.categoryAxis.chart = this;
        this.chartType = "serial";
        this.zoomOutOnDataUpdate = !0;
        this.skipZoom = !1
    },
    initChart: function() {
        AmCharts.AmSerialChart.base.initChart.call(this);
        this.updateCategoryAxis();
        if (this.dataChanged) this.updateData(),
        this.dataChanged = !1,
        this.dispatchDataUpdated = !0;
        this.updateScrollbar = !0;
        this.drawChart();
        if (this.autoMargins && !this.marginsUpdated) this.marginsUpdated = !0,
        this.measureMargins()
    },
    validateData: function(a) {
        AmCharts.AmSerialChart.base.validateData.call(this);
        this.marginsUpdated = !1;
        if (this.zoomOutOnDataUpdate && !a) this.endTime = this.end = this.startTime = this.start = NaN
    },
    drawChart: function() {
        AmCharts.AmSerialChart.base.drawChart.call(this);
        var a = this.chartData;
        if (a) if (0 < a.length) {
            var b = this.chartScrollbar;
            b && b.draw();
            var b = a.length - 1,
            c, d;
            c = this.categoryAxis;
            if (c.parseDates && !c.equalSpacing) {
                if (c = this.startTime, d = this.endTime, isNaN(c) || isNaN(d)) c = a[0].time,
                d = a[b].time
            } else if (c = this.start, d = this.end, isNaN(c) || isNaN(d)) c = 0,
            d = b;
            this.endTime = this.startTime = this.end = this.start = void 0;
            this.zoom(c, d)
        } else this.cleanChart();
        this.bringLabelsToFront();
        this.chartCreated = !0;
        this.dispatchDataUpdatedEvent()
    },
    cleanChart: function() {
        AmCharts.callMethod("destroy", [this.valueAxes, this.graphs, this.categoryAxis, this.chartScrollbar, this.chartCursor])
    },
    updateCategoryAxis: function() {
        var a = this.categoryAxis;
        a.id = "categoryAxis";
        a.rotate = this.rotate;
        a.axisRenderer = AmCharts.RectangularAxisRenderer;
        a.guideFillRenderer = AmCharts.RectangularAxisGuideFillRenderer;
        a.axisItemRenderer = AmCharts.RectangularAxisItemRenderer;
        a.setOrientation(!this.rotate);
        a.x = this.marginLeftReal;
        a.y = this.marginTopReal;
        a.dx = this.dx;
        a.dy = this.dy;
        a.width = this.plotAreaWidth;
        a.height = this.plotAreaHeight;
        a.visibleAxisWidth = this.plotAreaWidth;
        a.visibleAxisHeight = this.plotAreaHeight;
        a.visibleAxisX = this.marginLeftReal;
        a.visibleAxisY = this.marginTopReal
    },
    updateValueAxes: function() {
        AmCharts.AmSerialChart.base.updateValueAxes.call(this);
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.rotate = this.rotate;
            c.setOrientation(this.rotate);
            var d = this.categoryAxis;
            if (!d.startOnAxis || d.parseDates) c.expandMinMax = !0
        }
    },
    updateData: function() {
        this.parseData();
        this.columnCount = this.countColumns();
        this.chartCursor && this.chartCursor.updateData();
        for (var a = this.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.columnCount = this.columnCount;
            c.data = this.chartData
        }
    },
    updateMargins: function() {
        AmCharts.AmSerialChart.base.updateMargins.call(this);
        var a = this.chartScrollbar;
        a && (this.getScrollbarPosition(a, this.rotate, this.categoryAxis.position), this.adjustMargins(a, this.rotate))
    },
    updateScrollbars: function() {
        this.updateChartScrollbar(this.chartScrollbar, this.rotate)
    },
    zoom: function(a, b) {
        var c = this.categoryAxis;
        c.parseDates && !c.equalSpacing ? this.timeZoom(a, b) : this.indexZoom(a, b);
        this.updateDepths()
    },
    timeZoom: function(a, b) {
        var c = this.maxSelectedTime;
        if (!isNaN(c)) {
            if (b != this.endTime && b - a > c) a = b - c,
            this.updateScrollbar = !0;
            if (a != this.startTime && b - a > c) b = a + c,
            this.updateScrollbar = !0
        }
        var d = this.chartData,
        e = this.categoryAxis;
        if (d && 0 < d.length && (a != this.startTime || b != this.endTime)) {
            var f = e.minDuration(),
            g = d[0].time;
            this.firstTime = g;
            var h = d[d.length - 1].time;
            this.lastTime = h;
            a || (a = g, isNaN(c) || (a = h - c));
            b || (b = h);
            a > h && (a = h);
            b < g && (b = g);
            a < g && (a = g);
            b > h && (b = h);
            b < a && (b = a + f);
            this.startTime = a;
            this.endTime = b;
            c = d.length - 1;
            f = this.getClosestIndex(d, "time", a, !0, 0, c);
            d = this.getClosestIndex(d, "time", b, !1, f, c);
            e.timeZoom(a, b);
            e.zoom(f, d);
            this.start = AmCharts.fitToBounds(f, 0, c);
            this.end = AmCharts.fitToBounds(d, 0, c);
            this.zoomAxesAndGraphs();
            this.zoomScrollbar();
            a != g || b != h ? this.showZoomOutButton() : this.hideZoomOutButton();
            this.dispatchTimeZoomEvent()
        }
        this.renderfix()
    },
    indexZoom: function(a, b) {
        var c = this.maxSelectedSeries;
        if (!isNaN(c)) {
            if (b != this.end && b - a > c) a = b - c,
            this.updateScrollbar = !0;
            if (a != this.start && b - a > c) b = a + c,
            this.updateScrollbar = !0
        }
        if (a != this.start || b != this.end) {
            var d = this.chartData.length - 1;
            isNaN(a) && (a = 0, isNaN(c) || (a = d - c));
            isNaN(b) && (b = d);
            b < a && (b = a);
            b > d && (b = d);
            a > d && (a = d - 1);
            0 > a && (a = 0);
            this.start = a;
            this.end = b;
            this.categoryAxis.zoom(a, b);
            this.zoomAxesAndGraphs();
            this.zoomScrollbar();
            0 != a || b != this.chartData.length - 1 ? this.showZoomOutButton() : this.hideZoomOutButton();
            this.dispatchIndexZoomEvent()
        }
        this.renderfix()
    },
    updateGraphs: function() {
        AmCharts.AmSerialChart.base.updateGraphs.call(this);
        for (var a = this.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.columnWidth = this.columnWidth;
            c.categoryAxis = this.categoryAxis
        }
    },
    updateDepths: function() {
        var a = this.container.rect(0, 0, 10, 10);
        this.mostFrontObj = a;
        this.updateColumnsDepth();
        for (var b = this.graphs,
        c = 0; c < b.length; c++) {
            var d = b[c];
            "column" != d.type && d.set.insertBefore(a);
            var e = d.allBullets;
            if (e) for (var f = 0; f < e.length; f++) {
                var g = e[f];
                g && g.node && g.insertBefore(a)
            }
            if (e = d.positiveObjectsToClip) for (f = 0; f < e.length; f++) d.setPositiveClipRect(e[f]);
            if (e = d.negativeObjectsToClip) for (f = 0; f < e.length; f++) d.setNegativeClipRect(e[f]);
            if (e = d.objectsToAddListeners) for (f = 0; f < e.length; f++) d.addClickListeners(e[f]),
            d.addHoverListeners(e[f])
        }
        f = this.trendLines;
        for (c = 0; c < f.length; c++) if (b = f[c], (d = b.set) && d.insertBefore(a), d = b.line) b = b.valueAxis,
        d.attr({
            "clip-rect": "0,0," + b.visibleAxisWidth + "," + b.visibleAxisHeight
        }); (c = this.chartCursor) && c.set.insertBefore(a); (c = this.zoomOutButtonSet) && c.insertBefore(a);
        b = this.valueAxes;
        for (c = 0; c < b.length; c++) {
            f = b[c];
            f.axisLine.set.toFront();
            f.grid0 && AmCharts.putSetToFront(f.grid0);
            AmCharts.putSetToFront(f.axisLine.set);
            d = f.allLabels;
            for (f = 0; f < d.length; f++)(e = d[f]) && e.toFront()
        }
        c = this.categoryAxis;
        c.axisLine.set.toFront();
        d = c.allLabels;
        for (f = 0; f < d.length; f++)(e = c.allLabels[f]) && e.toFront();
        a.remove();
        this.bgImg && this.bgImg.toBack();
        this.background && this.background.toBack();
        this.drb()
    },
    updateColumnsDepth: function() {
        var a, b = this.graphs;
        this.columnsArray = [];
        for (a = 0; a < b.length; a++) {
            var c = b[a].columnsArray;
            if (c) for (var d = 0; d < c.length; d++) this.columnsArray.push(c[d])
        }
        this.columnsArray.sort(this.compareDepth);
        for (a = 0; a < this.columnsArray.length; a++) this.columnsArray[a].column.set.insertBefore(this.mostFrontObj)
    },
    compareDepth: function(a, b) {
        return a.depth > b.depth ? 1 : -1
    },
    zoomScrollbar: function() {
        var a = this.chartScrollbar,
        b = this.categoryAxis;
        if (a && this.updateScrollbar) b.parseDates && !b.equalSpacing ? a.timeZoom(this.startTime, this.endTime) : a.zoom(this.start, this.end),
        this.updateScrollbar = !0
    },
    updateTrendLines: function() {
        for (var a = this.trendLines,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.chart = this;
            if (!c.valueAxis) c.valueAxis = this.valueAxes[0];
            c.categoryAxis = this.categoryAxis
        }
    },
    zoomAxesAndGraphs: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) a[b].zoom(this.start, this.end);
        a = this.graphs;
        for (b = 0; b < a.length; b++) a[b].zoom(this.start, this.end);
        a = this.trendLines;
        for (b = 0; b < a.length; b++) {
            var c = a[b];
            c.valueAxis.recalculateToPercents ? c.set && c.set.hide() : (c.x = this.marginLeftReal + this.horizontalPosition, c.y = this.marginTopReal + this.verticalPosition, c.draw())
        } (b = this.chartCursor) && b.zoom(this.start, this.end, this.startTime, this.endTime)
    },
    countColumns: function() {
        for (var a = 0,
        b = this.valueAxes.length,
        c = this.graphs.length,
        d, e, f = !1,
        g, h = 0; h < b; h++) {
            e = this.valueAxes[h];
            var i = e.stackType;
            if ("100%" == i || "regular" == i) {
                f = !1;
                for (g = 0; g < c; g++) if (d = this.graphs[g], !d.hidden && d.valueAxis == e && "column" == d.type) ! f && d.stackable && (a++, f = !0),
                d.stackable || a++,
                d.columnIndex = a - 1
            }
            if ("none" == i || "3d" == i) for (g = 0; g < c; g++) if (d = this.graphs[g], !d.hidden && d.valueAxis == e && "column" == d.type) d.columnIndex = a,
            a++;
            if ("3d" == i) {
                for (h = 0; h < c; h++) d = this.graphs[h],
                d.depthCount = a;
                a = 1
            }
        }
        return a
    },
    parseData: function() {
        AmCharts.AmSerialChart.base.parseData.call(this);
        this.parseSerialData()
    },
    getCategoryIndexByValue: function(a) {
        for (var b = this.chartData,
        c, d = 0; d < b.length; d++) b[d].category == a && (c = d);
        return c
    },
    handleCursorChange: function(a) {
        this.updateLegendValues(a.index)
    },
    handleCursorZoom: function(a) {
        this.updateScrollbar = !0;
        this.zoom(a.start, a.end)
    },
    handleScrollbarZoom: function(a) {
        this.updateScrollbar = !1;
        this.zoom(a.start, a.end)
    },
    dispatchTimeZoomEvent: function() {
        if (this.prevStartTime != this.startTime || this.prevEndTime != this.endTime) {
            var a = {
                type: "zoomed"
            };
            a.startDate = new Date(this.startTime);
            a.endDate = new Date(this.endTime);
            a.startIndex = this.start;
            a.endIndex = this.end;
            this.startIndex = this.start;
            this.endIndex = this.end;
            this.prevStartTime = this.startTime;
            this.prevEndTime = this.endTime;
            var b = this.categoryAxis,
            c = AmCharts.extractPeriod(b.minPeriod).period,
            b = b.dateFormatsObject[c];
            a.startValue = AmCharts.formatDate(a.startDate, b);
            a.endValue = AmCharts.formatDate(a.endDate, b);
            this.fire(a.type, a)
        }
    },
    dispatchIndexZoomEvent: function() {
        if (this.prevStartIndex != this.start || this.prevEndIndex != this.end) {
            this.startIndex = this.start;
            this.endIndex = this.end;
            var a = this.chartData;
            if (a && 0 < a.length && !isNaN(this.start) && !isNaN(this.end)) {
                var b = {
                    type: "zoomed"
                };
                b.startIndex = this.start;
                b.endIndex = this.end;
                b.startValue = a[this.start].category;
                b.endValue = a[this.end].category;
                if (this.categoryAxis.parseDates) this.startTime = a[this.start].time,
                this.endTime = a[this.end].time,
                b.startDate = new Date(this.startTime),
                b.endDate = new Date(this.endTime);
                this.prevStartIndex = this.start;
                this.prevEndIndex = this.end;
                this.fire(b.type, b)
            }
        }
    },
    updateLegendValues: function(a) {
        for (var b = this.graphs,
        c = 0; c < b.length; c++) {
            var d = b[c];
            d.currentDataItem = isNaN(a) ? void 0 : this.chartData[a].axes[d.valueAxis.id].graphs[d.id]
        }
        this.legend && this.legend.updateValues()
    },
    getClosestIndex: function(a, b, c, d, e, f) {
        0 > e && (e = 0);
        f > a.length - 1 && (f = a.length - 1);
        var g = e + Math.round((f - e) / 2),
        h = a[g][b];
        if (1 >= f - e) {
            if (d) return e;
            d = a[f][b];
            return Math.abs(a[e][b] - c) < Math.abs(d - c) ? e: f
        }
        return c == h ? g: c < h ? this.getClosestIndex(a, b, c, d, e, g) : this.getClosestIndex(a, b, c, d, g, f)
    },
    zoomToIndexes: function(a, b) {
        this.updateScrollbar = !0;
        var c = this.chartData;
        if (c) {
            var d = c.length;
            if (0 < d) 0 > a && (a = 0),
            b > d - 1 && (b = d - 1),
            d = this.categoryAxis,
            d.parseDates && !d.equalSpacing ? this.zoom(c[a].time, c[b].time) : this.zoom(a, b)
        }
    },
    zoomToDates: function(a, b) {
        this.updateScrollbar = !0;
        var c = this.chartData;
        if (this.categoryAxis.equalSpacing) {
            var d = this.getClosestIndex(c, "time", a.getTime(), !0, 0, c.length),
            c = this.getClosestIndex(c, "time", b.getTime(), !1, 0, c.length);
            this.zoom(d, c)
        } else this.zoom(a.getTime(), b.getTime())
    },
    zoomToCategoryValues: function(a, b) {
        this.updateScrollbar = !0;
        this.zoom(this.getCategoryIndexByValue(a), this.getCategoryIndexByValue(b))
    },
    formatString: function(a, b) {
        var c = b.graph;
        if ( - 1 != a.indexOf("[[category]]")) {
            var d = b.serialDataItem.category;
            if (this.categoryAxis.parseDates) {
                var e = this.balloonDateFormat,
                f = this.chartCursor;
                if (f) e = f.categoryBalloonDateFormat; - 1 != a.indexOf("[[category]]") && (e = AmCharts.formatDate(d, e), -1 != e.indexOf("fff") && (e = AmCharts.formatMilliseconds(e, d)), d = e)
            }
            a = a.replace(/\[\[category\]\]/g, "" + d)
        }
        c = c.numberFormatter;
        if (!c) c = this.numberFormatter;
        d = b.graph.valueAxis;
        if ((e = d.duration) && !isNaN(b.values.value)) d = AmCharts.formatDuration(b.values.value, e, "", d.durationUnits, d.maxInterval, c),
        a = a.replace(RegExp("\\[\\[value\\]\\]", "g"), d);
        d = ["value", "open", "low", "high", "close"];
        e = this.percentFormatter;
        a = AmCharts.formatValue(a, b.percents, d, e, "percents.");
        a = AmCharts.formatValue(a, b.values, d, c, "", this.usePrefixes, this.prefixesOfSmallNumbers, this.prefixesOfBigNumbers);
        a = AmCharts.formatValue(a, b.values, ["percents"], e); - 1 != a.indexOf("[[") && (a = AmCharts.formatDataContextValue(a, b.dataContext));
        return a = AmCharts.AmSerialChart.base.formatString.call(this, a, b)
    },
    addChartScrollbar: function(a) {
        AmCharts.callMethod("destroy", [this.chartScrollbar]);
        if (a) a.chart = this,
        this.listenTo(a, "zoomed", this.handleScrollbarZoom);
        if (this.rotate) {
            if (void 0 == a.width) a.width = a.scrollbarHeight
        } else if (void 0 == a.height) a.height = a.scrollbarHeight;
        this.chartScrollbar = a
    },
    removeChartScrollbar: function() {
        AmCharts.callMethod("destroy", [this.chartScrollbar]);
        this.chartScrollbar = null
    },
    handleReleaseOutside: function(a) {
        AmCharts.AmSerialChart.base.handleReleaseOutside.call(this, a);
        AmCharts.callMethod("handleReleaseOutside", [this.chartScrollbar])
    }
});
AmCharts.AmRadarChart = AmCharts.Class({
    inherits: AmCharts.AmCoordinateChart,
    construct: function() {
        AmCharts.AmRadarChart.base.construct.call(this);
        this.marginRight = this.marginBottom = this.marginTop = this.marginLeft = 0;
        this.chartType = "radar";
        this.radius = "35%"
    },
    initChart: function() {
        AmCharts.AmRadarChart.base.initChart.call(this);
        if (this.dataChanged) this.updateData(),
        this.dataChanged = !1,
        this.dispatchDataUpdated = !0;
        this.drawChart()
    },
    updateData: function() {
        this.parseData();
        for (var a = this.graphs,
        b = 0; b < a.length; b++) a[b].data = this.chartData
    },
    updateGraphs: function() {
        for (var a = this.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.index = b;
            c.width = this.realRadius;
            c.height = this.realRadius;
            c.x = this.centerX;
            c.y = this.centerY;
            c.chartType = this.chartType
        }
    },
    parseData: function() {
        AmCharts.AmRadarChart.base.parseData.call(this);
        this.parseSerialData()
    },
    updateValueAxes: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.axisRenderer = AmCharts.RadarAxisRenderer;
            c.guideFillRenderer = AmCharts.RadarAxisGuideFillRenderer;
            c.axisItemRenderer = AmCharts.RadarAxisItemRenderer;
            c.autoGridCount = !1;
            c.x = this.centerX;
            c.y = this.centerY;
            c.width = this.realRadius;
            c.height = this.realRadius
        }
    },
    drawChart: function() {
        AmCharts.AmRadarChart.base.drawChart.call(this);
        var a = this.updateWidth(),
        b = this.updateHeight(),
        c = this.marginTop + this.getTitleHeight(),
        d = this.marginLeft,
        b = b - c - this.marginBottom;
        this.centerX = d + (a - d - this.marginRight) / 2;
        this.centerY = c + b / 2;
        this.realRadius = AmCharts.toCoordinate(this.radius, a, b);
        this.updateValueAxes();
        this.updateGraphs();
        if (a = this.chartData) if (0 < a.length) {
            for (c = 0; c < this.valueAxes.length; c++) this.valueAxes[c].zoom(0, a.length - 1);
            for (c = 0; c < this.graphs.length; c++) this.graphs[c].zoom(0, a.length - 1)
        } else this.cleanChart();
        this.bringLabelsToFront();
        this.chartCreated = !0;
        this.dispatchDataUpdatedEvent();
        this.drb()
    },
    formatString: function(a, b) {
        var c = b.graph; - 1 != a.indexOf("[[category]]") && (a = a.replace(/\[\[category\]\]/g, "" + b.serialDataItem.category));
        c = c.numberFormatter;
        if (!c) c = this.numberFormatter;
        a = AmCharts.formatValue(a, b.values, ["value"], c, "", this.usePrefixes, this.prefixesOfSmallNumbers, this.prefixesOfBigNumbers);
        return a = AmCharts.AmRadarChart.base.formatString.call(this, a, b)
    },
    cleanChart: function() {
        this.callMethod("destroy", [this.valueAxes, this.graphs])
    }
});
AmCharts.AxisBase = AmCharts.Class({
    construct: function() {
        this.dy = this.dx = 0;
        this.axisThickness = 1;
        this.axisColor = "#000000";
        this.axisAlpha = 1;
        this.gridCount = this.tickLength = 5;
        this.gridAlpha = 0.2;
        this.gridThickness = 1;
        this.gridColor = "#000000";
        this.dashLength = 0;
        this.labelFrequency = 1;
        this.showLastLabel = this.showFirstLabel = !0;
        this.fillColor = "#FFFFFF";
        this.fillAlpha = 0;
        this.labelsEnabled = !0;
        this.labelRotation = 0;
        this.autoGridCount = !0;
        this.valueRollOverColor = "#CC0000";
        this.offset = 0;
        this.guides = [];
        this.visible = !0;
        this.counter = 0;
        this.guides = [];
        this.ignoreAxisWidth = this.inside = !1;
        this.titleBold = !0
    },
    zoom: function(a, b) {
        this.start = a;
        this.end = b;
        this.dataChanged = !0;
        this.draw()
    },
    fixAxisPosition: function() {
        var a = this.position;
        "horizontal" == this.orientation ? ("left" == a && (a = "bottom"), "right" == a && (a = "top")) : ("bottom" == a && (a = "left"), "top" == a && (a = "right"));
        this.position = a
    },
    draw: function() {
        var a = this.chart;
        if (void 0 == this.titleColor) this.titleColor = a.color;
        if (isNaN(this.titleFontSize)) this.titleFontSize = a.fontSize + 1;
        this.allLabels = [];
        this.counter = 0;
        this.destroy();
        this.fixAxisPosition();
        this.set = this.chart.container.set();
        this.axisLine = new this.axisRenderer(this);
        a = this.axisLine.axisWidth;
        if (this.autoGridCount) {
            var b;
            "vertical" == this.orientation ? (b = this.height / 35, 3 > b && (b = 3)) : b = this.width / 75;
            this.gridCount = b
        }
        this.axisWidth = a;
        this.addTitle()
    },
    setOrientation: function(a) {
        this.orientation = a ? "horizontal": "vertical"
    },
    addTitle: function() {
        var a = this.title;
        if (a) {
            var b = this.chart,
            a = AmCharts.text(b.container, 0, 0, a, {
                fill: this.titleColor,
                "font-family": b.fontFamily,
                "font-size": this.titleFontSize
            }); ! 0 == this.titleBold && a.attr({
                "font-weight": "bold"
            });
            this.set.push(a);
            this.titleLabel = a
        }
    },
    positionTitle: function() {
        var a = this.titleLabel;
        if (a) {
            var b, c;
            this.set.exclude(a);
            var d = this.set.getBBox();
            this.set.push(a);
            var e = d.x,
            f = d.y,
            g = d.width,
            d = d.height,
            h = this.visibleAxisX,
            i = this.visibleAxisY,
            k = this.visibleAxisWidth,
            j = this.visibleAxisHeight,
            m = "middle",
            n = a.getBBox(),
            l = 0,
            o = this.titleFontSize / 2;
            switch (this.position) {
            case "top":
                b = h + k / 2;
                c = f - 10 - o;
                break;
            case "bottom":
                b = h + k / 2;
                c = f + d + 10 + o;
                break;
            case "left":
                b = -n.width / 2 - (h - e);
                b -= 10 + o;
                c = i + j / 2;
                l = 270;
                m = "start";
                break;
            case "right":
                b = k - n.width / 2 - (h + k - (e + g)) + 10 + o,
                c = i + j / 2,
                l = 270,
                m = "start"
            }
            a.attr({
                "text-anchor": m
            });
            "start" == m ? a.transform("...r" + l) : a.transform("r" + l);
            a.transform("...T" + b + "," + c)
        }
    },
    pushAxisItem: function(a) {
        a = a.graphics();
        0 < a.length && this.set.push(a)
    },
    addGuide: function(a) {
        this.guides.push(a)
    },
    removeGuide: function(a) {
        for (var b = this.guides,
        c = 0; c < b.length; c++) b[c] == a && b.splice(c, 1)
    },
    handleGuideOver: function(a) {
        clearTimeout(this.chart.hoverInt);
        var a = this.guides[a],
        b = a.graphics.getBBox(),
        c = b.x + b.width / 2,
        b = b.y + b.height / 2,
        d = a.fillColor;
        if (void 0 == d) d = a.lineColor;
        this.chart.showBalloon(a.balloonText, d, !0, c, b)
    },
    handleGuideOut: function() {
        this.chart.hideBalloon()
    },
    destroy: function() {
        AmCharts.removeSet(this.set);
        this.axisLine && AmCharts.removeSet(this.axisLine.set)
    }
});
AmCharts.ValueAxis = AmCharts.Class({
    inherits: AmCharts.AxisBase,
    construct: function() {
        this.createEvents("axisChanged", "logarithmicAxisFailed", "axisSelfZoomed", "axisZoomed");
        AmCharts.ValueAxis.base.construct.call(this);
        this.dataChanged = !0;
        this.gridCount = 8;
        this.stackType = "none";
        this.position = "left";
        this.unitPosition = "right";
        this.recalculateToPercents = this.includeHidden = this.includeGuidesInMinMax = this.integersOnly = !1;
        this.durationUnits = {
            DD: "d. ",
            hh: ":",
            mm: ":",
            ss: ""
        };
        this.scrollbar = !1;
        this.baseValue = 0;
        this.radarCategoriesEnabled = !0;
        this.gridType = "polygons";
        this.useScientificNotation = !1;
        this.axisTitleOffset = 10
    },
    updateData: function() {
        if (0 >= this.gridCount) this.gridCount = 1;
        this.data = this.chart.chartData;
        "xy" != this.chart.chartType && (this.stackGraphs("smoothedLine"), this.stackGraphs("line"), this.stackGraphs("column"), this.stackGraphs("step"));
        this.recalculateToPercents && this.recalculate();
        this.synchronizationMultiplyer && this.synchronizeWithAxis ? this.foundGraphs = !0 : (this.foundGraphs = !1, this.getMinMax())
    },
    draw: function() {
        AmCharts.ValueAxis.base.draw.call(this);
        var a = this.chart,
        b = this.set;
        if ("duration" == this.type) this.duration = "ss";
        if (!0 == this.dataChanged) this.updateData(),
        this.dataChanged = !1;
        if (this.logarithmic && (0 >= this.getMin(0, this.data.length - 1) || 0 >= this.minimum)) this.fire("logarithmicAxisFailed", {
            type: "logarithmicAxisFailed"
        });
        else {
            this.grid0 = null;
            var c, d, e = a.dx,
            f = a.dy,
            g = !1,
            h = this.logarithmic,
            i = a.chartType;
            if (!isNaN(this.min) && !isNaN(this.max) && this.foundGraphs && Infinity != this.min && -Infinity != this.max) {
                var k = this.labelFrequency,
                j = this.showFirstLabel,
                m = this.showLastLabel,
                n = 1,
                l = 0,
                o = Math.round((this.max - this.min) / this.step) + 1;
                if (!0 == h) {
                    var r = Math.log(this.max) * Math.LOG10E - Math.log(this.minReal) * Math.LOG10E;
                    this.stepWidth = this.axisWidth / r;
                    2 < r && (o = Math.ceil(Math.log(this.max) * Math.LOG10E) + 1, l = Math.round(Math.log(this.minReal) * Math.LOG10E), o > this.gridCount && (n = Math.ceil(o / this.gridCount)))
                } else this.stepWidth = this.axisWidth / (this.max - this.min);
                c = 0;
                1 > this.step && -1 < this.step && (d = this.step.toString(), c = -1 != d.indexOf("e-") ? Number(d.split("-")[1]) : d.split(".")[1].length);
                this.integersOnly && (c = 0);
                if (c > this.maxDecCount) c = this.maxDecCount;
                if (!isNaN(this.precision)) c = this.precision;
                this.max = AmCharts.roundTo(this.max, this.maxDecCount);
                this.min = AmCharts.roundTo(this.min, this.maxDecCount);
                var p = {};
                p.precision = c;
                p.decimalSeparator = a.numberFormatter.decimalSeparator;
                p.thousandsSeparator = a.numberFormatter.thousandsSeparator;
                this.numberFormatter = p;
                var q = this.guides,
                u = q.length;
                if (0 < u) {
                    var x = this.fillAlpha;
                    for (d = this.fillAlpha = 0; d < u; d++) {
                        var A = q[d],
                        I = NaN;
                        if (!isNaN(A.toValue)) {
                            var I = this.getCoordinate(A.toValue),
                            s = new this.axisItemRenderer(this, I, "", !0, NaN, NaN, A);
                            this.pushAxisItem(s)
                        }
                        var H = NaN;
                        isNaN(A.value) || (H = this.getCoordinate(A.value), s = new this.axisItemRenderer(this, H, A.label, !0, NaN, (I - H) / 2, A), this.pushAxisItem(s));
                        if (!isNaN(I - H)) {
                            s = new this.guideFillRenderer(this, I - H, H, A);
                            this.pushAxisItem(s);
                            s = s.graphics();
                            A.graphics = s;
                            s.index = d;
                            var G = this;
                            A.balloonText && (s.mouseover(function() {
                                G.handleGuideOver(this.index)
                            }), s.mouseout(function() {
                                G.handleGuideOut(this.index)
                            }))
                        }
                    }
                    this.fillAlpha = x
                }
                q = !1;
                for (d = l; d < o; d += n) s = AmCharts.roundTo(this.step * d + this.min, c),
                -1 != ("" + s).indexOf("e") && (q = !0, ("" + s).split("e"));
                if (this.duration) this.maxInterval = AmCharts.getMaxInterval(this.max, this.duration);
                for (d = l; d < o; d += n) if (l = this.step * d + this.min, l = AmCharts.roundTo(l, this.maxDecCount + 1), !(this.integersOnly && Math.round(l) != l)) {
                    if (!0 == h) {
                        if (0 == l) l = this.minReal;
                        2 < r && (l = Math.pow(10, d));
                        q = -1 != ("" + l).indexOf("e") ? !0 : !1
                    }
                    this.useScientificNotation && (q = !0);
                    this.usePrefixes && (q = !1);
                    if (q) s = -1 == ("" + l).indexOf("e") ? l.toExponential(15) : "" + l,
                    s = s.split("e"),
                    c = Number(s[0]),
                    s = Number(s[1]),
                    10 == c && (c = 1, s += 1),
                    s = c + "e" + s,
                    0 == l && (s = "0"),
                    1 == l && (s = "1");
                    else {
                        if (h) c = ("" + l).split("."),
                        p.precision = c[1] ? c[1].length: -1;
                        s = this.usePrefixes ? AmCharts.addPrefix(l, a.prefixesOfBigNumbers, a.prefixesOfSmallNumbers, p) : AmCharts.formatNumber(l, p, p.precision)
                    }
                    this.duration && (s = AmCharts.formatDuration(l, this.duration, "", this.durationUnits, this.maxInterval, p));
                    this.recalculateToPercents ? s += "%": (c = this.unit) && (s = "left" == this.unitPosition ? c + s: s + c);
                    Math.round(d / k) != d / k && (s = void 0);
                    if (0 == d && !j || d == o - 1 && !m) s = " ";
                    c = this.getCoordinate(l);
                    s = new this.axisItemRenderer(this, c, s);
                    this.pushAxisItem(s);
                    if (l == this.baseValue && "radar" != i) {
                        var C, S, l = this.visibleAxisWidth,
                        s = this.visibleAxisHeight,
                        u = this.visibleAxisX,
                        x = this.visibleAxisY;
                        "horizontal" == this.orientation ? c >= u && c <= u + l + 1 && (C = [c, c, c + e], S = [0 + s, 0, f]) : c >= x && c <= x + s + 1 && (C = [0, l, l + e], S = [c, c, c + f]);
                        if (C) this.grid0 = AmCharts.line(a.container, C, S, this.gridColor, 2 * this.gridAlpha, 1, this.dashLength),
                        b.push(this.grid0)
                    }
                }
                e = this.baseValue;
                if (this.min > this.baseValue && this.max > this.baseValue) e = this.min;
                if (this.min < this.baseValue && this.max < this.baseValue) e = this.max;
                if (h && e < this.minReal) e = this.minReal;
                this.baseCoord = this.getCoordinate(e);
                e = {
                    type: "axisChanged"
                };
                e.min = h ? this.minReal: this.min;
                e.max = this.max;
                this.fire("axisChanged", e);
                this.axisCreated = !0
            } else g = !0;
            "radar" != i ? this.rotate ? b.translate("0," + a.marginTopReal) : b.translate(a.marginLeftReal + ",0") : this.axisLine.set.toFront();
            this.positionTitle(); ! this.visible || g ? (b.hide(), this.axisLine.set.hide()) : (b.show(), this.axisLine.set.show())
        }
    },
    stackGraphs: function(a) {
        var b = this.stackType;
        "stacked" == b && (b = "regular");
        "line" == b && (b = "none");
        "100% stacked" == b && (b = "100%");
        this.stackType = b;
        var c = [],
        d = [],
        e = [],
        f = [],
        g,
        h = this.chart.graphs,
        i,
        k,
        j;
        if (("line" == a || "step" == a || "smoothedLine" == a) && ("regular" == b || "100%" == b)) for (j = 0; j < h.length; j++) if (g = h[j], !g.hidden && (k = g.type, g.chart == this.chart && g.valueAxis == this && a == k && g.stackable)) {
            if (i) g.stackGraph = i;
            i = g
        }
        for (i = this.start; i <= this.end; i++) for (j = 0; j < h.length; j++) if (g = h[j], !g.hidden && (k = g.type, g.chart == this.chart && g.valueAxis == this && a == k && g.stackable && (k = this.data[i].axes[this.id].graphs[g.id], g = k.values.value, !isNaN(g) && (f[i] = isNaN(f[i]) ? Math.abs(g) : f[i] + Math.abs(g), "regular" == b)))) {
            if ("line" == a || "step" == a || "smoothedLine" == a) isNaN(c[i]) ? (c[i] = g, k.values.close = g, k.values.open = this.baseValue) : (k.values.close = isNaN(g) ? c[i] : g + c[i], k.values.open = c[i], c[i] = k.values.close);
            if ("column" == a && !isNaN(g)) if (k.values.close = g, 0 > g) {
                k.values.close = g;
                if (!isNaN(d[i])) k.values.close += d[i],
                k.values.open = d[i];
                d[i] = k.values.close
            } else {
                k.values.close = g;
                if (!isNaN(e[i])) k.values.close += e[i],
                k.values.open = e[i];
                e[i] = k.values.close
            }
        }
        for (i = this.start; i <= this.end; i++) for (j = 0; j < h.length; j++) if (g = h[j], !g.hidden && (k = g.type, g.chart == this.chart && g.valueAxis == this && a == k && g.stackable && (k = this.data[i].axes[this.id].graphs[g.id], g = k.values.value, !isNaN(g) && (c = 100 * (g / f[i]), k.values.percents = c, k.values.total = f[i], "100%" == b)))) isNaN(d[i]) && (d[i] = 0),
        isNaN(e[i]) && (e[i] = 0),
        0 > c ? (k.values.close = c + d[i], k.values.open = d[i], d[i] = k.values.close) : (k.values.close = c + e[i], k.values.open = e[i], e[i] = k.values.close)
    },
    recalculate: function() {
        for (var a = this.chart.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            if (c.valueAxis == this) {
                var d = "value";
                if ("candlestick" == c.type || "ohlc" == c.type) d = "open";
                var e, f, g = this.end + 2,
                g = AmCharts.fitToBounds(this.end + 1, 0, this.data.length - 1),
                h = this.start;
                0 < h && h--;
                for (var i = this.start; i <= g && !(f = this.data[i].axes[this.id].graphs[c.id], e = f.values[d], !isNaN(e)); i++);
                for (d = h; d <= g; d++) {
                    f = this.data[d].axes[this.id].graphs[c.id];
                    f.percents = {};
                    var h = f.values,
                    k;
                    for (k in h) f.percents[k] = "percents" != k ? 100 * (h[k] / e) - 100 : h[k]
                }
            }
        }
    },
    getMinMax: function() {
        for (var a = !1,
        b = this.chart,
        c = b.graphs,
        d = 0; d < c.length; d++) {
            var e = c[d].type;
            if ("line" == e || "step" == e || "smoothedLine" == e) this.expandMinMax && (a = !0)
        }
        a && (0 < this.start && this.start--, this.end < this.data.length - 1 && this.end++);
        "serial" == b.chartType && !0 == b.categoryAxis.parseDates && !a && this.end < this.data.length - 1 && this.end++;
        this.min = this.getMin(this.start, this.end);
        this.max = this.getMax();
        a = this.guides.length;
        if (this.includeGuidesInMinMax && 0 < a) for (b = 0; b < a; b++) {
            c = this.guides[b];
            if (c.toValue < this.min) this.min = c.toValue;
            if (c.value < this.min) this.min = c.value;
            if (c.toValue > this.max) this.max = c.toValue;
            if (c.value > this.max) this.max = c.value
        }
        if (!isNaN(this.minimum)) this.min = this.minimum;
        if (!isNaN(this.maximum)) this.max = this.maximum;
        if (this.min > this.max) a = this.max,
        this.max = this.min,
        this.min = a;
        if (!isNaN(this.minTemp)) this.min = this.minTemp;
        if (!isNaN(this.maxTemp)) this.max = this.maxTemp;
        this.minReal = this.min;
        this.maxReal = this.max;
        if (0 == this.min && 0 == this.max) this.max = 9;
        if (this.min > this.max) this.min = this.max - 1;
        a = this.min;
        b = this.max;
        c = this.max - this.min;
        d = 0 == c ? Math.pow(10, Math.floor(Math.log(Math.abs(this.max)) * Math.LOG10E)) / 10 : Math.pow(10, Math.floor(Math.log(Math.abs(c)) * Math.LOG10E)) / 10;
        if (isNaN(this.maximum) && isNaN(this.maxTemp)) this.max = Math.ceil(this.max / d) * d + d;
        if (isNaN(this.minimum) && isNaN(this.minTemp)) this.min = Math.floor(this.min / d) * d - d;
        if (0 > this.min && 0 <= a) this.min = 0;
        if (0 < this.max && 0 >= b) this.max = 0;
        if ("100%" == this.stackType) this.min = 0 > this.min ? -100 : 0,
        this.max = 0 > this.max ? 0 : 100;
        c = this.max - this.min;
        d = Math.pow(10, Math.floor(Math.log(Math.abs(c)) * Math.LOG10E)) / 10;
        this.step = Math.ceil(c / this.gridCount / d) * d;
        c = Math.pow(10, Math.floor(Math.log(Math.abs(this.step)) * Math.LOG10E));
        c = c.toExponential(0).split("e");
        d = Number(c[1]);
        9 == Number(c[0]) && d++;
        c = this.generateNumber(1, d);
        d = Math.ceil(this.step / c);
        5 < d && (d = 10);
        5 >= d && 2 < d && (d = 5);
        this.step = Math.ceil(this.step / (c * d)) * c * d;
        1 > c ? (this.maxDecCount = Math.abs(Math.log(Math.abs(c)) * Math.LOG10E), this.maxDecCount = Math.round(this.maxDecCount), this.step = AmCharts.roundTo(this.step, this.maxDecCount + 1)) : this.maxDecCount = 0;
        this.min = this.step * Math.floor(this.min / this.step);
        this.max = this.step * Math.ceil(this.max / this.step);
        if (0 > this.min && 0 <= a) this.min = 0;
        if (0 < this.max && 0 >= b) this.max = 0;
        if (1 < this.minReal && 1 < this.max - this.minReal) this.minReal = Math.floor(this.minReal);
        c = Math.pow(10, Math.floor(Math.log(Math.abs(this.minReal)) * Math.LOG10E));
        if (0 == this.min) this.minReal = c;
        if (0 == this.min && 1 < this.minReal) this.minReal = 1;
        if (0 < this.min && 0 < this.minReal - this.step) this.minReal = this.min + this.step < this.minReal ? this.min + this.step: this.min;
        c = Math.log(b) * Math.LOG10E - Math.log(a) * Math.LOG10E;
        if (this.logarithmic) if (2 < c) this.minReal = this.min = Math.pow(10, Math.floor(Math.log(Math.abs(a)) * Math.LOG10E)),
        this.max = Math.pow(10, Math.ceil(Math.log(Math.abs(b)) * Math.LOG10E));
        else if (b = Math.pow(10, Math.floor(Math.log(Math.abs(this.min)) * Math.LOG10E)) / 10, a = Math.pow(10, Math.floor(Math.log(Math.abs(a)) * Math.LOG10E)) / 10, b < a) this.minReal = this.min = 10 * a
    },
    generateNumber: function(a, b) {
        var c = "",
        d;
        d = 0 > b ? Math.abs(b) - 1 : Math.abs(b);
        for (var e = 0; e < d; e++) c += "0";
        return 0 > b ? Number("0." + c + ("" + a)) : Number("" + a + c)
    },
    getMin: function(a, b) {
        for (var c, d = a; d <= b; d++) {
            var e = this.data[d].axes[this.id].graphs,
            f;
            for (f in e) {
                var g = this.chart.getGraphById(f);
                if (g.includeInMinMax && (!g.hidden || this.includeHidden)) {
                    isNaN(c) && (c = Infinity);
                    this.foundGraphs = !0;
                    g = e[f].values;
                    if (this.recalculateToPercents) g = e[f].percents;
                    var h;
                    if (this.minMaxField) h = g[this.minMaxField],
                    h < c && (c = h);
                    else for (var i in g)"percents" != i && "total" != i && (h = g[i], h < c && (c = h))
                }
            }
        }
        return c
    },
    getMax: function() {
        for (var a, b = this.start; b <= this.end; b++) {
            var c = this.data[b].axes[this.id].graphs,
            d;
            for (d in c) {
                var e = this.chart.getGraphById(d);
                if (e.includeInMinMax && (!e.hidden || this.includeHidden)) {
                    isNaN(a) && (a = -Infinity);
                    this.foundGraphs = !0;
                    e = c[d].values;
                    if (this.recalculateToPercents) e = c[d].percents;
                    var f;
                    if (this.minMaxField) f = e[this.minMaxField],
                    f > a && (a = f);
                    else for (var g in e)"percents" != g && "total" != g && (f = e[g], f > a && (a = f))
                }
            }
        }
        return a
    },
    dispatchZoomEvent: function(a, b) {
        var c = {
            type: "axisZoomed",
            startValue: a,
            endValue: b
        };
        this.fire(c.type, c)
    },
    zoomToValues: function(a, b) {
        if (b < a) var c = b,
        b = a,
        a = c;
        if (a < this.min) a = this.min;
        if (b > this.max) b = this.max;
        c = {
            type: "axisSelfZoomed",
            valueAxis: this
        };
        c.multiplyer = this.axisWidth / Math.abs(this.getCoordinate(b) - this.getCoordinate(a));
        c.position = "vertical" == this.orientation ? this.reversed ? this.getCoordinate(a) - this.y: this.getCoordinate(b) - this.y: this.reversed ? this.getCoordinate(b) - this.x: this.getCoordinate(a) - this.x;
        this.fire(c.type, c)
    },
    coordinateToValue: function(a) {
        return isNaN(a) ? NaN: !0 == this.logarithmic ? Math.pow(10, (this.rotate ? !0 == this.reversed ? (this.axisWidth - a) / this.stepWidth: a / this.stepWidth: !0 == this.reversed ? a / this.stepWidth: (this.axisWidth - a) / this.stepWidth) + Math.log(this.minReal) * Math.LOG10E) : !0 == this.reversed ? this.rotate ? this.min - (a - this.axisWidth) / this.stepWidth: a / this.stepWidth + this.min: this.rotate ? a / this.stepWidth + this.min: this.min - (a - this.axisWidth) / this.stepWidth
    },
    getCoordinate: function(a) {
        if (isNaN(a)) return NaN;
        var b = this.rotate,
        c = this.reversed,
        d = this.axisWidth,
        e = this.stepWidth; ! 0 == this.logarithmic ? (a = Math.log(a) * Math.LOG10E - Math.log(this.minReal) * Math.LOG10E, b = b ? !0 == c ? d - e * a: e * a: !0 == c ? e * a: d - e * a) : b = !0 == c ? b ? d - e * (a - this.min) : e * (a - this.min) : b ? e * (a - this.min) : d - e * (a - this.min);
        b = Math.round(10 * b) / 10;
        return b = this.rotate ? b + this.x: b + this.y
    },
    synchronizeWithAxis: function(a) {
        this.synchronizeWithAxis = a;
        this.removeListener(this.synchronizeWithAxis, "axisChanged", this.handleSynchronization);
        this.listenTo(this.synchronizeWithAxis, "axisChanged", this.handleSynchronization)
    },
    handleSynchronization: function() {
        var a = this.synchronizeWithAxis,
        b = a.min,
        c = a.max,
        a = a.step,
        d = this.synchronizationMultiplyer;
        if (d) this.min = b * d,
        this.max = c * d,
        this.step = a * d,
        b = Math.pow(10, Math.floor(Math.log(Math.abs(this.step)) * Math.LOG10E)),
        b = Math.abs(Math.log(Math.abs(b)) * Math.LOG10E),
        this.maxDecCount = b = Math.round(b),
        this.draw()
    }
});
AmCharts.CategoryAxis = AmCharts.Class({
    inherits: AmCharts.AxisBase,
    construct: function() {
        AmCharts.CategoryAxis.base.construct.call(this);
        this.minPeriod = "DD";
        this.equalSpacing = this.parseDates = !1;
        this.position = "bottom";
        this.startOnAxis = !1;
        this.firstDayOfWeek = 1;
        this.gridPosition = "middle";
        this.boldPeriodBeginning = !0;
        this.periods = [{
            period: "ss",
            count: 1
        },
        {
            period: "ss",
            count: 5
        },
        {
            period: "ss",
            count: 10
        },
        {
            period: "ss",
            count: 30
        },
        {
            period: "mm",
            count: 1
        },
        {
            period: "mm",
            count: 5
        },
        {
            period: "mm",
            count: 10
        },
        {
            period: "mm",
            count: 30
        },
        {
            period: "hh",
            count: 1
        },
        {
            period: "hh",
            count: 3
        },
        {
            period: "hh",
            count: 6
        },
        {
            period: "hh",
            count: 12
        },
        {
            period: "DD",
            count: 1
        },
        {
            period: "WW",
            count: 1
        },
        {
            period: "MM",
            count: 1
        },
        {
            period: "MM",
            count: 2
        },
        {
            period: "MM",
            count: 3
        },
        {
            period: "MM",
            count: 6
        },
        {
            period: "YYYY",
            count: 1
        },
        {
            period: "YYYY",
            count: 2
        },
        {
            period: "YYYY",
            count: 5
        },
        {
            period: "YYYY",
            count: 10
        },
        {
            period: "YYYY",
            count: 50
        },
        {
            period: "YYYY",
            count: 100
        }];
        this.dateFormats = [{
            period: "fff",
            format: "JJ:NN:SS"
        },
        {
            period: "ss",
            format: "JJ:NN:SS"
        },
        {
            period: "mm",
            format: "JJ:NN"
        },
        {
            period: "hh",
            format: "JJ:NN"
        },
        {
            period: "DD",
            format: "MMM DD"
        },
        {
            period: "WW",
            format: "MMM DD"
        },
        {
            period: "MM",
            format: "MMM"
        },
        {
            period: "YYYY",
            format: "YYYY"
        }];
        this.nextPeriod = {};
        this.nextPeriod.fff = "ss";
        this.nextPeriod.ss = "mm";
        this.nextPeriod.mm = "hh";
        this.nextPeriod.hh = "DD";
        this.nextPeriod.DD = "MM";
        this.nextPeriod.MM = "YYYY"
    },
    draw: function() {
        var a = this;
        AmCharts.CategoryAxis.base.draw.call(a);
        a.generateDFObject();
        var b = a.chart.chartData;
        a.data = b;
        if (0 < b.length) {
            var c = a.start,
            d = a.labelFrequency,
            e = 0,
            f = a.end - c + 1,
            g = a.gridCount,
            h = a.showFirstLabel,
            i = a.showLastLabel,
            k, j = "",
            m = AmCharts.extractPeriod(a.minPeriod);
            k = AmCharts.getPeriodDuration(m.period, m.count);
            var n, l, o, r, p = a.rotate,
            q = a.firstDayOfWeek,
            b = AmCharts.resetDateToMin(new Date(b[b.length - 1].time + 1.05 * k), a.minPeriod, 1, q).getTime();
            if (a.endTime > b) a.endTime = b;
            if (a.parseDates && !a.equalSpacing) {
                a.timeDifference = a.endTime - a.startTime;
                c = a.choosePeriod(0);
                d = c.period;
                b = c.count;
                n = AmCharts.getPeriodDuration(d, b);
                if (n < k) d = m.period,
                b = m.count,
                n = k;
                m = d;
                "WW" == m && (m = "DD");
                a.stepWidth = a.getStepWidth(a.timeDifference);
                g = Math.ceil(a.timeDifference / n) + 1;
                j = AmCharts.resetDateToMin(new Date(a.startTime - n), d, b, q).getTime();
                m == d && 1 == b && (l = n * a.stepWidth);
                a.cellWidth = k * a.stepWidth;
                f = Math.round(j / n);
                c = -1;
                f / 2 == Math.round(f / 2) && (c = -2, j -= n);
                if (0 < a.gridCount) for (f = c; f <= g; f++) {
                    o = j + 1.5 * n;
                    o = AmCharts.resetDateToMin(new Date(o), d, b, q).getTime();
                    k = (o - a.startTime) * a.stepWidth;
                    k = p ? k + a.y: k + a.x;
                    r = !1;
                    a.nextPeriod[m] && (r = a.checkPeriodChange(a.nextPeriod[m], 1, o, j));
                    var u = !1;
                    r ? (j = a.dateFormatsObject[a.nextPeriod[m]], u = !0) : j = a.dateFormatsObject[m];
                    a.boldPeriodBeginning || (u = !1);
                    j = AmCharts.formatDate(new Date(o), j);
                    if (f == c && !h || f == g && !i) j = " ";
                    k = new a.axisItemRenderer(this, k, j, !1, l, 0, !1, u);
                    a.pushAxisItem(k);
                    j = o
                }
            } else if (a.parseDates) {
                if (a.parseDates && a.equalSpacing) {
                    e = a.start;
                    a.startTime = a.data[a.start].time;
                    a.endTime = a.data[a.end].time;
                    a.timeDifference = a.endTime - a.startTime;
                    c = a.choosePeriod(0);
                    d = c.period;
                    b = c.count;
                    n = AmCharts.getPeriodDuration(d, b);
                    if (n < k) d = m.period,
                    b = m.count,
                    n = k;
                    m = d;
                    "WW" == m && (m = "DD");
                    a.stepWidth = a.getStepWidth(f);
                    g = Math.ceil(a.timeDifference / n) + 1;
                    j = AmCharts.resetDateToMin(new Date(a.startTime - n), d, b, q).getTime();
                    a.cellWidth = a.getStepWidth(f);
                    f = Math.round(j / n);
                    c = -1;
                    f / 2 == Math.round(f / 2) && (c = -2, j -= n);
                    f = a.start;
                    f / 2 == Math.round(f / 2) && f--;
                    0 > f && (f = 0);
                    l = a.end + 2;
                    if (l >= a.data.length) l = a.data.length;
                    q = !1;
                    for (a.end - a.start > a.gridCount && (q = !0); f < l; f++) if (o = a.data[f].time, a.checkPeriodChange(d, b, o, j)) {
                        k = a.getCoordinate(f - a.start);
                        r = !1;
                        a.nextPeriod[m] && (r = a.checkPeriodChange(a.nextPeriod[m], 1, o, j));
                        u = !1;
                        r ? (j = a.dateFormatsObject[a.nextPeriod[m]], u = !0) : j = a.dateFormatsObject[m];
                        j = AmCharts.formatDate(new Date(o), j);
                        if (f == c && !h || f == g && !i) j = " ";
                        q ? q = !1 : (k = new a.axisItemRenderer(this, k, j, void 0, void 0, void 0, void 0, u), k.graphics(), a.pushAxisItem(k));
                        j = o
                    }
                }
            } else if (a.cellWidth = a.getStepWidth(f), f < g && (g = f), e += a.start, a.stepWidth = a.getStepWidth(f), 0 < g) {
                g = Math.floor(f / g);
                f = e;
                f / 2 == Math.round(f / 2) && f--;
                0 > f && (f = 0);
                for (l = 0; f <= a.end + 2; f += g) {
                    l++;
                    j = 0 <= f && f < a.data.length ? a.data[f].category: "";
                    k = a.getCoordinate(f - e);
                    q = 0;
                    "start" == a.gridPosition && (k -= a.cellWidth / 2, q = a.cellWidth / 2);
                    if (f == c && !h || f == a.end && !i) j = " ";
                    Math.round(l / d) != l / d && (j = " ");
                    b = a.cellWidth;
                    p && (b = NaN);
                    k = new a.axisItemRenderer(this, k, j, !0, b, q, void 0, !1, q);
                    a.pushAxisItem(k)
                }
            }
            for (f = 0; f < a.data.length; f++) if (h = a.data[f]) a.parseDates && !a.equalSpacing ? (i = (h.time - a.startTime) * a.stepWidth + a.cellWidth / 2, i = p ? i + a.y: i + a.x) : i = a.getCoordinate(f - e),
            h.x[a.id] = i
        }
        h = a.guides.length;
        for (f = 0; f < h; f++) i = a.guides[f],
        d = g = c = NaN,
        i.toCategory && (d = a.chart.getCategoryIndexByValue(i.toCategory), isNaN(d) || (c = a.getCoordinate(d - e), k = new a.axisItemRenderer(this, c, "", !0, NaN, NaN, i), a.pushAxisItem(k))),
        i.category && (d = a.chart.getCategoryIndexByValue(i.category), isNaN(d) || (g = a.getCoordinate(d - e), d = (c - g) / 2, k = new a.axisItemRenderer(this, g, i.label, !0, NaN, d, i), a.pushAxisItem(k))),
        i.toDate && (a.equalSpacing ? (d = a.chart.getClosestIndex(a.data, "time", i.toDate.getTime(), !1, 0, a.data.length - 1), isNaN(d) || (c = a.getCoordinate(d - e))) : (c = (i.toDate.getTime() - a.startTime) * a.stepWidth, c = p ? c + a.y: c + a.x), k = new a.axisItemRenderer(this, c, "", !0, NaN, NaN, i), a.pushAxisItem(k)),
        i.date && (a.equalSpacing ? (d = a.chart.getClosestIndex(a.data, "time", i.date.getTime(), !1, 0, a.data.length - 1), isNaN(d) || (g = a.getCoordinate(d - e))) : (g = (i.date.getTime() - a.startTime) * a.stepWidth, g = p ? g + a.y: g + a.x), d = (c - g) / 2, k = "horizontal" == a.orientation ? new a.axisItemRenderer(this, g, i.label, !1, 2 * d, NaN, i) : new a.axisItemRenderer(this, g, i.label, !1, NaN, d, i), a.pushAxisItem(k)),
        c = new a.guideFillRenderer(this, c - g, g, i),
        g = c.graphics(),
        a.pushAxisItem(c),
        i.graphics = g,
        g.index = f,
        i.balloonText && (g.mouseover(function() {
            a.handleGuideOver(this.index)
        }), g.mouseout(function() {
            a.handleGuideOut(this.index)
        }));
        a.axisCreated = !0;
        p ? a.set.transform("...T" + a.x + ",0") : a.set.transform("...T0," + a.y);
        a.positionTitle();
        a.axisLine.set && a.axisLine.set.toFront()
    },
    choosePeriod: function(a) {
        var b = AmCharts.getPeriodDuration(this.periods[a].period, this.periods[a].count);
        return Math.ceil(this.timeDifference / b) <= this.gridCount ? this.periods[a] : a + 1 < this.periods.length ? this.choosePeriod(a + 1) : this.periods[a]
    },
    getStepWidth: function(a) {
        var b;
        if (this.startOnAxis) {
            if (b = this.axisWidth / (a - 1), 1 == a) b = this.axisWidth
        } else b = this.axisWidth / a;
        return b
    },
    getCoordinate: function(a) {
        a *= this.stepWidth;
        this.startOnAxis || (a += this.stepWidth / 2);
        return a = this.rotate ? a + this.y: a + this.x
    },
    timeZoom: function(a, b) {
        this.startTime = a;
        this.endTime = b + this.minDuration()
    },
    minDuration: function() {
        var a = AmCharts.extractPeriod(this.minPeriod);
        return AmCharts.getPeriodDuration(a.period, a.count)
    },
    checkPeriodChange: function(a, b, c, d) {
        var d = new Date(d),
        e = this.firstDayOfWeek,
        c = AmCharts.resetDateToMin(new Date(c), a, b, e).getTime(),
        a = AmCharts.resetDateToMin(d, a, b, e).getTime();
        return c != a ? !0 : !1
    },
    generateDFObject: function() {
        this.dateFormatsObject = {};
        for (var a = 0; a < this.dateFormats.length; a++) {
            var b = this.dateFormats[a];
            this.dateFormatsObject[b.period] = b.format
        }
    },
    xToIndex: function(a) {
        var b = this.data,
        c = this.chart,
        d = c.rotate,
        e = this.stepWidth,
        a = d ? a - this.y: a - this.x;
        this.parseDates && !this.equalSpacing ? (a = this.startTime + Math.round(a / e) - this.minDuration() / 2, c = c.getClosestIndex(b, "time", a, !1, this.start, this.end + 1)) : (this.startOnAxis || (a -= e / 2), c = this.start + Math.round(a / e));
        var c = AmCharts.fitToBounds(c, 0, b.length - 1),
        f;
        b[c] && (f = b[c].x[this.id]);
        d ? (f > this.height + 1 + this.y && c--, f < this.y && c++) : (f > this.width + 1 + this.x && c--, f < this.x && c++);
        return c = AmCharts.fitToBounds(c, 0, b.length - 1)
    },
    dateToCoordinate: function(a) {
        return this.parseDates && !this.equalSpacing ? (a.getTime() - this.startTime) * this.stepWidth: this.parseDates && this.equalSpacing ? this.getCoordinate(this.chart.getClosestIndex(this.data, "time", a.getTime(), !1, 0, this.data.length - 1) - this.start) : NaN
    },
    categoryToCoordinate: function(a) {
        return this.chart ? this.getCoordinate(this.chart.getCategoryIndexByValue(a) - this.start) : NaN
    },
    coordinateToDate: function(a) {
        return this.equalSpacing ? (a = this.xToIndex(a), new Date(this.data[a].time)) : new Date(this.startTime + a / this.stepWidth)
    }
});
AmCharts.RectangularAxisRenderer = AmCharts.Class({
    construct: function(a) {
        var b = a.axisThickness,
        c = a.axisColor,
        d = a.axisAlpha,
        e = a.offset,
        f = a.dx,
        g = a.dy,
        h = a.visibleAxisX,
        i = a.visibleAxisY,
        k = a.visibleAxisHeight,
        j = a.visibleAxisWidth,
        m = a.chart.container,
        n = m.set();
        this.set = n;
        "horizontal" == a.orientation ? (c = AmCharts.line(m, [0, j], [0, 0], c, d, b), this.axisWidth = a.width, "bottom" == a.position ? (a = b / 2 + e + k + i - 1, b = h) : (a = -b / 2 - e + i + g, b = f + h)) : (this.axisWidth = a.height, "right" == a.position ? (c = AmCharts.line(m, [0, 0, -f], [0, k, k - g], c, d, b), a = i + g, b = b / 2 + e + f + j + h - 1) : (c = AmCharts.line(m, [0, 0], [0, k], c, d, b), a = i, b = -b / 2 - e + h));
        n.push(c);
        n.translate(Math.round(b) + "," + Math.round(a))
    }
});
AmCharts.RectangularAxisItemRenderer = AmCharts.Class({
    construct: function(a, b, c, d, e, f, g, h, i) {
        b = Math.round(b);
        void 0 == c && (c = "");
        i || (i = 0);
        void 0 == d && (d = !0);
        var k = a.chart.fontFamily,
        j = a.fontSize;
        if (void 0 == j) j = a.chart.fontSize;
        var m = a.color;
        if (void 0 == m) m = a.chart.color;
        var n = a.chart.container,
        l = n.set();
        this.set = l;
        var o = a.axisThickness,
        r = a.axisColor,
        p = a.axisAlpha,
        q = a.tickLength,
        u = a.gridAlpha,
        x = a.gridThickness,
        A = a.gridColor,
        I = a.dashLength,
        s = a.fillColor,
        H = a.fillAlpha,
        G = a.labelsEnabled,
        C = a.labelRotation,
        S = a.counter,
        B = a.inside,
        J = a.dx,
        K = a.dy,
        Ha = a.orientation,
        ba = a.position,
        ga = a.previousCoord,
        Z = a.chart.rotate,
        M = a.visibleAxisX,
        oa = a.visibleAxisY,
        X = a.visibleAxisHeight,
        T = a.visibleAxisWidth,
        ca = a.offset,
        U, N;
        if (g) {
            G = !0;
            if (!isNaN(g.tickLength)) q = g.tickLength;
            if (void 0 != g.lineColor) A = g.lineColor;
            if (!isNaN(g.lineAlpha)) u = g.lineAlpha;
            if (!isNaN(g.dashLength)) I = g.dashLength;
            if (!isNaN(g.lineThickness)) x = g.lineThickness; ! 0 == g.inside && (B = !0);
            if (!isNaN(g.labelRotation)) C = g.labelRotation
        } else c || (u /= 3, q /= 2);
        N = "start";
        e && (N = "middle");
        var Y = C * Math.PI / 180,
        ha, y = 0,
        t = 0,
        da = 0,
        ea = ha = 0,
        za = M + J + "," + (oa + K) + "," + T + "," + X;
        "vertical" == Ha && (C = 0);
        if (G) {
            var O = AmCharts.text(n, 0, 0, c, {
                fill: m,
                "text-anchor": N,
                "font-family": k,
                "font-size": j
            }); ! 0 == h && O.attr({
                "font-weight": "bold"
            });
            l.push(O);
            ea = O.getBBox().width
        }
        if ("horizontal" == Ha) {
            if (b >= M && b <= T + 1 + M && (U = AmCharts.line(n, [b + i, b + i], [0, q], r, p, x), b + i > T + M + 1 ? (U.remove(), U = null) : l.push(U), N = AmCharts.line(n, [b, b + J, b + J], [X, X + K, K], A, u, x, I), l.push(N)), t = 0, y = b, g && 90 == C && (y -= j / 2 + 3), !1 == d ? (N = "start", Z || (t = "bottom" == ba ? B ? t + q: t - q: B ? t - q: t + q, y += 3, e && (y += e / 2, N = "middle"))) : N = "middle", 1 == S && 0 < H && !g && (ha = b - ga, fill = AmCharts.rect(n, ha, a.height, [s], [H]), fill.translate(b - ha + J + "," + K), fill.attr({
                "clip-rect": za
            }), l.push(fill)), "bottom" == ba ? (t += X + j / 2 + ca, B ? 0 < C ? (t = X - ea / 2 * Math.sin(Y) - q - 3, y += ea / 2 * Math.cos(Y)) : t -= q + j + 3 + 3 : 0 < C ? (t = X + ea / 2 * Math.sin(Y) + q + 3, y -= ea / 2 * Math.cos(Y)) : t += q + o + 3 + 3) : (t += K + j / 2 - ca, y += J, B ? 0 < C ? (t = ea / 2 * Math.sin(Y) + q + 3, y -= ea / 2 * Math.cos(Y)) : t += q + 3 : 0 < C ? (t = -(ea / 2) * Math.sin(Y) - q - 3, y += ea / 2 * Math.cos(Y)) : t -= q + j + 3 + o + 3), "bottom" == ba ? ha = (B ? X - q - 1 : X + o - 1) + ca: (da = J, ha = (B ? K: K - q - o + 1) - ca), f && (y += f), K = y, 0 < C && (K += ea / 2 * Math.cos(Y)), O && (K > M + T + 1 || K < M)) l.exclude(O),
            O.remove(),
            O = null
        } else {
            b >= oa && b <= X + 1 + oa && (U = AmCharts.line(n, [0, q], [b + i, b + i], r, p, x), b + i > X + oa + 1 ? (U.remove(), U = null) : l.push(U), N = AmCharts.line(n, [0, J, T + J], [b, b + K, b + K], A, u, x, I), l.push(N));
            N = "end";
            if (!0 == B && "left" == ba || !1 == B && "right" == ba) N = "start";
            t = b - j / 2;
            1 == S && 0 < H && !g && (M = b - ga, fill = AmCharts.rect(n, a.width, M, [s], [H]), fill.translate(J + "," + (b - M + K)), fill.attr({
                "clip-rect": za
            }), l.push(fill));
            t += j / 2;
            "right" == ba ? (y += J + T + ca, t += K, !0 == B ? (y -= q + 4, f || (t -= j / 2 + 3)) : (y += q + 4 + o, t -= 2)) : !0 == B ? (y += q + 4 - ca, f || (t -= j / 2 + 3), g && (y += J, t += K)) : (y += -q - o - 4 - 2 - ca, t -= 2);
            U && ("right" == ba ? (da += J + ca + T, ha += K, da = !0 == B ? da - o: da + o) : (da -= ca, !0 != B && (da -= q + o)));
            f && (t += f);
            T = oa - 3;
            "right" == ba && (T += K);
            if (O && (t > X + oa + 1 || t < T)) l.exclude(O),
            O.remove(),
            O = null
        }
        U && U.translate(da + "," + ha); ! 1 == a.visible && (U && (l.exclude(U), U.remove()), O && (l.exclude(O), O.remove(), O = null));
        O && (O.attr({
            "text-anchor": N
        }), O.transform("r" + -C), O.transform("...T" + y + "," + t), a.allLabels.push(O));
        a.counter = 0 == S ? 1 : 0;
        a.previousCoord = b
    },
    graphics: function() {
        return this.set
    }
});
AmCharts.RectangularAxisGuideFillRenderer = AmCharts.Class({
    construct: function(a, b, c, d) {
        var e = a.orientation,
        f = 0,
        g = d.fillAlpha,
        h = a.chart.container,
        i = a.dx,
        k = a.dy;
        isNaN(b) && (b = 4, f = 2, g = 0);
        this.set = h.set();
        var j = d.fillColor;
        void 0 == j && (j = "#000000");
        0 > b && "object" == typeof j && (j = j.join(",").split(",").reverse());
        isNaN(g) && (g = 0);
        d = a.visibleAxisX + i + "," + (a.visibleAxisY + k) + "," + a.visibleAxisWidth + "," + a.visibleAxisHeight;
        "vertical" == e ? (a = AmCharts.rect(h, a.width, b, j, g), a.translate(i + "," + (c - f + k))) : (a = AmCharts.rect(h, b, a.height, j, g), a.translate(c - f + i + "," + k));
        a.attr({
            "clip-rect": d
        });
        this.set.push(a)
    },
    graphics: function() {
        return this.set
    }
});
AmCharts.RadarAxisRenderer = AmCharts.Class({
    construct: function(a) {
        var b = a.chart,
        c = a.axisThickness,
        d = a.axisColor,
        e = a.axisAlpha,
        f = a.x,
        g = a.y;
        this.set = b.container.set();
        var h = a.axisTitleOffset,
        i = a.radarCategoriesEnabled,
        k = a.chart.fontFamily,
        j = a.fontSize;
        if (void 0 == j) j = a.chart.fontSize;
        var m = a.color;
        if (void 0 == m) m = a.chart.color;
        if (b) {
            this.axisWidth = a.height;
            for (var a = b.chartData,
            n = a.length,
            l = 0; l < n; l++) {
                var o = 180 - 360 / n * l,
                r = f + this.axisWidth * Math.sin(o / 180 * Math.PI),
                p = g + this.axisWidth * Math.cos(o / 180 * Math.PI);
                this.set.push(AmCharts.line(b.container, [f, r], [g, p], d, e, c));
                if (i) {
                    var r = "start",
                    p = f + (this.axisWidth + h) * Math.sin(o / 180 * Math.PI),
                    q = g + (this.axisWidth + h) * Math.cos(o / 180 * Math.PI);
                    if (180 == o || 0 == o) r = "middle",
                    p -= 5;
                    0 > o && (r = "end", p -= 10);
                    180 == o && (q -= 5);
                    0 == o && (q += 5);
                    o = AmCharts.text(b.container, p + 5, q, a[l].category, {
                        fill: m,
                        "text-anchor": r,
                        "font-family": k,
                        "font-size": j
                    });
                    this.set.push(o);
                    o.getBBox()
                }
            }
        }
    }
});
AmCharts.RadarAxisItemRenderer = AmCharts.Class({
    construct: function(a, b, c, d, e, f, g) {
        void 0 == c && (c = "");
        var h = a.chart.fontFamily,
        i = a.fontSize;
        if (void 0 == i) i = a.chart.fontSize;
        var k = a.color;
        if (void 0 == k) k = a.chart.color;
        var j = a.chart.container;
        this.set = j.set();
        var m = a.axisColor,
        n = a.axisAlpha,
        l = a.tickLength,
        o = a.gridAlpha,
        r = a.gridThickness,
        p = a.gridColor,
        q = a.dashLength,
        u = a.fillColor,
        x = a.fillAlpha,
        A = a.labelsEnabled,
        d = a.counter,
        I = a.inside,
        s = a.gridType,
        b = b - a.height,
        H, e = a.x,
        f = a.y;
        if (g) {
            A = !0;
            if (!isNaN(g.tickLength)) l = g.tickLength;
            if (void 0 != g.lineColor) p = g.lineColor;
            if (!isNaN(g.lineAlpha)) o = g.lineAlpha;
            if (!isNaN(g.dashLength)) q = g.dashLength;
            if (!isNaN(g.lineThickness)) r = g.lineThickness; ! 0 == g.inside && (I = !0)
        } else c || (o /= 3, l /= 2);
        var G = "end",
        C = -1;
        I && (G = "start", C = 1);
        if (A) {
            var S = AmCharts.text(j, e + (l + 3) * C, b, c, {
                fill: k,
                "text-anchor": G,
                "font-family": h,
                "font-size": i
            });
            this.set.push(S);
            H = AmCharts.line(j, [e, e + l * C], [b, b], m, n, r);
            this.set.push(H)
        }
        b = a.y - b;
        if ("polygons" == s) {
            for (var B = [], J = [], K = a.data.length, c = 0; c < K; c++) h = 180 - 360 / K * c,
            B.push(b * Math.sin(h / 180 * Math.PI)),
            J.push(b * Math.cos(h / 180 * Math.PI));
            B.push(B[0]);
            J.push(J[0]);
            c = AmCharts.line(j, B, J, p, o, r, q)
        } else c = AmCharts.circle(j, b, "#FFFFFF", 0, r, p, o);
        c.translate(e + "," + f);
        this.set.push(c);
        if (1 == d && 0 < x && !g) {
            g = a.previousCoord;
            if ("polygons" == s) {
                for (c = K; 0 <= c; c--) h = 180 - 360 / K * c,
                B.push(g * Math.sin(h / 180 * Math.PI)),
                J.push(g * Math.cos(h / 180 * Math.PI));
                B = AmCharts.polygon(j, B, J, [u], [x])
            } else B = AmCharts.wedge(j, 0, 0, 0, -360, b, b, g, 0, {
                fill: u,
                "fill-opacity": x,
                stroke: 0,
                "stroke-opacity": 0,
                "stroke-width": 0
            });
            this.set.push(B);
            B.translate(e + "," + f)
        } ! 1 == a.visible && (H && H.hide(), S && S.hide());
        a.counter = 0 == d ? 1 : 0;
        a.previousCoord = b
    },
    graphics: function() {
        return this.set
    }
});
AmCharts.RadarAxisGuideFillRenderer = AmCharts.Class({
    construct: function(a, b, c, d) {
        var e = a.chart.container,
        f = d.fillAlpha,
        g = d.fillColor,
        c = a.y - (c - a.height) - b,
        h = c + b,
        i = -d.angle,
        d = -d.toAngle;
        isNaN(i) && (i = 0);
        isNaN(d) && (d = -360);
        this.set = e.set();
        void 0 == g && (g = "#000000");
        isNaN(f) && (f = 0);
        if ("polygons" == a.gridType) {
            for (var b = [], d = [], k = a.data.length, j = 0; j < k; j++) i = 180 - 360 / k * j,
            b.push(c * Math.sin(i / 180 * Math.PI)),
            d.push(c * Math.cos(i / 180 * Math.PI));
            b.push(b[0]);
            d.push(d[0]);
            for (j = k; 0 <= j; j--) i = 180 - 360 / k * j,
            b.push(h * Math.sin(i / 180 * Math.PI)),
            d.push(h * Math.cos(i / 180 * Math.PI));
            this.fill = AmCharts.polygon(e, b, d, [g], [f])
        } else h = c - Math.abs(b),
        this.fill = AmCharts.wedge(e, 0, 0, i, d - i, c, c, h, 0, {
            fill: g,
            "fill-opacity": f,
            stroke: 0,
            "stroke-opacity": 0,
            "stroke-width": 0
        });
        this.set.push(this.fill);
        this.fill.translate(a.x + "," + a.y)
    },
    graphics: function() {
        return this.set
    }
});
AmCharts.AmGraph = AmCharts.Class({
    construct: function() {
        this.createEvents("rollOverGraphItem", "rollOutGraphItem", "clickGraphItem", "doubleClickGraphItem");
        this.type = "line";
        this.stackable = !0;
        this.columnCount = 1;
        this.columnIndex = 0;
        this.centerCustomBullets = this.showBalloon = !0;
        this.maxBulletSize = 50;
        this.minBulletSize = 0;
        this.balloonText = "[[value]]";
        this.hidden = this.scrollbar = this.animationPlayed = !1;
        this.columnWidth = 0.8;
        this.pointPosition = "middle";
        this.depthCount = 1;
        this.includeInMinMax = !0;
        this.negativeBase = 0;
        this.visibleInLegend = !0;
        this.showAllValueLabels = !1;
        this.showBalloonAt = "close";
        this.lineThickness = 1;
        this.dashLength = 0;
        this.connect = !0;
        this.lineAlpha = 1;
        this.bullet = "none";
        this.bulletBorderThickness = 2;
        this.bulletAlpha = this.bulletBorderAlpha = 1;
        this.bulletSize = 8;
        this.hideBulletsCount = this.bulletOffset = 0;
        this.labelPosition = "top";
        this.cornerRadiusTop = 0;
        this.cursorBulletAlpha = 1;
        this.gradientOrientation = "vertical";
        this.dy = this.dx = 0;
        this.periodValue = ""
    },
    draw: function() {
        this.container = this.chart.container;
        this.destroy();
        this.set = this.container.set();
        this.ownColumns = [];
        this.allBullets = [];
        this.objectsToAddListeners = [];
        this.positiveObjectsToClip = [];
        this.negativeObjectsToClip = [];
        this.animationArray = [];
        if (this.data && 0 < this.data.length) {
            var a = !1;
            if ("xy" == this.chartType) this.xAxis.axisCreated && this.yAxis.axisCreated && (a = !0);
            else if (this.valueAxis.axisCreated) this.columnsArray = [],
            a = !0; ! this.hidden && a && this.createGraph()
        }
    },
    createGraph: function() {
        if ("inside" == this.labelPosition) this.labelPosition = "bottom";
        this.sDur = this.chart.startDuration;
        this.sEff = this.chart.startEffect;
        this.startAlpha = this.chart.startAlpha;
        this.seqAn = this.chart.sequencedAnimation;
        this.baseCoord = this.valueAxis.baseCoord;
        if (!this.fillColors) this.fillColors = [this.lineColor];
        if (void 0 == this.fillAlphas) this.fillAlphas = 0;
        if (void 0 == this.bulletColor) this.bulletColor = this.lineColor,
        this.bulletColorNegative = this.negativeLineColor;
        if (void 0 == this.bulletAlpha) this.bulletAlpha = this.lineAlpha;
        if (!this.bulletBorderColor) this.bulletBorderAlpha = 0;
        if (!isNaN(this.valueAxis.min) && !isNaN(this.valueAxis.max)) {
            switch (this.chartType) {
            case "serial":
                this.createSerialGraph();
                break;
            case "radar":
                this.createRadarGraph();
                break;
            case "xy":
                this.createXYGraph()
            }
            this.animationPlayed = !0
        }
    },
    createXYGraph: function() {
        var a = [],
        b = [];
        this.pmh = this.yAxis.visibleAxisHeight + 1;
        this.pmw = this.xAxis.visibleAxisWidth + 1;
        this.pmx = this.yAxis.visibleAxisX;
        this.pmy = this.yAxis.visibleAxisY;
        for (var c = this.start; c <= this.end; c++) {
            var d = this.data[c].axes[this.xAxis.id].graphs[this.id],
            e = d.values.x,
            f = d.values.y,
            g = this.xAxis.getCoordinate(e),
            h = this.yAxis.getCoordinate(f); ! isNaN(e) && !isNaN(f) && (a.push(g), b.push(h), (e = this.createBullet(d, g, h, c)) || (e = 0), this.labelText && this.positionLabel(this.createLabel(d, g, h), this.labelPosition, e))
        }
        this.drawLineGraph(a, b);
        this.launchAnimation()
    },
    createRadarGraph: function() {
        for (var a = this.valueAxis.stackType,
        b = [], c = [], d, e, f = this.start; f <= this.end; f++) {
            var g = this.data[f].axes[this.valueAxis.id].graphs[this.id],
            h;
            h = "none" == a || "3d" == a ? g.values.value: g.values.close;
            if (isNaN(h)) this.drawLineGraph(b, c),
            b = [],
            c = [];
            else {
                var i = this.y - (this.valueAxis.getCoordinate(h) - this.height),
                k = 180 - 360 / (this.end - this.start + 1) * f;
                h = i * Math.sin(k / 180 * Math.PI);
                i *= Math.cos(k / 180 * Math.PI);
                b.push(h);
                c.push(i); (k = this.createBullet(g, h, i, f)) || (k = 0);
                this.labelText && this.positionLabel(this.createLabel(g, h, i), this.labelPosition, k);
                isNaN(d) && (d = h);
                isNaN(e) && (e = i)
            }
        }
        b.push(d);
        c.push(e);
        this.drawLineGraph(b, c);
        this.set.translate(this.x, this.y);
        this.launchAnimation();
        if (a = this.objectsToAddListeners) for (b = 0; b < a.length; b++) this.addHoverListeners(a[b]),
        this.addClickListeners(a[b])
    },
    positionLabel: function(a, b, c) {
        var d = a.attr("x"),
        e = a.attr("y"),
        f = a.getBBox();
        switch (b) {
        case "left":
            d -= (f.width + c) / 2 + 5;
            break;
        case "top":
            e -= (c + f.height) / 2 + 3;
            break;
        case "right":
            d += (f.width + c) / 2 + 5;
            break;
        case "bottom":
            e += (c + f.height) / 2 + 3
        }
        a.attr({
            x: d,
            y: e
        })
    },
    createSerialGraph: function() {
        var a = this,
        b = a.id,
        c = a.index,
        d = a.data,
        e = a.chart.container,
        f = a.valueAxis,
        g = a.type,
        h = a.columnWidth,
        i = a.width,
        k = a.height,
        j = a.x,
        m = a.y,
        n = a.rotate,
        l = a.columnCount,
        o = AmCharts.toCoordinate(a.cornerRadiusTop, h / 2),
        r = a.connect,
        p = [],
        q = [],
        u,
        x,
        A = a.chart.graphs.length,
        I,
        s = a.dx / a.depthCount,
        H = a.dy / a.depthCount,
        G = f.stackType,
        C = a.labelPosition,
        S = a.start,
        B = a.end,
        J = a.scrollbar,
        K = a.categoryAxis,
        Ha = a.baseCoord,
        ba = a.negativeBase,
        ga = a.columnIndex,
        Z = a.lineThickness,
        M = a.lineAlpha,
        oa = a.lineColor,
        X = a.dashLength,
        T = a.set;
        "above" == C && (C = "top");
        "below" == C && (C = "bottom");
        var ca, U = 270;
        "horizontal" == a.gradientOrientation && (U = 0);
        var N = a.chart.columnSpacing,
        Y = K.cellWidth,
        ha = (Y * h - l) / l;
        N > ha && (N = ha);
        var y, t, da, ea = k + 1,
        za = i + 1,
        O = j,
        ib = m,
        Sa, Ta, Ia, Ma, pb = a.fillColors,
        Ja = a.negativeFillColors,
        xa = a.negativeLineColor,
        ya = a.fillAlphas,
        Ka = a.negativeFillAlphas;
        "object" == typeof ya && (ya = ya[0]);
        "object" == typeof Ka && (Ka = Ka[0]);
        var Na = f.getCoordinate(f.min);
        f.logarithmic && (Na = f.getCoordinate(f.minReal));
        a.minCoord = Na;
        if (a.resetBullet) a.bullet = "none";
        if (!J && ("line" == g || "smoothedLine" == g || "step" == g)) {
            if (1 == d.length && "step" != g && "none" == a.bullet) a.bullet = "round",
            a.resetBullet = !0;
            if (Ja || void 0 != xa) {
                var Aa = ba;
                if (Aa > f.max) Aa = f.max;
                if (Aa < f.min) Aa = f.min;
                if (f.logarithmic) Aa = f.minReal;
                var pa = f.getCoordinate(Aa),
                jb = f.getCoordinate(f.max);
                n ? (ea = k, za = Math.abs(jb - pa), Sa = k, Ta = Math.abs(Na - pa), Ma = Ia = m, f.reversed ? (O = j, Ia = pa) : (O = pa, Ia = j)) : (za = i, ea = Math.abs(jb - pa), Ta = i, Sa = Math.abs(Na - pa), Ia = O = j, f.reversed ? (Ma = m, ib = pa) : Ma = pa)
            }
        }
        a.pmx = O;
        a.pmy = ib;
        a.pmh = ea;
        a.pmw = za;
        a.nmx = Ia;
        a.nmy = Ma;
        a.nmh = Sa;
        a.nmw = Ta;
        h = "column" == g ? (Y * h - N * (l - 1)) / l: Y * h;
        1 > h && (h = 1);
        var L;
        if ("line" == g || "step" == g || "smoothedLine" == g) {
            if (0 < S) for (L = S - 1; - 1 < L; L--) if (y = d[L], t = y.axes[f.id].graphs[b], da = t.values.value) {
                S = L;
                break
            }
            if (B < d.length - 1) for (L = B + 1; L < d.length; L++) if (y = d[L], t = y.axes[f.id].graphs[b], da = t.values.value) {
                B = L;
                break
            }
        }
        B < d.length - 1 && B++;
        var ja = [],
        ka = [],
        Ua = !1;
        if ("line" == g || "step" == g || "smoothedLine" == g) if (a.stackable && "regular" == G || "100%" == G) Ua = !0;
        for (L = S; L <= B; L++) {
            y = d[L];
            t = y.axes[f.id].graphs[b];
            t.index = L;
            var Va = "";
            t.url && (Va = "pointer");
            var qa, Ba = NaN,
            w = NaN,
            v = NaN,
            P = NaN,
            Q = NaN,
            Oa = NaN,
            Ca = NaN,
            Pa = NaN,
            Da = NaN,
            $ = NaN,
            aa = NaN,
            ra = NaN,
            sa = NaN,
            R = NaN,
            z = void 0,
            ta = pb,
            Wa = ya,
            W = oa;
            void 0 != t.color && (ta = [t.color]);
            if (t.fillColors) ta = t.fillColors;
            isNaN(t.alpha) || (Wa = [t.alpha]);
            var la = t.values;
            if (f.recalculateToPercents) la = t.percents;
            if (la) {
                R = !a.stackable || "none" == G || "3d" == G ? la.value: la.close;
                if ("candlestick" == g || "ohlc" == g) var R = la.close,
                Xa = la.low,
                Ca = f.getCoordinate(Xa),
                Ya = la.high,
                Da = f.getCoordinate(Ya);
                var fa = la.open,
                v = f.getCoordinate(R);
                isNaN(fa) || (Q = f.getCoordinate(fa));
                if (!J) switch (a.showBalloonAt) {
                case "close":
                    t.y = v;
                    break;
                case "open":
                    t.y = Q;
                    break;
                case "high":
                    t.y = Da;
                    break;
                case "low":
                    t.y = Ca
                }
                var Ba = y.x[K.id],
                La = Y / 2,
                Za = Y / 2;
                "start" == a.pointPosition && (Ba -= Y / 2, La = 0, Za = Y);
                if (!J) t.x = Ba;
                n ? (w = v, P = Q, Q = v = Ba, isNaN(fa) && (P = Ha), Oa = Ca, Pa = Da) : (P = w = Ba, isNaN(fa) && (Q = Ha));
                switch (g) {
                case "line":
                    if (isNaN(R)) r || (a.drawLineGraph(p, q, ja, ka), p = [], q = [], ja = [], ka = []);
                    else {
                        if (R < ba) t.isNegative = !0;
                        p.push(w);
                        q.push(v);
                        $ = w;
                        aa = v;
                        ra = w;
                        sa = v;
                        Ua && (ja.push(P), ka.push(Q))
                    }
                    break;
                case "smoothedLine":
                    if (isNaN(R)) r || (a.drawSmoothedGraph(p, q, ja, ka), p = [], q = [], ja = [], ka = []);
                    else {
                        if (R < ba) t.isNegative = !0;
                        p.push(w);
                        q.push(v);
                        $ = w;
                        aa = v;
                        ra = w;
                        sa = v;
                        Ua && (ja.push(P), ka.push(Q))
                    }
                    break;
                case "step":
                    if (isNaN(R)) r || (a.drawLineGraph(p, q, ja, ka), p = [], q = [], ja = [], ka = []);
                    else {
                        if (R < ba) t.isNegative = !0;
                        n ? (u && r && (p.push(u), q.push(v - La)), q.push(v - La), p.push(w), q.push(v + Za), p.push(w)) : (x && r && (q.push(x), p.push(w - La)), p.push(w - La), q.push(v), p.push(w + Za), q.push(v));
                        u = w;
                        x = v;
                        $ = w;
                        aa = v;
                        ra = w;
                        sa = v
                    }
                    break;
                case "column":
                    if (!isNaN(R)) {
                        if (R < ba) t.isNegative = !0,
                        Ja && (ta = Ja),
                        void 0 != xa && (W = xa);
                        var kb = f.min,
                        lb = f.max;
                        if (! (R < kb && (fa < kb || void 0 == fa) || R > lb && fa > lb)) if (n) {
                            if ("3d" == G) var D = v - 0.5 * (h + N) + N / 2 + H * ga,
                            F = P + s * ga;
                            else D = v - (l / 2 - ga) * (h + N) + N / 2,
                            F = P;
                            var E = h,
                            $ = w,
                            aa = D + h / 2,
                            ra = w,
                            sa = D + h / 2;
                            D + E > m + k && (E = m + k - D);
                            D < m && (E -= m - D, D = m);
                            var ia = w - P,
                            qb = F,
                            F = AmCharts.fitToBounds(F, j, j + i),
                            ia = ia + (qb - F),
                            ia = AmCharts.fitToBounds(ia, j - F, j + i - F);
                            D < m + k && 0 < E && (z = new AmCharts.Cuboid(e, ia, E, s, H, ta, ya, Z, W, M, U, o), z.y(D), z.x(F), "bottom" != C && (C = "right", 0 > R ? C = "left": ($ += a.dx, "regular" != G && "100%" != G && (aa += a.dy))))
                        } else {
                            "3d" == G ? (F = w - 0.5 * (h + N) + N / 2 + s * ga, D = Q + H * ga) : (F = w - (l / 2 - ga) * (h + N) + N / 2, D = Q);
                            E = h;
                            $ = F + h / 2;
                            aa = v;
                            ra = F + h / 2;
                            sa = v;
                            F + E > j + i + ga * s && (E = j + i - F + ga * s);
                            F < j && (E -= j - F, F = j);
                            var ia = v - Q,
                            rb = D,
                            D = AmCharts.fitToBounds(D, m, m + k),
                            ia = ia + (rb - D),
                            ia = AmCharts.fitToBounds(ia, m - D, m + k - D);
                            F < j + i + ga * s && 0 < E && (z = new AmCharts.Cuboid(e, E, ia, s, H, ta, ya, Z, W, a.lineAlpha, U, o), z.y(D), z.x(F), 0 > R ? C = "bottom": ("regular" != G && "100%" != G && ($ += a.dx), aa += a.dy))
                        }
                        if (z) {
                            if (!J) {
                                "none" == G && (I = n ? (a.end + 1 - L) * A - c: A * L + c);
                                "3d" == G && (n ? (I = (A - c) * (a.end + 1 - L), aa = D + h / 2) : (I = (A - c) * (L + 1), $ += s * a.columnIndex), aa += H * a.columnIndex);
                                if ("regular" == G || "100%" == G) C = "middle",
                                I = n ? 0 < la.value ? (a.end + 1 - L) * A + c: (a.end + 1 - L) * A - c: 0 < la.value ? A * L + c: A * L - c;
                                a.columnsArray.push({
                                    column: z,
                                    depth: I
                                });
                                t.x = n ? z.getY() + E / 2 : z.getX() + E / 2;
                                a.ownColumns.push(z);
                                if (0 == a.dx && 0 == a.dy && 0 < a.sDur && !a.animationPlayed) {
                                    var ua, va;
                                    n ? (qa = w - P, ua = w, va = P) : (qa = v - Q, ua = v, va = Q);
                                    a.seqAn ? (z.set.hide(), a.animationArray.push({
                                        obj: z.set,
                                        fh: qa,
                                        ip: va,
                                        fp: ua
                                    }), ca = setTimeout(function() {
                                        a.animate.call(a)
                                    },
                                    1E3 * a.sDur / (a.end - a.start + 1) * (L - a.start)), a.timeOuts.push(ca)) : a.animate(z.set, qa, va, ua)
                                }
                                for (var wa = z.set,
                                ma = 0; ma < wa.length; ma++) wa[ma].dItem = t,
                                wa[ma].attr({
                                    cursor: Va
                                });
                                a.objectsToAddListeners.push(z.set)
                            }
                            T.push(z.set);
                            t.columnSprite = wa
                        }
                    }
                    break;
                case "candlestick":
                    if (!isNaN(fa) && !isNaN(Ya) && !isNaN(Xa) && !isNaN(R)) {
                        var Qa, $a;
                        if (R < fa) t.isNegative = !0,
                        Ja && (ta = Ja),
                        Ka && (Wa = Ka),
                        void 0 != xa && (W = xa);
                        if (n) {
                            if (D = v - h / 2, F = P, E = h, D + E > m + k && (E = m + k - D), D < m && (E -= m - D, D = m), D < m + k && 0 < E) {
                                var ab, bb;
                                R > fa ? (ab = [w, Pa], bb = [P, Oa]) : (ab = [P, Pa], bb = [w, Oa]);
                                v < m + k && v > m && (Qa = AmCharts.line(e, ab, [v, v], W, M, Z), $a = AmCharts.line(e, bb, [v, v], W, M, Z));
                                1 > Math.abs(w - P) ? (z = new AmCharts.line(e, [0, 0], [0, E], W, M, 1), z.attr({
                                    x: F,
                                    y: D
                                })) : (z = new AmCharts.Cuboid(e, w - P, E, s, H, ta, ya, Z, W, M, U, o), z.y(D), z.x(F))
                            }
                        } else if (F = w - h / 2, D = Q + Z / 2, E = h, F + E > j + i && (E = j + i - F), F < j && (E -= j - F, F = j), F < j + i && 0 < E) {
                            1 > Math.abs(v - Q) ? (z = new AmCharts.line(e, [0, E], [0, 0], W, M, 1), z.translate(F + "," + D)) : (z = new AmCharts.Cuboid(e, E, v - Q, s, H, ta, Wa, Z, W, M, U, o), z.x(F), z.y(D));
                            var cb, db;
                            R > fa ? (cb = [v, Da], db = [Q, Ca]) : (cb = [Q, Da], db = [v, Ca]);
                            w < j + i && w > j && (Qa = AmCharts.line(e, [w, w], cb, W, M, Z), $a = AmCharts.line(e, [w, w], db, W, M, Z))
                        }
                        if (z && (z.set ? T.push(z.set) : T.push(z), Qa && (T.push(Qa), T.push($a)), $ = w, aa = v, ra = w, sa = v, !J)) {
                            if (z.getX) var mb = z.getX(),
                            nb = z.getY();
                            else mb = F,
                            nb = D;
                            t.x = n ? nb + E / 2 : mb + E / 2;
                            0 == a.dx && 0 == a.dy && 0 < a.sDur && !a.animationPlayed && (n ? (qa = w - P, ua = w, va = P) : (qa = v - Q, ua = v, va = Q), a.seqAn ? (z.set.show(), a.animationArray.push({
                                obj: z.set,
                                fh: qa,
                                ip: va,
                                fp: ua
                            }), ca = setTimeout(function() {
                                a.animate.call(a)
                            },
                            1E3 * a.sDur / (a.end - a.start + 1) * (L - a.start)), a.timeOuts.push(ca)) : a.animate(z.set, qa, va, ua));
                            if (z.set) {
                                wa = z.set;
                                for (ma = 0; ma < wa.length; ma++) wa[ma].dItem = t,
                                wa[ma].attr({
                                    cursor: Va
                                });
                                a.objectsToAddListeners.push(z.set)
                            }
                        }
                    }
                    break;
                case "ohlc":
                    if (!isNaN(fa) && !isNaN(Ya) && !isNaN(Xa) && !isNaN(R)) {
                        if (R < fa) t.isNegative = !0,
                        void 0 != xa && (W = xa);
                        var eb, fb, gb;
                        n ? (fb = AmCharts.line(e, [P, P], [v - h / 2, v], W, M, Z, X), eb = AmCharts.line(e, [Oa, Pa], [v, v], W, M, Z, X), gb = AmCharts.line(e, [w, w], [v, v + h / 2], W, M, Z, X)) : (fb = AmCharts.line(e, [w - h / 2, w], [Q, Q], W, M, Z, X), eb = AmCharts.line(e, [w, w], [Ca, Da], W, M, Z, X), gb = AmCharts.line(e, [w, w + h / 2], [v, v], W, M, Z, X));
                        T.push(fb);
                        T.push(eb);
                        T.push(gb);
                        $ = w;
                        aa = v;
                        ra = w;
                        sa = v
                    }
                }
                if (!J && !isNaN(R)) {
                    var ob = a.hideBulletsCount;
                    if (a.end - a.start <= ob || 0 == ob) {
                        var Ea = a.createBullet(t, ra, sa, L);
                        Ea || (Ea = 0);
                        if (a.labelText) {
                            var V = a.createLabel(t, 0, 0);
                            "column" == g && (n ? "right" == C || "bottom" == C ? V.attr({
                                width: i
                            }) : V.attr({
                                width: w - P
                            }) : V.attr({
                                width: Y
                            }));
                            var Fa = 0,
                            Ra = 0,
                            na = V.getBBox(),
                            Ga = na.width,
                            hb = na.height;
                            switch (C) {
                            case "left":
                                Fa = -(Ga / 2 + Ea / 2 + 3);
                                break;
                            case "top":
                                Ra = -(hb / 2 + Ea / 2 + 3);
                                break;
                            case "right":
                                Fa = Ea / 2 + 2 + Ga / 2;
                                break;
                            case "bottom":
                                n && "column" == g ? 0 > R ? (Fa = -Ga / 2 - 8, $ = P) : Fa = P + 6 + Ga / 2 - $: (Ra = Ea / 2 + hb / 2, V.x = -(Ga / 2 + 2));
                                break;
                            case "middle":
                                "column" == g && (n ? (Fa = -(w - P) / 2 - s, Math.abs(w - P) < Ga && !a.showAllValueLabels && (V.remove(), a.set.exclude(V), V = null)) : (Ra = -(v - Q) / 2 + 1 - H, Math.abs(v - Q) < hb && !a.showAllValueLabels && (V.remove(), a.set.exclude(V), V = null)))
                            }
                            if (V) if (V.attr({
                                x: $ + Fa,
                                y: aa + Ra
                            }), na = V.getBBox(), n) {
                                if (na.y < m || na.y + na.height > m + k) V.remove(),
                                a.set.exclude(V),
                                V = null
                            } else if (na.x < j || na.x + na.width > j + i) V.remove(),
                            a.set.exclude(V),
                            V = null
                        }
                    }
                }
            }
        }
        if ("line" == g || "step" == g || "smoothedLine" == g)"smoothedLine" == g ? a.drawSmoothedGraph(p, q, ja, ka) : a.drawLineGraph(p, q, ja, ka),
        J || a.launchAnimation()
    },
    createLabel: function(a, b, c) {
        var d = this.chart,
        e = this.color;
        if (void 0 == e) e = d.color;
        var f = this.fontSize;
        if (void 0 == f) f = d.fontSize;
        a = d.formatString(this.labelText, a, this);
        a = AmCharts.cleanFromEmpty(a);
        b = AmCharts.text(this.container, b, c, a, {
            fill: e,
            "font-family": d.fontFamily,
            "font-size": f
        });
        this.set.push(b);
        this.allBullets.push(b);
        return b
    },
    setPositiveClipRect: function(a) {
        a.attr({
            "clip-rect": this.pmx + "," + this.pmy + "," + this.pmw + "," + this.pmh
        })
    },
    setNegativeClipRect: function(a) {
        a.attr({
            "clip-rect": this.nmx + "," + this.nmy + "," + this.nmw + "," + this.nmh
        })
    },
    drawLineGraph: function(a, b, c, d) {
        if (1 < a.length) {
            var e = AmCharts.line(this.container, a, b, this.lineColor, this.lineAlpha, this.lineThickness, this.dashLength);
            this.positiveObjectsToClip.push(e);
            this.set.push(e);
            void 0 != this.negativeLineColor && (e = AmCharts.line(this.container, a, b, this.negativeLineColor, this.lineAlpha, this.lineThickness, this.dashLength), this.negativeObjectsToClip.push(e), this.set.push(e));
            if (void 0 != this.fillAlphas && 0 != this.fillAlphas) {
                var e = a.join(";").split(";"),
                f = b.join(";").split(";");
                "serial" == this.chartType && (0 < c.length ? (c.reverse(), d.reverse(), e = a.concat(c), f = b.concat(d)) : this.rotate ? (f.push(f[f.length - 1]), e.push(this.baseCoord), f.push(f[0]), e.push(this.baseCoord), f.push(f[0]), e.push(e[0])) : (e.push(e[e.length - 1]), f.push(this.baseCoord), e.push(e[0]), f.push(this.baseCoord), e.push(a[0]), f.push(f[0])));
                a = AmCharts.polygon(this.container, e, f, this.fillColors, this.fillAlphas);
                this.set.push(a);
                this.positiveObjectsToClip.push(a);
                if (this.negativeFillColors || void 0 != this.negativeLineColor) {
                    a = this.fillAlphas;
                    if (this.negativeFillAlphas) a = this.negativeFillAlphas;
                    b = this.negativeLineColor;
                    if (this.negativeFillColors) b = this.negativeFillColors;
                    e = AmCharts.polygon(this.container, e, f, b, a);
                    this.set.push(e);
                    this.negativeObjectsToClip.push(e)
                }
            }
        }
    },
    drawSmoothedGraph: function(a, b) {
        if (1 < a.length) {
            var c = new AmCharts.Bezier(this.container, a, b, this.lineColor, this.lineAlpha, this.lineThickness, NaN, NaN, this.dashLength);
            this.positiveObjectsToClip.push(c.path);
            this.set.push(c.path);
            void 0 != this.negativeLineColor && (c = new AmCharts.Bezier(this.container, a, b, this.negativeLineColor, this.lineAlpha, this.lineThickness, NaN, NaN, this.dashLength), this.set.push(c.path), this.negativeObjectsToClip.push(c.path));
            if (0 < this.fillAlphas) {
                c = [];
                this.rotate ? (c.push("L", this.baseCoord, b[b.length - 1]), c.push("L", this.baseCoord, b[0])) : (c.push("L", a[a.length - 1], this.baseCoord), c.push("L", a[0], this.baseCoord));
                c.push("L", a[0], b[0]);
                var d = new AmCharts.Bezier(this.container, a, b, NaN, NaN, 0, this.fillColors, this.fillAlphas, this.dashLength, c);
                this.positiveObjectsToClip.push(d.path);
                this.set.push(d.path);
                if (this.negativeFillColors || void 0 != this.negativeLineColor) {
                    d = this.fillAlphas;
                    if (this.negativeFillAlphas) d = this.negativeFillAlphas;
                    var e = this.negativeLineColor;
                    if (this.negativeFillColors) e = this.negativeFillColors;
                    c = new AmCharts.Bezier(this.container, a, b, NaN, NaN, 0, e, d, this.dashLength, c);
                    this.negativeObjectsToClip.push(c.path);
                    this.set.push(c.path)
                }
            }
        }
    },
    launchAnimation: function() {
        var a = this;
        if (0 < a.sDur && !a.animationPlayed) {
            var b = a.set;
            b.attr({
                opacity: a.startAlpha
            });
            a.rotate ? b.translate("-1000,0") : b.translate("0,-1000");
            a.seqAn ? (b = setTimeout(function() {
                a.animateGraphs.call(a)
            },
            1E3 * a.index * a.sDur), a.timeOuts.push(b)) : a.animateGraphs()
        }
    },
    animateGraphs: function() {
        if (0 < this.set.length) {
            var a = 0,
            b = 0;
            this.rotate ? a = 1E3: b = 1E3;
            this.set.animate({
                opacity: 1,
                transform: "...t" + a + "," + b
            },
            1E3 * this.sDur, this.sEff)
        }
    },
    animate: function(a, b, c, d) {
        var e = this.animationArray;
        if (!a && 0 < e.length) a = e[0].obj,
        b = e[0].fh,
        c = e[0].ip,
        d = e[0].fp,
        e.shift();
        a.show();
        this.rotate ? 0 < b ? (a.attr({
            "fill-opacity": this.startAlpha,
            width: 1
        }), a.animate({
            "fill-opacity": this.fillAlphas,
            width: Math.abs(b)
        },
        1E3 * this.sDur, this.sEff)) : 0 > b && (a.attr({
            "fill-opacity": this.startAlpha,
            width: 1,
            x: c
        }), a.animate({
            "fill-opacity": this.fillAlphas,
            width: Math.abs(b),
            x: d
        },
        1E3 * this.sDur, this.sEff)) : 0 < b ? (a.attr({
            "fill-opacity": this.startAlpha,
            height: 0.1
        }), a.animate({
            "fill-opacity": this.fillAlphas,
            height: Math.abs(b)
        },
        1E3 * this.sDur, this.sEff)) : 0 > b && (a.attr({
            "fill-opacity": this.startAlpha,
            height: 0.1,
            y: c
        }), a.animate({
            "fill-opacity": this.fillAlphas,
            height: Math.abs(b),
            y: d
        },
        1E3 * this.sDur, this.sEff))
    },
    legendKeyColor: function() {
        var a = this.legendColor,
        b = this.lineAlpha;
        if (void 0 == a && (a = this.lineColor, 0 == b))(b = this.fillColors) && (a = "object" == typeof b ? b[0] : b);
        return a
    },
    legendKeyAlpha: function() {
        var a = this.legendAlpha;
        if (void 0 == a) {
            a = this.lineAlpha;
            if (0 == a && this.fillAlphas) a = this.fillAlphas;
            if (0 == a) a = this.bulletAlpha;
            0 == a && (a = 1)
        }
        return a
    },
    createBullet: function(a, b, c) {
        var d = "";
        a.url && (d = "pointer");
        var e = this.bulletOffset,
        f = this.bulletSize;
        if (!isNaN(a.bulletSize)) f = a.bulletSize;
        if (!isNaN(this.maxValue)) {
            var g = a.values.value;
            isNaN(g) || (f = g / this.maxValue * this.maxBulletSize)
        }
        if (f < this.minBulletSize) f = this.minBulletSize;
        this.rotate ? b += e: c -= e;
        var h;
        if ("none" != this.bullet || a.bullet) {
            var i = this.bulletColor;
            if (a.isNegative && void 0 != this.bulletColorNegative) i = this.bulletColorNegative;
            if (void 0 != a.color) i = a.color;
            e = this.bullet;
            if (a.bullet) e = a.bullet;
            var g = this.bulletBorderThickness,
            k = this.bulletBorderColor,
            j = this.bulletBorderAlpha,
            m = this.bulletAlpha;
            switch (e) {
            case "round":
                h = AmCharts.circle(this.container, f / 2, i, m, g, k, j);
                break;
            case "square":
                h = AmCharts.rect(this.container, f, f, i, m, g, k, j);
                b -= f / 2;
                c -= f / 2;
                break;
            case "triangleUp":
                h = AmCharts.triangle(this.container, f, 0, i, m, g, k, j);
                break;
            case "triangleDown":
                h = AmCharts.triangle(this.container, f, 180, i, m, g, k, j);
                break;
            case "triangleLeft":
                h = AmCharts.triangle(this.container, f, 270, i, m, g, k, j);
                break;
            case "triangleRight":
                h = AmCharts.triangle(this.container, f, 90, i, m, g, k, j);
                break;
            case "bubble":
                h = AmCharts.circle(this.container, f / 2, i, m, g, k, j, !0)
            }
        }
        if (this.customBullet || a.customBullet) {
            e = this.customBullet;
            if (a.customBullet) e = a.customBullet;
            if (e) if (h && h.remove(), "function" == typeof e) {
                h = new e;
                h.chart = this.chart;
                if (a.bulletConfig) h.availableSpace = c,
                h.graph = this,
                a.bulletConfig.minCoord = this.minCoord - c,
                h.bulletConfig = a.bulletConfig;
                h.write(this.container);
                h = h.set
            } else this.chart.path && (e = this.chart.path + e),
            h = this.container.image(e, 0, 0, f, f).attr({
                preserveAspectRatio: !0
            }),
            this.centerCustomBullets && (b -= f / 2, c -= f / 2)
        }
        if (h) {
            h.attr({
                cursor: d
            });
            this.allBullets.push(h);
            if ("serial" == this.chartType && (b < this.x || b > this.x + this.width || c < this.y - f / 2 || c > this.y + this.height)) h.remove(),
            h = null;
            if (h)"xy" == this.chartType && this.setPositiveClipRect(h),
            this.set.push(h),
            h.transform("...T" + b + "," + c),
            h.dItem = a,
            this.objectsToAddListeners.push(h)
        }
        return f
    },
    showBullets: function() {
        for (var a = 0; a < this.allBullets.length; a++) this.allBullets[a].show()
    },
    hideBullets: function() {
        for (var a = 0; a < this.allBullets.length; a++) this.allBullets[a].hide()
    },
    addHoverListeners: function(a) {
        var b = this;
        a.mouseover(function() {
            b.handleRollOver.call(b, this.dItem)
        }).mouseout(function() {
            b.handleRollOut.call(b, this.dItem)
        })
    },
    addClickListeners: function(a) {
        var b = this;
        b.chart.touchEventsEnabled && a.touchstart(function() {
            b.handleRollOver(this.dItem)
        }).touchend(function() {
            b.handleClick(this.dItem)
        });
        a.click(function() {
            b.handleClick.call(b, this.dItem)
        }).dblclick(function() {
            b.handleDoubleClick.call(b, this.dItem)
        })
    },
    handleRollOver: function(a) {
        if (a) {
            var b = this.chart,
            c = {
                type: "rollOverGraphItem",
                item: a,
                index: a.index,
                graph: this
            };
            this.fire("rollOverGraphItem", c);
            b.fire("rollOverGraphItem", c);
            clearTimeout(b.hoverInt);
            c = !0;
            b.chartCursor && "serial" == this.chartType && (c = !1, b.chartCursor.valueBalloonsEnabled || (c = !0));
            if (c && this.showBalloon) c = b.formatString(this.balloonText, a, this),
            c = AmCharts.cleanFromEmpty(c),
            a = b.getBalloonColor(this, a),
            b.balloon.showBullet = !1,
            b.balloon.pointerOrientation = "vertical",
            b.showBalloon(c, a, !0)
        }
    },
    handleRollOut: function(a) {
        this.chart.hideBalloon();
        a && (a = {
            type: "rollOutGraphItem",
            item: a,
            index: a.index,
            graph: this
        },
        this.fire("rollOutGraphItem", a), this.chart.fire("rollOutGraphItem", a))
    },
    handleClick: function(a) {
        if (a) {
            var b = {
                type: "clickGraphItem",
                item: a,
                index: a.index,
                graph: this
            };
            this.fire("clickGraphItem", b);
            this.chart.fire("clickGraphItem", b);
            a = a.url;
            b = this.urlTarget;
            if (a)"_self" == b || !b ? window.location.href = a: (b = document.getElementsByName(b)[0]) ? b.src = a: window.open(a)
        }
    },
    handleDoubleClick: function(a) {
        a && (a = {
            type: "doubleClickGraphItem",
            item: a,
            index: a.index,
            graph: this
        },
        this.fire("doubleClickGraphItem", a), this.chart.fire("doubleClickGraphItem", a))
    },
    zoom: function(a, b) {
        this.start = a;
        this.end = b;
        this.draw()
    },
    changeOpacity: function(a) {
        this.set && this.set.attr({
            opacity: a
        })
    },
    destroy: function() {
        AmCharts.removeSet(this.set);
        var a = this.timeOuts;
        if (a) for (var b = 0; b < a.length; b++) clearTimeout(a[b]);
        this.timeOuts = []
    }
});
AmCharts.ChartCursor = AmCharts.Class({
    construct: function() {
        this.createEvents("changed", "zoomed", "onHideCursor", "draw");
        this.enabled = !0;
        this.cursorAlpha = 1;
        this.selectionAlpha = 0.2;
        this.cursorColor = "#CC0000";
        this.categoryBalloonAlpha = 1;
        this.color = "#FFFFFF";
        this.type = "cursor";
        this.zoomed = !1;
        this.zoomable = !0;
        this.pan = !1;
        this.animate = !0;
        this.categoryBalloonDateFormat = "MMM DD, YYYY";
        this.categoryBalloonEnabled = this.valueBalloonsEnabled = !0;
        this.rolledOver = !1;
        this.cursorPosition = "middle";
        this.bulletsEnabled = this.skipZoomDispatch = !1;
        this.bulletSize = 8;
        this.oneBalloonOnly = !1
    },
    draw: function() {
        var a = this;
        a.destroy();
        var b = a.chart,
        c = b.container;
        a.rotate = b.rotate;
        a.container = c;
        a.set = c.set();
        a.allBullets = c.set();
        c = new AmCharts.AmBalloon;
        a.categoryBalloon = c;
        c.cornerRadius = 0;
        c.borderWidth = 1;
        c.borderAlpha = 0;
        c.chart = b;
        b = a.categoryBalloonColor;
        if (void 0 == b) b = a.cursorColor;
        c.fillColor = b;
        c.fillAlpha = a.categoryBalloonAlpha;
        c.borderColor = b;
        c.color = a.color;
        if (a.rotate) c.pointerOrientation = "horizontal";
        "cursor" == a.type ? a.createCursor() : a.createCrosshair();
        a.interval = setInterval(function() {
            a.detectMovement.call(a)
        },
        20)
    },
    updateData: function() {
        var a = this.chart.chartData;
        if ((this.data = a) && 0 < a.length && a) this.firstTime = a[0].time,
        this.lastTime = a[a.length - 1].time
    },
    createCursor: function() {
        var a = this.chart,
        b = this.cursorAlpha,
        c = a.categoryAxis,
        d = c.position,
        e = c.inside,
        f = c.axisThickness,
        g = this.categoryBalloon,
        h, i, k = this.x,
        j = this.y,
        m = a.dx,
        n = a.dy,
        l = this.width,
        o = this.height,
        a = a.rotate,
        r = c.tickLength;
        g.pointerWidth = r;
        a ? (h = [0, l, l + m], i = [0, 0, n]) : (h = [m, 0, 0], i = [n, 0, o]);
        this.line = b = AmCharts.line(this.container, h, i, this.cursorColor, b, 1);
        b.translate(k + "," + j);
        this.set.push(b);
        if (a) {
            if (e) g.pointerWidth = 0;
            "right" == d ? e ? g.setBounds(k, j + n, k + l + m, j + o + n) : g.setBounds(k + l + m + f, j + n, k + l + 1E3, j + o + n) : e ? g.setBounds(k, j, l + k, o + j) : g.setBounds( - 1E3, -1E3, k - r - f, j + o + 15)
        } else {
            g.maxWidth = l;
            if (c.parseDates) r = 0,
            g.pointerWidth = 0;
            "top" == d ? e ? g.setBounds(k + m, j + n, l + m + k, o + j) : g.setBounds(k + m, -1E3, l + m + k, j + n - r - f) : e ? g.setBounds(k, j, l + k, o + j - r) : g.setBounds(k, j + o + r + f - 1, k + l, j + o + r + f)
        }
        this.hideCursor()
    },
    createCrosshair: function() {
        var a = this.cursorAlpha,
        b = this.container,
        c = AmCharts.line(b, [0, 0], [0, this.height], this.cursorColor, a, 1),
        a = AmCharts.line(b, [0, this.width], [0, 0], this.cursorColor, a, 1);
        c.translate(this.x + "," + this.y);
        a.translate(this.x + "," + this.y);
        this.set.push(c);
        this.set.push(a);
        this.vLine = c;
        this.hLine = a;
        this.selection = AmCharts.rect(b, 1, 1, [this.cursorColor], [this.selectionAlpha]);
        this.selection.hide();
        this.hideCursor()
    },
    detectMovement: function() {
        var a = this.chart;
        if (a.mouseIsOver) {
            var b = a.mouseX,
            c = a.mouseY,
            d = this.x,
            e = this.y;
            if (b > d && b < d + this.width && c > e && c < this.height + e) this.drawing ? this.rolledOver || a.setMouseCursor("crosshair") : this.pan && (this.rolledOver || a.setMouseCursor("move")),
            this.rolledOver = !0,
            this.setPosition();
            else if (this.rolledOver) this.handleMouseOut(),
            this.rolledOver = !1
        } else if (this.rolledOver) this.handleMouseOut(),
        this.rolledOver = !1
    },
    getMousePosition: function() {
        var a, b = this.x,
        c = this.y,
        d = this.width,
        e = this.height;
        a = this.chart;
        this.rotate ? (a = a.mouseY, a < c && (a = c), a > e + c && (a = e + c)) : (a = a.mouseX, a < b && (a = b), a > d + b && (a = d + b));
        return a
    },
    updateCrosshair: function() {
        var a = this.chart.mouseX,
        b = this.chart.mouseY,
        c = this.x,
        d = this.y,
        e = this.vLine,
        f = this.hLine,
        a = AmCharts.fitToBounds(a, c, c + this.width),
        b = AmCharts.fitToBounds(b, d, d + this.height);
        0 < this.cursorAlpha && (c = e.getBBox(), d = f.getBBox(), e.show(), f.show(), e.translate(Math.round(a - c.x) + ",0"), f.translate("0," + Math.round(b - d.y)));
        this.zooming && this.updateSelectionSize(a, b); ! this.chart.mouseIsOver && !this.zooming && this.hideCursor()
    },
    updateSelectionSize: function(a, b) {
        AmCharts.removeObject(this.selection, this.set);
        var c = this.x,
        d = this.y,
        e = this.width,
        f = this.height,
        g = this.selectionPosX,
        h = this.selectionPosY;
        isNaN(a) || (g > a && (c = a, e = g - a), g < a && (c = g, e = a - g), g == a && (c = a, e = 0));
        if (!isNaN(b)) d = this.y,
        h > b && (d = b, f = h - b),
        h < b && (d = h, f = b - h),
        h == b && (d = b, f = 0);
        if (0 < e && 0 < f) this.selection = AmCharts.rect(this.container, e, f, [this.cursorColor], [this.selectionAlpha]),
        this.selection.translate(c + "," + d),
        this.set.push(this.selection)
    },
    arrangeBalloons: function() {
        var a = this.x,
        b = this.y,
        c = this.valueBalloons,
        d = b + this.height;
        c.sort(this.compareY);
        for (var e = 0; e < c.length; e++) {
            var f = c[e].balloon;
            f.setBounds(a, b, a + this.width, d);
            f.draw();
            d = f.yPos - 3
        }
        this.arrangeBalloons2()
    },
    compareY: function(a, b) {
        return a.yy < b.yy ? 1 : -1
    },
    arrangeBalloons2: function() {
        var a = this.valueBalloons;
        a.reverse();
        for (var b, c, d = 0; d < a.length; d++) {
            var e = a[d].balloon;
            b = e.bottom;
            var f = e.bottom - e.yPos;
            0 < d && b - f < c + 3 && (e.setBounds(this.x, c + 3, this.x + this.width, c + f + 3), e.draw());
            e.set && e.set.show();
            c = e.bottom
        }
    },
    showBullets: function() {
        AmCharts.removeSet(this.allBullets);
        for (var a = this.chart.graphs,
        b = 0; b < a.length; b++) {
            var c = a[b];
            if (c.showBalloon && !c.hidden && c.balloonText) {
                var d = this.data[this.index].axes[c.valueAxis.id].graphs[c.id],
                e = d.y;
                if (!isNaN(e)) {
                    var f, g;
                    f = d.x;
                    this.rotate ? (g = e, e = f) : g = f;
                    c = AmCharts.circle(this.container, this.bulletSize / 2, this.chart.getBalloonColor(c, d), c.cursorBulletAlpha);
                    c.translate(g + "," + e);
                    this.allBullets.push(c)
                }
            }
        }
    },
    destroy: function() {
        this.clear();
        this.selection = null;
        var a = this.categoryBalloon;
        a && a.destroy();
        this.destroyValueBalloons();
        AmCharts.removeSet(this.set);
        AmCharts.removeSet(this.allBullets)
    },
    clear: function() {
        clearInterval(this.interval)
    },
    destroyValueBalloons: function() {
        var a = this.valueBalloons;
        if (a) for (var b = 0; b < a.length; b++) a[b].balloon.destroy()
    },
    zoom: function(a, b, c, d) {
        var e = this.chart;
        this.destroyValueBalloons();
        this.zooming = !1;
        var f;
        this.rotate ? this.selectionPosY = f = e.mouseY: this.selectionPosX = f = e.mouseX;
        this.start = a;
        this.end = b;
        this.startTime = c;
        this.endTime = d;
        this.zoomed = !0;
        var g = e.categoryAxis,
        e = this.rotate;
        f = this.width;
        var h = this.height;
        g.parseDates && !g.equalSpacing ? (a = d - c + g.minDuration(), a = e ? h / a: f / a) : a = e ? h / (b - a) : f / (b - a);
        this.stepWidth = a;
        this.setPosition();
        this.hideCursor()
    },
    hideCursor: function(a) {
        void 0 == a && (a = !0);
        this.set && this.set.hide();
        this.categoryBalloon && this.categoryBalloon.hide();
        this.destroyValueBalloons();
        AmCharts.removeSet(this.allBullets);
        this.previousIndex = NaN;
        a && this.fire("onHideCursor", {
            type: "onHideCursor",
            chart: this.chart,
            target: this
        });
        this.chart.setMouseCursor("auto")
    },
    setPosition: function(a, b) {
        void 0 == b && (b = !0);
        if ("cursor" == this.type) {
            if (this.data && 0 < this.data.length) {
                a || (a = this.getMousePosition());
                if ((a != this.previousMousePosition || !0 == this.zoomed || this.oneBalloonOnly) && !isNaN(a)) {
                    var c = this.chart.categoryAxis.xToIndex(a);
                    if (c != this.previousIndex || this.zoomed || "mouse" == this.cursorPosition || this.oneBalloonOnly) this.updateCursor(c, b),
                    this.zoomed = !1
                }
                this.previousMousePosition = a
            }
        } else this.updateCrosshair()
    },
    updateCursor: function(a, b) {
        var c = this.chart,
        d = c.mouseX,
        e = c.mouseY;
        if (this.drawingNow) AmCharts.removeObject(this.drawingLine),
        this.drawingLine = AmCharts.line(this.container, [this.drawStartX, d], [this.drawStartY, e], this.cursorColor, 1, 1);
        if (this.enabled) {
            void 0 == b && (b = !0);
            this.index = a;
            var f = c.categoryAxis,
            g = this.x,
            h = this.y,
            i = c.dx,
            k = c.dy,
            j = this.width,
            m = this.height,
            n = this.data[a],
            l = n.x[f.id],
            o = c.rotate,
            r = f.inside,
            p = this.stepWidth,
            q = this.categoryBalloon,
            u = this.firstTime,
            x = this.lastTime,
            A = this.cursorPosition,
            I = f.position,
            s = this.zooming,
            H = this.panning,
            G = c.graphs,
            C = c.touchEventsEnabled,
            S = f.axisThickness;
            if (c.mouseIsOver || s || H || this.forceShow) if (this.forceShow = !1, H) {
                j = this.panClickPos;
                c = this.panClickEndTime;
                s = this.panClickStartTime;
                g = this.panClickEnd;
                h = this.panClickStart;
                d = (o ? j - e: j - d) / p;
                if (!f.parseDates || f.equalSpacing) d = Math.round(d);
                if (0 != d) if (f.parseDates && !f.equalSpacing) c + d > x && (d = x - c),
                s + d < u && (d = u - s),
                j = {
                    type: "zoomed"
                },
                j.start = s + d,
                j.end = c + d,
                j.target = this,
                this.fire("zoomed", j);
                else if (! (g + d >= this.data.length || 0 > h + d)) j = {
                    type: "zoomed"
                },
                j.start = h + d,
                j.end = g + d,
                j.target = this,
                this.fire(j.type, j)
            } else {
                "start" == A && (l -= f.cellWidth / 2);
                "mouse" == A && (l = o ? e - 2 : d - 2);
                if (o) {
                    if (l < h) if (s) l = h;
                    else {
                        this.hideCursor();
                        return
                    }
                    if (l > m + 1 + h) if (s) l = m + 1 + h;
                    else {
                        this.hideCursor();
                        return
                    }
                } else {
                    if (l < g) if (s) l = g;
                    else {
                        this.hideCursor();
                        return
                    }
                    if (l > j + g) if (s) l = j + g;
                    else {
                        this.hideCursor();
                        return
                    }
                }
                if (0 < this.cursorAlpha) u = this.line,
                x = u.getBBox(),
                o ? u.translate("0," + Math.round(l - x.y + k)) : u.translate(Math.round(l - x.x) + ",0"),
                u.show();
                this.linePos = o ? l + k: l;
                s && (o ? this.updateSelectionSize(NaN, l) : this.updateSelectionSize(l, NaN));
                u = !0;
                C && s && (u = !1);
                this.categoryBalloonEnabled && u ? (o ? (r && ("right" == I ? q.setBounds(g, h + k, g + j + i, g + l + k) : q.setBounds(g, h + k, g + j + i, g + l)), "right" == I ? r ? q.setPosition(g + j + i, l + k) : q.setPosition(g + j + i + S, l + k) : r ? q.setPosition(g, l) : q.setPosition(g - S, l)) : "top" == I ? r ? q.setPosition(l + i, h + k) : q.setPosition(l + i, h + k - S + 1) : r ? q.setPosition(l, h + m) : q.setPosition(l, h + m + S - 1), f.parseDates ? (f = AmCharts.formatDate(n.category, this.categoryBalloonDateFormat), -1 != f.indexOf("fff") && (f = AmCharts.formatMilliseconds(f, n.category)), q.showBalloon(f)) : q.showBalloon(n.category)) : q.hide();
                G && this.bulletsEnabled && this.showBullets();
                this.destroyValueBalloons();
                if (G && this.valueBalloonsEnabled && u && c.balloon.enabled) {
                    this.valueBalloons = f = [];
                    if (this.oneBalloonOnly) for (var i = Infinity,
                    B, u = 0; u < G.length; u++) if (p = G[u], p.showBalloon && !p.hidden && p.balloonText) q = n.axes[p.valueAxis.id].graphs[p.id],
                    x = q.y,
                    isNaN(x) || (o ? Math.abs(d - x) < i && (i = Math.abs(d - x), B = p) : Math.abs(e - x) < i && (i = Math.abs(e - x), B = p));
                    for (u = 0; u < G.length; u++) if (p = G[u], !(this.oneBalloonOnly && p != B) && p.showBalloon && !p.hidden && p.balloonText && (q = n.axes[p.valueAxis.id].graphs[p.id], x = q.y, !isNaN(x))) {
                        k = q.x;
                        l = !0;
                        if (o) {
                            if (i = x, k < h || k > h + m) l = !1
                        } else if (i = k, k = x, i < g || i > g + j) l = !1;
                        if (l) {
                            r = c.getBalloonColor(p, q);
                            l = new AmCharts.AmBalloon;
                            l.chart = c;
                            AmCharts.copyProperties(c.balloon, l);
                            l.setBounds(g, h, g + j, h + m);
                            l.pointerOrientation = "horizontal";
                            l.changeColor(r);
                            if (void 0 != p.balloonAlpha) l.fillAlpha = p.balloonAlpha;
                            if (void 0 != p.balloonTextColor) l.color = p.balloonTextColor;
                            l.setPosition(i, k);
                            p = c.formatString(p.balloonText, q, p);
                            "" != p && l.showBalloon(p); ! o && l.set && l.set.hide();
                            f.push({
                                yy: x,
                                balloon: l
                            })
                        }
                    }
                    o || this.arrangeBalloons()
                }
                b ? (j = {
                    type: "changed"
                },
                j.index = a, j.zooming = s, j.position = o ? e: d, j.target = this, c.fire("changed", j), this.fire("changed", j), this.skipZoomDispatch = !1) : (this.skipZoomDispatch = !0, c.updateLegendValues(a));
                this.previousIndex = a
            }
        } else this.hideCursor()
    },
    enableDrawing: function(a) {
        this.enabled = !a;
        this.hideCursor();
        this.rolledOver = !1;
        this.drawing = a
    },
    isZooming: function(a) {
        a && a != this.zooming && this.handleMouseDown("fake"); ! a && a != this.zooming && this.handleMouseUp()
    },
    handleMouseOut: function() {
        if (this.enabled) this.zooming ? this.setPosition() : (this.index = void 0, this.fire("changed", {
            type: "changed",
            index: void 0,
            target: this
        }), this.hideCursor())
    },
    handleReleaseOutside: function() {
        this.handleMouseUp()
    },
    handleMouseUp: function() {
        var a = this.chart,
        b = a.mouseX,
        a = a.mouseY;
        if (this.drawingNow) {
            this.drawingNow = !1;
            AmCharts.removeObject(this.drawingLine);
            var c = this.drawStartX,
            d = this.drawStartY;
            if (2 < Math.abs(c - b) || 2 < Math.abs(d - a)) c = {
                type: "draw",
                initialX: c,
                initialY: d,
                finalX: b,
                finalY: a
            },
            this.fire(c.type, c)
        }
        if (this.enabled) {
            if (this.pan) this.rolledOver = !1;
            else if (this.zoomable && this.zooming) {
                if ("cursor" == this.type) {
                    if (this.rotate ? this.selectionPosY = b = a: this.selectionPosX = b, !(2 > Math.abs(b - this.initialMouse) && this.fromIndex == this.index)) {
                        b = {
                            type: "zoomed"
                        };
                        this.index < this.fromIndex ? (b.end = this.fromIndex, b.start = this.index) : (b.end = this.index, b.start = this.fromIndex);
                        a = this.chart.categoryAxis;
                        if (a.parseDates && !a.equalSpacing) b.start = this.data[b.start].time,
                        b.end = this.data[b.end].time;
                        AmCharts.removeSet(this.allBullets);
                        if (!this.skipZoomDispatch) b.target = this,
                        this.fire("zoomed", b)
                    }
                } else if (! (3 > Math.abs(b - this.initialMouseX) && 3 > Math.abs(a - this.initialMouseY)) && (a = this.selection.getBBox(), b = {
                    type: "zoomed"
                },
                b.selectionHeight = a.height, b.selectionWidth = a.width, b.selectionY = a.y - this.y, b.selectionX = a.x - this.x, !this.skipZoomDispatch)) b.target = this,
                this.fire("zoomed", b);
                AmCharts.removeObject(this.selection, this.set)
            }
            this.panning = this.zooming = this.skipZoomDispatch = !1
        }
    },
    handleMouseDown: function(a) {
        if (this.zoomable || this.pan || this.drawing) {
            var b = this.rotate,
            c = this.chart,
            d = c.mouseX,
            e = c.mouseY;
            if (d > this.x && d < this.x + this.width && e > this.y && e < this.height + this.y || "fake" == a) if (this.setPosition(), this.drawing) this.drawStartY = e,
            this.drawStartX = d,
            this.drawingNow = !0;
            else if (this.pan) this.zoomable = !1,
            c.setMouseCursor("move"),
            this.panning = !0,
            this.hideCursor(!0),
            this.panClickPos = b ? e: d,
            this.panClickStart = this.start,
            this.panClickEnd = this.end,
            this.panClickStartTime = this.startTime,
            this.panClickEndTime = this.endTime;
            else if (this.zoomable)"cursor" == this.type ? (this.fromIndex = this.index, b ? (this.initialMouse = e, this.selectionPosY = this.linePos) : (this.initialMouse = d, this.selectionPosX = this.linePos)) : (this.initialMouseX = d, this.initialMouseY = e, this.selectionPosX = d, this.selectionPosY = e),
            this.zooming = !0
        }
    }
});
AmCharts.SimpleChartScrollbar = AmCharts.Class({
    construct: function() {
        this.createEvents("zoomed");
        this.backgroundColor = "#D4D4D4";
        this.backgroundAlpha = 1;
        this.selectedBackgroundColor = "#EFEFEF";
        this.selectedBackgroundAlpha = 1;
        this.scrollDuration = 2;
        this.resizeEnabled = !0;
        this.hideResizeGrips = !1;
        this.scrollbarHeight = 20;
        this.updateOnReleaseOnly = !1;
        this.dragIconWidth = 11;
        this.dragIconHeight = 18
    },
    draw: function() {
        var a = this;
        a.destroy();
        a.interval = setInterval(function() {
            a.updateScrollbar.call(a)
        },
        20);
        var b = a.chart.container,
        c = a.rotate,
        d = a.chart,
        e = b.set();
        a.set = e;
        if (d.touchEventsEnabled) a.updateOnReleaseOnly = !0;
        var f, g;
        c ? (f = a.scrollbarHeight, g = a.chart.plotAreaHeight) : (g = a.scrollbarHeight, f = a.chart.plotAreaWidth);
        a.width = f;
        if ((a.height = g) && f) {
            var h = AmCharts.rect(b, f, g, [a.backgroundColor], [a.backgroundAlpha]);
            e.push(h);
            h = AmCharts.rect(b, f, g, [0], [0]);
            e.push(h);
            a.invisibleBg = h;
            d.touchEventsEnabled && h.touchend(function() {
                a.handleBackgroundClick()
            });
            h.click(function() {
                a.handleBackgroundClick()
            }).mouseover(function() {
                a.handleMouseOver()
            }).mouseout(function() {
                a.handleMouseOut()
            });
            h = AmCharts.rect(b, f, g, [a.selectedBackgroundColor], [a.selectedBackgroundAlpha]);
            a.selectedBG = h;
            e.push(h);
            f = AmCharts.rect(b, f, g, ["#000"], [0]);
            a.dragger = f;
            e.push(f);
            d.touchEventsEnabled && f.touchstart(function(b) {
                a.handleDragStart(b)
            }).touchend(function() {
                a.handleDragStop()
            });
            f.mousedown(function(b) {
                a.handleDragStart(b)
            }).mouseup(function() {
                a.handleDragStop()
            }).mouseover(function() {
                a.handleDraggerOver()
            }).mouseout(function() {
                a.handleMouseOut()
            });
            c ? (h = d.pathToImages + "dragIconH.gif", g = a.dragIconWidth, f = a.dragIconHeight) : (h = d.pathToImages + "dragIcon.gif", f = a.dragIconWidth, g = a.dragIconHeight);
            c = b.image(h, 0, 0, f, g);
            b = b.image(h, 0, 0, f, g);
            a.dragIconLeft = c;
            e.push(a.dragIconLeft);
            a.dragIconRight = b;
            e.push(b);
            c.mousedown(function() {
                a.handleLeftIconDragStart()
            }).mouseup(function() {
                a.handleLeftIconDragStop()
            }).mouseover(function() {
                a.handleIconRollOver()
            }).mouseout(function() {
                a.handleIconRollOut()
            });
            b.mousedown(function() {
                a.handleRightIconDragStart()
            }).mouseup(function() {
                a.handleRightIconDragStop()
            }).mouseover(function() {
                a.handleIconRollOver()
            }).mouseout(function() {
                a.handleIconRollOut()
            });
            0 < d.chartData.length ? e.show() : e.hide();
            a.hideResizeGrips && (c.hide(), b.hide())
        }
        e.attr({
            x: a.x,
            y: a.y
        });
        a.clipDragger(!1);
        a.updateDragIconPositions()
    },
    updateScrollbarSize: function(a, b) {
        var c = this.dragger,
        d, e, f, g;
        this.rotate ? (d = this.x, e = a, f = this.width, g = b - a, c.attr("height", b - a), c.attr("y", e)) : (d = a, e = this.y, f = b - a, g = this.height, c.attr("width", b - a), c.attr("x", d));
        this.clipAndUpdate(d, e, f, g)
    },
    updateScrollbar: function() {
        var a, b = !1,
        c, d, e = this.dragger,
        f = e.getBBox();
        c = f.x;
        d = f.y;
        var g = f.width,
        f = f.height,
        h = this.rotate,
        i = this.chart,
        k = this.width,
        j = this.height,
        m = i.mouseX,
        n = i.mouseY,
        l = this.x,
        o = this.y;
        a = this.initialMouseCoordinate;
        if (i.mouseIsOver) {
            if (this.dragging) i = this.initialDragCoordinate,
            h ? (a = i + (n - a), a < o && (a = o), i = o + j - f, a > i && (a = i), e.attr({
                y: a
            })) : (a = i + (m - a), a < l && (a = l), i = l + k - g, a > i && (a = i), e.attr({
                x: a
            }));
            if (this.resizingRight) h ? (a = n - d, a + d > j + o && (a = j - d + o), 0 > a ? (this.resizingRight = !1, b = this.resizingLeft = !0) : (0 == a && (a = 0.1), e.attr("height", a))) : (a = m - c, a + c > k + l && (a = k - c + l), 0 > a ? (this.resizingRight = !1, b = this.resizingLeft = !0) : (0 == a && (a = 0.1), e.attr("width", a)));
            if (this.resizingLeft) h ? (c = d, d = n, d < o && (d = o), d > j + o && (d = j + o), a = !0 == b ? c - d: f + c - d, 0 > a ? (this.resizingRight = !0, this.resizingLeft = !1, e.attr("y", c + f)) : (0 == a && (a = 0.1), e.attr("y", d), e.attr("height", a))) : (d = m, d < l && (d = l), d > k + l && (d = k + l), a = !0 == b ? c - d: g + c - d, 0 > a ? (this.resizingRight = !0, this.resizingLeft = !1, e.attr("x", c + g)) : (0 == a && (a = 0.1), e.attr("x", d), e.attr("width", a)));
            this.clipDragger(!0)
        }
    },
    clipDragger: function(a) {
        var b = this.dragger.getBBox(),
        c = b.x,
        d = b.y,
        e = b.width,
        b = b.height,
        f = !1;
        if (this.rotate) {
            if (this.clipY != d || this.clipH != b) f = !0
        } else if (this.clipX != c || this.clipW != e) f = !0;
        f && (this.clipAndUpdate(c, d, e, b), a && (this.updateOnReleaseOnly || this.dispatchScrollbarEvent()))
    },
    maskGraphs: function() {},
    clipAndUpdate: function(a, b, c, d) {
        this.clipX = a;
        this.clipY = b;
        this.clipW = c;
        this.clipH = d;
        this.clipRect = a = a + "," + b + "," + c + "," + d;
        this.selectedBG.attr({
            "clip-rect": a
        });
        this.updateDragIconPositions();
        this.maskGraphs(a)
    },
    dispatchScrollbarEvent: function() {
        if (this.skipEvent) this.skipEvent = !1;
        else {
            this.chart.hideBalloon();
            var a = this.dragger.getBBox(),
            b = a.x - this.x,
            c = a.y - this.y,
            d = a.width,
            a = a.height;
            this.rotate ? (b = c, d = this.height / a) : d = this.width / d;
            d = {
                type: "zoomed",
                position: b,
                multiplyer: d
            };
            this.fire(d.type, d)
        }
    },
    updateDragIconPositions: function() {
        var a = this.dragger.getBBox(),
        b = a.x,
        c = a.y,
        d = this.dragIconLeft,
        e = this.dragIconRight,
        f,
        g,
        h = this.scrollbarHeight;
        this.rotate ? (f = this.dragIconWidth, g = this.dragIconHeight, d.attr({
            y: Math.round(c - f / 2),
            x: b + (h - g) / 2
        }), e.attr({
            y: Math.round(c + a.height - f / 2),
            x: b + (h - g) / 2
        })) : (f = this.dragIconHeight, g = this.dragIconWidth, d.attr({
            x: Math.round(b - g / 2),
            y: c + (h - f) / 2
        }), e.attr({
            x: Math.round(b - g / 2 + a.width),
            y: c + (h - f) / 2
        }))
    },
    showDragIcons: function() {
        this.resizeEnabled && (this.dragIconLeft.show(), this.dragIconRight.show())
    },
    hideDragIcons: function() { ! this.resizingLeft && !this.resizingRight && !this.dragging && (this.hideResizeGrips && (this.dragIconLeft.hide(), this.dragIconRight.hide()), this.removeCursors())
    },
    removeCursors: function() {
        this.chart.setMouseCursor("auto")
    },
    relativeZoom: function(a, b) {
        this.multiplyer = a;
        var c = this.position = b,
        d;
        this.rotate ? (c += this.y, d = c + this.height / a) : (c += this.x, d = c + this.width / a);
        this.updateScrollbarSize(c, d)
    },
    destroy: function() {
        this.clear();
        AmCharts.removeSet(this.set)
    },
    clear: function() {
        clearInterval(this.interval)
    },
    handleDragStart: function(a) {
        this.dragger.stop();
        a && a.preventDefault();
        this.removeCursors();
        this.dragging = !0;
        a = this.dragger.getBBox();
        this.rotate ? (this.initialDragCoordinate = a.y, this.initialMouseCoordinate = this.chart.mouseY) : (this.initialDragCoordinate = a.x, this.initialMouseCoordinate = this.chart.mouseX)
    },
    handleDragStop: function() {
        if (this.updateOnReleaseOnly) this.updateScrollbar(),
        this.skipEvent = !1,
        this.dispatchScrollbarEvent();
        this.dragging = !1;
        this.mouseIsOver && this.removeCursors();
        this.updateScrollbar()
    },
    handleDraggerOver: function() {
        this.handleMouseOver()
    },
    handleLeftIconDragStart: function() {
        this.dragger.stop();
        this.resizingLeft = !0
    },
    handleLeftIconDragStop: function() {
        this.resizingLeft = !1;
        this.mouseIsOver || this.removeCursors();
        this.updateOnRelease()
    },
    handleRightIconDragStart: function() {
        this.dragger.stop();
        this.resizingRight = !0
    },
    handleRightIconDragStop: function() {
        this.resizingRight = !1;
        this.mouseIsOver || this.removeCursors();
        this.updateOnRelease()
    },
    handleIconRollOut: function() {
        this.removeCursors()
    },
    handleIconRollOver: function() {
        this.rotate ? this.chart.setMouseCursor("n-resize") : this.chart.setMouseCursor("e-resize");
        this.handleMouseOver()
    },
    handleBackgroundClick: function() {
        if (!this.resizingRight && !this.resizingLeft) {
            this.zooming = !0;
            var a, b, c = this.scrollDuration,
            d = this.dragger;
            a = this.dragger.getBBox();
            var e = a.height,
            f = a.width;
            b = this.chart;
            var g = this.y,
            h = this.x,
            i = this.rotate;
            i ? (a = "y", b = b.mouseY - e / 2, b = AmCharts.fitToBounds(b, g, g + this.height - e)) : (a = "x", b = b.mouseX - f / 2, b = AmCharts.fitToBounds(b, h, h + this.width - f));
            this.updateOnReleaseOnly ? (this.skipEvent = !1, d.attr(a, b), this.dispatchScrollbarEvent()) : i ? d.animate({
                y: b
            },
            1E3 * c, ">") : d.animate({
                x: b
            },
            1E3 * c, ">")
        }
    },
    updateOnRelease: function() {
        if (this.updateOnReleaseOnly) this.updateScrollbar(),
        this.skipEvent = !1,
        this.dispatchScrollbarEvent()
    },
    handleReleaseOutside: function() {
        if (this.set) {
            if (this.resizingLeft || this.resizingRight || this.dragging) this.updateOnRelease(),
            this.removeCursors();
            this.mouseIsOver = this.dragging = this.resizingRight = this.resizingLeft = !1;
            this.hideResizeGrips && (this.dragIconLeft.hide(), this.dragIconRight.hide());
            this.updateScrollbar()
        }
    },
    handleMouseOver: function() {
        this.mouseIsOver = !0;
        this.showDragIcons()
    },
    handleMouseOut: function() {
        this.mouseIsOver = !1;
        this.hideDragIcons()
    }
});
AmCharts.ChartScrollbar = AmCharts.Class({
    inherits: AmCharts.SimpleChartScrollbar,
    construct: function() {
        AmCharts.ChartScrollbar.base.construct.call(this);
        this.graphLineColor = "#000000";
        this.graphLineAlpha = 0;
        this.graphFillColor = "#000000";
        this.graphFillAlpha = 0.1;
        this.selectedGraphLineColor = "#000000";
        this.selectedGraphLineAlpha = 0;
        this.selectedGraphFillColor = "#000000";
        this.selectedGraphFillAlpha = 0.5;
        this.gridCount = 0;
        this.gridColor = "#FFFFFF";
        this.gridAlpha = 0.7;
        this.scrollbarCreated = this.skipEvent = this.autoGridCount = !1
    },
    init: function() {
        var a = this.categoryAxis,
        b = this.chart;
        if (!a) this.categoryAxis = a = new AmCharts.CategoryAxis;
        a.chart = b;
        a.id = "scrollbar";
        a.dateFormats = b.categoryAxis.dateFormats;
        a.axisItemRenderer = AmCharts.RectangularAxisItemRenderer;
        a.axisRenderer = AmCharts.RectangularAxisRenderer;
        a.guideFillRenderer = AmCharts.RectangularAxisGuideFillRenderer;
        a.inside = !0;
        a.fontSize = this.fontSize;
        a.tickLength = 0;
        a.axisAlpha = 0;
        if (this.graph) {
            a = this.valueAxis;
            if (!a) this.valueAxis = a = new AmCharts.ValueAxis,
            a.visible = !1,
            a.scrollbar = !0,
            a.axisItemRenderer = AmCharts.RectangularAxisItemRenderer,
            a.axisRenderer = AmCharts.RectangularAxisRenderer,
            a.guideFillRenderer = AmCharts.RectangularAxisGuideFillRenderer,
            a.chart = b;
            b = this.selectedGraph;
            if (!b) b = new AmCharts.AmGraph,
            b.scrollbar = !0,
            this.selectedGraph = b;
            b = this.unselectedGraph;
            if (!b) b = new AmCharts.AmGraph,
            b.scrollbar = !0,
            this.unselectedGraph = b
        }
        this.scrollbarCreated = !0
    },
    draw: function() {
        var a = this;
        AmCharts.ChartScrollbar.base.draw.call(a);
        a.scrollbarCreated || a.init();
        var b = a.chart,
        c = b.chartData,
        d = a.categoryAxis,
        e = a.rotate,
        f = a.x,
        g = a.y,
        h = a.width,
        i = a.height,
        k = b.categoryAxis;
        d.setOrientation(!e);
        d.parseDates = k.parseDates;
        d.rotate = e;
        d.equalSpacing = k.equalSpacing;
        d.minPeriod = k.minPeriod;
        d.startOnAxis = k.startOnAxis;
        d.x = f;
        d.y = g;
        d.visibleAxisWidth = h;
        d.visibleAxisHeight = i;
        d.visibleAxisX = f;
        d.visibleAxisY = g;
        d.width = h;
        d.height = i;
        d.gridCount = a.gridCount;
        d.gridColor = a.gridColor;
        d.gridAlpha = a.gridAlpha;
        d.color = a.color;
        d.autoGridCount = a.autoGridCount;
        d.parseDates && !d.equalSpacing && d.timeZoom(c[0].time, c[c.length - 1].time);
        d.zoom(0, c.length - 1);
        if (k = a.graph) {
            var j = a.valueAxis,
            m = k.valueAxis;
            j.id = m.id;
            j.rotate = e;
            j.setOrientation(e);
            j.x = f;
            j.y = g;
            j.width = h;
            j.height = i;
            j.visibleAxisX = f;
            j.visibleAxisY = g;
            j.visibleAxisWidth = h;
            j.visibleAxisHeight = i;
            j.dataProvider = c;
            j.reversed = m.reversed;
            j.logarithmic = m.logarithmic;
            for (var n = Infinity,
            l = -Infinity,
            o = 0; o < c.length; o++) {
                var r = c[o].axes[m.id].graphs[k.id].values,
                p;
                for (p in r) if ("percents" != p && "total" != p) {
                    var q = r[p];
                    q < n && (n = q);
                    q > l && (l = q)
                }
            }
            if (Infinity != n) j.minimum = n;
            if ( - Infinity != l) j.maximum = l + 0.1 * (l - n);
            n == l && (j.minimum -= 1, j.maximum += 1);
            j.zoom(0, c.length - 1);
            p = a.unselectedGraph;
            p.id = k.id;
            p.rotate = e;
            p.chart = b;
            p.chartType = b.chartType;
            p.data = c;
            p.valueAxis = j;
            p.chart = k.chart;
            p.categoryAxis = a.categoryAxis;
            p.valueField = k.valueField;
            p.openField = k.openField;
            p.closeField = k.closeField;
            p.highField = k.highField;
            p.lowField = k.lowField;
            p.lineAlpha = a.graphLineAlpha;
            p.lineColor = a.graphLineColor;
            p.fillAlphas = [a.graphFillAlpha];
            p.fillColors = [a.graphFillColor];
            p.connect = k.connect;
            p.hidden = k.hidden;
            p.width = h;
            p.height = i;
            p.x = f;
            p.y = g;
            m = a.selectedGraph;
            m.id = k.id;
            m.rotate = e;
            m.chart = b;
            m.chartType = b.chartType;
            m.data = c;
            m.valueAxis = j;
            m.chart = k.chart;
            m.categoryAxis = d;
            m.valueField = k.valueField;
            m.openField = k.openField;
            m.closeField = k.closeField;
            m.highField = k.highField;
            m.lowField = k.lowField;
            m.lineAlpha = a.selectedGraphLineAlpha;
            m.lineColor = a.selectedGraphLineColor;
            m.fillAlphas = [a.selectedGraphFillAlpha];
            m.fillColors = [a.selectedGraphFillColor];
            m.connect = k.connect;
            m.hidden = k.hidden;
            m.width = h;
            m.height = i;
            m.x = f;
            m.y = g;
            b = a.graphType;
            if (!b) b = k.type;
            p.type = b;
            m.type = b;
            c = c.length - 1;
            p.zoom(0, c);
            m.zoom(0, c);
            c = a.dragger;
            m.set.insertBefore(c);
            p.set.insertBefore(c);
            m.set.click(function() {
                a.handleBackgroundClick()
            }).mouseover(function() {
                a.handleMouseOver()
            }).mouseout(function() {
                a.handleMouseOut()
            });
            p.set.click(function() {
                a.handleBackgroundClick()
            }).mouseover(function() {
                a.handleMouseOver()
            }).mouseout(function() {
                a.handleMouseOut()
            })
        }
        a.dragger.toFront();
        a.invisibleBg.insertBefore(a.dragger);
        a.dragIconLeft.toFront();
        a.dragIconRight.toFront()
    },
    timeZoom: function(a, b) {
        this.startTime = a;
        this.endTime = b;
        this.timeDifference = b - a;
        this.skipEvent = !0;
        this.zoomScrollbar()
    },
    zoom: function(a, b) {
        this.start = a;
        this.end = b;
        this.skipEvent = !0;
        this.zoomScrollbar()
    },
    dispatchScrollbarEvent: function() {
        if (this.skipEvent) this.skipEvent = !1;
        else {
            var a = this.chart.chartData,
            b, c, d = this.dragger.getBBox();
            b = d.x;
            var e = d.y;
            c = d.width;
            d = d.height;
            this.rotate && (b = e, c = d);
            e = this.categoryAxis;
            d = this.stepWidth;
            if (e.parseDates && !e.equalSpacing) {
                a = a[0].time;
                b = this.rotate ? b - this.y: b - this.x;
                var f = e.minDuration(),
                e = Math.round(b / d) + a,
                a = this.dragging ? e + this.timeDifference: Math.round((b + c) / d) + a - f;
                e > a && (e = a);
                if (e != this.startTime || a != this.endTime) this.startTime = e,
                this.endTime = a,
                b = {
                    type: "zoomed",
                    start: e,
                    end: a,
                    startDate: new Date(e),
                    endDate: new Date(a)
                },
                this.fire(b.type, b)
            } else if (e.startOnAxis || (b += d / 2), c -= this.stepWidth / 2, d = e.xToIndex(b), b = e.xToIndex(b + c), d != this.start || this.end != b) {
                e.startOnAxis && (this.resizingRight && d == b && b++, this.resizingLeft && d == b && (0 < d ? d--:b = 1));
                this.start = d;
                this.end = this.dragging ? this.start + this.difference: b;
                b = {
                    type: "zoomed",
                    start: this.start,
                    end: this.end
                };
                if (e.parseDates) {
                    if (a[this.start]) b.startDate = new Date(a[this.start].time);
                    if (a[this.end]) b.endDate = new Date(a[this.end].time)
                }
                this.fire(b.type, b)
            }
        }
    },
    zoomScrollbar: function() {
        var a, b;
        b = this.chart.chartData;
        var c = this.categoryAxis,
        d;
        c.parseDates && !c.equalSpacing ? (d = c.stepWidth, b = b[0].time, a = d * (this.startTime - b), b = d * (this.endTime - b + c.minDuration()), this.rotate ? (a += this.y, b += this.y) : (a += this.x, b += this.x)) : (a = b[this.start].x[c.id], b = b[this.end].x[c.id], d = c.stepWidth, c.startOnAxis || (c = d / 2, a -= c, b += c));
        this.stepWidth = d;
        this.updateScrollbarSize(a, b)
    },
    maskGraphs: function(a) {
        var b = this.selectedGraph;
        if (b) for (var b = b.set,
        c = 0; c < b.length; c++) b[c].attr({
            "clip-rect": a
        })
    },
    handleDragStart: function() {
        AmCharts.ChartScrollbar.base.handleDragStart.call(this);
        this.difference = this.end - this.start;
        this.timeDifference = this.endTime - this.startTime;
        if (0 > this.timeDifference) this.timeDifference = 0
    },
    handleBackgroundClick: function() {
        AmCharts.ChartScrollbar.base.handleBackgroundClick.call(this);
        if (!this.dragging && (this.difference = this.end - this.start, this.timeDifference = this.endTime - this.startTime, 0 > this.timeDifference)) this.timeDifference = 0
    }
});
AmCharts.circle = function(a, b, c, d, e, f, g, h) {
    if (void 0 == e || 0 == e) e = 1;
    void 0 == f && (f = "#000000");
    void 0 == g && (g = 0);
    h && (c = "r" + c + "-" + AmCharts.adjustLuminosity(c, -0.6));
    c = {
        fill: c,
        stroke: f,
        "fill-opacity": d,
        "stroke-width": e,
        "stroke-opacity": g
    };
    return a.circle(0, 0, b).attr(c)
};
AmCharts.text = function(a, b, c, d, e) {
    a = a.text(b, c, d).attr(e);
    window.opera && a.translate("0,-2");
    return a
};
AmCharts.polygon = function(a, b, c, d, e, f, g, h, i) {
    "object" == typeof e && (e = e[0]);
    if (void 0 == f || 0 == f) f = 1;
    void 0 == g && (g = "#000000");
    void 0 == h && (h = 0);
    void 0 == i && (i = 270);
    d = {
        fill: "" + AmCharts.generateGradient(d, i),
        stroke: g,
        "fill-opacity": e,
        "stroke-width": f,
        "stroke-opacity": h
    };
    e = AmCharts.ddd;
    f = ["M", Math.round(b[0]) + e, Math.round(c[0]) + e];
    for (g = 1; g < b.length; g++) f.push("L"),
    f.push(Math.round(b[g]) + e),
    f.push(Math.round(c[g]) + e);
    f.push("Z");
    return a.path(f).attr(d)
};
AmCharts.rect = function(a, b, c, d, e, f, g, h, i, k) {
    if (void 0 == f || 0 == f) f = 1;
    void 0 == g && (g = "#000000");
    void 0 == h && (h = 0);
    void 0 == i && (i = 0);
    void 0 == k && (k = 270);
    "object" == typeof e && (e = e[0]);
    void 0 == e && (e = 0);
    var b = Math.round(b),
    c = Math.round(c),
    j = 0,
    m = 0;
    0 > b && (b = Math.abs(b), j = -b);
    0 > c && (c = Math.abs(c), m = -c);
    j += AmCharts.ddd;
    m += AmCharts.ddd; (d = AmCharts.generateGradient(d, k)) || (d = "#FFFFFF");
    e = {
        fill: "" + d,
        stroke: g,
        "fill-opacity": e,
        "stroke-width": f,
        "stroke-opacity": h
    };
    return a.rect(j, m, b, c, i).attr(e)
};
AmCharts.triangle = function(a, b, c, d, e, f, g, h) {
    if (void 0 == f || 0 == f) f = 1;
    void 0 == g && (g = "#000000");
    void 0 == h && (h = 0);
    d = {
        fill: d,
        stroke: g,
        "fill-opacity": e,
        "stroke-width": f,
        "stroke-opacity": h
    };
    a = a.path(["M", -b / 2, b / 2, "L", 0, -b / 2, "L", b / 2, b / 2, "Z", -b / 2, b / 2]).attr(d);
    a.transform("r" + c);
    return a
};
AmCharts.line = function(a, b, c, d, e, f, g) {
    var h = "";
    1 == g && (h = ". ");
    1 < g && (h = "- ");
    d = {
        stroke: d,
        "stroke-dasharray": h,
        "stroke-opacity": e,
        "stroke-width": f
    };
    e = AmCharts.ddd;
    f = ["M", Math.round(b[0]) + e, Math.round(c[0]) + e];
    for (g = 1; g < b.length; g++) f.push("L"),
    f.push(Math.round(b[g]) + e),
    f.push(Math.round(c[g]) + e);
    return a.path(f).attr(d)
};
AmCharts.wedge = function(a, b, c, d, e, f, g, h, i, k) {
    var j = g / f * h; - 359.99 >= e && (e = -359.99);
    var m = b + Math.cos(d / 180 * Math.PI) * h,
    n = c + Math.sin( - d / 180 * Math.PI) * j,
    l = b + Math.cos(d / 180 * Math.PI) * f,
    o = c + Math.sin( - d / 180 * Math.PI) * g,
    r = b + Math.cos((d + e) / 180 * Math.PI) * f,
    p = c + Math.sin(( - d - e) / 180 * Math.PI) * g,
    b = b + Math.cos((d + e) / 180 * Math.PI) * h,
    c = c + Math.sin(( - d - e) / 180 * Math.PI) * j,
    d = AmCharts.adjustLuminosity(k.fill, -0.2),
    q = {
        fill: d,
        "fill-opacity": k["fill-opacity"],
        stroke: d,
        "stroke-width": 1.0E-6,
        "stroke-opacity": 1.0E-5
    },
    d = 0;
    180 < Math.abs(e) && (d = 1);
    e = a.set();
    if (0 < i) {
        var u = 0 < h ? a.path(["M", m, n + i, "L", l, o + i, "A", f, g, 0, d, 1, r, p + i, "L", b, c + i, "A", h, j, 0, d, 0, m, n + i, "z"]).attr(q) : a.path(["M", m, n + i, "L", l, o + i, "A", f, g, 0, d, 1, r, p + i, "L", b, c + i, "Z"]).attr(q);
        e.push(u);
        u = a.path(["M", m, n, "L", m, n + i, "L", l, o + i, "L", l, o, "L", m, n, "z"]).attr(q);
        i = a.path(["M", r, p, "L", r, p + i, "L", b, c + i, "L", b, c, "L", r, p, "z"]).attr(q);
        e.push(u);
        e.push(i)
    }
    if (k.gradient) k.fill = null;
    a = 0 < h ? a.path(["M", m, n, "L", l, o, "A", f, g, 0, d, 1, r, p, "L", b, c, "A", h, j, 0, d, 0, m, n, "Z"]).attr(k) : a.path(["M", m, n, "L", l, o, "A", f, g, 0, d, 1, r, p, "L", b, c, "Z"]).attr(k);
    e.push(a);
    return e
};
AmCharts.adjustLuminosity = function(a, b) {
    var c = Raphael.rgb2hsb(a).toString().split(","),
    d = c[2],
    d = Number(d.substr(0, d.length - 1)),
    d = d + d * b;
    1 < d && (d = 1);
    return c[0] + "," + c[1] + "," + d + ")"
};
AmCharts.putSetToFront = function(a) {
    for (var b = a.length - 1; 0 >= b; b++) a[b].toFront()
};
AmCharts.putSetToBack = function(a) {
    for (var b = 0; b < a.length - 1; b++) a[b].toBack()
};
AmCharts.AmPieChart = AmCharts.Class({
    inherits: AmCharts.AmChart,
    construct: function() {
        this.createEvents("rollOverSlice", "rollOutSlice", "clickSlice", "pullOutSlice", "pullInSlice");
        AmCharts.AmPieChart.base.construct.call(this);
        this.colors = "#FF0F00,#FF6600,#FF9E01,#FCD202,#F8FF01,#B0DE09,#04D215,#0D8ECF,#0D52D1,#2A0CD0,#8A0CCF,#CD0D74,#754DEB,#DDDDDD,#999999,#333333,#000000,#57032A,#CA9726,#990000,#4B0C25".split(",");
        this.pieAlpha = 1;
        this.pieBrightnessStep = 30;
        this.groupPercent = 0;
        this.groupedTitle = "Other";
        this.groupedPulled = !1;
        this.groupedAlpha = 1;
        this.marginLeft = 0;
        this.marginBottom = this.marginTop = 10;
        this.marginRight = 0;
        this.minRadius = 10;
        this.hoverAlpha = 1;
        this.depth3D = 0;
        this.startAngle = 90;
        this.angle = this.innerRadius = 0;
        this.outlineColor = "#FFFFFF";
        this.outlineAlpha = 0;
        this.outlineThickness = 1;
        this.startRadius = "500%";
        this.startAlpha = 0;
        this.startDuration = 1;
        this.startEffect = "bounce";
        this.sequencedAnimation = !1;
        this.pullOutRadius = "20%";
        this.pullOutDuration = 1;
        this.pullOutEffect = "bounce";
        this.pullOnHover = this.pullOutOnlyOne = !1;
        this.labelsEnabled = !0;
        this.labelRadius = 30;
        this.labelTickColor = "#000000";
        this.labelTickAlpha = 0.2;
        this.labelText = "[[title]]: [[percents]]%";
        this.hideLabelsPercent = 0;
        this.balloonText = "[[title]]: [[percents]]% ([[value]])\n[[description]]";
        this.urlTarget = "_self";
        this.previousScale = 1;
        this.autoMarginOffset = 10
    },
    initChart: function() {
        AmCharts.AmPieChart.base.initChart.call(this);
        if (this.dataChanged) this.parseData(),
        this.dispatchDataUpdated = !0,
        this.dataChanged = !1,
        this.legend && this.legend.setData(this.chartData);
        this.drawChart()
    },
    handleLegendEvent: function(a) {
        var b = a.type;
        if (a = a.dataItem) {
            var c = a.hidden;
            switch (b) {
            case "clickMarker":
                c || this.clickSlice(a);
                break;
            case "clickLabel":
                c || this.clickSlice(a);
                break;
            case "rollOverItem":
                c || this.rollOverSlice(a, !1);
                break;
            case "rollOutItem":
                c || this.rollOutSlice(a);
                break;
            case "hideItem":
                this.hideSlice(a);
                break;
            case "showItem":
                this.showSlice(a)
            }
        }
    },
    invalidateVisibility: function() {
        this.recalculatePercents();
        this.drawChart();
        var a = this.legend;
        a && a.invalidateSize()
    },
    drawChart: function() {
        var a = this;
        AmCharts.AmPieChart.base.drawChart.call(a);
        var b = a.chartData;
        if (b && 0 < b.length) {
            var c = a.updateWidth();
            a.realWidth = c;
            var d = a.updateHeight();
            a.realHeight = d;
            var e = AmCharts.toCoordinate,
            f = e(a.marginLeft, c),
            g = e(a.marginRight, c),
            h = e(a.marginTop, d) + a.getTitleHeight(),
            i = e(a.marginBottom, d);
            a.chartDataLabels = [];
            a.ticks = [];
            var k, j, m, n = AmCharts.toNumber(a.labelRadius),
            l = a.measureMaxLabel();
            if (!a.labelText || !a.labelsEnabled) n = l = 0;
            k = void 0 == a.pieX ? (c - f - g) / 2 + f: e(a.pieX, a.realWidth);
            j = void 0 == a.pieY ? (d - h - i) / 2 + h: e(a.pieY, d);
            m = e(a.radius, c, d);
            a.pullOutRadiusReal = AmCharts.toCoordinate(a.pullOutRadius, m);
            if (!m) c = 0 <= n ? c - f - g - 2 * l: c - f - g,
            d = d - h - i,
            m = Math.min(c, d),
            d < c && (m /= 1 - a.angle / 90, m > c && (m = c)),
            a.pullOutRadiusReal = AmCharts.toCoordinate(a.pullOutRadius, m),
            m = 0 <= n ? m - 1.8 * (n + a.pullOutRadiusReal) : m - 1.8 * a.pullOutRadiusReal,
            m /= 2;
            if (m < a.minRadius) m = a.minRadius;
            a.pullOutRadiusReal = e(a.pullOutRadius, m);
            e = e(a.innerRadius, m);
            e >= m && (e = m - 1);
            d = AmCharts.fitToBounds(a.startAngle, 0, 360);
            0 < a.depth3D && (d = 270 <= d ? 270 : 90);
            h = m - m * a.angle / 90;
            for (i = 0; i < b.length; i++) if (c = b[i], !0 != c.hidden && 0 < c.percents) {
                var o = 360 * -c.percents / 100,
                l = Math.cos((d + o / 2) / 180 * Math.PI),
                g = Math.sin(( - d - o / 2) / 180 * Math.PI) * (h / m),
                f = {
                    fill: c.color,
                    "fill-opacity": a.startAlpha,
                    stroke: a.outlineColor,
                    "stroke-opacity": a.outlineAlpha,
                    "stroke-width": a.outlineThickness,
                    "stroke-linecap": "round",
                    cursor: c.url ? "pointer": ""
                },
                r = k,
                p = j;
                if (a.chartCreated) f["fill-opacity"] = c.alpha;
                f = AmCharts.wedge(a.container, r, p, d, o, m, h, e, a.depth3D, f);
                b[i].wedge = f;
                90 >= d && 0 <= d || 360 >= d && 270 < d ? AmCharts.putSetToFront(f) : (270 >= d && 180 < d || 180 >= d && 90 < d) && AmCharts.putSetToBack(f);
                c.ix = l;
                c.iy = g;
                c.wedge = f;
                c.index = i;
                if (a.labelsEnabled && a.labelText && c.percents >= a.hideLabelsPercent) {
                    r = d + o / 2;
                    0 >= r && (r += 360);
                    var l = k + l * (m + n),
                    g = j + g * (m + n),
                    q,
                    o = 0;
                    if (0 <= n) {
                        var u;
                        90 >= r && 0 <= r ? (u = 0, q = "start", o = 8) : 360 >= r && 270 < r ? (u = 1, q = "start", o = 8) : 270 >= r && 180 < r ? (u = 2, q = "end", o = -8) : 180 >= r && 90 < r && (u = 3, q = "end", o = -8);
                        c.labelQuarter = u
                    } else q = "middle";
                    r = a.formatString(a.labelText, c);
                    r = AmCharts.text(a.container, l + 1.5 * o, g, r, {
                        fill: a.color,
                        "text-anchor": q,
                        "font-family": a.fontFamily,
                        "font-size": a.fontSize
                    });
                    p = setTimeout(function() {
                        a.showLabels.call(a)
                    },
                    1E3 * a.startDuration);
                    a.timeOuts.push(p);
                    a.touchEventsEnabled && (f.touchend(function() {
                        handleTouchEnd(a.chartData[this.index])
                    }), f.touchstart(function() {
                        handleTouchStart(a.chartData[this.index])
                    }));
                    f.push(r);
                    c.labelObject = r;
                    a.chartDataLabels[i] = r;
                    r.cornerx = l;
                    r.cornery = g;
                    r.cornerx2 = l + o
                }
                for (g = 0; g < f.length; g++) f[g].index = i;
                f.hover(function() {
                    a.rollOverSlice(a.chartData[this.index], !0)
                },
                function() {
                    a.rollOutSlice(a.chartData[this.index])
                }).click(function() {
                    a.clickSlice(a.chartData[this.index])
                });
                a.set.push(f);
                0 == c.alpha && f.hide();
                d -= 360 * c.percents / 100;
                0 >= d && (d += 360)
            }
            0 < n && a.arrangeLabels();
            for (i = 0; i < a.chartDataLabels.length; i++) a.chartDataLabels[i] && a.chartDataLabels[i].toFront();
            a.pieXReal = k;
            a.pieYReal = j;
            a.radiusReal = m;
            a.innerRadiusReal = e;
            0 < n && a.drawTicks();
            a = this;
            a.chartCreated ? a.pullSlices(!0) : (p = setTimeout(function() {
                a.pullSlices.call(a)
            },
            1200 * a.startDuration), a.timeOuts.push(p));
            a.chartCreated || a.startSlices();
            a.bringLabelsToFront();
            a.chartCreated = !0;
            a.dispatchDataUpdatedEvent()
        }
        a.bgImg && a.bgImg.toBack();
        a.background && a.background.toBack();
        a.drb()
    },
    formatString: function(a, b) {
        a = AmCharts.formatValue(a, b, ["value"], this.numberFormatter, "", this.usePrefixes, this.prefixesOfSmallNumbers, this.prefixesOfBigNumbers);
        a = AmCharts.formatValue(a, b, ["percents"], this.percentFormatter);
        a = AmCharts.massReplace(a, {
            "[[title]]": b.title,
            "[[description]]": b.description,
            "<br>": "\n"
        });
        return a = AmCharts.cleanFromEmpty(a)
    },
    drawTicks: function() {
        for (var a = 0; a < this.chartData.length; a++) if (this.chartDataLabels[a]) {
            var b = this.chartData[a],
            c = this.chartDataLabels[a],
            d = c.cornery,
            c = this.container.path(["M", this.pieXReal + b.ix * this.radiusReal, this.pieYReal + b.iy * this.radiusReal, "L", c.cornerx, d, "L", c.cornerx2, d]).attr({
                stroke: this.labelTickColor,
                "stroke-opacity": this.labelTickAlpha,
                "stroke-width": 1,
                "stroke-linecap": "round"
            });
            b.wedge.push(c);
            this.chartCreated || b.wedge.hide();
            this.ticks[a] = c
        }
    },
    arrangeLabels: function() {
        for (var a = this.chartData,
        b = a.length,
        c = this.chartDataLabels,
        d, e = b - 1; 0 <= e; e--) if (d = a[e], 0 == d.labelQuarter && !d.hidden && c[e]) d = d.index,
        this.checkOverlapping(d, 0, !0, 0);
        for (e = 0; e < b; e++) if (d = a[e], 1 == d.labelQuarter && !d.hidden && c[e]) d = d.index,
        this.checkOverlapping(d, 1, !1, 0);
        for (e = b - 1; 0 <= e; e--) if (d = a[e], 2 == d.labelQuarter && !d.hidden && c[e]) d = d.index,
        this.checkOverlapping(d, 2, !0, 0);
        for (e = 0; e < b; e++) if (d = a[e], 3 == d.labelQuarter && !d.hidden && c[e]) d = d.index,
        this.checkOverlapping(d, 3, !1, 0)
    },
    checkOverlapping: function(a, b, c, d) {
        var e, f, g, h = this.chartData,
        i = this.chartDataLabels;
        if (!0 == c) for (f = a + 1; f < h.length; f++) g = h[f],
        g.labelQuarter == b && !g.hidden && i[f] && !0 == AmCharts.hitTest(i[a].getBBox(), i[f].getBBox()) && (e = !0);
        else for (f = a - 1; 0 <= f; f--) g = h[f],
        g.labelQuarter == b && !g.hidden && i[f] && !0 == AmCharts.hitTest(i[a].getBBox(), i[f].getBBox()) && (e = !0);
        g = i[a].getBBox();
        i[a].cornery = g.y += g.height / 2; ! 0 == e && 100 > d && (g = h[a], e = i[a], e.attr({
            y: e.attr("y") + 3 * g.iy
        }), this.checkOverlapping(a, b, c, d + 1))
    },
    startSlices: function() {
        for (var a = this,
        b = 500 * (a.startDuration / a.chartData.length), c = 0; c < a.chartData.length; c++) if (0 < a.startDuration && a.sequencedAnimation) {
            var d = setTimeout(function() {
                a.startSequenced.call(a)
            },
            b * c);
            a.timeOuts.push(d)
        } else a.startSlice(a.chartData[c])
    },
    pullSlices: function(a) {
        for (var b = this.chartData,
        c = 0; c < b.length; c++) b[c].pulled && this.pullSlice(b[c], 1, a)
    },
    startSequenced: function() {
        for (var a = this.chartData,
        b = 0; b < a.length; b++) if (!a[b].started) {
            this.startSlice(this.chartData[b]);
            break
        }
    },
    startSlice: function(a) {
        a.started = !0;
        var b = a.wedge;
        if (b) {
            0 < a.alpha && b.show();
            var c = AmCharts.toCoordinate(this.startRadius, this.radiusReal);
            b.translate(a.ix * c + "," + a.iy * c);
            b.animate({
                "fill-opacity": a.alpha,
                transform: "t0,0"
            },
            1E3 * this.startDuration, this.startEffect)
        }
    },
    showLabels: function() {
        for (var a = this.chartData,
        b = 0; b < a.length; b++) if (0 < a[b].alpha) {
            var c = this.chartDataLabels[b];
            c && c.show(); (c = this.ticks[b]) && c.show()
        }
    },
    showSlice: function(a) {
        isNaN(a) ? a.hidden = !1 : this.chartData[a].hidden = !1;
        this.hideBalloon();
        this.invalidateVisibility()
    },
    hideSlice: function(a) {
        isNaN(a) ? a.hidden = !0 : this.chartData[a].hidden = !0;
        this.hideBalloon();
        this.invalidateVisibility()
    },
    rollOverSlice: function(a, b) {
        isNaN(a) || (a = this.chartData[a]);
        clearTimeout(this.hoverInt);
        this.pullOnHover && this.pullSlice(a, 1);
        var c = this.innerRadiusReal + (this.radiusReal - this.innerRadiusReal) / 2;
        a.pulled && (c += this.pullOutRadiusReal);
        1 > this.hoverAlpha && a.wedge && a.wedge.attr({
            "fill-opacity": this.hoverAlpha
        });
        var d = a.ix * c + this.pieXReal,
        c = a.iy * c + this.pieYReal,
        e = this.formatString(this.balloonText, a),
        f = AmCharts.adjustLuminosity(a.color, -0.15);
        this.showBalloon(e, f, b, d, c);
        d = {
            type: "rollOverSlice",
            dataItem: a
        };
        this.fire(d.type, d)
    },
    rollOutSlice: function(a) {
        isNaN(a) || (a = this.chartData[a]);
        a.wedge && a.wedge.attr({
            "fill-opacity": a.alpha
        });
        this.hideBalloon();
        a = {
            type: "rollOutSlice",
            dataItem: a
        };
        this.fire(a.type, a)
    },
    clickSlice: function(a) {
        isNaN(a) || (a = this.chartData[a]);
        this.hideBalloon();
        a.pulled ? this.pullSlice(a, 0) : this.pullSlice(a, 1);
        var b = a.url,
        c = this.urlTarget;
        if (b)"_self" == c || !c ? window.location.href = b: (c = document.getElementsByName(c)[0]) ? c.src = b: window.open(b);
        a = {
            type: "clickSlice",
            dataItem: a
        };
        this.fire(a.type, a)
    },
    pullSlice: function(a, b, c) {
        var d = a.ix,
        e = a.iy,
        f = 1E3 * this.pullOutDuration; ! 0 === c && (f = 0);
        a.wedge && a.wedge.animate({
            transform: "t" + b * d * this.pullOutRadiusReal + "," + b * e * this.pullOutRadiusReal
        },
        f, this.pullOutEffect);
        1 == b ? (a.pulled = !0, this.pullOutOnlyOne && this.pullInAll(a.index), a = {
            type: "pullOutSlice",
            dataItem: a
        }) : (a.pulled = !1, a = {
            type: "pullInSlice",
            dataItem: a
        });
        this.fire(a.type, a)
    },
    pullInAll: function(a) {
        for (var b = this.chartData,
        c = 0; c < this.chartData.length; c++) c != a && b[c].pulled && this.pullSlice(b[c], 0)
    },
    pullOutAll: function() {
        for (var a = this.chartData,
        b = 0; b < a.length; b++) a[b].pulled || this.pullSlice(a[b], 1)
    },
    parseData: function() {
        var a = [];
        this.chartData = a;
        var b = this.dataProvider;
        if (void 0 != b) {
            for (var c = b.length,
            d = 0,
            e = 0; e < c; e++) {
                var f = {},
                g = b[e];
                f.dataContext = g;
                f.value = Number(g[this.valueField]);
                var h = g[this.titleField];
                h || (h = "");
                f.title = h;
                f.pulled = AmCharts.toBoolean(g[this.pulledField], !1); (h = g[this.descriptionField]) || (h = "");
                f.description = h;
                f.url = g[this.urlField];
                f.visibleInLegend = AmCharts.toBoolean(g[this.pulledField], !0);
                h = g[this.alphaField];
                f.alpha = void 0 != h ? Number(h) : this.pieAlpha;
                g = g[this.colorField];
                if (void 0 != g) f.color = AmCharts.toColor(g);
                d += f.value;
                f.hidden = !1;
                a[e] = f
            }
            for (e = b = 0; e < c; e++) f = a[e],
            f.percents = 100 * (f.value / d),
            f.percents < this.groupPercent && b++;
            if (1 < b) this.groupValue = 0,
            this.removeSmallSlices(),
            a.push({
                title: this.groupedTitle,
                value: this.groupValue,
                percents: 100 * (this.groupValue / d),
                pulled: this.groupedPulled,
                color: this.groupedColor,
                url: this.groupedUrl,
                description: this.groupedDescription,
                alpha: this.groupedAlpha
            });
            for (e = 0; e < a.length; e++) if (this.pieBaseColor ? g = AmCharts.adjustLuminosity(this.pieBaseColor, e * this.pieBrightnessStep / 100) : (g = this.colors[e], void 0 == g && (g = AmCharts.randomColor())), void 0 == a[e].color) a[e].color = g;
            this.recalculatePercents()
        }
    },
    recalculatePercents: function() {
        for (var a = this.chartData,
        b = 0,
        c = 0; c < a.length; c++) {
            var d = a[c]; ! d.hidden && 0 < d.value && (b += d.value)
        }
        for (c = 0; c < a.length; c++) d = this.chartData[c],
        d.percents = !d.hidden && 0 < d.value ? 100 * d.value / b: 0
    },
    handleTouchStart: function(a) {
        var b = this;
        AmCharts.AmPieChart.base.handleTouchStart.call(b);
        a.pulled ? (b.rolledOveredSlice = void 0, b.hideBalloon()) : (b.rolledOveredSlice = a, b.pullTimeOut = setTimeout(function() {
            b.padRollOver.call(b)
        },
        100))
    },
    padRollOver: function() {
        this.rollOverSlice(this.rolledOveredSlice, !1)
    },
    handleTouchEnd: function(a) {
        AmCharts.AmPieChart.base.handleTouchEnd.call(this);
        a.pulled ? this.pullSlice(a, 0) : this.pullSlice(a, 1)
    },
    removeSmallSlices: function() {
        for (var a = this.chartData,
        b = a.length - 1; 0 <= b; b--) a[b].percents < this.groupPercent && (this.groupValue += a[b].value, a.splice(b, 1))
    },
    animateAgain: function() {
        this.startSlices()
    },
    measureMaxLabel: function() {
        for (var a = this.chartData,
        b = 0,
        c = 0; c < a.length; c++) {
            var d = this.formatString(this.labelText, a[c]),
            d = AmCharts.text(this.container, 0, 0, d, {
                fill: this.color,
                "font-family": this.fontFamily,
                "font-size": this.fontSize
            }),
            e = d.getBBox().width;
            e > b && (b = e);
            d.remove()
        }
        return b
    }
});
AmCharts.AmXYChart = AmCharts.Class({
    inherits: AmCharts.AmRectangularChart,
    construct: function() {
        AmCharts.AmXYChart.base.construct.call(this);
        this.createEvents("zoomed");
        this.maxZoomFactor = 20;
        this.chartType = "xy"
    },
    initChart: function() {
        AmCharts.AmXYChart.base.initChart.call(this);
        if (this.dataChanged) this.updateData(),
        this.dataChanged = !1,
        this.dispatchDataUpdated = !0;
        this.updateScrollbar = !0;
        this.drawChart();
        if (this.autoMargins && !this.marginsUpdated) this.marginsUpdated = !0,
        this.measureMargins()
    },
    createValueAxes: function() {
        var a = [],
        b = [];
        this.xAxes = a;
        this.yAxes = b;
        for (var c = this.valueAxes,
        d = 0; d < c.length; d++) {
            var e = c[d],
            f = e.position;
            if ("top" == f || "bottom" == f) e.rotate = !0;
            e.setOrientation(e.rotate);
            f = e.orientation;
            "vertical" == f && b.push(e);
            "horizontal" == f && a.push(e)
        }
        if (0 == b.length) e = new AmCharts.ValueAxis,
        e.rotate = !1,
        e.setOrientation(!1),
        c.push(e),
        b.push(e);
        if (0 == a.length) e = new AmCharts.ValueAxis,
        e.rotate = !0,
        e.setOrientation(!0),
        c.push(e),
        a.push(e);
        for (d = 0; d < c.length; d++) this.processValueAxis(c[d], d);
        a = this.graphs;
        for (d = 0; d < a.length; d++) this.processGraph(a[d], d)
    },
    drawChart: function() {
        AmCharts.AmXYChart.base.drawChart.call(this);
        var a = this.chartData;
        a && (0 < a.length ? (this.chartScrollbar && (this.updateScrollbars(), this.scrollbarHorizontal.draw(), this.scrollbarVertical.draw()), this.zoomChart()) : this.cleanChart());
        this.bringLabelsToFront();
        this.chartCreated = !0;
        this.dispatchDataUpdatedEvent()
    },
    cleanChart: function() {
        AmCharts.callMethod("destroy", [this.valueAxes, this.graphs, this.scrollbarVertical, this.scrollbarHorizontal, this.chartCursor])
    },
    zoomChart: function() {
        this.toggleZoomOutButton();
        this.skipFix ? this.skipFix = !1 : this.fixMinMax();
        this.zoomObjects(this.valueAxes);
        this.zoomObjects(this.graphs);
        this.dispatchAxisZoom();
        this.updateDepths();
        this.renderfix()
    },
    toggleZoomOutButton: function() {
        1 == this.heightMultiplyer && 1 == this.widthMultiplyer ? this.hideZoomOutButton() : this.showZoomOutButton()
    },
    dispatchAxisZoom: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            if (!isNaN(c.min) && !isNaN(c.max)) {
                var d, e;
                "vertical" == c.orientation ? (d = c.coordinateToValue( - this.verticalPosition), e = c.coordinateToValue( - this.verticalPosition + this.plotAreaHeight)) : (d = c.coordinateToValue( - this.horizontalPosition), e = c.coordinateToValue( - this.horizontalPosition + this.plotAreaWidth));
                if (!isNaN(d) && !isNaN(e)) {
                    if (d > e) {
                        var f = e;
                        e = d;
                        d = f
                    }
                    c.dispatchZoomEvent(d, e)
                }
            }
        }
    },
    zoomObjects: function(a) {
        for (var b = a.length,
        c = 0; c < b; c++) {
            var d = a[c];
            this.updateObjectSize(d);
            d.zoom(0, this.chartData.length - 1)
        }
    },
    updateData: function() {
        this.parseData();
        for (var a = this.chartData,
        b = a.length - 1,
        c = this.graphs,
        d = this.dataProvider,
        e = 0; e < c.length; e++) {
            var f = c[e];
            f.data = a;
            f.zoom(0, b);
            var g = f.valueField,
            h = 0;
            if (g) for (var i = 0; i < d.length; i++) {
                var k = d[i][g];
                k > h && (h = k)
            }
            f.maxValue = h
        }
        this.skipFix = !0;
        if (a = this.chartCursor) a.updateData(),
        a.type = "crosshair",
        a.valueBalloonsEnabled = !1
    },
    zoomOut: function() {
        this.verticalPosition = this.horizontalPosition = 0;
        this.heightMultiplyer = this.widthMultiplyer = 1;
        this.zoomChart();
        this.zoomScrollbars()
    },
    updateDepths: function() {
        var a = this.container.rect(0, 0, 10, 10),
        b = this.chartCursor;
        b && b.set.insertBefore(a);
        for (var b = this.graphs,
        c = 0; c < b.length; c++) {
            var d = b[c];
            if (d.allBullets) for (var e = 0; e < d.allBullets.length; e++) d.allBullets[e].insertBefore(a);
            if (d.positiveObjectsToClip) for (e = 0; e < d.positiveObjectsToClip.length; e++) d.setPositiveClipRect(d.positiveObjectsToClip[e]);
            var f = d.objectsToAddListeners;
            if (f) for (e = 0; e < f.length; e++) d.addClickListeners(f[e]),
            d.addHoverListeners(f[e])
        } (b = this.zoomOutButtonSet) && b.insertBefore(a);
        a.remove(); (a = this.bgImg) && a.toBack(); (a = this.background) && a.toBack();
        this.drb()
    },
    processValueAxis: function(a) {
        a.chart = this;
        a.minMaxField = "horizontal" == a.orientation ? "x": "y";
        a.minTemp = NaN;
        a.maxTemp = NaN;
        this.listenTo(a, "axisSelfZoomed", this.handleAxisSelfZoom)
    },
    processGraph: function(a) {
        if (!a.xAxis) a.xAxis = this.xAxes[0];
        if (!a.yAxis) a.yAxis = this.yAxes[0]
    },
    parseData: function() {
        AmCharts.AmXYChart.base.parseData.call(this);
        this.chartData = [];
        for (var a = this.dataProvider,
        b = this.valueAxes,
        c = this.graphs,
        d = 0; d < a.length; d++) {
            for (var e = {
                axes: {},
                x: {},
                y: {}
            },
            f = a[d], g = 0; g < b.length; g++) {
                var h = b[g].id;
                e.axes[h] = {};
                e.axes[h].graphs = {};
                for (var i = 0; i < c.length; i++) {
                    var k = c[i],
                    j = k.id;
                    if (k.xAxis.id == h || k.yAxis.id == h) {
                        var m = {};
                        m.serialDataItem = e;
                        m.index = d;
                        var n = {},
                        l = Number(f[k.valueField]);
                        if (!isNaN(l)) n.value = l;
                        l = Number(f[k.xField]);
                        if (!isNaN(l)) n.x = l;
                        l = Number(f[k.yField]);
                        if (!isNaN(l)) n.y = l;
                        m.values = n;
                        this.processFields(k, m, f);
                        m.serialDataItem = e;
                        m.graph = k;
                        e.axes[h].graphs[j] = m
                    }
                }
            }
            this.chartData[d] = e
        }
    },
    formatString: function(a, b) {
        var c = b.graph.numberFormatter;
        if (!c) c = this.numberFormatter;
        a = AmCharts.formatValue(a, b.values, ["value", "x", "y"], c);
        return a = AmCharts.AmSerialChart.base.formatString.call(this, a, b)
    },
    addChartScrollbar: function(a) {
        AmCharts.callMethod("destroy", [this.chartScrollbar, this.scrollbarHorizontal, this.scrollbarVertical]);
        if (a) {
            var b = new AmCharts.SimpleChartScrollbar,
            c = new AmCharts.SimpleChartScrollbar;
            b.skipEvent = !0;
            c.skipEvent = !0;
            b.chart = this;
            c.chart = this;
            this.listenTo(b, "zoomed", this.handleVSBZoom);
            this.listenTo(c, "zoomed", this.handleHSBZoom);
            var d = "backgroundColor,backgroundAlpha,selectedBackgroundColor,selectedBackgroundAlpha,scrollDuration,resizeEnabled,hideResizeGrips,scrollbarHeight,updateOnReleaseOnly".split(",");
            AmCharts.copyProperties(a, b, d);
            AmCharts.copyProperties(a, c, d);
            b.rotate = !0;
            c.rotate = !1;
            this.scrollbarHeight = a.scrollbarHeight;
            this.scrollbarHorizontal = c;
            this.scrollbarVertical = b;
            this.chartScrollbar = a
        }
    },
    fixMinMax: function() {
        for (var a = this.valueAxes,
        b = 0; b < a.length; b++) {
            var c = a[b];
            if (!0 == c.logarithmic) {
                if (!isNaN(c.minReal)) c.minTemp = c.minReal
            } else if (!isNaN(c.min)) c.minTemp = c.min;
            if (!isNaN(c.max)) c.maxTemp = c.max
        }
        this.skipFix = !1
    },
    updateTrendLines: function() {
        for (var a = this.trendLines,
        b = 0; b < a.length; b++) {
            var c = a[b];
            c.chart = this;
            if (!c.valueAxis) c.valueAxis = this.yAxes[0];
            if (!c.valueAxisX) c.valueAxisX = this.xAxes[0]
        }
    },
    updateMargins: function() {
        AmCharts.AmXYChart.base.updateMargins.call(this);
        var a = this.scrollbarVertical;
        a && (this.getScrollbarPosition(a, !0, this.yAxes[0].position), this.adjustMargins(a, !0));
        if (a = this.scrollbarHorizontal) this.getScrollbarPosition(a, !1, this.xAxes[0].position),
        this.adjustMargins(a, !1)
    },
    updateScrollbars: function() {
        this.updateChartScrollbar(this.scrollbarVertical, !0);
        this.updateChartScrollbar(this.scrollbarHorizontal, !1)
    },
    zoomScrollbars: function() {
        var a = this.scrollbarHorizontal;
        a && a.relativeZoom(this.widthMultiplyer, -this.horizontalPosition / this.widthMultiplyer); (a = this.scrollbarVertical) && a.relativeZoom(this.heightMultiplyer, -this.verticalPosition / this.heightMultiplyer)
    },
    fitMultiplyer: function(a) {
        if (a > this.maxZoomFactor) a = this.maxZoomFactor;
        return a
    },
    handleHSBZoom: function(a) {
        var b = this.fitMultiplyer(a.multiplyer),
        a = -a.position * b,
        c = -(this.plotAreaWidth * b - this.plotAreaWidth);
        a < c && (a = c);
        this.widthMultiplyer = b;
        this.horizontalPosition = a;
        this.zoomChart()
    },
    handleVSBZoom: function(a) {
        var b = this.fitMultiplyer(a.multiplyer),
        a = -a.position * b,
        c = -(this.plotAreaHeight * b - this.plotAreaHeight);
        a < c && (a = c);
        this.heightMultiplyer = b;
        this.verticalPosition = a;
        this.zoomChart()
    },
    handleCursorZoom: function(a) {
        var b = this.widthMultiplyer * this.plotAreaWidth / a.selectionWidth,
        c = this.heightMultiplyer * this.plotAreaHeight / a.selectionHeight,
        b = this.fitMultiplyer(b),
        c = this.fitMultiplyer(c);
        this.horizontalPosition = (this.horizontalPosition - a.selectionX) * b / this.widthMultiplyer;
        this.verticalPosition = (this.verticalPosition - a.selectionY) * c / this.heightMultiplyer;
        this.widthMultiplyer = b;
        this.heightMultiplyer = c;
        this.zoomChart();
        this.zoomScrollbars()
    },
    handleAxisSelfZoom: function(a) {
        if ("horizontal" == a.valueAxis.orientation) {
            var b = this.fitMultiplyer(a.multiplyer),
            a = -a.position / this.widthMultiplyer * b,
            c = -(this.plotAreaWidth * b - this.plotAreaWidth);
            a < c && (a = c);
            this.horizontalPosition = a;
            this.widthMultiplyer = b
        } else b = this.fitMultiplyer(a.multiplyer),
        a = -a.position / this.heightMultiplyer * b,
        c = -(this.plotAreaHeight * b - this.plotAreaHeight),
        a < c && (a = c),
        this.verticalPosition = a,
        this.heightMultiplyer = b;
        this.zoomChart();
        this.zoomScrollbars()
    },
    removeChartScrollbar: function() {
        AmCharts.callMethod("destroy", [this.scrollbarHorizontal, this.scrollbarVertical]);
        this.scrollbarVertical = this.scrollbarHorizontal = null
    },
    handleReleaseOutside: function(a) {
        AmCharts.AmXYChart.base.handleReleaseOutside.call(this, a);
        AmCharts.callMethod("handleReleaseOutside", [this.scrollbarHorizontal, this.scrollbarVertical])
    }
});
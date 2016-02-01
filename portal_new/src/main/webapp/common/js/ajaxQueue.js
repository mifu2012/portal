(function($) {
    $.ajaxQueue = {
        requests: new Array(),
        
        offer: function(options) {
            var ajax_self = this,
               xhrOptions = $.extend({}, options, {
                   complete: function(jqXHR, textStatus) {
                       if($.isArray(options.complete)) {
                           var funcs = options.complete;
                           for(var i = 0, len = funcs.length; i < len; i++) 
                               funcs[i].call(this, jqXHR, textStatus);
                       } else {
                           if(options.complete)
                               options.complete.call(this, jqXHR, textStatus);            
                       }
                       ajax_self.poll();
                   },
                   
                   beforeSend: function(jqXHR, settings) {
                       if(options.beforeSend)
                           var ret = options.beforeSend.call(this, jqXHR, settings);
                       if(ret === false) { 
                          // $('#log').append('<p>an ajax request cancelled</p>');
                           ajax_self.poll();
                           return ret;
                       }
                   }
               });
            
            this.requests.push(xhrOptions);
            
            if(this.requests.length == 1) {
                $.ajax(xhrOptions);
            }
        },
        
        poll: function() {
            if(this.isEmpty()) {
                return null;    
            }
            
            var processedRequest = this.requests.shift();
            var nextRequest = this.peek();
            if(nextRequest != null) {
                $.ajax(nextRequest);
            }
            
            return processedRequest;
        },
        
        peek: function() {
            if(this.isEmpty()) {
                return null;
            }
            
            var nextRequest = this.requests[0];
            return nextRequest;
        },
        
        isEmpty: function() {
            return this.requests.length == 0;
        }
    }
})(jQuery);

/*
$.ajaxQueue.offer({
    type: "POST",  
    async:true,
    url: encodeURI("<%=basePath%>/reportChartDate/getPieChartDate.html?chartId=${reportField.chartId}&reportId=${reportField.reportId}&querySql="+queryWhere+"&random="+Math.random()),
    success: function(msg){ 
  		var reg=new RegExp("},{","g"); 
        	var reg2=new RegExp(":","g"); 
  	        msg=msg.substring(0,msg.indexOf(":")+1)+(msg.substring(msg.indexOf(":")+1, msg.length-2).replace("[{","[[").replace("}]","]]")).replace(reg,"],[")+msg.substring(msg.length-2,msg.length)
  	        msg=msg.substring(0,msg.indexOf("[["))+msg.substring(msg.indexOf("[["),msg.lastIndexOf("]]")).replace(reg2,",")+msg.substring(msg.lastIndexOf("]]"),msg.leng);
        	  msg=eval(msg); 
        	  option${vel.index+1}.series=msg;
        	  chart=new Highcharts.Chart(option${vel.index+1})
    }
}) */
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>公告信息</title>
    <script src="${base}/plugins/cashGift/js/mui.min.js"></script>
    <link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${base}/plugins/cashGift/css/packet.css"/>
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
    
</head>
<body class="contetBack">
	<div class="mui-content contetBack">
	    <div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%"/>
		</div>
	   <div class="share-content notice-content">
	    	<div class="send-code">
	    		<p class="notice-picture"> <img src="${base}/plugins/cashGift/images/shensu.png" width="100%"/></p>
	    		<div class="notice-state">
	    			<c:if test="${null != msg}">
	    				${msg}
	    			</c:if>
	    		</div>
	    		<div class="notice-state">
	    			感谢您对三全的支持！
	    		</div>
	    	</div>
	     </div>
	     
	</div>
	
</body>
<script type="text/javascript">
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    // 通过下面这个API隐藏右上角按钮
      WeixinJSBridge.call('hideOptionMenu');
});

</script>
</html>
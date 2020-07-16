<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>红包余额</title>
   	<script src="${base}/plugins/cashGift/js/jquery.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${base}/plugins/cashGift/js/jQuery-backtretch.js" type="text/javascript" charset="utf-8"></script>
   	<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
   	<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
  	<link rel="stylesheet" type="text/css" href="${base}/plugins/cashGift/css/packet.css" />
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
    
</head>
<body class="contetBack">
	<div class="mui-content contetBack">
		 <div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%"/>
		</div>
	    <div class="rankingList-title share-title3 ">
	    	<form action="${base}/link/send.do" id="form" method="post">
	    		<input type="hidden" value="${vo.balanceAmt }" name="bonus" />
		    	<p class="rankingList-price">
		    	 	<span>累计红包金额${vo.totalAmt }元</span>
		    	</p>
		    	 <p class="packet-top clearboth">
		    	  <span class="rankingList-ranking">未提现金额</span>
		    	 </p>
		    	 <p class="rankingList-price">
		    	 	 ${vo.balanceAmt }
		    	 	 <span class="packet-rightDistance">元</span>
		    	</p>
		    </form>
	    </div>
	    <ul class="packet-top packet-list">
	    	<c:forEach items="${vo.redpackageOpenVos }" var="red">
	    		<li>
		    		<div class="mui-pull-left" >
		    			<p class="ranking-listLeft">
		    				${red.takeTime }
		    			</p>
		    		</div>
		    		<div class="mui-pull-right packet-list-right padding-top0">
		    			<span class="font-size14px">提现</span>
		    			${red.bonus }
		    			<span class="font-size14px">元</span>
		    		</div>
	    		</li>
	    	</c:forEach>
	    </ul>
	    <div class="packet-footerbtn packet-footerbtn2">
	    	<button class="bluecolorBtn distance-btn70 grayColor">提现</button>
	    	<button class="bluecolorBtn distance-btn70 orangeColor">红包排行榜</button>
	    </div>
</body>
<script type="text/javascript">
	$(".orangeColor").click(function() {
		window.location.href = "${base}/award/rank.do";
	});
	
	$(".grayColor").click(function() {
		var balanceAmt = '${vo.balanceAmt }';
		balanceAmt = parseFloat(balanceAmt);
	    if(balanceAmt <= 1){
			mui.toast("提现金额必须大于1元,感谢您的支持!");
			return false;
		}
		$("#form").submit();
	});
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
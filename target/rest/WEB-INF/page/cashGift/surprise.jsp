<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>扫码有奖</title>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/jQuery-backtretch.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>

</head>
<body class="contetBack">

	<div class="mui-content">
		<div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%">
		</div>
		<div class="send-code2">
			<img src="${base}/plugins/cashGift/images/ipiad.png" width="204" />
		</div>
		<c:if test="${flag == true}">
			<div class="packet-button">
				<button id="obtain">领取</button>
			</div>
		</c:if>
		<c:if test="${flag == false}">
			<div style="left: 50%;margin-left: -120px;position: absolute;bottom: 20%;right: 15%;font-weight: bold;text-align:center;color: #CC6600">
				我们已接收到您的喜讯，工作人员将尽快与您联系
			</div>
		</c:if>
		
		<div class="packet-footer">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a> <a
				id="record" class="mui-pull-left color5E7D3D">中奖记录 》</a>
		</div>

	</div>

</body>
<script type="text/javascript">
	$(".packet-button button").on("click", function() {
		$(this).addClass("active")

	})
	
	//完善信息，领取实物大奖
	$("#obtain").click(function() {
		var redId = "${redId}";
		window.location.href = "${base}/lottery/obtainSurprise.do?redId="+redId;
	});
	
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	
	
	$(function() {
		$.backstretch(["${base}/plugins/cashGift/images/goodsback.png"]); //背景
	});
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
</html>
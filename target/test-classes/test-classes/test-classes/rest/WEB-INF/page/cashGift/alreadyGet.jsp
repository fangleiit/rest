<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开奖-已被领取</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<script type="text/javascript"
	src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
</head>
<body class="contetBack">
	<div class="beenGet">
		<div class="beenGetTop">
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<div class="beenGetContent">
			<div class="beenGetContent-word">
				<!-- <p>红包已于</p> -->
				<p>${result.msg}</p>
			</div>

			<div class="beenGetContent-img">
				<img src="${base}/plugins/cashGift/images/shensu.png" width="150" />
			</div>

			<div class="beenGetContent-bottomWord">
				<p>我的红包被别人领取了？</p>
				<p>向客服申述</p>

			</div>

		</div>

		<div class="packet-button beenGteButton">
			<button id="appeal">申诉</button>
		</div>
		<div class="packet-footer">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a>
			<a id="record" class="mui-pull-right color5E7D3D">中奖记录 》</a>
		</div>
		<div class=""></div>

	</div>

</body>
<script type="text/javascript">

	//申述
	$("#appeal").click(function() {
		var redId = "${redId}";
		window.location.href = "${base}/appeal.do?redId="+redId;
	});

	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
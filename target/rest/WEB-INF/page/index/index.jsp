<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>扫码有奖-拆红包</title>
<script src="${base}/plugins/cashGift/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/jQuery-backtretch.js" type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${base}/plugins/cashGift/css/packet.css" />
<!-- 引入微信JS -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>

</head>
<body>
	<div class="SweepCode">
		<div class="SweepCode-goldPic">
			<img src="${base}/plugins/cashGift/images/goldpic.png"/>
		</div>
		 
		<div class="main_content">
	 		<div class="content_head">
	 			<span>100%</span>
	 			<span>中奖</span>
	 		</div>
	 		
	 		<div class="main_inp">
			 	<div  class="content_center">
			     	新品上市&nbsp;开箱领红包
		 		</div>
		 		<div class="packet-button content_footer" onclick="sweepTransform()">
			        <button id="openRedEnvelope" onclick="openRedEnvelope()" >拆红包</button>
			    </div>
			</div>
		</div>
		 <div class="packet-footer">
	     	<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a>
	     	<a id="record" class="mui-pull-right color5E7D3D">中奖记录 》</a>
	     </div>
	</div>
</body>

<script type="text/javascript">
	$(function(){
	    $.backstretch(["${base}/plugins/cashGift/images/Sweepcode.png"]);  //背景
	});	 
	function sweepTransform(){
		$(".SweepCode-goldPic").addClass("SweepCode-transform")
	}
	//拆红包
	/* $("#openRedEnvelope").click(
			function() {
				var productId = '${productId}';
				var shopId = '${shopId}';
				var deliveryTime = '${deliveryTime}';
				var actId = '${actId}';
				var qrcode = '${qrcode}';
				window.location.href = "${base}/lottery.do?productId="+ productId + "&shopId=" + shopId + "&deliveryTime="+ deliveryTime+"&actId="+ actId+"&qrcode="+ qrcode;
			}); */
	//客户要求,延迟一秒开奖
	function openRedEnvelope(){
		document.getElementById("openRedEnvelope").disabled = true;
		setTimeout("open()",1000);
	}
	function open(){
		var productId = '${productId}';
		var shopId = '${shopId}';
		var deliveryTime = '${deliveryTime}';
		var actId = '${actId}';
		var qrcode = '${qrcode}';
		window.location.href = "${base}/lottery.do?productId="+ productId + "&shopId=" + shopId + "&deliveryTime="+ deliveryTime+"&actId="+ actId+"&qrcode="+ qrcode;
	}
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	$(".SweepCode-transform img").css({
		  "animation-name":"xuanzhuan",
		  "animation-duration":"1s"
	})
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		// 通过下面这个API隐藏右上角按钮
		WeixinJSBridge.call('hideOptionMenu');
	});
</script>
</html>
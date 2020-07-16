<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../base/base.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>发放红包</title>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<style type="text/css">
.footer-btnBottom {
	margin: 20px 0px;
	padding-bottom: 20px;
	position: relative !important;
}
</style>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>

</head>
<body class="contetBack">

	<div class="mui-content contetBack">
		<div class="beenGetTop">
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<div class="send-content">
			<p class="size14-color33">
				三全餐饮己经收到您的申诉信息，我们会在查实后与您联系，对您的工作和生活带来的不便，向您表示谦意！
			</p>
			<div class="send-code">
				<p class="send-codeBox-img">
					<img src="${base}/plugins/cashGift/images/twocode.png" />
				</p>
				<p class="send-join">长按二维码,进入"三全餐饮"公众号.查看红包余额</p>

			</div>




		</div>
		<div class="packet-footerbtn">
			<button class="bluecolorBtn">继续扫码</button>
			<button class="yellowcolorBtn">红包排行榜</button>
		</div>

		<div class="packet-footer footer-btnBottom">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a>
			<a id="record" class="mui-pull-right color5E7D3D">中奖记录》</a>
		</div>

	</div>

</body>
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${wxConfig.appid}', // 必填，公众号的唯一标识
	    timestamp: '${wxConfig.timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${wxConfig.signature}',// 必填，签名，见附录1
	    jsApiList: [
	                'getLocation',
	                'checkJsApi',
	                'scanQRCode'
	                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	wx.ready(function () {
		wx.checkJsApi({
	           jsApiList: [
						'getLocation',
						'scanQRCode'
	           ],
	           success: function (res) {
	           },
	           error:function(res){
	           }
	    });
		wx.error(function(res){
			 alert("wx.error"+res.errMsg); 
	    });
	});
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	$(".yellowcolorBtn").click(function() {
		window.location.href = "${base}/award/rank.do";
	});
	
	$(".bluecolorBtn").click(function() {
        wx.scanQRCode({
            // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
            needResult : 1,
            desc : 'scanQRCode desc',
            success : function(res) {
                //扫码后获取结果参数赋值给Input
                var url = res.resultStr;
                mui.openWindow(url);
            }
        });
    });
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
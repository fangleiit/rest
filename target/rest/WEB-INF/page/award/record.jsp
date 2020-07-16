<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>我的红包记录</title>
<style type="text/css">
.scrollBox_old {
	padding-top:60px!important;
	padding-bottom:74px!important;
}
.beenGetTFiexd{position: fixed; background: #E3FFE8; top: 0;}
.packet-footerbtn2{padding: 20px 0px!important;}
</style>
<script type="text/javascript"
	src="${base}/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>

</head>
<body class="contetBack">

	<div class="mui-content contetBack">
		<div class="beenGetTop beenGetTFiexd">
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<ul class="packet-list scrollBox_old packet-top">
			<c:forEach items="${dataList}" var="gift">
				<li>
					<div class="packet-list-left mui-pull-left packetWfour">
						<p class="packet-list-leftName">${gift.prize}</p>
						<p class="packet-list-leftTime">${gift.takeTime}</p>
					</div>
					<c:if test="${gift.ptype == 1}">
						<div class="packet-disinline packet-list-right">
							<span id="isTakeMonry">${not empty gift.isSendout?gift.isSendout==0?'<span class=\'recodr-red\'>未提现<span>':'<span class=\'recodr-green\'>已提现</span>':''}</span>
						</div>
					</c:if>
					<div class="mui-pull-right packet-list-right packetWthree">
						<c:if test="${gift.ptype == 1}">
							${gift.bonus}元
						</c:if>
						<c:if test="${gift.ptype == 2}">
							${gift.remark}
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
		<div class="packet-footerbtn packet-footerbtn2">
			<button class="bluecolorBtn">继续扫码</button>
			<button class="yellowcolorBtn">红包排行榜</button>
		</div>
	</div>

</body>
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
	
	$(function() {
		$(".yellowcolorBtn").click(function() {
			window.location.href = "${base}/award/rank.do";
		});
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
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
<title>红包排行榜</title>
<style type="text/css">
	#scroll {
	height: 230px;
	position: relative;
	overflow: hidden;
}

.scrollBox li {
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #e5e5e5;
	overflow: hidden;
	background: #fff;
	text-indent: 10px;
}

.scrollBox {
	position: absolute;
	width: 100%;
}
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

	<div class="mui-content contetBack main-contentBack">
		<div class="beenGetTop">
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<div class="rankingList-title share-title3 ">
			<!-- <a class="mui-pull-right share-WeChat">分享</a>-->

			<p class=" clearboth">
				<img src="${base}/plugins/cashGift/images/huizhang.png" width="30" />
				<span class="rankingList-ranking">我的排名:${myRank.rowno}</span>
			</p>
			<p class="rankingList-price">
				<span>共计领取</span> ${myRank.bonus}<span class="packet-rightDistance">元</span>
				<%-- ${myRank.total}<span>箱</span> --%>
			</p>
		</div>
		<ul class="packet-top packet-list">
			<li class="packetList-li">
				<div class="mui-pull-left">
					<p class="ranking-listLeft">
						<img src="${base}/plugins/cashGift/images/diyiming.png" width="30" />
						<span>第一名</span>
					</p>
				</div>
				<div class="packet-color148C3E redPacket-centerWord">${one.wechatNikename}</div>
				<div class="mui-pull-right packet-list-right">${one.bonus}元</div>
			</li>
			<li class="packetList-li">
				<div class="mui-pull-left">
					<p class="ranking-listLeft">
						<img src="${base}/plugins/cashGift/images/dierming.png" width="30" />
						<span>第二名</span>
					</p>
				</div>
				<div class="packet-color148C3E redPacket-centerWord">${two.wechatNikename}</div>
				<div class="mui-pull-right packet-list-right">${two.bonus}元</div>
			</li>
			<li class="packetList-li">
				<div class="mui-pull-left">
					<p class="ranking-listLeft">
						<img src="${base}/plugins/cashGift/images/disanming.png"
							width="30" /> <span>第三名</span>
					</p>
				</div>
				<div class="packet-color148C3E redPacket-centerWord">${three.wechatNikename}</div>
				<div class="mui-pull-right packet-list-right">${three.bonus}元</div>
			</li>

		</ul>

		<div class="rankingList-draw rankingList-panel">中奖名单</div>
		<div id="scroll">
			<ul  class="scrollBox">
				<c:forEach items="${winningNameList}" var="obj">
					<li class="rankingList-roll">
						<div class="mui-pull-left packet-color148C3E">${obj.wechatNikename}</div>
						<div class="mui-pull-left packet-color666">用户扫码获得</div>
						<div class="mui-pull-right packet-color982">
							<c:if test="${obj.ptype == 1}">
								红包${obj.bonus}元
							</c:if>
							<c:if test="${obj.ptype == 2}">
								${obj.remark}
							</c:if>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="packet-footerbtn packetShare">
			<button id="share" onclick="shareRank();" class="bluecolorBtn distance-btn70">分享</button>
		</div>
	</div>

</body>
<script type="text/javascript">

	

	//滚动
	var scrollIndex = 0; 
	var Timer = null; 
	function scroll_f() {
		clearInterval(Timer);
		var ul = $("#scroll ul");
		var li = ul.children("li");
		var h = li.height() + 1;
		var index = 0;
		ul.css("height", h * li);
		ul.html(ul.html() + ul.html());
		function run() {
			if (scrollIndex >= li.length) {
				ul.css({
					top : 0
				});
				scrollIndex = 1;
				ul.animate({
					top : -scrollIndex * h
				}, 500);
			} else {
				scrollIndex++;
				ul.animate({
					top : -scrollIndex * h
				}, 500);
			}
		}
		Timer = setInterval(run, 2000);
	}
	$(function() {
		scroll_f();
		
		
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${wxConfig.appid}', // 必填，公众号的唯一标识
		    timestamp: '${wxConfig.timestamp}', // 必填，生成签名的时间戳
		    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
		    signature: '${wxConfig.signature}',// 必填，签名，见附录1
		    jsApiList: [
		                'getLocation',
		                'checkJsApi',
		                'onMenuShareAppMessage',
		                'onMenuShareTimeline'
		                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		}); 

		wx.checkJsApi({
		    jsApiList: [
                'getLocation',
                'checkJsApi',
                'onMenuShareAppMessage',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareTimeline',
                'onMenuShareQQ',
    	        'onMenuShareWeibo',
    	        'onMenuShareQZone'
                ], // 需要检测的JS接口列表，所有JS接口列表见附录2,
		    success: function(res) {
		        // 以键值对的形式返回，可用的api值true，不可用为false
		        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
		    }
		});
		
		
		wx.error(function(res){
			alert("error:"+res);
		});
		
		wx.ready(function(){
			
			var shareData = {
		        title: '我的三全红包获奖排名',
		        link: '${base}/share.do?userOpenId=${openId}&type=cash',
		        imgUrl: '${base}/plugins/cashGift/images/hongbao-logo.png',
		        trigger: function (res) {
		          // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		          //alert('用户点击分享到朋友圈');
		        },
		        success: function (res) {
		          //alert('已分享');
		        },
		        cancel: function (res) {
		          //alert('已取消');
		        },
		        fail: function (res) {
		          alert(JSON.stringify(res));
		        }
		      };
			
			wx.onMenuShareAppMessage(shareData);
		    wx.onMenuShareTimeline(shareData);
		    wx.onMenuShareQQ(shareData);
		    wx.onMenuShareWeibo(shareData);
		    wx.onMenuShareQZone(shareData);
		    
		    
		    
		});
		
	})
	
	//分享排行
	function shareRank(){
		window.location.href = "${base}/share.do?userOpenId=${openId}&type=cash";
		 //mui.toast('请点击右上角的菜单栏使用分享功能',{ duration:'long', type:'div' }); 
	}
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
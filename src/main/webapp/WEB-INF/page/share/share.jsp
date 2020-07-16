<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>奖励分享</title>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>

</head>
<body class="contetBack">
	<div class="mui-content contetBack">
		<div class="beenGetTop">
		
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<div class="send-content share-content">
			<div class="share-title1 packet-top">恭喜&nbsp;${nickname}&nbsp;通过扫码获得${own}</div>
			<div class="share-title3 packet-top">
				<img src="${base}/plugins/cashGift/images/huizhang.png" width="24" />
				<a href="">${userRowno}<span>位</span></a>
			</div>
			<!-- <div class="share-title2 packet-top">累计扫码 : <span class="fontsize20px">9989</span>箱</div> -->
			<div class="send-code">
				<p class="send-codeBox-img">
					<img src="${base}/plugins/cashGift/images/twocode.png" />
				</p>
				<p class="send-join">长按二维码,进入"三全餐饮"公众号.查看红包余额</p>
			</div>
		</div>
		<div class="packet-footerbtn">
			<button
				onclick="mui.toast('请点击右上角的菜单栏使用分享功能',{ duration:'long', type:'div' });"
				class="bluecolorBtn distance-btn70">分享</button>
		</div>
	</div>

</body>
<script type="text/javascript">
	var shareUrl = '${shareUrl}';
	var imgUrl = '${base}/plugins/cashGift/images/hongbao-logo.png';
	var title = "我的三全红包获奖排名";
	var desc = "我的三全红包获奖排名";
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : '${wxConfig.appid}', // 必填，公众号的唯一标识
		timestamp : '${wxConfig.timestamp}', // 必填，生成签名的时间戳
		nonceStr : '${wxConfig.nonceStr}', // 必填，生成签名的随机串
		signature : '${wxConfig.signature}',// 必填，签名，见附录1
		jsApiList: ['onMenuShareTimeline',
		                'onMenuShareAppMessage',
		                'onMenuShareQQ',
		                'onMenuShareWeibo',
		                'onMenuShareQZone',
		                'checkJsApi'
		                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	<!--
	wx.ready(function () {
		 wx.checkJsApi({
	            jsApiList: [
							'onMenuShareTimeline',
							'onMenuShareAppMessage',
							'onMenuShareQQ',
							'onMenuShareWeibo',
							'onMenuShareQZone'
	            ],
	            success: function (res) {
	            },
	            error:function(res){
	            }
	        });
		 wx.error(function(res){
			 alert("wx.error"+res.errMsg); 
	     });
		 var shareData = {
					title : '我的三全红包获奖排名',
					link : '${base}/share.do?userOpenId=${openId}&type=cash',
					imgUrl : '${base}/plugins/cashGift/images/hongbao-logo.png',
					success : function(res) {
						//alert('已分享');
					},
					cancel : function(res) {
						//alert('已取消');
					}
				};
		//分享到朋友圈
		wx.onMenuShareTimeline(shareData);
		//分享给朋友
		wx.onMenuShareAppMessage(shareData);
		//分享到QQ
		wx.onMenuShareQQ(shareData);
		//分享到腾讯微博
		wx.onMenuShareWeibo(shareData);
		//分享到QQ空间
		wx.onMenuShareQZone(shareData);
	 });
	-->
	 wx.ready(function () {
		 //????????
		 wx.checkJsApi({
	            jsApiList: [
							'onMenuShareTimeline',
							'onMenuShareAppMessage',
							'onMenuShareQQ',
							'onMenuShareWeibo',
							'onMenuShareQZone'
	            ],
	            success: function (res) {
	            },
	            error:function(res){
	            }
	        });
		 wx.error(function(res){
			 alert("wx.error"+res.errMsg); 
	     });
		//分享到朋友圈
		wx.onMenuShareTimeline({
		    title: title, // 分享标题
		    link: shareUrl, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { }, //成功回调
		    cancel: function () { }, //失败回调
		});
		//分享给朋友
		wx.onMenuShareAppMessage({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: shareUrl, // 分享链接
		    success: function () { 
		        
		    },
		    cancel: function () { 
		    }
		});
		//分享到QQ
		wx.onMenuShareQQ({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: shareUrl, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
		//分享到腾讯微博
		wx.onMenuShareWeibo({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: shareUrl, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		//分享到QQ空间
		wx.onMenuShareQZone({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: shareUrl, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
	 });
</script>
</html>
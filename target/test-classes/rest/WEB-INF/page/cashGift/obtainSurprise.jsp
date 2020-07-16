<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../base/base.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>发放红包</title>
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
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
			<p class="size14-color33 textAlignleft">
				我们已经把您获奖的好消息，通知管理员了；TA会在1个工作日内与您联系！请保持<label id="phoneLabel">${info.mobilePhone }</label>手机畅通。</p>
			<input type="hidden" name="id" value="${info.id}" >
			<div class="goodsTwo-content">
				<p class="mui-pull-left goodsTwo-content-img">
					<img src="${base}/plugins/cashGift/images/ipiad_old.png" />
				</p>
				<p class="mui-pull-left goodsTwo-content-btn">
					<button class="update-phone">更换手机号码？</button>
					<button onclick="sharLottery();" class="show-btn">炫耀一下</button>
				</p>

			</div>
		</div>
		<div class="packet-footerbtn clearboth">
			<button class="bluecolorBtn">继续扫码</button>
			<button class="yellowcolorBtn">红包排行榜</button>
		</div>

		<div class="packet-footer">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a> <a
				id="record" class="mui-pull-left color5E7D3D">中奖记录 》</a>
		</div>

	</div>

	<!--弹框-->
	<div class="goods-cover" style="display: none;">
		<div class="pop-up">
			<div class="first-input">
				<input type="text" name="phone" id="" value="" placeholder="请输新入手机号" />
			</div>
			<div class="second-input">
				<input type="text" name="validCode" id="" value="" placeholder="请输入验证码" />
				<button id="validCodeBtn">发送验证码</button>
			</div>
			<div class="third-button clearboth">
				<button class="close-cover">取消</button>
				<button id="submit">确定</button>
			</div>
		</div>
	</div>


</body>
<script type="text/javascript">
	$(".update-phone").on("click", function() {
		$(".goods-cover").show();
	})
	$(".close-cover").on("click", function() {
		$(".goods-cover").hide();
	})
	$(".yellowcolorBtn").click(function() {
			window.location.href = "${base}/award/rank.do";
		});
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	
	
	$("#submit").click(function(){
		var phone = $("input[name='phone']").val();
		var validCode = $("input[name='validCode']").val();
		if (!(/^1\d{10}$/.test(phone))) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			return;
		}
		if (!(/\d{6}/.test(validCode))) {
			mui.toast('请输入正确的验证码!',{ duration:'long', type:'div' });
			return;
		}

		var boo = validCodeByAjax(phone, validCode);
		if (!boo) {
			mui.toast(validCodeInfo,{ duration:'long', type:'div' });
			return;
		}
		var redId = '${redId}';
		$.ajax({
			url : "${base}/award/updatePhone.do",
			type : "POST",
			async : false,
			data : {
				"phone" : phone,
				"id" : "${info.id}",
				"redId":redId
			},
			success : function(ret) {
				$(".goods-cover").hide();
				var res = $.parseJSON(ret);
				if(res.flag){
					$("#phoneLabel").html(phone);
					mui.toast('修改成功!',{ duration:'long', type:'div' });
				}else{
					mui.toast('修改失败!',{ duration:'long', type:'div' });
				}
			}
		});
	});
	

	//校验手机验证码
	var validCodeInfo;//验证码错误反馈
	function validCodeByAjax(phone, code) {
		var boo = false;
		$.ajax({
			url : "${base}/award/validCode.do",
			type : "POST",
			async : false,
			data : {
				"phone" : phone,
				"code" : code
			},
			success : function(ret) {
				var b = $.parseJSON(ret).flag;
				boo = b;
				if(boo==false){
					validCodeInfo=$.parseJSON(ret).info;
				}
			}
		});
		return boo;
	}
	/* document.getElementById("confirmBtn").addEventListener('tap', function() {
	           var btnArray = ['是', '否'];
	           mui.confirm('是否允许获取地理位置信息？', '', btnArray, function(e) {
	               console.log(e);
	           })
	       }); */

	var wait = 59;
	document.getElementById("validCodeBtn").disabled = false;
	function time(o) {
		if (wait == 0) {
			o.removeAttribute("disabled");
			o.innerHTML = " 发送验证码";
			wait = 59;
		} else {
			o.setAttribute("disabled", true);
			o.innerHTML = "重新发送(" + wait + ")";
			wait--;
			setTimeout(function() {
				time(o)
			}, 1000)
		}
	}
	document.getElementById("validCodeBtn").onclick = function() {
		var phone = $("input[name='phone']").val();
		if (!(/^1\d{10}$/.test(phone))) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			return false;
		}

		time(this);

		$.ajax({
			url : "${base}/award/sendValidCode.do",
			type : "POST",
			async : false,
			data : {
				"phone" : phone
			},
			success : function(ret) {
				console.log(ret);
			}
		});
	};
	
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
	                'scanQRCode',
	                'onMenuShareAppMessage',
	                'onMenuShareTimeline',
	                'onMenuShareQQ',
	    	        'onMenuShareWeibo',
	    	        'onMenuShareQZone'
	                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	wx.ready(function () {
		wx.checkJsApi({
	           jsApiList: [
						'getLocation',
						'scanQRCode',
		                'onMenuShareAppMessage',
		                'onMenuShareTimeline',
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
		        title: '我的三全红包获奖排名',
		        link: '${base}/share.do?userOpenId=${openId}&type=gift',
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
	//炫耀一下
	function sharLottery(){
		window.location.href="${base}/share.do?userOpenId=${info.openid}&type=gift";
	}
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
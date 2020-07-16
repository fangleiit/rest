<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<style type="text/css">
.footer-btnBottom {
	margin: 20px 0px;
	padding-bottom: 20px;
	position: relative !important;
}
</style>
<title>完善信息领红包</title>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
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

		<div class="perfect-content">
			<div class="size14-color33 textLeft">您是第一次领取扫码红包，请填写相关信息以便我们给你提供更多服务</div>
			<div class="perfect-phone">
				<form action="${base}/award/send.do" id="form" method="post">
				<!-- <form action="http://192.168.3.119:8081/rest/award/send.do" id="form" method="post"> -->
					<input type="hidden" value="${paramVo.qrcode}" name="qrcode" />
					<input type="hidden" value="${paramVo.actId}" name="actId" />
					<input type="hidden" value="${redIdValue}" name="redId" />
					<input type="hidden" value="${bonusValue}" name="bonus" />
					<input type="hidden" value="${paramVo.flag}" name="flag" />
					<input type="hidden" value="${uuid}" name="uuid" />
					<input type="hidden" value="WJ001" name="questionVer">
					<input type="hidden" id="longitude" name="longitude" value=""/>
					<input type="hidden" id="latitude" name="latitude" value=""/>
					<p class="packet-top">
						<input type="text" value="" name="phone" placeholder="请输入手机号" />
					</p>
					<p class="perfect-code">
						<input type="text" value="" name="validCode" placeholder="请输入验证码" />
						<button id="validBtn">发送验证码</button>
					</p>
					<p class="perfect-type">
						<span class="mui-pull-left">终端类型</span>
					<ul class="changeRadio mui-pull-left" style="margin-bottom: 12px;">
						<c:forEach items="${channelList}" var="obj">
							<c:if  test="${obj.channelCode=='N002'}">
								<li style="width: 100%;margin-bottom: 5px;">
									<input type="radio" class="radio-inp" name="channel" value="${obj.channelCode}" /> 
								<label for="">${obj.channelName}</label>
								</li>
							</c:if>
							<c:if  test="${obj.channelCode!='N002'}">
								<li class="width50" style="margin-bottom: 5px;">
									<input type="radio" class="radio-inp" name="channel" value="${obj.channelCode}" /> 
									<label for="">${obj.channelName}</label>
								</li>
							</c:if>
						</c:forEach>
					</ul>

					<p class="perfect-radio clearboth">
						<input class="radio-inps " type="radio" name="agreeRule" id=""
							value="" /> <label for="">已阅读并同意三全扫码</label><a href="${base}/rules/ruleDescription.do">活动规则</a>
					</p>
				</form>

			</div>
			<div class="footer-btn">
				<button id="confirmBtn" disabled="disabled">提现（￥${bonusValue}）</button>
			</div>



		</div>

		<div class="packet-footer footer-btnBottom">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a>
			<a id="record" class="mui-pull-right color5E7D3D">中奖记录 》</a>
		</div>
	</div>

</body>
<script type="text/javascript">
	$(".radio-inp").on(
			"click",
			function() {
				if ($(this).hasClass("active")) {
					$(this).removeClass("active");
				} else {
					$(this).addClass("active").parent().siblings()
							.find("input").removeClass("active");
				}
			})
	$(".radio-inps").on("click", function() {
		if ($(this).hasClass("active")) {
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	})

	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});

	//表单提交校验
	document.getElementById("confirmBtn").addEventListener('tap', function() {
		$("#confirmBtn").attr("disabled",true);
		var longitude = $("#longitude").val();
		var latitude = $("#latitude").val();
		/* if(longitude == "" || latitude == ""){
			mui.toast('未获取到位置信息，请允许获取位置信息!',{ duration:'long', type:'div' });
			return;
		} */
		var phone = $("input[name='phone']").val();
		var validCode = $("input[name='validCode']").val();
		if(null== phone || phone == "" || phone == 'undefind'){
			mui.toast('请输入手机号码!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}
		var preg = /^1[34578]\d{9}$/;
		if (!preg.test(phone)) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}
		if (!(/\d{6}/.test(validCode))) {
			mui.toast('请输入正确的验证码!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}

		var boo = validCodeByAjax(phone, validCode);
		if (!boo) {
			//显示验证码错误反馈
			mui.toast(validCodeInfo,{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}
		
		var channelCheck = $("input[name='channel']:checked").size();
		var clz = $("input[name='channel']:checked").attr("class");
		if(!clz || (clz && (clz.indexOf("active") == -1))){
			mui.toast('请选择终端类型!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}
		
		var channelCheck = $("input[name='agreeRule']:checked").size();
		if(!channelCheck){
			mui.toast('请阅读并同意三全扫码活动规则!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return;
		}
		$("#form").submit();
	});

	$("input[name='agreeRule']").click(function(){
		var clz = $("input[name='agreeRule']").attr("class");
		if(clz.indexOf("active") != -1){
			$("#confirmBtn").removeAttr("disabled");
		}else{
			$("#confirmBtn").attr("disabled","disabled");
		}
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
	document.getElementById("validBtn").disabled = false;
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
	document.getElementById("validBtn").onclick = function() {
		var phone = $("input[name='phone']").val();
		if (!(/^1\d{10}$/.test(phone))) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			return false;
		}
		time(this);
		$.ajax({
			url : "${base}/award/sendValidCode.do",
			type : "POST",
			async : true,
			data : {
				"phone" : phone
			},
			success : function(ret) {
				console.log(ret);
			}
		});
	};
	
	
	$("input[name='agreeRule']").click();
	
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${wxConfig.appid}', // 必填，公众号的唯一标识
	    timestamp: '${wxConfig.timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${wxConfig.signature}',// 必填，签名，见附录1
	    jsApiList: [
	                'getLocation',
	                'checkJsApi'
	                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	wx.ready(function () {
		wx.checkJsApi({
	           jsApiList: [
						'getLocation'
	           ],
	           success: function (res) {
	           },
	           error:function(res){
	           }
	    });
		wx.error(function(res){
			mui.toast('注入微信接口权限验证配置失败,'+res.errMsg+'!',{ duration:'long', type:'div' });
	    });
		wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		        $("#latitude").val(latitude);
		        $("#longitude").val(longitude);
		        //alert(JSON.stringify(res)); 
		        //alert("latitude:"+latitude+",longitude:"+longitude+"accuracy:"+accuracy);
		    },
		    error:function(res){
		    	mui.toast('获取地理位置失败,'+res.errMsg+'!',{ duration:'long', type:'div' });
		    }
		});
	});
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
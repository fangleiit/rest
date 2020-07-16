<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>扫码有奖-开奖</title>
    <script src="${base}/plugins/cashGift/js/mui.min.js"></script>
    <script src="${base}/plugins/cashGift/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="${base}/plugins/cashGift/js/jQuery-backtretch.js" type="text/javascript" charset="utf-8"></script>
    <link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${base}/plugins/cashGift/css/packet.css"/>
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
    
</head>
<body>
	 
	<div class="<!--packet-contentBack award-back-->">
		<div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%">
		</div>
		<div class="packet-time">${msg}</div>
		
		 <div class="packet-award"> 
		 	<span><a>¥</a>${bonus}</span>
		 </div>
		 
	     <div class="packet-button openRedPacket">
	        <button id="scanQRCode">继续扫码</button>
	     </div>
	     <div class="packet-footer">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a> <a
				id="record" class="mui-pull-left color5E7D3D">中奖记录 》</a>
		</div>
	</div>
	
</body>
 <script type="text/javascript">
 
//随机背景
	$(function(){
		bg = new Array(6);

		bg[0] = '${base}/plugins/cashGift/images/openBack/openOne.png'; 
		bg[1] = '${base}/plugins/cashGift/images/openBack/openTwo.png';
		bg[2] = '${base}/plugins/cashGift/images/openBack/openThree.png';
		bg[3] = '${base}/plugins/cashGift/images/openBack/openFour.png';
		bg[4] = '${base}/plugins/cashGift/images/openBack/openFive.png';
		bg[5] = '${base}/plugins/cashGift/images/openBack/openSix.png';
		bg[6] = '${base}/plugins/cashGift/images/openBack/openSeven.png';
		index = Math.floor(Math.random() * bg.length);
	    $.backstretch(bg[index]);  //背景
	});
	
	$(".input-inp").on("click",function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active")
		}else{
			$(this).addClass("active")
		}
	})
	//提现
	$("#action").click(function(){
		var actId = "${paramVo.actId}";
		var qrcode = "${paramVo.qrcode}";
		window.location.href = "${base}/lottery/obtainCash.do?actId="+actId+"&qrcode="+qrcode;
	});
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	$("#scanQRCode").click(function() {
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
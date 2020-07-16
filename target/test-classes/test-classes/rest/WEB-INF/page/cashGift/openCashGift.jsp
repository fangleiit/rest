<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>扫码有奖-开奖</title>
<script src="${base}/plugins/cashGift/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/jQuery-backtretch.js" type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
</head>
<body>

	<div>
		<div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%">
		</div>
		<div class="packet-award">
			<span><a>¥</a>${bonusValue}</span>
		</div>
		<div class="packet-balance" <c:if test="${empty balance}">style="display: none;"</c:if>>
			<input type="radio" class="input-inp" name="flag" id="balance" value="${balance}" />
			 <label for="">红包余额 ¥ ${balance}</label>
		</div>
		<div class="packet-button openRedPacket">
			<button id="action" data="${bonusValue}">领取 (￥${bonusValue})</button>
		</div>
		<div class="packet-footer">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a> <a
				id="record" class="mui-pull-left color5E7D3D">中奖记录 》</a>
		</div>
	</div>

</body>
<script type="text/javascript">
	//开奖随机背景
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
	
	$("#balance").click(function(){
		var balance = $(this).val();
		if(balance == ""){
			balance = 0;
		}
		var clz = $(this).attr("class");
		
		var data = $("#action").attr("data");
		
		var str = '';
		if(clz && clz.indexOf("active") == -1){
			data = parseFloat(data)+parseFloat(balance);
		}else{
			data = parseFloat(data)-parseFloat(balance);
		}
		data = data.toFixed(2);
		str = "领取 (￥"+data+")";
		 
		/* if(data >= 1){
			$("#action").removeAttr("disabled");
		}else{
			$("#action").attr("disabled","disabled");
		} */
		$("#action").attr("data",data);
		$("#action").html(str);
	});


	$(".input-inp").on("click", function() {
		if ($(this).hasClass("active")) {
			$(this).removeClass("active")
		} else {
			$(this).addClass("active")
		}
	})
	//点击领取的次数
	var count = 0;
	//默认提示语
	var msg = "请重新扫码!";
	//领取
	$("#action").click(function(){
		$("#action").attr("disabled",true);
		var actId = "${paramVo.actId}";
		var qrcode = "${paramVo.qrcode}";
		var bonusValue = "${bonusValue}";
		var redIdValue = "${redIdValue}";
		var uuid = "${uuid}"
		var val = $("#action").attr("data");
		
		var balance = $("#balance").val();
		
		var clz = $("#balance").attr("class");
		var url = "${base}/lottery/obtainCash.do?actId="+actId+"&qrcode="+qrcode+"&bonus="+bonusValue+"&redId="+redIdValue+"&uuid="+uuid;
		if(clz && clz.indexOf("active") == -1){
			
		}else{
			url += "&flag="+balance;
			
		}
		window.location.href = url;
		
	});
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});
	
	$(function(){
		$("#balance").click();
	});
	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});

</script>
</html>
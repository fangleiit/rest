<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>

<html lang="en"><head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>错误信息</title>
<meta name="keywords" content="请使用微信二维码扫描">
<meta name="description" content="请使用微信二维码扫描">
<link href="http://static.orayimg.com/weixin/css/weixin-1.0.2.css" type="text/css" rel="stylesheet">
<script async="" src="//www.google-analytics.com/analytics.js"></script><script src="http://static.orayimg.com/js/jquery-1.7.1.js"></script>
<script src="http://static.orayimg.com/weixin/js/common-1.0.1.js?1"></script>
<script src="http://static.orayimg.com/weixin/js/weixin-1.0.3.js"></script>
</head>
<body>

<div class="container" id="main-height">
    <div id="succeed-box" style="position: relative;">
        <div id="succeed-box-usual" style="display: block;">
            <div class="succeed-wrap">
                <p class="icon-wrap"><i class="mobile-iconfont"></i></p>
                <h2>请使用微信二维码扫描</h2>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
 window.onload=function(){
	var t = $('#main-height').offset().top;
	var height = document.body.offsetHeight - t;
	$('#succeed-wrap').css('height',height);
} 
 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	      WeixinJSBridge.call('hideOptionMenu');
});
</script>
<script type="text/javascript">
	WX.initBind();
</script>


</body></html>
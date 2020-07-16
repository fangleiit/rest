<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		.add-title {display: inline-block; width: 70%;margin-top: 17px;}
    	.add-header img{width: 60px;float: left;}
    	.add-type{font-size: 14px;  margin: 15px 0px;  text-align: left; clear: both;}
    	.add-shopSearch{clear: both;}
    	.add-shopSearch input{width: 66%; margin-left:34%;}
    	.add-shopSearch span{background: #F0962A;display: inline-block;position: absolute;width:28%; text-align: center;   height: 40px;  display: inline-block; vertical-align: middle; color: #fff; line-height: 40px;  border-radius: 4px; padding: 0px 10px;}
    	.add-address-imgBox img{width:24px}
    	.add-address-imgBox {float: left;}
    	.add-address-content h1 {font-size: 14px;margin-left: 40px;font-weight: normal;}
    	.add-addressC{margin-left: 40px;margin-top: 10px;color: #666;}
    	.add-ul li{margin-bottom: 20px;}
    	.add-ul{padding-bottom: 40px;}
    	.add-footerBtn{position: fixed;  width: 100%;  bottom:0px;}
	</style>
	<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
    <script src="${base}/plugins/cashGift/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="${base}/plugins/cashGift/js/cityselect.js"></script>
    <link href="${base}/plugins/cashGift/css/cityselect.css" rel="stylesheet" />
	<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css"
		href="${base}/plugins/cashGift/css/packet.css" />
    <script type="text/javascript" charset="utf-8">
      	mui.init();
    </script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=SVh7hzBinAgs0PKMqXvlPKbNbFWwtjgx"></script>
	<title>门店信息</title>
</head>
<body class="contetBack">
	<div class="mui-content contetBack">
		<!--<div class="beenGetTop add-header">
		 	<img src="images/hongbao-logo.png"/>
		 	<span class="add-title">
		 		三全餐饮，专注、专业！您是我们的忠实粉丝，请补充门店信息，以便我们给您更多惊喜
			</span>
		</div>-->
	    
	     <div class="beenGetTop">
		 	<img src="${base}/plugins/cashGift/images/topimg.png" width="100%"/>
		</div>
	    <div class="perfect-content">
	    	<div class="size14-color33 textLeft"> 三全餐饮，专注、专业！您是我们的忠实粉丝，请补充门店信息，以便我们给您更多惊喜</div>
	    </div>
	    <div class="perfect-content">
	     
	    	<div class="perfect-phone">
	    		<form action="${base}/award/terminalInfo.do" id="form" method="post">
	    		    <input type="hidden" value="${infoEntity.id}" name="terminalId">
	    		    <input type="hidden" value="${paramVo.qrcode}" name="qrcode" />
					<input type="hidden" value="${paramVo.actId}" name="actId" />
					<input type="hidden" value="${redIdValue}" name="redId" />
					<input type="hidden" value="${bonusValue}" name="bonus" />
					<input type="hidden" value="${paramVo.flag}" name="flag" />
					<input type="hidden" value="${uuid}" name="uuid" />
					<input type="hidden" value="WJ002" name="questionVer">
    				<!-- <span class="mui-pull-left add-type">终端类型</span> -->
    				<!-- <ul class="changeRadio mui-pull-left addClick">
    					<li class="width40">
    						<input type="radio" checked="checked" class="radio-inp" name="" id="" value="" />
    					    <label for="">酒店,餐厅</label>
    					</li>
    					<li class="width60">
    						<input type="radio" class="radio-inp" name="" id="" value="" />
    					    <label for="">乡厨</label>
    					</li>
    					
    					<li class="width40">
    						<input type="radio" class="radio-inp" name="" id="" value="" />
    					    <label for="">流动餐车</label>
    					</li>
    					<li class="width60">
    						<input type="radio" class="radio-inp" name="" id="" value="" />
    					    <label for="">农贸市场门市</label>
    					</li>
    					<li class="width40">
    						<input type="radio" class="radio-inp" name="" id="" value="" />
    					    <label for="">超市</label>
    					</li>
    					<li class="width60">
    						<input type="radio" class="radio-inp" name="" id="" value="" />
    					    <label for="">学校、企事业食堂</label>
    					</li>
    				</ul> --> 
    				<%-- <ul class="changeRadio mui-pull-left" style="margin-bottom: 12px;">
						<c:forEach items="${channelList}" var="obj">
							<c:if  test="${obj.channelCode=='N002'}">
								<li style="width: 100%;margin-bottom: 5px;">
									<c:if test="${obj.channelCode==infoEntity.channel}">
										<input type="radio" class="radio-inp active" checked="checked" onclick="chang()" name="channel" value="${obj.channelCode}" /> 
										<label for="">${obj.channelName}</label>
									</c:if>
									<c:if test="${obj.channelCode!=infoEntity.channel}">
										<input type="radio" class="radio-inp" name="channel" onclick="chang()" value="${obj.channelCode}" /> 
										<label for="">${obj.channelName}</label>
									</c:if>
								</li>
							</c:if>
							<c:if  test="${obj.channelCode!='N002'}">
								<li class="width50" style="margin-bottom: 5px;">
									<c:if test="${obj.channelCode==infoEntity.channel}">
										<input type="radio" class="radio-inp active" checked="checked" onclick="chang()" name="channel" value="${obj.channelCode}" /> 
										<label for="">${obj.channelName}</label>
									</c:if>
									<c:if test="${obj.channelCode!=infoEntity.channel}">
										<input type="radio" class="radio-inp" name="channel" onclick="chang()" value="${obj.channelCode}" /> 
										<label for="">${obj.channelName}</label>
									</c:if>
								</li>
							</c:if>
						</c:forEach>
					</ul> --%>
					<div id="storeDiv">
						<!-- <div class="add-shopSearch" id="maps">
		    				<span>城市</span>
		    				<input type="text" style="width:200px;" class="cityinput" id="citySelect" placeholder="请输入目的地">
		    			</div> -->
			    		<div class="add-shopSearch" id="r-result">
			    			<span>门店名称</span> 
			    			<input type="text" id="suggestId" style="width:240px" name="storeName"/>
			    			<input type="hidden" id="storeCity" name="storeCity">
			    			<input type="hidden" id="storeDistrict" name="storeDistrict">
			    		</div> 
		    		</div>
		    		<div id="contact" style="display: none">
		    			<div class="add-shopSearch" >
		    				<span>联系人</span>
		    				<input type="text" id="contacts" name="contacts" style="width:240px">
		    			</div>
		    		</div>
	    		</form>
	    		 
	    	</div>
	    	<!--内容框-->
	    	<!-- <div class="add-content">
	    		
	    	</div> -->
	    </div>
	  	<div class="footer-btn add-footerBtn">
    		<button id="confirmBtn">确定</button>
    	</div>   
	</div>
	<div id="l-map"></div>
	<!-- <div id="r-result">请输入:<input type="text" id="suggestId" size="20" value="百度" style="width:150px;" /></div> -->
	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
<script type="text/javascript">
	//var test=new Vcity.CitySelector({input:'citySelect'});
	var city = "${infoEntity.city}";
	//$("#citySelect").val(city);
	
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}
	$(function(){
		//getMap();
		var radio = $("input[type='radio']:checked").val();
		if(radio == "X001" || radio == "D001"){
			$("#storeDiv").hide();
			$("#contact").show();
		}
		
	})
	function chang(){
		var radio = $("input[type='radio']:checked").val();
		if(radio == "X001" || radio == "D001"){
			$("#storeDiv").hide();
			$("#contact").show();
		}else{
			$("#storeDiv").show();
			$("#contact").hide();
		}
	}
	//function getMap(){
		var map = new BMap.Map("l-map");
		map.centerAndZoom(city,12);  
		
		// 初始化地图,设置城市和地图级别。

		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "suggestId"
			,"location" : map
		});

		ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				$("#storeCity").val(_value.city);
				$("#storeDistrict").val(_value.district);
			}    
			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});

		var myValue;
		ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
		var _value = e.item.value;
			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
			/* $("#storeProvince").val(_value.province); */
			$("#storeCity").val(_value.city);
			$("#storeDistrict").val(_value.district);
		});
	//} 
	
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
	//表单提交验证
	document.getElementById("confirmBtn").addEventListener('tap', function() {
		$("#confirmBtn").attr("disabled",true);
		var storeName = $("#suggestId").val();
		var contacts = $("#contacts").val();
		if(storeName== "" && contacts == ""){
			mui.toast('请完善信息!',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return false;
		}
		if(contacts.length > 20){
			mui.toast('联系人名称太长',{ duration:'long', type:'div' });
			$("#confirmBtn").attr("disabled",false);
			return false;
		}
		$("#form").submit();
	});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>申诉</title>
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>

</head>
<body class="contetBack">

	<div class="mui-content contetBack">
		<div class="beenGetTop">
			<img src="${base}/plugins/cashGift/images/topimg.png" width="100%" />
		</div>
		<div class="perfect-content">
			<div class="size14-color33 textLeft">
				请留下您的联系方式和相关申诉信息，我们会查实后与您联系。谢谢！</div>
			<form action="${base}/appeal/saveAppeal.do" id="form"
				enctype="multipart/form-data" method="post">
				<div class="perfect-phone">
					<p class="packet-top">
						<input type="hidden" name="redid" value="${redId}" />
						
						<input type="hidden" name="province" value="" />
						<input type="hidden" name="city" value="" />
						<input type="hidden" name="area" value="" />
						<input type="hidden" name="address" value="" /> 
						<input type="text" name="mobilePhone" value="" placeholder="请输入手机号" />
					</p>
					<p class="perfect-code">
						<input type="text" name="validCode" value="" placeholder="请输入验证码" />
						<button id="validBtn">发送验证码</button>
					</p>

					<div class="packet-top">
						<p>定位地址</p>
						<p id="address"></p>
						<textarea class="perfect-textarea" name="note" rows="3" cols=""
							placeholder="填写申诉原因,最多30个字"></textarea>
					</div>

					<div class="perfect-file" id="imgDiv">
						<span class="">
						<img alt="" class="perfect-file-imgbox" src="" name="files1" id="img1">
						<img alt="" class="perfect-file-imgbox" src="" name="files2" id="img2">
						</span>
					</div>

				</div>


				<div class="footer-btn">
					<button type="button" id="submitButton">提交</button>
				</div>
			</form>
		</div>
		<div onclick="" class="packet-footer distanceFooter">
			<a id="ruleDescription" class="mui-pull-left color5E7D3D">活动规则 》</a>
			<a id="record" class="mui-pull-right color5E7D3D">中奖记录 》</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	//规则说明
	$("#ruleDescription").click(function() {
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function() {
		window.location.href = "${base}/award/record.do";
	});

	$("#submitButton").click(function() {
		var mobilePhone = $("input[name='mobilePhone']").val();
		var validCode = $("input[name='validCode']").val();
		if (!(/^1\d{10}$/.test(mobilePhone))) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			return;
		}
		if (!(/\d{6}/.test(validCode))) {
			mui.toast('请输入正确的验证码!',{ duration:'long', type:'div' });
			return;
		}

		var boo = validCodeByAjax(mobilePhone, validCode);
		if (!boo) {
			mui.toast(validCodeInfo,{ duration:'long', type:'div' });
			return;
		}
		var remark = $(".perfect-textarea").val();
		if(remark != "" && remark.length > 30){
			mui.toast('申诉原因最多填写30个字!',{ duration:'long', type:'div' });
			return;
		}
		
		$("#form").submit();
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
		var mobilePhone = $("input[name='mobilePhone']").val();
		if (!(/^1\d{10}$/.test(mobilePhone))) {
			mui.toast('请输入有效的手机号码!',{ duration:'long', type:'div' });
			return false;
		}
		time(this);
		$.ajax({
			url : "${base}/award/sendValidCode.do",
			type : "POST",
			async : true,
			data : {
				"phone" : mobilePhone
			},
			success : function(ret) {
				//console.log(ret);
			}
		});
	};
	
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${wxConfig.appid}', // 必填，公众号的唯一标识
	    timestamp: '${wxConfig.timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${wxConfig.nonceStr}', // 必填，生成签名的随机串
	    signature: '${wxConfig.signature}',// 必填，签名，见附录1
	    jsApiList: [
	                'getLocation',
	                'checkJsApi',
	                'chooseImage', 
	                'previewImage', 
	                'uploadImage'
	                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	var lat = ""; // 纬度，浮点数，范围为90 ~ -90
    var lgt = ""; // 经度，浮点数，范围为180 ~ -180。
	wx.ready(function () {
		wx.checkJsApi({
	           jsApiList: [
						'getLocation',
						'chooseImage', 
		                'previewImage', 
		                'uploadImage'
	           ],
	           success: function (res) {
	           },
	           error:function(res){
	           }
	    });
		wx.error(function(res){
			mui.toast('获取微信接口授权失败,'+res.errMsg,{ duration:'long', type:'div' });
	    });
		wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		    	lgt = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        lat = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		    },
		    error:function(res){
		    	mui.toast('获取位置信息失败,'+res.errMsg,{ duration:'long', type:'div' });
		    }
		});
	});
	
    function transAddress(){
    	if(lat != "" && lgt != ""){
    		$.ajax({
				url:"${base}/appeal/getAddress.do",
				type:"POST",
				async:true,
				data:{
					longitude:lat,
					latitude:lgt
				},
				success:function(ret){
					var r = $.parseJSON(ret);
					var country = r.country;
					var province = r.province;
					var city = r.city;
					var area = r.district;
					var street = r.street;
					
					$("input[name='province']").val(province);
					$("input[name='city']").val(city);
					$("input[name='area']").val(area);
					$("input[name='address']").val(street);
					$("#address").html(province+" "+city+" "+area+" "+street);
				}
			});
    		window.clearInterval(taskId);
    	}
    	
    }
    var taskId = null;
	$(function(){
		taskId = window.setInterval("transAddress();",2000);
	}); 
	
	//拍照或从手机相册中选图接口  
    function wxChooseImage() {  
        wx.chooseImage({  
            count: 1,  
            needResult: 1,  
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function (data) {  
                localIds = data.localIds[0].toString(); // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片  
                if (rh.tostr(localIds)) {  
                    wxuploadImage(localIds);  
                }  
            },  
            fail: function (res) {  
                alert("操作提示", JSON.stringify(res), "1", "确定", "", "", "");  
            }  

        });  
    }  
  	//上传图片接口  
    function wxuploadImage(e) {  
         
        wx.uploadImage({  
            localId: e, // 需要上传的图片的本地ID，由chooseImage接口获得  
            isShowProgressTips: 1, // 默认为1，显示进度提示  
            success: function (res) {  
                mediaId = res.serverId; // 返回图片的服务器端ID  
                if (rh.tostr(mediaId)) {  
                    $("#img1").attr("src", localIds);  
                }  

            },  
            fail: function (error) {  
                picPath = '';  
                localIds = '';  
                alert(Json.stringify(error));  

            }  

        });  
    } 
  	//从微信获取图片
  	function getMediaPic(){
  		$.ajax({  
            url: "test.ashx",  
            data: {  
                name: "getPicInfo",  
                media: $.trim(mediaId)  
            },  
            type: "Get",  
            dataType: "text",  
            success: function (data) {  
                picPath = data;  //picPath 取得图片的路径  
                
            },  
            error: function (XMLHttpRequest, textStatus, errorThrown) {  
                alert("提交失败" + textStatus);  
            }  

        });  
  	}
  	//绑定拍照事件
  	mui("#imgDiv").on('tap','#img1',function(){
  		wxChooseImage();
  	});
  	 document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		    // 通过下面这个API隐藏右上角按钮
		      WeixinJSBridge.call('hideOptionMenu');
		});
</script>
</html>
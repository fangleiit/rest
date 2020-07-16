<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="${base}/plugins/cashGift/js/jquery.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${base}/plugins/cashGift/js/mui.min.js"></script>
<link href="${base}/plugins/cashGift/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${base}/plugins/cashGift/css/packet.css" />
<script type="text/javascript" charset="utf-8">
	mui.init();
</script>
</head>
<body>
	<div style="widows: 200px;height: 200px;border: 1px solid red;margin: 0 auto;">
		<form id="uploadFileform"   action="${base}/appeal/save.do" method="post"  
    enctype="multipart/form-data" >  
    <center>  
      <label id="Header" cssClass="HeaderText" value="图片上传" />  
      <hr style="size: 1" />  
      <p id="FileList">  
    <input id="uploadImage" value="2" type="file" name="uploadImage" size="50" />  
    <img src="$!{request.contextPath}/images/lend/close.png" class="closea">  
      </p>  
                      
      <hr style="size: 1" />  
      <p>温馨提示：只允许上传.jpg .gif .png 后缀的图片</p>  
      <p style="color:green;">(请务必上传真实证件照片或图片 否则不会通过认证)</p>  
      <p>  
       <input class="btn btn-primary" type="button" value="继续添加" onclick="newFile()" />  
       <input class="btn btn-primary" type="button"  value="上传图片" onclick="uploadImages();"/>  
      </p>  
      <hr style="size: 1" />  
      </center>  
      <p align="center">  
        <span class="GbText" style="width: 100%; color: red;"></span>  
      </p>  
      <input type="hidden"  name="fileType" id="codeID" />  
      <input type="hidden" name="modeId" value="Obj0000009" />  
</form>
	</div>
</body>
<script type="text/javascript">
function deleteNode(obj) {  
    var parent = obj.parentNode;  
    if(parent){  
       parent.remove();  
    }  
   }  
 
   function newFile() {  
       $("#FileList").append('<p><input id="uploadImage" value="" type="file" name="uploadImage" size="50" /><a href="javascript:void(0);" onclick="deleteNode(this)" ><img src="$!{request.contextPath}/images/lend/close.png" class="closea"></a></p>');  
   }  
     
   function setCode(code,title) {  
       $("#uploadTitle").html(title);  
       $("#FileList").html('<p><input id="uploadImage" value="" type="file" name="uploadImage" size="50" /><a href="javascript:void(0);" onclick="deleteNode(this)" ><img src="$!{request.contextPath}/images/lend/close.png" class="closea"></a></p>');  
       $("#codeID").val(code);  
   }  
     
   function showImage(imageUrl,title) {  
       $("#authImage").attr("src", imageUrl);        
       $("#imageTitle").html(title);         
   } 
function uploadImages() {  
    var str = $("#uploadImage").val();  
    if(str.length!=0){  
        var reg = ".*\\.(jpg|png|gif|JPG|PNG|GIF)";  
        var r = str.match(reg);  
        if(r == null){  
            alert("对不起，您的图片格式不正确，请重新上传");  
        }  
        else {  
            if(window.ActiveXObject) {  
                var image=new Image();  
                image.dynsrc=str;  
                if(image.fileSize>5243000){  
                    alert("上传的图片大小不能超过5M，请重新上传");  
                    return false;  
                }  
            }  
            else{  
                var size = document.getElementById("uploadImage").files[0].size;  
                if(size>5243000) {  
                    alert("上传的图片大小不能超过5M，请重新上传");  
                    return false;  
                }  
            }  
            $('#uploadFileform').submit();  
        }  
    }  
    else {  
        alert("请先上传图片");  
    }  
}  
</script>
</html>
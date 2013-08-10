<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>羽人_s宝贝助手</title>

<link rel="stylesheet" href="css/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/invalid.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/sitem.css" type="text/css"
	media="screen" />

<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="scripts/lhgdialog/lhgdialog.min.js?skin=chrome"></script>

</head>

<body>
		<!-- Wrapper for the radial gradient background -->

			<!-- Main Content Section with everything -->

			<noscript>
				<!-- Show a notification if the user has disabled javascript -->
				<div class="notification error png_bg">
					<div>
						Javascript is disabled or is not supported by your browser. Please
						<a href="http://browsehappy.com/"
							title="Upgrade to a better browser">upgrade</a> your browser or <a
							href="http://www.google.com/support/bin/answer.py?answer=23852"
							title="Enable Javascript in your browser">enable</a> Javascript
						to navigate the interface properly. Download From <a
							href="http://www.exet.tk">exet.tk</a>
					</div>
				</div>
			</noscript>



				<div class="list-content">
					<ul class="list-view-enlarge">
						<c:forEach var="item" items="${hotItemSearchList}">
							<li class="list-item">
								<h3 class="summary">
									<a href="#" target="_blank" class="EventCanSelect">${item.nick}</a>
									<br>
									<a onclick="doCopyItem('${item.title}')" class="button">复制该宝贝</a>
								</h3>

								<div class="photo">
									<a href="${item.title}" target="_blank"> <img
										src="${item.picUrl}" class="hesper:small2big">
									</a>
								</div>
								<ul class="attribute">
									<li class="hot-sell-num"><span>月销<em>${item.num}</em>笔
									</span></li>
									<li class="price"><em>${item.price}</em></li>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</div>



</body>

<script type="text/javascript">

     function doCopyItem(itemurl){
 	    closeDialog();
 	    var infoDisplay = $.dialog({content:"正在复制宝贝中........请稍后"});
 		$.post('/item/ajaxCopyItem.htm',{itemurl:itemurl}, function(data) {
 		    infoDisplay.close();
   			if(data != null){
   			   if(data.sucess){
   			   		 var resultHtml = '<span class="input-notification success png_bg">复制宝贝成功&nbsp;&nbsp;'+
   			               '<a href="http://item.taobao.com/item.htm?id=' + data.item.numIid +'" target="_blank">查看</a>'+
   			               '</span>';
   			         closeDialog();
   			         $.dialog({zIndex:10001,content:resultHtml});   
   			   }else {
   			         if(data.subMsg){
   			         	var resultHtml = '<span class="input-notification error png_bg">'+
   			   		       data.subMsg +
   			               '</span>';
   			            closeDialog();
   			         	$.dialog({zIndex:10002,content: resultHtml});
   			         }
   			   }
   			}
 		});
 	}
 	
 	function closeDialog(){
 			var list = $.dialog.list;
 			for( var i in list ){
     			list[i].close();
 			}
 	}
</script>

<!-- Download From www.exet.tk-->
</html>

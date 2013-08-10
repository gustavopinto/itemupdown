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
	<div id="body-wrapper">
		<!-- Wrapper for the radial gradient background -->
		<jsp:include page="common/menu.jsp"></jsp:include>

		<div id="main-content">
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

			<!-- Page Head -->
			<h2>欢迎使用羽人_s宝贝助手</h2>
			<p id="page-intro">使用需知：</p>

			<p>1.用户输入宝贝id之后，系统会复制该宝贝的一些基本信息，便于用户发布宝贝。</p>
			<p>2.复制的宝贝默认是在仓库中的。</p>
			<p style="color: red">3.由于搜狗浏览器兼容模式不支持ajax,请把模式改为高速模式在使用。</p>
			<p>
				客服： <a target="_blank"
					href="http://www.taobao.com/webww/ww.php?ver=3&touid=%E4%B8%96%E5%B8%86%E7%A7%91%E6%8A%80&siteid=cntaobao&status=1&charset=utf-8"><img
					border="0"
					src="http://amos.alicdn.com/realonline.aw?v=2&uid=%E4%B8%96%E5%B8%86%E7%A7%91%E6%8A%80&site=cntaobao&s=1&charset=utf-8"
					alt="点击这里给我发消息" /></a>
			</p>
			<!--
			<ul class="shortcut-buttons-set">

				<li><a class="shortcut-button" href="#"><span> <img
							src="images/icons/pencil_48.png" alt="icon" /><br /> Write an
							Article
					</span></a></li>

				<li><a class="shortcut-button" href="#"><span> <img
							src="images/icons/paper_content_pencil_48.png" alt="icon" /><br />
							Create a New Page
					</span></a></li>

				<li><a class="shortcut-button" href="#"><span> <img
							src="images/icons/image_add_48.png" alt="icon" /><br /> Upload
							an Image
					</span></a></li>

				<li><a class="shortcut-button" href="#"><span> <img
							src="images/icons/clock_48.png" alt="icon" /><br /> Add an
							Event
					</span></a></li>

				<li><a class="shortcut-button" href="#messages" rel="modal"><span>
							<img src="images/icons/comment_48.png" alt="icon" /><br /> Open
							Modal
					</span></a></li>

			</ul>
                        -->
			<!-- End .shortcut-buttons-set -->

			<div class="clear"></div>

		    <div class="content-box"><!-- Start Content Box -->
				
				<div class="content-box-header">
					
					<h3>查询热卖宝贝</h3>
					
					<div class="clear"></div>
					
				</div> <!-- End .content-box-header -->
				
				<div class="content-box-content">
					
						<form id="searchHotItemForm" action="/item/searchHotItem.htm" method="post">
							
							<fieldset> <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->
								
								<p>
									<label>输入搜索宝贝关键词</label>
									<input class="text-input small-input" type="text" id="itemName" name="itemName" value="${itemName}" /> 
							    </p>
                                <br>
							    <p>
							    	<input class="button" type="button" value="查询" onclick="javascript:checkForm();"/>
							    </p>
								
							</fieldset>
							
							<div class="clear"></div><!-- End .clear -->
							
						</form>      
					
				</div> <!-- End .content-box-content -->

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

			</div> <!-- End .content-box -->

			<div class="clear"></div>
			<jsp:include page="common/footer.jsp"></jsp:include>
		</div>
	</div>
</body>

<script type="text/javascript">
     function checkForm(){
    	  var itemName = $('#itemName').val();
    	  if(itemName == ''){
    		  alert("请输入搜索宝贝关键词");
    		  return;
    	  }
    	  $("#searchHotItemForm").submit();
     }
     
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
   			         $.dialog({content:resultHtml});   
   			   }else {
   			         if(data.subMsg){
   			         	var resultHtml = '<span class="input-notification error png_bg">'+
   			   		       data.subMsg +
   			               '</span>';
   			            closeDialog();
   			         	$.dialog({content: resultHtml});
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

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
<title>羽人_s宝贝上下架</title>

<link rel="stylesheet" href="css/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/invalid.css" type="text/css"
	media="screen" />

<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="scripts/lhgdialog/lhgdialog.min.js"></script>

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
			<h2>欢迎使用羽人_s宝贝上下架</h2>
			<p id="page-intro">使用需知：</p>
                        
                        <p> 1.每个用户只能创建5个任务。</p>
                        <p> 2.用户可以通过新建任务来创建任务，包括（任务种类，宝贝选择)。</p>
                        <p> 3.创建任务之后，任务会显示下次执行日期。</p>
                        <p> 4.通过查看日志，监控任务执行情况。</p>
                        <p style="color:red">5.由于搜狗浏览器兼容模式不支持ajax,请把模式改为高速模式在使用。</p>
                        <p>客服： <a target="_blank" href="http://www.taobao.com/webww/ww.php?ver=3&touid=%E4%B8%96%E5%B8%86%E7%A7%91%E6%8A%80&siteid=cntaobao&status=1&charset=utf-8"><img border="0" src="http://amos.alicdn.com/realonline.aw?v=2&uid=%E4%B8%96%E5%B8%86%E7%A7%91%E6%8A%80&site=cntaobao&s=1&charset=utf-8" alt="点击这里给我发消息" /></a></p>
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
			<!-- End .clear -->

			<div class="content-box">
				<!-- Start Content Box -->

				<div class="content-box-header">

					<h3>宝贝上下架任务</h3>
					
					<ul class="content-box-tabs">
					    <li><a href="javascript:showLog();" class="button">查看运行日志</a></li>
					    <c:if test="${fn:length(taskList) < 5}">
					    	<li><a href="javascript:addTask();" class="button">新增任务</a></li>
					    </c:if>
					</ul>

					<div class="clear"></div>

				</div>
				<!-- End .content-box-header -->

				<div class="content-box-content">

					<div class="tab-content default-tab" id="tab1">
						<!-- This is the target div. id must match the href of this div's tab -->
                        
                        
						<div class="notification attention png_bg">
						    <!--  
							<a href="#" class="close"><img
								src="resources/images/icons/cross_grey_small.png"
								title="Close this notification" alt="close" /></a>
								-->
							<div>每个用户最多只能定义5个任务。</div>
						</div>
                     
						<table>

							<thead>
									<tr>
										<th>
										    <!--  
											<input class="check-all" type="checkbox" />
											-->
										</th>
										<th>任务名称</th>
										<th>任务类型</th>
										<th>任务状态</th>
                                        <th>下次执行时间</th>
										<th>操作</th>
									</tr>								
							</thead>
                            
                            <!--  
							<tfoot>
								<tr>
									<td colspan="6">
										<div class="bulk-actions align-left">
											<select name="dropdown">
												<option value="option1">Choose an action...</option>
												<option value="option2">Edit</option>
												<option value="option3">Delete</option>
											</select> <a class="button" href="#">Apply to selected</a>
										</div>

										<div class="pagination">
											<a href="#" title="First Page">&laquo; First</a><a href="#"
												title="Previous Page">&laquo; Previous</a> <a href="#"
												class="number" title="1">1</a> <a href="#" class="number"
												title="2">2</a> <a href="#" class="number current" title="3">3</a>
											<a href="#" class="number" title="4">4</a> <a href="#"
												title="Next Page">Next &raquo;</a><a href="#"
												title="Last Page">Last &raquo;</a>
										</div> 
										<div class="clear"></div>
									</td>
								</tr>
							</tfoot>
                            -->
							<tbody>
							   <c:forEach var="task" items="${taskList}">
									<tr>
										<td>
										    <!--  
											<input type="checkbox" />
											-->
										</td>
										<td>${task.taskName}</td>
										<td>
											<c:if test="${task.kind==1}">上架</c:if>
											<c:if test="${task.kind==2}">下架</c:if>
										</td>
										<td>
											<c:if test="${task.taskStatus==1}">启动</c:if>
											<c:if test="${task.taskStatus==2}">停止</c:if>
										</td>
                                        <td>
                                            <c:if test="${task.taskStatus==1}">
                                                <fmt:formatDate value="${task.nexecTime}" type="both"/>
                                            </c:if>
                                        </td>
										<td>
                                                                                    <a href="javascript:modifyTask(${task.id});" title="修改">
                                                                                         <img src="images/icons/pencil.png"  alt="修改" />
                                                                                    </a>
                                                                              
										</td>
									</tr>
									
								</c:forEach>
							   
							</tbody>

						</table>

					</div>
					<!-- End #tab1 -->

					
					<!-- End #tab2 -->

				</div>
				<!-- End .content-box-content -->

			</div>
			<!-- End .content-box -->

			
			<div class="clear"></div>
			<jsp:include page="common/footer.jsp"></jsp:include>
		</div>
		</div>
</body>

<script type="text/javascript">
	function addTask(){
		$.dialog({
			zIndex:100,
            width: 800,
            height: 700,
			content: 'url:/task/prepareAddTask.htm'
		});
	}
	function modifyTask(taskId){
            $.dialog({
            zIndex:100,
            width: 800,
            height: 700,
            content: 'url:/task/prepareModifyTask.htm?taskId='+taskId
        });
	}
	
        function delTask(taskId){
            window.location.href = "/task/delTask.htm?taskId=" + taskId;
        }
	
	function showLog(){
		$.dialog({
            width: 920,
            height: 700,
			content: 'url:/log/searchLog.htm'
		});
	}
</script>

<!-- Download From www.exet.tk-->
</html>

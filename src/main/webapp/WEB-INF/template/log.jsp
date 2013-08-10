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
<link href="scripts/lhgcalendar/common.css" rel="stylesheet" type="text/css" />
<link href="css/xwd_public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="scripts/lhgcalendar/lhgcalendar.min.js?skin=chrome"></script>
<style type="text/css">
.list_page5 li a {
	color: #6B83C1;
}

.input_btn {
	background: url(images/updateqr.png) no-repeat;
	border: 0;
	width: 58px;
	height: 24px;
	cursor: pointer;
	font-weight: bold;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener, cDG;
	$(function() {
		var date = new Date();
		var maxxDate = date.getDate() + 1;
		var minnDate = date.getDate() - 7;
		$('#beginTime').calendar({
			format:"yyyy-MM-dd HH:mm:ss",
			real : '#beginTime'
		});
		$('#endTime').calendar({
			format:"yyyy-MM-dd HH:mm:ss",
			real : '#endTime'
		});
	});

	function nextPage() {
		var curPage = document.getElementById("curPage").value;
		var pageCount = document.getElementById("pageCount").value;
		if (parseInt(curPage) < parseInt(pageCount)) {
			$("#curPage").val(parseInt(curPage) + 1);
			document.forms[0].submit();
		}
	}

	function endPage() {
		var pageCount = document.getElementById("pageCount").value;
		$("#curPage").val(pageCount);
		document.forms[0].submit();
	}

	function firstPage() {
		$("#curPage").val(1);
		document.forms[0].submit();
	}

	function lastPage() {
		var curPage = document.getElementById("curPage").value;
		if (curPage != 1) {
			$("#curPage").val(parseInt(curPage) - 1);
			document.forms[0].submit();
		}

	}

	function thePage() {
		var theCount = document.getElementById("pageno").value;
		if (theCount <= 0) {
			alert("请输入正整数！");
			return false;
		}
		if (theCount > 0) {
			var pageCount = document.getElementById("pageCount").value;
			if (theCount > pageCount) {
				theCount = pageCount;
			}
			$("#curPage").val(theCount);
			document.forms[0].submit();
		}

	}
    
	function search(){
		$('#curPage').val(1);
		document.forms[0].submit();
	}
	
	function closeWindow() {
		api.close();
	}
</script>
</head>
<body style="background: #fff;">
	<div class="xbwk" style="width: 905px; border: 0;">

		<div class="gg" style="margin: 0; width: 900px;">

			<p id="etyu">您可以查询最近7天的内的记录。</p>
			<form action="log/searchLog.htm" method="post">
				<table cellspacing="0" cellpadding="0" border="0" height="50px"
					class="MainFrameTable Table_content">
					<tbody>
						<tr>
							<td>请选择：</td>
							<td>
								<div style="overflow: hidden; zoom: 1">
									<span style="float: left;"> <input type="text"
										id="beginTime" name="beginTime" class="runcode"
										value="${parameter.beginTime}" readonly="readonly">
									</span> <span style="float: left; margin-left: 5px;"> <input
										type="text" id="endTime" name="endTime" class="runcode"
										value="${parameter.endTime}" readonly="readonly">
									</span>
								</div>

							</td>
							<td>
							    	宝贝名称:<input type="text" name="itemName" value="${parameter.itemName}"/>
							</td>
							<td>状态：<select id="status" name="status">
										<option value="0" <c:if test="${parameter.status == 0}">selected="selected"</c:if> >全部</option>
										<option value="1" <c:if test="${parameter.status == 1}">selected="selected"</c:if> >成功</option>
										<option value="2" <c:if test="${parameter.status == 2}">selected="selected"</c:if> >失败</option>
							</select>
							</td>
							<td>
								 <input type="button" value="" class="input_btn" onClick="javascript:search();">
							</td>
							<td>
								<span><a href="javascript:void(0);" onClick="closeWindow()">点此返回</a></span>
							</td>
						</tr>
					</tbody>
				</table>

				<table cellspacing="0" cellpadding="0" border="0" class="MainFrameTable Table_content">
					<tbody>
						<tr>
							<td width="20%" align="center" class="Table_th_brief"
								style="text-align: center;">执行时间</td>
							<td width="80%" align="center" class="Table_th_brief">宝贝上下架结果</td>
						</tr>
						<c:forEach var="taskLog" items="${pagination.list}" varStatus="status">
							<tr <c:if test="${status.index %2 == 0}">class="Table_Content_line-1"</c:if>
								<c:if test="${status.index %2 == 1}">class="Table_Content_line1"</c:if>
							>
								<td align="center">
								      <fmt:formatDate value="${taskLog.createTime}" type="both" />
								</td>
								<td style="line-height: 1.8">
									<div style="font-weight: bold">
										<a target="_blank" href="http://item.taobao.com/item.htm?id=${taskLog.itemId}">
											${taskLog.itemName}
										</a>
									</div> 
								    <span>
								         ${taskLog.resultinfo}
								    </span>   
								</td>
							</tr>
						</c:forEach>

						<tr class="Table_Content_line1">
							<td colspan="2">
								<div class="list_page5"
									style="background: none repeat scroll 0% 0% transparent; border: medium none;">
									<input type="hidden" id="curPage" name="currentPage" value="${pagination.curPage}" /> 
									<input type="hidden" id="pageCount"
										name="pageCount" value="${pagination.pageCount}" /> <input type="hidden"
										id="rowsCount" name="rowsCount" value="${pagination.rowsCount}" />
									<ul>
										<li>共 <span id="dateCount">${pagination.rowsCount}</span>
										</li>

										<li><a href="javascript:void(0)" onClick="firstPage()">
												首页 </a></li>
										<li><a href="javascript:void(0)" onClick="lastPage()">
												上一页 </a></li>
										<li>${pagination.curPage } / ${pagination.pageCount }</li>
										<li><a href="javascript:nextPage()">
												下一页 </a></li>
										<li><a href="javascript:endPage()">
												尾页 </a></li>
										<li>转到 <input value="" class="text1" size="3" id="pageno"
											name="pageno" tabindex="10000"> 页
										</li>

										<li class="rft" onClick="thePage()"><img
											src="images/go_s.gif" width="13" height="13" /></li>
									</ul>
								</div>
							</td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>

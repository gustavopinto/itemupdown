<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
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
<title>s宝贝上下架</title>

<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/du.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />


<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="scripts/lhgdialog/lhgdialog.min.js?skin=chrome"></script>

</head>

<body>
	<div class="content-box">
		<div class="content-box-header">

			<h3>上下架任务</h3>

			<ul class="content-box-tabs">
				
			</ul>

			<div class="clear"></div>

		</div>
		<div class="content-box-content">

			<!-- End #tab1 -->

			<div id="tab2" class="tab-content" style="display: block;">

				<form method="post" action="/task/addTask.htm" id="addTaskForm">

					<fieldset>
						<!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

						<p>
                                                    <label>任务名称：</label> <input type="text" name="taskName" maxlength="40"
								name="small-input" id="taskName" class="text-input small-input">
							<!-- Classes for input-notification: success, error, information, attention -->
							<br>
						</p>
						
						<p>
							<label>选择周期：</label>
						    <c:forEach items="${dayMap}" var="dayMap">
						       <input type="checkbox" name="day" value="${dayMap.key}">${dayMap.value}&nbsp;&nbsp;
						    </c:forEach>
						</p>    

						<p>
							<label>选择时间点：</label>
							<c:forEach var="time" items="${itemList}" varStatus="status">
								<input type="checkbox" name="time" value="${timeListV[status.count-1]}">${time}:00:00 - <c:if test="${time+1 < 10}">0${time+1}:00:00</c:if><c:if test="${time+1 >= 10}">${time+1}:00:00</c:if>
								<c:if test="${status.count % 4 == 0}">
									<br/>
								</c:if>						
							</c:forEach>

						</p>            
                        <p>
                            <label>任务类型：</label> <select class="small-input" name="kind"
								id="kind">
								<option value="0">请选择</option>
								<option value="1">上架</option>
								<option value="2">下架</option>

							</select>
                        </p>
                        <p>
                        	 <label>宝贝选择：</label>
                        	 <input type="radio" name="sitem" value="1" onclick="javascript:getItemCount(1)">全部宝贝
                        	 <input type="radio" name="sitem" value="2" onclick="javascript:getItemCount(2)">出售中的
                        	 <input type="radio" name="sitem" value="3" onclick="javascript:getItemCount(3)">库存中的
                        	 <input type="button" class="button" value="选择宝贝" onclick="javascript:searchItem();">
                        	 <span class="input-notification success png_bg" id="itemcount" style="display:none"></span>
                        	 <input type="hidden" id="selectItems" name="selectItems" value=""/>
                        </p>

						<p>
							<label>任务状态：</label> <select class="small-input" name="taskStatus"
								id="taskStatus">
								<option value="0">请选择</option>
								<option value="1">启动</option>
								<option value="2">停止</option>

							</select>
						</p>

						<p>
							<input type="button" value="提交" class="button" onclick="javascript:sb();">
						</p>

					</fieldset>

					<div class="clear"></div>
					<!-- End .clear -->

				</form>

			</div>
		</div>

	</div>

</body>

<script type="text/javascript">
	function addTask() {
		$.dialog({
			content : 'url:task'
		});
	}
	
	function getItemCount(type){
		$.getJSON("/item/getItemsCount.htm", {sitem:type}, function(data) {
			if(data.total){
				$('#itemcount').html("选中宝贝数量：" + data.total);
				$('#itemcount').show();
			}
		});
	}
	

	function searchItem(){
		$.dialog({
            width: 960,
            height:700,
			content: 'url:/item/prepareSearchItem.htm',
		});
	}
	
	function sb(){
		if(checkForm()){
			$('#addTaskForm').submit();
		}
	}
	function checkForm(){
		var taskName = $('#taskName').val();
	    var kind = $('#kind').val();
	    var selectItems = $('#selectItems').val();
	    var taskStatus = $('#taskStatus').val();
		
        if(taskName == ""){
        	alert("请填写任务名称");
        	return false;
        }
		
        if($('input[name="day"]:checked').length == 0){
         	alert("请选择周期");
         	return false;
        }
        
        if($('input[name="time"]:checked').length == 0){
         	alert("请选择时间点");
         	return false;
        }
        
        if(kind == 0){
        	alert("请选择任务类型");
        	return false;
        }
        
        if(selectItems == '' && $('input[name="sitem"]:checked').length == 0){
        	alert("请进行宝贝选择");
        	return;
        }
        
        if(taskStatus == 0){
        	alert("请选择任务状态");
        	return false;
        }
        
        return true;
		
	}
	

</script>

<!-- Download From www.exet.tk-->
</html>

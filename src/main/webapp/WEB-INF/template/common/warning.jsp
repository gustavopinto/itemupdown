<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn"%>

<div class="clearfix" id="pannelDash">

	<!-- Tabs-->
	<div class="menu">
	    <!-- 
		<ul>
			<li class="selected"><a
				onclick="showOnly('tabDashboard','dashWidget')" href="#"><img
					alt="Dashboard" src="img/icons/home.png">Dashboard</a></li>
			<li><a onclick="showOnly('tabSettings','dashWidget')" href="#">Settings</a>
			</li>
			<li><a onclick="showOnly('tabAccount','dashWidget')" href="#">Account</a>
			</li>
		</ul>
		 -->
		<div class="info">		     
			<div>
                            <a class="icOff" href="/exit.htm">退出</a>
		        </div>
			
			<div class="user">
				<img width="27" height="27" alt="User name" src="img/user_icon.png">
				<!--<span>${user.userNick}</span>--><span class="detail"><fmt:formatDate value="${user.modifyTime}" type="both" /></span>
			</div>
		</div>

		<div class="theme">
		    <!--  
			<a rel="red" class="styleswitch red" href="#">&nbsp;</a> <a
				rel="green" class="styleswitch green" href="#">&nbsp;</a> <a
				rel="brown" class="styleswitch brown" href="#">&nbsp;</a> <a
				rel="blue" class="styleswitch blue" href="#">&nbsp;</a> <span>
				Theme </span>
			-->
		</div>
	</div>

	<!-- Dashboard fast menu (6 items)  -->
	<!--  
	<div class="dashboard">
		<ul>
			<li><a href="#"> <img alt="Manage users!"
					src="img/dash/users.png"> Manage users
			</a></li>
			<li><a href="#"> <img alt="Edit article!"
					src="img/dash/edit.png"> Edit article
			</a></li>
			<li><a href="#"> <img alt="Write a new article!"
					src="img/dash/add.png"> Write article
			</a></li>
			<li><a href="#"> <img alt="Database manager!"
					src="img/dash/database.png"> DB manager
			</a></li>
			<li><a href="#"> <img alt="Recent comments!"
					src="img/dash/chat.png"> New comments
			</a></li>
			<li><a href="#"> <img alt="View deleted documents!"
					src="img/dash/delete.png"> Trash
			</a></li>
		</ul>
	</div>
    -->
	<!-- Large left widget -->
    <!-- 
	<div class="widget dashWidget">
		<div class="content tabContent">
			<div class="tabDashboard">&nbsp;</div>
			<div class="tabSettings">
				<div class="fields">
					<p class="sep">
						<label for="user02" class="small">User name</label> <input
							type="text" id="user02" class="sText" value="">
					</p>

					<p class="sep">
						<label for="select02" class="small">Time/Zone</label> <select
							id="select02" class="sSelect">
							<option value="ro">Romania</option>
							<option value="fr">France</option>
							<option value="ge">Germany</option>
							<option value="hu">Hungary</option>
							<option value="gr">Greece</option>
						</select>
					</p>

					<div class="fields">
						<p>&nbsp;</p>
						<p>
							<input type="checkbox" id="check08" value="ts_1" name="ts_1"
								class="sCheck"><label for="check08">Recive daily
								report</label>
						</p>
						<p>
							<input type="checkbox" id="check09" value="ts_2" name="ts_2"
								class="sCheck"><label for="check09">Recive
								weekly report</label>
						</p>
					</div>
				</div>

			</div>
			<div class="tabAccount">&nbsp;</div>
		</div>
	</div>
	 -->
</div>
<div class="toolTip tpYellow">

	<p class="clearfix">
	    <img src="img/icons/light-bulb-off.png" alt="Tip!" /><br><br>
		1.每个用户能创建多个任务，可以指定具体时间具体宝贝进行上下架。<br><br>
		2.用户可以通过新建任务来创建任务，包括（任务种类，宝贝选择)。<br><br>
		3.创建任务之后，任务会显示下次执行日期。<br><br>
		<font color="red">5.由于搜狗浏览器兼容模式不支持ajax,请把模式改为高速模式在使用。</font><br><br>
		客服：<a target="_blank" href="http://amos.im.alisoft.com/msg.aw?v=2&uid=czbabl&site=cntaobao&s=1&charset=utf-8" ><img border="0" src="http://amos.im.alisoft.com/online.aw?v=2&uid=czbabl&site=cntaobao&s=1&charset=utf-8" alt="点击这里给我发消息" /></a>
	</p>
	
	<a class="close" title="Close"></a>
</div>

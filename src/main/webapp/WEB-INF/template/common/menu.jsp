<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<c:if test="${empty mid}">
	<c:set var="mid" value="11"/>
</c:if>

<c:if test="${!empty mid}">
	<c:set var="mid" value="${mid}"/>
</c:if>

<div id="sidebar">
			<div id="sidebar-wrapper">
			
				<h1 id="sidebar-title">
					<a href="#">s宝贝助手</a>
				</h1>

				<!-- Sidebar Profile links -->
				<div id="profile-links">
					您好,欢迎使用！<br />
				</div>
 
				<ul id="main-nav">
					<!-- Accordion Menu -->
					<li>
						<a href="/task/itemUpDown.htm?mid=11" class="nav-top-item <c:if test="${mid == 11}">current</c:if><c:if test="${mid != 11}">no-submenu</c:if>"> <!-- Add the class "no-submenu" to menu items with no sub menu -->
							宝贝自动上下架
					    </a>
					</li>
					<li>
						<a href="/item/prepareCopyItem.htm?mid=12" class="nav-top-item <c:if test="${mid == 12}">current</c:if><c:if test="${mid != 12}">no-submenu</c:if>">宝贝复制</a>
					</li>
				    <li>
						<a href="/item/prepareHotItem.htm?mid=13" class="nav-top-item <c:if test="${mid == 13}">current</c:if><c:if test="${mid != 13}">no-submenu</c:if>">热卖宝贝</a>
					</li>
				</ul>	
			</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<c:if test="${empty mid}">
    <c:set var="mid" value="11" />
</c:if>

<c:if test="${!empty mid}">
    <c:set var="mid" value="${mid}" />
</c:if>

<div id="menu">
    <ul>
        <li <c:if test="${mid == 11}">class="tasks widget"</c:if>>
            <a href="/task/itemUpDown.htm?mid=11" class="clearfix">
                宝贝自动上下架
            </a>
        </li>

        <c:if test="${user.userNick == 'czbabl'}">
            <li <c:if test="${mid == 12}">class="tasks widget"</c:if>>
                    <a href="/user/prepareClearUser.htm?mid=12" class="clearfix">
                        清空过期用户
                    </a>
            </li>
        </c:if> 

        <li <c:if test="${mid == 14}">class="tasks widget"</c:if>>
            <a href="/task/itemGallery.htm?mid=14" class="clearfix">
                宝贝橱窗推荐
            </a>
        </li>

    </ul>
</div>
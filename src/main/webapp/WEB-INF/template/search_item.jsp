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
<title>上下架</title>
<link href="css/xwd_public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="scripts/jquery-1.7.2.min.js"> </script>
<script type="text/javascript">
                    var api = frameElement.api, W = api.opener;

                        function addItem(sid){
                        	if($("#t_" + sid).length > 0){
                        		alert("您已经推荐该宝贝!");
                        		return;
                        	}
                        	
                        	var inithtml = $("#s_" + sid).html();
                        	$("#s_" + sid + " > span").remove();
                        	var ihtml = $("#s_" + sid).html();
                        	var thtml = ihtml;
                        	
                        	var span = '<span class="sort"><span onclick="javascript:delItem('+sid+');" class="idelete"></span></span>';
                        	var thtml = '<div class="first item" id="t_'+ sid +'">' + thtml + span + "</div>";

                        	$('#t_allProduct').append(thtml);
                        	$("#s_" + sid).html(inithtml);
                        	$("#s_" + sid + " > span").html('<span class="irecommendsuc ggxxjj2"></span> 已选择');
                        	
                        	var num = $("#t_allProduct").children().length;
                        	$("#selectCount").text(num);
                        	
                        }
                        
                        function delItem(sid){
                        	$("#t_" + sid).remove();
                        	$("#s_" + sid + " > span").html('<span class="irecommendsuc ggxxjj2"></span> 未选择');
                        	var num = $("#t_allProduct").children().length;
                        	$("#selectCount").text(num);
                        }
                        
                        function idoGetItem(curpage,type,keyword,sellerCids){
                        	var paras = '{';
                        	paras = paras + '"currentPage":'+curpage;
                        	
                        	if(typeof(keyword) != "undefined"){
                        		paras = paras + ',' + '"title":"'+keyword + '"';
                        	}else{
                        		if($('#searchProductText').val().length > 0){
                            		paras = paras + ',' + '"title":"'+$('#searchProductText').val() + '"';
                            	}
                        	}
                        	
                           paras = paras + ',' + '"type":"' + type + '"';
                           
                           paras = paras + '}';	
                            
                        	var paras = eval("("+ paras +")");
                    		$.post('/item/ajaxSearchItem.htm',paras, function(data) {
                      			if(data != null){
                      			   if(data.list){
                      				 var html = "";
                      				 $.each(data.list, function(index, ritem){
                      					 html = html + 
                      					'<div class="first item" id="s_'+ritem.numIid+'">'+
                        				  '<a class="img" target="_blank" href="http://item.taobao.com/item.htm?id='+ritem.numIid+'">'+
                        				       '<img width="40" height="40" src="'+ritem.picUrl+'">'+
                        				  '</a>'+
                        				  '<div class="info2">'+
                                           '<p class="title">'+
                                           '<a title="'+ritem.title +'" target="_blank" href="http://item.taobao.com/item.htm?id='+ritem.numIid+'">'+
                                                   ritem.title+
                                            '</a>'+
                                        '</p>'+
                                        '<p>'+
                                            '<span class="jhss">'+
                                                   ' ￥'+ ritem.price +
                                            '</span>'+
                                        '</p>'+
                                    '</div>'+
                                    '<span class="recommend" onclick="javascript:addItem('+ritem.numIid+')">'+
                                        '<span class="irecommendsuc ggxxjj2">'+
                                        '</span>'+
                                               '未选择'+
                                        '</span>' +
                                    '</div>';
                      				 });		 
                      			   $("#s_allProduct").html(html);
                      			   
                      			   var prePage = data.curPage-1;
                      			   var nextPage = data.curPage+1;
                      			   
                      			   if(prePage <= 0){
                      				   prePage = 1;
                      			   }else if(prePage > data.pageCount){
                      				   prePage = data.pageCount;
                      			   }
                      			   
                      			   if(nextPage <= 0){
                      				 nextPage = 1;
                    			   }else if(nextPage > data.pageCount){
                    				   nextPage = data.pageCount;
                    			   }
                      			   
                      			   var page = '' +
                                 			'<ul class="Page">'+
                                    			 '<li id="itemTotal">' +
                                                       ' 共' + (data.rowsCount)+ '条记录' +
                                        '</li>'+
                                        '<li id="pageFirst" class="ipage-first">' +
                                            '<a href="javascript:doGetItem(1);">'+
                                              '<img src="images/shouye.gif" width="16" height="16" />' +
                                            '</a>'+
                                        '</li>'+
                                        '<li id="pagePrevious" class="ipage-prev">'+
                                           '<a href="javascript:doGetItem('+prePage+');">'+
                                             '<img src="images/qianye.gif" width="16" height="16" />' +
                                           '</a>' +
                                        '</li>' +
                                        '<li id="pageIndex">'+
                                            (data.curPage)+'/'+(data.pageCount) +
                                        '</li>'+
                                        '<li id="pageNext" class="ipage-next">' +
                                               '<a href="javascript:doGetItem('+nextPage+');">'+
                                                     '<img src="images/houye.gif" width="16" height="16" />'+
                                               '</a>'+
                                        '</li>' +
                                        '<li id="pageLast" class="ipage-last">' +
                                        	'<a href="javascript:doGetItem('+data.pageCount+');">'+
                                             	'<img src="images/moye.gif" width="16" height="16" />'+
                                            '</a>'+
                                        '</li>'+
                                        '<li class="count" id="pageCount" style="display: inline;">'+
                                         	'转到'+
                                            '<input type="text" id="pageInput">'+
                                        	 '页'+
                                        '</li>'+
                                        '<li class="ipage-go" id="pageGo" style="display: inline;">'+
                                            '<a href="javascript:goPage()">' +
                                                '<img src="images/go_s.gif" width="13" height="13" />' +
                                            '</a>'+
                                        '</li>'+
                                        '</ul>';

                      			  }else{
                      				  $('#s_allProduct').html("");
                      				  var page = '' +
                           			'<ul class="Page">'+
                              			 '<li id="itemTotal">' +
                                                 ' 共' + (0)+ '条记录' +
                                  '</li>'+
                                  '<li id="pageFirst" class="ipage-first">' +
                                      '<a href="javascript:doGetItem(1);">'+
                                        '<img src="images/shouye.gif" width="16" height="16" />' +
                                      '</a>'+
                                  '</li>'+
                                  '<li id="pagePrevious" class="ipage-prev">'+
                                     '<a href="javascript:doGetItem(1);">'+
                                       '<img src="images/qianye.gif" width="16" height="16" />' +
                                     '</a>' +
                                  '</li>' +
                                  '<li id="pageIndex">'+
                                      '1/1'+
                                  '</li>'+
                                  '<li id="pageNext" class="ipage-next">' +
                                         '<a href="javascript:doGetItem(1);">'+
                                               '<img src="images/houye.gif" width="16" height="16" />'+
                                         '</a>'+
                                  '</li>' +
                                  '<li id="pageLast" class="ipage-last">' +
                                  	'<a href="javascript:doGetItem(1);">'+
                                       	'<img src="images/moye.gif" width="16" height="16" />'+
                                      '</a>'+
                                  '</li>'+
                                  '<li class="count" id="pageCount" style="display: inline;">'+
                                   	'转到'+
                                      '<input type="text" id="pageInput">'+
                                  	 '页'+
                                  '</li>'+
                                  '<li class="ipage-go" id="pageGo" style="display: inline;">'+
                                      '<a href="javascript:goPage()">' +
                                          '<img src="images/go_s.gif" width="13" height="13" />' +
                                      '</a>'+
                                  '</li>'+
                                  '</ul>';
                      			  }
                      			  $(".pageBox").html(page);
                      			  checkSelect();
                      			}
                    		});
                    	}
                        
                        function checkItem(){                       	
                        	var ids = "";
                        	var itemcount = 0;
                        	$("#t_allProduct").children().each(function(){
                        		var idvalue = $(this).attr("id");
                        		var arr = idvalue.split("_");
                        		ids = ids + arr[1] + ":";
                        		itemcount++;
                        	});
                        	W.document.getElementById("selectItems").value = ids;

                        	var itemsCheck = W.document.getElementsByName('sitem');

                        	for(var i =0; i < itemsCheck.length; i++){
                        		itemsCheck[i].checked = false;
                        	}
                        	
                        	var inHtml =  "选中宝贝数量：" + itemcount;
                        	W.document.getElementById("itemcount").innerHTML = inHtml;
                        	W.document.getElementById("itemcount").style.display="";
                        	api.close();
                        }
                        
                        
                        function searchItem(){
                        	var keyword = $("#searchProductText").val();
                        	var itemtype = $("#itemtype").val();
                        	idoGetItem(1, itemtype, keyword);
                        }
                        
                        function doGetItem(curPage){
                        	var keyword = $("#searchProductText").val();
                        	var itemtype = $("#itemtype").val();
                        	idoGetItem(curPage, itemtype, keyword);
                        }
                        
                         
                    	function goPage(){
                    		var pageCount = "${itemPagination.pageCount}";
                    		var page = $('#pageInput').val();
                    		var regNum =/^[0-9]+.?[0-9]*$/;
                    		if(regNum.test(page)){
                    			if(parseInt(page) > parseInt(pageCount)){
                    				page = pageCount;
                    			}
                    		}else{
                    			page = 1;
                    		}
                    		doGetItem(page);
                    	}
                    </script>
</head>

<body>
	<div style="width: 900px;" class="xui-dialog-bg" id="dialog3_bar">
		<div style="margin: undefinedpx;" class="xui-dialog-border2">
			<div class="section shopKeyLayer"
				style="width: 890px; padding-bottom: 20px;">
				<div class="section2 selectProduct">
					<div class="col">
						<h3 class="werw">选择店铺中的宝贝</h3>
						<div class="con">
							<div class="hd">
								<select style="width:100px" name="type" id="itemtype">
									<option value="2" <c:if test="${queryPara.type == 2}">selected</c:if>>出售中的</option>
									<c:if test="${gtask == 0}">
									<option value="3" <c:if test="${queryPara.type == 3}">selected</c:if>>库存中的</option>
									</c:if>
								</select> 
								<input type="text" maxlength="60" name="title"
									id="searchProductText" class="ckka" value="${queryPara.title}"> <span
									id="searchProductBtn" class="ibtn-search"
									onclick="javascript:searchItem();"> </span>
							</div>
							<div class="product">
								<div class="list" id="s_allProduct">
									<c:forEach var="sitem" items="${itemPagination.list}">
										<div class="first item" id="s_${sitem.numIid}">
											<a class="img" target="_blank"
												href="http://item.taobao.com/item.htm?id=${sitem.numIid}"> <img
												width="40" height="40" src="${sitem.picUrl}">
											</a>
											<div class="info2">
												<p class="title">
													<a target="_blank"
														href="http://item.taobao.com/item.htm?id=${sitem.numIid}">
														${sitem.title} </a>
												</p>
												<p>
													<span class="jhss"> ￥${sitem.price} </span>
												</p>
											</div>
											<span class="recommend"
												onclick="javascript:addItem('${sitem.numIid}')"> <span
												class="irecommendsuc ggxxjj2"> </span> 未选择
											</span>
										</div>
									</c:forEach>
								</div>
								<div class="pageBox">
									<ul class="Page">
										<li id="itemTotal">共 ${itemPagination.rowsCount} 条记录</li>
										<li id="pageFirst" class="ipage-first"><a
											href="javascript:doGetItem(1);"> <img
												src="images/shouye.gif" width="16" height="16" />
										</a></li>
										<li id="pagePrevious" class="ipage-prev"><a
											href="javascript:doGetItem(${itemPagination.curPage - 1});">
												<img src="images/qianye.gif" width="16" height="16" />
										</a></li>
										<li id="pageIndex">
											${itemPagination.curPage}/${itemPagination.pageCount}</li>
										<li id="pageNext" class="ipage-next"><a
											href="javascript:doGetItem(${itemPagination.curPage + 1});"><img
												src="images/houye.gif" width="16" height="16" /></a></li>
										<li id="pageLast" class="ipage-last"><a
											href="javascript:doGetItem(${itemPagination.pageCount});">
												<img src="images/moye.gif" width="16" height="16" />
										</a></li>
										<li class="count" id="pageCount" style="display: inline;">
											转到 <input type="text" id="pageInput"> 页
										</li>
										<li class="ipage-go" id="pageGo" style="display: inline;">
											<a href="javascript:goPage();"> <img
												src="images/go_s.gif" width="13" height="13" />
										</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="section2 selectProduct">
						<div class="col">
							<h3 class="werw">
								已选中的宝贝 ( <span id="selectCount"><c:if test="${fn:length(selectItems) == 0}">0</c:if><c:if test="${fn:length(selectItems) > 0}">${fn:length(selectItems)}</c:if></span> ) 
							</h3>
							<div class="con" style="overflow: scroll">
								<div class="hd">
									<span class="hdwe"> 宝贝名称 </span> <span class="fn hdwe">
										操作 </span>
								</div>
								<div class="product">
									<div class="list" id="t_allProduct">
										<c:forEach var="sitem" items="${selectItems}">
											<div class="first item" id="t_${sitem.numIid}">
												<a class="img" target="_blank"
													href="http://item.taobao.com/item.htm?id=${sitem.numIid}">
													<img width="40" height="40" src="${sitem.picUrl}">
												</a>
												<div class="info2">
													<p class="title">
														<a target="_blank"
															href="http://item.taobao.com/item.htm?id=${sitem.numIid}">
															${sitem.title} </a>
													</p>
													<p>
														<span class="jhss"> ￥${sitem.price} </span>
													</p>
												</div>
												<span class="sort"> <span
													onclick="javascript:delItem(${sitem.numIid});" class="idelete">
												</span>
												</span>
											</div>
										</c:forEach>
									</div>
								</div>
								<div class="btn">
									<span id="selectProductSubmit" class="ibtn-selected"
										onclick="javascript:checkItem()"> </span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
            var intSelectItem = 0;
            
            function doInitSelect(){
            	$("#t_allProduct").children().each(function(){
                   	if($(this).attr("id").length > 0){
                   		var idvalue = $(this).attr("id");
                   		var arr = idvalue.split("_");
                   		repItem(arr[1]);
                   		intSelectItem = intSelectItem + 1;
                   	}      		
               	});
                $("#selectCount").text(intSelectItem);
            }
            
            function checkSelect(){
                var intSelectItem = 0;
            	$("#t_allProduct").children().each(function(){
                   	if($(this).attr("id").length > 0){
                   		var idvalue = $(this).attr("id");
                   		var arr = idvalue.split("_");
                   		repItem(arr[1]);
                   		intSelectItem = intSelectItem + 1;
                   	}      		
               	});
                $("#selectCount").text(intSelectItem);
            }
            
            
            function repItem(id){
           	 $("#s_allProduct").children().each(function(){
                 	if($(this).attr("id").length > 0){
                 		var idvalue = $(this).attr("id");
                 		var arr = idvalue.split("_");
                 		var sid = arr[1];
                 		if(sid == id){
                 			$('#s_'+ id + ' > span').html('<span class="irecommendsuc ggxxjj2"> </span> 已选择');
                 		}
                 	}      		
             	});
           }
            
            doInitSelect();
            </script>

</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="../commons/header.jsp"></jsp:include>
	</head>

	<body class="no-skin">
	    <!-- 导航栏 start		-->
		<jsp:include page="../commons/navbar.jsp"></jsp:include>
		<!-- 导航栏 end -->

		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
            <!--- 左边栏-菜单 start -->
			<jsp:include page="../commons/sidebar.jsp"></jsp:include>
			<!--- 左边栏-菜单 end -->

			<!--- 内容主体 start -->
			<div class="main-content">
				<div class="main-content-inner">
				    <!-- 向导栏 start-->
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
								<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">首页</a>
							</li>

							<li>
								<a href="#">后台用户管理</a>
							</li>
							<li class="active">后台用户-列表</li>
						</ul><!-- /.breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
					</div>
					<!-- 向导栏 end-->

					<!-- 内容页 start -->
					<div class="page-content">
				          			<div class="widget-box transparent" id="widget-box-13">
												<div class="widget-header">
													<h4 class="widget-title lighter">后台用户管理</h4>

													<div class="widget-toolbar no-border">
														<ul class="nav nav-tabs" id="myTab2">
															<li >
																<a data-toggle="tab" href="javascript:void(0)" onclick="window.location.href='${pageContext.request.contextPath}/user/toUserAdd'">增加</a>
															</li>

													

															<li class="active">
																<a data-toggle="tab" href="#adminList">列表</a>
															</li>
														</ul>
													</div>
												</div>

												<div class="widget-body">
													<div class="widget-main padding-12 no-padding-left no-padding-right">
														<div class="tab-content padding-4">
															

													        
															<div id="adminList" class="tab-pane in active">
															<button id="btnDeleteCheckUsers" class="btn btn-primary">删除勾选用户</button>
															     
																 <table class="table table-striped table-bordered align-center "> 
																    <tr class="bolder" >
																	  <td><input type="checkbox"></td>
																	  <td>编号</td>
																	  <td>用户名</td>
																	   <td>账号</td>
																	  <td>状态</td>
																	  <td>角色</td>
																	  <td>创建日期</td>
																	  <td>操作</td>
																	</tr>
																	<c:forEach var="user" items="${requestScope.userPage.data }">
																	 <tr>
																	  <td><input name="userId" value="${user.user_id}" type="checkbox"></td>
																	  <td>${user.user_id }</td>
																	  <td>${user.user_name }</td>
																	   <td>${user.user_account }</td>
																	   <td>${user.user_status}</td>
																	    <td>${user.role.role_name}</td>
																		 <td><fmt:formatDate value="${user.user_createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
																	   <td><button class="btn btn-minier btn-white btn-info" onclick="location.href='${pageContext.request.contextPath}/user/toUserEdit/${user.user_id}'">&nbsp;更&nbsp;新&nbsp;</button>&nbsp;&nbsp;<button onclick="location.href='${pageContext.request.contextPath}/user/deleteUserById/${user.user_id}'"  class="btn btn-minier btn-white btn-info">&nbsp;删&nbsp;除&nbsp;</button></td>
																	</tr>
																</c:forEach>
																   
																   
																 </table>
																 <div class="text-right">
																	 <nav>
																	  <ul class="pagination">
																	    <c:choose>
																	      <c:when test="${requestScope.userPage.previous==true }">
																	           <li>
																			      <a href="${pageContext.request.contextPath }/user/toUserList/${requestScope.userPage.index-1}" aria-label="Previous">
																			        <span aria-hidden="true">&laquo;</span>
																			      </a>
																			    </li>
																	      </c:when>
																	      <c:otherwise>
																	        <li class="disabled">
																		      <a href="#" aria-label="Previous">
																		        <span aria-hidden="true">&laquo;</span>
																		      </a>
																		    </li>
																	      </c:otherwise>
																	    </c:choose>
																	  
																	    
																	    <c:forEach var="index" begin="${requestScope.userPage.start }" end="${requestScope.userPage.end }">
																	      <c:choose>
																	       <%-- 如果迭代返回的索引与当前索引一样，表单就是当前页，所以选中 --%>
																	        <c:when test="${index==requestScope.userPage.index }">
																	           <li class="active"><a href="${pageContext.request.contextPath }/user/toUserList/${index}">${index}</a></li>
																	        </c:when>
																	        <c:otherwise>
																	           <li><a href="${pageContext.request.contextPath }/user/toUserList/${index}">${index}</a></li>
																	        </c:otherwise>
																	      </c:choose>
																	     
																	    </c:forEach>
																	    <c:choose>
																	      <c:when test="${requestScope.userPage.next==true }">
																	        <li>
																		      <a href="${pageContext.request.contextPath }/user/toUserList/${requestScope.userPage.index+1}" aria-label="Next">
																		        <span aria-hidden="true">&raquo;</span>
																		      </a>
																		    </li>
																	      </c:when>
																	      <c:otherwise>
																	           <li class="disabled">
																			      <a href="#" aria-label="Next">
																			        <span aria-hidden="true">&raquo;</span>
																			      </a>
																			    </li>
																	      </c:otherwise>
																	    </c:choose>
																	    
																	    
																	  </ul>
																	</nav>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
					</div><!-- /.page-content -->
					<!-- 内容页 end -->
				</div>
			</div><!-- /.main-content -->
			<!--- 内容主体 end -->

			 <!--页尾 start -->
		 <jsp:include page="../commons/footer.jsp"></jsp:include>

		
			 <!--页尾 end -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="${pageContext.request.contextPath}/libs/ace/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="${pageContext.request.contextPath}/libs/ace/js/jquery-1.11.3.min.js"></script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${pageContext.request.contextPath}/libs/ace/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${pageContext.request.contextPath}/libs/ace/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="${pageContext.request.contextPath}/libs/ace/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/libs/ace/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
				<script type="text/javascript">
		   //全选反选
		   $("#chkAllUsers").click(function(){
			   //当前选项框选中，所有的选项框都要选中，否则全部反选。
			   //第一步：获得当前选项框的对象
			   //第二步：判断当前选项框的选择状态，如果是选中的，其他选项框也是选中。否则也是反选。
			    if($(this).prop("checked")==true){
			    	$("input[type='checkbox']").prop("checked",true);
			    } else{
			    	$("input[type='checkbox']").prop("checked",false);
			    }
			   
		   });
		   //提交选中的用户编号，发送到数据库
		   $("#btnDeleteCheckUsers").click(function(){
			   
			   //第一步：获得选中的用户编号
			   var userIds=$("input[name='userId']:checked");
			   // console.log(userIds);
			   //第二步：将选中的用户的编号通过一个数组的方式发生到后台
			    location.href="${pageContext.request.contextPath}/user/deleteCheckUsers?"+userIds.serialize();
			   
		   });
		   
		</script>
	</body>
</html>

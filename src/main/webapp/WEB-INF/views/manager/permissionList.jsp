<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
								<a href="#">权限管理</a>
							</li>
							<li class="active">管理权限-列表</li>
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
													<h4 class="widget-title lighter">权限管理</h4>

													<div class="widget-toolbar no-border">
														<ul class="nav nav-tabs" id="myTab2">
															<li >
																<a data-toggle="tab" href="javascript:void(0)" onclick="window.location.href='${pageContext.request.contextPath}/permission/toPermissionAdd'">增加</a>
															</li>

													

															<li class="active">
																<a data-toggle="tab" href="#powerList">列表</a>
															</li>
														</ul>
													</div>
												</div>

												<div class="widget-body">
													<div class="widget-main padding-12 no-padding-left no-padding-right">
														<div class="tab-content padding-4">
															

													        
															<div id="powerList" class="tab-pane in active">
															<button onclick="Base.deleteCheckEntity('permissionId','${pageContext.request.contextPath}/permission/deleteCheckPermissions')" class="btn btn-primary">删除勾选权限</button>
															     
																 <table class="table table-striped table-bordered align-center "> 
																    <tr class="bolder" >
																	  <td><input onclick="Base.checkAll(this)" type="checkbox"></td>
																	  <td>编号</td>
																	  <td>权限名</td>
																	  <td>请求路径</td>
																	  <td>所属模块</td>
																	  <td>是否显示</td>
																	  <td>父权限</td>
																	  
																	  <td>操作</td>
																	</tr>
																	<c:forEach var="permission" items="${requestScope.permissionPage.data }">
																	<tr>
																	  <td><input name="permissionId" value="${permission.permission_id}" type="checkbox"></td>
																	  <td>${permission.permission_id }</td>
																	  <td>${permission.permission_name }</td>
																	   <td>${permission.permission_action}</td>
																	    <td>${permission.modular.modular_name}</td>
																	     <td>${permission.permission_is_show}</td>
																	        <td>${permission.parent.permission_name}</td>

																	   <td><button class="btn btn-minier btn-white btn-info" onclick="location.href='${pageContext.request.contextPath}/permission/toPermissionEdit/${permission.permission_id}'">&nbsp;更&nbsp;新&nbsp;</button>&nbsp;&nbsp;<button onclick="location.href='${pageContext.request.contextPath}/permission/deletePermissionById/${permission.permission_id}'"  class="btn btn-minier btn-white btn-info">&nbsp;删&nbsp;除&nbsp;</button></td>
																	</tr>
																   <tr>
																   </c:forEach>
																 </table>
																 <div class="text-right">
																	 <nav>
																		<ul class="pagination">
																	    <c:choose>
																	      <c:when test="${requestScope.permissionPage.previous==true }">
																	           <li>
																			      <a href="${pageContext.request.contextPath }/permission/toPermissionList/${requestScope.permissionPage.index-1}" aria-label="Previous">
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
																	  
																	    
																	    <c:forEach var="index" begin="${requestScope.permissionPage.start }" end="${requestScope.permissionPage.end }">
																	      <c:choose>
																	       <%-- 如果迭代返回的索引与当前索引一样，表单就是当前页，所以选中 --%>
																	        <c:when test="${index==requestScope.permissionPage.index }">
																	           <li class="active"><a href="${pageContext.request.contextPath }/permission/toPermissionList/${index}">${index}</a></li>
																	        </c:when>
																	        <c:otherwise>
																	           <li><a href="${pageContext.request.contextPath }/permission/toPermissionList/${index}">${index}</a></li>
																	        </c:otherwise>
																	      </c:choose>
																	     
																	    </c:forEach>
																	    <c:choose>
																	      <c:when test="${requestScope.permissionPage.next==true }">
																	        <li>
																		      <a href="${pageContext.request.contextPath }/permission/toPermissionList/${requestScope.permissionPage.index+1}" aria-label="Next">
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/libs/base/base.js"></script>

	</body>
</html>

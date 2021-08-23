<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
								<a href="#">角色管理</a>
							</li>
							<li class="active">角色-列表</li>
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
													<h4 class="widget-title lighter">角色管理</h4>

													<div class="widget-toolbar no-border">
														<ul class="nav nav-tabs" id="myTab2">
															<li  class="active">
																<a data-toggle="tab" href="#roleAdd">编辑</a>
															</li>

													

															<li>
																<a data-toggle="tab" href="javascript:void(0)" onclick="window.location.href='${pageContext.request.contextPath }/role/toRoleList/1'">列表</a>
															</li>
														</ul>
													</div>
												</div>

												<div class="widget-body">
													<div class="widget-main padding-12 no-padding-left no-padding-right">
														<div class="tab-content padding-4">
														
															<div id="roleAdd" class="tab-pane in active ">
																<div class="scrollable-horizontal" data-size="800">
																	 ${requestScope.role_edit_msg }
																	<form action="${pageContext.request.contextPath }/role/editRole" method="post" class="form-horizontal" role="form">
																	    <input name="role_id" type="hidden" value="${requestScope.role.role_id }">
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色名 </label>

																			<div class="col-sm-9">
																				<input name="role_name" value="${requestScope.role.role_name }" type="text" id="form-field-1" placeholder="角色名" class="col-xs-10 col-sm-5" />
																			</div>
																		</div>
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色权限 </label>

																			<div class="col-sm-9">
																				<input name="role_permissions" value="${requestScope.role.role_permissions}" type="text" id="form-field-2" placeholder="角色名" class="col-xs-10 col-sm-5" />
																			</div>
																		</div>
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 全部权限 <input onclick="Base.checkAll(this)"  type="checkbox" /></label>

																		</div>

																		<c:forEach var="modular" items="${requestScope.modulars }">
																			<div class="form-group">
																				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> ${modular.modular_name }<input onclick="Base.chkScopeCheckAll(this,'.form-group')"  type="checkbox" /></label>

																				<div class="col-sm-6">
																					<table class="table">

																						<c:forEach varStatus="status" var="permission" items="${modular.permissions }">
																							<%--序号被4整除就换行 --%>
																							<c:if test="${status.index%4==0 }">
																								<tr>
																							</c:if>

																							<%-- 选中条件：就是当前的编辑权限字符串里面包含循环迭代的权限编号，就选中 --%>
																							<%-- 解决方案，将字符串通过逗号分隔一个权限编号数组，一个个和当前的权限匹配，如果：有为使用一个标志位变量设置为true --%>
																							<c:set var="flag" value="false"></c:set>

																							<c:forTokens var="permissionId" items="${requestScope.role.role_permissions}" delims=",">
																								<c:if test="${fn:trim(permissionId)==permission.permission_id }">
																									<c:set var="flag" value="true"></c:set>
																								</c:if>
																							</c:forTokens>
																							<c:choose>
																								<c:when test="${flag==true }">
																									<td>${permission.permission_name }<input checked="checked" name="permissionId" value="${permission.permission_id }"  type="checkbox" /></td>
																								</c:when>
																								<c:otherwise>
																									<td>${permission.permission_name }<input name="permissionId" value="${permission.permission_id }"  type="checkbox" /></td>
																								</c:otherwise>
																							</c:choose>

																							<%-- 序号加1被4整除就换行--%>
																							<c:if test="${(status.index+1)%4==0 }">
																								</tr>
																							</c:if>
																						</c:forEach>


																					</table>

																				</div>
																			</div>
																		</c:forEach>
																		<div class="form-group">
																			
																			<div class="col-sm-7 text-right">
																				<button type="submit" class="btn btn-primary">编辑角色</button>
																			</div>
																		</div>
																	</form>

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

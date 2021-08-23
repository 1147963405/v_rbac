<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
							<li class="active">权限-列表</li>
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
															<li  class="active">
																<a data-toggle="tab" href="#powerAdd">编辑</a>
															</li>

													

															<li>
																<a data-toggle="tab" href="javascript:void(0)" onclick="window.location.href='${pageContext.request.contextPath }/permission/toPermissionList/1'">列表</a>
															</li>
														</ul>
													</div>
												</div>

												<div class="widget-body">
													<div class="widget-main padding-12 no-padding-left no-padding-right">
														<div class="tab-content padding-4">
														
															<div id="powerAdd" class="tab-pane in active ">
																<div class="scrollable-horizontal" data-size="800">
																	${requestScope.permission_edit_msg }
																	<form action="${pageContext.request.contextPath }/permission/editPermission" method="post" class="form-horizontal" role="form">
																		  <input name="permission_id" type="hidden" value="${requestScope.permission.permission_id }">
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 权限名 </label>

																			<div class="col-sm-9">
																				<input name="permission_name" value="${requestScope.permission.permission_name }" type="text" id="form-field-1" placeholder="权限名" class="col-xs-10 col-sm-5" />
																			</div>
																		</div>

																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 请求路径 </label>

																			<div class="col-sm-9">
																				<input name="permission_action" value="${requestScope.permission.permission_action }" type="text" id="form-field-2" placeholder="请求路径" class="col-xs-10 col-sm-5" />
																			</div>
																		</div>
																	
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属模块 </label>

																			<div class="col-sm-9">
																				<select name="modular_id">
																				   <c:forEach var="modular" items="${applicationScope.global_modulars }">
																				     <%--选中条件：当前迭代的模块的编号与当前编辑的权限对应的模块编号相同 --%>
																				     <c:choose>
																				       <c:when test="${modular.modular_id==requestScope.permission.modular_id }">
																				        <option selected="selected" value="${modular.modular_id}">${modular.modular_name }</option>
																				        </c:when>
																				       <c:otherwise>
																				       <option value="${modular.modular_id }">${modular.modular_name }</option>
																				       </c:otherwise>
																				     </c:choose>
																				   </c:forEach>
																				</select>
																			</div>
																		</div>
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示 </label>

																			<div class="col-sm-9">
																				<select name="permission_is_show">
																				   <c:forEach var="dic" items="${applicationScope.global_dictionarys }">
																				      <c:if test="${dic.dictionary_type_code==100002 }">
																				        <c:choose>
																				          <%--选中条件：当前迭代的字典值和当前编辑权限的状态值相同 --%>
																				          <c:when test="${dic.dictionary_value==requestScope.permission.permission_is_show }">
																				          <option selected="selected" value="${dic.dictionary_value }">${dic.dictionary_name }</option>
																				          </c:when>
																				          <c:otherwise>
																				          <option value="${dic.dictionary_value }">${dic.dictionary_name }</option>
																				          </c:otherwise>
																				        </c:choose>
																				      </c:if>
																				      
																				   </c:forEach>
																				</select>
																			</div>
																		</div>
																		<div class="form-group">
																			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 父权限 </label>

																			<div class="col-sm-9">
																				<select name="permission_parent">
																				   <option value="0">顶级菜单</option>
																				   <c:forEach var="p" items="${applicationScope.global_permissions }">
																				      <%--可以选择的权限必须是一个菜单，并且状态是显示的 --%>
																				      <c:if test="${p.permission_parent==0 and p.permission_is_show==0 }">
																				         <%--选中条件：当前权限的父权限编号，迭代权限的权限编号 --%>
																				         <c:choose>
																				           <c:when test="${p.permission_id==requestScope.permission.permission_parent }">
																				             <option selected="selected" value="${p.permission_id }">${p.permission_name}</option>
																				           </c:when>
																				           <c:otherwise>
																				           <option value="${p.permission_id }">${p.permission_name}</option>
																				           </c:otherwise>
																				         </c:choose>
																				         
																				      </c:if>
																				   </c:forEach>
																				</select>
																			</div>
																		</div>
																	
											
																		<div class="form-group">
																			
																			<div class="col-sm-7 text-right">
																				<button type="submit" class="btn btn-primary">编辑权限</button>
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
	</body>
</html>

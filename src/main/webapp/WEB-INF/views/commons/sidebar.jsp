<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="sidebar" class="sidebar responsive ace-save-state">
				<script type="text/javascript">
					try{ace.settings.loadState('sidebar')}catch(e){}
				</script>

				<ul class="nav nav-list">
					<li class="">
						<a href="">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text"> 系统首页 </span>
						</a>

						<b class="arrow"></b>
					</li>
					<c:forEach var="modular" items="${sessionScope.user.modulars }">
					<li class="open ">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop"></i>
							<span class="menu-text">
								${modular.modular_name }
							</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
						  <c:forEach var="permission" items="${sessionScope.user.role.permissions }">
						     <%--&lt;%&ndash;条件：权限必须是显示的，必须是菜单，必须是当前模块的 &ndash;%&gt;--%>
						     <c:if test="${permission.permission_is_show==0 and permission.permission_parent==0 and permission.modular_id==modular.modular_id }">
							   <%--	&lt;%&ndash;判断：什么时候选中，什么时候不选中。答：点击的时候选中 &ndash;%&gt;--%>
							   	<c:choose>
							   	  <c:when test="${fn:contains(sessionScope.path,permission.permission_action) }">
							   	  
							   	   <li class="active">
							   	
									<a href="${pageContext.request.contextPath }${permission.permission_action}/1">
										<i class="menu-icon fa fa-caret-right"></i>
										${permission.permission_name }
									</a>
									<b class="arrow"></b>
								</li>
							   	  </c:when>
							   	  <c:otherwise>
							   	  	<li class="">
							   	
									<a href="${pageContext.request.contextPath }${permission.permission_action}/1">
										<i class="menu-icon fa fa-caret-right"></i>
										${permission.permission_name }
									</a>
	
									<b class="arrow"></b>
								</li>
							   	  </c:otherwise>
							   	</c:choose>
								</c:if>
							</c:forEach>
					
						</ul>
					</li>
					</c:forEach>
				</ul><!-- /.nav-list -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>
			</div>
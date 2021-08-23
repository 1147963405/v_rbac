<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="navbar" class="navbar navbar-default          ace-save-state">
			<div class="navbar-container ace-save-state" id="navbar-container">
	

				<div class="navbar-header pull-left">
					<a href="" class="navbar-brand">
						<small>
						    <!-- 
							 系统Logo
							<i class="fa fa-leaf"></i>
							-->
							后台系统
						</small>
					</a>
				</div>
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue dropdown-modal">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${pageContext.request.contextPath}/libs/ace/images/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
								    <!--系统角色-->
									<small>${user.role.role_name }[角色]</small>
									<!--管理员名称-->
									${user.user_name }
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="${pageContext.request.contextPath }/user/toSetting">
										<i class="ace-icon fa fa-cog"></i>
										修改密码
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${pageContext.request.contextPath }/user/undo">
										<i class="ace-icon fa fa-power-off"></i>
										注销
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>
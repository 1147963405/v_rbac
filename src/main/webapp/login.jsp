<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>后台系统-登录</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${pageContext.request.contextPath}/libs/ace/css/ace-ie.min.css" />
		<![endif]-->

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${pageContext.request.contextPath}/libs/ace/js/html5shiv.min.js"></script>
		<script src="${pageContext.request.contextPath}/libs/ace/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">Ace</span>
									<span class="white" id="id-text2">后台系统</span>
								</h1>
								<h4 class="red" id="id-company-text">${user_login_msg}</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												后台用户登录
											</h4>

											<div class="space-6"></div>

											<form action="${pageContext.request.contextPath }/user/login" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input name="user_account" type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input name="user_password" type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
												

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登&nbsp;录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

										
										</div><!-- /.widget-main -->

									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->


							</div><!-- /.position-relative -->

						
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
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

		<!-- inline scripts related to this page -->

	</body>
</html>

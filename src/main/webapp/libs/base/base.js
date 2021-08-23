//声明一个类名
Base = function() {
}

// 全部选中、全部反选
Base.checkAll = function(obj) {
	if ($(obj).prop("checked") == true) {
		$("input[type='checkbox']").prop("checked", true);
	} else {
		$("input[type='checkbox']").prop("checked", false);
	}
};

//指定区域，全部选中、反选
Base.chkScopeCheckAll=function(obj,exp){
	//指定区域
	var chkPermissions=$(obj).parents(exp);
	//区域内，复选框全选反选
	var allCheckBox=chkPermissions.find("input[type='checkbox']");
	if($(obj).prop("checked")==true){
		allCheckBox.prop("checked",true);
	}else{
		allCheckBox.prop("checked",false);
	}
};


//提交选中的模块编号，发送到数据库
Base.deleteCheckEntity=function(idName,url){
	   //第一步：获得选中的模块编号
	   var objects=$("input[name='"+idName+"']:checked");
	   // console.log(permissionIds);
	   //第二步：将选中的模块的编号通过一个数组的方式发生到后台
	    location.href=url+"?"+objects.serialize(); 
};
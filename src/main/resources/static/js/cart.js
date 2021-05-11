function changeNum(cid,rid,num){
	var url="../carts/changeNum"
	var params={
		cid:cid,
		num:num
	}
	$.post(url,params,function(result){
		if(result.state==1000){
			console.log(cid+":changeNum:"+num+":success");
			var n = parseInt($("#goodsCount"+rid).val());
			$("#goodsCount"+rid).val(n + num);
			calcRow(rid);
		}else{
			alert(result.msg);
		}
	})
}

/*按加号数量增*/
function addNum(rid,cid) {
	// 修改数据库记录
	changeNum(cid,rid,1);
}
/*按减号数量减*/
function reduceNum(rid,cid) {
	var n = parseInt($("#goodsCount"+rid).val());
	if (n <=1)
		return;
	// 修改数据库记录
	changeNum(cid,rid,-1);
}
/*全选全不选*/
function checkall(ckbtn) {
	$(".ckitem").prop("checked", $(ckbtn).prop("checked"));
	calcTotal();
}
//删除按钮
function delCartItem(btn) {
	
	$(btn).parents("tr").remove();
	//calcTotal();
}

$(function() {
	//单选一个也得算价格
	// $(".ckitem").click(function() {
	// 	calcTotal();
	// })
	//开始时计算价格
	//calcTotal();
})
//计算单行小计价格的方法
function calcRow(rid) {
	//取单价
	var vprice = parseFloat($("#goodsPrice"+rid).html());
	//取数量
	var vnum = parseFloat($("#goodsCount"+rid).val());
	//小计金额
	var vtotal = vprice * vnum;
	//赋值
	$("#goodsCost"+rid).html("¥" + vtotal);
}

//计算总价格的方法
function calcTotal() {
	//选中商品的数量
	var vselectCount = 0;
	//选中商品的总价
	var vselectTotal = 0;

	//循环遍历所有tr
	for (var i = 0; i < $(".cart-body tr").length; i++) {
		//计算每个商品的价格小计开始
		//取出1行
		var $tr = $($(".cart-body tr")[i]);
		//取单价
		var vprice = parseFloat($tr.children(":eq(3)").children("span").html());
		//取数量
		var vnum = parseFloat($tr.children(":eq(4)").children(".num-text").val());
		//小计金额
		var vtotal = vprice * vnum;
		//赋值
		$tr.children(":eq(5)").children("span").html("¥" + vtotal);
		//计算每个商品的价格小计结束

		//检查是否选中
		if ($tr.children(":eq(0)").children(".ckitem").prop("checked")) {
			//计数
			vselectCount++;
			//计总价
			vselectTotal += vtotal;
		}
		//将选中的数量和价格赋值
		$("#selectTotal").html(vselectTotal);
		$("#selectCount").html(vselectCount);
	}
}
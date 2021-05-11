function changeOrders(status) {
    orderListUrl= "../orders/list?status="+status;

    $.get(orderListUrl,function(result){
        if(result.state === 200){
            vueObj.orders=result.data;
        }else{
            alert(result.msg);
        }
    })
}







$(function(){  //加载事件
    //1.发送异步请求获取房屋类型，进行显示
    $.post("getAllType",null,function (data) {
        //如果这里不用异步提交，后面getAllType中的参数用model,页面就可以用jstl和el表达式
        for(var i=0;i<data.length;i++){
            //创建节点
            var node=$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
            //追加节点
            $("#type_id").append(node);
        }
        //设置区域回显的选中项
        $("#district_id").val(${h.did});
        //设置回显时候的选中项
        $("#type_id").val(${h.typeId});
    },"json");
    //1.发送异步请求获取区域，进行显示
    $.post("getAllDistrict",null,function (data) {
        for(var i=0;i<data.length;i++){
            //创建节点
            var node=$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
            //追加节点
            $("#district_id").append(node);
        }
        loadStreet();
    },"json");
/*绑定数据并在类型管理页面显示*/
$(function () {
    //使用datagrid绑定数据库
    $('#dg').datagrid({
        url: "getTypeByPageBean",
        /*表示在相对当前目录下的getType/admin/目录下，如果在前面加一个/表示在根目录下的getType下
        * ！尝试下在url的路径前加一个斜杠，然后，controller层不加admin路径
        * */
        title: "区域信息",
        toolbar:"#ToolBar",//指定工具栏
        pagination: true,
        pageSize: 3,
        pageList: [3, 6, 10, 20],
        /*nowrap:true,*/
        fitColumns: true,
        columns: [[
            {field: 'cb',checkbox:"true"},
            {field: 'id', title: '编号', width: 100},
            {field: 'name', title: '区域名称', width: 100},
            {
                field: 's', title: '操作', width: 100,
                formatter: function (value, row, index) {
                    var str = "<a href='javascript:deleteTypeById("+row.id+");'>删除</a>" +
                        "|<a href='javascript:updateDialog()'>修改</a>";
                    return str;
                }
            }
        ]]
    });
});
//点击添加，打开窗口
function addDialog() {
    //alert("我要添加区域");
    $('#AddDialog').window('setTitle', "添加区域");
    $('#AddDialog').window('open');
}
//关闭对话框
function CloseAddDialog(id) {
    $('#'+id).window('close'); //关闭
}
//显示修改的对话框
function updateDialog() {
    //判断用户选择
    //1、获取datagrid的选中行
    var SelectRows=$("#dg").datagrid('getSelections');//返回数组
    if(SelectRows.length==1){
        $('#updateDialog').window('setTitle',"编辑区域");
        $('#updateDialog').window('open');

        //获取当前行的数据：获取主键，查询数据库获取单行数据
        //如果当显示的数据在datagrid中存在，无需查询数据库，如果当显示的数据在datagrid中补全，需要查询数据库获单条
        //发异步请求查询数据库
        $.post("getOneType",{"id":SelectRows[0].id},function (data) {
            $("#form2").form('load',data)//将对象还原表单,并且将查询到的数据重写到id为form2的表单中
        },"json");
        //将信息还原到表单中.
        //$("#form1").form('load',json对象);
        //$("#form2").form('load',{"name":"默认值"});  //name表示表单对象名称
        //$("#form2").form('load',SelectRows[0]);  //{"id":1001,"name":"东城"}
    }else{
        $.messager.alert('>>提示','你没有选择行或者选择多行','warn');//提示框
    }
}
/*新增窗口的保存操作*/
function SaveDialog() {
    //1、获取数据，发送异步请求实现添加
    //$.post("addType",给服务器传参，传入的参数与后台接收的参数名保持一致，回调函数function(data){},返回数据格式"json");
    /* $.post("addType",{"name":$("#name1").val()},function (data) {
         alert(data);
     },"json");*/

    //2、使用easyui插件以异步方式提交表单
    $('#form1').form('submit',{
        url: "addType",
        success: function (data) {//回调
            //将返回的json字符串转换为json对象
            data = $.parseJSON(data);
            if (data.result > 0) {
                $.messager.alert('>>提示', '添加成功！', 'info');//提示框
                $('#AddDialog').window('close');//关闭
            } else {
                $.messager.alert('>>提示', '添加失败！', 'error');
                $('#AddDialog').window('close');
            }
        }
    });
}
/*更新窗口中的更新操作*/
function updateSaveDialog() {
    //3、使用easyui插件以异步方式提交表单修改数据
    $('#form2').form('submit',{
        url: "updateType",
        success: function (data) {//回调
            //将返回的json字符串转换为json对象
            data = $.parseJSON(data);
            if (data.result > 0) {
                $.messager.alert('>>提示', '修改成功！', 'info');//提示框
                $('#updateDialog').window('close');//关闭
                $('#dg').datagrid('reload')//重新刷新dg
            } else {
                $.messager.alert('>>提示', '修改失败！', 'error');
                $('#updateDialog').window('close');
            }
        }
    });
}
//删除单个区域
function deleteTypeById(id){//参数表示点击时传递过来的参数
    $.messager.confirm('>>提示','此操作将会删除该类型房屋，确定删除么？',function (r) {
        if(r){//r返回的是confirm选择的结果是确定还是取消，确定r为true,取消为false
            //实现删除，删除数据库,删除单个，返回的是1或0，即受影响的行数
            $.post("deleteTypeById",{"id":id},function (data) {//传入的数据是大括号中json数据格式一定要与controller层调用方法中的形参名保持一致
                //回调函数运行成功后的返回结果是data中的json数据,responseBody将方法的返回值返回给返回函数的传入参数
                if(data.result>0){//删除成功
                    var a=data.result;
                    //刷新datagrid
                    $('#dg').datagrid('reload');
                }else{
                    $.messager.alert('>>提示','删除失败！','error');
                }
            },"json");
        }
    })
}
//批量删除区域
function deleteMoreType(){
    //1、获取选中的行；2、判断是否有选择行；3、获取选中项的值，拼接成字符串，格式1，2，3
    var selectRows=$("#dg").datagrid('getSelections');
    if(selectRows.length==0){
        $.messager.alert('>>提示','请选中后再删除！','warn');
    }else{
        //确认删除
        $.messager.confirm('>>提示','是否确定删除？',function (r) {
            if(r){
                var val="";//将删除的id拼接成字符串传到后端方法中
                for(var i=0;i<selectRows.length;i++){
                    val=val+selectRows[i].id+",";
                }
                val=val.substring(0,val.length-1);

                $.post("deleteMoreType",{"ids":val},function(data) {//1删除成功，0含有出租房，-1，删除操作失败
                    if (data.result == 1) {
                        $('#dg').datagrid('reload');
                    }else if(data.result==0){
                        $.messager.alert('>>提示','该类型中存在房屋在出租状态','error');
                    }else{
                        $.messager.alert('>>提示','删除失败！','error');
                    }
                },"json")
            }
        });
    }
}
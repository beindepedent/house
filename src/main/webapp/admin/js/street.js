/*绑定数据并在街道管理页面显示*/
$(function () {
    //使用datagrid绑定数据库
    $('#sg').datagrid({
        url: "getStreetByPageBean",
        /*表示在相对当前目录下的getStreet/admin/目录下，如果在前面加一个/表示在根目录下的getStreet下
        * ！尝试下在url的路径前加一个斜杠，然后，controller层不加admin路径
        * */
        title: "街道信息",
        toolbar:"#ToolBar",//指定工具栏
        pagination: true,
        pageSize: 3,
        pageList: [3, 6, 10, 20],
        /*nowrap:true,*/
        fitColumns: true,
        columns: [[
            {field: 'cb',checkbox:"true"},
            {field: 'id', title: '编号', width: 100},
            {field: 'name', title: '街道名称', width: 100},
            {
                field: 's', title: '操作', width: 100,
                formatter: function (value, row, index) {
                    var str = "<a href='javascript:deleteStreetById("+row.id+");'>删除</a>" +
                        "|<a href='javascript:updateDialog()'>修改</a>" +
                        "|<a href='javascript:openShowHouses("+row.id+")'>查看房屋</a>";
                    return str;
                }
            }
        ]]
    });
});
//点击添加，打开添加街道窗口窗口
function addDialog() {
    //alert("我要添加街道");
    $('#AddDialog').window('setTitle', "添加街道");
    $('#AddDialog').window('open');
}
//关闭对话框
function CloseAddDialog(id) {
    $('#'+id).window('close'); //关闭
}
//显示街道修改的对话框
function updateDialog() {
    //判断用户选择
    //1、获取datagrid的选中行
    var SelectRows=$("#sg").datagrid('getSelections');//返回数组
    if(SelectRows.length==1){
        $('#updateDialog').window('setTitle',"编辑街道");
        $('#updateDialog').window('open');

        //获取当前行的数据：获取主键，查询数据库获取单行数据
        //如果当显示的数据在datagrid中存在，无需查询数据库，如果当显示的数据在datagrid中补全，需要查询数据库获单条
        //发异步请求查询数据库
        $.post("getOneStreet",{"id":SelectRows[0].id},function (data) {
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
    //$.post("addStreet",给服务器传参，传入的参数与后台接收的参数名保持一致，回调函数function(data){},返回数据格式"json");
    /* $.post("addStreet",{"name":$("#name1").val()},function (data) {
         alert(data);
     },"json");*/

    //2、使用easyui插件以异步方式提交表单
    $('#form1').form('submit',{
        url: "addStreet",
        success: function (data) {//回调
            //将返回的json字符串转换为json对象
            data = $.parseJSON(data);
            if (data.result > 0) {
                $.messager.alert('>>提示', '添加成功！', 'info');//提示框
                $('#AddDialog').window('close');//关闭
                $('#sg').datagrid('reload')//重新刷新dg
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
        url: "updateStreet",
        success: function (data) {//回调
            //将返回的json字符串转换为json对象
            data = $.parseJSON(data);
            if (data.result > 0) {
                $.messager.alert('>>提示', '修改成功！', 'info');//提示框
                $('#updateDialog').window('close');//关闭
                $('#sg').datagrid('reload')//重新刷新dg
            } else {
                $.messager.alert('>>提示', '修改失败！', 'error');
                $('#updateDialog').window('close');
            }
        }
    });
}
//删除单个街道 ？？？需要判断该街道下是否有对应的出租房
function deleteStreetById(id){//参数表示点击时传递过来的参数
    $.messager.confirm('>>提示','此操作将会连带街道内的出租房一起删除，确定删除么？',function (r) {
        if(r){//r返回的是confirm选择的结果是确定还是取消，确定r为true,取消为false
            //实现删除，删除数据库,删除单个，返回的是1或0，即受影响的行数
            $.post("deleteStreetById",{"id":id},function (data) {//传入的数据是大括号中json数据格式一定要与controller层调用方法中的形参名保持一致
                //回调函数运行成功后的返回结果是data中的json数据,responseBody将方法的返回值返回给返回函数的传入参数
                if(data.result>0){//删除成功
                    var a=data.result;
                    //刷新datagrid
                    $('#sg').datagrid('reload');
                }else{
                    $.messager.alert('>>提示','删除失败！','error');
                }
            },"json");
        }
    })
}
//批量删除街道
function deleteMoreStreet(){
    //1、获取选中的行；2、判断是否有选择行；3、获取选中项的值，拼接成字符串，格式1，2，3
    var selectRows=$("#sg").datagrid('getSelections');
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

                $.post("deleteMoreStreet",{"ids":val},function(data) {
                    if (data.result > 0) {
                        $('#sg').datagrid('reload');
                    }else{
                        $.messager.alert('>>提示','批量删除失败！','error');
                    }
                },"json")
            }
        });
    }
}
//展示街道下面的房屋信息
function openShowHouses(sid) {
    $('#showHousesDialog').window('setTitle',"房屋信息");
    $('#showHousesDialog').window("open");
    //发请求绑定数据
    $('#dgHouse').datagrid({
        url: "getHousePageBySid?sid="+sid,
        /*表示在相对当前目录下的getDistrict/admin/目录下，如果在前面加一个/表示在根目录下的getDistrict下
        * ！尝试下在url的路径前加一个斜杠，然后，controller层不加admin路径
        * */
        title: "房屋信息",
        toolbar:"#ToolBar",//指定工具栏
        pagination: true,
        pageSize: 3,
        pageList: [3, 6, 10, 20],
        // sigleSelect:true,//单条选中
        /*nowrap:true,*/
        fitColumns: true,
        columns: [[
            {field: 'cb',checkbox:"true"},
            {field: 'id', title: '编号', width: 100},
            {field: 'userId', title: '用户编号', width: 100,
                formatter: function (value,row,index) {
                    var str="<input type='text' id='streetShow' value='"+value+"'>";
                    return str;
                }},
            {field:'typeId',title:'类型编号',width:100},
            {field:'title',title:'标题',width:100},
            {field:'description',title:'描述',width:100},
            {field:'price',title:'出租价格',width:100},
            {field:'pubdate',title:'发布日期',width:100},
            {field:'floorage',title:'面积',width:100},
            {field:'contact',title:'联系人',width:100},
            {field:'streetId',title:'街道编号',width:100},
            {field:'ispass',title:'是否审核通过',width:100},
            {field:'isdel',title:'是否删除',width:100},
            {field:'path',title:'图片路径',width:100},
            {
                field: 's', title: '操作', width: 100,
                formatter: function (value, row, index) {//index表示循环输出的下标
                    var str = "<a href='javascript:updateStreetInDistrict("+row.id+","+did+");'>修改</a>" ;
                    return str;
                }
            }
        ]]
    });
}
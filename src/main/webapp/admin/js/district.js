/*绑定数据并在区域管理页面显示*/
$(function () {
    //使用datagrid绑定数据库
    $('#dg').datagrid({
        url: "getDistrictByPageBean",
        /*表示在相对当前目录下的getDistrict/admin/目录下，如果在前面加一个/表示在根目录下的getDistrict下
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
                    var str = "<a href='javascript:deleteDistrictById("+row.id+");'>删除</a>" +
                        "|<a href='javascript:updateDialog()'>修改</a>" +
                        "|<a href='javascript:openShowStreets("+row.id+")'>查看街道</a>";
                    return str;
                }
            }
        ]]
    });
});
//点击添加，打开添加区域窗口窗口
function addDialog() {
    //alert("我要添加区域");
    $('#AddDialog').window('setTitle', "添加区域");
    $('#AddDialog').window('open');
}
//关闭对话框
function CloseAddDialog(id) {
    $('#'+id).window('close'); //关闭
}
//显示区域修改的对话框
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
        $.post("getOneDistrict",{"id":SelectRows[0].id},function (data) {
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
    //$.post("addDistrict",给服务器传参，传入的参数与后台接收的参数名保持一致，回调函数function(data){},返回数据格式"json");
    /* $.post("addDistrict",{"name":$("#name1").val()},function (data) {
         alert(data);
     },"json");*/

    //2、使用easyui插件以异步方式提交表单
    $('#form1').form('submit',{
        url: "addDistrict",
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
//区域更行窗口中区域更新操作
function updateSaveDialog() {
    // alert($("#name2").val());
    //3、使用easyui插件以异步方式提交表单修改数据
    $('#form2').form('submit',{
        url: "updateDistrict",
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
function deleteDistrictById(id){//参数表示点击时传递过来的参数
    $.messager.confirm('>>提示','此操作将会连带区域内的街道一起删除，确定删除么？',function (r) {
        if(r){//r返回的是confirm选择的结果是确定还是取消，确定r为true,取消为false
            //实现删除，删除数据库,删除单个，返回的是1或0，即受影响的行数
            $.post("deleteDistrictById",{"id":id},function (data) {//传入的数据是大括号中json数据格式一定要与controller层调用方法中的形参名保持一致
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
function deleteMoreDistrict(){
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

                $.post("deleteMoreDistrict",{"ids":val},function(data) {
                    if (data.result > 0) {
                        $('#dg').datagrid('reload');
                    }else{
                        $.messager.alert('>>提示','批量删除失败！','error');
                    }
                },"json")
            }
        });
    }
}
//打开对应区域显示区域中街道的对话框
function openShowStreets(did) {
    $('#showStreetDialog').window('setTitle',"街道信息");
    $('#showStreetDialog').window("open");
    //发请求绑定数据
    $('#dgStreet').datagrid({
        url: "getStreetPageByDid?did="+did,
        /*表示在相对当前目录下的getDistrict/admin/目录下，如果在前面加一个/表示在根目录下的getDistrict下
        * ！尝试下在url的路径前加一个斜杠，然后，controller层不加admin路径
        * */
        title: "街道信息",
        // toolbar:"#ToolBar",//指定工具栏
        pagination: true,
        pageSize: 3,
        pageList: [3, 6, 10, 20],
        // sigleSelect:true,//单条选中
        /*nowrap:true,*/
        fitColumns: true,
        columns: [[
            {field: 'cb',checkbox:"true"},
            {field: 'id', title: '编号', width: 100},
            {field: 'name', title: '街道名称', width: 100,
                formatter: function (value,row,index) {
                    var str="<input type='text' id='streetShow' value='"+value+"'>";
                    return str;
                }},
            {
                field: 's', title: '操作', width: 100,
                formatter: function (value, row, index) {//index表示循环输出的下标
                    var str = "<a href='javascript:updateStreetInDistrict("+row.id+","+did+");'>修改</a>" ;
                    return str;
                }
            }
        ]]
    });
    $("#form3").form('load',{"districtId":did});//区域展示街道窗口中回写对应区域的id,
}
//区域中街道窗口中的新增街道
function insertStreet() {
    $('#form3').form('submit',{
        url: "addStreet",
        success: function (data) {//回调
            //将返回的json字符串转换为json对象
            data = $.parseJSON(data);
            if (data.result > 0) {
                $('#dgStreet').datagrid('reload')//重新刷新dg
            } else {
                alert("添加失败！")
            }
        }
    });
}
//???通过街道的id删除对应的街道  ！！！删除街道操作需要判断街道中是否有租房信息，
function deleteStreetById(){
    /*$.messager.confirm('>>提示','此操作将会连带区域内的街道一起删除，确定删除么？',function (r) {
        if(r){//r返回的是confirm选择的结果是确定还是取消，确定r为true,取消为false
            //实现删除，删除数据库,删除单个，返回的是1或0，即受影响的行数
            $.post("deleteDistrictById",{"id":id},function (data) {//传入的数据是大括号中json数据格式一定要与controller层调用方法中的形参名保持一致
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
    * */
    $.messager.confirm(">>提示","此操作将会连带将街道中的租房信息一带删除，确定删除么？",function (r) {
        if(r){
            alert("通过街道的id删除对应的街道,还未实现 ！！！删除街道操作需要判断街道中是否有租房信息")
        }
    })
}
//???区域中显示街道，在对话框中异步更新修改街道名称
function updateStreetInDistrict(id,did) {
    if(id!=null){
        $.post("updateStreetInDistrict",{"id":id,"name":$("#streetShow").val(),"districtId":did},function (data) {
            data = $.parseJSON(data);
            if (data.result > 0) {
                $('#dg').datagrid('reload')//重新刷新dg
            }
        },"json");
    }
    // $.post()
}

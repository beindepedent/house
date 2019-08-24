$(function(){
    //使用datagrid绑定数据库
    $('#dg').datagrid({
        url:'getRUser',
        title:"用户信息",
        toolbar:"#ToolBar",  //指定工具栏
        pagination:true,
        pageList:[3,6,9,15,20],
        pageSize:3,
        //singleSelect:true,
        columns:[[
            {field:"cb",checkbox:true},
            {field:'id',title:'编号',width:100},
            {field:'name',title:'名称',width:100},
            {field:'telephone',title:'电话',width:100},
            {field:'age',title:'年龄',width:100},
            {field:'s',title:'操作',width:200,
                formatter: function(value,row,index){
                    var str="<a href='javascript:delDistrict("+row.id+");'>删除</a>";
                    return str;
                }
            }
        ]]
    });
});

//点击添加，打开窗口
function addDialog(){
    //alert("我要添加区域");
    $('#AddDialog').window('setTitle',"添加区域");
    $('#AddDialog').window('open');
}
//关闭对话框
function CloseAddDialog(id){
    $('#'+id).window('close'); //关闭
}


/*实现用户搜索功能*/
function userSearch(){
    //取值
    var name=$("#inputname").val();
    var tel=$("#inputtel").val();
    //重新加载
    // $('#dg').datagrid('load',传递查询条件参数);
    $('#dg').datagrid('load',{
        name: name,
        telephone: tel
    });
}
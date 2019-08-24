$(function(){
    //使用datagrid绑定数据库
    $('#dg').datagrid({
        url:'getYesPassHouse',
        title:"房屋审核",
        toolbar:"#ToolBar",  //指定工具栏
        pagination:true,
        pageList:[3,6,9,15,20],
        pageSize:3,
        //singleSelect:true,
        columns:[[
            {field: 'cb',checkbox:"true"},
            {field: 'did', title: '编号', width: 100},
            {field: 'title', title: '标题', width: 100},
            {field: 'tname', title: '类型', width: 100},
            {field: 'floorage', title: '面积', width: 100},
            {field: 'price', title: '价格', width: 100},
            {field: 'description', title: '描述', width: 100},
            {field: 'contact', title: '联系方式', width: 100},
            {field: 'dname', title: '区域', width: 100},
            {field: 'sname', title: '街道', width: 100},
            {
                field: 's', title: '操作', width: 100,
                formatter: function(value,row,index){
                    var str="<a href='javascript:goPass("+row.id+");'>取消审核</a>";
                    return str;
                }
            }
        ]]
    });
});
/*实现异步请求，实现取消审核的服务器接口*/
function  goPass(id) {
    //发送异步请求
    $.post("alterNoHouseState",{"id":id},function (data) {
        if(data.result>0){
            $('#dg').datagrid('load');
        }else{
            $.messager.alert("提示","取消审核失败","warn")
        }
    },"json")
}
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
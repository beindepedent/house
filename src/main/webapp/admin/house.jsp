<%--
  Created by IntelliJ IDEA.
  User: xxt
  Date: 2019/8/14
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyUI/css/demo.css">
    <script src="js/jquery-1.8.3.js"></script>
    <!--jquery.easyui.min.js包含了easyUI中的所有插件-->
    <script src="js/jquery.easyui.min.js"></script>
    <script language="JavaScript" src="js/house.js"></script>
    <script language="JavaScript">
        $(function(){  //加载事件
            //1.发送异步请求获取房屋类型，进行显示
            $.post("/page/getAllType",null,function (data) {
                //如果这里不用异步提交，后面getAllType中的参数用model,页面就可以用jstl和el表达式
                for(var i=0;i<data.length;i++){
                    //创建节点
                    var node=$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                    //追加节点
                    $("#type_id").append(node);
                }
            },"json");
            //1.发送异步请求获取区域，进行显示
            $.post("/page/getAllDistrict",null,function (data) {
                for(var i=0;i<data.length;i++){
                    //创建节点
                    var node=$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                    //追加节点
                    $("#district_id").append(node);
                }
                loadStreet();
            },"json");

            //给区域添加改变事件
            $('#district_id').change(loadStreet);
            //加载街道 代码复用
            function  loadStreet() {
                //获取区域编号
                var did=$("#district_id").val();
                //发送异步请求加载街道数据
                //清空原有数据项
                $("#street_id>option").remove();
                var node=$("<option value=0>"+"-不限-"+"</option>");
                //追加节点
                $("#street_id").append(node);
                $.post("/page/getStreetPageByDid",{"did":did},function (data) {
                    for(var i=0;i<data.length;i++){
                        //创建节点
                        var node=$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                        //追加节点
                        $("#street_id").append(node);
                    }
                },"json");
            }
        });
       function userSearch(){
           var title=$("#title").val();
           var did=$("#district_id").val();
           var streetId=$("#street_id").val();
           var typeId=$("#type_id").val();
           var startPrice=$("#startPrice").val();
           var endPrice=$("#endPrice").val();
           var startArea=$("#startArea").val();
           var endArea=$("#endArea").val();
           alert("title:"+title+"dname:"+did+"sname:"+streetId+"type:"+typeId
               +"startPrice:"+startPrice+"endPrice:"+endPrice+"startArea:"+startArea+"endArea:"+endArea);
           /*$.post("backHouseConditionSearch",{},function (data) {
               if(data.result>0){

               }

           },"json")*/

       }
    </script>
</head>
<body>
<!--显示所有区域-->
<table id="dg"></table>

<!--制作工具栏-->
<div id="ToolBar">
    <div style="height: 70px;">
        标题:<input type="text" id="inputname"  name="title"/>
        区域: <select  name="dname" id="district_id">
        <option value="0">--不限--</option>
    </select>
        街道: <select name="sname" id="street_id">
        <option value="0">--不限--</option>
    </select>
        类型: <select name="type" id="type_id">
        <option value="0">--不限--</option>
    </select>
        价格: <input name="startPrice" id="startPrice"/>---<input name="endPrice" id="endPrice"/>
        面积: <input name="startArea" id="startArea"/>-- <input type="endArea"id="endArea">
        <a id="btn" href="javascript:userSearch();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
    </div>
</div>
</body>
</html>

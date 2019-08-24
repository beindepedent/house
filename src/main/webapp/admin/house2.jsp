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
    <script language="JavaScript" src="js/house2.js"></script>
</head>
<body>
<!--显示所有区域-->
<table id="dg"></table>

<!--制作工具栏-->
<div id="ToolBar">
    <div style="height: 40px;">
        <a href="javascript:addDialog()" class="easyui-linkbutton"
           iconCls="icon-add" plain="true">添加</a> <a
            href="javascript:updateDialog()" class="easyui-linkbutton"
            iconCls="icon-edit" plain="true">修改</a> <a
            href="javascript:deleteMoreDistrict()" class="easyui-linkbutton"
            iconCls="icon-remove" plain="true">多项删除</a>

        标题:<input type="text" id="inputname"  name="title"/>
        区域: <select name=""  name="dname"></select>
        街道: <select name=""  name="sname"></select>
        类型: <select name="" name="type"></select>
        价格: <select name="" name="price"></select>
        <a id="btn" href="javascript:userSearch();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
    </div>
</div>
</body>
</html>

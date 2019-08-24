<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>区域管理</title>
    <link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyUI/css/demo.css">
    <script src="js/jquery-1.8.3.js"></script>
    <!--jquery.easyui.min.js包含了easyUI中的所有插件-->
    <script src="js/jquery.easyui.min.js"></script>
    <script language="JavaScript" src="js/district.js"></script>
</head>
<body>
<!--显示所有区域-->
<table id="dg"></table>
<%--制作工具栏--%>
<div id="ToolBar">
    <div style="height: 40px;">
        <a href="javascript:addDialog()" class="easyui-linkbutton"
           iconCls="icon-add" plain="true">添加</a> <a
            href="javascript:updateDialog()" class="easyui-linkbutton"
            iconCls="icon-edit" plain="true">修改</a> <a
            href="javascript:deleteMoreDistrict()" class="easyui-linkbutton"
            iconCls="icon-remove" plain="true">批量删除</a>
    </div>
</div>
<!--添加对话框-->
<div id="AddDialog" class="easyui-dialog" buttons="#AddDialogButtons"
     style="width: 280px; height: 250px; padding: 10px 20px;" closed="true">
    <form method="post" action="" name="form1" id="form1">
        区域名称:<input type="text" name="name" id="name1">
    </form>
</div>
<!--添加对话框的按钮-->
<div id="AddDialogButtons">
    <a href="javascript:SaveDialog()" class="easyui-linkbutton"
       iconCls="icon-ok">保存</a> <a href="javascript:CloseAddDialog('AddDialog')"
                                   class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
<!--修改对话框-->
<div id="updateDialog" class="easyui-dialog" buttons="#updateDialogButtons"
     style="width: 280px; height: 250px; padding: 10px 20px;" closed="true">
    <form method="post" action="" name="form1" id="form2">
        区域编号: <input type="text"  readonly style="border: none" class="easyui-validatebox" required
                     name="id" id="bname" /><br>
            <%--<input type="hidden" name="id">--%>
        区域名称:<input type="text" name="name" id="name2">
    </form>
</div>
<!--修改对话框的按钮-->
<div id="updateDialogButtons">
    <a href="javascript:updateSaveDialog()" class="easyui-linkbutton"
       iconCls="icon-ok">修改</a> <a href="javascript:CloseAddDialog('updateDialog')"
                                   class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
<%--显示对应区域中街道对话框--%>
<div id="showStreetDialog" class="easyui-dialog" buttons="#showDialogButtons" style="width: 500px;height:350px;padding: 10px 20px;" closed="true">
    <table id="dgStreet"></table>
    <br/>
    <form action="post" action="" id="form3" class="easyui-dialog" buttons="#insertStreetButton">
        所属区域: <input type="text" name="districtId" readonly style="border: none" class="easyui-validatebox"><br>
        街道名称: <input type="text" name="name">
        <%--<input type="button" id="streetSubmit" value="提交" &lt;%&ndash;buttons="#insertStreetButton"&ndash;%&gt;>--%>
    </form>
</div>
<%--街道对话框中的按钮--%>
<div id="showDialogButtons">
   <%-- <a href="javascript:showStreetSaveDialog()" class="easyui-linkbutton"
       iconCls="icon-ok">确认</a>--%> <a href="javascript:CloseAddDialog('showStreetDialog')"
                                   class="easyui-linkbutton" iconCls="icon-cancel">退出</a>
</div>
<%--<div id="insertStreetButton">
     <a href="javascript:insertStreet()"
        iconCls="icon-ok">确认</a>
</div>--%>

</body>
</html>

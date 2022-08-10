<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
		$(function () {
            //给CheckBoxall添加单机事件
            $("#checkedAll").click(function () {
                $("#tBody input[type='checkbox']").prop("checked", this.checked);
            });
            //给每条checkbox添加单机事件
            $("#tBody").on("click", "input[type='checkbox']", function () {
                if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
                    $("#checkedAll").prop("checked", true);
                } else {
                    $("#checkedAll").prop("checked", false);
                }
            });

            //给删除按钮添加单机事件
           $("#deleteDicTypeBtn").click(function () {
               var $checkedCodes = $("#tBody input[type='checkbox']:checked");
               if($checkedCodes.size()==0){
                   alert("请选择需要删除的字典类型");
                   return;
               }
               if (window.confirm("确定删除吗?")) {
                   var codes = "";
                   $.each($checkedCodes, function () {
                       //一个bug待处理
                       codes += "code=" + this.value + "&";
                   });
                   codes = codes.substring(0, codes.length - 1);
                   //alert(ids);
                   //发送请求
                   $.ajax({
                       url: 'settings/dictionary/type/deleteDicType.do',
                       data: codes,
                       type: 'post',
                       dataType: 'json',
                       success: function (data) {
                           if (data.code == "1") {
                               //alert("成功了");
                              window.location.href="settings/dictionary/type/index.do";
                           } else {
                               //提示信息
                               alert(data.message);
                           }
                       }
                   });
               }

           });

           //给编辑按钮添加单机事件
            $("#editDicTypeBtn").click(function () {
                var $checkedTypes = $("#tBody input[type='checkbox']:checked");
                if($checkedTypes.size()==0){
                    alert("请选择需要修改的字典类型");
                    return;
                }
                if($checkedTypes.size()>1){
                    alert("每次只能修改一条记录");
                    return;
                }
                var type = $checkedTypes[0].value;
                window.location.href='settings/dictionary/type/edit.do?'+type;
            });
        });
    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/save.do'">
            <span class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" id="editDicTypeBtn"><span
                class="glyphicon glyphicon-edit"></span> 编辑
        </button>
        <button type="button" class="btn btn-danger" id="deleteDicTypeBtn"><span
                class="glyphicon glyphicon-minus"></span> 删除
        </button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="checkedAll"/></td>
            <td>序号</td>
            <td>编码</td>
            <td>名称</td>
            <td>描述</td>
        </tr>
        </thead>
        <tbody id="tBody">
        <C:forEach items="${requestScope.typeList}" var="type" varStatus="stauts">
            <tr class="active">
                <td><input type="checkbox" value="code=${type.code}&name=${type.name}&description=${type.description}"/></td>
                <td>${stauts.count}</td>
                    <%--count从1开始--%>
                    <%--<td>${stauts.index}</td>--%> <%--index从0开始--%>
                <td>${type.code}</td>
                <td>${type.name}</td>
                <td>${type.description}</td>
            </tr>
        </C:forEach>
        <%--<tr class="active">
            <td><input type="checkbox" /></td>
            <td>1</td>
            <td>sex</td>
            <td>性别</td>
            <td>性别包括男和女</td>
        </tr>--%>
        </tbody>
    </table>
</div>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">


    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css"
          href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <!--  PAGINATION plugin -->
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

    <title>市场活动</title>
    <script type="text/javascript">

        $(function () {
            //给创建按钮加单击事件
            $("#createActivityBtn").click(function () {
                //重置表单  转为DOM对象重置
                $("#createActivityForm").get(0).reset();
                //弹出创建市场活动的模态窗口
                $("#createActivityModal").modal("show");
            });

            //给模态窗口日期添加日历
            //当容器加载完毕 对容器调用工具函数
            $(".myDate").datetimepicker({
                language: "zh-CN",
                format: 'yyyy-mm-dd',
                minView: 'month',
                initialDate: new Date(),
                autoclose: true,
                todayBtn: true,
                clearBtn: true
            });

            //给模态窗口保存按钮添加单机事件
            $("#saveCreateActivityBtn").click(function () {
                //收集参数
                var owner = $("#create-marketActivityOwner").val();
                var name = $.trim($("#create-marketActivityName").val());
                var startDate = $("#create-startDate").val();
                var endDate = $("#create-endDate").val();
                var cost = $("#create-cost").val();
                var description = $("#create-describe").val();
                if (owner == "") {
                    alert("所有者不能为空");
                    return;
                }
                if (name == "") {
                    alert("名称不能为空");
                    return;
                }
                if (startDate != "" && endDate != "") {
                    //使用字符串的大小比较
                    if (endDate < startDate) {
                        alert("结束日期不能小于开始日期")
                        return;
                    }
                }
                var regExp = /^(([1-9]\d*)|0)$/;
                if (!regExp.test(cost)) {
                    alert("成本只能为非负整数");
                    return;
                }
                $.ajax({
                    url: 'workbench/activity/saveCreateActivity.do',
                    data: {
                        owner: owner,
                        name: name,
                        startDate: startDate,
                        endDate: endDate,
                        cost: cost,
                        description: description
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");
                            //刷新市场活动列，显示第一页数据
                            queryActivityByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            //提示信息
                            alert(data.message);
                            //模态窗口不关闭
                            $("#createActivityModal").modal("show");//可省虐不写
                        }
                    }
                });
            });

            //当市场活动主页面加载完成，查询所有数据的第一页以及所有数据的总条数,默认每页显示10条
            queryActivityByConditionForPage(1, 10);

            //给"查询"按钮添加单击事件
            $("#queryActivityBtn").click(function () {
                //查询所有符合条件数据的第一页以及所有符合条件数据的总条数;
                queryActivityByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
            });

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
            $("#deleteBtn").click(function () {
                var checkedIds = $("#tBody input[type='checkbox']:checked");
                if (checkedIds.size() == 0) {
                    alert("请选择删除的市场活动");
                    return;
                }
                if (window.confirm("确定删除吗?")) {
                    var ids = "";
                    $.each(checkedIds, function () {
                        ids += "ids=" + this.value + "&";
                    });
                    ids = ids.substring(0, ids.length - 1);
                    //alert(ids);
                    //发送请求
                    $.ajax({
                        url: 'workbench/activity/deleteActivityByIds.do',
                        data: ids,
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == "1") {
                                //alert("成功了");
                                queryActivityByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                            } else {
                                //提示信息
                                alert(data.message);
                            }
                        }
                    });
                }

            });

            //给修改市场活动添加单机事件
            $("#editActivityBtn").click(function () {
                var $checkedIds = $("#tBody input[type='checkbox']:checked");
                if ($checkedIds.size() == 0) {
                    alert("请选择需要修改的一条市场活动");
                    return;
                }
                if ($checkedIds.size() > 1) {
                    alert("每次只能修改一条记录");
                    return;
                }
                var id = $checkedIds.get(0).value;
                //alert(id);
                $.ajax({
                    url: "workbench/activity/queryActivityById.do",
                    data: 'id=' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        $("#edit-markedActivityId").val(data.id);
                        $("#edit-marketActivityOwner").val(data.owner);
                        $("#edit-marketActivityName").val(data.name);
                        $("#edit-startDate").val(data.startDate);
                        $("#edit-endDate").val(data.endDate);
                        $("#edit-cost").val(data.cost);
                        $("#edit-describe").val(data.description);
                        //弹出模态窗口
                        $("#editActivityModal").modal("show");
                    }
                });
            });

            //给更新按钮添加点击事件
            $("#remarkActivityUpdateBtn").click(function () {
                var id = $("#edit-markedActivityId").val();
                var owner = $("#edit-marketActivityOwner").val();
                var name = $("#edit-marketActivityName").val();
                var startDate = $("#edit-startDate").val();
                var endDate = $("#edit-endDate").val();
                var cost = $("#edit-cost").val();
                var description = $("#edit-describe").val();
                if (owner == "") {
                    alert("所有者不能为空");
                    return;
                }
                if (name == "") {
                    alert("名称不能为空");
                    return;
                }
                if (startDate != "" && endDate != "") {
                    //使用字符串的大小比较
                    if (endDate < startDate) {
                        alert("结束日期不能小于开始日期")
                        return;
                    }
                }
                var regExp = /^(([1-9]\d*)|0)$/;
                if (!regExp.test(cost)) {
                    alert("成本只能为非负整数");
                    return;
                }
                $.ajax({
                    url: "workbench/activity/editActivity.do",
                    data: {
                        id: id,
                        owner: owner,
                        name: name,
                        startDate: startDate,
                        endDate: endDate,
                        cost: cost,
                        description: description
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == '1') {
                            $("#editActivityModal").modal("hide");
                            //刷新市场活动列，显示当前页
                            queryActivityByConditionForPage($("#demo_pag1").bs_pagination('getOption', 'currentPage'), $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            alert(data.message());
                            $("#editActivityModal").modal("show");
                        }
                    }
                });
            });

            //给批量导出市场活动按钮添加单机事件
            $("#exportActivityAllBtn").click(function () {
                window.location.href = "workbench/activity/exportAllActivities.do";
            });

            //给选择导出市场活动添加单机按钮
            $("#exportActivitySelectedBtn").click(function () {
                var $checkedIds = $("#tBody input[type='checkbox']:checked");
                if ($checkedIds.size() == 0) {
                    alert("请选择需要导出的市场活动");
                    return;
                }
                var ids = "";
                $.each($checkedIds, function () {
                    ids += "id=" + this.value + "&";
                });
                if (window.confirm("你选择了" + $checkedIds.size() + "条数据，确定导出吗?")) {
                    //var url="workbench/activity/exportSelectedActivities.do";
                    ids = ids.substring(0, ids.length - 1);
                    /* //发送带ids的同步请求 ???为什么不行????
                     $.ajaxSettings.async=false;
                     $.get(url,ids,function (data) {
                         return data;
                     });*/
                    window.location.href = "workbench/activity/exportSelectedActivities.do?" + ids;
                }
            });

            //给下载市场活动按钮添加单机事件
            $("#DownloadActivityFile").click(function () {
                window.location.href = "workbench/activity/downloadActivityFile.do";
            });

            //给导入按钮添加点击事件
            $("#importActivityBtn").click(function () {
                //收集参数
                var activityFileName = $("#uploadActivityFile").val();
                var suffix = activityFileName.substring(activityFileName.lastIndexOf(".") + 1).toLocaleLowerCase();
                //alert(suffix);
                if (suffix != "xls") {
                    alert("只支持xls文件");
                    return;
                }
                var activityFile = $("#uploadActivityFile")[0].files[0];
                //FormData是ajax提供的接口,可以模拟键值对向后台提交参数;
                //FormData最大的优势是不但能提交文本数据，还能提交二进制数据
                var formData = new FormData();
                formData.append("activityFile", activityFile);
                //formData.append("userName","lisi");
                //发送Ajax请求
                $.ajax({
                    url: 'workbench/activity/importActivities.do',
                    data: formData,
                    processData: false,
                    contentType: false,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            //提示成功导入记录条数
                            alert("成功导入" + data.retData + "条记录");
                            //关闭模态窗口
                            $("#importActivityModal").modal("hide");
                            //刷新市场活动列表,显示第一页数据,保持每页显示条数不变
                            queryActivityByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
                        } else {
                            //提示信息
                            alert(data.message);
                            //模态窗口不关闭
                            $("#importActivityModal").modal("show");
                        }
                    }
                });
            });

        });

        //条件分页查询市场活动
        function queryActivityByConditionForPage(pageNo, pageSize) {
            //收集参数
            var name = $("#query-name").val();
            var owner = $("#query-owner").val();
            var startDate = $("#query-startDate").val();
            var endDate = $("#query-endDate").val();
            $.ajax({
                url: 'workbench/activity/queryActivityByConditionForPage.do',
                data: {
                    name: name,
                    owner: owner,
                    startDate: startDate,
                    endDate: endDate,
                    pageNo: pageNo,
                    pageSize: pageSize
                },
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    //显示总条数
                    var htmlStr = "";
                    $.each(data.activityList, function (index, obj) {
                        htmlStr += "<tr class=\"active\">"
                        htmlStr += "<td><input type=\"checkbox\" value=\"" + obj.id + "\"/></td>"
                        htmlStr += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id=" + obj.id + "'\">" + obj.name + "</a></td>";
                        htmlStr += "<td>" + obj.owner + "</td>";
                        htmlStr += "<td>" + obj.startDate + "</td>";
                        htmlStr += "<td>" + obj.endDate + "</td>";
                        htmlStr += "</tr>"
                    });
                    $("#tBody").html(htmlStr);
                    //每查一次取消全选
                    $("#checkedAll").prop("checked", false);
                    //计算总页数
                    var totalPages = 1;
                    if (data.totalRows % pageSize == 0) {
                        totalPages = data.totalRows / pageSize;
                    } else {
                        totalPages = parseInt(data.totalRows / pageSize) + 1;
                    }
                    //调用分页工具函数,显示翻页信息
                    //对容器调用bs_pagination工具函数，显示翻页信息
                    $("#demo_pag1").bs_pagination({
                        currentPage: pageNo,//当前页号,相当于pageNo

                        rowsPerPage: pageSize,//每页显示条数,相当于pageSize
                        totalRows: data.totalRows,//总条数
                        totalPages: totalPages,  //总页数,必填参数.

                        visiblePageLinks: 4,//最多可以显示的卡片数

                        showGoToPage: true,//是否显示"跳转到"部分,默认true--显示
                        showRowsPerPage: true,//是否显示"每页显示条数"部分。默认true--显示
                        showRowsInfo: true,//是否显示记录的信息，默认true--显示

                        //用户每次切换页号，都自动触发本函数;
                        //每次返回切换页号之后的pageNo和pageSize
                        onChangePage: function (event, pageObj) { // returns page_num and rows_per_page after a link has clicked
                            //js代码
                            //alert(pageObj.currentPage);
                            //alert(pageObj.rowsPerPage);
                            queryActivityByConditionForPage(pageObj.currentPage, pageObj.rowsPerPage);
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" id="createActivityForm" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">
                                <c:forEach items="${requestScope.users}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control myDate" id="create-startDate" readonly>
                        </div>
                        <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control myDate" id="create-endDate" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveCreateActivityBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-markedActivityId">
                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                                <c:forEach items="${requestScope.users}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control myDate" id="edit-startDate" value="2020-10-10">
                        </div>
                        <label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control myDate" id="edit-endDate" value="2020-10-20">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" value="5,000">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="remarkActivityUpdateBtn">更新</button>
            </div>
        </div>
    </div>
</div>

<!-- 导入市场活动的模态窗口 -->
<div class="modal fade" id="importActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
            </div>
            <div class="modal-body" style="height: 350px;">
                <div style="position: relative;top: 20px; left: 50px;">
                    请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                </div>
                <div style="position: relative;top: 40px; left: 50px;">
                    <input type="file" id="uploadActivityFile">
                </div>
                <div style="position: relative;top: 60px; left: 50px;">
                    点击以下按钮下载模板文件：<small style="color: gray;">[仅支持.xls]</small>
                </div>
                <div style="position: relative;top: 80px; left: 50px;">
                    <button style="accent-color: #2a6496" id="DownloadActivityFile">市场活动模板文件下载</button>
                </div>
                <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;">
                    <h3>重要提示</h3>
                    <ul>
                        <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                        <li>给定文件的第一行将视为字段名。</li>
                        <li>请确认您的文件大小不超过5MB。</li>
                        <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                        <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                        <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                        <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="query-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="query-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="query-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="query-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="createActivityBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="editActivityBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal">
                    <span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）
                </button>
                <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）
                </button>
                <button id="exportActivitySelectedBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）
                </button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="checkedAll"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="tBody">
                <%-- <tr class="active">
                     <td><input type="checkbox"/></td>
                     <td><a style="text-decoration: none; cursor: pointer;"
                            onclick="window.location.href='detail.jsp';">发传单</a></td>
                     <td>zhangsan</td>
                     <td>2020-10-10</td>
                     <td>2020-10-20</td>
                 </tr>
                 <tr class="active">
                     <td><input type="checkbox"/></td>
                     <td><a style="text-decoration: none; cursor: pointer;"
                            onclick="window.location.href='detail.jsp';">发传单</a></td>
                     <td>zhangsan</td>
                     <td>2020-10-10</td>
                     <td>2020-10-20</td>
                 </tr>--%>
                </tbody>
            </table>
            <div id="demo_pag1"></div>
        </div>
        <%-- <div style="height: 50px; position: relative;top: 30px;">
             <div>
                 <button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
             </div>
             <div class="btn-group" style="position: relative;top: -34px; left: 110px;">
                 <button type="button" class="btn btn-default" style="cursor: default;">显示</button>
                 <div class="btn-group">
                     <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                         10
                         <span class="caret"></span>
                     </button>
                     <ul class="dropdown-menu" role="menu">
                         <li><a href="#">20</a></li>
                         <li><a href="#">30</a></li>
                     </ul>
                 </div>
                 <button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
             </div>
             <div style="position: relative;top: -88px; left: 285px;">
                 <nav>
                     <ul class="pagination">
                         <li class="disabled"><a href="#">首页</a></li>
                         <li class="disabled"><a href="#">上一页</a></li>
                         <li class="active"><a href="#">1</a></li>
                         <li><a href="#">2</a></li>
                         <li><a href="#">3</a></li>
                         <li><a href="#">4</a></li>
                         <li><a href="#">5</a></li>
                         <li><a href="#">下一页</a></li>
                         <li class="disabled"><a href="#">末页</a></li>
                     </ul>
                 </nav>
             </div>
         </div>--%>

    </div>

</div>
</body>
</html>
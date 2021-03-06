<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>租房推荐网站</title>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="css/house.css"/>
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
    <style type="text/css">
        .content {
            padding: 10px 10px 10px 10px;
        }
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    <ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
        <li>
            <span>房源管理</span>
            <ul>
                <li data-options="attributes:{'url':'house-rent-list'}">查询租房信息</li>
                <li data-options="attributes:{'url':'spider'}">获取租房数据</li>
                <li data-options="attributes:{'url':'favourate-house-rent-list'}">关注租房信息</li>
            </ul>
        </li>

    </ul>
</div>
<div data-options="region:'center',title:''">
    <div id="tabs" class="easyui-tabs">
        <div title="首页" style="padding:20px;">

        </div>
    </div>
</div>

<script type="text/javascript">
  $(function () {
    $('#menu').tree({
      onClick: function (node) {
        if ($('#menu').tree("isLeaf", node.target)) {
          var tabs = $("#tabs");
          var tab = tabs.tabs("getTab", node.text);
          if (tab) {
            tabs.tabs("select", node.text);
          } else {
            tabs.tabs('add', {
              title: node.text,
              href: node.attributes.url,
              closable: true,
              bodyCls: "content"
            });
          }
        }
      }
    });
  });
</script>
<script type="text/javascript" src="js/websocket.js"></script>
</body>
</html>
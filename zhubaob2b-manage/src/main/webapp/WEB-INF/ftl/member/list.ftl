<html>
<head>
    <title>我的会员</title>
</head>

<body>

    <div class="fr w750 bo1 pd10 mb20">
        <div class="t1w">
            <div class="div_title">我的会员（完善中......）</div>
            <div class="cont_choice">
                <input type="text"  placeholder="会员搜索" /><button class="bt3">搜索</button>
            </div>
            <div class="clear"></div>
        </div>
        <div class="pd10">
            <table class="myTable" width="100%">
                <thead class="mythead">
                <tr>
                    <td align="center">会员名称</td>
                    <td align="center">交易额</td>
                    <td align="center">交易次数</td>
                    <td align="center">地区</td>
                    <td align="center">最新交易</td>
                    <td align="center">加入时间 </td>
                    <td align="center">操作</td>
                </tr>
                </thead>
                <tr>
                    <td align="center">天然无语旗舰店</td>
                    <td align="center">1212313元</td>
                    <td align="center">40</td>
                    <td align="center">北京</td>
                    <td align="center">2013-10-15</td>
                    <td align="center">2013-12-21 </td>
                    <td align="center"><a href="" title="" target="_blank">查看详情>></a></td>
                </tr>
                <tr>
                    <td align="center">天然无语旗舰店</td>
                    <td align="center">1212313元</td>
                    <td align="center">40</td>
                    <td align="center">北京</td>
                    <td align="center">2013-10-15</td>
                    <td align="center">2013-12-21 </td>
                    <td align="center"><a href="" title="" target="_blank">查看详情>></a></td>
                </tr>
                <tr>
                    <td align="center">天然无语旗舰店</td>
                    <td align="center">1212313元</td>
                    <td align="center">40</td>
                    <td align="center">北京</td>
                    <td align="center">2013-10-15</td>
                    <td align="center">2013-12-21 </td>
                    <td align="center"><a href="" title="" target="_blank">查看详情>></a></td>
                </tr>
            </table>
            <div class="clear"></div>

            <div class="fy">
            <#assign pageLink='${managePath}/manage/member/list.do' />
            <@c.page pagination=pagination pageLink=pageLink></@c.page>
                <div class="clear"></div>
            </div>

            <div class="clear"></div>
        </div>
    </div>

<div class="clear"></div>

</body>
</html>
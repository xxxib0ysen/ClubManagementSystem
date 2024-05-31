// 加载首页内容
function loadHomePage() {
    // 在页面中设置一个容器用于显示图表
    $('#content').html('<div id="mainChart" style="height: 400px;"></div>');

    // 初始化Echarts实例
    var myChart = echarts.init(document.getElementById('mainChart'));

    // 发送Ajax请求获取社团会员数量统计数据
    $.ajax({
        url: '/clubs/member-counts', // 假设这个端点返回会员数量统计数据
        type: 'GET',
        success: function(response) {
            // 从响应中提取社团名称和会员数量
            var clubs = response.map(item => item.club_name);
            var counts = response.map(item => item.memberCount);

            // 定义Echarts图表的配置选项
            var option = {
                title: {
                    text: '社团会员数量统计',
                    left: 'center' // 标题居中显示
                },
                tooltip: {}, // 提示框组件
                xAxis: {
                    type: 'category',
                    data: clubs, // x轴数据为社团名称
                    name: '社团'
                },
                yAxis: {
                    type: 'value',
                    name: '会员数量' // y轴名称
                },
                series: [{
                    data: counts, // 系列数据为会员数量
                    type: 'bar', // 图表类型为柱状图
                    showBackground: true, // 显示背景
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)' // 背景颜色
                    }
                }]
            };

            // 使用指定的配置项和数据显示图表
            myChart.setOption(option);
        },
        error: function() {
            // 如果请求失败，显示错误信息
            $('#mainChart').html('<p>加载数据失败，请稍后重试。</p>');
        }
    });
}

function loadHomePage() {
    $('#content').html('<div id="mainChart" style="height: 400px;"></div>');
    var myChart = echarts.init(document.getElementById('mainChart'));

    $.ajax({
        url: '/clubs/member-counts', // Assume this endpoint returns member counts
        type: 'GET',
        success: function(response) {
            var clubs = response.map(item => item.club_name);
            var counts = response.map(item => item.memberCount);
            var option = {
                title: {
                    text: '社团会员数量统计',
                    left: 'center'
                },
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: clubs,
                    name: '社团'
                },
                yAxis: {
                    type: 'value',
                    name: '会员数量'
                },
                series: [{
                    data: counts,
                    type: 'bar',
                    showBackground: true,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'
                    }
                }]
            };
            myChart.setOption(option);
        },
        error: function() {
            $('#mainChart').html('<p>加载数据失败，请稍后重试。</p>');
        }
    });
}

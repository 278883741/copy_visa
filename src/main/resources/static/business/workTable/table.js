if($("#applyResult").val() == "" && $("#visaApply").val() == "" && $("#visaRecord").val() == "" && $("#visaResult").val() == "" && $("#studentDelay").val() == "" && $("#visitStudent").val() == 0 && $("#schoolConfirm").val() == 0 && $("#toAuditGetVisa").val() == 0 && $("#toAuditCOE").val() == 0){
    $("#backlogWork").hide();
}


$(document).ready(function() {
    var chart = {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    };
    var title = {
        text: '学生服务进程分布图'
    };
    var tooltip = {
        pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
    };
    var oaid = $("#oaid").val();
    var plotOptions = {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            },
            point:{
                // 每个扇区是数据点对象，所以事件应该写在 point 下面
                events: {
                    // 鼠标滑过是，突出当前扇区
                    mouseOver: function() {
                        this.slice();
                    },
                    // 鼠标移出时，收回突出显示
                    mouseOut: function() {
                        this.slice();
                    },
                    // 默认是点击突出，这里屏蔽掉
                    click: function() {
                        window.open("/studentInfo?status="+this.id+"&oaid="+oaid);
                    }
                }
            }
        }
    };
 /*   var series= [{
        type: 'pie',
        name: 'Browser share',
        data: [
            ['Firefox',   45.0],
            ['IE',       26.8],
            {
                name: 'Chrome',
                y: 12.8,
                sliced: true,
                selected: true
            },
            ['Safari',    8.5],
            ['Opera',     6.2],
            ['Others',   0.7]
        ]
    }];*/
  var series= [{}];
    $.ajax({
        url: "/data",
        type: "get",
        /*data: $("#edit").serialize(),*/
        success: function (data) {
            series[0]=data;
            var json = {};
            json.chart = chart;
            json.title = title;
            json.tooltip = tooltip;
            json.series = series;
            json.plotOptions = plotOptions;
            $('#container').highcharts(json);
        }
    });



    var json = {};
    json.chart = chart;
    json.title = title;
    json.tooltip = tooltip;
    json.series = series;
    json.plotOptions = plotOptions;
});


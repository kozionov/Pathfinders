function showGraph() {
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

        if (gr != null && gr.length > 1) {
            var data = google.visualization.arrayToDataTable(gr);

            var view = new google.visualization.DataView(data);
            view.setColumns([0, 1,
                {
                    calc: "stringify",
                    sourceColumn: 1,
                    type: "string",
                    role: "annotation"
                },
                2]);

            var options = {
                title: "Сумма баллов",
                width: 600,
                height: 400,
                bar: {groupWidth: "95%"},
                legend: {position: "none"},
            };
            var chart = new google.visualization.ColumnChart(document.getElementById("chart_div"));
            chart.draw(data, options);
        }
    }
};

function showStat(stat) {
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        stat.forEach(dat => {
            pie = [];
            pie.push(["Element", "Баллы"]);
            dat.y.forEach(function (value, i) {
                pie.push([dat.x[i] + "", value]);
            });

            var data = google.visualization.arrayToDataTable(pie);

            var options = {
                title: "" + dat.userName,
                is3D: false
            };

            var chart = new google.visualization.PieChart(
                document.getElementById("pie_div" + dat.userId)
            );
            chart.draw(data, options);
        });
    }
}
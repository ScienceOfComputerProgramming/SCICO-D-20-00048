var simulationColor = "#0579A8";
var currentColor = "#696969";

var strategicIndicators = [];
var qualityFactors = [];
var metrics = [];
var detailedCharts = [];
var factorsCharts = [];

function getAllMetrics(){
    var url = "../api/metrics/current";
    $.ajax({
        url : url,
        type: "GET",
        success: function (response) {
            metrics = response;
            showMetricsSliders(metrics);
        }
    });
}

function showMetricsSliders (metrics) {
    var metricsDiv = $("#metricsSliders");
    metrics.forEach(function (metric) {
        var div = document.createElement('div');
        div.id = "div" + metric.id;
        div.style.marginTop = "1em";
        div.style.marginBottom = "1em";

        var label = document.createElement('label');
        label.id = metric.id;
        label.textContent = metric.name;
        label.title = metric.description;
        div.appendChild(label);

        div.appendChild(document.createElement('br'));

        var slider = document.createElement("input");
        slider.id = "sliderValue" + metric.id;
        slider.style.width = "80%";
        var value = 0;
        if (metric.value !== 'NaN')
            value = metric.value;
        var sliderConfig = {
            id: "slider" + metric.id,
            min: 0,
            max: 1,
            step: 0.01,
            value: value
        };
        // Add original value
        var start, end;
        if (metric.value === 0) {
            start = 0;
            end = 0.03;
        }
        else if (metric.value === 1) {
            start = 0.97;
            end = 1;
        }
        else {
            start = metric.value - 0.015;
            end = metric.value + 0.015;
        }
        sliderConfig.rangeHighlights = [{
            start: start,
            end: end
        }];
        div.appendChild(slider);
        metricsDiv.append(div);
        $("#"+slider.id).slider(sliderConfig);
        $(".slider-rangeHighlight").css("background", currentColor);
    });
    if (qualityFactors.length > 0)
        checkMetricsSliders();
}

function getDetailedStrategicIndicators () {
    jQuery.ajax({
        dataType: "json",
        url: "../api/DetailedStrategicIndicators/CurrentEvaluation",
        cache: false,
        type: "GET",
        async: true,
        success: function (data) {
            function compare (a, b) {
                if (a.id < b.id) return -1;
                else if (a.id > b.id) return 1;
                else return 0;
            }
            data.sort(compare);
            var titles = [];
            var ids = [];
            var labels = [];
            var values = [];
            for (i = 0; i < data.length; ++i) {
                //for each dsi save name to titles vector and id to ids vector
                titles.push(data[i].name);
                strategicIndicators.push({
                    id: data[i].id,
                    name: data[i].name
                });
                strategicIndicators[i].factors = [];
                ids.push(data[i].id);
                labels.push([]);
                values.push([]);
                for (j = 0; j < data[i].factors.length; ++j) {
                    //for each factor save name to labels vector and value to values vector
                    if (data[i].factors[j].name.length < 27)
                        labels[i].push(data[i].factors[j].name);
                    else
                        labels[i].push(data[i].factors[j].name.slice(0, 23) + "...");
                    values[i].push(data[i].factors[j].value);
                    strategicIndicators[i].factors.push({
                        id: data[i].factors[j].id,
                        name: data[i].factors[j].name
                    });
                }
            }
            showDetailedStrategicIndicators(titles, ids, labels, values);
        }
    });
}

function showDetailedStrategicIndicators (titles, ids, labels, values) {
    for (i = 0; i < titles.length; ++i) {
        var p = document.createElement('p');
        p.innerHTML = titles[i];
        p.style.fontSize = "16px";
        p.style.color = "#000"
        var div = document.createElement('div');
        div.style.display = "inline-block";
        div.style.margin = "15px 5px 15px 5px";
        var ctx = document.createElement('canvas');
        ctx.id = 'canvas' + i;
        ctx.width = 400;
        ctx.style.display = "inline";
        document.getElementById("radarDetailed").appendChild(div).appendChild(ctx);
        div.appendChild(p)
        ctx.getContext("2d");
        if (labels[i].length === 2) {
            labels[i].push(null);
            //values[i].push(null);
        }
        var chart = new Chart(ctx, {    //draw chart with the following config
            type: 'radar',
            data: {
                labels: labels[i],
                datasets: [{
                    label: titles[i],
                    backgroundColor: 'rgba(105, 105, 105, 0.2)',
                    borderColor: currentColor,
                    pointBackgroundColor: currentColor,
                    pointBorderColor: currentColor,
                    data: values[i],
                    fill: true
                }]
            },
            options: {
                title: {
                    display: false,
                    fontSize: 16,
                    text: titles[i]
                },
                responsive: false,
                legend: {
                    display: false
                },
                scale: {    //make y axis scale 0 to 1 and set maximum number of axis lines
                    ticks: {
                        min: 0,
                        max: 1,
                        maxTicksLimit: 5
                    }
                }
            }
        });
        detailedCharts.push(chart);
        window.myLine = chart;
    }
}

function getFactors () {
    jQuery.ajax({
        dataType: "json",
        url: "../api/qualityFactors/current",
        cache: false,
        type: "GET",
        async: true,
        success: function (data) {
            var titles = [];
            var ids = [];
            var labels = [];
            var values = [];
            for (i = 0; i < data.length; ++i) {
                //for each dsi save name to titles vector and id to ids vector
                titles.push(data[i].name);
                qualityFactors.push({
                    id: data[i].id,
                    name: data[i].name
                });
                qualityFactors[i].metrics = [];
                ids.push(data[i].id);
                labels.push([]);
                values.push([]);
                for (j = 0; j < data[i].metrics.length; ++j) {
                    //for each factor save name to labels vector and value to values vector
                    if (data[i].metrics[j].name.length < 27)
                        labels[i].push(data[i].metrics[j].name);
                    else
                        labels[i].push(data[i].metrics[j].name.slice(0, 23) + "...");
                    values[i].push(data[i].metrics[j].value);
                    qualityFactors[i].metrics.push({
                        id: data[i].metrics[j].id,
                        name: data[i].metrics[j].name
                    });
                }
            }
            checkMetricsSliders();
            showFactors(titles, ids, labels, values);
        }
    });
}

function checkMetricsSliders() {
    metrics.forEach(function (metric) {
        var present = false;
        qualityFactors.forEach(function (qualityFactor) {
            qualityFactor.metrics.forEach(function (factorMetric) {
                if (metric.id === factorMetric.id)
                    present = true;
            });
        });
        if (!present) {
            var warning = document.createElement("span");
            warning.setAttribute("class", "glyphicon glyphicon-alert");
            warning.title = "This metric is not related to any factor"
            warning.style.paddingLeft = "1em";
            warning.style.fontSize = "15px";
            warning.style.color = "yellow";
            warning.style.textShadow = "-2px 0 2px black, 0 2px 2px black, 2px 0 2px black, 0 -2px 2px black";
            var divMetric = $("#div"+metric.id);
            divMetric.append(warning);
        }
    });
}

function showFactors (titles, ids, labels, values) {
    for (i = 0; i < titles.length; ++i) {
        var p = document.createElement('p');
        p.innerHTML = titles[i];
        p.style.fontSize = "16px";
        p.style.color = "#000"
        var div = document.createElement('div');
        div.style.display = "inline-block";
        div.style.margin = "15px 5px 15px 5px";
        var ctx = document.createElement('canvas');
        ctx.id = 'canvas' + i;
        ctx.width = 400;
        ctx.style.display = "inline";
        document.getElementById("radarFactors").appendChild(div).appendChild(ctx);
        div.appendChild(p)
        ctx.getContext("2d");
        if (labels[i].length === 2) {
            labels[i].push(null);
            //values[i].push(null);
        }
        var chart = new Chart(ctx, {    //draw chart with the following config
            type: 'radar',
            data: {
                labels: labels[i],
                datasets: [{
                    label: titles[i],
                    backgroundColor: 'rgba(105, 105, 105, 0.2)',
                    borderColor: currentColor,
                    pointBackgroundColor: currentColor,
                    pointBorderColor: currentColor,
                    data: values[i],
                    fill: true
                }]
            },
            options: {
                title: {
                    display: false,
                    fontSize: 16,
                    text: titles[i]
                },
                responsive: false,
                legend: {
                    display: false
                },
                scale: {    //make y axis scale 0 to 1 and set maximum number of axis lines
                    ticks: {
                        min: 0,
                        max: 1,
                        maxTicksLimit: 5
                    }
                }
            }
        });
        factorsCharts.push(chart);
        window.myLine = chart;
    }
}

$('#apply').click(function () {
    var metricsSlider = [];

    Array.from($("#metricsSliders").children()).forEach(function(element) {
        metricsSlider.push({
            id: element.children[0].id,
            name: element.children[0].textContent,
            value: element.children[3].value
        });
    });

    for (var i = 0; i < qualityFactors.length; i++) {
        var qualityFactor = qualityFactors[i];
        var dataset = {
            label: qualityFactor.name,
            backgroundColor: 'rgba(5, 121, 168, 0.2)',
            borderColor: simulationColor,
            pointBackgroundColor: simulationColor,
            pointBorderColor: simulationColor,
            data: [],
            fill: true
        };
        for (var j = 0; j < qualityFactor.metrics.length; j++) {
            var metric = qualityFactor.metrics[j];
            var newMetric = metricsSlider.find(function (element) {
                return element.id === metric.id;
            });
            dataset.data.push(newMetric.value);
        }

        if (factorsCharts[i].data.datasets.length > 1)
            factorsCharts[i].data.datasets[0].data = dataset.data;
        else
            factorsCharts[i].data.datasets.unshift(dataset);
        factorsCharts[i].update();
    }

    var newMetrics = [];
    for (var i = 0; i < metricsSlider.length; i++) {
        var previousMetric = metrics.find(function (element) {
            return element.id === metricsSlider[i].id
        });
        if (previousMetric.value !== 'NaN' && parseFloat(metricsSlider[i].value) !== parseFloat(previousMetric.value.toFixed(2)))
            newMetrics.push(metricsSlider[i]);
    }


    var date = metrics[0].date;

    $.ajax({
        url: "../api/qualityFactors/simulate?date="+date,
        data: JSON.stringify(newMetrics),
        type: "POST",
        contentType: 'application/json',
        success: function(qualityFactors) {
            for (var i = 0; i < strategicIndicators.length; i++) {
                var strategicIndicator = strategicIndicators[i];
                var dataset = {
                    label: strategicIndicator.name,
                    backgroundColor: 'rgba(5, 121, 168, 0.2)',
                    borderColor: simulationColor,
                    pointBackgroundColor: simulationColor,
                    pointBorderColor: simulationColor,
                    data: [],
                    fill: true
                };
                for (var j = 0; j < strategicIndicator.factors.length; j++) {
                    var factor = strategicIndicator.factors[j];
                    var newFactor = qualityFactors.find(function (element) {
                        return element.id === factor.id;
                    });
                    if (newFactor)
                        dataset.data.push(newFactor.value);
                }

                if (detailedCharts[i].data.datasets.length > 1)
                    detailedCharts[i].data.datasets[0].data = dataset.data;
                else
                    detailedCharts[i].data.datasets.unshift(dataset);
                detailedCharts[i].update();
            }
            simulateSI(qualityFactors);
        },
        error: function() {
            alert("Metrics simulation failed");
        }
    });
});

function simulateSI (qualityFactors) {
    var qfs = [];
    for (var i = 0; i < qualityFactors.length; i++) {
        qfs.push({
            id: qualityFactors[i].id,
            name: qualityFactors[i].name,
            value: qualityFactors[i].value
        });
    }

    console.log(qfs);

    var formData = new FormData();
    formData.append("factors", JSON.stringify(qfs));

    $.ajax({
        url: "../api/Simulate",
        data: formData,
        type: "POST",
        contentType: false,
        processData: false,
        error: function(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 405)
                alert(textStatus);
        },
        success: function(result) {
            data = result;
            drawSimulationNeedle("gaugeChart", 200, 237, simulationColor);
        }
    });
}

$('#restore').click(function () {
    $('#metricsSliders').empty();
    removeSimulation();
    getAllMetrics();
});

function removeSimulation() {
    d3.selectAll('.simulation').remove();
    if (factorsCharts[0].data.datasets.length > 1) {
        for (var i = 0; i < factorsCharts.length; i++) {
            factorsCharts[i].data.datasets.shift();
            factorsCharts[i].update();
        }
    }
    if (detailedCharts[0].data.datasets.length > 1) {
        for (var i = 0; i < detailedCharts.length; i++) {
            detailedCharts[i].data.datasets.shift();
            detailedCharts[i].update();
        }
    }
}

window.onload = function() {
    $("#simulationColor").css("background-color", simulationColor);
    $("#simulationColorDetailed").css("background-color", simulationColor);
    $("#simulationColorFactors").css("background-color", simulationColor);
    $("#currentColor").css("background-color", currentColor);
    $("#currentColorDetailed").css("background-color", currentColor);
    $("#currentColorFactors").css("background-color", currentColor);
    getAllMetrics();
    getFactors();
    getDetailedStrategicIndicators();
    getData(200, 237, false, false, currentColor);
};
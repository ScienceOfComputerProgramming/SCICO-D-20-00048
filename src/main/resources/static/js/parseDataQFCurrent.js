var isdsi = false;

var url;
if (getParameterByName('id').length !== 0) {
    url = parseURLSimple("../api/strategicIndicators/qualityFactors/metrics/current");
} else {
    var profileId = sessionStorage.getItem("profile_id");
    url = parseURLSimple("../api/qualityFactors/metrics/current?profile="+profileId);
}

//initialize data vectors
var titles = [];
var labels = [];
var ids = [];
var values = [];
var categories = [];

function getData() {
    titles = [];
    labels = [];
    ids = [];
    values = [];
    categories = [];

    //get data from API
    jQuery.ajax({
        dataType: "json",
        url: url,
        cache: false,
        type: "GET",
        async: true,
        success: function (data) {
            for (i = 0; i < data.length; ++i) {
                //for each qf save name to titles vector and id to ids vector
                titles.push(data[i].name);
                ids.push(data[i].id);
                labels.push([]);
                values.push([]);
                for (j = 0; j < data[i].metrics.length; ++j) {
                    //for each metric save name to labels vector and value to values vector
                    if (data[i].metrics[j].name.length < 27)
                        labels[i].push(data[i].metrics[j].name);
                    else
                        labels[i].push(data[i].metrics[j].name.slice(0, 23) + "...");
                    values[i].push(data[i].metrics[j].value);
                }
            }
            getMetricsCategories();
        }
    });

    console.log(titles);
    console.log("labels");
    console.log(labels);
    console.log(values);
}

function getMetricsCategories() {
    jQuery.ajax({
        url: "../api/metrics/categories",
        type: "GET",
        async: true,
        success: function (response) {
            categories = response;
            drawChart();
        }
    });
}
const chartRenderers = {
    load: renderSufferScoreChart,
    distance: renderDistanceChart,
};

let currentChart;

function renderChart(reportType, labels, values) {
    const renderer = chartRenderers[reportType];
    if (!renderer) {
        console.warn(`No chart renderer defined for reportType: ${reportType}`);
        return;
    }

    if (currentChart) {
        currentChart.destroy();
    }

    currentChart = renderer(labels, values);
}

function renderSufferScoreChart(labels, values) {
    const ctx = document.getElementById('chartCanvas');
    if (!ctx) return;

    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label: 'Weekly Suffer Score',
                data: values,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
            }]
        },
        options: {
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true
                }
            }
        }
    });
}


function renderDistanceChart(labels, values) {
    const ctx = document.getElementById('chartCanvas');
    if (!ctx) return;

    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label: 'Weekly Distance',
                data: values,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
            }]
        },
        options: {
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true
                }
            }
        }
    });
}
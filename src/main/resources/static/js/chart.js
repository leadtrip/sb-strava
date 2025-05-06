const chartRenderers = {
    load: (labels, values) => renderBarChart(labels, values, null, 'Load'),
    distance: (labels, values) => renderBarChart(labels, values, v => Math.round(v / 1000), 'Distance (km)'),
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

const COLOR_SCALE = [
    { pct: 0.0, color: [0, 128, 0] },     // Green
    { pct: 0.33, color: [255, 215, 0] },  // Yellow
    { pct: 0.66, color: [255, 0, 0] },    // Red
    { pct: 1.0, color: [128, 0, 128] }    // Purple
];


function getColorForValue(value, min, max) {
    const pct = (value - min) / (max - min);

    let i;
    for (i = 1; i < COLOR_SCALE.length - 1; i++) {
        if (pct < COLOR_SCALE[i].pct) break;
    }

    const lower = COLOR_SCALE[i - 1];
    const upper = COLOR_SCALE[i];
    const range = upper.pct - lower.pct;
    const rangePct = (pct - lower.pct) / range;

    const r = Math.round(lower.color[0] + rangePct * (upper.color[0] - lower.color[0]));
    const g = Math.round(lower.color[1] + rangePct * (upper.color[1] - lower.color[1]));
    const b = Math.round(lower.color[2] + rangePct * (upper.color[2] - lower.color[2]));

    return `rgb(${r},${g},${b})`;
}

function renderBarChart(labels, values, valueConverter, label) {
    const ctx = document.getElementById('chartCanvas');
    if (!ctx) return;

    const min = Math.min(...values);
    const max = Math.max(...values);

    const backgroundColors = values.map(v => getColorForValue(v, min, max));

    const chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: valueConverter ? values.map(valueConverter) : values,
                backgroundColor: backgroundColors,
                borderWidth: 2,
                borderRadius: 5,
            }]
        },
        options: {
            indexAxis: 'y',
            scales: {
                x: { beginAtZero: true }
            }
        }
    });

    return chart;
}
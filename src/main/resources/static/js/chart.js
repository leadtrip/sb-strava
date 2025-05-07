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

    return `rgb(${r},${g},${b},0.4)`;
}

function renderBarChart(labels, values, valueConverter, label) {
    const ctx = document.getElementById('chartCanvas');
    if (!ctx) return;

    const min = Math.min(...values);
    const max = Math.max(...values);
    const highlightIndex = 0;

    const backgroundColors = values.map((v, i) =>
        i === highlightIndex
            ? 'rgba(54, 162, 235, 0.4)'
            : getColorForValue(v, min, max)
    );

    const convertedValues = valueConverter ? values.map(valueConverter) : values;

    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label,
                data: convertedValues,
                backgroundColor: backgroundColors,
                borderWidth: 2,
                borderRadius: 20,
            }]
        },
        options: {
            indexAxis: 'y',
            responsive: true,
            animation: {
                duration: 1000,
                easing: 'easeOutBounce'
            },
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        color: '#333',
                        font: {
                            size: 14,
                            weight: 'bold'
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const value = context.parsed.x;
                            return `${label}: ${value}${label.includes('Distance') ? ' km' : ''}`;
                        }
                    }
                },
                datalabels: {
                    anchor: 'end',
                    align: 'right',
                    color: '#000',
                    font: {
                        size: 12,
                        weight: 'bold'
                    },
                    formatter: function(value) {
                        return value;
                    }
                }
            },
            scales: {
                x: {
                    position: 'top',
                    beginAtZero: true
                }
            },
            onClick: (e, elements) => {
                if (elements.length > 0) {
                    const index = elements[0].index;
                    const initialDateString = labels[index];
                    const {from, to} = getActivitySearchDates(initialDateString);
                    window.location.href = `/activities/filter?from=${from}&to=${to}`;
                }
            },
        },
        plugins: [ChartDataLabels]
    });
}

function getActivitySearchDates(dateString) {
    const [day, month, year] = dateString.split(" ");

    const monthIndex = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].indexOf(month);

    if (monthIndex === -1) {
        return "Invalid Month";
    }
    const from = new Date(year, monthIndex, parseInt(day, 10));

    if (isNaN(from.getTime())) {
        return "Invalid Date";
    }

    const to = new Date(from.getTime() + 7 * 24 * 60 * 60 * 1000);

    return {
        from: from.toISOString().slice(0, 19),
        to: to.toISOString().slice(0, 19)
    };
}

function renderActivityTypeChart(activities) {
    const typeCounts = activities.reduce((acc, activity) => {
        acc[activity.sportType] = (acc[activity.sportType] || 0) + 1;
        return acc;
    }, {});

    const labels = Object.keys(typeCounts);
    const values = Object.values(typeCounts);

    const ctx = document.getElementById('activityTypeChart').getContext('2d');

    return new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: [
                    '#4CAF50',
                    '#2196F3',
                    '#FF9800',
                    '#E91E63',
                    '#9C27B0'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Activity Type Breakdown'
                }
            }
        }
    });
}
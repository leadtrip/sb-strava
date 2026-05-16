let activityTypeChartInstance = null;


const chartRenderers = {
    load: (labels, values) => renderBarChart(labels, values, null, 'Load'),
    distance: (labels, values) => renderBarChart(labels, values, v => Math.round(v / 1000), 'Distance (km)'),
    loadCompare: (labels, values) => renderLineChart(labels, values, null, 'Load'),
    distanceCompare: (labels, values) => renderLineChart(labels, values, v => Math.round(v / 1000), 'Distance (km)'),
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

function renderLineChart(labels, values, valueConverter, label) {
    const canvas = document.getElementById('chartCanvas');
    if (!canvas) return;

    const container = document.getElementById('chartContainer');
    canvas.width = container.clientWidth;
    container.style.overflowY = 'auto';

    const convertedValues = valueConverter ? values.map(valueConverter) : values;

    return new Chart(canvas, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: convertedValues,
                tension: 0.4,
                pointRadius: 2,
                hitRadius: 10
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
                    text: label
                },
                tooltip: {
                    enabled: false,
                    external: externalTooltipHandler,
                    mode: 'index',
                    intersect: false
                }
            },
            interaction: {
                mode: 'index',
                intersect: false
            },
            scales: {
                x: {
                    ticks: {
                        autoSkip: true,
                        maxRotation: 45,
                        minRotation: 0
                    }
                }
            }
        },
    });
}

function externalTooltipHandler(context) {
    const {chart, tooltip} = context;
    let tooltipEl = chart.canvas.parentNode.querySelector('div.custom-chart-tooltip');

    if (!tooltipEl) {
        tooltipEl = document.createElement('div');
        tooltipEl.className = 'custom-chart-tooltip';
        tooltipEl.style.background = 'rgba(0, 0, 0, 0.85)';
        tooltipEl.style.borderRadius = '6px';
        tooltipEl.style.color = 'white';
        tooltipEl.style.opacity = 1;
        tooltipEl.style.pointerEvents = 'none';
        tooltipEl.style.position = 'absolute';
        tooltipEl.style.transition = 'all .1s ease';
        tooltipEl.style.padding = '12px';
        tooltipEl.style.zIndex = '100';
        tooltipEl.style.minWidth = '180px';
        tooltipEl.style.boxShadow = '0 4px 12px rgba(0,0,0,0.15)';
        chart.canvas.parentNode.appendChild(tooltipEl);
    }

    if (tooltip.opacity === 0) {
        tooltipEl.style.opacity = 0;
        return;
    }

    if (tooltip.body) {
        const titleLines = tooltip.title || [];
        const bodyLines = tooltip.body.map(b => b.lines);

        let innerHtml = '<div style="font-weight: bold; margin-bottom: 6px; border-bottom: 1px solid rgba(255,255,255,0.2); padding-bottom: 4px;">';
        titleLines.forEach(title => {
            innerHtml += `<span>Week of ${title}</span>`;
        });
        innerHtml += '</div><div style="font-size: 13px;">';

        bodyLines.forEach((body, i) => {
            const colors = tooltip.labelColors[i];
            const rawString = body[0];
            const numberOnly = rawString.includes(':') ? rawString.split(':')[1].trim() : rawString;
            const style = `
                display: inline-block;
                background: ${colors.borderColor};
                border-color: ${colors.borderColor};
                border-width: 2px;
                width: 10px;
                height: 10px;
                margin-right: 8px;
                border-radius: 50%;
            `;
            const indicator = `<span style="${style}"></span>`;
            innerHtml += `<div style="margin-bottom: 4px; display: flex; align-items: center; font-size: 14px;">${indicator}${numberOnly}</div>`;
        });
        innerHtml += '</div>';

        tooltipEl.innerHTML = innerHtml;
    }

    const {offsetLeft: positionX, offsetTop: positionY} = chart.canvas;

    tooltipEl.style.opacity = 1;
    tooltipEl.style.left = positionX + tooltip.caretX + 'px';
    tooltipEl.style.top = positionY + tooltip.caretY + 'px';

    tooltipEl.style.transform = 'translate(-50%, -105%)';
}

function renderBarChart(labels, values, valueConverter, label) {
    const canvas = document.getElementById('chartCanvas');
    if (!canvas) return;

    const container = document.getElementById('chartContainer');
    canvas.width = container.clientWidth;
    container.style.overflowY = 'auto';

    const barHeight = 30;
    const barSpacing = 10;
    canvas.height = labels.length * (barHeight + barSpacing);

    const min = Math.min(...values);
    const max = Math.max(...values);
    const highlightIndex = 0;

    const backgroundColors = values.map((v, i) =>
        i === highlightIndex
            ? 'rgba(54, 162, 235, 0.4)'
            : getColorForValue(v, min, max)
    );

    const convertedValues = valueConverter ? values.map(valueConverter) : values;

    return new Chart(canvas, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                label,
                data: convertedValues,
                backgroundColor: backgroundColors,
                borderWidth: 2,
                borderRadius: 20,
                barThickness: 20,
                maxBarThickness: 30
            }]
        },
        options: {
            indexAxis: 'y',
            responsive: false,
            maintainAspectRatio: false,
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
                },
                y: {
                    ticks: {
                        autoSkip: false
                    }
                }
            },
            onClick: (e, elements) => {
                if (elements.length > 0) {
                    const index = elements[0].index;
                    const initialDateString = labels[index];
                    const {from, to} = getActivitySearchDates(initialDateString);
                    window.location.href = `/activity/local?from=${from}&to=${to}`;
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

    const from = new Date(year, monthIndex, parseInt(day, 10), 0, 0, 0);
    if (isNaN(from.getTime())) {
        return "Invalid Date";
    }

    const to = new Date(from);
    to.setDate(to.getDate() + 6);
    to.setHours(23, 59, 0, 0);

    return {
        from: formatDateLocalISO(from),
        to: formatDateLocalISO(to)
    };
}


function formatDateLocalISO(date) {
    return date.getFullYear() +
        '-' + String(date.getMonth() + 1).padStart(2, '0') +
        '-' + String(date.getDate()).padStart(2, '0') +
        'T' + String(date.getHours()).padStart(2, '0') +
        ':' + String(date.getMinutes()).padStart(2, '0');
}


function renderActivityTypeChart(activities) {
    const typeCounts = activities.reduce((acc, activity) => {
        acc[activity.sportType] = (acc[activity.sportType] || 0) + 1;
        return acc;
    }, {});

    const activityColors = {
        'VirtualRide': '#4CAF50',
        'Ride': '#2196F3',
        'Run': '#f6db2a',
        'Swim': '#FF9800',
        'Walk': '#E91E63',
        'Hike': '#9C27B0',
        'WeightTraining': '#FF5722',
    }

    const labels = Object.keys(typeCounts);
    const values = Object.values(typeCounts);
    const backgroundColors = labels.map(label => activityColors[label] || '#CCCCCC');

    const ctx = document.getElementById('activityTypeChart').getContext('2d');

    if (activityTypeChartInstance) {
        activityTypeChartInstance.destroy();
    }

    activityTypeChartInstance = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: backgroundColors,
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
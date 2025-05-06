function renderSufferScoreChart(labels, values) {
    const ctx = document.getElementById('sufferScoreChart');
    if (!ctx) return;

    new Chart(ctx, {
    type: 'bar',
    data: {
    labels: labels,
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


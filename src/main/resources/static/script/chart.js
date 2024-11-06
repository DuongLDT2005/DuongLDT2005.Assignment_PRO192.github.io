window.onload = function () {
    const revenue = parseFloat(document.getElementById("revenueValue").innerText); 
    const ctx = document.getElementById('myChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['REVENUE'],
            datasets: [{
                label: 'vnd',
                data: [revenue],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
};


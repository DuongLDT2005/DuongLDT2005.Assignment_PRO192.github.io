window.onload = function () {
    const revenue = parseFloat(document.getElementById("revenueValue").innerText); // lấy giá trị revenue từ HTML
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

// const revenueElement = document.getElementById("revenueData");
// console.log(revenueElement.innerText); // Kiểm tra JSON nhận được
// console.log(labels);
// window.onload = function () {
//     // const rawData = /*[[${revenueJson}]]*/ '[]';

//     try {
//         const revenues = JSON.parse(revenueData.innerText);

//         // Kiểm tra dữ liệu sau khi phân tích JSON
//         console.log("Parsed Revenue Data: ", revenues);

//         const labels = revenues.map(item => item.date);
//         const data = revenues.map(item => item.revenue);
        
//         // Kiểm tra nhãn và dữ liệu
//         console.log("Labels: ", labels);
//         console.log("Data: ", data);

//         const ctx = document.getElementById('myChart').getContext('2d');
//         new Chart(ctx, {
//             type: 'bar',
//             data: {
//                 labels: labels,
//                 datasets: [{
//                     label: 'Doanh thu (VND)',
//                     data: data,
//                     backgroundColor: 'rgba(75, 192, 192, 0.2)',
//                     borderColor: 'rgba(75, 192, 192, 1)',
//                     borderWidth: 1
//                 }]
//             },
//             options: {
//                 scales: {
//                     y: {
//                         beginAtZero: true
//                     }
//                 }
//             }
//         });
//     } catch (error) {
//         console.error("Error parsing JSON:", error);
//     }
// };
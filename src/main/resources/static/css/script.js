
// function additeminlist() {

//     const button = document.getElementById("menu-item1");
//     const para = document.createElement("form");
//     const productId = 1;
//     const response = await fetch(`/api/product?id=${productId}`);
//     const product = await response.json();
//     para.innerHTML = `
//                 <p>Tên sản phẩm: <span> ${product.name} </span>, 
//                 Số lượng: <span> ${product.quantity} </span>, 
//                 Giá: <span> ${product.price} </span></p>
//         ` ;
//     button.addEventListener('click', function () {
//         document.getElementById("orderList").appendChild(para);
//     })
// };

function additeminlist() {
    const button = document.getElementById("menu-item1");

    // Add an event listener to the button
    button.addEventListener('click', async function () {
        try {
            const response = await fetch(`/addOrderItem`);

            // Check if the response is OK (status 200-299)
            if (!response.ok) {
                throw new Error(`Failed to fetch product: ${response.status}`);
            }

            const product = await response.json(); // Parse the JSON response

            // Create the form element dynamically
            const para = document.createElement("form");
            para.innerHTML = `
                <p>Tên sản phẩm: <span>${product.name}</span>, 
                Số lượng: <span>${product.quantity}</span>, 
                Giá: <span>${product.price}</span></p>
            `;

            // Append the form to the order list
            document.getElementById("orderList").appendChild(para);
        } catch (error) {
            console.error('Error fetching product data:', error);
        }
    });
}


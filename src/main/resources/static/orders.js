const apiUrl = "http://localhost:8080/orders";

function loadOrders() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(orders => {
            const list = document.getElementById("order-list");
            list.innerHTML = "";
            orders.forEach(o => {
                const li = document.createElement("li");
                // Klik på en ordre sender brugeren videre til order.html med ordrenummeret i URL'en
                li.innerHTML = `<a href="order.html?id=${o.id}">Ordre #${o.id} — ${o.supplier.navn}</a>`;
                list.appendChild(li);
            });
        });
}

loadOrders();

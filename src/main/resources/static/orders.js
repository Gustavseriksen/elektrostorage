const apiUrl = "http://localhost:8080/orders";

function loadOrders() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(orders => {
            const list = document.getElementById("order-list");
            list.innerHTML = "";
            if (orders.length === 0) {
                const li = document.createElement("li");
                li.className = "list-group-item text-muted fst-italic";
                li.textContent = "Ingen aktive bestillinger";
                list.appendChild(li);
                return;
            }
            orders.forEach(o => {
                const li = document.createElement("li");
                li.className = "list-group-item d-flex justify-content-between align-items-center";

                const link = document.createElement("a");
                link.href = `order.html?id=${o.id}`;
                link.className = "text-decoration-none";
                link.textContent = `Ordre #${o.id} — ${o.supplier.navn}`;
                li.appendChild(link);

                const badge = document.createElement("span");
                badge.className = "badge " + (o.sendtDato ? "bg-warning text-dark" : "bg-secondary");
                badge.textContent = o.sendtDato ? "Sendt" : "Under formulering";
                li.appendChild(badge);

                list.appendChild(li);
            });
        });
}

function loadSuppliers() {
    fetch("http://localhost:8080/suppliers")
        .then(res => res.json())
        .then(suppliers => {
            const select = document.getElementById("supplier-select");
            suppliers.forEach(s => {
                const option = document.createElement("option");
                option.value = s.id;
                option.textContent = s.navn;
                select.appendChild(option);
            });
        });
}

document.getElementById("create-form").addEventListener("submit", e => {
    e.preventDefault();
    const supplierId = document.getElementById("supplier-select").value;
    const trackingKode = document.getElementById("trackingKode").value || null;
    const forventetLeveringsDato = document.getElementById("forventetLeveringsDato").value || null;

    const body = {
        supplier: { id: parseInt(supplierId, 10) },
        trackingKode: trackingKode,
        forventetLeveringsDato: forventetLeveringsDato
    };

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    })
    .then(res => res.json())
    .then(order => {
        // Hop direkte til den nye ordre så man kan tilføje komponenter
        window.location.href = `order.html?id=${order.id}`;
    });
});

loadOrders();
loadSuppliers();

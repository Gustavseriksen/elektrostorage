const apiUrl = "http://localhost:8080/inventory";

// Henter og viser alle modtagne komponenter
function loadInventory() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(lines => {
            const list = document.getElementById("inventory-list");
            list.innerHTML = "";
            lines.forEach(l => {
                const li = document.createElement("li");
                li.textContent = `${l.component.internNummer} — ${l.component.eksterntVarenummer} — ${l.antal} stk (ordre #${l.order.id})`;
                list.appendChild(li);
            });
        });
}

// Fylder dropdown med alle komponenter
function loadComponents() {
    fetch("http://localhost:8080/components")
        .then(res => res.json())
        .then(components => {
            const select = document.getElementById("component-select");
            components.forEach(c => {
                const option = document.createElement("option");
                option.value = c.id;
                option.textContent = `${c.internNummer} — ${c.eksterntVarenummer}`;
                select.appendChild(option);
            });
        });
}

// Sender en manuel optælling til backend
document.getElementById("count-form").addEventListener("submit", e => {
    e.preventDefault();
    const body = {
        component: { id: document.getElementById("component-select").value },
        antal: parseInt(document.getElementById("antal").value),
        hvem: document.getElementById("hvem").value
    };
    fetch(`${apiUrl}/count`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    }).then(() => {
        document.getElementById("count-form").reset();
        alert("Optælling gemt!");
    });
});

loadInventory();
loadComponents();

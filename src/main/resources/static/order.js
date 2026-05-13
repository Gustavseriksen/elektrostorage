const params = new URLSearchParams(window.location.search);
const orderId = params.get("id");
const apiUrl = "http://localhost:8080/orders";

// Henter ordredetaljer og viser dem på siden
function loadOrder() {
    fetch(`${apiUrl}/${orderId}`)
        .then(res => res.json())
        .then(order => {
            document.getElementById("ordre-titel").textContent = `Ordre #${order.id}`;
            document.getElementById("ordre-info").textContent =
                `Leverandør: ${order.supplier.navn} | Sendt: ${order.sendtDato ?? "Ikke sendt endnu"}`;

            // Skjul tilføj-formular og send-knap hvis ordren allerede er sendt
            if (order.sendtDato !== null) {
                document.getElementById("tilfoej-sektion").style.display = "none";
                document.getElementById("send-knap").style.display = "none";
            }
        });
}

// Henter og viser alle linjer (komponenter) på ordren
function loadLines() {
    fetch(`${apiUrl}/${orderId}/lines`)
        .then(res => res.json())
        .then(lines => {
            const list = document.getElementById("line-list");
            list.innerHTML = "";
            lines.forEach(l => {
                const li = document.createElement("li");
                li.textContent = `${l.component.internNummer} — ${l.antal} stk`;
                list.appendChild(li);
            });
        });
}

// Fylder dropdown med alle tilgængelige komponenter
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

function addLine() {
    const componentId = document.getElementById("component-select").value;
    const antal = document.getElementById("antal").value;

    fetch(`${apiUrl}/${orderId}/lines?componentId=${componentId}&antal=${antal}`, {
        method: "POST"
    }).then(() => loadLines());
}

function markerSendt() {
    if (confirm("Er du sikker på at du vil markere denne ordre som sendt?")) {
        fetch(`${apiUrl}/${orderId}/send`, { method: "PUT" })
            .then(() => {
                // Genindlæs siden så formular og knap skjules
                location.reload();
            });
    }
}

loadOrder();
loadLines();
loadComponents();

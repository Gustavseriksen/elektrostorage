const params = new URLSearchParams(window.location.search);
const orderId = params.get("id");
const apiUrl = "http://localhost:8080/orders";

function loadOrder() {
    fetch(`${apiUrl}/${orderId}`)
        .then(res => res.json())
        .then(order => {
            document.getElementById("ordre-titel").textContent = `Ordre #${order.id}`;
            document.getElementById("ordre-info").textContent =
                `Leverandør: ${order.supplier.navn} | Sendt: ${order.sendtDato ?? "Ikke sendt endnu"} | Modtaget: ${order.modtagetDato ?? "Ikke modtaget"}`;

            // Skjul tilføj-formular og send-knap hvis ordren allerede er sendt
            if (order.sendtDato !== null) {
                document.getElementById("tilfoej-sektion").style.display = "none";
                document.getElementById("send-knap").style.display = "none";
            }
            // Vis modtag-knap hvis ordren er sendt men endnu ikke modtaget
            if (order.sendtDato !== null && order.modtagetDato === null) {
                document.getElementById("modtag-knap").style.display = "inline-block";
            }
        });
}

function loadLines() {
    fetch(`${apiUrl}/${orderId}/lines`)
        .then(res => res.json())
        .then(lines => {
            const list = document.getElementById("line-list");
            list.innerHTML = "";
            lines.forEach(l => {
                const li = document.createElement("li");
                li.className = "list-group-item";
                const ekstern = l.component.eksterntVarenummer ?? "(samles)";
                li.textContent = `#${l.component.internNummer} — ${ekstern} — ${l.antal} stk`;
                list.appendChild(li);
            });
        });
}

function loadComponents() {
    fetch("http://localhost:8080/components")
        .then(res => res.json())
        .then(components => {
            const select = document.getElementById("component-select");
            components.forEach(c => {
                // Komponenter der kun skal samles skal ikke kunne bestilles
                if (c.skalSamles) return;
                const option = document.createElement("option");
                option.value = c.id;
                option.textContent = `#${c.internNummer} — ${c.eksterntVarenummer ?? "(samles)"}`;
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

function markerModtaget() {
    if (confirm("Er du sikker på at du vil markere denne ordre som modtaget?")) {
        fetch(`${apiUrl}/${orderId}/modtag`, { method: "PUT" })
            .then(() => location.reload());
    }
}

loadOrder();
loadLines();
loadComponents();

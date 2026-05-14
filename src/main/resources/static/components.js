const apiUrl = "http://localhost:8080/components";

function loadComponents() {
    // Hent både komponenter og styklister, så vi kan vise styklistens navn
    // for samlede komponenter i stedet for et tomt eksterntVarenummer
    Promise.all([
        fetch(apiUrl).then(res => res.json()),
        fetch("http://localhost:8080/assemblies").then(res => res.json())
    ]).then(([components, assemblies]) => {
        // Map fra resulterende komponentId → styklistens navn
        const assemblyNameByResultId = {};
        assemblies.forEach(a => {
            if (a.resulteresI) {
                assemblyNameByResultId[a.resulteresI.id] = a.navn;
            }
        });

        const container = document.getElementById("component-list");
        container.innerHTML = "";

        const card = document.createElement("div");
        card.className = "card";

        const header = document.createElement("div");
        header.className = "card-header";
        header.innerHTML = `<strong>Alle komponenter</strong>`;
        card.appendChild(header);

        const ul = document.createElement("ul");
        ul.className = "list-group list-group-flush";

        components.forEach(c => {
            const li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center";

            const text = document.createElement("span");
            // Samlede komponenter har intet eksterntVarenummer — vis styklistens navn i stedet
            let displayName;
            if (c.skalSamles) {
                displayName = assemblyNameByResultId[c.id] ?? "Samlet komponent";
            } else {
                displayName = c.eksterntVarenummer ?? "";
            }
            text.textContent = `#${c.internNummer} — ${displayName}`;
            li.appendChild(text);

            const badges = document.createElement("span");

            if (c.skalSamles) {
                const samletBadge = document.createElement("span");
                samletBadge.className = "badge bg-info me-2";
                samletBadge.textContent = "Samlet komponent";
                badges.appendChild(samletBadge);
            }

            if (c.udgaaet) {
                const badge = document.createElement("span");
                badge.className = "badge bg-secondary";
                badge.textContent = "Udgået";
                badges.appendChild(badge);
            } else {
                const btn = document.createElement("button");
                btn.className = "btn btn-sm btn-outline-danger";
                btn.textContent = "Marker udgået";
                btn.onclick = () => markerUdgaaet(c.id);
                badges.appendChild(btn);
            }

            li.appendChild(badges);
            ul.appendChild(li);
        });

        card.appendChild(ul);
        container.appendChild(card);
    });
}

document.getElementById("create-form").addEventListener("submit", e => {
    e.preventDefault();
    const body = {
        internNummer: parseInt(document.getElementById("internNummer").value, 10),
        eksterntVarenummer: document.getElementById("eksterntVarenummer").value,
        udgaaet: false,
        skalSamles: false
    };
    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    }).then(() => {
        document.getElementById("create-form").reset();
        loadComponents();
    });
});

function markerUdgaaet(id) {
    if (confirm("Er du sikker på at du vil markere denne komponent som udgået?")) {
        fetch(`${apiUrl}/${id}/udgaaet`, { method: "PUT" })
            .then(() => loadComponents());
    }
}

loadComponents();

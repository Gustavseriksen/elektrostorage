const apiUrl = "http://localhost:8080/assemblies";

function loadAssemblies() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(assemblies => {
            const container = document.getElementById("assembly-list");
            container.innerHTML = "";

            // For hver stykliste hentes dens linjer i et separat kald
            assemblies.forEach(a => {
                const card = document.createElement("div");
                card.className = "card mb-3";
                card.innerHTML = `
                    <div class="card-header">
                        <strong>${a.navn}</strong>
                        <div class="small text-muted">Producerer 1 stk. komponent #${a.resulteresI.internNummer}</div>
                    </div>
                    <div class="card-body pb-0">
                        <p class="mb-2">Du skal bruge følgende komponenter for at samle én:</p>
                    </div>
                `;

                const ul = document.createElement("ul");
                ul.className = "list-group list-group-flush";

                fetch(`${apiUrl}/${a.id}/lines`)
                    .then(res => res.json())
                    .then(lines => {
                        lines.forEach(l => {
                            const li = document.createElement("li");
                            li.className = "list-group-item d-flex justify-content-between";

                            const tekst = document.createElement("span");
                            const ekstern = l.component.eksterntVarenummer ?? "(samles)";
                            tekst.textContent = `#${l.component.internNummer} — ${ekstern}`;
                            li.appendChild(tekst);

                            const antal = document.createElement("span");
                            antal.className = "badge bg-primary";
                            antal.textContent = `${l.antal} stk`;
                            li.appendChild(antal);

                            ul.appendChild(li);
                        });
                    });

                card.appendChild(ul);
                container.appendChild(card);
            });
        });
}

loadAssemblies();

const apiUrl = "http://localhost:8080/assemblies";

// Henter alle styklister og deres komponenter
function loadAssemblies() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(assemblies => {
            const container = document.getElementById("assembly-list");
            container.innerHTML = "";

            // For hver stykliste hentes dens linjer i et separat kald
            assemblies.forEach(a => {
                const div = document.createElement("div");
                div.innerHTML = `<h2>${a.navn} → resulterer i ${a.resulteresI.internNummer}</h2>`;

                fetch(`${apiUrl}/${a.id}/lines`)
                    .then(res => res.json())
                    .then(lines => {
                        const ul = document.createElement("ul");
                        lines.forEach(l => {
                            const li = document.createElement("li");
                            li.textContent = `${l.component.internNummer} — ${l.component.eksterntVarenummer} — ${l.antal} stk`;
                            ul.appendChild(li);
                        });
                        div.appendChild(ul);
                    });

                container.appendChild(div);
            });
        });
}

loadAssemblies();

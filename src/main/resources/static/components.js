const apiUrl = "http://localhost:8080/components";

function loadComponents() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(components => {
            const list = document.getElementById("component-list");
            list.innerHTML = "";
            components.forEach(c => {
                const li = document.createElement("li");
                li.textContent = `${c.internNummer} - ${c.eksterntVarenummer} ${c.udgaaet ? "(udgået)" : ""}`;

                if (!c.udgaaet) {
                    const btn = document.createElement("button");
                    btn.textContent = "Marker udgået";
                    btn.onclick = () => markerUdgaaet(c.id);
                    li.appendChild(btn);
                }

                list.appendChild(li);
            });
        });
}

document.getElementById("create-form").addEventListener("submit", e => {
    e.preventDefault();
    const body = {
        internNummer: document.getElementById("internNummer").value,
        eksterntVarenummer: document.getElementById("eksterntVarenummer").value,
        udgaaet: false
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

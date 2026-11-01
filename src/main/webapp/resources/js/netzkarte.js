(() => {
    "use strict";

    const bereich = document.getElementById("kartenbereich");
    const hinweis = document.getElementById("kartenhinweis");

    if (!bereich || !hinweis) {
        return;
    }

    let karte;

    function initialisieren() {
        if (!window.L) {
            hinweis.textContent =
                "Die Kartenbibliothek konnte nicht geladen werden.";
            return;
        }

        karte = L.map("netzkarte").setView([20, 0], 2);

        const kacheln = L.tileLayer(
            "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            {
                maxZoom: 19,
                attribution:
                    '&copy; <a href="https://www.openstreetmap.org/copyright">'
                    + "OpenStreetMap</a> contributors"
            }
        ).addTo(karte);

        kacheln.on("tileerror", () => {
            hinweis.textContent =
                "Kartenhintergrund konnte nicht vollständig geladen werden.";
        });

        const positionen = [];
        const flaechen = L.layerGroup();

        document.querySelectorAll(".karten-netz").forEach(element => {
            const lesen = klasse =>
                element.querySelector(klasse).textContent.trim();

            const breite = Number(lesen(".karten-breite"));
            const laenge = Number(lesen(".karten-laenge"));
            const flaeche = Number(lesen(".karten-flaeche"));
            const status = lesen(".karten-status");

            if (
                !Number.isFinite(breite)
                || !Number.isFinite(laenge)
                || Math.abs(breite) > 90
                || Math.abs(laenge) > 180
            ) {
                return;
            }

            const position = [breite, laenge];
            const farbe = status === "GEMELDET"
                ? "#2b647c"
                : "#2d7765";

            positionen.push(position);

            const marker = L.circleMarker(position, {
                radius: 8,
                color: "#ffffff",
                weight: 2,
                fillColor: farbe,
                fillOpacity: 1
            }).addTo(karte);

            const popup = document.createElement("div");

            const titel = document.createElement("strong");
            titel.textContent = lesen(".karten-statustext");
            popup.append(titel);

            const groesse = document.createElement("p");
            groesse.textContent = `Geschätzte Fläche: ${flaeche} m²`;
            popup.append(groesse);

            const details = document.createElement("button");
            details.type = "button";
            details.textContent = "Netzdetails öffnen";

            details.addEventListener("click", () => {
                element.querySelector(".karten-detailaktion").click();
            });

            popup.append(details);
            marker.bindPopup(popup);

            if (Number.isFinite(flaeche) && flaeche > 0) {
                L.circle(position, {
                    radius: Math.sqrt(flaeche / Math.PI),
                    color: farbe,
                    weight: 2,
                    fillColor: farbe,
                    fillOpacity: 0.2,
                    interactive: false
                }).addTo(flaechen);
            }
        });

        function flaechenAnzeigen() {
            if (karte.getZoom() >= 8) {
                if (!karte.hasLayer(flaechen)) {
                    flaechen.addTo(karte);
                }
            } else if (karte.hasLayer(flaechen)) {
                karte.removeLayer(flaechen);
            }
        }

        karte.on("zoomend", flaechenAnzeigen);

        if (positionen.length > 0) {
            karte.fitBounds(L.latLngBounds(positionen), {
                padding: [30, 30],
                maxZoom: 16
            });
        } else {
            hinweis.textContent =
                "Für diesen Filter sind keine offenen Netze vorhanden.";
        }

        flaechenAnzeigen();
    }

    bereich.addEventListener("toggle", () => {
        if (!bereich.open) {
            return;
        }

        if (!karte) {
            initialisieren();
        }

        if (karte) {
            requestAnimationFrame(() => karte.invalidateSize());
        }
    });

    if (bereich.open && !karte) {
        initialisieren();
    }
})();
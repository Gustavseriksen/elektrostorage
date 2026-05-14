# ElektroStorage

Et lagerstyringssystem til en elektronikvirksomhed, bygget med Spring Boot og vanilla JavaScript.

## Sådan kører du projektet

1. Klon repositoriet
2. Åbn projektet i VSCode (eller IntelliJ)
3. Kør `ElektrostorageApplication.java`
4. Åbn browseren på [http://localhost:8080](http://localhost:8080)
5. H2-databasekonsol: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:elektrostorage`
   - Brugernavn: `sa` (intet kodeord)

## Endpoints

### Komponenter
| Metode | URL | Beskrivelse |
|--------|-----|-------------|
| GET | `/components` | Hent alle komponenter |
| POST | `/components` | Opret ny komponent |
| PUT | `/components/{id}/udgaaet` | Marker komponent som udgået |

### Bestillinger
| Metode | URL | Beskrivelse |
|--------|-----|-------------|
| GET | `/orders` | Hent aktive bestillinger (ikke modtagne) |
| GET | `/orders/{id}` | Hent én bestilling |
| GET | `/orders/{id}/lines` | Hent linjer på en bestilling |
| POST | `/orders` | Opret ny bestilling (under formulering) |
| POST | `/orders/{id}/lines` | Tilføj komponent til bestilling |
| PUT | `/orders/{id}/send` | Marker bestilling som sendt |
| PUT | `/orders/{id}/modtag` | Marker bestilling som modtaget |

### Inventar
| Metode | URL | Beskrivelse |
|--------|-----|-------------|
| GET | `/inventory` | Hent modtagne komponenter |
| POST | `/inventory/count` | Indsend manuel optælling |
| GET | `/inventory/counts` | Hent alle indsendte optællinger |

### Styklister
| Metode | URL | Beskrivelse |
|--------|-----|-------------|
| GET | `/assemblies` | Hent alle styklister |
| GET | `/assemblies/{id}` | Hent én stykliste |
| GET | `/assemblies/{id}/lines` | Hent komponenter på stykliste |

### Leverandører
| Metode | URL | Beskrivelse |
|--------|-----|-------------|
| GET | `/suppliers` | Hent alle leverandører |

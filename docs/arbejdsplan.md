# ARBEJDSPLAN - ElektroStorage (24 timer)
***

## FASE 0: Opsætning
 
- [x] Opret Spring Boot projekt på start.spring.io
    - Dependencies: Spring Web, Spring Data JPA, H2, Lombok
- [x] Åbn projektet i VSCode
- [x] Opret `docs/` mappe og læg `opgave_oversigt.md`, `opgave_beskrivelse.md` og `arbejdsplan.md` ind
- [x] Tjek at projektet kører (kør main-metoden)
- [x] Opret GitHub repository
- [x] Push initial commit
- [x] Opsæt `application.properties` med H2
- [x] Test at H2 console virker (http://localhost:8080/h2-console)
- [x] Opret folder-struktur: `model`, `repository`, `service`, `controller`, `dto`, `config`


## FASE 1: Komponenter
 
**Mål:** Få EN feature til at virke hele vejen igennem først. Det giver et komplet eksempel at bygge resten over.
 
### Backend
- [x] Opret `Supplier` entity (id, navn, adresse)
- [x] Opret `Component` entity (id, internt nummer, eksternt varenummer, supplier-reference, udgået-boolean)
- [x] Opret `SupplierRepository`
- [x] Opret `ComponentRepository`
- [x] Opret `ComponentController` med endpoints:
    - `GET /components` (liste alle)
    - `POST /components` (tilføj ny)
    - `PUT /components/{id}/udgaaet` (marker udgået)
- [x] Test endpoints i Postman
- [x] **COMMIT + PUSH**

### Frontend
- [ ] Opret `index.html` eller `components.html` i `static/`
- [ ] Lav simpel CSS (kan være meget basal)
- [ ] Evt. lave simple styling med bootstrap
- [ ] JavaScript: hent komponenter med `fetch()` og vis i liste
- [ ] JavaScript: formular til at tilføje ny komponent
- [ ] JavaScript: knap der markerer komponent som udgået med "Er du sikker?"-dialog (`confirm()`)
- [ ] Test at det hele virker i browseren
- [ ] **COMMIT + PUSH**

 
## FASE 2: Resten af datamodellen
 
Nu hvor du har en feature der virker, byg resten af entiteterne.
 
- [ ] Opret `Order` entity (med supplier, tracking-kode, sendt-dato, forventet leveringsdato, modtaget-dato)
- [ ] Opret `OrderLine` entity (order, component, antal)
- [ ] Opret `Assembly` entity (resulterer i en component)
- [ ] Opret `AssemblyLine` entity (assembly, component, antal)
- [ ] Opret `InventoryCount` entity (component, antal, hvem, dato)
- [ ] Opret repositories for alle entiteter
- [ ] Tjek at databasen oprettes korrekt (kig i H2 console)
- [ ] **COMMIT + PUSH**

 
## FASE 3: DataSeeder med startdata
 
- [ ] Opret `DataSeeder`-klasse (med `CommandLineRunner` eller `@PostConstruct`)
- [ ] Indsæt 2-3 leverandører
- [ ] Indsæt 10 komponenter (opdigtede navne)
- [ ] Indsæt komponenter til "Lysende LED" (LED, modstand, batteriholder, 9V batteri)
- [ ] Indsæt 1 aktiv bestilling (10 stk af 3 komponenter, ikke leveret)
- [ ] Indsæt 1 færdig bestilling (100 stk af 1 komponent, gennemført)
- [ ] Indsæt stykliste "Lysende LED" med de 4 komponenter
- [ ] Test at data findes i H2 console
- [ ] **COMMIT + PUSH**

 
## FASE 4: Orders
 
### Backend
- [ ] `OrderController` med endpoints:
    - `GET /orders` (liste alle, eller filter ikke-modtagne)
    - `GET /orders/{id}` (hent en)
    - `POST /orders` (opret ny tom bestilling)
    - `POST /orders/{id}/lines` (tilføj komponent til ordre)
    - `PUT /orders/{id}/send` (marker som sendt)
- [ ] Logik: kan ikke tilføje til en sendt bestilling (returner 400 Bad Request)
- [ ] Test i Postman
- [ ] **COMMIT + PUSH**

### Frontend
- [ ] `orders.html`: liste over ikke-modtagne ordrer
- [ ] Klik på en ordre → gå til `order.html?id=X`
- [ ] `order.html`: vis ordre + formular til at tilføje komponenter (kun hvis ikke sendt)
- [ ] Knap til at markere ordre som sendt
- [ ] Test i browseren
- [ ] **COMMIT + PUSH**

 
## FASE 5: Inventar
 
### Backend
- [ ] `InventoryController` med endpoints:
    - `GET /inventory` (komponenter modtaget via bestillinger)
    - `POST /inventory/count` (indsend en optælling)
- [ ] Test i Postman

### Frontend
- [ ] `inventory.html`: liste over modtagne komponenter
- [ ] Evt. formular til at indsende optælling
- [ ] **COMMIT + PUSH**

 
## FASE 6: Styklister/Assemblies
 
### Backend
- [ ] `AssemblyController` med endpoints:
    - `GET /assemblies` (liste styklister)
    - `GET /assemblies/{id}` (en stykliste med komponenter)
- [ ] Test i Postman

### Frontend
- [ ] `assemblies.html`: liste styklister med komponenter og antal
- [ ] **COMMIT + PUSH**

 
## FASE 7: JUnit tests
 
- [ ] Test `ComponentController` (mindst GET og POST)
- [ ] Test `OrderController` (især logik om sendt-status)
- [ ] Test `InventoryController`
- [ ] Test `AssemblyController`
- [ ] Kør alle tests og tjek de er grønne
- [ ] **COMMIT + PUSH**

 
## FASE 8: Polering og aflevering
 
- [ ] Lav en navigation/forside så man kan hoppe mellem siderne
- [ ] Ryd op i kode, fjern udkommenteret/død kode
- [ ] Tjek at alt stadig virker efter oprydning
- [ ] Lav `README.md` med:
    - Kort projekt-beskrivelse
    - Hvordan man kører projektet
    - Hvilke endpoints der findes
- [ ] **FINAL COMMIT + PUSH**
- [ ] Skriv Wiseflow-dokumentet:
    - Fulde navn + KEA-email
    - Link til GitHub repo
    - 5-15 linjers beskrivelse af hvor langt du er kommet
    - Nævn evt. mangler ærligt
- [ ] Aflever på Wiseflow

 
## ⚠️ HUSKEREGLER UNDER HELE PROJEKTET
 
- Push til GitHub **HVER GANG** en feature virker (ikke kun til sidst)
- Lav hellere **FÆRRE** ting der virker end **MANGE** der er halvfærdige
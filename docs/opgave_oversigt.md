# 24 timers Eksamens projekt
 
Du skal i dette eksamensprojekt bygge en simpel full-stack REST web-applikation. Opgavens tema er ElektroStorage og består af en front-end og en back-end.
 
## Krav
- **Back-end:** Java og Spring Boot
- **Front-end:** HTML/CSS og JavaScript
- **Database:** H2 og/eller MySQL
- **Test:** JUnit tests af back-end

## Eksamensopgaven består af 3 separate delopgaver
1. Database med JPA
2. Byg et REST API
3. Front-end med HTML/CSS og JavaScript


## 1. Database med JPA
 
Modellér problemdomænet ElektroStorage (lagerstyring til elektronikproduktion) ved hjælp af JPA-klasser.
 
### Entiteter der skal modelleres
 
**Komponent**
- Internt nummer (tal)
- Leverandør (reference)
- Eksternt varenummer (tekst, kan indeholde bogstaver og bindestreger)
- Kan markeres som udgået (kan så ikke længere bestilles)

**Leverandør**
- Navn
- Adresse

**Bestilling**
- Leverandør (reference)
- Komponenter med antal af hver
- Tracking-kode
- Dato for hvornår bestillingen er sendt
- Forventet leveringsdato
- Dato for modtagelse (hvis modtaget)

**Stykliste**
- Liste af komponenter med antal af hver
- Resulterer i en ny komponent (som selv kan indgå i andre styklister)
- Nogle komponenter kan ikke bestilles, men skal samles via en stykliste

**Optælling**
- Hvilken komponent der er talt
- Antal talt
- Hvem der optalte

### Start-data til demonstration
- 2-3 leverandører
- 10 forskellige komponenter (opdigtede navne)
- 1 aktiv bestilling: 10 stk. af 3 forskellige komponenter (ikke leveret)
- 1 færdig bestilling: 100 stk. af 1 komponent (gennemført)
- 1 stykliste "Lysende LED" (er et samlesæt):
    - 1 stk. LED 5 mm, rød
    - 1 stk. modstand, 1 kOhm
    - 1 stk. batteriholder til 9 V batteri
    - 1 stk. 9 V batteri

(Komponenterne til samlesættet skal også findes i databasen)
 
 
## 2. Byg et REST API
 
Lav et REST API der understøtter kartotek, optælling og visning af styklister.
 
### Endpoints
 
**`/components` (kartotek)**
- Liste komponenter
- Tilføje komponent
- Markere komponent som udgået

**`/orders` (bestillinger)**
- Liste bestillinger
- Tilføje komponenter til en bestilling (kun før den er sendt)
- Markere bestilling som sendt
- Skal kunne skelne mellem bestillinger der er under oprettelse og sendte bestillinger (fx via datoer)
- Når en bestilling er markeret som sendt, kan der ikke tilføjes mere

**`/inventory` (lager)**
- Liste komponenter modtaget via bestillinger
- Indsende en optælling af en bestemt komponent

**`/assemblies` (styklister)**
- Vise styklister med komponenter og antal af hver

Der må gerne være under-endpoints til hver af dem — designet er op til dig.
 
### For hvert endpoint, overvej
- HTTP-metode (GET, POST, PUT, DELETE osv.)
- Request-parametre (fx ID'er)
- HTTP-statuskoder og fejlmeddelelser ved fejl

### Afprøvning
- Kald hvert endpoint mindst én gang under udviklingen
- Manuel test med en API-klient (fx Postman)
- Programmatisk test med JUnit

 
## 3. Front-end med HTML/CSS og JavaScript
 
Lav fem undersider med lister.
 
### Undersider
 
**Komponenter**
- Liste over alle komponenter
- Formular til at tilføje nye komponenter
- Knap til at markere en komponent som udgået (gerne med "Er du sikker?"-dialog)

**Ordrer (oversigt)**
- Liste over ordrer der endnu ikke er modtaget
- Hvis en ordre ikke er sendt, skal man kunne klikke sig ind på den og tilføje ting til bestillingen

**Ordre (enkelt)**
- Visning af én ordre som ikke er sendt
- Mulighed for at tilføje ting til ordren

**Inventar**
- Liste over komponenter der er modtaget

**Styklister**
- Liste over komponenter der skal til for at bygge en bestemt komponent

 
## ⚠️ Vigtigt
 
Det tæller mere at have en enkelt feature der virker hele vejen (JPA → backend → frontend → virkende knapper), end at alle fem undersider eksisterer halvt.

# 24 timers reeksamensprojekt
**Programmering 2, 3. semester KEA, E24.**
 
Denne reeksamensopgave udleveres: 13-05-2026 kl. 09:00  
Besvarelse skal afleveres senest: 14-05-2026 kl. 09:00
 
Du skal i dette eksamensprojekt bygge en simpel full-stack REST web-applikation. Opgavens tema er ElektroStorage og består af en front-end og en back-end. Back-end skal laves med brug af Spring Boot, H2 og/eller MySQL. Front-end skal laves med HTML/CSS og JavaScript.
 
Eksamensopgaven består af 3 separate delopgaver.

**Vigtigt:**
- Læs hele opgaveteksten før du går i gang, så du bedre ved hvor meget tid hver del tager.
- Det forventes at du under hele eksamensperioden arbejder alene på din besvarelse. Enhver deling af kode eller idéer med andre vil blive betragtet som eksamenssnyd.
- Når afleveringsfristen har passeret, er det ikke tilladt at push'e ændringer til GitHub. Sørg derfor for at aflevere løbende ved at commit'e og push'e løbende. Vent ikke til der er får minutter tilbage, før du afleverer noget som helst. Afprøv at aflevering virker.
- Det er tilladt at bruge AI-baserede hjælpeværktøjer som ChatGPT, Claude eller Copilot. Til den efterfølgende mundtlige prøve vil du dog blive bedt om at tilføje rettelser eller udvidelser til din kode, uden andre hjælpemidler end IntelliJ, så vær forberedt på det.
- Det tæller mere at have en feature der er lavet fra JPA til backend til frontend så knapperne virker, end at alle undersider eksisterer.
---
 
**Sådan afleverer du:**
- Til programmeringsdelen skal du lave et Spring Boot-projekt hvor du gør brug af Spring Web, JPA, MySQL og H2. Du kan også inkludere Thymeleaf for at distribuere dit HTML og JavaScript. Alle tre delopgaver kan løses inden for dette projekt, men du kan også lave et separat projekt til din frontend. Sørg for at git push'e til GitHub senest kl. 09:00 dagen efter du har modtaget opgaven. Programmeringsdelen er afleveret, når du har push'et den. Sørg for at aflevere både back-end med JUnit tests og front-end.
- Via Wiseflow skal du aflevere et enkelt dokument senest kl. 09:00 dagen efter du har modtaget opgaven med følgende oplysninger:
  - Dit fulde navn og KEA-email
  - Et link til dit GitHub repository
  - En kort beskrivelse (5-15 linjer) af hvor langt du er kommet med projektet. Hvis enkelte dele har åbenlyse mangler, må du gerne skrive det her. En åbenlys mangel kunne være noget der ikke er lavet færdigt, eller noget som forårsager at programmet fejler på uønskede tidspunkter. Du må også gerne skrive, i hvilken grad din back-end er afprøvet med unit tests, og hvordan du ville udvide dine tests, hvis du havde mere tid.
---
 
## Delopgave 1: Database med JPA
 
Modellér følgende problemdomæne ved hjælp af JPA-klasser:
 
ElektroStorage er en lille virksomhed, der laver inventarstyring til elektronikproduktion. De er blevet hyret af en virksomhed i Lyngby der laver deres egen elektronik, som har fået en stor ordre. Deres lager, som de har vedligeholdt med regneark, er nu blevet meget uoverskueligt. De kan ikke længere se om komponenter er på lager, om de er ved at løbe tør for noget, eller hvor mange enheder de har produceret.
 
Kunden har en række regneark, som de bruger til at holde styr på komponenter de har bestillt og produceret. Desværre er regnearkene rodede, fordi folk ikke opdaterer dem eller fordi de kommer til at overskrive hinanden på dele-drevet. Derfor vil de gerne have hjælp til at lave en app, som har en centralt styret database. Efter at undersøge problemdomænet, har du lært:
 
- Kunden har et kartotek over komponenter, de kan bestille hjem. Komponenter har et internt nummer, som altid er et tal, en leverandør, og et eksternt varenummer som leverandøren bestemmer, som også kan have bogstaver og bindestreger. En komponent kan også være markeret som udgået, hvorefter den ikke længere kan bestilles.
- Kunden har et kartotek over leverandører, som bare har et navn og en adresse.
- Kunden har også en liste over bestillinger: Den henviser til hvilken leverandør, der er bestilt fra, hvilke komponenter der er bestilt, en tracking-kode, hvor mange af hver komponent, der er bestilt, hvilken dato bestillingen er gjort endelig (markeret sendt), hvilken dato leveringen forventes, og om leveringen er modtaget (hvornår).
- Kunden har desuden noget som de kalder en stykliste, som de bruger til når de samler elektronik. En stykliste har en mængde af hver komponent som indgår, og hvis man følger en stykliste fremkommer en ny komponent, som også kan indgå i en stykliste. Derfor er nogle komponenter ikke mulige at bestille, men skal samles i stedet.
- Kunden har ikke et regneark til optælling, det foregår indtil videre på papir, men de vil gerne kunne lave optælling så det bliver gemt hvem som optæller og hvor mange af den komponent som er blevet talt der faktisk var.

Til demonstration må databasen gerne indeholde følgende fra start:
 
- 2-3 leverandører
- 10 forskellige komponenter, man kan bestille (bare lav tilfældige, opdigtede navne)
- 1 bestilling
- 1 aktiv bestilling på 10 stk. af 3 forskellige komponenter, som ikke er blevet leveret
- 1 færdig bestilling på 100 stk. af 1 komponent, som er blevet gennemført
- 1 stykliste for et samlesæt kaldet Lysende LED:
  - 1 stk. LED 5 mm, rød
  - 1 stk. modstand, 1 kOhm
  - 1 stk. batteriholder til 9 V batteri
  - 1 stk. 9 V batteri

Sørg for at komponenterne til samlesættet også eksisterer i databasen.
 
---
 
## Delopgave 2: Byg et REST API
 
ElektroStorage har mange idéer til hvad deres system med tiden skal kunne. Før de kommer for godt i gang, vil de gerne have at kartoteket virker, at man kan optælle varer, og at man kan vise styklisterne.
 
REST API'et må derfor gerne rumme følgende:
 
- `/components` (kartotek) hvor man kan liste, tilføje og markere komponenter udgået.
- `/orders` hvor man kan liste bestillinger, tilføje komponenter til dem, og markere dem som sendt. Man skal kunne skelne mellem en bestilling som er i gang med at blive formuleret, og en bestilling som er sendt afsted, fx ved at datoerne for bestilling er sat. Mens en bestilling er oprettet, og før den er sendt, skal man kunne tilføje komponenter og hvor mange af slagsen man ønsker. Når man markerer en bestilling som sendt, skal man ikke kunne tilføje til den længere.
- `/inventory` hvor man kan liste de komponenter som er modtaget via en bestilling. Man skal også kunne indsende en optælling af en bestemt komponent.
- `/assemblies` hvor man kan se styklister, deres komponenter og hvor mange af hver.

Der må gerne være under-endpoints til hver af dem, det er op til dig at finde et godt design.
 
For hvert endpoint:
- Afgør om endpoint'et skal benytte GET, POST, eller en af de andre REST-ful HTTP-metoder.
- Afgør hvilke request-parametre, hvis nogen, endpoint'et er bedst tjent med (fx ID'er).
- Overvej, hvis endpoint'et kan fejle, hvilken HTTP-statuskode, der passer, og hvad en god fejlmeddelelse ville være.

For at afprøve endpoint'et:
- Prøv at kalde hvert endpoint mindst én gang når du arbejder med opgaven
- Benyt en API-klient som fx Postman til manuel kontakt af endpointet
- Benyt JUnit til at afprøve endpoint'et programmatisk
---
 
## Delopgave 3: Front-end med HTML/CSS og JavaScript
 
ElektroStorage har fem forskellige undersider med lister:
 
- Siden som viser en liste af komponenter. Der må gerne være en formular der tilføjer nye komponenter, og en knap som markerer at en komponent er udgået. Knappen må gerne have en "Er du sikker?"-dialog.
- Siden som viser en liste af ordrer, der ikke er markeret som modtaget endnu. Hvis en ordre ikke er blevet sendt, skal man kunne trykke sig ind på ordren og tilføje ting til bestillingen.
- Siden som viser en enkelt ordre som ikke er blevet sendt, hvor man kan tilføje ting.
- Siden som viser inventar, dvs. komponenter der er modtaget.
- Siden som viser styklister, dvs. en liste af komponenter der skal til for at bygge en bestemt komponent.

Da kunden allerede har et system baseret på regneark der fungerer nogenlunde, er de mere interesseret i enkelte features der virker helt, end fem undersider der virker halvt. Det tæller altså mere at en feature er lavet fra JPA til backend til frontend så knapperne virker, end at alle fem undersider eksisterer.
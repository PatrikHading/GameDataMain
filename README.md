# GameData Project

## Projektbeskrivning

Detta projekt är en **webbapplikation** för att hantera spelare i ett sportlag. 
Användare kan lägga till, uppdatera, visa och ta bort spelare från systemet.
De tillagda spelarna används sedan för att lägga in i på  matchsidan, där det registreras antal
mål, assists och utvisningsminuter. Datan går sedan att spara till MySQL.
Det använder **Spring Boot** för backend och **Thymeleaf** för att rendera HTML-sidor på servern.


### Funktioner:
- **Lägg till spelare**: Användaren kan lägga till en spelare genom att ange förnamn, efternamn och tröjnummer.
- **Visa registrerade spelare**: Lista alla registrerade spelare.
- **Uppdatera spelare**: Användaren kan uppdatera en spelares information.
- **Ta bort spelare**: Möjlighet att ta bort en spelare från systemet.
- **Lägg till spelare i match**: Möjlighet att lägga till spelare i ett av två lag.
- **Lägg till statistik**: Användaren lägger in antalet mål, assists och utvisningsminuter på varje spelare.
- **Spara session**: När sessionen avslutas sparas den inskrivna datan till MySQL

---

## Teknologier och verktyg

- **Spring Boot**: Backend framework för att hantera API:er och affärslogik.
- **Thymeleaf**: Server-side templating engine för rendering av HTML-sidor.
- **My SQL**: Databas för att lagra spelar-, och matchdata.
- **Maven**: Byggverktyg för att hantera beroenden och bygga projektet.
- **Docker**: Används för containerisering.

## Länk till repository
**Github** https://github.com/PatrikHading/GameDataMain.git





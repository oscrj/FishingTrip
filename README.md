# FishingTrip
Projekt i Webbutveckling i Java (FishingTrip) 

Jag ska skapa en “FiskeDagbok” där du som användare ska kunna logga dina fiskepass med relevant information om vart, hur och när du fiskar. Under varje fiskepass kan du registrera varje fisk du fångar och spara information om denna fisken. Informationen kan vara vilken fiskart, längd, vikt, fiskedrag och en kort beskrivning om fångsten.

Användare:

Gäst:
Som gäst i appen kan du endast se startsidan och få möjlighet att registrera dig. Vill du använda dig utav appen måste du ha ett användarkonto. 

Fisherman:
Som Fisherman har du registrerat dig på appen och har möjlighet att skapa fiskepass och registrera dom fiskar du fångar under passet. Du kommer också ha möjlighet att redigera dom fiskar du fångar och även kunna ta bort fiskar som du registrerat under fiskepasset.
W
Admin:
Som ADMIN har du möjlighet att registrera användare till admin. Du har även möjlighet att ta bort Fisherman(användare) samt att redigera och ta bort deras fiskepass. 

Entiteter: 
Fisherman,
FishingTrip,
Species,
FishermanRole.

FishermanRole:
id, 
role.

Klassen tilldelar användaren en roll. Det finns två olika roller. USER och ADMIN.
USER kan använda appen och registrera varje fiskepass med fångstrapport.
ADMIN har behörigheten att ändra och ta bort Fisherman(USER) samt ändra och ta bort Fishermans Fiskepass(FishingTrip).

Fisherman:
FishermanId,
userName,
email,
password,
regDate,
FishingTrip.

Fisherman loggar in i applikationen med hjälp av sitt användarnamn och lösenord. Användarnamnet ska vara unikt(även email). Datumet som användaren registrerar sitt konto kommer sparas i regDate.

FishingTrip:

fishingTripId,
fishingMethod 3 olika val (spinn fishing, trolling or coarse fishing),
waterType(optional),
location(optional),
Species.

Varje Fisherman kan registrera sitt fiskepass med information som vilken fiskemetod som används, vart man fiskar(exempel Hav, Sjö, Älv, Å), och en lista med fångade fiskar. 

Species:

speciesId,
fishSpecies,
length,
weight,
fishingLure(optional),
description(optional).

När användaren har skapat ett nytt fiskepass så kan fiskaren registrera varje fiske som användaren fångar till sitt fiskepass och spara information om dagens fiskepass.




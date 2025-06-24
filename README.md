**El País Opinion Scraper**

This project is a Java + Selenium + TestNG automation suite that scrapes the latest opinion articles from El País. It extracts article titles, content, and images, translates the titles to English, and analyzes repeated words across the translated headers.

**Features**

Scrapes the first five opinion articles from El País.
Extracts title, content, and image for each article.
Translates article titles from Spanish to English using the RapidAPI Google Translate API.
Analyzes and prints words repeated more than twice across all translated titles.
Supports running tests locally (Chrome) and in parallel across multiple browsers/devices on BrowserStack.


**Getting Started**

1. Clone the repository:
    git clone https://github.com/ani-0009/Project_El_Pais.git
   
    cd Project_El_Pais
   
3. Install dependencies:
   Make sure you have Java 11+ and Maven installed.
   
4. Configure BrowserStack (optional):
   To run tests on BrowserStack, set your BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY in ElPaisTest.java.

5. Run tests:
   mvn clean test


<img width="1321" alt="image" src="https://github.com/user-attachments/assets/c3b01d76-d4cf-49e3-9c9d-b8669c1360a7" />
<img width="1017" alt="image" src="https://github.com/user-attachments/assets/f03c0bb8-8bb8-4cdc-9820-4696442e9300" />


**OUTPUT**


=== Articles ===
Title: Acuerdo, no imposición
Content: España logra que la OTAN reconozca que los objetivos de gasto en Defensa deben ser negociados y consensuados entre los socios
Image URL: 
---
Title: Nombres nuevos, corrupción antigua
Content: Una trama como la descubierta en el PSOE solo puede producirse por factores sistémicos que deben erradicarse
Image URL: 
---
=== Articles ===
Title: Acuerdo, no imposición
Content: España logra que la OTAN reconozca que los objetivos de gasto en Defensa deben ser negociados y consensuados entre los socios
Image URL: 
---
Title: Nombres nuevos, corrupción antigua
Content: Una trama como la descubierta en el PSOE solo puede producirse por factores sistémicos que deben erradicarse
Image URL: 
---
=== Translated Titles ===
Agreement, not imposition
New names, ancient corruption
=== Repeated Words (More than twice) ===
No words repeated more than twice.
=== Translated Titles ===
Agreement, not imposition
New names, ancient corruption
=== Repeated Words (More than twice) ===
No words repeated more than twice.

===============================================
Default Suite
Total tests run: 5, Passes: 5, Failures: 0, Skips: 0
===============================================



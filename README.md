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


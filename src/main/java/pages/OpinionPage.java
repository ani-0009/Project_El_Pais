package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OpinionPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "article.c.c-o")
    private List<WebElement> articleElements;

    @FindBy(css = "html[lang='es']")
    private WebElement spanishLanguageElement;

    public OpinionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isSpanishLanguageDisplayed() {
        return spanishLanguageElement != null;
    }

    public List<Article> getFirstFiveArticles() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(articleElements));
        } catch (Exception e) {
            System.out.println("No elements found with selector 'article.c.c-o'. Retrying...");
            articleElements = driver.findElements(By.cssSelector("article"));
            if (articleElements.isEmpty()) {
                System.out.println("Still no articles found." + driver.getPageSource());
                return new ArrayList<>();
            }
        }
        List<Article> articles = new ArrayList<>();
        int i = 0;
        while (articles.size() < 5 && i < articleElements.size()) {
            WebElement article = articleElements.get(i);
            String title = "";
            String content = "";
            String imageUrl = "";
            try {
                title = article.findElement(By.cssSelector("h2.c_t a")).getText();
                content = article.findElement(By.cssSelector("article.c.c-o p.c_d")).getText();
                if (content == null || content.trim().isEmpty()) {
                    i++;
                    continue;
                }
                try {
                    imageUrl = article.findElement(By.cssSelector("img")).getAttribute("src");
                } catch (Exception e) {
                    // No image found
                }
                articles.add(new Article(title, content, imageUrl));
            } catch (Exception e) {
                System.out.println("Error extracting article data: " + e.getMessage());
            }
            i++;
        }
        return articles;
    }

    public static class Article {
        private String title;
        private String content;
        private String imageUrl;

        public Article(String title, String content, String imageUrl) {
            this.title = title;
            this.content = content;
            this.imageUrl = imageUrl;
        }

        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getImageUrl() { return imageUrl; }
    }
}
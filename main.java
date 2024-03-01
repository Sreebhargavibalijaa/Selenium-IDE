import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;

public class DownloadLatestArxivPDF {
    public static void main(String[] args) {
        // Path to save PDF files, change as needed
        String downloadFilepath = "/path/to/download";

        // Set up Chrome options
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        // Set the path to your WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver");

        // Initialize WebDriver with Chrome options
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the arXiv listing page
            driver.get("https://arxiv.org/list/astro-ph/2303");

            // Find all PDF links on the page
            List<WebElement> pdfLinks = driver.findElements(By.xpath("//div[@class='list-dateline']/following-sibling::dl[1]//span[@class='list-identifier']/a[contains(text(),'pdf')]"));

            // Check if there are any PDF links
            if (!pdfLinks.isEmpty()) {
                // Get the href attribute of the first PDF link
                String latestPdfUrl = pdfLinks.get(0).getAttribute("href");

                // Open the PDF URL to initiate download
                driver.get(latestPdfUrl);

                // Add wait or additional logic as needed to ensure the file downloads completely.
                // This could include checking the download directory for the file's existence, etc.

                System.out.println("Latest PDF URL: " + latestPdfUrl);
            } else {
                System.out.println("No PDF links found on the page.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser after a delay (adjust as needed)
            try {
                Thread.sleep(10000); // Wait for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class news_fetcher extends TimerTask {

    private static String path = "D:\\SE\\3rd year\\2nd semester\\Software Engineering II\\Class\\assignments\\#6\\DIV news website\\automated_website\\public\\news.json";
    private static String URL = "https://addisfortune.net/";
    private static WebDriver driver;

    public static void main(String[] args){

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        Timer timer = new Timer();
        do try {
            Thread.sleep(10000000);
            timer.schedule(new news_fetcher(), 10000);
        } catch (Exception e) {
            e.printStackTrace();
        } while (true);

       
    }
    @Override
    public void run() {
        fetchNews();
    }

    private static void fetchNews() {
        driver = new ChromeDriver();

        driver.navigate().to(URL);

        try {
            sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement column = driver.findElement(By.className("span6"));
        List<WebElement> titles = column.findElements(By.tagName("h3"));
        List<WebElement> news = column.findElements(By.className("row"));

        String newsContent = generateNews(titles, news);
        writeToFile(path, newsContent);

        System.out.println("finished");
        driver.close();
    }

    private static String generateNews(List<WebElement> titles, List<WebElement> news){
        StringBuilder stringBuilder = new StringBuilder("news = '[ { \"title\" : \" \"," +
                " \"imageSrc\" : \" \", " +
                "\"detail\" : \" \" }");

        String newsTitle, newsImSrc, newsDetail;
        WebElement currentNews, currentTitle, newsImg;
        for (int i = 0; i < 10; i++) {
            currentNews = news.get(i);
            currentTitle = titles.get(i);
            newsTitle = currentTitle.getText();
            newsImg = currentNews.findElement(By.className("span2"));
            newsImSrc = newsImg.findElement(By.tagName("img")).getAttribute("src");
            newsDetail = currentNews.findElement(By.className("span4")).getText();

            stringBuilder.append(generateJSON(newsTitle, newsImSrc, newsDetail));
        }

        stringBuilder.append("]'");
        return stringBuilder.toString();
    }

    private static String generateJSON(String newsTitle, String imageSrc, String newsDetail){

        return (", { \"title\" : \"" + newsTitle + "\", " +
                "\"imageSrc\" : \"" + imageSrc + "\"," +
                " \"detail\" : \"" + newsDetail + "\" }  ");
    }

    private static void writeToFile(String filePath, String content){
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(content.getBytes());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

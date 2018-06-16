import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.util.*;

public class news_fetcher {
    static String URL1 = "https://addisfortune.net/";
    static String URL2 = "file:///D:/SE/3rd%20year/2nd%20semester/Software%20Engineering%20II/Lab/lab2/Addisfortune-The%20Largest%20English%20Weekly%20in%20Ethiopia.htm";
    static JSONObject jsonObject;
    static WebDriver driver;
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        jsonObject = new JSONObject();
        fetchData();
    }
    public static void  fetchData(){
        driver = new ChromeDriver();
        driver.get(URL2);
        Map<String, String> map = new LinkedHashMap<>();
        try {
            FileWriter fw = new FileWriter("news.json");
            WebElement column = driver.findElement(By.className("span6"));
            List<WebElement> title = column.findElements(By.tagName("h3"));
            WebElement row = column.findElement(By.className("row"));
            for (WebElement eachNews : title) {
                String newsTitle = eachNews.getText();
                System.out.println(newsTitle);
                String detail = row.findElement(By.tagName("p")).getText();
                System.out.println(detail);
                map.put(newsTitle, detail);
            }
            jsonObject.putAll(map);
            fw.write(jsonObject.toJSONString());
            fw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
    }
}

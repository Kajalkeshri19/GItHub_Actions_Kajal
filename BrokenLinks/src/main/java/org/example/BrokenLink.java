package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import resources.Base;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BrokenLink {
    static WebDriver driver;
    public Base base;
    static String homePage = "https://www.flipkart.com/";
    static String url = "";
    static HttpURLConnection huc = null;
    static int responseCode = 200;
    File src;
    int rowCount = 1;
    public BrokenLink(WebDriver driver, Base base) {
        this.driver = driver;
        this.base = base;
        PageFactory.initElements(driver, this);
        this.src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

    }

    public void checkAllLinks() throws IOException {

        System.out.println("Number of links available: " + driver.findElements(By.tagName("a")).size());
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Creating a new sheet in the workbook
        XSSFSheet sheet = workbook.createSheet("Links");

        // Creating a new row for the header
        Row headerRow = sheet.createRow(0);

        // Creating the first cell in the header row and setting its value
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Link");

        // Creating the second cell in the header row and setting its value
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Response Code");

        // Creating the third cell in the header row and setting its value
        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Status");

        while (it.hasNext()) {
            url = it.next().getAttribute("href");
            if (url == null || url.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            if (!url.startsWith(homePage)) {
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }
            try {
                huc = (HttpURLConnection) (new URL(url).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                responseCode = huc.getResponseCode();
                if (responseCode >= 400) {
                    System.out.println(url + " is a broken link");
                } else {
                    System.out.println(url + " is a valid link");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot1.png"));
            } catch (IOException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot2.png"));
            } catch (TimeoutException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot3.png"));
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot4.png"));
            } catch (WebDriverException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot5.png"));
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                FileUtils.copyFile(src, new File("/home/knoldus/screenshot6.png"));
            }

            // Adding all the links to the Excel sheet
            Row row = sheet.createRow(rowCount++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(url);
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(responseCode);
            Cell cell3 = row.createCell(2);
            if (responseCode >= 400) {
                cell3.setCellValue("Broken");
            } else {
                cell3.setCellValue("Active");
            }
        }
        FileOutputStream outputStream = new FileOutputStream("output.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        }


}
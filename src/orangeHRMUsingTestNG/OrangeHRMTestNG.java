package orangeHRMUsingTestNG;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrangeHRMTestNG {

	WebDriver driver = null;

	Object[][] data = null;

	@DataProvider(name = "loginData")
	public Object[][] loginData() throws Exception {

		data = readExcel();
		return data;

	}

	public Object[][] readExcel() throws Exception {
		File location = new File("E:\\Selenium\\DataDrivenTestNG\\ExcelData\\OrangeHRMData.xlsx");
		FileInputStream Stream = new FileInputStream(location);

		Workbook workbook = new XSSFWorkbook(Stream);
		Sheet sheet = workbook.getSheet("Sheet1");
		String value = null;
		int rowCount = sheet.getPhysicalNumberOfRows();
		Row headerRow = sheet.getRow(0);
		Object[][] obj = new Object[sheet.getPhysicalNumberOfRows() - 1][headerRow.getPhysicalNumberOfCells()];
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				Cell cell = row.getCell(j);
				value = cell.getStringCellValue();
				obj[i - 1][j] = value;
			}
		}
		return obj;
	}

	@BeforeTest
	public void beforeTest() {

		System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\OrangeHRMTestNg\\chromedriver.exe");

		driver = new ChromeDriver();

	}

	@AfterTest
	public void afterTest() {

		driver.quit();

	}

	@Test(dataProvider = "loginData")
	public void login(String username, String password) {

		driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/login");

		WebElement userName = driver.findElement(By.id("txtUsername"));
		userName.sendKeys(username);

		WebElement passWord = driver.findElement(By.id("txtPassword"));
		passWord.sendKeys(password);

		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		loginButton.click();

		System.out.printf(username, password);
	}

}

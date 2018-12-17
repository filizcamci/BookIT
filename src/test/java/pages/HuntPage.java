package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utilities.Driver;

public class HuntPage extends TopNavigationBar {
	public HuntPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	
	@FindBy(id = "mat-input-0")
	public WebElement date;
	
	@FindBy(xpath = "//*[@id=\"mat-tab-content-0-0\"]/div/div[3]/app-hunt-form/form/mat-form-field[2]/div/div[1]/div")
	public WebElement from;
	
	@FindBy(xpath = "//*[@id=\"mat-select-1\"]/div/div[1]/span")
	public WebElement to;
	
	
	@FindBy(xpath = "//button[@type='submit']")
	public WebElement search;
	
	public void selectFrom(WebElement element) {
		Select select=new Select(element);
		select.selectByIndex(4);
	}
	
	public void selectTo(WebElement element) {
		Select select=new Select(element);
		select.selectByIndex(2);
	}
	
	public void clickSearch() {
		Actions actions = new Actions(Driver.getDriver());
		actions.moveToElement(search).click().perform();
		
	}
}

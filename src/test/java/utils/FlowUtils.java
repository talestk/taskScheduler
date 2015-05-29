package utils;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlowUtils {
	private static final String SERVER = "https://cluster.partek.com/flow/login.xhtml";
	private static final String ADD_SAMPLES_BUTTON_XPATH = "/html/body/table/tbody/tr[3]/td/div/div/div[2]/div/div[2]/span/div/form[9]/button[1]";
	private static final String DELETE_PROJECT_LINK = "/html/body/table/tbody/tr[3]/td/div/div/div[2]/form/div/table[2]/tbody/tr[1]/td[2]/div/table/tbody/tr[2]/td[1]/div/a";
	private static final String PROJECT_MENU = "/html/body/div[6]/ul/li[2]/a/span";
	private final WebDriver driver;

	public FlowUtils(WebDriver driver) {
		this.driver = driver;
	}

	public void logIn(String userName, String password) {
		driver.get(SERVER);
		driver.findElement(By.id("login_form:username")).sendKeys(userName);
		driver.findElement(By.id("login_form:password")).sendKeys(password);
		driver.findElement(By.tagName("button")).click();
	}

	/**
	 * http://stackoverflow.com/questions/5868439/wait-for-page-load-in-selenium
	 * @param seconds number of seconds to wait for a page load
	 */
	public void waitSecondsForPageLoad(long seconds) {
		long timeOut = seconds * 1000;
		long end = System.currentTimeMillis() + timeOut;
		while (System.currentTimeMillis() < end) {
			if (String.valueOf(
					((JavascriptExecutor) driver)
							.executeScript("return document.readyState"))
					.equals("complete")) {
				break;
			}
		}
	}

	/**
	 * This was taken from StackOverflow but I am not sure on the exact link
	 * @param element element to be ready
	 * @param seconds the amount of seconds to wait
	 */
	public void waitForElementToBeReady(final By element, long seconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, seconds);
		ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					d.findElement(element);
				} catch (ElementNotFoundException e) {
					return false;
				}
				return true;
			}
		};
		webDriverWait.until(condition);
	}

	/**
	 * http://stackoverflow.com/questions/5868439/wait-for-page-load-in-selenium
	 * @param element the element to be visible
	 * @param seconds the amount of seconds to wait
	 */
	public void waitForElementToBeVisible(final By element, long seconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, seconds);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	/**
	 * This will add specific samples to a project
	 * @param samplesXpath the xpath for the desired samples to be added to the project
	 */
	public void addSamples(String samplesXpath) {
		driver.findElement(By.xpath(ADD_SAMPLES_BUTTON_XPATH)).click();
		By createSamplesButton = By.id("addsamplesdialog:autoCreateSamplesButton");
		waitForElementToBeVisible(createSamplesButton, 3);
		driver.findElement(createSamplesButton).click();
		By selectFilesButton = By.id("fileselector:next_server");
		waitForElementToBeReady(selectFilesButton, 3);
		driver.findElement(By.xpath(samplesXpath)).click();
		driver.findElement(selectFilesButton).click();
		waitForElementToBeReady(By.id("sampleManagement"), 5);
		driver.findElement(By.linkText("Analyses")).click();
		waitForElementToBeVisible(By.id("analysesView"), 3);
	}

	/**
	 * Adds a collaborator to a project
	 * @param collaborator the existing collaborator name
	 */
	public void addCollaborator(String collaborator) {
		driver.findElement(By.linkText("Project settings")).click();
		By addCollaboratorInput = By.id("add:memberToAdd_input");
		waitForElementToBeVisible(addCollaboratorInput, 4);
		driver.findElement(addCollaboratorInput).sendKeys(collaborator);
		driver.findElement(By.id("add:addButton")).click();
		driver.findElement(By.linkText("Data")).click();
		// we have refresh because the add member input suggests names and leaves the div on the screen
		driver.navigate().refresh();
		waitForElementToBeVisible(By.id("sampleManagement"), 3);
	}

	/**
	 * @param projectName the project name to be created
	 * @throws org.openqa.selenium.NoSuchElementException if a project with the same name already exists
	 */
	public void createProject(String projectName) {
		By homepageActionButton = By.className("HomepageActionText");
		waitForElementToBeReady(homepageActionButton, 3);
		driver.findElement(homepageActionButton).click();
		By projectNameInput = By.id("newProjectForm:newProjectName");
		waitForElementToBeVisible(projectNameInput, 3);
		driver.findElement(projectNameInput).sendKeys(projectName);
		driver.findElement(By.id("newProjectForm:createProjectButton")).click();
		waitForElementToBeReady(By.id("project-tabs"), 3);
	}

	public void gotToExistingProject() throws InterruptedException {
		driver.findElement(By.id("homepage:filterProjectsOption")).click();
		driver.findElement(By.xpath(PROJECT_MENU)).click();
		Thread.sleep(250); // lag for menu to pop up
		driver.findElement(By.xpath(DELETE_PROJECT_LINK)).click();
		waitForElementToBeReady(By.id("project-tabs"), 3);
	}
}
package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TaskScheduler {
	private static final String SELECT_NODE_JS = "for (var i in taskViewData.allNodes) {" +
			"if(taskViewData.allNodes[i].layer.label === 'Unaligned reads') {" +
			"taskViewData.allNodes[i].select();break;} }";
	private final WebDriver driver;
	private final FlowUtils flowUtils;

	public TaskScheduler(WebDriver driver, FlowUtils flowUtils) {
		this.driver = driver;
		this.flowUtils = flowUtils;
	}

	/**
	 * Queue tasks from the QA/QC section of Unaligned reads menu
	 * @param task the task to queue
	 * @throws InterruptedException
	 */
	public void queueQAQCTask(String task) throws InterruptedException {
		selectNode();
		queueTask(task);
	}

	/**
	 * Queue tasks from the Pre-alignment tools section of Unaligned reads menu
	 * @param task the task to queue
	 * @throws InterruptedException
	 */
	public void queuePreAlignTask(String task) throws InterruptedException {
		selectNode();
		driver.findElement(By.linkText("Pre-alignment tools")).click();
		flowUtils.waitForElementToBeVisible(By.linkText(task), 2);
		queueTask(task);
	}

	/**
	 * Queue tasks from the Aligners section of Unaligned reads menu
	 * @param task the task to queue
	 * @throws InterruptedException
	 */
	public void queueAlignerTask(String task) throws InterruptedException {
		selectNode();
		driver.findElement(By.linkText("Aligners")).click();
		flowUtils.waitForElementToBeVisible(By.linkText(task), 2);
		queueTask(task);
	}

	private void queueTask(String taskName) {
		driver.findElement(By.linkText(taskName)).click();
		By finishButton = By.id("selectorpage:execute");
		flowUtils.waitForElementToBeReady(finishButton, 5);
		driver.findElement(finishButton).click();
		flowUtils.waitForElementToBeVisible(By.id("analysesView"), 3);
	}

	private void selectNode() throws InterruptedException {
		flowUtils.waitSecondsForPageLoad(5);
		Thread.sleep(1500); // lag for drawing the task view
		((JavascriptExecutor) driver).executeScript(SELECT_NODE_JS);
		Thread.sleep(500); // lag for bringing the task menu
		By selectedNodeTaskOptions = By.id("tasksForm:taskAccordion");
		flowUtils.waitForElementToBeVisible(selectedNodeTaskOptions, 3);
	}
}

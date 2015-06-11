package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TaskScheduler {
	private final WebDriver driver;
	private final FlowUtils flowUtils;

	public TaskScheduler(WebDriver driver, FlowUtils flowUtils) {
		this.driver = driver;
		this.flowUtils = flowUtils;
	}

	private String getJSStringToQueueTaskOnFirstNode(String jsString) {
		return "for (var i in taskViewData.allNodes) {" +
				"if(taskViewData.allNodes[i].layer.label === '" + jsString + "') {" +
				"taskViewData.allNodes[i].select();break;} }";
	}


	private String getJSStringToQueueTask(String jsString) {
		return "var taskArr = [];" +
				"for (var i in taskViewData.allNodes) { " +
				"var status = taskViewData.allNodes[i].layer.status;" +
				"if(taskViewData.allNodes[i].layer.label === '" + jsString +
		"' &&  status !== 'Failed' && status !== 'Canceled') {taskArr.push(taskViewData.allNodes[i])}};" +
		"var choice = Math.floor(Math.random() * (taskArr.length - 0) + 0); taskArr[choice].select();";
	}

	private void queueTask(String taskName) {
		driver.findElement(By.linkText(taskName)).click();
		By finishButton = By.id("selectorpage:execute");
		flowUtils.waitForElementToBeReady(finishButton, 10);
		driver.findElement(finishButton).click();
		flowUtils.waitForElementToBeVisible(By.id("analysesView"), 10);
	}

	private void selectAlignedReadsNode(String node) throws InterruptedException {
		flowUtils.waitSecondsForPageLoad(5);
		Thread.sleep(3000); // lag for drawing the task view
		((JavascriptExecutor) driver).executeScript(getJSStringToQueueTaskOnFirstNode(node));
		Thread.sleep(1000); // lag for bringing the task menu
		By selectedNodeTaskOptions = By.id("tasksForm:taskAccordion");
		flowUtils.waitForElementToBeVisible(selectedNodeTaskOptions, 5);
	}

	private void selectRandomNodeWithName(String node) throws InterruptedException {
		flowUtils.waitSecondsForPageLoad(5);
		Thread.sleep(3000); // lag for drawing the task view
		((JavascriptExecutor) driver).executeScript(getJSStringToQueueTask(node));
		Thread.sleep(1000); // lag for bringing the task menu
		By selectedNodeTaskOptions = By.id("tasksForm:taskAccordion");
		flowUtils.waitForElementToBeVisible(selectedNodeTaskOptions, 5);
	}

	/**
	 * Queue tasks from the QA/QC section of Unaligned reads menu
	 * @param task the task to queue
	 * @throws InterruptedException
	 */
	public void queueQAQCTask(String task) throws InterruptedException {
		selectAlignedReadsNode("Unaligned reads");
		queueTask(task);
	}

	/**
	 * Queue tasks from the Pre-alignment tools section of Unaligned reads menu
	 * @param task the task to queue
	 * @throws InterruptedException
	 */
	public void queuePreAlignTask(String task) throws InterruptedException {
		selectAlignedReadsNode("Unaligned reads");
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
		selectAlignedReadsNode("Unaligned reads");
		driver.findElement(By.linkText("Aligners")).click();
		flowUtils.waitForElementToBeVisible(By.linkText(task), 3);
		queueTask(task);
	}

	public void queuePostAlignmentTask(String task) throws InterruptedException {
		selectAlignedReadsNode("Aligned reads");
		driver.findElement(By.linkText("Post-alignment QA/QC")).click();
		flowUtils.waitForElementToBeVisible(By.linkText(task), 5);
		driver.findElement(By.linkText(task)).click();
		flowUtils.waitForElementToBeVisible(By.id("analysesView"), 3);
	}

	public void queueRnaSeqAnalysisTask(String task) throws InterruptedException {
		selectRandomNodeWithName("Aligned reads");
		driver.findElement(By.linkText("RNA-Seq Analysis")).click();
		flowUtils.waitForElementToBeVisible(By.linkText(task), 2);
		queueTask(task);
	}
}

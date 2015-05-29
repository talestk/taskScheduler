package scheduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.FlowUtils;
import utils.TaskScheduler;
import utils.UnalignedReadsTasks;

public class TaskSchedulerTest {
	private static WebDriver driver;
	private static FlowUtils flowUtils;
	private static TaskScheduler taskScheduler;
	private static final String SAMPLES_XPATH = "/html/body/table/tbody/tr[3]/td/div/div/div[2]/form[2]/div[1]/div/div/div[2]" +
			"/div[1]/div[2]/div/table/tbody/tr/td/div/div/ul/li/ul/li[1]/ul/li/ul/li[1]/ul/li[1]/ul/li[4]/span/span[3]/span";

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		flowUtils = new FlowUtils(driver);
		taskScheduler = new TaskScheduler(driver, flowUtils);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void queueSingleTask() throws InterruptedException {
		flowUtils.logIn("admin0", "admin123");
		flowUtils.createProject("flowTest0");
		flowUtils.addCollaborator("admin");
		flowUtils.addSamples(SAMPLES_XPATH);
		taskScheduler.queueAlignerTask(UnalignedReadsTasks.Aligners.TMAP);
	}

	@Test
	public void queueMultipleTasks() throws InterruptedException {
		flowUtils.logIn("admin0", "admin123");
		flowUtils.createProject("flowTest1");
		flowUtils.addCollaborator("admin");
		flowUtils.addSamples(SAMPLES_XPATH);
		taskScheduler.queueAlignerTask(UnalignedReadsTasks.Aligners.getRandom());
		taskScheduler.queuePreAlignTask(UnalignedReadsTasks.PreAlignment.getRandom());
		taskScheduler.queueQAQCTask(UnalignedReadsTasks.QAQC.getRandom());
	}
}

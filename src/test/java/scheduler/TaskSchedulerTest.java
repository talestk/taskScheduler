package scheduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.AlignedReadsTasks;
import utils.FlowUtils;
import utils.TaskScheduler;
import utils.UnalignedReadsTasks;

public class TaskSchedulerTest {
	private static WebDriver driver = new ChromeDriver();
	private static FlowUtils flowUtils;
	private static TaskScheduler taskScheduler;
	private static final String SAMPLES_XPATH = "/html/body/table/tbody/tr[3]/td/div/div/div[2]/form[2]/div/div[1]/div" +
			"/div/div[2]/div[1]/div[2]/div/table/tbody/tr/td/div/div/ul/li/ul/li[1]/ul/li/ul/li[3]/ul/li[1]/ul/li[4]/span/span[3]/span";
	public static final String SAMPLE_ATTRIBUTES_XPATH = "/html/body/table/tbody/tr[3]/td/div/div/div[2]/form/div/div[1]/" +
			"div/div/div[2]/div[1]/div[2]/div/table/tbody/tr/td/div/div/ul/li/ul/li[1]/ul/li/ul/li[3]/ul/li[1]/ul/li[4]/span/span[3]/span";

	@Before
	public void setUp() {
		flowUtils = new FlowUtils(driver);
		taskScheduler = new TaskScheduler(driver, flowUtils);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void createProjectAndQueueManyRandomTask() throws InterruptedException {
		flowUtils.logIn("admin0", "admin123");
		flowUtils.createProject("flowTest0");
		flowUtils.addCollaborator("admin");
		flowUtils.addSamples(SAMPLES_XPATH);
		flowUtils.addSamplesAttributes(SAMPLE_ATTRIBUTES_XPATH);
		for (int i = 0; i < 5; i ++) {
			taskScheduler.queueAlignerTask(UnalignedReadsTasks.Aligners.getRandom());
			taskScheduler.queuePreAlignTask(UnalignedReadsTasks.PreAlignment.getRandom());
			taskScheduler.queueRnaSeqAnalysisTask(AlignedReadsTasks.RNASeqAnalysis.getRandom());
		}
	}

	@Test
	public void queueManyRandomTask() throws InterruptedException {
		flowUtils.logIn("admin0", "admin123");
		flowUtils.goToExistingProject("flowTest0");
		for (int i = 0; i < 5; i ++) {
			taskScheduler.queueRnaSeqAnalysisTask(AlignedReadsTasks.RNASeqAnalysis.getRandom());
		}
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

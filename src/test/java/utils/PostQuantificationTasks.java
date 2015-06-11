package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PostQuantificationTasks {
	private static final Random RANDOM = new Random();

	public static class RNASeqAnalysis {
		public static final String GSA = "Quantify to transcriptome (Partek E/M)";
		public static final String ANOVA = "Quantify to transcriptome (Cufflinks)";
		public static final String CUFFDIFF = "Quantify data aligned to transcriptome"; // this is only for cufflinks nodes
		public static final List<String> listOfAll = Arrays.asList(GSA, ANOVA);

		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}
}

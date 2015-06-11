package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AlignedReadsTasks {
	private static final Random RANDOM = new Random();

	public static class QAQC {
		public static final String POST_QAQC = "Post-alignment QA/QC";
		public static final String COVERAGE_REPORT = "Coverage report";
		public static final List<String> listOfAll = Arrays.asList(POST_QAQC, COVERAGE_REPORT);

		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}

	public static class RNASeqAnalysis {
		public static final String QUANT_PARTEK_EM = "Quantify to transcriptome (Partek E/M)";
		public static final String QUANT_CUFFLINKS = "Quantify to transcriptome (Cufflinks)";
		public static final String QUANT_TRANSCRIPTOME = "Quantify data aligned to transcriptome";
		public static final List<String> listOfAll = Arrays.asList(QUANT_PARTEK_EM, QUANT_CUFFLINKS, QUANT_TRANSCRIPTOME);

		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}

	public static class VariantDetection {
		public static final String DETECT_VARIANTS_AGAINST_REFERENCE = "Detect variants against reference";
		public static final String DETECT_VARIANTS_AMONG_SAMPLES = "Detect variants among samples";
		public static final String DETECT_FUSION_GENES = "Detect fusion genes";
		public static final String SEQDUO_ANALYSIS = "SeqDuo analysis";
		public static final List<String> listOfAll = Arrays.asList(DETECT_VARIANTS_AGAINST_REFERENCE, DETECT_VARIANTS_AMONG_SAMPLES, DETECT_FUSION_GENES, SEQDUO_ANALYSIS);

		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}
}

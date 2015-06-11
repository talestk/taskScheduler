package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UnalignedReadsTasks {
	private static final Random RANDOM = new Random();

	//Aligners
	public static class Aligners {
		public static final String BOWTIE = "Bowtie";
		public static final String BOWTIE_2 = "Bowtie 2";
		public static final String BWA = "BWA";
		public static final String GSNAP = "GSNAP";
		public static final String SHRIMP_2 = "SHRiMP 2";
		public static final String STAR = "STAR";
		public static final String TMAP = "TMAP";
		public static final String TOPHAT = "TopHat";
		public static final String TOPHAT_2 = "TopHat 2";
		public static final List<String> listOfAll = Arrays.asList(BOWTIE, BOWTIE_2, BWA, GSNAP, SHRIMP_2, STAR, TMAP, TOPHAT, TOPHAT_2);
		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}

	// QA/QC
	public static class QAQC {
		public static final String PRE_QAQC = "Pre-alignment QA/QC";
		public static final String ERCC = "ERCC (Bowtie)";
		public static final List<String> listOfAll = Arrays.asList(PRE_QAQC, ERCC);
		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}

	// Pre-alignment tools
	public static class PreAlignment {
		public static final String TRIM_BASES = "Trim bases";
		public static final String TRIM_ADAPTERS = "Trim adapters";
		public static final String SUB_SAMPLE = "Subsample FASTQ";
		public static final List<String> listOfAll = Arrays.asList(TRIM_BASES, SUB_SAMPLE);
		public static String getRandom() {
			int index = RANDOM.nextInt(listOfAll.size());
			return listOfAll.get(index);
		}
	}
}

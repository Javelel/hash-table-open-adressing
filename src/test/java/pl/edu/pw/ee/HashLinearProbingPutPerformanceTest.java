package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HashLinearProbingPutPerformanceTest {

	@Test
	public void performanceTest() throws FileNotFoundException {
		String fileName = "list_of_words";
		File file = new File(fileName);
		Scanner input;
		ArrayList<String> listOfWords = new ArrayList<>();
		int initialSize = 262144;
		long[] timeElapsed = new long[10];
		input = new Scanner(file);

		while (input.hasNextLine()) {
			listOfWords.add(input.nextLine());
		}

		for (int i = 0; i < 10; i++) {

			HashTable<String> hashList = new HashLinearProbing<>(initialSize);

			long start = System.currentTimeMillis();

			for (String word : listOfWords) {
				hashList.put(word);
			}

			long finish = System.currentTimeMillis();
			timeElapsed[i] = finish - start;

		}
		Arrays.sort(timeElapsed);

		long result = (timeElapsed[4] + timeElapsed[5]) / 2;
		System.out.println(initialSize + " - " + result);

	}
}

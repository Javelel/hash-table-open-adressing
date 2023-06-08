package pl.edu.pw.ee;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.pw.ee.services.HashTable;

import static org.junit.Assert.*;

public class HashLinearProbingTest {

	@Test(expected = IllegalArgumentException.class)
	public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
		// given
		int initialSize = 0;

		// when
		HashTable<Double> unusedHash = new HashLinearProbing<>(initialSize);

		// then
		assert false;
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_ThrowException_WhenPutNull() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);

		// when
		hash.put(null);

		// then
		assert false;
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_ThrowException_WhenGetNull() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);

		// when
		hash.get(null);

		// then
		assert false;
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_ThrowException_WhenDeleteNull() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);

		// when
		hash.delete(null);

		// then
		assert false;
	}

	@Test
	public void should_DeleteFirstElem_WhenManyElemsShareHashCode() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		hash.put(42);
		hash.put(22);
		hash.put(82);
		hash.put(12);

		// when
		hash.delete(42);
		// then
		assertNull(hash.get(42));

	}

	@Test
	public void should_DeleteMiddleElem_WhenManyElemsShareHashCode() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		hash.put(42);
		hash.put(22);
		hash.put(82);
		hash.put(12);

		// when
		hash.delete(82);
		// then
		assertNull(hash.get(82));

	}

	@Test
	public void should_DeleteLastElem_WhenManyElemsShareHashCode() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		hash.put(42);
		hash.put(22);
		hash.put(82);
		hash.put(12);

		// when
		hash.delete(12);
		// then
		assertNull(hash.get(12));

	}

	@Test
	public void should_DeleteManyElems_WhenAddedElemsHaveTheSameHash() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer[] elems = {34, 14, 54, 94, 44};
		for (Integer elem : elems) {
			hash.put(elem);
		}
		// when
		for (Integer elem : elems) {
			hash.delete(elem);
		}
		// then
		Integer[] result = new Integer[5];
		Integer[] expected = new Integer[5];
		for (int i = 0; i < 5; i++) {
			result[i] = hash.get(elems[i]);
		}
		Assert.assertArrayEquals(expected, result);

	}

	@Test
	public void should_GetElems_WhenAddedElemsHaveTheSameHash() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer[] elems = {34, 14, 54, 94, 44};
		for (Integer elem : elems) {
			hash.put(elem);
		}
		// when
		Integer[] result = new Integer[5];
		for (int i = 0; i < 5; i++) {
			result[i] = hash.get(elems[i]);
		}
		// then
		Assert.assertArrayEquals(elems, result);

	}

	@Test
	public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
		// given
		HashTable<String> emptyHash = new HashLinearProbing<>();
		String newEleme = "nothing special";

		// when
		int nOfElemsBeforePut = getNumOfElems(emptyHash);
		emptyHash.put(newEleme);
		int nOfElemsAfterPut = getNumOfElems(emptyHash);

		// then
		assertEquals(0, nOfElemsBeforePut);
		assertEquals(1, nOfElemsAfterPut);
	}

	@Test
	public void should_SetCorrectIndex() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer newElem = 42;

		// when
		hash.put(newElem);
		int indexOfTheElem = Arrays.asList(getHashElems(hash, Integer.class)).indexOf(newElem);

		// then
		assertEquals(2, indexOfTheElem);
	}

	@Test
	public void should_SetCorrectIndex_WhenMaxIndex() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer newElem = 19;

		// when
		hash.put(newElem);
		int indexOfTheElem = Arrays.asList(getHashElems(hash, Integer.class)).indexOf(newElem);

		// then
		assertEquals(9, indexOfTheElem);
	}

	@Test
	public void should_SetCorrectIndex_WhenIndexEqualsZero() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer newElem = 100;

		// when
		hash.put(newElem);
		int indexOfTheElem = Arrays.asList(getHashElems(hash, Integer.class)).indexOf(newElem);

		// then
		assertEquals(0, indexOfTheElem);
	}

	@Test
	public void should_GetElem_AfterAddingIt() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer elem = 34;
		hash.put(elem);

		// when
		Integer result = hash.get(elem);
		// then
		assertEquals(elem, result);

	}

	@Test
	public void shouldNot_AddNewElems_WhenAddedElemsAlreadyExistInHashTable() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer newElem = 45;

		// when
		for (int i = 0; i < 20; i++) {
			hash.put(newElem);
		}
		Integer[] result = getHashElems(hash, Integer.class);
		Integer[] expected = new Integer[10];
		expected[5] = newElem;

		// then
		Assert.assertArrayEquals(expected, result);

	}

	@Test
	public void shouldNot_ChangeSizeOfHashTable_WhenAddedElemsAlreadyExistInHashTable() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		Integer newElem = 45;

		// when
		for (int i = 0; i < 20; i++) {
			hash.put(newElem);
		}
		Integer[] result = getHashElems(hash, Integer.class);

		// then
		assertEquals(10, result.length);

	}

	@Test
	public void shouldNot_ChangeSizeOfHashTable_WhenDeletedAndAddedTheSameNumberOfElements() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(10);
		for (int i = 0; i < 20; i++) {
			hash.put(i);
		}
		int expected = getHashElems(hash, Integer.class).length;

		// when
		for (int i = 0; i < 15; i++) {
			hash.delete(i);
		}
		for (int i = 0; i < 15; i++) {
			hash.put(i);
		}
		int result = getHashElems(hash, Integer.class).length;

		// then
		assertEquals(expected, result);

	}

	@Test
	public void should_CorrectlyAddAndGet_WhenAddedManyElementsWithTheSameHashCode() {
		// given
		HashTable<Integer> hash = new HashLinearProbing<>(5);
		Integer elem = 8;
		Integer[] result = new Integer[100];
		Integer[] expected = new Integer[100];

		// when
		for (int i = 0; i < 100; i++) {
			hash.put(8 + 10 * i);
		}

		for (int i = 0; i < 100; i++) {
			result[i] = hash.get(8 + 10 * i);
			expected[i] = 8 + 10 * i;
		}

		// then
		assertArrayEquals(expected, result);

	}

	private int getNumOfElems(HashTable<?> hash) {
		String fieldNumOfElems = "nElems";
		try {
			System.out.println(hash.getClass().getSuperclass().getName());
			Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
			field.setAccessible(true);

			int numOfElems = field.getInt(hash);

			return numOfElems;

		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T[] getHashElems(HashTable<?> hash, Class<T> clazz) {
		String fieldHashElems = "hashElems";
		try {
			System.out.println(hash.getClass().getSuperclass().getName());
			Field field = hash.getClass().getSuperclass().getDeclaredField(fieldHashElems);
			field.setAccessible(true);

			T[] hashElems = (T[]) field.get(hash);

			@SuppressWarnings("unchecked")
			T[] res = (T[]) Array.newInstance(clazz, hashElems.length);
			int i = 0;
			for (T t : hashElems) {
				res[i] = t;
				i++;
			}
			return res;

		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}

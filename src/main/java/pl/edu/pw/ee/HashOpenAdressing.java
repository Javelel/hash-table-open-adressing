package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

import java.util.Arrays;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

	private final T nil = null;

	private class Del implements Comparable<T> {

		@Override
		public int compareTo(T o) {
			return 0;
		}
	}
	private final T del = (T) new Del();
	private int size;
	private int nElems;
	private T[] hashElems;
	private final double correctLoadFactor;

	HashOpenAdressing() {
		this(2039); // initial size as random prime number
	}

	HashOpenAdressing(int size) {
		validateHashInitSize(size);

		this.size = size;
		this.hashElems = (T[]) new Comparable[this.size];
		this.correctLoadFactor = 0.75;
	}

	@Override
	public void put(T newElem) {
		validateInputElem(newElem);
		resizeIfNeeded();

		int key = newElem.hashCode();
		int i = 0;
		int hashId = hashFunc(key, i);
		int firstElemWithCurrentHashCode = hashId;
		int firstDeletedElement = -1;

		while (hashElems[hashId] != nil && !hashElems[hashId].equals(newElem)) {
			if (hashElems[hashId].equals(del) && firstDeletedElement == -1) {
				firstDeletedElement = hashId;
			}
			i = (i + 1) % size;
			hashId = hashFunc(key, i);
			if (hashId == firstElemWithCurrentHashCode) {
				doubleResize();
				i = 0;
				hashId = hashFunc(key, i);
				firstElemWithCurrentHashCode = hashId;
			}
		}
		if (hashElems[hashId] != nil && hashElems[hashId].equals(del) && firstDeletedElement == -1) {
			firstDeletedElement = hashId;
		}
		if (firstDeletedElement != -1) {
			hashElems[firstDeletedElement] = newElem;
			return;
		}

		if (hashElems[hashId] == nil) {
			nElems++;
		}
		hashElems[hashId] = newElem;
	}

	@Override
	public T get(T elem) {
		validateInputElem(elem);
		int key = elem.hashCode();
		int i = 0;
		int hashId = hashFunc(key, i);

		while (hashElems[hashId] != nil && (!hashElems[hashId].equals(elem) || hashElems[hashId].equals(del))) {
			i = (i + 1) % size;
			hashId = hashFunc(key, i);
		}

		if (hashElems[hashId] == nil) {
			return hashElems[hashId];
		}

		if (hashElems[hashId].equals(elem)) {
			return hashElems[hashId];
		}
		return null;
	}

	@Override
	public void delete(T elem) {
		validateInputElem(elem);
		int key = elem.hashCode();
		int i = 0;
		int hashId = hashFunc(key, i);

		while (hashElems[hashId] != nil && !hashElems[hashId].equals(elem)) {
			i = (i + 1) % size;
			hashId = hashFunc(key, i);
		}
		if (hashElems[hashId] != nil) {
			hashElems[hashId] = del;
			nElems--;
		}
	}

	private void validateHashInitSize(int initialSize) {
		if (initialSize < 1) {
			throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
		}
	}

	private void validateInputElem(T newElem) {
		if (newElem == null) {
			throw new IllegalArgumentException("Input elem cannot be null!");
		}
	}

	abstract int hashFunc(int key, int i);

	int getSize() {
		return size;
	}

	private void resizeIfNeeded() {
		double loadFactor = countLoadFactor();

		if (loadFactor >= correctLoadFactor) {
			doubleResize();
		}
	}

	private double countLoadFactor() {
		return (double) nElems / size;
	}

	private void doubleResize() {
		this.size *= 2;
		T[] oldHashElems = this.hashElems;
		this.hashElems = (T[]) new Comparable[this.size];
		Arrays.fill(this.hashElems, nil);

		nElems = 0;
		for (T oldHashElem : oldHashElems) {
			if (oldHashElem != nil) {
				put(oldHashElem);
			}
		}

	}

}

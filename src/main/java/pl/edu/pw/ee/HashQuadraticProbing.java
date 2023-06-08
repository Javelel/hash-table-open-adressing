package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {

	private final double a;
	private final double b;

	public HashQuadraticProbing() {
		super();
		this.a = 0.5;
		this.b = 0.5;
	}

	public HashQuadraticProbing(int size) {
		super(size);
		this.a = 0.5;
		this.b = 0.5;
	}

	public HashQuadraticProbing(int size, double a, double b) {
		super(size);
		if (a == 0 || b == 0) {
			throw new IllegalArgumentException("a and b must be different from zero");
		}
		this.a = a;
		this.b = b;
	}

	@Override
	int hashFunc(int key, int i) {
		int m = getSize();

		double hash = ((key % m) + (a * i) + (b * i * i)) % m;

		hash = hash < 0 ? -hash : hash;

		return (int) hash;
	}
}

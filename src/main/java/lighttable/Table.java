package lighttable;

import java.util.*;

public class Table {

	private static final double Z = Math.E;

	private final Double[] table;

	private final int width;
	private final int height;

	long seed = 1234567893l;

	public Table(int x, int y) {
		this.width = x;
		this.height = y;
		this.table = new Double[x * y];

		tick();
	}

	public void set(int x, int y, double value) {
		table[y * width + x] = value;
	}

	public double get(int x, int y) {
		return table[y * width + x];
	}

	public int xMax() {
		return width;
	}

	public int yMax() {
		return height;
	}

	private double perlin(double x, double y, double z) {
		return PerlinImprovedNoise.noise(x / 9, y / 9, z);
	}

	public final void tick() {
		
		double time = (double)(System.currentTimeMillis()) / 10000;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				table[j * width + i] = perlin(i, j, time);
			}
		}

	}

}

package lighttable;

import java.util.ArrayList;

public class Table {

	private static double Z = Math.E;

	private double z = Table.Z;

	private int time = (int) System.currentTimeMillis() / 1000;

	private ArrayList<Double> table;

	private int width;
	private int height;

	long seed = 1234567893l;

	public Table(int x, int y) {
		this.width = x;
		this.height = y;
		this.table = new ArrayList<Double>(x * y);

		time = (int) System.currentTimeMillis() / 1000;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				table.add(j * width + i, perlin(i, j, time));
			}
		}
	}

	public void set(int x, int y, double value) {
		table.set(y * width + x, value);
	}

	public double get(int x, int y) {
		return table.get(y * width + x);
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

	public void tick() {
		
		double time = (double)(System.currentTimeMillis()) / 10000;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				table.set(j * width + i, perlin(i, j, time));
			}
		}

	}

}

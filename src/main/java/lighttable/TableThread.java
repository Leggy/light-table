package lighttable;


/**
 * Responsible for updating Table.
 * 
 * @author Leggy
 *
 */
public class TableThread implements Runnable {

	private int tick;
	private Table table;

	public TableThread(int tick, Table table) {
		this.tick = tick;
		this.table = table;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(tick);
				table.tick();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

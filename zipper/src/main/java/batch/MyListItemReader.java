package batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;

public class MyListItemReader implements ItemReader<Long> {
	protected final Logger log = Logger.getLogger(getClass());

	private static final int ID_BASE = 1000;
	private int listIndex;
	private int minIndex;
	private int maxIndex;
	private static List<List<Long>> list = new ArrayList<>(4);
	static {
		for (int i = 0; i < 4; i++) {
			List<Long> newList = new ArrayList<Long>();
			list.add(i, newList);
			//log.info("initializing " + minIndex + " to " + maxIndex + " in list index " + listIndex);
			for (int j = ID_BASE + i*500;j < ID_BASE + (i+1)*500; j++) {
				newList.add(0L+j);
			}
		}
	}

	@Override
	public synchronized Long read() {
		//logSize("read " + listIndex);
		if (!list.get(listIndex).isEmpty()) {
			return list.get(listIndex).remove(list.get(listIndex).size() - 1);
		}
		return null;
	}	
	
	public int getMinIndex() {
		return minIndex;
	}
	public void setMinIndex(int minIndex) {
		this.minIndex = minIndex;
	}
	public int getMaxIndex() {
		return maxIndex;
	}
	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}

	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}
	
	private void logSize(String prefix) {
		log.info(prefix +":[" + list.get(0).size() + "][" + list.get(1).size() + "][" + list.get(2).size() + "][" + list.get(3).size() + "]");
	}
}

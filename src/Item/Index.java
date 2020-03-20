package Item;

public class Index {

	private int curIDX;
	private int holdIDX;
	private int nextIDX;
	
	public int getCurIDX() {
		return curIDX;
	}

	public void setCurIDX(int curIDX) {
		this.curIDX = curIDX;
	}

	public int getHoldIDX() {
		return holdIDX;
	}

	public void setHoldIDX(int holdIDX) {
		this.holdIDX = holdIDX;
	}

	public int getNextIDX() {
		return nextIDX;
	}

	public void setNextIDX(int nextIDX) {
		this.nextIDX = nextIDX;
	}
	
	public Index() {
		holdIDX = 7;
	}

}

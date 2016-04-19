public class RBNode{
	int ID;
	int eventCount;
	boolean isBlack;
	RBNode left, right, parent;
	
	public RBNode(int ID, int c){
		this.ID = ID;
		eventCount = c;
		isBlack = false;
	}
}

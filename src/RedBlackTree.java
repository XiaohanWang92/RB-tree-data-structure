import java.util.*;


public class RedBlackTree {
	//field member root and count (size of the tree)
	private RBNode root;
	private int count;
	/**
	 * Constructor
	 */
	public RedBlackTree(){
		root = null;
		count = 0;
	}
	/**
	 * construct red black tree in O(n).
	 * @param events an array of event, the ID should be sorted first (external comparator)
	 */
	public void buildRedBlackTree(Event[] events){
		if(events == null || events.length == 0)
				return;
		int level = 1;
		int num = events.length;
		while(num >= (int)Math.pow(2, level) - 1){
			level++;
		}
//		int num2 = (int)Math.pow(2, level-1) - 1;
		root = treeBuilder(events, 0, num - 1, 1, level-1);
//		for(int i = num2; i < events.length; i++){
//			this.add(events[i].ID, events[i].count);
//		}
		count = events.length;
	}
	/**
	 * helper function to deal with the recursive construction.
	 * @param events an array of event, the ID should be sorted first
	 * @param lo index of Event array
	 * @param hi index of Event array
	 * @param currentLevel the current level of the tree
	 * @param level the last level of the tree, any node (inclusive) above or on the level is colored black, otherwise is colored red
	 * @return the root of the red black tree
	 */
	private RBNode treeBuilder(Event[] events, int lo, int hi, int currentLevel, int level){
		if(lo > hi)
			return null;
		int mid = lo + (hi - lo)/2;
		RBNode root = new RBNode(events[mid].ID, events[mid].count);
		if(currentLevel <= level)
			root.isBlack = true;
		RBNode lNode = treeBuilder(events, lo, mid-1, currentLevel + 1, level);
		root.left = lNode;
		if(lNode != null)
			lNode.parent = root;
		RBNode rNode = treeBuilder(events, mid+1, hi, currentLevel + 1, level);
		root.right = rNode;
		if(rNode != null)
			rNode.parent = root;
		return root;
	}
	/**
	 * overloaded version for constructing red black tree in O(n).
	 * @param arr the array which contains event count, event id is default from 1 to arr.length
	 */
	public void buildRedBlackTree(int[] arr){
		if(arr == null || arr.length == 0)
				return;
		int level = 1;
		int num = arr.length;
		while(num >= (int)Math.pow(2, level) - 1){
			level++;
		}
		root = treeBuilder(arr, 0, num - 1, 1, level-1);
		count = arr.length;
	}
	/**
	 * overloaded version for constructing red black tree in O(n).
	 * @param arr the array which contains event id, event count
	 */
	public void buildRedBlackTree(int[][] arr){
		if(arr == null || arr.length == 0)
				return;
		int level = 1;
		int num = arr.length;
		while(num >= (int)Math.pow(2, level) - 1){
			level++;
		}
		root = treeBuilder(arr, 0, num - 1, 1, level-1);
		count = arr.length;
	}
	/**
	 * overloaded version of helper function to deal with the recursive construction.
	 * @param arr the array which contains event id, event count
	 * @param lo index of array
	 * @param hi index of Event array
	 * @param currentLevel the current level of the tree
	 * @param level the last level of the tree, any node (inclusive) above or on the level is colored black, otherwise is colored red
	 * @return the root of the red black tree
	 */
	private RBNode treeBuilder(int[][] arr, int lo, int hi, int currentLevel, int level){
		if(lo > hi)
			return null;
		int mid = lo + (hi - lo)/2;
		RBNode root = new RBNode(arr[mid][0], arr[mid][1]);
		if(currentLevel <= level)
			root.isBlack = true;
		RBNode lNode = treeBuilder(arr, lo, mid-1, currentLevel + 1, level);
		root.left = lNode;
		if(lNode != null)
			lNode.parent = root;
		RBNode rNode = treeBuilder(arr, mid+1, hi, currentLevel + 1, level);
		root.right = rNode;
		if(rNode != null)
			rNode.parent = root;
		return root;
	}
	/**
	 * overloaded version of helper function to deal with the recursive construction.
	 * @param arr the array which contains event count, event id is default from 1 to arr.length
	 * @param lo index of array
	 * @param hi index of Event array
	 * @param currentLevel the current level of the tree
	 * @param level the last level of the tree, any node (inclusive) above or on the level is colored black, otherwise is colored red
	 * @return the root of the red black tree
	 */
	private RBNode treeBuilder(int[] arr, int lo, int hi, int currentLevel, int level){
		if(lo > hi)
			return null;
		int mid = lo + (hi - lo)/2;
		RBNode root = new RBNode(mid+1, arr[mid]);
		if(currentLevel <= level)
			root.isBlack = true;
		RBNode lNode = treeBuilder(arr, lo, mid-1, currentLevel + 1, level);
		root.left = lNode;
		if(lNode != null)
			lNode.parent = root;
		RBNode rNode = treeBuilder(arr, mid+1, hi, currentLevel + 1, level);
		root.right = rNode;
		if(rNode != null)
			rNode.parent = root;
		return root;
	}
    /**
     * adds the event to the tree.  Duplicate items and null items should not be added. O(log n).
     * @param ID the event ID that will be inserted
     * @param count the event count
     * @return true if item added, false if it is not
     */
	public boolean add(int ID, int count){
		if(ID <= 0 || count <= 0 || contains(ID))
				return false;
		boolean toRet = false;
		RBNode toAdd = new RBNode(ID, count);
		//empty tree or non-empty
		if(root == null){
			root = toAdd;
			root.isBlack = true;
			this.count++;
			return true;
		}else{
			toRet = add(toAdd, root);
		}
		if(toRet == true){
			fixAdd(toAdd);
		}
		//root is always black
		root.isBlack = true;
		return toRet;
	}
	/**
	 * add tree-node on an non-empty tree. If the node is already there, return false.
	 * @param toAdd the node that will be added
	 * @param subRoot the root that the node will be inserted
	 * @return true if item added, false if it is not
	 */
	
	private boolean add(RBNode toAdd, RBNode subRoot){
		//duplicate event!
		if(toAdd.ID == subRoot.ID){
			return false;
		}
		if(toAdd.ID < subRoot.ID){
			if(subRoot.left == null){
				subRoot.left = toAdd;
				toAdd.parent = subRoot;
				count++;
				return true;
			}else{
				return add(toAdd, subRoot.left);
			}
		}else{
			if(subRoot.right == null){
				subRoot.right = toAdd;
				toAdd.parent = subRoot;
				count++;
				return true;
			}else{
				return add(toAdd, subRoot.right);
			}
		}
	}
	/**
	 * fix R-B tree property after add new node.
	 * @param toAdd the new node which is added
	 */
	private void fixAdd(RBNode toAdd){
		RBNode parent, gparent, uncle;
        parent = toAdd.parent;
        //Case 1 & case 2 don't need to fix if toAdd is root or its parent is black (default isBlack false is red)
        while (parent != null && !parent.isBlack) {
            boolean parentIsRight = false;
            gparent = parent.parent;
            if (toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent) {
                parentIsRight = true;
            }
            if (!parentIsRight) {
                //parent is left
                uncle = gparent.right;
                //case 3 uncle is red
                if (uncle != null && !uncle.isBlack) {
                    uncle.isBlack = true;
                    parent.isBlack = true;
                    gparent.isBlack = false;
                    toAdd = gparent;
                } else{
                    // case4: uncle is black or null and toAdd is in right
                    if (parent.right == toAdd) {
                        // the current node is the parent now
                        toAdd = parent;
                        parent = rotateLeft(parent);
                        //after rotate, parent is still red, continue to rotate(case 5)
                    } else {
                        //case5: else uncle is black or null and toAdd is in left
                    	//re-color first
                        parent.isBlack = true;
                        gparent.isBlack = false;
                        rotateRight(gparent);
                    }
                }
            } else {
                //parent is right
                uncle = gparent.left;
                //case 3 uncle is red
                if (uncle != null && !uncle.isBlack) {
                    uncle.isBlack = true;
                    parent.isBlack = true;
                    gparent.isBlack = false;
                    toAdd = gparent;
                } else{
                    if (parent.left == toAdd) {
                        // case4: uncle is black or null and toAdd is in left
                        toAdd = parent;
                        parent = rotateRight(parent);
                    } else{
                        //case5: else uncle is black or null and toAdd is in right
                        parent.isBlack = true;
                        gparent.isBlack = false;
                        rotateLeft(gparent);
                    }
                }
            }
        }
	}
	/**
	 * perform left rotation on a designated node.
	 * @param node designated node that will be rotated
	 * @return new root of subtree
	 */
	private RBNode rotateLeft(RBNode node){
		//rotate parent, right child is toAdd actually
		//and it will be new root
		RBNode newRoot = node.right;
		node.right = newRoot.left;
		newRoot.left = node;
		//check node is a left or right child, then 
		//set the parent node
        if (node.parent != null) {
            boolean nodeIsRight = false;
            if (node.parent.right == node)
                nodeIsRight = true;
            if (nodeIsRight) {
                node.parent.right = newRoot;
            } else {
                node.parent.left = newRoot;
            }
        }
        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (node == root) {
            root = newRoot;
        }
        return newRoot;
	}
	/**
	 * perform right rotation on a designated node.
	 * @param node designated node that will be rotated
	 * @return new root of subtree
	 */
	//just the mirror of left rotation
    private RBNode rotateRight(RBNode node) {
        RBNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        if (node.parent != null) {
            boolean nodeIsRight = false;
            if (node.parent.right == node) {
                nodeIsRight = true;
            }
            if (nodeIsRight) {
                node.parent.right = newRoot;
            } else {
                node.parent.left = newRoot;
            }
        }
        newRoot.parent = node.parent;
        node.parent = newRoot;
        if (node == root) {
            root = newRoot;
        }
        return newRoot;
    }
    /**
     * return current size of red-black tree.
     * @return size of the tree
     */
    public int size() {
        return count;
    }

    /**
     * check if the tree is empty or not. O(1).
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        return count == 0 && root == null;
    }
    /**
     * returns the maximum node (based on ID) held in the tree. null if tree is empty. O(log n).
     * @return maximum RBNode or null if empty
     */
    public RBNode max() {
        if (isEmpty()) {
            return null;
        }
        RBNode currentNode = root;
        while (currentNode.right != null) {
            currentNode = currentNode.right;
        }
        return currentNode;
    }
    /**
     * find max node (based on ID) of the given sub-root.
     * @param current the given sub-root
     * @return the max node
     */
    private  RBNode max(RBNode current){
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    /**
     * returns the minimum node (based on ID) held in the tree. null if tree is empty. O(log n).
     * @return minimum RBNode or null if empty
     */
    public RBNode min() {
        if (isEmpty()) {
            return null;
        } else {
            RBNode currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return currentNode;
        }
    }
    /**
     * find min node (based on ID) of the given sub-root.
     * @param currentNode the given sub-root
     * @return the min node
     */
    private  RBNode min(RBNode currentNode){
    	while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode;
    }
    /**
     * given an event id, check whether or not it is in the tree.
     * @param id the given id, should be positive
     * @return true if it is in the tree, false otherwise
     */
    public boolean contains(int id) {
        if (id <= 0) {
            return false;
        }
        if (isEmpty()) {
            return false;
        } else {
            return ifContains(root, id);
        }
    }
    /**
     * helper method to find the node with given event id.
     * @param currentNode the current sub root
     * @param id the given event id
     * @return true if it is in the tree, false otherwise 
     */
    private boolean ifContains(RBNode currentNode, int id) {
        if (currentNode != null) {
            if (currentNode.ID == id) {
                return true;
            } else if (currentNode.ID > id) {
                return ifContains(currentNode.left, id);
            } else {
                return ifContains(currentNode.right, id);
            }
        }
        return false;
    }
    /**
     * find the node that with given id.
     * @param id the event id, it should be positive
     * @return returns the desired node with id, or null if there is no such node
     */
    public RBNode find(int id) {
        if (id <= 0) {
            return null;
        }
        if (isEmpty()) {
            return null;
        } else {
            return findHelper(root, id);
        }
    }
    /**
     * helper method that finds the node with given ID.
     * @param currentNode the current sub root
     * @param id the given id
     * @return the node that with the given id, or null if there is no such node with given id
     */
    private RBNode findHelper(RBNode currentNode, int id) {
        if (currentNode != null) {
            if (id == currentNode.ID) {
                return currentNode;
            } else if (id < currentNode.ID) {
                return findHelper(currentNode.left, id);
            } else {
                return findHelper(currentNode.right, id);
            }
        }
        return null;
    }
    /**
     * remove the event with given id.
     * @param id the given event id to be deleted
     * @return true if it is removed, false if it is not
     */
    public boolean remove(int id) {
    	//don't accept illegal id, or delete on an empty tree, 
    	//or if node with given id does not exist
        if (id <= 0) return false;
        if (isEmpty()) {
            return false;
        } else if (!contains(id)) {
            return false;
        }
        count--;
        RBNode toDelete = find(id);
        removeHelper(toDelete);
//        System.out.println(node.ID+","+node.eventCount);
        return true;
    }
    /**
     * remove current node on the tree.
     * @param toRemove the node that will be removed
     * @return the node which is actually deleted
     */
    private  RBNode removeHelper(RBNode toRemove){
        RBNode toDelete;
        //toFix is the child node
        RBNode toFix;
        
        if(toRemove.left == null || toRemove.right == null ){
            toDelete = toRemove;
        } else {
            //if it is not a leaf, we need it predecessor
            toDelete = max(toRemove.left);
        }

        if(toDelete.right != null){
            toFix = toDelete.right;
        } else {
            toFix = toDelete.left;
        }
        //don't care if leaf node
        RBNode parentNode = toDelete.parent;
        if(toFix!= null){
            toFix.parent = toDelete.parent;
        }
        if(toDelete.parent == null){
            root = toFix;
        } else {
            if( toDelete.parent.left == toDelete){
                toDelete.parent.left = toFix;
            } else{
                toDelete.parent.right = toFix;
            }
        }
        
        //set the parent pointer
        if(toRemove != toDelete){
            toRemove.ID = toDelete.ID;
            toRemove.eventCount = toDelete.eventCount;
        }
        if(toDelete.isBlack){
            fixRemove(toFix, parentNode);
        }
        return toDelete;
    }
    /**
     * fix red black tree property after deletion (if we delete a black node).
     * @param node the node that need to be fixed
     * @param parentNode the parent node that need to be fixed
     */
    private void fixRemove(RBNode node,RBNode parentNode) {
        RBNode brother;
        //if node is red, just color it black, we done
        //if node is null, we deleted a black node, we can't be done
        //if node is root, just color it black, we done
        while ((node == null || node.isBlack) && (node != root )) {
            //Case 1,2 return
            if(parentNode.left == node){
                brother = parentNode.right;
                if(brother != null && !brother.isBlack){
                    //Case 3: brother is red
                    brother.isBlack = true;
                    parentNode.isBlack = false;
                    rotateLeft(parentNode);
                    brother = parentNode.right;
                    //change to case 4
                }
                if( brother == null ||((brother.left == null || brother.left.isBlack)
                        && (brother.right == null || brother.right.isBlack))){
                    //case 4: node, brother, sons of brother are all black
                    if(brother!=null){
                        brother.isBlack = false;
                    }
                    node = parentNode;
                    parentNode = node.parent;
                }else{
                    //case 5: node is black, brother is black, brother left is red, right is black
                    if((brother.right == null || brother.right.isBlack)){
                        brother.left.isBlack = true;
                        brother.isBlack = false;
                        rotateRight(brother);
                        brother = parentNode.right;
                    }
                    //case 6: node is black, brother is black, brother right is red
                    brother.isBlack = parentNode.isBlack;
                    parentNode.isBlack = true;
                    if(brother.right != null)	brother.right.isBlack = true;
                    rotateLeft(parentNode);
                    node = root;
                }
            } else {
                brother = parentNode.left;
                if(brother != null &&!brother.isBlack){
                    //Case 3: brother is red
                    brother.isBlack = true;
                    parentNode.isBlack = false;
                    rotateRight(parentNode);
                    brother = parentNode.left;
                    //change to case 4
                }
                if(brother == null ||((brother.left == null || brother.left.isBlack)
                        && (brother.right == null || brother.right.isBlack))){
                    //case 4: node, brother, sons of brother are all black
                    if(brother!=null){
                        brother.isBlack = false;
                    }
                    node = parentNode;
                    parentNode = node.parent;
                }else{
                    //case 5: node is black, brother is black, brother left is black, right is red
                    if((brother.left == null || brother.left.isBlack)){
                        brother.right.isBlack = true;
                        brother.isBlack = false;
                        rotateLeft(brother);
                        brother = parentNode.left;
                    }
                    //case 6: node is black, brother is black, brother left is red
                    brother.isBlack = parentNode.isBlack;
                    parentNode.isBlack = true;
                    if(brother.left != null)	brother.left.isBlack = true;
                    rotateRight(parentNode);
                    node = root;
                }
            }
        }
        if(node != null){
            node.isBlack = true;
        }
    }
    /**
     * increase the count of the event theID by m. If theID is not present, insert it.
	   Non-positive id will yield no result, a null will be returned.
	   A negative m will decrease the corresponding event and may cause event remove.
     * @param id the given id
     * @param m the increasing number
     * @return the successful updated RBNode, or null if given parameter is improper
     */
    public RBNode increase(int id, int m){
    	if(id <= 0){
    		return null;
    	}
    	if(m < 0)
    		return decrease(id, -m);
    	if(this.contains(id)){
    		RBNode node = this.find(id);
    		node.eventCount += m;
    		return node;
    	}else{
    		this.add(id, m);
    		RBNode node = find(id);
    		return node;
    	}
    }
    /**
     * decrease the count of theID by m. If theID��s count becomes less than or equal to 0, remove
	   theID from the counter.
     * @param id the given id
     * @param m the decreasing number
     * @return the successful updated RBNode, or null if given parameter is improper or be deleted
     */
    public RBNode decrease(int id, int m){
    	if(id <= 0){
    		return null;
    	}
    	if(m < 0)
    		return increase(id, -m);
    	if(this.contains(id)){
    		RBNode node = this.find(id);
    		node.eventCount -= m;
    		if(node.eventCount <= 0){
    			this.remove(id);
    			return null;
    		}else{
    			return node;
    		}
    	}else{
    		return null;
    	}
    }
    /**
     * get the root of the red black tree.
     * @return the root of the red black tree
     */
    public RBNode getRoot(){
    	return root;
    }
    /**
     * given a range, find all the RBNode which represents event within that range, inclusively.
     * ID1 must be less or equal to ID2
     * @param ID1 given range left bound
     * @param ID2 given range right bound
     * @param subroot the root or sub root which is wished to start searching
     * @param res the result will be store into this given List
     */
    public void rangeSearch(int ID1, int ID2, RBNode subroot, List<RBNode> res){
    	if(ID1 > ID2){
    		return;
    	}
    	//find approximate range, not implement yet
    	if(subroot == null)
    		return;
    	if(ID1 < subroot.ID){
    		rangeSearch(ID1, ID2, subroot.left, res);
    	}
    	if(ID1 <= subroot.ID && ID2 >= subroot.ID){
    		res.add(subroot);
    	}
    	if(ID2 > subroot.ID){
    		rangeSearch(ID1, ID2, subroot.right, res);
    	}
    }
    /**
     * no matter id is presented in tree or not, return the next node with bigger ID than given id but smallest than other
     * @param id the given id
     * @param root the root of the tree
     * @return a Integer array that with 2 elements, first is corresponding ID, second is event count; or null if it doesn't exits
     */
    public int[] findNext(int id, RBNode root){
    	int[] val = new int[2];
    	while(root != null){
    		if(root.ID < id)
    			root = root.right;
    		else if(root.ID == id){
    			RBNode node = this.successor(id);
    			val[0] = node.ID;
    			val[1] = node.eventCount;
    			return val;
    		}
    		else{
    			val[0] = root.ID;
    			val[1] = root.eventCount;
    			root = root.left;
    		}
    	}
    	return val;
    }
    /**
     * no matter id is presented in tree or not, return the previous node with smaller ID than given id but largest than other
     * @param id the given id
     * @param root the root of the tree
     * @return a Integer array that with 2 elements, first is corresponding ID, second is event count; or null if it doesn't exits
     */
    public int[] findPrev(int id, RBNode root){
    	int[] val = new int[2];
    	while(root != null){
    		if(root.ID > id)
    			root = root.left;
    		else if(root.ID == id){
    			RBNode node = this.predecessor(id);
    			val[0] = node.ID;
    			val[1] = node.eventCount;
    			return val;
    		}
    		else{
    			val[0] = root.ID;
    			val[1] = root.eventCount;
    			root = root.right;
    		}
    	}
    	return val;
    }
    /**
     * find and return given id's successor.
     * @param id the given id
     * @return the successor node, or null if there is no successor
     */
    public RBNode successor(int id){
    	if(id <= 0 || !this.contains(id)){
    		return null;
    	}
    	RBNode node = this.find(id);
    	if(node.right != null){
    		return this.min(node.right);
    	}
    	RBNode y = node.parent, x = node;
    	while(y != null && y.right == x){
    		x = y;
    		y = y.parent;
    	}
    	return y;
    }
    /**
     * find and return given id's predecessor.
     * @param id the given id
     * @return the predecessor node, or null if there is no predecessor
     */
    public RBNode predecessor(int id){
    	if(id <= 0 || !this.contains(id)){
    		return null;
    	}
    	RBNode node = this.find(id);
    	if(node.left != null){
    		return this.max(node.left);
    	}
    	RBNode y = node.parent, x = node;
    	while(y != null && y.left == x){
    		x = y;
    		y = y.parent;
    	}
    	return y;
    }
    /**
     * reset the red black tree.
     */
    public void clear(){
    	root = null;
    	count = 0;
    }
    /**
     * level order traversing the red black tree, fill a list with each level's node.
     * @param list the input list that is wished to be filled
     */
    public void levelOrderTraverse(List<List<RBNode>> list){
    	if(root == null)
    		return;
    	Deque<RBNode> q = new LinkedList<>();
    	q.offer(root);
    	while(!q.isEmpty()){
    		int size = q.size();
    		List<RBNode> l = new ArrayList<>();
    		for(int i = 0; i < size; i++){
    			RBNode node = q.poll();
    			l.add(node);
    			if(node.left != null){
    				q.offer(node.left);
    			}
    			if(node.right != null){
    				q.offer(node.right);
    			}
    		}
    		list.add(l);
    	}
    }
    /**
     * in order traverse test method to test the sortedness correction of the tree.
     * @param list the input list that will be filled by tree node in order
     * @param root the subroot that is wished to start traversing
     */
    public void inOrderTraverse(List<RBNode> list, RBNode root){
    	if(root == null)
    		return;
    	inOrderTraverse(list, root.left);
    	list.add(root);
    	inOrderTraverse(list, root.right);
    	
    }
    /**
     * test method: check if the black height property is maintained.
     * @return the number of black height of all the path, if it is not 1, then indicating problem
     */
    public int blackHeightCheck(){
    	if(root == null)
    		return 1;
    	Set<Integer> set = new HashSet<Integer>();
    	heightCheckHelper(root, 1, set);
//    	System.out.print(set);
    	return set.size();
    }
    /**
     * helper function to help check the black height property
     * @param root the current subroot
     * @param h the current level's black height (from root)
     * @param set a set that contains different black height, 
     * since set doesn't allow duplication, if black height is unique, set size should be 1
     */
    private void heightCheckHelper(RBNode root, int h, Set<Integer> set){
    	if(root == null){
    		set.add(h);
    		return;
    	}
    	int h2 = h;
    	if(root.isBlack == true)
    		h2++;
    	heightCheckHelper(root.left, h2, set);
    	heightCheckHelper(root.right, h2, set);
    }
}

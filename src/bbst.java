import java.io.*;
import java.util.*;

public class bbst{
	
	
	public static void main(String[] args) throws Exception{
		
		if(args.length != 1){
			System.exit(-1);
		}
	
		String filename = System.getProperty("user.dir")+File.separator+args[0];

		File f = new File(filename);
		
		BufferedReader in = new BufferedReader(new FileReader(f));
		BufferedReader inQuery = new BufferedReader(new InputStreamReader(System.in));
		
		String s;
		s = in.readLine();
		int len = Integer.parseInt(s);
		int[][] events = new int[Integer.parseInt(s)][2];
		
		int idx = 0;
		while((s = in.readLine()) != null){
			String[] pair = s.split(" ");
			int id = Integer.parseInt(pair[0]);
			int count = Integer.parseInt(pair[1]);
			events[idx][0] = id;
			events[idx][1] = count;
			idx++;
		}
		
		RedBlackTree rBTree = new RedBlackTree();
		rBTree.buildRedBlackTree(events);
		
		RBNode root = rBTree.getRoot();
		List<RBNode> l = new ArrayList<>();
		
		
		while(true){
			String query = inQuery.readLine();
			if(query == null)	break;
			String[] parts = query.split(" ");
			if(parts.length == 1 && parts[0].equals("quit"))
				break;
			else if(parts.length == 2){
				if(parts[0].equals("count")){
					int id = Integer.parseInt(parts[1]);
					int c = rBTree.find(id).eventCount;
					System.out.println(c);
				}else if(parts[0].equals("next")){
					int id = Integer.parseInt(parts[1]);
					int[] next = rBTree.findNext(id, rBTree.getRoot());
					System.out.println(next[0] + " " + next[1]);

				}else if(parts[0].equals("previous")){
					int id = Integer.parseInt(parts[1]);
					int[] prev = rBTree.findPrev(id, rBTree.getRoot());
					System.out.println(prev[0] + " " + prev[1]);
				}else{
					System.out.println(query+" is not a standard query asked in this project!");
				}
			}else if(parts.length == 3){
				if(parts[0].equals("reduce")){
					int id = Integer.parseInt(parts[1]);
					int m = Integer.parseInt(parts[2]);
					RBNode reduce = rBTree.decrease(id, m);
					if(reduce !=null && reduce.eventCount > 0){
						System.out.println(reduce.eventCount);
					}else{
						System.out.println(0);
					}
				}else if(parts[0].equals("increase")){
					int id = Integer.parseInt(parts[1]);
					int m = Integer.parseInt(parts[2]);
					int c = rBTree.increase(id, m).eventCount;
					System.out.println(c);

				}else if(parts[0].equals("inrange")){
					int id1 = Integer.parseInt(parts[1]);
					int id2 = Integer.parseInt(parts[2]);
					rBTree.rangeSearch(id1, id2, root, l);
					System.out.println(sum(l));
					l.clear();
				}else{
					System.out.println(query+" is not a standard query asked in this project!");
				}
			}else{
				System.out.println(query+" is not a standard query asked in this project!");
			}

		}
		System.out.println();	

		in.close();
		inQuery.close();
	}
	private static int sum(List<RBNode> l){
		int sum = 0;
		for(RBNode r : l){
			sum += r.eventCount;
		}
		return sum;
	}
}

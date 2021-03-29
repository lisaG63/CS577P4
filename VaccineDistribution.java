import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class VaccineDistribution {
	
	public class Edge implements Comparable<Edge> {
		
		public int v1;
		public int v2;
		public int w;
		
		public Edge(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.w = w;
		}

		@Override
		public int compareTo(Edge e) {
			return e.w - this.w;
		}
		
	}
	
	private static int MST(ArrayList<Edge> graph, int pc, int vc) {
		Collections.sort(graph);
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < vc; i ++) {
			adj.add(new ArrayList<Integer>());
		}
		for (Edge e : graph) {
			adj.get(e.v1).add(e.v2);
			adj.get(e.v2).add(e.v1);
		}
		
		for (Edge e : graph) {
			adj.get(e.v1).removeIf(v -> v == e.v2);
			adj.get(e.v2).removeIf(v -> v == e.v1);
			if (!connected(adj, pc, vc)) {
				return e.w;
			}
		}
		return -1;
	}
	
	private static boolean connected(ArrayList<ArrayList<Integer>> adj, int pc, int vc) {
		boolean visited[] = new boolean[vc];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int count = 0;
		for(int i = 0; i < visited.length; i ++) {
			if(!visited[i]) {
				count ++;
				if (count > pc) return false;
				queue.add(i);
				visited[i] = true;
				
				while(!queue.isEmpty()) {
					int u = queue.remove();
					for (int j = 0; j < adj.get(u).size(); j ++) {
						int cur =adj.get(u).get(j);
						if (!visited[cur]) {
							visited[cur] = true;
							queue.add(cur);
						}
					}
				}
			}
		}
		return true;
		
	}
	
	


	public static void main(String[] args) {
		VaccineDistribution vaccine = new VaccineDistribution();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] numbers = bf.readLine().split(" ");
			int vc = Integer.parseInt(numbers[0]);
			int pc = Integer.parseInt(numbers[1]);
			int edges = Integer.parseInt(numbers[2]);
			ArrayList<Edge> graph = new ArrayList<Edge>();
			for (int i = 0; i < edges; i ++) {
				String[] edge = bf.readLine().split(" ");
				VaccineDistribution.Edge e = vaccine.new Edge(Integer.parseInt(edge[0]) - 1, Integer.parseInt(edge[1]) - 1, Integer.parseInt(edge[2]));
				graph.add(e);
			}
			
			
			
			System.out.println(MST(graph, pc, vc));

	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

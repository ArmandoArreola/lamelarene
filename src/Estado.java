
import java.util.HashMap;
public class Estado {
	private int information[][]=new	 int[3][3];
	private HashMap<Operadores, Estado> connections = new HashMap<Operadores, Estado>();
	private boolean visited=false;
	public Estado(int information[][]){
		this.information=information;
	}
	public void setConnections(Operadores operador, Estado estado){
		if(!this.connections.containsValue(estado)){
			this.connections.put(operador, estado);
		}else{
			System.out.println("Estado: "+estado.information.toString()+" ya existe.");
		}
	}
	public void setVisited(){//visited
		visited=true;
	}
	public int[][] getInformation(){
		return this.information;
	}
	public HashMap<Operadores, Estado> getConnections(){
		return this.connections;
	}
	public boolean getVisited(){
		return this.visited;
	}
	public void printInformation(){
		for (int[] info : information) {
			for (int i : info) {
				System.out.print(i+",");
			}
			System.out.println();
		}
	}
}

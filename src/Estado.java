
import java.util.HashMap;
public class Estado {
	private int information[][]=new	 int[3][3];
	private HashMap<Operadores, Estado> connections = new HashMap<Operadores, Estado>();
	private boolean visited=false;
	public Estado(int information[][]){
		this.information=information;
	}
	public void setConnections(Operadores operador, Estado estado){
		//if(!this.connections.containsValue(estado)){
			this.connections.put(operador, estado);
/*		}else{
			System.out.println("Estado: "+estado.information.toString()+" ya existe.");
		}*/
	}
	public void setVisited(boolean visited){//visited
		this.visited=visited;
	}
	public int[][] getInformation(){
		int[][] info=new int[3][3];
		for (int i=0;i<this.information.length;i++) {
			for(int j=0;j<this.information[i].length;j++){
				info[i][j]=this.information[i][j];
			}
		}
		return info;
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

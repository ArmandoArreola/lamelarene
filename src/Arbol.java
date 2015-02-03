import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;


public class Arbol {
	private Estado raiz;
	
	public Arbol(Estado raiz){
		this.raiz=raiz;
	}
	
	public Estado getRaiz(){
		return raiz;
	}
	//public void addEstado(){}
	public Estado generarEstado(Operadores operador, Estado estado){ //Genera un estado que es el resultado de aplicar el operador al estado que mandamos
		int[][] info=estado.getInformation();
		int position=findZero(info);
		int row=position/info.length;//fila
		int grid=position%info.length;//clumna
		switch(operador){
			case ARRIBA:
				if(position<(info.length*info.length)-info.length){// para que un numero pueda bajar tiene que ser su posicion menor que 6
					int numMovido=info[row+1][grid];
					info[row+1][grid]=0;
					info[row][grid]=numMovido;
				}
				break;
			case ABAJO:
				if(position>=info.length){
					int numMovido=info[row-1][grid];
					info[row-1][grid]=0;
					info[row][grid]=numMovido;
				}
				break;
			case IZQUIERDA:
				if(position%info.length!=0){
					int numMovido=info[row][grid+1];
					info[row][grid+1]=0;
					info[row][grid]=numMovido;
				}
				break;
			case DERECHA:
				if(position+1%info.length!=0){
					int numMovido=info[row][grid-1];
					info[row][grid-1]=0;
					info[row][grid]=numMovido;
				}
				break;
		}
		Estado estadoResultado=new Estado(info);
		return estadoResultado;
	}
	public boolean estadoValido(Estado estado){ //Regresa verdadero o falso si el estado es valido o inválido
		return false;
	}
	public Estado estadoRepetido(Estado nuevo){ //Regresa un estado; el mísmo si es un estado nuevo y el estado original si no es nuevo.
		Stack<Estado> stack=new Stack<Estado>();
		stack.add(raiz);
		while(!stack.isEmpty()){
			Estado estado=stack.pop();
			if(estado==nuevo){
				estado.setVisited();
				return estado;
			}
			HashMap<Operadores, Estado> connections=estado.getConnections();
			Collection<Estado> valores=connections.values();
			Object[] valoresArray=valores.toArray();
			for(int i=0;i<valores.size();i++){
				stack.push((Estado) valoresArray[i]);
			}
		}
		return nuevo;
	}
	public void imprimir(Estado raiz) { // Metodo para imprimir todo
		if (raiz != null) {
			HashMap<Operadores, Estado> connections=raiz.getConnections();
			if(!connections.isEmpty()){
				
			}
			raiz.printInformation();
		
		}
	}
	/*Return the position of the number zero staring from zero*/
	private int findZero(int[][] information){
		int i,j;
		int k=0;
		for (i=0;i<information.length;i++) {
			for(j=0;j<information[i].length;j++){
				if(information[i][j]==0){
					return k; 
				}
				k++;
			}
			
		}
		System.out.println("no se encontro el cero!!! U.U");
		return -1;
	}
	public void add(Estado estado,Estado nuevo,Operadores camino){
		estado.setConnections(camino, nuevo);
	}
	public Estado llenarArbol(Estado padre,Operadores operador){
		Estado hijo=generarEstado(operador,padre);
		hijo=estadoRepetido(hijo);
		padre.setConnections(operador, hijo);
		//izquierda,abajo,arriba,derecha
		if(hijo.getVisited()){
			return hijo;
		}
		switch(operador){
		case IZQUIERDA:
			llenarArbol(hijo,Operadores.IZQUIERDA);
		case ABAJO:
			llenarArbol(hijo,Operadores.ABAJO);
		case ARRIBA:
			llenarArbol(hijo,Operadores.ARRIBA);
		case DERECHA:
			llenarArbol(hijo,Operadores.DERECHA);
		}
		return null;
	}

}

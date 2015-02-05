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
					try{
						int numMovido=info[row+1][grid];
						info[row+1][grid]=0;
						info[row][grid]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				}
				break;
			case ABAJO:
				if(position>=info.length){
					try{
						int numMovido=info[row-1][grid];
						info[row-1][grid]=0;
						info[row][grid]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				}
				break;
			case IZQUIERDA:
				if(position%info.length!=0){
					try{
						int numMovido=info[row][grid+1];
						info[row][grid+1]=0;
						info[row][grid]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				}
				break;
			case DERECHA:
				if(position+1%info.length!=0){
					try{
						int numMovido=info[row][grid-1];
						info[row][grid-1]=0;
						info[row][grid]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				}
				break;
		}
		Estado estadoResultado=new Estado(info);
		return estadoResultado;
	}
	public boolean compare(int[][] estado1,int[][] estado2){ //Regresa verdadero o falso si el estado es valido o inválido
		for(int i=0;i<estado1.length;i++){
			for(int j=0;j<estado1[i].length;j++){
				if(estado1[i][j]!=estado2[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	public Estado estadoRepetido(Estado nuevo){ //Regresa un estado; el mísmo si es un estado nuevo y el estado original si no es nuevo.
		Stack<Estado> stack=new Stack<Estado>();
		stack.add(raiz);
		if(nuevo==null)
		{
			return null;
		}
		while(!stack.isEmpty()){
			Estado estado=stack.pop();
			if(compare(estado.getInformation(),nuevo.getInformation())){
				estado.setVisited(true);
				return null;
			}
			HashMap<Operadores, Estado> connections=estado.getConnections();
			Collection<Estado> valores=connections.values();
			Object[] valoresArray=valores.toArray();
			for(int i=0;i<valores.size();i++){
				if(valoresArray[i]!=null){
					stack.push((Estado) valoresArray[i]);
				}
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
		Estado raiz2=getRaiz();
		hijo=estadoRepetido(hijo);
		padre.setConnections(operador, hijo);
		//izquierda,abajo,arriba,derecha
		if(hijo==null||hijo.getVisited()){
			try{
				hijo.setVisited(false);
			}catch(Exception nul){}
			return null;
		}
		switch(operador){
		case IZQUIERDA:
			llenarArbol(hijo,Operadores.IZQUIERDA);
		case ABAJO:
			llenarArbol(hijo,Operadores.ABAJO);
		case DERECHA:
			llenarArbol(hijo,Operadores.DERECHA);
		case ARRIBA:
			llenarArbol(hijo,Operadores.ARRIBA);
		}
		return null;
	}

}

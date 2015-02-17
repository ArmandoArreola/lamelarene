import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;


public class Arbol {
	private Estado raiz;
	int[][] prueba={{4,1,5},{2,6,3},{0,7,8}};
	public Arbol(Estado raiz){
		this.raiz=raiz;
	}
	
	public Estado getRaiz(){
		return raiz;
	}
	//public void addEstado(){}
	public Estado generarEstado(Operadores operador, Estado estado){ //Genera un estado que es el resultado de aplicar el operador al estado que mandamos
		int[][] info=estado.getInformation();
		int position=findNum(info,0);
		int fila=position/info.length;//fila
		int columna=position%info.length;//clumna
		switch(operador){
			case ARRIBA:
				//if(position<(info.length*info.length)-info.length){// para que un numero pueda bajar tiene que ser su posicion menor que 6
					if(fila+1==3){
						return null;
					}else
					try{
						int numMovido=info[fila+1][columna];
						info[fila+1][columna]=0;
						info[fila][columna]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				//}
				break;
			case ABAJO:
				//if(position>=info.length){
					if(fila-1==-1){
						return null;
					}else
					try{
						int numMovido=info[fila-1][columna];
						info[fila-1][columna]=0;
						info[fila][columna]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				//}
				break;
			case IZQUIERDA:
				//if(position%info.length!=0){
					if(columna+1==3){
						return null;
					}else
					try{
						int numMovido=info[fila][columna+1];
						info[fila][columna+1]=0;
						info[fila][columna]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				//}
				break;
			case DERECHA:
			//	if(position+1%info.length!=0){
					if(columna-1==-1){
						return null;
					}else
					try{
						int numMovido=info[fila][columna-1];
						info[fila][columna-1]=0;
						info[fila][columna]=numMovido;
					}catch(Exception noSePuedeMover){
						return null;
					}
				//}
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
		Estado upadre = null;
		while(!stack.isEmpty()){
			Estado estado=stack.pop();
			if(compare(estado.getInformation(),nuevo.getInformation())){
				estado.setVisited(true);
				return estado;//aqui es el pobrema
			}
			HashMap<Operadores, Estado> connections=estado.getConnections();
			Collection<Estado> valores=connections.values();
			Object[] valoresArray=valores.toArray();
			for(int i=0;i<valores.size();i++){
				if(valoresArray[i]!=null){
					if (upadre!=null){
					Estado hijo =(Estado) valoresArray[i];
					if(!compare(hijo.getInformation(),upadre.getInformation())){
						//if(!compare(hijo.getInformation(),raiz.getInformation()))
						stack.push((Estado) valoresArray[i]);
					}
				}else
					stack.push((Estado) valoresArray[i]);
				}
				
				upadre =estado;
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
	private int findNum(int[][] information, int num){
		int i,j;
		int k=0;
		for (i=0;i<information.length;i++) {
			for(j=0;j<information[i].length;j++){
				if(information[i][j]==num){
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
		//Estado raiz2=getRaiz();
		if(hijo!=null){
			if(compare(hijo.getInformation(),prueba))
			{
				System.out.println("Valio verga");
			}
		}
		hijo=estadoRepetido(hijo);
		if(hijo!=null){
			padre.printInformation();
			System.out.println(operador);
			hijo.printInformation();
		}else{System.out.println(operador+"NULL");}
		padre.setConnections(operador, hijo);
		//izquierda,abajo,arriba,derecha
		if(hijo==null||hijo.getVisited()){
			
			return null;
		}

			llenarArbol(hijo,Operadores.IZQUIERDA);
			llenarArbol(hijo,Operadores.ABAJO);
			llenarArbol(hijo,Operadores.DERECHA);
			llenarArbol(hijo,Operadores.ARRIBA);
			return null;
	}
	public void resolver(int num,Estado estado){
		int cuadranteZero=0;
		int cuadrante=0;
		int[][] info=estado.getInformation();
		int currentPosition=findNum(info,num);
		if(currentPosition!=num){
			int positionZero=findNum(info,0);
			if(positionZero==1||positionZero==2||positionZero==4||positionZero==5){
				cuadranteZero=1;
			}else if(positionZero==2||positionZero==3||positionZero==6||positionZero==5){
				cuadranteZero=2;
			}else if(positionZero==4||positionZero==5||positionZero==7||positionZero==8){
				cuadranteZero=3;
			}else if(positionZero==5||positionZero==6||positionZero==8||positionZero==9){
				cuadranteZero=4;
			}
			if(currentPosition==1||currentPosition==2||currentPosition==4||currentPosition==5){
				cuadrante=1;
			}else if(currentPosition==2||currentPosition==3||currentPosition==6||currentPosition==5){
				cuadrante=2;
			}else if(currentPosition==4||currentPosition==5||currentPosition==7||currentPosition==8){
				cuadrante=3;
			}else if(currentPosition==5||currentPosition==6||currentPosition==8||currentPosition==9){
				cuadrante=4;
			}
			
		}
	}
}

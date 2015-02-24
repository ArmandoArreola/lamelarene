import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Arbol {
	private Estado raiz;
	int[][] prueba={{4,1,5},{2,6,3},{0,7,8}};
	Stack<int[][]> estados=new Stack<int[][]>();
	private PrintWriter pw;// es el escritor de archivos
	private double estadosGenerados;
	public Arbol(Estado raiz, PrintWriter pw){
		this.raiz=raiz;
		this.pw=pw;
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
	public Estado estadoRepetidoStack(Estado nuevo, PrintWriter writer){// hace stack overflow NO UTILIZAR
		if (nuevo==null){
			return null;
		}
		int[][] num=nuevo.getInformation();
		if(!this.estados.contains(num)){
			estados.add(num);
			pw.println(nuevo.toCadena());// escribe en el archivo el nuevo estado
			return nuevo;
		}else{
			int index=this.estados.indexOf(num);
			int[][] infoVieja=this.estados.get(index);
			Estado viejo=findEstado(infoVieja);
			viejo.setVisited(true);
			return viejo;
		}
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
		estadosGenerados++;
		pw.println(estadosGenerados+nuevo.toCadena());
		return nuevo;
	}
	public Estado findEstado(int[][] information){
		Stack<Estado> stack=new Stack<Estado>();
		stack.add(raiz);

		Estado upadre = null;
		while(!stack.isEmpty()){
			Estado estado=stack.pop();
			if(compare(estado.getInformation(),information)){
				estado.setVisited(true);
				return estado;//aqui es el pobrema
			}else{
				HashMap<Operadores, Estado> connections=estado.getConnections();
				Collection<Estado> valores=connections.values();
				Object[] valoresArray=valores.toArray();
				for(int i=0;i<valores.size();i++){
					stack.push((Estado) valoresArray[i]);
				}
			}
		}
		return null;
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
				System.out.println("Valio");
			}
		}
		hijo=estadoRepetido(hijo);
		if(hijo!=null){
			System.out.println("PADRE");
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
	
	public void heuristica(Estado estadoFinal){
		//resuelve al estado final deseado
			int[][] infoFinal=estadoFinal.getInformation();
			Estado estadoActual=new Estado(raiz.getInformation());
			int numero;
			int x=0;
			while(!compare(estadoActual.getInformation(),infoFinal)){
				for(numero=1;numero<9;numero++){//que numero empezaras a mover, si ya esta en su lugar no se tiene que mover
					int j=findNum(estadoActual.getInformation(),numero);
					if(numero!=j){
						break;
					}
					numero++;
				}
				if(ceroSercaDeNumero(estadoActual,numero)){//estoy serca del numero?
						if(posicionNumeroMayorACero(estadoActual,numero)){//numero es mayor que cero
							//true
							estadoActual=hacerEspacio(estadoActual,numero);//hacer espacio
							//posicionarCeroEnPosicionMenorQueNumero(estadoActual,numero);//posicionarte menor que numero
							estadoActual=moverAposicionDeCero(estadoActual,numero);//movernumero a posicion de cero
						}else{//false
							estadoActual=moverAposicionDeCero(estadoActual,numero);//movernumero a posicion de cero
						}
				}else{//false
						estadoActual=acercarseANumero(estadoActual,numero);//acercarseNumero
				}
				x++;
			}
			System.out.println("fueron"+x+"pasos para llegar al objetivo");
		}

		private Estado acercarseANumero(Estado estadoActual, int numero) {
			int posCero;
			int posNum;
			posNum=findNum(estadoActual.getInformation(),numero);//3
			posCero=findNum(estadoActual.getInformation(),0);//4
			HashMap<Operadores, Estado> conections=estadoActual.getConnections();
			if(posNum<posCero){
				if(conections.get(Operadores.DERECHA)!=null||!conections.get(Operadores.DERECHA).getVisited()){
					return conections.get(Operadores.DERECHA);
				}else if(conections.get(Operadores.IZQUIERDA)!=null||!conections.get(Operadores.IZQUIERDA).getVisited()){
					estadoActual.setVisited(true);
					return conections.get(Operadores.IZQUIERDA);
				}else if(conections.get(Operadores.ARRIBA)!=null||!conections.get(Operadores.ARRIBA).getVisited()){
					estadoActual.setVisited(true);
					return conections.get(Operadores.ARRIBA);
				}
			}else{
				if(conections.get(Operadores.IZQUIERDA)!=null||!conections.get(Operadores.IZQUIERDA).getVisited()){
					return conections.get(Operadores.IZQUIERDA);
				}else if(conections.get(Operadores.DERECHA)!=null||!conections.get(Operadores.DERECHA).getVisited()){
					estadoActual.setVisited(true);
					return conections.get(Operadores.DERECHA);
				}else if(conections.get(Operadores.ABAJO)!=null||!conections.get(Operadores.ABAJO).getVisited()){
					estadoActual.setVisited(true);
					return conections.get(Operadores.ABAJO);
				}
			}
			System.out.println("ERROR EN ACERCARSE A NUMERO");
			return null;
		}

		private boolean posicionNumeroMayorACero(Estado estadoActual, int numero) {
			int posNum=findNum(estadoActual.getInformation(),numero);
			int posCero=findNum(estadoActual.getInformation(),0);
			if (posNum>posCero){
				return true;
			}else{
			return false;
			}
		}

		private boolean ceroSercaDeNumero(Estado estadoActual, int numero) {
			int posNum=findNum(estadoActual.getInformation(),numero);
			int posCero=findNum(estadoActual.getInformation(),0);
			switch(posNum){
				case 1:
					if(posCero==2||posCero==4){
						return true;
					}
					break;
				case 3:
					if(posCero==2||posCero==6){
						return true;
					}
					break;
				case 4:
					if(posCero==1||posCero==5||posCero==7){
						return true;
					}
					break;
				case 6:
					if(posCero==3||posCero==5||posCero==9){
						return true;
					}
					break;
				case 7:
					if(posCero==4||posCero==8){
						return true;
					}
					break;
				case 9:
					if(posCero==6||posCero==8){
						return true;
					}
					break;
				default://los del centro
					if(posNum==posCero+1||posNum==posCero-1||posNum==posCero+3||posNum==posCero-3){
						return true;
					}
			}
			return false;
		}
		
		private Estado hacerEspacio(Estado estadoActual,int numero) {
			int posNum=findNum(estadoActual.getInformation(),numero);
			int posCero=findNum(estadoActual.getInformation(),0);
			HashMap<Operadores, Estado> estados=estadoActual.getConnections();
			Estado nuevo;
			if(posNum==posCero-1){
				 nuevo=estados.get(Operadores.ABAJO);
				 estados=nuevo.getConnections();
				 return estados.get(Operadores.DERECHA);
			}
			if(posNum==posCero+1){
				 nuevo=estados.get(Operadores.ABAJO);
				 estados=nuevo.getConnections();
				 return estados.get(Operadores.IZQUIERDA);
			}
			if(posNum==posCero-3){
				 nuevo=estados.get(Operadores.IZQUIERDA);
				 estados=nuevo.getConnections();
				 return estados.get(Operadores.ABAJO);
			}
			return null;
		}

		private Estado posicionarCeroEnPosicionMenorQueNumero(Estado estadoActual,int numero) {
			int posNum=findNum(estadoActual.getInformation(),numero);
			int posCero=findNum(estadoActual.getInformation(),0);
			if(posNum==posCero-1){//mover izquierda
				return generarEstado(Operadores.IZQUIERDA, estadoActual);
			}else if(posNum==posCero-3){
				return generarEstado(Operadores.ARRIBA, estadoActual);//mover arriba
			}else{
				return null;//error
			}
		}

		private Estado moverAposicionDeCero(Estado estadoActual,int numero) {
			int posNum=findNum(estadoActual.getInformation(),numero);
			int posCero=findNum(estadoActual.getInformation(),0);
			HashMap<Operadores, Estado> estados=estadoActual.getConnections();
			if(posNum==posCero+1){
				return estados.get(Operadores.DERECHA);
			}
			if(posNum==posCero-1){
				return estados.get(Operadores.IZQUIERDA);
			}
			if(posNum==posCero+3){
				return estados.get(Operadores.ABAJO);
			}
			if(posNum==posCero-3){
				return estados.get(Operadores.ARRIBA);
			}
			return null;
		}

}

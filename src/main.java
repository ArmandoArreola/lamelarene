
public class main {
	public static void main(String[] args) {
		int[][] juego= {{4,1,5},{6,0,3},{2,7,8}};
		Estado estado=new Estado(juego);
		Arbol arbol = new Arbol(estado);
		System.out.println("INICIO");
		estado.printInformation();
		estado=arbol.generarEstado(Operadores.ARRIBA, estado);
		System.out.println("ARRIBA");
		estado.printInformation();
		estado=arbol.generarEstado(Operadores.ABAJO, estado);
		System.out.println("ABAJO");
		estado.printInformation();
		estado=arbol.generarEstado(Operadores.IZQUIERDA, estado);
		System.out.println("IZQUIERDA");
		estado.printInformation();
		estado=arbol.generarEstado(Operadores.DERECHA, estado);
		System.out.println("DERECHA");
		estado.printInformation();
		estado=arbol.estadoRepetido(estado);
		
		arbol.llenarArbol(arbol.getRaiz(), Operadores.IZQUIERDA);
		
	}
}

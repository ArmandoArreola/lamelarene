import java.io.FileWriter;
import java.io.PrintWriter;

public class main {
	public static void main(String[] args) {
		int[][] estadoInicial = { { 4, 1, 5 }, { 6, 0, 3 }, { 2, 7, 8 } };
		int[][] estadoFinal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Estado estado = new Estado(estadoInicial);
		estado.setVisited(true);
		
		/*
		 * System.out.println("INICIO"); estado.printInformation();
		 * estado=arbol.generarEstado(Operadores.ARRIBA, estado);
		 * System.out.println("ARRIBA"); estado.printInformation();
		 * estado=arbol.generarEstado(Operadores.ABAJO, estado);
		 * System.out.println("ABAJO"); estado.printInformation();
		 * estado=arbol.generarEstado(Operadores.IZQUIERDA, estado);
		 * System.out.println("IZQUIERDA"); estado.printInformation();
		 * estado=arbol.generarEstado(Operadores.DERECHA, estado);
		 * System.out.println("DERECHA"); estado.printInformation();
		 */
		// estado=arbol.estadoRepetido(estado);
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("C:/Users/carlosarmando/Desktop/prueba.txt");
			 pw = new PrintWriter(fichero);
			 Arbol arbol = new Arbol(estado,pw);
			arbol.llenarArbol(arbol.getRaiz(), Operadores.IZQUIERDA);
		} catch (Exception write) {
			System.out.println(write.getMessage());
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("Congrats!!");
	}
}

import java.io.FileWriter;
import java.io.PrintWriter;

public class main {
	public static void main(String[] args) {
		int[][] estadoInicial = { { 6, 1, 8 }, { 7, 5, 3 }, { 2, 0, 4 } };//{ { 4, 1, 5 }, { 6, 0, 3 }, { 2, 7, 8 } };
		int[][] estadoFinal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Estado estado = new Estado(estadoInicial);
		estado.setVisited(true);

		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(
					"C:/Users/carlosarmando/Desktop/prueba.txt");
			pw = new PrintWriter(fichero);
			Arbol arbol = new Arbol(estado, pw);
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

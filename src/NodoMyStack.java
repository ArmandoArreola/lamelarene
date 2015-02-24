
public class NodoMyStack {
	private Object value;
	public NodoMyStack myAnterior;
	
	public NodoMyStack(Object value){
		this.value=value;
	}
	public Object getValue(){
		return this.value;
	}
	public NodoMyStack getAnterior(){
		return this.myAnterior;
	}
}

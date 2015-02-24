
public class MyStack {
//lifo
	private NodoMyStack ultimo;
	
	public MyStack(){
	}
	public void push(Object nuevoUltimo){
		NodoMyStack nodo=new NodoMyStack(ultimo);
		if(this.ultimo!=null){
			nodo.myAnterior=this.ultimo;
		}
		this.ultimo=nodo;
	}
	public Object pop(){
		Object popable=this.ultimo.getValue();
		this.ultimo=this.ultimo.getAnterior();
		return popable;
	}
}

package implementations;

import api.PilaTDA;

public class PilaD implements PilaTDA {
	
	private class nodo{
		int dato;
		nodo sig;
	}
	nodo inicio;

	public void InicializarPila() {
		inicio = null;
	}


	public void Apilar(int x) {
		nodo nuevo = new nodo();
		nuevo.dato = x;
		if(this.PilaVacia()) { //si la lista estaba  vacia, primer nodos
			nuevo.sig = null;
			inicio = nuevo;
		}else {
		nuevo.sig = inicio;
		inicio = nuevo;
		}
	}


	public void Desapilar() {
		inicio = inicio.sig;
	}


	public boolean PilaVacia() {
		return inicio == null;
	}


	public int Tope() {
		return inicio.dato;
	}

}

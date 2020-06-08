package api;

public interface PilaTDA {
	void InicializarPila();
	// Siempre que la pila este iniciada
	void Apilar(int x);
	// Siempre que la pila no este vacia
	void Desapilar();
	// Siempre que esté inicializada
	boolean PilaVacia();
	// Siempre que no esté vacia.
	int Tope();
	

}

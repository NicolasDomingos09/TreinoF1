package view;

import java.util.concurrent.Semaphore;
import controller.Treino;

public class Main {

	public static void main(String[] args) {
		
		Semaphore pista = new Semaphore(5);
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 2; j++) {
				Thread carro = new Treino(pista, (j+1), (i+1));
				carro.start();
			}
		}
	}
}

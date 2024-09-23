package view;

import java.util.concurrent.Semaphore;
import controller.Carro;

public class Main {
	public static void main(String[] args) {
        
		Semaphore[] equipes = new Semaphore[7];
		Semaphore pista = new Semaphore(5);
		
		for (int i = 0; i < 7; i++) {
            equipes[i] = new Semaphore(1);
        }
		
		for (int i = 0; i < 14; i++) {
            new Thread(new Carro(i, equipes, pista)).start();
        }
	}
}

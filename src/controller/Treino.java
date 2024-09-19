package controller;

import java.util.concurrent.Semaphore;
import java.util.Random;
public class Treino extends Thread{
	
	private Semaphore semaforoPista;
	private static Semaphore[] equipes;
	
	private static int[][] matrizRank = new int[14][3];

	private int[] carro = new int[5];
	// Piloto // Equipe // Volta 1 // Volta 2 // Volta 3
	
	private Random rand = new Random();
	
	public Treino(Semaphore semaforoPista, int piloto, int equipe) {
		this.semaforoPista = semaforoPista;
		this.carro[0] = piloto; 
		this.carro[1] = equipe;
	}
	
	private void entradaDeCarros() {
		
	}
	
	private void correndo() {
		for(int i = 0; i < 3; i++) {
			int tempoVolta = rand.nextInt(3,30);
			
			try {
				sleep(tempoVolta);
				this.carro[(i+2)] = tempoVolta;
				System.out.println("O piloto " + this.carro[0] + " da equipe " + this.carro[1] + " completou a sua " + (i+1) + " a. volta em " + (tempoVolta/Math.pow(10, 3)) + " segundos");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			semaforoPista.acquire();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			semaforoPista.release();
		}
	}
}

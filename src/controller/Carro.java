package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Carro extends Thread{
	private int id;
    private int equipe;
    private Semaphore[] equipes;
    private Semaphore pista;
    private static int carros = 14;
    
    Random rand = new Random();
    
    private static int[][] temposVoltas = new int[14][2];

    public Carro(int id, Semaphore[] equipes, Semaphore pista) {
        this.id = id;
        this.equipe = id / 2;
        this.equipes = equipes;
        this.pista = pista;
    }
    
    private void correndo() {
    	System.out.println("Carro " + (id + 1) + " da equipe " + (equipe + 1) + " entrou na pista");
    	
    	int menorTempo = 10000;
        for (int volta = 0; volta < 3; volta++) {
            int tempoVolta = rand.nextInt(3,30);
            
            if (tempoVolta < menorTempo) { 
                menorTempo = tempoVolta;
                temposVoltas[id][0] = (id+1); 
                temposVoltas[id][1] = menorTempo;
            }
            
            System.out.println("Carro " + (id + 1) + " da equipe " + (equipe + 1) + " completou a volta " + (volta + 1) + " em " + tempoVolta + " segundos");
            try {
				sleep(tempoVolta*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
    
    private static void gridLargada() {
    	int[][] melhoresTempos = new int[14][2];
        for (int i = 0; i < 14; i++) {
            melhoresTempos[i][0] = temposVoltas[i][0];
            melhoresTempos[i][1] = temposVoltas[i][1]; 
        }
        melhoresTempos = ordenarMatriz(melhoresTempos);

        System.out.println("\nGrid de Largada:");
        for (int i = 0; i < 14; i++) {
            System.out.println((i + 1) + ". Carro " + melhoresTempos[i][0] + " - Tempo: " + melhoresTempos[i][1] + " segundos");
        }
    }

    private static int[][] ordenarMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length - 1; i++) {
            for (int j = i + 1; j < matriz.length; j++) {
                if (matriz[i][1] > matriz[j][1]) {

                    int[] temp = matriz[i];
                    matriz[i] = matriz[j];
                    matriz[j] = temp;
                }
            }
        }
        return matriz;
    }


    @Override
    public void run() {
        try {
            equipes[equipe].acquire();
            pista.acquire();
            correndo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
        	pista.release();
        	equipes[equipe].release();
            System.out.println("Carro " + (id+1) + " da equipe " + (equipe+1) + " saiu da pista");
            --carros;
        }
        if(carros <= 0) {
        	gridLargada();
        }
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptron;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Frankz
 */

/*
                Scanner leer = new Scanner(System.in);
        double[] muestra = new double[8];//Muestra de un paciente
        System.out.println("Ingres los 8 sintomas");
        String x;
        do {
            for (int i = 0; i < muestra.length; i++) {
                //Ingreso de la muestra
                muestra[i] = Double.parseDouble(leer.nextLine());
            }
            if (neurona.diagnosticarPaciente(muestra) == 1) {
                //Si el diagnostico es 1, está enfermo
                System.out.println("Tienes diabetes");
            } else {
                //Si no es 1, no está enfermo
                System.out.println("No tienes diabetes");
            }
            System.out.println("Deseas continuar. press \"s\"");
            x = leer.nextLine();
        } while (x.equals("s"));
*/
public class TestPerceptron {

    public static void main(String[] args) {
        //Creacion de objeto neurona
        Perceptron neurona = new Perceptron();        
        double []paciente1 = {6,148,72,35,0,33.6,0.627,50};
        double []paciente2 = {1,115,70,30,96,34.6,0.529,32};
        double []paciente3 = {8,99,84,0,0,35.4,0.388,50};
        System.out.println("Vector de pesos inicial:" + Arrays.toString(neurona.pesos));        
        neurona.entrenamiento();//entrenamiento de la neurona 
        System.out.println("Vector de pesos entrenado:" + Arrays.toString(neurona.pesos)); 
        System.out.println("Diagnostico de pacientes:");
        System.out.println("El paciente 1 tiene diabetes: " + estaEnfermo(neurona.diagnosticarPaciente(paciente1)));
        System.out.println("El paciente 2 tiene diabetes: " + estaEnfermo(neurona.diagnosticarPaciente(paciente2)));
        System.out.println("El paciente 3 tiene diabetes: " + estaEnfermo(neurona.diagnosticarPaciente(paciente3)));
    }
    
    public static boolean estaEnfermo(int clase){
        return clase == 1;
    }
}

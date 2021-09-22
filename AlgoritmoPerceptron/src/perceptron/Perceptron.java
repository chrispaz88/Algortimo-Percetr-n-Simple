/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptron;

import java.util.Arrays;

/**
 *
 * @author Frankz
 */
public class Perceptron {
    //Resultados de exámenes médicos anteriores
    //Muestras que serviran para el entrenamiento del algoritmo
    double[][]resultadosExamenesMedicos= {{6,148,72,35,0,33.6,0.627,50,1},                                            
                                            {1,85,66,29,0,26.6,0.351,31,0},
                                            {8,183,64,0,0,23.3,0.672,32,1},
                                            {1,89,66,23,94,28.1,0.167,21,0},
                                            {0,137,40,35,168,43.1,2.288,33,1},
                                            {5,116,74,0,0,25.6,0.201,30,0},
                                            {3,78,50,32,88,31,0.248,26,1},
                                            {10,115,0,0,0,35.3,0.134,29,0},
                                            {2,197,70,45,543,30.5,0.158,53,1},
                                            {8,125,96,0,0,0,0.232,54,1},
                                            {4,110,92,0,0,37.6,0.191,30,0},
                                            {10,168,74,0,0,38,0.537,34,1},
                                            {10,139,80,0,0,27.1,1.1441,57,0},
                                            {1,189,60,23,846,30.1,0.398,59,1},
                                            {5,166,72,19,175,25.8,0.587,51,1},
                                            {7,100,0,0,0,30,0.484,32,1},
                                            {0,118,84,47,230,45.8,0.551,31,1},
                                            {1,103,30,38,83,43.3,0.183,33,0},
                                            {1,115,70,30,96,34.6,0.629,32,1},
                                            {7,196,90,0,0,39.8,0.451,41,1}};
    
    //Vector de pesos
    double pesos[];
    /*Factor de aprendizaje del algoritmo:
    cte que determina la velocidad con la que la red aprende*/
    final double FACTOR_APRENDIZAJE = 0.1;
    //umbral
    double teta;
    
    //Constructor
    public Perceptron(){
        this.pesos = new double[8];//Establece el numero de pesos.
        this.generarPesos();//Genera aleatoriamente los pesos iniciales.
        teta = (Math.random()*-20)+10.0;//Asigna el umbral aleatoriamente
    }
    
    //Método para generar los pesos aleatorios iniciales
    public void generarPesos(){
        double random;
        //Se repite hasta llenar el vector de pesos.
        for (int i = 0; i < this.pesos.length; i++) {
            random = (Math.random()*-20)+10.0;//Genera numeros entre [-10,10]
            if(random != 0){
                //Si el numero es diferente de 0 se agrega a los pesos
                this.pesos[i] = random;
            }else{
            //Si es igual a cero se reduce el índice para volver a calcular
                i--;
            }         
        }
    }
    
    //Método para calcular la salida:sumatorio de muestras * sus pesos
    public double unionSumadora(int fila){
        double suma=0;
        //Se realiza con todos los pesos y datos de una muestra
        for (int i = 0; i < pesos.length; i++) {
            //sumatorio de cada peso*dato de muestra
            suma += pesos[i]*resultadosExamenesMedicos[fila][i];           
        }      
        suma = suma - teta;//Se resta el umbral
        return suma;
    }
    
    //Metodo para clasificar la clase
    public int clasificarClase(int fila){
        //Aplica la funcion de activacion
        if(unionSumadora(fila)>0){
            /*Si el resultado de la union sumadora es positivo
            Se le asigna 1*/
            return 1;
        }else{
            /*Si el resultado es negativo se le asigna 0*/
            return 0;
        }          
    }
    
    //Verificación de clase
    public boolean verificarClase(int fila){
        //Regresa true si coincide la clase esperada con la clase verdadera.
        return this.clasificarClase(fila) == this.resultadosExamenesMedicos[fila][8];
    }

    //Método para recalcular los nuevos pesos
    public void calcularNuevoPeso(int fila){
        //se obtiene la clase de la cada muestra*vector de pesos
        double claseEncontrada = clasificarClase(fila);
        //Se recalculan todos los pesos
        for (int i = 0; i < this.pesos.length; i++) {
            //nuevoPeso = pesoAnterior + factorAprendizaje*datoMuestra*error
            this.pesos[i] = this.pesos[i] + (this.FACTOR_APRENDIZAJE*this.resultadosExamenesMedicos[fila][i]*
                    (this.resultadosExamenesMedicos[fila][8]-claseEncontrada));                     
        }
        //Se calcula es nuevo umbral
        //nuevoUmbral = umbralAnterior - factorAprendizaje*error
        this.teta = this.teta - (this.FACTOR_APRENDIZAJE*(this.resultadosExamenesMedicos[fila][8]-claseEncontrada));
    }
   
    //!Importante
    //Método de entrenamiento de la neurona
    public void entrenamiento(){
        int fila=0;//Se inicia en la primera fila delas mustras y pesos
        boolean siCoincide=true;//Controla las repeticiones del bucle
        int repeticiones=0;
        do{       
            repeticiones++;
            if(!verificarClase(fila)){
                //si la clase obtenida no es igual a la clase esperada
                calcularNuevoPeso(fila); //Se ajustan nuevos pesos
                //Se vuelve a reiniciar el entrenamiento para los nuevos pesos
                fila=0;                
            }else{
                //Si la clase obtenida coincide con la clase esperada               
                fila++;//sigue con la siguiente fila
            }
            if(fila==this.resultadosExamenesMedicos.length){
                /*Si la fila es igual a la longitud de muestras
                se ha terminado de entrenar la neurona*/                
                siCoincide = false;//Se termina el ciclo
            }
        }while(siCoincide); 
        System.out.println("El total de repeticiones para encontrar los pesos fué"+repeticiones);
    }
        
    //Metodo para diagnosticar nuevos pacientes con muestras distintas
    //Requiere las muestras del paciente
    public int diagnosticarPaciente(double datosPaciente[]){
        int claseEncontrada;//Alamcenará el resultado del diagnostico
        double suma=0;
        //Se calcula la union sumadora del nuevo paciente
        for (int i = 0; i < pesos.length; i++) {
           //Union sumandora con los pesos despues el entrenamiento
            suma += pesos[i]*datosPaciente[i];           
        }
        //se resta el umbral obtenido despues del entrenamiento       
        suma = suma - teta;
        //Se realiza la clasificacion
        if(suma > 0){
            //Si es mayor a 0 está enfermo
            claseEncontrada = 1;
        }else{
            //Si es menor a cero no está enfermo           
            claseEncontrada = 0;
        }
        return claseEncontrada;
    }
}

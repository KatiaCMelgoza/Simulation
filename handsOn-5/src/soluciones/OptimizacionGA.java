package soluciones;

import java.util.Arrays;
import java.util.Random;

public class OptimizacionGA {

    public static class Resultado {
        public double mejorValor;
        public double[] mejoresParametros;

        public Resultado(double mejorValor, double[] mejoresParametros) {
            this.mejorValor = mejorValor;
            this.mejoresParametros = mejoresParametros;
        }
    }

    private static final Random random = new Random();

    public static Resultado optimizar(double[] x, double[] y) {
        int tamanoPoblacion = 100;
        int numeroGeneraciones = 1000;
        double tasaMutacion = 0.01;

        // Inicializar población
        double[][] poblacion = new double[tamanoPoblacion][3];
        for (int i = 0; i < tamanoPoblacion; i++) {
            poblacion[i] = new double[]{random.nextDouble(), random.nextDouble(), random.nextDouble()};
        }

        double mejorValor = Double.NEGATIVE_INFINITY;
        double[] mejoresParametros = new double[3];

        for (int generacion = 0; generacion < numeroGeneraciones; generacion++) {
            // Evaluar la población
            double[] fitness = new double[tamanoPoblacion];
            for (int i = 0; i < tamanoPoblacion; i++) {
                fitness[i] = evaluar(poblacion[i], x, y);
                if (fitness[i] > mejorValor) {
                    mejorValor = fitness[i];
                    mejoresParametros = Arrays.copyOf(poblacion[i], 3);
                }
            }

            // Selección
            double[][] nuevaPoblacion = new double[tamanoPoblacion][3];
            for (int i = 0; i < tamanoPoblacion; i++) {
                double[] padre1 = seleccionar(poblacion, fitness);
                double[] padre2 = seleccionar(poblacion, fitness);
                nuevaPoblacion[i] = cruzar(padre1, padre2);
            }

            // Mutación
            for (int i = 0; i < tamanoPoblacion; i++) {
                mutar(nuevaPoblacion[i], tasaMutacion);
            }

            poblacion = nuevaPoblacion;
        }

        return new Resultado(mejorValor, mejoresParametros);
    }

    private static double evaluar(double[] individuo, double[] x, double[] y) {
        // Supongamos que estamos optimizando los parámetros para una regresión lineal simple
        double b0 = individuo[0];
        double b1 = individuo[1];
        double b2 = individuo[2];

        double errorTotal = 0.0;
        for (int i = 0; i < x.length; i++) {
            double prediccion = b0 + b1 * x[i] + b2 * x[i] * x[i];
            errorTotal += Math.pow(prediccion - y[i], 2);
        }

        // Queremos minimizar el error, así que devolvemos el negativo del error total
        return -errorTotal;
    }

    private static double[] seleccionar(double[][] poblacion, double[] fitness) {
        // Selección por torneo
        int tamanoTorneo = 5;
        int mejorIndice = random.nextInt(poblacion.length);
        for (int i = 1; i < tamanoTorneo; i++) {
            int indice = random.nextInt(poblacion.length);
            if (fitness[indice] > fitness[mejorIndice]) {
                mejorIndice = indice;
            }
        }
        return poblacion[mejorIndice];
    }

    private static double[] cruzar(double[] padre1, double[] padre2) {
        // Cruce de un punto
        int puntoCruce = random.nextInt(padre1.length);
        double[] hijo = new double[padre1.length];
        for (int i = 0; i < puntoCruce; i++) {
            hijo[i] = padre1[i];
        }
        for (int i = puntoCruce; i < hijo.length; i++) {
            hijo[i] = padre2[i];
        }
        return hijo;
    }

    private static void mutar(double[] individuo, double tasaMutacion) {
        for (int i = 0; i < individuo.length; i++) {
            if (random.nextDouble() < tasaMutacion) {
                individuo[i] += random.nextGaussian() * 0.1; // Mutación con distribución gaussiana
            }
        }
    }
}

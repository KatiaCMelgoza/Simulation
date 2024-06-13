package soluciones;

import java.util.Random;

public class OptimizacionPSO {

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
        int tamanoPoblacion = 30;
        int numeroIteraciones = 1000;
        double w = 0.5; // Factor de inercia
        double c1 = 1.5; // Coeficiente cognitivo
        double c2 = 1.5; // Coeficiente social

        // Inicializar partículas
        double[][] poblacion = new double[tamanoPoblacion][3];
        double[][] velocidad = new double[tamanoPoblacion][3];
        double[][] mejoresLocales = new double[tamanoPoblacion][3];
        double[] fitnessLocales = new double[tamanoPoblacion];
        double[] mejoresGlobales = new double[3];
        double mejorFitnessGlobal = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < tamanoPoblacion; i++) {
            for (int j = 0; j < 3; j++) {
                poblacion[i][j] = random.nextDouble();
                velocidad[i][j] = random.nextDouble();
            }
            fitnessLocales[i] = evaluar(poblacion[i], x, y);
            System.arraycopy(poblacion[i], 0, mejoresLocales[i], 0, 3);
            if (fitnessLocales[i] > mejorFitnessGlobal) {
                mejorFitnessGlobal = fitnessLocales[i];
                System.arraycopy(poblacion[i], 0, mejoresGlobales, 0, 3);
            }
        }

        for (int iteracion = 0; iteracion < numeroIteraciones; iteracion++) {
            for (int i = 0; i < tamanoPoblacion; i++) {
                for (int j = 0; j < 3; j++) {
                    velocidad[i][j] = w * velocidad[i][j]
                            + c1 * random.nextDouble() * (mejoresLocales[i][j] - poblacion[i][j])
                            + c2 * random.nextDouble() * (mejoresGlobales[j] - poblacion[i][j]);
                    poblacion[i][j] += velocidad[i][j];
                }

                double fitness = evaluar(poblacion[i], x, y);
                if (fitness > fitnessLocales[i]) {
                    fitnessLocales[i] = fitness;
                    System.arraycopy(poblacion[i], 0, mejoresLocales[i], 0, 3);
                }

                if (fitness > mejorFitnessGlobal) {
                    mejorFitnessGlobal = fitness;
                    System.arraycopy(poblacion[i], 0, mejoresGlobales, 0, 3);
                }
            }
        }

        return new Resultado(mejorFitnessGlobal, mejoresGlobales);
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
}

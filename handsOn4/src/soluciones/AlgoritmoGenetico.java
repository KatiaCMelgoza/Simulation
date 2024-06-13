package soluciones;

import java.util.Random;


public class AlgoritmoGenetico {
    private static  Random random = new Random();


    // el metodo se usa para la  seleccion por ruleta
    public static Individuo seleccionarPadre(Poblacion poblacion) {
        double aptitudTotal = poblacion.getIndividuos().stream().mapToDouble(ind -> ind.aptitud).sum();
        double valorAleatorio = random.nextDouble() * aptitudTotal;
        double sumaParcial = 0;


        for (Individuo ind : poblacion.getIndividuos()) {
            sumaParcial += ind.aptitud;
            if (sumaParcial >= valorAleatorio) {
                return ind;
            }
        }
        return poblacion.getIndividuos().get(0); // regresa el primer individuo
    }


    // el metodo se usa para el cruzamiento
    public static Individuo[] cruzar(Individuo padre1, Individuo padre2) {
        double nuevoB0 = 0.5 * (padre1.b0 + padre2.b0);
        double nuevoB1 = 0.5 * (padre1.b1 + padre2.b1);
        return new Individuo[]{new Individuo(nuevoB0, nuevoB1), new Individuo(nuevoB0, nuevoB1)};
    }


    // el metodo se usa para la mutacion
    public static void mutar(Individuo individuo) {
        if (random.nextDouble() < 0.1) {  // la probabilidad de mutacion del 10%
            individuo.b0 += random.nextGaussian();
            individuo.b1 += random.nextGaussian();
        }
    }
}

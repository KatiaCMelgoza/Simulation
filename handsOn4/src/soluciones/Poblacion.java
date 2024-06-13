package soluciones;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Poblacion {
    private List<Individuo> individuos;


    public Poblacion(int tamano) {
        individuos = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            double b0 = new Random().nextDouble() * 1000;
            double b1 = new Random().nextDouble() * 100;
            individuos.add(new Individuo(b0, b1));
        }
    }


    public List<Individuo> getIndividuos() {
        return individuos;
    }
}

package soluciones;

import dataset.DataSet;

public class Individuo {
    public double b0;
    public double b1;
    public double aptitud;


    public Individuo(double b0, double b1) {
        this.b0 = b0;
        this.b1 = b1;
    }


    // el metodo se usa para calcular la aptitud del individuo
    public void calcularAptitud(DataSet dataSet) {
        aptitud = -dataSet.calcularECM(b0, b1);
    }
}

import java.util.Random;

public class GradianteDescendiente {
    double beta1 = 0;
    double beta0 = 0;
    double alpha = 0.0005;
    int contador = 0;
    private DataSet dataSet;
    public double presicion = 0.0001;

    Random rand = new Random();

    GradianteDescendiente(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public double getBeta0() {
        return beta0;
    }

    public double getBeta1() {
        return beta1;
    }

    private double Errores(double[] x, double[] y) {
        double sumSquareError = 0;
        for (int i = 0; i < x.length; i++) {
            sumSquareError += (y[i] - (beta0 + (beta1 * x[i])));
        }
        return (sumSquareError * sumSquareError) / x.length;
    }

    private double derivadaB0(double[] x, double[] y) {
        double additionB0 = 0;
        for (int i = 0; i < x.length; i++) {
            additionB0 += (y[i] - (beta0 + beta1 * x[i]));
        }
        return (-2.0 / x.length) * additionB0;
    }

    private double derivadaB1(double[] x, double[] y) {
        double additionB1 = 0;
        for (int i = 0; i < x.length; i++) {
            additionB1 += (y[i] - (beta0 + beta1 * x[i])) * x[i];
        }
        return (-2.0 / x.length) * additionB1;
    }

    public void gradientDescendiente() {
        contador = 0;
        while ((Errores(dataSet.getX(), dataSet.getY()) > presicion)) {
            double dbeta0 = derivadaB0(dataSet.getX(), dataSet.getY());
            double dbeta1 = derivadaB1(dataSet.getX(), dataSet.getY());
            System.out.println(Errores(dataSet.getX(), dataSet.getY()));
            beta0 -= alpha * dbeta0;
            beta1 -= alpha * dbeta1;
            contador++;
        }
    }

    public void predicciones(){
        double [] advertising= {23,43,24};
        for (double v : advertising) {
            System.out.println((beta0 + (beta1 * v)));


        }
    }
}

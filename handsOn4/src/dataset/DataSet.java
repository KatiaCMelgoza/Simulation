package dataset;

public class DataSet {
    public double[] x = {23, 26, 30, 34, 43, 48, 52, 57, 58};
    public double[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};


    // el metodo se usa  para calcular el error cuadratico medio
    public double calcularECM(double b0, double b1) {
        double error = 0;
        for (int i = 0; i < x.length; i++) {
            double prediccion = b1 * x[i] + b0;
            error += Math.pow(prediccion - y[i], 2);
        }
        return error / x.length;
    }
}

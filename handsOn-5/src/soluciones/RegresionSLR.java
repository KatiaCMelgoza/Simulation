package soluciones;

public class RegresionSLR {

    public static class Resultado {
        public double b0;
        public double b1;
        public double r2;
        public double r;

        public Resultado(double b0, double b1, double r2, double r) {
            this.b0 = b0;
            this.b1 = b1;
            this.r2 = r2;
            this.r = r;
        }
    }

    public static Resultado calcular(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }

        double b1 = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double b0 = (sumY - b1 * sumX) / n;

        // Calcular el coeficiente de correlación y el coeficiente de determinación
        double yMean = sumY / n;
        double ssTotal = 0, ssResidual = 0;

        for (int i = 0; i < n; i++) {
            double yPred = b0 + b1 * x[i];
            ssTotal += Math.pow(y[i] - yMean, 2);
            ssResidual += Math.pow(y[i] - yPred, 2);
        }

        double r2 = 1 - (ssResidual / ssTotal);
        double r = Math.sqrt(r2);

        return new Resultado(b0, b1, r2, r);
    }
}

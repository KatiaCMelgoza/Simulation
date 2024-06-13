package soluciones;

public class RegresionPR {

    public static class Resultado {
        public double b0;
        public double b1;
        public double b2;
        public double r2;

        public Resultado(double b0, double b1, double b2, double r2) {
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
            this.r2 = r2;
        }
    }

    public static Resultado calcular(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0, sumXY = 0, sumX2Y = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumX2 += x[i] * x[i];
            sumX3 += x[i] * x[i] * x[i];
            sumX4 += x[i] * x[i] * x[i] * x[i];
            sumXY += x[i] * y[i];
            sumX2Y += x[i] * x[i] * y[i];
        }

        double[][] matrix = {
                {n, sumX, sumX2, sumY},
                {sumX, sumX2, sumX3, sumXY},
                {sumX2, sumX3, sumX4, sumX2Y}
        };

        double[] coefficients = gaussJordanElimination(matrix);
        double b0 = coefficients[0];
        double b1 = coefficients[1];
        double b2 = coefficients[2];

        double meanY = sumY / n;
        double totalVariation = 0;
        double explainedVariation = 0;

        for (int i = 0; i < n; i++) {
            double yiHat = b0 + b1 * x[i] + b2 * x[i] * x[i];
            totalVariation += (y[i] - meanY) * (y[i] - meanY);
            explainedVariation += (yiHat - meanY) * (yiHat - meanY);
        }

        double r2 = explainedVariation / totalVariation;

        return new Resultado(b0, b1, b2, r2);
    }

    private static double[] gaussJordanElimination(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // Make the diagonal contain all 1's
            double max = matrix[i][i];
            for (int k = i; k < n + 1; k++) {
                matrix[i][k] /= max;
            }

            // Make the other rows contain 0's
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = matrix[j][i];
                    for (int k = i; k < n + 1; k++) {
                        matrix[j][k] -= factor * matrix[i][k];
                    }
                }
            }
        }

        double[] coefficients = new double[n];
        for (int i = 0; i < n; i++) {
            coefficients[i] = matrix[i][n];
        }

        return coefficients;
    }
}

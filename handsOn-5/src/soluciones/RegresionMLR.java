package soluciones;

public class RegresionMLR {

    public static class Resultado {
        public double b0;
        public double[] betas;
        public double r2;

        public Resultado(double b0, double[] betas, double r2) {
            this.b0 = b0;
            this.betas = betas;
            this.r2 = r2;
        }
    }

    public static Resultado calcular(double[][] x, double[] y) {
        int n = x.length;
        int m = x[0].length;

        // Construir la matriz extendida
        double[][] augmentedMatrix = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(x[i], 0, augmentedMatrix[i], 0, m);
            augmentedMatrix[i][m] = y[i];
        }

        // Aplicar eliminación de Gauss
        for (int i = 0; i < m; i++) {
            // Buscar el mayor en la columna i
            int max = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[max][i])) {
                    max = k;
                }
            }

            // Intercambiar filas
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[max];
            augmentedMatrix[max] = temp;

            // Hacer que todos los valores debajo del pivote sean cero
            for (int k = i + 1; k < n; k++) {
                double factor = augmentedMatrix[k][i] / augmentedMatrix[i][i];
                for (int j = i; j <= m; j++) {
                    augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                }
            }
        }

        // Resolver la matriz triangular superior
        double[] betas = new double[m];
        for (int i = m - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < m; j++) {
                sum += augmentedMatrix[i][j] * betas[j];
            }
            betas[i] = (augmentedMatrix[i][m] - sum) / augmentedMatrix[i][i];
        }

        // Calcular el término independiente b0
        double sumY = 0.0;
        for (double value : y) {
            sumY += value;
        }
        double meanY = sumY / n;

        double b0 = meanY;
        for (int i = 0; i < m; i++) {
            double sumX = 0.0;
            for (int j = 0; j < n; j++) {
                sumX += x[j][i];
            }
            double meanX = sumX / n;
            b0 -= betas[i] * meanX;
        }

        // Calcular R^2
        double totalVariation = 0.0;
        double explainedVariation = 0.0;
        for (int i = 0; i < n; i++) {
            double predictedY = b0;
            for (int j = 0; j < m; j++) {
                predictedY += betas[j] * x[i][j];
            }
            totalVariation += (y[i] - meanY) * (y[i] - meanY);
            explainedVariation += (predictedY - meanY) * (predictedY - meanY);
        }
        double r2 = explainedVariation / totalVariation;

        return new Resultado(b0, betas, r2);
    }
}

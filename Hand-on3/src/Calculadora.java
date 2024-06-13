public class Calculadora {

    public static double Correlacion(double[] x, double[] y, DataSet dataSet){
        int n = x.length;
        double sumX = sum(x);
        double sumY = sum(y);
        double sumXY = sumaXY(x, y);
        double cuadradoX = sumaCuadrada(x);
        double cuadradoY = sumaCuadrada(y);

        double numerador = n * sumXY - sumX * sumY;
        double denominador = Math.sqrt((n * cuadradoX - Math.pow(sumX, 2)) * (n * cuadradoY - Math.pow(sumY, 2)));

        return numerador / denominador;
    }

    public static double Determinacion(double[] x, double[] y, DataSet dataSet){
        double r = Correlacion(x, y, dataSet);
        return Math.pow(r, 2);
    }

    private static double sum(double[] z){
        double total = 0;
        for(double valor : z){
            total += valor;
        }
        return total;
    }

    private static double sumaXY(double[] x, double[] y){
        double total = 0;
        for(int i = 0; i < x.length; i++){
            total += x[i] * y[i];
        }
        return total;
    }

    private static double sumaCuadrada(double[] datos){
        double total = 0;
        for(double valor : datos){
            total += Math.pow(valor, 2);
        }
        return total;
    }
}

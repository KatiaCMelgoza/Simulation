public class Impresora {

    public static void imprimirLineaVacia() {
        System.out.println();
    }

    public static void imprimirResultadosGradiente(double B0, double B1) {

        System.out.println("Betas");
        System.out.println("B0: " + B0);
        System.out.println("B1: " + B1);
        imprimirLineaVacia();
        System.out.println("Y= " + B0 + "x + " + B1);
        imprimirLineaVacia();
    }

    public static void imprimirCoeficientes(double coeficienteCorrelacion, double coeficienteDeterminacion) {
        System.out.println("Coficientes");
        System.out.println("Coeficiente de Correlacion: " + coeficienteCorrelacion);
        System.out.println("Coeficiente de Determinacion: " + coeficienteDeterminacion);
        imprimirLineaVacia();
    }

    public static void imprimirPredicciones(double[] predictions) {
        for (double prediction : predictions) {
            System.out.println(prediction);
        }
        imprimirLineaVacia();
    }

    public static void imprimirError(double error) {
        System.out.println(error);
    }
}

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class HandsOn3Agent extends Agent {
    protected void setup() {

        System.out.println(getLocalName());


        addBehaviour(new MyBehaviour());
    }

    private class MyBehaviour extends OneShotBehaviour {
        public void action() {
            DataSet dataSet = new DataSet();
            GradianteDescendiente gradiente = new GradianteDescendiente(dataSet);
            gradiente.gradientDescendiente();

            Impresora.imprimirResultadosGradiente(gradiente.getBeta0(), gradiente.getBeta1());
            Impresora.imprimirCoeficientes(
                    Calculadora.Correlacion(dataSet.getX(), dataSet.getY(), dataSet),
                    Calculadora.Determinacion(dataSet.getX(), dataSet.getY(), dataSet)

            );
            System.out.println("Predicciones:");
            gradiente.predicciones();

            doDelete();
        }
    }
}

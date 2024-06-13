package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import soluciones.RegresionPR;
import utilidades.YellowPage;

public class AgentePolinomial extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " Iniciado");

        // Registrar el servicio de regresión PR en las páginas amarillas
        YellowPage.registrarServicio(this, "RegresionPR", "JADE-regresion-pr");

        addBehaviour(new ReceiveDatasetBehaviour());
    }

    @Override
    protected void takeDown() {
        YellowPage.deregistrarServicio(this);
        System.out.println("Agente " + getAID().getName() + " terminado");
    }

    private class ReceiveDatasetBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage mensaje = myAgent.receive(mt);

            if (mensaje != null) {
                System.out.println("Dataset recibido para PR");
                String contenido = mensaje.getContent();
                DataSet dataSet = DataSet.fromString(contenido);

                double[] x = dataSet.getXData().stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = dataSet.getYData().stream().mapToDouble(Double::doubleValue).toArray();

                // se realiza la regresion polinomial
                RegresionPR.Resultado resultados = RegresionPR.calcular(x, y);

                // se envian los resultados al agente solicitador
                ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                respuesta.addReceiver(new AID(mensaje.getReplyWith(), AID.ISLOCALNAME));
                respuesta.setContent(String.format("Ecuación de regresión polinomial: y = %.6f + %.6fx + %.6fx^2\nCoeficiente de determinación: %.6f",
                        resultados.b0, resultados.b1, resultados.b2, resultados.r2));
                send(respuesta);
            } else {
                block();
            }
        }
    }
}

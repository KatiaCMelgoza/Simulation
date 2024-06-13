package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import soluciones.RegresionSLR;
import utilidades.YellowPage;

public class AgenteRegresionSLR extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " Iniciado");

        // Registrar el servicio de regresión SLR en las páginas amarillas
        YellowPage.registrarServicio(this, "Regresion", "JADE-regresion-slr");

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
                String contenido = mensaje.getContent();
                DataSet dataSet = DataSet.fromString(contenido);

                double[] x = dataSet.getXData().stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = dataSet.getYData().stream().mapToDouble(Double::doubleValue).toArray();

                // se realiza la regresion lineal simple
                RegresionSLR.Resultado resultados = RegresionSLR.calcular(x, y);

                // se envian los resultados al agente solicitador
                ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                respuesta.addReceiver(new AID(mensaje.getReplyWith(), AID.ISLOCALNAME));
                respuesta.setContent(String.format("Ecuación de regresión: y = %.6f + %.6fx\nCoeficiente de determinación: %.6f\nCoeficiente de correlación: %.6f",
                        resultados.b0, resultados.b1, resultados.r2, resultados.r));
                send(respuesta);
            } else {
                block();
            }
        }
    }
}

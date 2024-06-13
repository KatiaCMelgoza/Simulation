package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import soluciones.RegresionMLR;
import utilidades.YellowPage;

public class AgenteRegresionMLR extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " Iniciado");

        //Aqui se hace el registro del servicio de regresionMultipleLinealRegression en las paginas amarillas
        YellowPage.registrarServicio(this, "RegresionMLR", "JADE-regresion-mlr");

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
                System.out.println("Dataset recibido para MLR");
                String contenido = mensaje.getContent();
                DataSet dataSet = DataSet.fromString(contenido);

                double[][] x = dataSet.getXMatrix();
                double[] y = dataSet.getYData().stream().mapToDouble(Double::doubleValue).toArray();

                // se realiza la regresion lineal multiple
                RegresionMLR.Resultado resultados = RegresionMLR.calcular(x, y);

                // se envian los resultados al agente solicitador
                ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                respuesta.addReceiver(new AID(mensaje.getReplyWith(), AID.ISLOCALNAME));
                respuesta.setContent(String.format("Ecuación de regresión MLR: y = %.6f + %.6fx1 + %.6fx2 + %.6fx3\nCoeficiente de determinación: %.6f",
                        resultados.b0, resultados.betas[0], resultados.betas[1], resultados.betas[2], resultados.r2));
                send(respuesta);
            } else {
                block();
            }
        }
    }
}

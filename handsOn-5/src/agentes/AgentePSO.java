package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import soluciones.OptimizacionPSO;
import utilidades.YellowPage;

public class AgentePSO extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " Iniciado");

        // Aqui se hace el registro del servicio del agente PSO en las paginas amarillas
        YellowPage.registrarServicio(this, "OptimizacionPSO", "JADE-pso");

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
                System.out.println("Dataset recibido para PSO");
                String contenido = mensaje.getContent();
                DataSet dataSet = DataSet.fromString(contenido);

                double[] x = dataSet.getXData().stream().mapToDouble(Double::doubleValue).toArray();
                double[] y = dataSet.getYData().stream().mapToDouble(Double::doubleValue).toArray();

                //  optimización con PSO
                OptimizacionPSO.Resultado resultados = OptimizacionPSO.optimizar(x, y);

                // Envia los resultados al agente solicitador
                ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
                respuesta.addReceiver(new AID(mensaje.getReplyWith(), AID.ISLOCALNAME));
                respuesta.setContent(String.format("Resultados de optimización PSO: Mejor valor = %.6f", resultados.mejorValor));
                send(respuesta);
            } else {
                block();
            }
        }
    }
}

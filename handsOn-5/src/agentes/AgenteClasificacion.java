package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utilidades.YellowPage;

public class AgenteClasificacion extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " Iniciado");

        // Aqui se hace el registro del servicio del agente clasificador en las paginas amarillas
        YellowPage.registrarServicio(this, "Clasificacion", "AgenteClasificacion");

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

                if (dataSet.getXMatrix() != null) {
                    System.out.println("El dataSet utilizado es matriz, no se utiliza SLR, PR o GA");
                    buscarYSolicitarServicio("RegresionMLR", dataSet.toString(), mensaje.getSender());
                } else {
                    // Se envian ambos dataset de los  agentes SLR y PR
                    System.out.println("El DataSet asignado es simple, no se utilizamos MLR");
                    buscarYSolicitarServicio("Regresion", dataSet.toString(), mensaje.getSender());
                    buscarYSolicitarServicio("RegresionPR", dataSet.toString(), mensaje.getSender());
                    buscarYSolicitarServicio("OptimizacionGA", dataSet.toString(), mensaje.getSender());
                    buscarYSolicitarServicio("OptimizacionPSO", dataSet.toString(), mensaje.getSender());
                }
            } else {
                block();
            }
        }
    }

    private void buscarYSolicitarServicio(String tipoDeAnalisis, String dataset, AID solicitador) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                AID agente = YellowPage.buscarServicio(myAgent, tipoDeAnalisis);
                if (agente != null) {
                    ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
                    mensaje.addReceiver(agente);
                    mensaje.setContent(dataset);
                    mensaje.setReplyWith(solicitador.getLocalName());
                    send(mensaje);
                    System.out.println("Servicio de " + tipoDeAnalisis + " encontrado y solicitud enviada.");
                } else {
                    System.out.println("No se encontró un agente para " + tipoDeAnalisis);
                }
            }
        });
    }
}

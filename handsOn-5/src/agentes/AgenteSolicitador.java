package agentes;

import datos.DataSet;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteSolicitador extends Agent {
    private DataSet dataSet;

    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " inicializado");

        // se define el dataset
        double[] xData = {23, 26, 30, 34, 43, 48, 52, 57, 58};
        double[] yData = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        // se define el dataset matricial
        double[][] xMatrix = {
                {23, 40000, 12},
                {26, 50000, 14},
                {30, 60000, 16},
                {34, 70000, 18},
                {43, 80000, 20},
                {48, 90000, 22},
                {52, 100000, 24},
                {57, 110000, 26},
                {58, 120000, 28}
        };
        double[] yMatrix = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        // Selecciona el dataset a usar (simple o matricial)
         dataSet = new DataSet(xData, yData);  // Para dataset simple
        //dataSet = new DataSet(xMatrix, yMatrix);  // Para dataset matricial

        addBehaviour(new SendDatasetBehaviour());
        addBehaviour(new ReceiveResultBehaviour());
    }

    private class SendDatasetBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
            mensaje.addReceiver(new AID("AgenteClasificacion", AID.ISLOCALNAME));
            mensaje.setContent(dataSet.toString());
            send(mensaje);
        }
    }

    private class ReceiveResultBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage mensaje = myAgent.receive(mt);

            if (mensaje != null) {
                System.out.println("Resultado obtenido:");
                System.out.println(mensaje.getContent());
            } else {
                block();
            }
        }
    }
}

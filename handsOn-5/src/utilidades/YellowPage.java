package utilidades;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class YellowPage {

    public static void registrarServicio(Agent agente, String tipoServicio, String nombreServicio) {
        DFAgentDescription descripcionAgente = new DFAgentDescription();
        descripcionAgente.setName(agente.getAID());

        ServiceDescription descripcionServicio = new ServiceDescription();
        descripcionServicio.setType(tipoServicio);
        descripcionServicio.setName(nombreServicio);
        descripcionAgente.addServices(descripcionServicio);

        try {
            DFService.register(agente, descripcionAgente);
            System.out.println(agente.getLocalName() + ": Servicio " + tipoServicio + " registrado como " + nombreServicio);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public static void deregistrarServicio(Agent agente) {
        try {
            DFService.deregister(agente);
            System.out.println(agente.getLocalName() + ": Servicio desregistrado.");
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public static AID buscarServicio(Agent agente, String tipoServicio) {
        DFAgentDescription descripcionBusqueda = new DFAgentDescription();
        ServiceDescription descripcionServicio = new ServiceDescription();
        descripcionServicio.setType(tipoServicio);
        descripcionBusqueda.addServices(descripcionServicio);

        try {
            DFAgentDescription[] resultados = DFService.search(agente, descripcionBusqueda);
            if (resultados.length > 0) {
                return resultados[0].getName();
            } else {
                System.out.println("No se encontró el servicio " + tipoServicio);
                return null;
            }
        } catch (FIPAException e) {
            e.printStackTrace();
            return null;
        }
    }
}

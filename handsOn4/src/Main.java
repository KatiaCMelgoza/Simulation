import dataset.DataSet;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import soluciones.AlgoritmoGenetico;
import soluciones.Individuo;
import soluciones.Poblacion;


public class Main extends Agent {
    public void setup() {
        System.out.println(getLocalName());
        addBehaviour(new GeneticAgent());
    }


    private class GeneticAgent extends OneShotBehaviour {

        //se inicia el comportamiento
        // el metodo action se sobrecarga
        public void action() {

            DataSet dataSet = new DataSet();


            // se inicializa la poblacion con 20 individuos
            Poblacion poblacion = new Poblacion(20);


            // se calcula la aptitud de cada individuo en la poblacion inicial
            for (Individuo individuo : poblacion.getIndividuos()) {
                individuo.calcularAptitud(dataSet);
            }


            // se repite por 100 generaciones, se imprime 100 veces
            for (int generacion = 0; generacion < 100; generacion++) {

                // se crea una nueva poblacion vacia
                Poblacion nuevaPoblacion = new Poblacion(0);


                // se crean nuevos individuos a partir de la poblacion actual
                while (nuevaPoblacion.getIndividuos().size() < 20) {

                    // se seleccionan dos padres
                    Individuo padre1 = AlgoritmoGenetico.seleccionarPadre(poblacion);
                    Individuo padre2 = AlgoritmoGenetico.seleccionarPadre(poblacion);


                    // se cruzan los padres para producir hijos
                    Individuo[] hijos = AlgoritmoGenetico.cruzar(padre1, padre2);


                    // se mutan los hijos
                    AlgoritmoGenetico.mutar(hijos[0]);
                    AlgoritmoGenetico.mutar(hijos[1]);


                    // se calcula cual de los hijos hijos es el mejor
                    hijos[0].calcularAptitud(dataSet);
                    hijos[1].calcularAptitud(dataSet);


                    // se añaden los hijos a la nueva poblacion
                    nuevaPoblacion.getIndividuos().add(hijos[0]);
                    nuevaPoblacion.getIndividuos().add(hijos[1]);
                }


                // cambia la poblacion nueva y  se convierte en la poblacion actual
                poblacion = nuevaPoblacion;


                // se busca y se muestra la mejor solucion en la generacion actual
                Individuo mejor = poblacion.getIndividuos().get(0);
                for (Individuo ind : poblacion.getIndividuos()) {
                    if (ind.aptitud > mejor.aptitud) {
                        mejor = ind;
                    }
                }
                System.out.println("Generacion " + generacion + ": b0 = " + mejor.b1 + ", b1 = " + mejor.b0 + ", Aptitud = " + mejor.aptitud);
            }


            Individuo mejorFinal = poblacion.getIndividuos().get(0);
            for (Individuo ind : poblacion.getIndividuos()) {
                if (ind.aptitud > mejorFinal.aptitud) {
                    mejorFinal = ind;
                }
            }


            // se imprime la ecuacion de regresion final
            System.out.println("Ecuacion de regresion final: y = " + mejorFinal.b1 + "x + " + mejorFinal.b0);
        }



        public int onEnd() {
            // se finaliza el comportamiento
            System.out.println("Proceso de algoritmo genetico completado.");
            return super.onEnd();
        }
    }
}

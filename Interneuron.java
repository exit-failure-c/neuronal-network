import java.util.ArrayList;
/**
 * The class Neuron implents a interneuron for the class Network.
 *
 * @author Vera Röhr
 * @version 1.0
 * @since 2019-01-11
 */
public class Interneuron extends Neuron {
    /**
     * {@inheritDoc}
     */
    public Interneuron(int index) {
        super(index); // sends index to Constructor the super class (Neuron)
        this.outgoingsynapses = new ArrayList <Synapse>();
    }

    /**
     * Divides incoming signal into equal parts for all the outgoing synapses
     * Transmits the parts via Synapse.transmit(Double[]);
     *
     * @param signal 3 dimensional signal from another neuron
     * @return 3 dimensional neural signal, which got transmitted to the synapses (for testing.)
     */
    @Override
    public double[] integrateSignal(double[] signal) {
        double[] new_signal = signal;
        System.out.println(new_signal);
        for (int i=0; i<3; i++){
            new_signal[i] = (signal[i]/this.outgoingsynapses.size());
            for (Synapse s : this.outgoingsynapses){
                s.transmit(new_signal);
            }   
        }
        return signal;
    }
}



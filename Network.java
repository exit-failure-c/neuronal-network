/**
 * The Network class implements a neural network.
 * The network consists of three types of neurons: photoreceptors(@see
 * Photoreceptor), interneurons(@see Interneuron) and cortical neurons(@see
 * CorticalNeuron). The network processes light waves. There are three types of
 * photoreceptors, that perceive the different colors.
 *
 * @author Vera Röhr
 * @version 1.0
 * @since 2019-01-11
 */
public class Network {
    /**
     * #Photoreceptors in the network
     */
    int receptors;
    /**
     * #Cortical neurons in the network
     */
    int cortical;
    /**
     * All the neurons in the network
     */
    Neuron[] neurons;
    /**
     * Different receptor types
     */
    String[] receptortypes = {"blue", "green", "red"};

    /**
     * Adds neurons to the network.
     * Initializes the neurons in the network. Should initialize approximately equal numbers
     * of the different types of photoreceptors. Synapses are not added here!
     *
     * @param inter     #Interneurons
     * @param receptors #Photoreceptors
     * @param cortical  #CorticalNeurons
     * @throws RuntimeException if there are less than 3 photoreceptors or less interreceptors than photoreceptors.
     */

    public Network(int inter, int receptors, int cortical) {
        Neuron[] new_Neuron = new Neuron[(inter+cortical+receptors)];
        this.neurons = new_Neuron;
        this.cortical = cortical;
        this.receptors = receptors;
        if (receptors<3 || inter<receptors){
            throw new RuntimeException("there are less than 3 photoreceptors or less interreceptors than photoreceptors.");
        }
        else{

            for (int i=0; i <= inter-1; i++){
                Interneuron new_interneuron = new Interneuron(0);
                    this.neurons[i] = new_interneuron;
            }
            for (int i=inter ; i <= (inter+cortical)-1 ; i++){
                CorticalNeuron new_corticalneuron = new CorticalNeuron(0);
                this.neurons[i] = new_corticalneuron;
            }

            int place_of_array = inter+cortical;
            int number_of_receptor_type = (receptors-(receptors%3))/3;
            boolean red = true ;
            boolean blue = true ;

            for (int i=1 ; i<= number_of_receptor_type ; i++){
                if ((receptors%3 == 1) && (red = true)){
                    Photoreceptor new_receptor_RED = new Photoreceptor(0,"red");
                    this.neurons[place_of_array] = new_receptor_RED;
                    place_of_array++;
                    red = false;
                }
                Photoreceptor new_receptor_RED = new Photoreceptor(0,"red");
                this.neurons[place_of_array] = new_receptor_RED;
                place_of_array++;
            }
            for (int i=1 ; i<= number_of_receptor_type ; i++){
                if ((receptors%3 == 2) && (blue = true)){
                    Photoreceptor new_receptor_BLUE = new Photoreceptor(0,"blue");
                    this.neurons[place_of_array] = new_receptor_BLUE;
                    place_of_array++;
                    blue = false;
                }
                Photoreceptor new_receptor_BLUE = new Photoreceptor(0,"blue");
                this.neurons[place_of_array] = new_receptor_BLUE;
                place_of_array++;
            }
            for (int i=1 ; i<= number_of_receptor_type ; i++){
                Photoreceptor new_receptor_GREEN = new Photoreceptor(0,"green");
                this.neurons[place_of_array] = new_receptor_GREEN;
                place_of_array++;
            }
        }
    }



    /**
     * Add a Synapse between the Neurons. The different neuons have their outgoing
     * synapses as an attribute and each a method addSynapse. ({@link Interneuron}, {@link Photoreceptor},
     * {@link CorticalNeuron})
     *
     * @param n1 Presynaptic Neuron (Sender)
     * @param n2 Postsynaptic Neuron (Receiver)
     */

    public void addSynapse(Neuron n1, Neuron n2) {
        Synapse new_syanpse = new Synapse(n1,n2);
        if (n1 instanceof Photoreceptor){
          if (n1.outgoingsynapses.isEmpty()){
            n1.outgoingsynapses.add(new_syanpse);
          }
          else{
            throw new RuntimeException ("a Photoreceptor cann´t have two or more Sypase"); 
          }
        }

        else if (n1 instanceof Interneuron)
            n1.outgoingsynapses.add(new_syanpse);  
        else if (n1 instanceof CorticalNeuron)
            n1.outgoingsynapses.add(new_syanpse);  
    }

    /**
     * Processes the light waves. The lightwaves are integrated by the
     * photoreceptors (@see Photoreceptor.integrateSignal(double[] signal)) and the
     * final neural signal is found by summing up the signals in the cortical
     * neurons(@see CorticalNeuron)
     *
     * @param input light waves in nm
     * @return the neural signal that can be used to classify the color
     */
    public double[] signalprocessing(double[] input) {
        double[] signal = new double[3];
        int blue = (receptors-(receptors%3))/3;
        int green = (receptors-(receptors%3))/3;
        int red = (receptors-(receptors%3))/3;
        int number_of_rest = (receptors%3);
        if (number_of_rest == 1) red++;
        else if (number_of_rest == 2){
            blue++;
        }
        for(double  i:input){
          signal[0]+= i/(double)blue;
          signal[1]+= i/(double)green;
          signal[2]+= i/(double)red;

        }
        
        return signal;
    }



	public double[] countColorreceptors() {
		double[] colorreceptors = new double[3];
		Photoreceptor c;
		for (Neuron neuron : this.neurons) {
			if (neuron instanceof Photoreceptor) {
				c = (Photoreceptor) neuron;
				if (c.type.equals("blue"))
					colorreceptors[0]++;
				else if (c.type.equals("green"))
					colorreceptors[1]++;
				else if (c.type.equals("red"))
					colorreceptors[2]++;
			}
		}
		return colorreceptors;
	}

    /**
     * Classifies the neural signal to a color.
     *
     * @param signal neural signal from the cortical neurons
     * @return color of the mixed light signals as a String
     */
    public String colors(double[] signal) {
        String color = "grey";
        if (signal[0] > 0.6 && signal[1] < 0.074)
            color = "violet";
        else if (signal[0] >= 0.21569329706627882 && signal[1] < 0.678)
            color = "blue";
        else if (signal[0] < 0.21569329706627882 && signal[1] >= 0.678 && signal[2] > 0.333)
            color = "green";
        else if (signal[1] < 0.713 && signal[2] > 0.913)
            color = "yellow";
        else if (signal[1] > 0.068 && signal[2] > 0.227)
            color = "orange";
        else if (signal[2] > 0.002)
            color = "red";
        return color;
    }

    public static void main(String[] args) {
    
		//This is a example implementation of a main. Start with the simplest network possible as it is shown in the video.
		Network neural = new Network(3, 3, 1);
        //use neural.addSynapse to add the synapses based on the order of neurons in the neurons array.
        //For the structure of the network look at the video


		double coloredLight[] = {456} ;
        System.out.println(coloredLight);
		System.out.println(neural.colors(neural.signalprocessing(coloredLight)));
		CorticalNeuron n= (CorticalNeuron) neural.neurons[3];
		n.reset();
		coloredLight[0]= 578;
		System.out.println(neural.colors(neural.signalprocessing(coloredLight)));
    }
}



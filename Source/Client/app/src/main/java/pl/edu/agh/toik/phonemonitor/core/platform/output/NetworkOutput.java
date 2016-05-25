package pl.edu.agh.toik.phonemonitor.core.platform.output;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class NetworkOutput implements IOutput {

    private final String ip;

    public NetworkOutput(String ip) {

        this.ip = ip;
    }

    @Override
    public void write(String message) {
        //TODO
    }
}

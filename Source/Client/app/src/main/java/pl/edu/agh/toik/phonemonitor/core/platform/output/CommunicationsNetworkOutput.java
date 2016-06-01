package pl.edu.agh.toik.phonemonitor.core.platform.output;

import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public class CommunicationsNetworkOutput implements IOutput {

    private final String ip;
    private final String sensorName;
//    private final CommunicationsClient client;

    public CommunicationsNetworkOutput(String ip, String sensorName) {

        this.ip = ip;
        this.sensorName = sensorName;

//        this.client = new CommunicationsClient(this.ip, 19308, this.sensorName);
    }

    @Override
    public void write(String tag, String message) {
//        client.sendUpdate(message, tag);
    }
}

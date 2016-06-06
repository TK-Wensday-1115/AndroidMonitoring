package handler;

import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import sample.TermometerPanel;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class TemperatureChartHandler implements IChartHandler {

    private final TermometerPanel temperatureDiagram;

    public TemperatureChartHandler(TermometerPanel temperatureDiagram) {
        this.temperatureDiagram = temperatureDiagram;
    }

    @Override
    public void addReading(SensorReading reading) {
        temperatureDiagram.setTemperature(Double.parseDouble(reading.getValue()));
    }
}

package handler;

import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import sample.TermometerPanel;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class BatteryLevelChartHandler implements IChartHandler {

    private final TermometerPanel batteryDiagram;

    public BatteryLevelChartHandler(TermometerPanel batteryDiagram) {
        this.batteryDiagram = batteryDiagram;
    }

    @Override
    public void addReading(SensorReading reading) {
        batteryDiagram.setTemperature(Double.parseDouble(reading.getValue()));
    }
}

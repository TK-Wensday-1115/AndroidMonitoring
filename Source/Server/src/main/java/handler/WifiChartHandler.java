package handler;

import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class WifiChartHandler implements IChartHandler {

    private final PieChartPanel wifiDiagram;

    public WifiChartHandler(PieChartPanel wifiDiagram) {
        this.wifiDiagram = wifiDiagram;
    }

    @Override
    public void addReading(SensorReading reading) {
        double value = Double.parseDouble(reading.getValue());
        wifiDiagram.setChartValue("WIFI", value);
        wifiDiagram.setChartValue("", 10d - value);
    }
}

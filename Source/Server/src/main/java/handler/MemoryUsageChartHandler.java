package handler;

import console.Console;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class MemoryUsageChartHandler implements IChartHandler {

    private final Console console;

    public MemoryUsageChartHandler(Console console) {
        this.console = console;
    }

    @Override
    public void addReading(SensorReading reading) {
        try {
            console.newData(reading.getColor(), Float.parseFloat(reading.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

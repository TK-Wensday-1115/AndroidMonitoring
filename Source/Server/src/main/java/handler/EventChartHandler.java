package handler;

import console.Console;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class EventChartHandler implements IChartHandler {

    private final Console console;

    public EventChartHandler(Console console) {
        this.console = console;
    }

    @Override
    public void addReading(SensorReading reading) {
        console.logEvent(reading.getValue());
    }
}

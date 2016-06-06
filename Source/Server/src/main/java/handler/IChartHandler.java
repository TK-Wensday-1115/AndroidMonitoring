package handler;

import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public interface IChartHandler {
    void addReading(SensorReading reading);
}

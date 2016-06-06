package handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class ChartHandlerRepository {

    private Map<String, IChartHandler> handlers;

    public ChartHandlerRepository() {
        handlers = new HashMap<>();
    }

    public void put(String sensorName, IChartHandler handler) {
        handlers.put(sensorName, handler);
    }

    public IChartHandler get(String sensorName) {
        return handlers.get(sensorName);
    }

}

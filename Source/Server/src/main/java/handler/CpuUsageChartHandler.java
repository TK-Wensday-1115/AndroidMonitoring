package handler;

import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.toik.historychart.DataLineDoesNotExistException;
import pl.edu.agh.toik.historychart.HistoryChart;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maciej Imiolek on 2016-06-06.
 */
public class CpuUsageChartHandler implements IChartHandler {

    private final HistoryChart chart;
    private Map<String, Integer> cpuUsages;

    public CpuUsageChartHandler(HistoryChart chart) {
        this.chart = chart;
        this.cpuUsages = new HashMap<>();
    }

    @Override
    public void addReading(SensorReading reading) {
        if (!cpuUsages.containsKey(reading.getColor())) {
            cpuUsages.put(reading.getColor(), chart.registerNewLine(reading.getColor()));
        }

        int lineId = cpuUsages.get(reading.getColor());
        try {
            chart.addNewEntry(lineId, Double.parseDouble(reading.getValue()), Date.from(reading.getTimestamp()));
        } catch (DataLineDoesNotExistException e) {
            e.printStackTrace();
        }
    }
}

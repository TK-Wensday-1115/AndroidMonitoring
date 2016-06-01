package communication;

import console.Console;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;
import pl.edu.agh.toik.historychart.DataLineDoesNotExistException;
import pl.edu.agh.toik.historychart.HistoryChart;
import sample.TermometerPanel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dom-Pc on 2016-05-29.
 */
public class Receiver implements SensorReadingCallback {

    private TermometerPanel batteryDiagram;
    private HistoryChart processorUsageDiagram;
    private HistoryChart gyroscopeUsageDiagram;
    private PieChartPanel wifiDiagram;
    private Console console;

    Map<String, Integer> cpuUsages = new HashMap<>();
    Map<String, Integer> gyroscopeUsages = new HashMap<>();

    public void setTemperatureDiagram(TermometerPanel temperatureDiagram) {
        this.temperatureDiagram = temperatureDiagram;
    }

    private TermometerPanel temperatureDiagram;

    public void setProcessorUsageDiagram(HistoryChart processorUsageDiagram) {
        this.processorUsageDiagram = processorUsageDiagram;
    }

    public void setBatteryDiagram(TermometerPanel batteryDiagram) {
        this.batteryDiagram = batteryDiagram;
    }

    public void setGyroscopeUsageDiagram(HistoryChart gyroscopeUsageDiagram) {
        this.gyroscopeUsageDiagram = gyroscopeUsageDiagram;
    }

    public void setWifiDiagram(PieChartPanel wifiDiagram) {
        this.wifiDiagram = wifiDiagram;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    @Override
    public void receive(SensorReading reading) {

        String sensorName = reading.getSensorName();

        switch (sensorName) {
            case "CPU" :
                if (!cpuUsages.containsKey(reading.getColor())) {
                    cpuUsages.put(reading.getColor(), processorUsageDiagram.registerNewLine(reading.getColor()));
                }
                int lineId = cpuUsages.get(reading.getColor());
                try {
                    processorUsageDiagram.addNewEntry(lineId, Double.parseDouble(reading.getValue()), Date.from(reading.getTimestamp()));
                } catch (DataLineDoesNotExistException e) {
                    e.printStackTrace();
                }
                break;
            case "MEM" :
                try {
                    console.newData(reading.getColor(), Float.parseFloat(reading.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GYR" :
                if (!gyroscopeUsages.containsKey(reading.getColor())) {
                    gyroscopeUsages.put(reading.getColor(), gyroscopeUsageDiagram.registerNewLine(reading.getColor()));
                }
                int newLineId = gyroscopeUsages.get(reading.getColor());
                try {
                    gyroscopeUsageDiagram.addNewEntry(newLineId, Double.parseDouble(reading.getValue()), Date.from(reading.getTimestamp()));
                } catch (DataLineDoesNotExistException e) {
                    e.printStackTrace();
                }
                break;
            case "TEMP" :
                temperatureDiagram.setTemperature(Double.parseDouble(reading.getValue()));
                break;
            case "BAT" :
                batteryDiagram.setTemperature(Double.parseDouble(reading.getValue()));
                break;
            case "WIFI" :
                double value = Double.parseDouble(reading.getValue());
                wifiDiagram.setChartValue("WIFI", value);
                wifiDiagram.setChartValue("", 10d - value);
                break;
            case "GPS" :
                console.logGps(reading.getValue());
                break;
            case "EVE" :
                console.logEvent(reading.getValue());
                break;
        }

    }

}
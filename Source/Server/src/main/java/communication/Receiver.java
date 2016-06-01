package communication;

import console.Console;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;
import pl.edu.agh.toik.historychart.DataLineDoesNotExistException;
import pl.edu.agh.toik.historychart.HistoryChart;
import sample.BubbleBandwidth;
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
    private BubbleBandwidth memoryUsageDiagram;
    private Console gpsDiagram;
    private Console eventDiagram;

    Map<String, Integer> cpuUsages = new HashMap<>();

    public TermometerPanel getTemperatureDiagram() {
        return temperatureDiagram;
    }

    public void setTemperatureDiagram(TermometerPanel temperatureDiagram) {
        this.temperatureDiagram = temperatureDiagram;
    }

    private TermometerPanel temperatureDiagram;

    public HistoryChart getProcessorUsageDiagram() {
        return processorUsageDiagram;
    }

    public void setProcessorUsageDiagram(HistoryChart processorUsageDiagram) {
        this.processorUsageDiagram = processorUsageDiagram;
    }

    public TermometerPanel getBatteryDiagram() {
        return batteryDiagram;
    }

    public void setBatteryDiagram(TermometerPanel batteryDiagram) {
        this.batteryDiagram = batteryDiagram;
    }

    public HistoryChart getGyroscopeUsageDiagram() {
        return gyroscopeUsageDiagram;
    }

    public void setGyroscopeUsageDiagram(HistoryChart gyroscopeUsageDiagram) {
        this.gyroscopeUsageDiagram = gyroscopeUsageDiagram;
    }

    public PieChartPanel getWifiDiagram() {
        return wifiDiagram;
    }

    public void setWifiDiagram(PieChartPanel wifiDiagram) {
        this.wifiDiagram = wifiDiagram;
    }

    public BubbleBandwidth getMemoryUsageDiagram() {
        return memoryUsageDiagram;
    }

    public void setMemoryUsageDiagram(BubbleBandwidth memoryUsageDiagram) {
        this.memoryUsageDiagram = memoryUsageDiagram;
    }

    public Console getGpsDiagram() {
        return gpsDiagram;
    }

    public void setGpsDiagram(Console gpsDiagram) {
        this.gpsDiagram = gpsDiagram;
    }

    public Console getEventDiagram() {
        return eventDiagram;
    }

    public void setEventDiagram(Console eventDiagram) {
        this.eventDiagram = eventDiagram;
    }

    @Override
    public void receive(SensorReading reading) {

        String sensorName = reading.getSensorName();

        switch (sensorName) {
            case "CPU" :
                System.out.println("CPU");

                if (!cpuUsages.containsKey(reading.getColor())) {
                    cpuUsages.put(reading.getColor(), processorUsageDiagram.registerNewLine(reading.getColor()));
                }
                int lineId = cpuUsages.get(reading.getColor());
                try {
                    processorUsageDiagram.addNewEntry(lineId,
                            Double.parseDouble(reading.getValue()),
                            Date.from(reading.getTimestamp()));
                } catch (DataLineDoesNotExistException e) {
                    e.printStackTrace();
                }

                break;
            case "MEM" :
                System.out.println("MEM");
                try {
                    memoryUsageDiagram.newData(reading.getColor(), Float.parseFloat(reading.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GYR" :
                System.out.println("GYR");
                break;
            case "TEMP" :
                System.out.println("TEMP");
                temperatureDiagram.setTemperature(Double.parseDouble(reading.getValue()));
                break;
            case "BAT" :
                System.out.println("BAT");
                batteryDiagram.setTemperature(Double.parseDouble(reading.getValue()));
                break;
            case "WIFI" :
                System.out.println("WIFI");

                double value = Double.parseDouble(reading.getValue());
                wifiDiagram.setChartValue("WIFI", value);
                wifiDiagram.setChartValue("", 10d - value);
                break;
            case "GPS" :
                System.out.println("GPS");
                break;
            case "EVE" :
                System.out.println("EVE");
                break;
        }

    }

}
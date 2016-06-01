package communication;

import console.Console;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;
import pl.edu.agh.toik.historychart.HistoryChart;
import sample.BubbleBandwidth;
import sample.TermometerPanel;

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
                break;
            case "MEM" :
                System.out.println("MEM");
                break;
            case "GYR" :
                System.out.println("GYR");
                break;
            case "TEMP" :
                System.out.println("TEMP");
                break;
            case "BAT" :
                System.out.println("BAT");
                break;
            case "WIFI" :
                System.out.println("WIFI");
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
package communication;

import console.Console;
import handler.*;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;
import pl.edu.agh.toik.historychart.HistoryChart;
import sample.TermometerPanel;

/**
 * Created by Dom-Pc on 2016-05-29.
 */
public class Receiver implements SensorReadingCallback {

    private ChartHandlerRepository handlerRepository;

    public Receiver() {
        handlerRepository = new ChartHandlerRepository();
    }

    public void setProcessorUsageDiagram(HistoryChart processorUsageDiagram) {
        this.handlerRepository.put("CPU", new CpuUsageChartHandler(processorUsageDiagram));
    }

    public void setAccelerometerUsageDiagram(HistoryChart accelerometerUsageDiagram) {
        this.handlerRepository.put("ACC", new AccelerometerChartHandler(accelerometerUsageDiagram));
    }

    public void setTemperatureDiagram(TermometerPanel temperatureDiagram) {
        this.handlerRepository.put("TEMP", new TemperatureChartHandler(temperatureDiagram));
    }

    public void setBatteryDiagram(TermometerPanel batteryDiagram) {
        this.handlerRepository.put("BAT", new BatteryLevelChartHandler(batteryDiagram));
    }

    public void setWifiDiagram(PieChartPanel wifiDiagram) {
        this.handlerRepository.put("WIFI", new WifiChartHandler(wifiDiagram));
    }

    public void setConsole(Console console) {
        this.handlerRepository.put("MEM", new MemoryUsageChartHandler(console));
        this.handlerRepository.put("GPS", new GpsChartHandler(console));
        this.handlerRepository.put("EVE", new EventChartHandler(console));
    }

    @Override
    public void receive(SensorReading reading) {
        IChartHandler handler = this.handlerRepository.get(reading.getSensorName());
        if (handler != null)
            handler.addReading(reading);
    }
}
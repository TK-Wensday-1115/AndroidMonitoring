import communication.Receiver;
import console.Console;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.CommunicationsServer;
import pl.edu.agh.toik.historychart.HistoryChart;
import pl.edu.agh.toik.historychart.HistoryChartFactory;
import pl.edu.agh.toik.historychart.TimeUnit;
import sample.TermometerPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Dom-Pc on 2016-05-29.
 */
public class Main {

    public static void main(String[] args){

        final JFrame temperatureFrame = new JFrame();
        temperatureFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        final TermometerPanel temperatureDiagram = new TermometerPanel("Temperature", 0.0, 100.0);
        temperatureFrame.getContentPane().add(temperatureDiagram, BorderLayout.WEST);
        temperatureFrame.setSize(715, 490);
        temperatureFrame.setVisible(true);
        temperatureFrame.setResizable(false);

        final JFrame batteryFrame = new JFrame();
        batteryFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        final TermometerPanel batteryDiagram = new TermometerPanel("Battery", 0.0, 100.0);
        batteryFrame.getContentPane().add(batteryDiagram, BorderLayout.WEST);
        batteryFrame.setSize(715, 490);
        batteryFrame.setVisible(true);
        batteryFrame.setResizable(false);

        final JFrame processorUsageFrame = new JFrame();
        processorUsageFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        HistoryChart processorUsageDiagram = HistoryChartFactory.createNew("Processor's usage per process", "Time", TimeUnit.Second, "Usage", "%");
        processorUsageFrame.getContentPane().add(processorUsageDiagram, BorderLayout.WEST);
        processorUsageFrame.setSize(715, 490);
        processorUsageFrame.setVisible(true);
        processorUsageFrame.setResizable(false);

        final JFrame accelerometerUsageFrame = new JFrame();
        accelerometerUsageFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        HistoryChart accelerometerUsageDiagram = HistoryChartFactory.createNew("Accelerometer", "Time", TimeUnit.Second, "Force", "G");
        accelerometerUsageFrame.getContentPane().add(accelerometerUsageDiagram, BorderLayout.WEST);
        accelerometerUsageFrame.setSize(715, 490);
        accelerometerUsageFrame.setVisible(true);
        accelerometerUsageFrame.setResizable(false);

        final JFrame wifiFrame = new JFrame();
        wifiFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        PieChartPanel wifiDiagram = new PieChartPanel("Wifi");
        wifiFrame.getContentPane().add(wifiDiagram, BorderLayout.WEST);
        wifiFrame.setSize(715, 490);
        wifiFrame.setVisible(true);
        wifiFrame.setResizable(false);

        final Console console = new Console();

        final Thread gpsThread = new Thread(() -> {
            try {
                console.startApplication();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gpsThread.start();

        Receiver receiver = new Receiver();
        receiver.setBatteryDiagram(batteryDiagram);
        receiver.setConsole(console);
        receiver.setAccelerometerUsageDiagram(accelerometerUsageDiagram);
        receiver.setProcessorUsageDiagram(processorUsageDiagram);
        receiver.setTemperatureDiagram(temperatureDiagram);
        receiver.setWifiDiagram(wifiDiagram);

        CommunicationsServer.registerCallback(receiver);
        CommunicationsServer.start(8080);
    }
}
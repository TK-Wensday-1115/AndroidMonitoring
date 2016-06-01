import communication.Receiver;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.smialek.tk.communications.server.CommunicationsServer;
import pl.edu.agh.toik.historychart.HistoryChart;
import pl.edu.agh.toik.historychart.HistoryChartFactory;
import pl.edu.agh.toik.historychart.TimeUnit;
import sample.BubbleBandwidth;
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
        HistoryChart processorUsageDiagram = HistoryChartFactory.createNew("Processor's usage per process", "Time", TimeUnit.Second, "Weight", "mb");
        processorUsageFrame.getContentPane().add(processorUsageDiagram, BorderLayout.WEST);
        processorUsageFrame.setSize(715, 490);
        processorUsageFrame.setVisible(true);
        processorUsageFrame.setResizable(false);

        final JFrame gyroscopeUsageFrame = new JFrame();
        gyroscopeUsageFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        HistoryChart gyroscopeUsageDiagram = HistoryChartFactory.createNew("Gyroscope", "Time", TimeUnit.Second, "Angle", "rad");
        gyroscopeUsageFrame.getContentPane().add(gyroscopeUsageDiagram, BorderLayout.WEST);
        gyroscopeUsageFrame.setSize(715, 490);
        gyroscopeUsageFrame.setVisible(true);
        gyroscopeUsageFrame.setResizable(false);

        final JFrame wifiFrame = new JFrame();
        wifiFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        PieChartPanel wifiDiagram = new PieChartPanel("Wifi");
        wifiFrame.getContentPane().add(wifiDiagram, BorderLayout.WEST);
        wifiFrame.setSize(715, 490);
        wifiFrame.setVisible(true);
        wifiFrame.setResizable(false);

        final BubbleBandwidth memoryUsageDiagram = new BubbleBandwidth();

        final Thread memoryUsageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                memoryUsageDiagram.startApplication();
            }
        });

        memoryUsageThread.start();

//        final Console gpsDiagram = new Console();
//
//        final Thread gpsThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    gpsDiagram.start(new Stage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        gpsThread.start();
//
//        final Console eventDiagram = new Console();
//
//        final Thread eventThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    eventDiagram.start(new Stage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        eventThread.start();

        Receiver receiver = new Receiver();
        receiver.setBatteryDiagram(batteryDiagram);
//        receiver.setEventDiagram(eventDiagram);
//        receiver.setGpsDiagram(gpsDiagram);
        receiver.setGyroscopeUsageDiagram(gyroscopeUsageDiagram);
        receiver.setMemoryUsageDiagram(memoryUsageDiagram);
        receiver.setProcessorUsageDiagram(processorUsageDiagram);
        receiver.setTemperatureDiagram(temperatureDiagram);
        receiver.setWifiDiagram(wifiDiagram);
        CommunicationsServer.registerCallback(receiver);
        CommunicationsServer.start(8080);

    }
}
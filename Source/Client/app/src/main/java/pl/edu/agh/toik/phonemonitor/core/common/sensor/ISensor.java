package pl.edu.agh.toik.phonemonitor.core.common.sensor;

/**
 * Created by Imiolak.
 * 18.05.2016
 */
public interface ISensor {

    String getDescription();

    String getCurrentReading();

    long getDefaultInterval();
}

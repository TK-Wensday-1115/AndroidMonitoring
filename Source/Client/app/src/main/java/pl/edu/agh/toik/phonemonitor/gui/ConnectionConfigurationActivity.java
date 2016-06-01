package pl.edu.agh.toik.phonemonitor.gui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.toik.phonemonitor.R;
import pl.edu.agh.toik.phonemonitor.core.common.output.IOutput;
import pl.edu.agh.toik.phonemonitor.core.common.sensor.ISensor;
import pl.edu.agh.toik.phonemonitor.core.platform.output.LogCatOutput;
import pl.edu.agh.toik.phonemonitor.core.platform.output.RawRequestNetworkOutput;
import pl.edu.agh.toik.phonemonitor.core.platform.runner.SensorRunner;
import pl.edu.agh.toik.phonemonitor.core.platform.sensor.SensorFactory;

public class ConnectionConfigurationActivity extends AppCompatActivity {

    private boolean running = false;
    private List<CheckBox> parameterCheckBoxes;
    private String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_configuration);

        Config.context = this;

        prepareGui();
    }

    private void prepareGui() {
        populateParameterCheckBoxes();
    }

    private void populateParameterCheckBoxes() {
        String[] parameters = getResources().getStringArray(R.array.parameters_array);
        LinearLayout cbLayout = (LinearLayout) findViewById(R.id.parametersCBLayout);

        parameterCheckBoxes = new ArrayList<>();

        for (String param: parameters) {
            CheckBox newCheckBox = new CheckBox(getApplicationContext());
            newCheckBox.setText(param);
            newCheckBox.setTextColor(Color.BLACK);

            cbLayout.addView(newCheckBox);
            parameterCheckBoxes.add(newCheckBox);
        }
    }

    public void runButtonClick(View view) {
        if (running)
            return;

        TextView hostTextView = (TextView) findViewById(R.id.ipEditText);
        this.host = hostTextView.getText().toString();

        startMonitor(view);
    }

    private void startMonitor(View view) {
        for (CheckBox cb: parameterCheckBoxes) {
            if (!cb.isChecked())
                continue;

            String param = cb.getText().toString();
            ISensor sensor = SensorFactory.create(param);
            IOutput[] outputs = new IOutput[] {
                    new RawRequestNetworkOutput(this.host, sensor.getSensorName()),
                    new LogCatOutput(sensor.getSensorName())
            };
            SensorRunner runner = new SensorRunner(sensor, outputs);

            runner.run();
        }

        Button runButton = (Button) view.findViewById(R.id.runButton);
        runButton.setText("RUNNING!");
        runButton.setBackgroundColor(Color.GREEN);

        running = true;
    }
}

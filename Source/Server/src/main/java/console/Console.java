package console;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logger.component.Logger;
import java.util.Date;

/**
 * Created by Dom-Pc on 2016-05-31.
 */
public class Console extends Application {

    public void startApplication() {
        launch(new String[0]);
    }

    Thread thread;
    Logger logger;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger = new Logger();
        Scene scene = new Scene(logger);
        primaryStage.setScene(scene);
        primaryStage.show();
        logger.setColor(Color.BROWN);
        logger.setHistorySize(1000);
    }

    @Override
    public void stop() throws Exception {
        thread.interrupt();
    }

    public void log(String message) {
        logger.append(new Date(), message);
    }

}
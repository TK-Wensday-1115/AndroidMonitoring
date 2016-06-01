package console;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logger.component.Logger;
import sample.CircleData;
import sample.CircleDataController;
import sample.Transition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Dom-Pc on 2016-05-31.
 */
public class Console extends Application {

    public void startApplication() {
        launch(new String[0]);
    }

    Thread thread;
    Logger gpsLogger;
    Logger eventLogger;

    /**
     * Collection of current data of existing circles. Used for easier iteration over current CircleDatas.
     */
    public ArrayList<CircleData> circles = new ArrayList<>();
    public static AnchorPane canvas;
    public static Pane circlesPane;
    public final CircleDataController circleDataController = new CircleDataController();
    private final static int CANVAS_WIDTH = 800;
    private final static int CANVAS_HEIGHT = 800;

    /**
     * Variables used to determine which transition should be used for a circle being drawn right now.
     */
    private ArrayList<Transition> transitions;
    private int transitionsIndex;

    /**
     * Variable used to determine current scale multiplier of the view.
     */
    private double scaleFactor = 1.0;

    /**
     * Two variables used for dragging of the view.
     */
    private double pressedX;
    private double pressedY;

    public Console() {
        transitions = new ArrayList<>();
        transitions.add(new Transition(0, 1)); //UP
        transitions.add(new Transition(1, 1)); //RIGHT-UP
        transitions.add(new Transition(1, 0));  //RIGHT
        transitions.add(new Transition(1, -1)); //RIGHT-DOWN
        transitions.add(new Transition(0, -1)); //DOWN
        transitions.add(new Transition(-1, -1)); //LEFT-DOWN
        transitions.add(new Transition(-1, 0)); //LEFT
        transitions.add(new Transition(-1, 1)); //LEFT-UP
        transitionsIndex = 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage thirdStage = new Stage();
        gpsLogger = new Logger();
        Scene thirdScene = new Scene(gpsLogger);
        thirdStage.setScene(thirdScene);
        thirdStage.show();
        gpsLogger.setColor(Color.BROWN);
        gpsLogger.setHistorySize(1000);

        Stage secondaryStage = new Stage();
        eventLogger = new Logger();
        Scene scene2 = new Scene(eventLogger);
        secondaryStage.setScene(scene2);
        secondaryStage.show();
        eventLogger.setColor(Color.BROWN);
        eventLogger.setHistorySize(1000);

        canvas = new AnchorPane();
        circlesPane = new Pane();
        circlesPane.setMinHeight(CANVAS_HEIGHT);
        circlesPane.setMinWidth(CANVAS_WIDTH - 200);
        circlesPane.getStyleClass().add("bordered-pane");

        final Scene scene = new Scene(canvas, CANVAS_WIDTH, CANVAS_HEIGHT);

        circlesPane.setOnScroll(event -> {
            event.consume();
            if (event.getDeltaY() == 0 || (event.getDeltaY() < 0 && circlesPane.getScaleY() < 1.0)) {
                return;
            }

            scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 1/1.1;
            circlesPane.setScaleX(circlesPane.getScaleX() * scaleFactor);
            circlesPane.setScaleY(circlesPane.getScaleY() * scaleFactor);
        });

        circlesPane.setOnMousePressed(event -> {
            pressedX = event.getX();
            pressedY = event.getY();
        });

        circlesPane.setOnMouseDragged(event -> {
            circlesPane.setTranslateX(circlesPane.getTranslateX() + event.getX() - pressedX);
            circlesPane.setTranslateY(circlesPane.getTranslateY() + event.getY() - pressedY);
            event.consume();
        });

        primaryStage.setTitle("Bubble Bandwidth");
        primaryStage.setScene(scene);
        primaryStage.show();

        canvas.getChildren().add(circlesPane);
    }

    @Override
    public void stop() throws Exception {
        thread.interrupt();
    }

    public void logGps(String message) {
        gpsLogger.append(new Date(), message);
    }

    public void logEvent(String message) {
        eventLogger.append(new Date(), message);
    }

    /**
     * Creates a Runnable that will update current view.
     * Best way to do this in javafx.
     */
    private void taskToUpdateStage() {
        Platform.runLater(() -> updateView());
    }

    /**
     * Method used to update current state of javafx stage.
     * It paints any changes concerning circles (added new, deleted exsisting, modified existing).
     */
    private void updateView() {
        circlesPane.getChildren().clear();
        circles.clear();
        HashMap<String, CircleData> circleDatas = circleDataController.getCirclesDatasMap();
        for(String key : circleDatas.keySet()) {

            CircleData cd = circleDatas.get(key);
            cd.recalculatePercents(circleDataController.getSummedCirclesValues());

            Circle c = new Circle(cd.getPercentsValue()*scaleFactor, cd.getColor());
            Tooltip.install(
                    c,
                    createTooltip(cd)
            );

            c.setStrokeWidth(1.0);
            c.setStroke(Color.BLACK);
            cd.setCircle(c);
            circles.add(cd);
            c.setId(cd.getId());

            if(cd.getX() == -1 && cd.getY() == -1) {
                cd.setX(400);
                cd.setY(300);
            }
            c.setCenterX(cd.getX());
            c.setCenterY(cd.getY());

            circlesPane.getChildren().addAll(c);
            for(CircleData cir : circles) {
                checkShapeIntersection(cir);
            }
        }
    }

    /**
     * Used to operate on data concerning circles. This method will determine whether we must add a new circle,
     * modify existing one or remove it.
     * @param id - id of circle that we are going to add/modify/delete
     * @param value - value for circle that will be modified/added
     * @throws Exception - throws exception if given value is invalid or when value = -1 (points to removal operation)
     * but id does not exists
     */
    public void newData(String id, Float value) throws Exception {
        HashMap<String, CircleData> circlesMap = circleDataController.getCirclesDatasMap();
        if(value <= 0.0) {
            if(value == -1.0) {
                if(circlesMap.containsKey(id)) {
                    circleDataController.removeCircle(id);
                    taskToUpdateStage();
                } else {
                    throw new Exception("Circle with given id=" + id + " does not exist and so can't be deleted!");
                }
            } else {
                throw new Exception("Invalid value! Value parameter must be either greater than 0 " +
                        "(adding new circle or editing existing one) or equal to '-1'(deleting existing circle) !");
            }
        }

        if(circlesMap.containsKey(id)) {
            circleDataController.modifyExistingCirlce(id, value);
            taskToUpdateStage();
        } else {
            circleDataController.addNewCircle(id, value, transitions.get(transitionsIndex));
            taskToUpdateStage();
            transitionsIndex++;
            if(transitionsIndex >= transitions.size()) {
                transitionsIndex = 0;
            }
        }
    }

    /**
     * Creates tooltip for a given circle.
     * It contains description of that circle: it's id, value and percentage value.
     * @param cd - data of the circle we create tooltip for
     * @return
     */
    private Tooltip createTooltip(CircleData cd) {
        Tooltip tooltip = new Tooltip("ID: "+cd.getId()+"\n"+
                "VALUE: "+cd.getValue()+"\n"+
                "PERCENTAGE: "+cd.getPercentsValue());
        tooltip.setTextAlignment(TextAlignment.CENTER);

        return tooltip;
    }

    /**
     * Checks collisions between given circle and all other circles.
     * If collision is detected then we move one with the smaller id away until it's not colliding anymore.
     * @param cd1 - data of the circle we check collisions with
     */
    private void checkShapeIntersection(CircleData cd1) {
        Shape circle1 = cd1.getCircle();
        for (CircleData cd2 : circles) {
            Shape circle2 = cd2.getCircle();
            if (!circle2.equals(circle1)) {
                boolean intersect = circle1.getBoundsInParent().intersects(circle2.getBoundsInParent());

                Circle c1 = (Circle) circle1;
                Circle c2 = (Circle) circle2;
                while(intersect){
                    intersect = circle1.getBoundsInParent().intersects(circle2.getBoundsInParent());
                    if(Integer.parseInt(c1.getId()) < Integer.parseInt(c2.getId())) {
                        Transition transition = cd2.getTransition();
                        circle2.setLayoutX(circle2.getLayoutX()+ transition.x);
                        circle2.setLayoutY(circle2.getLayoutY()+ transition.y);
                    } else {
                        Transition transition = cd1.getTransition();
                        circle1.setLayoutX(circle1.getLayoutX() + transition.x);
                        circle1.setLayoutY(circle1.getLayoutY() + transition.y);
                    }
                }
            }
        }

    }

}
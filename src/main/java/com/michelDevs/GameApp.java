package com.michelDevs;

import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.Substation;
import com.powsybl.iidm.network.Line;
import com.powsybl.iidm.network.Terminal;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameApp extends Application {

    private GameEngine engine = new GameEngine();
    private Network network;
    private Group root = new Group();
    private Map<String, Point2D> nodeCoordonates = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        network = engine.getNetwork();

        int x = 200;
        for (Substation substation : network.getSubstations()) {
            nodeCoordonates.put(substation.getId(), new Point2D(x, 300));
            x += 400;
        }

        drawNetwork();

        Scene scene = new Scene(root, 800, 600, Color.BLACK); // Fond noir style "Scada"
        primaryStage.setTitle("PowSyBl Grid Master");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawNetwork() {


        for (Substation substation : network.getSubstations()) {
            Circle node1 = new Circle(
                    nodeCoordonates.get(substation.getId()).getX(),
                    nodeCoordonates.get(substation.getId()).getY(),
                    20,
                    Color.CYAN);
            node1.setId(substation.getId());
            root.getChildren().add(node1);
        }

        for (Line line : network.getLines()) {
            String nodeId1 = line.getTerminal1().getVoltageLevel().getSubstation().get().getId();
            String nodeId2 = line.getTerminal2().getVoltageLevel().getSubstation().get().getId();
            javafx.scene.shape.Line lineShape = new javafx.scene.shape.Line(
                    nodeCoordonates.get(nodeId1).getX(),
                    nodeCoordonates.get(nodeId1).getY(),
                    nodeCoordonates.get(nodeId2).getX(),
                    nodeCoordonates.get(nodeId2).getY()
            );
            lineShape.setId(line.getId());
            lineShape.setStroke(Color.WHITE);
            lineShape.setStrokeWidth(5);

            // Exemple de logique à mettre dans l'événement de clic
            lineShape.setOnMouseClicked(event -> {
                Terminal t1 = line.getTerminal1();

                if (t1.isConnected()) {
                    t1.disconnect();
                    lineShape.setStroke(Color.GRAY); // Visuel : Gris
                    lineShape.getStrokeDashArray().addAll(10d, 10d); // Visuel : Pointillés
                    System.out.println("Ligne ouverte !");
                } else {
                    // RECONNEXION PHYSIQUE
                    var vl = t1.getVoltageLevel();


                    lineShape.setStroke(Color.WHITE);
                    lineShape.getStrokeDashArray().clear();
                    System.out.println("Ligne fermée et reconnectée !");
                }
            });



            root.getChildren().add(lineShape);
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

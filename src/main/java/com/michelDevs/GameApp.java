package com.michelDevs;

import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.Substation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameApp extends Application {

    private GameEngine engine = new GameEngine();
    private Network network;
    private Group root = new Group();
    private Map<String, Point2> nodeCoordonates = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        network = engine.getNetwork();

        int x = 200;
        for (Substation substation : network.getSubstations()) {
            nodeCoordonates.put(substation.getId(), new Point(x, 300));
            x += 400;
        }

        drawNetwork();

        Scene scene = new Scene(root, 800, 600, Color.BLACK); // Fond noir style "Scada"
        primaryStage.setTitle("PowSyBl Grid Master");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawNetwork() {
        // --- TON DÉFI COMMENCE ICI ---

        // Indice : Tu dois placer les éléments manuellement pour l'instant.
        // Poste 1 à gauche (x=200, y=300)
        // Poste 2 à droite (x=600, y=300)

        // 1. Dessine un Cercle pour chaque Substation du réseau
        // Pour l'instant, fais-le "en dur" ou essaie de boucler sur network.getSubstations()
        // et de leur attribuer des coordonnées arbitraires.

        // 2. Dessine une Ligne JavaFX pour relier les deux.

        // Exemple pour t'aider à démarrer :
        /*
        Circle node1 = new Circle(200, 300, 20, Color.CYAN);
        root.getChildren().add(node1);
        */

        for (Substation substation : network.getSubstations()) {
            Circle node1 = new Circle(
                    nodeCoordonates.get(substation.getId()).x,
                    nodeCoordonates.get(substation.getId()).y,
                    20,
                    Color.CYAN);
            node1.setId(substation.getId());
            root.getChildren().add(node1);
        }

        Line line = new Line(200, 300, 600, 300);
        line.setStroke(Color.WHITE);
        line.setId("Ligne_HT");
        root.getChildren().add(line);



    }

    public static void main(String[] args) {
        launch(args);
    }
}

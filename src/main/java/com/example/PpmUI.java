package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;

public class PpmUI extends ImageUI {
    private static HBox hbox ;
    private static HBox hbox2 ;
    private static HBox hbox3 ;
    private static void updateImage() {
        try {
            PpmConverter.ecrireImagePPM(myObj.toPath().toString());
            imageView.setImage(PpmConverter.convert(myObj));
        } catch (Exception ef) {
            System.out.println("An error occurred.");
        }
    }

    private static HBox seuillage(VBox root){
         hbox = new HBox();
        hbox.setSpacing(10);
        Button button= new Button("apply");
        Button resetButton= new Button("reset");
        TextField r = new TextField ();
        r.setPromptText("R");
        r.setMaxWidth(40);
        TextField g = new TextField ();
        g.setPromptText("G");
        g.setMaxWidth(40);
        TextField b = new TextField ();
        b.setPromptText("B");
        b.setMaxWidth(40);
        Text label1 = new Text("Seuillage:");
        label1.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");

        Text error = new Text("please provide the R,G,B values");
        error.setFill(Color.RED);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    int ir=Integer.parseInt(r.getText()); 
                    int ig=Integer.parseInt(g.getText()); 
                    int ib=Integer.parseInt(b.getText()); 
                    PpmConverter.seuiller( ir, ig, ib);
                    hbox.getChildren().remove(error);
                } catch (NumberFormatException m) {
                    hbox.getChildren().add(error);
                }
                updateImage();
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                imageView.setImage(PpmConverter.convert(file));
            }
        });

        button.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        resetButton.setStyle("-fx-font: 18 arial; -fx-base: #E06666;");
        
        hbox.getChildren().add(label1);
        hbox.getChildren().add(r);
        hbox.getChildren().add(g);
        hbox.getChildren().add(b);
        hbox.getChildren().add(button);
        hbox.getChildren().add(resetButton);
        return hbox ;
    }

    private static HBox seuillageVariante(VBox root) {
        hbox2 = new HBox();
        hbox2.setSpacing(10);
        Text label = new Text("Seuillage Variante Et/Ou :");
        
        label.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        Button button= new Button("apply");
        TextField r = new TextField ();
        r.setPromptText("R");
        r.setMaxWidth(40);
        TextField g = new TextField ();
        g.setPromptText("G");
        g.setMaxWidth(40);
        TextField b = new TextField ();
        b.setPromptText("B");
        b.setMaxWidth(40);
        CheckBox c = new CheckBox("OU");
        c.setStyle("-fx-padding: 5 0 0 0;");
        Text error = new Text("please provide the threshhold");
        error.setFill(Color.RED);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    int ir=Integer.parseInt(r.getText()); 
                    int ig=Integer.parseInt(g.getText()); 
                    int ib=Integer.parseInt(b.getText()); 
                    PpmConverter.seuillerVariante(ir,ig,ib, c.isSelected());
                    hbox2.getChildren().remove(error);
                } catch (NumberFormatException m) {
                    hbox2.getChildren().add(error);
                }
                updateImage();
            }
        });
        button.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        
        hbox2.getChildren().add(label);
        hbox2.getChildren().add(r);
        hbox2.getChildren().add(g);
        hbox2.getChildren().add(b);
        hbox2.getChildren().add(c);
        hbox2.getChildren().add(button);
        return hbox2;
    }

    private static HBox otsuHBox(VBox root) {
        hbox3 = new HBox();
        hbox3.setSpacing(10);
        Text label = new Text("Otsu :");
        
        label.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        Button button= new Button("independant");
        Button button1= new Button("avec OU");
        Button button2= new Button("avec ET");
        int r=0,g=1,b=2;

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int seuilR = (int) PpmConverter.otsuRGB( r);
                int seuilG = (int) PpmConverter.otsuRGB( g);
                int seuilB = (int) PpmConverter.otsuRGB( b);
                System.out.println("r : "+seuilR);
                System.out.println("g : "+seuilG);
                System.out.println("b : "+seuilB);
                PpmConverter.seuiller(seuilR, seuilG, seuilB);
                updateImage();
            }
        });
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int seuilR = (int) PpmConverter.otsuRGB( r);
                int seuilG = (int) PpmConverter.otsuRGB( g);
                int seuilB = (int) PpmConverter.otsuRGB( b);
                System.out.println("r : "+seuilR);
                System.out.println("g : "+seuilG);
                System.out.println("b : "+seuilB);
                PpmConverter.seuillerVariante(seuilR, seuilG, seuilB, true);
                updateImage();
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int seuilR = (int) PpmConverter.otsuRGB( r);
                int seuilG = (int) PpmConverter.otsuRGB( g);
                int seuilB = (int) PpmConverter.otsuRGB( b);
                System.out.println("r : "+seuilR);
                System.out.println("g : "+seuilG);
                System.out.println("b : "+seuilB);
                PpmConverter.seuillerVariante(seuilR, seuilG, seuilB, false);
                updateImage();
            }
        });
        button.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        button1.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");
        button2.setStyle("-fx-font: 18 arial; -fx-base: #b6e7c9;");

        hbox3.getChildren().add(label);
        hbox3.getChildren().add(button);
        hbox3.getChildren().add(button1);
        hbox3.getChildren().add(button2);
        return hbox3;
    }

    public static void loadImage(File imageFile, VBox root) {
        imageView.setImage(PpmConverter.convert(imageFile));
        root.getChildren().add(imageView);
        HBox hbox =  seuillage(root);
        root.getChildren().add(hbox);
        
        HBox hbox2 = seuillageVariante(root);
        root.getChildren().add(hbox2);

        root.getChildren().add(otsuHBox(root));
    }

    public static void unloadImage(VBox root) {
        root.getChildren().remove(imageView);
        root.getChildren().remove(hbox);
        root.getChildren().remove(hbox2);
    }

}

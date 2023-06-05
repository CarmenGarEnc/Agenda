package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegalosController implements Initializable {
    @FXML
    private ImageView globos;
    @FXML
    private ImageView globos1;
    @FXML
    private Button btnRegalos;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnVolver;
    @FXML
    private TextField txtRegalo1;

    @FXML
    private TextField txtRegalo2;

    @FXML
    private TextField txtRegalo3;

    @FXML
    private TextField txtRegalo4;

    @FXML
    private TextField txtRegalo5;

    /**
     * Metodo initialize crea una animacion en lasimagenes globos y globo1
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    public void  initialize(URL url, ResourceBundle resourceBundle){
        //translate de la primera imagen de globos
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(globos);
        translate.setDuration(Duration.millis(5000));
        translate.setByY(-360);
        translate.play();
        //translate de la segunda imagen de globos
        TranslateTransition tr = new TranslateTransition();
        tr.setNode(globos1);
        tr.setDuration(Duration.millis(5000));
        tr.setByY(-360);
        tr.play();
    }

    /**
     * Metodo guardarRegalos envia la informacion de lso regalos para que COnexionBBDD lo actualice en la base de datos
     * @param event ActionEvent
     */
    @FXML
    void guardarRegalos(ActionEvent event) {
        ArrayList<String> regalos=new ArrayList<String>();
        String reg1=txtRegalo1.getText();
        String reg2=txtRegalo2.getText();
        String reg3=txtRegalo3.getText();
        String reg4=txtRegalo4.getText();
        String reg5=txtRegalo5.getText();
        if(!reg1.isEmpty()){
            regalos.add(reg1);
        }
        if(!reg2.isEmpty()){
            regalos.add(reg2);
        }
        if(!reg3.isEmpty()){
            regalos.add(reg3);
        }
        if(!reg4.isEmpty()){
            regalos.add(reg4);
        }
        if(!reg5.isEmpty()){
            regalos.add(reg5);
        }
        if (regalos.isEmpty()){
            Alert alertvacio = new Alert(Alert.AlertType.WARNING);
            alertvacio.setTitle("Regalos vacios.");
            alertvacio.setContentText("Debe poner al menos un regalo para poder guardarse.");
            alertvacio.showAndWait();
        }else{
            if(ConexionBBDD.insertarRegalos(regalos)){
                Alert alertresagoOK = new Alert(Alert.AlertType.CONFIRMATION);
                alertresagoOK.setTitle("Regalos guardados.");
                alertresagoOK.setContentText("Se han guardado correctamente los regalos.");
                alertresagoOK.showAndWait();
            }else{
                Alert alertregaloERROR = new Alert(Alert.AlertType.ERROR);
                alertregaloERROR.setTitle("Error al guardar los regalos.");
                alertregaloERROR.setContentText("No se ha podido guardar los regalos introducidos.");
                alertregaloERROR.showAndWait();
            }
        }
    }
    /**
     * Metodo salir, una vez se pulsa el botón btnSalir, el programa cierra su ejecución
     * @param event ActionEvent
     */
    @FXML
    void salir(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrando...");
        alert.setContentText("Quieres Salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK){
            Stage stagePrincipal=(Stage) btnSalir.getScene().getWindow();
            stagePrincipal.close();
        }
    }
    /**
     * Metodo volver, una vez se pulsa el boton btnVolver, vuelve a la escena operaciones
     * @param event ActionEvent
     */
    @FXML
    void volver(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("operaciones.fxml"));
        try {
            Parent rt = fxmlLoader.load();
            Scene scene = new Scene(rt);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stagePrincipal=(Stage) btnVolver.getScene().getWindow();
        stagePrincipal.close();
    }

}

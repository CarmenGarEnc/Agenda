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
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BuscarController implements Initializable {
    @FXML
    private ImageView amigos;
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnVolver;
    @FXML
    public ChoiceBox<String> cboxUsuarios;
    ArrayList<String> usuarios= ConexionBBDD.devolverUsuarios();

    /**
     * Metodo initialize, inicia los valores del cboxUsuarios y la animacion de rotacion de la imagen amigos
     * @param url
     * @param resourceBundle
     */
    public void  initialize(URL url, ResourceBundle resourceBundle){
        //Esto sirve para rellenar el comboBox inicialmente con los usuarios
        cboxUsuarios.getItems().addAll(usuarios);
        cboxUsuarios.setValue(usuarios.get(0));
        //rotate
        RotateTransition rotate = new RotateTransition();
         rotate.setNode(amigos);
         rotate.setDuration(Duration.millis(15000));
         rotate.setCycleCount(TranslateTransition.INDEFINITE);
         rotate.setInterpolator(Interpolator.LINEAR);
         rotate.setByAngle(360);
         rotate.play();

    }
    private static String idBuscar="";

    /**
     * Metodo getIdBuscar
     * @return String
     */
    public static String getIdBuscar() {
        return idBuscar;
    }

    /**
     * Metodo setIdBuscar
     * @param idBuscar String
     */
    public static void setIdBuscar(String idBuscar) {
        BuscarController.idBuscar = idBuscar;
    }

    /**
     * Metodo buscar, da el valor a la variable idBuscar del usuario que se desea buscar y carga la escena de informacion
     * @param event ActionEvent
     */
    @FXML
    void buscar(ActionEvent event) {
        setIdBuscar(cboxUsuarios.getValue());

        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("informacion.fxml"));
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
        Stage stagePrincipal=(Stage) btnBuscar.getScene().getWindow();
        stagePrincipal.close();
    }

    /**
     * Metodo volver, una vez se pulsa el boton btnVolver, vuelve a la escena consultar
     * @param event ActionEvent
     */
    @FXML
    void volver(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("consultar.fxml"));
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
    /**
     * Metodo cerrar, una vez se pulsa el botón btnCerrar, el programa cierra su ejecución
     * @param event ActionEvent
     */
    @FXML
    void cerrar(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrando...");
        alert.setContentText("Quieres Salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== ButtonType.OK){
            Stage stagePrincipal=(Stage) btnCerrar.getScene().getWindow();
            stagePrincipal.close();
            ConexionBBDD.desconectar();
        }
    }

}

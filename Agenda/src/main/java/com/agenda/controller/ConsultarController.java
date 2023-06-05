package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class ConsultarController {

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnVolver;
    /**
     * Metodo irBurcar cuando se pulsa el boton btnConsultar carga la escena de buscar
     * @param event ActionEvent
     */
    @FXML
    void irBuscar(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("buscar.fxml"));
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
        Stage stage1=(Stage) btnConsultar.getScene().getWindow();
        stage1.close();
    }

    /**
     * Metodo irOperaciones cuando se pulsa el boton btnModificar carga la escena de operaciones
     * @param event ActionEvent
     */
    @FXML
    void irOperaciones(ActionEvent event) {
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
        Stage stage2=(Stage) btnModificar.getScene().getWindow();
        stage2.close();
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
            ConexionBBDD.desconectar();
        }

    }
    /**
     * Metodo volver, una vez se pulsa el boton btnVolver, vuelve a la escena inicio
     * @param event ActionEvent
     */
    @FXML
    void volver(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("inicio.fxml"));
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

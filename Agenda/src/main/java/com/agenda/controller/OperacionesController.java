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

public class OperacionesController {

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnDatos;

    @FXML
    private Button btnDeseos;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnVolver;
    /**
     * Metodo irInsertarusuario cuando se pulsa el boton btnAlta carga la escena de insertarusuario
     * @param event ActionEvent
     */
    @FXML
    void irInsertarusuario(ActionEvent event) {
        if(ConexionBBDD.getID().equals("Admin")){
            FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("insertarusuario.fxml"));
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
            Stage stagePrincipal=(Stage) btnAlta.getScene().getWindow();
            stagePrincipal.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Usuario no permitido");
            alert.setContentText("No tiene permiso para acceder a esta sección.");
            alert.showAndWait();
        }

    }
    /**
     * Metodo irModificar cuando se pulsa el boton btnDatos carga la escena de modificar
     * @param event ActionEvent
     */
    @FXML
    void irModificar(ActionEvent event) {
        if(!ConexionBBDD.getID().equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("modificar.fxml"));
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
            Stage stagePrincipal = (Stage) btnDatos.getScene().getWindow();
            stagePrincipal.close();
        }else{
            Alert alertadmin=new Alert(Alert.AlertType.INFORMATION);
            alertadmin.setTitle("Usuario Administrador");
            alertadmin.setContentText("El usuario Administrador no puede modificar datos, debe eliminar el usuario e introducirlo de nuevo.");
            alertadmin.showAndWait();
        }
    }
    /**
     * Metodo irRegalos cuando se pulsa el boton btnDeseos carga la escena de regalos
     * @param event ActionEvent
     */
    @FXML
    void irRegalos(ActionEvent event) {
        if(!ConexionBBDD.getID().equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(AgendaApplication.class.getResource("regalos.fxml"));
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
            Stage stagePrincipal = (Stage) btnDeseos.getScene().getWindow();
            stagePrincipal.close();
        }else{
            Alert alertadmin=new Alert(Alert.AlertType.INFORMATION);
            alertadmin.setTitle("Usuario Administrador");
            alertadmin.setContentText("El usuario Administrador no puede introducir regalos de usuario.");
            alertadmin.showAndWait();
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
            ConexionBBDD.desconectar();
        }
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

}

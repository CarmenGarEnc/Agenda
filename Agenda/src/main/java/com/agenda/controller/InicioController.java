package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class InicioController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSalir;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsuario;

    /**
     * Método loguear, enviar la informacion a la bbdd para validarla
     * @param event ActionEvent
     */
    @FXML
    void loguear(ActionEvent event) {
        String id = txtUsuario.getText();
        String pass = txtPassword.getText();
        Alert alertNoexiste, alertPassError, alertVacio, alertConexion;
        ConexionBBDD.conectar();
        if (!id.isEmpty()|| !pass.isEmpty()){
            if (ConexionBBDD.conectar()) {
                if (ConexionBBDD.comprobarUsuario(id, pass) == 0) {
                    alertNoexiste = new Alert(Alert.AlertType.ERROR);
                    alertNoexiste.setTitle("Usuario no registrado");
                    alertNoexiste.setContentText("El usuario que ha introducido no existe.");
                    alertNoexiste.showAndWait();
                } else if (ConexionBBDD.comprobarUsuario(id, pass) == 1) {
                    alertPassError = new Alert(Alert.AlertType.ERROR);
                    alertPassError.setTitle("Contraseña Incorrecta");
                    alertPassError.setContentText("La contraseña introducida es erronea.");
                    alertPassError.showAndWait();
                } else if (ConexionBBDD.comprobarUsuario(id, pass) == 2) {
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
                    Stage stagePrincipal = (Stage) btnLogin.getScene().getWindow();
                    stagePrincipal.close();
                }
            } else {
                alertConexion = new Alert(Alert.AlertType.ERROR);
                alertConexion.setTitle("No conectado");
                alertConexion.setContentText("No hay conexion con el servidor.");
                alertConexion.showAndWait();
            }
        } else {
            alertVacio = new Alert(Alert.AlertType.ERROR);
            alertVacio.setTitle("Valores vacios");
            alertVacio.setContentText("Debe introducir usuario y contraseña para poder acceder.");
            alertVacio.showAndWait();
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
        else{
            txtUsuario.requestFocus();
        }

    }

}

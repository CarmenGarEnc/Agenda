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
import java.time.LocalDate;
import java.util.Optional;

public class InsertarUsuarioController {

    @FXML
    private Button btnBorrarUsuario;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnVolver;
    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;


    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker dpFnacimiento;

    /**
     * Metodo borrar que envia el id del usuario a ConexionBBDD y lo elimina de la bbdd
     * @param event AcionEvent
     */
    @FXML
    void borrarUsuario(ActionEvent event) {
        String id=txtId.getText();
        String password=" ";
        if(!id.isEmpty()) {
            if (ConexionBBDD.comprobarUsuario(id, password) == 1) {
                if (ConexionBBDD.borrarUsuario(id)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Usuario borrado");
                    alert.setContentText("Se ha borrado con existo.");
                    alert.showAndWait();

                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error al borrar el usuario.");
                    alertError.setContentText("No se ha podido borrar el usuario.");
                    alertError.showAndWait();
                }
            } else {
                Alert alertUsuariorep = new Alert(Alert.AlertType.WARNING);
                alertUsuariorep.setTitle("Usuario no existe.");
                alertUsuariorep.setContentText("No existe un usuario con ese Id.");
                alertUsuariorep.showAndWait();
            }
        }else{
            Alert alertvacio = new Alert(Alert.AlertType.WARNING);
            alertvacio.setTitle("Valores vacios.");
            alertvacio.setContentText("Debe introducir el id del usuario que desea eliminar.");
            alertvacio.showAndWait();
        }
    }

    /**
     * Metodo guardar, envia la informacion a la bbdd para hacer un insert del registro
     * @param event ActionEvent
     */
    @FXML
    void guardar(ActionEvent event) {
        String id=txtId.getText();
        String password=txtPassword.getText();
        String nombre=txtNombre.getText();
        String telefono=txtTelefono.getText();
        String direccion=txtDireccion.getText();
        String email=txtEmail.getText();
        String Fnacimiento=dpFnacimiento.getValue()+"";
        System.out.println(Fnacimiento);
        if(!id.isEmpty()&&!password.isEmpty()&&!nombre.isEmpty()&&!telefono.isEmpty()&&!direccion.isEmpty()&&!email.isEmpty()&&dpFnacimiento.getValue()!=null) {
            if (ConexionBBDD.comprobarUsuario(id, password) != 1) {
                boolean ok = ConexionBBDD.insertarUsuarios(id, password, nombre, telefono, direccion, email, Fnacimiento);
                if (ok) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Usuario guardado");
                    alert.setContentText("Se ha guardado con existo.");
                    alert.showAndWait();
                    txtId.clear();
                    txtPassword.clear();
                    txtNombre.clear();
                    txtTelefono.clear();
                    txtDireccion.clear();
                    txtEmail.clear();
                    dpFnacimiento.setValue(null);

                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error al introducir el nuevo usuario.");
                    alertError.setContentText("No se ha podido guardar el nuevo usuario.");
                    alertError.showAndWait();
                }
            } else {
                Alert alertUsuariorep = new Alert(Alert.AlertType.WARNING);
                alertUsuariorep.setTitle("Usuario existente.");
                alertUsuariorep.setContentText("Ya existe un usuario con ese Id.");
                alertUsuariorep.showAndWait();
            }
        }else{
            Alert alertvacio = new Alert(Alert.AlertType.WARNING);
            alertvacio.setTitle("Valores vacios.");
            alertvacio.setContentText("Debe rellenar todos los apartados.");
            alertvacio.showAndWait();
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

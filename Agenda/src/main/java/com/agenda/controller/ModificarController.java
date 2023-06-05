package com.agenda.controller;

import com.agenda.AgendaApplication;
import com.agenda.clases.ConexionBBDD;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModificarController implements Initializable {

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
    private TextField txtFechaNacimiento;

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField txtPassword;
    @FXML
    private DatePicker dpFnacimiento;
    @FXML
    private TextField txtTelefono;

    /**
     * Metodo initialize da a cada caja de texto el valor de la informacion del usuario
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    public void  initialize(URL url, ResourceBundle resourceBundle){
        try {
            ResultSet usuario = ConexionBBDD.consultar("Select * from usuarios where Id_usuario='" + ConexionBBDD.getID() + "';");
            ResultSet pas = ConexionBBDD.consultar("Select Password from identificacion where Id='" + ConexionBBDD.getID() + "';");
            while(pas.next()){
                txtPassword.setText(pas.getString("Password"));
            }
            while(usuario.next()) {
                txtNombre.setText(usuario.getString("Nombre"));
                txtDireccion.setText(usuario.getString("Direccion"));
                txtTelefono.setText(usuario.getString("Telefono"));
                txtEmail.setText(usuario.getString("Email"));
                dpFnacimiento.setValue(LocalDate.parse(usuario.getString("Fecha_nacimiento")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo guardar envia los datos a conexionBBDD para actualizarlos en la bbdd
     * @param event ActionEvent
     */
    @FXML
    void guardar(ActionEvent event) {

        String id=ConexionBBDD.getID();
        String password=txtPassword.getText();
        String nombre=txtNombre.getText();
        String telefono=txtTelefono.getText();
        String direccion=txtDireccion.getText();
        String email=txtEmail.getText();
        String Fnacimiento=dpFnacimiento.getValue()+"";
        if(!id.isEmpty()&&!password.isEmpty()&&!nombre.isEmpty()&&!telefono.isEmpty()&&!direccion.isEmpty()&&!email.isEmpty()&&dpFnacimiento.getValue()!=null) {
                boolean ok = ConexionBBDD.actualizarUsuario(id, password, nombre, telefono, direccion, email, Fnacimiento);
                if (ok) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Usuario guardado");
                    alert.setContentText("Se ha guardado con existo.");
                    alert.showAndWait();

                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error al introducir los datos.");
                    alertError.setContentText("No se ha podido guardar los datos nuevos del usuario.");
                    alertError.showAndWait();
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
            ConexionBBDD.desconectar();
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

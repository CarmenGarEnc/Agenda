package com.agenda.clases;

import com.agenda.controller.BuscarController;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ConexionBBDD {
        private static final String BBDD="agenda";
        private static final String PUERTO="3306";
        private static final String IP="localhost";
        private static final String URL="jdbc:mysql://"+IP+":"+PUERTO+"/"+BBDD+"?serverTimezone=UTC";
        private static final String USER="root";
        private static final String PASSWORD="toor8";
        private static Connection con=null;
        private static String ID;

    /**
     * Metodo getId
     * @return String con el id del usuario con el que se ha registrado
     */
    public static String getID() {
        return ID;
    }

    /**
     * Metodo setId, se setea este valor una vez se ha autentificado el usuario al hacer login
     * @param ID String
     */
    public static void setID(String ID) {
        ConexionBBDD.ID = ID;
    }

    /**
     * Método para conectarse a la Base de datos
     * @return boolean indicando si se ha realizado la conexion
     */
    public static boolean conectar(){
            boolean conectado=false;
            try{
                con= DriverManager.getConnection(URL,USER,PASSWORD);
                conectado= true;

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return conectado;
        }

    /**
     * Método desconectar, para apagar la conexion con la base de datos
     * @return boolean indicando si se ha realizado la desconexion
     */
    public static boolean desconectar(){
            boolean desconectado=false;
            try{
                con.close();
                desconectado=true;
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return desconectado;
        }

    /**
     * Método consultar, se le envia una consulta a la base de datos y devuelve los datos de dicha consulta
     * @param sql String indicando la consulta de sql
     * @return Resultset con la información de la consulta
     */
    public static ResultSet consultar(String sql){
            ResultSet rs=null;
            try {
                //Se crea una consulta en modo TYPE_SCROLL_SENSITIVE para posicionar el cursor donde necesitamos y CONCUR_UPDATABLE para poder realizar insert y update en la base de datos
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery(sql);//Resultado de la consulta
            }catch (SQLException e) {
            throw new RuntimeException(e);
            }
            return rs;
        }

    /**
     * Devolver Usuarios, devuelve todos los nombres de lso usuarios menos el Admin. Guardandolso en un ArrayList
     * @return ArrayList con los nombres de los usuarios menos el usuario Admin.
     */
    public static ArrayList devolverUsuarios(){
            ArrayList<String> usuarios=new ArrayList<String>();
            String sql="Select Id from identificacion where Id!='Admin';";
            ResultSet datos=consultar(sql);
            try {
                while (datos.next()){
                    usuarios.add(datos.getString("Id"));
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return usuarios;
        }

    /**
     * Método comprobar Usuario, comprueba que el nombre del usuario existe y ademas corresponde con su password
     * @param id String identificador del usuario
     * @param password String password correspondiente a ese id de usuario
     * @return int indicando 0 si no existe, 1 si existe pero el password no corresponde y 2 si usuario y password son válidos
     */
        public static int comprobarUsuario(String id, String password){
            int existe=0;//SI el usuario no existe
            try{

                ResultSet identificacion=consultar("Select * from identificacion;");

                    while(identificacion.next()){
                        if (identificacion.getString("Id").equals(id)){
                            existe=1;//Si el usuario existe
                            if (identificacion.getString("Password").equals(password)){
                                existe=2;//Si el usuario existe y la contraseña coincide
                                setID(id);
                            }
                        }
                    }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return existe;
        }

    /**
     * Método informacion Usuario que nos devuelve la informacion de un usuario determinado
      * @param id String identificador del usuario del que se desea conocer informacion
     * @return String con la informacion de ese usuario
     */
    public static String informacionUsuario(String id){
        String infoUsuario="";
        ResultSet resultado=consultar("Select * from usuarios where Id_usuario='"+id+"';");
        try{
            while(resultado.next()){
                infoUsuario="Nombre: "+resultado.getString("Nombre")+
                        "\nTelefono: "+resultado.getString("Telefono")+
                        "\nDirección: "+resultado.getString("Direccion")+
                        "\nEmail: "+resultado.getString("Email")+
                        "\nFecha de Nacimiento: "+resultado.getString("Fecha_nacimiento")+
                        "\n=====================================\n";
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return infoUsuario;
    }

    /**
     * Método insertar usuario, crea un nuevo registro en la base de datos
     * @param id String
     * @param pas String
     * @param nombre String
     * @param telefono String
     * @param direccion String
     * @param email String
     * @param Fnacimiento String
     * @return
     */
    public static boolean insertarUsuarios(String id, String pas, String nombre, String telefono, String direccion, String email, String Fnacimiento){
        boolean ok=false;
        ResultSet rsIdentificacion=consultar("Select * from identificacion;");//Resultset para trabajar en la tabla identificacion
        ResultSet rs=consultar("Select * from usuarios;");//Resultset paar trabajar en la tabla usuarios
        try{
            //Primero insertamos en la tabla identificacion para que no de error las foraneas
            rsIdentificacion.moveToInsertRow();//movemos es el cursor para insertar
            rsIdentificacion.updateString("Id",id);
            rsIdentificacion.updateString("Password",pas);
            rsIdentificacion.insertRow();//Insertamos los datos
            //Despues insertamos en la tabla usuarios
            rs.moveToInsertRow();//movemos es el cursor para insertar
            rs.updateString("Id_usuario",id);
            rs.updateString("Nombre",nombre);
            rs.updateString("Telefono",telefono);
            rs.updateString("Direccion",direccion);
            rs.updateString("Email",email);
            rs.updateDate("Fecha_nacimiento", Date.valueOf(Fnacimiento));//Convertimos el String en un Date para poder insertarlo en la tabla
            rs.insertRow();//Insertamos los datos
            ok= true;
        }catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return ok;
    }

    /**
     * Método borrar Usuario, eliminacion del registro del usuario con el identificador dado como parametro
     * @param id String
     * @return boolean indicando si se ha realizado el borrado de forma correcta
     */
    public static boolean borrarUsuario(String id){
        boolean ok=false;
        ResultSet rs=consultar("Select * from identificacion where Id='"+id+"';");
        try{
            rs.last();//Movemos el cursor, ultimo registro, este resultset consta de un unico registro por eso lo posicionamos asi
            rs.deleteRow();//Borrado de ese registro
            ok=true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ok;
    }

    /**
     * Método Actualizar usuario, actualiza los datos de un usuario registrado
     * @param id String
     * @param pas String
     * @param nombre String
     * @param telefono String
     * @param direccion String
     * @param email String
     * @param Fnacimiento String
     * @return
     */
    public static boolean actualizarUsuario(String id, String pas, String nombre, String telefono, String direccion, String email, String Fnacimiento){
        boolean ok=false;
        ResultSet rsIdentificacion=consultar("Select * from identificacion where Id='" + ConexionBBDD.getID() + "';");
        ResultSet rs=consultar("Select * from usuarios where Id_usuario='" + ConexionBBDD.getID() + "';");
        try{
            //Primero actualizamos en la tabla identificacion
            rsIdentificacion.last();//Movemos el cursor, ultimo registro, este resultset consta de un unico registro por eso lo posicionamos asi
            rsIdentificacion.updateString("Password",pas);
            rsIdentificacion.updateRow();//Actualizamos el registro
            //Despues actualizamos en la tabla usuarios
            rs.last();//Movemos el cursor, ultimo registro, este resultset consta de un unico registro por eso lo posicionamos asi
            rs.updateString("Nombre",nombre);
            rs.updateString("Telefono",telefono);
            rs.updateString("Direccion",direccion);
            rs.updateString("Email",email);
            rs.updateDate("Fecha_nacimiento", Date.valueOf(Fnacimiento));//Convertimos el String en un Date para poder insertarlo en la tabla
            rs.updateRow();//Actualizamos la fila del registro
            ok= true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ok;
    }

    /**
     * Método listar Regalos, lista de los regalos que tiene guardados un usuario
     * @param id String
     * @return ArrayList<String> con los regalos almacenados
     */
    public static ArrayList listarRegalos(String id){
        ArrayList<String> listarregalos=new ArrayList<>();
        String sql="Select * from regalos where Id_usuario='"+id+"';";
        ResultSet datos=consultar(sql);
        try {
            if(datos.next()) {
                for (int i = 2; i <=6; i++) {
                    if(datos.getString(i)!=null && !datos.getString(i).isEmpty()) {
                        listarregalos.add(datos.getString(i));
                    }else{
                        i=7;//Para que deje de recorrer el bucle
                    }
                }
            }else{
                listarregalos.add("vacio");//En caso de no haber regalos guardados
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listarregalos;
    }

    /**
     * Método informacion regalos
     * @param id String
     * @return String devuelve una cadena con lso regalos o indicando que no tiene nada guardado
     */
    public static String informacionRegalos(String id){
        String regalos;
        String sql="Select * from regalos where Id_usuario='"+id+"';";
        ResultSet reg=consultar(sql);
        try {
            if(!reg.next()){
                regalos=""+id+" no ha guardado sugerencia de regalos.";
            }else{
                regalos="Sugerencia de regalos para "+id+".\n";
                for (int i = 2; i <= 6; i++) {
                    if (reg.getString(i)!=null&&!reg.getString(i).equals("")){
                        regalos=regalos.concat((i-1)+": "+reg.getString(i)+".\n");
                    }
                }
            }
            return regalos;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método insertar Regalos
     * @param regalos ArrayList<String>
     * @return boolean indicando si se han guardado correctamente
     */
    public static boolean insertarRegalos(ArrayList<String> regalos){
        boolean ok=false;
        ResultSet resultado=consultar("Select * from regalos where Id_usuario='"+getID()+"';");
        try {
            int cont=2;//variable donde comienza el campo donde queremos insertar el primer valor del ArrayList
            if(!resultado.next()){
                //En caso de que no exista un registro con datos hay que hacer un insert
                ResultSet resul=consultar("Select * from regalos;");
                resul.moveToInsertRow();//MOvemos le cursor a la posicion para insert
                resul.updateString("Id_usuario",getID());
                for (int i = 0; i < regalos.size(); i++) {
                    resul.updateString(cont,regalos.get(i));//Actualizamos el campo cont del Resultset con la posicion i del ArrayList
                    cont++;//Sumamos una posicion para ir al siguiente campo del resultset
                }
                resul.insertRow();//Realizamos el insert
                ok= true;

            }else{
                //En caso de que exista un registro con datos hay que hacer un update
                resultado.last();//MOvemos le cursor al final porque el resultset solo devuelve un registro
                for (int i = 0; i < regalos.size(); i++) {
                    resultado.updateString(cont,regalos.get(i));//Actualizamos el campo cont del Resultset con la posicion i del ArrayList
                    cont++;//Sumamos una posicion para ir al siguiente campo del resultset
                }
                if(cont<6){//En caso de que el ArrayList sea inferior a todos lso campos, rellenamos con valores vacios para que no de error
                    for (int i = cont; i <=6; i++) {
                        resultado.updateString(i,"");
                    }
                }
                resultado.updateRow();
                ok= true;
            }
            return ok;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

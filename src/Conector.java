import java.sql.*;

/**
 * Clase que gestiona la conexión a una base de datos MySQL y proporciona 
 * un método para verificar las credenciales de usuario (nombre de usuario y contraseña).
 * Utiliza JDBC para interactuar con la base de datos.
 */
public class Conector {

    // Constantes de configuración para la base de datos
    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/derrapdb?useSSL=false";
    private static final String USUARIO = "root";
    private static final String CLAVE = "medac";

    // Atributos relacionados con la conexión a la base de datos
    Connection cn = null;
    Statement st = null;
    ResultSet resultado = null;

    /**
     * Establece una conexión con la base de datos MySQL utilizando los parámetros
     * de conexión definidos en la clase (URL, usuario, y clave).
     * 
     * @return La conexión a la base de datos (de tipo {@link Connection}).
     */
    public Connection conexion_correcta() {
        try {
            // Cargar el controlador de MySQL
            cn = DriverManager.getConnection(URL, USUARIO, CLAVE);
            st = cn.createStatement();
            // System.out.print("se pudo conectar");  // Debugging line
        } catch (SQLException e) {
            // Si no se puede conectar, imprime el error
            e.printStackTrace();
            System.out.print("no se pudo conectar");
        }
        return cn;
    }

    /**
     * Compara las credenciales del usuario con los datos almacenados en la base de datos.
     * 
     * <p>Este método verifica si el nombre de usuario y la contraseña coinciden con
     * un usuario registrado en la base de datos. Dependiendo del tipo de usuario
     * (administrador o no), devuelve un valor específico:</p>
     * <ul>
     *     <li>1 si el usuario es un administrador</li>
     *     <li>0 si el usuario es un mecánico (no administrador)</li>
     *     <li>2 si las credenciales son incorrectas</li>
     * </ul>
     * 
     * @param usuario El nombre de usuario o DNI del usuario a verificar.
     * @param contraseña La contraseña del usuario a verificar.
     * @return Un entero que indica el tipo de usuario o si las credenciales son incorrectas.
     */
    public int comprobarusuario(String usuario, String contraseña) {
        int usuariof = 2;
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            resultado = st.executeQuery("SELECT * FROM usuario");

            while (resultado.next()) {
                if (resultado.getString("dni").equals(usuario) && resultado.getString("clave").equals(contraseña)) {
                    if (resultado.getInt("administrador") == 1) {
                        usuariof = 1;
                    } else if (resultado.getInt("administrador") == 0) {
                        usuariof = 0;
                    }
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
        return usuariof;
    }

    public ResultSet listado() {
        try {
            this.conexion_correcta();
            resultado = st.executeQuery("Select * from ordenreparacion");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Dio error");
        }
        return resultado;
    }

    public ResultSet listadoDNI(String dni) {
        try {
            this.conexion_correcta();
            resultado = st.executeQuery("Select * from pedido where " + dni);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Dio error");
        }

        return resultado;
    }

    /**
     * Método para agregar un cliente.
     */
    public boolean agregarCliente(String dni, String nombre, String apellidos, String telefono, String direccion, String codigopostal) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "INSERT INTO cliente (DNI, nombre, apellidos, telefono, direccion, codigopostal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, telefono);
            ps.setString(5, direccion);
            ps.setString(6, codigopostal);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para obtener la lista de clientes.
     */
    public ResultSet obtenerClientes() {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            String query = "SELECT * FROM cliente";
            resultado = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Método para actualizar un cliente.
     */
    public boolean actualizarCliente(String dni, String nombre, String apellidos, String telefono, String direccion, String codigopostal) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "UPDATE cliente SET nombre = ?, apellidos = ?, telefono = ?, direccion = ?, codigopostal = ? WHERE DNI = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, codigopostal);
            ps.setString(6, dni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para eliminar un cliente.
     */
    public boolean eliminarCliente(String dni) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "DELETE FROM cliente WHERE DNI = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, dni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para agregar un vehículo.
     */
    public boolean agregarVehiculo(String matricula, String modelo, String marca, String tipocombustible, int kilometraje, String color, String anio, String clienteDni) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "INSERT INTO vehiculo (matricula, modelo, marca, tipocombustible, kilometraje, color, anio, cliente_DNI) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setString(2, modelo);
            ps.setString(3, marca);
            ps.setString(4, tipocombustible);
            ps.setInt(5, kilometraje);
            ps.setString(6, color);
            ps.setString(7, anio);
            ps.setString(8, clienteDni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para obtener la lista de vehículos.
     */
    public ResultSet obtenerVehiculos() {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            String sql = "SELECT * FROM vehiculo";
            resultado = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Método para actualizar un vehículo.
     */
    public boolean actualizarVehiculo(String matricula, String modelo, String marca, String tipocombustible, int kilometraje, String color, String anio, String clienteDni) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "UPDATE vehiculo SET modelo = ?, marca = ?, tipocombustible = ?, kilometraje = ?, color = ?, anio = ?, cliente_DNI = ? WHERE matricula = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, modelo);
            ps.setString(2, marca);
            ps.setString(3, tipocombustible);
            ps.setInt(4, kilometraje);
            ps.setString(5, color);
            ps.setString(6, anio);
            ps.setString(7, clienteDni);
            ps.setString(8, matricula);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para eliminar un vehículo.
     */
    public boolean eliminarVehiculo(String matricula) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "DELETE FROM vehiculo WHERE matricula = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para agregar un trabajador.
     */
    public boolean agregarTrabajador(String dni, String nombre, String apellidos, String telefono, String direccion, String clave) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "INSERT INTO usuario (dni, clave, administrador, nombre, apellidos, telefono, direccion) VALUES (?, ?, 0, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, dni);
            ps.setString(2, clave);
            ps.setString(3, nombre);
            ps.setString(4, apellidos);
            ps.setString(5, telefono);
            ps.setString(6, direccion);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para obtener la lista de trabajadores.
     */
    public ResultSet obtenerTrabajadores() {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            String query = "SELECT * FROM usuario WHERE administrador = 0";
            resultado = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Método para actualizar un trabajador.
     */
    public boolean actualizarTrabajador(String dni, String nombre, String apellidos, String telefono, String direccion, String clave) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "UPDATE usuario SET nombre = ?, apellidos = ?, telefono = ?, direccion = ?, clave = ? WHERE dni = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, clave);
            ps.setString(6, dni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    /**
     * Método para eliminar un trabajador.
     */
    public boolean eliminarTrabajador(String dni) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "DELETE FROM usuario WHERE dni = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, dni);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    

    /**
     * Método para cerrar los recursos (ResultSet, Statement, Connection).
     */
    private void cerrarRecursos() {
        try {
            if (resultado != null) resultado.close();
            if (st != null) st.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet vehiculomatricula() {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            String query = "SELECT matricula FROM vehiculo";
            resultado = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    //Obtener ordenes
    public ResultSet obtenerOrden() {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            if (st == null || st.isClosed()) {
                st = cn.createStatement();
            }

            String query = "SELECT * FROM ordenreparacion";
            resultado = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    //Actualizar ordenes
    public boolean actualizarOrden(int idorden, String estadoreparacion, String usuario_dni, String vehiculo_matricula, int importe, String fecha, String servicio) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "UPDATE ordenreparacion SET idorden = ?, estadoreparacion = ?, usuario_dni = ?, vehiculo_matricula = ?, importe = ?, fecha = ?, servicio = ? WHERE idorden = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            
            // Establecer los valores de los parámetros
            ps.setInt(1, idorden);
            ps.setString(2, estadoreparacion);
            ps.setString(3, usuario_dni);
            ps.setString(4, vehiculo_matricula);
            ps.setInt(5, importe);
            ps.setString(6, fecha);
            ps.setString(7, servicio);
            ps.setInt(8, idorden);  // Este es el valor del idorden en el WHERE

            // Ejecutar la actualización
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    public boolean agregarOrden(int idord, String estado, String usuario, String matrivehi, int importe, String fecha, String servicio) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "INSERT INTO ordenreparacion (idorden, estadoreparacion, usuario_dni, vehiculo_matricula, importe, fecha, servicio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idord);
            ps.setString(2, estado);
            ps.setString(3, usuario);
            ps.setString(4, matrivehi);
            ps.setInt(5, importe);
            ps.setString(6, fecha);
            ps.setString(7, servicio);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    public boolean eliminarOrden(int id) {
        try {
            if (cn == null || cn.isClosed()) {
                conexion_correcta();
            }

            String sql = "DELETE FROM ordenreparacion WHERE idorden = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos();
        }
    }
}
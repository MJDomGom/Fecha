package modelo.persistencia.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.entidades.Cliente;
import modelo.entidades.ClienteImpl;
import modelo.entidades.Factura;
import modelo.entidades.FacturaImpl;
import modelo.persistencia.FacturaDAO;

/**
 *
 * @author IS2: Norberto Díaz-Díaz
 */
public class FacturaDAOJDBC implements FacturaDAO {

    public List<Factura> listByCliente(String dni) {
        List<Factura> facturas = new ArrayList<Factura>();

        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas where DNI=" + dni);
            String nombre, direccion, id_factura;
            Double importe;
            Date fecha;
            while (res.next()) {
                //DNI = res.getString("DNI");
                nombre = res.getString("nombre");
                direccion = res.getString("direccion");
                id_factura = res.getString("identificador");
                importe = res.getDouble("importe");

                //creo cliente
                Cliente cliente = new ClienteImpl(dni, nombre, direccion);
                //Añado la factura
                if (res.getDate("fecha") == null) {
                    facturas.add(new FacturaImpl(id_factura, cliente, importe));
                } else {
                    fecha = res.getDate("fecha");
                    Factura f = new FacturaImpl(id_factura, cliente, importe);
                    f.setFecha(fecha);
                    facturas.add(f);
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return facturas;
    }

    public void create(Factura entidad) {
        //LA SENTENCIA SQL CAMBIARA SI LA FECHA DE LA FACTURA EXISTE O NO, POR ESO ESTE IF
        String sql = "insert into facturas(identificador,id_cliente,importe,fecha) values (?,?,?,?)";

        try {
            PreparedStatement stm = Persistencia.createConnection().prepareStatement(sql);
            stm.setString(1, entidad.getIdentificador());
            stm.setString(2, entidad.getCliente().getDNI());
            stm.setDouble(3, entidad.getImporte());
            java.sql.Date variableSqlDate = null;
            if (entidad.getFecha() != null) {
                variableSqlDate = new java.sql.Date(entidad.getFecha().getTime());
            }
            stm.setDate(4, variableSqlDate);
            stm.execute();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public Factura read(String pk) {
        Factura f = null;
        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM facturas where identificador=" + pk);
            String identificador, id_cliente;
            Double importe;
            Date fecha;
            if (res.next()) {
                identificador = res.getString("identificador");
                id_cliente = res.getString("id_cliente");
                importe = res.getDouble("importe");
                fecha = res.getDate("fecha");
                //Leo el Cliente
                Cliente cliente = (new ClienteDAOJDBC()).read(id_cliente);
                //Creo la factura
                f = new FacturaImpl(identificador, cliente, importe);
                if (fecha != null) {
                    f.setFecha(fecha);
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return f;
    }

    public void update(Factura entidad) {
        //LA SENTENCIA SQL CAMBIARA SI LA FECHA DE LA FACTURA EXISTE O NO, POR ESO ESTE IF
        String sql = "update facturas set id_cliente=?, importe=?, fecha=? where identificador like ?";

        try {
            PreparedStatement stm = Persistencia.createConnection().prepareStatement(sql);
            stm.setString(4, entidad.getIdentificador());
            stm.setString(1, entidad.getCliente().getDNI());
            stm.setDouble(2, entidad.getImporte());
            java.sql.Date variableSqlDate = null;
            if (entidad.getFecha() != null) {
                variableSqlDate = new java.sql.Date(entidad.getFecha().getTime());
            }
            stm.setDate(3, variableSqlDate);
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public void delete(Factura entidad) {
        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            stmt.executeUpdate("DELETE FROM facturas where identificador=" + entidad.getIdentificador());

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    public List<Factura> list() {
        List<Factura> facturas = new ArrayList<Factura>();

        try {
            Statement stmt = Persistencia.createConnection().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM vfacturas");
            String DNI, nombre, direccion, id_factura;
            Double importe;
            Date fecha;
            while (res.next()) {
                DNI = res.getString("DNI");
                nombre = res.getString("nombre");
                direccion = res.getString("direccion");
                id_factura = res.getString("identificador");
                importe = res.getDouble("importe");
                //creo cliente
                Cliente cliente = new ClienteImpl(DNI, nombre, direccion);
                //Añado la factura
                if (res.getDate("fecha") != null) {
                    fecha = res.getDate("fecha");
                    Factura f = new FacturaImpl(id_factura, cliente, importe);
                    f.setFecha(fecha);
                    facturas.add(f);
                } else {
                    facturas.add(new FacturaImpl(id_factura, cliente, importe));
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            Persistencia.closeConnection();
        }
        return facturas;
    }
}

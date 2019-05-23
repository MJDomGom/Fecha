package modelo.entidades;

import java.util.Date;

/**
 *
 * @author IS2: Norberto Díaz-Díaz
 */
public class FacturaImpl implements Factura {

    private String identificador;
    private Cliente cliente;
    private Double importe;
    private Date fechaPagado;

    public FacturaImpl(String identifiador, Cliente cliente, Double importe) {
        this.identificador = identifiador;
        this.cliente = cliente;
        this.importe = importe;
        this.fechaPagado = null;//LA FECHA DEBE DE SER NULA SI NO SE ESPECIFICA UNA FECHA EN LA VISTA
    }

    public FacturaImpl(String identificador) {
        this(identificador, null, null);
    }

    @Override
    public String getIdentificador() {
        return this.identificador;
    }

    @Override
    public Cliente getCliente() {
        return this.cliente;
    }

    @Override
    public Double getImporte() {
        return this.importe;
    }

    @Override
    public void setIdentificador(String id) {
        this.identificador = id;
    }

    @Override
    public void setFecha(Date fecha) {
        this.fechaPagado = fecha;
    }

    @Override
    public Date getFecha() {
        return this.fechaPagado;
    }

}

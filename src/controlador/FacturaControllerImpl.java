package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.FacturaModel;
import modelo.entidades.Cliente;
import modelo.entidades.Factura;
import modelo.entidades.FacturaImpl;
import vista.factura.FacturaView;

/**
 *
 * @author IS2: Norberto Díaz-Díaz, Roberto Ruiz
 */
public class FacturaControllerImpl implements FacturaController {
    
    private FacturaModel model;
    
    private List<FacturaView> views;
    
    public FacturaControllerImpl() {
        this.views = new ArrayList<FacturaView>();
    }
    
    @Override
    public void setup(FacturaModel model, List<FacturaView> views) {
        this.model = model;
        model.setController(this);
        addViews(views);
    }
    
    @Override
    public void start() {
        for (FacturaView f : this.views) {
            f.display();
        }
    }
    
    private void addViews(List<FacturaView> views) {
        for (FacturaView f : views) {
            this.addView(f);
        }
    }
    
    @Override
    public void addView(FacturaView view) {
        this.views.add(view);
        view.setController(this);
        view.setModel(this.model);
    }
    
    @Override
    public void removeView(FacturaView view) {
        this.views.remove(view);
    }
    
    @Override
    public FacturaModel getModel() {
        return this.model;
    }
    
    @Override
    public void setModel(FacturaModel model) {
        this.model = model;
    }
    
    @Override
    public void nuevaFacturaGesture(String id, Cliente cliente, String importe) {
        Factura factura = new FacturaImpl(id, cliente, new Double((String) importe));
        this.model.nuevaFactura(factura);
    }
    
     //NUEVO METODO PARA LA CREACION DE FACTURAS, CON LA CONVERSION DE LA FECHA
    public void nuevaFacturaGesture(String id, Cliente cliente, String importe, String fecha)throws ParseException {
        Date fechaDate = conversionFechas(fecha);
        Factura factura = new FacturaImpl(id, cliente, new Double((String) importe));
        factura.setFecha(fechaDate);
        this.model.nuevaFactura(factura);
    }
    
    @Override
    public void borraFacturaGesture(String id) {
        Factura factura = new FacturaImpl(id);
        this.model.eliminarFactura(factura);
    }
    
    @Override
    public void actualizaFacturaGesture(String id, Cliente cliente, String importe) {
        Factura factura = new FacturaImpl(id, cliente, new Double((String) importe));
        this.model.actualizarFactura(factura);
    }
    
    @Override
    public void fireDataModelChanged() {
        for (FacturaView f : this.views) {
            f.display();
        }
    }
    
    public Date conversionFechas(String fecha) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fechaDate = null; // Variable que almacenará la fecha
// fechaEnString es de tipo String y contiene la fecha a convertir
        if (fecha.length() > 0) {
                fechaDate = formatter.parse(fecha);
        }
        return fechaDate;
    }
    
    @Override
    //NUEVO METODO PARA EL ACTUALIZAR, CON LA CONVERSION DE LA FECHA 
    public void actualizaFacturaGesture(String id, Cliente cliente, String importe, String fecha)throws ParseException { 
        Date fechaDate = conversionFechas(fecha);
        Factura factura = new FacturaImpl(id, cliente, new Double((String) importe));
        factura.setFecha(fechaDate);
        this.model.actualizarFactura(factura);
    }
}

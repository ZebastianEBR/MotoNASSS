package com.nasss.taller.motonasss.controllers;

import com.nasss.taller.motonasss.factory.ProductFactory;
import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.ProductCrudService;
import com.nasss.taller.motonasss.services.ProductPriceService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NuevoProductoController {
    private final ProductCrudService pcs = ProductCrudService.getInstance();
    private final ProductPriceService pps = ProductPriceService.getInstance();
    private Product productoActual;

    @FXML private ComboBox<ProductType> comboTipo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private HBox hboxPrecioVenta;
    @FXML private TextField txtPrecioVenta;
    @FXML private TextField txtStock;
    @FXML private TextField txtAgregarStock;
    @FXML private HBox hboxAgregarStock;

    private ObservableList<Product> listaProductos;
    private TableView<Product> tablaProductos; // referencia pasada desde MainController

    public void setListaProductos(ObservableList<Product> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void setTablaProductos(TableView<Product> tablaProductos) {
        this.tablaProductos = tablaProductos;
    }

    @FXML
    public void initialize() {
        comboTipo.getItems().setAll(ProductType.values());
        hboxAgregarStock.setVisible(false);
        hboxAgregarStock.setManaged(false);

        hboxPrecioVenta.setVisible(false);
        hboxPrecioVenta.setManaged(false);
    }

    @FXML
    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText().trim();
            ProductType tipo = comboTipo.getValue();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());
            int agregarStock = txtAgregarStock.getText().isEmpty() ? 0 : Integer.parseInt(txtAgregarStock.getText());

            if (nombre.isEmpty() || tipo == null) {
                mostrarAlerta("Complete todos los campos obligatorios.");
                return;
            }

            if (!tipo.matchesName(nombre)) {
                mostrarAlerta("El nombre del producto no coincide con el tipo seleccionado.");
                return;
            }

            if (productoActual == null) {
                Product producto = ProductFactory.createProduct(tipo, nombre, precio, stock);
                pcs.registerProduct(producto);
                pps.calculatePrice(producto); // calcula y setea el precio de venta
                if (listaProductos != null) listaProductos.add(producto);
            } else {
                productoActual.setName(nombre);
                productoActual.setPurchasePrice(precio);
                productoActual.setStock(stock + agregarStock);

                if (hboxPrecioVenta.isVisible() && !txtPrecioVenta.getText().isEmpty()) {
                    // Usuario editó el precio de venta manualmente
                    double precioVenta = Double.parseDouble(txtPrecioVenta.getText());
                    productoActual.setSalePrice(precioVenta);
                } else {
                    // Recalcula el precio de venta automáticamente según el nuevo precio de compra
                    pps.calculatePrice(productoActual);
                    txtPrecioVenta.setText(String.valueOf(productoActual.getSalePrice())); // <- actualizar el TextField
                }

                pcs.updateProduct(productoActual);

                // Refrescar la tabla
                if (tablaProductos != null) {
                    tablaProductos.refresh();
                }
            }


            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Precio, Stock y Precio de venta deben ser números.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cargarProducto(Product producto) {
        this.productoActual = producto;

        txtNombre.setText(producto.getName());
        txtPrecio.setText(String.valueOf(producto.getPurchasePrice()));
        txtStock.setText(String.valueOf(producto.getStock()));
        comboTipo.setValue(obtenerTipoProducto(producto));

        hboxPrecioVenta.setVisible(true);
        hboxPrecioVenta.setManaged(true);
        txtPrecioVenta.setText(String.valueOf(producto.getSalePrice()));

        txtStock.setEditable(false);
        hboxAgregarStock.setVisible(true);
        hboxAgregarStock.setManaged(true);
    }

    private ProductType obtenerTipoProducto(Product p) {
        if (p instanceof ChainKit) return ProductType.KITARRASTRE;
        if (p instanceof Battery) return ProductType.BATERIA;
        if (p instanceof Bearing) return ProductType.BALINERA;
        if (p instanceof BrakeShoes) return ProductType.BANDAS;
        if (p instanceof BreakPads) return ProductType.PASTILLAS;
        if (p instanceof Cable) return ProductType.GUAYA;
        if (p instanceof Chain) return ProductType.CADENA;
        if (p instanceof Oil) return ProductType.ACEITE;
        if (p instanceof TurnSignal) return ProductType.DIRECCIONAL;
        throw new IllegalArgumentException("Tipo de producto desconocido");
    }
}

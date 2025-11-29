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

import java.sql.SQLOutput;

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
        System.out.println("\n Iniciando producto controller");
        comboTipo.getItems().setAll(ProductType.values());
        hboxAgregarStock.setVisible(false);
        hboxAgregarStock.setManaged(false);

        hboxPrecioVenta.setVisible(false);
        hboxPrecioVenta.setManaged(false);
    }

    @FXML
    private void guardarProducto() {
        System.out.println("\n guardando producto...");
        try {
            String nombre = txtNombre.getText().trim();
            ProductType tipo = comboTipo.getValue();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());
            int agregarStock = txtAgregarStock.getText().isEmpty() ? 0 : Integer.parseInt(txtAgregarStock.getText());

            // 🔹 Validar campos obligatorios
            if (nombre.isEmpty() || tipo == null) {
                mostrarAlerta("Complete todos los campos obligatorios.");
                return;
            }

            // 🔹 Validar coincidencia de tipo y nombre
            if (!tipo.matchesName(nombre)) {
                mostrarAlerta("El nombre del producto no coincide con el tipo seleccionado.");
                return;
            }

            // 🔹 Validar valores negativos
            if (precio < 0) {
                mostrarAlerta("El precio no puede ser negativo.");
                return;
            }
            if (stock < 0) {
                mostrarAlerta("El stock no puede ser negativo.");
                return;
            }
            if (agregarStock < 0) {
                mostrarAlerta("No puedes agregar una cantidad negativa de stock.");
                return;
            }

            // 🔹 Crear o actualizar producto
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
                    double precioVenta = Double.parseDouble(txtPrecioVenta.getText());
                    if (precioVenta < 0) {
                        mostrarAlerta("El precio de venta no puede ser negativo.");
                        return;
                    }
                    productoActual.setSalePrice(precioVenta);
                } else {
                    pps.calculatePrice(productoActual);
                    txtPrecioVenta.setText(String.valueOf(productoActual.getSalePrice()));
                }

                pcs.updateProduct(productoActual);

                if (tablaProductos != null) {
                    tablaProductos.refresh();
                }
            }

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Precio y Stock deben ser números.");
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

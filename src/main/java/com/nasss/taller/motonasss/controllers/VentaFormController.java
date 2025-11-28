package com.nasss.taller.motonasss.controllers;

import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.SaleCrudService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VentaFormController {

    private MainController mainController;
    private SaleCrudService scs = SaleCrudService.getInstance();
    private ObservableList<Product> productosDisponibles = FXCollections.observableArrayList();
    private final ObservableList<SaleItem> listaItems = FXCollections.observableArrayList();
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @FXML private Label lblIdVenta;
    @FXML private Label lblFecha;
    @FXML private ComboBox<String> cbProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView<SaleItem> tablaItems;
    @FXML private TableColumn<SaleItem, String> colProducto;
    @FXML private TableColumn<SaleItem, Integer> colCantidad;
    @FXML private TableColumn<SaleItem, Double> colPrecio;
    @FXML private TableColumn<SaleItem, Double> colSubtotal;
    @FXML private Label lblTotal;

    @FXML
    private void initialize() {
        lblIdVenta.setText("V-" + idGenerator.getAndIncrement());
        lblFecha.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        colProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        colCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        colSubtotal.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getQuantity() * data.getValue().getSalePrice()).asObject());

        tablaItems.setItems(listaItems);
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void setProductosDisponibles(List<Product> productos) {
        this.productosDisponibles.setAll(productos);
        cbProducto.setItems(FXCollections.observableArrayList(
                productos.stream().map(Product::getName).toList()
        ));
    }

    @FXML
    private void agregarItem() {
        String nombre = cbProducto.getValue();
        if (nombre == null || txtCantidad.getText().isEmpty()) {
            showAlert("Campos incompletos", "Debes seleccionar un producto y cantidad.", Alert.AlertType.WARNING);
            return;
        }

        Product producto = productosDisponibles.stream()
                .filter(p -> p.getName().equals(nombre))
                .findFirst().orElse(null);

        if (producto == null) {
            showAlert("Error", "El producto seleccionado no existe.", Alert.AlertType.ERROR);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Error", "Cantidad inválida.", Alert.AlertType.ERROR);
            return;
        }

        if (cantidad > producto.getStock()) {
            showAlert("Stock insuficiente", "Solo hay " + producto.getStock() + " unidades disponibles.", Alert.AlertType.WARNING);
            return;
        }

        SaleItem item = new SaleItem(producto, cantidad, producto.getSalePrice());
        listaItems.add(item);
        actualizarTotal();
        txtCantidad.clear();
    }

    @FXML
    private void eliminarItem() {
        SaleItem seleccionado = tablaItems.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaItems.remove(seleccionado);
            actualizarTotal();
        }
    }

    @FXML
    private void confirmarVenta() {
        if (listaItems.isEmpty()) {
            showAlert("Sin productos", "Debes agregar al menos un producto.", Alert.AlertType.WARNING);
            return;
        }

        Sale nuevaVenta = new Sale(LocalDateTime.now(),
                listaItems.stream().mapToDouble(i -> i.getQuantity() * i.getSalePrice()).sum());

        listaItems.forEach(item -> scs.addSaleItem(nuevaVenta, item));
        scs.registerSale(nuevaVenta);

        if (mainController != null) {
            mainController.actualizarTablaVentas();
            mainController.getTablaProductos().refresh();
        }

        showAlert("Venta completada", "La venta fue registrada correctamente.", Alert.AlertType.INFORMATION);
        cerrarFormulario();
    }

    @FXML
    private void cerrarFormulario() {
        Stage stage = (Stage) lblTotal.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void actualizarTotal() {
        double total = listaItems.stream().mapToDouble(i -> i.getQuantity() * i.getSalePrice()).sum();
        lblTotal.setText(String.format("$%.2f", total));
    }
}

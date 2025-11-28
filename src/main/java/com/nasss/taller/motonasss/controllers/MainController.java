package com.nasss.taller.motonasss.controllers;

import com.nasss.taller.motonasss.model.*;
import com.nasss.taller.motonasss.services.ProductCrudService;
import com.nasss.taller.motonasss.viewController.ViewController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML private VBox panelProductos;
    @FXML private VBox panelVentas;

    private final Motoshop motodb = Motoshop.getInstance();

    @FXML private TextField txtBuscarProductos;
    @FXML private TableView<Product> tablaProductos;
    @FXML private TableColumn<Product, String> colId;
    @FXML private TableColumn<Product, String> colNombre;
    @FXML private TableColumn<Product, Double> colPrecio;
    @FXML private TableColumn<Product, Integer> colStock;
    @FXML private TableColumn<Product, Double> colPrecioVenta;
    private final ObservableList<Product> productosTabla = FXCollections.observableArrayList();

    @FXML private TableView<Sale> tablaVentas;
    @FXML private TableColumn<Sale, String> colIdVenta;
    @FXML private TableColumn<Sale, String> colFechaVenta;
    @FXML private TableColumn<Sale, Double> colTotalVenta;
    private final ObservableList<Sale> ventasTabla = FXCollections.observableArrayList();

    @FXML private TextField txtBuscarVenta;
    @FXML private TableView<SaleItem> tablaDetalleVenta;
    @FXML private TableColumn<SaleItem, String> colDetProducto;
    @FXML private TableColumn<SaleItem, Integer> colDetCantidad;
    @FXML private TableColumn<SaleItem, Double> colDetPrecio;
    @FXML private TableColumn<SaleItem, Double> colDetSubtotal;

    @FXML
    public void initialize() {
        // --- Tabla productos ---
        tablaProductos.setItems(productosTabla);
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCode()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPurchasePrice()).asObject());

        // Columna stock con alerta visual
        colStock.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        colStock.setCellFactory(column -> new TableCell<Product, Integer>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);
                if (empty || stock == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(stock.toString());
                    if (stock == 0) {
                        setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-alignment: CENTER;");
                    } else if (stock <= 2) {
                        setStyle("-fx-background-color: #ffd966; -fx-text-fill: black; -fx-alignment: CENTER;");
                    } else if (stock <= 3) {
                        setStyle("-fx-background-color: #ffb84d; -fx-text-fill: black; -fx-alignment: CENTER;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        colPrecioVenta.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        productosTabla.addAll(motodb.getProducts());

        // --- Tabla ventas ---
        tablaVentas.setItems(ventasTabla);
        actualizarTablaVentas();

        colIdVenta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colFechaVenta.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        colTotalVenta.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotal()).asObject());

        colDetProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        colDetCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        colDetPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        colDetSubtotal.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getQuantity() * data.getValue().getSalePrice()).asObject());

        tablaVentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tablaDetalleVenta.setItems(FXCollections.observableArrayList(newSel.getItems()));
            } else {
                tablaDetalleVenta.getItems().clear();
            }
        });

        // Filtros de búsqueda
        txtBuscarProductos.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.isEmpty()) {
                tablaProductos.setItems(productosTabla);
            } else {
                String lower = newText.toLowerCase().trim();
                ObservableList<Product> filtered = productosTabla.filtered(p ->
                        p.getCode().toLowerCase().contains(lower) || p.getName().toLowerCase().contains(lower));
                tablaProductos.setItems(filtered);
            }
        });

        txtBuscarVenta.textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.isEmpty()) {
                tablaVentas.setItems(ventasTabla);
            } else {
                String lower = newText.toLowerCase();
                ObservableList<Sale> filtered = ventasTabla.filtered(s ->
                        s.getId().toLowerCase().contains(lower) ||
                                s.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toLowerCase().contains(lower));
                tablaVentas.setItems(filtered);
            }
        });
    }

    // --- Mostrar paneles ---
    @FXML
    private void mostrarProductos() {
        panelProductos.setVisible(true); panelProductos.setManaged(true);
        panelVentas.setVisible(false); panelVentas.setManaged(false);
    }

    @FXML
    private void mostrarVentas() {
        panelVentas.setVisible(true); panelVentas.setManaged(true);
        panelProductos.setVisible(false); panelProductos.setManaged(false);
    }

    // --- Productos CRUD ---
    @FXML
    public void mostrarFormularioNuevoProducto(javafx.event.ActionEvent event) {
        ViewController.ViewLoaderResult<NuevoProductoController> result =
                ViewController.loadView("/com/nasss/taller/motonasss/fxml/product_crud.fxml");
        if (result != null) {
            Stage stage = new Stage();
            stage.setScene(new Scene(result.getRoot()));
            stage.setTitle("Nuevo Producto");

            result.getController().setListaProductos(productosTabla);
            stage.initOwner(tablaProductos.getScene().getWindow());
            stage.showAndWait();
        }
    }

    @FXML
    private void modificarProducto() {
        Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Debes seleccionar un producto para modificar.");
            alert.showAndWait();
            return;
        }

        ViewController.ViewLoaderResult<NuevoProductoController> result =
                ViewController.loadView("/com/nasss/taller/motonasss/fxml/product_crud.fxml");
        if (result != null) {
            Stage stage = new Stage();
            stage.setScene(new Scene(result.getRoot()));
            stage.setWidth(600);
            stage.setHeight(400);
            stage.setTitle("Modificar Producto");

            NuevoProductoController controller = result.getController();
            controller.setListaProductos(productosTabla);
            controller.cargarProducto(seleccionado);

            stage.initOwner(tablaProductos.getScene().getWindow());
            stage.showAndWait();
            tablaProductos.refresh();
        }
    }

    @FXML
    private void eliminarProducto() {
        Product seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Debes seleccionar un producto para eliminarlo.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Deseas eliminar el producto seleccionado?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    ProductCrudService.getInstance().deleteProduct(seleccionado);
                    productosTabla.remove(seleccionado);
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    private void hacerVenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nasss/taller/motonasss/fxml/venta_form.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Nueva Venta - MotoNASSS");

            // Configurar controller
            VentaFormController controller = loader.getController();
            controller.setProductosDisponibles(Motoshop.getInstance().getProducts());
            controller.setMainController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTablaVentas() {
        ventasTabla.setAll(Motoshop.getInstance().getSales());
    }

    public TableView<Product> getTablaProductos() {
        return tablaProductos;
    }
}

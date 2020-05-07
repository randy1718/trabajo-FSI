/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demanda.controller;

import demanda.ClientApplication;
import demanda.entities.ProductData;
import demanda.misc.PerformanceType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataEntryController {

    public @FXML
    GridPane dataEntryGrid;
    public @FXML
    Text existsPreviousPredictionText, existsPreviousRealText;
    public @FXML
    ComboBox<PerformanceType> typeCombobox;
    public @FXML
    TextField eventoProbabilistico;
    public @FXML
    TextField eventoDeterministico;

    private final List<String> productos = ClientApplication.model.getProductos();
    private int nCols;
    private int nRows;

    public void initialize() {

        nCols = 13;
        nRows = productos.size() + 1;

        List<PerformanceType> options = new ArrayList<>();

        // Se le informa al usuario si ya ha ingresado las predicciones y los históricos.
        if (ClientApplication.model.getPreviousYearPrediction() == null) {
            existsPreviousPredictionText.setText("NO");
            existsPreviousPredictionText.setFill(Color.RED);
            options.add(PerformanceType.PREDICTED);
        } else {
            existsPreviousPredictionText.setText("YES");
            existsPreviousPredictionText.setFill(Color.valueOf("#139028"));
        }
        if (ClientApplication.model.getPreviousYearReal() == null) {
            existsPreviousRealText.setText("NO");
            existsPreviousRealText.setFill(Color.RED);
            options.add(PerformanceType.REAL);
        } else {
            existsPreviousRealText.setText("YES");
            existsPreviousRealText.setFill(Color.valueOf("#139028"));
        }

        typeCombobox.setItems(FXCollections.observableList(options));
        typeCombobox.getSelectionModel().select(0);

        // Inicialización del grid.
        for (int i = 0; i < productos.size(); i++) {
            for (int j = 0; j <= 12; j++) {
                if (j != 0) {
                    TextField textField = new TextField();
                    textField.setFont(Font.font(16));
                    dataEntryGrid.add(textField, j, i + 1);
                } else {
                    Text text = new Text(productos.get(i));
                    text.setFont(Font.font(18));
                    dataEntryGrid.add(new Text(productos.get(i)), j, i + 1);
                }
            }
        }
    }

    public void ingresarDatos() throws IOException {

        List<ProductData> productData = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            List<Double> rowProductData = new ArrayList<>();
            for (int j = 0; j <= 12; j++) {
                if (j != 0) {
                    String extractedText = ((TextField) getNodeFromGridPane(dataEntryGrid, j, i + 1)).getText();
                    if (extractedText.equals("")) {
                        rowProductData.add(0.0);
                    } else {
                        rowProductData.add(Double.parseDouble(extractedText));
                    }
                }
            }
            ProductData currentRowProductData = new ProductData(productos.get(i), rowProductData);
            productData.add(currentRowProductData);
        }

        if (typeCombobox.getValue() == PerformanceType.PREDICTED) {
            System.out.println("Prediction" + productData);
            ClientApplication.model.setPreviousYearPrediction(productData);
            ClientApplication.reloadScene();
        } else {
            System.out.println("RealSales" + productData);
            ClientApplication.model.setPreviousYearReal(productData);
            ClientApplication.reloadScene();
        }

    }

    public void proyeccionDemanda() throws IOException {
        if (ClientApplication.model.getPreviousYearPrediction() == null || ClientApplication.model.getPreviousYearReal() == null) {
            System.out.println("No se ha ingresado datos");
        } else if (eventoProbabilistico.getText().isEmpty() || eventoDeterministico.getText().isEmpty()) {
            System.out.println("No se ingresaron los eventos");
        } else {
            List<ProductData> productData = new ArrayList<>();
            List<ProductData> productDataProm = new ArrayList<>();
            double eventProbabilistico = Double.parseDouble(eventoProbabilistico.getText());
            double eventDeterministico = Double.parseDouble(eventoDeterministico.getText());
            double eventoTotal=(eventDeterministico*eventProbabilistico);
            System.out.println("" + eventoTotal);
            List<ProductData> PreviousPrediction = ClientApplication.model.getPreviousYearPrediction();
            List<ProductData> Real = ClientApplication.model.getPreviousYearReal();
            
           for (int i = 0; i < productos.size(); i++) {
                 List<Double> ventas = PreviousPrediction.get(i).getVentas();
                 List<Double> ventasReales = Real.get(i).getVentas();
                 List<Double> promVentas = new ArrayList<>();
               for (int j = 0; j < ventas.size(); j++) {
                  Double prom=(ventas.get(j)*ventasReales.get(j))/2;
                 promVentas.add(prom);
               }
               ProductData currentRowProductData = new ProductData(productos.get(i), promVentas);
               productDataProm.add(currentRowProductData);
           }
           System.out.println("Promedio:  " + productDataProm);
           ClientApplication.model.setPromPreviousSales(productDataProm);
  
           List<ProductData> AverageSales = ClientApplication.model.getPromSales();
          
            for (int i = 0; i < productos.size(); i++) {
                List<Double> ventasProyectadas = new ArrayList<>();
                List<Double> ventas = AverageSales.get(i).getVentas();
                 
                for (int j = 0; j < ventas.size(); j++) {
                   if(j==2 || j==4 || j==8 || j==11){
                    ventasProyectadas.add(ventas.get(j) * eventoTotal);
                    ((TextField) getNodeFromGridPane(dataEntryGrid, j+1, i + 1)).setText(""+(ventas.get(j) * eventoTotal));
                   }else{
                     
                    ventasProyectadas.add(ventas.get(j) * eventProbabilistico);
                    ((TextField) getNodeFromGridPane(dataEntryGrid, j+1, i + 1)).setText(""+(ventas.get(j) * eventProbabilistico));
                       
                   }
                    System.out.println("     venta:    "+productos.get(i)+ ventasProyectadas.get(j));
                    
                }
                ProductData currentRowProductData = new ProductData(productos.get(i), ventasProyectadas);
                productData.add(currentRowProductData);
            }
            ClientApplication.model.setPrediction(productData);
        }
    }

    
    
    
    
    // Método custom para obtener un nodo del GridPane por columna y fila.
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        int index = row * nCols + col;
        return gridPane.getChildren().get(index);
    }

}

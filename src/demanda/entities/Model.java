package demanda.entities;

import java.util.List;

public class Model {

    private final List<String> productos;
    private List<ProductData> previousYearPrediction;
    private List<ProductData> previousYearReal;
    private List<ProductData> prediction;


    public Model(List<String> productos) {
        this.productos = productos;
    }

    public List<String> getProductos() {
        return productos;
    }

    public List<ProductData> getPreviousYearPrediction() {
        return previousYearPrediction;
    }

    public void setPreviousYearPrediction(List<ProductData> previousYearPrediction) {
        this.previousYearPrediction = previousYearPrediction;
    }

    public List<ProductData> getPreviousYearReal() {
        return previousYearReal;
    }

    public void setPreviousYearReal(List<ProductData> previousYearReal) {
        this.previousYearReal = previousYearReal;
    }

    public List<ProductData> getPrediction() {
        return prediction;
    }

    public void setPrediction(List<ProductData> prediction) {
        this.prediction = prediction;
    }
}

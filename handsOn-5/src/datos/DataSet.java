package datos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataSet {
    private double[] xData;
    private double[] yData;
    private double[][] xMatrix;

    // Constructor para datos simples
    public DataSet(double[] xData, double[] yData) {
        this.xData = xData;
        this.yData = yData;
    }

    // Constructor para datos matriciales
    public DataSet(double[][] xMatrix, double[] yData) {
        this.xMatrix = xMatrix;
        this.yData = yData;
    }

    public List<Double> getXData() {
        return Arrays.stream(xData).boxed().collect(Collectors.toList());
    }

    public List<Double> getYData() {
        return Arrays.stream(yData).boxed().collect(Collectors.toList());
    }

    public double[][] getXMatrix() {
        return xMatrix;
    }

    @Override
    public String toString() {
        if (xData != null) {
            return "simple;" + Arrays.toString(xData) + ";" + Arrays.toString(yData);
        } else {
            StringBuilder sb = new StringBuilder("matrix;");
            for (double[] row : xMatrix) {
                sb.append(Arrays.toString(row)).append(";");
            }
            sb.append(Arrays.toString(yData));
            return sb.toString();
        }
    }

    public static DataSet fromString(String str) {
        String[] parts = str.split(";");
        if (parts[0].equals("simple")) {
            double[] xData = Arrays.stream(parts[1].replaceAll("[\\[\\]]", "").split(","))
                    .mapToDouble(Double::parseDouble).toArray();
            double[] yData = Arrays.stream(parts[2].replaceAll("[\\[\\]]", "").split(","))
                    .mapToDouble(Double::parseDouble).toArray();
            return new DataSet(xData, yData);
        } else if (parts[0].equals("matrix")) {
            int rows = parts.length - 2;
            double[][] xMatrix = new double[rows][];
            for (int i = 1; i <= rows; i++) {
                xMatrix[i - 1] = Arrays.stream(parts[i].replaceAll("[\\[\\]]", "").split(","))
                        .mapToDouble(Double::parseDouble).toArray();
            }
            double[] yData = Arrays.stream(parts[parts.length - 1].replaceAll("[\\[\\]]", "").split(","))
                    .mapToDouble(Double::parseDouble).toArray();
            return new DataSet(xMatrix, yData);
        }
        throw new IllegalArgumentException("Formato desconocido para DataSet");
    }
}

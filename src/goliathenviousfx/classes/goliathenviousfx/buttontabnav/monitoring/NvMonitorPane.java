package goliathenviousfx.buttontabnav.monitoring;

import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.custom.Space;
import goliathenviousfx.custom.Tile;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class NvMonitorPane extends VBox implements Runnable
{
    private static final List<NvMonitorPane> CHARTS = new ArrayList<>();
    
    public static List<NvMonitorPane> getCharts()
    {
        return CHARTS;
    }
    
    private final TilePane tilePane;
    
    private final LineChart<Integer, Integer> chart;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private final Series<Integer, Integer> series;
    
    private final IntegerProperty seconds;
    private final IntegerProperty average;
    private final IntegerProperty min;
    private final IntegerProperty max;
    private final IntegerProperty delta;
    
    private final Button reset;
    private final Button apply;
    private final Spinner<Integer> spinner;
    
    private BigInteger sum;
    private BigInteger totalUpdates;
    
    private final ReadOnlyNvReadable<? extends Number> readable;
    
    public NvMonitorPane(ReadOnlyNvReadable<? extends Number> rdbl, int maxVal)
    {
        super();
        super.setPrefWidth(Integer.MAX_VALUE);
        super.setStyle("-fx-background-color: -fx-theme-header;");
                 
        readable = rdbl;
        
        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: -fx-theme-header;");
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setPadding(new Insets(15*GoliathEnviousFX.SCALE));
        tilePane.setVgap(8*GoliathEnviousFX.SCALE);
        tilePane.setHgap(8*GoliathEnviousFX.SCALE);
        
        seconds = new SimpleIntegerProperty(60);
        min = new SimpleIntegerProperty(readable.getValue().intValue());
        max = new SimpleIntegerProperty(readable.getValue().intValue());
        delta = new SimpleIntegerProperty(readable.getValue().intValue());
        average = new SimpleIntegerProperty(readable.getValue().intValue());
        
        sum = new BigInteger("0");
        totalUpdates = new BigInteger("1");
        
        seconds.addListener(new SecondsListener());
        
        Tile averageTile = new Tile();
        averageTile.setObjectProperty("Average Value", average);
        averageTile.setOnMouseClicked(new AverageTileResetHandler());
        
        Tile minTile = new Tile();
        minTile.setObjectProperty("Minimum Value", min);
        minTile.setOnMouseClicked(new MinTileResetHandler());
        
        Tile maxTile = new Tile();
        maxTile.setObjectProperty("Maximum Value", max);
        maxTile.setOnMouseClicked(new MaxTileResetHandler());
        
        Tile deltaTile = new Tile();
        deltaTile.setObjectProperty("Min/Max Delta", delta);
        deltaTile.setOnMouseClicked(new DeltaTileResetHandler());
        
        Tile lastTile = new Tile();
        lastTile.setObjectProperty("Current Value", readable.valueProperty());
        
        Tooltip.install(averageTile, new Tooltip("Reset Average"));
        Tooltip.install(minTile, new Tooltip("Reset Min"));
        Tooltip.install(maxTile, new Tooltip("Reset Max"));
        Tooltip.install(deltaTile, new Tooltip("Reset Min/Max Delta"));
        
        tilePane.getChildren().add(averageTile);
        tilePane.getChildren().add(minTile);
        tilePane.getChildren().add(maxTile);
        tilePane.getChildren().add(deltaTile);
        tilePane.getChildren().add(lastTile);
        
        for(int i = 0; i < tilePane.getChildren().size()-1; i++)
        {
            tilePane.getChildren().get(i).setOnMouseEntered(new Tile.TileMouseEnterHandler());
            tilePane.getChildren().get(i).setOnMouseExited(new Tile.TileMouseExitHandler());
        }
        
        Space firstSpace = new Space(true);
        firstSpace.setMinHeight(1*GoliathEnviousFX.SCALE);
        firstSpace.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        xAxis = new NumberAxis();
        xAxis.setTickMarkVisible(true);
        xAxis.setTickLabelsVisible(true);
        xAxis.setLabel("Seconds");
        xAxis.upperBoundProperty().bind(seconds.asObject());
        xAxis.setAnimated(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(seconds.get()/6);
        
        yAxis = new NumberAxis();
        yAxis.setTickMarkVisible(true);
        yAxis.setTickLabelsVisible(true);
        yAxis.setAutoRanging(false);
        
        if(maxVal == 0)
            yAxis.setAutoRanging(true);
        
        yAxis.setAnimated(false);
        yAxis.setUpperBound(maxVal);
        yAxis.setLabel(readable.getUnit().getFullString());
        yAxis.setTickUnit(10);
        yAxis.setMinorTickVisible(true);
        yAxis.setMinorTickVisible(false);

        if(maxVal > 100)
            yAxis.setTickUnit(50);
        
        if(maxVal > 1000)
            yAxis.setTickUnit(100);
        
        if(maxVal > 2000)
            yAxis.setTickUnit(200);
            
        if(maxVal > 3000)
            yAxis.setTickUnit(300);
        
        if(maxVal > 4000)
            yAxis.setTickUnit(600);

        if(maxVal > 8000)
            yAxis.setTickUnit(1200);
        
        if(maxVal > 16000)
            yAxis.setTickUnit(2400);
        
        if(maxVal > 32000)
            yAxis.setTickUnit(4800);
        
        chart = new LineChart(xAxis, yAxis);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setPrefWidth(Integer.MAX_VALUE);
        chart.setMinHeight(300*GoliathEnviousFX.SCALE);
        chart.setMaxHeight(300*GoliathEnviousFX.SCALE);
        
        series = new XYChart.Series<>();
        chart.getData().add(series);
        
        series.nodeProperty().get().setStyle("-fx-stroke-width: " + 3*GoliathEnviousFX.SCALE);
        
        CHARTS.add(this);
        
        Space secondSpace = new Space(true);
        secondSpace.setMinHeight(1*GoliathEnviousFX.SCALE);
        secondSpace.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(8*GoliathEnviousFX.SCALE);

        reset = new Button("Reset");
        reset.setTooltip(new Tooltip("Set value to 60 seconds"));
        reset.setPrefWidth(100*GoliathEnviousFX.SCALE);
        reset.setOnMouseClicked(new SecondsResetHandler());
        
        apply = new Button("Apply");
        apply.setTooltip(new Tooltip("Set value to " + seconds.get() + " seconds"));
        apply.setPrefWidth(100*GoliathEnviousFX.SCALE);
        apply.setOnMouseClicked(new ApplyHandler());
        
        spinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 180, seconds.get()));
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory()).setAmountToStepBy(15);
        spinner.valueProperty().addListener(new SpinnerValueListener());
        spinner.setPrefWidth(100*GoliathEnviousFX.SCALE);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE, 8*GoliathEnviousFX.SCALE, 5*GoliathEnviousFX.SCALE));
        borderPane.setLeft(buttonBox);
        borderPane.setRight(spinner);
        
        buttonBox.getChildren().add(apply);
        buttonBox.getChildren().add(reset);
        
        super.getChildren().add(tilePane);
        super.getChildren().add(firstSpace);
        super.getChildren().add(chart);
        super.getChildren().add(secondSpace);
        super.getChildren().add(borderPane);
    }

    
    public ReadOnlyNvReadable<? extends Number> getNvReadable()
    {
        return readable;
    }
    
    public void update()
    {
        Platform.runLater(this);
    }
    
    @Override
    public void run()
    {
        sum = sum.add(new BigInteger(String.valueOf(readable.getValue().intValue())));
        average.set(sum.divide(totalUpdates).intValue());
        totalUpdates = new BigInteger(String.valueOf(totalUpdates.intValue()+1));
        
        if(readable.getValue().intValue() > max.get())
                max.set(readable.getValue().intValue());
            
        if(readable.getValue().intValue() < min.get())
                min.set(readable.getValue().intValue());
        
        delta.set(max.get() - min.get());
        
        series.getData().add(new XYChart.Data<>(series.getData().size(), readable.getValue().intValue()));
        
        if(series.getData().size() > seconds.get()+1)
        {
            ObservableList<Data<Integer, Integer>> data = FXCollections.observableArrayList(series.getData());
            
            series.getData().removeAll(series.getData());
            data.remove(0);

            for(int i = 0; i < data.size(); i++)
                series.getData().add(new Data<>(series.getData().size() ,data.get(i).getYValue()));
        }
    }
    
    private class ApplyHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            seconds.set(spinner.getValue());
            series.getData().removeAll(series.getData());
        }
    }
    
    private class SecondsResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            seconds.set(60);
            spinner.getValueFactory().setValue(60);
            series.getData().removeAll(series.getData());
        }
    }
    
    private class SecondsListener implements ChangeListener<Number>
    {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
        {
            xAxis.setTickUnit(t1.intValue()/5);
        }
    }
    
    private class SpinnerValueListener implements ChangeListener<Number>
    {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
        {
            apply.setTooltip(new Tooltip("Set value to " + t1.intValue() + " seconds"));
        } 
    }
    
    private class MinTileResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            min.set(readable.getValue().intValue());
        }
    }
    
    private class MaxTileResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            max.set(0);
        }
    }
    
    private class DeltaTileResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            delta.set(0);
        }
    }
    
    private class AverageTileResetHandler implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent t)
        {
            average.set(0);
            sum = new BigInteger("0");
            totalUpdates = new BigInteger("1");
        }
    }
}

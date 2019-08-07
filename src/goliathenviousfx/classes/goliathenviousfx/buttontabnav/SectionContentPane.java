/*
 * The MIT License
 *
 * Copyright 2018 ty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliathenviousfx.buttontabnav;

import goliathenviousfx.GoliathEnviousFX;
import goliathenviousfx.custom.Space;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SectionContentPane extends VBox
{
    private final GridPane subGrid;
    
    public SectionContentPane(String tlt)
    {
        super();
        super.setPrefWidth(Integer.MAX_VALUE);
        super.setAlignment(Pos.TOP_CENTER);
        
        subGrid = new GridPane();
        subGrid.setStyle("-fx-background-color: -fx-theme-header;");
        subGrid.setAlignment(Pos.TOP_CENTER);
        
        ColumnConstraints conWidthContent = new ColumnConstraints();
        conWidthContent.setPercentWidth(100);
        
        subGrid.getColumnConstraints().add(conWidthContent);
        
        Space bottomSpace = new Space(true);
        bottomSpace.setMinHeight(1*GoliathEnviousFX.SCALE);
        bottomSpace.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        Label sectionLabel = new Label(tlt);
        sectionLabel.setStyle("-fx-background-color: -fx-theme-header;");
        sectionLabel.setAlignment(Pos.CENTER);
        sectionLabel.setPrefWidth(Integer.MAX_VALUE);
        sectionLabel.setPadding(new Insets(8*GoliathEnviousFX.SCALE));
        
        super.getChildren().add(sectionLabel);
        super.getChildren().add(bottomSpace);
        super.getChildren().add(subGrid);
    }
    
    public SectionContentPane(String tlt, Node node)
    {
        super();
        super.setPrefWidth(Integer.MAX_VALUE);
        super.setAlignment(Pos.TOP_CENTER);
        
        subGrid = new GridPane();
        subGrid.setStyle("-fx-background-color: -fx-theme-header;");
        subGrid.setAlignment(Pos.TOP_CENTER);
        
        ColumnConstraints conWidthContent = new ColumnConstraints();
        conWidthContent.setPercentWidth(100);
        
        subGrid.getColumnConstraints().add(conWidthContent);
        
        subGrid.add(node, 0, 0);
        
        Space bottomSpace = new Space(true);
        bottomSpace.setMinHeight(1*GoliathEnviousFX.SCALE);
        bottomSpace.setMaxHeight(1*GoliathEnviousFX.SCALE);
        
        Label sectionLabel = new Label(tlt);
        sectionLabel.setStyle("-fx-background-color: -fx-theme-header;");
        sectionLabel.setAlignment(Pos.CENTER);
        sectionLabel.setPrefWidth(Integer.MAX_VALUE);
        sectionLabel.setPadding(new Insets(8*GoliathEnviousFX.SCALE));
        
        super.getChildren().add(sectionLabel);
        super.getChildren().add(bottomSpace);
        super.getChildren().add(subGrid);
    }
    
    public void addTo(Node node)
    {
        subGrid.add(node, 0, subGrid.getRowCount());
    }
}

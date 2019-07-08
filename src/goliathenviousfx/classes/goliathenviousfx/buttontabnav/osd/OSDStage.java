/* 
 * The MIT License
 *
 * Copyright 2018 Ty Young.
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
package goliathenviousfx.buttontabnav.osd;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathEnviousFX;

public class OSDStage extends Stage
{

    public static boolean OSD_ENABLED = false;

    private final Scene scene;

    public OSDStage()
    {
        super();
        super.setTitle("Goliath Envious FX OSD");
        super.setResizable(false);
        super.setX(0);
        super.setY(0);
        super.initStyle(StageStyle.TRANSPARENT);
        super.setAlwaysOnTop(true);

        scene = new Scene(new OsdTable());
        scene.setFill(Color.TRANSPARENT);
        scene.getRoot().setStyle("-fx-font-size: " + GoliathEnviousFX.FONT.getSize() + "px;");
        OSD_ENABLED = true;

        super.setScene(scene);
        super.show();
    }

    public void setColor(String col)
    {
        ((OsdTable)scene.getRoot()).setColor(col);
    }
    
    @Override
    public void close()
    {
        OSD_ENABLED = false;
        super.close();
    }

    private class OsdTable extends TableView<ReadOnlyNvReadable>
    {

        private final TableColumn<ReadOnlyNvReadable, String> colOne, colTwo;

        public OsdTable()
        {
            super();
            super.setPrefWidth(447*GoliathEnviousFX.SCALE);
            super.setPrefHeight(600*GoliathEnviousFX.SCALE);
            super.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            super.setEditable(false);
            super.setMouseTransparent(true);
            super.setTableMenuButtonVisible(false);
            super.getStylesheets().add("goliathenviousfx/buttontabnav/osd/osd.css");
            super.setItems(OSDSectionContentPane.rdbls);
            
            colOne = new TableColumn<>();
            colOne.setMinWidth(245*GoliathEnviousFX.SCALE);
            colOne.setMaxWidth(245*GoliathEnviousFX.SCALE);
            colOne.setStyle("-fx-text-fill: #00FF00;");
            colOne.setCellValueFactory(new PropertyValueFactory<>("displayName"));
            colOne.setEditable(false);

            colTwo = new TableColumn<>();
            colTwo.setMinWidth(200*GoliathEnviousFX.SCALE);
            colTwo.setMaxWidth(200*GoliathEnviousFX.SCALE);
            colTwo.setStyle("-fx-text-fill: #00FF00;");
            colTwo.setCellValueFactory(new PropertyValueFactory<>("displayValue"));
            colTwo.setEditable(false);

            super.getColumns().add(colOne);
            super.getColumns().add(colTwo);
        }
        
        public void setColor(String col)
        {
            colOne.setStyle("-fx-text-fill: " + col);
            colTwo.setStyle("-fx-text-fill: " + col);
        }
    }
}

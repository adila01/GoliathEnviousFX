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
package goliathenviousfx.custom;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import goliath.envious.interfaces.ReadOnlyNvReadable;
import goliathenviousfx.GoliathENVIOUSFX;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;

public class GenericReadableTablePane extends VBox
{   
    private final TableView<ReadOnlyNvReadable> infoTable;
    private final TableColumn<ReadOnlyNvReadable, String> displayName;
    private final TableColumn<ReadOnlyNvReadable, String> displayValue;
    
    public GenericReadableTablePane(List<ReadOnlyNvReadable> readables)
    {
        super();
        super.setAlignment(Pos.TOP_CENTER);
        super.setMinHeight(200*GoliathENVIOUSFX.SCALE);
        
        DoubleBinding bind = super.widthProperty().multiply(.85);
        infoTable = new TableView<>(FXCollections.observableArrayList(readables));
        infoTable.minWidthProperty().bind(bind);
        infoTable.maxWidthProperty().bind(bind);
        infoTable.setEditable(false);
        infoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        displayName = new TableColumn<>("Name");
        displayName.setEditable(false);
        displayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        
        displayValue = new TableColumn<>("Value");
        displayValue.setEditable(false);
        displayValue.setCellValueFactory(new PropertyValueFactory<>("displayValue"));
        
        infoTable.getColumns().addAll(displayName, displayValue);
        
        super.getChildren().add(infoTable);
    }
}

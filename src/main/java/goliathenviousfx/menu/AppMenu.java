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
package goliathenviousfx.menu;

import goliath.envious.exceptions.ControllerResetFailedException;
import goliath.envious.gpu.NvGPU;
import goliath.nvsettings.main.NvSettings;
import goliathenviousfx.menu.items.ResetMenu;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class AppMenu extends MenuBar
{
    private final Menu[] items;
    
    public AppMenu()
    {
        super();
        super.getStyleClass().add("menubar");


        items = new Menu[1];
        //items[0] = new TabsMenu(tabPane);
        items[0] = new ResetMenu();
        

        
        
        //items[1] = new ThemesMenu();

        super.getMenus().addAll(items);
    }
    
    private class ResetHandler implements EventHandler<Event>
    {

        @Override
        public void handle(Event event)
        {
            try
            {
                NvSettings.getInstance(NvGPU.getPrimaryNvGPU()).resetControllables();
            }
            catch (ControllerResetFailedException ex)
            {
                ex.printStackTrace();
            }
        }
        
    }
}

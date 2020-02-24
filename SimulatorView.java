import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
        
/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 * @version 2016.02.29
 */
public interface SimulatorView
{
// Colors used for empty locations.
 Color UNKNOWN_COLOR = Color.gray;

/**
 * Define a color to be used for a given class of animal.
 * @param animalClass The animal's Class object.
 * @param color The color to be used for the given class.
 */
void setColor(Class animalClass, Color color);

/**
 * Show the current status of the field.
 * @param step Which iteration step it is.
 * @param field The field whose status is to be displayed.
 */
void showStatus(int step, Field field);

/**
 * Determine whether the simulation should continue to run.
 * @return true If there is more than one species alive.
 */
boolean isViable(Field field);

/**
 * Prepare for a new run.
 */
void reset();
}

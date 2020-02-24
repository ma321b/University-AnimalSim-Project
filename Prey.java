import java.util.List;
import java.util.Iterator;

/**
 * Abstract class Prey, subclass of class Actor.
 * This class stores information about the preys in the
 * Simulation, i.e., Triceratops, Mammoth, Stegosaurus
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public abstract class Prey extends Animal
{
    public Prey(Field field , Location location)
    {
        super(field, location);
    }

    /**
     * This is what the Prey does most of the time: it eats plants.
     * In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newPrey A list to return newly born predators.
     */
    public void act(List<Actor> newPrey)
    {
        if(isActive()) {
            giveBirth(newPrey);
            // Try to move into a free location.
            Location newLocation;
            if (getFoodLevel() <= getMaxApetite()/2) {
                newLocation = findFood();
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            else {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null)  {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * @return The current food level of the animal.
     */
    protected abstract int getFoodLevel();

    /**
     * (Abstract) Give birth to young
     * @param newPreys A list to return newly born Predators.
     */
    abstract public void giveBirth(List<Actor> newPreys);

    /**
     * Look for Plants adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Plant) {
                Plant plant = (Plant) actor;
                if(plant.isActive()) {
                    plant.setDead();
                    satisfyHunger();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Fill the animal's food level to a certain
     * maximum level.
     */
    abstract public void satisfyHunger();

    /**
     * @return The maximum food level of the animal.
     */
    abstract public int getMaxApetite();
}
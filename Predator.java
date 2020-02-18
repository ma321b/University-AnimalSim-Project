import java.util.List;
import java.util.Iterator;

/**
 * Abstract class Predator - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Predator extends Animal
{
    // instance variables - replace the example below with your own

    public Predator(Field field , Location location)
    {
        super(field, location);
    }

    /**
     * This is what the tRex does most of the time: it hunts for
     * stegosauruss. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newPredators A list to return newly born predators.
     */
    public void act(List<Actor> newPredators)
    {

        if (isAlive()) {
            giveBirth(newPredators);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }

    }
    /**
     * Look for stegosauruss adjacent to the current location.
     * Only the first live stegosaurus is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Prey) {
                Prey prey = (Prey) actor;
                if(prey.isAlive()) {
                    prey.setDead();
                    satisfyHunger();
                    return where;
                }
            }
        }
        return null;
    }

    abstract public void giveBirth(List<Actor> newPredators);

    abstract public void satisfyHunger();
}
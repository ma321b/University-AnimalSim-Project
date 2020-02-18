import java.util.List;
import java.util.Iterator;

/**
 * Abstract class Prey - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Prey extends Animal
{
    public Prey(Field field , Location location)
    {
        super(field, location);
    }

    public void act(List<Actor> newPrey)
    {
        if(isAlive()) {
            giveBirth(newPrey);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    abstract public void giveBirth(List<Actor> newPredators);

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
                if(plant.isAlive()) {
                    plant.setDead();
                    satisfyHunger();
                    return where;
                }
            }
        }
        return null;
    }
    abstract public void satisfyHunger();
}
import java.util.List;
import java.util.Iterator;

/**
 * Abstract class Predator, subclass of class Actor. 
 * This class stores information about the predators in the 
 * Simulation, i.e., TRex and Raptor.
 * 
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public abstract class Predator extends Animal
{
    
    public Predator(Field field , Location location)
    {
        super(field, location);
    }

    /**
     * This is what the Predator does most of the time: it hunts for
     * Preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newPredators A list to return newly born predators.
     */
    public void act(List<Actor> newPredators)
    {
        if (isActive()) {
            giveBirth(newPredators);
            // Move towards a source of food if found.
            Location newLocation;
            if (getFoodLevel() <= getMaxApetite()/2)
            {
                newLocation = findFood();
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            else {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }

            if (newLocation != null){
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }

    }

    /**
     * Look for Preys adjacent to the current location.
     * Only the first live Prey is eaten.
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
                if(prey.isActive()) {
                    prey.setDead();
                    satisfyHunger();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * (Abstract) Give birth to young
     * @param newPredators A list to return newly born Predators.
     */
    abstract public void giveBirth(List<Actor> newPredators);

    /**
     * Fill the animal's food level to a certain
     * maximum level.
      */
    abstract public void satisfyHunger();

    /**
     * @return The maximum food level of the animal.
     */
    abstract public int getMaxApetite();

    /**
     * @return The current food level of the animal.
     */
    abstract public int getFoodLevel();
}
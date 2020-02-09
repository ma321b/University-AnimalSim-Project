import java.util.List;

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
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newPredators)
    {
        if(isAlive()) {
            giveBirth(newPredators);      
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    abstract public void giveBirth(List<Animal> newPredators);
    abstract public Location findFood();
    
    
}

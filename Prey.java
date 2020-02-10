import java.util.List;

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
    
}

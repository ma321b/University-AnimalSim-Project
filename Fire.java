import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * Fire is an actor in the simulation which spreads and destroys animals
 * and plants in the field
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Fire extends Actor
{
    private boolean active;
    private Weather currentWeather;
    private static final double SPREAD_PROBABILITY = 0.1;
    private static final int MAX_FIRE_SIZE = 10;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    public Fire(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        active = true;
    }

    /**
     * Moves fire around and spreads fire.
     * @param newFire The list containing actors.
     */
    public void act(List<Actor> newFire)
    {
        spreadFire(newFire);
        Location newLocation = burn();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }

        else {
            newLocation = field.freeAdjacentLocation(location);
        }
        if(newLocation != null)  {
            setLocation(newLocation);
        }
    }

    /**
     * Place the fire at the new location in the given field.
     * @param newLocation The fire's new location.
     */
    private void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * @return True if active. Otherwise false.
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Spreads fire to adjacent locations.
     * @param newFire List containing actors.
     */
    public void spreadFire(List<Actor> newFire)
    {
        // Fire is spread into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int moreFire = spread();
        for(int b = 0; b < moreFire && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fire fire = new Fire(field, loc);
            newFire.add(fire);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int spread()
    {
        int moreFire = 0;
        if(rand.nextDouble() <= SPREAD_PROBABILITY) {
            moreFire = rand.nextInt(MAX_FIRE_SIZE) + 1;
        }
        return moreFire;
    }

    /**
     * Move fire randomly around
     * @return Free location to act method
     */
    public Location burn()
    {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if (actor instanceof Animal){
                Animal animal = (Animal) actor;
                if(animal.isActive()) {
                    animal.setDead();
                    return where;
                }
            }
            else if (actor instanceof Plant){
                Plant plant = (Plant) actor;
                if(plant.isActive()) {
                    plant.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}


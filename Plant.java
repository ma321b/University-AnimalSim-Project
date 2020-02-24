import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Write a description of class Plant here.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class Plant extends Actor
{
    private boolean active;
    private Weather currentWeather;
    private static final double GROWTH_PROBABILITY = 0.04;
    private static final int MAX_GROWTH_SIZE = 5;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    public Plant(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        active = true;
    }

    /**
     * Check whether the plant is alive or not.
     * @return True if the plant is still alive.
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Make the plant grow.
     * @param newPlants List of actors.
     */
    public void act(List<Actor> newPlants)
    {
        currentWeather = currentWeather.getCurrentWeather();
        grow(newPlants);
    }

    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        active = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Grow new plants
     * @param newPlants The new plant's location
     */
    public void grow(List<Actor> newPlants)
    {
        if (location != null) {
            List<Location> free = field.getFreeAdjacentLocations(location);
            int growths = canGrow();
            for(int b = 0; b < growths && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Plant young = new Plant(field, loc);
                newPlants.add(young);
            }
        }
    }

    /**
     * @return Number of plants that can grow.
     */
    public int canGrow()
    {
        int growths = 0;
        if ((currentWeather instanceof Rain) && (rand.nextDouble() <= GROWTH_PROBABILITY)){
            growths = rand.nextInt(MAX_GROWTH_SIZE) + 1;
        }
        return growths;
    }

    /**
     * This method gets a list of free locations from the field class
     * It checks for rain and if true it creates new plants into each free location
     * in the list if the GROWTH_PROBABILITY allows.
     * @param newPlants A list to return newly born mammoths.
     * @param field where the actors are located
     */
    public static void autoGrow(List<Actor> newPlants, Field field)
    {
        if ((TimeTrack.getStep() % 50) == 0){
            List<Location> free = field.getFreeLocations();
            Iterator<Location> it = free.iterator();
            if ((Weather.getCurrentWeather() instanceof Rain)){
                while (it.hasNext()){
                    if (rand.nextDouble() <= GROWTH_PROBABILITY){
                        Location where = it.next();
                        Plant young = new Plant(field, where);
                        newPlants.add(young);
                    }
                }
            }
        }
    }

}
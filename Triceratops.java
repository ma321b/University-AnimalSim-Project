import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class Triceratops here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Triceratops extends Prey
{
    // Characteristics shared by all stegosauruss (class variables).

    // The age at which a stegosaurus can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a stegosaurus can live.
    private static final int MAX_AGE = 60;
    // The likelihood of a stegosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    private static final int PLANT_FOOD_VALUE = 10;

    // The stegosaurus's age.
    private int age;

    private int foodLevel;

    /**
     * Create a new stegosaurus. A stegosaurus may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the stegosaurus will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Triceratops(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * This is what the stegosaurus does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     * @param newTriceratops A list to return newly born stegosauruss.
     */
    public void act(List<Actor> newTriceratops)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newTriceratops);
            super.findFood();
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

    /**
     * Increase the age.
     * This could result in the stegosaurus's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this stegosaurus is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newTriceratops A list to return newly born stegosauruss.
     */
    public void giveBirth(List<Actor> newTriceratops)
    {
        // New stegosauruss are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Triceratops young = new Triceratops(false, field, loc);
            newTriceratops.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A stegosaurus can breed if it has reached the breeding age.
     * @return true if the stegosaurus can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && mate();
    }

    public void satisfyHunger(){
        foodLevel += PLANT_FOOD_VALUE;
    }

    /**
     * @return True if two same species are in adjacent fields and are
     *         of an opposite gender. Return False otherwise.
     */
    public boolean mate()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (age >= BREEDING_AGE && animal instanceof Triceratops) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }
}
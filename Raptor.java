import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Raptor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Raptor extends Predator
{
    // Characteristics shared by all raptors (class variables).

    // The age at which a raptor can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a raptor can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a raptor breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The food value of a single raptor. In effect, this is the
    // number of steps a raptor can go before it has to eat again.
    private static final int PREY_FOOD_VALUE = 10;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The Raptor's age.
    private int age;
    // The Raptor's food level, which is increased by eating raptors.
    private int foodLevel;

    /**
     * Create a Raptor. A Raptor can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the raptor will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Raptor(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PREY_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = PREY_FOOD_VALUE;
        }
    }

    /**
     * This is what the raptor does most of the time: it hunts for
     * raptors. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newRaptors A list to return newly born raptores.
     */
    public void act(List<Actor> newRaptors)
    {
        incrementAge();
        incrementHunger();
        super.act(newRaptors);
    }
    /**
     * Increase the age. This could result in the raptor's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this raptor more hungry. This could result in the raptor's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this raptor is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRaptors A list to return newly born raptores.
     */
    public void giveBirth(List<Actor> newRaptors)
    {
        // New raptores are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Raptor young = new Raptor(false, field, loc);
            newRaptors.add(young);
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
     * A raptor can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && mate();
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
            if (age >= BREEDING_AGE && animal instanceof Raptor) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void satisfyHunger()
    {
        foodLevel += PREY_FOOD_VALUE;
    }
}
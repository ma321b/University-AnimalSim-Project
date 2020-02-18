import java.util.List;
import java.util.Random;

/**
 * A simple model of a tRex.
 * TRex age, move, eat stegosauruses, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class TRex extends Predator
{
    // Characteristics shared by all tRexes (class variables).

    // The age at which a tRex can start to breed.
    private static final int BREEDING_AGE = 8;
    private static final int MAX_AGE = 150;
    // The likelihood of a tRex breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // The food value of a single stegosaurus. In effect, this is the
    // number of steps a tRex can go before it has to eat again.
    private static final int PREY_FOOD_VALUE = 9;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).

    // The tRex's age.
    private int age;
    // The tRex's food level, which is increased by eating stegosauruss.
    private int foodLevel;

    /**
     * Create a tRex. A tRex can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the tRex will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public TRex(boolean randomAge, Field field, Location location)
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
     * This is what the tRex does most of the time: it hunts for
     * Stegosauruses. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newTRexes A list to return newly born tRexes.
     */
    public void act(List<Actor> newTRexes)
    {
        incrementAge();
        incrementHunger();
        super.act(newTRexes);
    }
    /**
     * Increase the age. This could result in the tRex's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this tRex more hungry. This could result in the tRex's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether or not this tRex is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newTRexes A list to return newly born tRexes.
     */
    public void giveBirth(List<Actor> newTRexes)
    {
        // New tRexes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            TRex young = new TRex(false, field, loc);
            newTRexes.add(young);
        }
    }

    public void satisfyHunger()
    {
        foodLevel += PREY_FOOD_VALUE;
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
     * A tRex can breed if it has reached the breeding age.
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
            if (age >= BREEDING_AGE && animal instanceof TRex) {
                if (((Animal) animal).isMale() != this.isMale()) {
                    return true;
                }
            }
        }
        return false;
    }
}
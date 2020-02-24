import java.util.List;
import java.util.Random;

/**
 * A simple model of a T-Rex.
 * TRexes age, move, eat preys, and die.
 *
 * @author Muhammad Athar Abdullah (k19037983), Muhammad Ismail Kamdar(k19009749)
 */
public class TRex extends Predator
{
    // Characteristics shared by all tRexes (class variables).

    // The age at which a tRex can start to breed.
    private static final int BREEDING_AGE = 4;
    private static final int MAX_AGE = 80;
    // The likelihood of a tRex breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single prey. In effect, this is the
    // number of steps a tRex can go before it has to eat again.
    private static final int PREY_FOOD_VALUE = 20;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The tRex's age.
    private int age;
    // The tRex's food level, which is increased by eating prey.
    private int foodLevel;
    
    private static final int TREX_MAX_APETITE = 40;

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
            foodLevel = rand.nextInt(TREX_MAX_APETITE);
        }
        else {
            age = 0;
            foodLevel = PREY_FOOD_VALUE;
        }
    }

    /**
     * This is what the tRex does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
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
     * @param newtRexes A list to return newly born tRexes.
     */
    public void giveBirth(List<Actor> newtRexes)
    {
        // New tRexes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            TRex young = new TRex(false, field, loc);
            newtRexes.add(young);
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

    /**
     * Fill the TRex's food level to a certain
     * maximum level.
     */
    public void satisfyHunger()
    {
        if ((foodLevel += PREY_FOOD_VALUE) > TREX_MAX_APETITE) {
            foodLevel = TREX_MAX_APETITE;
        }
        else {
            foodLevel += PREY_FOOD_VALUE;
        }
    }

    /**
     * @return The maximum food level of the TRex.
     */
    public int getMaxApetite()
    {
        return TREX_MAX_APETITE;
    }

    /**
     * @return The current food level of the TRex.
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }
}
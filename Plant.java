import java.util.List;
/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plant extends Actor
{
    private boolean active;

    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        active = true;
    }
    public boolean isAlive()
    {
        return active;
    }

    public void act(List<Actor> newActors)
    {

    }

    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Indicate that the animal is no longer alive.
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
}
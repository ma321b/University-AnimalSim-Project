import java.util.List;
/**
 * Abstract class Actor - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Actor
{
    abstract public void act(List<Actor> newActors);
    abstract boolean isAlive();
}

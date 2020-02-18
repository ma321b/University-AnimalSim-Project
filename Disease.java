import java.util.Random;

public class Disease
{
    private static boolean pandemicSpread;
    private static Random rand;

    public Disease()
    {
        pandemicSpread = false;
        rand = new Random();
    }

    /**
     * Randomly spreads the pandemic in the
     * simulator every 500 steps.
     */
    public static void spreadPandemic()
    {
        if (TimeTrack.getStep() % 500 == 0 && TimeTrack.getStep() != 0) {
            if (rand.nextBoolean()) {
                pandemicSpread = true;
            }
        }
    }

    /**
     * @return True if virus is spread. Else return false.
     */
    public static boolean isPandemicSpread()
    {
        return pandemicSpread;
    }
}

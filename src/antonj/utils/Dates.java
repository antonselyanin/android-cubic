package antonj.utils;

/**
 * User: Anton
 * Date: 21.03.2010
 * Time: 20:12:31
 */
public class Dates {
    //TODO: implement
    public static String timeToString(long time) {
        time /= 1000;

        int seconds = (int) (time % 60);

        time /= 60;
        int minutes = (int) (time % 60);

        time /= 60;

        int hours = (int) time;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hrs ");
        }

        if (hours > 0 || minutes > 0) {
            result.append(minutes).append(" min ");
        }

        if (hours > 0 || minutes > 0 || seconds > 0) {
            result.append(seconds).append(" sec");
        }

        return result.toString();
    }
}

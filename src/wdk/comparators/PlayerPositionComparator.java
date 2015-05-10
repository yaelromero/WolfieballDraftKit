package wdk.comparators;

import java.util.Comparator;
import wdk.data.Player;

/**
 *
 * @author Yael
 */
public class PlayerPositionComparator implements Comparator<Player> {

    // - 1 if first comes before second, zero if same, 1 if second comes before first.
    @Override
    public int compare(Player o1, Player o2) {
        String o1Pos = o1.getChosenPosition();
        String o2Pos = o2.getChosenPosition();
        if(o1Pos.equalsIgnoreCase("C")) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("C")) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("CI") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("3B") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("2B") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI")
                && !o2Pos.equalsIgnoreCase("3B"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("MI") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI") &&
                !o2Pos.equalsIgnoreCase("3B") && !o2Pos.equalsIgnoreCase("2B"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("SS") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI") &&
                !o2Pos.equalsIgnoreCase("3B") && !o2Pos.equalsIgnoreCase("2B") &&
                !o2Pos.equalsIgnoreCase("MI"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("OF") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI") &&
                !o2Pos.equalsIgnoreCase("3B") && !o2Pos.equalsIgnoreCase("2B") &&
                !o2Pos.equalsIgnoreCase("MI") && !o2Pos.equalsIgnoreCase("SS"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("U") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI") &&
                !o2Pos.equalsIgnoreCase("3B") && !o2Pos.equalsIgnoreCase("2B") &&
                !o2Pos.equalsIgnoreCase("MI") && !o2Pos.equalsIgnoreCase("SS") &&
                !o2Pos.equalsIgnoreCase("OF"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase("P") && (!o2Pos.equalsIgnoreCase("C") &&
                !o2Pos.equalsIgnoreCase("1B") && !o2Pos.equalsIgnoreCase("CI") &&
                !o2Pos.equalsIgnoreCase("3B") && !o2Pos.equalsIgnoreCase("2B") &&
                !o2Pos.equalsIgnoreCase("MI") && !o2Pos.equalsIgnoreCase("SS") &&
                !o2Pos.equalsIgnoreCase("OF") && !o2Pos.equalsIgnoreCase("U"))) {
            return -1;
        }
        else if(o1Pos.equalsIgnoreCase(o2Pos)) {
            return 0;
        }
        else
            return 1;
    }
    
}

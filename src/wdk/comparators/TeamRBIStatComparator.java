package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamRBIStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggRBIStat() > o2.getAggRBIStat()) {
            return -1;
        }
        else if(o2.getAggRBIStat() < o2.getAggRBIStat()) {
            return 1;
        }
        else
            return 0;
    }   
}

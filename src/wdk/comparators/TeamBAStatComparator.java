package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamBAStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggBAStat() > o2.getAggBAStat()) {
            return -1;
        }
        else if(o2.getAggBAStat() < o2.getAggBAStat()) {
            return 1;
        }
        else
            return 0;
    }   
}


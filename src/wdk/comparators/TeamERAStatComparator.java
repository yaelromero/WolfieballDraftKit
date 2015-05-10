package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamERAStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggERAStat() > o2.getAggERAStat()) {
            return -1;
        }
        else if(o2.getAggERAStat() < o2.getAggERAStat()) {
            return 1;
        }
        else
            return 0;
    }   
}

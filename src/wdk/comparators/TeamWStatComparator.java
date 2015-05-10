package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamWStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggWStat() > o2.getAggWStat()) {
            return -1;
        }
        else if(o2.getAggWStat() < o2.getAggWStat()) {
            return 1;
        }
        else
            return 0;
    }   
}

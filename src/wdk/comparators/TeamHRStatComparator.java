package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamHRStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggHRStat() > o2.getAggHRStat()) {
            return -1;
        }
        else if(o2.getAggHRStat() < o2.getAggHRStat()) {
            return 1;
        }
        else
            return 0;
    }   
}

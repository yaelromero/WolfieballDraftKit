package wdk.comparators;

import java.util.Comparator;
import wdk.data.Team;

/**
 *
 * @author Yael
 */

public class TeamSBStatComparator implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getAggSBStat() > o2.getAggSBStat()) {
            return -1;
        }
        else if(o2.getAggSBStat() < o2.getAggSBStat()) {
            return 1;
        }
        else
            return 0;
    }   
}

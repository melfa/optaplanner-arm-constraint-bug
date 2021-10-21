package org.acme;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Schedule {
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeslotRange")
    public List<Timeslot> timeslots;

    @PlanningEntityCollectionProperty
    public List<WaveMeeting> meetings;

    @PlanningScore
    private HardSoftScore score;

    public HardSoftScore getScore() {
        return score;
    }
}

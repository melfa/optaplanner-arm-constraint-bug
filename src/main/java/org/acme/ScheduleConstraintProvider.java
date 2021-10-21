package org.acme;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class ScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
            equipmentSufficientForOverlappedMeetingsHardConstraint(constraintFactory),
        };
    }

    public Constraint equipmentSufficientForOverlappedMeetingsHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
            .from(WaveMeeting.class)
            .join(Timeslot.class,
                Joiners.overlapping(
                    WaveMeeting::getStartMinute,
                    WaveMeeting::getEndMinute,
                    Timeslot::getStartMinute,
                    Timeslot::getEndMinute
                )
            )
            .groupBy(
                (meeting, timeslot) -> timeslot,
                ConstraintCollectors.toList((WaveMeeting meeting, Timeslot timeslot) -> meeting)
            )
            .penalize(
                "Не хватает оборудования для всех встреч в это время",
                HardSoftScore.ONE_HARD
            );
    }
}

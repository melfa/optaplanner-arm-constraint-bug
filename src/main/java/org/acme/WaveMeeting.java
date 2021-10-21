package org.acme;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;


@PlanningEntity
public class WaveMeeting {
    @PlanningId
    private String id;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    // @ProblemFactCollectionProperty
    // @ValueRangeProvider(id = "timeslotRange")
    // private List<Timeslot> suitableTimeslots;

    public WaveMeeting() {
        id = NanoIdUtils.randomNanoId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public long getStartMinute() {
        return timeslot.getStartMinute();
    }

    public long getEndMinute() {
        return timeslot.getStartMinute() + 45;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    // public void setSuitableTimeslots(List<Timeslot> suitableTimeslots) {
    //     this.suitableTimeslots = suitableTimeslots;
    // }

    // public List<Timeslot> getSuitableTimeslots() {
    //     return suitableTimeslots;
    // }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
        }
		if (obj == null) {
			return false;
        }
		if (getClass() != obj.getClass()) {
			return false;
        }
		WaveMeeting other = (WaveMeeting) obj;
		if (!id.equals(other.id)) {
			return false;
        }
		return true;
	}

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

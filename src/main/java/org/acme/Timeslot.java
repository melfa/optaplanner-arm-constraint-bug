package org.acme;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class Timeslot {
    @PlanningId
    private String id;

    private long startMinute;
    private long endMinute;

    public Timeslot() {
        id = NanoIdUtils.randomNanoId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartMinute() {
        return startMinute;
    }

    public long getEndMinute() {
        return endMinute;
    }

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
		Timeslot other = (Timeslot) obj;
		if (!id.equals(other.id)) {
			return false;
        }
		return true;
	}

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}

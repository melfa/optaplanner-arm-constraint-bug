package org.acme;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig;
import org.optaplanner.core.config.localsearch.decider.forager.LocalSearchForagerConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationCompositionStyle;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

@Path("/hello")
public class GreetingResource {

    @POST
    public Schedule test(Schedule schedule) {
        SolverManager<Schedule, String> solverManager = SolverManager.create(createSolverFactory());
        SolverJob<Schedule, String> solverJob = solverManager.solve("1", schedule);

        Schedule solution;
        try {
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }

    protected SolverFactory<Schedule> createSolverFactory() {
        LocalSearchPhaseConfig localSearchPhaseConfig = new LocalSearchPhaseConfig()
                .withAcceptorConfig(new LocalSearchAcceptorConfig().withSimulatedAnnealingStartingTemperature("15hard/1000soft"))
                .withForagerConfig(new LocalSearchForagerConfig().withAcceptedCountLimit(4));

        localSearchPhaseConfig.setTerminationConfig(
            new TerminationConfig()
                .withTerminationCompositionStyle(TerminationCompositionStyle.AND)
                .withUnimprovedMinutesSpentLimit(30L)
                .withUnimprovedScoreDifferenceThreshold("5hard/*soft")
        );

        return SolverFactory.create(
            new SolverConfig()
                .withSolutionClass(Schedule.class)
                .withEntityClasses(WaveMeeting.class)
                .withConstraintProviderClass(ScheduleConstraintProvider.class)

                .withPhases(
                    new ConstructionHeuristicPhaseConfig(),
                    localSearchPhaseConfig
                )

                .withMoveThreadCount("10")
                .withTerminationConfig(
                    new TerminationConfig()
                        .withHoursSpentLimit(4L)

                        .withBestScoreFeasible(true)
                )
        );
    }
}
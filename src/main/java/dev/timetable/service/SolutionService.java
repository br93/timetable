package dev.timetable.service;

import org.springframework.stereotype.Service;

import dev.timetable.domain.timetable.Solution;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.repository.SolutionRepository;
import dev.timetable.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final MessageUtil messageUtil;

    public Solution createSolution(Solution solution) {
        return this.solutionRepository.save(solution);
    }

    public Solution findSolutionById(String id) {
        return this.solutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.notFoundMessage("Solution", "id", id)));
    }
}

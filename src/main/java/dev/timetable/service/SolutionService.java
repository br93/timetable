package dev.timetable.service;

import org.springframework.stereotype.Service;

import dev.timetable.domain.timetable.Solution;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.rabbitmq.Producer;
import dev.timetable.repository.SolutionRepository;
import dev.timetable.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final MessageUtil messageUtil;
    private final Producer producer;

    public Solution createSolution(Solution solution, String token, String author) {
        Solution newSolution = this.solutionRepository.save(solution);
        producer.send(this.messageUtil.eventMessage(newSolution.getId(), token, author));
        return newSolution;
    }

    public Solution findSolutionById(String id) {
        return this.solutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.notFoundMessage("Solution", "id", id)));
    }
}

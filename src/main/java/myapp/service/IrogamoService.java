package myapp.service;

import myapp.dto.*;
import myapp.entity.*;
import myapp.exception.TaskNotFindException;
import myapp.featureorigami.OrigamiResponseDto;
import myapp.featureorigami.TaskQueryDto;
import myapp.featurework.WorkDoneDto;
import myapp.repository.TaskRepository;
import myapp.repository.UserRepository;
import myapp.repository.WorkDoneRepository;
import myapp.featuretask.TaskDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IrogamoService {
    private final WorkDoneRepository workDoneRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public IrogamoService(WorkDoneRepository workDoneRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.workDoneRepository = workDoneRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public WorkDone save(WorkDoneDto workDoneDto) {
        // get user from context
        IrogamoUser user = (IrogamoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Si des taches existent, on recupère la plus récente.
        Set<Task> tasks = taskRepository.findByCategoryAndProjectAndLabel(workDoneDto.getCategory(), workDoneDto.getProject(), workDoneDto.getLabel());
        Optional<Task> recentTask = tasks.stream().max(Comparator.comparing(Task::getCreation));
        if (recentTask.isPresent()) {
            WorkDuration duration = new WorkDuration();
            duration.setCreation(workDoneDto.getCreation() != null ? workDoneDto.getCreation() : LocalDate.now());
            duration.setDuration(workDoneDto.getDuration());
            duration.setDescription(workDoneDto.getDescription());
            duration.setBusinessUnit(workDoneDto.getBusinessUnit());
            Optional<WorkDone> workDone = workDoneRepository.findByIrogamoUserAndTask(user, recentTask.get());
            if (workDone.isPresent()) {
                workDone.get().getDurations().add(duration);
                return workDone.get();
            } else {
                WorkDone workDoneNew = new WorkDone();
                workDoneNew.setTask(recentTask.get());
                workDoneNew.setIrogamoUser(user);
                workDoneNew.setDurations(new HashSet<>());
                workDoneNew.getDurations().add(duration);
                return workDoneRepository.save(workDoneNew);
            }
        } else {
            throw new TaskNotFindException("La tache " + workDoneDto.getLabel() + " n'existe pas (pour le couple catégorie/projet : " + workDoneDto.getCategory() + "/" + workDoneDto.getProject() + "). Il faut la créer avant d'y ajouter du temps.");
        }
    }

    @Transactional
    public Task save(TaskDto taskDto) {
        Task task = new Task();
        task.setCategory(taskDto.getCategory());
        task.setCodeOrigami(taskDto.getCodeOrigami());
        task.setCreation(LocalDateTime.now());
        task.setLabel(taskDto.getLabel());
        task.setProject(taskDto.getProject());
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAll() {
        List<Task> all = taskRepository.findAll();
        // On ne prend que les dernieres versions des tasks.
        Map<TaskIdentifier, List<Task>> tasksByIdentifier =
                all.stream().collect(Collectors.groupingBy(task -> new TaskIdentifier(task.getProject(), task.getCategory(), task.getLabel())));
        List<TaskDto> results = new ArrayList<>();
        for (Map.Entry<TaskIdentifier, List<Task>> entry : tasksByIdentifier.entrySet()) {
            entry.getValue().stream().max(Comparator.comparing(Task::getCreation)).map(task -> {
                TaskDto dto = new TaskDto();
                dto.setCategory(task.getCategory());
                dto.setCodeOrigami(task.getCodeOrigami());
                dto.setProject(task.getProject());
                dto.setLabel(task.getLabel());
                return dto;
            }).ifPresent(results::add);
        }
        return results;
    }

    @Transactional
    public List<TaskResponseDto> getTaskStatistics(TaskQueryDto param) {
        List<TaskResponseDto> results = new ArrayList<>();

        // get user
        IrogamoUser user;
        if (param.getUserName() == null) {
            user = (IrogamoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            Optional<IrogamoUser> irogamoUser = userRepository.findByUsername(param.getUserName());
            if (irogamoUser.isPresent()) {
                user = irogamoUser.get();
            } else {
                throw new IllegalArgumentException("Utilisateur " + param.getUserName() + " non trouvé.");
            }
        }
        Set<WorkDone> workDones = workDoneRepository.findByIrogamoUser(user);

        // get works of user
        for (WorkDone workDone : workDones) {
            for (BusinessUnit businessUnit : BusinessUnit.values()) {
                workDone.getDurations().stream()
                        .filter(duration -> businessUnit == duration.getBusinessUnit())
                        .filter(duration -> param.getDateMax() == null || !duration.getCreation().isAfter(param.getDateMax()))
                        .filter(duration -> param.getDateMin() == null || !duration.getCreation().isBefore(param.getDateMin()))
                        .map(WorkDuration::getDuration)
                        .reduce(Integer::sum)
                        .ifPresent(duration -> {
                            TaskResponseDto taskResponseDto = new TaskResponseDto();
                            taskResponseDto.setDurationInMin(duration);
                            taskResponseDto.setTask(workDone.getTask());
                            taskResponseDto.setUserName(user.getUsername());
                            taskResponseDto.setBusinessUnit(businessUnit);
                            results.add(taskResponseDto);
                        });
            }
        }
        return results;
    }

    @Transactional
    public List<OrigamiResponseDto> getOrigamiStatistics(TaskQueryDto param) {
        List<OrigamiResponseDto> origamiResponseDtos =
                getTaskStatistics(param).stream()
                        .map(OrigamiResponseDto::new)
                        .collect(Collectors.toList());

        List<OrigamiResponseDto> results = new ArrayList<>();
        List<String> origamiCodes = origamiResponseDtos.stream().map(OrigamiResponseDto::getOrigami).distinct().collect(Collectors.toList());
        for (String origamiCode : origamiCodes) {
            origamiResponseDtos.stream()
                    .filter(origamiResponseDto -> origamiCode.equals(origamiResponseDto.getOrigami()))
                    .map(OrigamiResponseDto::getDurationInMin)
                    .reduce(Integer::sum)
                    .ifPresent(sum -> {
                        OrigamiResponseDto origamiResponseDto = new OrigamiResponseDto();
                        origamiResponseDto.setDurationInMin(sum);
                        origamiResponseDto.setOrigami(origamiCode);
                        origamiResponseDto.setUserName(origamiResponseDtos.get(0).getUserName());
                        results.add(origamiResponseDto);
                    });
        }
        return results;
    }

    private static class TaskIdentifier {
        private final Project project;
        private final Category category;
        private final String label;

        public TaskIdentifier(Project project, Category category, String label) {
            this.project = project;
            this.category = category;
            this.label = label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TaskIdentifier that = (TaskIdentifier) o;

            if (!project.equals(that.project)) return false;
            if (!category.equals(that.category)) return false;
            return label.equals(that.label);
        }

        @Override
        public int hashCode() {
            int result = project.hashCode();
            result = 31 * result + category.hashCode();
            result = 31 * result + label.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "TaskIdentifier{" +
                    "project='" + project + '\'' +
                    ", category='" + category + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

}

package myapp.featurework;

import myapp.dto.TaskResponseDto;
import myapp.entity.BusinessUnit;
import myapp.entity.Category;
import myapp.entity.Project;
import myapp.exception.TaskNotFindException;
import myapp.featureorigami.TaskQueryDto;
import myapp.featuretask.TaskDto;
import myapp.service.IrogamoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class WorkController {

    private final IrogamoService irogamoService;

    public WorkController(IrogamoService irogamoService) {
        this.irogamoService = irogamoService;
    }

    private LocalDate startDate;
    private LocalDate endDate;

    @RequestMapping(value = {"/work-list"}, method = RequestMethod.GET)
    public String workList(Model model) {
        // si les dates ne sont pas définies, on prend comme période la journée en cours
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();
        }
        TaskQueryDto taskQueryDto = new TaskQueryDto();
        taskQueryDto.setDateMin(startDate);
        taskQueryDto.setDateMax(endDate);
        model.addAttribute("taskQueryDto", taskQueryDto);

        List<TaskResponseDto> tasksResponses = irogamoService.getTaskStatistics(taskQueryDto).stream()
                .sorted(Comparator.comparing(TaskResponseDto::getDurationInMin))
                .collect(Collectors.toList());
        model.addAttribute("tasksResponses", tasksResponses);
        return "work-list";
    }

    @RequestMapping(value = {"/work-list"}, method = RequestMethod.POST)
    public String changePeriod(Model model, @ModelAttribute("taskQueryDto") TaskQueryDto taskQueryDto) {
        startDate = taskQueryDto.getDateMin();
        endDate = taskQueryDto.getDateMax();
        return "redirect:/work-list";
    }

    @RequestMapping(value = {"/add-work"}, method = RequestMethod.GET)
    public String showAddWorkPage(Model model) {
        WorkDoneDto workForm = new WorkDoneDto();
        model.addAttribute("workForm", workForm);

        // Récupération des labels pour initialiser liste déroulante dans le front
        List<String> existingLabels = irogamoService.findAll().stream().map(TaskDto::getLabel).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        model.addAttribute("existingLabels", existingLabels);
        return "add-work";
    }

    @RequestMapping(value = {"/add-work"}, method = RequestMethod.POST)
    public String addTask(Model model,
                          @ModelAttribute("workForm") WorkDoneDto workForm) {
        Category category = workForm.getCategory();
        Project project = workForm.getProject();
        String label = workForm.getLabel();
        BusinessUnit businessUnit = workForm.getBusinessUnit();
        Integer duration = workForm.getDuration();

        if (category == null
                || project == null
                || businessUnit == null
                || duration == null
                || label == null) {
            model.addAttribute("errorMessage", "Tous les champs à l'exception de date et description sont requis pour l'ajout d'un nouveau travail.");
            return "add-work";
        }

        try {
            irogamoService.save(workForm);
        } catch (TaskNotFindException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-work";
        }
        return "redirect:/work-list";
    }

}

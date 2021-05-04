package myapp.featuretask;

import myapp.entity.Category;
import myapp.entity.Project;
import myapp.service.IrogamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private final IrogamoService irogamoService;

    public TaskController(IrogamoService irogamoService) {
        this.irogamoService = irogamoService;
    }

    @RequestMapping(value = {"/task-list"}, method = RequestMethod.GET)
    public String taskList(Model model) {
        List<TaskDto> tasks = irogamoService.findAll().stream()
                .sorted((o1, o2) -> {
                    if (!o1.getCategory().equals(o2.getCategory())) {
                        return o1.getCategory().compareTo(o2.getCategory());
                    } else if (!o1.getProject().equals(o2.getProject())) {
                        return o1.getProject().compareTo(o2.getProject());
                    } else {
                        return o1.getLabel().compareTo(o2.getLabel());
                    }
                })
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @RequestMapping(value = {"/add-task"}, method = RequestMethod.GET)
    public String showAddTaskPage(Model model) {
        TaskDto taskForm = new TaskDto();
        model.addAttribute("taskForm", taskForm);
        return "add-task";
    }

    @RequestMapping(value = {"/add-task"}, method = RequestMethod.POST)
    public String addTask(Model model,
                          @ModelAttribute("taskForm") TaskDto taskForm) {

        Category category = taskForm.getCategory();
        Project project = taskForm.getProject();
        String label = taskForm.getLabel();
        String codeOrigami = taskForm.getCodeOrigami();

        if (category != null
                && project != null
                && label != null && label.length() > 0
                && codeOrigami != null && codeOrigami.length() > 0) {

            irogamoService.save(taskForm);

            return "redirect:/task-list";
        }

        model.addAttribute("errorMessage", "Tous les champs sont requis pour l'ajout d'une nouvelle tâche.");
        return "add-task";
    }

}

package myapp.featureorigami;

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
public class OrigamiController {

    private final IrogamoService irogamoService;

    public OrigamiController(IrogamoService irogamoService) {
        this.irogamoService = irogamoService;
    }

    private LocalDate startDate;
    private LocalDate endDate;

    @RequestMapping(value = {"/origami-list"}, method = RequestMethod.GET)
    public String origamiList(Model model) {

        // si les dates ne sont pas définies, on prend comme période le mois en cours
        if (startDate == null || endDate == null) {
            LocalDate initial = LocalDate.now();
            startDate = initial.withDayOfMonth(1);
            endDate = initial.withDayOfMonth(initial.lengthOfMonth());
        }
        TaskQueryDto taskQueryDto = new TaskQueryDto();
        taskQueryDto.setDateMin(startDate);
        taskQueryDto.setDateMax(endDate);
        model.addAttribute("taskQueryDto", taskQueryDto);

        List<OrigamiResponseDto> origamiResponses = irogamoService.getOrigamiStatistics(taskQueryDto).stream()
                .sorted(Comparator.comparing(OrigamiResponseDto::getDurationInMin))
                .collect(Collectors.toList());
        model.addAttribute("origamiResponses", origamiResponses);
        return "origami-list";
    }

    @RequestMapping(value = {"/origami-list"}, method = RequestMethod.POST)
    public String changePeriod(Model model, @ModelAttribute("taskQueryDto") TaskQueryDto taskQueryDto) {
        startDate = taskQueryDto.getDateMin();
        endDate = taskQueryDto.getDateMax();
        return "redirect:/origami-list";
    }

}

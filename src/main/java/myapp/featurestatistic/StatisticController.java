package myapp.featurestatistic;

import myapp.entity.IrogamoUser;
import myapp.featureorigami.TaskQueryDto;
import myapp.service.IrogamoService;
import myapp.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class StatisticController {

    private final IrogamoService irogamoService;
    private final UserService userService;

    public StatisticController(IrogamoService irogamoService, UserService userService) {
        this.irogamoService = irogamoService;
        this.userService = userService;
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;
    private List<String> existingUsers = new ArrayList<>();

    private List<StatisticDto> statisticResponses;

    @RequestMapping(value = {"/statistic-list"}, method = RequestMethod.GET)
    public String statisticList(Model model) {

        if(existingUsers.isEmpty()) {
            existingUsers.add("All");
            existingUsers.addAll(userService.getUsers().stream()
                    .map(IrogamoUser::getUsername)
                    .collect(Collectors.toList()));
        }

        // si les dates ne sont pas définies, on prend comme période le mois en cours
        if (startDate == null || endDate == null) {
            LocalDate initial = LocalDate.now();
            startDate = initial.withDayOfMonth(1);
            endDate = initial.withDayOfMonth(initial.lengthOfMonth());
        }

        TaskQueryDto taskQueryDto = new TaskQueryDto();
        taskQueryDto.setDateMin(startDate);
        taskQueryDto.setDateMax(endDate);
        taskQueryDto.setUserName(userName);
        model.addAttribute("taskQueryDto", taskQueryDto);
        model.addAttribute("existingUsers", existingUsers);

        statisticResponses = irogamoService.getFullStatistics(taskQueryDto);
        model.addAttribute("statisticResponses", statisticResponses);
        return "statistic-list";
    }

    @RequestMapping(value = {"/statistic-list"}, method = RequestMethod.POST)
    public String changePeriod(Model model, @ModelAttribute("taskQueryDto") TaskQueryDto taskQueryDto) {
        startDate = taskQueryDto.getDateMin();
        endDate = taskQueryDto.getDateMax();
        userName = taskQueryDto.getUserName();
        return "redirect:/statistic-list";
    }

    @GetMapping("/stats/csv2")
    public void getStatsCsv(HttpServletResponse response) throws IOException {
        List<StatisticDto> dtos = statisticResponses;

        response.setContentType("text/csv; charset=Iso-8859-1");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+"export_works.csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        String[] csvHeader = {"username", "date", "category", "project", "label", "bu", "codeOrigami", "description", "duration"};
        String[] nameMapping = {"username", "date", "category", "project", "label", "bu", "codeOrigami", "description", "duration"};

        csvWriter.writeHeader(csvHeader);

        for (StatisticDto dto : dtos) {
            csvWriter.write(dto, nameMapping);
        }
        csvWriter.close();
    }

}

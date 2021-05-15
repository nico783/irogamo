package myapp.rest;

import myapp.dto.*;
import myapp.entity.Task;
import myapp.entity.WorkDone;
import myapp.featureorigami.OrigamiResponseDto;
import myapp.featureorigami.TaskQueryDto;
import myapp.featurestatistic.StatisticDto;
import myapp.featurework.WorkDoneDto;
import myapp.service.IrogamoService;
import myapp.featuretask.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class IrogamoController {

    private final Logger logger = LoggerFactory.getLogger(IrogamoController.class);

    private final IrogamoService irogamoService;

    public IrogamoController(IrogamoService irogamoService) {
        this.irogamoService = irogamoService;
    }

    @GetMapping("/my-origami")
    public ResponseEntity<List<OrigamiResponseDto>> getOrigami(TaskQueryDto param) {
        return new ResponseEntity<>(irogamoService.getOrigamiStatistics(param), HttpStatus.OK);
    }

    @GetMapping("/my-works")
    public ResponseEntity<List<TaskResponseDto>> getWorks(TaskQueryDto param) {
        return new ResponseEntity<>(irogamoService.getTaskStatistics(param), HttpStatus.OK);
    }

    @PostMapping("/works")
    public ResponseEntity<WorkDone> saveWorks(WorkDoneDto workDoneDto) {
        return new ResponseEntity<>(irogamoService.save(workDoneDto), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return new ResponseEntity<>(irogamoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> saveTasks(TaskDto taskDto) {
        return new ResponseEntity<>(irogamoService.save(taskDto), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatisticDto>> getStats(TaskQueryDto param) {
        return new ResponseEntity<>(irogamoService.getFullStatistics(param), HttpStatus.OK);
    }

    @GetMapping("/stats/csv")
    public void getStatsCsv(TaskQueryDto param, HttpServletResponse response) throws IOException {
        List<StatisticDto> dtos = irogamoService.getFullStatistics(param);

        response.setContentType("text/csv; charset=UTF-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+"export_works.csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        String[] csvHeader = {"username", "date", "category", "project", "bu", "codeOrigami", "description", "duration"};
        String[] nameMapping = {"username", "date", "category", "project", "bu", "codeOrigami", "description", "duration"};

        csvWriter.writeHeader(csvHeader);

        for (StatisticDto dto : dtos) {
            csvWriter.write(dto, nameMapping);
        }
        csvWriter.close();
    }
}

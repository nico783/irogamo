package myapp.rest;

import myapp.dto.*;
import myapp.entity.Task;
import myapp.entity.WorkDone;
import myapp.featureorigami.OrigamiResponseDto;
import myapp.featureorigami.TaskQueryDto;
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
}

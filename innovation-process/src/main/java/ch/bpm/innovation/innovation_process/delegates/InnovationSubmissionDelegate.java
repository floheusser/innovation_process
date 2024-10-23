package ch.bpm.innovation.innovation_process.delegates;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.bpm.innovation.innovation_process.service.PointsService;

@Component
public class InnovationSubmissionDelegate implements JavaDelegate {

    @Autowired
    private PointsService pointsService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Get the task service and identity service
        Task task = execution.getProcessEngineServices().getTaskService().createTaskQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .list().get(0);

        String assignee = task.getAssignee();


        // Get the identity service
        IdentityService identityService = execution.getProcessEngineServices().getIdentityService();
        User user = identityService.createUserQuery().userId(assignee).singleResult();

        // Retrieve email address of the assignee
        String employeeId = user.getId();

        // 30 Punkte für das Genehmigen der Innovation vergeben
        pointsService.addPoints(employeeId, 10);

        System.out.println("10 Punkte wurden für das Einreichen der Innovation hinzugefügt.");
    }
}

package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.RegionState;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Service.EmployeeService;
import ru.rut.telegram.Service.WorkRegionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class EndWorkCommand implements BotCommand {

    private final EmployeeService employeeService;
    private final WorkRegionService workRegionService;

    public EndWorkCommand(EmployeeService employeeService, WorkRegionService workRegionService) {
        this.employeeService = employeeService;
        this.workRegionService = workRegionService;
    }

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);

        employee.getWorkList().sort(Comparator.comparing(Work::getWorkDate));
        Work lastWork = employee.getWorkList().get(0);

        if (lastWork.getStartDate() == null) {
            sendMessage.setText("Нельзя закрыть не открытую смену");
        } else {
            List<WorkRegion> workRegions = workRegionService.getByWork(lastWork);
            if (workRegions.stream().anyMatch(workRegion -> workRegion.getState() == null)) {
                sendMessage.setText("Нельзя закрыть смену, не внеся информацию по всем участкам");
            }
            for (int i = 0; i < workRegions.size(); i++) {
                if (workRegions.get(i).getState() != RegionState.OK) {
                    sendMessage.setText("Нельзя закрыть смену, не завершив работу на всех участках");
                } else {
                    lastWork.setEndDate(new Date());
                    sendMessage.setText("Смена закрыта.");
                }
            }
        }

        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/end_work";
    }
}

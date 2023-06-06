package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Service.EmployeeServiceImpl;
import ru.rut.telegram.Service.WorkRegionServiceImpl;
import ru.rut.telegram.Service.WorkService;
import ru.rut.telegram.Service.WorkServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkRequest implements BotCommand {
    private String adminChatId = "5768266930";
    private final WorkServiceImpl workService;
    private final WorkRegionServiceImpl workRegionService;

    public WorkRequest(WorkServiceImpl workService, WorkRegionServiceImpl workRegionService) {
        this.workService = workService;
        this.workRegionService = workRegionService;
    }

    @Override
    public List<SendMessage> runCommand(Message message) {
        SendMessage employeeMessage = new SendMessage();
        employeeMessage.setChatId(adminChatId);

        String login = message.getChat().getUserName();
        List<Work> myWork = workService.getEmployeeWork(login);

        if (myWork.isEmpty()) {
            employeeMessage.setText("Запрос рабочей смены от "
                    + message.getChat().getUserName());
        } else {
            employeeMessage.setText("Сформируйте расписание для "
                    + message.getChat().getUserName());
          }
        List<SendMessage> messages = new ArrayList<>();
        messages.add(employeeMessage);

        return messages;
    }

    @Override
    public String supportedCommand() {
        return null;
    }
}

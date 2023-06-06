package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Service.*;

import java.util.*;

@Service
public class MyWorkRegionsCommand implements BotCommand {
    private final WorkRegionServiceImpl workRegionService;
    private final EmployeeServiceImpl employeeService;

    public MyWorkRegionsCommand(WorkRegionServiceImpl workRegionService, EmployeeServiceImpl employeeService) {
        this.workRegionService = workRegionService;
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {
        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        List<WorkRegion> employeeWork = workRegionService.getByWork(employee.getWorkList().get(0));

        if (employeeWork.isEmpty()) {
            sendMessage.setText("""
                    Нет работы в регионах, запросите смену! Запросить смены - /request_work
                    """);
        } else {
            for (int i = 0; i < employee.getWorkList().size(); i++) {
                employeeWork = workRegionService.getByWork(employee.getWorkList().get(i));
                sendMessage.setText(employeeWork.toString().replaceAll("^\\[|\\]$", ""));
            }
        }
        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/my_work_regions";
    }
}


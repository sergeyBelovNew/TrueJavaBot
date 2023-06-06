package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Service.EmployeeServiceImpl;
import ru.rut.telegram.Service.WorkServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class StartWorkCommand implements BotCommand {
    private final WorkServiceImpl workService;

    private final EmployeeServiceImpl employeeService;

    public StartWorkCommand(WorkServiceImpl workService, EmployeeServiceImpl employeeService) {
        this.workService = workService;
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);


        LocalDate date = LocalDate.now();
        Work startedWork = employee.getWorkList().get(employee.getWorkList().size() - 1);;

        if (startedWork.getStartDate() != null) {
            sendMessage.setText("Смена уже открыта");
        } else {
            startedWork.setStartDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            sendMessage.setText("Выполнено открытие смены за дату: " + startedWork.getStartDate());
        }

        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return null;
    }
}

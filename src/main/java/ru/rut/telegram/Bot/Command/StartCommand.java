package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Service.EmployeeService;
import ru.rut.telegram.Service.RegionServiceImpl;
import ru.rut.telegram.Service.WorkRegionServiceImpl;
import ru.rut.telegram.Service.WorkService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StartCommand implements BotCommand {

    private final EmployeeService employeeService;

    private final WorkService workService;
    private final RegionServiceImpl regionService;
    private final WorkRegionServiceImpl workRegionService;

    public StartCommand(EmployeeService employeeService, WorkService workService, RegionServiceImpl regionService, WorkRegionServiceImpl workRegionService) {
        this.employeeService = employeeService;
        this.workService = workService;
        this.regionService = regionService;
        this.workRegionService = workRegionService;
    }

    @Transactional
    public List<SendMessage> runCommand(Message message) {
        List<SendMessage> messageList = new ArrayList<>();
        messageList.add(new SendMessage(message.getChat().getId().toString(), "Привет"));
        messageList.add(new SendMessage(message.getChat().getId().toString(), "Ваш логин: "
                + message.getChat().getUserName()));
        messageList.add(new SendMessage(message.getChat().getId().toString(), "Ваш chatId: "
                + message.getChatId()));

        boolean exists = employeeService.checkEmployee(message.getChat().getUserName());
        if (!exists) {
            Employee employee = new Employee(message.getChat().getUserName(), message.getChatId().toString(), false);
            employeeService.create(employee);
            workService.create(employee);
            regionService.create();
        }

        return messageList;
    }

    @Override
    public String supportedCommand() {
        return "/start";
    }
}

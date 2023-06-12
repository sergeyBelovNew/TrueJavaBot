package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Service.*;

import java.util.*;

@Service
public class AllRegionsCommand implements BotCommand {
    private final WorkRegionServiceImpl workRegionService;
    private final EmployeeServiceImpl employeeService;

    private final RegionServiceImpl regionService;

    public AllRegionsCommand(WorkRegionServiceImpl workRegionService, EmployeeServiceImpl employeeService, RegionServiceImpl regionService) {
        this.workRegionService = workRegionService;
        this.employeeService = employeeService;
        this.regionService = regionService;
    }

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {
        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        List<Region> regions = regionService.getAllRegions();
        if (regions.isEmpty()) {
            sendMessage.setText("""
                    Нет работы в регионах, запросите смену! Запросить смены - /request_work
                    """);
        } else {
            sendMessage.setText(regions.toString().replaceAll("^\\[|\\]$", ""));
        }
        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/my_work_regions";
    }
}


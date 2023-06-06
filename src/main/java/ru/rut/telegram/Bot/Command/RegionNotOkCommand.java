package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.RegionState;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Service.EmployeeService;
import ru.rut.telegram.Service.WorkRegionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class RegionNotOkCommand implements BotCommand{

    private final EmployeeService employeeService;
    private final WorkRegionService workRegionService;

    public RegionNotOkCommand(EmployeeService employeeService, WorkRegionService workRegionService) {
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


        Work lastWork = employee.getWorkList().get(0);

        List<WorkRegion> regionData = workRegionService.getByWork(lastWork);
        regionData.sort(Comparator.comparing(workRegion -> workRegion.getRegion().getNumber()));

        WorkRegion regionToChangeState = regionData.get(0);
        regionToChangeState.setState(RegionState.NOT_OK);
        workRegionService.save(regionToChangeState);

        List<WorkRegion> regionsWithoutState = new ArrayList<>(regionData.stream().filter(region -> region.getState() == null).toList());
        regionsWithoutState.sort(Comparator.comparing(region -> region.getRegion().getNumber()));

        sendMessage.setText(buildResponse(sendMessage, regionsWithoutState, regionToChangeState.getRegion().getNumber()));

        return Collections.singletonList(sendMessage);
    }

    private String buildResponse(SendMessage sendMessage, List<WorkRegion> regions, Integer changed) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Изменен статус для участка: №").append(changed).append("\n");
        if (!regions.isEmpty()) {
            stringBuilder.append("Следующий участок: #").append(regions.get(0));
        } else {
            stringBuilder.append("Участок разотмечен");
        }


        return stringBuilder.toString();
    }



    @Override
    public String supportedCommand() {
        return "/region_ok";
    }
}

package ru.rut.telegram.Bot.Command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Repo.WorkRegionRepo;
import ru.rut.telegram.Repo.WorkRepo;
import ru.rut.telegram.Service.EmployeeService;
import ru.rut.telegram.Service.WorkRegionService;
import ru.rut.telegram.Service.WorkService;

import java.util.*;

@Service
public class MyWorkCommand implements BotCommand {
    private final WorkService workService;

    public MyWorkCommand(WorkService workService, EmployeeService employeeService,
                         WorkRepo workRepo, WorkRegionService workRegionService, WorkRegionRepo workRegionRepo) {
        this.workService = workService;
    }

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {
        String login = message.getChat().getUserName();
        List<Work> myWork = workService.getEmployeeWork(login);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        if (myWork.isEmpty()) {
            sendMessage.setText("""
                    Нет назначенных смен! Запросить смены - /request_work
                    """);
        } else {

            sendMessage.setText(myWork.toString().replaceAll("^\\[|\\]$", ""));
        }

        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/my_work";
    }
}

package ru.rut.telegram.Bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.rut.telegram.Bot.Command.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    final private String botToken;
    @Value("${bot.name}")
    final private String botName;
    private final StartCommand startCommand;
    final List<SendMessage> messageList = new ArrayList<>();
    private final MyWorkCommand myWorkCommand;
    private final WorkRequest workRequestCommand;
    private final StartWorkCommand startWorkCommand;
    private final RegionOkCommand regionOkCommand;
    private final RegionNotOkCommand regionNotOkCommand;
    private final EndWorkCommand endWorkCommand;
    private final MyWorkRegionsCommand myWorkRegionsCommand;
    private final Help runHelp;

    @Autowired
    public TelegramBot(TelegramBotsApi telegramBotsApi,
                       @Value("${bot.token}") String token,
                       @Value("${bot.name}") String name,
                       StartCommand startCommand,
                       MyWorkCommand myWorkCommand,
                       WorkRequest workRequestCommand,
                       StartWorkCommand startWorkCommand,
                       RegionOkCommand regionOkCommand,
                       RegionNotOkCommand regionNotOkCommand,
                       EndWorkCommand endWorkCommand,
                       MyWorkRegionsCommand myWorkRegionsCommand,
                       Help runHelp

    ) throws TelegramApiException {
        this.botToken = token;
        this.botName = name;
        this.startCommand = startCommand;
        this.myWorkCommand = myWorkCommand;
        this.workRequestCommand = workRequestCommand;
        this.startWorkCommand = startWorkCommand;
        this.regionOkCommand = regionOkCommand;
        this.regionNotOkCommand = regionNotOkCommand;
        this.endWorkCommand = endWorkCommand;
        this.myWorkRegionsCommand = myWorkRegionsCommand;
        this.runHelp = runHelp;
        telegramBotsApi.registerBot(this);
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        String text = update.getMessage().getText();
        switch (text) {
            case "/start":
                List<SendMessage> sendMessage = startCommand.runCommand(message);
                for (SendMessage message1 : sendMessage) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/my_work":
                List<SendMessage> sendMessage1 = myWorkCommand.runCommand(message);
                for (SendMessage message1 : sendMessage1) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/my_work_regions":
                List<SendMessage> sendMessage2 = myWorkRegionsCommand.runCommand(message);
                for (SendMessage message1 : sendMessage2) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/request_work":
                List<SendMessage> sendMessage3 = workRequestCommand.runCommand(message);
                for (SendMessage message1 : sendMessage3) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/start_work":
                List<SendMessage> sendMessage4 = startWorkCommand.runCommand(message);
                for (SendMessage message1 : sendMessage4) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/region_not_ok":
                List<SendMessage> sendMessage5 = regionNotOkCommand.runCommand(message);
                for (SendMessage message1 : sendMessage5) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/region_ok":
                List<SendMessage> sendMessage6 = regionOkCommand.runCommand(message);
                for (SendMessage message1 : sendMessage6) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/end_work":
                List<SendMessage> sendMessage7 = endWorkCommand.runCommand(message);
                for (SendMessage message1 : sendMessage7) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "/help":
                List<SendMessage> sendMessage8 = runHelp.getCommandList(message);
                for (SendMessage message1 : sendMessage8) {
                    try {
                        execute(message1);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            default:
                SendMessage sendMessage0 = new SendMessage(message.getChat().getId().toString(),
                        "Команда не распознана. " +
                                "Если хотите увидеть весь список доступных команд, то воспользуйтесь командой /help ");
                break;

        }

        System.out.println(update);
    }

    public void messageTimeOut(List<SendMessage> sendMessage) {
        for (SendMessage message1 : sendMessage) {
            try {
                execute(message1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

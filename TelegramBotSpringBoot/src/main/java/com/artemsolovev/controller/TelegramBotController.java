package com.artemsolovev.controller;

import com.artemsolovev.model.Compliment;
import com.artemsolovev.model.ComplimentUser;
import com.artemsolovev.model.HistoryUser;
import com.artemsolovev.service.ComplimentService;
import com.artemsolovev.service.ComplimentUserService;
import com.artemsolovev.service.HistoryUserService;
import com.artemsolovev.service.UserService;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@BotController
public class TelegramBotController implements TelegramMvcController {

    @Value("${bot.token}")
    private String token;
    private Keyboard replyKeyboardMarkup;
    private Keyboard replyKeyboardMarkupRegistered;
    private UserService userService;
    private ComplimentService complimentService;
    private ComplimentUserService complimentUserService;
    private HistoryUserService historyUserService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setComplimentService(ComplimentService complimentService) {
        this.complimentService = complimentService;
    }

    @Autowired
    public void setComplimentUserService(ComplimentUserService complimentUserService){
        this.complimentUserService = complimentUserService;
    }

    @Autowired
    public void setHistoryUserService(HistoryUserService historyUserService) {
        this.historyUserService = historyUserService;
    }

    @PostConstruct
    public void init(){
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup(
                "/register")
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);
    }

    @PostConstruct
    public void initRegistered(){
        this.replyKeyboardMarkupRegistered = new ReplyKeyboardMarkup(
                "/next", "/all", "/photos")
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);
    }

    @Override
    public String getToken() {
        return this.token;
    }

    private SendMessage sendMessageWithButtons(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.replyMarkup(replyKeyboardMarkup);
        return sendMessage.parseMode(ParseMode.HTML);
    }

    private SendMessage sendMessageWithButtonsRegistered(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.replyMarkup(replyKeyboardMarkupRegistered);
        return sendMessage.parseMode(ParseMode.HTML);
    }

    private SendMessage sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        return sendMessage.parseMode(ParseMode.HTML);
    }

    private SendPhoto sendImageWithButtons(long chatId, String fileName) {
        File file = new File("photos\\" + fileName);
        if(!file.exists()){
            throw new IllegalArgumentException();
        }
        SendPhoto sendPhoto = new SendPhoto(chatId, file);
        sendPhoto.replyMarkup(replyKeyboardMarkupRegistered);
        return sendPhoto.parseMode(ParseMode.HTML);
    }

    @BotRequest(value = "/start", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest start(User user, Chat chat) {
        //TODO handle chatId
        String username = user.username();
        long chatId = chat.id();
        System.out.println(chatId);
        if (userService.get(user.username()) == null) {
            userService.add(new com.artemsolovev.model.User(username, chatId));
            return sendMessageWithButtons(chatId, "Добро пожаловать! Пройдите форму регистрации.");
        } else if (userService.get(user.username()).getAge() == null){
            historyUserService.add(new HistoryUser("/start", userService.get(user.username())));
            return sendMessageWithButtons(chatId, "Добро пожаловать! Пройдите форму регистрации.");
        } else {
            historyUserService.add(new HistoryUser("/start", userService.get(user.username())));
            return sendMessageWithButtonsRegistered(chatId, "Добро пожаловать!");
        }
    }

    @BotRequest(value = "/register", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest startRegistration(User user, Chat chat) {
        if (userService.get(user.username()) == null) {
            return sendMessage(chat.id(), "Для начала работы бота, введите команду /start.");
        }
        com.artemsolovev.model.User userDB = userService.get(user.username());
        historyUserService.add(new HistoryUser("/register", userDB));
        if (userDB.getLogin() == null)
            return login(user, chat);
        if (userDB.getName() == null)
            return name(user, chat);
        if (userDB.getAge() == null)
            return age(user, chat);
        return sendMessageWithButtonsRegistered(chat.id(), "Вы успешно зарегистровались, повторная регистрация не нужна.");
    }

    @BotRequest(value = "/next", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest next(User user, Chat chat) {
        if (userService.get(user.username()) == null) {
            return sendMessage(chat.id(), "Для начала работы бота, введите команду /start.");
        }
        com.artemsolovev.model.User userDB = userService.get(user.username());
        historyUserService.add(new HistoryUser("/next", userDB));
        if (userDB.getAge() == null) {
            return sendMessageWithButtons(chat.id(), "Для использования этой команды, вам нужно зарегистрироваться.");
        }
        Compliment compliment = this.complimentService.getRandom(userDB.getId());
        complimentUserService.add(new ComplimentUser(compliment.getText(), userDB));

        return sendMessageWithButtonsRegistered(chat.id(), compliment.getText());
    }

    @BotRequest(value = "/all", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest all(User user, Chat chat) {
        if (userService.get(user.username()) == null) {
            return sendMessage(chat.id(), "Для начала работы бота, введите команду /start.");
        }
        com.artemsolovev.model.User userDB = userService.get(user.username());
        historyUserService.add(new HistoryUser("/all", userDB));
        if (userDB.getAge() == null) {
            return sendMessageWithButtons(chat.id(), "Для использования этой команды, вам нужно зарегистрироваться.");
        }
        List<Compliment> complimentList = this.complimentService.get();
        StringBuilder stringBuilder = new StringBuilder();
        for (Compliment compliment : complimentList) {
            stringBuilder.append(compliment.getText()).append("\n");
        }
        return sendMessageWithButtonsRegistered(chat.id(), stringBuilder.toString());
    }

    @BotRequest(value = "/photos", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest photos(User user, Chat chat) {
        if (userService.get(user.username()) == null) {
            return sendMessage(chat.id(), "Для начала работы бота, введите команду /start.");
        }
        com.artemsolovev.model.User userDB = userService.get(user.username());
        historyUserService.add(new HistoryUser("/photos", userDB));
        if (userDB.getAge() == null) {
            return sendMessageWithButtons(chat.id(), "Для использования этой команды, вам нужно зарегистрироваться.");
        }
        this.userService.addStep("photos", userDB);
        File directory = new File("photos");
        File[] files = directory.listFiles();
        String collect = Arrays.stream(files).map(File::getName).collect(Collectors.joining("\n"));
        return sendMessageWithButtonsRegistered(chat.id(), collect);
    }

    @BotRequest(type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest login(User user, Chat chat) {
        this.userService.addStep("login", userService.get(user.username()));
        return sendMessage(chat.id(), "Введите ваш логин.");
    }

    @BotRequest(type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest name(User user, Chat chat) {
        this.userService.addStep("name", userService.get(user.username()));
        return sendMessage(chat.id(), "Введите ваше имя.");
    }

    @BotRequest(type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest age(User user, Chat chat) {
        this.userService.addStep("age", userService.get(user.username()));
        return sendMessage(chat.id(), "Введите ваш возраст.");
    }

    @BotRequest(value = "{message:[\\S ]+}", type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest message(@BotPathVariable("message") String text, User user, Chat chat) {
        if (userService.get(user.username()) == null) {
            return sendMessage(chat.id(), "Для начала работы бота, введите команду /start.");
        }
        com.artemsolovev.model.User userDB = userService.get(user.username());
        if(userDB.getStep() == null) {
            historyUserService.add(new HistoryUser(text, userDB));
            return sendMessage(chat.id(), "Ваше сообщение: " + text);
        }
        if (userDB.getStep().equals("login")) {
            this.userService.addLogin(text, userDB);
            this.userService.addStep("name", userDB);
            historyUserService.add(new HistoryUser(text, userDB));
            return sendMessage(chat.id(), "Ваш логин: " + text + "\n\nВведите ваше имя.");
        } else if (userDB.getStep().equals("name")) {
            this.userService.addStep("age", userDB);
            this.userService.addName(text, userDB);
            historyUserService.add(new HistoryUser(text, userDB));
            return sendMessage(chat.id(), "Ваше имя: " + text + "\n\nВведите ваш возраст.");
        } else if (userDB.getStep().equals("age")) {
            this.userService.addStep("", userDB);
            this.userService.addAge(text, userDB);
            historyUserService.add(new HistoryUser(text, userDB));
            return sendMessageWithButtonsRegistered(chat.id(), "Ваш возраст: " + text + "\n\nВы успешно зарегистрированы.");
        } else if (userDB.getStep().equals("photos")) {
            try {
                this.userService.addStep("", userDB);
                historyUserService.add(new HistoryUser(text, userDB));
                return sendImageWithButtons(chat.id(), text);
            } catch (IllegalArgumentException e) {
                return sendMessage(chat.id(), "Файл не найден.");
            }
        }
        historyUserService.add(new HistoryUser(text, userDB));
        return sendMessage(chat.id(), "Ваше сообщение: " + text);
    }
}

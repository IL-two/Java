import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    DB_Users dbUsers = new DB_Users();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        WeatherModel model = new WeatherModel();
        UserModel user = null;
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            user = getUser(update.getMessage().getFrom());
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, user.toString());
                    try {
                        dbUsers.addUser(user);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/help":
                    sendMsg(message, helpText());
                    break;
                case "/setting":
                    sendMsg(message, "Опа!"); //Произвести поиск по юзеру и найти его подписки
                    break;
                default:
                    try {
                        execute(SubscribeCity(message.getChatId(), Weather.getWeather(message.getText(), model).toString(), model));
                    } catch (IOException | TelegramApiException | SQLException e) {
                        sendMsg(message, "Такого города нет...\n" +
                                            "Попробуй вести корректрое название.");
                    }
            }
        }
        else if(update.hasCallbackQuery()){
            try {
                user = getUser(update.getCallbackQuery().getFrom());
                subsCity(user, model);
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));

            } catch (TelegramApiException | IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg(Message message, String s) {
        SendMessage sendMessag = new SendMessage();
        sendMessag.enableMarkdown(false);
        sendMessag.setChatId(message.getChatId());
        sendMessag.setText(s);

        try {
            setButtons(sendMessag);
            execute(sendMessag);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();

        keyboardButtons.add(new KeyboardButton("/help"));
        keyboardButtons.add(new KeyboardButton("/setting"));

        keyboardRowList.add(keyboardButtons);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "";
    }

    public String getBotToken() {
        return "";
    }

    public UserModel getUser(User user){
        UserModel newUser = new UserModel();

        newUser.setUserID(user.getId());
        newUser.setUserFirstName(user.getFirstName());
        newUser.setUserLastName(user.getLastName());

        return newUser;
    }

    public SendMessage SubscribeCity(long chtID, String text, WeatherModel model) throws IOException, SQLException {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardSubscruble = new InlineKeyboardButton();
        inlineKeyboardSubscruble.setText("Подписаться");
        inlineKeyboardSubscruble.setCallbackData("Вы подписаны на город " + model.getName());
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardSubscruble);
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chtID).setText(text).setReplyMarkup(inlineKeyboardMarkup);
    }

    public String helpText() {
        return "Что я умею?\n" +
                "Я выдаю погоду по городам со всего света. Введи название города и получишь погоду на данный момент в этом городе.\n" +
                "Ты можешь подписаться на прогноз по этому городу, для этого введи город, получи прогноз и нажми кнопку \'Подписаться\'.\n" +
                "И ты будешь получать каждый день актуальную погоду для выбранного города.\n" +
                "Нажав /setting - увидишь все свои подписки и оможешь отписаться от ненужной информации!\n" +
                "Удачи!";
    }

    public void subsCity(UserModel user, WeatherModel model) throws IOException, SQLException {
        SubscribleCity subscrible = new SubscribleCity();
        subscrible.setUserId(user.getUserID());
        subscrible.setCity(model.getName());
        dbUsers.searchSubscribleUser(subscrible);
    }
}

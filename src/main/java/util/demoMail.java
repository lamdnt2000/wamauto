package util;

import me.shivzee.JMailTM;
import me.shivzee.callbacks.MessageFetchedCallback;
import me.shivzee.exceptions.MessageFetchException;
import me.shivzee.util.JMailBuilder;
import me.shivzee.util.Message;
import me.shivzee.util.Response;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class demoMail {
    public static void main(String[] args) {

        try {
            JMailTM mailer = JMailBuilder.login("muncosggrqrlwcm@scpulse.com","z]-DNPXI");
            mailer.init();
            System.out.println("Email : "+mailer.getSelf().getEmail());

            mailer.fetchMessages(1,new MessageFetchedCallback() {
                @Override
                public void onMessagesFetched(List<Message> list) {
                    for (Message message : list){
                        // All Message Functions
                        System.out.println(CommonUtils.getUrlFromText(message.getContent()));

                    }
                }

                @Override
                public void onError(Response response) {

                }
            });

        }catch (MessageFetchException | LoginException exception){
            System.out.println("Exception Caught "+exception);
        }

    }
}

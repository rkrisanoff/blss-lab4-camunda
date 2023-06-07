package ifmo.blss.service.interfaces;

import ifmo.blss.entity.Message;

public interface SendEmail {
    void sendMail(final Message message);
}

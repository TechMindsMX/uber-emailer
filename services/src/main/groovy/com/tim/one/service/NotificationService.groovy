package com.tim.one.service

import com.tim.one.bean.mail.EmailBean

interface NotificationService {

  void sendNotification(EmailBean bean)

}

package com.tim.one.service

import com.tim.one.bean.mail.MessageBean

interface NotificationService {

  void sendNotification(MessageBean bean)

}

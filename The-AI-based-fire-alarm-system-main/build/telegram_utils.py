import telegram

def send_telegram(photo_path="alert.png"):
    try:
        my_token = "7949419488:AAEaOkQhgZMItJrFoVTHAi0BxX50cbf_Ydw"
        bot = telegram.Bot(token=my_token)
        bot.sendPhoto(chat_id="7511920439", photo=open(photo_path, "rb"), caption="co chay, nguy hiem!")
    except Exception as ex:
        print("Can not send message telegram ", ex)

    print("Send sucess")

send_telegram
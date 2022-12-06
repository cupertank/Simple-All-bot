import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId


fun main() {
    val TOKEN = System.getenv("TOKEN")

    val bot = bot {
        token = TOKEN

        dispatch {
            command("all") {
                val admins =  bot.getChatAdministrators(ChatId.fromId(message.chat.id)).getOrDefault(emptyList())
                val stringBuilder = StringBuilder()

                for (admin in admins) {
                    if (admin.user.isBot || admin.user.id == message.from?.id) continue
                    admin.user.username?.let { username -> stringBuilder.append("@$username ") }
                }

                val replyMessage = stringBuilder.toString()
                if (replyMessage.isNotEmpty()) {
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = replyMessage)
                }
            }
        }
    }

    bot.startPolling()
}

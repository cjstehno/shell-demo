package commands

import com.example.Message
import com.example.ShellDemoApplication
import org.crsh.cli.Argument
import org.crsh.cli.Command
import org.crsh.cli.Option
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.crsh.text.ui.Overflow
import org.crsh.text.ui.UIBuilder

import java.util.concurrent.TimeUnit

@Usage('Interactions with the message of the day.')
class message {

    private static final String FMT = 'MM/dd/yyyy HH:mm:ss'

    @Usage('View the current message of the day.') @Command
    def view(InvocationContext context) {
        def message = findBean(context, ShellDemoApplication).message

        out.print new UIBuilder().table(separator: dashed, overflow: Overflow.HIDDEN, rightCellPadding: 1) {
            header(decoration: bold, foreground: black, background: white) {
                label('Date')
                label('Expires')
                label('Message')
            }

            row {
                label(message.timestamp.format(FMT), foreground: green)
                label(message.timeToLive ? new Date(message.timestamp.time + message.timeToLive).format(FMT) : '-', foreground: white)
                label(message.text, foreground: yellow)
            }
        }
    }

    @Usage('Update the message') @Command
    def update(InvocationContext context,
               @Usage('The new message text') @Argument String messageText,
               @Option(names = ['t', 'time-to-live']) Integer timeToLive,
               @Option(names = ['u', 'units']) TimeUnit unit
    ) {

        Long ttl = null
        if (timeToLive) {
            ttl = TimeUnit.MILLISECONDS.convert(timeToLive, unit ?: TimeUnit.HOURS)
        }

        findBean(context, ShellDemoApplication).message = new Message(
            timestamp: new Date(),
            text: messageText,
            timeToLive: ttl
        )

        view(context)
    }

    private static findBean(InvocationContext context, Class type) {
        context.attributes['spring.beanfactory'].getBean(type)
    }
}
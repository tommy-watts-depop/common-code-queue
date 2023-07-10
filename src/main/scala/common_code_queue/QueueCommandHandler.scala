package common_code_queue

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import collection.mutable.Queue


class QueueCommandHandler(queue: Queue[String]) extends SlashCommandHandler {

  override def apply(req: SlashCommandRequest, ctx: SlashCommandContext) = {
    val commandText = req.getPayload.getText
    val userName = req.getPayload.getUserName

    if (req.getPayload().getResponseUrl() != null) {

      commandText match {
        case "join" =>
          processQueueAction(ctx, queue, userName, join = true)

        case "leave" =>
          processQueueAction(ctx, queue, userName, join = false)

        case _ =>
          ctx.ack("Invalid command. Usage: /common_queue [join|leave]")
      }
    }

    ctx.ack()
  }

  private def processQueueAction(ctx: SlashCommandContext, queue: Queue[String], userName: String, join: Boolean) = {
    join match {
      case true => queue += userName
      case false => queue -= userName
    }
    ctx.respond(CommonCodeQueueMessage(queue).buildMessage())
    ctx.ack()
  }

}






package common_code_queue

import com.slack.api.bolt.context.builtin.ActionContext
import com.slack.api.bolt.handler.builtin.BlockActionHandler
import com.slack.api.bolt.request.builtin.BlockActionRequest
import com.slack.api.bolt.response.Response
import scala.collection.mutable.Queue


class SlackBlockActionHandler(queue: Queue[String]) extends BlockActionHandler {
  override def apply(req: BlockActionRequest, ctx: ActionContext): Response = {

    val userName = req.getPayload.getUser.getName
    val actionId = req.getPayload.getActions().get(0).getActionId

    actionId match {
      case "button_join" => queue += userName
      case "button_leave" => queue -= userName
    }


    if (req.getPayload().getResponseUrl() != null) {
      ctx.respond(CommonCodeQueueMessage(queue).buildMessage())
    }

    ctx.ack()
  }
}
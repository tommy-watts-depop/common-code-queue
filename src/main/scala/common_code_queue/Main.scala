package common_code_queue

import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.bolt.handler.builtin.BlockActionHandler
import com.slack.api.bolt.context.builtin.ActionContext
import com.slack.api.bolt.handler.Handler
import com.slack.api.bolt.request.builtin.BlockActionRequest
import com.slack.api.bolt.response.Response

import scala.collection.mutable.Queue



object Main {
  def main(args: Array[String]): Unit = {

    val queue = new Queue[String]()

    val app = new App()

    app.command("/common_queue", new QueueCommandHandler(queue))

    app.blockAction("button_join", new SlackBlockActionHandler(queue))

    app.blockAction("button_leave", new SlackBlockActionHandler(queue))

    val slackAppServer = new SlackAppServer(app, 3000)
    slackAppServer.start()

    sys.addShutdownHook(() => {
      slackAppServer.stop()
    })
  }
}
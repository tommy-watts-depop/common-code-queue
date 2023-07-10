package common_code_queue

import com.slack.api.model.block.{ActionsBlock, Blocks, DividerBlock, LayoutBlock, SectionBlock}
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.{ButtonElement, BlockElement}

import scala.collection.mutable.Queue

import scala.collection.JavaConverters._


case class CommonCodeQueueMessage(queue: Queue[String]) {

  def buildMessage(): java.util.List[LayoutBlock] = {
    val queueTuples = buildQueueTuples(queue)
    buildBlocks(queueTuples)
  }

  private def buildBlocks(queueTuples: List[(String, String)]): java.util.List[LayoutBlock] = {
    val headerSection = SectionBlock.builder()
      .text(MarkdownTextObject.builder().text("*People in queue*").build())
      .build()

    val divider = DividerBlock.builder().build()

    val peopleInQueueMarkdown = queueTuples.map {
      case (emoji, user) =>
        val markdownText = MarkdownTextObject.builder().text(s"$emoji @$user").build()
        SectionBlock.builder()
          .text(markdownText)
          .build()
    }

    val elements: java.util.List[BlockElement] =
      (List(
          buildButtonElement("Join"),
          buildButtonElement("Leave")
      ): List[BlockElement]).asJava

    val actions = ActionsBlock.builder()
      .blockId("buttons")
      .elements(elements)
      .build()

    val layoutBlocks = (headerSection :: divider :: peopleInQueueMarkdown) :+ actions

    Blocks.asBlocks(layoutBlocks: _*)
  }

  private def buildButtonElement(queueAction: String): ButtonElement = {
    ButtonElement.builder()
      .text(PlainTextObject.builder()
        .text(queueAction)
        .emoji(true)
        .build())
      .value(s"button_${queueAction.toLowerCase}")
      .actionId(s"button_${queueAction.toLowerCase}")
      .build()
  }

  private def buildQueueTuples(queue: Queue[String]): List[(String, String)] = {
    val emojis = List(
      ":first_place_medal:",
      ":two:",
      ":three:",
      ":four:",
      ":five:",
      ":six:",
      ":seven:",
      ":eight:",
      ":nine:",
      ":ten:"
    )

    queue.toList.zipWithIndex.map {
      case (user, index) =>
        val emoji = emojis.lift(index).getOrElse("")
        (emoji, user)
    }
  }
}

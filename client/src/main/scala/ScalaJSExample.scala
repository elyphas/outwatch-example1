package example_outwatch

import cats.effect.IO
import monix.execution.Scheduler.Implicits.global
import outwatch.dom._
import outwatch.dom.dsl._

object ExampleWoutWatch {

  def render: IO[VNode] = for {
                              hdl <- Handler.create[String]
                            } yield {
                              div(
                                label( "Example!" ),
                                input( placeholder := "Introduce your text",
                                  value <-- hdl.map( t => t ),
                                  onChange.target.value.map( t => t) --> hdl
                                ),
                                div( clear.both,
                                  label( "Any event to update the label: ",
                                    hdl.map( t => t )
                                  )
                                )
                              )
                            }


  def main(args: Array[String]): Unit = {

    val app = for {
      r <- render
      _ <- OutWatch.renderInto("#root", r )
    }  yield ()

    app.unsafeRunSync()

  }


}

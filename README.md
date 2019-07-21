# 
run: 

```bash
$sbt
```

`sbt>reStart`

and in the browser `http://localhost:8090`

shows how the Handler works

```scala
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

```
object ExceptionDemo {
  def main(args: Array[String]): Unit = {
    args foreach(arg => contLines(arg))
  }

  def contLines(fileName:String)={
    var source:Option[Source] = None
    try {
      source = Option.apply(Source.fromFile(fileName))
      val size = source.get.getLines().size
      println(s"file $fileName has $size lines")
    } catch {
      //匹配所有的非致命错误 即运行时异常
      case NonFatal(ex) => println(s"ex = ${ex}")
    } finally {
      for (s <- source ) {
        println(s"closring  ${fileName} ...")
        s.close()
      }
    }
  }


  object manage{
    def apply[R <: {def close():Unit},T](resource: => R)(f:R => T) = {
      var res : Option[R] = None
      try {
        res = Some(resource)
        f(res.get)
      } catch {
        case NonFatal(ex) => println(s"ex = ${ex}")
      } finally {
        if (res != None){
          println(s"closing resource .... ")
          res.get.close()
        }
      }
    }
  }
}

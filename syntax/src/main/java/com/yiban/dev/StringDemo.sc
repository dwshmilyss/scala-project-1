object StringDemo{
  val gross = 1000F
  val net = 64f
  val percent = (net/gross)*100
  println(f"$$$gross%.2f vs. $$$net%.2f or $percent%.1f%%")
  println("====================")
  printf("$%.2f vs. $%.2f or %.1f%%\n",gross,net,percent)
//  println("\n")
  val s = "%02d : name = %s".format(5,"dww")
  println("====================")
  println(s)
}
StringDemo
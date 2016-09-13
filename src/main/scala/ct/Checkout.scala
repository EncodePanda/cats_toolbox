package ct

sealed trait Item
object Apple extends Item
object Orange extends Item

object Checkout {

  def totalCost(items: List[Item]): BigDecimal = ???

}

package ct

sealed trait Item
object Apple extends Item
object Orange extends Item

object Item {
  def all: Seq[Item] = List(Apple, Orange)
}

case class Checked(sum: BigDecimal, scanned: Map[Item, Int])

object Checked {
  val ZERO: Checked = Checked(sum = BigDecimal("0.0"), scanned = Map())
  def apply(item: Item): Checked = Checked(sum = Checkout.price(item), scanned = Map(item -> 1))
}

object Checkout {

  def price(item: Item) = item match {
    case Apple => BigDecimal("0.6")
    case Orange => BigDecimal("0.25")
  }

  def totalCost(items: List[Item]): BigDecimal = items.foldLeft(BigDecimal("0.0")) {
    case (acc, item) => acc + price(item)
  }

}

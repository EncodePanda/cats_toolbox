package ct

import cats._
import cats.implicits._

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

  def totalCost(items: List[Item])(implicit monoid: Monoid[Checked]): BigDecimal = {
    val checked = items.foldLeft(monoid.empty) {
      case (acc, item) => acc |+| Checked(item)
    }
    checked.sum
  }
}

object Strategies {
  implicit def step1: Monoid[Checked] = new Monoid[Checked]() {
    def empty = Checked.ZERO
    def combine(c1: Checked, c2: Checked): Checked =
      c1.copy(sum = c1.sum + c2.sum, scanned = c1.scanned |+| c2.scanned)
  }

  implicit def step2: Monoid[Checked] = new Monoid[Checked]() {
    def empty = Checked.ZERO
    def combine(c1: Checked, c2: Checked): Checked = {

      def discount(item: Item, initial: Checked): (BigDecimal, Int) = ???

      val initial = c1.copy(sum = c1.sum + c2.sum, scanned = c1.scanned |+| c2.scanned)
      val (applesDiscount, applesLeft) = discount(Apple, initial)
      val (orangesDiscount, orangesLeft) = discount(Orange, initial)
      initial.copy(
        sum = initial.sum - applesDiscount - orangesDiscount,
        scanned = initial.scanned + (Apple -> applesLeft) + (Orange -> orangesLeft)
      )
    }
  }
}

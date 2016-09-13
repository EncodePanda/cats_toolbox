package ct

import org.scalatest._

class CheckoutTest extends FreeSpec with Matchers {

  val items = List(Orange, Orange, Apple, Orange, Apple, Orange, Apple)

  "A Checkout" - {
    "should checkout with startegy 1" in {
      import Strategies.step1
      Checkout.totalCost(items) should equal(2.8)
    }

    "should checkout with startegy 2" in {
      import Strategies.step2
      Checkout.totalCost(items) should equal(1.95)
    }
  }
}

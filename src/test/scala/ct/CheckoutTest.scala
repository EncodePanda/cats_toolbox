package ct

import org.scalatest._

class CheckoutTest extends FreeSpec with Matchers {

  "A Checkout" - {
    "should sum apples and oranges" in {
      Checkout.totalCost(List(Orange, Apple, Orange)) should equal(1.10)
    }
  }


}

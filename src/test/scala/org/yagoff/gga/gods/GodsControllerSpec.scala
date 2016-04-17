//package org.yagoff.gga.gods
//
//import org.scalatest.{FlatSpec, Matchers}
//import org.scalatest.concurrent.ScalaFutures
//
//class GodsControllerSpec extends FlatSpec with Matchers with ScalaFutures {
//
//  lazy val godsController = new GodsController
//
//  "nextGodId" should "return the next number every call" in {
//    val one = godsController.nextGodId()
//    val two = godsController.nextGodId()
//    two should equal (one + 1)
//  }
//
//  "addGod" should "return a new God with a proper ID" in {
//    val god = new God(None, "Ares", List("war", "bloodshed", "violence"))
//    val godWithId = godsController.addGod(god)
//    godWithId.id shouldBe a [Option[_]]
//  }
//
//}

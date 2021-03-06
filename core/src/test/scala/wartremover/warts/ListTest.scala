package org.wartremover
package test

import org.scalatest.FunSuite

import org.wartremover.warts.ListOps

class ListTest extends FunSuite {
  test("can't use List#head on List") {
    val result = WartTestTraverser(ListOps) {
      println(List(1).head)
    }
    assertResult(List("List#head is disabled - use List#headOption instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#tail on List") {
    val result = WartTestTraverser(ListOps) {
      println(List().tail)
    }
    assertResult(List("List#tail is disabled - use List#drop(1) instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#init on List") {
    val result = WartTestTraverser(ListOps) {
      println(List().init)
    }
    assertResult(List("List#init is disabled - use List#dropRight(1) instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#last on List") {
    val result = WartTestTraverser(ListOps) {
      println(List().last)
    }
    assertResult(List("List#last is disabled - use List#lastOption instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#reduce on List") {
    val result = WartTestTraverser(ListOps) {
      println(List.empty[Int].reduce(_ + _))
    }
    assertResult(List("List#reduce is disabled - use List#reduceOption or List#fold instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#reduceLeft on List") {
    val result = WartTestTraverser(ListOps) {
      println(List.empty[Int].reduceLeft(_ + _))
    }
    assertResult(List("List#reduceLeft is disabled - use List#reduceLeftOption or List#foldLeft instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use List#reduceRight on List") {
    val result = WartTestTraverser(ListOps) {
      println(List.empty[Int].reduceRight(_ + _))
    }
    assertResult(List("List#reduceRight is disabled - use List#reduceRightOption or List#foldRight instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("ListOps wart obeys SuppressWarnings") {
    val result = WartTestTraverser(ListOps) {
      @SuppressWarnings(Array("org.wartremover.warts.ListOps"))
      val foo = {
        println(List(1).head)
        println(List().tail)
        println(List().init)
        println(List().last)
        println(List.empty[Int].reduce(_ + _))
        println(List.empty[Int].reduceLeft(_ + _))
        println(List.empty[Int].reduceRight(_ + _))
      }
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

}

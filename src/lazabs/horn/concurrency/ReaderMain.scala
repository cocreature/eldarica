/**
 * Copyright (c) 2015 Philipp Ruemmer. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of the authors nor the names of their
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lazabs.horn.concurrency

import lazabs.horn.bottomup.HornClauses

object ReaderMain {

  def printClauses(system : ParametricEncoder.System,
                   assertions : Seq[HornClauses.Clause]) = {
    println("System transitions:")
    for ((p, r) <- system.processes) {
      r match {
        case ParametricEncoder.Singleton =>
          println("  Singleton thread:")
        case ParametricEncoder.Infinite =>
          println("  Replicated thread:")
      }
      for ((c, _) <- p)
        println("    " + c.toPrologString)
    }

    if (!system.timeInvariants.isEmpty) {
      println
      println("Time invariants:")
      for (c <- system.timeInvariants)
        println("  " + c.toPrologString)
    }

    if (!assertions.isEmpty) {
      println
      println("Assertions:")
      for (c <- assertions)
        println("  " + c.toPrologString)
    }
  }

  def main(args: Array[String]) : Unit = {
    ap.util.Debug enableAllAssertions false
    lazabs.GlobalParameters.get.assertions = false

    for (name <- args) {
      val (system, assertions) = 
        CCReader(new java.io.BufferedReader (
                   new java.io.FileReader(new java.io.File (name))),
                 "main")

      val (smallSystem, smallAssertions) = system mergeLocalTransitions assertions
      printClauses(smallSystem, smallAssertions)

      println
      new VerificationLoop(smallSystem, smallAssertions)
    }
  }

}
/**
  * Copyright (c) 2011-2016 Philipp Ruemmer. All rights reserved.
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


package lazabs;

import ap.util.CmdlParser

import scala.collection.mutable.ArrayBuffer
import java.util.concurrent.Semaphore

import java.net._

object ServerMain {
  private val MaxWaitNum   = 32
  private val WatchdogInit = 30

  //////////////////////////////////////////////////////////////////////////////

  def main(args : Array[String]) : Unit = {
    val predefPort = args match {
      case Array(CmdlParser.IntVal(v)) => Some(v)
      case _ => None
    }

    val socket =
      new ServerSocket(predefPort getOrElse 0, MaxWaitNum,
        InetAddress getByName "0.0.0.0")
    val port = socket.getLocalPort

    Console.withOut(Console.err) {
      println(lazabs.Main.greeting)
      println
      println("Daemon started on port " + port)
    }

    ////////////////////////////////////////////////////////////////////////////
    // The main loop

    var serverRunning = true
    while (serverRunning) {
      try {
        val clientSocket = socket.accept

        val thread = new Thread(new Runnable { def run : Unit = {
          Console setErr lazabs.horn.bottomup.HornWrapper.NullStream

          val inputReader =
            new java.io.BufferedReader(
              new java.io.InputStreamReader(clientSocket.getInputStream))

          val arguments = new ArrayBuffer[String]

          var str = inputReader.readLine
          var done = false
          while (!done && str != null) {
            str.trim match {
              case "PROVE_AND_EXIT" => {
                done = true
                Console.withOut(clientSocket.getOutputStream) {
                  var lastPing = System.currentTimeMillis
                  var cancel = false
                  var watchdogCounter = WatchdogInit
                  var watchdogCont = true

                  // watchdog that makes sure that the system
                  // is shut down eventually, in case the normal
                  // timeout fails
                  val watchdog = new Thread(new Runnable { def run : Unit = {
                    while (watchdogCont) {
                      Thread sleep 1000
                      watchdogCounter = watchdogCounter - 1
                      if (watchdogCounter <= 0) {
                        println("ERROR: hanging system, shutting down")
                        java.lang.System exit 1
                      }
                    }
                  }})

                  watchdog.start

                  try {
                    Main.doMain(arguments.toArray, {
                      watchdogCounter = WatchdogInit
                      cancel || {
                        val currentTime = System.currentTimeMillis
                        while (inputReader.ready) {
                          inputReader.read
                          lastPing = currentTime
                        }
                        cancel = currentTime - lastPing > 3000
                        cancel
                      }
                    })
                  } catch {
                    case t : Throwable =>
                      println("catched throwable")
                      println("ERROR: " + t.getMessage)
                      //      t.printStackTrace
                  } finally {
                    watchdogCont = false
                  }
                }
              }
              case str =>
                arguments += str
            }

            if (!done)
              str = inputReader.readLine
          }

          inputReader.close
        }})

        thread.start

      }
    }
  }
}

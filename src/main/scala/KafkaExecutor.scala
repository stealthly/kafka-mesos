/**
  * Licensed to the Apache Software Foundation (ASF) under one or more
  * contributor license agreements.  See the NOTICE file distributed with
  * this work for additional information regarding copyright ownership.
  * The ASF licenses this file to You under the Apache License, Version 2.0
  * (the "License"); you may not use this file except in compliance with
  * the License.  You may obtain a copy of the License at
  *
  *    http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package kafka.mesos

import org.apache.mesos.{ MesosExecutorDriver, ExecutorDriver, Executor }
import org.apache.mesos.Protos._
import kafka.utils.{ Logging => AppLogging }

// Mesos Framework API http://mesos.apache.org/documentation/latest/app-framework-development-guide/

class KafkaExecutor extends Executor with AppLogging {

  /**
    * Invoked when the executor becomes "disconnected" from the slave
    * (e.g., the slave is being restarted due to an upgrade).
    */
  def disconnected(executorDriver: ExecutorDriver) = {
    info("disconnected")
  }

  /**
    * Invoked when a fatal error has occured with the executor and/or
    * executor driver. The driver will be aborted BEFORE invoking this
    * callback.
    */
  def error(executorDriver: ExecutorDriver, message: String) = {
    super.error(message)
  }

  /**
    * Invoked when a framework message has arrived for this
    * executor. These messages are best effort; do not expect a
    * framework message to be retransmitted in any reliable fashion.
    */
  def frameworkMessage(executorDriver: ExecutorDriver, data: Array[Byte]) = {
    info("frameworkMessage data bytes")
  }

  /**
    * Invoked when a task running within this executor has been killed
    * (via SchedulerDriver::killTask). Note that no status update will
    * be sent on behalf of the executor, the executor is responsible
    * for creating a new TaskStatus (i.e., with TASK_KILLED) and
    * invoking ExecutorDriver::sendStatusUpdate.
    */
  def killTask(executorDriver: ExecutorDriver, taskId: TaskID) = {
    info("kill task")
  }

  /**
    * Invoked when a task has been launched on this executor (initiated
    * via Scheduler::launchTasks). Note that this task can be realized
    * with a thread, a process, or some simple computation, however, no
    * other callbacks will be invoked on this executor until this
    * callback has returned.
    */
  def launchTask(executorDriver: ExecutorDriver, taskInfo: TaskInfo) = {
    info("launching task")
  }

  /**
    * Invoked once the executor driver has been able to successfully
    * connect with Mesos. In particular, a scheduler can pass some
    * data to it's executors through the FrameworkInfo.ExecutorInfo's
    * data field.
    */
  def registered(executorDriver: ExecutorDriver, executorInfo: ExecutorInfo, frameworkInfo: FrameworkInfo, slaveInfo: SlaveInfo) = {
    info("registered")
  }

  /**
    * Invoked when the executor re-registers with a restarted slave.
    */
  def reregistered(x$1: ExecutorDriver, x$2: org.apache.mesos.Protos.SlaveInfo) = {
    info("registered with a restarted slave")
  }

  /**
    * Invoked when the executor should terminate all of it's currently
    * running tasks. Note that after a Mesos has determined that an
    * executor has terminated any tasks that the executor did not send
    * terminal status updates for (e.g., TASK_KILLED, TASK_FINISHED,
    * TASK_FAILED, etc) a TASK_LOST status update will be created.
    */
  def shutdown(x$1: ExecutorDriver) = {
    info("shutting down")
  }
}

object KafkaExecutorShellExecutor {

  def main(args: Array[String]) {

    val executor = new KafkaExecutor()
    val executorDriver = new MesosExecutorDriver(executor)
    val status = executorDriver.run()
    status match {
      case Status.DRIVER_STOPPED => System.exit(0)
      case _                     => System.exit(1)
    }
  }
}
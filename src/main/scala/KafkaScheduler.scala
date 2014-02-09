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

import org.apache.mesos.{ SchedulerDriver, Scheduler }
import java.util
import org.apache.mesos.Protos._
import org.apache.mesos.state.State
import kafka.utils.{ Logging => AppLogging }

// Mesos Framework API http://mesos.apache.org/documentation/latest/app-framework-development-guide/
//(command: String, numInstances: Int, state: State)

class KafkaScheduler extends Scheduler with AppLogging {

  /**
    * Invoked when the scheduler becomes "disconnected" from the master
    * (e.g., the master fails and another is taking over).
    */
  def disconnected(schedulerDriver: SchedulerDriver) = {
    info("disconnected started")
  }

  /**
    * Invoked when there is an unrecoverable error in the scheduler or
    * scheduler driver. The driver will be aborted BEFORE invoking this
    * callback.
    */
  def error(schedulerDriver: SchedulerDriver, message: String) = {
    super.error(message)
  }

  /**
    * Invoked when an executor has exited/terminated. Note that any
    * tasks running will have TASK_LOST status updates automagically
    * generated.
    */
  def executorLost(schedulerDriver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, status: Int) = {
    info("executorLost status = " + status.toString)
  }

  /**
    * Invoked when a framework message has arrived for this
    * executor. These messages are best effort; do not expect a
    * framework message to be retransmitted in any reliable fashion.
    */
  def frameworkMessage(schedulerDriver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, data: Array[Byte]) = {
    info("frameworkMessage received")
  }

  /**
    * Invoked when an offer is no longer valid (e.g., the slave was
    * lost or another framework used resources in the offer). If for
    * whatever reason an offer is never rescinded (e.g., dropped
    * message, failing over framework, etc.), a framwork that attempts
    * to launch tasks using an invalid offer will receive TASK_LOST
    * status updats for those tasks (see Scheduler::resourceOffers).
    */
  def offerRescinded(schedulerDriver: SchedulerDriver, executorId: OfferID) = {
    info("the offer we had was taken away from us")
  }

  /**
    * Invoked once the executor driver has been able to successfully
    * connect with Mesos. In particular, a scheduler can pass some
    * data to it's executors through the FrameworkInfo.ExecutorInfo's
    * data field.
    */
  def registered(schedulerDriver: SchedulerDriver, framworkId: FrameworkID, masterInfo: MasterInfo) = {
    info("registered with FrameworkID")
  }

  /**
    * Invoked when the executor re-registers with a restarted slave.
    */
  def reregistered(schedulerDriver: SchedulerDriver, masterInfo: MasterInfo) = {
    info("registered without FrameworkID")
  }

  /**
    * Invoked when resources have been offered to this framework. A
    * single offer will only contain resources from a single slave.
    * Resources associated with an offer will not be re-offered to
    * _this_ framework until either (a) this framework has rejected
    * those resources (see SchedulerDriver::launchTasks) or (b) those
    * resources have been rescinded (see Scheduler::offerRescinded).
    * Note that resources may be concurrently offered to more than one
    * framework at a time (depending on the allocator being used). In
    * that case, the first framework to launch tasks using those
    * resources will be able to use them while the other frameworks
    * will have those resources rescinded (or if a framework has
    * already launched tasks with those resources then those tasks will
    * fail with a TASK_LOST status and a message saying as much).
    */
  def resourceOffers(schedulerDriver: SchedulerDriver, offer: util.List[Offer]) = {
    info("resource offers")
  }

  /**
    * Invoked when a slave has been determined unreachable (e.g.,
    * machine failure, network partition). Most frameworks will need to
    * reschedule any tasks launched on this slave on a new slave.
    */
  def slaveLost(schedulerDriver: SchedulerDriver, slaveId: SlaveID) = {
    info("slave lost")
  }

  /**
    * Invoked when the status of a task has changed (e.g., a slave is
    * lost and so the task is lost, a task finishes and an executor
    * sends a status update saying so, etc). Note that returning from
    * this callback _acknowledges_ receipt of this status update! If
    * for whatever reason the scheduler aborts during this callback (or
    * the process exits) another status update will be delivered (note,
    * however, that this is currently not true if the slave sending the
    * status update is lost/fails during that time).
    */
  def statusUpdate(schedulerDriver: SchedulerDriver, taskStatus: TaskStatus) = {
    info("status update")
  }
}
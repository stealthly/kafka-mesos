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

import com.typesafe.config.{ Config, ConfigFactory }
import org.apache.mesos.{ MesosSchedulerDriver }
import org.apache.mesos.Protos.{ FrameworkInfo, FrameworkID }
import kafka.utils.{ Logging => AppLogging }

object KafkaFramework extends AppLogging {

  //val defaultSettings = ConfigFactory.parseString("""
  //  mesos {
  //    master = "localhost:5050"
  //  }
  //""")

  //val config = ConfigFactory.load.getConfig("kafka.mesos").withFallback(defaultSettings)

  val normalizedName = "KafkaFramework"
  val failoverTimeout = 600000 // in milliseconds (10 minutes)
  val mesosMaster = "zk://192.168.57.10:2181,/etc" //"localhost:5050" //config.getString("mesos.master")

  val frameworkId = FrameworkID.newBuilder.setValue(normalizedName)

  val frameworkInfo = FrameworkInfo.newBuilder()
    .setId(frameworkId)
    .setName(normalizedName)
    .setFailoverTimeout(failoverTimeout)
    .setUser("") // let Mesos assign the user
    .setCheckpoint(true)
    .build

  val scheduler = new KafkaScheduler

  val driver = new MesosSchedulerDriver(
    scheduler,
    frameworkInfo,
    mesosMaster
  )

  // Execution entry point
  def main(args: Array[String]): Unit = {
    info("Mesos framework [{%s}] starting up!".format(normalizedName))
    driver.run()
  }

}
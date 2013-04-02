package com.softwaremill.codebrag.dao

import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import net.liftweb.mongodb.{MongoDB, DefaultMongoIdentifier}
import com.mongodb.Mongo

trait FlatSpecWithMongo extends FlatSpec with BeforeAndAfterAll {

  val mongoPort = 24567

  protected var mongoRunner: MongoRunner = null

  override protected def beforeAll() {
    super.beforeAll()
    startMongo()
  }

  override protected def afterAll() {
    stopMongo()
    super.afterAll()
  }

  def startMongo() {
    mongoRunner = MongoRunner.run(mongoPort, verbose = true)
    MongoDB.defineDb(DefaultMongoIdentifier, new Mongo("localhost", mongoPort), "codebrag_test")
  }

  def stopMongo() {
    mongoRunner.stop()
  }
}

/**
 * Run tests on MongoDB instance that is running on given host and port,
 * This will neither start nor stop MongoDB instance for you.
 */
trait FlatSpecWithRemoteMongo extends FlatSpec with BeforeAndAfterAll {

  override protected def beforeAll() {
    super.beforeAll()
    MongoDB.defineDb(DefaultMongoIdentifier, new Mongo(mongoHost, mongoPort), databaseName)
  }

  override protected def afterAll() {
    super.afterAll()
  }

  protected def mongoPort = 27017
  protected def mongoHost = "localhost"
  protected def databaseName = "codebrag_test"
}

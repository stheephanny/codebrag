package com.softwaremill.codebrag.activities

import com.softwaremill.codebrag.domain.ReviewedCommit
import com.softwaremill.codebrag.common.{EventBus, Clock}
import com.typesafe.scalalogging.slf4j.Logging
import com.softwaremill.codebrag.dao.commitinfo.CommitInfoDAO
import com.softwaremill.codebrag.domain.reactions.CommitReviewedEvent
import org.bson.types.ObjectId
import com.softwaremill.codebrag.cache.UserReviewedCommitsCache

/**
 * Handles user activity when user wants to mark given commit as reviewed
 */
class ReviewCommitUseCase(
  commitDao: CommitInfoDAO,
  reviewedCommitsCache: UserReviewedCommitsCache,
  eventBus: EventBus) (implicit clock: Clock) extends Logging {

  def markAsReviewed(sha: String, userId: ObjectId) {
    commitDao.findBySha(sha).foreach { commit =>
      val reviewedCommit = ReviewedCommit(commit.sha, userId, clock.nowUtc)
      reviewedCommitsCache.markCommitAsReviewed(reviewedCommit)
      eventBus.publish(CommitReviewedEvent(commit, userId))
    }

  }

}
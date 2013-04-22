import org.mockito.{ArgumentCaptor, ArgumentMatcher}
import scala.collection.JavaConversions._
    val authorTime = new DateTime(revCommit.getAuthorIdent.getWhen)
      commitTime, authorTime, List(), List(CommitFileInfo("file.txt", "added", expectedPatch)))
    val authorTime = new DateTime(revCommit.getAuthorIdent.getWhen)
      commitTime, authorTime, List(), List(CommitFileInfo("file.txt", "added", expectedPatch)))
    val commitArgument = ArgumentCaptor.forClass(classOf[CommitInfo])
    verify(commitInfoDaoMock, times(2)).storeCommit(commitArgument.capture())
    val capturedCommits = commitArgument.getAllValues
    capturedCommits(0).message should equal("fourth update message")
    capturedCommits(1).message should equal("third update message")
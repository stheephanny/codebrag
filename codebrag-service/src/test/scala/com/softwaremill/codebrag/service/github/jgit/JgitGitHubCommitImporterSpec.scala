import org.mockito.BDDMockito._
    val lastCommit = givenCommit("file.txt", "file content update", "commit2 msg")
    given(commitInfoDaoMock.findLastSha()).willReturn(Some(lastCommit.getId.name))
    verify(commitInfoDaoMock).findLastSha()
      uriBuilder,
      commitInfoDaoMock),
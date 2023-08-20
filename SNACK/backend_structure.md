```
UMC-SNACKS\SNACK\src
├─main
│  ├─java
│  │  └─com
│  │      └─example
│  │          └─demo
│  │              │  AppConfig.java
│  │              │  BaseTimeEntity.java
│  │              │  Games.java
│  │              │  SnackApplication.java
│  │              │  WebMvcConfigurerImpl.java
│  │              │
│  │              ├─board
│  │              │  │  BoardController.java
│  │              │  │  BoardDTO.java
│  │              │  │  BoardService.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │      BoardRequestDTO.java
│  │              │  │      BoardResponseDTO.java
│  │              │  │
│  │              │  ├─enrollment
│  │              │  │      Enrollment.java
│  │              │  │      EnrollmentController.java
│  │              │  │      EnrollmentRepository.java
│  │              │  │      EnrollmentRequestDTO.java
│  │              │  │      EnrollmentResponseDTO.java
│  │              │  │      EnrollmentService.java
│  │              │  │
│  │              │  ├─entity
│  │              │  │      Board.java
│  │              │  │      BoardMember.java
│  │              │  │      BoardSearch.java
│  │              │  │
│  │              │  └─repository
│  │              │          BoardMemberRepository.java
│  │              │          BoardMemberRepositoryCustom.java
│  │              │          BoardMemberRepositoryImpl.java
│  │              │          BoardRepository.java
│  │              │          BoardSearchRepositoryImpl.java
│  │              │
│  │              ├─chat
│  │              │  │  ChatController.java
│  │              │  │  ChatService.java
│  │              │  │  ChatType.java
│  │              │  │  Mapper.java
│  │              │  │
│  │              │  ├─Dto
│  │              │  │      ChatRoomDTO.java
│  │              │  │      ChatRoomListDTO.java
│  │              │  │      ChatRoomMemberJoinMessageDTO.java
│  │              │  │      MemberSearchDTO.java
│  │              │  │      MessageDTO.java
│  │              │  │
│  │              │  ├─Entity
│  │              │  │      ChatMessage.java
│  │              │  │      ChatRoom.java
│  │              │  │      ChatRoomMember.java
│  │              │  │      ChatRoomMemberId.java
│  │              │  │
│  │              │  └─repository
│  │              │          ChatMessageCustomRepository.java
│  │              │          ChatMessageCustomRepositoryImpl.java
│  │              │          ChatMessageRepository.java
│  │              │          ChatRoomMemberCustomRepository.java
│  │              │          ChatRoomMemberCustomRepositoryImpl.java
│  │              │          ChatRoomMemberRepository.java
│  │              │          ChatRoomRepository.java
│  │              │          MapperConfig.java
│  │              │
│  │              ├─comment
│  │              │  │  CommentController.java
│  │              │  │  CommentRepository.java
│  │              │  │  CommentService.java
│  │              │  │  ReplyCommentController.java
│  │              │  │  ReplyCommentRepository.java
│  │              │  │  ReplyCommentService.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │      CommentRequestDTO.java
│  │              │  │      CommentResponseDTO.java
│  │              │  │      ReplyCommentResponseDTO.java
│  │              │  │      ReplyRequestCommentDTO.java
│  │              │  │
│  │              │  └─entity
│  │              │          Comment.java
│  │              │          ReplyComment.java
│  │              │
│  │              ├─config
│  │              │      ErrorCode.java
│  │              │      ExceptionHandlerFilter.java
│  │              │      JwtAuthenticationFilter.java
│  │              │      JwtTokenProvider.java
│  │              │      MyUserDetailsService.java
│  │              │      QuerydslConfiguration.java
│  │              │      SecurityUtil.java
│  │              │      SimplePasswordEncoder.java
│  │              │      SpringSecurityConfig.java
│  │              │      WebSocketConfig.java
│  │              │
│  │              ├─exception
│  │              │      ApiExceptionHandler.java
│  │              │      BoardHostAuthenticationException.java
│  │              │      BoardMemberOverlappingException.java
│  │              │      BoardSizeOverException.java
│  │              │      ControllerAdvice.java
│  │              │      EnrollmentOverlappingException.java
│  │              │      EnrollmentRequestException.java
│  │              │      ErrorCode.java
│  │              │      ErrorResponse.java
│  │              │      ErrorResult.java
│  │              │      HeartRequestException.java
│  │              │      RestApiException.java
│  │              │
│  │              ├─heart
│  │              │      Heart.java
│  │              │      HeartController.java
│  │              │      HeartRepository.java
│  │              │      HeartRequestDTO.java
│  │              │      HeartService.java
│  │              │
│  │              ├─member
│  │              │  ├─controller
│  │              │  │      MemberController.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │      MemberPublicResponse.java
│  │              │  │      MemberRequestDTO.java
│  │              │  │      MemberResponseDTO.java
│  │              │  │
│  │              │  ├─entity
│  │              │  │      Member.java
│  │              │  │
│  │              │  ├─repository
│  │              │  │      MemberRepository.java
│  │              │  │
│  │              │  └─service
│  │              │          EmailService.java
│  │              │          MemberService.java
│  │              │          RandomPasswordService.java
│  │              │
│  │              ├─profile
│  │              │  │  UserRequestException.java
│  │              │  │
│  │              │  ├─controller
│  │              │  │      MyInfoController.java
│  │              │  │
│  │              │  ├─domain
│  │              │  │  ├─follow
│  │              │  │  │      Follow.java
│  │              │  │  │      FollowRepository.java
│  │              │  │  │
│  │              │  │  └─userinfo
│  │              │  │          UserInfo.java
│  │              │  │          UserInfoRepository.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │  ├─myInfo
│  │              │  │  │      MyInfoArticleResponseDto.java
│  │              │  │  │      MyInfoResponseDto.java
│  │              │  │  │
│  │              │  │  └─profileUpdate
│  │              │  │          ProfileReadResponseDto.java
│  │              │  │          ProfileUpdateRequestDto.java
│  │              │  │          ProfileUpdateResponseDto.java
│  │              │  │
│  │              │  └─service
│  │              │          FollowService.java
│  │              │          MyInfoService.java
│  │              │          UserInfoService.java
│  │              │
│  │              ├─socialboard
│  │              │  │  SocialBoardController.java
│  │              │  │  SocialBoardService.java
│  │              │  │  VoteRepository.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │      CommentDTO.java
│  │              │  │      SocialBoardDTO.java
│  │              │  │      SocialBoardResponseDTO.java
│  │              │  │      VoteBoardDTO.java
│  │              │  │      VoteBoardResponseDTO.java
│  │              │  │      VoteDTO.java
│  │              │  │      VoteResponseDTO.java
│  │              │  │
│  │              │  ├─entity
│  │              │  │      SocialBoard.java
│  │              │  │      Vote.java
│  │              │  │      VoteBoard.java
│  │              │  │
│  │              │  └─repository
│  │              │          SocialBoardRepository.java
│  │              │          SocialBoardRepositoryCustom.java
│  │              │          SocialBoardRepositoryImpl.java
│  │              │
│  │              ├─swagger
│  │              │      SwaggerConfig.java
│  │              │
│  │              └─utils
│  │                      JwtUtil.java
│  │
│  └─resources
│          application-dev.properties
│          application-real.properties
│          application.properties
│          log4j2.xml
│
├─querydsl
│  └─java
└─test
└─java
└─com
└─example
└─demo
│  SnackApplicationTests.java
│
└─board
BoardServiceTest.java
```

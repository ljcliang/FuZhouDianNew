package com.yiwo.fuzhoudian.network;

/**
 * Created by Administrator on 2018/7/12.
 */

public class NetConfig {

    //测试服务器
//    public static String BaseUrl = "http://www.91yiwo.com/ylyy/index.php/";
//    正式服务器
    public static String BaseUrl = "https://fzd.91yiwo.com/index.php/";
//    public static String BaseUrl = "http://fzd.tongbanapp.com/index.php/";
    //首页店铺URl
    public static String ShopHomeUrl = BaseUrl+"action/ac_goods/goodsList?uid="; //后面加 店铺的id
    //管理店铺 URL
    public static String GuanLiGoodsUrl = BaseUrl+"action/ac_goods/myGoods?uid="; //后面加 用户id
    //销售明细URLhttps://fzd.91yiwo.com/action/ac_user/sellDetailed?uid=
    public static String XiaoSHouMingXiUrl = BaseUrl+"action/ac_user/sellDetailed?uid="; // +店铺id
    //店铺商品评论URL：
    public static String GoodsCommentUrl = BaseUrl+"action/ac_goods/goodsComment?uid="; // +店铺id
    //店铺文章评论URL：
    public static String ArticleCommentUrl = BaseUrl+"action/ac_goods/articleComment?uid="; // +店铺id

    //未登录 订单列表 https://fzd.91yiwo.com/action/ac_order/lwOrderList?uid=用户id
    public static String LwOrderList = BaseUrl+"action/ac_order/lwOrderList?uid="; // +店铺id

    //检测版本号 返回 an_version 安卓版本号  an_download下载地址
    public static String checkVersion = "action/ac_public/check_version";
    //登录
    public static String loginUrl = "action/ac_login/login";
    //获取验证码
    public static String getCodeUrl = "action/ac_login/SendCode";
    //用户注册
    public static String registerUrl = "action/ac_login/UseRregister";
    //找回密码
    public static String forgetPwUrl = "action/ac_login/Retrievethepassword";
    //城市列表
    public static String cityUrl = "action/ac_login/get_all_city";
    //文章列表
    public static String friendsRememberUrl = "action/ac_article/article_list";
    //文章详情
    public static String articleContentUrl = "action/ac_article/article_slide";
    //友聚列表
    public static String friendsTogetherUrl = "action/ac_activity/activity_all_list";
    //新友聚列表
    public static String activityAllList = "action/ac_activity/activityAllList";
    //友聚详情
    public static String friendsTogetherDetailsUrl = "action/ac_activity/activity_info";
    //首页友记热门列表
    public static String homeHotFriendsRememberUrl = "action/ac_article/index_fmrecommend";
    //热门城市
    public static String hotCityUrl = "action/ac_activity/hot_city";
    //获取用户信息
    public static String userInformation = "action/ac_user/UserInformation";
    //我的关注
    public static String userFocus = "action/ac_user/my_look";
    //我的收藏
    public static String userCollection = "action/ac_user/Usercollection";
    //我的评论
    public static String userComment = "action/ac_article/article_comment_list";
    //获取标签
    public static String userLabel = "action/ac_public/Label";
    //友记发布
    public static String userRelease = "action/ac_article/InsertArticle";
    //用户友记列表(草稿)// 传参  type  不传  返回所有已发布和草稿，传 1 返回所有草稿，传0返回已发布的友记
    public static String userRemember = "action/ac_article/Listofarticles";
    //添加插文
    public static String userIntercalation = "action/ac_article/Intercalation";
    //续写友记
    public static String userRenewTheArticle = "action/ac_article/RenewTheArticle";
    //创建活动
    public static String createActivityUrl = "action/ac_activity/add_travel";
    //用户插文列表
    public static String userIntercalationListUrl = "action/ac_article/MylistoFinserts";
    //编辑友记
    public static String editorFriendRememberUrl = "action/ac_article/Friendeditor";
    //删除续写
    public static String deleteRenewUrl = "action/ac_article/DeleteRenew";
    //活动报名
    public static String applyActivityUrl = "action/ac_order/user_join";
    //删除友记
    public static String deleteFriendRememberUrl = "action/ac_article/delArticles";
    //发布草稿
    public static String releaseDraftUrl = "action/ac_article/draftRadio";
    //搜索友聚列表
    public static String searchFriendTogetherUrl = "action/ac_activity/search_activity_list";
    //关注活动
    public static String focusOnToFriendTogetherUrl = "action/ac_activity/follow_attention";
    //补充内容
    public static String addContentFriendTogetherUrl = "action/ac_activity/add_travel_info";
    //是否实名认证
    public static String isRealNameUrl = "action/ac_order/check_usercodeok";
    //相册列表
    public static String myPictureListUrl = "action/ac_user/AlbumList";
    //相册上传图片
    public static String myPictureUploadUrl = "action/ac_user/Photoalbum";
    //相册删除图片
    public static String myPictureDeleteUrl = "action/ac_user/deleteAlbum";
    //文章详情
    public static String detailsOfFriendsUrl = "action/ac_article/ArticleContent";
    //浏览历史
    public static String lookHistoryUrl = "action/ac_article/BrowsEhistory";
    //删除浏览历史
    public static String deleteLookHistoryUrl = "action/ac_article/deletehistory";
    //文章点赞
    public static String articlePraiseUrl = "action/ac_article/Give";
    //文章收藏
    public static String articleCollectionUrl = "action/ac_article/Collection";
    //保存用户信息
    public static String saveUserInformationUrl = "action/ac_user/saveUserinfo";
    //用户上传头像
    public static String userUploadHeaderUrl = "action/ac_user/UploadSheader";
    //•根据相册里面的图片更改头像
    public static String setupHeaderFromPics = "action/ac_user/SetupHeader";
    //实名认证
    public static String realNameUrl = "action/ac_user/Realnameauthentication";
    //删除收藏
    public static String deleteCollectionUrl = "action/ac_user/deletecollection";
    //获取插文位置列表
    public static String intercalationLocationUrl = "action/ac_article/IntercalationPosition";
    //添加插文
    public static String insertIntercalationUrl = "action/ac_article/Intercalation";
    //添加关注
    public static String userFocusUrl = "action/ac_user/ExecutiveAttention";
    //取消关注人
    public static String userCancelFocusUrl = "action/ac_user/AbolishConcern";
    //个人中心插文删除
    public static String userDeleteIntercalationFocusUrl = "action/ac_article/Deletetheinsert";
    //队友插文列表
    public static String teamIntercalationListUrl = "action/ac_article/IntercalationList";
    //设置插文是否展示
    public static String intercalationShowUrl = "action/ac_article/Exhibition";
    //设置屏蔽插文
    public static String sheildIntercalationUrl = "action/ac_article/ShieldedArticles";
    //文章评论
    public static String articleCommentUrl = "action/ac_article/ArticleReviews";
    //文章评论列表
    public static String articleCommentListUrl = "action/ac_article/ReviewList";
    //首页友聚
    public static String homeTogetherListUrl = "action/ac_activity/index_activity";
    //回复评论
    public static String replyCommentUrl = "action/ac_article/Reply";
    //关注领队
    public static String focusOnLeaderUrl = "action/ac_user/attention_captain";
    //订单列表
    public static String myOrderListUrl = "action/ac_order/my_order";
    //消息列表
    public static String messageListUrl = "action/ac_activity/add_activity";
    //历史意见反馈
    public static String historicalFeedBackUrl = "action/ac_user/feedbackList";
    //提交意见反馈
    public static String submitFeedBackUrl = "action/ac_user/feedback";
    //订单详情
    public static String detailsOrderUrl = "action/ac_order/order_detail";
    //发起的活动
    public static String initiativesListUrl = "action/ac_activity/activity_list";
    //参加的活动
    public static String activitiesAttendedUrl = "action/ac_activity/activity_join";
    //取消订单行程
    public static String cancelOrderTripUrl = "action/ac_order/order_out";
    //删除订单行程
    public static String deleteOrderTripUrl = "action/ac_order/del_order";
    //取消活动
    public static String cancleActivityUrl = "action/ac_activity/activity_cancel";
    //订单去支付
    public static String orderToPayUrl = "action/ac_order/go_pay";
    //获取修改活动页面
    public static String getEditorFriendTogetherUrl = "action/ac_activity/get_activity_info";
    //修改主活动信息
    public static String editorFriendTogetherUrl = "action/ac_activity/update_activity";
    //删除活动子标题全部内容
    public static String deleteFriendTogetherSubtitleContentUrl = "action/ac_activity/del" +
            "_activity_info";
    //修改活动子标题信息
    public static String editorFriendTogetherSubtitleContentUrl = "action/ac_activity/update_activity_info";
    //用户协议地址
//    public static String userAgreementUrl = "http://47.92.136.19/index.php/action/ac_public/user_agreement";
    public static String userAgreementUrl = BaseUrl +"action/ac_public/userDeal";
    public static String userAgreementUrl1 = BaseUrl + "action/ac_public/userDeal2";
    public static String chongZhiXieYiUrl = BaseUrl + "action/ac_public/paydeal";
////      报名协议
//    public static String joinDealUrl = "http://tb.91yiwo.cn/action/ac_public/joinDeal";
    //获取用户的活动列表
    public static String userActiveListUrl = "action/ac_article/ActivityList";
    //显示实名认证信息
    public static String userRealNameInfoUrl = "action/ac_user/Realdisplay";
    //活动评价列表
    public static String activeEvaluationListUrl = "action/ac_activity/activity_comment_list";
    //友记修改基础信息
    public static String modifyFriendRememberUrl = "action/ac_article/AmendFriends";
    //保存友记或草稿的修改信息
    public static String saveFriendRememberUrl = "action/ac_article/SaveFriendsText";
    //获取续写信息（修改）
    public static String modifyIntercalationUrl = "action/ac_article/RenewInfo";
    //保存续写修改信息
    public static String saveModifyIntercalationUrl = "action/ac_article/SaveRenewInfo";
    //保存续写修改信息(新 20200424)
    public static String saveModifyIntercalationUrl_New  = "action/ac_article/SaveRenewInfoIos";
    //黑色个人中心
    public static String otherUserInformationUrl = "action/ac_user/PersonalcenterOthers";
    //活动发布评价
    public static String orderCommentUrl = "action/ac_activity/activityReviews";
    //续写信息(修改描述和删除)
    public static String savePicAndDescribeUrl = "action/ac_article/SaveDescribe";
    //添加好友
    public static String addFriendsUrl = "action/ac_user/Addfriends";
    //进入聊天室
    public static String enterChatRoomUrl = "action/ac_activity/GroupActives";
    //获取要修改的活动标题内容和图片
    public static String getActiveIntercalationInfoUrl = "action/ac_activity/get_title_info";
    //删除活动图片
    public static String delActivePicUrl = "action/ac_activity/del_activity_info";
    //修改活动图片描述
    public static String modifyActivePicInfoUrl = "action/ac_activity/update_image_info";
    //活动回复
    public static String activeReturnCommentUrl = "action/ac_activity/reply_activity_comment";
    //活动分享
    public static String activeShareUrl = "action/ac_activity/SharingInformation";
    //发起邀请
    public static String activeInvitationUrl = "action/ac_activity/Invitation";
    //活动列表（邀请）
    public static String activeInvitationListUrl = "action/ac_activity/ActivityList";
    //首页消息中心
    public static String homeMessageCenterUrl = "action/ac_public/get_message";
    //系统、热门消息列表
    public static String systemHotMessageListUrl = "action/ac_public/system_hot_list";
    //邀请列表（消息中心）
    public static String messageInvitationListUrl = "action/ac_activity/InvitationList";
    //所有幻灯片
    public static String allBannerUrl = "action/ac_article/article_slide";
    //好友消息（消息中心）
    public static String messageFriendsUrl = "action/ac_user/FriendsList";
    //同意好友请求（消息中心）
    public static String messageFriendsOkUrl = "action/ac_user/AssentRequestFriends";
    //大家都在搜
    public static String allSearchUrl = "action/ac_public/all_search";
    //评价消息列表
    public static String messageCommentUrl = "action/ac_public/comment_message_list";
    //系统、热门消息详情
    public static String systemHotMessageDetailsUrl = "action/ac_public/system_hot_content";
    //获取报名信息(邀请同意时)
    public static String invitationOkUrl = "action/ac_activity/ActivityInfo";
    //消息中心邀请(拒绝)
    public static String invitationNoUrl = "action/ac_activity/refuse";
    //视频相关活动
    public static String videoActiveUrl = "action/ac_activity/video_activity";
    //我关注的活动
    public static String MyFocusActiveUrl = "action/ac_user/party_follow_attention";
    //好友列表
    public static String MyFriendListUrl = "action/ac_user/Myfriend";
    //删除好友
    public static String deleteFriendUrl = "action/ac_user/DeleteUser";
    //将好友拉入黑名单
    public static String blackFriendUrl = "action/ac_user/BlackUser";
    //黑名单列表
    public static String blackUserListUrl = "action/ac_user/BlacklistInfo";
    //取消拉黑
    public static String userCancelBlackUrl = "action/ac_user/CancelBlackout";
    //彻底删除(黑名单)
    public static String deleteBlackUserUrl = "action/ac_user/DeleteBlackUser";
    //清空消息
    public static String deleteMessageUrl = "action/ac_public/clear_message";
    //活动草稿发布接口
    public static String activeDraftReleaseUrl = "action/ac_activity/show_activity";
    //删除参加/发起的活动
    public static String deleteActiveUrl = "action/ac_activity/del_activity";
    //获取活动列表(发布友记)
    public static String getFriendActiveListUrl = "action/ac_article/FriendsList";
    //参加的活动获取活动评价信息接口
    public static String joinGetCommentInfoUrl = "action/ac_activity/get_join_comment";
    //解散群组
    public static String disbandedGroupUrl = "action/ac_public/DisbandedGroup";
    //获取全球国家名称
    public static String otherCityUrl = "action/ac_public/get_country";
    //删除评论
    public static String deleteCommentUrl = "action/ac_article/CommentaryDeleting";

    //获取参加的活动（报名活动、往期活动）
    public static String activityJoin = "action/ac_activity/activity_join";
    //获取关注我的人
    public static String guanZhuWoDe = "action/ac_user/get_AttentionTome";
    //首页新的列表数据接口
    public static String newHomeData = "action/ac_activity/Home_data";
    //友记新的列表数据接口
    public static String newYoujiData = "action/ac_article/Friends_List";
    //他人主页接口
    public static String personMain = "action/ac_user/Homepage";
    //获取标签列表
    public static String userlabel = "action/ac_user/userlabel";
    //获取用户以保存的标签
    public static String usersavelabel = "action/ac_user/User_Label_List";
    //保存用户选择的标签
    public static String saveuserlabel = "action/ac_user/SaveUserLabel";
    //超级喜欢匹配好友
    public static String matching_user = "action/ac_user/matching_user";
    //http://47.92.136.19/index.php/action/ac_user/Sayhello 匹配成功、打招呼
    public static String sayHello = "action/ac_user/Sayhello";
    //http://47.92.136.19/index.php/action/ac_user/Privateletterlist 私信列表
    public static String privateLetterList = "action/ac_user/Privateletterlist";
    //http://47.92.136.19/index.php/action/ac_user/Agree_or_refuse  同意或者拒绝聊天
    public static String agreeOrRefuse = "action/ac_user/Agree_or_refuse";
    //首页标签类别
    public static String indexLabel = "action/ac_user/index_label";
    //点赞收藏消息
    public static String zanAndSoucang = "action/ac_user/settings";
    //取消关注(他人主页)   http://47.92.136.19/index.php/action/ac_user/RemoveConcerns
    public static String removeConcerns = "action/ac_user/RemoveConcerns";
    //友记评论消息
    public static String youjiPinglun = "action/ac_user/ArticleReview";
    //获取活动期数
    public static String getPhase = "action/ac_activity/getphase";
    //清除用户浏览历史
    public static String clearLookHistory = "action/ac_activity/clear_look_history";
    //个人主页 关注&粉丝
    public static String lookUserAttention ="action/ac_user/lookUserAttention";
    //私信消息 删除     传uid用户id    type =0删除一条  =1全部清空   id要删除一条的id
    public static String delFriendInfo =  "action/ac_user/delFriendInfo";
    //友记详情web页面让Js 获取到UID的接口 传id 游记的id   uid登录用户的id  返回str  myID； 在此接口中 回调JS方法 （js交互方法  userGZinfo(gzStr,myID)）
    public static String youJiDoInfo = "action/ac_article/youJiDoInfo";
    //友记详情web页面获取数据
    public static String getInfo = "action/ac_public/getInfo";
    //友聚详情web页面数据
    public static String getActivityInfo = "action/ac_activity/getActivityInfo";
    //申请进群
    public static String inGroupInfo = "action/ac_public/inGroupInfo";
    //拒接进群
    public static String noInGroup = "action/ac_public/noInGroup";
    //同意进群
    public static String agreeIngroup = "action/ac_public/agreeInGroup";
    //删除活动评价
    public static String delComments = "action/ac_public/delComments";
    //更多活动评价接口
    public static String  commentListAll = "action/ac_activity/commentListAll";//action/ac_activity/commentListAll  传 pfID  活动的ID    全部评论接口   返回 fctime时间  userpic用户头像   username用户名  userID用户ID   pctitle评论内容
    //获取关注数量
    public static String getAttentionNum = "action/ac_public/getAttentionNum";//attentionNum我关注的数量   attentionMe关注我的数量   attentionActivity关注的活动 的数量
//    action/ac_user/clearAdmire 清空赞收藏接口  传userID
    public static String clearAdmire = "action/ac_user/clearAdmire";
//    action/ac_user/clearComment 清空评论接口  传userID
    public static String clearComment = "action/ac_user/clearComment";
    // 搜索好友
    public static String searchUser = "action/ac_user/searchUser";//    action/ac_user /     searchUser   搜索用户接口     传 userLogin用户账号 userID 我的ID  返回  userID 用户id  username 用户名   userpic 头像 status  0 不是好友  1是好友

   //获取心动状态接口
    public static String heartBeatStatus = "action/ac_user/heartBeatStatus" ; //action/ ac_user  /  heartBeatStatus 状态接口   传userID用户ID   heartbeatID 新东人的id   返回 status 0不是 1是; openStatus  0未开启  1已开启; h 1 是互为心动，0 是没有互为心动
    //点击心动操作
    public static String heartbeat = "action/ac_user/heartbeat"; //点击心动操作   传userID用户id  heartbeatID心动人的id
    //对我心动的人
    public static String heartList = "action/ac_user/heartList"; // 列表    传userID用户id           返回userID用户id  username用户名   userpic头像\n"
    //获取所有个人评论标签接口
    public static String commentLabel = "action/ac_user/commentLabel"; //action/ac_user/commentLabel  评论标签接口   传 userID  被评论人的ID   uid登录用户的ID   返回 id 标签的ID   label_name标签名 status 0未评价过  1评价过
    //论标签接口
    public static String userCommentLabel = "action/ac_user/userCommentLabel";//action/ac_user/userCommentLabel  评论标签接口  传userID登录用户的ID   buserID 被评论用户的ID    labelID 标签ID
    //获取个人评论标签接口
    public static String userCommentLabelList = "action/ac_user/userCommentLabelList";//action/ac_user/userCommentLabelList  我的评论标签接口   传 userID 登录用户的ID   type=0返回 10个     1返回全部 返回  label_name 标签名   num评论数量
    //获取评论的人 接口
    public static String clickUserList = "action/ac_user/clickUserList";//action/ac_user/clickUserList 点击的用户列表接口  传 userID登录用户ID  labelID标签ID   page分页
    //action/ac_public/Report 举报
    public static String report = "action/ac_public/Report";//  传userID 举报人ID  reportID 友记或友聚的ID    type 0友聚 1友记 info举报内容
    //action/ac_public/addGroup 邀请加入群   传userID群主ID   phone电话号   gid 群id
    public static String addGroup = "action/ac_public/addGroup";
    //群列表
    public static String groupList = "action/ac_public/groupList";//群列表    传userID 用户id    返回 name群名称   groupid群ID
    //退出群
    public static String moveOutGroup = "action/ac_public/moveOutGroup";  //action/ac_public/moveOutGroup 退出群   传group_id群 ID   userID用户ID
    //新个人主页
    public static String homepagePartOne = "action/ac_user/HomepagePartOne"; //action/ac_user/HomepagePartOne 用户基本信息接口 传status  tid  uid
    public static String homepagePartTwo = "action/ac_user/HomepagePartTwo"; //action/ac_user/HomepagePartTwo 友记数据 传status  tid  uid
    public static String homepagePartthree = "action/ac_user/HomepagePartThree"; //action/ac_user/HomepagePartTwo 友聚数据 传status  tid  uid
    public static String homepagePartFour = "action/ac_user/HomepagePartFour"; //action/ac_user/HomepagePartTwo 照片 传status  tid  uid
    public static String homepageVideos = "action/ac_user/myVideos";//action/ac_user/myVideos   传tid   status =0 tid为用户id  =1为网易id   vID视频id,vname视频名,vurl视频地址,vtime视频时间,img图片,look_num查看数量,wy_vid网易视频vid

    //任务URL
    public static String gameList = "action/ac_coupon/gameList";
    //添加视频接口
    public static String upLoadVideo = "action/ac_video/uploadVideo"; //添加视频接口   传userID用户id  vname视频名称   vurl视频地址
    //获取视频信息接口
    public static String videoInfo = "action/ac_video/videoInfo";//   传vid"
    //新上传视频接口，无需在调用获取视频信息接口 //action/ac_video/   videoInfoUpload  传vname视频标题  vid视频网易id   uid用户id   address地址
    public static String videoInfoUpload = "action/ac_video/videoInfoUpload";
    //我的视频列表
    public static String myVideo = "action/ac_video/myVideo"; //传userID 用户id   page分页  返回 vID 视频ID   vname名称   vurl地址  vtime时间  img图片地址
    //编辑视频名称
    public static String videoEdit = "action/ac_video/videoEdit";//编辑   传vname 名称  vID视频ID   userID用户id
    //删除视频
    public static String videoDel = "action/ac_video/videoDel"; // 删除   传userID 用户id   vID视频ID
    //视频点赞
    public static String videoPraise = "action/ac_video/videoPraise";//action/ac_video / videoPraise  点赞/取消点赞     传id视频id  uid用户id
    //视频点赞数据
    public static String videoNumInfo = "action/ac_video/videoNumInfo";//action/ac_video  /  videoNumInfo   传vID视频ID   uid用户ID    返回 praise_num赞数量   comment_num评论数量   status 0未赞过  1已赞
    //视频评论列表
    public static String videoReviewsList = "action/ac_video/videoReviewsList";//action/ac_article  /   videoReviewsList  评论列表   传vID视频ID   page分页     返回 vcID评论ID   vcontent内容   vctime时间  username用户名    userpic用户图片   replyList回复列表
    //视频评论
    public static String videoReviews = "action/ac_video/videoReviews";//action/ac_article / videoReviews  评论  传id视频id  fctitle内容  uid用户id
    //回复视频评论
    public static String replyVideoReviews = "action/ac_video/reply";// 回复接口   传userID用户ID  vcID评论的ID  vinfo回复内容
    //新首页 推荐列表接口
    public static String homeRecommend = "action/ac_activity/HomeRecommend"; //action/ac_activity/HomeRecommend 推荐 传 page 分页 uid用户id 未登录传0    city城市
    public static String homeRecommend2 = "action/ac_activity/HomeRecommend2";
    /////新首页顶部数据接口  banner 推荐活动 活动队长直播；
    public static String homePage = "action/ac_home/homePage";
    ////action/ac_home/homeYouJiVideo   友记列表   传page分页  uid用户Id city城市
    public static String homeYouJiVideo = "action/ac_home/homeYouJiVideo";
    //新首页 视频列表接口
    public static String homeVideo = "action/ac_activity/HomeVideo";//action/ac_activity/HomeVideo  视频   传page 分页 uid用户id 未登陆传0   返回    status 是否赞过 0未赞 1已赞  praise_num赞数量  comment_num评论数量  vID视频ID  vname视频名称  img图片  vurl视频地址  userID发视频人的ID    vtime发布时间  username发布视频的用户的昵称
    //新首页 友记列表接口
    public static String homeYouJi = "action/ac_activity/HomeYouJi";//action/ac_activity/HomeYouJi 友记   传page分页 uid 用户id  city城市
    //新首页 关注列表接口
    public static String homeGuanZhu = "action/ac_activity/HomeGuanZhuModel";//action/ac_activity/HomeGuanZhuModel 关注   传page  分页uid用户id
    public static String homeGuanZhu2 = "action/ac_activity/HomeGuanZhu2";//action/ac_activity/HomeGuanZhu2 关注   传page  分页uid用户id
    //新首页 日期 和消息数 接口
    public  static String dataInfo = "action/ac_user/dataInfo";//传uid   返回 news消息数  day日期
    //友聚页面 要开始活动数量接口
    public static  String myWillBegin = "action/ac_user/myWillBegin";//要开始的活动数量   传uid用户id  返回 willBeginNum 活动个数
    //个人信息开启恋爱模式 判断接口 action/ac_public/userSet   传userID
    public static String userSet = "action/ac_public/userSet";
    // 恋爱模式开启关闭接口 action/ac_user/changeType  传uid用户ID   type 0关闭 1开启
    public static String changeType = "action/ac_user/changeType";
    //d获取队长的活动  action/ac_coupon/willBeginActivity  获取队长的活动   传 userID 队长id   返回 pfpic图片   pfID活动id   pftitle活动名
    public static String willBeginActivity = "action/ac_coupon/willBeginActivity";
    //获取报名活动的所有人员  action/ac_activity/getAllUser    传pfID活动id  phase_id期数id  返回userID用户id  username昵称  game_num号码
    public static String getAllUser = "action/ac_activity/getAllUser";
    //队长分组   传pfID活动id  phase_id期数id   userList   json字符串 userID   队长的id
    public static String gameGroup = "action/ac_activity/gameGroup";
    //获取用户所在信息接口  传userID
    public static String lookGroup = "action/ac_activity/lookGroup";
    //管理员删除友记视频接口 action/ac_user/managerInfo 删除友记  视频接口  传type 0删除游记 1删除视频  delID要删除的ID   userID登录用户的ID
    public static String managerInfo = "action/ac_user/managerInfo";
    //管理员删除评论接口 action/ac_user/managerComments  删除评论   传type 0友记  1视频  delID删除的ID   userID用户id
    public static String managerComments = "action/ac_user/managerComments";
    //队长游戏列表接口
    public static String pictureGame = "action/ac_coupon/pictureGame";
    //直播信息接口   action/ac_zhibo/zhibo 传uid用户id  返回cid频道id  creator直播人的网易id  hlsPullUrl  httpPullUrl   pushUrl  rtmpPullUrl   roomId聊天室id
    public static String zhiBo = "action/ac_zhibo/zhibo";
    //直播列表 传uid用户id  page分页  返回userID直播人的id  hlsPullUrl,  httpPullUrl,  pushUrl,   rtmpPullUrl 直播地址   cid频道id   room_id房间id   username用户名  userpic头像
    public static String zhiBoList = "action/ac_zhibo/zhiboList";//暂未使用，暂使用首页推荐接口获取
    //action/ac_zhibo/zihboInfo   传uid用户id   cid频道id   返回zhibostatus直播状态    start_time 直播时间
    public static String zhiBoInfo = "action/ac_zhibo/zihboInfo";
    //action/ac_zhibo/   settZhiBoTime  设置开始时间  传uid用户id    starttime开始时间
    public static String settZhiBoTime = "action/ac_zhibo/setZhiBoTime";
    //action/ac_zhibo  /  sendPresent   送礼物   传name礼物(ID)  爱心 0 / 棒棒糖 1 / 戒指 2 /  饮料 3  / 游艇 4/  钻石 5/   num数量    uid我的id    touid送的人的网易id
    public static String sendPresent = "action/ac_zhibo/sendPresent";
    //action/ac_zhibo/presentList   传uid 用户id   返回 presentName礼物名  num数量 //获取礼物列表
    public static String presentList = "action/ac_zhibo/presentList";

    // action/ac_zhibo/addRobot 加机器人   传roomid房间号//直播间添加机器人
    public static String addRobot = "action/ac_zhibo/addRobot";

    //action/ac_zhibo/   offroom   关闭房间  传room_id 房间id   wy_accid 网易账 //
    public static String offRoom = "action/ac_zhibo/offroom";

    // action/ac_user/addwxunionid    传uid用户id   unionid微信id //绑定微信唯一标识
    public static String addWXUnionid = "action/ac_user/addwxunionid";
    //action/ac_user/getUserIntegral   获取我的积分  传uid  返回integral
    public static String getUserIntegral = "action/ac_user/getUserIntegral";
    //action/ac_zhibo/payIntegralList   充值列表  返回 id  money 钱  integral金币
    public static String payIntegralList = "action/ac_zhibo/payIntegralList";
    // action/ac_zhibo/    buyIntegral  购买积分  传paytype 支付类型 0微信 1支付宝    uid用户id  money付款金额      返回订单信息
    public static String buyIntegral = "action/ac_zhibo/buyIntegral";
    //action/ac_zhibo/     buyIntegralList  充值记录列表   传uid用户id   page分页     返回  orderSn订单号  payMoney充值的金额   buyIntegral充值的积分  addTime充值时间   ifPay支付状态   payType支付类型
    public static String buyIntegralList = "action/ac_zhibo/buyIntegralList";
    //action/ac_zhibo/    sendList 送礼物列表    传uid用户id   page分页   返回userID送给人的ID    presentName礼物名称   presentImg礼物图片   integral花费的积分    num送的数量    username送给人的名字   addTime送礼物的时间
    public static String sendList = "action/ac_zhibo/sendList";
    //action/ac_user/pleaseCaptain 申请成为队长  传uid用户id   captainImg图片
    public static String pleaseCaptain = "action/ac_user/pleaseCaptain";

    ///新首页
    //action/ac_newHome/tuijian   传uid用户id home推荐接口 city 城市
    public static String homeTuiJian = "action/ac_newHome/tuijian";
    //action/ac_newHome/yj_video  游记视频接口  传uid  page分页
    public static String yj_video = "action/ac_newHome/yj_video";
    //action/ac_newHome/homePageGz action/ac_newHome/homePageGz   首页关注接口  传uid用户id
    public static String homePageGz = "action/ac_newHome/homePageGz";
    //action/ac_newHome/gzCaptainList 首页关注队长 左拉更多页面 传uid用id  page分页
    public static String gzCaptainList = "action/ac_newHome/gzCaptainList";
//    action/ac_newHome/homeGoodsList   友铺接口   传page , uid
    public static String homeGoodsList = "action/ac_newHome/homeGoodsList";
//action/ac_newHome/yjVideoList   游记视频列表
//传page 分页  uid用户id
    public static String yjVideoList = "action/ac_newHome/yjVideoList";
// action/ac_captainMission/missionInfo   队长页接口  传uid
    public static String missionInfo = "action/ac_captainMission/missionInfo";
//    action/ac_captainMission/searchShop   搜索商家接口   传 keyWord 搜索词  uid用户id
    public static String searchShop = "action/ac_captainMission/searchShop";
//    action/ac_captainMission/keepShop   保存商家接口  传uid 用户id    shopID商家id
    public static String keepShop = "action/ac_captainMission/keepShop";
//    action/ac_captainMission/pfList   获取活动接口  传uid 用户id   keyWord 关键词
    public static String pfListDuizhangZhuanShu = "action/ac_captainMission/pfList";
//action/ac_captainMission/getMission       传uid 用户id   pfID活动id  phase_id期数id
    public static String getMission = "action/ac_captainMission/getMission";
//    action/ac_captainMission/shareMission  去分享接口      传uid 用户id  pfID活动iD
    public static String shareMission = "action/ac_captainMission/shareMission";
//    action/ac_captainMission/overMission   领取任务奖励   传uid用户id
    public static String overMission = "action/ac_captainMission/overMission";
//    action/ac_goods/tagList   标签列表   返回id标签id  name标签名
    public static String tagList = "action/ac_goods/tagList";
//    action/ac_goods/addServe 添加服务   传uid 用户id name 名字 info详情
    public static String addServe = "action/ac_goods/addServe";
//    action/ac_goods/serveList服务列表  传uid用户id
    public static String serveList = "action/ac_goods/serveList";
//    action/ac_goods/getEditGoodsInfo  获取编辑商品信息的接口   参数名传gid
    public static String getEditGoodsInfo = "action/ac_goods/getEditGoodsInfo";
//    action/ac_goods/addGoods  添加商品接口   传uid用户id   goodsInfo 商品信息 json字符串     goods_files 图片名称
    public static String addGoods = "action/ac_goods/addGoods";
//    action/ac_goods/updateGoods  修改商品保存接口 传的和添加的一样  增加 删除图片的参数  delImgID  图片的ID 用逗号拼成字符串，商品ID gid
    public static String updateGoods = "action/ac_goods/updateGoods";
//action/ac_goods/delServe   删除服务   传uid 用户id   sID 服务id
    public static String delServe = "action/ac_goods/delServe";

//    action/ac_goods/inShop    用户进入店铺接口 传page分页  uid登录用户的id未登录传0或不传   shopUserID店铺所属用户的id    keyWord搜索词
    public static String inShop = "action/ac_goods/inShop";
    //    action/ac_goods/insertGoodsOrder 下单接口
    //传参：getAddress收货地址、getPerson收货人、getTel收货电话、goodsID商品id、specID规格id、uid用户id、num购买数量、beizhu备注、payType支付类型
    public static String insertGoodsOrder = "action/ac_goods/insertGoodsOrder";
    //fabushangpinmessage
    public static String addGoodsMes = "action/ac_goods/addGoodsMes";
//    action/ac_goods   /    sellerOrder    传page分页  uid店铺id status  不传或传100 全部     传1待处理  2已处理   3已完成   4退款
    public static String sellerOrder = "action/ac_goods/sellerOrder";
//    action/ac_goods   /   sellerDoOrder  操作订单   传uid 用户id   orderID 订单id  type 操作类型  0拒绝接单  1出单  2删除       type=0时传 qxyy 拒绝接单的原因
    public static String sellerDoOrder = "action/ac_goods/sellerDoOrder";
//    action/ac_goods/orderRefund   确认退款   传 uid 用户id   orderID 订单id
    public static String orderRefund = "action/ac_goods/orderRefund";
//    action/ac_goods/     shopSureGetThings   确认收货 传uid用户id    orderID订单id
    public static String shopSureGetThings = "action/ac_goods/shopSureGetThings";
//action/ac_goods/      sendSet     配送设置           传uid 用户id   money配送费   noMoney满多少钱  zt  0可自提  1不可自提      ps0可配送 1不可配送
    public static String sendSet = "action/ac_goods/sendSet";
//    action/ac_goods/getSendSet   获取配送设置信息    传uid用户id
    public static String getSendSet =  "action/ac_goods/getSendSet";
//    action/ac_goods/    shopAboutGoods   获取相关商品列表   传uid ，searchword
    public static String shopAboutGoods =  "action/ac_goods/shopAboutGoods";
//action/ac_user/userAddressLatLng   地址接口  传uid用户id  shopLat  shopLng经纬度 address
    public static String userAddressLatLng =  "action/ac_user/userAddressLatLng";
//    action/ac_user/getUserAddressLatLng   获取地址信息  传uid用户id
    public static String getUserAddressLatLng =  "action/ac_user/getUserAddressLatLng";
//action/ac_goods/addMyTag   添加我的标签   name标签名   uid用户id  返回id
    public static String addMyTag =  "action/ac_goods/addMyTag";
//    action/ac_goods/getMyTag  我的标签  传uid
    public static String getMyTag =  "action/ac_goods/getMyTag";
//action/ac_goods/editMyTag   编辑  传id   name名字
    public static String editMyTag =  "action/ac_goods/editMyTag";

    /**
     *  action/ac_user/userVerify         传   uid 用户id    tel 电话    store_name 店铺名称
     *  merchant_shortname  店铺简称    email 邮箱     id_card_name身份证上的姓名
     *  id_card_number身份证号    idCardCopy身份证人像面   idCardNational 身份证国徽面   businessLicense营业执照   shopImg店铺照片
     */
    public static String userVerify =  "action/ac_user/userVerify";
    /**
     * action/ac_user/     wxQuery
     * 查询是否签约微信   传uid用户id   返回 sign0待签约  1已签约   url签约地址
     *   sign  0 未上传  1审核中  2待授权  3已授权  4失败
     *   pay  0未付费  1已付费 2 不需要验证费；
     */
    public static String wxQuery = "action/ac_user/wxQuery";
    /**
     * action/ac_user/getMoneyFromWx  提现
     * 传uid 用户id
     * 返回 status 0未认证  1提现余额不足   2未绑定银行卡   3绑定银行卡信息有误 请重新绑定  4操作成功
     */
    public static String getMoneyFromWx = "action/ac_user/getMoneyFromWx";

    /**
     * action/ac_user/getCode  获取邀请码   传uid  用户id   返回 inviteCode
     */
    public static String getCode = "action/ac_user/getCode";
    /**
     * action/ac_user/getCodeMoney    获取邀请码 金额    传 code 邀请码   返回 price
     */
    public static String getCodeMoney = "action/ac_user/getCodeMoney";
    /**
     * action/ac_shopInfo     /     shopInvitePay    传uid用户id     inviteCode邀请码
     */
    public static String shopInvitePay = "action/ac_shopInfo/shopInvitePay";
    /**
     * action/ac_user/inviteInfo     返回 price
     */
    public static String inviteInfo = "action/ac_user/inviteInfo";
    /**(
     * action/ac_goods/getSureTime 收货时间       传 orderID 订单id
     */
    public static String getSureTime = "action/ac_goods/getSureTime";
    /**
     * action/ac_login/verifyStatus          verifyCodeStatus 0关闭 1开启
     */
    public static String verifyStatus = "action/ac_login/verifyStatus";
    /**
     * 传uid用户id   paytype支付方式   money支付金额
     */
    public static String adOrder = "action/ac_ad/ad_order";

    /**
     * username 姓名
     *  uid  登陆用户id
     *  sfz  身份证号码
     *  tel 联系电话
     *  sex 性别 0男 1 女
     *  work_year 工作经验
     *  job 专业名( 从 接口 action/ac_user/attestationJob  获取所有专业名称 选择一个 把汉字传过来 不传数字)
     *  user_files 图片
     */
    public static String lwAttestation = "action/ac_user/lwAttestation";

    /**
     * 获取专业名称接口 ：action/ac_user/attestationJob   返回 jobname 专业名称
     */
    public static String attestationJob = "action/ac_user/attestationJob";
    /**
     * action/ac_user/lwStatus
     * action/ac_user/lwStatus   获取劳务状态接口   传uid 用户id     返回 status 0未认证 1已认证
     */
    public static String lwStatus = "action/ac_user/lwStatus";
}

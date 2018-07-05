/*
package com.example.miles.hlsplayer;

import android.text.TextUtils;
import android.util.Pair;
import android.view.Gravity;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import jam.ChatCommand;
import jam.protocol.receive.AnswerReceive;
import jam.protocol.receive.DismissReceive;
import jam.protocol.receive.EndEpisodeReceive;
import jam.protocol.receive.EndQuizReceive;
import jam.protocol.receive.EpisodeUserCountReceive;
import jam.protocol.receive.ReceiveHeartReceive;
import jam.protocol.receive.RewardPopupReceive;
import jam.protocol.receive.StartEpisodeReceive;
import jam.protocol.receive.StartQuizReceive;
import jam.protocol.receive.WinnersReceive;
import jam.protocol.receive.WriteReceive;
import jam.protocol.request.AckRequest;
import jam.protocol.request.AnswerRequest;
import jam.protocol.request.ApplyGiftRequest;
import jam.protocol.request.CashoutRequest;
import jam.protocol.request.CheckAnswerRequest;
import jam.protocol.request.CheckWinnerRequest;
import jam.protocol.request.GetAccountRequest;
import jam.protocol.request.GetEpisodeRequest;
import jam.protocol.request.GetGiftBoxRequest;
import jam.protocol.request.GetLeaderboardRequest;
import jam.protocol.request.GetNotificationsRequest;
import jam.protocol.request.GetPlayersRequest;
import jam.protocol.request.GetProfileRequest;
import jam.protocol.request.GetQuizRequest;
import jam.protocol.request.JoinEpisodeRequest;
import jam.protocol.request.LeaveEpisodeRequest;
import jam.protocol.request.QuackRequest;
import jam.protocol.request.ReadyRequest;
import jam.protocol.request.SearchAddressRequest;
import jam.protocol.request.SetAccountRequest;
import jam.protocol.request.SetProfileRequest;
import jam.protocol.request.SetUserDetailRequest;
import jam.protocol.request.SyncLiveRequest;
import jam.protocol.request.UpdatePushTokenRequest;
import jam.protocol.request.WriteRequest;
import jam.protocol.response.AnswerResponse;
import jam.protocol.response.ApplyGiftResponse;
import jam.protocol.response.CashoutResponse;
import jam.protocol.response.CheckAnswerResponse;
import jam.protocol.response.CheckWinnerResponse;
import jam.protocol.response.GetAccountResponse;
import jam.protocol.response.GetEpisodeResponse;
import jam.protocol.response.GetGiftBoxResponse;
import jam.protocol.response.GetLeaderboardResponse;
import jam.protocol.response.GetNotificationsResponse;
import jam.protocol.response.GetPlayersResponse;
import jam.protocol.response.GetProfileResponse;
import jam.protocol.response.GetQuizResponse;
import jam.protocol.response.GetSettingsResponse;
import jam.protocol.response.JoinEpisodeResponse;
import jam.protocol.response.LeaveEpisodeResponse;
import jam.protocol.response.ReadyResponse;
import jam.protocol.response.ResponseBase;
import jam.protocol.response.SearchAddressResponse;
import jam.protocol.response.SetAccountResponse;
import jam.protocol.response.SetProfileResponse;
import jam.protocol.response.SetUserDetailResponse;
import jam.protocol.response.SyncLiveResponse;
import jam.protocol.response.UpdatePushTokenResponse;
import jam.protocol.response.WriteResponse;
import jam.struct.UserAccount;
import jam.struct.quiz.Episode;
import me.snow.chat.JamChatApi;
import me.snow.chat.JamChatApiV2;
import me.snow.chat.LifecycleEvent;
import me.snow.chat.enums.AppGroundStatus;
import me.snow.chat.exception.ChatApiException;
import me.snow.chat.iface.AuthInfo;
import me.snow.chat.iface.ChatApiV2;
import me.snow.chat.iface.impl.AuthInfoImpl;
import me.snow.chat.stomp.StompClient;
import me.snow.chat.stomp.StompHeader;
import me.snow.chat.stomp.StompMessage;
import me.snow.chat.stompclient.ConnectionConfig;
import me.snow.chat.stompclient.RxNettyClientConnectionProvider;
import me.snow.chat.stompclient.ServerInfo;
import me.snow.db.model.iface.IChatConfigAction;
import me.snow.db.model.iface.IChatConfigCommand;
import me.snow.utils.struct.Is;
import studiox.core.ActivityStack;
import studiox.util.Logger;
import tv.jamlive.JamApp;
import tv.jamlive.api.remote.JamApiException;
import tv.jamlive.cache.JamCache;
import tv.jamlive.coordinator.RxBinder;
import tv.jamlive.ui.quiz.QuizCenter;
import tv.jamlive.util.Devices;
import tv.jamlive.util.Version;

*/
/**
 * Session
 * - get method 에 request 를 사용하지 않으면 local cached data 를 return
 *
 * @author nova
 * @since 06/02/2018
 *//*

public class Session implements ChatApiV2 {

    public static final String OS_TYPE = "a";

    private static Session instance;

    private ChatApiV2 api;
    private volatile boolean created = false;
    private volatile boolean enabled = false;

    private BehaviorRelay<ChatApiV2> apiRelay;
    private BehaviorRelay<Session> enabledReceive;

    private BehaviorRelay<GetAccountResponse> account;
    private PublishRelay<WinnersReceive> winnersReceive;
    private PublishRelay<StartQuizReceive> startQuizReceive;
    private PublishRelay<StartQuizReceive> startQuizReceiveRelay;
    private PublishRelay<EndQuizReceive> endQuizReceive;
    private PublishRelay<EndQuizReceive> endQuizReceiveRelay;
    private PublishRelay<StartEpisodeReceive> startEpisodeReceive;
    private BehaviorRelay<Episode> episodeRelay;
    private BehaviorRelay<LeaveEpisodeRequest> leaveEpisodeRequest;
    private StompClient stompClient;
    private PublishRelay<Long> timestampRelay;


    private RxBinder rxBinder = new RxBinder();

    private Session() {
        initRelay();
    }

    */
/**
     * Session 의 캐시를 클리어한다.
     *//*

    public void clearCache() {
        initRelay();
    }

    private void initRelay() {
        apiRelay = BehaviorRelay.create();
        enabledReceive = BehaviorRelay.create();

        account = BehaviorRelay.create();
        winnersReceive = PublishRelay.create();
        startQuizReceive = PublishRelay.create();
        startQuizReceiveRelay = PublishRelay.create();
        endQuizReceive = PublishRelay.create();
        endQuizReceiveRelay = PublishRelay.create();
        startEpisodeReceive = PublishRelay.create();
        episodeRelay = BehaviorRelay.create();
        leaveEpisodeRequest = BehaviorRelay.create();
        timestampRelay = PublishRelay.create();
    }

    public static Session getInstance() {
        if (instance == null) {
            synchronized (Session.class) {
                instance = new Session();
            }
        }
        return instance;
    }

    public boolean isEnabled() {
        return enabled && api != null;
    }

    */
/**
     * API Settings 호출 이후에 사용해야합니다.
     * - 한번만 호출합니다. 생성 되어있을때 다시 생성하게 되면 세션 두개를 다 사용하지 못하게됩니다.
     * - 다시 생성하고 싶다면 명확하게 세션을 닫고 릴리즈 한 후 생성합니다.
     *//*

    public synchronized void create() {
        if (created) {
            Logger.d("session already created");
            return;
        }
        if (api != null) {
            Single.just(api)
                    .subscribe(
                            ChatApiV2::close,
                            throwable -> Logger.e(Session.class, "" + throwable.getLocalizedMessage())
                    );
        }
        Logger.d("session create");
        GetSettingsResponse getSettingsResponse = JamCache.get().settings.get();
        if (getSettingsResponse == null) {
            Logger.w("settings is null");
            return;
        }
        List<String> sessionHosts = getSettingsResponse.getSessionHosts();
        if (sessionHosts == null || sessionHosts.size() == 0) {
            Logger.w("session hosts are null or zero");
            return;
        }
        List<ServerInfo> serverInfos = Stream.of(sessionHosts)
                .map(urls -> {
                    String[] split = urls.split(":");
                    return ServerInfo.of(split[0], Integer.valueOf(split[1]));
                }).toList();
        ConnectionConfig config = new ConnectionConfig()
                .setAppGroundFunction(() -> {
                    boolean foreground = ActivityStack.getInstance().size() > 0 && ActivityStack.getInstance().getCurrent().isRunning();
                    return foreground ? AppGroundStatus.FOREGROUND : AppGroundStatus.BACKGROUND;
                })
                .setServerInfos(serverInfos)
                .setDebug(BuildConfig.DEBUG)
                .setUseSsl(true);
        stompClient = new StompClient(new RxNettyClientConnectionProvider(config));
        JamChatApi jamChatApi = new JamChatApi(stompClient);
        this.api = new JamChatApiV2(jamChatApi);
        this.apiRelay.accept(api);
        this.created = true;
    }


    */
/**
     * enable session
     *//*

    public Observable<Session> enable() {
        Observable<String> sessionObservable = JamCache.get().session.observable();
        Observable<Long> uidObservable = JamCache.get().uid.observable();

        return Observable.zip(sessionObservable, uidObservable,
                (session, uid) -> {
                    Logger.d("enable - zip");
                    Devices devices = Devices.get();
                    return enable(uid, session, devices);
                }
        );
    }

    public Session enable(long uid, String session, Devices devices) {
        try {
            Logger.d("enable - uid: " + uid + ", session: " + session.substring(0, 10));
        } catch (Exception e) {
            Logger.e(Session.class, "" + e.getLocalizedMessage());
        }
        String versionName = Version.versionName();

        AuthInfoImpl authInfo = new AuthInfoImpl()
                .setAppVersion(versionName)
                .setUid(uid)
                .setSession(session)
                .setMccMnc(devices.getMccMnc())
                .setCountry(devices.getRegionCode())
                .setLocale(devices.getLocaleString())
                .setOsType(OS_TYPE);

        if (api == null || !created) {
            created = false;
            create();
        }

        api.setAuthInfo(authInfo);
        this.enabled = true;

        if (enabledReceive != null) {
            enabledReceive.accept(this);
        }

        bind();

        return this;
    }

    public Observable<Session> enabledReceive() {
        return enabledReceive;
    }

    private void bind() {
        rxBinder.unsubscribe();

        rxBinder.bind(apiRelay.filter(api -> api != null)
                .flatMap(api -> Observable.merge(startQuizReceive, api.startQuizReceive())
                        .doOnNext(startQuizReceive -> api.quack(new QuackRequest()
                                .setChatCommand(ChatCommand.START_QUIZ_RECEIVE)
                                .setQuizId(startQuizReceive.getQuiz().getQuizId())))
                        .doOnNext(startQuizReceive -> QuizCenter.getInstance().checkTimeDifferences())
                )
                .subscribe(
                        startQuizReceive -> startQuizReceiveRelay.accept(startQuizReceive),
                        throwable -> {
                            // TODO Add log
                        }
                )
        );

        rxBinder.bind(apiRelay.filter(api -> api != null)
                .flatMap(api -> Observable.merge(endQuizReceive, api.endQuizReceive())
                        .doOnNext(endQuizReceive -> api.quack(new QuackRequest()
                                .setChatCommand(ChatCommand.END_QUIZ_RECEIVE)
                                .setQuizId(endQuizReceive.getQuiz().getQuizId())))
                        .doOnNext(startQuizReceive -> QuizCenter.getInstance().checkTimeDifferences())
                )
                .subscribe(
                        endQuizReceive -> endQuizReceiveRelay.accept(endQuizReceive),
                        throwable -> {
                            // TODO Add log
                        }
                )
        );

        rxBinder.bind(apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::timestampObservable)
                .subscribe(
                        serverTimeStamp -> {
                            timestampRelay.accept(serverTimeStamp);
                            QuizCenter.getInstance().updateServerTimestamp(serverTimeStamp);
                        },
                        throwable -> Logger.e(Session.class, "" + throwable.getLocalizedMessage())
                )
        );
    }

    @Override
    public Observable<StompMessage> topic() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::topic)
                .doOnError(handleError());
    }

    @Override
    public Observable<StompMessage> connectedReceive() {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("connectedReceive"))
                .flatMap(ChatApiV2::connectedReceive)
                .doOnNext(stompMessage -> {
                    try {
                        long zoneId = Long.parseLong(stompMessage.findHeader(StompHeader.ZONE_ID));
                        Logger.d("zone id is: " + zoneId);
                        if (Is.positive(zoneId)) {
                            JamCache.get().zoneId.set(zoneId);
                        }
                    } catch (NumberFormatException e) {
                        Logger.e(Session.class, "connectedReceive - failed to parse zoneId header - " + e.getLocalizedMessage());
                    }

//                    try {
//                        long timestamp = Long.parseLong(stompMessage.findHeader("timestamp"));
//                        QuizCenter.getInstance().setConnectedServerDate(new Date(timestamp));
//                    } catch (NumberFormatException e) {
//                        Logger.e(Session.class, "connectedReceive - failed to parse zoneId header - " + e.getLocalizedMessage());
//                    }
                })
                .doOnError(handleError());
    }

    @Override
    public Observable<Long> timestampObservable() {
        return timestampRelay.doOnError(handleError());
    }

    @Override
    public Observable<ResponseBase> errorReceive() {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("errorReceive"))
                .flatMap(ChatApiV2::errorReceive);
    }

    @Override
    public Observable<WriteResponse> write(WriteRequest writeRequest) {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("write"))
                .flatMap(api -> api.write(writeRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<AnswerResponse> answer(AnswerRequest answerRequest) {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("answer"))
                .flatMap(api -> api.answer(answerRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetEpisodeResponse> getEpisode(GetEpisodeRequest getEpisodeRequest) {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("getEpisode"))
                .flatMap(api -> api.getEpisode(getEpisodeRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetQuizResponse> getQuiz(GetQuizRequest getQuizRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.getQuiz(getQuizRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<JoinEpisodeResponse> joinEpisode(JoinEpisodeRequest joinEpisodeRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.joinEpisode(joinEpisodeRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<LeaveEpisodeResponse> leaveEpisode(LeaveEpisodeRequest leaveEpisodeRequest) {
        this.leaveEpisodeRequest.accept(leaveEpisodeRequest);
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.leaveEpisode(leaveEpisodeRequest))
                .doOnError(handleError());
    }

    public BehaviorRelay<LeaveEpisodeRequest> leaveEpisodeRequest() {
        return leaveEpisodeRequest;
    }

    @Override
    public Observable<GetProfileResponse> getProfile(GetProfileRequest getProfileRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.getProfile(getProfileRequest))
                .doOnNext(getProfileResponse -> JamCache.get().profile.set(getProfileResponse.getProfile()))
                .doOnError(handleError());
    }

    @Override
    public Observable<SetProfileResponse> setProfile(SetProfileRequest setProfileRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.setProfile(setProfileRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetPlayersResponse> getPlayers(GetPlayersRequest getPlayersRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.getPlayers(getPlayersRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<UpdatePushTokenResponse> updatePushToken(UpdatePushTokenRequest updatePushTokenRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.updatePushToken(updatePushTokenRequest));
    }

    @Override
    public Observable<CashoutResponse> cashout(CashoutRequest cashoutRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.cashout(cashoutRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetAccountResponse> getAccount(GetAccountRequest getAccountRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.getAccount(getAccountRequest))
                .doOnNext(account)
                .doOnError(handleError());
    }

    */
/**
     * @return local cached GetAccountResponse
     *//*

    public Observable<GetAccountResponse> getAccount() {
        return account;
    }

    @Override
    public Observable<SetAccountResponse> setAccount(SetAccountRequest setAccountRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.setAccount(setAccountRequest))
                .doOnNext(setAccountResponse -> setAccount(setAccountResponse.getUserAccount()))
                .doOnError(handleError());
    }

    private void setAccount(UserAccount userAccount) {
        if (!account.hasValue() || userAccount == null) {
            return;
        }
        GetAccountResponse value = account.getValue();
        value.setUserAccount(userAccount);
        account.accept(value);
    }

    @Override
    public Observable<GetNotificationsResponse> getNotifications(GetNotificationsRequest getNotificationsRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.getNotifications(getNotificationsRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<ReadyResponse> ready(ReadyRequest readyRequest) {
        return apiRelay.filter(api -> api != null)
                .doOnSubscribe(__ -> Logger.d("ready"))
                .flatMap(api -> api.ready(readyRequest))
                .doOnNext(readyResponse -> {
                    if (readyResponse.getEpisode() != null) {
                        Episode episode = readyResponse.getEpisode();
                        if (Is.positive(episode.getEpisodeId())) {
                            episodeRelay.accept(readyResponse.getEpisode());
                        }
                    }
                    if (readyResponse.getProfile() != null) {
                        JamCache.get().profile.set(readyResponse.getProfile());
                    }
                })
                .doOnError(handleError());
    }

    @Override
    public Observable<SyncLiveResponse> syncLive(SyncLiveRequest syncLiveRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.syncLive(syncLiveRequest))
                .doOnError(handleError());
    }

    */
/**
     * AnswerReceive 를 받지 못한 경우, Answer 를 확인한다.
     *
     * @param checkAnswerRequest
     * @return
     *//*

    @Override
    public Observable<CheckAnswerResponse> checkAnswer(CheckAnswerRequest checkAnswerRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.checkAnswer(checkAnswerRequest))
                .doOnError(handleError());
    }

    */
/**
     * 내가 Winner 인지 한번 더 확인한다.
     *
     * @param checkWinnerRequest
     * @return
     *//*

    @Override
    public Observable<CheckWinnerResponse> checkWinner(CheckWinnerRequest checkWinnerRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> api.checkWinner(checkWinnerRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetLeaderboardResponse> getLeaderboard(GetLeaderboardRequest getLeaderboardRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(chatApiV2 -> chatApiV2.getLeaderboard(getLeaderboardRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<SearchAddressResponse> searchAddress(SearchAddressRequest searchAddressRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(chatApiV2 -> chatApiV2.searchAddress(searchAddressRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<GetGiftBoxResponse> getGiftBox(GetGiftBoxRequest getGiftBoxRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(chatApiV2 -> chatApiV2.getGiftBox(getGiftBoxRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<ApplyGiftResponse> applyGift(ApplyGiftRequest applyGiftRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(chatApiV2 -> chatApiV2.applyGift(applyGiftRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<SetUserDetailResponse> setUserDetail(SetUserDetailRequest setUserDetailRequest) {
        return apiRelay.filter(api -> api != null)
                .flatMap(chatApiV2 -> chatApiV2.setUserDetail(setUserDetailRequest))
                .doOnError(handleError());
    }

    @Override
    public Observable<WriteReceive> writeReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::writeReceive)
                .doOnError(handleError());
    }

    @Override
    public Observable<StartEpisodeReceive> startEpisodeReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> Observable.merge(startEpisodeReceive, api.startEpisodeReceive())
                        .doOnNext(startEpisodeReceive -> {
                            if (startEpisodeReceive.getEpisode() != null) {
                                episodeRelay.accept(startEpisodeReceive.getEpisode());
                            }
                        })
                        .doOnNext(startEpisodeReceive -> {
                            if (api != null && startEpisodeReceive.getEpisode() != null) {
                                QuackRequest quackRequest = new QuackRequest()
                                        .setChatCommand(ChatCommand.START_EPISODE_RECEIVE)
                                        .setEpisodeId(startEpisodeReceive.getEpisode().getEpisodeId());
                                api.quack(quackRequest);
                            }
                        })
                        .distinctUntilChanged(startEpisodeReceive -> {
                            Episode episode = startEpisodeReceive.getEpisode();
                            return Pair.create(episode.getEpisodeId(), episode.getStartedAt());
                        }))
                .doOnError(handleError());
    }

    public void passStartEpisodeReceive(StartEpisodeReceive startEpisodeReceive) {
        this.startEpisodeReceive.accept(startEpisodeReceive);
    }

    @Override
    public Observable<EndEpisodeReceive> endEpisodeReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::endEpisodeReceive)
                .doOnError(handleError());
    }

    public Observable<Episode> episode() {
        return episodeRelay;
    }

    @Override
    public Observable<StartQuizReceive> startQuizReceive() {
        return startQuizReceiveRelay
                .distinctUntilChanged(startQuizReceive -> startQuizReceive.getQuiz().getQuizId());
    }

    public void passStartQuizReceive(StartQuizReceive startQuizReceive) {
        this.startQuizReceive.accept(startQuizReceive);
    }

    @Override
    public Observable<EndQuizReceive> endQuizReceive() {
        return endQuizReceiveRelay
                .distinctUntilChanged(endQuizReceive -> endQuizReceive.getQuiz().getQuizId());
    }

    */
/**
     * - This method used by fcm
     *
     * @param endQuizReceive
     *//*

    public void passEndQuizReceive(EndQuizReceive endQuizReceive) {
        this.endQuizReceive.accept(endQuizReceive);
    }

    @Override
    public Observable<WinnersReceive> winnersReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(api -> Observable.merge(winnersReceive, api.winnersReceive())
                        .doOnNext(
                                winnersReceive -> {
                                    QuackRequest quackRequest = new QuackRequest()
                                            .setChatCommand(ChatCommand.WINNERS_RECEIVE)
                                            .setEpisodeId(winnersReceive.getEpisodeId());
                                    api.quack(quackRequest);
                                }
                        )
                        .distinctUntilChanged(WinnersReceive::getEpisodeId));
    }

    */
/**
     * winnersReceiveOnFcm
     *
     * @param winnersReceive
     *//*

    public void passWinnersReceive(WinnersReceive winnersReceive) {
        this.winnersReceive.accept(winnersReceive);
    }

    @Override
    public Observable<EpisodeUserCountReceive> episodeUserCountReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::episodeUserCountReceive);
    }

    @Override
    public Observable<DismissReceive> dismissReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::dismissReceive);
    }

    @Override
    public Observable<AnswerReceive> answerReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::answerReceive);
    }

    @Override
    public Observable<ReceiveHeartReceive> receiveHeartReceive() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::receiveHeartReceive);
    }

    @Override
    public Observable<RewardPopupReceive> rewardPopupReceive() {
        return null;
    }

    @Override
    public void ack(AckRequest ackRequest) {
        Logger.d("ack");
        apiRelay.filter(api -> api != null)
                .subscribe(api -> api.ack(ackRequest));
    }

    @Override
    public void quack(QuackRequest quackRequest) {
        Logger.d("quack");
        apiRelay.filter(api -> api != null)
                .subscribe(api -> api.quack(quackRequest));
    }

    @Override
    public void close() {
        Logger.d("end");
        apiRelay.filter(api -> api != null)
                .subscribe(ChatApiV2::close);
    }

    @Override
    public void disconnect() {
        apiRelay.filter(api -> api != null)
                .subscribe(ChatApiV2::disconnect);
    }

    @Override
    public AuthInfo getAuthInfo() {
        if (apiRelay.hasValue()) {
            return apiRelay.getValue().getAuthInfo();
        }
        return api.getAuthInfo();
    }

    @Override
    public void setAuthInfo(AuthInfo authInfo) {
        Logger.d("setAuthInfo");
        apiRelay.filter(api -> api != null)
                .subscribe(api -> api.setAuthInfo(authInfo));
    }

    @Override
    public Observable<LifecycleEvent> lifeCycle() {
        return apiRelay.filter(api -> api != null)
                .flatMap(ChatApiV2::lifeCycle);
    }

    @Override
    public IChatConfigCommand findChatConfigCommand(ChatCommand command) {
        if (apiRelay.hasValue()) {
            return apiRelay.getValue().findChatConfigCommand(command);
        }
        return api.findChatConfigCommand(command);
    }

    @Override
    public IChatConfigAction getChatConfigCation() {
        if (apiRelay.hasValue()) {
            return apiRelay.getValue().getChatConfigCation();
        }
        return api.getChatConfigCation();
    }

    @Override
    public boolean canBackgroundConnection() {
        if (apiRelay.hasValue()) {
            return apiRelay.getValue().canBackgroundConnection();
        }
        return api.canBackgroundConnection();
    }

    @Override
    public Completable keep() {
        if (apiRelay.hasValue()) {
            return apiRelay.getValue().keep();
        }
        return api.keep();
    }

    @Override
    public boolean isConnected() {
        if (api == null) {
            return false;
        }
        return api.isConnected();
    }

    public void reset() {
        close();
        enabled = false;
        created = false;
        rxBinder.unsubscribe();
    }

    private Consumer<Throwable> handleError() {
        return throwable -> {
            if (throwable instanceof ChatApiException) {
                ResponseBase responseBase = ((ChatApiException) throwable).getResponseBase();
                showError(responseBase);
            } else if (throwable instanceof JamApiException) {
                ResponseBase responseBase = ((JamApiException) throwable).getResponseBase();
                showError(responseBase);
            }
        };
    }

    private void showError(ResponseBase response) {
        String errorMessage = response.getErrorMessage();
        if (!TextUtils.isEmpty(errorMessage)) {
            Single.just(errorMessage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            message -> {
                                Toast toast = Toast.makeText(JamApp.get().getApplicationContext(), message, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 70);
                                toast.show();
                            },
                            throwable -> Logger.e(Session.class, "" + throwable.getLocalizedMessage())
                    );
        }
    }
}
*/

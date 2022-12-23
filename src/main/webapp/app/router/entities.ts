import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Admin = () => import('@/entities/admin/admin.vue');
// prettier-ignore
const AdminUpdate = () => import('@/entities/admin/admin-update.vue');
// prettier-ignore
const AdminDetails = () => import('@/entities/admin/admin-details.vue');
// prettier-ignore
const Coach = () => import('@/entities/coach/coach.vue');
// prettier-ignore
const CoachUpdate = () => import('@/entities/coach/coach-update.vue');
// prettier-ignore
const CoachDetails = () => import('@/entities/coach/coach-details.vue');
// prettier-ignore
const Team = () => import('@/entities/team/team.vue');
// prettier-ignore
const TeamUpdate = () => import('@/entities/team/team-update.vue');
// prettier-ignore
const TeamDetails = () => import('@/entities/team/team-details.vue');
// prettier-ignore
const Stadium = () => import('@/entities/stadium/stadium.vue');
// prettier-ignore
const StadiumUpdate = () => import('@/entities/stadium/stadium-update.vue');
// prettier-ignore
const StadiumDetails = () => import('@/entities/stadium/stadium-details.vue');
// prettier-ignore
const Player = () => import('@/entities/player/player.vue');
// prettier-ignore
const PlayerUpdate = () => import('@/entities/player/player-update.vue');
// prettier-ignore
const PlayerDetails = () => import('@/entities/player/player-details.vue');
// prettier-ignore
const Train = () => import('@/entities/train/train.vue');
// prettier-ignore
const TrainUpdate = () => import('@/entities/train/train-update.vue');
// prettier-ignore
const TrainDetails = () => import('@/entities/train/train-details.vue');
// prettier-ignore
const Match = () => import('@/entities/match/match.vue');
// prettier-ignore
const MatchUpdate = () => import('@/entities/match/match-update.vue');
// prettier-ignore
const MatchDetails = () => import('@/entities/match/match-details.vue');
// prettier-ignore
const Composition = () => import('@/entities/composition/composition.vue');
// prettier-ignore
const CompositionUpdate = () => import('@/entities/composition/composition-update.vue');
// prettier-ignore
const CompositionDetails = () => import('@/entities/composition/composition-details.vue');
// prettier-ignore
const PlayerArrange = () => import('@/entities/player-arrange/player-arrange.vue');
// prettier-ignore
const PlayerArrangeUpdate = () => import('@/entities/player-arrange/player-arrange-update.vue');
// prettier-ignore
const PlayerArrangeDetails = () => import('@/entities/player-arrange/player-arrange-details.vue');
// prettier-ignore
const Transfer = () => import('@/entities/transfer/transfer.vue');
// prettier-ignore
const TransferUpdate = () => import('@/entities/transfer/transfer-update.vue');
// prettier-ignore
const TransferDetails = () => import('@/entities/transfer/transfer-details.vue');
// prettier-ignore
const League = () => import('@/entities/league/league.vue');
// prettier-ignore
const LeagueUpdate = () => import('@/entities/league/league-update.vue');
// prettier-ignore
const LeagueDetails = () => import('@/entities/league/league-details.vue');
// prettier-ignore
const LeagueBoard = () => import('@/entities/league-board/league-board.vue');
// prettier-ignore
const LeagueBoardUpdate = () => import('@/entities/league-board/league-board-update.vue');
// prettier-ignore
const LeagueBoardDetails = () => import('@/entities/league-board/league-board-details.vue');
// prettier-ignore
const Cup = () => import('@/entities/cup/cup.vue');
// prettier-ignore
const CupUpdate = () => import('@/entities/cup/cup-update.vue');
// prettier-ignore
const CupDetails = () => import('@/entities/cup/cup-details.vue');
// prettier-ignore
const CupBoard = () => import('@/entities/cup-board/cup-board.vue');
// prettier-ignore
const CupBoardUpdate = () => import('@/entities/cup-board/cup-board-update.vue');
// prettier-ignore
const CupBoardDetails = () => import('@/entities/cup-board/cup-board-details.vue');
// prettier-ignore
const NewsPaper = () => import('@/entities/news-paper/news-paper.vue');
// prettier-ignore
const NewsPaperUpdate = () => import('@/entities/news-paper/news-paper-update.vue');
// prettier-ignore
const NewsPaperDetails = () => import('@/entities/news-paper/news-paper-details.vue');
// prettier-ignore
const FriendRequest = () => import('@/entities/friend-request/friend-request.vue');
// prettier-ignore
const FriendRequestUpdate = () => import('@/entities/friend-request/friend-request-update.vue');
// prettier-ignore
const FriendRequestDetails = () => import('@/entities/friend-request/friend-request-details.vue');
// prettier-ignore
const Ticket = () => import('@/entities/ticket/ticket.vue');
// prettier-ignore
const TicketUpdate = () => import('@/entities/ticket/ticket-update.vue');
// prettier-ignore
const TicketDetails = () => import('@/entities/ticket/ticket-details.vue');
// prettier-ignore
const PrivateChat = () => import('@/entities/private-chat/private-chat.vue');
// prettier-ignore
const PrivateChatUpdate = () => import('@/entities/private-chat/private-chat-update.vue');
// prettier-ignore
const PrivateChatDetails = () => import('@/entities/private-chat/private-chat-details.vue');
// prettier-ignore
const PublicChat = () => import('@/entities/public-chat/public-chat.vue');
// prettier-ignore
const PublicChatUpdate = () => import('@/entities/public-chat/public-chat-update.vue');
// prettier-ignore
const PublicChatDetails = () => import('@/entities/public-chat/public-chat-details.vue');
// prettier-ignore
const Message = () => import('@/entities/message/message.vue');
// prettier-ignore
const MessageUpdate = () => import('@/entities/message/message-update.vue');
// prettier-ignore
const MessageDetails = () => import('@/entities/message/message-details.vue');
// prettier-ignore
const Variables = () => import('@/entities/variables/variables.vue');
// prettier-ignore
const VariablesUpdate = () => import('@/entities/variables/variables-update.vue');
// prettier-ignore
const VariablesDetails = () => import('@/entities/variables/variables-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'admin',
      name: 'Admin',
      component: Admin,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/new',
      name: 'AdminCreate',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/edit',
      name: 'AdminEdit',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/view',
      name: 'AdminView',
      component: AdminDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coach',
      name: 'Coach',
      component: Coach,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coach/new',
      name: 'CoachCreate',
      component: CoachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coach/:coachId/edit',
      name: 'CoachEdit',
      component: CoachUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'coach/:coachId/view',
      name: 'CoachView',
      component: CoachDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team',
      name: 'Team',
      component: Team,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/new',
      name: 'TeamCreate',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/edit',
      name: 'TeamEdit',
      component: TeamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'team/:teamId/view',
      name: 'TeamView',
      component: TeamDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stadium',
      name: 'Stadium',
      component: Stadium,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stadium/new',
      name: 'StadiumCreate',
      component: StadiumUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stadium/:stadiumId/edit',
      name: 'StadiumEdit',
      component: StadiumUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stadium/:stadiumId/view',
      name: 'StadiumView',
      component: StadiumDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player',
      name: 'Player',
      component: Player,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player/new',
      name: 'PlayerCreate',
      component: PlayerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player/:playerId/edit',
      name: 'PlayerEdit',
      component: PlayerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player/:playerId/view',
      name: 'PlayerView',
      component: PlayerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'train',
      name: 'Train',
      component: Train,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'train/new',
      name: 'TrainCreate',
      component: TrainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'train/:trainId/edit',
      name: 'TrainEdit',
      component: TrainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'train/:trainId/view',
      name: 'TrainView',
      component: TrainDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match',
      name: 'Match',
      component: Match,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/new',
      name: 'MatchCreate',
      component: MatchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/:matchId/edit',
      name: 'MatchEdit',
      component: MatchUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'match/:matchId/view',
      name: 'MatchView',
      component: MatchDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'composition',
      name: 'Composition',
      component: Composition,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'composition/new',
      name: 'CompositionCreate',
      component: CompositionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'composition/:compositionId/edit',
      name: 'CompositionEdit',
      component: CompositionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'composition/:compositionId/view',
      name: 'CompositionView',
      component: CompositionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-arrange',
      name: 'PlayerArrange',
      component: PlayerArrange,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-arrange/new',
      name: 'PlayerArrangeCreate',
      component: PlayerArrangeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-arrange/:playerArrangeId/edit',
      name: 'PlayerArrangeEdit',
      component: PlayerArrangeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-arrange/:playerArrangeId/view',
      name: 'PlayerArrangeView',
      component: PlayerArrangeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transfer',
      name: 'Transfer',
      component: Transfer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transfer/new',
      name: 'TransferCreate',
      component: TransferUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transfer/:transferId/edit',
      name: 'TransferEdit',
      component: TransferUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transfer/:transferId/view',
      name: 'TransferView',
      component: TransferDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league',
      name: 'League',
      component: League,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league/new',
      name: 'LeagueCreate',
      component: LeagueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league/:leagueId/edit',
      name: 'LeagueEdit',
      component: LeagueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league/:leagueId/view',
      name: 'LeagueView',
      component: LeagueDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league-board',
      name: 'LeagueBoard',
      component: LeagueBoard,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league-board/new',
      name: 'LeagueBoardCreate',
      component: LeagueBoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league-board/:leagueBoardId/edit',
      name: 'LeagueBoardEdit',
      component: LeagueBoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'league-board/:leagueBoardId/view',
      name: 'LeagueBoardView',
      component: LeagueBoardDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup',
      name: 'Cup',
      component: Cup,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup/new',
      name: 'CupCreate',
      component: CupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup/:cupId/edit',
      name: 'CupEdit',
      component: CupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup/:cupId/view',
      name: 'CupView',
      component: CupDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup-board',
      name: 'CupBoard',
      component: CupBoard,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup-board/new',
      name: 'CupBoardCreate',
      component: CupBoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup-board/:cupBoardId/edit',
      name: 'CupBoardEdit',
      component: CupBoardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cup-board/:cupBoardId/view',
      name: 'CupBoardView',
      component: CupBoardDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'news-paper',
      name: 'NewsPaper',
      component: NewsPaper,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'news-paper/new',
      name: 'NewsPaperCreate',
      component: NewsPaperUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'news-paper/:newsPaperId/edit',
      name: 'NewsPaperEdit',
      component: NewsPaperUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'news-paper/:newsPaperId/view',
      name: 'NewsPaperView',
      component: NewsPaperDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'friend-request',
      name: 'FriendRequest',
      component: FriendRequest,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'friend-request/new',
      name: 'FriendRequestCreate',
      component: FriendRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'friend-request/:friendRequestId/edit',
      name: 'FriendRequestEdit',
      component: FriendRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'friend-request/:friendRequestId/view',
      name: 'FriendRequestView',
      component: FriendRequestDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket',
      name: 'Ticket',
      component: Ticket,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/new',
      name: 'TicketCreate',
      component: TicketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/:ticketId/edit',
      name: 'TicketEdit',
      component: TicketUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket/:ticketId/view',
      name: 'TicketView',
      component: TicketDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'private-chat',
      name: 'PrivateChat',
      component: PrivateChat,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'private-chat/new',
      name: 'PrivateChatCreate',
      component: PrivateChatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'private-chat/:privateChatId/edit',
      name: 'PrivateChatEdit',
      component: PrivateChatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'private-chat/:privateChatId/view',
      name: 'PrivateChatView',
      component: PrivateChatDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'public-chat',
      name: 'PublicChat',
      component: PublicChat,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'public-chat/new',
      name: 'PublicChatCreate',
      component: PublicChatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'public-chat/:publicChatId/edit',
      name: 'PublicChatEdit',
      component: PublicChatUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'public-chat/:publicChatId/view',
      name: 'PublicChatView',
      component: PublicChatDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message',
      name: 'Message',
      component: Message,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/new',
      name: 'MessageCreate',
      component: MessageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/:messageId/edit',
      name: 'MessageEdit',
      component: MessageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/:messageId/view',
      name: 'MessageView',
      component: MessageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'variables',
      name: 'Variables',
      component: Variables,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'variables/new',
      name: 'VariablesCreate',
      component: VariablesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'variables/:variablesId/edit',
      name: 'VariablesEdit',
      component: VariablesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'variables/:variablesId/view',
      name: 'VariablesView',
      component: VariablesDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};

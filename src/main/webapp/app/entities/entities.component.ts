import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import AdminService from './admin/admin.service';
import CoachService from './coach/coach.service';
import TeamService from './team/team.service';
import StadiumService from './stadium/stadium.service';
import PlayerService from './player/player.service';
import TrainService from './train/train.service';
import MatchService from './match/match.service';
import CompositionService from './composition/composition.service';
import PlayerArrangeService from './player-arrange/player-arrange.service';
import TransferService from './transfer/transfer.service';
import LeagueService from './league/league.service';
import LeagueBoardService from './league-board/league-board.service';
import CupService from './cup/cup.service';
import CupBoardService from './cup-board/cup-board.service';
import NewsPaperService from './news-paper/news-paper.service';
import FriendRequestService from './friend-request/friend-request.service';
import TicketService from './ticket/ticket.service';
import PrivateChatService from './private-chat/private-chat.service';
import PublicChatService from './public-chat/public-chat.service';
import MessageService from './message/message.service';
import VariablesService from './variables/variables.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('adminService') private adminService = () => new AdminService();
  @Provide('coachService') private coachService = () => new CoachService();
  @Provide('teamService') private teamService = () => new TeamService();
  @Provide('stadiumService') private stadiumService = () => new StadiumService();
  @Provide('playerService') private playerService = () => new PlayerService();
  @Provide('trainService') private trainService = () => new TrainService();
  @Provide('matchService') private matchService = () => new MatchService();
  @Provide('compositionService') private compositionService = () => new CompositionService();
  @Provide('playerArrangeService') private playerArrangeService = () => new PlayerArrangeService();
  @Provide('transferService') private transferService = () => new TransferService();
  @Provide('leagueService') private leagueService = () => new LeagueService();
  @Provide('leagueBoardService') private leagueBoardService = () => new LeagueBoardService();
  @Provide('cupService') private cupService = () => new CupService();
  @Provide('cupBoardService') private cupBoardService = () => new CupBoardService();
  @Provide('newsPaperService') private newsPaperService = () => new NewsPaperService();
  @Provide('friendRequestService') private friendRequestService = () => new FriendRequestService();
  @Provide('ticketService') private ticketService = () => new TicketService();
  @Provide('privateChatService') private privateChatService = () => new PrivateChatService();
  @Provide('publicChatService') private publicChatService = () => new PublicChatService();
  @Provide('messageService') private messageService = () => new MessageService();
  @Provide('variablesService') private variablesService = () => new VariablesService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}

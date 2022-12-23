import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import CompositionService from '@/entities/composition/composition.service';
import { IComposition } from '@/shared/model/composition.model';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import LeagueService from '@/entities/league/league.service';
import { ILeague } from '@/shared/model/league.model';

import CupBoardService from '@/entities/cup-board/cup-board.service';
import { ICupBoard } from '@/shared/model/cup-board.model';

import { IMatch, Match } from '@/shared/model/match.model';
import MatchService from './match.service';
import { Weather } from '@/shared/model/enumerations/weather.model';
import { MatchType } from '@/shared/model/enumerations/match-type.model';

const validations: any = {
  match: {
    date: {
      required,
    },
    weather: {
      required,
    },
    hostGoals: {},
    guestGoals: {},
    events: {},
    type: {},
  },
};

@Component({
  validations,
})
export default class MatchUpdate extends Vue {
  @Inject('matchService') private matchService: () => MatchService;
  @Inject('alertService') private alertService: () => AlertService;

  public match: IMatch = new Match();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('compositionService') private compositionService: () => CompositionService;

  public compositions: IComposition[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('leagueService') private leagueService: () => LeagueService;

  public leagues: ILeague[] = [];

  @Inject('cupBoardService') private cupBoardService: () => CupBoardService;

  public cupBoards: ICupBoard[] = [];
  public weatherValues: string[] = Object.keys(Weather);
  public matchTypeValues: string[] = Object.keys(MatchType);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchId) {
        vm.retrieveMatch(to.params.matchId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.match.id) {
      this.matchService()
        .update(this.match)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.match.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.matchService()
        .create(this.match)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.match.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.match[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.match[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.match[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.match[field] = null;
    }
  }

  public retrieveMatch(matchId): void {
    this.matchService()
      .find(matchId)
      .then(res => {
        res.date = new Date(res.date);
        this.match = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.compositionService()
      .retrieve()
      .then(res => {
        this.compositions = res.data;
      });
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.leagueService()
      .retrieve()
      .then(res => {
        this.leagues = res.data;
      });
    this.cupBoardService()
      .retrieve()
      .then(res => {
        this.cupBoards = res.data;
      });
  }
}

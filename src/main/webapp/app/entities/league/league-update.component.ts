import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, maxValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import NewsPaperService from '@/entities/news-paper/news-paper.service';
import { INewsPaper } from '@/shared/model/news-paper.model';

import MatchService from '@/entities/match/match.service';
import { IMatch } from '@/shared/model/match.model';

import LeagueBoardService from '@/entities/league-board/league-board.service';
import { ILeagueBoard } from '@/shared/model/league-board.model';

import { ILeague, League } from '@/shared/model/league.model';
import LeagueService from './league.service';

const validations: any = {
  league: {
    clazz: {
      required,
      numeric,
      min: minValue(1),
      max: maxValue(14),
    },
    events: {},
    finished: {},
    start: {},
  },
};

@Component({
  validations,
})
export default class LeagueUpdate extends Vue {
  @Inject('leagueService') private leagueService: () => LeagueService;
  @Inject('alertService') private alertService: () => AlertService;

  public league: ILeague = new League();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('newsPaperService') private newsPaperService: () => NewsPaperService;

  public newsPapers: INewsPaper[] = [];

  @Inject('matchService') private matchService: () => MatchService;

  public matches: IMatch[] = [];

  @Inject('leagueBoardService') private leagueBoardService: () => LeagueBoardService;

  public leagueBoards: ILeagueBoard[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.leagueId) {
        vm.retrieveLeague(to.params.leagueId);
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
    if (this.league.id) {
      this.leagueService()
        .update(this.league)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.league.updated', { param: param.id });
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
      this.leagueService()
        .create(this.league)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.league.created', { param: param.id });
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
      this.league[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.league[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.league[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.league[field] = null;
    }
  }

  public retrieveLeague(leagueId): void {
    this.leagueService()
      .find(leagueId)
      .then(res => {
        res.start = new Date(res.start);
        this.league = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.newsPaperService()
      .retrieve()
      .then(res => {
        this.newsPapers = res.data;
      });
    this.matchService()
      .retrieve()
      .then(res => {
        this.matches = res.data;
      });
    this.leagueBoardService()
      .retrieve()
      .then(res => {
        this.leagueBoards = res.data;
      });
  }
}

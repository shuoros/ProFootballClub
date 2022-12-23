import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import LeagueService from '@/entities/league/league.service';
import { ILeague } from '@/shared/model/league.model';

import { ILeagueBoard, LeagueBoard } from '@/shared/model/league-board.model';
import LeagueBoardService from './league-board.service';

const validations: any = {
  leagueBoard: {
    position: {
      required,
      numeric,
      min: minValue(1),
      max: maxValue(8),
    },
    win: {},
    lose: {},
    draw: {},
    goalDifference: {},
    points: {},
  },
};

@Component({
  validations,
})
export default class LeagueBoardUpdate extends Vue {
  @Inject('leagueBoardService') private leagueBoardService: () => LeagueBoardService;
  @Inject('alertService') private alertService: () => AlertService;

  public leagueBoard: ILeagueBoard = new LeagueBoard();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('leagueService') private leagueService: () => LeagueService;

  public leagues: ILeague[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.leagueBoardId) {
        vm.retrieveLeagueBoard(to.params.leagueBoardId);
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
    if (this.leagueBoard.id) {
      this.leagueBoardService()
        .update(this.leagueBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.leagueBoard.updated', { param: param.id });
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
      this.leagueBoardService()
        .create(this.leagueBoard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.leagueBoard.created', { param: param.id });
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

  public retrieveLeagueBoard(leagueBoardId): void {
    this.leagueBoardService()
      .find(leagueBoardId)
      .then(res => {
        this.leagueBoard = res;
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
    this.leagueService()
      .retrieve()
      .then(res => {
        this.leagues = res.data;
      });
  }
}
